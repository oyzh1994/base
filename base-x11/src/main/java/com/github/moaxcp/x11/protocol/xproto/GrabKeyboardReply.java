package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class GrabKeyboardReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";
  private final byte status;
  private final short sequenceNumber;

  public static GrabKeyboardReply readGrabKeyboardReply(byte status, short sequenceNumber, X11Input in) throws IOException {
    GrabKeyboardReply.GrabKeyboardReplyBuilder javaBuilder = GrabKeyboardReply.builder();
    int length = in.readCard32();
    in.readPad(24);
    javaBuilder.status(status);
    javaBuilder.sequenceNumber(sequenceNumber);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeByte(status);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GrabKeyboardReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte status;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;

    public GrabKeyboardReply.GrabKeyboardReplyBuilder status(GrabStatus status) {
      this.status = (byte) status.getValue();
      return this;
    }

    public GrabKeyboardReply.GrabKeyboardReplyBuilder status(byte status) {
      this.status = status;
      return this;
    }

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    GrabKeyboardReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GrabKeyboardReply.GrabKeyboardReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GrabKeyboardReply build() {
      return new GrabKeyboardReply(this.status, this.sequenceNumber);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GrabKeyboardReply.GrabKeyboardReplyBuilder(status=" + this.status + ", sequenceNumber=" + this.sequenceNumber + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GrabKeyboardReply(final byte status, final short sequenceNumber) {
    this.status = status;
    this.sequenceNumber = sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public static GrabKeyboardReply.GrabKeyboardReplyBuilder builder() {
    return new GrabKeyboardReply.GrabKeyboardReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getStatus() {
    return this.status;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GrabKeyboardReply)) return false;
    final GrabKeyboardReply other = (GrabKeyboardReply) o;
    if (this.getStatus() != other.getStatus()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getStatus();
    result = result * PRIME + this.getSequenceNumber();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GrabKeyboardReply(status=" + this.getStatus() + ", sequenceNumber=" + this.getSequenceNumber() + ")";
  }
}
