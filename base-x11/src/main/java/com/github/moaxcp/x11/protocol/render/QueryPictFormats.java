package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class QueryPictFormats implements TwoWayRequest<QueryPictFormatsReply> {
  public static final String PLUGIN_NAME = "render";
  public static final byte OPCODE = 1;

  public XReplyFunction<QueryPictFormatsReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryPictFormatsReply.readQueryPictFormatsReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryPictFormats readQueryPictFormats(X11Input in) throws IOException {
    QueryPictFormats.QueryPictFormatsBuilder javaBuilder = QueryPictFormats.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
  }

  @Override
  public int getSize() {
    return 4;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryPictFormatsBuilder {
    public int getSize() {
      return 4;
    }

    @java.lang.SuppressWarnings("all")
    QueryPictFormatsBuilder() {
    }

    @java.lang.SuppressWarnings("all")
    public QueryPictFormats build() {
      return new QueryPictFormats();
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryPictFormats.QueryPictFormatsBuilder()";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryPictFormats() {
  }

  @java.lang.SuppressWarnings("all")
  public static QueryPictFormats.QueryPictFormatsBuilder builder() {
    return new QueryPictFormats.QueryPictFormatsBuilder();
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryPictFormats)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int result = 1;
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryPictFormats()";
  }
}
