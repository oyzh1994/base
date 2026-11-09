package com.github.moaxcp.x11.protocol.xinerama;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class QueryScreens implements TwoWayRequest<QueryScreensReply> {
  public static final String PLUGIN_NAME = "xinerama";
  public static final byte OPCODE = 5;

  public XReplyFunction<QueryScreensReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryScreensReply.readQueryScreensReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryScreens readQueryScreens(X11Input in) throws IOException {
    QueryScreens.QueryScreensBuilder javaBuilder = QueryScreens.builder();
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


  public static class QueryScreensBuilder {
    public int getSize() {
      return 4;
    }

    @java.lang.SuppressWarnings("all")
    QueryScreensBuilder() {
    }

    @java.lang.SuppressWarnings("all")
    public QueryScreens build() {
      return new QueryScreens();
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryScreens.QueryScreensBuilder()";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryScreens() {
  }

  @java.lang.SuppressWarnings("all")
  public static QueryScreens.QueryScreensBuilder builder() {
    return new QueryScreens.QueryScreensBuilder();
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryScreens)) return false;
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
    return "QueryScreens()";
  }
}
