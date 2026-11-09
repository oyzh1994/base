package com.github.moaxcp.x11.protocol.damage;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class QueryVersion implements TwoWayRequest<QueryVersionReply> {
  public static final String PLUGIN_NAME = "damage";
  public static final byte OPCODE = 0;
  private final int clientMajorVersion;
  private final int clientMinorVersion;

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
    int clientMajorVersion = in.readCard32();
    int clientMinorVersion = in.readCard32();
    javaBuilder.clientMajorVersion(clientMajorVersion);
    javaBuilder.clientMinorVersion(clientMinorVersion);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(clientMajorVersion);
    out.writeCard32(clientMinorVersion);
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
    private int clientMajorVersion;
    @java.lang.SuppressWarnings("all")
    private int clientMinorVersion;

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
    public QueryVersion.QueryVersionBuilder clientMajorVersion(final int clientMajorVersion) {
      this.clientMajorVersion = clientMajorVersion;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryVersion.QueryVersionBuilder clientMinorVersion(final int clientMinorVersion) {
      this.clientMinorVersion = clientMinorVersion;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryVersion build() {
      return new QueryVersion(this.clientMajorVersion, this.clientMinorVersion);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryVersion.QueryVersionBuilder(clientMajorVersion=" + this.clientMajorVersion + ", clientMinorVersion=" + this.clientMinorVersion + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryVersion(final int clientMajorVersion, final int clientMinorVersion) {
    this.clientMajorVersion = clientMajorVersion;
    this.clientMinorVersion = clientMinorVersion;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryVersion.QueryVersionBuilder builder() {
    return new QueryVersion.QueryVersionBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getClientMajorVersion() {
    return this.clientMajorVersion;
  }

  @java.lang.SuppressWarnings("all")
  public int getClientMinorVersion() {
    return this.clientMinorVersion;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryVersion)) return false;
    final QueryVersion other = (QueryVersion) o;
    if (this.getClientMajorVersion() != other.getClientMajorVersion()) return false;
    if (this.getClientMinorVersion() != other.getClientMinorVersion()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getClientMajorVersion();
    result = result * PRIME + this.getClientMinorVersion();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryVersion(clientMajorVersion=" + this.getClientMajorVersion() + ", clientMinorVersion=" + this.getClientMinorVersion() + ")";
  }
}
