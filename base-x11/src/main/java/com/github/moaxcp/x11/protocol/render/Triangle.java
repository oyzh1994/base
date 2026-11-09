package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class Triangle implements XStruct {
  public static final String PLUGIN_NAME = "render";
  private final Pointfix p1;
  private final Pointfix p2;
  private final Pointfix p3;

  public static Triangle readTriangle(X11Input in) throws IOException {
    Triangle.TriangleBuilder javaBuilder = Triangle.builder();
    Pointfix p1 = Pointfix.readPointfix(in);
    Pointfix p2 = Pointfix.readPointfix(in);
    Pointfix p3 = Pointfix.readPointfix(in);
    javaBuilder.p1(p1);
    javaBuilder.p2(p2);
    javaBuilder.p3(p3);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    p1.write(out);
    p2.write(out);
    p3.write(out);
  }

  @Override
  public int getSize() {
    return 24;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class TriangleBuilder {
    @java.lang.SuppressWarnings("all")
    private Pointfix p1;
    @java.lang.SuppressWarnings("all")
    private Pointfix p2;
    @java.lang.SuppressWarnings("all")
    private Pointfix p3;

    public int getSize() {
      return 24;
    }

    @java.lang.SuppressWarnings("all")
    TriangleBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Triangle.TriangleBuilder p1(final Pointfix p1) {
      if (p1 == null) {
        throw new java.lang.NullPointerException("p1 is marked non-null but is null");
      }
      this.p1 = p1;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Triangle.TriangleBuilder p2(final Pointfix p2) {
      if (p2 == null) {
        throw new java.lang.NullPointerException("p2 is marked non-null but is null");
      }
      this.p2 = p2;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Triangle.TriangleBuilder p3(final Pointfix p3) {
      if (p3 == null) {
        throw new java.lang.NullPointerException("p3 is marked non-null but is null");
      }
      this.p3 = p3;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Triangle build() {
      return new Triangle(this.p1, this.p2, this.p3);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Triangle.TriangleBuilder(p1=" + this.p1 + ", p2=" + this.p2 + ", p3=" + this.p3 + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Triangle(final Pointfix p1, final Pointfix p2, final Pointfix p3) {
    if (p1 == null) {
      throw new java.lang.NullPointerException("p1 is marked non-null but is null");
    }
    if (p2 == null) {
      throw new java.lang.NullPointerException("p2 is marked non-null but is null");
    }
    if (p3 == null) {
      throw new java.lang.NullPointerException("p3 is marked non-null but is null");
    }
    this.p1 = p1;
    this.p2 = p2;
    this.p3 = p3;
  }

  @java.lang.SuppressWarnings("all")
  public static Triangle.TriangleBuilder builder() {
    return new Triangle.TriangleBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public Pointfix getP1() {
    return this.p1;
  }

  @java.lang.SuppressWarnings("all")
  public Pointfix getP2() {
    return this.p2;
  }

  @java.lang.SuppressWarnings("all")
  public Pointfix getP3() {
    return this.p3;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Triangle)) return false;
    final Triangle other = (Triangle) o;
    final java.lang.Object this$p1 = this.getP1();
    final java.lang.Object other$p1 = other.getP1();
    if (this$p1 == null ? other$p1 != null : !this$p1.equals(other$p1)) return false;
    final java.lang.Object this$p2 = this.getP2();
    final java.lang.Object other$p2 = other.getP2();
    if (this$p2 == null ? other$p2 != null : !this$p2.equals(other$p2)) return false;
    final java.lang.Object this$p3 = this.getP3();
    final java.lang.Object other$p3 = other.getP3();
    if (this$p3 == null ? other$p3 != null : !this$p3.equals(other$p3)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final java.lang.Object $p1 = this.getP1();
    result = result * PRIME + ($p1 == null ? 43 : $p1.hashCode());
    final java.lang.Object $p2 = this.getP2();
    result = result * PRIME + ($p2 == null ? 43 : $p2.hashCode());
    final java.lang.Object $p3 = this.getP3();
    result = result * PRIME + ($p3 == null ? 43 : $p3.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Triangle(p1=" + this.getP1() + ", p2=" + this.getP2() + ", p3=" + this.getP3() + ")";
  }
}
