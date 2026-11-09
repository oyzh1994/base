package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class Color implements XStruct {
  public static final String PLUGIN_NAME = "render";
  private final short red;
  private final short green;
  private final short blue;
  private final short alpha;

  public static Color readColor(X11Input in) throws IOException {
    Color.ColorBuilder javaBuilder = Color.builder();
    short red = in.readCard16();
    short green = in.readCard16();
    short blue = in.readCard16();
    short alpha = in.readCard16();
    javaBuilder.red(red);
    javaBuilder.green(green);
    javaBuilder.blue(blue);
    javaBuilder.alpha(alpha);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard16(red);
    out.writeCard16(green);
    out.writeCard16(blue);
    out.writeCard16(alpha);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ColorBuilder {
    @java.lang.SuppressWarnings("all")
    private short red;
    @java.lang.SuppressWarnings("all")
    private short green;
    @java.lang.SuppressWarnings("all")
    private short blue;
    @java.lang.SuppressWarnings("all")
    private short alpha;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    ColorBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Color.ColorBuilder red(final short red) {
      this.red = red;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Color.ColorBuilder green(final short green) {
      this.green = green;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Color.ColorBuilder blue(final short blue) {
      this.blue = blue;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Color.ColorBuilder alpha(final short alpha) {
      this.alpha = alpha;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Color build() {
      return new Color(this.red, this.green, this.blue, this.alpha);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Color.ColorBuilder(red=" + this.red + ", green=" + this.green + ", blue=" + this.blue + ", alpha=" + this.alpha + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Color(final short red, final short green, final short blue, final short alpha) {
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.alpha = alpha;
  }

  @java.lang.SuppressWarnings("all")
  public static Color.ColorBuilder builder() {
    return new Color.ColorBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getRed() {
    return this.red;
  }

  @java.lang.SuppressWarnings("all")
  public short getGreen() {
    return this.green;
  }

  @java.lang.SuppressWarnings("all")
  public short getBlue() {
    return this.blue;
  }

  @java.lang.SuppressWarnings("all")
  public short getAlpha() {
    return this.alpha;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Color)) return false;
    final Color other = (Color) o;
    if (this.getRed() != other.getRed()) return false;
    if (this.getGreen() != other.getGreen()) return false;
    if (this.getBlue() != other.getBlue()) return false;
    if (this.getAlpha() != other.getAlpha()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getRed();
    result = result * PRIME + this.getGreen();
    result = result * PRIME + this.getBlue();
    result = result * PRIME + this.getAlpha();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Color(red=" + this.getRed() + ", green=" + this.getGreen() + ", blue=" + this.getBlue() + ", alpha=" + this.getAlpha() + ")";
  }
}
