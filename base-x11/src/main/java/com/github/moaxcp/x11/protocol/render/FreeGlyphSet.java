package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class FreeGlyphSet implements OneWayRequest {
  public static final String PLUGIN_NAME = "render";
  public static final byte OPCODE = 19;
  private final int glyphset;

  public byte getOpCode() {
    return OPCODE;
  }

  public static FreeGlyphSet readFreeGlyphSet(X11Input in) throws IOException {
    FreeGlyphSet.FreeGlyphSetBuilder javaBuilder = FreeGlyphSet.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int glyphset = in.readCard32();
    javaBuilder.glyphset(glyphset);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(glyphset);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class FreeGlyphSetBuilder {
    @java.lang.SuppressWarnings("all")
    private int glyphset;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    FreeGlyphSetBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public FreeGlyphSet.FreeGlyphSetBuilder glyphset(final int glyphset) {
      this.glyphset = glyphset;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public FreeGlyphSet build() {
      return new FreeGlyphSet(this.glyphset);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "FreeGlyphSet.FreeGlyphSetBuilder(glyphset=" + this.glyphset + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  FreeGlyphSet(final int glyphset) {
    this.glyphset = glyphset;
  }

  @java.lang.SuppressWarnings("all")
  public static FreeGlyphSet.FreeGlyphSetBuilder builder() {
    return new FreeGlyphSet.FreeGlyphSetBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getGlyphset() {
    return this.glyphset;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof FreeGlyphSet)) return false;
    final FreeGlyphSet other = (FreeGlyphSet) o;
    if (this.getGlyphset() != other.getGlyphset()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getGlyphset();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "FreeGlyphSet(glyphset=" + this.getGlyphset() + ")";
  }
}
