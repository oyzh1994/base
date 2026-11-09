package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.ShortList;

public final class SetCrtcGamma implements OneWayRequest {
  public static final String PLUGIN_NAME = "randr";
  public static final byte OPCODE = 24;
  private final int crtc;
  private final ShortList red;
  private final ShortList green;
  private final ShortList blue;

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
    ShortList red = in.readCard16(Short.toUnsignedInt(size));
    ShortList green = in.readCard16(Short.toUnsignedInt(size));
    ShortList blue = in.readCard16(Short.toUnsignedInt(size));
    javaBuilder.crtc(crtc);
    javaBuilder.red(red.toImmutable());
    javaBuilder.green(green.toImmutable());
    javaBuilder.blue(blue.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(crtc);
    short size = (short) blue.size();
    out.writeCard16(size);
    out.writePad(2);
    out.writeCard16(red);
    out.writeCard16(green);
    out.writeCard16(blue);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + 2 * red.size() + 2 * green.size() + 2 * blue.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetCrtcGammaBuilder {
    @java.lang.SuppressWarnings("all")
    private int crtc;
    @java.lang.SuppressWarnings("all")
    private ShortList red;
    @java.lang.SuppressWarnings("all")
    private ShortList green;
    @java.lang.SuppressWarnings("all")
    private ShortList blue;

    public int getSize() {
      return 12 + 2 * red.size() + 2 * green.size() + 2 * blue.size();
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
    public SetCrtcGamma.SetCrtcGammaBuilder red(final ShortList red) {
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
    public SetCrtcGamma.SetCrtcGammaBuilder green(final ShortList green) {
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
    public SetCrtcGamma.SetCrtcGammaBuilder blue(final ShortList blue) {
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
      return "SetCrtcGamma.SetCrtcGammaBuilder(crtc=" + this.crtc + ", red=" + this.red + ", green=" + this.green + ", blue=" + this.blue + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetCrtcGamma(final int crtc, final ShortList red, final ShortList green, final ShortList blue) {
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
  public ShortList getRed() {
    return this.red;
  }

  @java.lang.SuppressWarnings("all")
  public ShortList getGreen() {
    return this.green;
  }

  @java.lang.SuppressWarnings("all")
  public ShortList getBlue() {
    return this.blue;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetCrtcGamma)) return false;
    final SetCrtcGamma other = (SetCrtcGamma) o;
    if (this.getCrtc() != other.getCrtc()) return false;
    final java.lang.Object this$red = this.getRed();
    final java.lang.Object other$red = other.getRed();
    if (this$red == null ? other$red != null : !this$red.equals(other$red)) return false;
    final java.lang.Object this$green = this.getGreen();
    final java.lang.Object other$green = other.getGreen();
    if (this$green == null ? other$green != null : !this$green.equals(other$green)) return false;
    final java.lang.Object this$blue = this.getBlue();
    final java.lang.Object other$blue = other.getBlue();
    if (this$blue == null ? other$blue != null : !this$blue.equals(other$blue)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getCrtc();
    final java.lang.Object $red = this.getRed();
    result = result * PRIME + ($red == null ? 43 : $red.hashCode());
    final java.lang.Object $green = this.getGreen();
    result = result * PRIME + ($green == null ? 43 : $green.hashCode());
    final java.lang.Object $blue = this.getBlue();
    result = result * PRIME + ($blue == null ? 43 : $blue.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetCrtcGamma(crtc=" + this.getCrtc() + ", red=" + this.getRed() + ", green=" + this.getGreen() + ", blue=" + this.getBlue() + ")";
  }
}
