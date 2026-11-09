package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class CreateCursor implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 93;
  private final int cid;
  private final int source;
  private final int mask;
  private final short foreRed;
  private final short foreGreen;
  private final short foreBlue;
  private final short backRed;
  private final short backGreen;
  private final short backBlue;
  private final short x;
  private final short y;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateCursor readCreateCursor(X11Input in) throws IOException {
    CreateCursor.CreateCursorBuilder javaBuilder = CreateCursor.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int cid = in.readCard32();
    int source = in.readCard32();
    int mask = in.readCard32();
    short foreRed = in.readCard16();
    short foreGreen = in.readCard16();
    short foreBlue = in.readCard16();
    short backRed = in.readCard16();
    short backGreen = in.readCard16();
    short backBlue = in.readCard16();
    short x = in.readCard16();
    short y = in.readCard16();
    javaBuilder.cid(cid);
    javaBuilder.source(source);
    javaBuilder.mask(mask);
    javaBuilder.foreRed(foreRed);
    javaBuilder.foreGreen(foreGreen);
    javaBuilder.foreBlue(foreBlue);
    javaBuilder.backRed(backRed);
    javaBuilder.backGreen(backGreen);
    javaBuilder.backBlue(backBlue);
    javaBuilder.x(x);
    javaBuilder.y(y);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(cid);
    out.writeCard32(source);
    out.writeCard32(mask);
    out.writeCard16(foreRed);
    out.writeCard16(foreGreen);
    out.writeCard16(foreBlue);
    out.writeCard16(backRed);
    out.writeCard16(backGreen);
    out.writeCard16(backBlue);
    out.writeCard16(x);
    out.writeCard16(y);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CreateCursorBuilder {
    @java.lang.SuppressWarnings("all")
    private int cid;
    @java.lang.SuppressWarnings("all")
    private int source;
    @java.lang.SuppressWarnings("all")
    private int mask;
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
    @java.lang.SuppressWarnings("all")
    private short x;
    @java.lang.SuppressWarnings("all")
    private short y;

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    CreateCursorBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateCursor.CreateCursorBuilder cid(final int cid) {
      this.cid = cid;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateCursor.CreateCursorBuilder source(final int source) {
      this.source = source;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateCursor.CreateCursorBuilder mask(final int mask) {
      this.mask = mask;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateCursor.CreateCursorBuilder foreRed(final short foreRed) {
      this.foreRed = foreRed;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateCursor.CreateCursorBuilder foreGreen(final short foreGreen) {
      this.foreGreen = foreGreen;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateCursor.CreateCursorBuilder foreBlue(final short foreBlue) {
      this.foreBlue = foreBlue;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateCursor.CreateCursorBuilder backRed(final short backRed) {
      this.backRed = backRed;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateCursor.CreateCursorBuilder backGreen(final short backGreen) {
      this.backGreen = backGreen;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateCursor.CreateCursorBuilder backBlue(final short backBlue) {
      this.backBlue = backBlue;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateCursor.CreateCursorBuilder x(final short x) {
      this.x = x;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateCursor.CreateCursorBuilder y(final short y) {
      this.y = y;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CreateCursor build() {
      return new CreateCursor(this.cid, this.source, this.mask, this.foreRed, this.foreGreen, this.foreBlue, this.backRed, this.backGreen, this.backBlue, this.x, this.y);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CreateCursor.CreateCursorBuilder(cid=" + this.cid + ", source=" + this.source + ", mask=" + this.mask + ", foreRed=" + this.foreRed + ", foreGreen=" + this.foreGreen + ", foreBlue=" + this.foreBlue + ", backRed=" + this.backRed + ", backGreen=" + this.backGreen + ", backBlue=" + this.backBlue + ", x=" + this.x + ", y=" + this.y + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CreateCursor(final int cid, final int source, final int mask, final short foreRed, final short foreGreen, final short foreBlue, final short backRed, final short backGreen, final short backBlue, final short x, final short y) {
    this.cid = cid;
    this.source = source;
    this.mask = mask;
    this.foreRed = foreRed;
    this.foreGreen = foreGreen;
    this.foreBlue = foreBlue;
    this.backRed = backRed;
    this.backGreen = backGreen;
    this.backBlue = backBlue;
    this.x = x;
    this.y = y;
  }

  @java.lang.SuppressWarnings("all")
  public static CreateCursor.CreateCursorBuilder builder() {
    return new CreateCursor.CreateCursorBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getCid() {
    return this.cid;
  }

  @java.lang.SuppressWarnings("all")
  public int getSource() {
    return this.source;
  }

  @java.lang.SuppressWarnings("all")
  public int getMask() {
    return this.mask;
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

  @java.lang.SuppressWarnings("all")
  public short getX() {
    return this.x;
  }

  @java.lang.SuppressWarnings("all")
  public short getY() {
    return this.y;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CreateCursor)) return false;
    final CreateCursor other = (CreateCursor) o;
    if (this.getCid() != other.getCid()) return false;
    if (this.getSource() != other.getSource()) return false;
    if (this.getMask() != other.getMask()) return false;
    if (this.getForeRed() != other.getForeRed()) return false;
    if (this.getForeGreen() != other.getForeGreen()) return false;
    if (this.getForeBlue() != other.getForeBlue()) return false;
    if (this.getBackRed() != other.getBackRed()) return false;
    if (this.getBackGreen() != other.getBackGreen()) return false;
    if (this.getBackBlue() != other.getBackBlue()) return false;
    if (this.getX() != other.getX()) return false;
    if (this.getY() != other.getY()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getCid();
    result = result * PRIME + this.getSource();
    result = result * PRIME + this.getMask();
    result = result * PRIME + this.getForeRed();
    result = result * PRIME + this.getForeGreen();
    result = result * PRIME + this.getForeBlue();
    result = result * PRIME + this.getBackRed();
    result = result * PRIME + this.getBackGreen();
    result = result * PRIME + this.getBackBlue();
    result = result * PRIME + this.getX();
    result = result * PRIME + this.getY();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CreateCursor(cid=" + this.getCid() + ", source=" + this.getSource() + ", mask=" + this.getMask() + ", foreRed=" + this.getForeRed() + ", foreGreen=" + this.getForeGreen() + ", foreBlue=" + this.getForeBlue() + ", backRed=" + this.getBackRed() + ", backGreen=" + this.getBackGreen() + ", backBlue=" + this.getBackBlue() + ", x=" + this.getX() + ", y=" + this.getY() + ")";
  }
}
