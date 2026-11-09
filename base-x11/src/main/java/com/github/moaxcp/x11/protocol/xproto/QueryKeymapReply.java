package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class QueryKeymapReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";
  private final short sequenceNumber;
  private final byte[] keys;

  public static QueryKeymapReply readQueryKeymapReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    QueryKeymapReply.QueryKeymapReplyBuilder javaBuilder = QueryKeymapReply.builder();
    int length = in.readCard32();
    byte[] keys = in.readCard8(32);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.keys(keys);
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
    out.writeCard8(keys);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 8 + 1 * keys.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryKeymapReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private byte[] keys;

    public int getSize() {
      return 8 + 1 * keys.length;
    }

    @java.lang.SuppressWarnings("all")
    QueryKeymapReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryKeymapReply.QueryKeymapReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryKeymapReply.QueryKeymapReplyBuilder keys(final byte[] keys) {
      if (keys == null) {
        throw new java.lang.NullPointerException("keys is marked non-null but is null");
      }
      this.keys = keys;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryKeymapReply build() {
      return new QueryKeymapReply(this.sequenceNumber, this.keys);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryKeymapReply.QueryKeymapReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", keys=" + java.util.Arrays.toString(this.keys) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryKeymapReply(final short sequenceNumber, final byte[] keys) {
    if (keys == null) {
      throw new java.lang.NullPointerException("keys is marked non-null but is null");
    }
    this.sequenceNumber = sequenceNumber;
    this.keys = keys;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryKeymapReply.QueryKeymapReplyBuilder builder() {
    return new QueryKeymapReply.QueryKeymapReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getKeys() {
    return this.keys;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryKeymapReply)) return false;
    final QueryKeymapReply other = (QueryKeymapReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (!java.util.Arrays.equals(this.getKeys(), other.getKeys())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + java.util.Arrays.hashCode(this.getKeys());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryKeymapReply(sequenceNumber=" + this.getSequenceNumber() + ", keys=" + java.util.Arrays.toString(this.getKeys()) + ")";
  }
}
