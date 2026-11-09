package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class CopyArea implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 62;
  private final int srcDrawable;
  private final int dstDrawable;
  private final int gc;
  private final short srcX;
  private final short srcY;
  private final short dstX;
  private final short dstY;
  private final short width;
  private final short height;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CopyArea readCopyArea(X11Input in) throws IOException {
    CopyArea.CopyAreaBuilder javaBuilder = CopyArea.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int srcDrawable = in.readCard32();
    int dstDrawable = in.readCard32();
    int gc = in.readCard32();
    short srcX = in.readInt16();
    short srcY = in.readInt16();
    short dstX = in.readInt16();
    short dstY = in.readInt16();
    short width = in.readCard16();
    short height = in.readCard16();
    javaBuilder.srcDrawable(srcDrawable);
    javaBuilder.dstDrawable(dstDrawable);
    javaBuilder.gc(gc);
    javaBuilder.srcX(srcX);
    javaBuilder.srcY(srcY);
    javaBuilder.dstX(dstX);
    javaBuilder.dstY(dstY);
    javaBuilder.width(width);
    javaBuilder.height(height);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(srcDrawable);
    out.writeCard32(dstDrawable);
    out.writeCard32(gc);
    out.writeInt16(srcX);
    out.writeInt16(srcY);
    out.writeInt16(dstX);
    out.writeInt16(dstY);
    out.writeCard16(width);
    out.writeCard16(height);
  }

  @Override
  public int getSize() {
    return 28;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CopyAreaBuilder {
    @java.lang.SuppressWarnings("all")
    private int srcDrawable;
    @java.lang.SuppressWarnings("all")
    private int dstDrawable;
    @java.lang.SuppressWarnings("all")
    private int gc;
    @java.lang.SuppressWarnings("all")
    private short srcX;
    @java.lang.SuppressWarnings("all")
    private short srcY;
    @java.lang.SuppressWarnings("all")
    private short dstX;
    @java.lang.SuppressWarnings("all")
    private short dstY;
    @java.lang.SuppressWarnings("all")
    private short width;
    @java.lang.SuppressWarnings("all")
    private short height;

    public int getSize() {
      return 28;
    }

    @java.lang.SuppressWarnings("all")
    CopyAreaBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CopyArea.CopyAreaBuilder srcDrawable(final int srcDrawable) {
      this.srcDrawable = srcDrawable;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CopyArea.CopyAreaBuilder dstDrawable(final int dstDrawable) {
      this.dstDrawable = dstDrawable;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CopyArea.CopyAreaBuilder gc(final int gc) {
      this.gc = gc;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CopyArea.CopyAreaBuilder srcX(final short srcX) {
      this.srcX = srcX;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CopyArea.CopyAreaBuilder srcY(final short srcY) {
      this.srcY = srcY;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CopyArea.CopyAreaBuilder dstX(final short dstX) {
      this.dstX = dstX;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CopyArea.CopyAreaBuilder dstY(final short dstY) {
      this.dstY = dstY;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CopyArea.CopyAreaBuilder width(final short width) {
      this.width = width;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CopyArea.CopyAreaBuilder height(final short height) {
      this.height = height;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CopyArea build() {
      return new CopyArea(this.srcDrawable, this.dstDrawable, this.gc, this.srcX, this.srcY, this.dstX, this.dstY, this.width, this.height);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CopyArea.CopyAreaBuilder(srcDrawable=" + this.srcDrawable + ", dstDrawable=" + this.dstDrawable + ", gc=" + this.gc + ", srcX=" + this.srcX + ", srcY=" + this.srcY + ", dstX=" + this.dstX + ", dstY=" + this.dstY + ", width=" + this.width + ", height=" + this.height + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CopyArea(final int srcDrawable, final int dstDrawable, final int gc, final short srcX, final short srcY, final short dstX, final short dstY, final short width, final short height) {
    this.srcDrawable = srcDrawable;
    this.dstDrawable = dstDrawable;
    this.gc = gc;
    this.srcX = srcX;
    this.srcY = srcY;
    this.dstX = dstX;
    this.dstY = dstY;
    this.width = width;
    this.height = height;
  }

  @java.lang.SuppressWarnings("all")
  public static CopyArea.CopyAreaBuilder builder() {
    return new CopyArea.CopyAreaBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getSrcDrawable() {
    return this.srcDrawable;
  }

  @java.lang.SuppressWarnings("all")
  public int getDstDrawable() {
    return this.dstDrawable;
  }

  @java.lang.SuppressWarnings("all")
  public int getGc() {
    return this.gc;
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
  public short getDstX() {
    return this.dstX;
  }

  @java.lang.SuppressWarnings("all")
  public short getDstY() {
    return this.dstY;
  }

  @java.lang.SuppressWarnings("all")
  public short getWidth() {
    return this.width;
  }

  @java.lang.SuppressWarnings("all")
  public short getHeight() {
    return this.height;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CopyArea)) return false;
    final CopyArea other = (CopyArea) o;
    if (this.getSrcDrawable() != other.getSrcDrawable()) return false;
    if (this.getDstDrawable() != other.getDstDrawable()) return false;
    if (this.getGc() != other.getGc()) return false;
    if (this.getSrcX() != other.getSrcX()) return false;
    if (this.getSrcY() != other.getSrcY()) return false;
    if (this.getDstX() != other.getDstX()) return false;
    if (this.getDstY() != other.getDstY()) return false;
    if (this.getWidth() != other.getWidth()) return false;
    if (this.getHeight() != other.getHeight()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSrcDrawable();
    result = result * PRIME + this.getDstDrawable();
    result = result * PRIME + this.getGc();
    result = result * PRIME + this.getSrcX();
    result = result * PRIME + this.getSrcY();
    result = result * PRIME + this.getDstX();
    result = result * PRIME + this.getDstY();
    result = result * PRIME + this.getWidth();
    result = result * PRIME + this.getHeight();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CopyArea(srcDrawable=" + this.getSrcDrawable() + ", dstDrawable=" + this.getDstDrawable() + ", gc=" + this.getGc() + ", srcX=" + this.getSrcX() + ", srcY=" + this.getSrcY() + ", dstX=" + this.getDstX() + ", dstY=" + this.getDstY() + ", width=" + this.getWidth() + ", height=" + this.getHeight() + ")";
  }
}
