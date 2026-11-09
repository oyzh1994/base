package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class QueryFont implements TwoWayRequest<QueryFontReply> {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 47;
  private final int font;

  public XReplyFunction<QueryFontReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryFontReply.readQueryFontReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryFont readQueryFont(X11Input in) throws IOException {
    QueryFont.QueryFontBuilder javaBuilder = QueryFont.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int font = in.readCard32();
    javaBuilder.font(font);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(font);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryFontBuilder {
    @java.lang.SuppressWarnings("all")
    private int font;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    QueryFontBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryFont.QueryFontBuilder font(final int font) {
      this.font = font;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryFont build() {
      return new QueryFont(this.font);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryFont.QueryFontBuilder(font=" + this.font + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryFont(final int font) {
    this.font = font;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryFont.QueryFontBuilder builder() {
    return new QueryFont.QueryFontBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getFont() {
    return this.font;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryFont)) return false;
    final QueryFont other = (QueryFont) o;
    if (this.getFont() != other.getFont()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFont();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryFont(font=" + this.getFont() + ")";
  }
}
