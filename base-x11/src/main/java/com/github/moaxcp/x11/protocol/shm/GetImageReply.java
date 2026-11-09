package com.github.moaxcp.x11.protocol.shm;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class GetImageReply implements XReply {
  public static final String PLUGIN_NAME = "shm";
  private final byte depth;
  private final short sequenceNumber;
  private final int visual;
  private final int size;

  public static GetImageReply readGetImageReply(byte depth, short sequenceNumber, X11Input in) throws IOException {
    GetImageReply.GetImageReplyBuilder javaBuilder = GetImageReply.builder();
    int length = in.readCard32();
    int visual = in.readCard32();
    int size = in.readCard32();
    in.readPad(16);
    javaBuilder.depth(depth);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.visual(visual);
    javaBuilder.size(size);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(depth);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(visual);
    out.writeCard32(size);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetImageReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte depth;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int visual;
    @java.lang.SuppressWarnings("all")
    private int size;

    public int getSize() {
      return 16;
    }

    @java.lang.SuppressWarnings("all")
    GetImageReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetImageReply.GetImageReplyBuilder depth(final byte depth) {
      this.depth = depth;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetImageReply.GetImageReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetImageReply.GetImageReplyBuilder visual(final int visual) {
      this.visual = visual;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetImageReply.GetImageReplyBuilder size(final int size) {
      this.size = size;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetImageReply build() {
      return new GetImageReply(this.depth, this.sequenceNumber, this.visual, this.size);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetImageReply.GetImageReplyBuilder(depth=" + this.depth + ", sequenceNumber=" + this.sequenceNumber + ", visual=" + this.visual + ", size=" + this.size + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetImageReply(final byte depth, final short sequenceNumber, final int visual, final int size) {
    this.depth = depth;
    this.sequenceNumber = sequenceNumber;
    this.visual = visual;
    this.size = size;
  }

  @java.lang.SuppressWarnings("all")
  public static GetImageReply.GetImageReplyBuilder builder() {
    return new GetImageReply.GetImageReplyBuilder();
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
  public int getVisual() {
    return this.visual;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetImageReply)) return false;
    final GetImageReply other = (GetImageReply) o;
    if (this.getDepth() != other.getDepth()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getVisual() != other.getVisual()) return false;
    if (this.getSize() != other.getSize()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDepth();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getVisual();
    result = result * PRIME + this.getSize();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetImageReply(depth=" + this.getDepth() + ", sequenceNumber=" + this.getSequenceNumber() + ", visual=" + this.getVisual() + ", size=" + this.getSize() + ")";
  }
}
