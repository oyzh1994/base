package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.IntList;

public final class CreateRadialGradient implements OneWayRequest {
  public static final String PLUGIN_NAME = "render";
  public static final byte OPCODE = 35;
  private final int picture;
  private final Pointfix inner;
  private final Pointfix outer;
  private final int innerRadius;
  private final int outerRadius;
  private final IntList stops;
  private final ImmutableList<Color> colors;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateRadialGradient readCreateRadialGradient(X11Input in) throws IOException {
    CreateRadialGradient.CreateRadialGradientBuilder javaBuilder = CreateRadialGradient.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int picture = in.readCard32();
    Pointfix inner = Pointfix.readPointfix(in);
    Pointfix outer = Pointfix.readPointfix(in);
    int innerRadius = in.readInt32();
    int outerRadius = in.readInt32();
    int numStops = in.readCard32();
    IntList stops = in.readInt32((int) (Integer.toUnsignedLong(numStops)));
    MutableList<Color> colors = Lists.mutable.withInitialCapacity((int) (Integer.toUnsignedLong(numStops)));
    for (int i = 0; i < Integer.toUnsignedLong(numStops); i++) {
      colors.add(Color.readColor(in));
    }
    javaBuilder.picture(picture);
    javaBuilder.inner(inner);
    javaBuilder.outer(outer);
    javaBuilder.innerRadius(innerRadius);
    javaBuilder.outerRadius(outerRadius);
    javaBuilder.stops(stops.toImmutable());
    javaBuilder.colors(colors.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(picture);
    inner.write(out);
    outer.write(out);
    out.writeInt32(innerRadius);
    out.writeInt32(outerRadius);
    int numStops = colors.size();
    out.writeCard32(numStops);
    out.writeInt32(stops);
    for (Color t : colors) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 36 + 4 * stops.size() + XObject.sizeOf(colors);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CreateRadialGradientBuilder {
    @java.lang.SuppressWarnings("all")
    private int picture;
    @java.lang.SuppressWarnings("all")
    private Pointfix inner;
    @java.lang.SuppressWarnings("all")
    private Pointfix outer;
    @java.lang.SuppressWarnings("all")
    private int innerRadius;
    @java.lang.SuppressWarnings("all")
    private int outerRadius;
    @java.lang.SuppressWarnings("all")
    private IntList stops;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<Color> colors;

    public int getSize() {
      return 36 + 4 * stops.size() + XObject.sizeOf(colors);
    }

    @java.lang.SuppressWarnings("all")
    CreateRadialGradientBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateRadialGradient.CreateRadialGradientBuilder picture(final int picture) {
      this.picture = picture;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateRadialGradient.CreateRadialGradientBuilder inner(final Pointfix inner) {
      if (inner == null) {
        throw new java.lang.NullPointerException("inner is marked non-null but is null");
      }
      this.inner = inner;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateRadialGradient.CreateRadialGradientBuilder outer(final Pointfix outer) {
      if (outer == null) {
        throw new java.lang.NullPointerException("outer is marked non-null but is null");
      }
      this.outer = outer;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateRadialGradient.CreateRadialGradientBuilder innerRadius(final int innerRadius) {
      this.innerRadius = innerRadius;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateRadialGradient.CreateRadialGradientBuilder outerRadius(final int outerRadius) {
      this.outerRadius = outerRadius;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateRadialGradient.CreateRadialGradientBuilder stops(final IntList stops) {
      if (stops == null) {
        throw new java.lang.NullPointerException("stops is marked non-null but is null");
      }
      this.stops = stops;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateRadialGradient.CreateRadialGradientBuilder colors(final ImmutableList<Color> colors) {
      if (colors == null) {
        throw new java.lang.NullPointerException("colors is marked non-null but is null");
      }
      this.colors = colors;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CreateRadialGradient build() {
      return new CreateRadialGradient(this.picture, this.inner, this.outer, this.innerRadius, this.outerRadius, this.stops, this.colors);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CreateRadialGradient.CreateRadialGradientBuilder(picture=" + this.picture + ", inner=" + this.inner + ", outer=" + this.outer + ", innerRadius=" + this.innerRadius + ", outerRadius=" + this.outerRadius + ", stops=" + this.stops + ", colors=" + this.colors + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CreateRadialGradient(final int picture, final Pointfix inner, final Pointfix outer, final int innerRadius, final int outerRadius, final IntList stops, final ImmutableList<Color> colors) {
    if (inner == null) {
      throw new java.lang.NullPointerException("inner is marked non-null but is null");
    }
    if (outer == null) {
      throw new java.lang.NullPointerException("outer is marked non-null but is null");
    }
    if (stops == null) {
      throw new java.lang.NullPointerException("stops is marked non-null but is null");
    }
    if (colors == null) {
      throw new java.lang.NullPointerException("colors is marked non-null but is null");
    }
    this.picture = picture;
    this.inner = inner;
    this.outer = outer;
    this.innerRadius = innerRadius;
    this.outerRadius = outerRadius;
    this.stops = stops;
    this.colors = colors;
  }

  @java.lang.SuppressWarnings("all")
  public static CreateRadialGradient.CreateRadialGradientBuilder builder() {
    return new CreateRadialGradient.CreateRadialGradientBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getPicture() {
    return this.picture;
  }

  @java.lang.SuppressWarnings("all")
  public Pointfix getInner() {
    return this.inner;
  }

  @java.lang.SuppressWarnings("all")
  public Pointfix getOuter() {
    return this.outer;
  }

  @java.lang.SuppressWarnings("all")
  public int getInnerRadius() {
    return this.innerRadius;
  }

  @java.lang.SuppressWarnings("all")
  public int getOuterRadius() {
    return this.outerRadius;
  }

  @java.lang.SuppressWarnings("all")
  public IntList getStops() {
    return this.stops;
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<Color> getColors() {
    return this.colors;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CreateRadialGradient)) return false;
    final CreateRadialGradient other = (CreateRadialGradient) o;
    if (this.getPicture() != other.getPicture()) return false;
    if (this.getInnerRadius() != other.getInnerRadius()) return false;
    if (this.getOuterRadius() != other.getOuterRadius()) return false;
    final java.lang.Object this$inner = this.getInner();
    final java.lang.Object other$inner = other.getInner();
    if (this$inner == null ? other$inner != null : !this$inner.equals(other$inner)) return false;
    final java.lang.Object this$outer = this.getOuter();
    final java.lang.Object other$outer = other.getOuter();
    if (this$outer == null ? other$outer != null : !this$outer.equals(other$outer)) return false;
    final java.lang.Object this$stops = this.getStops();
    final java.lang.Object other$stops = other.getStops();
    if (this$stops == null ? other$stops != null : !this$stops.equals(other$stops)) return false;
    final java.lang.Object this$colors = this.getColors();
    final java.lang.Object other$colors = other.getColors();
    if (this$colors == null ? other$colors != null : !this$colors.equals(other$colors)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getPicture();
    result = result * PRIME + this.getInnerRadius();
    result = result * PRIME + this.getOuterRadius();
    final java.lang.Object $inner = this.getInner();
    result = result * PRIME + ($inner == null ? 43 : $inner.hashCode());
    final java.lang.Object $outer = this.getOuter();
    result = result * PRIME + ($outer == null ? 43 : $outer.hashCode());
    final java.lang.Object $stops = this.getStops();
    result = result * PRIME + ($stops == null ? 43 : $stops.hashCode());
    final java.lang.Object $colors = this.getColors();
    result = result * PRIME + ($colors == null ? 43 : $colors.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CreateRadialGradient(picture=" + this.getPicture() + ", inner=" + this.getInner() + ", outer=" + this.getOuter() + ", innerRadius=" + this.getInnerRadius() + ", outerRadius=" + this.getOuterRadius() + ", stops=" + this.getStops() + ", colors=" + this.getColors() + ")";
  }
}
