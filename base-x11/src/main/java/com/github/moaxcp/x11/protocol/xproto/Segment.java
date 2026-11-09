package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class Segment implements XStruct {
  public static final String PLUGIN_NAME = "xproto";
  private final short x1;
  private final short y1;
  private final short x2;
  private final short y2;

  public static Segment readSegment(X11Input in) throws IOException {
    Segment.SegmentBuilder javaBuilder = Segment.builder();
    short x1 = in.readInt16();
    short y1 = in.readInt16();
    short x2 = in.readInt16();
    short y2 = in.readInt16();
    javaBuilder.x1(x1);
    javaBuilder.y1(y1);
    javaBuilder.x2(x2);
    javaBuilder.y2(y2);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeInt16(x1);
    out.writeInt16(y1);
    out.writeInt16(x2);
    out.writeInt16(y2);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SegmentBuilder {
    @java.lang.SuppressWarnings("all")
    private short x1;
    @java.lang.SuppressWarnings("all")
    private short y1;
    @java.lang.SuppressWarnings("all")
    private short x2;
    @java.lang.SuppressWarnings("all")
    private short y2;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    SegmentBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Segment.SegmentBuilder x1(final short x1) {
      this.x1 = x1;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Segment.SegmentBuilder y1(final short y1) {
      this.y1 = y1;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Segment.SegmentBuilder x2(final short x2) {
      this.x2 = x2;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Segment.SegmentBuilder y2(final short y2) {
      this.y2 = y2;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Segment build() {
      return new Segment(this.x1, this.y1, this.x2, this.y2);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Segment.SegmentBuilder(x1=" + this.x1 + ", y1=" + this.y1 + ", x2=" + this.x2 + ", y2=" + this.y2 + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Segment(final short x1, final short y1, final short x2, final short y2) {
    this.x1 = x1;
    this.y1 = y1;
    this.x2 = x2;
    this.y2 = y2;
  }

  @java.lang.SuppressWarnings("all")
  public static Segment.SegmentBuilder builder() {
    return new Segment.SegmentBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getX1() {
    return this.x1;
  }

  @java.lang.SuppressWarnings("all")
  public short getY1() {
    return this.y1;
  }

  @java.lang.SuppressWarnings("all")
  public short getX2() {
    return this.x2;
  }

  @java.lang.SuppressWarnings("all")
  public short getY2() {
    return this.y2;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Segment)) return false;
    final Segment other = (Segment) o;
    if (this.getX1() != other.getX1()) return false;
    if (this.getY1() != other.getY1()) return false;
    if (this.getX2() != other.getX2()) return false;
    if (this.getY2() != other.getY2()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getX1();
    result = result * PRIME + this.getY1();
    result = result * PRIME + this.getX2();
    result = result * PRIME + this.getY2();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Segment(x1=" + this.getX1() + ", y1=" + this.getY1() + ", x2=" + this.getX2() + ", y2=" + this.getY2() + ")";
  }
}
