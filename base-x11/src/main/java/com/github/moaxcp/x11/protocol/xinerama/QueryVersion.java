package com.github.moaxcp.x11.protocol.xinerama;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class QueryVersion implements TwoWayRequest<QueryVersionReply> {
  public static final String PLUGIN_NAME = "xinerama";
  public static final byte OPCODE = 0;
  private final byte major;
  private final byte minor;

  public XReplyFunction<QueryVersionReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryVersionReply.readQueryVersionReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryVersion readQueryVersion(X11Input in) throws IOException {
    QueryVersion.QueryVersionBuilder javaBuilder = QueryVersion.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    byte major = in.readCard8();
    byte minor = in.readCard8();
    javaBuilder.major(major);
    javaBuilder.minor(minor);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard8(major);
    out.writeCard8(minor);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 6;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryVersionBuilder {
    @java.lang.SuppressWarnings("all")
    private byte major;
    @java.lang.SuppressWarnings("all")
    private byte minor;

    public int getSize() {
      return 6;
    }

    @java.lang.SuppressWarnings("all")
    QueryVersionBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryVersion.QueryVersionBuilder major(final byte major) {
      this.major = major;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryVersion.QueryVersionBuilder minor(final byte minor) {
      this.minor = minor;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryVersion build() {
      return new QueryVersion(this.major, this.minor);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryVersion.QueryVersionBuilder(major=" + this.major + ", minor=" + this.minor + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryVersion(final byte major, final byte minor) {
    this.major = major;
    this.minor = minor;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryVersion.QueryVersionBuilder builder() {
    return new QueryVersion.QueryVersionBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getMajor() {
    return this.major;
  }

  @java.lang.SuppressWarnings("all")
  public byte getMinor() {
    return this.minor;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryVersion)) return false;
    final QueryVersion other = (QueryVersion) o;
    if (this.getMajor() != other.getMajor()) return false;
    if (this.getMinor() != other.getMinor()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getMajor();
    result = result * PRIME + this.getMinor();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryVersion(major=" + this.getMajor() + ", minor=" + this.getMinor() + ")";
  }
}
