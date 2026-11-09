package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public final class Pictscreen implements XStruct {
  public static final String PLUGIN_NAME = "render";
  private final int fallback;
  private final ImmutableList<Pictdepth> depths;

  public static Pictscreen readPictscreen(X11Input in) throws IOException {
    Pictscreen.PictscreenBuilder javaBuilder = Pictscreen.builder();
    int numDepths = in.readCard32();
    int fallback = in.readCard32();
    MutableList<Pictdepth> depths = Lists.mutable.withInitialCapacity((int) (Integer.toUnsignedLong(numDepths)));
    for (int i = 0; i < Integer.toUnsignedLong(numDepths); i++) {
      depths.add(Pictdepth.readPictdepth(in));
    }
    javaBuilder.fallback(fallback);
    javaBuilder.depths(depths.toImmutable());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    int numDepths = depths.size();
    out.writeCard32(numDepths);
    out.writeCard32(fallback);
    for (Pictdepth t : depths) {
      t.write(out);
    }
  }

  @Override
  public int getSize() {
    return 8 + XObject.sizeOf(depths);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class PictscreenBuilder {
    @java.lang.SuppressWarnings("all")
    private int fallback;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<Pictdepth> depths;

    public int getSize() {
      return 8 + XObject.sizeOf(depths);
    }

    @java.lang.SuppressWarnings("all")
    PictscreenBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Pictscreen.PictscreenBuilder fallback(final int fallback) {
      this.fallback = fallback;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Pictscreen.PictscreenBuilder depths(final ImmutableList<Pictdepth> depths) {
      if (depths == null) {
        throw new java.lang.NullPointerException("depths is marked non-null but is null");
      }
      this.depths = depths;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Pictscreen build() {
      return new Pictscreen(this.fallback, this.depths);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Pictscreen.PictscreenBuilder(fallback=" + this.fallback + ", depths=" + this.depths + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Pictscreen(final int fallback, final ImmutableList<Pictdepth> depths) {
    if (depths == null) {
      throw new java.lang.NullPointerException("depths is marked non-null but is null");
    }
    this.fallback = fallback;
    this.depths = depths;
  }

  @java.lang.SuppressWarnings("all")
  public static Pictscreen.PictscreenBuilder builder() {
    return new Pictscreen.PictscreenBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getFallback() {
    return this.fallback;
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<Pictdepth> getDepths() {
    return this.depths;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Pictscreen)) return false;
    final Pictscreen other = (Pictscreen) o;
    if (this.getFallback() != other.getFallback()) return false;
    final java.lang.Object this$depths = this.getDepths();
    final java.lang.Object other$depths = other.getDepths();
    if (this$depths == null ? other$depths != null : !this$depths.equals(other$depths)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFallback();
    final java.lang.Object $depths = this.getDepths();
    result = result * PRIME + ($depths == null ? 43 : $depths.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Pictscreen(fallback=" + this.getFallback() + ", depths=" + this.getDepths() + ")";
  }
}
