package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class WarpPointer implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 41;
  private final int srcWindow;
  private final int dstWindow;
  private final short srcX;
  private final short srcY;
  private final short srcWidth;
  private final short srcHeight;
  private final short dstX;
  private final short dstY;

  public byte getOpCode() {
    return OPCODE;
  }

  public static WarpPointer readWarpPointer(X11Input in) throws IOException {
    WarpPointer.WarpPointerBuilder javaBuilder = WarpPointer.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int srcWindow = in.readCard32();
    int dstWindow = in.readCard32();
    short srcX = in.readInt16();
    short srcY = in.readInt16();
    short srcWidth = in.readCard16();
    short srcHeight = in.readCard16();
    short dstX = in.readInt16();
    short dstY = in.readInt16();
    javaBuilder.srcWindow(srcWindow);
    javaBuilder.dstWindow(dstWindow);
    javaBuilder.srcX(srcX);
    javaBuilder.srcY(srcY);
    javaBuilder.srcWidth(srcWidth);
    javaBuilder.srcHeight(srcHeight);
    javaBuilder.dstX(dstX);
    javaBuilder.dstY(dstY);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(srcWindow);
    out.writeCard32(dstWindow);
    out.writeInt16(srcX);
    out.writeInt16(srcY);
    out.writeCard16(srcWidth);
    out.writeCard16(srcHeight);
    out.writeInt16(dstX);
    out.writeInt16(dstY);
  }

  @Override
  public int getSize() {
    return 24;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class WarpPointerBuilder {
    @java.lang.SuppressWarnings("all")
    private int srcWindow;
    @java.lang.SuppressWarnings("all")
    private int dstWindow;
    @java.lang.SuppressWarnings("all")
    private short srcX;
    @java.lang.SuppressWarnings("all")
    private short srcY;
    @java.lang.SuppressWarnings("all")
    private short srcWidth;
    @java.lang.SuppressWarnings("all")
    private short srcHeight;
    @java.lang.SuppressWarnings("all")
    private short dstX;
    @java.lang.SuppressWarnings("all")
    private short dstY;

    public int getSize() {
      return 24;
    }

    @java.lang.SuppressWarnings("all")
    WarpPointerBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public WarpPointer.WarpPointerBuilder srcWindow(final int srcWindow) {
      this.srcWindow = srcWindow;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public WarpPointer.WarpPointerBuilder dstWindow(final int dstWindow) {
      this.dstWindow = dstWindow;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public WarpPointer.WarpPointerBuilder srcX(final short srcX) {
      this.srcX = srcX;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public WarpPointer.WarpPointerBuilder srcY(final short srcY) {
      this.srcY = srcY;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public WarpPointer.WarpPointerBuilder srcWidth(final short srcWidth) {
      this.srcWidth = srcWidth;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public WarpPointer.WarpPointerBuilder srcHeight(final short srcHeight) {
      this.srcHeight = srcHeight;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public WarpPointer.WarpPointerBuilder dstX(final short dstX) {
      this.dstX = dstX;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public WarpPointer.WarpPointerBuilder dstY(final short dstY) {
      this.dstY = dstY;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public WarpPointer build() {
      return new WarpPointer(this.srcWindow, this.dstWindow, this.srcX, this.srcY, this.srcWidth, this.srcHeight, this.dstX, this.dstY);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "WarpPointer.WarpPointerBuilder(srcWindow=" + this.srcWindow + ", dstWindow=" + this.dstWindow + ", srcX=" + this.srcX + ", srcY=" + this.srcY + ", srcWidth=" + this.srcWidth + ", srcHeight=" + this.srcHeight + ", dstX=" + this.dstX + ", dstY=" + this.dstY + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  WarpPointer(final int srcWindow, final int dstWindow, final short srcX, final short srcY, final short srcWidth, final short srcHeight, final short dstX, final short dstY) {
    this.srcWindow = srcWindow;
    this.dstWindow = dstWindow;
    this.srcX = srcX;
    this.srcY = srcY;
    this.srcWidth = srcWidth;
    this.srcHeight = srcHeight;
    this.dstX = dstX;
    this.dstY = dstY;
  }

  @java.lang.SuppressWarnings("all")
  public static WarpPointer.WarpPointerBuilder builder() {
    return new WarpPointer.WarpPointerBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getSrcWindow() {
    return this.srcWindow;
  }

  @java.lang.SuppressWarnings("all")
  public int getDstWindow() {
    return this.dstWindow;
  }

  @java.lang.SuppressWarnings("all")
  public short getSrcX() {
    return this.srcX;
  }

  @java.lang.SuppressWarnings("all")
  public short getSrcY() {
    return this.srcY;
  }

  @java.lang.SuppressWarnings("all")
  public short getSrcWidth() {
    return this.srcWidth;
  }

  @java.lang.SuppressWarnings("all")
  public short getSrcHeight() {
    return this.srcHeight;
  }

  @java.lang.SuppressWarnings("all")
  public short getDstX() {
    return this.dstX;
  }

  @java.lang.SuppressWarnings("all")
  public short getDstY() {
    return this.dstY;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof WarpPointer)) return false;
    final WarpPointer other = (WarpPointer) o;
    if (this.getSrcWindow() != other.getSrcWindow()) return false;
    if (this.getDstWindow() != other.getDstWindow()) return false;
    if (this.getSrcX() != other.getSrcX()) return false;
    if (this.getSrcY() != other.getSrcY()) return false;
    if (this.getSrcWidth() != other.getSrcWidth()) return false;
    if (this.getSrcHeight() != other.getSrcHeight()) return false;
    if (this.getDstX() != other.getDstX()) return false;
    if (this.getDstY() != other.getDstY()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSrcWindow();
    result = result * PRIME + this.getDstWindow();
    result = result * PRIME + this.getSrcX();
    result = result * PRIME + this.getSrcY();
    result = result * PRIME + this.getSrcWidth();
    result = result * PRIME + this.getSrcHeight();
    result = result * PRIME + this.getDstX();
    result = result * PRIME + this.getDstY();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "WarpPointer(srcWindow=" + this.getSrcWindow() + ", dstWindow=" + this.getDstWindow() + ", srcX=" + this.getSrcX() + ", srcY=" + this.getSrcY() + ", srcWidth=" + this.getSrcWidth() + ", srcHeight=" + this.getSrcHeight() + ", dstX=" + this.getDstX() + ", dstY=" + this.getDstY() + ")";
  }
}
