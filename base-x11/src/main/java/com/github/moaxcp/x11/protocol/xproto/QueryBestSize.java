package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class QueryBestSize implements TwoWayRequest<QueryBestSizeReply> {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 97;
  private final byte clazz;
  private final int drawable;
  private final short width;
  private final short height;

  public XReplyFunction<QueryBestSizeReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryBestSizeReply.readQueryBestSizeReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryBestSize readQueryBestSize(X11Input in) throws IOException {
    QueryBestSize.QueryBestSizeBuilder javaBuilder = QueryBestSize.builder();
    byte clazz = in.readCard8();
    short length = in.readCard16();
    int drawable = in.readCard32();
    short width = in.readCard16();
    short height = in.readCard16();
    javaBuilder.clazz(clazz);
    javaBuilder.drawable(drawable);
    javaBuilder.width(width);
    javaBuilder.height(height);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard8(clazz);
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
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


  public static class QueryBestSizeBuilder {
    @java.lang.SuppressWarnings("all")
    private byte clazz;
    @java.lang.SuppressWarnings("all")
    private int drawable;
    @java.lang.SuppressWarnings("all")
    private short width;
    @java.lang.SuppressWarnings("all")
    private short height;

    public QueryBestSize.QueryBestSizeBuilder clazz(QueryShapeOf clazz) {
      this.clazz = (byte) clazz.getValue();
      return this;
    }

    public QueryBestSize.QueryBestSizeBuilder clazz(byte clazz) {
      this.clazz = clazz;
      return this;
    }

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    QueryBestSizeBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryBestSize.QueryBestSizeBuilder drawable(final int drawable) {
      this.drawable = drawable;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryBestSize.QueryBestSizeBuilder width(final short width) {
      this.width = width;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryBestSize.QueryBestSizeBuilder height(final short height) {
      this.height = height;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryBestSize build() {
      return new QueryBestSize(this.clazz, this.drawable, this.width, this.height);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryBestSize.QueryBestSizeBuilder(clazz=" + this.clazz + ", drawable=" + this.drawable + ", width=" + this.width + ", height=" + this.height + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryBestSize(final byte clazz, final int drawable, final short width, final short height) {
    this.clazz = clazz;
    this.drawable = drawable;
    this.width = width;
    this.height = height;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryBestSize.QueryBestSizeBuilder builder() {
    return new QueryBestSize.QueryBestSizeBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getClazz() {
    return this.clazz;
  }

  @java.lang.SuppressWarnings("all")
  public int getDrawable() {
    return this.drawable;
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
    if (!(o instanceof QueryBestSize)) return false;
    final QueryBestSize other = (QueryBestSize) o;
    if (this.getClazz() != other.getClazz()) return false;
    if (this.getDrawable() != other.getDrawable()) return false;
    if (this.getWidth() != other.getWidth()) return false;
    if (this.getHeight() != other.getHeight()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getClazz();
    result = result * PRIME + this.getDrawable();
    result = result * PRIME + this.getWidth();
    result = result * PRIME + this.getHeight();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryBestSize(clazz=" + this.getClazz() + ", drawable=" + this.getDrawable() + ", width=" + this.getWidth() + ", height=" + this.getHeight() + ")";
  }
}
