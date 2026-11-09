package com.github.moaxcp.x11.protocol.shape;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import com.github.moaxcp.x11.protocol.xproto.ClipOrdering;
import com.github.moaxcp.x11.protocol.xproto.Rectangle;
import java.io.IOException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public final class GetRectanglesReply implements XReply {
  public static final String PLUGIN_NAME = "shape";
  private final byte ordering;
  private final short sequenceNumber;
  private final ImmutableList<Rectangle> rectangles;

  public static GetRectanglesReply readGetRectanglesReply(byte ordering, short sequenceNumber, X11Input in) throws IOException {
    GetRectanglesReply.GetRectanglesReplyBuilder javaBuilder = GetRectanglesReply.builder();
    int length = in.readCard32();
    int rectanglesLen = in.readCard32();
    byte[] pad5 = in.readPad(20);
    MutableList<Rectangle> rectangles = Lists.mutable.withInitialCapacity((int) (Integer.toUnsignedLong(rectanglesLen)));
    for (int i = 0; i < Integer.toUnsignedLong(rectanglesLen); i++) {
      rectangles.add(Rectangle.readRectangle(in));
    }
    javaBuilder.ordering(ordering);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.rectangles(rectangles.toImmutable());
    if (javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeByte(ordering);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    int rectanglesLen = rectangles.size();
    out.writeCard32(rectanglesLen);
    out.writePad(20);
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


  public static class GetRectanglesReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte ordering;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<Rectangle> rectangles;

    public GetRectanglesReply.GetRectanglesReplyBuilder ordering(ClipOrdering ordering) {
      this.ordering = (byte) ordering.getValue();
      return this;
    }

    public GetRectanglesReply.GetRectanglesReplyBuilder ordering(byte ordering) {
      this.ordering = ordering;
      return this;
    }

    public int getSize() {
      return 32 + XObject.sizeOf(rectangles);
    }

    @java.lang.SuppressWarnings("all")
    GetRectanglesReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetRectanglesReply.GetRectanglesReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetRectanglesReply.GetRectanglesReplyBuilder rectangles(final ImmutableList<Rectangle> rectangles) {
      if (rectangles == null) {
        throw new java.lang.NullPointerException("rectangles is marked non-null but is null");
      }
      this.rectangles = rectangles;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetRectanglesReply build() {
      return new GetRectanglesReply(this.ordering, this.sequenceNumber, this.rectangles);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetRectanglesReply.GetRectanglesReplyBuilder(ordering=" + this.ordering + ", sequenceNumber=" + this.sequenceNumber + ", rectangles=" + this.rectangles + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetRectanglesReply(final byte ordering, final short sequenceNumber, final ImmutableList<Rectangle> rectangles) {
    if (rectangles == null) {
      throw new java.lang.NullPointerException("rectangles is marked non-null but is null");
    }
    this.ordering = ordering;
    this.sequenceNumber = sequenceNumber;
    this.rectangles = rectangles;
  }

  @java.lang.SuppressWarnings("all")
  public static GetRectanglesReply.GetRectanglesReplyBuilder builder() {
    return new GetRectanglesReply.GetRectanglesReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getOrdering() {
    return this.ordering;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<Rectangle> getRectangles() {
    return this.rectangles;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetRectanglesReply)) return false;
    final GetRectanglesReply other = (GetRectanglesReply) o;
    if (this.getOrdering() != other.getOrdering()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
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
    result = result * PRIME + this.getSequenceNumber();
    final java.lang.Object $rectangles = this.getRectangles();
    result = result * PRIME + ($rectangles == null ? 43 : $rectangles.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetRectanglesReply(ordering=" + this.getOrdering() + ", sequenceNumber=" + this.getSequenceNumber() + ", rectangles=" + this.getRectangles() + ")";
  }
}
