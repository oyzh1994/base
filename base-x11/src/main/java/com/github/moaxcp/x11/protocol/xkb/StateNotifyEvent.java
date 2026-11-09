package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import com.github.moaxcp.x11.protocol.xproto.KeyButMask;
import com.github.moaxcp.x11.protocol.xproto.ModMask;
import java.io.IOException;

public final class StateNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "xkb";
  public static final byte NUMBER = 2;
  private final byte firstEventOffset;
  private final boolean sentEvent;
  private final byte xkbType;
  private final short sequenceNumber;
  private final int time;
  private final byte deviceID;
  private final byte mods;
  private final byte baseMods;
  private final byte latchedMods;
  private final byte lockedMods;
  private final byte group;
  private final short baseGroup;
  private final short latchedGroup;
  private final byte lockedGroup;
  private final byte compatState;
  private final byte grabMods;
  private final byte compatGrabMods;
  private final byte lookupMods;
  private final byte compatLoockupMods;
  private final short ptrBtnState;
  private final short changed;
  private final byte keycode;
  private final byte eventType;
  private final byte requestMajor;
  private final byte requestMinor;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static StateNotifyEvent readStateNotifyEvent(byte firstEventOffset, boolean sentEvent, X11Input in) throws IOException {
    StateNotifyEvent.StateNotifyEventBuilder javaBuilder = StateNotifyEvent.builder();
    byte xkbType = in.readCard8();
    short sequenceNumber = in.readCard16();
    int time = in.readCard32();
    byte deviceID = in.readCard8();
    byte mods = in.readCard8();
    byte baseMods = in.readCard8();
    byte latchedMods = in.readCard8();
    byte lockedMods = in.readCard8();
    byte group = in.readCard8();
    short baseGroup = in.readInt16();
    short latchedGroup = in.readInt16();
    byte lockedGroup = in.readCard8();
    byte compatState = in.readCard8();
    byte grabMods = in.readCard8();
    byte compatGrabMods = in.readCard8();
    byte lookupMods = in.readCard8();
    byte compatLoockupMods = in.readCard8();
    short ptrBtnState = in.readCard16();
    short changed = in.readCard16();
    byte keycode = in.readCard8();
    byte eventType = in.readCard8();
    byte requestMajor = in.readCard8();
    byte requestMinor = in.readCard8();
    javaBuilder.xkbType(xkbType);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.time(time);
    javaBuilder.deviceID(deviceID);
    javaBuilder.mods(mods);
    javaBuilder.baseMods(baseMods);
    javaBuilder.latchedMods(latchedMods);
    javaBuilder.lockedMods(lockedMods);
    javaBuilder.group(group);
    javaBuilder.baseGroup(baseGroup);
    javaBuilder.latchedGroup(latchedGroup);
    javaBuilder.lockedGroup(lockedGroup);
    javaBuilder.compatState(compatState);
    javaBuilder.grabMods(grabMods);
    javaBuilder.compatGrabMods(compatGrabMods);
    javaBuilder.lookupMods(lookupMods);
    javaBuilder.compatLoockupMods(compatLoockupMods);
    javaBuilder.ptrBtnState(ptrBtnState);
    javaBuilder.changed(changed);
    javaBuilder.keycode(keycode);
    javaBuilder.eventType(eventType);
    javaBuilder.requestMajor(requestMajor);
    javaBuilder.requestMinor(requestMinor);
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
    out.writeCard8(mods);
    out.writeCard8(baseMods);
    out.writeCard8(latchedMods);
    out.writeCard8(lockedMods);
    out.writeCard8(group);
    out.writeInt16(baseGroup);
    out.writeInt16(latchedGroup);
    out.writeCard8(lockedGroup);
    out.writeCard8(compatState);
    out.writeCard8(grabMods);
    out.writeCard8(compatGrabMods);
    out.writeCard8(lookupMods);
    out.writeCard8(compatLoockupMods);
    out.writeCard16(ptrBtnState);
    out.writeCard16(changed);
    out.writeCard8(keycode);
    out.writeCard8(eventType);
    out.writeCard8(requestMajor);
    out.writeCard8(requestMinor);
  }

  public boolean isModsEnabled(ModMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (ModMask m : maskEnums) {
      if (!m.isEnabled(mods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isBaseModsEnabled(ModMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (ModMask m : maskEnums) {
      if (!m.isEnabled(baseMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isLatchedModsEnabled(ModMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (ModMask m : maskEnums) {
      if (!m.isEnabled(latchedMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isLockedModsEnabled(ModMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (ModMask m : maskEnums) {
      if (!m.isEnabled(lockedMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isCompatStateEnabled(ModMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (ModMask m : maskEnums) {
      if (!m.isEnabled(compatState)) {
        return false;
      }
    }
    return true;
  }

  public boolean isGrabModsEnabled(ModMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (ModMask m : maskEnums) {
      if (!m.isEnabled(grabMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isCompatGrabModsEnabled(ModMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (ModMask m : maskEnums) {
      if (!m.isEnabled(compatGrabMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isLookupModsEnabled(ModMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (ModMask m : maskEnums) {
      if (!m.isEnabled(lookupMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isCompatLoockupModsEnabled(ModMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (ModMask m : maskEnums) {
      if (!m.isEnabled(compatLoockupMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isPtrBtnStateEnabled(KeyButMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (KeyButMask m : maskEnums) {
      if (!m.isEnabled(ptrBtnState)) {
        return false;
      }
    }
    return true;
  }

  public boolean isChangedEnabled(StatePart... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (StatePart m : maskEnums) {
      if (!m.isEnabled(changed)) {
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


  public static class StateNotifyEventBuilder {
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
    private byte mods;
    @java.lang.SuppressWarnings("all")
    private byte baseMods;
    @java.lang.SuppressWarnings("all")
    private byte latchedMods;
    @java.lang.SuppressWarnings("all")
    private byte lockedMods;
    @java.lang.SuppressWarnings("all")
    private byte group;
    @java.lang.SuppressWarnings("all")
    private short baseGroup;
    @java.lang.SuppressWarnings("all")
    private short latchedGroup;
    @java.lang.SuppressWarnings("all")
    private byte lockedGroup;
    @java.lang.SuppressWarnings("all")
    private byte compatState;
    @java.lang.SuppressWarnings("all")
    private byte grabMods;
    @java.lang.SuppressWarnings("all")
    private byte compatGrabMods;
    @java.lang.SuppressWarnings("all")
    private byte lookupMods;
    @java.lang.SuppressWarnings("all")
    private byte compatLoockupMods;
    @java.lang.SuppressWarnings("all")
    private short ptrBtnState;
    @java.lang.SuppressWarnings("all")
    private short changed;
    @java.lang.SuppressWarnings("all")
    private byte keycode;
    @java.lang.SuppressWarnings("all")
    private byte eventType;
    @java.lang.SuppressWarnings("all")
    private byte requestMajor;
    @java.lang.SuppressWarnings("all")
    private byte requestMinor;

    public boolean isModsEnabled(ModMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (ModMask m : maskEnums) {
        if (!m.isEnabled(mods)) {
          return false;
        }
      }
      return true;
    }

    public StateNotifyEvent.StateNotifyEventBuilder modsEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        mods((byte) m.enableFor(mods));
      }
      return this;
    }

    public StateNotifyEvent.StateNotifyEventBuilder modsDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        mods((byte) m.disableFor(mods));
      }
      return this;
    }

    public boolean isBaseModsEnabled(ModMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (ModMask m : maskEnums) {
        if (!m.isEnabled(baseMods)) {
          return false;
        }
      }
      return true;
    }

    public StateNotifyEvent.StateNotifyEventBuilder baseModsEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        baseMods((byte) m.enableFor(baseMods));
      }
      return this;
    }

    public StateNotifyEvent.StateNotifyEventBuilder baseModsDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        baseMods((byte) m.disableFor(baseMods));
      }
      return this;
    }

    public boolean isLatchedModsEnabled(ModMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (ModMask m : maskEnums) {
        if (!m.isEnabled(latchedMods)) {
          return false;
        }
      }
      return true;
    }

    public StateNotifyEvent.StateNotifyEventBuilder latchedModsEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        latchedMods((byte) m.enableFor(latchedMods));
      }
      return this;
    }

    public StateNotifyEvent.StateNotifyEventBuilder latchedModsDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        latchedMods((byte) m.disableFor(latchedMods));
      }
      return this;
    }

    public boolean isLockedModsEnabled(ModMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (ModMask m : maskEnums) {
        if (!m.isEnabled(lockedMods)) {
          return false;
        }
      }
      return true;
    }

    public StateNotifyEvent.StateNotifyEventBuilder lockedModsEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        lockedMods((byte) m.enableFor(lockedMods));
      }
      return this;
    }

    public StateNotifyEvent.StateNotifyEventBuilder lockedModsDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        lockedMods((byte) m.disableFor(lockedMods));
      }
      return this;
    }

    public StateNotifyEvent.StateNotifyEventBuilder group(Group group) {
      this.group = (byte) group.getValue();
      return this;
    }

    public StateNotifyEvent.StateNotifyEventBuilder group(byte group) {
      this.group = group;
      return this;
    }

    public StateNotifyEvent.StateNotifyEventBuilder lockedGroup(Group lockedGroup) {
      this.lockedGroup = (byte) lockedGroup.getValue();
      return this;
    }

    public StateNotifyEvent.StateNotifyEventBuilder lockedGroup(byte lockedGroup) {
      this.lockedGroup = lockedGroup;
      return this;
    }

    public boolean isCompatStateEnabled(ModMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (ModMask m : maskEnums) {
        if (!m.isEnabled(compatState)) {
          return false;
        }
      }
      return true;
    }

    public StateNotifyEvent.StateNotifyEventBuilder compatStateEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        compatState((byte) m.enableFor(compatState));
      }
      return this;
    }

    public StateNotifyEvent.StateNotifyEventBuilder compatStateDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        compatState((byte) m.disableFor(compatState));
      }
      return this;
    }

    public boolean isGrabModsEnabled(ModMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (ModMask m : maskEnums) {
        if (!m.isEnabled(grabMods)) {
          return false;
        }
      }
      return true;
    }

    public StateNotifyEvent.StateNotifyEventBuilder grabModsEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        grabMods((byte) m.enableFor(grabMods));
      }
      return this;
    }

    public StateNotifyEvent.StateNotifyEventBuilder grabModsDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        grabMods((byte) m.disableFor(grabMods));
      }
      return this;
    }

    public boolean isCompatGrabModsEnabled(ModMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (ModMask m : maskEnums) {
        if (!m.isEnabled(compatGrabMods)) {
          return false;
        }
      }
      return true;
    }

    public StateNotifyEvent.StateNotifyEventBuilder compatGrabModsEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        compatGrabMods((byte) m.enableFor(compatGrabMods));
      }
      return this;
    }

    public StateNotifyEvent.StateNotifyEventBuilder compatGrabModsDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        compatGrabMods((byte) m.disableFor(compatGrabMods));
      }
      return this;
    }

    public boolean isLookupModsEnabled(ModMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (ModMask m : maskEnums) {
        if (!m.isEnabled(lookupMods)) {
          return false;
        }
      }
      return true;
    }

    public StateNotifyEvent.StateNotifyEventBuilder lookupModsEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        lookupMods((byte) m.enableFor(lookupMods));
      }
      return this;
    }

    public StateNotifyEvent.StateNotifyEventBuilder lookupModsDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        lookupMods((byte) m.disableFor(lookupMods));
      }
      return this;
    }

    public boolean isCompatLoockupModsEnabled(ModMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (ModMask m : maskEnums) {
        if (!m.isEnabled(compatLoockupMods)) {
          return false;
        }
      }
      return true;
    }

    public StateNotifyEvent.StateNotifyEventBuilder compatLoockupModsEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        compatLoockupMods((byte) m.enableFor(compatLoockupMods));
      }
      return this;
    }

    public StateNotifyEvent.StateNotifyEventBuilder compatLoockupModsDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        compatLoockupMods((byte) m.disableFor(compatLoockupMods));
      }
      return this;
    }

    public boolean isPtrBtnStateEnabled(KeyButMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (KeyButMask m : maskEnums) {
        if (!m.isEnabled(ptrBtnState)) {
          return false;
        }
      }
      return true;
    }

    public StateNotifyEvent.StateNotifyEventBuilder ptrBtnStateEnable(KeyButMask... maskEnums) {
      for (KeyButMask m : maskEnums) {
        ptrBtnState((short) m.enableFor(ptrBtnState));
      }
      return this;
    }

    public StateNotifyEvent.StateNotifyEventBuilder ptrBtnStateDisable(KeyButMask... maskEnums) {
      for (KeyButMask m : maskEnums) {
        ptrBtnState((short) m.disableFor(ptrBtnState));
      }
      return this;
    }

    public boolean isChangedEnabled(StatePart... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (StatePart m : maskEnums) {
        if (!m.isEnabled(changed)) {
          return false;
        }
      }
      return true;
    }

    public StateNotifyEvent.StateNotifyEventBuilder changedEnable(StatePart... maskEnums) {
      for (StatePart m : maskEnums) {
        changed((short) m.enableFor(changed));
      }
      return this;
    }

    public StateNotifyEvent.StateNotifyEventBuilder changedDisable(StatePart... maskEnums) {
      for (StatePart m : maskEnums) {
        changed((short) m.disableFor(changed));
      }
      return this;
    }

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    StateNotifyEventBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public StateNotifyEvent.StateNotifyEventBuilder firstEventOffset(final byte firstEventOffset) {
      this.firstEventOffset = firstEventOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public StateNotifyEvent.StateNotifyEventBuilder sentEvent(final boolean sentEvent) {
      this.sentEvent = sentEvent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public StateNotifyEvent.StateNotifyEventBuilder xkbType(final byte xkbType) {
      this.xkbType = xkbType;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public StateNotifyEvent.StateNotifyEventBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public StateNotifyEvent.StateNotifyEventBuilder time(final int time) {
      this.time = time;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public StateNotifyEvent.StateNotifyEventBuilder deviceID(final byte deviceID) {
      this.deviceID = deviceID;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public StateNotifyEvent.StateNotifyEventBuilder mods(final byte mods) {
      this.mods = mods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public StateNotifyEvent.StateNotifyEventBuilder baseMods(final byte baseMods) {
      this.baseMods = baseMods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public StateNotifyEvent.StateNotifyEventBuilder latchedMods(final byte latchedMods) {
      this.latchedMods = latchedMods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public StateNotifyEvent.StateNotifyEventBuilder lockedMods(final byte lockedMods) {
      this.lockedMods = lockedMods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public StateNotifyEvent.StateNotifyEventBuilder baseGroup(final short baseGroup) {
      this.baseGroup = baseGroup;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public StateNotifyEvent.StateNotifyEventBuilder latchedGroup(final short latchedGroup) {
      this.latchedGroup = latchedGroup;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public StateNotifyEvent.StateNotifyEventBuilder compatState(final byte compatState) {
      this.compatState = compatState;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public StateNotifyEvent.StateNotifyEventBuilder grabMods(final byte grabMods) {
      this.grabMods = grabMods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public StateNotifyEvent.StateNotifyEventBuilder compatGrabMods(final byte compatGrabMods) {
      this.compatGrabMods = compatGrabMods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public StateNotifyEvent.StateNotifyEventBuilder lookupMods(final byte lookupMods) {
      this.lookupMods = lookupMods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public StateNotifyEvent.StateNotifyEventBuilder compatLoockupMods(final byte compatLoockupMods) {
      this.compatLoockupMods = compatLoockupMods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public StateNotifyEvent.StateNotifyEventBuilder ptrBtnState(final short ptrBtnState) {
      this.ptrBtnState = ptrBtnState;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public StateNotifyEvent.StateNotifyEventBuilder changed(final short changed) {
      this.changed = changed;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public StateNotifyEvent.StateNotifyEventBuilder keycode(final byte keycode) {
      this.keycode = keycode;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public StateNotifyEvent.StateNotifyEventBuilder eventType(final byte eventType) {
      this.eventType = eventType;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public StateNotifyEvent.StateNotifyEventBuilder requestMajor(final byte requestMajor) {
      this.requestMajor = requestMajor;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public StateNotifyEvent.StateNotifyEventBuilder requestMinor(final byte requestMinor) {
      this.requestMinor = requestMinor;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public StateNotifyEvent build() {
      return new StateNotifyEvent(this.firstEventOffset, this.sentEvent, this.xkbType, this.sequenceNumber, this.time, this.deviceID, this.mods, this.baseMods, this.latchedMods, this.lockedMods, this.group, this.baseGroup, this.latchedGroup, this.lockedGroup, this.compatState, this.grabMods, this.compatGrabMods, this.lookupMods, this.compatLoockupMods, this.ptrBtnState, this.changed, this.keycode, this.eventType, this.requestMajor, this.requestMinor);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "StateNotifyEvent.StateNotifyEventBuilder(firstEventOffset=" + this.firstEventOffset + ", sentEvent=" + this.sentEvent + ", xkbType=" + this.xkbType + ", sequenceNumber=" + this.sequenceNumber + ", time=" + this.time + ", deviceID=" + this.deviceID + ", mods=" + this.mods + ", baseMods=" + this.baseMods + ", latchedMods=" + this.latchedMods + ", lockedMods=" + this.lockedMods + ", group=" + this.group + ", baseGroup=" + this.baseGroup + ", latchedGroup=" + this.latchedGroup + ", lockedGroup=" + this.lockedGroup + ", compatState=" + this.compatState + ", grabMods=" + this.grabMods + ", compatGrabMods=" + this.compatGrabMods + ", lookupMods=" + this.lookupMods + ", compatLoockupMods=" + this.compatLoockupMods + ", ptrBtnState=" + this.ptrBtnState + ", changed=" + this.changed + ", keycode=" + this.keycode + ", eventType=" + this.eventType + ", requestMajor=" + this.requestMajor + ", requestMinor=" + this.requestMinor + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  StateNotifyEvent(final byte firstEventOffset, final boolean sentEvent, final byte xkbType, final short sequenceNumber, final int time, final byte deviceID, final byte mods, final byte baseMods, final byte latchedMods, final byte lockedMods, final byte group, final short baseGroup, final short latchedGroup, final byte lockedGroup, final byte compatState, final byte grabMods, final byte compatGrabMods, final byte lookupMods, final byte compatLoockupMods, final short ptrBtnState, final short changed, final byte keycode, final byte eventType, final byte requestMajor, final byte requestMinor) {
    this.firstEventOffset = firstEventOffset;
    this.sentEvent = sentEvent;
    this.xkbType = xkbType;
    this.sequenceNumber = sequenceNumber;
    this.time = time;
    this.deviceID = deviceID;
    this.mods = mods;
    this.baseMods = baseMods;
    this.latchedMods = latchedMods;
    this.lockedMods = lockedMods;
    this.group = group;
    this.baseGroup = baseGroup;
    this.latchedGroup = latchedGroup;
    this.lockedGroup = lockedGroup;
    this.compatState = compatState;
    this.grabMods = grabMods;
    this.compatGrabMods = compatGrabMods;
    this.lookupMods = lookupMods;
    this.compatLoockupMods = compatLoockupMods;
    this.ptrBtnState = ptrBtnState;
    this.changed = changed;
    this.keycode = keycode;
    this.eventType = eventType;
    this.requestMajor = requestMajor;
    this.requestMinor = requestMinor;
  }

  @java.lang.SuppressWarnings("all")
  public static StateNotifyEvent.StateNotifyEventBuilder builder() {
    return new StateNotifyEvent.StateNotifyEventBuilder();
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
  public byte getMods() {
    return this.mods;
  }

  @java.lang.SuppressWarnings("all")
  public byte getBaseMods() {
    return this.baseMods;
  }

  @java.lang.SuppressWarnings("all")
  public byte getLatchedMods() {
    return this.latchedMods;
  }

  @java.lang.SuppressWarnings("all")
  public byte getLockedMods() {
    return this.lockedMods;
  }

  @java.lang.SuppressWarnings("all")
  public byte getGroup() {
    return this.group;
  }

  @java.lang.SuppressWarnings("all")
  public short getBaseGroup() {
    return this.baseGroup;
  }

  @java.lang.SuppressWarnings("all")
  public short getLatchedGroup() {
    return this.latchedGroup;
  }

  @java.lang.SuppressWarnings("all")
  public byte getLockedGroup() {
    return this.lockedGroup;
  }

  @java.lang.SuppressWarnings("all")
  public byte getCompatState() {
    return this.compatState;
  }

  @java.lang.SuppressWarnings("all")
  public byte getGrabMods() {
    return this.grabMods;
  }

  @java.lang.SuppressWarnings("all")
  public byte getCompatGrabMods() {
    return this.compatGrabMods;
  }

  @java.lang.SuppressWarnings("all")
  public byte getLookupMods() {
    return this.lookupMods;
  }

  @java.lang.SuppressWarnings("all")
  public byte getCompatLoockupMods() {
    return this.compatLoockupMods;
  }

  @java.lang.SuppressWarnings("all")
  public short getPtrBtnState() {
    return this.ptrBtnState;
  }

  @java.lang.SuppressWarnings("all")
  public short getChanged() {
    return this.changed;
  }

  @java.lang.SuppressWarnings("all")
  public byte getKeycode() {
    return this.keycode;
  }

  @java.lang.SuppressWarnings("all")
  public byte getEventType() {
    return this.eventType;
  }

  @java.lang.SuppressWarnings("all")
  public byte getRequestMajor() {
    return this.requestMajor;
  }

  @java.lang.SuppressWarnings("all")
  public byte getRequestMinor() {
    return this.requestMinor;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof StateNotifyEvent)) return false;
    final StateNotifyEvent other = (StateNotifyEvent) o;
    if (this.getFirstEventOffset() != other.getFirstEventOffset()) return false;
    if (this.isSentEvent() != other.isSentEvent()) return false;
    if (this.getXkbType() != other.getXkbType()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getTime() != other.getTime()) return false;
    if (this.getDeviceID() != other.getDeviceID()) return false;
    if (this.getMods() != other.getMods()) return false;
    if (this.getBaseMods() != other.getBaseMods()) return false;
    if (this.getLatchedMods() != other.getLatchedMods()) return false;
    if (this.getLockedMods() != other.getLockedMods()) return false;
    if (this.getGroup() != other.getGroup()) return false;
    if (this.getBaseGroup() != other.getBaseGroup()) return false;
    if (this.getLatchedGroup() != other.getLatchedGroup()) return false;
    if (this.getLockedGroup() != other.getLockedGroup()) return false;
    if (this.getCompatState() != other.getCompatState()) return false;
    if (this.getGrabMods() != other.getGrabMods()) return false;
    if (this.getCompatGrabMods() != other.getCompatGrabMods()) return false;
    if (this.getLookupMods() != other.getLookupMods()) return false;
    if (this.getCompatLoockupMods() != other.getCompatLoockupMods()) return false;
    if (this.getPtrBtnState() != other.getPtrBtnState()) return false;
    if (this.getChanged() != other.getChanged()) return false;
    if (this.getKeycode() != other.getKeycode()) return false;
    if (this.getEventType() != other.getEventType()) return false;
    if (this.getRequestMajor() != other.getRequestMajor()) return false;
    if (this.getRequestMinor() != other.getRequestMinor()) return false;
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
    result = result * PRIME + this.getMods();
    result = result * PRIME + this.getBaseMods();
    result = result * PRIME + this.getLatchedMods();
    result = result * PRIME + this.getLockedMods();
    result = result * PRIME + this.getGroup();
    result = result * PRIME + this.getBaseGroup();
    result = result * PRIME + this.getLatchedGroup();
    result = result * PRIME + this.getLockedGroup();
    result = result * PRIME + this.getCompatState();
    result = result * PRIME + this.getGrabMods();
    result = result * PRIME + this.getCompatGrabMods();
    result = result * PRIME + this.getLookupMods();
    result = result * PRIME + this.getCompatLoockupMods();
    result = result * PRIME + this.getPtrBtnState();
    result = result * PRIME + this.getChanged();
    result = result * PRIME + this.getKeycode();
    result = result * PRIME + this.getEventType();
    result = result * PRIME + this.getRequestMajor();
    result = result * PRIME + this.getRequestMinor();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "StateNotifyEvent(firstEventOffset=" + this.getFirstEventOffset() + ", sentEvent=" + this.isSentEvent() + ", xkbType=" + this.getXkbType() + ", sequenceNumber=" + this.getSequenceNumber() + ", time=" + this.getTime() + ", deviceID=" + this.getDeviceID() + ", mods=" + this.getMods() + ", baseMods=" + this.getBaseMods() + ", latchedMods=" + this.getLatchedMods() + ", lockedMods=" + this.getLockedMods() + ", group=" + this.getGroup() + ", baseGroup=" + this.getBaseGroup() + ", latchedGroup=" + this.getLatchedGroup() + ", lockedGroup=" + this.getLockedGroup() + ", compatState=" + this.getCompatState() + ", grabMods=" + this.getGrabMods() + ", compatGrabMods=" + this.getCompatGrabMods() + ", lookupMods=" + this.getLookupMods() + ", compatLoockupMods=" + this.getCompatLoockupMods() + ", ptrBtnState=" + this.getPtrBtnState() + ", changed=" + this.getChanged() + ", keycode=" + this.getKeycode() + ", eventType=" + this.getEventType() + ", requestMajor=" + this.getRequestMajor() + ", requestMinor=" + this.getRequestMinor() + ")";
  }
}
