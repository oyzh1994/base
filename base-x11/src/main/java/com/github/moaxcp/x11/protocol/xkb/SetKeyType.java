package com.github.moaxcp.x11.protocol.xkb;

import java.util.ArrayList;
import java.util.List;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XStruct;
import com.github.moaxcp.x11.protocol.xproto.ModMask;
import java.io.IOException;

public final class SetKeyType implements XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final byte mask;
  private final byte realMods;
  private final short virtualMods;
  private final byte numLevels;
  private final boolean preserve;
  private final List<KTSetMapEntry> entries;
  private final List<KTSetMapEntry> preserveEntries;

  public static SetKeyType readSetKeyType(X11Input in) throws IOException {
    SetKeyType.SetKeyTypeBuilder javaBuilder = SetKeyType.builder();
    byte mask = in.readCard8();
    byte realMods = in.readCard8();
    short virtualMods = in.readCard16();
    byte numLevels = in.readCard8();
    byte nMapEntries = in.readCard8();
    boolean preserve = in.readBool();
    byte[] pad6 = in.readPad(1);
    List<KTSetMapEntry> entries = new ArrayList<>(Byte.toUnsignedInt(nMapEntries));
    for (int i = 0; i < Byte.toUnsignedInt(nMapEntries); i++) {
      entries.add(KTSetMapEntry.readKTSetMapEntry(in));
    }
    List<KTSetMapEntry> preserveEntries = new ArrayList<>((preserve ? 1 : 0) * Byte.toUnsignedInt(nMapEntries));
    for (int i = 0; i < (preserve ? 1 : 0) * Byte.toUnsignedInt(nMapEntries); i++) {
      preserveEntries.add(KTSetMapEntry.readKTSetMapEntry(in));
    }
    javaBuilder.mask(mask);
    javaBuilder.realMods(realMods);
    javaBuilder.virtualMods(virtualMods);
    javaBuilder.numLevels(numLevels);
    javaBuilder.preserve(preserve);
    javaBuilder.entries(List.copyOf(entries));
    javaBuilder.preserveEntries(List.copyOf(preserveEntries));
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(mask);
    out.writeCard8(realMods);
    out.writeCard16(virtualMods);
    out.writeCard8(numLevels);
    byte nMapEntries = (byte) entries.size();
    out.writeCard8(nMapEntries);
    out.writeBool(preserve);
    out.writePad(1);
    for (KTSetMapEntry t : entries) {
      t.write(out);
    }
    for (KTSetMapEntry t : preserveEntries) {
      t.write(out);
    }
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

  public boolean isVirtualModsEnabled(VMod... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (VMod m : maskEnums) {
      if (!m.isEnabled(virtualMods)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 8 + XObject.sizeOf(entries) + XObject.sizeOf(preserveEntries);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetKeyTypeBuilder {
    @java.lang.SuppressWarnings("all")
    private byte mask;
    @java.lang.SuppressWarnings("all")
    private byte realMods;
    @java.lang.SuppressWarnings("all")
    private short virtualMods;
    @java.lang.SuppressWarnings("all")
    private byte numLevels;
    @java.lang.SuppressWarnings("all")
    private boolean preserve;
    @java.lang.SuppressWarnings("all")
    private List<KTSetMapEntry> entries;
    @java.lang.SuppressWarnings("all")
    private List<KTSetMapEntry> preserveEntries;

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

    public SetKeyType.SetKeyTypeBuilder maskEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        mask((byte) m.enableFor(mask));
      }
      return this;
    }

    public SetKeyType.SetKeyTypeBuilder maskDisable(ModMask... maskEnums) {
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

    public SetKeyType.SetKeyTypeBuilder realModsEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        realMods((byte) m.enableFor(realMods));
      }
      return this;
    }

    public SetKeyType.SetKeyTypeBuilder realModsDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        realMods((byte) m.disableFor(realMods));
      }
      return this;
    }

    public boolean isVirtualModsEnabled(VMod... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (VMod m : maskEnums) {
        if (!m.isEnabled(virtualMods)) {
          return false;
        }
      }
      return true;
    }

    public SetKeyType.SetKeyTypeBuilder virtualModsEnable(VMod... maskEnums) {
      for (VMod m : maskEnums) {
        virtualMods((short) m.enableFor(virtualMods));
      }
      return this;
    }

    public SetKeyType.SetKeyTypeBuilder virtualModsDisable(VMod... maskEnums) {
      for (VMod m : maskEnums) {
        virtualMods((short) m.disableFor(virtualMods));
      }
      return this;
    }

    public int getSize() {
      return 8 + XObject.sizeOf(entries) + XObject.sizeOf(preserveEntries);
    }

    @java.lang.SuppressWarnings("all")
    SetKeyTypeBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetKeyType.SetKeyTypeBuilder mask(final byte mask) {
      this.mask = mask;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetKeyType.SetKeyTypeBuilder realMods(final byte realMods) {
      this.realMods = realMods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetKeyType.SetKeyTypeBuilder virtualMods(final short virtualMods) {
      this.virtualMods = virtualMods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetKeyType.SetKeyTypeBuilder numLevels(final byte numLevels) {
      this.numLevels = numLevels;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetKeyType.SetKeyTypeBuilder preserve(final boolean preserve) {
      this.preserve = preserve;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetKeyType.SetKeyTypeBuilder entries(final List<KTSetMapEntry> entries) {
      if (entries == null) {
        throw new java.lang.NullPointerException("entries is marked non-null but is null");
      }
      this.entries = entries;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetKeyType.SetKeyTypeBuilder preserveEntries(final List<KTSetMapEntry> preserveEntries) {
      if (preserveEntries == null) {
        throw new java.lang.NullPointerException("preserveEntries is marked non-null but is null");
      }
      this.preserveEntries = preserveEntries;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetKeyType build() {
      return new SetKeyType(this.mask, this.realMods, this.virtualMods, this.numLevels, this.preserve, this.entries, this.preserveEntries);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetKeyType.SetKeyTypeBuilder(mask=" + this.mask + ", realMods=" + this.realMods + ", virtualMods=" + this.virtualMods + ", numLevels=" + this.numLevels + ", preserve=" + this.preserve + ", entries=" + this.entries + ", preserveEntries=" + this.preserveEntries + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetKeyType(final byte mask, final byte realMods, final short virtualMods, final byte numLevels, final boolean preserve, final List<KTSetMapEntry> entries, final List<KTSetMapEntry> preserveEntries) {
    if (entries == null) {
      throw new java.lang.NullPointerException("entries is marked non-null but is null");
    }
    if (preserveEntries == null) {
      throw new java.lang.NullPointerException("preserveEntries is marked non-null but is null");
    }
    this.mask = mask;
    this.realMods = realMods;
    this.virtualMods = virtualMods;
    this.numLevels = numLevels;
    this.preserve = preserve;
    this.entries = entries;
    this.preserveEntries = preserveEntries;
  }

  @java.lang.SuppressWarnings("all")
  public static SetKeyType.SetKeyTypeBuilder builder() {
    return new SetKeyType.SetKeyTypeBuilder();
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
  public short getVirtualMods() {
    return this.virtualMods;
  }

  @java.lang.SuppressWarnings("all")
  public byte getNumLevels() {
    return this.numLevels;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isPreserve() {
    return this.preserve;
  }

  @java.lang.SuppressWarnings("all")
  public List<KTSetMapEntry> getEntries() {
    return this.entries;
  }

  @java.lang.SuppressWarnings("all")
  public List<KTSetMapEntry> getPreserveEntries() {
    return this.preserveEntries;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetKeyType)) return false;
    final SetKeyType other = (SetKeyType) o;
    if (this.getMask() != other.getMask()) return false;
    if (this.getRealMods() != other.getRealMods()) return false;
    if (this.getVirtualMods() != other.getVirtualMods()) return false;
    if (this.getNumLevels() != other.getNumLevels()) return false;
    if (this.isPreserve() != other.isPreserve()) return false;
    final java.lang.Object this$entries = this.getEntries();
    final java.lang.Object other$entries = other.getEntries();
    if (this$entries == null ? other$entries != null : !this$entries.equals(other$entries)) return false;
    final java.lang.Object this$preserveEntries = this.getPreserveEntries();
    final java.lang.Object other$preserveEntries = other.getPreserveEntries();
    if (this$preserveEntries == null ? other$preserveEntries != null : !this$preserveEntries.equals(other$preserveEntries)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getMask();
    result = result * PRIME + this.getRealMods();
    result = result * PRIME + this.getVirtualMods();
    result = result * PRIME + this.getNumLevels();
    result = result * PRIME + (this.isPreserve() ? 79 : 97);
    final java.lang.Object $entries = this.getEntries();
    result = result * PRIME + ($entries == null ? 43 : $entries.hashCode());
    final java.lang.Object $preserveEntries = this.getPreserveEntries();
    result = result * PRIME + ($preserveEntries == null ? 43 : $preserveEntries.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetKeyType(mask=" + this.getMask() + ", realMods=" + this.getRealMods() + ", virtualMods=" + this.getVirtualMods() + ", numLevels=" + this.getNumLevels() + ", preserve=" + this.isPreserve() + ", entries=" + this.getEntries() + ", preserveEntries=" + this.getPreserveEntries() + ")";
  }
}
