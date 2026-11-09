package com.github.moaxcp.x11.protocol.xfixes;

import java.util.ArrayList;
import java.util.List;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import com.github.moaxcp.x11.protocol.xproto.Rectangle;
import java.io.IOException;

public final class FetchRegionReply implements XReply {
  public static final String PLUGIN_NAME = "xfixes";
  private final short sequenceNumber;
  private final Rectangle extents;
  private final List<Rectangle> rectangles;

  public static FetchRegionReply readFetchRegionReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    FetchRegionReply.FetchRegionReplyBuilder javaBuilder = FetchRegionReply.builder();
    int length = in.readCard32();
    Rectangle extents = Rectangle.readRectangle(in);
    byte[] pad5 = in.readPad(16);
    List<Rectangle> rectangles = new ArrayList<>((int) (Integer.toUnsignedLong(length) / 2));
    for (int i = 0; i < Integer.toUnsignedLong(length) / 2; i++) {
      rectangles.add(Rectangle.readRectangle(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.extents(extents);
    javaBuilder.rectangles(List.copyOf(rectangles));
    if (javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    int length = rectangles.size();
    out.writeCard32(getLength());
    extents.write(out);
    out.writePad(16);
    for (Rectangle t : rectangles) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(rectangles);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class FetchRegionReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private Rectangle extents;
    @java.lang.SuppressWarnings("all")
    private List<Rectangle> rectangles;

    public int getSize() {
      return 32 + XObject.sizeOf(rectangles);
    }

    @java.lang.SuppressWarnings("all")
    FetchRegionReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public FetchRegionReply.FetchRegionReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public FetchRegionReply.FetchRegionReplyBuilder extents(final Rectangle extents) {
      if (extents == null) {
        throw new java.lang.NullPointerException("extents is marked non-null but is null");
      }
      this.extents = extents;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public FetchRegionReply.FetchRegionReplyBuilder rectangles(final List<Rectangle> rectangles) {
      if (rectangles == null) {
        throw new java.lang.NullPointerException("rectangles is marked non-null but is null");
      }
      this.rectangles = rectangles;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public FetchRegionReply build() {
      return new FetchRegionReply(this.sequenceNumber, this.extents, this.rectangles);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "FetchRegionReply.FetchRegionReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", extents=" + this.extents + ", rectangles=" + this.rectangles + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  FetchRegionReply(final short sequenceNumber, final Rectangle extents, final List<Rectangle> rectangles) {
    if (extents == null) {
      throw new java.lang.NullPointerException("extents is marked non-null but is null");
    }
    if (rectangles == null) {
      throw new java.lang.NullPointerException("rectangles is marked non-null but is null");
    }
    this.sequenceNumber = sequenceNumber;
    this.extents = extents;
    this.rectangles = rectangles;
  }

  @java.lang.SuppressWarnings("all")
  public static FetchRegionReply.FetchRegionReplyBuilder builder() {
    return new FetchRegionReply.FetchRegionReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public Rectangle getExtents() {
    return this.extents;
  }

  @java.lang.SuppressWarnings("all")
  public List<Rectangle> getRectangles() {
    return this.rectangles;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof FetchRegionReply)) return false;
    final FetchRegionReply other = (FetchRegionReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    final java.lang.Object this$extents = this.getExtents();
    final java.lang.Object other$extents = other.getExtents();
    if (this$extents == null ? other$extents != null : !this$extents.equals(other$extents)) return false;
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
    result = result * PRIME + this.getSequenceNumber();
    final java.lang.Object $extents = this.getExtents();
    result = result * PRIME + ($extents == null ? 43 : $extents.hashCode());
    final java.lang.Object $rectangles = this.getRectangles();
    result = result * PRIME + ($rectangles == null ? 43 : $rectangles.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "FetchRegionReply(sequenceNumber=" + this.getSequenceNumber() + ", extents=" + this.getExtents() + ", rectangles=" + this.getRectangles() + ")";
  }
}
