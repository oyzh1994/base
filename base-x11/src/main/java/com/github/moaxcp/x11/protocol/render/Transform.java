package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class Transform implements XStruct {
  public static final String PLUGIN_NAME = "render";
  private final int matrix11;
  private final int matrix12;
  private final int matrix13;
  private final int matrix21;
  private final int matrix22;
  private final int matrix23;
  private final int matrix31;
  private final int matrix32;
  private final int matrix33;

  public static Transform readTransform(X11Input in) throws IOException {
    Transform.TransformBuilder javaBuilder = Transform.builder();
    int matrix11 = in.readInt32();
    int matrix12 = in.readInt32();
    int matrix13 = in.readInt32();
    int matrix21 = in.readInt32();
    int matrix22 = in.readInt32();
    int matrix23 = in.readInt32();
    int matrix31 = in.readInt32();
    int matrix32 = in.readInt32();
    int matrix33 = in.readInt32();
    javaBuilder.matrix11(matrix11);
    javaBuilder.matrix12(matrix12);
    javaBuilder.matrix13(matrix13);
    javaBuilder.matrix21(matrix21);
    javaBuilder.matrix22(matrix22);
    javaBuilder.matrix23(matrix23);
    javaBuilder.matrix31(matrix31);
    javaBuilder.matrix32(matrix32);
    javaBuilder.matrix33(matrix33);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeInt32(matrix11);
    out.writeInt32(matrix12);
    out.writeInt32(matrix13);
    out.writeInt32(matrix21);
    out.writeInt32(matrix22);
    out.writeInt32(matrix23);
    out.writeInt32(matrix31);
    out.writeInt32(matrix32);
    out.writeInt32(matrix33);
  }

  @Override
  public int getSize() {
    return 36;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class TransformBuilder {
    @java.lang.SuppressWarnings("all")
    private int matrix11;
    @java.lang.SuppressWarnings("all")
    private int matrix12;
    @java.lang.SuppressWarnings("all")
    private int matrix13;
    @java.lang.SuppressWarnings("all")
    private int matrix21;
    @java.lang.SuppressWarnings("all")
    private int matrix22;
    @java.lang.SuppressWarnings("all")
    private int matrix23;
    @java.lang.SuppressWarnings("all")
    private int matrix31;
    @java.lang.SuppressWarnings("all")
    private int matrix32;
    @java.lang.SuppressWarnings("all")
    private int matrix33;

    public int getSize() {
      return 36;
    }

    @java.lang.SuppressWarnings("all")
    TransformBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Transform.TransformBuilder matrix11(final int matrix11) {
      this.matrix11 = matrix11;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Transform.TransformBuilder matrix12(final int matrix12) {
      this.matrix12 = matrix12;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Transform.TransformBuilder matrix13(final int matrix13) {
      this.matrix13 = matrix13;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Transform.TransformBuilder matrix21(final int matrix21) {
      this.matrix21 = matrix21;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Transform.TransformBuilder matrix22(final int matrix22) {
      this.matrix22 = matrix22;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Transform.TransformBuilder matrix23(final int matrix23) {
      this.matrix23 = matrix23;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Transform.TransformBuilder matrix31(final int matrix31) {
      this.matrix31 = matrix31;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Transform.TransformBuilder matrix32(final int matrix32) {
      this.matrix32 = matrix32;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Transform.TransformBuilder matrix33(final int matrix33) {
      this.matrix33 = matrix33;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Transform build() {
      return new Transform(this.matrix11, this.matrix12, this.matrix13, this.matrix21, this.matrix22, this.matrix23, this.matrix31, this.matrix32, this.matrix33);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Transform.TransformBuilder(matrix11=" + this.matrix11 + ", matrix12=" + this.matrix12 + ", matrix13=" + this.matrix13 + ", matrix21=" + this.matrix21 + ", matrix22=" + this.matrix22 + ", matrix23=" + this.matrix23 + ", matrix31=" + this.matrix31 + ", matrix32=" + this.matrix32 + ", matrix33=" + this.matrix33 + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Transform(final int matrix11, final int matrix12, final int matrix13, final int matrix21, final int matrix22, final int matrix23, final int matrix31, final int matrix32, final int matrix33) {
    this.matrix11 = matrix11;
    this.matrix12 = matrix12;
    this.matrix13 = matrix13;
    this.matrix21 = matrix21;
    this.matrix22 = matrix22;
    this.matrix23 = matrix23;
    this.matrix31 = matrix31;
    this.matrix32 = matrix32;
    this.matrix33 = matrix33;
  }

  @java.lang.SuppressWarnings("all")
  public static Transform.TransformBuilder builder() {
    return new Transform.TransformBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getMatrix11() {
    return this.matrix11;
  }

  @java.lang.SuppressWarnings("all")
  public int getMatrix12() {
    return this.matrix12;
  }

  @java.lang.SuppressWarnings("all")
  public int getMatrix13() {
    return this.matrix13;
  }

  @java.lang.SuppressWarnings("all")
  public int getMatrix21() {
    return this.matrix21;
  }

  @java.lang.SuppressWarnings("all")
  public int getMatrix22() {
    return this.matrix22;
  }

  @java.lang.SuppressWarnings("all")
  public int getMatrix23() {
    return this.matrix23;
  }

  @java.lang.SuppressWarnings("all")
  public int getMatrix31() {
    return this.matrix31;
  }

  @java.lang.SuppressWarnings("all")
  public int getMatrix32() {
    return this.matrix32;
  }

  @java.lang.SuppressWarnings("all")
  public int getMatrix33() {
    return this.matrix33;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Transform)) return false;
    final Transform other = (Transform) o;
    if (this.getMatrix11() != other.getMatrix11()) return false;
    if (this.getMatrix12() != other.getMatrix12()) return false;
    if (this.getMatrix13() != other.getMatrix13()) return false;
    if (this.getMatrix21() != other.getMatrix21()) return false;
    if (this.getMatrix22() != other.getMatrix22()) return false;
    if (this.getMatrix23() != other.getMatrix23()) return false;
    if (this.getMatrix31() != other.getMatrix31()) return false;
    if (this.getMatrix32() != other.getMatrix32()) return false;
    if (this.getMatrix33() != other.getMatrix33()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getMatrix11();
    result = result * PRIME + this.getMatrix12();
    result = result * PRIME + this.getMatrix13();
    result = result * PRIME + this.getMatrix21();
    result = result * PRIME + this.getMatrix22();
    result = result * PRIME + this.getMatrix23();
    result = result * PRIME + this.getMatrix31();
    result = result * PRIME + this.getMatrix32();
    result = result * PRIME + this.getMatrix33();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Transform(matrix11=" + this.getMatrix11() + ", matrix12=" + this.getMatrix12() + ", matrix13=" + this.getMatrix13() + ", matrix21=" + this.getMatrix21() + ", matrix22=" + this.getMatrix22() + ", matrix23=" + this.getMatrix23() + ", matrix31=" + this.getMatrix31() + ", matrix32=" + this.getMatrix32() + ", matrix33=" + this.getMatrix33() + ")";
  }
}
