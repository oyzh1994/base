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

public final class CreateLinearGradient implements OneWayRequest {
  public static final String PLUGIN_NAME = "render";
  public static final byte OPCODE = 34;
  private final int picture;
  private final Pointfix p1;
  private final Pointfix p2;
  private final IntList stops;
  private final ImmutableList<Color> colors;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateLinearGradient readCreateLinearGradient(X11Input in) throws IOException {
    CreateLinearGradient.CreateLinearGradientBuilder javaBuilder = CreateLinearGradient.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int picture = in.readCard32();
    Pointfix p1 = Pointfix.readPointfix(in);
    Pointfix p2 = Pointfix.readPointfix(in);
    int numStops = in.readCard32();
    IntList stops = in.readInt32((int) (Integer.toUnsignedLong(numStops)));
    MutableList<Color> colors = Lists.mutable.withInitialCapacity((int) (Integer.toUnsignedLong(numStops)));
    for (int i = 0; i < Integer.toUnsignedLong(numStops); i++) {
      colors.add(Color.readColor(in));
    }
    javaBuilder.picture(picture);
    javaBuilder.p1(p1);
    javaBuilder.p2(p2);
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
    p1.write(out);
    p2.write(out);
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
    return 28 + 4 * stops.size() + XObject.sizeOf(colors);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CreateLinearGradientBuilder {
    @java.lang.SuppressWarnings("all")
    private int picture;
    @java.lang.SuppressWarnings("all")
    private Pointfix p1;
    @java.lang.SuppressWarnings("all")
    private Pointfix p2;
    @java.lang.SuppressWarnings("all")
    private IntList stops;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<Color> colors;

    public int getSize() {
      return 28 + 4 * stops.size() + XObject.sizeOf(colors);
    }

    @java.lang.SuppressWarnings("all")
    CreateLinearGradientBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateLinearGradient.CreateLinearGradientBuilder picture(final int picture) {
      this.picture = picture;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateLinearGradient.CreateLinearGradientBuilder p1(final Pointfix p1) {
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
    public CreateLinearGradient.CreateLinearGradientBuilder p2(final Pointfix p2) {
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
    public CreateLinearGradient.CreateLinearGradientBuilder stops(final IntList stops) {
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
    public CreateLinearGradient.CreateLinearGradientBuilder colors(final ImmutableList<Color> colors) {
      if (colors == null) {
        throw new java.lang.NullPointerException("colors is marked non-null but is null");
      }
      this.colors = colors;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CreateLinearGradient build() {
      return new CreateLinearGradient(this.picture, this.p1, this.p2, this.stops, this.colors);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CreateLinearGradient.CreateLinearGradientBuilder(picture=" + this.picture + ", p1=" + this.p1 + ", p2=" + this.p2 + ", stops=" + this.stops + ", colors=" + this.colors + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CreateLinearGradient(final int picture, final Pointfix p1, final Pointfix p2, final IntList stops, final ImmutableList<Color> colors) {
    if (p1 == null) {
      throw new java.lang.NullPointerException("p1 is marked non-null but is null");
    }
    if (p2 == null) {
      throw new java.lang.NullPointerException("p2 is marked non-null but is null");
    }
    if (stops == null) {
      throw new java.lang.NullPointerException("stops is marked non-null but is null");
    }
    if (colors == null) {
      throw new java.lang.NullPointerException("colors is marked non-null but is null");
    }
    this.picture = picture;
    this.p1 = p1;
    this.p2 = p2;
    this.stops = stops;
    this.colors = colors;
  }

  @java.lang.SuppressWarnings("all")
  public static CreateLinearGradient.CreateLinearGradientBuilder builder() {
    return new CreateLinearGradient.CreateLinearGradientBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getPicture() {
    return this.picture;
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
    if (!(o instanceof CreateLinearGradient)) return false;
    final CreateLinearGradient other = (CreateLinearGradient) o;
    if (this.getPicture() != other.getPicture()) return false;
    final java.lang.Object this$p1 = this.getP1();
    final java.lang.Object other$p1 = other.getP1();
    if (this$p1 == null ? other$p1 != null : !this$p1.equals(other$p1)) return false;
    final java.lang.Object this$p2 = this.getP2();
    final java.lang.Object other$p2 = other.getP2();
    if (this$p2 == null ? other$p2 != null : !this$p2.equals(other$p2)) return false;
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
    final java.lang.Object $p1 = this.getP1();
    result = result * PRIME + ($p1 == null ? 43 : $p1.hashCode());
    final java.lang.Object $p2 = this.getP2();
    result = result * PRIME + ($p2 == null ? 43 : $p2.hashCode());
    final java.lang.Object $stops = this.getStops();
    result = result * PRIME + ($stops == null ? 43 : $stops.hashCode());
    final java.lang.Object $colors = this.getColors();
    result = result * PRIME + ($colors == null ? 43 : $colors.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CreateLinearGradient(picture=" + this.getPicture() + ", p1=" + this.getP1() + ", p2=" + this.getP2() + ", stops=" + this.getStops() + ", colors=" + this.getColors() + ")";
  }
}
