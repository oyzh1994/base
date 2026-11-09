package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;

public final class MapNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "xkb";
  public static final byte NUMBER = 1;
  private final byte firstEventOffset;
  private final boolean sentEvent;
  private final byte xkbType;
  private final short sequenceNumber;
  private final int time;
  private final byte deviceID;
  private final byte ptrBtnActions;
  private final short changed;
  private final byte minKeyCode;
  private final byte maxKeyCode;
  private final byte firstType;
  private final byte nTypes;
  private final byte firstKeySym;
  private final byte nKeySyms;
  private final byte firstKeyAct;
  private final byte nKeyActs;
  private final byte firstKeyBehavior;
  private final byte nKeyBehavior;
  private final byte firstKeyExplicit;
  private final byte nKeyExplicit;
  private final byte firstModMapKey;
  private final byte nModMapKeys;
  private final byte firstVModMapKey;
  private final byte nVModMapKeys;
  private final short virtualMods;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static MapNotifyEvent readMapNotifyEvent(byte firstEventOffset, boolean sentEvent, X11Input in) throws IOException {
    MapNotifyEvent.MapNotifyEventBuilder javaBuilder = MapNotifyEvent.builder();
    byte xkbType = in.readCard8();
    short sequenceNumber = in.readCard16();
    int time = in.readCard32();
    byte deviceID = in.readCard8();
    byte ptrBtnActions = in.readCard8();
    short changed = in.readCard16();
    byte minKeyCode = in.readCard8();
    byte maxKeyCode = in.readCard8();
    byte firstType = in.readCard8();
    byte nTypes = in.readCard8();
    byte firstKeySym = in.readCard8();
    byte nKeySyms = in.readCard8();
    byte firstKeyAct = in.readCard8();
    byte nKeyActs = in.readCard8();
    byte firstKeyBehavior = in.readCard8();
    byte nKeyBehavior = in.readCard8();
    byte firstKeyExplicit = in.readCard8();
    byte nKeyExplicit = in.readCard8();
    byte firstModMapKey = in.readCard8();
    byte nModMapKeys = in.readCard8();
    byte firstVModMapKey = in.readCard8();
    byte nVModMapKeys = in.readCard8();
    short virtualMods = in.readCard16();
    byte[] pad24 = in.readPad(2);
    javaBuilder.xkbType(xkbType);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.time(time);
    javaBuilder.deviceID(deviceID);
    javaBuilder.ptrBtnActions(ptrBtnActions);
    javaBuilder.changed(changed);
    javaBuilder.minKeyCode(minKeyCode);
    javaBuilder.maxKeyCode(maxKeyCode);
    javaBuilder.firstType(firstType);
    javaBuilder.nTypes(nTypes);
    javaBuilder.firstKeySym(firstKeySym);
    javaBuilder.nKeySyms(nKeySyms);
    javaBuilder.firstKeyAct(firstKeyAct);
    javaBuilder.nKeyActs(nKeyActs);
    javaBuilder.firstKeyBehavior(firstKeyBehavior);
    javaBuilder.nKeyBehavior(nKeyBehavior);
    javaBuilder.firstKeyExplicit(firstKeyExplicit);
    javaBuilder.nKeyExplicit(nKeyExplicit);
    javaBuilder.firstModMapKey(firstModMapKey);
    javaBuilder.nModMapKeys(nModMapKeys);
    javaBuilder.firstVModMapKey(firstVModMapKey);
    javaBuilder.nVModMapKeys(nVModMapKeys);
    javaBuilder.virtualMods(virtualMods);
    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (128 | getResponseCode()) : getResponseCode());
    out.writeCard8(xkbType);
    out.writeCard16(sequenceNumber);
    out.writeCard32(time);
    out.writeCard8(deviceID);
    out.writeCard8(ptrBtnActions);
    out.writeCard16(changed);
    out.writeCard8(minKeyCode);
    out.writeCard8(maxKeyCode);
    out.writeCard8(firstType);
    out.writeCard8(nTypes);
    out.writeCard8(firstKeySym);
    out.writeCard8(nKeySyms);
    out.writeCard8(firstKeyAct);
    out.writeCard8(nKeyActs);
    out.writeCard8(firstKeyBehavior);
    out.writeCard8(nKeyBehavior);
    out.writeCard8(firstKeyExplicit);
    out.writeCard8(nKeyExplicit);
    out.writeCard8(firstModMapKey);
    out.writeCard8(nModMapKeys);
    out.writeCard8(firstVModMapKey);
    out.writeCard8(nVModMapKeys);
    out.writeCard16(virtualMods);
    out.writePad(2);
  }

  public boolean isChangedEnabled(MapPart... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (MapPart m : maskEnums) {
      if (!m.isEnabled(changed)) {
        return false;
      }
    }
    return true;
  }

  public boolean isVirtualModsEnabled(VMod... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (VMod m : maskEnums) {
      if (!m.isEnabled(virtualMods)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class MapNotifyEventBuilder {
    @java.lang.SuppressWarnings("all")
    private byte firstEventOffset;
    @java.lang.SuppressWarnings("all")
    private boolean sentEvent;
    @java.lang.SuppressWarnings("all")
    private byte xkbType;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int time;
    @java.lang.SuppressWarnings("all")
    private byte deviceID;
    @java.lang.SuppressWarnings("all")
    private byte ptrBtnActions;
    @java.lang.SuppressWarnings("all")
    private short changed;
    @java.lang.SuppressWarnings("all")
    private byte minKeyCode;
    @java.lang.SuppressWarnings("all")
    private byte maxKeyCode;
    @java.lang.SuppressWarnings("all")
    private byte firstType;
    @java.lang.SuppressWarnings("all")
    private byte nTypes;
    @java.lang.SuppressWarnings("all")
    private byte firstKeySym;
    @java.lang.SuppressWarnings("all")
    private byte nKeySyms;
    @java.lang.SuppressWarnings("all")
    private byte firstKeyAct;
    @java.lang.SuppressWarnings("all")
    private byte nKeyActs;
    @java.lang.SuppressWarnings("all")
    private byte firstKeyBehavior;
    @java.lang.SuppressWarnings("all")
    private byte nKeyBehavior;
    @java.lang.SuppressWarnings("all")
    private byte firstKeyExplicit;
    @java.lang.SuppressWarnings("all")
    private byte nKeyExplicit;
    @java.lang.SuppressWarnings("all")
    private byte firstModMapKey;
    @java.lang.SuppressWarnings("all")
    private byte nModMapKeys;
    @java.lang.SuppressWarnings("all")
    private byte firstVModMapKey;
    @java.lang.SuppressWarnings("all")
    private byte nVModMapKeys;
    @java.lang.SuppressWarnings("all")
    private short virtualMods;

    public boolean isChangedEnabled(MapPart... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (MapPart m : maskEnums) {
        if (!m.isEnabled(changed)) {
          return false;
        }
      }
      return true;
    }

    public MapNotifyEvent.MapNotifyEventBuilder changedEnable(MapPart... maskEnums) {
      for (MapPart m : maskEnums) {
        changed((short) m.enableFor(changed));
      }
      return this;
    }

    public MapNotifyEvent.MapNotifyEventBuilder changedDisable(MapPart... maskEnums) {
      for (MapPart m : maskEnums) {
        changed((short) m.disableFor(changed));
      }
      return this;
    }

    public boolean isVirtualModsEnabled(VMod... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (VMod m : maskEnums) {
        if (!m.isEnabled(virtualMods)) {
          return false;
        }
      }
      return true;
    }

    public MapNotifyEvent.MapNotifyEventBuilder virtualModsEnable(VMod... maskEnums) {
      for (VMod m : maskEnums) {
        virtualMods((short) m.enableFor(virtualMods));
      }
      return this;
    }

    public MapNotifyEvent.MapNotifyEventBuilder virtualModsDisable(VMod... maskEnums) {
      for (VMod m : maskEnums) {
        virtualMods((short) m.disableFor(virtualMods));
      }
      return this;
    }

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    MapNotifyEventBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MapNotifyEvent.MapNotifyEventBuilder firstEventOffset(final byte firstEventOffset) {
      this.firstEventOffset = firstEventOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MapNotifyEvent.MapNotifyEventBuilder sentEvent(final boolean sentEvent) {
      this.sentEvent = sentEvent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MapNotifyEvent.MapNotifyEventBuilder xkbType(final byte xkbType) {
      this.xkbType = xkbType;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MapNotifyEvent.MapNotifyEventBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MapNotifyEvent.MapNotifyEventBuilder time(final int time) {
      this.time = time;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MapNotifyEvent.MapNotifyEventBuilder deviceID(final byte deviceID) {
      this.deviceID = deviceID;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MapNotifyEvent.MapNotifyEventBuilder ptrBtnActions(final byte ptrBtnActions) {
      this.ptrBtnActions = ptrBtnActions;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MapNotifyEvent.MapNotifyEventBuilder changed(final short changed) {
      this.changed = changed;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MapNotifyEvent.MapNotifyEventBuilder minKeyCode(final byte minKeyCode) {
      this.minKeyCode = minKeyCode;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MapNotifyEvent.MapNotifyEventBuilder maxKeyCode(final byte maxKeyCode) {
      this.maxKeyCode = maxKeyCode;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MapNotifyEvent.MapNotifyEventBuilder firstType(final byte firstType) {
      this.firstType = firstType;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MapNotifyEvent.MapNotifyEventBuilder nTypes(final byte nTypes) {
      this.nTypes = nTypes;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MapNotifyEvent.MapNotifyEventBuilder firstKeySym(final byte firstKeySym) {
      this.firstKeySym = firstKeySym;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MapNotifyEvent.MapNotifyEventBuilder nKeySyms(final byte nKeySyms) {
      this.nKeySyms = nKeySyms;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MapNotifyEvent.MapNotifyEventBuilder firstKeyAct(final byte firstKeyAct) {
      this.firstKeyAct = firstKeyAct;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MapNotifyEvent.MapNotifyEventBuilder nKeyActs(final byte nKeyActs) {
      this.nKeyActs = nKeyActs;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MapNotifyEvent.MapNotifyEventBuilder firstKeyBehavior(final byte firstKeyBehavior) {
      this.firstKeyBehavior = firstKeyBehavior;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MapNotifyEvent.MapNotifyEventBuilder nKeyBehavior(final byte nKeyBehavior) {
      this.nKeyBehavior = nKeyBehavior;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MapNotifyEvent.MapNotifyEventBuilder firstKeyExplicit(final byte firstKeyExplicit) {
      this.firstKeyExplicit = firstKeyExplicit;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MapNotifyEvent.MapNotifyEventBuilder nKeyExplicit(final byte nKeyExplicit) {
      this.nKeyExplicit = nKeyExplicit;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MapNotifyEvent.MapNotifyEventBuilder firstModMapKey(final byte firstModMapKey) {
      this.firstModMapKey = firstModMapKey;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MapNotifyEvent.MapNotifyEventBuilder nModMapKeys(final byte nModMapKeys) {
      this.nModMapKeys = nModMapKeys;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MapNotifyEvent.MapNotifyEventBuilder firstVModMapKey(final byte firstVModMapKey) {
      this.firstVModMapKey = firstVModMapKey;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MapNotifyEvent.MapNotifyEventBuilder nVModMapKeys(final byte nVModMapKeys) {
      this.nVModMapKeys = nVModMapKeys;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MapNotifyEvent.MapNotifyEventBuilder virtualMods(final short virtualMods) {
      this.virtualMods = virtualMods;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public MapNotifyEvent build() {
      return new MapNotifyEvent(this.firstEventOffset, this.sentEvent, this.xkbType, this.sequenceNumber, this.time, this.deviceID, this.ptrBtnActions, this.changed, this.minKeyCode, this.maxKeyCode, this.firstType, this.nTypes, this.firstKeySym, this.nKeySyms, this.firstKeyAct, this.nKeyActs, this.firstKeyBehavior, this.nKeyBehavior, this.firstKeyExplicit, this.nKeyExplicit, this.firstModMapKey, this.nModMapKeys, this.firstVModMapKey, this.nVModMapKeys, this.virtualMods);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "MapNotifyEvent.MapNotifyEventBuilder(firstEventOffset=" + this.firstEventOffset + ", sentEvent=" + this.sentEvent + ", xkbType=" + this.xkbType + ", sequenceNumber=" + this.sequenceNumber + ", time=" + this.time + ", deviceID=" + this.deviceID + ", ptrBtnActions=" + this.ptrBtnActions + ", changed=" + this.changed + ", minKeyCode=" + this.minKeyCode + ", maxKeyCode=" + this.maxKeyCode + ", firstType=" + this.firstType + ", nTypes=" + this.nTypes + ", firstKeySym=" + this.firstKeySym + ", nKeySyms=" + this.nKeySyms + ", firstKeyAct=" + this.firstKeyAct + ", nKeyActs=" + this.nKeyActs + ", firstKeyBehavior=" + this.firstKeyBehavior + ", nKeyBehavior=" + this.nKeyBehavior + ", firstKeyExplicit=" + this.firstKeyExplicit + ", nKeyExplicit=" + this.nKeyExplicit + ", firstModMapKey=" + this.firstModMapKey + ", nModMapKeys=" + this.nModMapKeys + ", firstVModMapKey=" + this.firstVModMapKey + ", nVModMapKeys=" + this.nVModMapKeys + ", virtualMods=" + this.virtualMods + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  MapNotifyEvent(final byte firstEventOffset, final boolean sentEvent, final byte xkbType, final short sequenceNumber, final int time, final byte deviceID, final byte ptrBtnActions, final short changed, final byte minKeyCode, final byte maxKeyCode, final byte firstType, final byte nTypes, final byte firstKeySym, final byte nKeySyms, final byte firstKeyAct, final byte nKeyActs, final byte firstKeyBehavior, final byte nKeyBehavior, final byte firstKeyExplicit, final byte nKeyExplicit, final byte firstModMapKey, final byte nModMapKeys, final byte firstVModMapKey, final byte nVModMapKeys, final short virtualMods) {
    this.firstEventOffset = firstEventOffset;
    this.sentEvent = sentEvent;
    this.xkbType = xkbType;
    this.sequenceNumber = sequenceNumber;
    this.time = time;
    this.deviceID = deviceID;
    this.ptrBtnActions = ptrBtnActions;
    this.changed = changed;
    this.minKeyCode = minKeyCode;
    this.maxKeyCode = maxKeyCode;
    this.firstType = firstType;
    this.nTypes = nTypes;
    this.firstKeySym = firstKeySym;
    this.nKeySyms = nKeySyms;
    this.firstKeyAct = firstKeyAct;
    this.nKeyActs = nKeyActs;
    this.firstKeyBehavior = firstKeyBehavior;
    this.nKeyBehavior = nKeyBehavior;
    this.firstKeyExplicit = firstKeyExplicit;
    this.nKeyExplicit = nKeyExplicit;
    this.firstModMapKey = firstModMapKey;
    this.nModMapKeys = nModMapKeys;
    this.firstVModMapKey = firstVModMapKey;
    this.nVModMapKeys = nVModMapKeys;
    this.virtualMods = virtualMods;
  }

  @java.lang.SuppressWarnings("all")
  public static MapNotifyEvent.MapNotifyEventBuilder builder() {
    return new MapNotifyEvent.MapNotifyEventBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getFirstEventOffset() {
    return this.firstEventOffset;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isSentEvent() {
    return this.sentEvent;
  }

  @java.lang.SuppressWarnings("all")
  public byte getXkbType() {
    return this.xkbType;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getTime() {
    return this.time;
  }

  @java.lang.SuppressWarnings("all")
  public byte getDeviceID() {
    return this.deviceID;
  }

  @java.lang.SuppressWarnings("all")
  public byte getPtrBtnActions() {
    return this.ptrBtnActions;
  }

  @java.lang.SuppressWarnings("all")
  public short getChanged() {
    return this.changed;
  }

  @java.lang.SuppressWarnings("all")
  public byte getMinKeyCode() {
    return this.minKeyCode;
  }

  @java.lang.SuppressWarnings("all")
  public byte getMaxKeyCode() {
    return this.maxKeyCode;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFirstType() {
    return this.firstType;
  }

  @java.lang.SuppressWarnings("all")
  public byte getNTypes() {
    return this.nTypes;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFirstKeySym() {
    return this.firstKeySym;
  }

  @java.lang.SuppressWarnings("all")
  public byte getNKeySyms() {
    return this.nKeySyms;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFirstKeyAct() {
    return this.firstKeyAct;
  }

  @java.lang.SuppressWarnings("all")
  public byte getNKeyActs() {
    return this.nKeyActs;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFirstKeyBehavior() {
    return this.firstKeyBehavior;
  }

  @java.lang.SuppressWarnings("all")
  public byte getNKeyBehavior() {
    return this.nKeyBehavior;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFirstKeyExplicit() {
    return this.firstKeyExplicit;
  }

  @java.lang.SuppressWarnings("all")
  public byte getNKeyExplicit() {
    return this.nKeyExplicit;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFirstModMapKey() {
    return this.firstModMapKey;
  }

  @java.lang.SuppressWarnings("all")
  public byte getNModMapKeys() {
    return this.nModMapKeys;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFirstVModMapKey() {
    return this.firstVModMapKey;
  }

  @java.lang.SuppressWarnings("all")
  public byte getNVModMapKeys() {
    return this.nVModMapKeys;
  }

  @java.lang.SuppressWarnings("all")
  public short getVirtualMods() {
    return this.virtualMods;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof MapNotifyEvent)) return false;
    final MapNotifyEvent other = (MapNotifyEvent) o;
    if (this.getFirstEventOffset() != other.getFirstEventOffset()) return false;
    if (this.isSentEvent() != other.isSentEvent()) return false;
    if (this.getXkbType() != other.getXkbType()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getTime() != other.getTime()) return false;
    if (this.getDeviceID() != other.getDeviceID()) return false;
    if (this.getPtrBtnActions() != other.getPtrBtnActions()) return false;
    if (this.getChanged() != other.getChanged()) return false;
    if (this.getMinKeyCode() != other.getMinKeyCode()) return false;
    if (this.getMaxKeyCode() != other.getMaxKeyCode()) return false;
    if (this.getFirstType() != other.getFirstType()) return false;
    if (this.getNTypes() != other.getNTypes()) return false;
    if (this.getFirstKeySym() != other.getFirstKeySym()) return false;
    if (this.getNKeySyms() != other.getNKeySyms()) return false;
    if (this.getFirstKeyAct() != other.getFirstKeyAct()) return false;
    if (this.getNKeyActs() != other.getNKeyActs()) return false;
    if (this.getFirstKeyBehavior() != other.getFirstKeyBehavior()) return false;
    if (this.getNKeyBehavior() != other.getNKeyBehavior()) return false;
    if (this.getFirstKeyExplicit() != other.getFirstKeyExplicit()) return false;
    if (this.getNKeyExplicit() != other.getNKeyExplicit()) return false;
    if (this.getFirstModMapKey() != other.getFirstModMapKey()) return false;
    if (this.getNModMapKeys() != other.getNModMapKeys()) return false;
    if (this.getFirstVModMapKey() != other.getFirstVModMapKey()) return false;
    if (this.getNVModMapKeys() != other.getNVModMapKeys()) return false;
    if (this.getVirtualMods() != other.getVirtualMods()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFirstEventOffset();
    result = result * PRIME + (this.isSentEvent() ? 79 : 97);
    result = result * PRIME + this.getXkbType();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getTime();
    result = result * PRIME + this.getDeviceID();
    result = result * PRIME + this.getPtrBtnActions();
    result = result * PRIME + this.getChanged();
    result = result * PRIME + this.getMinKeyCode();
    result = result * PRIME + this.getMaxKeyCode();
    result = result * PRIME + this.getFirstType();
    result = result * PRIME + this.getNTypes();
    result = result * PRIME + this.getFirstKeySym();
    result = result * PRIME + this.getNKeySyms();
    result = result * PRIME + this.getFirstKeyAct();
    result = result * PRIME + this.getNKeyActs();
    result = result * PRIME + this.getFirstKeyBehavior();
    result = result * PRIME + this.getNKeyBehavior();
    result = result * PRIME + this.getFirstKeyExplicit();
    result = result * PRIME + this.getNKeyExplicit();
    result = result * PRIME + this.getFirstModMapKey();
    result = result * PRIME + this.getNModMapKeys();
    result = result * PRIME + this.getFirstVModMapKey();
    result = result * PRIME + this.getNVModMapKeys();
    result = result * PRIME + this.getVirtualMods();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "MapNotifyEvent(firstEventOffset=" + this.getFirstEventOffset() + ", sentEvent=" + this.isSentEvent() + ", xkbType=" + this.getXkbType() + ", sequenceNumber=" + this.getSequenceNumber() + ", time=" + this.getTime() + ", deviceID=" + this.getDeviceID() + ", ptrBtnActions=" + this.getPtrBtnActions() + ", changed=" + this.getChanged() + ", minKeyCode=" + this.getMinKeyCode() + ", maxKeyCode=" + this.getMaxKeyCode() + ", firstType=" + this.getFirstType() + ", nTypes=" + this.getNTypes() + ", firstKeySym=" + this.getFirstKeySym() + ", nKeySyms=" + this.getNKeySyms() + ", firstKeyAct=" + this.getFirstKeyAct() + ", nKeyActs=" + this.getNKeyActs() + ", firstKeyBehavior=" + this.getFirstKeyBehavior() + ", nKeyBehavior=" + this.getNKeyBehavior() + ", firstKeyExplicit=" + this.getFirstKeyExplicit() + ", nKeyExplicit=" + this.getNKeyExplicit() + ", firstModMapKey=" + this.getFirstModMapKey() + ", nModMapKeys=" + this.getNModMapKeys() + ", firstVModMapKey=" + this.getFirstVModMapKey() + ", nVModMapKeys=" + this.getNVModMapKeys() + ", virtualMods=" + this.getVirtualMods() + ")";
  }
}
