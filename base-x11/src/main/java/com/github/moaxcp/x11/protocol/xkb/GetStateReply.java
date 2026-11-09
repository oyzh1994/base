package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import com.github.moaxcp.x11.protocol.xproto.KeyButMask;
import com.github.moaxcp.x11.protocol.xproto.ModMask;
import java.io.IOException;

public final class GetStateReply implements XReply {
  public static final String PLUGIN_NAME = "xkb";
  private final byte deviceID;
  private final short sequenceNumber;
  private final byte mods;
  private final byte baseMods;
  private final byte latchedMods;
  private final byte lockedMods;
  private final byte group;
  private final byte lockedGroup;
  private final short baseGroup;
  private final short latchedGroup;
  private final byte compatState;
  private final byte grabMods;
  private final byte compatGrabMods;
  private final byte lookupMods;
  private final byte compatLookupMods;
  private final short ptrBtnState;

  public static GetStateReply readGetStateReply(byte deviceID, short sequenceNumber, X11Input in) throws IOException {
    GetStateReply.GetStateReplyBuilder javaBuilder = GetStateReply.builder();
    int length = in.readCard32();
    byte mods = in.readCard8();
    byte baseMods = in.readCard8();
    byte latchedMods = in.readCard8();
    byte lockedMods = in.readCard8();
    byte group = in.readCard8();
    byte lockedGroup = in.readCard8();
    short baseGroup = in.readInt16();
    short latchedGroup = in.readInt16();
    byte compatState = in.readCard8();
    byte grabMods = in.readCard8();
    byte compatGrabMods = in.readCard8();
    byte lookupMods = in.readCard8();
    byte compatLookupMods = in.readCard8();
    byte[] pad17 = in.readPad(1);
    short ptrBtnState = in.readCard16();
    byte[] pad19 = in.readPad(6);
    javaBuilder.deviceID(deviceID);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.mods(mods);
    javaBuilder.baseMods(baseMods);
    javaBuilder.latchedMods(latchedMods);
    javaBuilder.lockedMods(lockedMods);
    javaBuilder.group(group);
    javaBuilder.lockedGroup(lockedGroup);
    javaBuilder.baseGroup(baseGroup);
    javaBuilder.latchedGroup(latchedGroup);
    javaBuilder.compatState(compatState);
    javaBuilder.grabMods(grabMods);
    javaBuilder.compatGrabMods(compatGrabMods);
    javaBuilder.lookupMods(lookupMods);
    javaBuilder.compatLookupMods(compatLookupMods);
    javaBuilder.ptrBtnState(ptrBtnState);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(deviceID);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard8(mods);
    out.writeCard8(baseMods);
    out.writeCard8(latchedMods);
    out.writeCard8(lockedMods);
    out.writeCard8(group);
    out.writeCard8(lockedGroup);
    out.writeInt16(baseGroup);
    out.writeInt16(latchedGroup);
    out.writeCard8(compatState);
    out.writeCard8(grabMods);
    out.writeCard8(compatGrabMods);
    out.writeCard8(lookupMods);
    out.writeCard8(compatLookupMods);
    out.writePad(1);
    out.writeCard16(ptrBtnState);
    out.writePad(6);
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

  public boolean isCompatLookupModsEnabled(ModMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (ModMask m : maskEnums) {
      if (!m.isEnabled(compatLookupMods)) {
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

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetStateReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte deviceID;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
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
    private byte lockedGroup;
    @java.lang.SuppressWarnings("all")
    private short baseGroup;
    @java.lang.SuppressWarnings("all")
    private short latchedGroup;
    @java.lang.SuppressWarnings("all")
    private byte compatState;
    @java.lang.SuppressWarnings("all")
    private byte grabMods;
    @java.lang.SuppressWarnings("all")
    private byte compatGrabMods;
    @java.lang.SuppressWarnings("all")
    private byte lookupMods;
    @java.lang.SuppressWarnings("all")
    private byte compatLookupMods;
    @java.lang.SuppressWarnings("all")
    private short ptrBtnState;

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

    public GetStateReply.GetStateReplyBuilder modsEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        mods((byte) m.enableFor(mods));
      }
      return this;
    }

    public GetStateReply.GetStateReplyBuilder modsDisable(ModMask... maskEnums) {
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

    public GetStateReply.GetStateReplyBuilder baseModsEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        baseMods((byte) m.enableFor(baseMods));
      }
      return this;
    }

    public GetStateReply.GetStateReplyBuilder baseModsDisable(ModMask... maskEnums) {
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

    public GetStateReply.GetStateReplyBuilder latchedModsEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        latchedMods((byte) m.enableFor(latchedMods));
      }
      return this;
    }

