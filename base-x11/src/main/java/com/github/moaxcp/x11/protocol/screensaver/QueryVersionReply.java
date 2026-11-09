package com.github.moaxcp.x11.protocol.screensaver;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class QueryVersionReply implements XReply {
  public static final String PLUGIN_NAME = "screensaver";
  private final short sequenceNumber;
  private final short serverMajorVersion;
  private final short serverMinorVersion;

  public static QueryVersionReply readQueryVersionReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    QueryVersionReply.QueryVersionReplyBuilder javaBuilder = QueryVersionReply.builder();
    int length = in.readCard32();
    short serverMajorVersion = in.readCard16();
    short serverMinorVersion = in.readCard16();
    byte[] pad6 = in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.serverMajorVersion(serverMajorVersion);
    javaBuilder.serverMinorVersion(serverMinorVersion);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard16(serverMajorVersion);
    out.writeCard16(serverMinorVersion);
    out.writePad(20);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryVersionReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private short serverMajorVersion;
    @java.lang.SuppressWarnings("all")
    private short serverMinorVersion;

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    QueryVersionReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryVersionReply.QueryVersionReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryVersionReply.QueryVersionReplyBuilder serverMajorVersion(final short serverMajorVersion) {
      this.serverMajorVersion = serverMajorVersion;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryVersionReply.QueryVersionReplyBuilder serverMinorVersion(final short serverMinorVersion) {
      this.serverMinorVersion = serverMinorVersion;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryVersionReply build() {
      return new QueryVersionReply(this.sequenceNumber, this.serverMajorVersion, this.serverMinorVersion);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryVersionReply.QueryVersionReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", serverMajorVersion=" + this.serverMajorVersion + ", serverMinorVersion=" + this.serverMinorVersion + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryVersionReply(final short sequenceNumber, final short serverMajorVersion, final short serverMinorVersion) {
    this.sequenceNumber = sequenceNumber;
    this.serverMajorVersion = serverMajorVersion;
    this.serverMinorVersion = serverMinorVersion;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryVersionReply.QueryVersionReplyBuilder builder() {
    return new QueryVersionReply.QueryVersionReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public short getServerMajorVersion() {
    return this.serverMajorVersion;
  }

  @java.lang.SuppressWarnings("all")
  public short getServerMinorVersion() {
    return this.serverMinorVersion;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryVersionReply)) return false;
    final QueryVersionReply other = (QueryVersionReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getServerMajorVersion() != other.getServerMajorVersion()) return false;
    if (this.getServerMinorVersion() != other.getServerMinorVersion()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getServerMajorVersion();
    result = result * PRIME + this.getServerMinorVersion();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryVersionReply(sequenceNumber=" + this.getSequenceNumber() + ", serverMajorVersion=" + this.getServerMajorVersion() + ", serverMinorVersion=" + this.getServerMinorVersion() + ")";
  }
}
