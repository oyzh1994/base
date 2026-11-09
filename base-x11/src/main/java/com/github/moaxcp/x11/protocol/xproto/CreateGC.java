package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class CreateGC implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 55;
  private final int cid;
  private final int drawable;
  private final int valueMask;
  private final int function;
  private final int planeMask;
  private final int foreground;
  private final int background;
  private final int lineWidth;
  private final int lineStyle;
  private final int capStyle;
  private final int joinStyle;
  private final int fillStyle;
  private final int fillRule;
  private final int tile;
  private final int stipple;
  private final int tileStippleXOrigin;
  private final int tileStippleYOrigin;
  private final int font;
  private final int subwindowMode;
  private final int graphicsExposures;
  private final int clipXOrigin;
  private final int clipYOrigin;
  private final int clipMask;
  private final int dashOffset;
  private final int dashes;
  private final int arcMode;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateGC readCreateGC(X11Input in) throws IOException {
    CreateGC.CreateGCBuilder javaBuilder = CreateGC.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int cid = in.readCard32();
    int drawable = in.readCard32();
    int valueMask = in.readCard32();
    int function = 0;
    int planeMask = 0;
    int foreground = 0;
    int background = 0;
    int lineWidth = 0;
    int lineStyle = 0;
    int capStyle = 0;
    int joinStyle = 0;
    int fillStyle = 0;
    int fillRule = 0;
    int tile = 0;
    int stipple = 0;
    int tileStippleXOrigin = 0;
    int tileStippleYOrigin = 0;
    int font = 0;
    int subwindowMode = 0;
    int graphicsExposures = 0;
    int clipXOrigin = 0;
    int clipYOrigin = 0;
    int clipMask = 0;
    int dashOffset = 0;
    int dashes = 0;
    int arcMode = 0;
    javaBuilder.cid(cid);
    javaBuilder.drawable(drawable);
    javaBuilder.valueMask(valueMask);
    if (Gc.FUNCTION.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      function = in.readCard32();
      javaBuilder.function(function);
    }
    if (Gc.PLANE_MASK.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      planeMask = in.readCard32();
      javaBuilder.planeMask(planeMask);
    }
    if (Gc.FOREGROUND.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      foreground = in.readCard32();
      javaBuilder.foreground(foreground);
    }
    if (Gc.BACKGROUND.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      background = in.readCard32();
      javaBuilder.background(background);
    }
    if (Gc.LINE_WIDTH.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      lineWidth = in.readCard32();
      javaBuilder.lineWidth(lineWidth);
    }
    if (Gc.LINE_STYLE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      lineStyle = in.readCard32();
      javaBuilder.lineStyle(lineStyle);
    }
    if (Gc.CAP_STYLE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      capStyle = in.readCard32();
      javaBuilder.capStyle(capStyle);
    }
    if (Gc.JOIN_STYLE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      joinStyle = in.readCard32();
      javaBuilder.joinStyle(joinStyle);
    }
    if (Gc.FILL_STYLE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      fillStyle = in.readCard32();
      javaBuilder.fillStyle(fillStyle);
    }
    if (Gc.FILL_RULE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      fillRule = in.readCard32();
      javaBuilder.fillRule(fillRule);
    }
    if (Gc.TILE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      tile = in.readCard32();
      javaBuilder.tile(tile);
    }
    if (Gc.STIPPLE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      stipple = in.readCard32();
      javaBuilder.stipple(stipple);
    }
    if (Gc.TILE_STIPPLE_ORIGIN_X.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      tileStippleXOrigin = in.readInt32();
      javaBuilder.tileStippleXOrigin(tileStippleXOrigin);
    }
    if (Gc.TILE_STIPPLE_ORIGIN_Y.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      tileStippleYOrigin = in.readInt32();
      javaBuilder.tileStippleYOrigin(tileStippleYOrigin);
    }
    if (Gc.FONT.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      font = in.readCard32();
      javaBuilder.font(font);
    }
    if (Gc.SUBWINDOW_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      subwindowMode = in.readCard32();
      javaBuilder.subwindowMode(subwindowMode);
    }
    if (Gc.GRAPHICS_EXPOSURES.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      graphicsExposures = in.readCard32();
      javaBuilder.graphicsExposures(graphicsExposures);
    }
    if (Gc.CLIP_ORIGIN_X.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      clipXOrigin = in.readInt32();
      javaBuilder.clipXOrigin(clipXOrigin);
    }
    if (Gc.CLIP_ORIGIN_Y.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      clipYOrigin = in.readInt32();
      javaBuilder.clipYOrigin(clipYOrigin);
    }
    if (Gc.CLIP_MASK.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      clipMask = in.readCard32();
      javaBuilder.clipMask(clipMask);
    }
    if (Gc.DASH_OFFSET.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      dashOffset = in.readCard32();
      javaBuilder.dashOffset(dashOffset);
    }
    if (Gc.DASH_LIST.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      dashes = in.readCard32();
      javaBuilder.dashes(dashes);
    }
    if (Gc.ARC_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      arcMode = in.readCard32();
      javaBuilder.arcMode(arcMode);
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(cid);
    out.writeCard32(drawable);
    out.writeCard32(valueMask);
    if (Gc.FUNCTION.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(function);
    }
    if (Gc.PLANE_MASK.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(planeMask);
    }
    if (Gc.FOREGROUND.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(foreground);
    }
    if (Gc.BACKGROUND.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(background);
    }
    if (Gc.LINE_WIDTH.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(lineWidth);
    }
    if (Gc.LINE_STYLE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(lineStyle);
    }
    if (Gc.CAP_STYLE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(capStyle);
    }
    if (Gc.JOIN_STYLE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(joinStyle);
    }
    if (Gc.FILL_STYLE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(fillStyle);
    }
    if (Gc.FILL_RULE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(fillRule);
    }
    if (Gc.TILE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(tile);
    }
    if (Gc.STIPPLE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(stipple);
    }
    if (Gc.TILE_STIPPLE_ORIGIN_X.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeInt32(tileStippleXOrigin);
    }
    if (Gc.TILE_STIPPLE_ORIGIN_Y.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeInt32(tileStippleYOrigin);
    }
    if (Gc.FONT.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(font);
    }
    if (Gc.SUBWINDOW_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(subwindowMode);
    }
    if (Gc.GRAPHICS_EXPOSURES.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(graphicsExposures);
    }
    if (Gc.CLIP_ORIGIN_X.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeInt32(clipXOrigin);
    }
    if (Gc.CLIP_ORIGIN_Y.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeInt32(clipYOrigin);
    }
    if (Gc.CLIP_MASK.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(clipMask);
    }
    if (Gc.DASH_OFFSET.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(dashOffset);
    }
    if (Gc.DASH_LIST.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(dashes);
    }
    if (Gc.ARC_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(arcMode);
    }
    out.writePadAlign(getSize());
  }

  public boolean isValueMaskEnabled(Gc... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (Gc m : maskEnums) {
      if (!m.isEnabled(valueMask)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 16 + (Gc.FUNCTION.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.PLANE_MASK.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.FOREGROUND.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.BACKGROUND.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.LINE_WIDTH.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.LINE_STYLE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.CAP_STYLE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.JOIN_STYLE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.FILL_STYLE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.FILL_RULE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.TILE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.STIPPLE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.TILE_STIPPLE_ORIGIN_X.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.TILE_STIPPLE_ORIGIN_Y.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.FONT.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.SUBWINDOW_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.GRAPHICS_EXPOSURES.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.CLIP_ORIGIN_X.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.CLIP_ORIGIN_Y.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.CLIP_MASK.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.DASH_OFFSET.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.DASH_LIST.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.ARC_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CreateGCBuilder {
    @java.lang.SuppressWarnings("all")
    private int cid;
    @java.lang.SuppressWarnings("all")
    private int drawable;
    @java.lang.SuppressWarnings("all")
    private int valueMask;
    @java.lang.SuppressWarnings("all")
    private int function;
    @java.lang.SuppressWarnings("all")
    private int planeMask;
    @java.lang.SuppressWarnings("all")
    private int foreground;
    @java.lang.SuppressWarnings("all")
    private int background;
    @java.lang.SuppressWarnings("all")
    private int lineWidth;
    @java.lang.SuppressWarnings("all")
    private int lineStyle;
    @java.lang.SuppressWarnings("all")
    private int capStyle;
    @java.lang.SuppressWarnings("all")
    private int joinStyle;
    @java.lang.SuppressWarnings("all")
    private int fillStyle;
    @java.lang.SuppressWarnings("all")
    private int fillRule;
    @java.lang.SuppressWarnings("all")
    private int tile;
    @java.lang.SuppressWarnings("all")
    private int stipple;
    @java.lang.SuppressWarnings("all")
    private int tileStippleXOrigin;
    @java.lang.SuppressWarnings("all")
    private int tileStippleYOrigin;
    @java.lang.SuppressWarnings("all")
    private int font;
    @java.lang.SuppressWarnings("all")
    private int subwindowMode;
    @java.lang.SuppressWarnings("all")
    private int graphicsExposures;
    @java.lang.SuppressWarnings("all")
    private int clipXOrigin;
    @java.lang.SuppressWarnings("all")
    private int clipYOrigin;
    @java.lang.SuppressWarnings("all")
    private int clipMask;
    @java.lang.SuppressWarnings("all")
    private int dashOffset;
    @java.lang.SuppressWarnings("all")
    private int dashes;
    @java.lang.SuppressWarnings("all")
    private int arcMode;

    private CreateGC.CreateGCBuilder valueMask(int valueMask) {
      this.valueMask = valueMask;
      return this;
    }

    public boolean isValueMaskEnabled(Gc... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (Gc m : maskEnums) {
        if (!m.isEnabled(valueMask)) {
          return false;
        }
      }
      return true;
    }

    private CreateGC.CreateGCBuilder valueMaskEnable(Gc... maskEnums) {
      for (Gc m : maskEnums) {
        valueMask((int) m.enableFor(valueMask));
      }
      return this;
    }

    private CreateGC.CreateGCBuilder valueMaskDisable(Gc... maskEnums) {
      for (Gc m : maskEnums) {
        valueMask((int) m.disableFor(valueMask));
      }
      return this;
    }

    public CreateGC.CreateGCBuilder function(int function) {
      this.function = function;
      valueMaskEnable(Gc.FUNCTION);
      return this;
    }

    public CreateGC.CreateGCBuilder function(Gx function) {
      this.function = (int) function.getValue();
      valueMaskEnable(Gc.FUNCTION);
      return this;
    }

    public CreateGC.CreateGCBuilder planeMask(int planeMask) {
      this.planeMask = planeMask;
      valueMaskEnable(Gc.PLANE_MASK);
      return this;
    }

    public CreateGC.CreateGCBuilder foreground(int foreground) {
      this.foreground = foreground;
      valueMaskEnable(Gc.FOREGROUND);
      return this;
    }

    public CreateGC.CreateGCBuilder background(int background) {
      this.background = background;
      valueMaskEnable(Gc.BACKGROUND);
      return this;
    }

    public CreateGC.CreateGCBuilder lineWidth(int lineWidth) {
      this.lineWidth = lineWidth;
      valueMaskEnable(Gc.LINE_WIDTH);
      return this;
    }

    public CreateGC.CreateGCBuilder lineStyle(int lineStyle) {
      this.lineStyle = lineStyle;
      valueMaskEnable(Gc.LINE_STYLE);
      return this;
    }

    public CreateGC.CreateGCBuilder lineStyle(LineStyle lineStyle) {
      this.lineStyle = (int) lineStyle.getValue();
      valueMaskEnable(Gc.LINE_STYLE);
      return this;
    }

    public CreateGC.CreateGCBuilder capStyle(int capStyle) {
      this.capStyle = capStyle;
      valueMaskEnable(Gc.CAP_STYLE);
      return this;
    }

    public CreateGC.CreateGCBuilder capStyle(CapStyle capStyle) {
      this.capStyle = (int) capStyle.getValue();
      valueMaskEnable(Gc.CAP_STYLE);
      return this;
    }

    public CreateGC.CreateGCBuilder joinStyle(int joinStyle) {
      this.joinStyle = joinStyle;
      valueMaskEnable(Gc.JOIN_STYLE);
      return this;
    }

    public CreateGC.CreateGCBuilder joinStyle(JoinStyle joinStyle) {
      this.joinStyle = (int) joinStyle.getValue();
      valueMaskEnable(Gc.JOIN_STYLE);
      return this;
    }

    public CreateGC.CreateGCBuilder fillStyle(int fillStyle) {
      this.fillStyle = fillStyle;
      valueMaskEnable(Gc.FILL_STYLE);
      return this;
    }

    public CreateGC.CreateGCBuilder fillStyle(FillStyle fillStyle) {
      this.fillStyle = (int) fillStyle.getValue();
      valueMaskEnable(Gc.FILL_STYLE);
      return this;
    }

    public CreateGC.CreateGCBuilder fillRule(int fillRule) {
      this.fillRule = fillRule;
      valueMaskEnable(Gc.FILL_RULE);
      return this;
    }

    public CreateGC.CreateGCBuilder fillRule(FillRule fillRule) {
      this.fillRule = (int) fillRule.getValue();
      valueMaskEnable(Gc.FILL_RULE);
      return this;
    }

    public CreateGC.CreateGCBuilder tile(int tile) {
      this.tile = tile;
      valueMaskEnable(Gc.TILE);
      return this;
    }

    public CreateGC.CreateGCBuilder stipple(int stipple) {
      this.stipple = stipple;
      valueMaskEnable(Gc.STIPPLE);
      return this;
    }

    public CreateGC.CreateGCBuilder tileStippleXOrigin(int tileStippleXOrigin) {
      this.tileStippleXOrigin = tileStippleXOrigin;
      valueMaskEnable(Gc.TILE_STIPPLE_ORIGIN_X);
      return this;
    }

    public CreateGC.CreateGCBuilder tileStippleYOrigin(int tileStippleYOrigin) {
      this.tileStippleYOrigin = tileStippleYOrigin;
      valueMaskEnable(Gc.TILE_STIPPLE_ORIGIN_Y);
      return this;
    }

    public CreateGC.CreateGCBuilder font(int font) {
      this.font = font;
      valueMaskEnable(Gc.FONT);
      return this;
    }

    public CreateGC.CreateGCBuilder subwindowMode(int subwindowMode) {
      this.subwindowMode = subwindowMode;
      valueMaskEnable(Gc.SUBWINDOW_MODE);
      return this;
    }

    public CreateGC.CreateGCBuilder subwindowMode(SubwindowMode subwindowMode) {
      this.subwindowMode = (int) subwindowMode.getValue();
      valueMaskEnable(Gc.SUBWINDOW_MODE);
      return this;
    }

    public CreateGC.CreateGCBuilder graphicsExposures(int graphicsExposures) {
      this.graphicsExposures = graphicsExposures;
      valueMaskEnable(Gc.GRAPHICS_EXPOSURES);
      return this;
    }

    public CreateGC.CreateGCBuilder clipXOrigin(int clipXOrigin) {
      this.clipXOrigin = clipXOrigin;
      valueMaskEnable(Gc.CLIP_ORIGIN_X);
      return this;
    }

    public CreateGC.CreateGCBuilder clipYOrigin(int clipYOrigin) {
      this.clipYOrigin = clipYOrigin;
      valueMaskEnable(Gc.CLIP_ORIGIN_Y);
      return this;
    }

    public CreateGC.CreateGCBuilder clipMask(int clipMask) {
      this.clipMask = clipMask;
      valueMaskEnable(Gc.CLIP_MASK);
      return this;
    }

    public CreateGC.CreateGCBuilder dashOffset(int dashOffset) {
      this.dashOffset = dashOffset;
      valueMaskEnable(Gc.DASH_OFFSET);
      return this;
    }

    public CreateGC.CreateGCBuilder dashes(int dashes) {
      this.dashes = dashes;
      valueMaskEnable(Gc.DASH_LIST);
      return this;
    }

    public CreateGC.CreateGCBuilder arcMode(int arcMode) {
      this.arcMode = arcMode;
      valueMaskEnable(Gc.ARC_MODE);
      return this;
    }

    public CreateGC.CreateGCBuilder arcMode(ArcMode arcMode) {
      this.arcMode = (int) arcMode.getValue();
      valueMaskEnable(Gc.ARC_MODE);
      return this;
    }

    public int getSize() {
      return 16 + (Gc.FUNCTION.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.PLANE_MASK.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.FOREGROUND.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.BACKGROUND.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.LINE_WIDTH.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.LINE_STYLE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.CAP_STYLE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.JOIN_STYLE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.FILL_STYLE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.FILL_RULE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.TILE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.STIPPLE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.TILE_STIPPLE_ORIGIN_X.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.TILE_STIPPLE_ORIGIN_Y.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.FONT.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.SUBWINDOW_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.GRAPHICS_EXPOSURES.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.CLIP_ORIGIN_X.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.CLIP_ORIGIN_Y.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.CLIP_MASK.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.DASH_OFFSET.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.DASH_LIST.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Gc.ARC_MODE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0);
    }

    @java.lang.SuppressWarnings("all")
    CreateGCBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateGC.CreateGCBuilder cid(final int cid) {
      this.cid = cid;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateGC.CreateGCBuilder drawable(final int drawable) {
      this.drawable = drawable;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CreateGC build() {
      return new CreateGC(this.cid, this.drawable, this.valueMask, this.function, this.planeMask, this.foreground, this.background, this.lineWidth, this.lineStyle, this.capStyle, this.joinStyle, this.fillStyle, this.fillRule, this.tile, this.stipple, this.tileStippleXOrigin, this.tileStippleYOrigin, this.font, this.subwindowMode, this.graphicsExposures, this.clipXOrigin, this.clipYOrigin, this.clipMask, this.dashOffset, this.dashes, this.arcMode);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CreateGC.CreateGCBuilder(cid=" + this.cid + ", drawable=" + this.drawable + ", valueMask=" + this.valueMask + ", function=" + this.function + ", planeMask=" + this.planeMask + ", foreground=" + this.foreground + ", background=" + this.background + ", lineWidth=" + this.lineWidth + ", lineStyle=" + this.lineStyle + ", capStyle=" + this.capStyle + ", joinStyle=" + this.joinStyle + ", fillStyle=" + this.fillStyle + ", fillRule=" + this.fillRule + ", tile=" + this.tile + ", stipple=" + this.stipple + ", tileStippleXOrigin=" + this.tileStippleXOrigin + ", tileStippleYOrigin=" + this.tileStippleYOrigin + ", font=" + this.font + ", subwindowMode=" + this.subwindowMode + ", graphicsExposures=" + this.graphicsExposures + ", clipXOrigin=" + this.clipXOrigin + ", clipYOrigin=" + this.clipYOrigin + ", clipMask=" + this.clipMask + ", dashOffset=" + this.dashOffset + ", dashes=" + this.dashes + ", arcMode=" + this.arcMode + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CreateGC(final int cid, final int drawable, final int valueMask, final int function, final int planeMask, final int foreground, final int background, final int lineWidth, final int lineStyle, final int capStyle, final int joinStyle, final int fillStyle, final int fillRule, final int tile, final int stipple, final int tileStippleXOrigin, final int tileStippleYOrigin, final int font, final int subwindowMode, final int graphicsExposures, final int clipXOrigin, final int clipYOrigin, final int clipMask, final int dashOffset, final int dashes, final int arcMode) {
    this.cid = cid;
    this.drawable = drawable;
    this.valueMask = valueMask;
    this.function = function;
    this.planeMask = planeMask;
    this.foreground = foreground;
    this.background = background;
    this.lineWidth = lineWidth;
    this.lineStyle = lineStyle;
    this.capStyle = capStyle;
    this.joinStyle = joinStyle;
    this.fillStyle = fillStyle;
    this.fillRule = fillRule;
    this.tile = tile;
    this.stipple = stipple;
    this.tileStippleXOrigin = tileStippleXOrigin;
    this.tileStippleYOrigin = tileStippleYOrigin;
    this.font = font;
    this.subwindowMode = subwindowMode;
    this.graphicsExposures = graphicsExposures;
    this.clipXOrigin = clipXOrigin;
    this.clipYOrigin = clipYOrigin;
    this.clipMask = clipMask;
    this.dashOffset = dashOffset;
    this.dashes = dashes;
    this.arcMode = arcMode;
  }

  @java.lang.SuppressWarnings("all")
  public static CreateGC.CreateGCBuilder builder() {
    return new CreateGC.CreateGCBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getCid() {
    return this.cid;
  }

  @java.lang.SuppressWarnings("all")
  public int getDrawable() {
    return this.drawable;
  }

  @java.lang.SuppressWarnings("all")
  public int getValueMask() {
    return this.valueMask;
  }

  @java.lang.SuppressWarnings("all")
  public int getFunction() {
    return this.function;
  }

  @java.lang.SuppressWarnings("all")
  public int getPlaneMask() {
    return this.planeMask;
  }

  @java.lang.SuppressWarnings("all")
  public int getForeground() {
    return this.foreground;
  }

  @java.lang.SuppressWarnings("all")
  public int getBackground() {
    return this.background;
  }

  @java.lang.SuppressWarnings("all")
  public int getLineWidth() {
    return this.lineWidth;
  }

  @java.lang.SuppressWarnings("all")
  public int getLineStyle() {
    return this.lineStyle;
  }

  @java.lang.SuppressWarnings("all")
  public int getCapStyle() {
    return this.capStyle;
  }

  @java.lang.SuppressWarnings("all")
  public int getJoinStyle() {
    return this.joinStyle;
  }

  @java.lang.SuppressWarnings("all")
  public int getFillStyle() {
    return this.fillStyle;
  }

  @java.lang.SuppressWarnings("all")
  public int getFillRule() {
    return this.fillRule;
  }

  @java.lang.SuppressWarnings("all")
  public int getTile() {
    return this.tile;
  }

  @java.lang.SuppressWarnings("all")
  public int getStipple() {
    return this.stipple;
  }

  @java.lang.SuppressWarnings("all")
  public int getTileStippleXOrigin() {
    return this.tileStippleXOrigin;
  }

  @java.lang.SuppressWarnings("all")
  public int getTileStippleYOrigin() {
    return this.tileStippleYOrigin;
  }

  @java.lang.SuppressWarnings("all")
  public int getFont() {
    return this.font;
  }

  @java.lang.SuppressWarnings("all")
  public int getSubwindowMode() {
    return this.subwindowMode;
  }

  @java.lang.SuppressWarnings("all")
  public int getGraphicsExposures() {
    return this.graphicsExposures;
  }

  @java.lang.SuppressWarnings("all")
  public int getClipXOrigin() {
    return this.clipXOrigin;
  }

  @java.lang.SuppressWarnings("all")
  public int getClipYOrigin() {
    return this.clipYOrigin;
  }

  @java.lang.SuppressWarnings("all")
  public int getClipMask() {
    return this.clipMask;
  }

  @java.lang.SuppressWarnings("all")
  public int getDashOffset() {
    return this.dashOffset;
  }

  @java.lang.SuppressWarnings("all")
  public int getDashes() {
    return this.dashes;
  }

  @java.lang.SuppressWarnings("all")
  public int getArcMode() {
    return this.arcMode;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CreateGC)) return false;
    final CreateGC other = (CreateGC) o;
    if (this.getCid() != other.getCid()) return false;
    if (this.getDrawable() != other.getDrawable()) return false;
    if (this.getValueMask() != other.getValueMask()) return false;
    if (this.getFunction() != other.getFunction()) return false;
    if (this.getPlaneMask() != other.getPlaneMask()) return false;
    if (this.getForeground() != other.getForeground()) return false;
    if (this.getBackground() != other.getBackground()) return false;
    if (this.getLineWidth() != other.getLineWidth()) return false;
    if (this.getLineStyle() != other.getLineStyle()) return false;
    if (this.getCapStyle() != other.getCapStyle()) return false;
    if (this.getJoinStyle() != other.getJoinStyle()) return false;
    if (this.getFillStyle() != other.getFillStyle()) return false;
    if (this.getFillRule() != other.getFillRule()) return false;
    if (this.getTile() != other.getTile()) return false;
    if (this.getStipple() != other.getStipple()) return false;
    if (this.getTileStippleXOrigin() != other.getTileStippleXOrigin()) return false;
    if (this.getTileStippleYOrigin() != other.getTileStippleYOrigin()) return false;
    if (this.getFont() != other.getFont()) return false;
    if (this.getSubwindowMode() != other.getSubwindowMode()) return false;
    if (this.getGraphicsExposures() != other.getGraphicsExposures()) return false;
    if (this.getClipXOrigin() != other.getClipXOrigin()) return false;
    if (this.getClipYOrigin() != other.getClipYOrigin()) return false;
    if (this.getClipMask() != other.getClipMask()) return false;
    if (this.getDashOffset() != other.getDashOffset()) return false;
    if (this.getDashes() != other.getDashes()) return false;
    if (this.getArcMode() != other.getArcMode()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getCid();
    result = result * PRIME + this.getDrawable();
    result = result * PRIME + this.getValueMask();
    result = result * PRIME + this.getFunction();
    result = result * PRIME + this.getPlaneMask();
    result = result * PRIME + this.getForeground();
    result = result * PRIME + this.getBackground();
    result = result * PRIME + this.getLineWidth();
    result = result * PRIME + this.getLineStyle();
    result = result * PRIME + this.getCapStyle();
    result = result * PRIME + this.getJoinStyle();
    result = result * PRIME + this.getFillStyle();
    result = result * PRIME + this.getFillRule();
    result = result * PRIME + this.getTile();
    result = result * PRIME + this.getStipple();
    result = result * PRIME + this.getTileStippleXOrigin();
    result = result * PRIME + this.getTileStippleYOrigin();
    result = result * PRIME + this.getFont();
    result = result * PRIME + this.getSubwindowMode();
    result = result * PRIME + this.getGraphicsExposures();
    result = result * PRIME + this.getClipXOrigin();
    result = result * PRIME + this.getClipYOrigin();
    result = result * PRIME + this.getClipMask();
    result = result * PRIME + this.getDashOffset();
    result = result * PRIME + this.getDashes();
    result = result * PRIME + this.getArcMode();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CreateGC(cid=" + this.getCid() + ", drawable=" + this.getDrawable() + ", valueMask=" + this.getValueMask() + ", function=" + this.getFunction() + ", planeMask=" + this.getPlaneMask() + ", foreground=" + this.getForeground() + ", background=" + this.getBackground() + ", lineWidth=" + this.getLineWidth() + ", lineStyle=" + this.getLineStyle() + ", capStyle=" + this.getCapStyle() + ", joinStyle=" + this.getJoinStyle() + ", fillStyle=" + this.getFillStyle() + ", fillRule=" + this.getFillRule() + ", tile=" + this.getTile() + ", stipple=" + this.getStipple() + ", tileStippleXOrigin=" + this.getTileStippleXOrigin() + ", tileStippleYOrigin=" + this.getTileStippleYOrigin() + ", font=" + this.getFont() + ", subwindowMode=" + this.getSubwindowMode() + ", graphicsExposures=" + this.getGraphicsExposures() + ", clipXOrigin=" + this.getClipXOrigin() + ", clipYOrigin=" + this.getClipYOrigin() + ", clipMask=" + this.getClipMask() + ", dashOffset=" + this.getDashOffset() + ", dashes=" + this.getDashes() + ", arcMode=" + this.getArcMode() + ")";
  }
}
