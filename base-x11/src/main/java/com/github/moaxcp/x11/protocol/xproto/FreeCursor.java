package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class FreeCursor implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 95;
  private final int cursor;

  public byte getOpCode() {
    return OPCODE;
  }

  public static FreeCursor readFreeCursor(X11Input in) throws IOException {
    FreeCursor.FreeCursorBuilder javaBuilder = FreeCursor.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int cursor = in.readCard32();
    javaBuilder.cursor(cursor);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(cursor);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class FreeCursorBuilder {
    @java.lang.SuppressWarnings("all")
    private int cursor;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    FreeCursorBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public FreeCursor.FreeCursorBuilder cursor(final int cursor) {
      this.cursor = cursor;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public FreeCursor build() {
      return new FreeCursor(this.cursor);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "FreeCursor.FreeCursorBuilder(cursor=" + this.cursor + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  FreeCursor(final int cursor) {
    this.cursor = cursor;
  }

  @java.lang.SuppressWarnings("all")
  public static FreeCursor.FreeCursorBuilder builder() {
    return new FreeCursor.FreeCursorBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getCursor() {
    return this.cursor;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof FreeCursor)) return false;
    final FreeCursor other = (FreeCursor) o;
    if (this.getCursor() != other.getCursor()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getCursor();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "FreeCursor(cursor=" + this.getCursor() + ")";
  }
}
