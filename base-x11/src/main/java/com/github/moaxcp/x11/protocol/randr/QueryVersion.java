package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class QueryVersion implements TwoWayRequest<QueryVersionReply> {
  public static final String PLUGIN_NAME = "randr";
  public static final byte OPCODE = 0;
  private final int majorVersion;
  private final int minorVersion;

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
    int majorVersion = in.readCard32();
    int minorVersion = in.readCard32();
    javaBuilder.majorVersion(majorVersion);
    javaBuilder.minorVersion(minorVersion);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(majorVersion);
    out.writeCard32(minorVersion);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryVersionBuilder {
    @java.lang.SuppressWarnings("all")
    private int majorVersion;
    @java.lang.SuppressWarnings("all")
    private int minorVersion;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    QueryVersionBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryVersion.QueryVersionBuilder majorVersion(final int majorVersion) {
      this.majorVersion = majorVersion;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryVersion.QueryVersionBuilder minorVersion(final int minorVersion) {
      this.minorVersion = minorVersion;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryVersion build() {
      return new QueryVersion(this.majorVersion, this.minorVersion);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryVersion.QueryVersionBuilder(majorVersion=" + this.majorVersion + ", minorVersion=" + this.minorVersion + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryVersion(final int majorVersion, final int minorVersion) {
    this.majorVersion = majorVersion;
    this.minorVersion = minorVersion;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryVersion.QueryVersionBuilder builder() {
    return new QueryVersion.QueryVersionBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getMajorVersion() {
    return this.majorVersion;
  }

  @java.lang.SuppressWarnings("all")
  public int getMinorVersion() {
    return this.minorVersion;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryVersion)) return false;
    final QueryVersion other = (QueryVersion) o;
    if (this.getMajorVersion() != other.getMajorVersion()) return false;
    if (this.getMinorVersion() != other.getMinorVersion()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getMajorVersion();
    result = result * PRIME + this.getMinorVersion();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryVersion(majorVersion=" + this.getMajorVersion() + ", minorVersion=" + this.getMinorVersion() + ")";
  }
}
