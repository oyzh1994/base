package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class CreateGlyphCursor implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 94;
  private final int cid;
  private final int sourceFont;
  private final int maskFont;
  private final short sourceChar;
  private final short maskChar;
  private final short foreRed;
  private final short foreGreen;
  private final short foreBlue;
  private final short backRed;
  private final short backGreen;
  private final short backBlue;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateGlyphCursor readCreateGlyphCursor(X11Input in) throws IOException {
    CreateGlyphCursor.CreateGlyphCursorBuilder javaBuilder = CreateGlyphCursor.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int cid = in.readCard32();
    int sourceFont = in.readCard32();
    int maskFont = in.readCard32();
    short sourceChar = in.readCard16();
    short maskChar = in.readCard16();
    short foreRed = in.readCard16();
    short foreGreen = in.readCard16();
    short foreBlue = in.readCard16();
    short backRed = in.readCard16();
    short backGreen = in.readCard16();
    short backBlue = in.readCard16();
    javaBuilder.cid(cid);
    javaBuilder.sourceFont(sourceFont);
    javaBuilder.maskFont(maskFont);
    javaBuilder.sourceChar(sourceChar);
    javaBuilder.maskChar(maskChar);
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
    out.writeCard32(cid);
    out.writeCard32(sourceFont);
    out.writeCard32(maskFont);
    out.writeCard16(sourceChar);
    out.writeCard16(maskChar);
    out.writeCard16(foreRed);
    out.writeCard16(foreGreen);
    out.writeCard16(foreBlue);
    out.writeCard16(backRed);
    out.writeCard16(backGreen);
    out.writeCard16(backBlue);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CreateGlyphCursorBuilder {
    @java.lang.SuppressWarnings("all")
    private int cid;
    @java.lang.SuppressWarnings("all")
    private int sourceFont;
    @java.lang.SuppressWarnings("all")
    private int maskFont;
    @java.lang.SuppressWarnings("all")
    private short sourceChar;
    @java.lang.SuppressWarnings("all")
    private short maskChar;
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
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    CreateGlyphCursorBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateGlyphCursor.CreateGlyphCursorBuilder cid(final int cid) {
      this.cid = cid;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateGlyphCursor.CreateGlyphCursorBuilder sourceFont(final int sourceFont) {
      this.sourceFont = sourceFont;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateGlyphCursor.CreateGlyphCursorBuilder maskFont(final int maskFont) {
      this.maskFont = maskFont;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateGlyphCursor.CreateGlyphCursorBuilder sourceChar(final short sourceChar) {
      this.sourceChar = sourceChar;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateGlyphCursor.CreateGlyphCursorBuilder maskChar(final short maskChar) {
      this.maskChar = maskChar;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateGlyphCursor.CreateGlyphCursorBuilder foreRed(final short foreRed) {
      this.foreRed = foreRed;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateGlyphCursor.CreateGlyphCursorBuilder foreGreen(final short foreGreen) {
      this.foreGreen = foreGreen;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateGlyphCursor.CreateGlyphCursorBuilder foreBlue(final short foreBlue) {
      this.foreBlue = foreBlue;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateGlyphCursor.CreateGlyphCursorBuilder backRed(final short backRed) {
      this.backRed = backRed;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateGlyphCursor.CreateGlyphCursorBuilder backGreen(final short backGreen) {
      this.backGreen = backGreen;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateGlyphCursor.CreateGlyphCursorBuilder backBlue(final short backBlue) {
      this.backBlue = backBlue;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CreateGlyphCursor build() {
      return new CreateGlyphCursor(this.cid, this.sourceFont, this.maskFont, this.sourceChar, this.maskChar, this.foreRed, this.foreGreen, this.foreBlue, this.backRed, this.backGreen, this.backBlue);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CreateGlyphCursor.CreateGlyphCursorBuilder(cid=" + this.cid + ", sourceFont=" + this.sourceFont + ", maskFont=" + this.maskFont + ", sourceChar=" + this.sourceChar + ", maskChar=" + this.maskChar + ", foreRed=" + this.foreRed + ", foreGreen=" + this.foreGreen + ", foreBlue=" + this.foreBlue + ", backRed=" + this.backRed + ", backGreen=" + this.backGreen + ", backBlue=" + this.backBlue + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CreateGlyphCursor(final int cid, final int sourceFont, final int maskFont, final short sourceChar, final short maskChar, final short foreRed, final short foreGreen, final short foreBlue, final short backRed, final short backGreen, final short backBlue) {
    this.cid = cid;
    this.sourceFont = sourceFont;
    this.maskFont = maskFont;
    this.sourceChar = sourceChar;
    this.maskChar = maskChar;
    this.foreRed = foreRed;
    this.foreGreen = foreGreen;
    this.foreBlue = foreBlue;
    this.backRed = backRed;
    this.backGreen = backGreen;
    this.backBlue = backBlue;
  }

  @java.lang.SuppressWarnings("all")
  public static CreateGlyphCursor.CreateGlyphCursorBuilder builder() {
    return new CreateGlyphCursor.CreateGlyphCursorBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getCid() {
    return this.cid;
  }

  @java.lang.SuppressWarnings("all")
  public int getSourceFont() {
    return this.sourceFont;
  }

  @java.lang.SuppressWarnings("all")
  public int getMaskFont() {
    return this.maskFont;
  }

  @java.lang.SuppressWarnings("all")
  public short getSourceChar() {
    return this.sourceChar;
  }

  @java.lang.SuppressWarnings("all")
  public short getMaskChar() {
    return this.maskChar;
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
    if (!(o instanceof CreateGlyphCursor)) return false;
    final CreateGlyphCursor other = (CreateGlyphCursor) o;
    if (this.getCid() != other.getCid()) return false;
    if (this.getSourceFont() != other.getSourceFont()) return false;
    if (this.getMaskFont() != other.getMaskFont()) return false;
    if (this.getSourceChar() != other.getSourceChar()) return false;
    if (this.getMaskChar() != other.getMaskChar()) return false;
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
    result = result * PRIME + this.getCid();
    result = result * PRIME + this.getSourceFont();
    result = result * PRIME + this.getMaskFont();
    result = result * PRIME + this.getSourceChar();
    result = result * PRIME + this.getMaskChar();
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
    return "CreateGlyphCursor(cid=" + this.getCid() + ", sourceFont=" + this.getSourceFont() + ", maskFont=" + this.getMaskFont() + ", sourceChar=" + this.getSourceChar() + ", maskChar=" + this.getMaskChar() + ", foreRed=" + this.getForeRed() + ", foreGreen=" + this.getForeGreen() + ", foreBlue=" + this.getForeBlue() + ", backRed=" + this.getBackRed() + ", backGreen=" + this.getBackGreen() + ", backBlue=" + this.getBackBlue() + ")";
  }
}
