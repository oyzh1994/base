package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import com.github.moaxcp.x11.protocol.xproto.ModMask;
import java.io.IOException;

public final class IndicatorMap implements XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final byte flags;
  private final byte whichGroups;
  private final byte groups;
  private final byte whichMods;
  private final byte mods;
  private final byte realMods;
  private final short vmods;
  private final int ctrls;

  public static IndicatorMap readIndicatorMap(X11Input in) throws IOException {
    IndicatorMap.IndicatorMapBuilder javaBuilder = IndicatorMap.builder();
    byte flags = in.readCard8();
    byte whichGroups = in.readCard8();
    byte groups = in.readCard8();
    byte whichMods = in.readCard8();
    byte mods = in.readCard8();
    byte realMods = in.readCard8();
    short vmods = in.readCard16();
    int ctrls = in.readCard32();
    javaBuilder.flags(flags);
    javaBuilder.whichGroups(whichGroups);
    javaBuilder.groups(groups);
    javaBuilder.whichMods(whichMods);
    javaBuilder.mods(mods);
    javaBuilder.realMods(realMods);
    javaBuilder.vmods(vmods);
    javaBuilder.ctrls(ctrls);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(flags);
    out.writeCard8(whichGroups);
    out.writeCard8(groups);
    out.writeCard8(whichMods);
    out.writeCard8(mods);
    out.writeCard8(realMods);
    out.writeCard16(vmods);
    out.writeCard32(ctrls);
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

  public boolean isRealModsEnabled(ModMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (ModMask m : maskEnums) {
      if (!m.isEnabled(realMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isVmodsEnabled(VMod... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (VMod m : maskEnums) {
      if (!m.isEnabled(vmods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isCtrlsEnabled(BoolCtrl... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (BoolCtrl m : maskEnums) {
      if (!m.isEnabled(ctrls)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class IndicatorMapBuilder {
    @java.lang.SuppressWarnings("all")
    private byte flags;
    @java.lang.SuppressWarnings("all")
    private byte whichGroups;
    @java.lang.SuppressWarnings("all")
    private byte groups;
    @java.lang.SuppressWarnings("all")
    private byte whichMods;
    @java.lang.SuppressWarnings("all")
    private byte mods;
    @java.lang.SuppressWarnings("all")
    private byte realMods;
    @java.lang.SuppressWarnings("all")
    private short vmods;
    @java.lang.SuppressWarnings("all")
    private int ctrls;

    public IndicatorMap.IndicatorMapBuilder flags(IMFlag flags) {
      this.flags = (byte) flags.getValue();
      return this;
    }

    public IndicatorMap.IndicatorMapBuilder flags(byte flags) {
      this.flags = flags;
      return this;
    }

    public IndicatorMap.IndicatorMapBuilder whichGroups(IMGroupsWhich whichGroups) {
      this.whichGroups = (byte) whichGroups.getValue();
      return this;
    }

    public IndicatorMap.IndicatorMapBuilder whichGroups(byte whichGroups) {
      this.whichGroups = whichGroups;
      return this;
    }

    public IndicatorMap.IndicatorMapBuilder groups(SetOfGroup groups) {
      this.groups = (byte) groups.getValue();
      return this;
    }

    public IndicatorMap.IndicatorMapBuilder groups(byte groups) {
      this.groups = groups;
      return this;
    }

    public IndicatorMap.IndicatorMapBuilder whichMods(IMModsWhich whichMods) {
      this.whichMods = (byte) whichMods.getValue();
      return this;
    }

    public IndicatorMap.IndicatorMapBuilder whichMods(byte whichMods) {
      this.whichMods = whichMods;
      return this;
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

    public IndicatorMap.IndicatorMapBuilder modsEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        mods((byte) m.enableFor(mods));
      }
      return this;
    }

    public IndicatorMap.IndicatorMapBuilder modsDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        mods((byte) m.disableFor(mods));
      }
      return this;
    }

    public boolean isRealModsEnabled(ModMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (ModMask m : maskEnums) {
        if (!m.isEnabled(realMods)) {
          return false;
        }
      }
      return true;
    }

    public IndicatorMap.IndicatorMapBuilder realModsEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        realMods((byte) m.enableFor(realMods));
      }
      return this;
    }

    public IndicatorMap.IndicatorMapBuilder realModsDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        realMods((byte) m.disableFor(realMods));
      }
      return this;
    }

    public boolean isVmodsEnabled(VMod... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (VMod m : maskEnums) {
        if (!m.isEnabled(vmods)) {
          return false;
        }
      }
      return true;
    }

    public IndicatorMap.IndicatorMapBuilder vmodsEnable(VMod... maskEnums) {
      for (VMod m : maskEnums) {
        vmods((short) m.enableFor(vmods));
      }
      return this;
    }

    public IndicatorMap.IndicatorMapBuilder vmodsDisable(VMod... maskEnums) {
      for (VMod m : maskEnums) {
        vmods((short) m.disableFor(vmods));
      }
      return this;
    }

    public boolean isCtrlsEnabled(BoolCtrl... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (BoolCtrl m : maskEnums) {
        if (!m.isEnabled(ctrls)) {
          return false;
        }
      }
      return true;
    }

    public IndicatorMap.IndicatorMapBuilder ctrlsEnable(BoolCtrl... maskEnums) {
      for (BoolCtrl m : maskEnums) {
        ctrls((int) m.enableFor(ctrls));
      }
      return this;
    }

    public IndicatorMap.IndicatorMapBuilder ctrlsDisable(BoolCtrl... maskEnums) {
      for (BoolCtrl m : maskEnums) {
        ctrls((int) m.disableFor(ctrls));
      }
      return this;
    }

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    IndicatorMapBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public IndicatorMap.IndicatorMapBuilder mods(final byte mods) {
      this.mods = mods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public IndicatorMap.IndicatorMapBuilder realMods(final byte realMods) {
      this.realMods = realMods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public IndicatorMap.IndicatorMapBuilder vmods(final short vmods) {
      this.vmods = vmods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public IndicatorMap.IndicatorMapBuilder ctrls(final int ctrls) {
      this.ctrls = ctrls;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public IndicatorMap build() {
      return new IndicatorMap(this.flags, this.whichGroups, this.groups, this.whichMods, this.mods, this.realMods, this.vmods, this.ctrls);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "IndicatorMap.IndicatorMapBuilder(flags=" + this.flags + ", whichGroups=" + this.whichGroups + ", groups=" + this.groups + ", whichMods=" + this.whichMods + ", mods=" + this.mods + ", realMods=" + this.realMods + ", vmods=" + this.vmods + ", ctrls=" + this.ctrls + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  IndicatorMap(final byte flags, final byte whichGroups, final byte groups, final byte whichMods, final byte mods, final byte realMods, final short vmods, final int ctrls) {
    this.flags = flags;
    this.whichGroups = whichGroups;
    this.groups = groups;
    this.whichMods = whichMods;
    this.mods = mods;
    this.realMods = realMods;
    this.vmods = vmods;
    this.ctrls = ctrls;
  }

  @java.lang.SuppressWarnings("all")
  public static IndicatorMap.IndicatorMapBuilder builder() {
    return new IndicatorMap.IndicatorMapBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getFlags() {
    return this.flags;
  }

  @java.lang.SuppressWarnings("all")
  public byte getWhichGroups() {
    return this.whichGroups;
  }

  @java.lang.SuppressWarnings("all")
  public byte getGroups() {
    return this.groups;
  }

  @java.lang.SuppressWarnings("all")
  public byte getWhichMods() {
    return this.whichMods;
  }

  @java.lang.SuppressWarnings("all")
  public byte getMods() {
    return this.mods;
  }

  @java.lang.SuppressWarnings("all")
  public byte getRealMods() {
    return this.realMods;
  }

  @java.lang.SuppressWarnings("all")
  public short getVmods() {
    return this.vmods;
  }

  @java.lang.SuppressWarnings("all")
  public int getCtrls() {
    return this.ctrls;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof IndicatorMap)) return false;
    final IndicatorMap other = (IndicatorMap) o;
    if (this.getFlags() != other.getFlags()) return false;
    if (this.getWhichGroups() != other.getWhichGroups()) return false;
    if (this.getGroups() != other.getGroups()) return false;
    if (this.getWhichMods() != other.getWhichMods()) return false;
    if (this.getMods() != other.getMods()) return false;
    if (this.getRealMods() != other.getRealMods()) return false;
    if (this.getVmods() != other.getVmods()) return false;
    if (this.getCtrls() != other.getCtrls()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFlags();
    result = result * PRIME + this.getWhichGroups();
    result = result * PRIME + this.getGroups();
    result = result * PRIME + this.getWhichMods();
    result = result * PRIME + this.getMods();
    result = result * PRIME + this.getRealMods();
    result = result * PRIME + this.getVmods();
    result = result * PRIME + this.getCtrls();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "IndicatorMap(flags=" + this.getFlags() + ", whichGroups=" + this.getWhichGroups() + ", groups=" + this.getGroups() + ", whichMods=" + this.getWhichMods() + ", mods=" + this.getMods() + ", realMods=" + this.getRealMods() + ", vmods=" + this.getVmods() + ", ctrls=" + this.getCtrls() + ")";
  }
}
