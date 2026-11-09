package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.IntList;

public final class QueryColors implements TwoWayRequest<QueryColorsReply> {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 91;
  private final int cmap;
  private final IntList pixels;

  public XReplyFunction<QueryColorsReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryColorsReply.readQueryColorsReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryColors readQueryColors(X11Input in) throws IOException {
    QueryColors.QueryColorsBuilder javaBuilder = QueryColors.builder();
    int javaStart = 1;
    byte[] pad1 = in.readPad(1);
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int cmap = in.readCard32();
    javaStart += 4;
    IntList pixels = in.readCard32(Short.toUnsignedInt(length) - javaStart);
    javaBuilder.cmap(cmap);
    javaBuilder.pixels(pixels.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(cmap);
    out.writeCard32(pixels);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 8 + 4 * pixels.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryColorsBuilder {
    @java.lang.SuppressWarnings("all")
    private int cmap;
    @java.lang.SuppressWarnings("all")
    private IntList pixels;

    public int getSize() {
      return 8 + 4 * pixels.size();
    }

    @java.lang.SuppressWarnings("all")
    QueryColorsBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryColors.QueryColorsBuilder cmap(final int cmap) {
      this.cmap = cmap;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryColors.QueryColorsBuilder pixels(final IntList pixels) {
      if (pixels == null) {
        throw new java.lang.NullPointerException("pixels is marked non-null but is null");
      }
      this.pixels = pixels;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryColors build() {
      return new QueryColors(this.cmap, this.pixels);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryColors.QueryColorsBuilder(cmap=" + this.cmap + ", pixels=" + this.pixels + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryColors(final int cmap, final IntList pixels) {
    if (pixels == null) {
      throw new java.lang.NullPointerException("pixels is marked non-null but is null");
    }
    this.cmap = cmap;
    this.pixels = pixels;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryColors.QueryColorsBuilder builder() {
    return new QueryColors.QueryColorsBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getCmap() {
    return this.cmap;
  }

  @java.lang.SuppressWarnings("all")
  public IntList getPixels() {
    return this.pixels;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryColors)) return false;
    final QueryColors other = (QueryColors) o;
    if (this.getCmap() != other.getCmap()) return false;
    final java.lang.Object this$pixels = this.getPixels();
    final java.lang.Object other$pixels = other.getPixels();
    if (this$pixels == null ? other$pixels != null : !this$pixels.equals(other$pixels)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getCmap();
    final java.lang.Object $pixels = this.getPixels();
    result = result * PRIME + ($pixels == null ? 43 : $pixels.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryColors(cmap=" + this.getCmap() + ", pixels=" + this.getPixels() + ")";
  }
}
