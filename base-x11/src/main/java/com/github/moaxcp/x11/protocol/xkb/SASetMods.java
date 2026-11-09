package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import com.github.moaxcp.x11.protocol.xproto.ModMask;
import java.io.IOException;

public final class SASetMods implements ActionUnion, XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final byte type;
  private final byte flags;
  private final byte mask;
  private final byte realMods;
  private final byte vmodsHigh;
  private final byte vmodsLow;

  public static SASetMods readSASetMods(X11Input in) throws IOException {
    SASetMods.SASetModsBuilder javaBuilder = SASetMods.builder();
    byte type = in.readCard8();
    byte flags = in.readCard8();
    byte mask = in.readCard8();
    byte realMods = in.readCard8();
    byte vmodsHigh = in.readCard8();
    byte vmodsLow = in.readCard8();
    byte[] pad6 = in.readPad(2);
    javaBuilder.type(type);
    javaBuilder.flags(flags);
    javaBuilder.mask(mask);
    javaBuilder.realMods(realMods);
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
    out.writeCard8(vmodsHigh);
    out.writeCard8(vmodsLow);
    out.writePad(2);
  }

  public boolean isFlagsEnabled(Sa... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (Sa m : maskEnums) {
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


  public static class SASetModsBuilder {
    @java.lang.SuppressWarnings("all")
    private byte type;
    @java.lang.SuppressWarnings("all")
    private byte flags;
    @java.lang.SuppressWarnings("all")
    private byte mask;
    @java.lang.SuppressWarnings("all")
    private byte realMods;
    @java.lang.SuppressWarnings("all")
    private byte vmodsHigh;
    @java.lang.SuppressWarnings("all")
    private byte vmodsLow;

    public SASetMods.SASetModsBuilder type(SAType type) {
      this.type = (byte) type.getValue();
      return this;
    }

    public SASetMods.SASetModsBuilder type(byte type) {
      this.type = type;
      return this;
    }

    public boolean isFlagsEnabled(Sa... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (Sa m : maskEnums) {
        if (!m.isEnabled(flags)) {
          return false;
        }
      }
      return true;
    }

    public SASetMods.SASetModsBuilder flagsEnable(Sa... maskEnums) {
      for (Sa m : maskEnums) {
        flags((byte) m.enableFor(flags));
      }
      return this;
    }

    public SASetMods.SASetModsBuilder flagsDisable(Sa... maskEnums) {
      for (Sa m : maskEnums) {
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

    public SASetMods.SASetModsBuilder maskEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        mask((byte) m.enableFor(mask));
      }
      return this;
    }

    public SASetMods.SASetModsBuilder maskDisable(ModMask... maskEnums) {
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

    public SASetMods.SASetModsBuilder realModsEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        realMods((byte) m.enableFor(realMods));
      }
      return this;
    }

    public SASetMods.SASetModsBuilder realModsDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        realMods((byte) m.disableFor(realMods));
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

    public SASetMods.SASetModsBuilder vmodsHighEnable(VModsHigh... maskEnums) {
      for (VModsHigh m : maskEnums) {
        vmodsHigh((byte) m.enableFor(vmodsHigh));
      }
      return this;
    }

    public SASetMods.SASetModsBuilder vmodsHighDisable(VModsHigh... maskEnums) {
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

    public SASetMods.SASetModsBuilder vmodsLowEnable(VModsLow... maskEnums) {
      for (VModsLow m : maskEnums) {
        vmodsLow((byte) m.enableFor(vmodsLow));
      }
      return this;
    }

    public SASetMods.SASetModsBuilder vmodsLowDisable(VModsLow... maskEnums) {
      for (VModsLow m : maskEnums) {
        vmodsLow((byte) m.disableFor(vmodsLow));
      }
      return this;
    }

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    SASetModsBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SASetMods.SASetModsBuilder flags(final byte flags) {
      this.flags = flags;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SASetMods.SASetModsBuilder mask(final byte mask) {
      this.mask = mask;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SASetMods.SASetModsBuilder realMods(final byte realMods) {
      this.realMods = realMods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SASetMods.SASetModsBuilder vmodsHigh(final byte vmodsHigh) {
      this.vmodsHigh = vmodsHigh;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SASetMods.SASetModsBuilder vmodsLow(final byte vmodsLow) {
      this.vmodsLow = vmodsLow;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SASetMods build() {
      return new SASetMods(this.type, this.flags, this.mask, this.realMods, this.vmodsHigh, this.vmodsLow);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SASetMods.SASetModsBuilder(type=" + this.type + ", flags=" + this.flags + ", mask=" + this.mask + ", realMods=" + this.realMods + ", vmodsHigh=" + this.vmodsHigh + ", vmodsLow=" + this.vmodsLow + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SASetMods(final byte type, final byte flags, final byte mask, final byte realMods, final byte vmodsHigh, final byte vmodsLow) {
    this.type = type;
    this.flags = flags;
    this.mask = mask;
    this.realMods = realMods;
    this.vmodsHigh = vmodsHigh;
    this.vmodsLow = vmodsLow;
  }

  @java.lang.SuppressWarnings("all")
  public static SASetMods.SASetModsBuilder builder() {
    return new SASetMods.SASetModsBuilder();
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
    if (!(o instanceof SASetMods)) return false;
    final SASetMods other = (SASetMods) o;
    if (this.getType() != other.getType()) return false;
    if (this.getFlags() != other.getFlags()) return false;
    if (this.getMask() != other.getMask()) return false;
    if (this.getRealMods() != other.getRealMods()) return false;
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
    result = result * PRIME + this.getVmodsHigh();
    result = result * PRIME + this.getVmodsLow();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SASetMods(type=" + this.getType() + ", flags=" + this.getFlags() + ", mask=" + this.getMask() + ", realMods=" + this.getRealMods() + ", vmodsHigh=" + this.getVmodsHigh() + ", vmodsLow=" + this.getVmodsLow() + ")";
  }
}