    public GetStateReply.GetStateReplyBuilder latchedModsDisable(ModMask... maskEnums) {
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

    public GetStateReply.GetStateReplyBuilder lockedModsEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        lockedMods((byte) m.enableFor(lockedMods));
      }
      return this;
    }

    public GetStateReply.GetStateReplyBuilder lockedModsDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        lockedMods((byte) m.disableFor(lockedMods));
      }
      return this;
    }

    public GetStateReply.GetStateReplyBuilder group(Group group) {
      this.group = (byte) group.getValue();
      return this;
    }

    public GetStateReply.GetStateReplyBuilder group(byte group) {
      this.group = group;
      return this;
    }

    public GetStateReply.GetStateReplyBuilder lockedGroup(Group lockedGroup) {
      this.lockedGroup = (byte) lockedGroup.getValue();
      return this;
    }

    public GetStateReply.GetStateReplyBuilder lockedGroup(byte lockedGroup) {
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

    public GetStateReply.GetStateReplyBuilder compatStateEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        compatState((byte) m.enableFor(compatState));
      }
      return this;
    }

    public GetStateReply.GetStateReplyBuilder compatStateDisable(ModMask... maskEnums) {
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

    public GetStateReply.GetStateReplyBuilder grabModsEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        grabMods((byte) m.enableFor(grabMods));
      }
      return this;
    }

    public GetStateReply.GetStateReplyBuilder grabModsDisable(ModMask... maskEnums) {
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

    public GetStateReply.GetStateReplyBuilder compatGrabModsEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        compatGrabMods((byte) m.enableFor(compatGrabMods));
      }
      return this;
    }

    public GetStateReply.GetStateReplyBuilder compatGrabModsDisable(ModMask... maskEnums) {
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

    public GetStateReply.GetStateReplyBuilder lookupModsEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        lookupMods((byte) m.enableFor(lookupMods));
      }
      return this;
    }

    public GetStateReply.GetStateReplyBuilder lookupModsDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        lookupMods((byte) m.disableFor(lookupMods));
      }
      return this;
    }

    public boolean isCompatLookupModsEnabled(ModMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (ModMask m : maskEnums) {
        if (!m.isEnabled(compatLookupMods)) {
          return false;
        }
      }
      return true;
    }

    public GetStateReply.GetStateReplyBuilder compatLookupModsEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        compatLookupMods((byte) m.enableFor(compatLookupMods));
      }
      return this;
    }

    public GetStateReply.GetStateReplyBuilder compatLookupModsDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        compatLookupMods((byte) m.disableFor(compatLookupMods));
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

    public GetStateReply.GetStateReplyBuilder ptrBtnStateEnable(KeyButMask... maskEnums) {
      for (KeyButMask m : maskEnums) {
        ptrBtnState((short) m.enableFor(ptrBtnState));
      }
      return this;
    }

    public GetStateReply.GetStateReplyBuilder ptrBtnStateDisable(KeyButMask... maskEnums) {
      for (KeyButMask m : maskEnums) {
        ptrBtnState((short) m.disableFor(ptrBtnState));
      }
      return this;
    }

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    GetStateReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetStateReply.GetStateReplyBuilder deviceID(final byte deviceID) {
      this.deviceID = deviceID;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetStateReply.GetStateReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetStateReply.GetStateReplyBuilder mods(final byte mods) {
      this.mods = mods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetStateReply.GetStateReplyBuilder baseMods(final byte baseMods) {
      this.baseMods = baseMods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetStateReply.GetStateReplyBuilder latchedMods(final byte latchedMods) {
      this.latchedMods = latchedMods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetStateReply.GetStateReplyBuilder lockedMods(final byte lockedMods) {
      this.lockedMods = lockedMods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetStateReply.GetStateReplyBuilder baseGroup(final short baseGroup) {
      this.baseGroup = baseGroup;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetStateReply.GetStateReplyBuilder latchedGroup(final short latchedGroup) {
      this.latchedGroup = latchedGroup;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetStateReply.GetStateReplyBuilder compatState(final byte compatState) {
      this.compatState = compatState;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetStateReply.GetStateReplyBuilder grabMods(final byte grabMods) {
      this.grabMods = grabMods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetStateReply.GetStateReplyBuilder compatGrabMods(final byte compatGrabMods) {
      this.compatGrabMods = compatGrabMods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetStateReply.GetStateReplyBuilder lookupMods(final byte lookupMods) {
      this.lookupMods = lookupMods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetStateReply.GetStateReplyBuilder compatLookupMods(final byte compatLookupMods) {
      this.compatLookupMods = compatLookupMods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetStateReply.GetStateReplyBuilder ptrBtnState(final short ptrBtnState) {
      this.ptrBtnState = ptrBtnState;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetStateReply build() {
      return new GetStateReply(this.deviceID, this.sequenceNumber, this.mods, this.baseMods, this.latchedMods, this.lockedMods, this.group, this.lockedGroup, this.baseGroup, this.latchedGroup, this.compatState, this.grabMods, this.compatGrabMods, this.lookupMods, this.compatLookupMods, this.ptrBtnState);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetStateReply.GetStateReplyBuilder(deviceID=" + this.deviceID + ", sequenceNumber=" + this.sequenceNumber + ", mods=" + this.mods + ", baseMods=" + this.baseMods + ", latchedMods=" + this.latchedMods + ", lockedMods=" + this.lockedMods + ", group=" + this.group + ", lockedGroup=" + this.lockedGroup + ", baseGroup=" + this.baseGroup + ", latchedGroup=" + this.latchedGroup + ", compatState=" + this.compatState + ", grabMods=" + this.grabMods + ", compatGrabMods=" + this.compatGrabMods + ", lookupMods=" + this.lookupMods + ", compatLookupMods=" + this.compatLookupMods + ", ptrBtnState=" + this.ptrBtnState + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetStateReply(final byte deviceID, final short sequenceNumber, final byte mods, final byte baseMods, final byte latchedMods, final byte lockedMods, final byte group, final byte lockedGroup, final short baseGroup, final short latchedGroup, final byte compatState, final byte grabMods, final byte compatGrabMods, final byte lookupMods, final byte compatLookupMods, final short ptrBtnState) {
    this.deviceID = deviceID;
    this.sequenceNumber = sequenceNumber;
    this.mods = mods;
    this.baseMods = baseMods;
    this.latchedMods = latchedMods;
    this.lockedMods = lockedMods;
    this.group = group;
    this.lockedGroup = lockedGroup;
    this.baseGroup = baseGroup;
    this.latchedGroup = latchedGroup;
    this.compatState = compatState;
    this.grabMods = grabMods;
    this.compatGrabMods = compatGrabMods;
    this.lookupMods = lookupMods;
    this.compatLookupMods = compatLookupMods;
    this.ptrBtnState = ptrBtnState;
  }

  @java.lang.SuppressWarnings("all")
  public static GetStateReply.GetStateReplyBuilder builder() {
    return new GetStateReply.GetStateReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getDeviceID() {
    return this.deviceID;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
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
  public byte getLockedGroup() {
    return this.lockedGroup;
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
  public byte getCompatLookupMods() {
    return this.compatLookupMods;
  }

  @java.lang.SuppressWarnings("all")
  public short getPtrBtnState() {
    return this.ptrBtnState;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetStateReply)) return false;
    final GetStateReply other = (GetStateReply) o;
    if (this.getDeviceID() != other.getDeviceID()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getMods() != other.getMods()) return false;
    if (this.getBaseMods() != other.getBaseMods()) return false;
    if (this.getLatchedMods() != other.getLatchedMods()) return false;
    if (this.getLockedMods() != other.getLockedMods()) return false;
    if (this.getGroup() != other.getGroup()) return false;
    if (this.getLockedGroup() != other.getLockedGroup()) return false;
    if (this.getBaseGroup() != other.getBaseGroup()) return false;
    if (this.getLatchedGroup() != other.getLatchedGroup()) return false;
    if (this.getCompatState() != other.getCompatState()) return false;
    if (this.getGrabMods() != other.getGrabMods()) return false;
    if (this.getCompatGrabMods() != other.getCompatGrabMods()) return false;
    if (this.getLookupMods() != other.getLookupMods()) return false;
    if (this.getCompatLookupMods() != other.getCompatLookupMods()) return false;
    if (this.getPtrBtnState() != other.getPtrBtnState()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDeviceID();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getMods();
    result = result * PRIME + this.getBaseMods();
    result = result * PRIME + this.getLatchedMods();
    result = result * PRIME + this.getLockedMods();
    result = result * PRIME + this.getGroup();
    result = result * PRIME + this.getLockedGroup();
    result = result * PRIME + this.getBaseGroup();
    result = result * PRIME + this.getLatchedGroup();
    result = result * PRIME + this.getCompatState();
    result = result * PRIME + this.getGrabMods();
    result = result * PRIME + this.getCompatGrabMods();
    result = result * PRIME + this.getLookupMods();
    result = result * PRIME + this.getCompatLookupMods();
    result = result * PRIME + this.getPtrBtnState();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetStateReply(deviceID=" + this.getDeviceID() + ", sequenceNumber=" + this.getSequenceNumber() + ", mods=" + this.getMods() + ", baseMods=" + this.getBaseMods() + ", latchedMods=" + this.getLatchedMods() + ", lockedMods=" + this.getLockedMods() + ", group=" + this.getGroup() + ", lockedGroup=" + this.getLockedGroup() + ", baseGroup=" + this.getBaseGroup() + ", latchedGroup=" + this.getLatchedGroup() + ", compatState=" + this.getCompatState() + ", grabMods=" + this.getGrabMods() + ", compatGrabMods=" + this.getCompatGrabMods() + ", lookupMods=" + this.getLookupMods() + ", compatLookupMods=" + this.getCompatLookupMods() + ", ptrBtnState=" + this.getPtrBtnState() + ")";
  }
}
