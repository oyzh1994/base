package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class Bell implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 104;
  private final byte percent;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Bell readBell(X11Input in) throws IOException {
    Bell.BellBuilder javaBuilder = Bell.builder();
    byte percent = in.readInt8();
    short length = in.readCard16();
    javaBuilder.percent(percent);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeInt8(percent);
    out.writeCard16((short) getLength());
  }

  @Override
  public int getSize() {
    return 4;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class BellBuilder {
    @java.lang.SuppressWarnings("all")
    private byte percent;

    public int getSize() {
      return 4;
    }

    @java.lang.SuppressWarnings("all")
    BellBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Bell.BellBuilder percent(final byte percent) {
      this.percent = percent;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Bell build() {
      return new Bell(this.percent);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Bell.BellBuilder(percent=" + this.percent + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Bell(final byte percent) {
    this.percent = percent;
  }

  @java.lang.SuppressWarnings("all")
  public static Bell.BellBuilder builder() {
    return new Bell.BellBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getPercent() {
    return this.percent;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Bell)) return false;
    final Bell other = (Bell) o;
    if (this.getPercent() != other.getPercent()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getPercent();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Bell(percent=" + this.getPercent() + ")";
  }
}
