package com.github.moaxcp.x11.protocol.xkb;

import java.util.ArrayList;
import java.util.List;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XStruct;
import com.github.moaxcp.x11.protocol.xproto.Point;
import java.io.IOException;

public final class Outline implements XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final byte cornerRadius;
  private final List<Point> points;

  public static Outline readOutline(X11Input in) throws IOException {
    Outline.OutlineBuilder javaBuilder = Outline.builder();
    byte nPoints = in.readCard8();
    byte cornerRadius = in.readCard8();
    byte[] pad2 = in.readPad(2);
    List<Point> points = new ArrayList<>(Byte.toUnsignedInt(nPoints));
    for (int i = 0; i < Byte.toUnsignedInt(nPoints); i++) {
      points.add(Point.readPoint(in));
    }
    javaBuilder.cornerRadius(cornerRadius);
    javaBuilder.points(List.copyOf(points));
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    byte nPoints = (byte) points.size();
    out.writeCard8(nPoints);
    out.writeCard8(cornerRadius);
    out.writePad(2);
    for (Point t : points) {
      t.write(out);
    }
  }

  @Override
  public int getSize() {
    return 4 + XObject.sizeOf(points);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class OutlineBuilder {
    @java.lang.SuppressWarnings("all")
    private byte cornerRadius;
    @java.lang.SuppressWarnings("all")
    private List<Point> points;

    public int getSize() {
      return 4 + XObject.sizeOf(points);
    }

    @java.lang.SuppressWarnings("all")
    OutlineBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Outline.OutlineBuilder cornerRadius(final byte cornerRadius) {
      this.cornerRadius = cornerRadius;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Outline.OutlineBuilder points(final List<Point> points) {
      if (points == null) {
        throw new java.lang.NullPointerException("points is marked non-null but is null");
      }
      this.points = points;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Outline build() {
      return new Outline(this.cornerRadius, this.points);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Outline.OutlineBuilder(cornerRadius=" + this.cornerRadius + ", points=" + this.points + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Outline(final byte cornerRadius, final List<Point> points) {
    if (points == null) {
      throw new java.lang.NullPointerException("points is marked non-null but is null");
    }
    this.cornerRadius = cornerRadius;
    this.points = points;
  }

  @java.lang.SuppressWarnings("all")
  public static Outline.OutlineBuilder builder() {
    return new Outline.OutlineBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getCornerRadius() {
    return this.cornerRadius;
  }

  @java.lang.SuppressWarnings("all")
  public List<Point> getPoints() {
    return this.points;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Outline)) return false;
    final Outline other = (Outline) o;
    if (this.getCornerRadius() != other.getCornerRadius()) return false;
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
    result = result * PRIME + this.getCornerRadius();
    final java.lang.Object $points = this.getPoints();
    result = result * PRIME + ($points == null ? 43 : $points.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Outline(cornerRadius=" + this.getCornerRadius() + ", points=" + this.getPoints() + ")";
  }
}
