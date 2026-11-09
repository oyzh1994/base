package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class QueryPointer implements TwoWayRequest<QueryPointerReply> {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 38;
  private final int window;

  public XReplyFunction<QueryPointerReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryPointerReply.readQueryPointerReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryPointer readQueryPointer(X11Input in) throws IOException {
    QueryPointer.QueryPointerBuilder javaBuilder = QueryPointer.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int window = in.readCard32();
    javaBuilder.window(window);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryPointerBuilder {
    @java.lang.SuppressWarnings("all")
    private int window;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    QueryPointerBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryPointer.QueryPointerBuilder window(final int window) {
      this.window = window;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryPointer build() {
      return new QueryPointer(this.window);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryPointer.QueryPointerBuilder(window=" + this.window + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryPointer(final int window) {
    this.window = window;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryPointer.QueryPointerBuilder builder() {
    return new QueryPointer.QueryPointerBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryPointer)) return false;
    final QueryPointer other = (QueryPointer) o;
    if (this.getWindow() != other.getWindow()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getWindow();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryPointer(window=" + this.getWindow() + ")";
  }
}
