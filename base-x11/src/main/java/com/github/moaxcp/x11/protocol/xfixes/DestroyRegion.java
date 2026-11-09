package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class DestroyRegion implements OneWayRequest {
  public static final String PLUGIN_NAME = "xfixes";
  public static final byte OPCODE = 10;
  private final int region;

  public byte getOpCode() {
    return OPCODE;
  }

  public static DestroyRegion readDestroyRegion(X11Input in) throws IOException {
    DestroyRegion.DestroyRegionBuilder javaBuilder = DestroyRegion.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int region = in.readCard32();
    javaBuilder.region(region);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(region);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class DestroyRegionBuilder {
    @java.lang.SuppressWarnings("all")
    private int region;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    DestroyRegionBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public DestroyRegion.DestroyRegionBuilder region(final int region) {
      this.region = region;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public DestroyRegion build() {
      return new DestroyRegion(this.region);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "DestroyRegion.DestroyRegionBuilder(region=" + this.region + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  DestroyRegion(final int region) {
    this.region = region;
  }

  @java.lang.SuppressWarnings("all")
  public static DestroyRegion.DestroyRegionBuilder builder() {
    return new DestroyRegion.DestroyRegionBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getRegion() {
    return this.region;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof DestroyRegion)) return false;
    final DestroyRegion other = (DestroyRegion) o;
    if (this.getRegion() != other.getRegion()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getRegion();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "DestroyRegion(region=" + this.getRegion() + ")";
  }
}
