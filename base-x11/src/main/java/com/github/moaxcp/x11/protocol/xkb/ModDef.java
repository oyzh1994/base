package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import com.github.moaxcp.x11.protocol.xproto.ModMask;
import java.io.IOException;

public final class ModDef implements XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final byte mask;
  private final byte realMods;
  private final short vmods;

  public static ModDef readModDef(X11Input in) throws IOException {
    ModDef.ModDefBuilder javaBuilder = ModDef.builder();
    byte mask = in.readCard8();
    byte realMods = in.readCard8();
    short vmods = in.readCard16();
    javaBuilder.mask(mask);
    javaBuilder.realMods(realMods);
    javaBuilder.vmods(vmods);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(mask);
    out.writeCard8(realMods);
    out.writeCard16(vmods);
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

  @Override
  public int getSize() {
    return 4;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ModDefBuilder {
    @java.lang.SuppressWarnings("all")
    private byte mask;
    @java.lang.SuppressWarnings("all")
    private byte realMods;
    @java.lang.SuppressWarnings("all")
    private short vmods;

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

    public ModDef.ModDefBuilder maskEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        mask((byte) m.enableFor(mask));
      }
      return this;
    }

    public ModDef.ModDefBuilder maskDisable(ModMask... maskEnums) {
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

    public ModDef.ModDefBuilder realModsEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        realMods((byte) m.enableFor(realMods));
      }
      return this;
    }

    public ModDef.ModDefBuilder realModsDisable(ModMask... maskEnums) {
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

    public ModDef.ModDefBuilder vmodsEnable(VMod... maskEnums) {
      for (VMod m : maskEnums) {
        vmods((short) m.enableFor(vmods));
      }
      return this;
    }

    public ModDef.ModDefBuilder vmodsDisable(VMod... maskEnums) {
      for (VMod m : maskEnums) {
        vmods((short) m.disableFor(vmods));
      }
      return this;
    }

    public int getSize() {
      return 4;
    }

    @java.lang.SuppressWarnings("all")
    ModDefBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ModDef.ModDefBuilder mask(final byte mask) {
      this.mask = mask;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ModDef.ModDefBuilder realMods(final byte realMods) {
      this.realMods = realMods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ModDef.ModDefBuilder vmods(final short vmods) {
      this.vmods = vmods;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ModDef build() {
      return new ModDef(this.mask, this.realMods, this.vmods);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ModDef.ModDefBuilder(mask=" + this.mask + ", realMods=" + this.realMods + ", vmods=" + this.vmods + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ModDef(final byte mask, final byte realMods, final short vmods) {
    this.mask = mask;
    this.realMods = realMods;
    this.vmods = vmods;
  }

  @java.lang.SuppressWarnings("all")
  public static ModDef.ModDefBuilder builder() {
    return new ModDef.ModDefBuilder();
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
  public short getVmods() {
    return this.vmods;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ModDef)) return false;
    final ModDef other = (ModDef) o;
    if (this.getMask() != other.getMask()) return false;
    if (this.getRealMods() != other.getRealMods()) return false;
    if (this.getVmods() != other.getVmods()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getMask();
    result = result * PRIME + this.getRealMods();
    result = result * PRIME + this.getVmods();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ModDef(mask=" + this.getMask() + ", realMods=" + this.getRealMods() + ", vmods=" + this.getVmods() + ")";
  }
}
