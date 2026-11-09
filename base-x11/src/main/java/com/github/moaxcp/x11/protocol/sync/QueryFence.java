package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class QueryFence implements TwoWayRequest<QueryFenceReply> {
  public static final String PLUGIN_NAME = "sync";
  public static final byte OPCODE = 18;
  private final int fence;

  public XReplyFunction<QueryFenceReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryFenceReply.readQueryFenceReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryFence readQueryFence(X11Input in) throws IOException {
    QueryFence.QueryFenceBuilder javaBuilder = QueryFence.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int fence = in.readCard32();
    javaBuilder.fence(fence);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(fence);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryFenceBuilder {
    @java.lang.SuppressWarnings("all")
    private int fence;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    QueryFenceBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryFence.QueryFenceBuilder fence(final int fence) {
      this.fence = fence;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryFence build() {
      return new QueryFence(this.fence);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryFence.QueryFenceBuilder(fence=" + this.fence + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryFence(final int fence) {
    this.fence = fence;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryFence.QueryFenceBuilder builder() {
    return new QueryFence.QueryFenceBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getFence() {
    return this.fence;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryFence)) return false;
    final QueryFence other = (QueryFence) o;
    if (this.getFence() != other.getFence()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFence();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryFence(fence=" + this.getFence() + ")";
  }
}
