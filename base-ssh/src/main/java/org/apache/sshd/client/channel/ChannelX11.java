/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.sshd.client.channel;

import org.apache.sshd.client.future.DefaultOpenFuture;
import org.apache.sshd.client.future.OpenFuture;
import org.apache.sshd.client.session.ClientConnectionService;
import org.apache.sshd.client.x11.X11IoHandler;
import org.apache.sshd.common.Property;
import org.apache.sshd.common.SshConstants;
import org.apache.sshd.common.channel.ChannelOutputStream;
import org.apache.sshd.common.io.IoConnectFuture;
import org.apache.sshd.common.io.IoConnector;
import org.apache.sshd.common.io.IoHandler;
import org.apache.sshd.common.io.IoSession;
import org.apache.sshd.common.util.buffer.Buffer;
import org.apache.sshd.common.util.buffer.BufferUtils;
import org.apache.sshd.common.util.buffer.ByteArrayBuffer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author <a href="mailto:dev@mina.apache.org">Apache MINA SSHD Project</a>
 */
public class ChannelX11 extends AbstractClientChannel {

    public static final Property<Object> X11_COOKIE = Property.object("x11-cookie");
    public static final Property<Object> X11_COOKIE_HEX = Property.object("x11-cookie-hex");

    /**
     * Size of the fixed part of an X11 connection setup request, followed by the
     * authentication protocol name and the authentication data (see the X11
     * protocol specification, "Connection Setup Request").
     */
    private static final int SETUP_HEADER_LENGTH = 12;

    private final AtomicBoolean isInitialized = new AtomicBoolean(false);
    private final String host;
    private final int port;

    private IoSession x11;
    /** Connection setup request accumulated while it is still incomplete. */
    private byte[] setupRequest;
    /** Set once the connection setup request cannot be honoured. */
    private boolean setupRejected;

    public ChannelX11(String host, int port) {
        super("x11");
        this.host = host;
        this.port = port;
    }

    @Override
    public OpenFuture open(long recipient, long rwSize, long packetSize, Buffer buffer) {
        final DefaultOpenFuture defaultOpenFuture = new DefaultOpenFuture(this, futureLock);
        super.openFuture = defaultOpenFuture;

        final IoConnector connector
                = getSession().getFactoryManager().getIoServiceFactory().createConnector(createX11IoHandler());
        addCloseFutureListener(future -> {
            if (future.isClosed()) {
                connector.close(true);
            }
        });

        final IoConnectFuture connectFuture = connector.connect(new InetSocketAddress(host, port), this, null);
        connectFuture.addListener(future -> {
            if (future.isConnected()) {
                x11 = future.getSession();
                handleOpenSuccess(recipient, rwSize, packetSize, buffer);
            } else {
                if (future.getException() != null) {
                    defaultOpenFuture.setException(future.getException());
                } else {
                    defaultOpenFuture.setValue(false);
                }
                unregisterSelf();
            }
        });

        return defaultOpenFuture;

    }

    @Override
    protected void doOpen() throws IOException {
        setOut(new ChannelOutputStream(this, getRemoteWindow(), log, SshConstants.SSH_MSG_CHANNEL_DATA, true));
    }

    @Override
    protected void doWriteData(byte[] data, int off, long len) throws IOException {
        // Incoming channel data is charged against the local window as soon as the
        // packet is decoded (AbstractChannel.validateIncomingDataSize) and has to be
        // released once it has been taken over here, which is what later makes the
        // peer send an SSH_MSG_CHANNEL_WINDOW_ADJUST. Without this release the window
        // only ever shrinks: once the initial window is used up the peer may not send
        // anything else and the X11 channel stalls. Small clients such as xeyes or
        // xclock stay below it, while the requests of any real toolkit (icons,
        // pixmaps, fonts - none of which can use shared memory across hosts) run it
        // dry before the first paint and leave a black window. The server side does
        // the same in ChannelForwardedX11.doWriteData.
        getLocalWindow().release(len);

        if (setupRejected) {
            // The connection was refused locally, the channel is on its way out.
            return;
        }

        if (!isInitialized.get()) {
            if (handleConnectionSetup(data, off, (int) len)) {
                isInitialized.set(true);
            }
            return;
        }

        if (x11.isOpen()) {
            // The session's decoder buffer is re-used for the next packet while
            // the socket write completes asynchronously, so the bytes must be
            // copied before they are queued - exactly like the server side does
            // in ChannelForwardedX11. Wrapping them corrupts large transfers
            // (window contents, images) in a way that depends on timing.
            x11.writeBuffer(ByteArrayBuffer.getCompactClone(data, off, (int) len));
        } else if (x11.isClosing() || x11.isClosed()) {
            sendEof();
        }
    }

