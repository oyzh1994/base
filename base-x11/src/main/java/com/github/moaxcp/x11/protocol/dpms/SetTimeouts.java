package com.github.moaxcp.x11.protocol.dpms;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class SetTimeouts implements OneWayRequest {
  public static final String PLUGIN_NAME = "dpms";
  public static final byte OPCODE = 3;
  private final short standbyTimeout;
  private final short suspendTimeout;
  private final short offTimeout;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetTimeouts readSetTimeouts(X11Input in) throws IOException {
    SetTimeouts.SetTimeoutsBuilder javaBuilder = SetTimeouts.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short standbyTimeout = in.readCard16();
    short suspendTimeout = in.readCard16();
    short offTimeout = in.readCard16();
    javaBuilder.standbyTimeout(standbyTimeout);
    javaBuilder.suspendTimeout(suspendTimeout);
    javaBuilder.offTimeout(offTimeout);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(standbyTimeout);
    out.writeCard16(suspendTimeout);
    out.writeCard16(offTimeout);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 10;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetTimeoutsBuilder {
    @java.lang.SuppressWarnings("all")
    private short standbyTimeout;
    @java.lang.SuppressWarnings("all")
    private short suspendTimeout;
    @java.lang.SuppressWarnings("all")
    private short offTimeout;

    public int getSize() {
      return 10;
    }

    @java.lang.SuppressWarnings("all")
    SetTimeoutsBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetTimeouts.SetTimeoutsBuilder standbyTimeout(final short standbyTimeout) {
      this.standbyTimeout = standbyTimeout;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetTimeouts.SetTimeoutsBuilder suspendTimeout(final short suspendTimeout) {
      this.suspendTimeout = suspendTimeout;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetTimeouts.SetTimeoutsBuilder offTimeout(final short offTimeout) {
      this.offTimeout = offTimeout;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetTimeouts build() {
      return new SetTimeouts(this.standbyTimeout, this.suspendTimeout, this.offTimeout);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetTimeouts.SetTimeoutsBuilder(standbyTimeout=" + this.standbyTimeout + ", suspendTimeout=" + this.suspendTimeout + ", offTimeout=" + this.offTimeout + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetTimeouts(final short standbyTimeout, final short suspendTimeout, final short offTimeout) {
    this.standbyTimeout = standbyTimeout;
    this.suspendTimeout = suspendTimeout;
    this.offTimeout = offTimeout;
  }

  @java.lang.SuppressWarnings("all")
  public static SetTimeouts.SetTimeoutsBuilder builder() {
    return new SetTimeouts.SetTimeoutsBuilder();
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
    if (!(o instanceof SetTimeouts)) return false;
    final SetTimeouts other = (SetTimeouts) o;
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
    result = result * PRIME + this.getStandbyTimeout();
    result = result * PRIME + this.getSuspendTimeout();
    result = result * PRIME + this.getOffTimeout();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetTimeouts(standbyTimeout=" + this.getStandbyTimeout() + ", suspendTimeout=" + this.getSuspendTimeout() + ", offTimeout=" + this.getOffTimeout() + ")";
  }
}
