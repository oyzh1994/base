package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import com.github.moaxcp.x11.protocol.xproto.ModMask;
import java.io.IOException;

public final class KTSetMapEntry implements XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final byte level;
  private final byte realMods;
  private final short virtualMods;

  public static KTSetMapEntry readKTSetMapEntry(X11Input in) throws IOException {
    KTSetMapEntry.KTSetMapEntryBuilder javaBuilder = KTSetMapEntry.builder();
    byte level = in.readCard8();
    byte realMods = in.readCard8();
    short virtualMods = in.readCard16();
    javaBuilder.level(level);
    javaBuilder.realMods(realMods);
    javaBuilder.virtualMods(virtualMods);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(level);
    out.writeCard8(realMods);
    out.writeCard16(virtualMods);
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
    return 4;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class KTSetMapEntryBuilder {
    @java.lang.SuppressWarnings("all")
    private byte level;
    @java.lang.SuppressWarnings("all")
    private byte realMods;
    @java.lang.SuppressWarnings("all")
    private short virtualMods;

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

    public KTSetMapEntry.KTSetMapEntryBuilder realModsEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        realMods((byte) m.enableFor(realMods));
      }
      return this;
    }

    public KTSetMapEntry.KTSetMapEntryBuilder realModsDisable(ModMask... maskEnums) {
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

    public KTSetMapEntry.KTSetMapEntryBuilder virtualModsEnable(VMod... maskEnums) {
      for (VMod m : maskEnums) {
        virtualMods((short) m.enableFor(virtualMods));
      }
      return this;
    }

    public KTSetMapEntry.KTSetMapEntryBuilder virtualModsDisable(VMod... maskEnums) {
      for (VMod m : maskEnums) {
        virtualMods((short) m.disableFor(virtualMods));
      }
      return this;
    }

    public int getSize() {
      return 4;
    }

    @java.lang.SuppressWarnings("all")
    KTSetMapEntryBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public KTSetMapEntry.KTSetMapEntryBuilder level(final byte level) {
      this.level = level;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public KTSetMapEntry.KTSetMapEntryBuilder realMods(final byte realMods) {
      this.realMods = realMods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public KTSetMapEntry.KTSetMapEntryBuilder virtualMods(final short virtualMods) {
      this.virtualMods = virtualMods;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public KTSetMapEntry build() {
      return new KTSetMapEntry(this.level, this.realMods, this.virtualMods);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "KTSetMapEntry.KTSetMapEntryBuilder(level=" + this.level + ", realMods=" + this.realMods + ", virtualMods=" + this.virtualMods + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  KTSetMapEntry(final byte level, final byte realMods, final short virtualMods) {
    this.level = level;
    this.realMods = realMods;
    this.virtualMods = virtualMods;
  }

  @java.lang.SuppressWarnings("all")
  public static KTSetMapEntry.KTSetMapEntryBuilder builder() {
    return new KTSetMapEntry.KTSetMapEntryBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getLevel() {
    return this.level;
  }

  @java.lang.SuppressWarnings("all")
  public byte getRealMods() {
    return this.realMods;
  }

  @java.lang.SuppressWarnings("all")
  public short getVirtualMods() {
    return this.virtualMods;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof KTSetMapEntry)) return false;
    final KTSetMapEntry other = (KTSetMapEntry) o;
    if (this.getLevel() != other.getLevel()) return false;
    if (this.getRealMods() != other.getRealMods()) return false;
    if (this.getVirtualMods() != other.getVirtualMods()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getLevel();
    result = result * PRIME + this.getRealMods();
    result = result * PRIME + this.getVirtualMods();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "KTSetMapEntry(level=" + this.getLevel() + ", realMods=" + this.getRealMods() + ", virtualMods=" + this.getVirtualMods() + ")";
  }
}
