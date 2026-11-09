package com.github.moaxcp.x11.protocol.xproto;

import java.util.ArrayList;
import java.util.List;
import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class QueryTextExtents implements TwoWayRequest<QueryTextExtentsReply> {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 48;
  private final int font;
  private final List<Char2b> string;

  public XReplyFunction<QueryTextExtentsReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryTextExtentsReply.readQueryTextExtentsReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryTextExtents readQueryTextExtents(X11Input in) throws IOException {
    QueryTextExtents.QueryTextExtentsBuilder javaBuilder = QueryTextExtents.builder();
    int javaStart = 1;
    boolean oddLength = in.readBool();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int font = in.readCard32();
    javaStart += 4;
    List<Char2b> string = new ArrayList<>(Short.toUnsignedInt(length) - javaStart);
    while (javaStart < Short.toUnsignedInt(length) * 4) {
      Char2b baseObject = Char2b.readChar2b(in);
      string.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.font(font);
    javaBuilder.string(List.copyOf(string));
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeBool(getOddLength());
    out.writeCard16((short) getLength());
    out.writeCard32(font);
    for (Char2b t : string) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  public boolean getOddLength() {
    return ((string.size()) & (1)) > 0;
  }

  @Override
  public int getSize() {
    return 8 + XObject.sizeOf(string);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryTextExtentsBuilder {
    @java.lang.SuppressWarnings("all")
    private int font;
    @java.lang.SuppressWarnings("all")
    private List<Char2b> string;

    public int getSize() {
      return 8 + XObject.sizeOf(string);
    }

    @java.lang.SuppressWarnings("all")
    QueryTextExtentsBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryTextExtents.QueryTextExtentsBuilder font(final int font) {
      this.font = font;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryTextExtents.QueryTextExtentsBuilder string(final List<Char2b> string) {
      if (string == null) {
        throw new java.lang.NullPointerException("string is marked non-null but is null");
      }
      this.string = string;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryTextExtents build() {
      return new QueryTextExtents(this.font, this.string);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryTextExtents.QueryTextExtentsBuilder(font=" + this.font + ", string=" + this.string + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryTextExtents(final int font, final List<Char2b> string) {
    if (string == null) {
      throw new java.lang.NullPointerException("string is marked non-null but is null");
    }
    this.font = font;
    this.string = string;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryTextExtents.QueryTextExtentsBuilder builder() {
    return new QueryTextExtents.QueryTextExtentsBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getFont() {
    return this.font;
  }

  @java.lang.SuppressWarnings("all")
  public List<Char2b> getString() {
    return this.string;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryTextExtents)) return false;
    final QueryTextExtents other = (QueryTextExtents) o;
    if (this.getFont() != other.getFont()) return false;
    final java.lang.Object this$string = this.getString();
    final java.lang.Object other$string = other.getString();
    if (this$string == null ? other$string != null : !this$string.equals(other$string)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFont();
    final java.lang.Object $string = this.getString();
    result = result * PRIME + ($string == null ? 43 : $string.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryTextExtents(font=" + this.getFont() + ", string=" + this.getString() + ")";
  }
}
