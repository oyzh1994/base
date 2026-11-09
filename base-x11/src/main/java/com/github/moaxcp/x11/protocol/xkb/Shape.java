package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public final class Shape implements XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final int name;
  private final byte primaryNdx;
  private final byte approxNdx;
  private final ImmutableList<Outline> outlines;

  public static Shape readShape(X11Input in) throws IOException {
    Shape.ShapeBuilder javaBuilder = Shape.builder();
    int name = in.readCard32();
    byte nOutlines = in.readCard8();
    byte primaryNdx = in.readCard8();
    byte approxNdx = in.readCard8();
    byte[] pad4 = in.readPad(1);
    MutableList<Outline> outlines = Lists.mutable.withInitialCapacity(Byte.toUnsignedInt(nOutlines));
    for (int i = 0; i < Byte.toUnsignedInt(nOutlines); i++) {
      outlines.add(Outline.readOutline(in));
    }
    javaBuilder.name(name);
    javaBuilder.primaryNdx(primaryNdx);
    javaBuilder.approxNdx(approxNdx);
    javaBuilder.outlines(outlines.toImmutable());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(name);
    byte nOutlines = (byte) outlines.size();
    out.writeCard8(nOutlines);
    out.writeCard8(primaryNdx);
    out.writeCard8(approxNdx);
    out.writePad(1);
    for (Outline t : outlines) {
      t.write(out);
    }
  }

  @Override
  public int getSize() {
    return 8 + XObject.sizeOf(outlines);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ShapeBuilder {
    @java.lang.SuppressWarnings("all")
    private int name;
    @java.lang.SuppressWarnings("all")
    private byte primaryNdx;
    @java.lang.SuppressWarnings("all")
    private byte approxNdx;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<Outline> outlines;

    public int getSize() {
      return 8 + XObject.sizeOf(outlines);
    }

    @java.lang.SuppressWarnings("all")
    ShapeBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Shape.ShapeBuilder name(final int name) {
      this.name = name;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Shape.ShapeBuilder primaryNdx(final byte primaryNdx) {
      this.primaryNdx = primaryNdx;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Shape.ShapeBuilder approxNdx(final byte approxNdx) {
      this.approxNdx = approxNdx;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Shape.ShapeBuilder outlines(final ImmutableList<Outline> outlines) {
      if (outlines == null) {
        throw new java.lang.NullPointerException("outlines is marked non-null but is null");
      }
      this.outlines = outlines;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Shape build() {
      return new Shape(this.name, this.primaryNdx, this.approxNdx, this.outlines);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Shape.ShapeBuilder(name=" + this.name + ", primaryNdx=" + this.primaryNdx + ", approxNdx=" + this.approxNdx + ", outlines=" + this.outlines + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Shape(final int name, final byte primaryNdx, final byte approxNdx, final ImmutableList<Outline> outlines) {
    if (outlines == null) {
      throw new java.lang.NullPointerException("outlines is marked non-null but is null");
    }
    this.name = name;
    this.primaryNdx = primaryNdx;
    this.approxNdx = approxNdx;
    this.outlines = outlines;
  }

  @java.lang.SuppressWarnings("all")
  public static Shape.ShapeBuilder builder() {
    return new Shape.ShapeBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getName() {
    return this.name;
  }

  @java.lang.SuppressWarnings("all")
  public byte getPrimaryNdx() {
    return this.primaryNdx;
  }

  @java.lang.SuppressWarnings("all")
  public byte getApproxNdx() {
    return this.approxNdx;
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<Outline> getOutlines() {
    return this.outlines;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Shape)) return false;
    final Shape other = (Shape) o;
    if (this.getName() != other.getName()) return false;
    if (this.getPrimaryNdx() != other.getPrimaryNdx()) return false;
    if (this.getApproxNdx() != other.getApproxNdx()) return false;
    final java.lang.Object this$outlines = this.getOutlines();
    final java.lang.Object other$outlines = other.getOutlines();
    if (this$outlines == null ? other$outlines != null : !this$outlines.equals(other$outlines)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getName();
    result = result * PRIME + this.getPrimaryNdx();
    result = result * PRIME + this.getApproxNdx();
    final java.lang.Object $outlines = this.getOutlines();
    result = result * PRIME + ($outlines == null ? 43 : $outlines.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Shape(name=" + this.getName() + ", primaryNdx=" + this.getPrimaryNdx() + ", approxNdx=" + this.getApproxNdx() + ", outlines=" + this.getOutlines() + ")";
  }
}
