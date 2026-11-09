package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class Point implements XStruct {
  public static final String PLUGIN_NAME = "xproto";
  private final short x;
  private final short y;

  public static Point readPoint(X11Input in) throws IOException {
    Point.PointBuilder javaBuilder = Point.builder();
    short x = in.readInt16();
    short y = in.readInt16();
    javaBuilder.x(x);
    javaBuilder.y(y);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeInt16(x);
    out.writeInt16(y);
  }

  @Override
  public int getSize() {
    return 4;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class PointBuilder {
    @java.lang.SuppressWarnings("all")
    private short x;
    @java.lang.SuppressWarnings("all")
    private short y;

    public int getSize() {
      return 4;
    }

    @java.lang.SuppressWarnings("all")
    PointBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Point.PointBuilder x(final short x) {
      this.x = x;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Point.PointBuilder y(final short y) {
      this.y = y;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Point build() {
      return new Point(this.x, this.y);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Point.PointBuilder(x=" + this.x + ", y=" + this.y + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Point(final short x, final short y) {
    this.x = x;
    this.y = y;
  }

  @java.lang.SuppressWarnings("all")
  public static Point.PointBuilder builder() {
    return new Point.PointBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getX() {
    return this.x;
  }

  @java.lang.SuppressWarnings("all")
  public short getY() {
    return this.y;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Point)) return false;
    final Point other = (Point) o;
    if (this.getX() != other.getX()) return false;
    if (this.getY() != other.getY()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getX();
    result = result * PRIME + this.getY();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Point(x=" + this.getX() + ", y=" + this.getY() + ")";
  }
}
