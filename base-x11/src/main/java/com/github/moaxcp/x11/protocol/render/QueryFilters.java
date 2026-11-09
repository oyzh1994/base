package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class QueryFilters implements TwoWayRequest<QueryFiltersReply> {
  public static final String PLUGIN_NAME = "render";
  public static final byte OPCODE = 29;
  private final int drawable;

  public XReplyFunction<QueryFiltersReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryFiltersReply.readQueryFiltersReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryFilters readQueryFilters(X11Input in) throws IOException {
    QueryFilters.QueryFiltersBuilder javaBuilder = QueryFilters.builder();
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


  public static class QueryFiltersBuilder {
    @java.lang.SuppressWarnings("all")
    private int drawable;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    QueryFiltersBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryFilters.QueryFiltersBuilder drawable(final int drawable) {
      this.drawable = drawable;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryFilters build() {
      return new QueryFilters(this.drawable);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryFilters.QueryFiltersBuilder(drawable=" + this.drawable + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryFilters(final int drawable) {
    this.drawable = drawable;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryFilters.QueryFiltersBuilder builder() {
    return new QueryFilters.QueryFiltersBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getDrawable() {
    return this.drawable;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryFilters)) return false;
    final QueryFilters other = (QueryFilters) o;
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
    return "QueryFilters(drawable=" + this.getDrawable() + ")";
  }
}
