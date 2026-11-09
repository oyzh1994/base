package com.github.moaxcp.x11.protocol.render;

import java.util.ArrayList;
import java.util.List;
import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;

public final class CreateConicalGradient implements OneWayRequest {
  public static final String PLUGIN_NAME = "render";
  public static final byte OPCODE = 36;
  private final int picture;
  private final Pointfix center;
  private final int angle;
  private final int[] stops;
  private final List<Color> colors;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateConicalGradient readCreateConicalGradient(X11Input in) throws IOException {
    CreateConicalGradient.CreateConicalGradientBuilder javaBuilder = CreateConicalGradient.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int picture = in.readCard32();
    Pointfix center = Pointfix.readPointfix(in);
    int angle = in.readInt32();
    int numStops = in.readCard32();
    int[] stops = in.readInt32((int) (Integer.toUnsignedLong(numStops)));
    List<Color> colors = new ArrayList<>((int) (Integer.toUnsignedLong(numStops)));
    for (int i = 0; i < Integer.toUnsignedLong(numStops); i++) {
      colors.add(Color.readColor(in));
    }
    javaBuilder.picture(picture);
    javaBuilder.center(center);
    javaBuilder.angle(angle);
    javaBuilder.stops(stops);
    javaBuilder.colors(List.copyOf(colors));
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(picture);
    center.write(out);
    out.writeInt32(angle);
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
    return 24 + 4 * stops.length + XObject.sizeOf(colors);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CreateConicalGradientBuilder {
    @java.lang.SuppressWarnings("all")
    private int picture;
    @java.lang.SuppressWarnings("all")
    private Pointfix center;
    @java.lang.SuppressWarnings("all")
    private int angle;
    @java.lang.SuppressWarnings("all")
    private int[] stops;
    @java.lang.SuppressWarnings("all")
    private List<Color> colors;

    public int getSize() {
      return 24 + 4 * stops.length + XObject.sizeOf(colors);
    }

    @java.lang.SuppressWarnings("all")
    CreateConicalGradientBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateConicalGradient.CreateConicalGradientBuilder picture(final int picture) {
      this.picture = picture;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateConicalGradient.CreateConicalGradientBuilder center(final Pointfix center) {
      if (center == null) {
        throw new java.lang.NullPointerException("center is marked non-null but is null");
      }
      this.center = center;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateConicalGradient.CreateConicalGradientBuilder angle(final int angle) {
      this.angle = angle;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateConicalGradient.CreateConicalGradientBuilder stops(final int[] stops) {
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
    public CreateConicalGradient.CreateConicalGradientBuilder colors(final List<Color> colors) {
      if (colors == null) {
        throw new java.lang.NullPointerException("colors is marked non-null but is null");
      }
      this.colors = colors;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CreateConicalGradient build() {
      return new CreateConicalGradient(this.picture, this.center, this.angle, this.stops, this.colors);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CreateConicalGradient.CreateConicalGradientBuilder(picture=" + this.picture + ", center=" + this.center + ", angle=" + this.angle + ", stops=" + java.util.Arrays.toString(this.stops) + ", colors=" + this.colors + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CreateConicalGradient(final int picture, final Pointfix center, final int angle, final int[] stops, final List<Color> colors) {
    if (center == null) {
      throw new java.lang.NullPointerException("center is marked non-null but is null");
    }
    if (stops == null) {
      throw new java.lang.NullPointerException("stops is marked non-null but is null");
    }
    if (colors == null) {
      throw new java.lang.NullPointerException("colors is marked non-null but is null");
    }
    this.picture = picture;
    this.center = center;
    this.angle = angle;
    this.stops = stops;
    this.colors = colors;
  }

  @java.lang.SuppressWarnings("all")
  public static CreateConicalGradient.CreateConicalGradientBuilder builder() {
    return new CreateConicalGradient.CreateConicalGradientBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getPicture() {
    return this.picture;
  }

  @java.lang.SuppressWarnings("all")
  public Pointfix getCenter() {
    return this.center;
  }

  @java.lang.SuppressWarnings("all")
  public int getAngle() {
    return this.angle;
  }

  @java.lang.SuppressWarnings("all")
  public int[] getStops() {
    return this.stops;
  }

  @java.lang.SuppressWarnings("all")
  public List<Color> getColors() {
    return this.colors;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CreateConicalGradient)) return false;
    final CreateConicalGradient other = (CreateConicalGradient) o;
    if (this.getPicture() != other.getPicture()) return false;
    if (this.getAngle() != other.getAngle()) return false;
    final java.lang.Object this$center = this.getCenter();
    final java.lang.Object other$center = other.getCenter();
    if (this$center == null ? other$center != null : !this$center.equals(other$center)) return false;
    if (!java.util.Arrays.equals(this.getStops(), other.getStops())) return false;
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
    result = result * PRIME + this.getAngle();
    final java.lang.Object $center = this.getCenter();
    result = result * PRIME + ($center == null ? 43 : $center.hashCode());
    result = result * PRIME + java.util.Arrays.hashCode(this.getStops());
    final java.lang.Object $colors = this.getColors();
    result = result * PRIME + ($colors == null ? 43 : $colors.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CreateConicalGradient(picture=" + this.getPicture() + ", center=" + this.getCenter() + ", angle=" + this.getAngle() + ", stops=" + java.util.Arrays.toString(this.getStops()) + ", colors=" + this.getColors() + ")";
  }
}
