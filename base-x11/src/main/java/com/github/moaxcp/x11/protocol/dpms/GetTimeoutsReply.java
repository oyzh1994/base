package com.github.moaxcp.x11.protocol.dpms;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class GetTimeoutsReply implements XReply {
  public static final String PLUGIN_NAME = "dpms";
  private final short sequenceNumber;
  private final short standbyTimeout;
  private final short suspendTimeout;
  private final short offTimeout;

  public static GetTimeoutsReply readGetTimeoutsReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    GetTimeoutsReply.GetTimeoutsReplyBuilder javaBuilder = GetTimeoutsReply.builder();
    int length = in.readCard32();
    short standbyTimeout = in.readCard16();
    short suspendTimeout = in.readCard16();
    short offTimeout = in.readCard16();
    byte[] pad7 = in.readPad(18);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.standbyTimeout(standbyTimeout);
    javaBuilder.suspendTimeout(suspendTimeout);
    javaBuilder.offTimeout(offTimeout);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard16(standbyTimeout);
    out.writeCard16(suspendTimeout);
    out.writeCard16(offTimeout);
    out.writePad(18);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetTimeoutsReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private short standbyTimeout;
    @java.lang.SuppressWarnings("all")
    private short suspendTimeout;
    @java.lang.SuppressWarnings("all")
    private short offTimeout;

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    GetTimeoutsReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetTimeoutsReply.GetTimeoutsReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetTimeoutsReply.GetTimeoutsReplyBuilder standbyTimeout(final short standbyTimeout) {
      this.standbyTimeout = standbyTimeout;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetTimeoutsReply.GetTimeoutsReplyBuilder suspendTimeout(final short suspendTimeout) {
      this.suspendTimeout = suspendTimeout;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetTimeoutsReply.GetTimeoutsReplyBuilder offTimeout(final short offTimeout) {
      this.offTimeout = offTimeout;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetTimeoutsReply build() {
      return new GetTimeoutsReply(this.sequenceNumber, this.standbyTimeout, this.suspendTimeout, this.offTimeout);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetTimeoutsReply.GetTimeoutsReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", standbyTimeout=" + this.standbyTimeout + ", suspendTimeout=" + this.suspendTimeout + ", offTimeout=" + this.offTimeout + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetTimeoutsReply(final short sequenceNumber, final short standbyTimeout, final short suspendTimeout, final short offTimeout) {
    this.sequenceNumber = sequenceNumber;
    this.standbyTimeout = standbyTimeout;
    this.suspendTimeout = suspendTimeout;
    this.offTimeout = offTimeout;
  }

  @java.lang.SuppressWarnings("all")
  public static GetTimeoutsReply.GetTimeoutsReplyBuilder builder() {
    return new GetTimeoutsReply.GetTimeoutsReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public short getStandbyTimeout() {
    return this.standbyTimeout;
  }

  @java.lang.SuppressWarnings("all")
  public short getSuspendTimeout() {
    return this.suspendTimeout;
  }

  @java.lang.SuppressWarnings("all")
  public short getOffTimeout() {
    return this.offTimeout;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetTimeoutsReply)) return false;
    final GetTimeoutsReply other = (GetTimeoutsReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getStandbyTimeout() != other.getStandbyTimeout()) return false;
    if (this.getSuspendTimeout() != other.getSuspendTimeout()) return false;
    if (this.getOffTimeout() != other.getOffTimeout()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getStandbyTimeout();
    result = result * PRIME + this.getSuspendTimeout();
    result = result * PRIME + this.getOffTimeout();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetTimeoutsReply(sequenceNumber=" + this.getSequenceNumber() + ", standbyTimeout=" + this.getStandbyTimeout() + ", suspendTimeout=" + this.getSuspendTimeout() + ", offTimeout=" + this.getOffTimeout() + ")";
  }
}
