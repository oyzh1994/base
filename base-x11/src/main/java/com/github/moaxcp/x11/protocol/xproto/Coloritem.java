package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class Coloritem implements XStruct {
  public static final String PLUGIN_NAME = "xproto";
  private final int pixel;
  private final short red;
  private final short green;
  private final short blue;
  private final byte flags;

  public static Coloritem readColoritem(X11Input in) throws IOException {
    Coloritem.ColoritemBuilder javaBuilder = Coloritem.builder();
    int pixel = in.readCard32();
    short red = in.readCard16();
    short green = in.readCard16();
    short blue = in.readCard16();
    byte flags = in.readByte();
    byte[] pad5 = in.readPad(1);
    javaBuilder.pixel(pixel);
    javaBuilder.red(red);
    javaBuilder.green(green);
    javaBuilder.blue(blue);
    javaBuilder.flags(flags);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(pixel);
    out.writeCard16(red);
    out.writeCard16(green);
    out.writeCard16(blue);
    out.writeByte(flags);
    out.writePad(1);
  }

  public boolean isFlagsEnabled(ColorFlag... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (ColorFlag m : maskEnums) {
      if (!m.isEnabled(flags)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ColoritemBuilder {
    @java.lang.SuppressWarnings("all")
    private int pixel;
    @java.lang.SuppressWarnings("all")
    private short red;
    @java.lang.SuppressWarnings("all")
    private short green;
    @java.lang.SuppressWarnings("all")
    private short blue;
    @java.lang.SuppressWarnings("all")
    private byte flags;

    public boolean isFlagsEnabled(ColorFlag... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (ColorFlag m : maskEnums) {
        if (!m.isEnabled(flags)) {
          return false;
        }
      }
      return true;
    }

    public Coloritem.ColoritemBuilder flagsEnable(ColorFlag... maskEnums) {
      for (ColorFlag m : maskEnums) {
        flags((byte) m.enableFor(flags));
      }
      return this;
    }

    public Coloritem.ColoritemBuilder flagsDisable(ColorFlag... maskEnums) {
      for (ColorFlag m : maskEnums) {
        flags((byte) m.disableFor(flags));
      }
      return this;
    }

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    ColoritemBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Coloritem.ColoritemBuilder pixel(final int pixel) {
      this.pixel = pixel;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Coloritem.ColoritemBuilder red(final short red) {
      this.red = red;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Coloritem.ColoritemBuilder green(final short green) {
      this.green = green;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Coloritem.ColoritemBuilder blue(final short blue) {
      this.blue = blue;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Coloritem.ColoritemBuilder flags(final byte flags) {
      this.flags = flags;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Coloritem build() {
      return new Coloritem(this.pixel, this.red, this.green, this.blue, this.flags);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Coloritem.ColoritemBuilder(pixel=" + this.pixel + ", red=" + this.red + ", green=" + this.green + ", blue=" + this.blue + ", flags=" + this.flags + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Coloritem(final int pixel, final short red, final short green, final short blue, final byte flags) {
    this.pixel = pixel;
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.flags = flags;
  }

  @java.lang.SuppressWarnings("all")
  public static Coloritem.ColoritemBuilder builder() {
    return new Coloritem.ColoritemBuilder();
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
  public byte getFlags() {
    return this.flags;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Coloritem)) return false;
    final Coloritem other = (Coloritem) o;
    if (this.getPixel() != other.getPixel()) return false;
    if (this.getRed() != other.getRed()) return false;
    if (this.getGreen() != other.getGreen()) return false;
    if (this.getBlue() != other.getBlue()) return false;
    if (this.getFlags() != other.getFlags()) return false;
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
    result = result * PRIME + this.getFlags();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Coloritem(pixel=" + this.getPixel() + ", red=" + this.getRed() + ", green=" + this.getGreen() + ", blue=" + this.getBlue() + ", flags=" + this.getFlags() + ")";
  }
}
