package com.github.moaxcp.x11.protocol.screensaver;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.xproto.BackingStore;
import com.github.moaxcp.x11.protocol.xproto.Cw;
import com.github.moaxcp.x11.protocol.xproto.EventMask;
import com.github.moaxcp.x11.protocol.xproto.Gravity;
import com.github.moaxcp.x11.protocol.xproto.WindowClass;
import java.io.IOException;

public final class SetAttributes implements OneWayRequest {
  public static final String PLUGIN_NAME = "screensaver";
  public static final byte OPCODE = 3;
  private final int drawable;
  private final short x;
  private final short y;
  private final short width;
  private final short height;
  private final short borderWidth;
  private final byte clazz;
  private final byte depth;
  private final int visual;
  private final int valueMask;
  private final int backgroundPixmap;
  private final int backgroundPixel;
  private final int borderPixmap;
  private final int borderPixel;
  private final int bitGravity;
  private final int winGravity;
  private final int backingStore;
  private final int backingPlanes;
  private final int backingPixel;
  private final int overrideRedirect;
  private final int saveUnder;
  private final int eventMask;
  private final int doNotPropogateMask;
  private final int colormap;
  private final int cursor;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetAttributes readSetAttributes(X11Input in) throws IOException {
    SetAttributes.SetAttributesBuilder javaBuilder = SetAttributes.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int drawable = in.readCard32();
    short x = in.readInt16();
    short y = in.readInt16();
    short width = in.readCard16();
    short height = in.readCard16();
    short borderWidth = in.readCard16();
    byte clazz = in.readByte();
    byte depth = in.readCard8();
    int visual = in.readCard32();
    int valueMask = in.readCard32();
    int backgroundPixmap = 0;
    int backgroundPixel = 0;
    int borderPixmap = 0;
    int borderPixel = 0;
    int bitGravity = 0;
    int winGravity = 0;
    int backingStore = 0;
    int backingPlanes = 0;
    int backingPixel = 0;
    int overrideRedirect = 0;
    int saveUnder = 0;
    int eventMask = 0;
    int doNotPropogateMask = 0;
    int colormap = 0;
    int cursor = 0;
    javaBuilder.drawable(drawable);
    javaBuilder.x(x);
    javaBuilder.y(y);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.borderWidth(borderWidth);
    javaBuilder.clazz(clazz);
    javaBuilder.depth(depth);
    javaBuilder.visual(visual);
    javaBuilder.valueMask(valueMask);
    if (Cw.BACK_PIXMAP.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      backgroundPixmap = in.readCard32();
      javaBuilder.backgroundPixmap(backgroundPixmap);
    }
    if (Cw.BACK_PIXEL.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      backgroundPixel = in.readCard32();
      javaBuilder.backgroundPixel(backgroundPixel);
    }
    if (Cw.BORDER_PIXMAP.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      borderPixmap = in.readCard32();
      javaBuilder.borderPixmap(borderPixmap);
    }
    if (Cw.BORDER_PIXEL.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      borderPixel = in.readCard32();
      javaBuilder.borderPixel(borderPixel);
    }
    if (Cw.BIT_GRAVITY.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      bitGravity = in.readCard32();
      javaBuilder.bitGravity(bitGravity);
    }
    if (Cw.WIN_GRAVITY.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      winGravity = in.readCard32();
      javaBuilder.winGravity(winGravity);
    }
    if (Cw.BACKING_STORE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      backingStore = in.readCard32();
      javaBuilder.backingStore(backingStore);
    }
    if (Cw.BACKING_PLANES.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      backingPlanes = in.readCard32();
      javaBuilder.backingPlanes(backingPlanes);
    }
    if (Cw.BACKING_PIXEL.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      backingPixel = in.readCard32();
      javaBuilder.backingPixel(backingPixel);
    }
    if (Cw.OVERRIDE_REDIRECT.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      overrideRedirect = in.readCard32();
      javaBuilder.overrideRedirect(overrideRedirect);
    }
    if (Cw.SAVE_UNDER.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      saveUnder = in.readCard32();
      javaBuilder.saveUnder(saveUnder);
    }
    if (Cw.EVENT_MASK.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      eventMask = in.readCard32();
      javaBuilder.eventMask(eventMask);
    }
    if (Cw.DONT_PROPAGATE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      doNotPropogateMask = in.readCard32();
      javaBuilder.doNotPropogateMask(doNotPropogateMask);
    }
    if (Cw.COLORMAP.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      colormap = in.readCard32();
      javaBuilder.colormap(colormap);
    }
    if (Cw.CURSOR.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      cursor = in.readCard32();
      javaBuilder.cursor(cursor);
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeInt16(x);
    out.writeInt16(y);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeCard16(borderWidth);
    out.writeByte(clazz);
    out.writeCard8(depth);
    out.writeCard32(visual);
    out.writeCard32(valueMask);
    if (Cw.BACK_PIXMAP.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(backgroundPixmap);
    }
    if (Cw.BACK_PIXEL.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(backgroundPixel);
    }
    if (Cw.BORDER_PIXMAP.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(borderPixmap);
    }
    if (Cw.BORDER_PIXEL.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(borderPixel);
    }
    if (Cw.BIT_GRAVITY.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(bitGravity);
    }
    if (Cw.WIN_GRAVITY.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(winGravity);
    }
    if (Cw.BACKING_STORE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(backingStore);
    }
    if (Cw.BACKING_PLANES.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(backingPlanes);
    }
    if (Cw.BACKING_PIXEL.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(backingPixel);
    }
    if (Cw.OVERRIDE_REDIRECT.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(overrideRedirect);
    }
    if (Cw.SAVE_UNDER.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(saveUnder);
    }
    if (Cw.EVENT_MASK.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(eventMask);
    }
    if (Cw.DONT_PROPAGATE.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(doNotPropogateMask);
    }
    if (Cw.COLORMAP.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(colormap);
    }
    if (Cw.CURSOR.isEnabled((int) (Integer.toUnsignedLong(valueMask)))) {
      out.writeCard32(cursor);
    }
    out.writePadAlign(getSize());
  }

  public boolean isValueMaskEnabled(Cw... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (Cw m : maskEnums) {
      if (!m.isEnabled(valueMask)) {
        return false;
      }
    }
    return true;
  }

  public boolean isEventMaskEnabled(EventMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (EventMask m : maskEnums) {
      if (!m.isEnabled(eventMask)) {
        return false;
      }
    }
    return true;
  }

  public boolean isDoNotPropogateMaskEnabled(EventMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (EventMask m : maskEnums) {
      if (!m.isEnabled(doNotPropogateMask)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 28 + (Cw.BACK_PIXMAP.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BACK_PIXEL.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BORDER_PIXMAP.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BORDER_PIXEL.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BIT_GRAVITY.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.WIN_GRAVITY.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BACKING_STORE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BACKING_PLANES.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BACKING_PIXEL.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.OVERRIDE_REDIRECT.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.SAVE_UNDER.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.EVENT_MASK.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.DONT_PROPAGATE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.COLORMAP.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.CURSOR.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetAttributesBuilder {
    @java.lang.SuppressWarnings("all")
    private int drawable;
    @java.lang.SuppressWarnings("all")
    private short x;
    @java.lang.SuppressWarnings("all")
    private short y;
    @java.lang.SuppressWarnings("all")
    private short width;
    @java.lang.SuppressWarnings("all")
    private short height;
    @java.lang.SuppressWarnings("all")
    private short borderWidth;
    @java.lang.SuppressWarnings("all")
    private byte clazz;
    @java.lang.SuppressWarnings("all")
    private byte depth;
    @java.lang.SuppressWarnings("all")
    private int visual;
    @java.lang.SuppressWarnings("all")
    private int valueMask;
    @java.lang.SuppressWarnings("all")
    private int backgroundPixmap;
    @java.lang.SuppressWarnings("all")
    private int backgroundPixel;
    @java.lang.SuppressWarnings("all")
    private int borderPixmap;
    @java.lang.SuppressWarnings("all")
    private int borderPixel;
    @java.lang.SuppressWarnings("all")
    private int bitGravity;
    @java.lang.SuppressWarnings("all")
    private int winGravity;
    @java.lang.SuppressWarnings("all")
    private int backingStore;
    @java.lang.SuppressWarnings("all")
    private int backingPlanes;
    @java.lang.SuppressWarnings("all")
    private int backingPixel;
    @java.lang.SuppressWarnings("all")
    private int overrideRedirect;
    @java.lang.SuppressWarnings("all")
    private int saveUnder;
    @java.lang.SuppressWarnings("all")
    private int eventMask;
    @java.lang.SuppressWarnings("all")
    private int doNotPropogateMask;
    @java.lang.SuppressWarnings("all")
    private int colormap;
    @java.lang.SuppressWarnings("all")
    private int cursor;

    public SetAttributes.SetAttributesBuilder clazz(WindowClass clazz) {
      this.clazz = (byte) clazz.getValue();
      return this;
    }

    public SetAttributes.SetAttributesBuilder clazz(byte clazz) {
      this.clazz = clazz;
      return this;
    }

    private SetAttributes.SetAttributesBuilder valueMask(int valueMask) {
      this.valueMask = valueMask;
      return this;
    }

    public boolean isValueMaskEnabled(Cw... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (Cw m : maskEnums) {
        if (!m.isEnabled(valueMask)) {
          return false;
        }
      }
      return true;
    }

    private SetAttributes.SetAttributesBuilder valueMaskEnable(Cw... maskEnums) {
      for (Cw m : maskEnums) {
        valueMask((int) m.enableFor(valueMask));
      }
      return this;
    }

    private SetAttributes.SetAttributesBuilder valueMaskDisable(Cw... maskEnums) {
      for (Cw m : maskEnums) {
        valueMask((int) m.disableFor(valueMask));
      }
      return this;
    }

    public SetAttributes.SetAttributesBuilder backgroundPixmap(int backgroundPixmap) {
      this.backgroundPixmap = backgroundPixmap;
      valueMaskEnable(Cw.BACK_PIXMAP);
      return this;
    }

    public SetAttributes.SetAttributesBuilder backgroundPixel(int backgroundPixel) {
      this.backgroundPixel = backgroundPixel;
      valueMaskEnable(Cw.BACK_PIXEL);
      return this;
    }

    public SetAttributes.SetAttributesBuilder borderPixmap(int borderPixmap) {
      this.borderPixmap = borderPixmap;
      valueMaskEnable(Cw.BORDER_PIXMAP);
      return this;
    }

    public SetAttributes.SetAttributesBuilder borderPixel(int borderPixel) {
      this.borderPixel = borderPixel;
      valueMaskEnable(Cw.BORDER_PIXEL);
      return this;
    }

    public SetAttributes.SetAttributesBuilder bitGravity(int bitGravity) {
      this.bitGravity = bitGravity;
      valueMaskEnable(Cw.BIT_GRAVITY);
      return this;
    }

    public SetAttributes.SetAttributesBuilder bitGravity(Gravity bitGravity) {
      this.bitGravity = (int) bitGravity.getValue();
      valueMaskEnable(Cw.BIT_GRAVITY);
      return this;
    }

    public SetAttributes.SetAttributesBuilder winGravity(int winGravity) {
      this.winGravity = winGravity;
      valueMaskEnable(Cw.WIN_GRAVITY);
      return this;
    }

    public SetAttributes.SetAttributesBuilder winGravity(Gravity winGravity) {
      this.winGravity = (int) winGravity.getValue();
      valueMaskEnable(Cw.WIN_GRAVITY);
      return this;
    }

    public SetAttributes.SetAttributesBuilder backingStore(int backingStore) {
      this.backingStore = backingStore;
      valueMaskEnable(Cw.BACKING_STORE);
      return this;
    }

    public SetAttributes.SetAttributesBuilder backingStore(BackingStore backingStore) {
      this.backingStore = (int) backingStore.getValue();
      valueMaskEnable(Cw.BACKING_STORE);
      return this;
    }

    public SetAttributes.SetAttributesBuilder backingPlanes(int backingPlanes) {
      this.backingPlanes = backingPlanes;
      valueMaskEnable(Cw.BACKING_PLANES);
      return this;
    }

    public SetAttributes.SetAttributesBuilder backingPixel(int backingPixel) {
      this.backingPixel = backingPixel;
      valueMaskEnable(Cw.BACKING_PIXEL);
      return this;
    }

    public SetAttributes.SetAttributesBuilder overrideRedirect(int overrideRedirect) {
      this.overrideRedirect = overrideRedirect;
      valueMaskEnable(Cw.OVERRIDE_REDIRECT);
      return this;
    }

    public SetAttributes.SetAttributesBuilder saveUnder(int saveUnder) {
      this.saveUnder = saveUnder;
      valueMaskEnable(Cw.SAVE_UNDER);
      return this;
    }

    public SetAttributes.SetAttributesBuilder eventMask(int eventMask) {
      this.eventMask = eventMask;
      valueMaskEnable(Cw.EVENT_MASK);
      return this;
    }

    public boolean isEventMaskEnabled(EventMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (EventMask m : maskEnums) {
        if (!m.isEnabled(eventMask)) {
          return false;
        }
      }
      return true;
    }

    public SetAttributes.SetAttributesBuilder eventMaskEnable(EventMask... maskEnums) {
      for (EventMask m : maskEnums) {
        eventMask((int) m.enableFor(eventMask));
      }
      return this;
    }

    public SetAttributes.SetAttributesBuilder eventMaskDisable(EventMask... maskEnums) {
      for (EventMask m : maskEnums) {
        eventMask((int) m.disableFor(eventMask));
      }
      return this;
    }

    public SetAttributes.SetAttributesBuilder doNotPropogateMask(int doNotPropogateMask) {
      this.doNotPropogateMask = doNotPropogateMask;
      valueMaskEnable(Cw.DONT_PROPAGATE);
      return this;
    }

    public boolean isDoNotPropogateMaskEnabled(EventMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (EventMask m : maskEnums) {
        if (!m.isEnabled(doNotPropogateMask)) {
          return false;
        }
      }
      return true;
    }

    public SetAttributes.SetAttributesBuilder doNotPropogateMaskEnable(EventMask... maskEnums) {
      for (EventMask m : maskEnums) {
        doNotPropogateMask((int) m.enableFor(doNotPropogateMask));
      }
      return this;
    }

    public SetAttributes.SetAttributesBuilder doNotPropogateMaskDisable(EventMask... maskEnums) {
      for (EventMask m : maskEnums) {
        doNotPropogateMask((int) m.disableFor(doNotPropogateMask));
      }
      return this;
    }

    public SetAttributes.SetAttributesBuilder colormap(int colormap) {
      this.colormap = colormap;
      valueMaskEnable(Cw.COLORMAP);
      return this;
    }

    public SetAttributes.SetAttributesBuilder cursor(int cursor) {
      this.cursor = cursor;
      valueMaskEnable(Cw.CURSOR);
      return this;
    }

    public int getSize() {
      return 28 + (Cw.BACK_PIXMAP.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BACK_PIXEL.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BORDER_PIXMAP.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BORDER_PIXEL.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BIT_GRAVITY.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.WIN_GRAVITY.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BACKING_STORE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BACKING_PLANES.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BACKING_PIXEL.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.OVERRIDE_REDIRECT.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.SAVE_UNDER.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.EVENT_MASK.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.DONT_PROPAGATE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.COLORMAP.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.CURSOR.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0);
    }

    @java.lang.SuppressWarnings("all")
    SetAttributesBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetAttributes.SetAttributesBuilder drawable(final int drawable) {
      this.drawable = drawable;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetAttributes.SetAttributesBuilder x(final short x) {
      this.x = x;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetAttributes.SetAttributesBuilder y(final short y) {
      this.y = y;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetAttributes.SetAttributesBuilder width(final short width) {
      this.width = width;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetAttributes.SetAttributesBuilder height(final short height) {
      this.height = height;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetAttributes.SetAttributesBuilder borderWidth(final short borderWidth) {
      this.borderWidth = borderWidth;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetAttributes.SetAttributesBuilder depth(final byte depth) {
      this.depth = depth;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetAttributes.SetAttributesBuilder visual(final int visual) {
      this.visual = visual;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetAttributes build() {
      return new SetAttributes(this.drawable, this.x, this.y, this.width, this.height, this.borderWidth, this.clazz, this.depth, this.visual, this.valueMask, this.backgroundPixmap, this.backgroundPixel, this.borderPixmap, this.borderPixel, this.bitGravity, this.winGravity, this.backingStore, this.backingPlanes, this.backingPixel, this.overrideRedirect, this.saveUnder, this.eventMask, this.doNotPropogateMask, this.colormap, this.cursor);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetAttributes.SetAttributesBuilder(drawable=" + this.drawable + ", x=" + this.x + ", y=" + this.y + ", width=" + this.width + ", height=" + this.height + ", borderWidth=" + this.borderWidth + ", clazz=" + this.clazz + ", depth=" + this.depth + ", visual=" + this.visual + ", valueMask=" + this.valueMask + ", backgroundPixmap=" + this.backgroundPixmap + ", backgroundPixel=" + this.backgroundPixel + ", borderPixmap=" + this.borderPixmap + ", borderPixel=" + this.borderPixel + ", bitGravity=" + this.bitGravity + ", winGravity=" + this.winGravity + ", backingStore=" + this.backingStore + ", backingPlanes=" + this.backingPlanes + ", backingPixel=" + this.backingPixel + ", overrideRedirect=" + this.overrideRedirect + ", saveUnder=" + this.saveUnder + ", eventMask=" + this.eventMask + ", doNotPropogateMask=" + this.doNotPropogateMask + ", colormap=" + this.colormap + ", cursor=" + this.cursor + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetAttributes(final int drawable, final short x, final short y, final short width, final short height, final short borderWidth, final byte clazz, final byte depth, final int visual, final int valueMask, final int backgroundPixmap, final int backgroundPixel, final int borderPixmap, final int borderPixel, final int bitGravity, final int winGravity, final int backingStore, final int backingPlanes, final int backingPixel, final int overrideRedirect, final int saveUnder, final int eventMask, final int doNotPropogateMask, final int colormap, final int cursor) {
    this.drawable = drawable;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.borderWidth = borderWidth;
    this.clazz = clazz;
    this.depth = depth;
    this.visual = visual;
    this.valueMask = valueMask;
    this.backgroundPixmap = backgroundPixmap;
    this.backgroundPixel = backgroundPixel;
    this.borderPixmap = borderPixmap;
    this.borderPixel = borderPixel;
    this.bitGravity = bitGravity;
    this.winGravity = winGravity;
    this.backingStore = backingStore;
    this.backingPlanes = backingPlanes;
    this.backingPixel = backingPixel;
    this.overrideRedirect = overrideRedirect;
    this.saveUnder = saveUnder;
    this.eventMask = eventMask;
    this.doNotPropogateMask = doNotPropogateMask;
    this.colormap = colormap;
    this.cursor = cursor;
  }

  @java.lang.SuppressWarnings("all")
  public static SetAttributes.SetAttributesBuilder builder() {
    return new SetAttributes.SetAttributesBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getDrawable() {
    return this.drawable;
  }

  @java.lang.SuppressWarnings("all")
  public short getX() {
    return this.x;
  }

  @java.lang.SuppressWarnings("all")
  public short getY() {
    return this.y;
  }

  @java.lang.SuppressWarnings("all")
  public short getWidth() {
    return this.width;
  }

  @java.lang.SuppressWarnings("all")
  public short getHeight() {
    return this.height;
  }

  @java.lang.SuppressWarnings("all")
  public short getBorderWidth() {
    return this.borderWidth;
  }

  @java.lang.SuppressWarnings("all")
  public byte getClazz() {
    return this.clazz;
  }

  @java.lang.SuppressWarnings("all")
  public byte getDepth() {
    return this.depth;
  }

  @java.lang.SuppressWarnings("all")
  public int getVisual() {
    return this.visual;
  }

  @java.lang.SuppressWarnings("all")
  public int getValueMask() {
    return this.valueMask;
  }

  @java.lang.SuppressWarnings("all")
  public int getBackgroundPixmap() {
    return this.backgroundPixmap;
  }

  @java.lang.SuppressWarnings("all")
  public int getBackgroundPixel() {
    return this.backgroundPixel;
  }

  @java.lang.SuppressWarnings("all")
  public int getBorderPixmap() {
    return this.borderPixmap;
  }

  @java.lang.SuppressWarnings("all")
  public int getBorderPixel() {
    return this.borderPixel;
  }

  @java.lang.SuppressWarnings("all")
  public int getBitGravity() {
    return this.bitGravity;
  }

  @java.lang.SuppressWarnings("all")
  public int getWinGravity() {
    return this.winGravity;
  }

  @java.lang.SuppressWarnings("all")
  public int getBackingStore() {
    return this.backingStore;
  }

  @java.lang.SuppressWarnings("all")
  public int getBackingPlanes() {
    return this.backingPlanes;
  }

  @java.lang.SuppressWarnings("all")
  public int getBackingPixel() {
    return this.backingPixel;
  }

  @java.lang.SuppressWarnings("all")
  public int getOverrideRedirect() {
    return this.overrideRedirect;
  }

  @java.lang.SuppressWarnings("all")
  public int getSaveUnder() {
    return this.saveUnder;
  }

  @java.lang.SuppressWarnings("all")
  public int getEventMask() {
    return this.eventMask;
  }

  @java.lang.SuppressWarnings("all")
  public int getDoNotPropogateMask() {
    return this.doNotPropogateMask;
  }

  @java.lang.SuppressWarnings("all")
  public int getColormap() {
    return this.colormap;
  }

  @java.lang.SuppressWarnings("all")
  public int getCursor() {
    return this.cursor;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetAttributes)) return false;
    final SetAttributes other = (SetAttributes) o;
    if (this.getDrawable() != other.getDrawable()) return false;
    if (this.getX() != other.getX()) return false;
    if (this.getY() != other.getY()) return false;
    if (this.getWidth() != other.getWidth()) return false;
    if (this.getHeight() != other.getHeight()) return false;
    if (this.getBorderWidth() != other.getBorderWidth()) return false;
    if (this.getClazz() != other.getClazz()) return false;
    if (this.getDepth() != other.getDepth()) return false;
    if (this.getVisual() != other.getVisual()) return false;
    if (this.getValueMask() != other.getValueMask()) return false;
    if (this.getBackgroundPixmap() != other.getBackgroundPixmap()) return false;
    if (this.getBackgroundPixel() != other.getBackgroundPixel()) return false;
    if (this.getBorderPixmap() != other.getBorderPixmap()) return false;
    if (this.getBorderPixel() != other.getBorderPixel()) return false;
    if (this.getBitGravity() != other.getBitGravity()) return false;
    if (this.getWinGravity() != other.getWinGravity()) return false;
    if (this.getBackingStore() != other.getBackingStore()) return false;
    if (this.getBackingPlanes() != other.getBackingPlanes()) return false;
    if (this.getBackingPixel() != other.getBackingPixel()) return false;
    if (this.getOverrideRedirect() != other.getOverrideRedirect()) return false;
    if (this.getSaveUnder() != other.getSaveUnder()) return false;
    if (this.getEventMask() != other.getEventMask()) return false;
    if (this.getDoNotPropogateMask() != other.getDoNotPropogateMask()) return false;
    if (this.getColormap() != other.getColormap()) return false;
    if (this.getCursor() != other.getCursor()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDrawable();
    result = result * PRIME + this.getX();
    result = result * PRIME + this.getY();
    result = result * PRIME + this.getWidth();
    result = result * PRIME + this.getHeight();
    result = result * PRIME + this.getBorderWidth();
    result = result * PRIME + this.getClazz();
    result = result * PRIME + this.getDepth();
    result = result * PRIME + this.getVisual();
    result = result * PRIME + this.getValueMask();
    result = result * PRIME + this.getBackgroundPixmap();
    result = result * PRIME + this.getBackgroundPixel();
    result = result * PRIME + this.getBorderPixmap();
    result = result * PRIME + this.getBorderPixel();
    result = result * PRIME + this.getBitGravity();
    result = result * PRIME + this.getWinGravity();
    result = result * PRIME + this.getBackingStore();
    result = result * PRIME + this.getBackingPlanes();
    result = result * PRIME + this.getBackingPixel();
    result = result * PRIME + this.getOverrideRedirect();
    result = result * PRIME + this.getSaveUnder();
    result = result * PRIME + this.getEventMask();
    result = result * PRIME + this.getDoNotPropogateMask();
    result = result * PRIME + this.getColormap();
    result = result * PRIME + this.getCursor();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetAttributes(drawable=" + this.getDrawable() + ", x=" + this.getX() + ", y=" + this.getY() + ", width=" + this.getWidth() + ", height=" + this.getHeight() + ", borderWidth=" + this.getBorderWidth() + ", clazz=" + this.getClazz() + ", depth=" + this.getDepth() + ", visual=" + this.getVisual() + ", valueMask=" + this.getValueMask() + ", backgroundPixmap=" + this.getBackgroundPixmap() + ", backgroundPixel=" + this.getBackgroundPixel() + ", borderPixmap=" + this.getBorderPixmap() + ", borderPixel=" + this.getBorderPixel() + ", bitGravity=" + this.getBitGravity() + ", winGravity=" + this.getWinGravity() + ", backingStore=" + this.getBackingStore() + ", backingPlanes=" + this.getBackingPlanes() + ", backingPixel=" + this.getBackingPixel() + ", overrideRedirect=" + this.getOverrideRedirect() + ", saveUnder=" + this.getSaveUnder() + ", eventMask=" + this.getEventMask() + ", doNotPropogateMask=" + this.getDoNotPropogateMask() + ", colormap=" + this.getColormap() + ", cursor=" + this.getCursor() + ")";
  }
}
