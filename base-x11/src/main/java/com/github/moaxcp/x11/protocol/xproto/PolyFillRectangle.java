package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public final class PolyFillRectangle implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 70;
  private final int drawable;
  private final int gc;
  private final ImmutableList<Rectangle> rectangles;

  public byte getOpCode() {
    return OPCODE;
  }

  public static PolyFillRectangle readPolyFillRectangle(X11Input in) throws IOException {
    PolyFillRectangle.PolyFillRectangleBuilder javaBuilder = PolyFillRectangle.builder();
    int javaStart = 1;
    byte[] pad1 = in.readPad(1);
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int drawable = in.readCard32();
    javaStart += 4;
    int gc = in.readCard32();
    javaStart += 4;
    MutableList<Rectangle> rectangles = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(length) - javaStart);
    while (javaStart < Short.toUnsignedInt(length) * 4) {
      Rectangle baseObject = Rectangle.readRectangle(in);
      rectangles.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.drawable(drawable);
    javaBuilder.gc(gc);
    javaBuilder.rectangles(rectangles.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeCard32(gc);
    for (Rectangle t : rectangles) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + XObject.sizeOf(rectangles);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class PolyFillRectangleBuilder {
    @java.lang.SuppressWarnings("all")
    private int drawable;
    @java.lang.SuppressWarnings("all")
    private int gc;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<Rectangle> rectangles;

    public int getSize() {
      return 12 + XObject.sizeOf(rectangles);
    }

    @java.lang.SuppressWarnings("all")
    PolyFillRectangleBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PolyFillRectangle.PolyFillRectangleBuilder drawable(final int drawable) {
      this.drawable = drawable;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PolyFillRectangle.PolyFillRectangleBuilder gc(final int gc) {
      this.gc = gc;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PolyFillRectangle.PolyFillRectangleBuilder rectangles(final ImmutableList<Rectangle> rectangles) {
      if (rectangles == null) {
        throw new java.lang.NullPointerException("rectangles is marked non-null but is null");
      }
      this.rectangles = rectangles;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public PolyFillRectangle build() {
      return new PolyFillRectangle(this.drawable, this.gc, this.rectangles);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "PolyFillRectangle.PolyFillRectangleBuilder(drawable=" + this.drawable + ", gc=" + this.gc + ", rectangles=" + this.rectangles + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  PolyFillRectangle(final int drawable, final int gc, final ImmutableList<Rectangle> rectangles) {
    if (rectangles == null) {
      throw new java.lang.NullPointerException("rectangles is marked non-null but is null");
    }
    this.drawable = drawable;
    this.gc = gc;
    this.rectangles = rectangles;
  }

  @java.lang.SuppressWarnings("all")
  public static PolyFillRectangle.PolyFillRectangleBuilder builder() {
    return new PolyFillRectangle.PolyFillRectangleBuilder();
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
  public ImmutableList<Rectangle> getRectangles() {
    return this.rectangles;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof PolyFillRectangle)) return false;
    final PolyFillRectangle other = (PolyFillRectangle) o;
    if (this.getDrawable() != other.getDrawable()) return false;
    if (this.getGc() != other.getGc()) return false;
    final java.lang.Object this$rectangles = this.getRectangles();
    final java.lang.Object other$rectangles = other.getRectangles();
    if (this$rectangles == null ? other$rectangles != null : !this$rectangles.equals(other$rectangles)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDrawable();
    result = result * PRIME + this.getGc();
    final java.lang.Object $rectangles = this.getRectangles();
    result = result * PRIME + ($rectangles == null ? 43 : $rectangles.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "PolyFillRectangle(drawable=" + this.getDrawable() + ", gc=" + this.getGc() + ", rectangles=" + this.getRectangles() + ")";
  }
}
