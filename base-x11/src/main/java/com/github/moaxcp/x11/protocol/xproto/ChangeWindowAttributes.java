package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class ChangeWindowAttributes implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 2;
  private final int window;
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

  public static ChangeWindowAttributes readChangeWindowAttributes(X11Input in) throws IOException {
    ChangeWindowAttributes.ChangeWindowAttributesBuilder javaBuilder = ChangeWindowAttributes.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int window = in.readCard32();
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
    javaBuilder.window(window);
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
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
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
    return 12 + (Cw.BACK_PIXMAP.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BACK_PIXEL.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BORDER_PIXMAP.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BORDER_PIXEL.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BIT_GRAVITY.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.WIN_GRAVITY.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BACKING_STORE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BACKING_PLANES.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BACKING_PIXEL.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.OVERRIDE_REDIRECT.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.SAVE_UNDER.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.EVENT_MASK.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.DONT_PROPAGATE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.COLORMAP.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.CURSOR.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ChangeWindowAttributesBuilder {
    @java.lang.SuppressWarnings("all")
    private int window;
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

    private ChangeWindowAttributes.ChangeWindowAttributesBuilder valueMask(int valueMask) {
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

    private ChangeWindowAttributes.ChangeWindowAttributesBuilder valueMaskEnable(Cw... maskEnums) {
      for (Cw m : maskEnums) {
        valueMask((int) m.enableFor(valueMask));
      }
      return this;
    }

    private ChangeWindowAttributes.ChangeWindowAttributesBuilder valueMaskDisable(Cw... maskEnums) {
      for (Cw m : maskEnums) {
        valueMask((int) m.disableFor(valueMask));
      }
      return this;
    }

    public ChangeWindowAttributes.ChangeWindowAttributesBuilder backgroundPixmap(int backgroundPixmap) {
      this.backgroundPixmap = backgroundPixmap;
      valueMaskEnable(Cw.BACK_PIXMAP);
      return this;
    }

    public ChangeWindowAttributes.ChangeWindowAttributesBuilder backgroundPixel(int backgroundPixel) {
      this.backgroundPixel = backgroundPixel;
      valueMaskEnable(Cw.BACK_PIXEL);
      return this;
    }

    public ChangeWindowAttributes.ChangeWindowAttributesBuilder borderPixmap(int borderPixmap) {
      this.borderPixmap = borderPixmap;
      valueMaskEnable(Cw.BORDER_PIXMAP);
      return this;
    }

    public ChangeWindowAttributes.ChangeWindowAttributesBuilder borderPixel(int borderPixel) {
      this.borderPixel = borderPixel;
      valueMaskEnable(Cw.BORDER_PIXEL);
      return this;
    }

    public ChangeWindowAttributes.ChangeWindowAttributesBuilder bitGravity(int bitGravity) {
      this.bitGravity = bitGravity;
      valueMaskEnable(Cw.BIT_GRAVITY);
      return this;
    }

    public ChangeWindowAttributes.ChangeWindowAttributesBuilder bitGravity(Gravity bitGravity) {
      this.bitGravity = (int) bitGravity.getValue();
      valueMaskEnable(Cw.BIT_GRAVITY);
      return this;
    }

    public ChangeWindowAttributes.ChangeWindowAttributesBuilder winGravity(int winGravity) {
      this.winGravity = winGravity;
      valueMaskEnable(Cw.WIN_GRAVITY);
      return this;
    }

    public ChangeWindowAttributes.ChangeWindowAttributesBuilder winGravity(Gravity winGravity) {
      this.winGravity = (int) winGravity.getValue();
      valueMaskEnable(Cw.WIN_GRAVITY);
      return this;
    }

    public ChangeWindowAttributes.ChangeWindowAttributesBuilder backingStore(int backingStore) {
      this.backingStore = backingStore;
      valueMaskEnable(Cw.BACKING_STORE);
      return this;
    }

    public ChangeWindowAttributes.ChangeWindowAttributesBuilder backingStore(BackingStore backingStore) {
      this.backingStore = (int) backingStore.getValue();
      valueMaskEnable(Cw.BACKING_STORE);
      return this;
    }

    public ChangeWindowAttributes.ChangeWindowAttributesBuilder backingPlanes(int backingPlanes) {
      this.backingPlanes = backingPlanes;
      valueMaskEnable(Cw.BACKING_PLANES);
      return this;
    }

    public ChangeWindowAttributes.ChangeWindowAttributesBuilder backingPixel(int backingPixel) {
      this.backingPixel = backingPixel;
      valueMaskEnable(Cw.BACKING_PIXEL);
      return this;
    }

    public ChangeWindowAttributes.ChangeWindowAttributesBuilder overrideRedirect(int overrideRedirect) {
      this.overrideRedirect = overrideRedirect;
      valueMaskEnable(Cw.OVERRIDE_REDIRECT);
      return this;
    }

    public ChangeWindowAttributes.ChangeWindowAttributesBuilder saveUnder(int saveUnder) {
      this.saveUnder = saveUnder;
      valueMaskEnable(Cw.SAVE_UNDER);
      return this;
    }

    public ChangeWindowAttributes.ChangeWindowAttributesBuilder eventMask(int eventMask) {
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

    public ChangeWindowAttributes.ChangeWindowAttributesBuilder eventMaskEnable(EventMask... maskEnums) {
      for (EventMask m : maskEnums) {
        eventMask((int) m.enableFor(eventMask));
      }
      return this;
    }

    public ChangeWindowAttributes.ChangeWindowAttributesBuilder eventMaskDisable(EventMask... maskEnums) {
      for (EventMask m : maskEnums) {
        eventMask((int) m.disableFor(eventMask));
      }
      return this;
    }

    public ChangeWindowAttributes.ChangeWindowAttributesBuilder doNotPropogateMask(int doNotPropogateMask) {
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

    public ChangeWindowAttributes.ChangeWindowAttributesBuilder doNotPropogateMaskEnable(EventMask... maskEnums) {
      for (EventMask m : maskEnums) {
        doNotPropogateMask((int) m.enableFor(doNotPropogateMask));
      }
      return this;
    }

    public ChangeWindowAttributes.ChangeWindowAttributesBuilder doNotPropogateMaskDisable(EventMask... maskEnums) {
      for (EventMask m : maskEnums) {
        doNotPropogateMask((int) m.disableFor(doNotPropogateMask));
      }
      return this;
    }

    public ChangeWindowAttributes.ChangeWindowAttributesBuilder colormap(int colormap) {
      this.colormap = colormap;
      valueMaskEnable(Cw.COLORMAP);
      return this;
    }

    public ChangeWindowAttributes.ChangeWindowAttributesBuilder cursor(int cursor) {
      this.cursor = cursor;
      valueMaskEnable(Cw.CURSOR);
      return this;
    }

    public int getSize() {
      return 12 + (Cw.BACK_PIXMAP.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BACK_PIXEL.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BORDER_PIXMAP.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BORDER_PIXEL.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BIT_GRAVITY.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.WIN_GRAVITY.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BACKING_STORE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BACKING_PLANES.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.BACKING_PIXEL.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.OVERRIDE_REDIRECT.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.SAVE_UNDER.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.EVENT_MASK.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.DONT_PROPAGATE.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.COLORMAP.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0) + (Cw.CURSOR.isEnabled((int) (Integer.toUnsignedLong(valueMask))) ? 4 : 0);
    }

    @java.lang.SuppressWarnings("all")
    ChangeWindowAttributesBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangeWindowAttributes.ChangeWindowAttributesBuilder window(final int window) {
      this.window = window;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ChangeWindowAttributes build() {
      return new ChangeWindowAttributes(this.window, this.valueMask, this.backgroundPixmap, this.backgroundPixel, this.borderPixmap, this.borderPixel, this.bitGravity, this.winGravity, this.backingStore, this.backingPlanes, this.backingPixel, this.overrideRedirect, this.saveUnder, this.eventMask, this.doNotPropogateMask, this.colormap, this.cursor);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ChangeWindowAttributes.ChangeWindowAttributesBuilder(window=" + this.window + ", valueMask=" + this.valueMask + ", backgroundPixmap=" + this.backgroundPixmap + ", backgroundPixel=" + this.backgroundPixel + ", borderPixmap=" + this.borderPixmap + ", borderPixel=" + this.borderPixel + ", bitGravity=" + this.bitGravity + ", winGravity=" + this.winGravity + ", backingStore=" + this.backingStore + ", backingPlanes=" + this.backingPlanes + ", backingPixel=" + this.backingPixel + ", overrideRedirect=" + this.overrideRedirect + ", saveUnder=" + this.saveUnder + ", eventMask=" + this.eventMask + ", doNotPropogateMask=" + this.doNotPropogateMask + ", colormap=" + this.colormap + ", cursor=" + this.cursor + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ChangeWindowAttributes(final int window, final int valueMask, final int backgroundPixmap, final int backgroundPixel, final int borderPixmap, final int borderPixel, final int bitGravity, final int winGravity, final int backingStore, final int backingPlanes, final int backingPixel, final int overrideRedirect, final int saveUnder, final int eventMask, final int doNotPropogateMask, final int colormap, final int cursor) {
    this.window = window;
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
  public static ChangeWindowAttributes.ChangeWindowAttributesBuilder builder() {
    return new ChangeWindowAttributes.ChangeWindowAttributesBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
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
    if (!(o instanceof ChangeWindowAttributes)) return false;
    final ChangeWindowAttributes other = (ChangeWindowAttributes) o;
    if (this.getWindow() != other.getWindow()) return false;
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
    result = result * PRIME + this.getWindow();
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
    return "ChangeWindowAttributes(window=" + this.getWindow() + ", valueMask=" + this.getValueMask() + ", backgroundPixmap=" + this.getBackgroundPixmap() + ", backgroundPixel=" + this.getBackgroundPixel() + ", borderPixmap=" + this.getBorderPixmap() + ", borderPixel=" + this.getBorderPixel() + ", bitGravity=" + this.getBitGravity() + ", winGravity=" + this.getWinGravity() + ", backingStore=" + this.getBackingStore() + ", backingPlanes=" + this.getBackingPlanes() + ", backingPixel=" + this.getBackingPixel() + ", overrideRedirect=" + this.getOverrideRedirect() + ", saveUnder=" + this.getSaveUnder() + ", eventMask=" + this.getEventMask() + ", doNotPropogateMask=" + this.getDoNotPropogateMask() + ", colormap=" + this.getColormap() + ", cursor=" + this.getCursor() + ")";
  }
}
