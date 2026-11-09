package com.github.moaxcp.x11.protocol.xproto;

import java.util.ArrayList;
import java.util.List;
import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;

public final class SetFontPath implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 51;
  private final List<Str> font;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetFontPath readSetFontPath(X11Input in) throws IOException {
    SetFontPath.SetFontPathBuilder javaBuilder = SetFontPath.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    short fontQty = in.readCard16();
    byte[] pad4 = in.readPad(2);
    List<Str> font = new ArrayList<>(Short.toUnsignedInt(fontQty));
    for (int i = 0; i < Short.toUnsignedInt(fontQty); i++) {
      font.add(Str.readStr(in));
    }
    javaBuilder.font(List.copyOf(font));
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    short fontQty = (short) font.size();
    out.writeCard16(fontQty);
    out.writePad(2);
    for (Str t : font) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 8 + XObject.sizeOf(font);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetFontPathBuilder {
    @java.lang.SuppressWarnings("all")
    private List<Str> font;

    public int getSize() {
      return 8 + XObject.sizeOf(font);
    }

    @java.lang.SuppressWarnings("all")
    SetFontPathBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetFontPath.SetFontPathBuilder font(final List<Str> font) {
      if (font == null) {
        throw new java.lang.NullPointerException("font is marked non-null but is null");
      }
      this.font = font;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetFontPath build() {
      return new SetFontPath(this.font);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetFontPath.SetFontPathBuilder(font=" + this.font + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetFontPath(final List<Str> font) {
    if (font == null) {
      throw new java.lang.NullPointerException("font is marked non-null but is null");
    }
    this.font = font;
  }

  @java.lang.SuppressWarnings("all")
  public static SetFontPath.SetFontPathBuilder builder() {
    return new SetFontPath.SetFontPathBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public List<Str> getFont() {
    return this.font;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetFontPath)) return false;
    final SetFontPath other = (SetFontPath) o;
    final java.lang.Object this$font = this.getFont();
    final java.lang.Object other$font = other.getFont();
    if (this$font == null ? other$font != null : !this$font.equals(other$font)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final java.lang.Object $font = this.getFont();
    result = result * PRIME + ($font == null ? 43 : $font.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetFontPath(font=" + this.getFont() + ")";
  }
}
