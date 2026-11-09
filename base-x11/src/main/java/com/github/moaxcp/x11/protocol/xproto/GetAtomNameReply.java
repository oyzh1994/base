package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.ByteList;

public final class GetAtomNameReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";
  private final short sequenceNumber;
  private final ByteList name;

  public static GetAtomNameReply readGetAtomNameReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    GetAtomNameReply.GetAtomNameReplyBuilder javaBuilder = GetAtomNameReply.builder();
    int length = in.readCard32();
    short nameLen = in.readCard16();
    byte[] pad5 = in.readPad(22);
    ByteList name = in.readChar(Short.toUnsignedInt(nameLen));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.name(name.toImmutable());
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
    short nameLen = (short) name.size();
    out.writeCard16(nameLen);
    out.writePad(22);
    out.writeChar(name);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 1 * name.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetAtomNameReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private ByteList name;

    public int getSize() {
      return 32 + 1 * name.size();
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
    public GetAtomNameReply.GetAtomNameReplyBuilder name(final ByteList name) {
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
      return "GetAtomNameReply.GetAtomNameReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", name=" + this.name + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetAtomNameReply(final short sequenceNumber, final ByteList name) {
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
  public ByteList getName() {
    return this.name;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetAtomNameReply)) return false;
    final GetAtomNameReply other = (GetAtomNameReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    final java.lang.Object this$name = this.getName();
    final java.lang.Object other$name = other.getName();
    if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    final java.lang.Object $name = this.getName();
    result = result * PRIME + ($name == null ? 43 : $name.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetAtomNameReply(sequenceNumber=" + this.getSequenceNumber() + ", name=" + this.getName() + ")";
  }
}
