package com.github.moaxcp.x11.protocol.res;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public final class QueryClientIds implements TwoWayRequest<QueryClientIdsReply> {
  public static final String PLUGIN_NAME = "res";
  public static final byte OPCODE = 4;
  private final ImmutableList<ClientIdSpec> specs;

  public XReplyFunction<QueryClientIdsReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryClientIdsReply.readQueryClientIdsReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryClientIds readQueryClientIds(X11Input in) throws IOException {
    QueryClientIds.QueryClientIdsBuilder javaBuilder = QueryClientIds.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int numSpecs = in.readCard32();
    MutableList<ClientIdSpec> specs = Lists.mutable.withInitialCapacity((int) (Integer.toUnsignedLong(numSpecs)));
    for (int i = 0; i < Integer.toUnsignedLong(numSpecs); i++) {
      specs.add(ClientIdSpec.readClientIdSpec(in));
    }
    javaBuilder.specs(specs.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    int numSpecs = specs.size();
    out.writeCard32(numSpecs);
    for (ClientIdSpec t : specs) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 8 + XObject.sizeOf(specs);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryClientIdsBuilder {
    @java.lang.SuppressWarnings("all")
    private ImmutableList<ClientIdSpec> specs;

    public int getSize() {
      return 8 + XObject.sizeOf(specs);
    }

    @java.lang.SuppressWarnings("all")
    QueryClientIdsBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryClientIds.QueryClientIdsBuilder specs(final ImmutableList<ClientIdSpec> specs) {
      if (specs == null) {
        throw new java.lang.NullPointerException("specs is marked non-null but is null");
      }
      this.specs = specs;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryClientIds build() {
      return new QueryClientIds(this.specs);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryClientIds.QueryClientIdsBuilder(specs=" + this.specs + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryClientIds(final ImmutableList<ClientIdSpec> specs) {
    if (specs == null) {
      throw new java.lang.NullPointerException("specs is marked non-null but is null");
    }
    this.specs = specs;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryClientIds.QueryClientIdsBuilder builder() {
    return new QueryClientIds.QueryClientIdsBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<ClientIdSpec> getSpecs() {
    return this.specs;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryClientIds)) return false;
    final QueryClientIds other = (QueryClientIds) o;
    final java.lang.Object this$specs = this.getSpecs();
    final java.lang.Object other$specs = other.getSpecs();
    if (this$specs == null ? other$specs != null : !this$specs.equals(other$specs)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final java.lang.Object $specs = this.getSpecs();
    result = result * PRIME + ($specs == null ? 43 : $specs.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryClientIds(specs=" + this.getSpecs() + ")";
  }
}
