package com.github.moaxcp.x11.protocol.screensaver;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class QueryInfo implements TwoWayRequest<QueryInfoReply> {
  public static final String PLUGIN_NAME = "screensaver";
  public static final byte OPCODE = 1;
  private final int drawable;

  public XReplyFunction<QueryInfoReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryInfoReply.readQueryInfoReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryInfo readQueryInfo(X11Input in) throws IOException {
    QueryInfo.QueryInfoBuilder javaBuilder = QueryInfo.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int drawable = in.readCard32();
    javaBuilder.drawable(drawable);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryInfoBuilder {
    @java.lang.SuppressWarnings("all")
    private int drawable;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    QueryInfoBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryInfo.QueryInfoBuilder drawable(final int drawable) {
      this.drawable = drawable;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryInfo build() {
      return new QueryInfo(this.drawable);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryInfo.QueryInfoBuilder(drawable=" + this.drawable + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryInfo(final int drawable) {
    this.drawable = drawable;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryInfo.QueryInfoBuilder builder() {
    return new QueryInfo.QueryInfoBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getDrawable() {
    return this.drawable;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryInfo)) return false;
    final QueryInfo other = (QueryInfo) o;
    if (this.getDrawable() != other.getDrawable()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDrawable();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryInfo(drawable=" + this.getDrawable() + ")";
  }
}
