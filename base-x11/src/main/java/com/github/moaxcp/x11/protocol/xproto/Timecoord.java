package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class Timecoord implements XStruct {
  public static final String PLUGIN_NAME = "xproto";
  private final int time;
  private final short x;
  private final short y;

  public static Timecoord readTimecoord(X11Input in) throws IOException {
    Timecoord.TimecoordBuilder javaBuilder = Timecoord.builder();
    int time = in.readCard32();
    short x = in.readInt16();
    short y = in.readInt16();
    javaBuilder.time(time);
    javaBuilder.x(x);
    javaBuilder.y(y);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(time);
    out.writeInt16(x);
    out.writeInt16(y);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class TimecoordBuilder {
    @java.lang.SuppressWarnings("all")
    private int time;
    @java.lang.SuppressWarnings("all")
    private short x;
    @java.lang.SuppressWarnings("all")
    private short y;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    TimecoordBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Timecoord.TimecoordBuilder time(final int time) {
      this.time = time;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Timecoord.TimecoordBuilder x(final short x) {
      this.x = x;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Timecoord.TimecoordBuilder y(final short y) {
      this.y = y;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Timecoord build() {
      return new Timecoord(this.time, this.x, this.y);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Timecoord.TimecoordBuilder(time=" + this.time + ", x=" + this.x + ", y=" + this.y + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Timecoord(final int time, final short x, final short y) {
    this.time = time;
    this.x = x;
    this.y = y;
  }

  @java.lang.SuppressWarnings("all")
  public static Timecoord.TimecoordBuilder builder() {
    return new Timecoord.TimecoordBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getTime() {
    return this.time;
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
    if (!(o instanceof Timecoord)) return false;
    final Timecoord other = (Timecoord) o;
    if (this.getTime() != other.getTime()) return false;
    if (this.getX() != other.getX()) return false;
    if (this.getY() != other.getY()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getTime();
    result = result * PRIME + this.getX();
    result = result * PRIME + this.getY();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Timecoord(time=" + this.getTime() + ", x=" + this.getX() + ", y=" + this.getY() + ")";
  }
}
