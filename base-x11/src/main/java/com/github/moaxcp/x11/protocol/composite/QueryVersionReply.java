package com.github.moaxcp.x11.protocol.composite;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class QueryVersionReply implements XReply {
  public static final String PLUGIN_NAME = "composite";
  private final short sequenceNumber;
  private final int majorVersion;
  private final int minorVersion;

  public static QueryVersionReply readQueryVersionReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    QueryVersionReply.QueryVersionReplyBuilder javaBuilder = QueryVersionReply.builder();
    int length = in.readCard32();
    int majorVersion = in.readCard32();
    int minorVersion = in.readCard32();
    byte[] pad6 = in.readPad(16);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.majorVersion(majorVersion);
    javaBuilder.minorVersion(minorVersion);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(majorVersion);
    out.writeCard32(minorVersion);
    out.writePad(16);
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
    private int majorVersion;
    @java.lang.SuppressWarnings("all")
    private int minorVersion;

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
    public QueryVersionReply.QueryVersionReplyBuilder majorVersion(final int majorVersion) {
      this.majorVersion = majorVersion;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryVersionReply.QueryVersionReplyBuilder minorVersion(final int minorVersion) {
      this.minorVersion = minorVersion;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryVersionReply build() {
      return new QueryVersionReply(this.sequenceNumber, this.majorVersion, this.minorVersion);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryVersionReply.QueryVersionReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", majorVersion=" + this.majorVersion + ", minorVersion=" + this.minorVersion + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryVersionReply(final short sequenceNumber, final int majorVersion, final int minorVersion) {
    this.sequenceNumber = sequenceNumber;
    this.majorVersion = majorVersion;
    this.minorVersion = minorVersion;
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
    if (!(o instanceof QueryVersionReply)) return false;
    final QueryVersionReply other = (QueryVersionReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getMajorVersion() != other.getMajorVersion()) return false;
    if (this.getMinorVersion() != other.getMinorVersion()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getMajorVersion();
    result = result * PRIME + this.getMinorVersion();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryVersionReply(sequenceNumber=" + this.getSequenceNumber() + ", majorVersion=" + this.getMajorVersion() + ", minorVersion=" + this.getMinorVersion() + ")";
  }
}
