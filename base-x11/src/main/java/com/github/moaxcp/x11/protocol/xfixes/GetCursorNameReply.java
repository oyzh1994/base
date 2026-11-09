package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class GetCursorNameReply implements XReply {
  public static final String PLUGIN_NAME = "xfixes";
  private final short sequenceNumber;
  private final int atom;
  private final byte[] name;

  public static GetCursorNameReply readGetCursorNameReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    GetCursorNameReply.GetCursorNameReplyBuilder javaBuilder = GetCursorNameReply.builder();
    int length = in.readCard32();
    int atom = in.readCard32();
    short nbytes = in.readCard16();
    byte[] pad6 = in.readPad(18);
    byte[] name = in.readChar(Short.toUnsignedInt(nbytes));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.atom(atom);
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
    out.writeCard32(atom);
    short nbytes = (short) name.length;
    out.writeCard16(nbytes);
    out.writePad(18);
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


  public static class GetCursorNameReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int atom;
    @java.lang.SuppressWarnings("all")
    private byte[] name;

    public int getSize() {
      return 32 + 1 * name.length;
    }

    @java.lang.SuppressWarnings("all")
    GetCursorNameReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCursorNameReply.GetCursorNameReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCursorNameReply.GetCursorNameReplyBuilder atom(final int atom) {
      this.atom = atom;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCursorNameReply.GetCursorNameReplyBuilder name(final byte[] name) {
      if (name == null) {
        throw new java.lang.NullPointerException("name is marked non-null but is null");
      }
      this.name = name;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetCursorNameReply build() {
      return new GetCursorNameReply(this.sequenceNumber, this.atom, this.name);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetCursorNameReply.GetCursorNameReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", atom=" + this.atom + ", name=" + java.util.Arrays.toString(this.name) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetCursorNameReply(final short sequenceNumber, final int atom, final byte[] name) {
    if (name == null) {
      throw new java.lang.NullPointerException("name is marked non-null but is null");
    }
    this.sequenceNumber = sequenceNumber;
    this.atom = atom;
    this.name = name;
  }

  @java.lang.SuppressWarnings("all")
  public static GetCursorNameReply.GetCursorNameReplyBuilder builder() {
    return new GetCursorNameReply.GetCursorNameReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getAtom() {
    return this.atom;
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getName() {
    return this.name;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetCursorNameReply)) return false;
    final GetCursorNameReply other = (GetCursorNameReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getAtom() != other.getAtom()) return false;
    if (!java.util.Arrays.equals(this.getName(), other.getName())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getAtom();
    result = result * PRIME + java.util.Arrays.hashCode(this.getName());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetCursorNameReply(sequenceNumber=" + this.getSequenceNumber() + ", atom=" + this.getAtom() + ", name=" + java.util.Arrays.toString(this.getName()) + ")";
  }
}
