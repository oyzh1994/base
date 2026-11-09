package com.github.moaxcp.x11.protocol.screensaver;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class QueryVersion implements TwoWayRequest<QueryVersionReply> {
  public static final String PLUGIN_NAME = "screensaver";
  public static final byte OPCODE = 0;
  private final byte clientMajorVersion;
  private final byte clientMinorVersion;

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
    byte clientMajorVersion = in.readCard8();
    byte clientMinorVersion = in.readCard8();
    byte[] pad5 = in.readPad(2);
    javaBuilder.clientMajorVersion(clientMajorVersion);
    javaBuilder.clientMinorVersion(clientMinorVersion);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard8(clientMajorVersion);
    out.writeCard8(clientMinorVersion);
    out.writePad(2);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryVersionBuilder {
    @java.lang.SuppressWarnings("all")
    private byte clientMajorVersion;
    @java.lang.SuppressWarnings("all")
    private byte clientMinorVersion;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    QueryVersionBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryVersion.QueryVersionBuilder clientMajorVersion(final byte clientMajorVersion) {
      this.clientMajorVersion = clientMajorVersion;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryVersion.QueryVersionBuilder clientMinorVersion(final byte clientMinorVersion) {
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
  QueryVersion(final byte clientMajorVersion, final byte clientMinorVersion) {
    this.clientMajorVersion = clientMajorVersion;
    this.clientMinorVersion = clientMinorVersion;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryVersion.QueryVersionBuilder builder() {
    return new QueryVersion.QueryVersionBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getClientMajorVersion() {
    return this.clientMajorVersion;
  }

  @java.lang.SuppressWarnings("all")
  public byte getClientMinorVersion() {
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
