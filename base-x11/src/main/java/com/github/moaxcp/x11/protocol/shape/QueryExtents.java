package com.github.moaxcp.x11.protocol.shape;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class QueryExtents implements TwoWayRequest<QueryExtentsReply> {
  public static final String PLUGIN_NAME = "shape";
  public static final byte OPCODE = 5;
  private final int destinationWindow;

  public XReplyFunction<QueryExtentsReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryExtentsReply.readQueryExtentsReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryExtents readQueryExtents(X11Input in) throws IOException {
    QueryExtents.QueryExtentsBuilder javaBuilder = QueryExtents.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int destinationWindow = in.readCard32();
    javaBuilder.destinationWindow(destinationWindow);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(destinationWindow);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryExtentsBuilder {
    @java.lang.SuppressWarnings("all")
    private int destinationWindow;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    QueryExtentsBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryExtents.QueryExtentsBuilder destinationWindow(final int destinationWindow) {
      this.destinationWindow = destinationWindow;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryExtents build() {
      return new QueryExtents(this.destinationWindow);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryExtents.QueryExtentsBuilder(destinationWindow=" + this.destinationWindow + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryExtents(final int destinationWindow) {
    this.destinationWindow = destinationWindow;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryExtents.QueryExtentsBuilder builder() {
    return new QueryExtents.QueryExtentsBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getDestinationWindow() {
    return this.destinationWindow;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryExtents)) return false;
    final QueryExtents other = (QueryExtents) o;
    if (this.getDestinationWindow() != other.getDestinationWindow()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDestinationWindow();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryExtents(destinationWindow=" + this.getDestinationWindow() + ")";
  }
}
