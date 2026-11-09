package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class SetCrtcConfigReply implements XReply {
  public static final String PLUGIN_NAME = "randr";
  private final byte status;
  private final short sequenceNumber;
  private final int timestamp;

  public static SetCrtcConfigReply readSetCrtcConfigReply(byte status, short sequenceNumber, X11Input in) throws IOException {
    SetCrtcConfigReply.SetCrtcConfigReplyBuilder javaBuilder = SetCrtcConfigReply.builder();
    int length = in.readCard32();
    int timestamp = in.readCard32();
    byte[] pad5 = in.readPad(20);
    javaBuilder.status(status);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.timestamp(timestamp);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(status);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(timestamp);
    out.writePad(20);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetCrtcConfigReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte status;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int timestamp;

    public SetCrtcConfigReply.SetCrtcConfigReplyBuilder status(SetConfig status) {
      this.status = (byte) status.getValue();
      return this;
    }

    public SetCrtcConfigReply.SetCrtcConfigReplyBuilder status(byte status) {
      this.status = status;
      return this;
    }

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    SetCrtcConfigReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetCrtcConfigReply.SetCrtcConfigReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetCrtcConfigReply.SetCrtcConfigReplyBuilder timestamp(final int timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetCrtcConfigReply build() {
      return new SetCrtcConfigReply(this.status, this.sequenceNumber, this.timestamp);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetCrtcConfigReply.SetCrtcConfigReplyBuilder(status=" + this.status + ", sequenceNumber=" + this.sequenceNumber + ", timestamp=" + this.timestamp + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetCrtcConfigReply(final byte status, final short sequenceNumber, final int timestamp) {
    this.status = status;
    this.sequenceNumber = sequenceNumber;
    this.timestamp = timestamp;
  }

  @java.lang.SuppressWarnings("all")
  public static SetCrtcConfigReply.SetCrtcConfigReplyBuilder builder() {
    return new SetCrtcConfigReply.SetCrtcConfigReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getStatus() {
    return this.status;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getTimestamp() {
    return this.timestamp;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetCrtcConfigReply)) return false;
    final SetCrtcConfigReply other = (SetCrtcConfigReply) o;
    if (this.getStatus() != other.getStatus()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getTimestamp() != other.getTimestamp()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getStatus();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getTimestamp();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetCrtcConfigReply(status=" + this.getStatus() + ", sequenceNumber=" + this.getSequenceNumber() + ", timestamp=" + this.getTimestamp() + ")";
  }
}
