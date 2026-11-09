package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class PutImage implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 72;
  private final byte format;
  private final int drawable;
  private final int gc;
  private final short width;
  private final short height;
  private final short dstX;
  private final short dstY;
  private final byte leftPad;
  private final byte depth;
  private final byte[] data;

  public byte getOpCode() {
    return OPCODE;
  }

  public static PutImage readPutImage(X11Input in) throws IOException {
    PutImage.PutImageBuilder javaBuilder = PutImage.builder();
    int javaStart = 1;
    byte format = in.readCard8();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int drawable = in.readCard32();
    javaStart += 4;
    int gc = in.readCard32();
    javaStart += 4;
    short width = in.readCard16();
    javaStart += 2;
    short height = in.readCard16();
    javaStart += 2;
    short dstX = in.readInt16();
    javaStart += 2;
    short dstY = in.readInt16();
    javaStart += 2;
    byte leftPad = in.readCard8();
    javaStart += 1;
    byte depth = in.readCard8();
    javaStart += 1;
    byte[] pad11 = in.readPad(2);
    javaStart += 2;
    byte[] data = in.readByte(Short.toUnsignedInt(length) - javaStart);
    javaBuilder.format(format);
    javaBuilder.drawable(drawable);
    javaBuilder.gc(gc);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.dstX(dstX);
    javaBuilder.dstY(dstY);
    javaBuilder.leftPad(leftPad);
    javaBuilder.depth(depth);
    javaBuilder.data(data);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard8(format);
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeCard32(gc);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeInt16(dstX);
    out.writeInt16(dstY);
    out.writeCard8(leftPad);
    out.writeCard8(depth);
    out.writePad(2);
    out.writeByte(data);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 24 + 1 * data.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class PutImageBuilder {
    @java.lang.SuppressWarnings("all")
    private byte format;
    @java.lang.SuppressWarnings("all")
    private int drawable;
    @java.lang.SuppressWarnings("all")
    private int gc;
    @java.lang.SuppressWarnings("all")
    private short width;
    @java.lang.SuppressWarnings("all")
    private short height;
    @java.lang.SuppressWarnings("all")
    private short dstX;
    @java.lang.SuppressWarnings("all")
    private short dstY;
    @java.lang.SuppressWarnings("all")
    private byte leftPad;
    @java.lang.SuppressWarnings("all")
    private byte depth;
    @java.lang.SuppressWarnings("all")
    private byte[] data;

    public PutImage.PutImageBuilder format(ImageFormat format) {
      this.format = (byte) format.getValue();
      return this;
    }

    public PutImage.PutImageBuilder format(byte format) {
      this.format = format;
      return this;
    }

    public int getSize() {
      return 24 + 1 * data.length;
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
    public PutImage.PutImageBuilder width(final short width) {
      this.width = width;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PutImage.PutImageBuilder height(final short height) {
      this.height = height;
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
    public PutImage.PutImageBuilder leftPad(final byte leftPad) {
      this.leftPad = leftPad;
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
    public PutImage.PutImageBuilder data(final byte[] data) {
      if (data == null) {
        throw new java.lang.NullPointerException("data is marked non-null but is null");
      }
      this.data = data;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public PutImage build() {
      return new PutImage(this.format, this.drawable, this.gc, this.width, this.height, this.dstX, this.dstY, this.leftPad, this.depth, this.data);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "PutImage.PutImageBuilder(format=" + this.format + ", drawable=" + this.drawable + ", gc=" + this.gc + ", width=" + this.width + ", height=" + this.height + ", dstX=" + this.dstX + ", dstY=" + this.dstY + ", leftPad=" + this.leftPad + ", depth=" + this.depth + ", data=" + java.util.Arrays.toString(this.data) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  PutImage(final byte format, final int drawable, final int gc, final short width, final short height, final short dstX, final short dstY, final byte leftPad, final byte depth, final byte[] data) {
    if (data == null) {
      throw new java.lang.NullPointerException("data is marked non-null but is null");
    }
    this.format = format;
    this.drawable = drawable;
    this.gc = gc;
    this.width = width;
    this.height = height;
    this.dstX = dstX;
    this.dstY = dstY;
    this.leftPad = leftPad;
    this.depth = depth;
    this.data = data;
  }

  @java.lang.SuppressWarnings("all")
  public static PutImage.PutImageBuilder builder() {
    return new PutImage.PutImageBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getFormat() {
    return this.format;
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
  public short getWidth() {
    return this.width;
  }

  @java.lang.SuppressWarnings("all")
  public short getHeight() {
    return this.height;
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
  public byte getLeftPad() {
    return this.leftPad;
  }

  @java.lang.SuppressWarnings("all")
  public byte getDepth() {
    return this.depth;
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getData() {
    return this.data;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof PutImage)) return false;
    final PutImage other = (PutImage) o;
    if (this.getFormat() != other.getFormat()) return false;
    if (this.getDrawable() != other.getDrawable()) return false;
    if (this.getGc() != other.getGc()) return false;
    if (this.getWidth() != other.getWidth()) return false;
    if (this.getHeight() != other.getHeight()) return false;
    if (this.getDstX() != other.getDstX()) return false;
    if (this.getDstY() != other.getDstY()) return false;
    if (this.getLeftPad() != other.getLeftPad()) return false;
    if (this.getDepth() != other.getDepth()) return false;
    if (!java.util.Arrays.equals(this.getData(), other.getData())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFormat();
    result = result * PRIME + this.getDrawable();
    result = result * PRIME + this.getGc();
    result = result * PRIME + this.getWidth();
    result = result * PRIME + this.getHeight();
    result = result * PRIME + this.getDstX();
    result = result * PRIME + this.getDstY();
    result = result * PRIME + this.getLeftPad();
    result = result * PRIME + this.getDepth();
    result = result * PRIME + java.util.Arrays.hashCode(this.getData());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "PutImage(format=" + this.getFormat() + ", drawable=" + this.getDrawable() + ", gc=" + this.getGc() + ", width=" + this.getWidth() + ", height=" + this.getHeight() + ", dstX=" + this.getDstX() + ", dstY=" + this.getDstY() + ", leftPad=" + this.getLeftPad() + ", depth=" + this.getDepth() + ", data=" + java.util.Arrays.toString(this.getData()) + ")";
  }
}
