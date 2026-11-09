package com.github.moaxcp.x11.protocol.render;

import java.util.ArrayList;
import java.util.List;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import com.github.moaxcp.x11.protocol.xproto.Str;
import java.io.IOException;

public final class QueryFiltersReply implements XReply {
  public static final String PLUGIN_NAME = "render";
  private final short sequenceNumber;
  private final short[] aliases;
  private final List<Str> filters;

  public static QueryFiltersReply readQueryFiltersReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    QueryFiltersReply.QueryFiltersReplyBuilder javaBuilder = QueryFiltersReply.builder();
    int length = in.readCard32();
    int numAliases = in.readCard32();
    int numFilters = in.readCard32();
    byte[] pad6 = in.readPad(16);
    short[] aliases = in.readCard16((int) (Integer.toUnsignedLong(numAliases)));
    List<Str> filters = new ArrayList<>((int) (Integer.toUnsignedLong(numFilters)));
    for (int i = 0; i < Integer.toUnsignedLong(numFilters); i++) {
      filters.add(Str.readStr(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.aliases(aliases);
    javaBuilder.filters(List.copyOf(filters));
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
    int numAliases = aliases.length;
    out.writeCard32(numAliases);
    int numFilters = filters.size();
    out.writeCard32(numFilters);
    out.writePad(16);
    out.writeCard16(aliases);
    for (Str t : filters) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 2 * aliases.length + XObject.sizeOf(filters);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryFiltersReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private short[] aliases;
    @java.lang.SuppressWarnings("all")
    private List<Str> filters;

    public int getSize() {
      return 32 + 2 * aliases.length + XObject.sizeOf(filters);
    }

    @java.lang.SuppressWarnings("all")
    QueryFiltersReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryFiltersReply.QueryFiltersReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryFiltersReply.QueryFiltersReplyBuilder aliases(final short[] aliases) {
      if (aliases == null) {
        throw new java.lang.NullPointerException("aliases is marked non-null but is null");
      }
      this.aliases = aliases;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryFiltersReply.QueryFiltersReplyBuilder filters(final List<Str> filters) {
      if (filters == null) {
        throw new java.lang.NullPointerException("filters is marked non-null but is null");
      }
      this.filters = filters;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryFiltersReply build() {
      return new QueryFiltersReply(this.sequenceNumber, this.aliases, this.filters);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryFiltersReply.QueryFiltersReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", aliases=" + java.util.Arrays.toString(this.aliases) + ", filters=" + this.filters + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryFiltersReply(final short sequenceNumber, final short[] aliases, final List<Str> filters) {
    if (aliases == null) {
      throw new java.lang.NullPointerException("aliases is marked non-null but is null");
    }
    if (filters == null) {
      throw new java.lang.NullPointerException("filters is marked non-null but is null");
    }
    this.sequenceNumber = sequenceNumber;
    this.aliases = aliases;
    this.filters = filters;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryFiltersReply.QueryFiltersReplyBuilder builder() {
    return new QueryFiltersReply.QueryFiltersReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public short[] getAliases() {
    return this.aliases;
  }

  @java.lang.SuppressWarnings("all")
  public List<Str> getFilters() {
    return this.filters;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryFiltersReply)) return false;
    final QueryFiltersReply other = (QueryFiltersReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (!java.util.Arrays.equals(this.getAliases(), other.getAliases())) return false;
    final java.lang.Object this$filters = this.getFilters();
    final java.lang.Object other$filters = other.getFilters();
    if (this$filters == null ? other$filters != null : !this$filters.equals(other$filters)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + java.util.Arrays.hashCode(this.getAliases());
    final java.lang.Object $filters = this.getFilters();
    result = result * PRIME + ($filters == null ? 43 : $filters.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryFiltersReply(sequenceNumber=" + this.getSequenceNumber() + ", aliases=" + java.util.Arrays.toString(this.getAliases()) + ", filters=" + this.getFilters() + ")";
  }
}
