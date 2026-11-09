package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.xproto.ModMask;
import java.io.IOException;

public final class SetNamedIndicator implements OneWayRequest {
  public static final String PLUGIN_NAME = "xkb";
  public static final byte OPCODE = 16;
  private final short deviceSpec;
  private final short ledClass;
  private final short ledID;
  private final int indicator;
  private final boolean setState;
  private final boolean on;
  private final boolean setMap;
  private final boolean createMap;
  private final byte mapFlags;
  private final byte mapWhichgroups;
  private final byte mapGroups;
  private final byte mapWhichmods;
  private final byte mapRealmods;
  private final short mapVmods;
  private final int mapCtrls;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetNamedIndicator readSetNamedIndicator(X11Input in) throws IOException {
    SetNamedIndicator.SetNamedIndicatorBuilder javaBuilder = SetNamedIndicator.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short deviceSpec = in.readCard16();
    short ledClass = in.readCard16();
    short ledID = in.readCard16();
    byte[] pad6 = in.readPad(2);
    int indicator = in.readCard32();
    boolean setState = in.readBool();
    boolean on = in.readBool();
    boolean setMap = in.readBool();
    boolean createMap = in.readBool();
    byte[] pad12 = in.readPad(1);
    byte mapFlags = in.readCard8();
    byte mapWhichgroups = in.readCard8();
    byte mapGroups = in.readCard8();
    byte mapWhichmods = in.readCard8();
    byte mapRealmods = in.readCard8();
    short mapVmods = in.readCard16();
    int mapCtrls = in.readCard32();
    javaBuilder.deviceSpec(deviceSpec);
    javaBuilder.ledClass(ledClass);
    javaBuilder.ledID(ledID);
    javaBuilder.indicator(indicator);
    javaBuilder.setState(setState);
    javaBuilder.on(on);
    javaBuilder.setMap(setMap);
    javaBuilder.createMap(createMap);
    javaBuilder.mapFlags(mapFlags);
    javaBuilder.mapWhichgroups(mapWhichgroups);
    javaBuilder.mapGroups(mapGroups);
    javaBuilder.mapWhichmods(mapWhichmods);
    javaBuilder.mapRealmods(mapRealmods);
    javaBuilder.mapVmods(mapVmods);
    javaBuilder.mapCtrls(mapCtrls);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(deviceSpec);
    out.writeCard16(ledClass);
    out.writeCard16(ledID);
    out.writePad(2);
    out.writeCard32(indicator);
    out.writeBool(setState);
    out.writeBool(on);
    out.writeBool(setMap);
    out.writeBool(createMap);
    out.writePad(1);
    out.writeCard8(mapFlags);
    out.writeCard8(mapWhichgroups);
    out.writeCard8(mapGroups);
    out.writeCard8(mapWhichmods);
    out.writeCard8(mapRealmods);
    out.writeCard16(mapVmods);
    out.writeCard32(mapCtrls);
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

  public boolean isMapVmodsEnabled(VMod... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (VMod m : maskEnums) {
      if (!m.isEnabled(mapVmods)) {
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


  public static class SetNamedIndicatorBuilder {
    @java.lang.SuppressWarnings("all")
    private short deviceSpec;
    @java.lang.SuppressWarnings("all")
    private short ledClass;
    @java.lang.SuppressWarnings("all")
    private short ledID;
    @java.lang.SuppressWarnings("all")
    private int indicator;
    @java.lang.SuppressWarnings("all")
    private boolean setState;
    @java.lang.SuppressWarnings("all")
    private boolean on;
    @java.lang.SuppressWarnings("all")
    private boolean setMap;
    @java.lang.SuppressWarnings("all")
    private boolean createMap;
    @java.lang.SuppressWarnings("all")
    private byte mapFlags;
    @java.lang.SuppressWarnings("all")
    private byte mapWhichgroups;
    @java.lang.SuppressWarnings("all")
    private byte mapGroups;
    @java.lang.SuppressWarnings("all")
    private byte mapWhichmods;
    @java.lang.SuppressWarnings("all")
    private byte mapRealmods;
    @java.lang.SuppressWarnings("all")
    private short mapVmods;
    @java.lang.SuppressWarnings("all")
    private int mapCtrls;

    public SetNamedIndicator.SetNamedIndicatorBuilder ledClass(LedClass ledClass) {
      this.ledClass = (short) ledClass.getValue();
      return this;
    }

    public SetNamedIndicator.SetNamedIndicatorBuilder ledClass(short ledClass) {
      this.ledClass = ledClass;
      return this;
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

    public SetNamedIndicator.SetNamedIndicatorBuilder mapFlagsEnable(IMFlag... maskEnums) {
      for (IMFlag m : maskEnums) {
        mapFlags((byte) m.enableFor(mapFlags));
      }
      return this;
    }

    public SetNamedIndicator.SetNamedIndicatorBuilder mapFlagsDisable(IMFlag... maskEnums) {
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

    public SetNamedIndicator.SetNamedIndicatorBuilder mapWhichgroupsEnable(IMGroupsWhich... maskEnums) {
      for (IMGroupsWhich m : maskEnums) {
        mapWhichgroups((byte) m.enableFor(mapWhichgroups));
      }
      return this;
    }

    public SetNamedIndicator.SetNamedIndicatorBuilder mapWhichgroupsDisable(IMGroupsWhich... maskEnums) {
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

    public SetNamedIndicator.SetNamedIndicatorBuilder mapGroupsEnable(SetOfGroups... maskEnums) {
      for (SetOfGroups m : maskEnums) {
        mapGroups((byte) m.enableFor(mapGroups));
      }
      return this;
    }

    public SetNamedIndicator.SetNamedIndicatorBuilder mapGroupsDisable(SetOfGroups... maskEnums) {
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

    public SetNamedIndicator.SetNamedIndicatorBuilder mapWhichmodsEnable(IMModsWhich... maskEnums) {
      for (IMModsWhich m : maskEnums) {
        mapWhichmods((byte) m.enableFor(mapWhichmods));
      }
      return this;
    }

    public SetNamedIndicator.SetNamedIndicatorBuilder mapWhichmodsDisable(IMModsWhich... maskEnums) {
      for (IMModsWhich m : maskEnums) {
        mapWhichmods((byte) m.disableFor(mapWhichmods));
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

    public SetNamedIndicator.SetNamedIndicatorBuilder mapRealmodsEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        mapRealmods((byte) m.enableFor(mapRealmods));
      }
      return this;
    }

    public SetNamedIndicator.SetNamedIndicatorBuilder mapRealmodsDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        mapRealmods((byte) m.disableFor(mapRealmods));
      }
      return this;
    }

    public boolean isMapVmodsEnabled(VMod... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (VMod m : maskEnums) {
        if (!m.isEnabled(mapVmods)) {
          return false;
        }
      }
      return true;
    }

    public SetNamedIndicator.SetNamedIndicatorBuilder mapVmodsEnable(VMod... maskEnums) {
      for (VMod m : maskEnums) {
        mapVmods((short) m.enableFor(mapVmods));
      }
      return this;
    }

    public SetNamedIndicator.SetNamedIndicatorBuilder mapVmodsDisable(VMod... maskEnums) {
      for (VMod m : maskEnums) {
        mapVmods((short) m.disableFor(mapVmods));
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

    public SetNamedIndicator.SetNamedIndicatorBuilder mapCtrlsEnable(BoolCtrl... maskEnums) {
      for (BoolCtrl m : maskEnums) {
        mapCtrls((int) m.enableFor(mapCtrls));
      }
      return this;
    }

    public SetNamedIndicator.SetNamedIndicatorBuilder mapCtrlsDisable(BoolCtrl... maskEnums) {
      for (BoolCtrl m : maskEnums) {
        mapCtrls((int) m.disableFor(mapCtrls));
      }
      return this;
    }

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    SetNamedIndicatorBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetNamedIndicator.SetNamedIndicatorBuilder deviceSpec(final short deviceSpec) {
      this.deviceSpec = deviceSpec;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetNamedIndicator.SetNamedIndicatorBuilder ledID(final short ledID) {
      this.ledID = ledID;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetNamedIndicator.SetNamedIndicatorBuilder indicator(final int indicator) {
      this.indicator = indicator;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetNamedIndicator.SetNamedIndicatorBuilder setState(final boolean setState) {
      this.setState = setState;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetNamedIndicator.SetNamedIndicatorBuilder on(final boolean on) {
      this.on = on;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetNamedIndicator.SetNamedIndicatorBuilder setMap(final boolean setMap) {
      this.setMap = setMap;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetNamedIndicator.SetNamedIndicatorBuilder createMap(final boolean createMap) {
      this.createMap = createMap;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetNamedIndicator.SetNamedIndicatorBuilder mapFlags(final byte mapFlags) {
      this.mapFlags = mapFlags;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetNamedIndicator.SetNamedIndicatorBuilder mapWhichgroups(final byte mapWhichgroups) {
      this.mapWhichgroups = mapWhichgroups;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetNamedIndicator.SetNamedIndicatorBuilder mapGroups(final byte mapGroups) {
      this.mapGroups = mapGroups;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetNamedIndicator.SetNamedIndicatorBuilder mapWhichmods(final byte mapWhichmods) {
      this.mapWhichmods = mapWhichmods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetNamedIndicator.SetNamedIndicatorBuilder mapRealmods(final byte mapRealmods) {
      this.mapRealmods = mapRealmods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetNamedIndicator.SetNamedIndicatorBuilder mapVmods(final short mapVmods) {
      this.mapVmods = mapVmods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetNamedIndicator.SetNamedIndicatorBuilder mapCtrls(final int mapCtrls) {
      this.mapCtrls = mapCtrls;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetNamedIndicator build() {
      return new SetNamedIndicator(this.deviceSpec, this.ledClass, this.ledID, this.indicator, this.setState, this.on, this.setMap, this.createMap, this.mapFlags, this.mapWhichgroups, this.mapGroups, this.mapWhichmods, this.mapRealmods, this.mapVmods, this.mapCtrls);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetNamedIndicator.SetNamedIndicatorBuilder(deviceSpec=" + this.deviceSpec + ", ledClass=" + this.ledClass + ", ledID=" + this.ledID + ", indicator=" + this.indicator + ", setState=" + this.setState + ", on=" + this.on + ", setMap=" + this.setMap + ", createMap=" + this.createMap + ", mapFlags=" + this.mapFlags + ", mapWhichgroups=" + this.mapWhichgroups + ", mapGroups=" + this.mapGroups + ", mapWhichmods=" + this.mapWhichmods + ", mapRealmods=" + this.mapRealmods + ", mapVmods=" + this.mapVmods + ", mapCtrls=" + this.mapCtrls + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetNamedIndicator(final short deviceSpec, final short ledClass, final short ledID, final int indicator, final boolean setState, final boolean on, final boolean setMap, final boolean createMap, final byte mapFlags, final byte mapWhichgroups, final byte mapGroups, final byte mapWhichmods, final byte mapRealmods, final short mapVmods, final int mapCtrls) {
    this.deviceSpec = deviceSpec;
    this.ledClass = ledClass;
    this.ledID = ledID;
    this.indicator = indicator;
    this.setState = setState;
    this.on = on;
    this.setMap = setMap;
    this.createMap = createMap;
    this.mapFlags = mapFlags;
    this.mapWhichgroups = mapWhichgroups;
    this.mapGroups = mapGroups;
    this.mapWhichmods = mapWhichmods;
    this.mapRealmods = mapRealmods;
    this.mapVmods = mapVmods;
    this.mapCtrls = mapCtrls;
  }

  @java.lang.SuppressWarnings("all")
  public static SetNamedIndicator.SetNamedIndicatorBuilder builder() {
    return new SetNamedIndicator.SetNamedIndicatorBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getDeviceSpec() {
    return this.deviceSpec;
  }

  @java.lang.SuppressWarnings("all")
  public short getLedClass() {
    return this.ledClass;
  }

  @java.lang.SuppressWarnings("all")
  public short getLedID() {
    return this.ledID;
  }

  @java.lang.SuppressWarnings("all")
  public int getIndicator() {
    return this.indicator;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isSetState() {
    return this.setState;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isOn() {
    return this.on;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isSetMap() {
    return this.setMap;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isCreateMap() {
    return this.createMap;
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
  public byte getMapRealmods() {
    return this.mapRealmods;
  }

  @java.lang.SuppressWarnings("all")
  public short getMapVmods() {
    return this.mapVmods;
  }

  @java.lang.SuppressWarnings("all")
  public int getMapCtrls() {
    return this.mapCtrls;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetNamedIndicator)) return false;
    final SetNamedIndicator other = (SetNamedIndicator) o;
    if (this.getDeviceSpec() != other.getDeviceSpec()) return false;
    if (this.getLedClass() != other.getLedClass()) return false;
    if (this.getLedID() != other.getLedID()) return false;
    if (this.getIndicator() != other.getIndicator()) return false;
    if (this.isSetState() != other.isSetState()) return false;
    if (this.isOn() != other.isOn()) return false;
    if (this.isSetMap() != other.isSetMap()) return false;
    if (this.isCreateMap() != other.isCreateMap()) return false;
    if (this.getMapFlags() != other.getMapFlags()) return false;
    if (this.getMapWhichgroups() != other.getMapWhichgroups()) return false;
    if (this.getMapGroups() != other.getMapGroups()) return false;
    if (this.getMapWhichmods() != other.getMapWhichmods()) return false;
    if (this.getMapRealmods() != other.getMapRealmods()) return false;
    if (this.getMapVmods() != other.getMapVmods()) return false;
    if (this.getMapCtrls() != other.getMapCtrls()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDeviceSpec();
    result = result * PRIME + this.getLedClass();
    result = result * PRIME + this.getLedID();
    result = result * PRIME + this.getIndicator();
    result = result * PRIME + (this.isSetState() ? 79 : 97);
    result = result * PRIME + (this.isOn() ? 79 : 97);
    result = result * PRIME + (this.isSetMap() ? 79 : 97);
    result = result * PRIME + (this.isCreateMap() ? 79 : 97);
    result = result * PRIME + this.getMapFlags();
    result = result * PRIME + this.getMapWhichgroups();
    result = result * PRIME + this.getMapGroups();
    result = result * PRIME + this.getMapWhichmods();
    result = result * PRIME + this.getMapRealmods();
    result = result * PRIME + this.getMapVmods();
    result = result * PRIME + this.getMapCtrls();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetNamedIndicator(deviceSpec=" + this.getDeviceSpec() + ", ledClass=" + this.getLedClass() + ", ledID=" + this.getLedID() + ", indicator=" + this.getIndicator() + ", setState=" + this.isSetState() + ", on=" + this.isOn() + ", setMap=" + this.isSetMap() + ", createMap=" + this.isCreateMap() + ", mapFlags=" + this.getMapFlags() + ", mapWhichgroups=" + this.getMapWhichgroups() + ", mapGroups=" + this.getMapGroups() + ", mapWhichmods=" + this.getMapWhichmods() + ", mapRealmods=" + this.getMapRealmods() + ", mapVmods=" + this.getMapVmods() + ", mapCtrls=" + this.getMapCtrls() + ")";
  }
}
