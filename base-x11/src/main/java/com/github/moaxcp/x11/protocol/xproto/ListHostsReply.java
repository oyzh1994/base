package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public final class ListHostsReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";
  private final byte mode;
  private final short sequenceNumber;
  private final ImmutableList<Host> hosts;

  public static ListHostsReply readListHostsReply(byte mode, short sequenceNumber, X11Input in) throws IOException {
    ListHostsReply.ListHostsReplyBuilder javaBuilder = ListHostsReply.builder();
    int length = in.readCard32();
    short hostsLen = in.readCard16();
    byte[] pad5 = in.readPad(22);
    MutableList<Host> hosts = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(hostsLen));
    for (int i = 0; i < Short.toUnsignedInt(hostsLen); i++) {
      hosts.add(Host.readHost(in));
    }
    javaBuilder.mode(mode);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.hosts(hosts.toImmutable());
    if (javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeByte(mode);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    short hostsLen = (short) hosts.size();
    out.writeCard16(hostsLen);
    out.writePad(22);
    for (Host t : hosts) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(hosts);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ListHostsReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte mode;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<Host> hosts;

    public ListHostsReply.ListHostsReplyBuilder mode(AccessControl mode) {
      this.mode = (byte) mode.getValue();
      return this;
    }

    public ListHostsReply.ListHostsReplyBuilder mode(byte mode) {
      this.mode = mode;
      return this;
    }

    public int getSize() {
      return 32 + XObject.sizeOf(hosts);
    }

    @java.lang.SuppressWarnings("all")
    ListHostsReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListHostsReply.ListHostsReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListHostsReply.ListHostsReplyBuilder hosts(final ImmutableList<Host> hosts) {
      if (hosts == null) {
        throw new java.lang.NullPointerException("hosts is marked non-null but is null");
      }
      this.hosts = hosts;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ListHostsReply build() {
      return new ListHostsReply(this.mode, this.sequenceNumber, this.hosts);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ListHostsReply.ListHostsReplyBuilder(mode=" + this.mode + ", sequenceNumber=" + this.sequenceNumber + ", hosts=" + this.hosts + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ListHostsReply(final byte mode, final short sequenceNumber, final ImmutableList<Host> hosts) {
    if (hosts == null) {
      throw new java.lang.NullPointerException("hosts is marked non-null but is null");
    }
    this.mode = mode;
    this.sequenceNumber = sequenceNumber;
    this.hosts = hosts;
  }

  @java.lang.SuppressWarnings("all")
  public static ListHostsReply.ListHostsReplyBuilder builder() {
    return new ListHostsReply.ListHostsReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getMode() {
    return this.mode;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<Host> getHosts() {
    return this.hosts;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ListHostsReply)) return false;
    final ListHostsReply other = (ListHostsReply) o;
    if (this.getMode() != other.getMode()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    final java.lang.Object this$hosts = this.getHosts();
    final java.lang.Object other$hosts = other.getHosts();
    if (this$hosts == null ? other$hosts != null : !this$hosts.equals(other$hosts)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getMode();
    result = result * PRIME + this.getSequenceNumber();
    final java.lang.Object $hosts = this.getHosts();
    result = result * PRIME + ($hosts == null ? 43 : $hosts.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ListHostsReply(mode=" + this.getMode() + ", sequenceNumber=" + this.getSequenceNumber() + ", hosts=" + this.getHosts() + ")";
  }
}
