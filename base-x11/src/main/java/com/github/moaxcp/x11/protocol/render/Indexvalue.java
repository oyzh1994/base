package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class Indexvalue implements XStruct {
  public static final String PLUGIN_NAME = "render";
  private final int pixel;
  private final short red;
  private final short green;
  private final short blue;
  private final short alpha;

  public static Indexvalue readIndexvalue(X11Input in) throws IOException {
    Indexvalue.IndexvalueBuilder javaBuilder = Indexvalue.builder();
    int pixel = in.readCard32();
    short red = in.readCard16();
    short green = in.readCard16();
    short blue = in.readCard16();
    short alpha = in.readCard16();
    javaBuilder.pixel(pixel);
    javaBuilder.red(red);
    javaBuilder.green(green);
    javaBuilder.blue(blue);
    javaBuilder.alpha(alpha);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(pixel);
    out.writeCard16(red);
    out.writeCard16(green);
    out.writeCard16(blue);
    out.writeCard16(alpha);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class IndexvalueBuilder {
    @java.lang.SuppressWarnings("all")
    private int pixel;
    @java.lang.SuppressWarnings("all")
    private short red;
    @java.lang.SuppressWarnings("all")
    private short green;
    @java.lang.SuppressWarnings("all")
    private short blue;
    @java.lang.SuppressWarnings("all")
    private short alpha;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    IndexvalueBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Indexvalue.IndexvalueBuilder pixel(final int pixel) {
      this.pixel = pixel;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Indexvalue.IndexvalueBuilder red(final short red) {
      this.red = red;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Indexvalue.IndexvalueBuilder green(final short green) {
      this.green = green;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Indexvalue.IndexvalueBuilder blue(final short blue) {
      this.blue = blue;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Indexvalue.IndexvalueBuilder alpha(final short alpha) {
      this.alpha = alpha;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Indexvalue build() {
      return new Indexvalue(this.pixel, this.red, this.green, this.blue, this.alpha);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Indexvalue.IndexvalueBuilder(pixel=" + this.pixel + ", red=" + this.red + ", green=" + this.green + ", blue=" + this.blue + ", alpha=" + this.alpha + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Indexvalue(final int pixel, final short red, final short green, final short blue, final short alpha) {
    this.pixel = pixel;
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.alpha = alpha;
  }

  @java.lang.SuppressWarnings("all")
  public static Indexvalue.IndexvalueBuilder builder() {
    return new Indexvalue.IndexvalueBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getPixel() {
    return this.pixel;
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
    if (!(o instanceof Indexvalue)) return false;
    final Indexvalue other = (Indexvalue) o;
    if (this.getPixel() != other.getPixel()) return false;
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
    result = result * PRIME + this.getPixel();
    result = result * PRIME + this.getRed();
    result = result * PRIME + this.getGreen();
    result = result * PRIME + this.getBlue();
    result = result * PRIME + this.getAlpha();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Indexvalue(pixel=" + this.getPixel() + ", red=" + this.getRed() + ", green=" + this.getGreen() + ", blue=" + this.getBlue() + ", alpha=" + this.getAlpha() + ")";
  }
}
