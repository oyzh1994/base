package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class QueryPictIndexValues implements TwoWayRequest<QueryPictIndexValuesReply> {
  public static final String PLUGIN_NAME = "render";
  public static final byte OPCODE = 2;
  private final int format;

  public XReplyFunction<QueryPictIndexValuesReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryPictIndexValuesReply.readQueryPictIndexValuesReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryPictIndexValues readQueryPictIndexValues(X11Input in) throws IOException {
    QueryPictIndexValues.QueryPictIndexValuesBuilder javaBuilder = QueryPictIndexValues.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int format = in.readCard32();
    javaBuilder.format(format);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(format);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryPictIndexValuesBuilder {
    @java.lang.SuppressWarnings("all")
    private int format;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    QueryPictIndexValuesBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryPictIndexValues.QueryPictIndexValuesBuilder format(final int format) {
      this.format = format;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryPictIndexValues build() {
      return new QueryPictIndexValues(this.format);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryPictIndexValues.QueryPictIndexValuesBuilder(format=" + this.format + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryPictIndexValues(final int format) {
    this.format = format;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryPictIndexValues.QueryPictIndexValuesBuilder builder() {
    return new QueryPictIndexValues.QueryPictIndexValuesBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getFormat() {
    return this.format;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryPictIndexValues)) return false;
    final QueryPictIndexValues other = (QueryPictIndexValues) o;
    if (this.getFormat() != other.getFormat()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFormat();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryPictIndexValues(format=" + this.getFormat() + ")";
  }
}
