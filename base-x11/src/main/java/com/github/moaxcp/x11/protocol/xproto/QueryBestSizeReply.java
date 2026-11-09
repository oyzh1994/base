package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class QueryBestSizeReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";
  private final short sequenceNumber;
  private final short width;
  private final short height;

  public static QueryBestSizeReply readQueryBestSizeReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    QueryBestSizeReply.QueryBestSizeReplyBuilder javaBuilder = QueryBestSizeReply.builder();
    int length = in.readCard32();
    short width = in.readCard16();
    short height = in.readCard16();
    in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.width(width);
    javaBuilder.height(height);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard16(width);
    out.writeCard16(height);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryBestSizeReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private short width;
    @java.lang.SuppressWarnings("all")
    private short height;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    QueryBestSizeReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryBestSizeReply.QueryBestSizeReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryBestSizeReply.QueryBestSizeReplyBuilder width(final short width) {
      this.width = width;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryBestSizeReply.QueryBestSizeReplyBuilder height(final short height) {
      this.height = height;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryBestSizeReply build() {
      return new QueryBestSizeReply(this.sequenceNumber, this.width, this.height);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryBestSizeReply.QueryBestSizeReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", width=" + this.width + ", height=" + this.height + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryBestSizeReply(final short sequenceNumber, final short width, final short height) {
    this.sequenceNumber = sequenceNumber;
    this.width = width;
    this.height = height;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryBestSizeReply.QueryBestSizeReplyBuilder builder() {
    return new QueryBestSizeReply.QueryBestSizeReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public short getWidth() {
    return this.width;
  }

  @java.lang.SuppressWarnings("all")
  public short getHeight() {
    return this.height;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryBestSizeReply)) return false;
    final QueryBestSizeReply other = (QueryBestSizeReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getWidth() != other.getWidth()) return false;
    if (this.getHeight() != other.getHeight()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getWidth();
    result = result * PRIME + this.getHeight();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryBestSizeReply(sequenceNumber=" + this.getSequenceNumber() + ", width=" + this.getWidth() + ", height=" + this.getHeight() + ")";
  }
}
