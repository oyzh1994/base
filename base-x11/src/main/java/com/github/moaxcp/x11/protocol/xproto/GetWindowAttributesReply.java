package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class GetWindowAttributesReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";
  private final byte backingStore;
  private final short sequenceNumber;
  private final int visual;
  private final short clazz;
  private final byte bitGravity;
  private final byte winGravity;
  private final int backingPlanes;
  private final int backingPixel;
  private final boolean saveUnder;
  private final boolean mapIsInstalled;
  private final byte mapState;
  private final boolean overrideRedirect;
  private final int colormap;
  private final int allEventMasks;
  private final int yourEventMask;
  private final short doNotPropagateMask;

  public static GetWindowAttributesReply readGetWindowAttributesReply(byte backingStore, short sequenceNumber, X11Input in) throws IOException {
    GetWindowAttributesReply.GetWindowAttributesReplyBuilder javaBuilder = GetWindowAttributesReply.builder();
    int length = in.readCard32();
    int visual = in.readCard32();
    short clazz = in.readCard16();
    byte bitGravity = in.readCard8();
    byte winGravity = in.readCard8();
    int backingPlanes = in.readCard32();
    int backingPixel = in.readCard32();
    boolean saveUnder = in.readBool();
    boolean mapIsInstalled = in.readBool();
    byte mapState = in.readCard8();
    boolean overrideRedirect = in.readBool();
    int colormap = in.readCard32();
    int allEventMasks = in.readCard32();
    int yourEventMask = in.readCard32();
    short doNotPropagateMask = in.readCard16();
    byte[] pad18 = in.readPad(2);
    javaBuilder.backingStore(backingStore);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.visual(visual);
    javaBuilder.clazz(clazz);
    javaBuilder.bitGravity(bitGravity);
    javaBuilder.winGravity(winGravity);
    javaBuilder.backingPlanes(backingPlanes);
    javaBuilder.backingPixel(backingPixel);
    javaBuilder.saveUnder(saveUnder);
    javaBuilder.mapIsInstalled(mapIsInstalled);
    javaBuilder.mapState(mapState);
    javaBuilder.overrideRedirect(overrideRedirect);
    javaBuilder.colormap(colormap);
    javaBuilder.allEventMasks(allEventMasks);
    javaBuilder.yourEventMask(yourEventMask);
    javaBuilder.doNotPropagateMask(doNotPropagateMask);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(backingStore);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(visual);
    out.writeCard16(clazz);
    out.writeCard8(bitGravity);
    out.writeCard8(winGravity);
    out.writeCard32(backingPlanes);
    out.writeCard32(backingPixel);
    out.writeBool(saveUnder);
    out.writeBool(mapIsInstalled);
    out.writeCard8(mapState);
    out.writeBool(overrideRedirect);
    out.writeCard32(colormap);
    out.writeCard32(allEventMasks);
    out.writeCard32(yourEventMask);
    out.writeCard16(doNotPropagateMask);
    out.writePad(2);
  }

  public boolean isAllEventMasksEnabled(EventMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (EventMask m : maskEnums) {
      if (!m.isEnabled(allEventMasks)) {
        return false;
      }
    }
    return true;
  }

  public boolean isYourEventMaskEnabled(EventMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (EventMask m : maskEnums) {
      if (!m.isEnabled(yourEventMask)) {
        return false;
      }
    }
    return true;
  }

  public boolean isDoNotPropagateMaskEnabled(EventMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (EventMask m : maskEnums) {
      if (!m.isEnabled(doNotPropagateMask)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 44;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetWindowAttributesReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte backingStore;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int visual;
    @java.lang.SuppressWarnings("all")
    private short clazz;
    @java.lang.SuppressWarnings("all")
    private byte bitGravity;
    @java.lang.SuppressWarnings("all")
    private byte winGravity;
    @java.lang.SuppressWarnings("all")
    private int backingPlanes;
    @java.lang.SuppressWarnings("all")
    private int backingPixel;
    @java.lang.SuppressWarnings("all")
    private boolean saveUnder;
    @java.lang.SuppressWarnings("all")
    private boolean mapIsInstalled;
    @java.lang.SuppressWarnings("all")
    private byte mapState;
    @java.lang.SuppressWarnings("all")
    private boolean overrideRedirect;
    @java.lang.SuppressWarnings("all")
    private int colormap;
    @java.lang.SuppressWarnings("all")
    private int allEventMasks;
    @java.lang.SuppressWarnings("all")
    private int yourEventMask;
    @java.lang.SuppressWarnings("all")
    private short doNotPropagateMask;

    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder backingStore(BackingStore backingStore) {
      this.backingStore = (byte) backingStore.getValue();
      return this;
    }

    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder backingStore(byte backingStore) {
      this.backingStore = backingStore;
      return this;
    }

    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder clazz(WindowClass clazz) {
      this.clazz = (short) clazz.getValue();
      return this;
    }

    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder clazz(short clazz) {
      this.clazz = clazz;
      return this;
    }

    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder bitGravity(Gravity bitGravity) {
      this.bitGravity = (byte) bitGravity.getValue();
      return this;
    }

    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder bitGravity(byte bitGravity) {
      this.bitGravity = bitGravity;
      return this;
    }

    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder winGravity(Gravity winGravity) {
      this.winGravity = (byte) winGravity.getValue();
      return this;
    }

    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder winGravity(byte winGravity) {
      this.winGravity = winGravity;
      return this;
    }

    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder mapState(MapState mapState) {
      this.mapState = (byte) mapState.getValue();
      return this;
    }

    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder mapState(byte mapState) {
      this.mapState = mapState;
      return this;
    }

    public boolean isAllEventMasksEnabled(EventMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (EventMask m : maskEnums) {
        if (!m.isEnabled(allEventMasks)) {
          return false;
        }
      }
      return true;
    }

    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder allEventMasksEnable(EventMask... maskEnums) {
      for (EventMask m : maskEnums) {
        allEventMasks((int) m.enableFor(allEventMasks));
      }
      return this;
    }

    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder allEventMasksDisable(EventMask... maskEnums) {
      for (EventMask m : maskEnums) {
        allEventMasks((int) m.disableFor(allEventMasks));
      }
      return this;
    }

    public boolean isYourEventMaskEnabled(EventMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (EventMask m : maskEnums) {
        if (!m.isEnabled(yourEventMask)) {
          return false;
        }
      }
      return true;
    }

    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder yourEventMaskEnable(EventMask... maskEnums) {
      for (EventMask m : maskEnums) {
        yourEventMask((int) m.enableFor(yourEventMask));
      }
      return this;
    }

    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder yourEventMaskDisable(EventMask... maskEnums) {
      for (EventMask m : maskEnums) {
        yourEventMask((int) m.disableFor(yourEventMask));
      }
      return this;
    }

    public boolean isDoNotPropagateMaskEnabled(EventMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (EventMask m : maskEnums) {
        if (!m.isEnabled(doNotPropagateMask)) {
          return false;
        }
      }
      return true;
    }

    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder doNotPropagateMaskEnable(EventMask... maskEnums) {
      for (EventMask m : maskEnums) {
        doNotPropagateMask((short) m.enableFor(doNotPropagateMask));
      }
      return this;
    }

    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder doNotPropagateMaskDisable(EventMask... maskEnums) {
      for (EventMask m : maskEnums) {
        doNotPropagateMask((short) m.disableFor(doNotPropagateMask));
      }
      return this;
    }

    public int getSize() {
      return 44;
    }

    @java.lang.SuppressWarnings("all")
    GetWindowAttributesReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder visual(final int visual) {
      this.visual = visual;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder backingPlanes(final int backingPlanes) {
      this.backingPlanes = backingPlanes;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder backingPixel(final int backingPixel) {
      this.backingPixel = backingPixel;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder saveUnder(final boolean saveUnder) {
      this.saveUnder = saveUnder;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder mapIsInstalled(final boolean mapIsInstalled) {
      this.mapIsInstalled = mapIsInstalled;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder overrideRedirect(final boolean overrideRedirect) {
      this.overrideRedirect = overrideRedirect;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder colormap(final int colormap) {
      this.colormap = colormap;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder allEventMasks(final int allEventMasks) {
      this.allEventMasks = allEventMasks;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder yourEventMask(final int yourEventMask) {
      this.yourEventMask = yourEventMask;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetWindowAttributesReply.GetWindowAttributesReplyBuilder doNotPropagateMask(final short doNotPropagateMask) {
      this.doNotPropagateMask = doNotPropagateMask;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetWindowAttributesReply build() {
      return new GetWindowAttributesReply(this.backingStore, this.sequenceNumber, this.visual, this.clazz, this.bitGravity, this.winGravity, this.backingPlanes, this.backingPixel, this.saveUnder, this.mapIsInstalled, this.mapState, this.overrideRedirect, this.colormap, this.allEventMasks, this.yourEventMask, this.doNotPropagateMask);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetWindowAttributesReply.GetWindowAttributesReplyBuilder(backingStore=" + this.backingStore + ", sequenceNumber=" + this.sequenceNumber + ", visual=" + this.visual + ", clazz=" + this.clazz + ", bitGravity=" + this.bitGravity + ", winGravity=" + this.winGravity + ", backingPlanes=" + this.backingPlanes + ", backingPixel=" + this.backingPixel + ", saveUnder=" + this.saveUnder + ", mapIsInstalled=" + this.mapIsInstalled + ", mapState=" + this.mapState + ", overrideRedirect=" + this.overrideRedirect + ", colormap=" + this.colormap + ", allEventMasks=" + this.allEventMasks + ", yourEventMask=" + this.yourEventMask + ", doNotPropagateMask=" + this.doNotPropagateMask + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetWindowAttributesReply(final byte backingStore, final short sequenceNumber, final int visual, final short clazz, final byte bitGravity, final byte winGravity, final int backingPlanes, final int backingPixel, final boolean saveUnder, final boolean mapIsInstalled, final byte mapState, final boolean overrideRedirect, final int colormap, final int allEventMasks, final int yourEventMask, final short doNotPropagateMask) {
    this.backingStore = backingStore;
    this.sequenceNumber = sequenceNumber;
    this.visual = visual;
    this.clazz = clazz;
    this.bitGravity = bitGravity;
    this.winGravity = winGravity;
    this.backingPlanes = backingPlanes;
    this.backingPixel = backingPixel;
    this.saveUnder = saveUnder;
    this.mapIsInstalled = mapIsInstalled;
    this.mapState = mapState;
    this.overrideRedirect = overrideRedirect;
    this.colormap = colormap;
    this.allEventMasks = allEventMasks;
    this.yourEventMask = yourEventMask;
    this.doNotPropagateMask = doNotPropagateMask;
  }

  @java.lang.SuppressWarnings("all")
  public static GetWindowAttributesReply.GetWindowAttributesReplyBuilder builder() {
    return new GetWindowAttributesReply.GetWindowAttributesReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getBackingStore() {
    return this.backingStore;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getVisual() {
    return this.visual;
  }

  @java.lang.SuppressWarnings("all")
  public short getClazz() {
    return this.clazz;
  }

  @java.lang.SuppressWarnings("all")
  public byte getBitGravity() {
    return this.bitGravity;
  }

  @java.lang.SuppressWarnings("all")
  public byte getWinGravity() {
    return this.winGravity;
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
  public boolean isSaveUnder() {
    return this.saveUnder;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isMapIsInstalled() {
    return this.mapIsInstalled;
  }

  @java.lang.SuppressWarnings("all")
  public byte getMapState() {
    return this.mapState;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isOverrideRedirect() {
    return this.overrideRedirect;
  }

  @java.lang.SuppressWarnings("all")
  public int getColormap() {
    return this.colormap;
  }

  @java.lang.SuppressWarnings("all")
  public int getAllEventMasks() {
    return this.allEventMasks;
  }

  @java.lang.SuppressWarnings("all")
  public int getYourEventMask() {
    return this.yourEventMask;
  }

  @java.lang.SuppressWarnings("all")
  public short getDoNotPropagateMask() {
    return this.doNotPropagateMask;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetWindowAttributesReply)) return false;
    final GetWindowAttributesReply other = (GetWindowAttributesReply) o;
    if (this.getBackingStore() != other.getBackingStore()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getVisual() != other.getVisual()) return false;
    if (this.getClazz() != other.getClazz()) return false;
    if (this.getBitGravity() != other.getBitGravity()) return false;
    if (this.getWinGravity() != other.getWinGravity()) return false;
    if (this.getBackingPlanes() != other.getBackingPlanes()) return false;
    if (this.getBackingPixel() != other.getBackingPixel()) return false;
    if (this.isSaveUnder() != other.isSaveUnder()) return false;
    if (this.isMapIsInstalled() != other.isMapIsInstalled()) return false;
    if (this.getMapState() != other.getMapState()) return false;
    if (this.isOverrideRedirect() != other.isOverrideRedirect()) return false;
    if (this.getColormap() != other.getColormap()) return false;
    if (this.getAllEventMasks() != other.getAllEventMasks()) return false;
    if (this.getYourEventMask() != other.getYourEventMask()) return false;
    if (this.getDoNotPropagateMask() != other.getDoNotPropagateMask()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getBackingStore();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getVisual();
    result = result * PRIME + this.getClazz();
    result = result * PRIME + this.getBitGravity();
    result = result * PRIME + this.getWinGravity();
    result = result * PRIME + this.getBackingPlanes();
    result = result * PRIME + this.getBackingPixel();
    result = result * PRIME + (this.isSaveUnder() ? 79 : 97);
    result = result * PRIME + (this.isMapIsInstalled() ? 79 : 97);
    result = result * PRIME + this.getMapState();
    result = result * PRIME + (this.isOverrideRedirect() ? 79 : 97);
    result = result * PRIME + this.getColormap();
    result = result * PRIME + this.getAllEventMasks();
    result = result * PRIME + this.getYourEventMask();
    result = result * PRIME + this.getDoNotPropagateMask();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetWindowAttributesReply(backingStore=" + this.getBackingStore() + ", sequenceNumber=" + this.getSequenceNumber() + ", visual=" + this.getVisual() + ", clazz=" + this.getClazz() + ", bitGravity=" + this.getBitGravity() + ", winGravity=" + this.getWinGravity() + ", backingPlanes=" + this.getBackingPlanes() + ", backingPixel=" + this.getBackingPixel() + ", saveUnder=" + this.isSaveUnder() + ", mapIsInstalled=" + this.isMapIsInstalled() + ", mapState=" + this.getMapState() + ", overrideRedirect=" + this.isOverrideRedirect() + ", colormap=" + this.getColormap() + ", allEventMasks=" + this.getAllEventMasks() + ", yourEventMask=" + this.getYourEventMask() + ", doNotPropagateMask=" + this.getDoNotPropagateMask() + ")";
  }
}
