package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public final class QueryColorsReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";
  private final short sequenceNumber;
  private final ImmutableList<Rgb> colors;

  public static QueryColorsReply readQueryColorsReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    QueryColorsReply.QueryColorsReplyBuilder javaBuilder = QueryColorsReply.builder();
    int length = in.readCard32();
    short colorsLen = in.readCard16();
    byte[] pad5 = in.readPad(22);
    MutableList<Rgb> colors = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(colorsLen));
    for (int i = 0; i < Short.toUnsignedInt(colorsLen); i++) {
      colors.add(Rgb.readRgb(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.colors(colors.toImmutable());
    if (javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    short colorsLen = (short) colors.size();
    out.writeCard16(colorsLen);
    out.writePad(22);
    for (Rgb t : colors) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(colors);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryColorsReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<Rgb> colors;

    public int getSize() {
      return 32 + XObject.sizeOf(colors);
    }

    @java.lang.SuppressWarnings("all")
    QueryColorsReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryColorsReply.QueryColorsReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryColorsReply.QueryColorsReplyBuilder colors(final ImmutableList<Rgb> colors) {
      if (colors == null) {
        throw new java.lang.NullPointerException("colors is marked non-null but is null");
      }
      this.colors = colors;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryColorsReply build() {
      return new QueryColorsReply(this.sequenceNumber, this.colors);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryColorsReply.QueryColorsReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", colors=" + this.colors + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryColorsReply(final short sequenceNumber, final ImmutableList<Rgb> colors) {
    if (colors == null) {
      throw new java.lang.NullPointerException("colors is marked non-null but is null");
    }
    this.sequenceNumber = sequenceNumber;
    this.colors = colors;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryColorsReply.QueryColorsReplyBuilder builder() {
    return new QueryColorsReply.QueryColorsReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<Rgb> getColors() {
    return this.colors;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryColorsReply)) return false;
    final QueryColorsReply other = (QueryColorsReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    final java.lang.Object this$colors = this.getColors();
    final java.lang.Object other$colors = other.getColors();
    if (this$colors == null ? other$colors != null : !this$colors.equals(other$colors)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    final java.lang.Object $colors = this.getColors();
    result = result * PRIME + ($colors == null ? 43 : $colors.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryColorsReply(sequenceNumber=" + this.getSequenceNumber() + ", colors=" + this.getColors() + ")";
  }
}
