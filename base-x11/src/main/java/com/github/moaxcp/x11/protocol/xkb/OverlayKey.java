package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class OverlayKey implements XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final byte[] over;
  private final byte[] under;

  public static OverlayKey readOverlayKey(X11Input in) throws IOException {
    OverlayKey.OverlayKeyBuilder javaBuilder = OverlayKey.builder();
    byte[] over = in.readChar(4);
    byte[] under = in.readChar(4);
    javaBuilder.over(over);
    javaBuilder.under(under);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeChar(over);
    out.writeChar(under);
  }

  @Override
  public int getSize() {
    return 0 + 1 * over.length + 1 * under.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class OverlayKeyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte[] over;
    @java.lang.SuppressWarnings("all")
    private byte[] under;

    public int getSize() {
      return 0 + 1 * over.length + 1 * under.length;
    }

    @java.lang.SuppressWarnings("all")
    OverlayKeyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public OverlayKey.OverlayKeyBuilder over(final byte[] over) {
      if (over == null) {
        throw new java.lang.NullPointerException("over is marked non-null but is null");
      }
      this.over = over;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public OverlayKey.OverlayKeyBuilder under(final byte[] under) {
      if (under == null) {
        throw new java.lang.NullPointerException("under is marked non-null but is null");
      }
      this.under = under;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public OverlayKey build() {
      return new OverlayKey(this.over, this.under);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "OverlayKey.OverlayKeyBuilder(over=" + java.util.Arrays.toString(this.over) + ", under=" + java.util.Arrays.toString(this.under) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  OverlayKey(final byte[] over, final byte[] under) {
    if (over == null) {
      throw new java.lang.NullPointerException("over is marked non-null but is null");
    }
    if (under == null) {
      throw new java.lang.NullPointerException("under is marked non-null but is null");
    }
    this.over = over;
    this.under = under;
  }

  @java.lang.SuppressWarnings("all")
  public static OverlayKey.OverlayKeyBuilder builder() {
    return new OverlayKey.OverlayKeyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getOver() {
    return this.over;
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getUnder() {
    return this.under;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof OverlayKey)) return false;
    final OverlayKey other = (OverlayKey) o;
    if (!java.util.Arrays.equals(this.getOver(), other.getOver())) return false;
    if (!java.util.Arrays.equals(this.getUnder(), other.getUnder())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + java.util.Arrays.hashCode(this.getOver());
    result = result * PRIME + java.util.Arrays.hashCode(this.getUnder());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "OverlayKey(over=" + java.util.Arrays.toString(this.getOver()) + ", under=" + java.util.Arrays.toString(this.getUnder()) + ")";
  }
}
