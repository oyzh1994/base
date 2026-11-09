package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class TranslateRegion implements OneWayRequest {
  public static final String PLUGIN_NAME = "xfixes";
  public static final byte OPCODE = 17;
  private final int region;
  private final short dx;
  private final short dy;

  public byte getOpCode() {
    return OPCODE;
  }

  public static TranslateRegion readTranslateRegion(X11Input in) throws IOException {
    TranslateRegion.TranslateRegionBuilder javaBuilder = TranslateRegion.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int region = in.readCard32();
    short dx = in.readInt16();
    short dy = in.readInt16();
    javaBuilder.region(region);
    javaBuilder.dx(dx);
    javaBuilder.dy(dy);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(region);
    out.writeInt16(dx);
    out.writeInt16(dy);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class TranslateRegionBuilder {
    @java.lang.SuppressWarnings("all")
    private int region;
    @java.lang.SuppressWarnings("all")
    private short dx;
    @java.lang.SuppressWarnings("all")
    private short dy;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    TranslateRegionBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public TranslateRegion.TranslateRegionBuilder region(final int region) {
      this.region = region;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public TranslateRegion.TranslateRegionBuilder dx(final short dx) {
      this.dx = dx;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public TranslateRegion.TranslateRegionBuilder dy(final short dy) {
      this.dy = dy;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public TranslateRegion build() {
      return new TranslateRegion(this.region, this.dx, this.dy);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "TranslateRegion.TranslateRegionBuilder(region=" + this.region + ", dx=" + this.dx + ", dy=" + this.dy + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  TranslateRegion(final int region, final short dx, final short dy) {
    this.region = region;
    this.dx = dx;
    this.dy = dy;
  }

  @java.lang.SuppressWarnings("all")
  public static TranslateRegion.TranslateRegionBuilder builder() {
    return new TranslateRegion.TranslateRegionBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getRegion() {
    return this.region;
  }

  @java.lang.SuppressWarnings("all")
  public short getDx() {
    return this.dx;
  }

  @java.lang.SuppressWarnings("all")
  public short getDy() {
    return this.dy;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof TranslateRegion)) return false;
    final TranslateRegion other = (TranslateRegion) o;
    if (this.getRegion() != other.getRegion()) return false;
    if (this.getDx() != other.getDx()) return false;
    if (this.getDy() != other.getDy()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getRegion();
    result = result * PRIME + this.getDx();
    result = result * PRIME + this.getDy();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "TranslateRegion(region=" + this.getRegion() + ", dx=" + this.getDx() + ", dy=" + this.getDy() + ")";
  }
}
