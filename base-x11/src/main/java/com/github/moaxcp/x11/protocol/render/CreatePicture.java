package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.xproto.SubwindowMode;
import java.io.IOException;

public final class CreatePicture implements OneWayRequest {
  public static final String PLUGIN_NAME = "render";
  public static final byte OPCODE = 4;
  private final int pid;
  private final int drawable;
  private final int format;
  private final int valueMask;
  private final int repeat;
  private final int alphamap;
  private final int alphaxorigin;
  private final int alphayorigin;
  private final int clipxorigin;
  private final int clipyorigin;
  private final int clipmask;
  private final int graphicsexposure;
  private final int subwindowmode;
  private final int polyedge;
  private final int polymode;
  private final int dither;
  private final int componentalpha;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreatePicture readCreatePicture(X11Input in) throws IOException {
    CreatePicture.CreatePictureBuilder javaBuilder = CreatePicture.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int pid = in.readCard32();
    int drawable = in.readCard32();
    int format = in.readCard32();
    int valueMask = in.readCard32();
    int repeat = 0;
    int alphamap = 0;
    int alphaxorigin = 0;
    int alphayorigin = 0;
    int clipxorigin = 0;
    int clipyorigin = 0;
    int clipmask = 0;
    int graphicsexposure = 0;
    int subwindowmode = 0;
    int polyedge = 0;
    int polymode = 0;
    int dither = 0;
    int componentalpha = 0;
    javaBuilder.pid(pid);
    javaBuilder.drawable(drawable);
    javaBuilder.format(format);
    javaBuilder.valueMask(valueMask);
    if (Cp.REPEAT.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      repeat = in.readCard32();
      javaBuilder.repeat(repeat);
    }
    if (Cp.ALPHA_MAP.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      alphamap = in.readCard32();
      javaBuilder.alphamap(alphamap);
    }
    if (Cp.ALPHA_X_ORIGIN.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      alphaxorigin = in.readInt32();
      javaBuilder.alphaxorigin(alphaxorigin);
    }
    if (Cp.ALPHA_Y_ORIGIN.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      alphayorigin = in.readInt32();
      javaBuilder.alphayorigin(alphayorigin);
    }
    if (Cp.CLIP_X_ORIGIN.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      clipxorigin = in.readInt32();
      javaBuilder.clipxorigin(clipxorigin);
    }
    if (Cp.CLIP_Y_ORIGIN.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      clipyorigin = in.readInt32();
      javaBuilder.clipyorigin(clipyorigin);
    }
    if (Cp.CLIP_MASK.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      clipmask = in.readCard32();
      javaBuilder.clipmask(clipmask);
    }
    if (Cp.GRAPHICS_EXPOSURE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      graphicsexposure = in.readCard32();
      javaBuilder.graphicsexposure(graphicsexposure);
    }
    if (Cp.SUBWINDOW_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      subwindowmode = in.readCard32();
      javaBuilder.subwindowmode(subwindowmode);
    }
    if (Cp.POLY_EDGE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      polyedge = in.readCard32();
      javaBuilder.polyedge(polyedge);
    }
    if (Cp.POLY_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      polymode = in.readCard32();
      javaBuilder.polymode(polymode);
    }
    if (Cp.DITHER.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      dither = in.readCard32();
      javaBuilder.dither(dither);
    }
    if (Cp.COMPONENT_ALPHA.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      componentalpha = in.readCard32();
      javaBuilder.componentalpha(componentalpha);
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(pid);
    out.writeCard32(drawable);
    out.writeCard32(format);
    out.writeCard32(valueMask);
    if (Cp.REPEAT.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(repeat);
    }
    if (Cp.ALPHA_MAP.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(alphamap);
    }
    if (Cp.ALPHA_X_ORIGIN.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeInt32(alphaxorigin);
    }
    if (Cp.ALPHA_Y_ORIGIN.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeInt32(alphayorigin);
    }
    if (Cp.CLIP_X_ORIGIN.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeInt32(clipxorigin);
    }
    if (Cp.CLIP_Y_ORIGIN.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeInt32(clipyorigin);
    }
    if (Cp.CLIP_MASK.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(clipmask);
    }
    if (Cp.GRAPHICS_EXPOSURE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(graphicsexposure);
    }
    if (Cp.SUBWINDOW_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(subwindowmode);
    }
    if (Cp.POLY_EDGE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(polyedge);
    }
    if (Cp.POLY_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(polymode);
    }
    if (Cp.DITHER.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(dither);
    }
    if (Cp.COMPONENT_ALPHA.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(componentalpha);
    }
    out.writePadAlign(getSize());
  }

  public boolean isValueMaskEnabled(Cp... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (Cp m : maskEnums) {
      if (!m.isEnabled(valueMask)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 20 + (Cp.REPEAT.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.ALPHA_MAP.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.ALPHA_X_ORIGIN.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.ALPHA_Y_ORIGIN.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.CLIP_X_ORIGIN.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.CLIP_Y_ORIGIN.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.CLIP_MASK.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.GRAPHICS_EXPOSURE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.SUBWINDOW_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.POLY_EDGE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.POLY_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.DITHER.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.COMPONENT_ALPHA.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CreatePictureBuilder {
    @java.lang.SuppressWarnings("all")
    private int pid;
    @java.lang.SuppressWarnings("all")
    private int drawable;
    @java.lang.SuppressWarnings("all")
    private int format;
    @java.lang.SuppressWarnings("all")
    private int valueMask;
    @java.lang.SuppressWarnings("all")
    private int repeat;
    @java.lang.SuppressWarnings("all")
    private int alphamap;
    @java.lang.SuppressWarnings("all")
    private int alphaxorigin;
    @java.lang.SuppressWarnings("all")
    private int alphayorigin;
    @java.lang.SuppressWarnings("all")
    private int clipxorigin;
    @java.lang.SuppressWarnings("all")
    private int clipyorigin;
    @java.lang.SuppressWarnings("all")
    private int clipmask;
    @java.lang.SuppressWarnings("all")
    private int graphicsexposure;
    @java.lang.SuppressWarnings("all")
    private int subwindowmode;
    @java.lang.SuppressWarnings("all")
    private int polyedge;
    @java.lang.SuppressWarnings("all")
    private int polymode;
    @java.lang.SuppressWarnings("all")
    private int dither;
    @java.lang.SuppressWarnings("all")
    private int componentalpha;

    private CreatePicture.CreatePictureBuilder valueMask(int valueMask) {
      this.valueMask = valueMask;
      return this;
    }

    public boolean isValueMaskEnabled(Cp... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (Cp m : maskEnums) {
        if (!m.isEnabled(valueMask)) {
          return false;
        }
      }
      return true;
    }

    private CreatePicture.CreatePictureBuilder valueMaskEnable(Cp... maskEnums) {
      for (Cp m : maskEnums) {
        valueMask((int) m.enableFor(valueMask));
      }
      return this;
    }

    private CreatePicture.CreatePictureBuilder valueMaskDisable(Cp... maskEnums) {
      for (Cp m : maskEnums) {
        valueMask((int) m.disableFor(valueMask));
      }
      return this;
    }

    public CreatePicture.CreatePictureBuilder repeat(int repeat) {
      this.repeat = repeat;
      valueMaskEnable(Cp.REPEAT);
      return this;
    }

    public CreatePicture.CreatePictureBuilder repeat(Repeat repeat) {
      this.repeat = (int) repeat.getValue();
      valueMaskEnable(Cp.REPEAT);
      return this;
    }

    public CreatePicture.CreatePictureBuilder alphamap(int alphamap) {
      this.alphamap = alphamap;
      valueMaskEnable(Cp.ALPHA_MAP);
      return this;
    }

    public CreatePicture.CreatePictureBuilder alphaxorigin(int alphaxorigin) {
      this.alphaxorigin = alphaxorigin;
      valueMaskEnable(Cp.ALPHA_X_ORIGIN);
      return this;
    }

    public CreatePicture.CreatePictureBuilder alphayorigin(int alphayorigin) {
      this.alphayorigin = alphayorigin;
      valueMaskEnable(Cp.ALPHA_Y_ORIGIN);
      return this;
    }

    public CreatePicture.CreatePictureBuilder clipxorigin(int clipxorigin) {
      this.clipxorigin = clipxorigin;
      valueMaskEnable(Cp.CLIP_X_ORIGIN);
      return this;
    }

    public CreatePicture.CreatePictureBuilder clipyorigin(int clipyorigin) {
      this.clipyorigin = clipyorigin;
      valueMaskEnable(Cp.CLIP_Y_ORIGIN);
      return this;
    }

    public CreatePicture.CreatePictureBuilder clipmask(int clipmask) {
      this.clipmask = clipmask;
      valueMaskEnable(Cp.CLIP_MASK);
      return this;
    }

    public CreatePicture.CreatePictureBuilder graphicsexposure(int graphicsexposure) {
      this.graphicsexposure = graphicsexposure;
      valueMaskEnable(Cp.GRAPHICS_EXPOSURE);
      return this;
    }

    public CreatePicture.CreatePictureBuilder subwindowmode(int subwindowmode) {
      this.subwindowmode = subwindowmode;
      valueMaskEnable(Cp.SUBWINDOW_MODE);
      return this;
    }

    public CreatePicture.CreatePictureBuilder subwindowmode(SubwindowMode subwindowmode) {
      this.subwindowmode = (int) subwindowmode.getValue();
      valueMaskEnable(Cp.SUBWINDOW_MODE);
      return this;
    }

    public CreatePicture.CreatePictureBuilder polyedge(int polyedge) {
      this.polyedge = polyedge;
      valueMaskEnable(Cp.POLY_EDGE);
      return this;
    }

    public CreatePicture.CreatePictureBuilder polyedge(PolyEdge polyedge) {
      this.polyedge = (int) polyedge.getValue();
      valueMaskEnable(Cp.POLY_EDGE);
      return this;
    }

    public CreatePicture.CreatePictureBuilder polymode(int polymode) {
      this.polymode = polymode;
      valueMaskEnable(Cp.POLY_MODE);
      return this;
    }

    public CreatePicture.CreatePictureBuilder polymode(PolyMode polymode) {
      this.polymode = (int) polymode.getValue();
      valueMaskEnable(Cp.POLY_MODE);
      return this;
    }

    public CreatePicture.CreatePictureBuilder dither(int dither) {
      this.dither = dither;
      valueMaskEnable(Cp.DITHER);
      return this;
    }

    public CreatePicture.CreatePictureBuilder componentalpha(int componentalpha) {
      this.componentalpha = componentalpha;
      valueMaskEnable(Cp.COMPONENT_ALPHA);
      return this;
    }

    public int getSize() {
      return 20 + (Cp.REPEAT.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.ALPHA_MAP.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.ALPHA_X_ORIGIN.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.ALPHA_Y_ORIGIN.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.CLIP_X_ORIGIN.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.CLIP_Y_ORIGIN.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.CLIP_MASK.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.GRAPHICS_EXPOSURE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.SUBWINDOW_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.POLY_EDGE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.POLY_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.DITHER.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cp.COMPONENT_ALPHA.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0);
    }

    @java.lang.SuppressWarnings("all")
    CreatePictureBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreatePicture.CreatePictureBuilder pid(final int pid) {
      this.pid = pid;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreatePicture.CreatePictureBuilder drawable(final int drawable) {
      this.drawable = drawable;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreatePicture.CreatePictureBuilder format(final int format) {
      this.format = format;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CreatePicture build() {
      return new CreatePicture(this.pid, this.drawable, this.format, this.valueMask, this.repeat, this.alphamap, this.alphaxorigin, this.alphayorigin, this.clipxorigin, this.clipyorigin, this.clipmask, this.graphicsexposure, this.subwindowmode, this.polyedge, this.polymode, this.dither, this.componentalpha);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CreatePicture.CreatePictureBuilder(pid=" + this.pid + ", drawable=" + this.drawable + ", format=" + this.format + ", valueMask=" + this.valueMask + ", repeat=" + this.repeat + ", alphamap=" + this.alphamap + ", alphaxorigin=" + this.alphaxorigin + ", alphayorigin=" + this.alphayorigin + ", clipxorigin=" + this.clipxorigin + ", clipyorigin=" + this.clipyorigin + ", clipmask=" + this.clipmask + ", graphicsexposure=" + this.graphicsexposure + ", subwindowmode=" + this.subwindowmode + ", polyedge=" + this.polyedge + ", polymode=" + this.polymode + ", dither=" + this.dither + ", componentalpha=" + this.componentalpha + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CreatePicture(final int pid, final int drawable, final int format, final int valueMask, final int repeat, final int alphamap, final int alphaxorigin, final int alphayorigin, final int clipxorigin, final int clipyorigin, final int clipmask, final int graphicsexposure, final int subwindowmode, final int polyedge, final int polymode, final int dither, final int componentalpha) {
    this.pid = pid;
    this.drawable = drawable;
    this.format = format;
    this.valueMask = valueMask;
    this.repeat = repeat;
    this.alphamap = alphamap;
    this.alphaxorigin = alphaxorigin;
    this.alphayorigin = alphayorigin;
    this.clipxorigin = clipxorigin;
    this.clipyorigin = clipyorigin;
    this.clipmask = clipmask;
    this.graphicsexposure = graphicsexposure;
    this.subwindowmode = subwindowmode;
    this.polyedge = polyedge;
    this.polymode = polymode;
    this.dither = dither;
    this.componentalpha = componentalpha;
  }

  @java.lang.SuppressWarnings("all")
  public static CreatePicture.CreatePictureBuilder builder() {
    return new CreatePicture.CreatePictureBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getPid() {
    return this.pid;
  }

  @java.lang.SuppressWarnings("all")
  public int getDrawable() {
    return this.drawable;
  }

  @java.lang.SuppressWarnings("all")
  public int getFormat() {
    return this.format;
  }

  @java.lang.SuppressWarnings("all")
  public int getValueMask() {
    return this.valueMask;
  }

  @java.lang.SuppressWarnings("all")
  public int getRepeat() {
    return this.repeat;
  }

  @java.lang.SuppressWarnings("all")
  public int getAlphamap() {
    return this.alphamap;
  }

  @java.lang.SuppressWarnings("all")
  public int getAlphaxorigin() {
    return this.alphaxorigin;
  }

  @java.lang.SuppressWarnings("all")
  public int getAlphayorigin() {
    return this.alphayorigin;
  }

  @java.lang.SuppressWarnings("all")
  public int getClipxorigin() {
    return this.clipxorigin;
  }

  @java.lang.SuppressWarnings("all")
  public int getClipyorigin() {
    return this.clipyorigin;
  }

  @java.lang.SuppressWarnings("all")
  public int getClipmask() {
    return this.clipmask;
  }

  @java.lang.SuppressWarnings("all")
  public int getGraphicsexposure() {
    return this.graphicsexposure;
  }

  @java.lang.SuppressWarnings("all")
  public int getSubwindowmode() {
    return this.subwindowmode;
  }

  @java.lang.SuppressWarnings("all")
  public int getPolyedge() {
    return this.polyedge;
  }

  @java.lang.SuppressWarnings("all")
  public int getPolymode() {
    return this.polymode;
  }

  @java.lang.SuppressWarnings("all")
  public int getDither() {
    return this.dither;
  }

  @java.lang.SuppressWarnings("all")
  public int getComponentalpha() {
    return this.componentalpha;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CreatePicture)) return false;
    final CreatePicture other = (CreatePicture) o;
    if (this.getPid() != other.getPid()) return false;
    if (this.getDrawable() != other.getDrawable()) return false;
    if (this.getFormat() != other.getFormat()) return false;
    if (this.getValueMask() != other.getValueMask()) return false;
    if (this.getRepeat() != other.getRepeat()) return false;
    if (this.getAlphamap() != other.getAlphamap()) return false;
    if (this.getAlphaxorigin() != other.getAlphaxorigin()) return false;
    if (this.getAlphayorigin() != other.getAlphayorigin()) return false;
    if (this.getClipxorigin() != other.getClipxorigin()) return false;
    if (this.getClipyorigin() != other.getClipyorigin()) return false;
    if (this.getClipmask() != other.getClipmask()) return false;
    if (this.getGraphicsexposure() != other.getGraphicsexposure()) return false;
    if (this.getSubwindowmode() != other.getSubwindowmode()) return false;
    if (this.getPolyedge() != other.getPolyedge()) return false;
    if (this.getPolymode() != other.getPolymode()) return false;
    if (this.getDither() != other.getDither()) return false;
    if (this.getComponentalpha() != other.getComponentalpha()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getPid();
    result = result * PRIME + this.getDrawable();
    result = result * PRIME + this.getFormat();
    result = result * PRIME + this.getValueMask();
    result = result * PRIME + this.getRepeat();
    result = result * PRIME + this.getAlphamap();
    result = result * PRIME + this.getAlphaxorigin();
    result = result * PRIME + this.getAlphayorigin();
    result = result * PRIME + this.getClipxorigin();
    result = result * PRIME + this.getClipyorigin();
    result = result * PRIME + this.getClipmask();
    result = result * PRIME + this.getGraphicsexposure();
    result = result * PRIME + this.getSubwindowmode();
    result = result * PRIME + this.getPolyedge();
    result = result * PRIME + this.getPolymode();
    result = result * PRIME + this.getDither();
    result = result * PRIME + this.getComponentalpha();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CreatePicture(pid=" + this.getPid() + ", drawable=" + this.getDrawable() + ", format=" + this.getFormat() + ", valueMask=" + this.getValueMask() + ", repeat=" + this.getRepeat() + ", alphamap=" + this.getAlphamap() + ", alphaxorigin=" + this.getAlphaxorigin() + ", alphayorigin=" + this.getAlphayorigin() + ", clipxorigin=" + this.getClipxorigin() + ", clipyorigin=" + this.getClipyorigin() + ", clipmask=" + this.getClipmask() + ", graphicsexposure=" + this.getGraphicsexposure() + ", subwindowmode=" + this.getSubwindowmode() + ", polyedge=" + this.getPolyedge() + ", polymode=" + this.getPolymode() + ", dither=" + this.getDither() + ", componentalpha=" + this.getComponentalpha() + ")";
  }
}
