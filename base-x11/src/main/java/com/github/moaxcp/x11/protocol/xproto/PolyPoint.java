package com.github.moaxcp.x11.protocol.xproto;

import java.util.ArrayList;
import java.util.List;
import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;

public final class PolyPoint implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 64;
  private final byte coordinateMode;
  private final int drawable;
  private final int gc;
  private final List<Point> points;

  public byte getOpCode() {
    return OPCODE;
  }

  public static PolyPoint readPolyPoint(X11Input in) throws IOException {
    PolyPoint.PolyPointBuilder javaBuilder = PolyPoint.builder();
    int javaStart = 1;
    byte coordinateMode = in.readByte();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int drawable = in.readCard32();
    javaStart += 4;
    int gc = in.readCard32();
    javaStart += 4;
    List<Point> points = new ArrayList<>(Short.toUnsignedInt(length) - javaStart);
    while (javaStart < Short.toUnsignedInt(length) * 4) {
      Point baseObject = Point.readPoint(in);
      points.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.coordinateMode(coordinateMode);
    javaBuilder.drawable(drawable);
    javaBuilder.gc(gc);
    javaBuilder.points(List.copyOf(points));
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


  public static class PolyPointBuilder {
    @java.lang.SuppressWarnings("all")
    private byte coordinateMode;
    @java.lang.SuppressWarnings("all")
    private int drawable;
    @java.lang.SuppressWarnings("all")
    private int gc;
    @java.lang.SuppressWarnings("all")
    private List<Point> points;

    public PolyPoint.PolyPointBuilder coordinateMode(CoordMode coordinateMode) {
      this.coordinateMode = (byte) coordinateMode.getValue();
      return this;
    }

    public PolyPoint.PolyPointBuilder coordinateMode(byte coordinateMode) {
      this.coordinateMode = coordinateMode;
      return this;
    }

    public int getSize() {
      return 12 + XObject.sizeOf(points);
    }

    @java.lang.SuppressWarnings("all")
    PolyPointBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PolyPoint.PolyPointBuilder drawable(final int drawable) {
      this.drawable = drawable;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PolyPoint.PolyPointBuilder gc(final int gc) {
      this.gc = gc;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PolyPoint.PolyPointBuilder points(final List<Point> points) {
      if (points == null) {
        throw new java.lang.NullPointerException("points is marked non-null but is null");
      }
      this.points = points;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public PolyPoint build() {
      return new PolyPoint(this.coordinateMode, this.drawable, this.gc, this.points);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "PolyPoint.PolyPointBuilder(coordinateMode=" + this.coordinateMode + ", drawable=" + this.drawable + ", gc=" + this.gc + ", points=" + this.points + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  PolyPoint(final byte coordinateMode, final int drawable, final int gc, final List<Point> points) {
    if (points == null) {
      throw new java.lang.NullPointerException("points is marked non-null but is null");
    }
    this.coordinateMode = coordinateMode;
    this.drawable = drawable;
    this.gc = gc;
    this.points = points;
  }

  @java.lang.SuppressWarnings("all")
  public static PolyPoint.PolyPointBuilder builder() {
    return new PolyPoint.PolyPointBuilder();
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
  public List<Point> getPoints() {
    return this.points;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof PolyPoint)) return false;
    final PolyPoint other = (PolyPoint) o;
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
    return "PolyPoint(coordinateMode=" + this.getCoordinateMode() + ", drawable=" + this.getDrawable() + ", gc=" + this.getGc() + ", points=" + this.getPoints() + ")";
  }
}
