package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class Rgb implements XStruct {
  public static final String PLUGIN_NAME = "xproto";
  private final short red;
  private final short green;
  private final short blue;

  public static Rgb readRgb(X11Input in) throws IOException {
    Rgb.RgbBuilder javaBuilder = Rgb.builder();
    short red = in.readCard16();
    short green = in.readCard16();
    short blue = in.readCard16();
    byte[] pad3 = in.readPad(2);
    javaBuilder.red(red);
    javaBuilder.green(green);
    javaBuilder.blue(blue);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard16(red);
    out.writeCard16(green);
    out.writeCard16(blue);
    out.writePad(2);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class RgbBuilder {
    @java.lang.SuppressWarnings("all")
    private short red;
    @java.lang.SuppressWarnings("all")
    private short green;
    @java.lang.SuppressWarnings("all")
    private short blue;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    RgbBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Rgb.RgbBuilder red(final short red) {
      this.red = red;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Rgb.RgbBuilder green(final short green) {
      this.green = green;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Rgb.RgbBuilder blue(final short blue) {
      this.blue = blue;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Rgb build() {
      return new Rgb(this.red, this.green, this.blue);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Rgb.RgbBuilder(red=" + this.red + ", green=" + this.green + ", blue=" + this.blue + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Rgb(final short red, final short green, final short blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  @java.lang.SuppressWarnings("all")
  public static Rgb.RgbBuilder builder() {
    return new Rgb.RgbBuilder();
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

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Rgb)) return false;
    final Rgb other = (Rgb) o;
    if (this.getRed() != other.getRed()) return false;
    if (this.getGreen() != other.getGreen()) return false;
    if (this.getBlue() != other.getBlue()) return false;
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
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Rgb(red=" + this.getRed() + ", green=" + this.getGreen() + ", blue=" + this.getBlue() + ")";
  }
}
