package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class SetPointerMappingReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";
  private final byte status;
  private final short sequenceNumber;

  public static SetPointerMappingReply readSetPointerMappingReply(byte status, short sequenceNumber, X11Input in) throws IOException {
    SetPointerMappingReply.SetPointerMappingReplyBuilder javaBuilder = SetPointerMappingReply.builder();
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


  public static class SetPointerMappingReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte status;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;

    public SetPointerMappingReply.SetPointerMappingReplyBuilder status(MappingStatus status) {
      this.status = (byte) status.getValue();
      return this;
    }

    public SetPointerMappingReply.SetPointerMappingReplyBuilder status(byte status) {
      this.status = status;
      return this;
    }

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    SetPointerMappingReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetPointerMappingReply.SetPointerMappingReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetPointerMappingReply build() {
      return new SetPointerMappingReply(this.status, this.sequenceNumber);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetPointerMappingReply.SetPointerMappingReplyBuilder(status=" + this.status + ", sequenceNumber=" + this.sequenceNumber + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetPointerMappingReply(final byte status, final short sequenceNumber) {
    this.status = status;
    this.sequenceNumber = sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public static SetPointerMappingReply.SetPointerMappingReplyBuilder builder() {
    return new SetPointerMappingReply.SetPointerMappingReplyBuilder();
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
    if (!(o instanceof SetPointerMappingReply)) return false;
    final SetPointerMappingReply other = (SetPointerMappingReply) o;
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
    return "SetPointerMappingReply(status=" + this.getStatus() + ", sequenceNumber=" + this.getSequenceNumber() + ")";
  }
}
