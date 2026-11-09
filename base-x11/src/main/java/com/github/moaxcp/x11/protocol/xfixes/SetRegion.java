package com.github.moaxcp.x11.protocol.xfixes;

import java.util.ArrayList;
import java.util.List;
import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.xproto.Rectangle;
import java.io.IOException;

public final class SetRegion implements OneWayRequest {
  public static final String PLUGIN_NAME = "xfixes";
  public static final byte OPCODE = 11;
  private final int region;
  private final List<Rectangle> rectangles;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetRegion readSetRegion(X11Input in) throws IOException {
    SetRegion.SetRegionBuilder javaBuilder = SetRegion.builder();
    int javaStart = 1;
    byte majorOpcode = in.readCard8();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int region = in.readCard32();
    javaStart += 4;
    List<Rectangle> rectangles = new ArrayList<>(Short.toUnsignedInt(length) - javaStart);
    while (javaStart < Short.toUnsignedInt(length) * 4) {
      Rectangle baseObject = Rectangle.readRectangle(in);
      rectangles.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.region(region);
    javaBuilder.rectangles(List.copyOf(rectangles));
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


  public static class SetRegionBuilder {
    @java.lang.SuppressWarnings("all")
    private int region;
    @java.lang.SuppressWarnings("all")
    private List<Rectangle> rectangles;

    public int getSize() {
      return 8 + XObject.sizeOf(rectangles);
    }

    @java.lang.SuppressWarnings("all")
    SetRegionBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetRegion.SetRegionBuilder region(final int region) {
      this.region = region;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetRegion.SetRegionBuilder rectangles(final List<Rectangle> rectangles) {
      if (rectangles == null) {
        throw new java.lang.NullPointerException("rectangles is marked non-null but is null");
      }
      this.rectangles = rectangles;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetRegion build() {
      return new SetRegion(this.region, this.rectangles);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetRegion.SetRegionBuilder(region=" + this.region + ", rectangles=" + this.rectangles + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetRegion(final int region, final List<Rectangle> rectangles) {
    if (rectangles == null) {
      throw new java.lang.NullPointerException("rectangles is marked non-null but is null");
    }
    this.region = region;
    this.rectangles = rectangles;
  }

  @java.lang.SuppressWarnings("all")
  public static SetRegion.SetRegionBuilder builder() {
    return new SetRegion.SetRegionBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getRegion() {
    return this.region;
  }

  @java.lang.SuppressWarnings("all")
  public List<Rectangle> getRectangles() {
    return this.rectangles;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetRegion)) return false;
    final SetRegion other = (SetRegion) o;
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
    return "SetRegion(region=" + this.getRegion() + ", rectangles=" + this.getRectangles() + ")";
  }
}
