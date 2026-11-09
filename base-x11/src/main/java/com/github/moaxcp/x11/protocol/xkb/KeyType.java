package com.github.moaxcp.x11.protocol.xkb;

import java.util.ArrayList;
import java.util.List;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XStruct;
import com.github.moaxcp.x11.protocol.xproto.ModMask;
import java.io.IOException;

public final class KeyType implements XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final byte modsMask;
  private final byte modsMods;
  private final short modsVmods;
  private final byte numLevels;
  private final boolean hasPreserve;
  private final List<KTMapEntry> map;
  private final List<ModDef> preserve;

  public static KeyType readKeyType(X11Input in) throws IOException {
    KeyType.KeyTypeBuilder javaBuilder = KeyType.builder();
    byte modsMask = in.readCard8();
    byte modsMods = in.readCard8();
    short modsVmods = in.readCard16();
    byte numLevels = in.readCard8();
    byte nMapEntries = in.readCard8();
    boolean hasPreserve = in.readBool();
    byte[] pad6 = in.readPad(1);
    List<KTMapEntry> map = new ArrayList<>(Byte.toUnsignedInt(nMapEntries));
    for (int i = 0; i < Byte.toUnsignedInt(nMapEntries); i++) {
      map.add(KTMapEntry.readKTMapEntry(in));
    }
    List<ModDef> preserve = new ArrayList<>((hasPreserve ? 1 : 0) * Byte.toUnsignedInt(nMapEntries));
    for (int i = 0; i < (hasPreserve ? 1 : 0) * Byte.toUnsignedInt(nMapEntries); i++) {
      preserve.add(ModDef.readModDef(in));
    }
    javaBuilder.modsMask(modsMask);
    javaBuilder.modsMods(modsMods);
    javaBuilder.modsVmods(modsVmods);
    javaBuilder.numLevels(numLevels);
    javaBuilder.hasPreserve(hasPreserve);
    javaBuilder.map(List.copyOf(map));
    javaBuilder.preserve(List.copyOf(preserve));
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(modsMask);
    out.writeCard8(modsMods);
    out.writeCard16(modsVmods);
    out.writeCard8(numLevels);
    byte nMapEntries = (byte) map.size();
    out.writeCard8(nMapEntries);
    out.writeBool(hasPreserve);
    out.writePad(1);
    for (KTMapEntry t : map) {
      t.write(out);
    }
    for (ModDef t : preserve) {
      t.write(out);
    }
  }

  public boolean isModsMaskEnabled(ModMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (ModMask m : maskEnums) {
      if (!m.isEnabled(modsMask)) {
        return false;
      }
    }
    return true;
  }

  public boolean isModsModsEnabled(ModMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (ModMask m : maskEnums) {
      if (!m.isEnabled(modsMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isModsVmodsEnabled(VMod... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (VMod m : maskEnums) {
      if (!m.isEnabled(modsVmods)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 8 + XObject.sizeOf(map) + XObject.sizeOf(preserve);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class KeyTypeBuilder {
    @java.lang.SuppressWarnings("all")
    private byte modsMask;
    @java.lang.SuppressWarnings("all")
    private byte modsMods;
    @java.lang.SuppressWarnings("all")
    private short modsVmods;
    @java.lang.SuppressWarnings("all")
    private byte numLevels;
    @java.lang.SuppressWarnings("all")
    private boolean hasPreserve;
    @java.lang.SuppressWarnings("all")
    private List<KTMapEntry> map;
    @java.lang.SuppressWarnings("all")
    private List<ModDef> preserve;

    public boolean isModsMaskEnabled(ModMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (ModMask m : maskEnums) {
        if (!m.isEnabled(modsMask)) {
          return false;
        }
      }
      return true;
    }

    public KeyType.KeyTypeBuilder modsMaskEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        modsMask((byte) m.enableFor(modsMask));
      }
      return this;
    }

    public KeyType.KeyTypeBuilder modsMaskDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        modsMask((byte) m.disableFor(modsMask));
      }
      return this;
    }

    public boolean isModsModsEnabled(ModMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (ModMask m : maskEnums) {
        if (!m.isEnabled(modsMods)) {
          return false;
        }
      }
      return true;
    }

    public KeyType.KeyTypeBuilder modsModsEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        modsMods((byte) m.enableFor(modsMods));
      }
      return this;
    }

    public KeyType.KeyTypeBuilder modsModsDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        modsMods((byte) m.disableFor(modsMods));
      }
      return this;
    }

    public boolean isModsVmodsEnabled(VMod... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (VMod m : maskEnums) {
        if (!m.isEnabled(modsVmods)) {
          return false;
        }
      }
      return true;
    }

    public KeyType.KeyTypeBuilder modsVmodsEnable(VMod... maskEnums) {
      for (VMod m : maskEnums) {
        modsVmods((short) m.enableFor(modsVmods));
      }
      return this;
    }

    public KeyType.KeyTypeBuilder modsVmodsDisable(VMod... maskEnums) {
      for (VMod m : maskEnums) {
        modsVmods((short) m.disableFor(modsVmods));
      }
      return this;
    }

    public int getSize() {
      return 8 + XObject.sizeOf(map) + XObject.sizeOf(preserve);
    }

    @java.lang.SuppressWarnings("all")
    KeyTypeBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public KeyType.KeyTypeBuilder modsMask(final byte modsMask) {
      this.modsMask = modsMask;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public KeyType.KeyTypeBuilder modsMods(final byte modsMods) {
      this.modsMods = modsMods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public KeyType.KeyTypeBuilder modsVmods(final short modsVmods) {
      this.modsVmods = modsVmods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public KeyType.KeyTypeBuilder numLevels(final byte numLevels) {
      this.numLevels = numLevels;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public KeyType.KeyTypeBuilder hasPreserve(final boolean hasPreserve) {
      this.hasPreserve = hasPreserve;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public KeyType.KeyTypeBuilder map(final List<KTMapEntry> map) {
      if (map == null) {
        throw new java.lang.NullPointerException("map is marked non-null but is null");
      }
      this.map = map;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public KeyType.KeyTypeBuilder preserve(final List<ModDef> preserve) {
      if (preserve == null) {
        throw new java.lang.NullPointerException("preserve is marked non-null but is null");
      }
      this.preserve = preserve;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public KeyType build() {
      return new KeyType(this.modsMask, this.modsMods, this.modsVmods, this.numLevels, this.hasPreserve, this.map, this.preserve);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "KeyType.KeyTypeBuilder(modsMask=" + this.modsMask + ", modsMods=" + this.modsMods + ", modsVmods=" + this.modsVmods + ", numLevels=" + this.numLevels + ", hasPreserve=" + this.hasPreserve + ", map=" + this.map + ", preserve=" + this.preserve + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  KeyType(final byte modsMask, final byte modsMods, final short modsVmods, final byte numLevels, final boolean hasPreserve, final List<KTMapEntry> map, final List<ModDef> preserve) {
    if (map == null) {
      throw new java.lang.NullPointerException("map is marked non-null but is null");
    }
    if (preserve == null) {
      throw new java.lang.NullPointerException("preserve is marked non-null but is null");
    }
    this.modsMask = modsMask;
    this.modsMods = modsMods;
    this.modsVmods = modsVmods;
    this.numLevels = numLevels;
    this.hasPreserve = hasPreserve;
    this.map = map;
    this.preserve = preserve;
  }

  @java.lang.SuppressWarnings("all")
  public static KeyType.KeyTypeBuilder builder() {
    return new KeyType.KeyTypeBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getModsMask() {
    return this.modsMask;
  }

  @java.lang.SuppressWarnings("all")
  public byte getModsMods() {
    return this.modsMods;
  }

  @java.lang.SuppressWarnings("all")
  public short getModsVmods() {
    return this.modsVmods;
  }

  @java.lang.SuppressWarnings("all")
  public byte getNumLevels() {
    return this.numLevels;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isHasPreserve() {
    return this.hasPreserve;
  }

  @java.lang.SuppressWarnings("all")
  public List<KTMapEntry> getMap() {
    return this.map;
  }

  @java.lang.SuppressWarnings("all")
  public List<ModDef> getPreserve() {
    return this.preserve;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof KeyType)) return false;
    final KeyType other = (KeyType) o;
    if (this.getModsMask() != other.getModsMask()) return false;
    if (this.getModsMods() != other.getModsMods()) return false;
    if (this.getModsVmods() != other.getModsVmods()) return false;
    if (this.getNumLevels() != other.getNumLevels()) return false;
    if (this.isHasPreserve() != other.isHasPreserve()) return false;
    final java.lang.Object this$map = this.getMap();
    final java.lang.Object other$map = other.getMap();
    if (this$map == null ? other$map != null : !this$map.equals(other$map)) return false;
    final java.lang.Object this$preserve = this.getPreserve();
    final java.lang.Object other$preserve = other.getPreserve();
    if (this$preserve == null ? other$preserve != null : !this$preserve.equals(other$preserve)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getModsMask();
    result = result * PRIME + this.getModsMods();
    result = result * PRIME + this.getModsVmods();
    result = result * PRIME + this.getNumLevels();
    result = result * PRIME + (this.isHasPreserve() ? 79 : 97);
    final java.lang.Object $map = this.getMap();
    result = result * PRIME + ($map == null ? 43 : $map.hashCode());
    final java.lang.Object $preserve = this.getPreserve();
    result = result * PRIME + ($preserve == null ? 43 : $preserve.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "KeyType(modsMask=" + this.getModsMask() + ", modsMods=" + this.getModsMods() + ", modsVmods=" + this.getModsVmods() + ", numLevels=" + this.getNumLevels() + ", hasPreserve=" + this.isHasPreserve() + ", map=" + this.getMap() + ", preserve=" + this.getPreserve() + ")";
  }
}
