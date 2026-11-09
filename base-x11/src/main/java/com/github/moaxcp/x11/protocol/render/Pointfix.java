package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class Pointfix implements XStruct {
  public static final String PLUGIN_NAME = "render";
  private final int x;
  private final int y;

  public static Pointfix readPointfix(X11Input in) throws IOException {
    Pointfix.PointfixBuilder javaBuilder = Pointfix.builder();
    int x = in.readInt32();
    int y = in.readInt32();
    javaBuilder.x(x);
    javaBuilder.y(y);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeInt32(x);
    out.writeInt32(y);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class PointfixBuilder {
    @java.lang.SuppressWarnings("all")
    private int x;
    @java.lang.SuppressWarnings("all")
    private int y;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    PointfixBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Pointfix.PointfixBuilder x(final int x) {
      this.x = x;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Pointfix.PointfixBuilder y(final int y) {
      this.y = y;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Pointfix build() {
      return new Pointfix(this.x, this.y);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Pointfix.PointfixBuilder(x=" + this.x + ", y=" + this.y + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Pointfix(final int x, final int y) {
    this.x = x;
    this.y = y;
  }

  @java.lang.SuppressWarnings("all")
  public static Pointfix.PointfixBuilder builder() {
    return new Pointfix.PointfixBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getX() {
    return this.x;
  }

  @java.lang.SuppressWarnings("all")
  public int getY() {
    return this.y;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Pointfix)) return false;
    final Pointfix other = (Pointfix) o;
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
    return "Pointfix(x=" + this.getX() + ", y=" + this.getY() + ")";
  }
}
