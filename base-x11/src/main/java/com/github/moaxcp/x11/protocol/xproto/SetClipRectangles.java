package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public final class SetClipRectangles implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 59;
  private final byte ordering;
  private final int gc;
  private final short clipXOrigin;
  private final short clipYOrigin;
  private final ImmutableList<Rectangle> rectangles;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetClipRectangles readSetClipRectangles(X11Input in) throws IOException {
    SetClipRectangles.SetClipRectanglesBuilder javaBuilder = SetClipRectangles.builder();
    int javaStart = 1;
    byte ordering = in.readByte();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int gc = in.readCard32();
    javaStart += 4;
    short clipXOrigin = in.readInt16();
    javaStart += 2;
    short clipYOrigin = in.readInt16();
    javaStart += 2;
    MutableList<Rectangle> rectangles = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(length) - javaStart);
    while (javaStart < Short.toUnsignedInt(length) * 4) {
      Rectangle baseObject = Rectangle.readRectangle(in);
      rectangles.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.ordering(ordering);
    javaBuilder.gc(gc);
    javaBuilder.clipXOrigin(clipXOrigin);
    javaBuilder.clipYOrigin(clipYOrigin);
    javaBuilder.rectangles(rectangles.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeByte(ordering);
    out.writeCard16((short) getLength());
    out.writeCard32(gc);
    out.writeInt16(clipXOrigin);
    out.writeInt16(clipYOrigin);
    for (Rectangle t : rectangles) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + XObject.sizeOf(rectangles);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetClipRectanglesBuilder {
    @java.lang.SuppressWarnings("all")
    private byte ordering;
    @java.lang.SuppressWarnings("all")
    private int gc;
    @java.lang.SuppressWarnings("all")
    private short clipXOrigin;
    @java.lang.SuppressWarnings("all")
    private short clipYOrigin;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<Rectangle> rectangles;

    public SetClipRectangles.SetClipRectanglesBuilder ordering(ClipOrdering ordering) {
      this.ordering = (byte) ordering.getValue();
      return this;
    }

    public SetClipRectangles.SetClipRectanglesBuilder ordering(byte ordering) {
      this.ordering = ordering;
      return this;
    }

    public int getSize() {
      return 12 + XObject.sizeOf(rectangles);
    }

    @java.lang.SuppressWarnings("all")
    SetClipRectanglesBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetClipRectangles.SetClipRectanglesBuilder gc(final int gc) {
      this.gc = gc;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetClipRectangles.SetClipRectanglesBuilder clipXOrigin(final short clipXOrigin) {
      this.clipXOrigin = clipXOrigin;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetClipRectangles.SetClipRectanglesBuilder clipYOrigin(final short clipYOrigin) {
      this.clipYOrigin = clipYOrigin;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetClipRectangles.SetClipRectanglesBuilder rectangles(final ImmutableList<Rectangle> rectangles) {
      if (rectangles == null) {
        throw new java.lang.NullPointerException("rectangles is marked non-null but is null");
      }
      this.rectangles = rectangles;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetClipRectangles build() {
      return new SetClipRectangles(this.ordering, this.gc, this.clipXOrigin, this.clipYOrigin, this.rectangles);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetClipRectangles.SetClipRectanglesBuilder(ordering=" + this.ordering + ", gc=" + this.gc + ", clipXOrigin=" + this.clipXOrigin + ", clipYOrigin=" + this.clipYOrigin + ", rectangles=" + this.rectangles + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetClipRectangles(final byte ordering, final int gc, final short clipXOrigin, final short clipYOrigin, final ImmutableList<Rectangle> rectangles) {
    if (rectangles == null) {
      throw new java.lang.NullPointerException("rectangles is marked non-null but is null");
    }
    this.ordering = ordering;
    this.gc = gc;
    this.clipXOrigin = clipXOrigin;
    this.clipYOrigin = clipYOrigin;
    this.rectangles = rectangles;
  }

  @java.lang.SuppressWarnings("all")
  public static SetClipRectangles.SetClipRectanglesBuilder builder() {
    return new SetClipRectangles.SetClipRectanglesBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getOrdering() {
    return this.ordering;
  }

  @java.lang.SuppressWarnings("all")
  public int getGc() {
    return this.gc;
  }

  @java.lang.SuppressWarnings("all")
  public short getClipXOrigin() {
    return this.clipXOrigin;
  }

  @java.lang.SuppressWarnings("all")
  public short getClipYOrigin() {
    return this.clipYOrigin;
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<Rectangle> getRectangles() {
    return this.rectangles;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetClipRectangles)) return false;
    final SetClipRectangles other = (SetClipRectangles) o;
    if (this.getOrdering() != other.getOrdering()) return false;
    if (this.getGc() != other.getGc()) return false;
    if (this.getClipXOrigin() != other.getClipXOrigin()) return false;
    if (this.getClipYOrigin() != other.getClipYOrigin()) return false;
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
    result = result * PRIME + this.getOrdering();
    result = result * PRIME + this.getGc();
    result = result * PRIME + this.getClipXOrigin();
    result = result * PRIME + this.getClipYOrigin();
    final java.lang.Object $rectangles = this.getRectangles();
    result = result * PRIME + ($rectangles == null ? 43 : $rectangles.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetClipRectangles(ordering=" + this.getOrdering() + ", gc=" + this.getGc() + ", clipXOrigin=" + this.getClipXOrigin() + ", clipYOrigin=" + this.getClipYOrigin() + ", rectangles=" + this.getRectangles() + ")";
  }
}
