package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class CloseFont implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 46;
  private final int font;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CloseFont readCloseFont(X11Input in) throws IOException {
    CloseFont.CloseFontBuilder javaBuilder = CloseFont.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int font = in.readCard32();
    javaBuilder.font(font);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(font);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CloseFontBuilder {
    @java.lang.SuppressWarnings("all")
    private int font;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    CloseFontBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CloseFont.CloseFontBuilder font(final int font) {
      this.font = font;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CloseFont build() {
      return new CloseFont(this.font);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CloseFont.CloseFontBuilder(font=" + this.font + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CloseFont(final int font) {
    this.font = font;
  }

  @java.lang.SuppressWarnings("all")
  public static CloseFont.CloseFontBuilder builder() {
    return new CloseFont.CloseFontBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getFont() {
    return this.font;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CloseFont)) return false;
    final CloseFont other = (CloseFont) o;
    if (this.getFont() != other.getFont()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFont();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CloseFont(font=" + this.getFont() + ")";
  }
}
