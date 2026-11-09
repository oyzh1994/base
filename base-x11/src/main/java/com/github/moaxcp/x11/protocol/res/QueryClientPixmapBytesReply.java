package com.github.moaxcp.x11.protocol.res;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class QueryClientPixmapBytesReply implements XReply {
  public static final String PLUGIN_NAME = "res";
  private final short sequenceNumber;
  private final int bytes;
  private final int bytesOverflow;

  public static QueryClientPixmapBytesReply readQueryClientPixmapBytesReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    QueryClientPixmapBytesReply.QueryClientPixmapBytesReplyBuilder javaBuilder = QueryClientPixmapBytesReply.builder();
    int length = in.readCard32();
    int bytes = in.readCard32();
    int bytesOverflow = in.readCard32();
    in.readPad(16);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.bytes(bytes);
    javaBuilder.bytesOverflow(bytesOverflow);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(bytes);
    out.writeCard32(bytesOverflow);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryClientPixmapBytesReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int bytes;
    @java.lang.SuppressWarnings("all")
    private int bytesOverflow;

    public int getSize() {
      return 16;
    }

    @java.lang.SuppressWarnings("all")
    QueryClientPixmapBytesReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryClientPixmapBytesReply.QueryClientPixmapBytesReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryClientPixmapBytesReply.QueryClientPixmapBytesReplyBuilder bytes(final int bytes) {
      this.bytes = bytes;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryClientPixmapBytesReply.QueryClientPixmapBytesReplyBuilder bytesOverflow(final int bytesOverflow) {
      this.bytesOverflow = bytesOverflow;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryClientPixmapBytesReply build() {
      return new QueryClientPixmapBytesReply(this.sequenceNumber, this.bytes, this.bytesOverflow);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryClientPixmapBytesReply.QueryClientPixmapBytesReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", bytes=" + this.bytes + ", bytesOverflow=" + this.bytesOverflow + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryClientPixmapBytesReply(final short sequenceNumber, final int bytes, final int bytesOverflow) {
    this.sequenceNumber = sequenceNumber;
    this.bytes = bytes;
    this.bytesOverflow = bytesOverflow;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryClientPixmapBytesReply.QueryClientPixmapBytesReplyBuilder builder() {
    return new QueryClientPixmapBytesReply.QueryClientPixmapBytesReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getBytes() {
    return this.bytes;
  }

  @java.lang.SuppressWarnings("all")
  public int getBytesOverflow() {
    return this.bytesOverflow;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryClientPixmapBytesReply)) return false;
    final QueryClientPixmapBytesReply other = (QueryClientPixmapBytesReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getBytes() != other.getBytes()) return false;
    if (this.getBytesOverflow() != other.getBytesOverflow()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getBytes();
    result = result * PRIME + this.getBytesOverflow();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryClientPixmapBytesReply(sequenceNumber=" + this.getSequenceNumber() + ", bytes=" + this.getBytes() + ", bytesOverflow=" + this.getBytesOverflow() + ")";
  }
}
