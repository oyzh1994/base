package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.ByteList;

public final class GetOutputPropertyReply implements XReply {
  public static final String PLUGIN_NAME = "randr";
  private final byte format;
  private final short sequenceNumber;
  private final int type;
  private final int bytesAfter;
  private final int numItems;
  private final ByteList data;

  public static GetOutputPropertyReply readGetOutputPropertyReply(byte format, short sequenceNumber, X11Input in) throws IOException {
    GetOutputPropertyReply.GetOutputPropertyReplyBuilder javaBuilder = GetOutputPropertyReply.builder();
    int length = in.readCard32();
    int type = in.readCard32();
    int bytesAfter = in.readCard32();
    int numItems = in.readCard32();
    byte[] pad7 = in.readPad(12);
    ByteList data = in.readByte((int) (Integer.toUnsignedLong(numItems) * (Byte.toUnsignedInt(format) / 8)));
    javaBuilder.format(format);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.type(type);
    javaBuilder.bytesAfter(bytesAfter);
    javaBuilder.numItems(numItems);
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
    out.writeCard8(format);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(type);
    out.writeCard32(bytesAfter);
    out.writeCard32(numItems);
    out.writePad(12);
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


  public static class GetOutputPropertyReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte format;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int type;
    @java.lang.SuppressWarnings("all")
    private int bytesAfter;
    @java.lang.SuppressWarnings("all")
    private int numItems;
    @java.lang.SuppressWarnings("all")
    private ByteList data;

    public int getSize() {
      return 32 + 1 * data.size();
    }

    @java.lang.SuppressWarnings("all")
    GetOutputPropertyReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetOutputPropertyReply.GetOutputPropertyReplyBuilder format(final byte format) {
      this.format = format;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetOutputPropertyReply.GetOutputPropertyReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetOutputPropertyReply.GetOutputPropertyReplyBuilder type(final int type) {
      this.type = type;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetOutputPropertyReply.GetOutputPropertyReplyBuilder bytesAfter(final int bytesAfter) {
      this.bytesAfter = bytesAfter;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetOutputPropertyReply.GetOutputPropertyReplyBuilder numItems(final int numItems) {
      this.numItems = numItems;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetOutputPropertyReply.GetOutputPropertyReplyBuilder data(final ByteList data) {
      if (data == null) {
        throw new java.lang.NullPointerException("data is marked non-null but is null");
      }
      this.data = data;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetOutputPropertyReply build() {
      return new GetOutputPropertyReply(this.format, this.sequenceNumber, this.type, this.bytesAfter, this.numItems, this.data);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetOutputPropertyReply.GetOutputPropertyReplyBuilder(format=" + this.format + ", sequenceNumber=" + this.sequenceNumber + ", type=" + this.type + ", bytesAfter=" + this.bytesAfter + ", numItems=" + this.numItems + ", data=" + this.data + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetOutputPropertyReply(final byte format, final short sequenceNumber, final int type, final int bytesAfter, final int numItems, final ByteList data) {
    if (data == null) {
      throw new java.lang.NullPointerException("data is marked non-null but is null");
    }
    this.format = format;
    this.sequenceNumber = sequenceNumber;
    this.type = type;
    this.bytesAfter = bytesAfter;
    this.numItems = numItems;
    this.data = data;
  }

  @java.lang.SuppressWarnings("all")
  public static GetOutputPropertyReply.GetOutputPropertyReplyBuilder builder() {
    return new GetOutputPropertyReply.GetOutputPropertyReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getFormat() {
    return this.format;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getType() {
    return this.type;
  }

  @java.lang.SuppressWarnings("all")
  public int getBytesAfter() {
    return this.bytesAfter;
  }

  @java.lang.SuppressWarnings("all")
  public int getNumItems() {
    return this.numItems;
  }

  @java.lang.SuppressWarnings("all")
  public ByteList getData() {
    return this.data;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetOutputPropertyReply)) return false;
    final GetOutputPropertyReply other = (GetOutputPropertyReply) o;
    if (this.getFormat() != other.getFormat()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getType() != other.getType()) return false;
    if (this.getBytesAfter() != other.getBytesAfter()) return false;
    if (this.getNumItems() != other.getNumItems()) return false;
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
    result = result * PRIME + this.getFormat();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getType();
    result = result * PRIME + this.getBytesAfter();
    result = result * PRIME + this.getNumItems();
    final java.lang.Object $data = this.getData();
    result = result * PRIME + ($data == null ? 43 : $data.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetOutputPropertyReply(format=" + this.getFormat() + ", sequenceNumber=" + this.getSequenceNumber() + ", type=" + this.getType() + ", bytesAfter=" + this.getBytesAfter() + ", numItems=" + this.getNumItems() + ", data=" + this.getData() + ")";
  }
}
