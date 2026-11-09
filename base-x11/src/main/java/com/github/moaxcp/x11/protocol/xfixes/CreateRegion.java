package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.xproto.Rectangle;
import java.io.IOException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public final class CreateRegion implements OneWayRequest {
  public static final String PLUGIN_NAME = "xfixes";
  public static final byte OPCODE = 5;
  private final int region;
  private final ImmutableList<Rectangle> rectangles;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateRegion readCreateRegion(X11Input in) throws IOException {
    CreateRegion.CreateRegionBuilder javaBuilder = CreateRegion.builder();
    int javaStart = 1;
    byte majorOpcode = in.readCard8();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int region = in.readCard32();
    javaStart += 4;
    MutableList<Rectangle> rectangles = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(length) - javaStart);
    while (javaStart < Short.toUnsignedInt(length) * 4) {
      Rectangle baseObject = Rectangle.readRectangle(in);
      rectangles.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.region(region);
    javaBuilder.rectangles(rectangles.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(region);
    for (Rectangle t : rectangles) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 8 + XObject.sizeOf(rectangles);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CreateRegionBuilder {
    @java.lang.SuppressWarnings("all")
    private int region;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<Rectangle> rectangles;

    public int getSize() {
      return 8 + XObject.sizeOf(rectangles);
    }

    @java.lang.SuppressWarnings("all")
    CreateRegionBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateRegion.CreateRegionBuilder region(final int region) {
      this.region = region;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateRegion.CreateRegionBuilder rectangles(final ImmutableList<Rectangle> rectangles) {
      if (rectangles == null) {
        throw new java.lang.NullPointerException("rectangles is marked non-null but is null");
      }
      this.rectangles = rectangles;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CreateRegion build() {
      return new CreateRegion(this.region, this.rectangles);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CreateRegion.CreateRegionBuilder(region=" + this.region + ", rectangles=" + this.rectangles + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CreateRegion(final int region, final ImmutableList<Rectangle> rectangles) {
    if (rectangles == null) {
      throw new java.lang.NullPointerException("rectangles is marked non-null but is null");
    }
    this.region = region;
    this.rectangles = rectangles;
  }

  @java.lang.SuppressWarnings("all")
  public static CreateRegion.CreateRegionBuilder builder() {
    return new CreateRegion.CreateRegionBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getRegion() {
    return this.region;
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<Rectangle> getRectangles() {
    return this.rectangles;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CreateRegion)) return false;
    final CreateRegion other = (CreateRegion) o;
    if (this.getRegion() != other.getRegion()) return false;
    final java.lang.Object this$rectangles = this.getRectangles();
    final java.lang.Object other$rectangles = other.getRectangles();
    if (this$rectangles == null ? other$rectangles != null : !this$rectangles.equals(other$rectangles)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getRegion();
    final java.lang.Object $rectangles = this.getRectangles();
    result = result * PRIME + ($rectangles == null ? 43 : $rectangles.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CreateRegion(region=" + this.getRegion() + ", rectangles=" + this.getRectangles() + ")";
  }
}
