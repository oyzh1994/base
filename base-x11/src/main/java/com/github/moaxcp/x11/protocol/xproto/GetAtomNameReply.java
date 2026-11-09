package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class GetAtomNameReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";
  private final short sequenceNumber;
  private final byte[] name;

  public static GetAtomNameReply readGetAtomNameReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    GetAtomNameReply.GetAtomNameReplyBuilder javaBuilder = GetAtomNameReply.builder();
    int length = in.readCard32();
    short nameLen = in.readCard16();
    byte[] pad5 = in.readPad(22);
    byte[] name = in.readChar(Short.toUnsignedInt(nameLen));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.name(name);
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
    out.writeCard32(getLength());
    short nameLen = (short) name.length;
    out.writeCard16(nameLen);
    out.writePad(22);
    out.writeChar(name);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 1 * name.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetAtomNameReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private byte[] name;

    public int getSize() {
      return 32 + 1 * name.length;
    }

    @java.lang.SuppressWarnings("all")
    GetAtomNameReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetAtomNameReply.GetAtomNameReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetAtomNameReply.GetAtomNameReplyBuilder name(final byte[] name) {
      if (name == null) {
        throw new java.lang.NullPointerException("name is marked non-null but is null");
      }
      this.name = name;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetAtomNameReply build() {
      return new GetAtomNameReply(this.sequenceNumber, this.name);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetAtomNameReply.GetAtomNameReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", name=" + java.util.Arrays.toString(this.name) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetAtomNameReply(final short sequenceNumber, final byte[] name) {
    if (name == null) {
      throw new java.lang.NullPointerException("name is marked non-null but is null");
    }
    this.sequenceNumber = sequenceNumber;
    this.name = name;
  }

  @java.lang.SuppressWarnings("all")
  public static GetAtomNameReply.GetAtomNameReplyBuilder builder() {
    return new GetAtomNameReply.GetAtomNameReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getName() {
    return this.name;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetAtomNameReply)) return false;
    final GetAtomNameReply other = (GetAtomNameReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (!java.util.Arrays.equals(this.getName(), other.getName())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + java.util.Arrays.hashCode(this.getName());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetAtomNameReply(sequenceNumber=" + this.getSequenceNumber() + ", name=" + java.util.Arrays.toString(this.getName()) + ")";
  }
}
