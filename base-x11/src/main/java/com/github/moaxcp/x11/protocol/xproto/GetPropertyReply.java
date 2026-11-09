package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class GetPropertyReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";
  private final byte format;
  private final short sequenceNumber;
  private final int type;
  private final int bytesAfter;
  private final int valueLen;
  private final byte[] value;

  public static GetPropertyReply readGetPropertyReply(byte format, short sequenceNumber, X11Input in) throws IOException {
    GetPropertyReply.GetPropertyReplyBuilder javaBuilder = GetPropertyReply.builder();
    int length = in.readCard32();
    int type = in.readCard32();
    int bytesAfter = in.readCard32();
    int valueLen = in.readCard32();
    byte[] pad7 = in.readPad(12);
    byte[] value = in.readVoid((int) (Integer.toUnsignedLong(valueLen) * (Byte.toUnsignedInt(format) / 8)));
    javaBuilder.format(format);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.type(type);
    javaBuilder.bytesAfter(bytesAfter);
    javaBuilder.valueLen(valueLen);
    javaBuilder.value(value);
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
    out.writeCard32(valueLen);
    out.writePad(12);
    out.writeVoid(value);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 1 * value.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetPropertyReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte format;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int type;
    @java.lang.SuppressWarnings("all")
    private int bytesAfter;
    @java.lang.SuppressWarnings("all")
    private int valueLen;
    @java.lang.SuppressWarnings("all")
    private byte[] value;

    public int getSize() {
      return 32 + 1 * value.length;
    }

    @java.lang.SuppressWarnings("all")
    GetPropertyReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetPropertyReply.GetPropertyReplyBuilder format(final byte format) {
      this.format = format;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetPropertyReply.GetPropertyReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetPropertyReply.GetPropertyReplyBuilder type(final int type) {
      this.type = type;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetPropertyReply.GetPropertyReplyBuilder bytesAfter(final int bytesAfter) {
      this.bytesAfter = bytesAfter;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetPropertyReply.GetPropertyReplyBuilder valueLen(final int valueLen) {
      this.valueLen = valueLen;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetPropertyReply.GetPropertyReplyBuilder value(final byte[] value) {
      if (value == null) {
        throw new java.lang.NullPointerException("value is marked non-null but is null");
      }
      this.value = value;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetPropertyReply build() {
      return new GetPropertyReply(this.format, this.sequenceNumber, this.type, this.bytesAfter, this.valueLen, this.value);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetPropertyReply.GetPropertyReplyBuilder(format=" + this.format + ", sequenceNumber=" + this.sequenceNumber + ", type=" + this.type + ", bytesAfter=" + this.bytesAfter + ", valueLen=" + this.valueLen + ", value=" + java.util.Arrays.toString(this.value) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetPropertyReply(final byte format, final short sequenceNumber, final int type, final int bytesAfter, final int valueLen, final byte[] value) {
    if (value == null) {
      throw new java.lang.NullPointerException("value is marked non-null but is null");
    }
    this.format = format;
    this.sequenceNumber = sequenceNumber;
    this.type = type;
    this.bytesAfter = bytesAfter;
    this.valueLen = valueLen;
    this.value = value;
  }

  @java.lang.SuppressWarnings("all")
  public static GetPropertyReply.GetPropertyReplyBuilder builder() {
    return new GetPropertyReply.GetPropertyReplyBuilder();
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
  public int getValueLen() {
    return this.valueLen;
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getValue() {
    return this.value;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetPropertyReply)) return false;
    final GetPropertyReply other = (GetPropertyReply) o;
    if (this.getFormat() != other.getFormat()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getType() != other.getType()) return false;
    if (this.getBytesAfter() != other.getBytesAfter()) return false;
    if (this.getValueLen() != other.getValueLen()) return false;
    if (!java.util.Arrays.equals(this.getValue(), other.getValue())) return false;
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
    result = result * PRIME + this.getValueLen();
    result = result * PRIME + java.util.Arrays.hashCode(this.getValue());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetPropertyReply(format=" + this.getFormat() + ", sequenceNumber=" + this.getSequenceNumber() + ", type=" + this.getType() + ", bytesAfter=" + this.getBytesAfter() + ", valueLen=" + this.getValueLen() + ", value=" + java.util.Arrays.toString(this.getValue()) + ")";
  }
}
