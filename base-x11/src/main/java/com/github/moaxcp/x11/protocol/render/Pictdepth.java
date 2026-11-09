package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public final class Pictdepth implements XStruct {
  public static final String PLUGIN_NAME = "render";
  private final byte depth;
  private final ImmutableList<Pictvisual> visuals;

  public static Pictdepth readPictdepth(X11Input in) throws IOException {
    Pictdepth.PictdepthBuilder javaBuilder = Pictdepth.builder();
    byte depth = in.readCard8();
    byte[] pad1 = in.readPad(1);
    short numVisuals = in.readCard16();
    byte[] pad3 = in.readPad(4);
    MutableList<Pictvisual> visuals = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(numVisuals));
    for (int i = 0; i < Short.toUnsignedInt(numVisuals); i++) {
      visuals.add(Pictvisual.readPictvisual(in));
    }
    javaBuilder.depth(depth);
    javaBuilder.visuals(visuals.toImmutable());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(depth);
    out.writePad(1);
    short numVisuals = (short) visuals.size();
    out.writeCard16(numVisuals);
    out.writePad(4);
    for (Pictvisual t : visuals) {
      t.write(out);
    }
  }

  @Override
  public int getSize() {
    return 8 + XObject.sizeOf(visuals);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class PictdepthBuilder {
    @java.lang.SuppressWarnings("all")
    private byte depth;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<Pictvisual> visuals;

    public int getSize() {
      return 8 + XObject.sizeOf(visuals);
    }

    @java.lang.SuppressWarnings("all")
    PictdepthBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Pictdepth.PictdepthBuilder depth(final byte depth) {
      this.depth = depth;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Pictdepth.PictdepthBuilder visuals(final ImmutableList<Pictvisual> visuals) {
      if (visuals == null) {
        throw new java.lang.NullPointerException("visuals is marked non-null but is null");
      }
      this.visuals = visuals;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Pictdepth build() {
      return new Pictdepth(this.depth, this.visuals);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Pictdepth.PictdepthBuilder(depth=" + this.depth + ", visuals=" + this.visuals + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Pictdepth(final byte depth, final ImmutableList<Pictvisual> visuals) {
    if (visuals == null) {
      throw new java.lang.NullPointerException("visuals is marked non-null but is null");
    }
    this.depth = depth;
    this.visuals = visuals;
  }

  @java.lang.SuppressWarnings("all")
  public static Pictdepth.PictdepthBuilder builder() {
    return new Pictdepth.PictdepthBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getDepth() {
    return this.depth;
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<Pictvisual> getVisuals() {
    return this.visuals;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Pictdepth)) return false;
    final Pictdepth other = (Pictdepth) o;
    if (this.getDepth() != other.getDepth()) return false;
    final java.lang.Object this$visuals = this.getVisuals();
    final java.lang.Object other$visuals = other.getVisuals();
    if (this$visuals == null ? other$visuals != null : !this$visuals.equals(other$visuals)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDepth();
    final java.lang.Object $visuals = this.getVisuals();
    result = result * PRIME + ($visuals == null ? 43 : $visuals.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Pictdepth(depth=" + this.getDepth() + ", visuals=" + this.getVisuals() + ")";
  }
}
