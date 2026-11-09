package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.ByteList;

public final class GetImageReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";
  private final byte depth;
  private final short sequenceNumber;
  private final int visual;
  private final ByteList data;

  public static GetImageReply readGetImageReply(byte depth, short sequenceNumber, X11Input in) throws IOException {
    GetImageReply.GetImageReplyBuilder javaBuilder = GetImageReply.builder();
    int length = in.readCard32();
    int visual = in.readCard32();
    byte[] pad5 = in.readPad(20);
    ByteList data = in.readByte((int) (Integer.toUnsignedLong(length) * 4));
    javaBuilder.depth(depth);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.visual(visual);
    javaBuilder.data(data.toImmutable());
    if (javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(depth);
    out.writeCard16(sequenceNumber);
    int length = data.size();
    out.writeCard32(getLength());
    out.writeCard32(visual);
    out.writePad(20);
    out.writeByte(data);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 1 * data.size();
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
    private ByteList data;

    public int getSize() {
      return 32 + 1 * data.size();
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
    public GetImageReply.GetImageReplyBuilder data(final ByteList data) {
      if (data == null) {
        throw new java.lang.NullPointerException("data is marked non-null but is null");
      }
      this.data = data;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetImageReply build() {
      return new GetImageReply(this.depth, this.sequenceNumber, this.visual, this.data);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetImageReply.GetImageReplyBuilder(depth=" + this.depth + ", sequenceNumber=" + this.sequenceNumber + ", visual=" + this.visual + ", data=" + this.data + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetImageReply(final byte depth, final short sequenceNumber, final int visual, final ByteList data) {
    if (data == null) {
      throw new java.lang.NullPointerException("data is marked non-null but is null");
    }
    this.depth = depth;
    this.sequenceNumber = sequenceNumber;
    this.visual = visual;
    this.data = data;
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

  @java.lang.SuppressWarnings("all")
  public ByteList getData() {
    return this.data;
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
    final java.lang.Object this$data = this.getData();
    final java.lang.Object other$data = other.getData();
    if (this$data == null ? other$data != null : !this$data.equals(other$data)) return false;
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
    final java.lang.Object $data = this.getData();
    result = result * PRIME + ($data == null ? 43 : $data.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetImageReply(depth=" + this.getDepth() + ", sequenceNumber=" + this.getSequenceNumber() + ", visual=" + this.getVisual() + ", data=" + this.getData() + ")";
  }
}
