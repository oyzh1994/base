package com.github.moaxcp.x11.protocol.present;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class QueryCapabilities implements TwoWayRequest<QueryCapabilitiesReply> {
  public static final String PLUGIN_NAME = "present";
  public static final byte OPCODE = 4;
  private final int target;

  public XReplyFunction<QueryCapabilitiesReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryCapabilitiesReply.readQueryCapabilitiesReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryCapabilities readQueryCapabilities(X11Input in) throws IOException {
    QueryCapabilities.QueryCapabilitiesBuilder javaBuilder = QueryCapabilities.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int target = in.readCard32();
    javaBuilder.target(target);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(target);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryCapabilitiesBuilder {
    @java.lang.SuppressWarnings("all")
    private int target;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    QueryCapabilitiesBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryCapabilities.QueryCapabilitiesBuilder target(final int target) {
      this.target = target;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryCapabilities build() {
      return new QueryCapabilities(this.target);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryCapabilities.QueryCapabilitiesBuilder(target=" + this.target + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryCapabilities(final int target) {
    this.target = target;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryCapabilities.QueryCapabilitiesBuilder builder() {
    return new QueryCapabilities.QueryCapabilitiesBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getTarget() {
    return this.target;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryCapabilities)) return false;
    final QueryCapabilities other = (QueryCapabilities) o;
    if (this.getTarget() != other.getTarget()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getTarget();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryCapabilities(target=" + this.getTarget() + ")";
  }
}
