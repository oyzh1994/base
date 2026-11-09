package com.github.moaxcp.x11.protocol.xproto;

import java.util.ArrayList;
import java.util.List;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class Depth implements XStruct {
  public static final String PLUGIN_NAME = "xproto";
  private final byte depth;
  private final List<Visualtype> visuals;

  public static Depth readDepth(X11Input in) throws IOException {
    Depth.DepthBuilder javaBuilder = Depth.builder();
    byte depth = in.readCard8();
    byte[] pad1 = in.readPad(1);
    short visualsLen = in.readCard16();
    byte[] pad3 = in.readPad(4);
    List<Visualtype> visuals = new ArrayList<>(Short.toUnsignedInt(visualsLen));
    for (int i = 0; i < Short.toUnsignedInt(visualsLen); i++) {
      visuals.add(Visualtype.readVisualtype(in));
    }
    javaBuilder.depth(depth);
    javaBuilder.visuals(List.copyOf(visuals));
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(depth);
    out.writePad(1);
    short visualsLen = (short) visuals.size();
    out.writeCard16(visualsLen);
    out.writePad(4);
    for (Visualtype t : visuals) {
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


  public static class DepthBuilder {
    @java.lang.SuppressWarnings("all")
    private byte depth;
    @java.lang.SuppressWarnings("all")
    private List<Visualtype> visuals;

    public int getSize() {
      return 8 + XObject.sizeOf(visuals);
    }

    @java.lang.SuppressWarnings("all")
    DepthBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Depth.DepthBuilder depth(final byte depth) {
      this.depth = depth;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Depth.DepthBuilder visuals(final List<Visualtype> visuals) {
      if (visuals == null) {
        throw new java.lang.NullPointerException("visuals is marked non-null but is null");
      }
      this.visuals = visuals;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Depth build() {
      return new Depth(this.depth, this.visuals);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Depth.DepthBuilder(depth=" + this.depth + ", visuals=" + this.visuals + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Depth(final byte depth, final List<Visualtype> visuals) {
    if (visuals == null) {
      throw new java.lang.NullPointerException("visuals is marked non-null but is null");
    }
    this.depth = depth;
    this.visuals = visuals;
  }

  @java.lang.SuppressWarnings("all")
  public static Depth.DepthBuilder builder() {
    return new Depth.DepthBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getDepth() {
    return this.depth;
  }

  @java.lang.SuppressWarnings("all")
  public List<Visualtype> getVisuals() {
    return this.visuals;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Depth)) return false;
    final Depth other = (Depth) o;
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
    return "Depth(depth=" + this.getDepth() + ", visuals=" + this.getVisuals() + ")";
  }
}
