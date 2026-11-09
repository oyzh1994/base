package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class GetIndicatorStateReply implements XReply {
  public static final String PLUGIN_NAME = "xkb";
  private final byte deviceID;
  private final short sequenceNumber;
  private final int state;

  public static GetIndicatorStateReply readGetIndicatorStateReply(byte deviceID, short sequenceNumber, X11Input in) throws IOException {
    GetIndicatorStateReply.GetIndicatorStateReplyBuilder javaBuilder = GetIndicatorStateReply.builder();
    int length = in.readCard32();
    int state = in.readCard32();
    byte[] pad5 = in.readPad(20);
    javaBuilder.deviceID(deviceID);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.state(state);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(deviceID);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(state);
    out.writePad(20);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetIndicatorStateReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte deviceID;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int state;

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    GetIndicatorStateReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetIndicatorStateReply.GetIndicatorStateReplyBuilder deviceID(final byte deviceID) {
      this.deviceID = deviceID;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetIndicatorStateReply.GetIndicatorStateReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetIndicatorStateReply.GetIndicatorStateReplyBuilder state(final int state) {
      this.state = state;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetIndicatorStateReply build() {
      return new GetIndicatorStateReply(this.deviceID, this.sequenceNumber, this.state);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetIndicatorStateReply.GetIndicatorStateReplyBuilder(deviceID=" + this.deviceID + ", sequenceNumber=" + this.sequenceNumber + ", state=" + this.state + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetIndicatorStateReply(final byte deviceID, final short sequenceNumber, final int state) {
    this.deviceID = deviceID;
    this.sequenceNumber = sequenceNumber;
    this.state = state;
  }

  @java.lang.SuppressWarnings("all")
  public static GetIndicatorStateReply.GetIndicatorStateReplyBuilder builder() {
    return new GetIndicatorStateReply.GetIndicatorStateReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getDeviceID() {
    return this.deviceID;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getState() {
    return this.state;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetIndicatorStateReply)) return false;
    final GetIndicatorStateReply other = (GetIndicatorStateReply) o;
    if (this.getDeviceID() != other.getDeviceID()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getState() != other.getState()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDeviceID();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getState();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetIndicatorStateReply(deviceID=" + this.getDeviceID() + ", sequenceNumber=" + this.getSequenceNumber() + ", state=" + this.getState() + ")";
  }
}
