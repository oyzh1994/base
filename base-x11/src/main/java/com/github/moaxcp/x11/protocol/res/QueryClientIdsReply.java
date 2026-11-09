package com.github.moaxcp.x11.protocol.res;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public final class QueryClientIdsReply implements XReply {
  public static final String PLUGIN_NAME = "res";
  private final short sequenceNumber;
  private final ImmutableList<ClientIdValue> ids;

  public static QueryClientIdsReply readQueryClientIdsReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    QueryClientIdsReply.QueryClientIdsReplyBuilder javaBuilder = QueryClientIdsReply.builder();
    int length = in.readCard32();
    int numIds = in.readCard32();
    byte[] pad5 = in.readPad(20);
    MutableList<ClientIdValue> ids = Lists.mutable.withInitialCapacity((int) (Integer.toUnsignedLong(numIds)));
    for (int i = 0; i < Integer.toUnsignedLong(numIds); i++) {
      ids.add(ClientIdValue.readClientIdValue(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.ids(ids.toImmutable());
    if (javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    int numIds = ids.size();
    out.writeCard32(numIds);
    out.writePad(20);
    for (ClientIdValue t : ids) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(ids);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryClientIdsReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<ClientIdValue> ids;

    public int getSize() {
      return 32 + XObject.sizeOf(ids);
    }

    @java.lang.SuppressWarnings("all")
    QueryClientIdsReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryClientIdsReply.QueryClientIdsReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryClientIdsReply.QueryClientIdsReplyBuilder ids(final ImmutableList<ClientIdValue> ids) {
      if (ids == null) {
        throw new java.lang.NullPointerException("ids is marked non-null but is null");
      }
      this.ids = ids;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryClientIdsReply build() {
      return new QueryClientIdsReply(this.sequenceNumber, this.ids);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryClientIdsReply.QueryClientIdsReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", ids=" + this.ids + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryClientIdsReply(final short sequenceNumber, final ImmutableList<ClientIdValue> ids) {
    if (ids == null) {
      throw new java.lang.NullPointerException("ids is marked non-null but is null");
    }
    this.sequenceNumber = sequenceNumber;
    this.ids = ids;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryClientIdsReply.QueryClientIdsReplyBuilder builder() {
    return new QueryClientIdsReply.QueryClientIdsReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<ClientIdValue> getIds() {
    return this.ids;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryClientIdsReply)) return false;
    final QueryClientIdsReply other = (QueryClientIdsReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    final java.lang.Object this$ids = this.getIds();
    final java.lang.Object other$ids = other.getIds();
    if (this$ids == null ? other$ids != null : !this$ids.equals(other$ids)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    final java.lang.Object $ids = this.getIds();
    result = result * PRIME + ($ids == null ? 43 : $ids.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryClientIdsReply(sequenceNumber=" + this.getSequenceNumber() + ", ids=" + this.getIds() + ")";
  }
}
