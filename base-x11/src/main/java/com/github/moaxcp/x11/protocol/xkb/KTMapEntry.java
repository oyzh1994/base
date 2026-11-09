package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import com.github.moaxcp.x11.protocol.xproto.ModMask;
import java.io.IOException;

public final class KTMapEntry implements XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final boolean active;
  private final byte modsMask;
  private final byte level;
  private final byte modsMods;
  private final short modsVmods;

  public static KTMapEntry readKTMapEntry(X11Input in) throws IOException {
    KTMapEntry.KTMapEntryBuilder javaBuilder = KTMapEntry.builder();
    boolean active = in.readBool();
    byte modsMask = in.readCard8();
    byte level = in.readCard8();
    byte modsMods = in.readCard8();
    short modsVmods = in.readCard16();
    byte[] pad5 = in.readPad(2);
    javaBuilder.active(active);
    javaBuilder.modsMask(modsMask);
    javaBuilder.level(level);
    javaBuilder.modsMods(modsMods);
    javaBuilder.modsVmods(modsVmods);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeBool(active);
    out.writeCard8(modsMask);
    out.writeCard8(level);
    out.writeCard8(modsMods);
    out.writeCard16(modsVmods);
    out.writePad(2);
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
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class KTMapEntryBuilder {
    @java.lang.SuppressWarnings("all")
    private boolean active;
    @java.lang.SuppressWarnings("all")
    private byte modsMask;
    @java.lang.SuppressWarnings("all")
    private byte level;
    @java.lang.SuppressWarnings("all")
    private byte modsMods;
    @java.lang.SuppressWarnings("all")
    private short modsVmods;

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

    public KTMapEntry.KTMapEntryBuilder modsMaskEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        modsMask((byte) m.enableFor(modsMask));
      }
      return this;
    }

    public KTMapEntry.KTMapEntryBuilder modsMaskDisable(ModMask... maskEnums) {
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

    public KTMapEntry.KTMapEntryBuilder modsModsEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        modsMods((byte) m.enableFor(modsMods));
      }
      return this;
    }

    public KTMapEntry.KTMapEntryBuilder modsModsDisable(ModMask... maskEnums) {
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

    public KTMapEntry.KTMapEntryBuilder modsVmodsEnable(VMod... maskEnums) {
      for (VMod m : maskEnums) {
        modsVmods((short) m.enableFor(modsVmods));
      }
      return this;
    }

    public KTMapEntry.KTMapEntryBuilder modsVmodsDisable(VMod... maskEnums) {
      for (VMod m : maskEnums) {
        modsVmods((short) m.disableFor(modsVmods));
      }
      return this;
    }

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    KTMapEntryBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public KTMapEntry.KTMapEntryBuilder active(final boolean active) {
      this.active = active;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public KTMapEntry.KTMapEntryBuilder modsMask(final byte modsMask) {
      this.modsMask = modsMask;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public KTMapEntry.KTMapEntryBuilder level(final byte level) {
      this.level = level;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public KTMapEntry.KTMapEntryBuilder modsMods(final byte modsMods) {
      this.modsMods = modsMods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public KTMapEntry.KTMapEntryBuilder modsVmods(final short modsVmods) {
      this.modsVmods = modsVmods;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public KTMapEntry build() {
      return new KTMapEntry(this.active, this.modsMask, this.level, this.modsMods, this.modsVmods);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "KTMapEntry.KTMapEntryBuilder(active=" + this.active + ", modsMask=" + this.modsMask + ", level=" + this.level + ", modsMods=" + this.modsMods + ", modsVmods=" + this.modsVmods + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  KTMapEntry(final boolean active, final byte modsMask, final byte level, final byte modsMods, final short modsVmods) {
    this.active = active;
    this.modsMask = modsMask;
    this.level = level;
    this.modsMods = modsMods;
    this.modsVmods = modsVmods;
  }

  @java.lang.SuppressWarnings("all")
  public static KTMapEntry.KTMapEntryBuilder builder() {
    return new KTMapEntry.KTMapEntryBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public boolean isActive() {
    return this.active;
  }

  @java.lang.SuppressWarnings("all")
  public byte getModsMask() {
    return this.modsMask;
  }

  @java.lang.SuppressWarnings("all")
  public byte getLevel() {
    return this.level;
  }

  @java.lang.SuppressWarnings("all")
  public byte getModsMods() {
    return this.modsMods;
  }

  @java.lang.SuppressWarnings("all")
  public short getModsVmods() {
    return this.modsVmods;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof KTMapEntry)) return false;
    final KTMapEntry other = (KTMapEntry) o;
    if (this.isActive() != other.isActive()) return false;
    if (this.getModsMask() != other.getModsMask()) return false;
    if (this.getLevel() != other.getLevel()) return false;
    if (this.getModsMods() != other.getModsMods()) return false;
    if (this.getModsVmods() != other.getModsVmods()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + (this.isActive() ? 79 : 97);
    result = result * PRIME + this.getModsMask();
    result = result * PRIME + this.getLevel();
    result = result * PRIME + this.getModsMods();
    result = result * PRIME + this.getModsVmods();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "KTMapEntry(active=" + this.isActive() + ", modsMask=" + this.getModsMask() + ", level=" + this.getLevel() + ", modsMods=" + this.getModsMods() + ", modsVmods=" + this.getModsVmods() + ")";
  }
}
