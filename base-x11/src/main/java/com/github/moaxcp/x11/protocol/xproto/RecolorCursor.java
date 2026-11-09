package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class RecolorCursor implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 96;
  private final int cursor;
  private final short foreRed;
  private final short foreGreen;
  private final short foreBlue;
  private final short backRed;
  private final short backGreen;
  private final short backBlue;

  public byte getOpCode() {
    return OPCODE;
  }

  public static RecolorCursor readRecolorCursor(X11Input in) throws IOException {
    RecolorCursor.RecolorCursorBuilder javaBuilder = RecolorCursor.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int cursor = in.readCard32();
    short foreRed = in.readCard16();
    short foreGreen = in.readCard16();
    short foreBlue = in.readCard16();
    short backRed = in.readCard16();
    short backGreen = in.readCard16();
    short backBlue = in.readCard16();
    javaBuilder.cursor(cursor);
    javaBuilder.foreRed(foreRed);
    javaBuilder.foreGreen(foreGreen);
    javaBuilder.foreBlue(foreBlue);
    javaBuilder.backRed(backRed);
    javaBuilder.backGreen(backGreen);
    javaBuilder.backBlue(backBlue);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(cursor);
    out.writeCard16(foreRed);
    out.writeCard16(foreGreen);
    out.writeCard16(foreBlue);
    out.writeCard16(backRed);
    out.writeCard16(backGreen);
    out.writeCard16(backBlue);
  }

  @Override
  public int getSize() {
    return 20;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class RecolorCursorBuilder {
    @java.lang.SuppressWarnings("all")
    private int cursor;
    @java.lang.SuppressWarnings("all")
    private short foreRed;
    @java.lang.SuppressWarnings("all")
    private short foreGreen;
    @java.lang.SuppressWarnings("all")
    private short foreBlue;
    @java.lang.SuppressWarnings("all")
    private short backRed;
    @java.lang.SuppressWarnings("all")
    private short backGreen;
    @java.lang.SuppressWarnings("all")
    private short backBlue;

    public int getSize() {
      return 20;
    }

    @java.lang.SuppressWarnings("all")
    RecolorCursorBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RecolorCursor.RecolorCursorBuilder cursor(final int cursor) {
      this.cursor = cursor;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RecolorCursor.RecolorCursorBuilder foreRed(final short foreRed) {
      this.foreRed = foreRed;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RecolorCursor.RecolorCursorBuilder foreGreen(final short foreGreen) {
      this.foreGreen = foreGreen;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RecolorCursor.RecolorCursorBuilder foreBlue(final short foreBlue) {
      this.foreBlue = foreBlue;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RecolorCursor.RecolorCursorBuilder backRed(final short backRed) {
      this.backRed = backRed;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RecolorCursor.RecolorCursorBuilder backGreen(final short backGreen) {
      this.backGreen = backGreen;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RecolorCursor.RecolorCursorBuilder backBlue(final short backBlue) {
      this.backBlue = backBlue;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public RecolorCursor build() {
      return new RecolorCursor(this.cursor, this.foreRed, this.foreGreen, this.foreBlue, this.backRed, this.backGreen, this.backBlue);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "RecolorCursor.RecolorCursorBuilder(cursor=" + this.cursor + ", foreRed=" + this.foreRed + ", foreGreen=" + this.foreGreen + ", foreBlue=" + this.foreBlue + ", backRed=" + this.backRed + ", backGreen=" + this.backGreen + ", backBlue=" + this.backBlue + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  RecolorCursor(final int cursor, final short foreRed, final short foreGreen, final short foreBlue, final short backRed, final short backGreen, final short backBlue) {
    this.cursor = cursor;
    this.foreRed = foreRed;
    this.foreGreen = foreGreen;
    this.foreBlue = foreBlue;
    this.backRed = backRed;
    this.backGreen = backGreen;
    this.backBlue = backBlue;
  }

  @java.lang.SuppressWarnings("all")
  public static RecolorCursor.RecolorCursorBuilder builder() {
    return new RecolorCursor.RecolorCursorBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getCursor() {
    return this.cursor;
  }

  @java.lang.SuppressWarnings("all")
  public short getForeRed() {
    return this.foreRed;
  }

  @java.lang.SuppressWarnings("all")
  public short getForeGreen() {
    return this.foreGreen;
  }

  @java.lang.SuppressWarnings("all")
  public short getForeBlue() {
    return this.foreBlue;
  }

  @java.lang.SuppressWarnings("all")
  public short getBackRed() {
    return this.backRed;
  }

  @java.lang.SuppressWarnings("all")
  public short getBackGreen() {
    return this.backGreen;
  }

  @java.lang.SuppressWarnings("all")
  public short getBackBlue() {
    return this.backBlue;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof RecolorCursor)) return false;
    final RecolorCursor other = (RecolorCursor) o;
    if (this.getCursor() != other.getCursor()) return false;
    if (this.getForeRed() != other.getForeRed()) return false;
    if (this.getForeGreen() != other.getForeGreen()) return false;
    if (this.getForeBlue() != other.getForeBlue()) return false;
    if (this.getBackRed() != other.getBackRed()) return false;
    if (this.getBackGreen() != other.getBackGreen()) return false;
    if (this.getBackBlue() != other.getBackBlue()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getCursor();
    result = result * PRIME + this.getForeRed();
    result = result * PRIME + this.getForeGreen();
    result = result * PRIME + this.getForeBlue();
    result = result * PRIME + this.getBackRed();
    result = result * PRIME + this.getBackGreen();
    result = result * PRIME + this.getBackBlue();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "RecolorCursor(cursor=" + this.getCursor() + ", foreRed=" + this.getForeRed() + ", foreGreen=" + this.getForeGreen() + ", foreBlue=" + this.getForeBlue() + ", backRed=" + this.getBackRed() + ", backGreen=" + this.getBackGreen() + ", backBlue=" + this.getBackBlue() + ")";
  }
}
