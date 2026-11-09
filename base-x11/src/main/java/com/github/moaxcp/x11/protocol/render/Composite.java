package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class Composite implements OneWayRequest {
  public static final String PLUGIN_NAME = "render";
  public static final byte OPCODE = 8;
  private final byte op;
  private final int src;
  private final int mask;
  private final int dst;
  private final short srcX;
  private final short srcY;
  private final short maskX;
  private final short maskY;
  private final short dstX;
  private final short dstY;
  private final short width;
  private final short height;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Composite readComposite(X11Input in) throws IOException {
    Composite.CompositeBuilder javaBuilder = Composite.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    byte op = in.readCard8();
    byte[] pad4 = in.readPad(3);
    int src = in.readCard32();
    int mask = in.readCard32();
    int dst = in.readCard32();
    short srcX = in.readInt16();
    short srcY = in.readInt16();
    short maskX = in.readInt16();
    short maskY = in.readInt16();
    short dstX = in.readInt16();
    short dstY = in.readInt16();
    short width = in.readCard16();
    short height = in.readCard16();
    javaBuilder.op(op);
    javaBuilder.src(src);
    javaBuilder.mask(mask);
    javaBuilder.dst(dst);
    javaBuilder.srcX(srcX);
    javaBuilder.srcY(srcY);
    javaBuilder.maskX(maskX);
    javaBuilder.maskY(maskY);
    javaBuilder.dstX(dstX);
    javaBuilder.dstY(dstY);
    javaBuilder.width(width);
    javaBuilder.height(height);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard8(op);
    out.writePad(3);
    out.writeCard32(src);
    out.writeCard32(mask);
    out.writeCard32(dst);
    out.writeInt16(srcX);
    out.writeInt16(srcY);
    out.writeInt16(maskX);
    out.writeInt16(maskY);
    out.writeInt16(dstX);
    out.writeInt16(dstY);
    out.writeCard16(width);
    out.writeCard16(height);
  }

  @Override
  public int getSize() {
    return 36;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CompositeBuilder {
    @java.lang.SuppressWarnings("all")
    private byte op;
    @java.lang.SuppressWarnings("all")
    private int src;
    @java.lang.SuppressWarnings("all")
    private int mask;
    @java.lang.SuppressWarnings("all")
    private int dst;
    @java.lang.SuppressWarnings("all")
    private short srcX;
    @java.lang.SuppressWarnings("all")
    private short srcY;
    @java.lang.SuppressWarnings("all")
    private short maskX;
    @java.lang.SuppressWarnings("all")
    private short maskY;
    @java.lang.SuppressWarnings("all")
    private short dstX;
    @java.lang.SuppressWarnings("all")
    private short dstY;
    @java.lang.SuppressWarnings("all")
    private short width;
    @java.lang.SuppressWarnings("all")
    private short height;

    public Composite.CompositeBuilder op(PictOp op) {
      this.op = (byte) op.getValue();
      return this;
    }

    public Composite.CompositeBuilder op(byte op) {
      this.op = op;
      return this;
    }

    public int getSize() {
      return 36;
    }

    @java.lang.SuppressWarnings("all")
    CompositeBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Composite.CompositeBuilder src(final int src) {
      this.src = src;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Composite.CompositeBuilder mask(final int mask) {
      this.mask = mask;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Composite.CompositeBuilder dst(final int dst) {
      this.dst = dst;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Composite.CompositeBuilder srcX(final short srcX) {
      this.srcX = srcX;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Composite.CompositeBuilder srcY(final short srcY) {
      this.srcY = srcY;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Composite.CompositeBuilder maskX(final short maskX) {
      this.maskX = maskX;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Composite.CompositeBuilder maskY(final short maskY) {
      this.maskY = maskY;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Composite.CompositeBuilder dstX(final short dstX) {
      this.dstX = dstX;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Composite.CompositeBuilder dstY(final short dstY) {
      this.dstY = dstY;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Composite.CompositeBuilder width(final short width) {
      this.width = width;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Composite.CompositeBuilder height(final short height) {
      this.height = height;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Composite build() {
      return new Composite(this.op, this.src, this.mask, this.dst, this.srcX, this.srcY, this.maskX, this.maskY, this.dstX, this.dstY, this.width, this.height);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Composite.CompositeBuilder(op=" + this.op + ", src=" + this.src + ", mask=" + this.mask + ", dst=" + this.dst + ", srcX=" + this.srcX + ", srcY=" + this.srcY + ", maskX=" + this.maskX + ", maskY=" + this.maskY + ", dstX=" + this.dstX + ", dstY=" + this.dstY + ", width=" + this.width + ", height=" + this.height + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Composite(final byte op, final int src, final int mask, final int dst, final short srcX, final short srcY, final short maskX, final short maskY, final short dstX, final short dstY, final short width, final short height) {
    this.op = op;
    this.src = src;
    this.mask = mask;
    this.dst = dst;
    this.srcX = srcX;
    this.srcY = srcY;
    this.maskX = maskX;
    this.maskY = maskY;
    this.dstX = dstX;
    this.dstY = dstY;
    this.width = width;
    this.height = height;
  }

  @java.lang.SuppressWarnings("all")
  public static Composite.CompositeBuilder builder() {
    return new Composite.CompositeBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getOp() {
    return this.op;
  }

  @java.lang.SuppressWarnings("all")
  public int getSrc() {
    return this.src;
  }

  @java.lang.SuppressWarnings("all")
  public int getMask() {
    return this.mask;
  }

  @java.lang.SuppressWarnings("all")
  public int getDst() {
    return this.dst;
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
  public short getMaskX() {
    return this.maskX;
  }

  @java.lang.SuppressWarnings("all")
  public short getMaskY() {
    return this.maskY;
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
    if (!(o instanceof Composite)) return false;
    final Composite other = (Composite) o;
    if (this.getOp() != other.getOp()) return false;
    if (this.getSrc() != other.getSrc()) return false;
    if (this.getMask() != other.getMask()) return false;
    if (this.getDst() != other.getDst()) return false;
    if (this.getSrcX() != other.getSrcX()) return false;
    if (this.getSrcY() != other.getSrcY()) return false;
    if (this.getMaskX() != other.getMaskX()) return false;
    if (this.getMaskY() != other.getMaskY()) return false;
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
    result = result * PRIME + this.getOp();
    result = result * PRIME + this.getSrc();
    result = result * PRIME + this.getMask();
    result = result * PRIME + this.getDst();
    result = result * PRIME + this.getSrcX();
    result = result * PRIME + this.getSrcY();
    result = result * PRIME + this.getMaskX();
    result = result * PRIME + this.getMaskY();
    result = result * PRIME + this.getDstX();
    result = result * PRIME + this.getDstY();
    result = result * PRIME + this.getWidth();
    result = result * PRIME + this.getHeight();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Composite(op=" + this.getOp() + ", src=" + this.getSrc() + ", mask=" + this.getMask() + ", dst=" + this.getDst() + ", srcX=" + this.getSrcX() + ", srcY=" + this.getSrcY() + ", maskX=" + this.getMaskX() + ", maskY=" + this.getMaskY() + ", dstX=" + this.getDstX() + ", dstY=" + this.getDstY() + ", width=" + this.getWidth() + ", height=" + this.getHeight() + ")";
  }
}
