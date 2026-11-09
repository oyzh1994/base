package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.ByteList;

public final class QueryKeymapReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";
  private final short sequenceNumber;
  private final ByteList keys;

  public static QueryKeymapReply readQueryKeymapReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    QueryKeymapReply.QueryKeymapReplyBuilder javaBuilder = QueryKeymapReply.builder();
    int length = in.readCard32();
    ByteList keys = in.readCard8(32);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.keys(keys.toImmutable());
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
    return 8 + 1 * keys.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryKeymapReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private ByteList keys;

    public int getSize() {
      return 8 + 1 * keys.size();
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
    public QueryKeymapReply.QueryKeymapReplyBuilder keys(final ByteList keys) {
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
      return "QueryKeymapReply.QueryKeymapReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", keys=" + this.keys + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryKeymapReply(final short sequenceNumber, final ByteList keys) {
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
  public ByteList getKeys() {
    return this.keys;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryKeymapReply)) return false;
    final QueryKeymapReply other = (QueryKeymapReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    final java.lang.Object this$keys = this.getKeys();
    final java.lang.Object other$keys = other.getKeys();
    if (this$keys == null ? other$keys != null : !this$keys.equals(other$keys)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    final java.lang.Object $keys = this.getKeys();
    result = result * PRIME + ($keys == null ? 43 : $keys.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryKeymapReply(sequenceNumber=" + this.getSequenceNumber() + ", keys=" + this.getKeys() + ")";
  }
}
