package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.ByteList;

public final class OverlayKey implements XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final ByteList over;
  private final ByteList under;

  public static OverlayKey readOverlayKey(X11Input in) throws IOException {
    OverlayKey.OverlayKeyBuilder javaBuilder = OverlayKey.builder();
    ByteList over = in.readChar(4);
    ByteList under = in.readChar(4);
    javaBuilder.over(over.toImmutable());
    javaBuilder.under(under.toImmutable());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeChar(over);
    out.writeChar(under);
  }

  @Override
  public int getSize() {
    return 0 + 1 * over.size() + 1 * under.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class OverlayKeyBuilder {
    @java.lang.SuppressWarnings("all")
    private ByteList over;
    @java.lang.SuppressWarnings("all")
    private ByteList under;

    public int getSize() {
      return 0 + 1 * over.size() + 1 * under.size();
    }

    @java.lang.SuppressWarnings("all")
    OverlayKeyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public OverlayKey.OverlayKeyBuilder over(final ByteList over) {
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
    public OverlayKey.OverlayKeyBuilder under(final ByteList under) {
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
      return "OverlayKey.OverlayKeyBuilder(over=" + this.over + ", under=" + this.under + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  OverlayKey(final ByteList over, final ByteList under) {
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
  public ByteList getOver() {
    return this.over;
  }

  @java.lang.SuppressWarnings("all")
  public ByteList getUnder() {
    return this.under;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof OverlayKey)) return false;
    final OverlayKey other = (OverlayKey) o;
    final java.lang.Object this$over = this.getOver();
    final java.lang.Object other$over = other.getOver();
    if (this$over == null ? other$over != null : !this$over.equals(other$over)) return false;
    final java.lang.Object this$under = this.getUnder();
    final java.lang.Object other$under = other.getUnder();
    if (this$under == null ? other$under != null : !this$under.equals(other$under)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final java.lang.Object $over = this.getOver();
    result = result * PRIME + ($over == null ? 43 : $over.hashCode());
    final java.lang.Object $under = this.getUnder();
    result = result * PRIME + ($under == null ? 43 : $under.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "OverlayKey(over=" + this.getOver() + ", under=" + this.getUnder() + ")";
  }
}
