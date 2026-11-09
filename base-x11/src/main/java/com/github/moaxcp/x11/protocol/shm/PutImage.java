package com.github.moaxcp.x11.protocol.shm;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class PutImage implements OneWayRequest {
  public static final String PLUGIN_NAME = "shm";
  public static final byte OPCODE = 3;
  private final int drawable;
  private final int gc;
  private final short totalWidth;
  private final short totalHeight;
  private final short srcX;
  private final short srcY;
  private final short srcWidth;
  private final short srcHeight;
  private final short dstX;
  private final short dstY;
  private final byte depth;
  private final byte format;
  private final boolean sendEvent;
  private final int shmseg;
  private final int offset;

  public byte getOpCode() {
    return OPCODE;
  }

  public static PutImage readPutImage(X11Input in) throws IOException {
    PutImage.PutImageBuilder javaBuilder = PutImage.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int drawable = in.readCard32();
    int gc = in.readCard32();
    short totalWidth = in.readCard16();
    short totalHeight = in.readCard16();
    short srcX = in.readCard16();
    short srcY = in.readCard16();
    short srcWidth = in.readCard16();
    short srcHeight = in.readCard16();
    short dstX = in.readInt16();
    short dstY = in.readInt16();
    byte depth = in.readCard8();
    byte format = in.readCard8();
    boolean sendEvent = in.readBool();
    byte[] pad16 = in.readPad(1);
    int shmseg = in.readCard32();
    int offset = in.readCard32();
    javaBuilder.drawable(drawable);
    javaBuilder.gc(gc);
    javaBuilder.totalWidth(totalWidth);
    javaBuilder.totalHeight(totalHeight);
    javaBuilder.srcX(srcX);
    javaBuilder.srcY(srcY);
    javaBuilder.srcWidth(srcWidth);
    javaBuilder.srcHeight(srcHeight);
    javaBuilder.dstX(dstX);
    javaBuilder.dstY(dstY);
    javaBuilder.depth(depth);
    javaBuilder.format(format);
    javaBuilder.sendEvent(sendEvent);
    javaBuilder.shmseg(shmseg);
    javaBuilder.offset(offset);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeCard32(gc);
    out.writeCard16(totalWidth);
    out.writeCard16(totalHeight);
    out.writeCard16(srcX);
    out.writeCard16(srcY);
    out.writeCard16(srcWidth);
    out.writeCard16(srcHeight);
    out.writeInt16(dstX);
    out.writeInt16(dstY);
    out.writeCard8(depth);
    out.writeCard8(format);
    out.writeBool(sendEvent);
    out.writePad(1);
    out.writeCard32(shmseg);
    out.writeCard32(offset);
  }

  @Override
  public int getSize() {
    return 40;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class PutImageBuilder {
    @java.lang.SuppressWarnings("all")
    private int drawable;
    @java.lang.SuppressWarnings("all")
    private int gc;
    @java.lang.SuppressWarnings("all")
    private short totalWidth;
    @java.lang.SuppressWarnings("all")
    private short totalHeight;
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
    @java.lang.SuppressWarnings("all")
    private byte depth;
    @java.lang.SuppressWarnings("all")
    private byte format;
    @java.lang.SuppressWarnings("all")
    private boolean sendEvent;
    @java.lang.SuppressWarnings("all")
    private int shmseg;
    @java.lang.SuppressWarnings("all")
    private int offset;

    public int getSize() {
      return 40;
    }

    @java.lang.SuppressWarnings("all")
    PutImageBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PutImage.PutImageBuilder drawable(final int drawable) {
      this.drawable = drawable;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PutImage.PutImageBuilder gc(final int gc) {
      this.gc = gc;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PutImage.PutImageBuilder totalWidth(final short totalWidth) {
      this.totalWidth = totalWidth;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PutImage.PutImageBuilder totalHeight(final short totalHeight) {
      this.totalHeight = totalHeight;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PutImage.PutImageBuilder srcX(final short srcX) {
      this.srcX = srcX;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PutImage.PutImageBuilder srcY(final short srcY) {
      this.srcY = srcY;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PutImage.PutImageBuilder srcWidth(final short srcWidth) {
      this.srcWidth = srcWidth;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PutImage.PutImageBuilder srcHeight(final short srcHeight) {
      this.srcHeight = srcHeight;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PutImage.PutImageBuilder dstX(final short dstX) {
      this.dstX = dstX;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PutImage.PutImageBuilder dstY(final short dstY) {
      this.dstY = dstY;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PutImage.PutImageBuilder depth(final byte depth) {
      this.depth = depth;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PutImage.PutImageBuilder format(final byte format) {
      this.format = format;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PutImage.PutImageBuilder sendEvent(final boolean sendEvent) {
      this.sendEvent = sendEvent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PutImage.PutImageBuilder shmseg(final int shmseg) {
      this.shmseg = shmseg;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PutImage.PutImageBuilder offset(final int offset) {
      this.offset = offset;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public PutImage build() {
      return new PutImage(this.drawable, this.gc, this.totalWidth, this.totalHeight, this.srcX, this.srcY, this.srcWidth, this.srcHeight, this.dstX, this.dstY, this.depth, this.format, this.sendEvent, this.shmseg, this.offset);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "PutImage.PutImageBuilder(drawable=" + this.drawable + ", gc=" + this.gc + ", totalWidth=" + this.totalWidth + ", totalHeight=" + this.totalHeight + ", srcX=" + this.srcX + ", srcY=" + this.srcY + ", srcWidth=" + this.srcWidth + ", srcHeight=" + this.srcHeight + ", dstX=" + this.dstX + ", dstY=" + this.dstY + ", depth=" + this.depth + ", format=" + this.format + ", sendEvent=" + this.sendEvent + ", shmseg=" + this.shmseg + ", offset=" + this.offset + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  PutImage(final int drawable, final int gc, final short totalWidth, final short totalHeight, final short srcX, final short srcY, final short srcWidth, final short srcHeight, final short dstX, final short dstY, final byte depth, final byte format, final boolean sendEvent, final int shmseg, final int offset) {
    this.drawable = drawable;
    this.gc = gc;
    this.totalWidth = totalWidth;
    this.totalHeight = totalHeight;
    this.srcX = srcX;
    this.srcY = srcY;
    this.srcWidth = srcWidth;
    this.srcHeight = srcHeight;
    this.dstX = dstX;
    this.dstY = dstY;
    this.depth = depth;
    this.format = format;
    this.sendEvent = sendEvent;
    this.shmseg = shmseg;
    this.offset = offset;
  }

  @java.lang.SuppressWarnings("all")
  public static PutImage.PutImageBuilder builder() {
    return new PutImage.PutImageBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getDrawable() {
    return this.drawable;
  }

  @java.lang.SuppressWarnings("all")
  public int getGc() {
    return this.gc;
  }

  @java.lang.SuppressWarnings("all")
  public short getTotalWidth() {
    return this.totalWidth;
  }

  @java.lang.SuppressWarnings("all")
  public short getTotalHeight() {
    return this.totalHeight;
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

  @java.lang.SuppressWarnings("all")
  public byte getDepth() {
    return this.depth;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFormat() {
    return this.format;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isSendEvent() {
    return this.sendEvent;
  }

  @java.lang.SuppressWarnings("all")
  public int getShmseg() {
    return this.shmseg;
  }

  @java.lang.SuppressWarnings("all")
  public int getOffset() {
    return this.offset;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof PutImage)) return false;
    final PutImage other = (PutImage) o;
    if (this.getDrawable() != other.getDrawable()) return false;
    if (this.getGc() != other.getGc()) return false;
    if (this.getTotalWidth() != other.getTotalWidth()) return false;
    if (this.getTotalHeight() != other.getTotalHeight()) return false;
    if (this.getSrcX() != other.getSrcX()) return false;
    if (this.getSrcY() != other.getSrcY()) return false;
    if (this.getSrcWidth() != other.getSrcWidth()) return false;
    if (this.getSrcHeight() != other.getSrcHeight()) return false;
    if (this.getDstX() != other.getDstX()) return false;
    if (this.getDstY() != other.getDstY()) return false;
    if (this.getDepth() != other.getDepth()) return false;
    if (this.getFormat() != other.getFormat()) return false;
    if (this.isSendEvent() != other.isSendEvent()) return false;
    if (this.getShmseg() != other.getShmseg()) return false;
    if (this.getOffset() != other.getOffset()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDrawable();
    result = result * PRIME + this.getGc();
    result = result * PRIME + this.getTotalWidth();
    result = result * PRIME + this.getTotalHeight();
    result = result * PRIME + this.getSrcX();
    result = result * PRIME + this.getSrcY();
    result = result * PRIME + this.getSrcWidth();
    result = result * PRIME + this.getSrcHeight();
    result = result * PRIME + this.getDstX();
    result = result * PRIME + this.getDstY();
    result = result * PRIME + this.getDepth();
    result = result * PRIME + this.getFormat();
    result = result * PRIME + (this.isSendEvent() ? 79 : 97);
    result = result * PRIME + this.getShmseg();
    result = result * PRIME + this.getOffset();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "PutImage(drawable=" + this.getDrawable() + ", gc=" + this.getGc() + ", totalWidth=" + this.getTotalWidth() + ", totalHeight=" + this.getTotalHeight() + ", srcX=" + this.getSrcX() + ", srcY=" + this.getSrcY() + ", srcWidth=" + this.getSrcWidth() + ", srcHeight=" + this.getSrcHeight() + ", dstX=" + this.getDstX() + ", dstY=" + this.getDstY() + ", depth=" + this.getDepth() + ", format=" + this.getFormat() + ", sendEvent=" + this.isSendEvent() + ", shmseg=" + this.getShmseg() + ", offset=" + this.getOffset() + ")";
  }
}
