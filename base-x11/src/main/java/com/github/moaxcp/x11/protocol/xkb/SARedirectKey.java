package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import com.github.moaxcp.x11.protocol.xproto.ModMask;
import java.io.IOException;

public final class SARedirectKey implements ActionUnion, XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final byte type;
  private final byte newkey;
  private final byte mask;
  private final byte realModifiers;
  private final byte vmodsMaskHigh;
  private final byte vmodsMaskLow;
  private final byte vmodsHigh;
  private final byte vmodsLow;

  public static SARedirectKey readSARedirectKey(X11Input in) throws IOException {
    SARedirectKey.SARedirectKeyBuilder javaBuilder = SARedirectKey.builder();
    byte type = in.readCard8();
    byte newkey = in.readCard8();
    byte mask = in.readCard8();
    byte realModifiers = in.readCard8();
    byte vmodsMaskHigh = in.readCard8();
    byte vmodsMaskLow = in.readCard8();
    byte vmodsHigh = in.readCard8();
    byte vmodsLow = in.readCard8();
    javaBuilder.type(type);
    javaBuilder.newkey(newkey);
    javaBuilder.mask(mask);
    javaBuilder.realModifiers(realModifiers);
    javaBuilder.vmodsMaskHigh(vmodsMaskHigh);
    javaBuilder.vmodsMaskLow(vmodsMaskLow);
    javaBuilder.vmodsHigh(vmodsHigh);
    javaBuilder.vmodsLow(vmodsLow);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(type);
    out.writeCard8(newkey);
    out.writeCard8(mask);
    out.writeCard8(realModifiers);
    out.writeCard8(vmodsMaskHigh);
    out.writeCard8(vmodsMaskLow);
    out.writeCard8(vmodsHigh);
    out.writeCard8(vmodsLow);
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

  public boolean isRealModifiersEnabled(ModMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (ModMask m : maskEnums) {
      if (!m.isEnabled(realModifiers)) {
        return false;
      }
    }
    return true;
  }

  public boolean isVmodsMaskHighEnabled(VModsHigh... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (VModsHigh m : maskEnums) {
      if (!m.isEnabled(vmodsMaskHigh)) {
        return false;
      }
    }
    return true;
  }

  public boolean isVmodsMaskLowEnabled(VModsLow... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (VModsLow m : maskEnums) {
      if (!m.isEnabled(vmodsMaskLow)) {
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


  public static class SARedirectKeyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte type;
    @java.lang.SuppressWarnings("all")
    private byte newkey;
    @java.lang.SuppressWarnings("all")
    private byte mask;
    @java.lang.SuppressWarnings("all")
    private byte realModifiers;
    @java.lang.SuppressWarnings("all")
    private byte vmodsMaskHigh;
    @java.lang.SuppressWarnings("all")
    private byte vmodsMaskLow;
    @java.lang.SuppressWarnings("all")
    private byte vmodsHigh;
    @java.lang.SuppressWarnings("all")
    private byte vmodsLow;

    public SARedirectKey.SARedirectKeyBuilder type(SAType type) {
      this.type = (byte) type.getValue();
      return this;
    }

    public SARedirectKey.SARedirectKeyBuilder type(byte type) {
      this.type = type;
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

    public SARedirectKey.SARedirectKeyBuilder maskEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        mask((byte) m.enableFor(mask));
      }
      return this;
    }

    public SARedirectKey.SARedirectKeyBuilder maskDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        mask((byte) m.disableFor(mask));
      }
      return this;
    }

    public boolean isRealModifiersEnabled(ModMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (ModMask m : maskEnums) {
        if (!m.isEnabled(realModifiers)) {
          return false;
        }
      }
      return true;
    }

    public SARedirectKey.SARedirectKeyBuilder realModifiersEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        realModifiers((byte) m.enableFor(realModifiers));
      }
      return this;
    }

    public SARedirectKey.SARedirectKeyBuilder realModifiersDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        realModifiers((byte) m.disableFor(realModifiers));
      }
      return this;
    }

    public boolean isVmodsMaskHighEnabled(VModsHigh... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (VModsHigh m : maskEnums) {
        if (!m.isEnabled(vmodsMaskHigh)) {
          return false;
        }
      }
      return true;
    }

    public SARedirectKey.SARedirectKeyBuilder vmodsMaskHighEnable(VModsHigh... maskEnums) {
      for (VModsHigh m : maskEnums) {
        vmodsMaskHigh((byte) m.enableFor(vmodsMaskHigh));
      }
      return this;
    }

    public SARedirectKey.SARedirectKeyBuilder vmodsMaskHighDisable(VModsHigh... maskEnums) {
      for (VModsHigh m : maskEnums) {
        vmodsMaskHigh((byte) m.disableFor(vmodsMaskHigh));
      }
      return this;
    }

    public boolean isVmodsMaskLowEnabled(VModsLow... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (VModsLow m : maskEnums) {
        if (!m.isEnabled(vmodsMaskLow)) {
          return false;
        }
      }
      return true;
    }

    public SARedirectKey.SARedirectKeyBuilder vmodsMaskLowEnable(VModsLow... maskEnums) {
      for (VModsLow m : maskEnums) {
        vmodsMaskLow((byte) m.enableFor(vmodsMaskLow));
      }
      return this;
    }

    public SARedirectKey.SARedirectKeyBuilder vmodsMaskLowDisable(VModsLow... maskEnums) {
      for (VModsLow m : maskEnums) {
        vmodsMaskLow((byte) m.disableFor(vmodsMaskLow));
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

    public SARedirectKey.SARedirectKeyBuilder vmodsHighEnable(VModsHigh... maskEnums) {
      for (VModsHigh m : maskEnums) {
        vmodsHigh((byte) m.enableFor(vmodsHigh));
      }
      return this;
    }

    public SARedirectKey.SARedirectKeyBuilder vmodsHighDisable(VModsHigh... maskEnums) {
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

    public SARedirectKey.SARedirectKeyBuilder vmodsLowEnable(VModsLow... maskEnums) {
      for (VModsLow m : maskEnums) {
        vmodsLow((byte) m.enableFor(vmodsLow));
      }
      return this;
    }

    public SARedirectKey.SARedirectKeyBuilder vmodsLowDisable(VModsLow... maskEnums) {
      for (VModsLow m : maskEnums) {
        vmodsLow((byte) m.disableFor(vmodsLow));
      }
      return this;
    }

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    SARedirectKeyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SARedirectKey.SARedirectKeyBuilder newkey(final byte newkey) {
      this.newkey = newkey;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SARedirectKey.SARedirectKeyBuilder mask(final byte mask) {
      this.mask = mask;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SARedirectKey.SARedirectKeyBuilder realModifiers(final byte realModifiers) {
      this.realModifiers = realModifiers;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SARedirectKey.SARedirectKeyBuilder vmodsMaskHigh(final byte vmodsMaskHigh) {
      this.vmodsMaskHigh = vmodsMaskHigh;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SARedirectKey.SARedirectKeyBuilder vmodsMaskLow(final byte vmodsMaskLow) {
      this.vmodsMaskLow = vmodsMaskLow;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SARedirectKey.SARedirectKeyBuilder vmodsHigh(final byte vmodsHigh) {
      this.vmodsHigh = vmodsHigh;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SARedirectKey.SARedirectKeyBuilder vmodsLow(final byte vmodsLow) {
      this.vmodsLow = vmodsLow;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SARedirectKey build() {
      return new SARedirectKey(this.type, this.newkey, this.mask, this.realModifiers, this.vmodsMaskHigh, this.vmodsMaskLow, this.vmodsHigh, this.vmodsLow);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SARedirectKey.SARedirectKeyBuilder(type=" + this.type + ", newkey=" + this.newkey + ", mask=" + this.mask + ", realModifiers=" + this.realModifiers + ", vmodsMaskHigh=" + this.vmodsMaskHigh + ", vmodsMaskLow=" + this.vmodsMaskLow + ", vmodsHigh=" + this.vmodsHigh + ", vmodsLow=" + this.vmodsLow + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SARedirectKey(final byte type, final byte newkey, final byte mask, final byte realModifiers, final byte vmodsMaskHigh, final byte vmodsMaskLow, final byte vmodsHigh, final byte vmodsLow) {
    this.type = type;
    this.newkey = newkey;
    this.mask = mask;
    this.realModifiers = realModifiers;
    this.vmodsMaskHigh = vmodsMaskHigh;
    this.vmodsMaskLow = vmodsMaskLow;
    this.vmodsHigh = vmodsHigh;
    this.vmodsLow = vmodsLow;
  }

  @java.lang.SuppressWarnings("all")
  public static SARedirectKey.SARedirectKeyBuilder builder() {
    return new SARedirectKey.SARedirectKeyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getType() {
    return this.type;
  }

  @java.lang.SuppressWarnings("all")
  public byte getNewkey() {
    return this.newkey;
  }

  @java.lang.SuppressWarnings("all")
  public byte getMask() {
    return this.mask;
  }

  @java.lang.SuppressWarnings("all")
  public byte getRealModifiers() {
    return this.realModifiers;
  }

  @java.lang.SuppressWarnings("all")
  public byte getVmodsMaskHigh() {
    return this.vmodsMaskHigh;
  }

  @java.lang.SuppressWarnings("all")
  public byte getVmodsMaskLow() {
    return this.vmodsMaskLow;
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
    if (!(o instanceof SARedirectKey)) return false;
    final SARedirectKey other = (SARedirectKey) o;
    if (this.getType() != other.getType()) return false;
    if (this.getNewkey() != other.getNewkey()) return false;
    if (this.getMask() != other.getMask()) return false;
    if (this.getRealModifiers() != other.getRealModifiers()) return false;
    if (this.getVmodsMaskHigh() != other.getVmodsMaskHigh()) return false;
    if (this.getVmodsMaskLow() != other.getVmodsMaskLow()) return false;
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
    result = result * PRIME + this.getNewkey();
    result = result * PRIME + this.getMask();
    result = result * PRIME + this.getRealModifiers();
    result = result * PRIME + this.getVmodsMaskHigh();
    result = result * PRIME + this.getVmodsMaskLow();
    result = result * PRIME + this.getVmodsHigh();
    result = result * PRIME + this.getVmodsLow();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SARedirectKey(type=" + this.getType() + ", newkey=" + this.getNewkey() + ", mask=" + this.getMask() + ", realModifiers=" + this.getRealModifiers() + ", vmodsMaskHigh=" + this.getVmodsMaskHigh() + ", vmodsMaskLow=" + this.getVmodsMaskLow() + ", vmodsHigh=" + this.getVmodsHigh() + ", vmodsLow=" + this.getVmodsLow() + ")";
  }
}
