package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.IntList;

public final class FreeGlyphs implements OneWayRequest {
  public static final String PLUGIN_NAME = "render";
  public static final byte OPCODE = 22;
  private final int glyphset;
  private final IntList glyphs;

  public byte getOpCode() {
    return OPCODE;
  }

  public static FreeGlyphs readFreeGlyphs(X11Input in) throws IOException {
    FreeGlyphs.FreeGlyphsBuilder javaBuilder = FreeGlyphs.builder();
    int javaStart = 1;
    byte majorOpcode = in.readCard8();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int glyphset = in.readCard32();
    javaStart += 4;
    IntList glyphs = in.readCard32(Short.toUnsignedInt(length) - javaStart);
    javaBuilder.glyphset(glyphset);
    javaBuilder.glyphs(glyphs.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(glyphset);
    out.writeCard32(glyphs);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 8 + 4 * glyphs.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class FreeGlyphsBuilder {
    @java.lang.SuppressWarnings("all")
    private int glyphset;
    @java.lang.SuppressWarnings("all")
    private IntList glyphs;

    public int getSize() {
      return 8 + 4 * glyphs.size();
    }

    @java.lang.SuppressWarnings("all")
    FreeGlyphsBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public FreeGlyphs.FreeGlyphsBuilder glyphset(final int glyphset) {
      this.glyphset = glyphset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public FreeGlyphs.FreeGlyphsBuilder glyphs(final IntList glyphs) {
      if (glyphs == null) {
        throw new java.lang.NullPointerException("glyphs is marked non-null but is null");
      }
      this.glyphs = glyphs;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public FreeGlyphs build() {
      return new FreeGlyphs(this.glyphset, this.glyphs);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "FreeGlyphs.FreeGlyphsBuilder(glyphset=" + this.glyphset + ", glyphs=" + this.glyphs + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  FreeGlyphs(final int glyphset, final IntList glyphs) {
    if (glyphs == null) {
      throw new java.lang.NullPointerException("glyphs is marked non-null but is null");
    }
    this.glyphset = glyphset;
    this.glyphs = glyphs;
  }

  @java.lang.SuppressWarnings("all")
  public static FreeGlyphs.FreeGlyphsBuilder builder() {
    return new FreeGlyphs.FreeGlyphsBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getGlyphset() {
    return this.glyphset;
  }

  @java.lang.SuppressWarnings("all")
  public IntList getGlyphs() {
    return this.glyphs;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof FreeGlyphs)) return false;
    final FreeGlyphs other = (FreeGlyphs) o;
    if (this.getGlyphset() != other.getGlyphset()) return false;
    final java.lang.Object this$glyphs = this.getGlyphs();
    final java.lang.Object other$glyphs = other.getGlyphs();
    if (this$glyphs == null ? other$glyphs != null : !this$glyphs.equals(other$glyphs)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getGlyphset();
    final java.lang.Object $glyphs = this.getGlyphs();
    result = result * PRIME + ($glyphs == null ? 43 : $glyphs.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "FreeGlyphs(glyphset=" + this.getGlyphset() + ", glyphs=" + this.getGlyphs() + ")";
  }
}
