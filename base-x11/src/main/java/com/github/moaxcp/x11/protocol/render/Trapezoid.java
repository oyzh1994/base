package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class Trapezoid implements XStruct {
  public static final String PLUGIN_NAME = "render";
  private final int top;
  private final int bottom;
  private final Linefix left;
  private final Linefix right;

  public static Trapezoid readTrapezoid(X11Input in) throws IOException {
    Trapezoid.TrapezoidBuilder javaBuilder = Trapezoid.builder();
    int top = in.readInt32();
    int bottom = in.readInt32();
    Linefix left = Linefix.readLinefix(in);
    Linefix right = Linefix.readLinefix(in);
    javaBuilder.top(top);
    javaBuilder.bottom(bottom);
    javaBuilder.left(left);
    javaBuilder.right(right);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeInt32(top);
    out.writeInt32(bottom);
    left.write(out);
    right.write(out);
  }

  @Override
  public int getSize() {
    return 40;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class TrapezoidBuilder {
    @java.lang.SuppressWarnings("all")
    private int top;
    @java.lang.SuppressWarnings("all")
    private int bottom;
    @java.lang.SuppressWarnings("all")
    private Linefix left;
    @java.lang.SuppressWarnings("all")
    private Linefix right;

    public int getSize() {
      return 40;
    }

    @java.lang.SuppressWarnings("all")
    TrapezoidBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Trapezoid.TrapezoidBuilder top(final int top) {
      this.top = top;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Trapezoid.TrapezoidBuilder bottom(final int bottom) {
      this.bottom = bottom;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Trapezoid.TrapezoidBuilder left(final Linefix left) {
      if (left == null) {
        throw new java.lang.NullPointerException("left is marked non-null but is null");
      }
      this.left = left;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Trapezoid.TrapezoidBuilder right(final Linefix right) {
      if (right == null) {
        throw new java.lang.NullPointerException("right is marked non-null but is null");
      }
      this.right = right;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Trapezoid build() {
      return new Trapezoid(this.top, this.bottom, this.left, this.right);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Trapezoid.TrapezoidBuilder(top=" + this.top + ", bottom=" + this.bottom + ", left=" + this.left + ", right=" + this.right + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Trapezoid(final int top, final int bottom, final Linefix left, final Linefix right) {
    if (left == null) {
      throw new java.lang.NullPointerException("left is marked non-null but is null");
    }
    if (right == null) {
      throw new java.lang.NullPointerException("right is marked non-null but is null");
    }
    this.top = top;
    this.bottom = bottom;
    this.left = left;
    this.right = right;
  }

  @java.lang.SuppressWarnings("all")
  public static Trapezoid.TrapezoidBuilder builder() {
    return new Trapezoid.TrapezoidBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getTop() {
    return this.top;
  }

  @java.lang.SuppressWarnings("all")
  public int getBottom() {
    return this.bottom;
  }

  @java.lang.SuppressWarnings("all")
  public Linefix getLeft() {
    return this.left;
  }

  @java.lang.SuppressWarnings("all")
  public Linefix getRight() {
    return this.right;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Trapezoid)) return false;
    final Trapezoid other = (Trapezoid) o;
    if (this.getTop() != other.getTop()) return false;
    if (this.getBottom() != other.getBottom()) return false;
    final java.lang.Object this$left = this.getLeft();
    final java.lang.Object other$left = other.getLeft();
    if (this$left == null ? other$left != null : !this$left.equals(other$left)) return false;
    final java.lang.Object this$right = this.getRight();
    final java.lang.Object other$right = other.getRight();
    if (this$right == null ? other$right != null : !this$right.equals(other$right)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getTop();
    result = result * PRIME + this.getBottom();
    final java.lang.Object $left = this.getLeft();
    result = result * PRIME + ($left == null ? 43 : $left.hashCode());
    final java.lang.Object $right = this.getRight();
    result = result * PRIME + ($right == null ? 43 : $right.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Trapezoid(top=" + this.getTop() + ", bottom=" + this.getBottom() + ", left=" + this.getLeft() + ", right=" + this.getRight() + ")";
  }
}
