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

public final class QueryResourceBytes implements TwoWayRequest<QueryResourceBytesReply> {
  public static final String PLUGIN_NAME = "res";
  public static final byte OPCODE = 5;
  private final int client;
  private final ImmutableList<ResourceIdSpec> specs;

  public XReplyFunction<QueryResourceBytesReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryResourceBytesReply.readQueryResourceBytesReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryResourceBytes readQueryResourceBytes(X11Input in) throws IOException {
    QueryResourceBytes.QueryResourceBytesBuilder javaBuilder = QueryResourceBytes.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int client = in.readCard32();
    int numSpecs = in.readCard32();
    MutableList<ResourceIdSpec> specs = Lists.mutable.withInitialCapacity((int) (Integer.toUnsignedLong(numSpecs)));
    for (int i = 0; i < Integer.toUnsignedLong(numSpecs); i++) {
      specs.add(ResourceIdSpec.readResourceIdSpec(in));
    }
    javaBuilder.client(client);
    javaBuilder.specs(specs.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(client);
    int numSpecs = specs.size();
    out.writeCard32(numSpecs);
    for (ResourceIdSpec t : specs) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + XObject.sizeOf(specs);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryResourceBytesBuilder {
    @java.lang.SuppressWarnings("all")
    private int client;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<ResourceIdSpec> specs;

    public int getSize() {
      return 12 + XObject.sizeOf(specs);
    }

    @java.lang.SuppressWarnings("all")
    QueryResourceBytesBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryResourceBytes.QueryResourceBytesBuilder client(final int client) {
      this.client = client;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryResourceBytes.QueryResourceBytesBuilder specs(final ImmutableList<ResourceIdSpec> specs) {
      if (specs == null) {
        throw new java.lang.NullPointerException("specs is marked non-null but is null");
      }
      this.specs = specs;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryResourceBytes build() {
      return new QueryResourceBytes(this.client, this.specs);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryResourceBytes.QueryResourceBytesBuilder(client=" + this.client + ", specs=" + this.specs + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryResourceBytes(final int client, final ImmutableList<ResourceIdSpec> specs) {
    if (specs == null) {
      throw new java.lang.NullPointerException("specs is marked non-null but is null");
    }
    this.client = client;
    this.specs = specs;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryResourceBytes.QueryResourceBytesBuilder builder() {
    return new QueryResourceBytes.QueryResourceBytesBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getClient() {
    return this.client;
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<ResourceIdSpec> getSpecs() {
    return this.specs;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryResourceBytes)) return false;
    final QueryResourceBytes other = (QueryResourceBytes) o;
    if (this.getClient() != other.getClient()) return false;
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
    result = result * PRIME + this.getClient();
    final java.lang.Object $specs = this.getSpecs();
    result = result * PRIME + ($specs == null ? 43 : $specs.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryResourceBytes(client=" + this.getClient() + ", specs=" + this.getSpecs() + ")";
  }
}
