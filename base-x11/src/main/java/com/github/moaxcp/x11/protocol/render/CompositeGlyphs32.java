package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class CompositeGlyphs32 implements OneWayRequest {
  public static final String PLUGIN_NAME = "render";
  public static final byte OPCODE = 25;
  private final byte op;
  private final int src;
  private final int dst;
  private final int maskFormat;
  private final int glyphset;
  private final short srcX;
  private final short srcY;
  private final byte[] glyphcmds;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CompositeGlyphs32 readCompositeGlyphs32(X11Input in) throws IOException {
    CompositeGlyphs32.CompositeGlyphs32Builder javaBuilder = CompositeGlyphs32.builder();
    int javaStart = 1;
    byte majorOpcode = in.readCard8();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    byte op = in.readCard8();
    javaStart += 1;
    byte[] pad4 = in.readPad(3);
    javaStart += 3;
    int src = in.readCard32();
    javaStart += 4;
    int dst = in.readCard32();
    javaStart += 4;
    int maskFormat = in.readCard32();
    javaStart += 4;
    int glyphset = in.readCard32();
    javaStart += 4;
    short srcX = in.readInt16();
    javaStart += 2;
    short srcY = in.readInt16();
    javaStart += 2;
    byte[] glyphcmds = in.readByte(Short.toUnsignedInt(length) - javaStart);
    javaBuilder.op(op);
    javaBuilder.src(src);
    javaBuilder.dst(dst);
    javaBuilder.maskFormat(maskFormat);
    javaBuilder.glyphset(glyphset);
    javaBuilder.srcX(srcX);
    javaBuilder.srcY(srcY);
    javaBuilder.glyphcmds(glyphcmds);
    in.readPadAlign(javaBuilder.getSize());
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
    out.writeCard32(dst);
    out.writeCard32(maskFormat);
    out.writeCard32(glyphset);
    out.writeInt16(srcX);
    out.writeInt16(srcY);
    out.writeByte(glyphcmds);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 28 + 1 * glyphcmds.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CompositeGlyphs32Builder {
    @java.lang.SuppressWarnings("all")
    private byte op;
    @java.lang.SuppressWarnings("all")
    private int src;
    @java.lang.SuppressWarnings("all")
    private int dst;
    @java.lang.SuppressWarnings("all")
    private int maskFormat;
    @java.lang.SuppressWarnings("all")
    private int glyphset;
    @java.lang.SuppressWarnings("all")
    private short srcX;
    @java.lang.SuppressWarnings("all")
    private short srcY;
    @java.lang.SuppressWarnings("all")
    private byte[] glyphcmds;

    public CompositeGlyphs32.CompositeGlyphs32Builder op(PictOp op) {
      this.op = (byte) op.getValue();
      return this;
    }

    public CompositeGlyphs32.CompositeGlyphs32Builder op(byte op) {
      this.op = op;
      return this;
    }

    public int getSize() {
      return 28 + 1 * glyphcmds.length;
    }

    @java.lang.SuppressWarnings("all")
    CompositeGlyphs32Builder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CompositeGlyphs32.CompositeGlyphs32Builder src(final int src) {
      this.src = src;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CompositeGlyphs32.CompositeGlyphs32Builder dst(final int dst) {
      this.dst = dst;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CompositeGlyphs32.CompositeGlyphs32Builder maskFormat(final int maskFormat) {
      this.maskFormat = maskFormat;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CompositeGlyphs32.CompositeGlyphs32Builder glyphset(final int glyphset) {
      this.glyphset = glyphset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CompositeGlyphs32.CompositeGlyphs32Builder srcX(final short srcX) {
      this.srcX = srcX;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CompositeGlyphs32.CompositeGlyphs32Builder srcY(final short srcY) {
      this.srcY = srcY;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CompositeGlyphs32.CompositeGlyphs32Builder glyphcmds(final byte[] glyphcmds) {
      if (glyphcmds == null) {
        throw new java.lang.NullPointerException("glyphcmds is marked non-null but is null");
      }
      this.glyphcmds = glyphcmds;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CompositeGlyphs32 build() {
      return new CompositeGlyphs32(this.op, this.src, this.dst, this.maskFormat, this.glyphset, this.srcX, this.srcY, this.glyphcmds);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CompositeGlyphs32.CompositeGlyphs32Builder(op=" + this.op + ", src=" + this.src + ", dst=" + this.dst + ", maskFormat=" + this.maskFormat + ", glyphset=" + this.glyphset + ", srcX=" + this.srcX + ", srcY=" + this.srcY + ", glyphcmds=" + java.util.Arrays.toString(this.glyphcmds) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CompositeGlyphs32(final byte op, final int src, final int dst, final int maskFormat, final int glyphset, final short srcX, final short srcY, final byte[] glyphcmds) {
    if (glyphcmds == null) {
      throw new java.lang.NullPointerException("glyphcmds is marked non-null but is null");
    }
    this.op = op;
    this.src = src;
    this.dst = dst;
    this.maskFormat = maskFormat;
    this.glyphset = glyphset;
    this.srcX = srcX;
    this.srcY = srcY;
    this.glyphcmds = glyphcmds;
  }

  @java.lang.SuppressWarnings("all")
  public static CompositeGlyphs32.CompositeGlyphs32Builder builder() {
    return new CompositeGlyphs32.CompositeGlyphs32Builder();
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
  public int getDst() {
    return this.dst;
  }

  @java.lang.SuppressWarnings("all")
  public int getMaskFormat() {
    return this.maskFormat;
  }

  @java.lang.SuppressWarnings("all")
  public int getGlyphset() {
    return this.glyphset;
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
  public byte[] getGlyphcmds() {
    return this.glyphcmds;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CompositeGlyphs32)) return false;
    final CompositeGlyphs32 other = (CompositeGlyphs32) o;
    if (this.getOp() != other.getOp()) return false;
    if (this.getSrc() != other.getSrc()) return false;
    if (this.getDst() != other.getDst()) return false;
    if (this.getMaskFormat() != other.getMaskFormat()) return false;
    if (this.getGlyphset() != other.getGlyphset()) return false;
    if (this.getSrcX() != other.getSrcX()) return false;
    if (this.getSrcY() != other.getSrcY()) return false;
    if (!java.util.Arrays.equals(this.getGlyphcmds(), other.getGlyphcmds())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getOp();
    result = result * PRIME + this.getSrc();
    result = result * PRIME + this.getDst();
    result = result * PRIME + this.getMaskFormat();
    result = result * PRIME + this.getGlyphset();
    result = result * PRIME + this.getSrcX();
    result = result * PRIME + this.getSrcY();
    result = result * PRIME + java.util.Arrays.hashCode(this.getGlyphcmds());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CompositeGlyphs32(op=" + this.getOp() + ", src=" + this.getSrc() + ", dst=" + this.getDst() + ", maskFormat=" + this.getMaskFormat() + ", glyphset=" + this.getGlyphset() + ", srcX=" + this.getSrcX() + ", srcY=" + this.getSrcY() + ", glyphcmds=" + java.util.Arrays.toString(this.getGlyphcmds()) + ")";
  }
}
