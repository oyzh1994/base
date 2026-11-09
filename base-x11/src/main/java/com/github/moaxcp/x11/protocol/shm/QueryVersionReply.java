package com.github.moaxcp.x11.protocol.shm;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class QueryVersionReply implements XReply {
  public static final String PLUGIN_NAME = "shm";
  private final boolean sharedPixmaps;
  private final short sequenceNumber;
  private final short majorVersion;
  private final short minorVersion;
  private final short uid;
  private final short gid;
  private final byte pixmapFormat;

  public static QueryVersionReply readQueryVersionReply(byte sharedPixmaps, short sequenceNumber, X11Input in) throws IOException {
    QueryVersionReply.QueryVersionReplyBuilder javaBuilder = QueryVersionReply.builder();
    int length = in.readCard32();
    short majorVersion = in.readCard16();
    short minorVersion = in.readCard16();
    short uid = in.readCard16();
    short gid = in.readCard16();
    byte pixmapFormat = in.readCard8();
    byte[] pad9 = in.readPad(15);
    javaBuilder.sharedPixmaps(sharedPixmaps > 0);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.majorVersion(majorVersion);
    javaBuilder.minorVersion(minorVersion);
    javaBuilder.uid(uid);
    javaBuilder.gid(gid);
    javaBuilder.pixmapFormat(pixmapFormat);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeBool(sharedPixmaps);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard16(majorVersion);
    out.writeCard16(minorVersion);
    out.writeCard16(uid);
    out.writeCard16(gid);
    out.writeCard8(pixmapFormat);
    out.writePad(15);
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
    private boolean sharedPixmaps;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private short majorVersion;
    @java.lang.SuppressWarnings("all")
    private short minorVersion;
    @java.lang.SuppressWarnings("all")
    private short uid;
    @java.lang.SuppressWarnings("all")
    private short gid;
    @java.lang.SuppressWarnings("all")
    private byte pixmapFormat;

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
    public QueryVersionReply.QueryVersionReplyBuilder sharedPixmaps(final boolean sharedPixmaps) {
      this.sharedPixmaps = sharedPixmaps;
      return this;
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

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryVersionReply.QueryVersionReplyBuilder uid(final short uid) {
      this.uid = uid;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryVersionReply.QueryVersionReplyBuilder gid(final short gid) {
      this.gid = gid;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryVersionReply.QueryVersionReplyBuilder pixmapFormat(final byte pixmapFormat) {
      this.pixmapFormat = pixmapFormat;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryVersionReply build() {
      return new QueryVersionReply(this.sharedPixmaps, this.sequenceNumber, this.majorVersion, this.minorVersion, this.uid, this.gid, this.pixmapFormat);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryVersionReply.QueryVersionReplyBuilder(sharedPixmaps=" + this.sharedPixmaps + ", sequenceNumber=" + this.sequenceNumber + ", majorVersion=" + this.majorVersion + ", minorVersion=" + this.minorVersion + ", uid=" + this.uid + ", gid=" + this.gid + ", pixmapFormat=" + this.pixmapFormat + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryVersionReply(final boolean sharedPixmaps, final short sequenceNumber, final short majorVersion, final short minorVersion, final short uid, final short gid, final byte pixmapFormat) {
    this.sharedPixmaps = sharedPixmaps;
    this.sequenceNumber = sequenceNumber;
    this.majorVersion = majorVersion;
    this.minorVersion = minorVersion;
    this.uid = uid;
    this.gid = gid;
    this.pixmapFormat = pixmapFormat;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryVersionReply.QueryVersionReplyBuilder builder() {
    return new QueryVersionReply.QueryVersionReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public boolean isSharedPixmaps() {
    return this.sharedPixmaps;
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

  @java.lang.SuppressWarnings("all")
  public short getUid() {
    return this.uid;
  }

  @java.lang.SuppressWarnings("all")
  public short getGid() {
    return this.gid;
  }

  @java.lang.SuppressWarnings("all")
  public byte getPixmapFormat() {
    return this.pixmapFormat;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryVersionReply)) return false;
    final QueryVersionReply other = (QueryVersionReply) o;
    if (this.isSharedPixmaps() != other.isSharedPixmaps()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getMajorVersion() != other.getMajorVersion()) return false;
    if (this.getMinorVersion() != other.getMinorVersion()) return false;
    if (this.getUid() != other.getUid()) return false;
    if (this.getGid() != other.getGid()) return false;
    if (this.getPixmapFormat() != other.getPixmapFormat()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + (this.isSharedPixmaps() ? 79 : 97);
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getMajorVersion();
    result = result * PRIME + this.getMinorVersion();
    result = result * PRIME + this.getUid();
    result = result * PRIME + this.getGid();
    result = result * PRIME + this.getPixmapFormat();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryVersionReply(sharedPixmaps=" + this.isSharedPixmaps() + ", sequenceNumber=" + this.getSequenceNumber() + ", majorVersion=" + this.getMajorVersion() + ", minorVersion=" + this.getMinorVersion() + ", uid=" + this.getUid() + ", gid=" + this.getGid() + ", pixmapFormat=" + this.getPixmapFormat() + ")";
  }
}
