package com.github.moaxcp.x11.protocol.shape;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class QueryVersionReply implements XReply {
  public static final String PLUGIN_NAME = "shape";
  private final short sequenceNumber;
  private final short majorVersion;
  private final short minorVersion;

  public static QueryVersionReply readQueryVersionReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    QueryVersionReply.QueryVersionReplyBuilder javaBuilder = QueryVersionReply.builder();
    int length = in.readCard32();
    short majorVersion = in.readCard16();
    short minorVersion = in.readCard16();
    in.readPad(20);
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
    out.writeCard16(majorVersion);
    out.writeCard16(minorVersion);
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
    private short majorVersion;
    @java.lang.SuppressWarnings("all")
    private short minorVersion;

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
    public QueryVersionReply.QueryVersionReplyBuilder majorVersion(final short majorVersion) {
      this.majorVersion = majorVersion;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryVersionReply.QueryVersionReplyBuilder minorVersion(final short minorVersion) {
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
  QueryVersionReply(final short sequenceNumber, final short majorVersion, final short minorVersion) {
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
  public short getMajorVersion() {
    return this.majorVersion;
  }

  @java.lang.SuppressWarnings("all")
  public short getMinorVersion() {
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
