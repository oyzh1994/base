package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.IntList;

public final class FreeColors implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 88;
  private final int cmap;
  private final int planeMask;
  private final IntList pixels;

  public byte getOpCode() {
    return OPCODE;
  }

  public static FreeColors readFreeColors(X11Input in) throws IOException {
    FreeColors.FreeColorsBuilder javaBuilder = FreeColors.builder();
    int javaStart = 1;
    byte[] pad1 = in.readPad(1);
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int cmap = in.readCard32();
    javaStart += 4;
    int planeMask = in.readCard32();
    javaStart += 4;
    IntList pixels = in.readCard32(Short.toUnsignedInt(length) - javaStart);
    javaBuilder.cmap(cmap);
    javaBuilder.planeMask(planeMask);
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
    out.writeCard32(planeMask);
    out.writeCard32(pixels);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + 4 * pixels.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class FreeColorsBuilder {
    @java.lang.SuppressWarnings("all")
    private int cmap;
    @java.lang.SuppressWarnings("all")
    private int planeMask;
    @java.lang.SuppressWarnings("all")
    private IntList pixels;

    public int getSize() {
      return 12 + 4 * pixels.size();
    }

    @java.lang.SuppressWarnings("all")
    FreeColorsBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public FreeColors.FreeColorsBuilder cmap(final int cmap) {
      this.cmap = cmap;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public FreeColors.FreeColorsBuilder planeMask(final int planeMask) {
      this.planeMask = planeMask;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public FreeColors.FreeColorsBuilder pixels(final IntList pixels) {
      if (pixels == null) {
        throw new java.lang.NullPointerException("pixels is marked non-null but is null");
      }
      this.pixels = pixels;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public FreeColors build() {
      return new FreeColors(this.cmap, this.planeMask, this.pixels);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "FreeColors.FreeColorsBuilder(cmap=" + this.cmap + ", planeMask=" + this.planeMask + ", pixels=" + this.pixels + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  FreeColors(final int cmap, final int planeMask, final IntList pixels) {
    if (pixels == null) {
      throw new java.lang.NullPointerException("pixels is marked non-null but is null");
    }
    this.cmap = cmap;
    this.planeMask = planeMask;
    this.pixels = pixels;
  }

  @java.lang.SuppressWarnings("all")
  public static FreeColors.FreeColorsBuilder builder() {
    return new FreeColors.FreeColorsBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getCmap() {
    return this.cmap;
  }

  @java.lang.SuppressWarnings("all")
  public int getPlaneMask() {
    return this.planeMask;
  }

  @java.lang.SuppressWarnings("all")
  public IntList getPixels() {
    return this.pixels;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof FreeColors)) return false;
    final FreeColors other = (FreeColors) o;
    if (this.getCmap() != other.getCmap()) return false;
    if (this.getPlaneMask() != other.getPlaneMask()) return false;
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
    result = result * PRIME + this.getPlaneMask();
    final java.lang.Object $pixels = this.getPixels();
    result = result * PRIME + ($pixels == null ? 43 : $pixels.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "FreeColors(cmap=" + this.getCmap() + ", planeMask=" + this.getPlaneMask() + ", pixels=" + this.getPixels() + ")";
  }
}
