package com.github.moaxcp.x11.protocol.render;

import java.util.ArrayList;
import java.util.List;
import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.xproto.Rectangle;
import java.io.IOException;

public final class SetPictureClipRectangles implements OneWayRequest {
  public static final String PLUGIN_NAME = "render";
  public static final byte OPCODE = 6;
  private final int picture;
  private final short clipXOrigin;
  private final short clipYOrigin;
  private final List<Rectangle> rectangles;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetPictureClipRectangles readSetPictureClipRectangles(X11Input in) throws IOException {
    SetPictureClipRectangles.SetPictureClipRectanglesBuilder javaBuilder = SetPictureClipRectangles.builder();
    int javaStart = 1;
    byte majorOpcode = in.readCard8();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int picture = in.readCard32();
    javaStart += 4;
    short clipXOrigin = in.readInt16();
    javaStart += 2;
    short clipYOrigin = in.readInt16();
    javaStart += 2;
    List<Rectangle> rectangles = new ArrayList<>(Short.toUnsignedInt(length) - javaStart);
    while (javaStart < Short.toUnsignedInt(length) * 4) {
      Rectangle baseObject = Rectangle.readRectangle(in);
      rectangles.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.picture(picture);
    javaBuilder.clipXOrigin(clipXOrigin);
    javaBuilder.clipYOrigin(clipYOrigin);
    javaBuilder.rectangles(List.copyOf(rectangles));
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(picture);
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


  public static class SetPictureClipRectanglesBuilder {
    @java.lang.SuppressWarnings("all")
    private int picture;
    @java.lang.SuppressWarnings("all")
    private short clipXOrigin;
    @java.lang.SuppressWarnings("all")
    private short clipYOrigin;
    @java.lang.SuppressWarnings("all")
    private List<Rectangle> rectangles;

    public int getSize() {
      return 12 + XObject.sizeOf(rectangles);
    }

    @java.lang.SuppressWarnings("all")
    SetPictureClipRectanglesBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetPictureClipRectangles.SetPictureClipRectanglesBuilder picture(final int picture) {
      this.picture = picture;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetPictureClipRectangles.SetPictureClipRectanglesBuilder clipXOrigin(final short clipXOrigin) {
      this.clipXOrigin = clipXOrigin;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetPictureClipRectangles.SetPictureClipRectanglesBuilder clipYOrigin(final short clipYOrigin) {
      this.clipYOrigin = clipYOrigin;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetPictureClipRectangles.SetPictureClipRectanglesBuilder rectangles(final List<Rectangle> rectangles) {
      if (rectangles == null) {
        throw new java.lang.NullPointerException("rectangles is marked non-null but is null");
      }
      this.rectangles = rectangles;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetPictureClipRectangles build() {
      return new SetPictureClipRectangles(this.picture, this.clipXOrigin, this.clipYOrigin, this.rectangles);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetPictureClipRectangles.SetPictureClipRectanglesBuilder(picture=" + this.picture + ", clipXOrigin=" + this.clipXOrigin + ", clipYOrigin=" + this.clipYOrigin + ", rectangles=" + this.rectangles + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetPictureClipRectangles(final int picture, final short clipXOrigin, final short clipYOrigin, final List<Rectangle> rectangles) {
    if (rectangles == null) {
      throw new java.lang.NullPointerException("rectangles is marked non-null but is null");
    }
    this.picture = picture;
    this.clipXOrigin = clipXOrigin;
    this.clipYOrigin = clipYOrigin;
    this.rectangles = rectangles;
  }

  @java.lang.SuppressWarnings("all")
  public static SetPictureClipRectangles.SetPictureClipRectanglesBuilder builder() {
    return new SetPictureClipRectangles.SetPictureClipRectanglesBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getPicture() {
    return this.picture;
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
  public List<Rectangle> getRectangles() {
    return this.rectangles;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetPictureClipRectangles)) return false;
    final SetPictureClipRectangles other = (SetPictureClipRectangles) o;
    if (this.getPicture() != other.getPicture()) return false;
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
    result = result * PRIME + this.getPicture();
    result = result * PRIME + this.getClipXOrigin();
    result = result * PRIME + this.getClipYOrigin();
    final java.lang.Object $rectangles = this.getRectangles();
    result = result * PRIME + ($rectangles == null ? 43 : $rectangles.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetPictureClipRectangles(picture=" + this.getPicture() + ", clipXOrigin=" + this.getClipXOrigin() + ", clipYOrigin=" + this.getClipYOrigin() + ", rectangles=" + this.getRectangles() + ")";
  }
}
