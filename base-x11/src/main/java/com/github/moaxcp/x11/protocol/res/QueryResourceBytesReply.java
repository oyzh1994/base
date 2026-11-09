package com.github.moaxcp.x11.protocol.res;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public final class QueryResourceBytesReply implements XReply {
  public static final String PLUGIN_NAME = "res";
  private final short sequenceNumber;
  private final ImmutableList<ResourceSizeValue> sizes;

  public static QueryResourceBytesReply readQueryResourceBytesReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    QueryResourceBytesReply.QueryResourceBytesReplyBuilder javaBuilder = QueryResourceBytesReply.builder();
    int length = in.readCard32();
    int numSizes = in.readCard32();
    byte[] pad5 = in.readPad(20);
    MutableList<ResourceSizeValue> sizes = Lists.mutable.withInitialCapacity((int) (Integer.toUnsignedLong(numSizes)));
    for (int i = 0; i < Integer.toUnsignedLong(numSizes); i++) {
      sizes.add(ResourceSizeValue.readResourceSizeValue(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.sizes(sizes.toImmutable());
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
    int numSizes = sizes.size();
    out.writeCard32(numSizes);
    out.writePad(20);
    for (ResourceSizeValue t : sizes) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(sizes);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryResourceBytesReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<ResourceSizeValue> sizes;

    public int getSize() {
      return 32 + XObject.sizeOf(sizes);
    }

    @java.lang.SuppressWarnings("all")
    QueryResourceBytesReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryResourceBytesReply.QueryResourceBytesReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryResourceBytesReply.QueryResourceBytesReplyBuilder sizes(final ImmutableList<ResourceSizeValue> sizes) {
      if (sizes == null) {
        throw new java.lang.NullPointerException("sizes is marked non-null but is null");
      }
      this.sizes = sizes;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryResourceBytesReply build() {
      return new QueryResourceBytesReply(this.sequenceNumber, this.sizes);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryResourceBytesReply.QueryResourceBytesReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", sizes=" + this.sizes + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryResourceBytesReply(final short sequenceNumber, final ImmutableList<ResourceSizeValue> sizes) {
    if (sizes == null) {
      throw new java.lang.NullPointerException("sizes is marked non-null but is null");
    }
    this.sequenceNumber = sequenceNumber;
    this.sizes = sizes;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryResourceBytesReply.QueryResourceBytesReplyBuilder builder() {
    return new QueryResourceBytesReply.QueryResourceBytesReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<ResourceSizeValue> getSizes() {
    return this.sizes;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryResourceBytesReply)) return false;
    final QueryResourceBytesReply other = (QueryResourceBytesReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    final java.lang.Object this$sizes = this.getSizes();
    final java.lang.Object other$sizes = other.getSizes();
    if (this$sizes == null ? other$sizes != null : !this$sizes.equals(other$sizes)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    final java.lang.Object $sizes = this.getSizes();
    result = result * PRIME + ($sizes == null ? 43 : $sizes.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryResourceBytesReply(sequenceNumber=" + this.getSequenceNumber() + ", sizes=" + this.getSizes() + ")";
  }
}
