package com.github.moaxcp.x11.protocol.res;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class QueryClientResources implements TwoWayRequest<QueryClientResourcesReply> {
  public static final String PLUGIN_NAME = "res";
  public static final byte OPCODE = 2;
  private final int xid;

  public XReplyFunction<QueryClientResourcesReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryClientResourcesReply.readQueryClientResourcesReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryClientResources readQueryClientResources(X11Input in) throws IOException {
    QueryClientResources.QueryClientResourcesBuilder javaBuilder = QueryClientResources.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int xid = in.readCard32();
    javaBuilder.xid(xid);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(xid);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryClientResourcesBuilder {
    @java.lang.SuppressWarnings("all")
    private int xid;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    QueryClientResourcesBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryClientResources.QueryClientResourcesBuilder xid(final int xid) {
      this.xid = xid;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryClientResources build() {
      return new QueryClientResources(this.xid);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryClientResources.QueryClientResourcesBuilder(xid=" + this.xid + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryClientResources(final int xid) {
    this.xid = xid;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryClientResources.QueryClientResourcesBuilder builder() {
    return new QueryClientResources.QueryClientResourcesBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getXid() {
    return this.xid;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryClientResources)) return false;
    final QueryClientResources other = (QueryClientResources) o;
    if (this.getXid() != other.getXid()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getXid();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryClientResources(xid=" + this.getXid() + ")";
  }
}
