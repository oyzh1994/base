package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public final class FillPoly implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 69;
  private final int drawable;
  private final int gc;
  private final byte shape;
  private final byte coordinateMode;
  private final ImmutableList<Point> points;

  public byte getOpCode() {
    return OPCODE;
  }

  public static FillPoly readFillPoly(X11Input in) throws IOException {
    FillPoly.FillPolyBuilder javaBuilder = FillPoly.builder();
    int javaStart = 1;
    byte[] pad1 = in.readPad(1);
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int drawable = in.readCard32();
    javaStart += 4;
    int gc = in.readCard32();
    javaStart += 4;
    byte shape = in.readCard8();
    javaStart += 1;
    byte coordinateMode = in.readCard8();
    javaStart += 1;
    byte[] pad7 = in.readPad(2);
    javaStart += 2;
    MutableList<Point> points = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(length) - javaStart);
    while (javaStart < Short.toUnsignedInt(length) * 4) {
      Point baseObject = Point.readPoint(in);
      points.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.drawable(drawable);
    javaBuilder.gc(gc);
    javaBuilder.shape(shape);
    javaBuilder.coordinateMode(coordinateMode);
    javaBuilder.points(points.toImmutable());
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
    out.writeCard8(shape);
    out.writeCard8(coordinateMode);
    out.writePad(2);
    for (Point t : points) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 16 + XObject.sizeOf(points);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class FillPolyBuilder {
    @java.lang.SuppressWarnings("all")
    private int drawable;
    @java.lang.SuppressWarnings("all")
    private int gc;
    @java.lang.SuppressWarnings("all")
    private byte shape;
    @java.lang.SuppressWarnings("all")
    private byte coordinateMode;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<Point> points;

    public FillPoly.FillPolyBuilder shape(PolyShape shape) {
      this.shape = (byte) shape.getValue();
      return this;
    }

    public FillPoly.FillPolyBuilder shape(byte shape) {
      this.shape = shape;
      return this;
    }

    public FillPoly.FillPolyBuilder coordinateMode(CoordMode coordinateMode) {
      this.coordinateMode = (byte) coordinateMode.getValue();
      return this;
    }

    public FillPoly.FillPolyBuilder coordinateMode(byte coordinateMode) {
      this.coordinateMode = coordinateMode;
      return this;
    }

    public int getSize() {
      return 16 + XObject.sizeOf(points);
    }

    @java.lang.SuppressWarnings("all")
    FillPolyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public FillPoly.FillPolyBuilder drawable(final int drawable) {
      this.drawable = drawable;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public FillPoly.FillPolyBuilder gc(final int gc) {
      this.gc = gc;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public FillPoly.FillPolyBuilder points(final ImmutableList<Point> points) {
      if (points == null) {
        throw new java.lang.NullPointerException("points is marked non-null but is null");
      }
      this.points = points;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public FillPoly build() {
      return new FillPoly(this.drawable, this.gc, this.shape, this.coordinateMode, this.points);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "FillPoly.FillPolyBuilder(drawable=" + this.drawable + ", gc=" + this.gc + ", shape=" + this.shape + ", coordinateMode=" + this.coordinateMode + ", points=" + this.points + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  FillPoly(final int drawable, final int gc, final byte shape, final byte coordinateMode, final ImmutableList<Point> points) {
    if (points == null) {
      throw new java.lang.NullPointerException("points is marked non-null but is null");
    }
    this.drawable = drawable;
    this.gc = gc;
    this.shape = shape;
    this.coordinateMode = coordinateMode;
    this.points = points;
  }

  @java.lang.SuppressWarnings("all")
  public static FillPoly.FillPolyBuilder builder() {
    return new FillPoly.FillPolyBuilder();
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
  public byte getShape() {
    return this.shape;
  }

  @java.lang.SuppressWarnings("all")
  public byte getCoordinateMode() {
    return this.coordinateMode;
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<Point> getPoints() {
    return this.points;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof FillPoly)) return false;
    final FillPoly other = (FillPoly) o;
    if (this.getDrawable() != other.getDrawable()) return false;
    if (this.getGc() != other.getGc()) return false;
    if (this.getShape() != other.getShape()) return false;
    if (this.getCoordinateMode() != other.getCoordinateMode()) return false;
    final java.lang.Object this$points = this.getPoints();
    final java.lang.Object other$points = other.getPoints();
    if (this$points == null ? other$points != null : !this$points.equals(other$points)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDrawable();
    result = result * PRIME + this.getGc();
    result = result * PRIME + this.getShape();
    result = result * PRIME + this.getCoordinateMode();
    final java.lang.Object $points = this.getPoints();
    result = result * PRIME + ($points == null ? 43 : $points.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "FillPoly(drawable=" + this.getDrawable() + ", gc=" + this.getGc() + ", shape=" + this.getShape() + ", coordinateMode=" + this.getCoordinateMode() + ", points=" + this.getPoints() + ")";
  }
}
