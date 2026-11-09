package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class UngrabKeyboard implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 32;
  private final int time;

  public byte getOpCode() {
    return OPCODE;
  }

  public static UngrabKeyboard readUngrabKeyboard(X11Input in) throws IOException {
    UngrabKeyboard.UngrabKeyboardBuilder javaBuilder = UngrabKeyboard.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int time = in.readCard32();
    javaBuilder.time(time);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(time);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class UngrabKeyboardBuilder {
    @java.lang.SuppressWarnings("all")
    private int time;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    UngrabKeyboardBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public UngrabKeyboard.UngrabKeyboardBuilder time(final int time) {
      this.time = time;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public UngrabKeyboard build() {
      return new UngrabKeyboard(this.time);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "UngrabKeyboard.UngrabKeyboardBuilder(time=" + this.time + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  UngrabKeyboard(final int time) {
    this.time = time;
  }

  @java.lang.SuppressWarnings("all")
  public static UngrabKeyboard.UngrabKeyboardBuilder builder() {
    return new UngrabKeyboard.UngrabKeyboardBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getTime() {
    return this.time;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof UngrabKeyboard)) return false;
    final UngrabKeyboard other = (UngrabKeyboard) o;
    if (this.getTime() != other.getTime()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getTime();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "UngrabKeyboard(time=" + this.getTime() + ")";
  }
}
