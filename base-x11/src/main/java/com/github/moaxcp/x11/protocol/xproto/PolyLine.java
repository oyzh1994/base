package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public final class PolyLine implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 65;
  private final byte coordinateMode;
  private final int drawable;
  private final int gc;
  private final ImmutableList<Point> points;

  public byte getOpCode() {
    return OPCODE;
  }

  public static PolyLine readPolyLine(X11Input in) throws IOException {
    PolyLine.PolyLineBuilder javaBuilder = PolyLine.builder();
    int javaStart = 1;
    byte coordinateMode = in.readByte();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int drawable = in.readCard32();
    javaStart += 4;
    int gc = in.readCard32();
    javaStart += 4;
    MutableList<Point> points = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(length) - javaStart);
    while (javaStart < Short.toUnsignedInt(length) * 4) {
      Point baseObject = Point.readPoint(in);
      points.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.coordinateMode(coordinateMode);
    javaBuilder.drawable(drawable);
    javaBuilder.gc(gc);
    javaBuilder.points(points.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeByte(coordinateMode);
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeCard32(gc);
    for (Point t : points) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + XObject.sizeOf(points);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class PolyLineBuilder {
    @java.lang.SuppressWarnings("all")
    private byte coordinateMode;
    @java.lang.SuppressWarnings("all")
    private int drawable;
    @java.lang.SuppressWarnings("all")
    private int gc;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<Point> points;

    public PolyLine.PolyLineBuilder coordinateMode(CoordMode coordinateMode) {
      this.coordinateMode = (byte) coordinateMode.getValue();
      return this;
    }

    public PolyLine.PolyLineBuilder coordinateMode(byte coordinateMode) {
      this.coordinateMode = coordinateMode;
      return this;
    }

    public int getSize() {
      return 12 + XObject.sizeOf(points);
    }

    @java.lang.SuppressWarnings("all")
    PolyLineBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PolyLine.PolyLineBuilder drawable(final int drawable) {
      this.drawable = drawable;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PolyLine.PolyLineBuilder gc(final int gc) {
      this.gc = gc;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PolyLine.PolyLineBuilder points(final ImmutableList<Point> points) {
      if (points == null) {
        throw new java.lang.NullPointerException("points is marked non-null but is null");
      }
      this.points = points;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public PolyLine build() {
      return new PolyLine(this.coordinateMode, this.drawable, this.gc, this.points);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "PolyLine.PolyLineBuilder(coordinateMode=" + this.coordinateMode + ", drawable=" + this.drawable + ", gc=" + this.gc + ", points=" + this.points + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  PolyLine(final byte coordinateMode, final int drawable, final int gc, final ImmutableList<Point> points) {
    if (points == null) {
      throw new java.lang.NullPointerException("points is marked non-null but is null");
    }
    this.coordinateMode = coordinateMode;
    this.drawable = drawable;
    this.gc = gc;
    this.points = points;
  }

  @java.lang.SuppressWarnings("all")
  public static PolyLine.PolyLineBuilder builder() {
    return new PolyLine.PolyLineBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getCoordinateMode() {
    return this.coordinateMode;
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
  public ImmutableList<Point> getPoints() {
    return this.points;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof PolyLine)) return false;
    final PolyLine other = (PolyLine) o;
    if (this.getCoordinateMode() != other.getCoordinateMode()) return false;
    if (this.getDrawable() != other.getDrawable()) return false;
    if (this.getGc() != other.getGc()) return false;
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
    result = result * PRIME + this.getCoordinateMode();
    result = result * PRIME + this.getDrawable();
    result = result * PRIME + this.getGc();
    final java.lang.Object $points = this.getPoints();
    result = result * PRIME + ($points == null ? 43 : $points.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "PolyLine(coordinateMode=" + this.getCoordinateMode() + ", drawable=" + this.getDrawable() + ", gc=" + this.getGc() + ", points=" + this.getPoints() + ")";
  }
}
