package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class Glyphinfo implements XStruct {
  public static final String PLUGIN_NAME = "render";
  private final short width;
  private final short height;
  private final short x;
  private final short y;
  private final short xOff;
  private final short yOff;

  public static Glyphinfo readGlyphinfo(X11Input in) throws IOException {
    Glyphinfo.GlyphinfoBuilder javaBuilder = Glyphinfo.builder();
    short width = in.readCard16();
    short height = in.readCard16();
    short x = in.readInt16();
    short y = in.readInt16();
    short xOff = in.readInt16();
    short yOff = in.readInt16();
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.x(x);
    javaBuilder.y(y);
    javaBuilder.xOff(xOff);
    javaBuilder.yOff(yOff);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeInt16(x);
    out.writeInt16(y);
    out.writeInt16(xOff);
    out.writeInt16(yOff);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GlyphinfoBuilder {
    @java.lang.SuppressWarnings("all")
    private short width;
    @java.lang.SuppressWarnings("all")
    private short height;
    @java.lang.SuppressWarnings("all")
    private short x;
    @java.lang.SuppressWarnings("all")
    private short y;
    @java.lang.SuppressWarnings("all")
    private short xOff;
    @java.lang.SuppressWarnings("all")
    private short yOff;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    GlyphinfoBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Glyphinfo.GlyphinfoBuilder width(final short width) {
      this.width = width;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Glyphinfo.GlyphinfoBuilder height(final short height) {
      this.height = height;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Glyphinfo.GlyphinfoBuilder x(final short x) {
      this.x = x;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Glyphinfo.GlyphinfoBuilder y(final short y) {
      this.y = y;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Glyphinfo.GlyphinfoBuilder xOff(final short xOff) {
      this.xOff = xOff;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Glyphinfo.GlyphinfoBuilder yOff(final short yOff) {
      this.yOff = yOff;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Glyphinfo build() {
      return new Glyphinfo(this.width, this.height, this.x, this.y, this.xOff, this.yOff);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Glyphinfo.GlyphinfoBuilder(width=" + this.width + ", height=" + this.height + ", x=" + this.x + ", y=" + this.y + ", xOff=" + this.xOff + ", yOff=" + this.yOff + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Glyphinfo(final short width, final short height, final short x, final short y, final short xOff, final short yOff) {
    this.width = width;
    this.height = height;
    this.x = x;
    this.y = y;
    this.xOff = xOff;
    this.yOff = yOff;
  }

  @java.lang.SuppressWarnings("all")
  public static Glyphinfo.GlyphinfoBuilder builder() {
    return new Glyphinfo.GlyphinfoBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getWidth() {
    return this.width;
  }

  @java.lang.SuppressWarnings("all")
  public short getHeight() {
    return this.height;
  }

  @java.lang.SuppressWarnings("all")
  public short getX() {
    return this.x;
  }

  @java.lang.SuppressWarnings("all")
  public short getY() {
    return this.y;
  }

  @java.lang.SuppressWarnings("all")
  public short getXOff() {
    return this.xOff;
  }

  @java.lang.SuppressWarnings("all")
  public short getYOff() {
    return this.yOff;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Glyphinfo)) return false;
    final Glyphinfo other = (Glyphinfo) o;
    if (this.getWidth() != other.getWidth()) return false;
    if (this.getHeight() != other.getHeight()) return false;
    if (this.getX() != other.getX()) return false;
    if (this.getY() != other.getY()) return false;
    if (this.getXOff() != other.getXOff()) return false;
    if (this.getYOff() != other.getYOff()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getWidth();
    result = result * PRIME + this.getHeight();
    result = result * PRIME + this.getX();
    result = result * PRIME + this.getY();
    result = result * PRIME + this.getXOff();
    result = result * PRIME + this.getYOff();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Glyphinfo(width=" + this.getWidth() + ", height=" + this.getHeight() + ", x=" + this.getX() + ", y=" + this.getY() + ", xOff=" + this.getXOff() + ", yOff=" + this.getYOff() + ")";
  }
}
