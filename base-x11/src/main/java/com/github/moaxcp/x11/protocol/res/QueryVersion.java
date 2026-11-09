package com.github.moaxcp.x11.protocol.res;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class QueryVersion implements TwoWayRequest<QueryVersionReply> {
  public static final String PLUGIN_NAME = "res";
  public static final byte OPCODE = 0;
  private final byte clientMajor;
  private final byte clientMinor;

  public XReplyFunction<QueryVersionReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryVersionReply.readQueryVersionReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryVersion readQueryVersion(X11Input in) throws IOException {
    QueryVersion.QueryVersionBuilder javaBuilder = QueryVersion.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    byte clientMajor = in.readCard8();
    byte clientMinor = in.readCard8();
    javaBuilder.clientMajor(clientMajor);
    javaBuilder.clientMinor(clientMinor);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard8(clientMajor);
    out.writeCard8(clientMinor);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 6;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryVersionBuilder {
    @java.lang.SuppressWarnings("all")
    private byte clientMajor;
    @java.lang.SuppressWarnings("all")
    private byte clientMinor;

    public int getSize() {
      return 6;
    }

    @java.lang.SuppressWarnings("all")
    QueryVersionBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryVersion.QueryVersionBuilder clientMajor(final byte clientMajor) {
      this.clientMajor = clientMajor;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryVersion.QueryVersionBuilder clientMinor(final byte clientMinor) {
      this.clientMinor = clientMinor;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryVersion build() {
      return new QueryVersion(this.clientMajor, this.clientMinor);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryVersion.QueryVersionBuilder(clientMajor=" + this.clientMajor + ", clientMinor=" + this.clientMinor + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryVersion(final byte clientMajor, final byte clientMinor) {
    this.clientMajor = clientMajor;
    this.clientMinor = clientMinor;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryVersion.QueryVersionBuilder builder() {
    return new QueryVersion.QueryVersionBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getClientMajor() {
    return this.clientMajor;
  }

  @java.lang.SuppressWarnings("all")
  public byte getClientMinor() {
    return this.clientMinor;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryVersion)) return false;
    final QueryVersion other = (QueryVersion) o;
    if (this.getClientMajor() != other.getClientMajor()) return false;
    if (this.getClientMinor() != other.getClientMinor()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getClientMajor();
    result = result * PRIME + this.getClientMinor();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryVersion(clientMajor=" + this.getClientMajor() + ", clientMinor=" + this.getClientMinor() + ")";
  }
}
