package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class Visualtype implements XStruct {
  public static final String PLUGIN_NAME = "xproto";
  private final int visualId;
  private final byte clazz;
  private final byte bitsPerRgbValue;
  private final short colormapEntries;
  private final int redMask;
  private final int greenMask;
  private final int blueMask;

  public static Visualtype readVisualtype(X11Input in) throws IOException {
    Visualtype.VisualtypeBuilder javaBuilder = Visualtype.builder();
    int visualId = in.readCard32();
    byte clazz = in.readCard8();
    byte bitsPerRgbValue = in.readCard8();
    short colormapEntries = in.readCard16();
    int redMask = in.readCard32();
    int greenMask = in.readCard32();
    int blueMask = in.readCard32();
    byte[] pad7 = in.readPad(4);
    javaBuilder.visualId(visualId);
    javaBuilder.clazz(clazz);
    javaBuilder.bitsPerRgbValue(bitsPerRgbValue);
    javaBuilder.colormapEntries(colormapEntries);
    javaBuilder.redMask(redMask);
    javaBuilder.greenMask(greenMask);
    javaBuilder.blueMask(blueMask);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(visualId);
    out.writeCard8(clazz);
    out.writeCard8(bitsPerRgbValue);
    out.writeCard16(colormapEntries);
    out.writeCard32(redMask);
    out.writeCard32(greenMask);
    out.writeCard32(blueMask);
    out.writePad(4);
  }

  @Override
  public int getSize() {
    return 24;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class VisualtypeBuilder {
    @java.lang.SuppressWarnings("all")
    private int visualId;
    @java.lang.SuppressWarnings("all")
    private byte clazz;
    @java.lang.SuppressWarnings("all")
    private byte bitsPerRgbValue;
    @java.lang.SuppressWarnings("all")
    private short colormapEntries;
    @java.lang.SuppressWarnings("all")
    private int redMask;
    @java.lang.SuppressWarnings("all")
    private int greenMask;
    @java.lang.SuppressWarnings("all")
    private int blueMask;

    public Visualtype.VisualtypeBuilder clazz(VisualClass clazz) {
      this.clazz = (byte) clazz.getValue();
      return this;
    }

    public Visualtype.VisualtypeBuilder clazz(byte clazz) {
      this.clazz = clazz;
      return this;
    }

    public int getSize() {
      return 24;
    }

    @java.lang.SuppressWarnings("all")
    VisualtypeBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Visualtype.VisualtypeBuilder visualId(final int visualId) {
      this.visualId = visualId;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Visualtype.VisualtypeBuilder bitsPerRgbValue(final byte bitsPerRgbValue) {
      this.bitsPerRgbValue = bitsPerRgbValue;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Visualtype.VisualtypeBuilder colormapEntries(final short colormapEntries) {
      this.colormapEntries = colormapEntries;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Visualtype.VisualtypeBuilder redMask(final int redMask) {
      this.redMask = redMask;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Visualtype.VisualtypeBuilder greenMask(final int greenMask) {
      this.greenMask = greenMask;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Visualtype.VisualtypeBuilder blueMask(final int blueMask) {
      this.blueMask = blueMask;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Visualtype build() {
      return new Visualtype(this.visualId, this.clazz, this.bitsPerRgbValue, this.colormapEntries, this.redMask, this.greenMask, this.blueMask);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Visualtype.VisualtypeBuilder(visualId=" + this.visualId + ", clazz=" + this.clazz + ", bitsPerRgbValue=" + this.bitsPerRgbValue + ", colormapEntries=" + this.colormapEntries + ", redMask=" + this.redMask + ", greenMask=" + this.greenMask + ", blueMask=" + this.blueMask + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Visualtype(final int visualId, final byte clazz, final byte bitsPerRgbValue, final short colormapEntries, final int redMask, final int greenMask, final int blueMask) {
    this.visualId = visualId;
    this.clazz = clazz;
    this.bitsPerRgbValue = bitsPerRgbValue;
    this.colormapEntries = colormapEntries;
    this.redMask = redMask;
    this.greenMask = greenMask;
    this.blueMask = blueMask;
  }

  @java.lang.SuppressWarnings("all")
  public static Visualtype.VisualtypeBuilder builder() {
    return new Visualtype.VisualtypeBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getVisualId() {
    return this.visualId;
  }

  @java.lang.SuppressWarnings("all")
  public byte getClazz() {
    return this.clazz;
  }

  @java.lang.SuppressWarnings("all")
  public byte getBitsPerRgbValue() {
    return this.bitsPerRgbValue;
  }

  @java.lang.SuppressWarnings("all")
  public short getColormapEntries() {
    return this.colormapEntries;
  }

  @java.lang.SuppressWarnings("all")
  public int getRedMask() {
    return this.redMask;
  }

  @java.lang.SuppressWarnings("all")
  public int getGreenMask() {
    return this.greenMask;
  }

  @java.lang.SuppressWarnings("all")
  public int getBlueMask() {
    return this.blueMask;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Visualtype)) return false;
    final Visualtype other = (Visualtype) o;
    if (this.getVisualId() != other.getVisualId()) return false;
    if (this.getClazz() != other.getClazz()) return false;
    if (this.getBitsPerRgbValue() != other.getBitsPerRgbValue()) return false;
    if (this.getColormapEntries() != other.getColormapEntries()) return false;
    if (this.getRedMask() != other.getRedMask()) return false;
    if (this.getGreenMask() != other.getGreenMask()) return false;
    if (this.getBlueMask() != other.getBlueMask()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getVisualId();
    result = result * PRIME + this.getClazz();
    result = result * PRIME + this.getBitsPerRgbValue();
    result = result * PRIME + this.getColormapEntries();
    result = result * PRIME + this.getRedMask();
    result = result * PRIME + this.getGreenMask();
    result = result * PRIME + this.getBlueMask();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Visualtype(visualId=" + this.getVisualId() + ", clazz=" + this.getClazz() + ", bitsPerRgbValue=" + this.getBitsPerRgbValue() + ", colormapEntries=" + this.getColormapEntries() + ", redMask=" + this.getRedMask() + ", greenMask=" + this.getGreenMask() + ", blueMask=" + this.getBlueMask() + ")";
  }
}
