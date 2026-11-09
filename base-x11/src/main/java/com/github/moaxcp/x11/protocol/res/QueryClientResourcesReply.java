package com.github.moaxcp.x11.protocol.res;

import java.util.ArrayList;
import java.util.List;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class QueryClientResourcesReply implements XReply {
  public static final String PLUGIN_NAME = "res";
  private final short sequenceNumber;
  private final List<Type> types;

  public static QueryClientResourcesReply readQueryClientResourcesReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    QueryClientResourcesReply.QueryClientResourcesReplyBuilder javaBuilder = QueryClientResourcesReply.builder();
    int length = in.readCard32();
    int numTypes = in.readCard32();
    byte[] pad5 = in.readPad(20);
    List<Type> types = new ArrayList<>((int) (Integer.toUnsignedLong(numTypes)));
    for (int i = 0; i < Integer.toUnsignedLong(numTypes); i++) {
      types.add(Type.readType(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.types(List.copyOf(types));
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
    int numTypes = types.size();
    out.writeCard32(numTypes);
    out.writePad(20);
    for (Type t : types) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(types);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryClientResourcesReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private List<Type> types;

    public int getSize() {
      return 32 + XObject.sizeOf(types);
    }

    @java.lang.SuppressWarnings("all")
    QueryClientResourcesReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryClientResourcesReply.QueryClientResourcesReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryClientResourcesReply.QueryClientResourcesReplyBuilder types(final List<Type> types) {
      if (types == null) {
        throw new java.lang.NullPointerException("types is marked non-null but is null");
      }
      this.types = types;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryClientResourcesReply build() {
      return new QueryClientResourcesReply(this.sequenceNumber, this.types);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryClientResourcesReply.QueryClientResourcesReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", types=" + this.types + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryClientResourcesReply(final short sequenceNumber, final List<Type> types) {
    if (types == null) {
      throw new java.lang.NullPointerException("types is marked non-null but is null");
    }
    this.sequenceNumber = sequenceNumber;
    this.types = types;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryClientResourcesReply.QueryClientResourcesReplyBuilder builder() {
    return new QueryClientResourcesReply.QueryClientResourcesReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public List<Type> getTypes() {
    return this.types;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryClientResourcesReply)) return false;
    final QueryClientResourcesReply other = (QueryClientResourcesReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    final java.lang.Object this$types = this.getTypes();
    final java.lang.Object other$types = other.getTypes();
    if (this$types == null ? other$types != null : !this$types.equals(other$types)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    final java.lang.Object $types = this.getTypes();
    result = result * PRIME + ($types == null ? 43 : $types.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryClientResourcesReply(sequenceNumber=" + this.getSequenceNumber() + ", types=" + this.getTypes() + ")";
  }
}
