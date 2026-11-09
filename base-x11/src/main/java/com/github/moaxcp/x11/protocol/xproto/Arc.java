package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class Arc implements XStruct {
  public static final String PLUGIN_NAME = "xproto";
  private final short x;
  private final short y;
  private final short width;
  private final short height;
  private final short angle1;
  private final short angle2;

  public static Arc readArc(X11Input in) throws IOException {
    Arc.ArcBuilder javaBuilder = Arc.builder();
    short x = in.readInt16();
    short y = in.readInt16();
    short width = in.readCard16();
    short height = in.readCard16();
    short angle1 = in.readInt16();
    short angle2 = in.readInt16();
    javaBuilder.x(x);
    javaBuilder.y(y);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.angle1(angle1);
    javaBuilder.angle2(angle2);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeInt16(x);
    out.writeInt16(y);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeInt16(angle1);
    out.writeInt16(angle2);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ArcBuilder {
    @java.lang.SuppressWarnings("all")
    private short x;
    @java.lang.SuppressWarnings("all")
    private short y;
    @java.lang.SuppressWarnings("all")
    private short width;
    @java.lang.SuppressWarnings("all")
    private short height;
    @java.lang.SuppressWarnings("all")
    private short angle1;
    @java.lang.SuppressWarnings("all")
    private short angle2;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    ArcBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Arc.ArcBuilder x(final short x) {
      this.x = x;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Arc.ArcBuilder y(final short y) {
      this.y = y;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Arc.ArcBuilder width(final short width) {
      this.width = width;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Arc.ArcBuilder height(final short height) {
      this.height = height;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Arc.ArcBuilder angle1(final short angle1) {
      this.angle1 = angle1;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Arc.ArcBuilder angle2(final short angle2) {
      this.angle2 = angle2;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Arc build() {
      return new Arc(this.x, this.y, this.width, this.height, this.angle1, this.angle2);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Arc.ArcBuilder(x=" + this.x + ", y=" + this.y + ", width=" + this.width + ", height=" + this.height + ", angle1=" + this.angle1 + ", angle2=" + this.angle2 + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Arc(final short x, final short y, final short width, final short height, final short angle1, final short angle2) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.angle1 = angle1;
    this.angle2 = angle2;
  }

  @java.lang.SuppressWarnings("all")
  public static Arc.ArcBuilder builder() {
    return new Arc.ArcBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getX() {
    return this.x;
  }

  @java.lang.SuppressWarnings("all")
  public short getY() {
    return this.y;
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
  public short getAngle1() {
    return this.angle1;
  }

  @java.lang.SuppressWarnings("all")
  public short getAngle2() {
    return this.angle2;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Arc)) return false;
    final Arc other = (Arc) o;
    if (this.getX() != other.getX()) return false;
    if (this.getY() != other.getY()) return false;
    if (this.getWidth() != other.getWidth()) return false;
    if (this.getHeight() != other.getHeight()) return false;
    if (this.getAngle1() != other.getAngle1()) return false;
    if (this.getAngle2() != other.getAngle2()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getX();
    result = result * PRIME + this.getY();
    result = result * PRIME + this.getWidth();
    result = result * PRIME + this.getHeight();
    result = result * PRIME + this.getAngle1();
    result = result * PRIME + this.getAngle2();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Arc(x=" + this.getX() + ", y=" + this.getY() + ", width=" + this.getWidth() + ", height=" + this.getHeight() + ", angle1=" + this.getAngle1() + ", angle2=" + this.getAngle2() + ")";
  }
}
