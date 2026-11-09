package com.github.moaxcp.x11.protocol.res;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class QueryVersionReply implements XReply {
  public static final String PLUGIN_NAME = "res";
  private final short sequenceNumber;
  private final short serverMajor;
  private final short serverMinor;

  public static QueryVersionReply readQueryVersionReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    QueryVersionReply.QueryVersionReplyBuilder javaBuilder = QueryVersionReply.builder();
    int length = in.readCard32();
    short serverMajor = in.readCard16();
    short serverMinor = in.readCard16();
    in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.serverMajor(serverMajor);
    javaBuilder.serverMinor(serverMinor);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard16(serverMajor);
    out.writeCard16(serverMinor);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryVersionReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private short serverMajor;
    @java.lang.SuppressWarnings("all")
    private short serverMinor;

    public int getSize() {
      return 12;
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
    public QueryVersionReply.QueryVersionReplyBuilder serverMajor(final short serverMajor) {
      this.serverMajor = serverMajor;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryVersionReply.QueryVersionReplyBuilder serverMinor(final short serverMinor) {
      this.serverMinor = serverMinor;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryVersionReply build() {
      return new QueryVersionReply(this.sequenceNumber, this.serverMajor, this.serverMinor);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryVersionReply.QueryVersionReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", serverMajor=" + this.serverMajor + ", serverMinor=" + this.serverMinor + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryVersionReply(final short sequenceNumber, final short serverMajor, final short serverMinor) {
    this.sequenceNumber = sequenceNumber;
    this.serverMajor = serverMajor;
    this.serverMinor = serverMinor;
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
  public short getServerMajor() {
    return this.serverMajor;
  }

  @java.lang.SuppressWarnings("all")
  public short getServerMinor() {
    return this.serverMinor;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryVersionReply)) return false;
    final QueryVersionReply other = (QueryVersionReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getServerMajor() != other.getServerMajor()) return false;
    if (this.getServerMinor() != other.getServerMinor()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getServerMajor();
    result = result * PRIME + this.getServerMinor();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryVersionReply(sequenceNumber=" + this.getSequenceNumber() + ", serverMajor=" + this.getServerMajor() + ", serverMinor=" + this.getServerMinor() + ")";
  }
}
