package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public final class QueryPictIndexValuesReply implements XReply {
  public static final String PLUGIN_NAME = "render";
  private final short sequenceNumber;
  private final ImmutableList<Indexvalue> values;

  public static QueryPictIndexValuesReply readQueryPictIndexValuesReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    QueryPictIndexValuesReply.QueryPictIndexValuesReplyBuilder javaBuilder = QueryPictIndexValuesReply.builder();
    int length = in.readCard32();
    int numValues = in.readCard32();
    byte[] pad5 = in.readPad(20);
    MutableList<Indexvalue> values = Lists.mutable.withInitialCapacity((int) (Integer.toUnsignedLong(numValues)));
    for (int i = 0; i < Integer.toUnsignedLong(numValues); i++) {
      values.add(Indexvalue.readIndexvalue(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.values(values.toImmutable());
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
    int numValues = values.size();
    out.writeCard32(numValues);
    out.writePad(20);
    for (Indexvalue t : values) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(values);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryPictIndexValuesReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<Indexvalue> values;

    public int getSize() {
      return 32 + XObject.sizeOf(values);
    }

    @java.lang.SuppressWarnings("all")
    QueryPictIndexValuesReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryPictIndexValuesReply.QueryPictIndexValuesReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryPictIndexValuesReply.QueryPictIndexValuesReplyBuilder values(final ImmutableList<Indexvalue> values) {
      if (values == null) {
        throw new java.lang.NullPointerException("values is marked non-null but is null");
      }
      this.values = values;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryPictIndexValuesReply build() {
      return new QueryPictIndexValuesReply(this.sequenceNumber, this.values);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryPictIndexValuesReply.QueryPictIndexValuesReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", values=" + this.values + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryPictIndexValuesReply(final short sequenceNumber, final ImmutableList<Indexvalue> values) {
    if (values == null) {
      throw new java.lang.NullPointerException("values is marked non-null but is null");
    }
    this.sequenceNumber = sequenceNumber;
    this.values = values;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryPictIndexValuesReply.QueryPictIndexValuesReplyBuilder builder() {
    return new QueryPictIndexValuesReply.QueryPictIndexValuesReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<Indexvalue> getValues() {
    return this.values;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryPictIndexValuesReply)) return false;
    final QueryPictIndexValuesReply other = (QueryPictIndexValuesReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    final java.lang.Object this$values = this.getValues();
    final java.lang.Object other$values = other.getValues();
    if (this$values == null ? other$values != null : !this$values.equals(other$values)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    final java.lang.Object $values = this.getValues();
    result = result * PRIME + ($values == null ? 43 : $values.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryPictIndexValuesReply(sequenceNumber=" + this.getSequenceNumber() + ", values=" + this.getValues() + ")";
  }
}
