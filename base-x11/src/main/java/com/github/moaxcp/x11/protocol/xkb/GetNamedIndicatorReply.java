package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import com.github.moaxcp.x11.protocol.xproto.ModMask;
import java.io.IOException;

public final class GetNamedIndicatorReply implements XReply {
  public static final String PLUGIN_NAME = "xkb";
  private final byte deviceID;
  private final short sequenceNumber;
  private final int indicator;
  private final boolean found;
  private final boolean on;
  private final boolean realIndicator;
  private final byte ndx;
  private final byte mapFlags;
  private final byte mapWhichgroups;
  private final byte mapGroups;
  private final byte mapWhichmods;
  private final byte mapMods;
  private final byte mapRealmods;
  private final short mapVmod;
  private final int mapCtrls;
  private final boolean supported;

  public static GetNamedIndicatorReply readGetNamedIndicatorReply(byte deviceID, short sequenceNumber, X11Input in) throws IOException {
    GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder javaBuilder = GetNamedIndicatorReply.builder();
    int length = in.readCard32();
    int indicator = in.readCard32();
    boolean found = in.readBool();
    boolean on = in.readBool();
    boolean realIndicator = in.readBool();
    byte ndx = in.readCard8();
    byte mapFlags = in.readCard8();
    byte mapWhichgroups = in.readCard8();
    byte mapGroups = in.readCard8();
    byte mapWhichmods = in.readCard8();
    byte mapMods = in.readCard8();
    byte mapRealmods = in.readCard8();
    short mapVmod = in.readCard16();
    int mapCtrls = in.readCard32();
    boolean supported = in.readBool();
    byte[] pad18 = in.readPad(3);
    javaBuilder.deviceID(deviceID);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.indicator(indicator);
    javaBuilder.found(found);
    javaBuilder.on(on);
    javaBuilder.realIndicator(realIndicator);
    javaBuilder.ndx(ndx);
    javaBuilder.mapFlags(mapFlags);
    javaBuilder.mapWhichgroups(mapWhichgroups);
    javaBuilder.mapGroups(mapGroups);
    javaBuilder.mapWhichmods(mapWhichmods);
    javaBuilder.mapMods(mapMods);
    javaBuilder.mapRealmods(mapRealmods);
    javaBuilder.mapVmod(mapVmod);
    javaBuilder.mapCtrls(mapCtrls);
    javaBuilder.supported(supported);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(deviceID);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(indicator);
    out.writeBool(found);
    out.writeBool(on);
    out.writeBool(realIndicator);
    out.writeCard8(ndx);
    out.writeCard8(mapFlags);
    out.writeCard8(mapWhichgroups);
    out.writeCard8(mapGroups);
    out.writeCard8(mapWhichmods);
    out.writeCard8(mapMods);
    out.writeCard8(mapRealmods);
    out.writeCard16(mapVmod);
    out.writeCard32(mapCtrls);
    out.writeBool(supported);
    out.writePad(3);
  }

  public boolean isMapFlagsEnabled(IMFlag... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (IMFlag m : maskEnums) {
      if (!m.isEnabled(mapFlags)) {
        return false;
      }
    }
    return true;
  }

  public boolean isMapWhichgroupsEnabled(IMGroupsWhich... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (IMGroupsWhich m : maskEnums) {
      if (!m.isEnabled(mapWhichgroups)) {
        return false;
      }
    }
    return true;
  }

  public boolean isMapGroupsEnabled(SetOfGroups... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (SetOfGroups m : maskEnums) {
      if (!m.isEnabled(mapGroups)) {
        return false;
      }
    }
    return true;
  }

