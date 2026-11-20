package cn.oyzh.common.network;

import org.junit.Test;

import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.CancellationException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * NetworkUtil连接重置检测单元测试
 *
 * @author oyzh
 * @since 2026-11-21
 */
public class NetworkUtilTest {

    @Test
    public void detectsResetInNestedCause() {
        Exception error = new IllegalStateException("stream failed",
                new SocketException("Connection reset by peer"));

        assertTrue(NetworkUtil.isConnectionReset(error));
    }

    @Test
    public void detectsLocalizedConnectionResetMessages() {
        assertReset("java.net.SocketException: 远程主机强迫关闭了一个现有的连接。");
        assertReset("I/O error: 远程主机强制关闭了一个现有的连接 (os error 10054)");
        assertReset("你的主机中的软件中止了一个已建立的连接");
        assertReset("软件导致连接中止");
        assertReset("重置时网络连接断开");
        assertReset("连接被重设");
        assertReset("连接被对方重置");
        assertReset("按对等方重置连接");
        assertReset("连接\u00A0被\u3000重置");
    }

    @Test
    public void detectsOtherDisconnectedTransportVariants() {
        assertReset("Broken pipe");
        assertReset("Connection reset by peer: socket write error");
        assertReset("Software caused connection abort: recv failed");
        assertReset("unexpected end of stream on https://example.com");
        assertReset("connection was closed unexpectedly");
        assertReset("received a GOAWAY frame");
        assertReset("stream was reset: REFUSED_STREAM");
        assertReset("管道破裂");
    }

    @Test
    public void detectsKnownTransientExceptionTypes() {
        assertTrue(NetworkUtil.isConnectionReset(
                new NoHttpResponseException("no response")));
        assertTrue(NetworkUtil.isConnectionReset(
                new PrematureChannelClosureException("channel closed")));
    }

    @Test
    public void detectsWindowsSocketErrorCodes() {
        assertReset("[WinError 10054]");
        assertReset("WSAECONNRESET");
        assertReset("WSAECONNABORTED");
        assertReset("WSAENETRESET");
    }

    @Test
    public void ignoresNonRetryableFailures() {
        assertFalse(NetworkUtil.isConnectionReset(
                new IllegalStateException("HTTP 401 Unauthorized")));
        assertFalse(NetworkUtil.isConnectionReset(
                new SocketException("Connection refused")));
        assertFalse(NetworkUtil.isConnectionReset(
                new SocketTimeoutException("Read timed out")));
        assertFalse(NetworkUtil.isConnectionReset(
                new InterruptedIOException("request cancelled")));
        assertFalse(NetworkUtil.isConnectionReset(
                new CancellationException("user cancelled")));
        assertFalse(NetworkUtil.isConnectionReset(
                new IllegalStateException("SSL certificate validation failed")));
        assertFalse(NetworkUtil.isConnectionReset(
                new IllegalStateException("stream was reset: CANCEL")));
        assertFalse(NetworkUtil.isConnectionReset(new EOFException()));
    }

    private static void assertReset(String message) {
        assertTrue("Expected reset for: " + message,
                NetworkUtil.isConnectionReset(new IOException(message)));
    }

    private static final class NoHttpResponseException extends IOException {

        private NoHttpResponseException(String message) {
            super(message);
        }
    }

    private static final class PrematureChannelClosureException extends IOException {

        private PrematureChannelClosureException(String message) {
            super(message);
        }
    }
}
