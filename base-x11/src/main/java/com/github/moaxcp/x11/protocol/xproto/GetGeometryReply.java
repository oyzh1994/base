package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class GetGeometryReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";
  private final byte depth;
  private final short sequenceNumber;
  private final int root;
  private final short x;
  private final short y;
  private final short width;
  private final short height;
  private final short borderWidth;

  public static GetGeometryReply readGetGeometryReply(byte depth, short sequenceNumber, X11Input in) throws IOException {
    GetGeometryReply.GetGeometryReplyBuilder javaBuilder = GetGeometryReply.builder();
    int length = in.readCard32();
    int root = in.readCard32();
    short x = in.readInt16();
    short y = in.readInt16();
    short width = in.readCard16();
    short height = in.readCard16();
    short borderWidth = in.readCard16();
    byte[] pad10 = in.readPad(2);
    in.readPad(8);
    javaBuilder.depth(depth);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.root(root);
    javaBuilder.x(x);
    javaBuilder.y(y);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.borderWidth(borderWidth);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(depth);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(root);
    out.writeInt16(x);
    out.writeInt16(y);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeCard16(borderWidth);
    out.writePad(2);
  }

  @Override
  public int getSize() {
    return 24;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetGeometryReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte depth;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int root;
    @java.lang.SuppressWarnings("all")
    private short x;
    @java.lang.SuppressWarnings("all")
    private short y;
    @java.lang.SuppressWarnings("all")
    private short width;
    @java.lang.SuppressWarnings("all")
    private short height;
    @java.lang.SuppressWarnings("all")
    private short borderWidth;

    public int getSize() {
      return 24;
    }

    @java.lang.SuppressWarnings("all")
    GetGeometryReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetGeometryReply.GetGeometryReplyBuilder depth(final byte depth) {
      this.depth = depth;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetGeometryReply.GetGeometryReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetGeometryReply.GetGeometryReplyBuilder root(final int root) {
      this.root = root;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetGeometryReply.GetGeometryReplyBuilder x(final short x) {
      this.x = x;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetGeometryReply.GetGeometryReplyBuilder y(final short y) {
      this.y = y;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetGeometryReply.GetGeometryReplyBuilder width(final short width) {
      this.width = width;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetGeometryReply.GetGeometryReplyBuilder height(final short height) {
      this.height = height;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetGeometryReply.GetGeometryReplyBuilder borderWidth(final short borderWidth) {
      this.borderWidth = borderWidth;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetGeometryReply build() {
      return new GetGeometryReply(this.depth, this.sequenceNumber, this.root, this.x, this.y, this.width, this.height, this.borderWidth);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetGeometryReply.GetGeometryReplyBuilder(depth=" + this.depth + ", sequenceNumber=" + this.sequenceNumber + ", root=" + this.root + ", x=" + this.x + ", y=" + this.y + ", width=" + this.width + ", height=" + this.height + ", borderWidth=" + this.borderWidth + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetGeometryReply(final byte depth, final short sequenceNumber, final int root, final short x, final short y, final short width, final short height, final short borderWidth) {
    this.depth = depth;
    this.sequenceNumber = sequenceNumber;
    this.root = root;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.borderWidth = borderWidth;
  }

  @java.lang.SuppressWarnings("all")
  public static GetGeometryReply.GetGeometryReplyBuilder builder() {
    return new GetGeometryReply.GetGeometryReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getDepth() {
    return this.depth;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getRoot() {
    return this.root;
  }

  @java.lang.SuppressWarnings("all")
  public short getX() {
    return this.x;
  }

  @java.lang.SuppressWarnings("all")
  public short getY() {
    return this.y;
  }

  @java.lang.SuppressWarnings("all")
  public short getWidth() {
    return this.width;
  }

  @java.lang.SuppressWarnings("all")
  public short getHeight() {
    return this.height;
  }

  @java.lang.SuppressWarnings("all")
  public short getBorderWidth() {
    return this.borderWidth;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetGeometryReply)) return false;
    final GetGeometryReply other = (GetGeometryReply) o;
    if (this.getDepth() != other.getDepth()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getRoot() != other.getRoot()) return false;
    if (this.getX() != other.getX()) return false;
    if (this.getY() != other.getY()) return false;
    if (this.getWidth() != other.getWidth()) return false;
    if (this.getHeight() != other.getHeight()) return false;
    if (this.getBorderWidth() != other.getBorderWidth()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDepth();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getRoot();
    result = result * PRIME + this.getX();
    result = result * PRIME + this.getY();
    result = result * PRIME + this.getWidth();
    result = result * PRIME + this.getHeight();
    result = result * PRIME + this.getBorderWidth();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetGeometryReply(depth=" + this.getDepth() + ", sequenceNumber=" + this.getSequenceNumber() + ", root=" + this.getRoot() + ", x=" + this.getX() + ", y=" + this.getY() + ", width=" + this.getWidth() + ", height=" + this.getHeight() + ", borderWidth=" + this.getBorderWidth() + ")";
  }
}
