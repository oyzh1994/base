package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.xproto.Rectangle;
import java.io.IOException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public final class FillRectangles implements OneWayRequest {
  public static final String PLUGIN_NAME = "render";
  public static final byte OPCODE = 26;
  private final byte op;
  private final int dst;
  private final Color color;
  private final ImmutableList<Rectangle> rects;

  public byte getOpCode() {
    return OPCODE;
  }

  public static FillRectangles readFillRectangles(X11Input in) throws IOException {
    FillRectangles.FillRectanglesBuilder javaBuilder = FillRectangles.builder();
    int javaStart = 1;
    byte majorOpcode = in.readCard8();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    byte op = in.readCard8();
    javaStart += 1;
    byte[] pad4 = in.readPad(3);
    javaStart += 3;
    int dst = in.readCard32();
    javaStart += 4;
    Color color = Color.readColor(in);
    javaStart += color.getSize();
    MutableList<Rectangle> rects = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(length) - javaStart);
    while (javaStart < Short.toUnsignedInt(length) * 4) {
      Rectangle baseObject = Rectangle.readRectangle(in);
      rects.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.op(op);
    javaBuilder.dst(dst);
    javaBuilder.color(color);
    javaBuilder.rects(rects.toImmutable());
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
    out.writeCard32(dst);
    color.write(out);
    for (Rectangle t : rects) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 20 + XObject.sizeOf(rects);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class FillRectanglesBuilder {
    @java.lang.SuppressWarnings("all")
    private byte op;
    @java.lang.SuppressWarnings("all")
    private int dst;
    @java.lang.SuppressWarnings("all")
    private Color color;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<Rectangle> rects;

    public FillRectangles.FillRectanglesBuilder op(PictOp op) {
      this.op = (byte) op.getValue();
      return this;
    }

    public FillRectangles.FillRectanglesBuilder op(byte op) {
      this.op = op;
      return this;
    }

    public int getSize() {
      return 20 + XObject.sizeOf(rects);
    }

    @java.lang.SuppressWarnings("all")
    FillRectanglesBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public FillRectangles.FillRectanglesBuilder dst(final int dst) {
      this.dst = dst;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public FillRectangles.FillRectanglesBuilder color(final Color color) {
      if (color == null) {
        throw new java.lang.NullPointerException("color is marked non-null but is null");
      }
      this.color = color;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public FillRectangles.FillRectanglesBuilder rects(final ImmutableList<Rectangle> rects) {
      if (rects == null) {
        throw new java.lang.NullPointerException("rects is marked non-null but is null");
      }
      this.rects = rects;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public FillRectangles build() {
      return new FillRectangles(this.op, this.dst, this.color, this.rects);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "FillRectangles.FillRectanglesBuilder(op=" + this.op + ", dst=" + this.dst + ", color=" + this.color + ", rects=" + this.rects + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  FillRectangles(final byte op, final int dst, final Color color, final ImmutableList<Rectangle> rects) {
    if (color == null) {
      throw new java.lang.NullPointerException("color is marked non-null but is null");
    }
    if (rects == null) {
      throw new java.lang.NullPointerException("rects is marked non-null but is null");
    }
    this.op = op;
    this.dst = dst;
    this.color = color;
    this.rects = rects;
  }

  @java.lang.SuppressWarnings("all")
  public static FillRectangles.FillRectanglesBuilder builder() {
    return new FillRectangles.FillRectanglesBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getOp() {
    return this.op;
  }

  @java.lang.SuppressWarnings("all")
  public int getDst() {
    return this.dst;
  }

  @java.lang.SuppressWarnings("all")
  public Color getColor() {
    return this.color;
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<Rectangle> getRects() {
    return this.rects;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof FillRectangles)) return false;
    final FillRectangles other = (FillRectangles) o;
    if (this.getOp() != other.getOp()) return false;
    if (this.getDst() != other.getDst()) return false;
    final java.lang.Object this$color = this.getColor();
    final java.lang.Object other$color = other.getColor();
    if (this$color == null ? other$color != null : !this$color.equals(other$color)) return false;
    final java.lang.Object this$rects = this.getRects();
    final java.lang.Object other$rects = other.getRects();
    if (this$rects == null ? other$rects != null : !this$rects.equals(other$rects)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getOp();
    result = result * PRIME + this.getDst();
    final java.lang.Object $color = this.getColor();
    result = result * PRIME + ($color == null ? 43 : $color.hashCode());
    final java.lang.Object $rects = this.getRects();
    result = result * PRIME + ($rects == null ? 43 : $rects.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "FillRectangles(op=" + this.getOp() + ", dst=" + this.getDst() + ", color=" + this.getColor() + ", rects=" + this.getRects() + ")";
  }
}
