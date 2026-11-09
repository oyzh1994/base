package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class SetCrtcGamma implements OneWayRequest {
  public static final String PLUGIN_NAME = "randr";
  public static final byte OPCODE = 24;
  private final int crtc;
  private final short[] red;
  private final short[] green;
  private final short[] blue;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetCrtcGamma readSetCrtcGamma(X11Input in) throws IOException {
    SetCrtcGamma.SetCrtcGammaBuilder javaBuilder = SetCrtcGamma.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int crtc = in.readCard32();
    short size = in.readCard16();
    byte[] pad5 = in.readPad(2);
    short[] red = in.readCard16(Short.toUnsignedInt(size));
    short[] green = in.readCard16(Short.toUnsignedInt(size));
    short[] blue = in.readCard16(Short.toUnsignedInt(size));
    javaBuilder.crtc(crtc);
    javaBuilder.red(red);
    javaBuilder.green(green);
    javaBuilder.blue(blue);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(crtc);
    short size = (short) blue.length;
    out.writeCard16(size);
    out.writePad(2);
    out.writeCard16(red);
    out.writeCard16(green);
    out.writeCard16(blue);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + 2 * red.length + 2 * green.length + 2 * blue.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetCrtcGammaBuilder {
    @java.lang.SuppressWarnings("all")
    private int crtc;
    @java.lang.SuppressWarnings("all")
    private short[] red;
    @java.lang.SuppressWarnings("all")
    private short[] green;
    @java.lang.SuppressWarnings("all")
    private short[] blue;

    public int getSize() {
      return 12 + 2 * red.length + 2 * green.length + 2 * blue.length;
    }

    @java.lang.SuppressWarnings("all")
    SetCrtcGammaBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetCrtcGamma.SetCrtcGammaBuilder crtc(final int crtc) {
      this.crtc = crtc;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetCrtcGamma.SetCrtcGammaBuilder red(final short[] red) {
      if (red == null) {
        throw new java.lang.NullPointerException("red is marked non-null but is null");
      }
      this.red = red;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetCrtcGamma.SetCrtcGammaBuilder green(final short[] green) {
      if (green == null) {
        throw new java.lang.NullPointerException("green is marked non-null but is null");
      }
      this.green = green;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetCrtcGamma.SetCrtcGammaBuilder blue(final short[] blue) {
      if (blue == null) {
        throw new java.lang.NullPointerException("blue is marked non-null but is null");
      }
      this.blue = blue;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetCrtcGamma build() {
      return new SetCrtcGamma(this.crtc, this.red, this.green, this.blue);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetCrtcGamma.SetCrtcGammaBuilder(crtc=" + this.crtc + ", red=" + java.util.Arrays.toString(this.red) + ", green=" + java.util.Arrays.toString(this.green) + ", blue=" + java.util.Arrays.toString(this.blue) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetCrtcGamma(final int crtc, final short[] red, final short[] green, final short[] blue) {
    if (red == null) {
      throw new java.lang.NullPointerException("red is marked non-null but is null");
    }
    if (green == null) {
      throw new java.lang.NullPointerException("green is marked non-null but is null");
    }
    if (blue == null) {
      throw new java.lang.NullPointerException("blue is marked non-null but is null");
    }
    this.crtc = crtc;
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  @java.lang.SuppressWarnings("all")
  public static SetCrtcGamma.SetCrtcGammaBuilder builder() {
    return new SetCrtcGamma.SetCrtcGammaBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getCrtc() {
    return this.crtc;
  }

  @java.lang.SuppressWarnings("all")
  public short[] getRed() {
    return this.red;
  }

  @java.lang.SuppressWarnings("all")
  public short[] getGreen() {
    return this.green;
  }

  @java.lang.SuppressWarnings("all")
  public short[] getBlue() {
    return this.blue;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetCrtcGamma)) return false;
    final SetCrtcGamma other = (SetCrtcGamma) o;
    if (this.getCrtc() != other.getCrtc()) return false;
    if (!java.util.Arrays.equals(this.getRed(), other.getRed())) return false;
    if (!java.util.Arrays.equals(this.getGreen(), other.getGreen())) return false;
    if (!java.util.Arrays.equals(this.getBlue(), other.getBlue())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getCrtc();
    result = result * PRIME + java.util.Arrays.hashCode(this.getRed());
    result = result * PRIME + java.util.Arrays.hashCode(this.getGreen());
    result = result * PRIME + java.util.Arrays.hashCode(this.getBlue());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetCrtcGamma(crtc=" + this.getCrtc() + ", red=" + java.util.Arrays.toString(this.getRed()) + ", green=" + java.util.Arrays.toString(this.getGreen()) + ", blue=" + java.util.Arrays.toString(this.getBlue()) + ")";
  }
}