    /**
     * Validate the X11 connection setup request and forward it to the local X server.
     *
     * <p>
     * The request is not guaranteed to arrive in a single {@code SSH_MSG_CHANNEL_DATA} packet: the remote forwarder
     * sends whatever a single read of the X client socket returned, and TCP may split it further. Partial data is
     * accumulated until the whole request is available (the same thing JSch does in {@code ChannelX11.addCache}).
     * Dropping it leaves the X client waiting for a setup reply that never comes, which shows up as a black window.
     * </p>
     *
     * @param  data        the received channel data
     * @param  off         offset of the channel data within {@code data}
     * @param  len         length of the channel data
     * @return             {@code true} once the request is complete and has been handled, {@code false} while more
     *                     data is needed
     * @throws IOException on error
     */
    private boolean handleConnectionSetup(byte[] data, int off, int len) throws IOException {
        byte[] request = appendSetupRequest(data, off, len);
        int[] auth = decodeSetupRequest(request);
        if (auth == null) {
            return false;
        }

        int authOffset = auth[0];
        int dlen = auth[1];
        setupRequest = null;

        final byte[] xCookie = getXCookie();
        if (xCookie == null) {
            rejectSetup();
            return true;
        }

        byte[] bar = new byte[dlen];
        System.arraycopy(request, authOffset, bar, 0, dlen);

        if (Arrays.equals(xCookie, bar)) {
            x11.writeBuffer(new ByteArrayBuffer(request, 0, request.length));
        } else {
            // Without this the X client just hangs (a black window) and there is
            // nothing in the log to explain why.
            log.warn("handleConnectionSetup({}) X11 authentication cookie mismatch:"
                     + " received {} bytes, expected {} bytes - closing the channel",
                    this, dlen, xCookie.length);
            if (log.isDebugEnabled()) {
                log.debug("handleConnectionSetup({}) received={} expected={}",
                        this, BufferUtils.toHex(bar), BufferUtils.toHex(xCookie));
            }
            rejectSetup();
        }
        return true;
    }

    /**
     * Locate the authentication data of an X11 connection setup request.
     *
     * @param  request the bytes received so far
     * @return         the offset and length of the authentication data, or {@code null} when {@code request} does not
     *                 hold the complete request yet
     */
    static int[] decodeSetupRequest(byte[] request) {
        if (request.length < SETUP_HEADER_LENGTH) {
            return null;
        }

        int plen = (request[6] & 0xff) * 256 + (request[7] & 0xff);
        int dlen = (request[8] & 0xff) * 256 + (request[9] & 0xff);

        if ((request[0] & 0xff) == 0x6c) {
            // Little endian client: the lengths are read backwards above.
            plen = ((plen >>> 8) & 0xff) | ((plen << 8) & 0xff00);
            dlen = ((dlen >>> 8) & 0xff) | ((dlen << 8) & 0xff00);
        }

        // the protocol name is padded to a 4 byte boundary, the data is not
        int authOffset = SETUP_HEADER_LENGTH + plen + ((-plen) & 3);
        if (request.length < authOffset + dlen) {
            return null;
        }
        return new int[] { authOffset, dlen };
    }

    /** Append the received channel data to the pending connection setup request. */
    private byte[] appendSetupRequest(byte[] data, int off, int len) {
        byte[] pending = setupRequest;
        int pendingLength = (pending == null) ? 0 : pending.length;
        byte[] merged = new byte[pendingLength + len];
        if (pendingLength > 0) {
            System.arraycopy(pending, 0, merged, 0, pendingLength);
        }
        System.arraycopy(data, off, merged, pendingLength, len);
        setupRequest = merged;
        return merged;
    }

    private void rejectSetup() throws IOException {
        setupRequest = null;
        setupRejected = true;
        sendEof();
    }

    @Override
    public void handleEof() throws IOException {
        super.handleEof();
        close(true);
    }

    private void unregisterSelf() {
        getSession().getService(ClientConnectionService.class)
                .unregisterChannel(this);
        close(true);
    }

    protected byte[] getXCookie() {
        final Object xCookie = X11_COOKIE.getOrNull(getSession());
        if (xCookie instanceof byte[]) {
            return (byte[]) xCookie;
        }
        return null;
    }

    protected IoHandler createX11IoHandler() {
        return new X11IoHandler(this, log);
    }
}