package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import com.github.moaxcp.x11.protocol.xproto.ModMask;
import java.io.IOException;

public final class SAIsoLock implements ActionUnion, XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final byte type;
  private final byte flags;
  private final byte mask;
  private final byte realMods;
  private final byte group;
  private final byte affect;
  private final byte vmodsHigh;
  private final byte vmodsLow;

  public static SAIsoLock readSAIsoLock(X11Input in) throws IOException {
    SAIsoLock.SAIsoLockBuilder javaBuilder = SAIsoLock.builder();
    byte type = in.readCard8();
    byte flags = in.readCard8();
    byte mask = in.readCard8();
    byte realMods = in.readCard8();
    byte group = in.readInt8();
    byte affect = in.readCard8();
    byte vmodsHigh = in.readCard8();
    byte vmodsLow = in.readCard8();
    javaBuilder.type(type);
    javaBuilder.flags(flags);
    javaBuilder.mask(mask);
    javaBuilder.realMods(realMods);
    javaBuilder.group(group);
    javaBuilder.affect(affect);
    javaBuilder.vmodsHigh(vmodsHigh);
    javaBuilder.vmodsLow(vmodsLow);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(type);
    out.writeCard8(flags);
    out.writeCard8(mask);
    out.writeCard8(realMods);
    out.writeInt8(group);
    out.writeCard8(affect);
    out.writeCard8(vmodsHigh);
    out.writeCard8(vmodsLow);
  }

  public boolean isFlagsEnabled(SAIsoLockFlag... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (SAIsoLockFlag m : maskEnums) {
      if (!m.isEnabled(flags)) {
        return false;
      }
    }
    return true;
  }

  public boolean isMaskEnabled(ModMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (ModMask m : maskEnums) {
      if (!m.isEnabled(mask)) {
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

  public boolean isAffectEnabled(SAIsoLockNoAffect... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (SAIsoLockNoAffect m : maskEnums) {
      if (!m.isEnabled(affect)) {
        return false;
      }
    }
    return true;
  }

  public boolean isVmodsHighEnabled(VModsHigh... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (VModsHigh m : maskEnums) {
      if (!m.isEnabled(vmodsHigh)) {
        return false;
      }
    }
    return true;
  }

  public boolean isVmodsLowEnabled(VModsLow... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (VModsLow m : maskEnums) {
      if (!m.isEnabled(vmodsLow)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SAIsoLockBuilder {
    @java.lang.SuppressWarnings("all")
    private byte type;
    @java.lang.SuppressWarnings("all")
    private byte flags;
    @java.lang.SuppressWarnings("all")
    private byte mask;
    @java.lang.SuppressWarnings("all")
    private byte realMods;
    @java.lang.SuppressWarnings("all")
    private byte group;
    @java.lang.SuppressWarnings("all")
    private byte affect;
    @java.lang.SuppressWarnings("all")
    private byte vmodsHigh;
    @java.lang.SuppressWarnings("all")
    private byte vmodsLow;

    public SAIsoLock.SAIsoLockBuilder type(SAType type) {
      this.type = (byte) type.getValue();
      return this;
    }

    public SAIsoLock.SAIsoLockBuilder type(byte type) {
      this.type = type;
      return this;
    }

    public boolean isFlagsEnabled(SAIsoLockFlag... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (SAIsoLockFlag m : maskEnums) {
        if (!m.isEnabled(flags)) {
          return false;
        }
      }
      return true;
    }

    public SAIsoLock.SAIsoLockBuilder flagsEnable(SAIsoLockFlag... maskEnums) {
      for (SAIsoLockFlag m : maskEnums) {
        flags((byte) m.enableFor(flags));
      }
      return this;
    }

    public SAIsoLock.SAIsoLockBuilder flagsDisable(SAIsoLockFlag... maskEnums) {
      for (SAIsoLockFlag m : maskEnums) {
        flags((byte) m.disableFor(flags));
      }
      return this;
    }

    public boolean isMaskEnabled(ModMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (ModMask m : maskEnums) {
        if (!m.isEnabled(mask)) {
          return false;
        }
      }
      return true;
    }

    public SAIsoLock.SAIsoLockBuilder maskEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        mask((byte) m.enableFor(mask));
      }
      return this;
    }

    public SAIsoLock.SAIsoLockBuilder maskDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        mask((byte) m.disableFor(mask));
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

    public SAIsoLock.SAIsoLockBuilder realModsEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        realMods((byte) m.enableFor(realMods));
      }
      return this;
    }

    public SAIsoLock.SAIsoLockBuilder realModsDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        realMods((byte) m.disableFor(realMods));
      }
      return this;
    }

    public boolean isAffectEnabled(SAIsoLockNoAffect... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (SAIsoLockNoAffect m : maskEnums) {
        if (!m.isEnabled(affect)) {
          return false;
        }
      }
      return true;
    }

    public SAIsoLock.SAIsoLockBuilder affectEnable(SAIsoLockNoAffect... maskEnums) {
      for (SAIsoLockNoAffect m : maskEnums) {
        affect((byte) m.enableFor(affect));
      }
      return this;
    }

    public SAIsoLock.SAIsoLockBuilder affectDisable(SAIsoLockNoAffect... maskEnums) {
      for (SAIsoLockNoAffect m : maskEnums) {
        affect((byte) m.disableFor(affect));
      }
      return this;
    }

    public boolean isVmodsHighEnabled(VModsHigh... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (VModsHigh m : maskEnums) {
        if (!m.isEnabled(vmodsHigh)) {
          return false;
        }
      }
      return true;
    }

    public SAIsoLock.SAIsoLockBuilder vmodsHighEnable(VModsHigh... maskEnums) {
      for (VModsHigh m : maskEnums) {
        vmodsHigh((byte) m.enableFor(vmodsHigh));
      }
      return this;
    }

    public SAIsoLock.SAIsoLockBuilder vmodsHighDisable(VModsHigh... maskEnums) {
      for (VModsHigh m : maskEnums) {
        vmodsHigh((byte) m.disableFor(vmodsHigh));
      }
      return this;
    }

    public boolean isVmodsLowEnabled(VModsLow... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (VModsLow m : maskEnums) {
        if (!m.isEnabled(vmodsLow)) {
          return false;
        }
      }
      return true;
    }

    public SAIsoLock.SAIsoLockBuilder vmodsLowEnable(VModsLow... maskEnums) {
      for (VModsLow m : maskEnums) {
        vmodsLow((byte) m.enableFor(vmodsLow));
      }
      return this;
    }

    public SAIsoLock.SAIsoLockBuilder vmodsLowDisable(VModsLow... maskEnums) {
      for (VModsLow m : maskEnums) {
        vmodsLow((byte) m.disableFor(vmodsLow));
      }
      return this;
    }

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    SAIsoLockBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SAIsoLock.SAIsoLockBuilder flags(final byte flags) {
      this.flags = flags;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SAIsoLock.SAIsoLockBuilder mask(final byte mask) {
      this.mask = mask;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SAIsoLock.SAIsoLockBuilder realMods(final byte realMods) {
      this.realMods = realMods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SAIsoLock.SAIsoLockBuilder group(final byte group) {
      this.group = group;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SAIsoLock.SAIsoLockBuilder affect(final byte affect) {
      this.affect = affect;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SAIsoLock.SAIsoLockBuilder vmodsHigh(final byte vmodsHigh) {
      this.vmodsHigh = vmodsHigh;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SAIsoLock.SAIsoLockBuilder vmodsLow(final byte vmodsLow) {
      this.vmodsLow = vmodsLow;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SAIsoLock build() {
      return new SAIsoLock(this.type, this.flags, this.mask, this.realMods, this.group, this.affect, this.vmodsHigh, this.vmodsLow);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SAIsoLock.SAIsoLockBuilder(type=" + this.type + ", flags=" + this.flags + ", mask=" + this.mask + ", realMods=" + this.realMods + ", group=" + this.group + ", affect=" + this.affect + ", vmodsHigh=" + this.vmodsHigh + ", vmodsLow=" + this.vmodsLow + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SAIsoLock(final byte type, final byte flags, final byte mask, final byte realMods, final byte group, final byte affect, final byte vmodsHigh, final byte vmodsLow) {
    this.type = type;
    this.flags = flags;
    this.mask = mask;
    this.realMods = realMods;
    this.group = group;
    this.affect = affect;
    this.vmodsHigh = vmodsHigh;
    this.vmodsLow = vmodsLow;
  }

  @java.lang.SuppressWarnings("all")
  public static SAIsoLock.SAIsoLockBuilder builder() {
    return new SAIsoLock.SAIsoLockBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getType() {
    return this.type;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFlags() {
    return this.flags;
  }

  @java.lang.SuppressWarnings("all")
  public byte getMask() {
    return this.mask;
  }

  @java.lang.SuppressWarnings("all")
  public byte getRealMods() {
    return this.realMods;
  }

  @java.lang.SuppressWarnings("all")
  public byte getGroup() {
    return this.group;
  }

  @java.lang.SuppressWarnings("all")
  public byte getAffect() {
    return this.affect;
  }

  @java.lang.SuppressWarnings("all")
  public byte getVmodsHigh() {
    return this.vmodsHigh;
  }

  @java.lang.SuppressWarnings("all")
  public byte getVmodsLow() {
    return this.vmodsLow;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SAIsoLock)) return false;
    final SAIsoLock other = (SAIsoLock) o;
    if (this.getType() != other.getType()) return false;
    if (this.getFlags() != other.getFlags()) return false;
    if (this.getMask() != other.getMask()) return false;
    if (this.getRealMods() != other.getRealMods()) return false;
    if (this.getGroup() != other.getGroup()) return false;
    if (this.getAffect() != other.getAffect()) return false;
    if (this.getVmodsHigh() != other.getVmodsHigh()) return false;
    if (this.getVmodsLow() != other.getVmodsLow()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getType();
    result = result * PRIME + this.getFlags();
    result = result * PRIME + this.getMask();
    result = result * PRIME + this.getRealMods();
    result = result * PRIME + this.getGroup();
    result = result * PRIME + this.getAffect();
    result = result * PRIME + this.getVmodsHigh();
    result = result * PRIME + this.getVmodsLow();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SAIsoLock(type=" + this.getType() + ", flags=" + this.getFlags() + ", mask=" + this.getMask() + ", realMods=" + this.getRealMods() + ", group=" + this.getGroup() + ", affect=" + this.getAffect() + ", vmodsHigh=" + this.getVmodsHigh() + ", vmodsLow=" + this.getVmodsLow() + ")";
  }
}
