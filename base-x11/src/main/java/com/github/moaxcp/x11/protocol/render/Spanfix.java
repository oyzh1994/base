package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class Spanfix implements XStruct {
  public static final String PLUGIN_NAME = "render";
  private final int l;
  private final int r;
  private final int y;

  public static Spanfix readSpanfix(X11Input in) throws IOException {
    Spanfix.SpanfixBuilder javaBuilder = Spanfix.builder();
    int l = in.readInt32();
    int r = in.readInt32();
    int y = in.readInt32();
    javaBuilder.l(l);
    javaBuilder.r(r);
    javaBuilder.y(y);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeInt32(l);
    out.writeInt32(r);
    out.writeInt32(y);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SpanfixBuilder {
    @java.lang.SuppressWarnings("all")
    private int l;
    @java.lang.SuppressWarnings("all")
    private int r;
    @java.lang.SuppressWarnings("all")
    private int y;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    SpanfixBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Spanfix.SpanfixBuilder l(final int l) {
      this.l = l;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Spanfix.SpanfixBuilder r(final int r) {
      this.r = r;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Spanfix.SpanfixBuilder y(final int y) {
      this.y = y;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Spanfix build() {
      return new Spanfix(this.l, this.r, this.y);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Spanfix.SpanfixBuilder(l=" + this.l + ", r=" + this.r + ", y=" + this.y + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Spanfix(final int l, final int r, final int y) {
    this.l = l;
    this.r = r;
    this.y = y;
  }

  @java.lang.SuppressWarnings("all")
  public static Spanfix.SpanfixBuilder builder() {
    return new Spanfix.SpanfixBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getL() {
    return this.l;
  }

  @java.lang.SuppressWarnings("all")
  public int getR() {
    return this.r;
  }

  @java.lang.SuppressWarnings("all")
  public int getY() {
    return this.y;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Spanfix)) return false;
    final Spanfix other = (Spanfix) o;
    if (this.getL() != other.getL()) return false;
    if (this.getR() != other.getR()) return false;
    if (this.getY() != other.getY()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getL();
    result = result * PRIME + this.getR();
    result = result * PRIME + this.getY();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Spanfix(l=" + this.getL() + ", r=" + this.getR() + ", y=" + this.getY() + ")";
  }
}