  public boolean isMapWhichmodsEnabled(IMModsWhich... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (IMModsWhich m : maskEnums) {
      if (!m.isEnabled(mapWhichmods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isMapModsEnabled(ModMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (ModMask m : maskEnums) {
      if (!m.isEnabled(mapMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isMapRealmodsEnabled(ModMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (ModMask m : maskEnums) {
      if (!m.isEnabled(mapRealmods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isMapVmodEnabled(VMod... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (VMod m : maskEnums) {
      if (!m.isEnabled(mapVmod)) {
        return false;
      }
    }
    return true;
  }

  public boolean isMapCtrlsEnabled(BoolCtrl... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (BoolCtrl m : maskEnums) {
      if (!m.isEnabled(mapCtrls)) {
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


  public static class GetNamedIndicatorReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte deviceID;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int indicator;
    @java.lang.SuppressWarnings("all")
    private boolean found;
    @java.lang.SuppressWarnings("all")
    private boolean on;
    @java.lang.SuppressWarnings("all")
    private boolean realIndicator;
    @java.lang.SuppressWarnings("all")
    private byte ndx;
    @java.lang.SuppressWarnings("all")
    private byte mapFlags;
    @java.lang.SuppressWarnings("all")
    private byte mapWhichgroups;
    @java.lang.SuppressWarnings("all")
    private byte mapGroups;
    @java.lang.SuppressWarnings("all")
    private byte mapWhichmods;
    @java.lang.SuppressWarnings("all")
    private byte mapMods;
    @java.lang.SuppressWarnings("all")
    private byte mapRealmods;
    @java.lang.SuppressWarnings("all")
    private short mapVmod;
    @java.lang.SuppressWarnings("all")
    private int mapCtrls;
    @java.lang.SuppressWarnings("all")
    private boolean supported;

    public boolean isMapFlagsEnabled(IMFlag... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (IMFlag m : maskEnums) {
        if (!m.isEnabled(mapFlags)) {
          return false;
        }
      }
      return true;
    }

    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder mapFlagsEnable(IMFlag... maskEnums) {
      for (IMFlag m : maskEnums) {
        mapFlags((byte) m.enableFor(mapFlags));
      }
      return this;
    }

    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder mapFlagsDisable(IMFlag... maskEnums) {
      for (IMFlag m : maskEnums) {
        mapFlags((byte) m.disableFor(mapFlags));
      }
      return this;
    }

    public boolean isMapWhichgroupsEnabled(IMGroupsWhich... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (IMGroupsWhich m : maskEnums) {
        if (!m.isEnabled(mapWhichgroups)) {
          return false;
        }
      }
      return true;
    }

    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder mapWhichgroupsEnable(IMGroupsWhich... maskEnums) {
      for (IMGroupsWhich m : maskEnums) {
        mapWhichgroups((byte) m.enableFor(mapWhichgroups));
      }
      return this;
    }

    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder mapWhichgroupsDisable(IMGroupsWhich... maskEnums) {
      for (IMGroupsWhich m : maskEnums) {
        mapWhichgroups((byte) m.disableFor(mapWhichgroups));
      }
      return this;
    }

    public boolean isMapGroupsEnabled(SetOfGroups... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (SetOfGroups m : maskEnums) {
        if (!m.isEnabled(mapGroups)) {
          return false;
        }
      }
      return true;
    }

    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder mapGroupsEnable(SetOfGroups... maskEnums) {
      for (SetOfGroups m : maskEnums) {
        mapGroups((byte) m.enableFor(mapGroups));
      }
      return this;
    }

    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder mapGroupsDisable(SetOfGroups... maskEnums) {
      for (SetOfGroups m : maskEnums) {
        mapGroups((byte) m.disableFor(mapGroups));
      }
      return this;
    }

    public boolean isMapWhichmodsEnabled(IMModsWhich... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (IMModsWhich m : maskEnums) {
        if (!m.isEnabled(mapWhichmods)) {
          return false;
        }
      }
      return true;
    }

    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder mapWhichmodsEnable(IMModsWhich... maskEnums) {
      for (IMModsWhich m : maskEnums) {
        mapWhichmods((byte) m.enableFor(mapWhichmods));
      }
      return this;
    }

    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder mapWhichmodsDisable(IMModsWhich... maskEnums) {
      for (IMModsWhich m : maskEnums) {
        mapWhichmods((byte) m.disableFor(mapWhichmods));
      }
      return this;
    }

    public boolean isMapModsEnabled(ModMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (ModMask m : maskEnums) {
        if (!m.isEnabled(mapMods)) {
          return false;
        }
      }
      return true;
    }

    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder mapModsEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        mapMods((byte) m.enableFor(mapMods));
      }
      return this;
    }

    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder mapModsDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        mapMods((byte) m.disableFor(mapMods));
      }
      return this;
    }

    public boolean isMapRealmodsEnabled(ModMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (ModMask m : maskEnums) {
        if (!m.isEnabled(mapRealmods)) {
          return false;
        }
      }
      return true;
    }

    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder mapRealmodsEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        mapRealmods((byte) m.enableFor(mapRealmods));
      }
      return this;
    }

    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder mapRealmodsDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        mapRealmods((byte) m.disableFor(mapRealmods));
      }
      return this;
    }

    public boolean isMapVmodEnabled(VMod... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (VMod m : maskEnums) {
        if (!m.isEnabled(mapVmod)) {
          return false;
        }
      }
      return true;
    }

    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder mapVmodEnable(VMod... maskEnums) {
      for (VMod m : maskEnums) {
        mapVmod((short) m.enableFor(mapVmod));
      }
      return this;
    }

    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder mapVmodDisable(VMod... maskEnums) {
      for (VMod m : maskEnums) {
        mapVmod((short) m.disableFor(mapVmod));
      }
      return this;
    }

    public boolean isMapCtrlsEnabled(BoolCtrl... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (BoolCtrl m : maskEnums) {
        if (!m.isEnabled(mapCtrls)) {
          return false;
        }
      }
      return true;
    }

    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder mapCtrlsEnable(BoolCtrl... maskEnums) {
      for (BoolCtrl m : maskEnums) {
        mapCtrls((int) m.enableFor(mapCtrls));
      }
      return this;
    }

    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder mapCtrlsDisable(BoolCtrl... maskEnums) {
      for (BoolCtrl m : maskEnums) {
        mapCtrls((int) m.disableFor(mapCtrls));
      }
      return this;
    }

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    GetNamedIndicatorReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder deviceID(final byte deviceID) {
      this.deviceID = deviceID;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder indicator(final int indicator) {
      this.indicator = indicator;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder found(final boolean found) {
      this.found = found;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder on(final boolean on) {
      this.on = on;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder realIndicator(final boolean realIndicator) {
      this.realIndicator = realIndicator;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder ndx(final byte ndx) {
      this.ndx = ndx;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder mapFlags(final byte mapFlags) {
      this.mapFlags = mapFlags;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder mapWhichgroups(final byte mapWhichgroups) {
      this.mapWhichgroups = mapWhichgroups;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder mapGroups(final byte mapGroups) {
      this.mapGroups = mapGroups;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder mapWhichmods(final byte mapWhichmods) {
      this.mapWhichmods = mapWhichmods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder mapMods(final byte mapMods) {
      this.mapMods = mapMods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder mapRealmods(final byte mapRealmods) {
      this.mapRealmods = mapRealmods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder mapVmod(final short mapVmod) {
      this.mapVmod = mapVmod;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder mapCtrls(final int mapCtrls) {
      this.mapCtrls = mapCtrls;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder supported(final boolean supported) {
      this.supported = supported;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetNamedIndicatorReply build() {
      return new GetNamedIndicatorReply(this.deviceID, this.sequenceNumber, this.indicator, this.found, this.on, this.realIndicator, this.ndx, this.mapFlags, this.mapWhichgroups, this.mapGroups, this.mapWhichmods, this.mapMods, this.mapRealmods, this.mapVmod, this.mapCtrls, this.supported);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder(deviceID=" + this.deviceID + ", sequenceNumber=" + this.sequenceNumber + ", indicator=" + this.indicator + ", found=" + this.found + ", on=" + this.on + ", realIndicator=" + this.realIndicator + ", ndx=" + this.ndx + ", mapFlags=" + this.mapFlags + ", mapWhichgroups=" + this.mapWhichgroups + ", mapGroups=" + this.mapGroups + ", mapWhichmods=" + this.mapWhichmods + ", mapMods=" + this.mapMods + ", mapRealmods=" + this.mapRealmods + ", mapVmod=" + this.mapVmod + ", mapCtrls=" + this.mapCtrls + ", supported=" + this.supported + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetNamedIndicatorReply(final byte deviceID, final short sequenceNumber, final int indicator, final boolean found, final boolean on, final boolean realIndicator, final byte ndx, final byte mapFlags, final byte mapWhichgroups, final byte mapGroups, final byte mapWhichmods, final byte mapMods, final byte mapRealmods, final short mapVmod, final int mapCtrls, final boolean supported) {
    this.deviceID = deviceID;
    this.sequenceNumber = sequenceNumber;
    this.indicator = indicator;
    this.found = found;
    this.on = on;
    this.realIndicator = realIndicator;
    this.ndx = ndx;
    this.mapFlags = mapFlags;
    this.mapWhichgroups = mapWhichgroups;
    this.mapGroups = mapGroups;
    this.mapWhichmods = mapWhichmods;
    this.mapMods = mapMods;
    this.mapRealmods = mapRealmods;
    this.mapVmod = mapVmod;
    this.mapCtrls = mapCtrls;
    this.supported = supported;
  }

  @java.lang.SuppressWarnings("all")
  public static GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder builder() {
    return new GetNamedIndicatorReply.GetNamedIndicatorReplyBuilder();
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
  public int getIndicator() {
    return this.indicator;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isFound() {
    return this.found;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isOn() {
    return this.on;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isRealIndicator() {
    return this.realIndicator;
  }

  @java.lang.SuppressWarnings("all")
  public byte getNdx() {
    return this.ndx;
  }

  @java.lang.SuppressWarnings("all")
  public byte getMapFlags() {
    return this.mapFlags;
  }

  @java.lang.SuppressWarnings("all")
  public byte getMapWhichgroups() {
    return this.mapWhichgroups;
  }

  @java.lang.SuppressWarnings("all")
  public byte getMapGroups() {
    return this.mapGroups;
  }

  @java.lang.SuppressWarnings("all")
  public byte getMapWhichmods() {
    return this.mapWhichmods;
  }

  @java.lang.SuppressWarnings("all")
  public byte getMapMods() {
    return this.mapMods;
  }

  @java.lang.SuppressWarnings("all")
  public byte getMapRealmods() {
    return this.mapRealmods;
  }

  @java.lang.SuppressWarnings("all")
  public short getMapVmod() {
    return this.mapVmod;
  }

  @java.lang.SuppressWarnings("all")
  public int getMapCtrls() {
    return this.mapCtrls;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isSupported() {
    return this.supported;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetNamedIndicatorReply)) return false;
    final GetNamedIndicatorReply other = (GetNamedIndicatorReply) o;
    if (this.getDeviceID() != other.getDeviceID()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getIndicator() != other.getIndicator()) return false;
    if (this.isFound() != other.isFound()) return false;
    if (this.isOn() != other.isOn()) return false;
    if (this.isRealIndicator() != other.isRealIndicator()) return false;
    if (this.getNdx() != other.getNdx()) return false;
    if (this.getMapFlags() != other.getMapFlags()) return false;
    if (this.getMapWhichgroups() != other.getMapWhichgroups()) return false;
    if (this.getMapGroups() != other.getMapGroups()) return false;
    if (this.getMapWhichmods() != other.getMapWhichmods()) return false;
    if (this.getMapMods() != other.getMapMods()) return false;
    if (this.getMapRealmods() != other.getMapRealmods()) return false;
    if (this.getMapVmod() != other.getMapVmod()) return false;
    if (this.getMapCtrls() != other.getMapCtrls()) return false;
    if (this.isSupported() != other.isSupported()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDeviceID();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getIndicator();
    result = result * PRIME + (this.isFound() ? 79 : 97);
    result = result * PRIME + (this.isOn() ? 79 : 97);
    result = result * PRIME + (this.isRealIndicator() ? 79 : 97);
    result = result * PRIME + this.getNdx();
    result = result * PRIME + this.getMapFlags();
    result = result * PRIME + this.getMapWhichgroups();
    result = result * PRIME + this.getMapGroups();
    result = result * PRIME + this.getMapWhichmods();
    result = result * PRIME + this.getMapMods();
    result = result * PRIME + this.getMapRealmods();
    result = result * PRIME + this.getMapVmod();
    result = result * PRIME + this.getMapCtrls();
    result = result * PRIME + (this.isSupported() ? 79 : 97);
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetNamedIndicatorReply(deviceID=" + this.getDeviceID() + ", sequenceNumber=" + this.getSequenceNumber() + ", indicator=" + this.getIndicator() + ", found=" + this.isFound() + ", on=" + this.isOn() + ", realIndicator=" + this.isRealIndicator() + ", ndx=" + this.getNdx() + ", mapFlags=" + this.getMapFlags() + ", mapWhichgroups=" + this.getMapWhichgroups() + ", mapGroups=" + this.getMapGroups() + ", mapWhichmods=" + this.getMapWhichmods() + ", mapMods=" + this.getMapMods() + ", mapRealmods=" + this.getMapRealmods() + ", mapVmod=" + this.getMapVmod() + ", mapCtrls=" + this.getMapCtrls() + ", supported=" + this.isSupported() + ")";
  }
}
