package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import com.github.moaxcp.x11.protocol.xproto.ModMask;
import java.io.IOException;

public final class SymInterpret implements XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final int sym;
  private final byte mods;
  private final byte match;
  private final byte virtualMod;
  private final byte flags;
  private final SIAction action;

  public static SymInterpret readSymInterpret(X11Input in) throws IOException {
    SymInterpret.SymInterpretBuilder javaBuilder = SymInterpret.builder();
    int sym = in.readCard32();
    byte mods = in.readCard8();
    byte match = in.readCard8();
    byte virtualMod = in.readCard8();
    byte flags = in.readCard8();
    SIAction action = SIAction.readSIAction(in);
    javaBuilder.sym(sym);
    javaBuilder.mods(mods);
    javaBuilder.match(match);
    javaBuilder.virtualMod(virtualMod);
    javaBuilder.flags(flags);
    javaBuilder.action(action);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(sym);
    out.writeCard8(mods);
    out.writeCard8(match);
    out.writeCard8(virtualMod);
    out.writeCard8(flags);
    action.write(out);
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

  public boolean isVirtualModEnabled(VModsLow... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (VModsLow m : maskEnums) {
      if (!m.isEnabled(virtualMod)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 8 + action.getSize();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SymInterpretBuilder {
    @java.lang.SuppressWarnings("all")
    private int sym;
    @java.lang.SuppressWarnings("all")
    private byte mods;
    @java.lang.SuppressWarnings("all")
    private byte match;
    @java.lang.SuppressWarnings("all")
    private byte virtualMod;
    @java.lang.SuppressWarnings("all")
    private byte flags;
    @java.lang.SuppressWarnings("all")
    private SIAction action;

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

    public SymInterpret.SymInterpretBuilder modsEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        mods((byte) m.enableFor(mods));
      }
      return this;
    }

    public SymInterpret.SymInterpretBuilder modsDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        mods((byte) m.disableFor(mods));
      }
      return this;
    }

    public boolean isVirtualModEnabled(VModsLow... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (VModsLow m : maskEnums) {
        if (!m.isEnabled(virtualMod)) {
          return false;
        }
      }
      return true;
    }

    public SymInterpret.SymInterpretBuilder virtualModEnable(VModsLow... maskEnums) {
      for (VModsLow m : maskEnums) {
        virtualMod((byte) m.enableFor(virtualMod));
      }
      return this;
    }

    public SymInterpret.SymInterpretBuilder virtualModDisable(VModsLow... maskEnums) {
      for (VModsLow m : maskEnums) {
        virtualMod((byte) m.disableFor(virtualMod));
      }
      return this;
    }

    public int getSize() {
      return 8 + action.getSize();
    }

    @java.lang.SuppressWarnings("all")
    SymInterpretBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SymInterpret.SymInterpretBuilder sym(final int sym) {
      this.sym = sym;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SymInterpret.SymInterpretBuilder mods(final byte mods) {
      this.mods = mods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SymInterpret.SymInterpretBuilder match(final byte match) {
      this.match = match;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SymInterpret.SymInterpretBuilder virtualMod(final byte virtualMod) {
      this.virtualMod = virtualMod;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SymInterpret.SymInterpretBuilder flags(final byte flags) {
      this.flags = flags;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SymInterpret.SymInterpretBuilder action(final SIAction action) {
      if (action == null) {
        throw new java.lang.NullPointerException("action is marked non-null but is null");
      }
      this.action = action;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SymInterpret build() {
      return new SymInterpret(this.sym, this.mods, this.match, this.virtualMod, this.flags, this.action);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SymInterpret.SymInterpretBuilder(sym=" + this.sym + ", mods=" + this.mods + ", match=" + this.match + ", virtualMod=" + this.virtualMod + ", flags=" + this.flags + ", action=" + this.action + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SymInterpret(final int sym, final byte mods, final byte match, final byte virtualMod, final byte flags, final SIAction action) {
    if (action == null) {
      throw new java.lang.NullPointerException("action is marked non-null but is null");
    }
    this.sym = sym;
    this.mods = mods;
    this.match = match;
    this.virtualMod = virtualMod;
    this.flags = flags;
    this.action = action;
  }

  @java.lang.SuppressWarnings("all")
  public static SymInterpret.SymInterpretBuilder builder() {
    return new SymInterpret.SymInterpretBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getSym() {
    return this.sym;
  }

  @java.lang.SuppressWarnings("all")
  public byte getMods() {
    return this.mods;
  }

  @java.lang.SuppressWarnings("all")
  public byte getMatch() {
    return this.match;
  }

  @java.lang.SuppressWarnings("all")
  public byte getVirtualMod() {
    return this.virtualMod;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFlags() {
    return this.flags;
  }

  @java.lang.SuppressWarnings("all")
  public SIAction getAction() {
    return this.action;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SymInterpret)) return false;
    final SymInterpret other = (SymInterpret) o;
    if (this.getSym() != other.getSym()) return false;
    if (this.getMods() != other.getMods()) return false;
    if (this.getMatch() != other.getMatch()) return false;
    if (this.getVirtualMod() != other.getVirtualMod()) return false;
    if (this.getFlags() != other.getFlags()) return false;
    final java.lang.Object this$action = this.getAction();
    final java.lang.Object other$action = other.getAction();
    if (this$action == null ? other$action != null : !this$action.equals(other$action)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSym();
    result = result * PRIME + this.getMods();
    result = result * PRIME + this.getMatch();
    result = result * PRIME + this.getVirtualMod();
    result = result * PRIME + this.getFlags();
    final java.lang.Object $action = this.getAction();
    result = result * PRIME + ($action == null ? 43 : $action.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SymInterpret(sym=" + this.getSym() + ", mods=" + this.getMods() + ", match=" + this.getMatch() + ", virtualMod=" + this.getVirtualMod() + ", flags=" + this.getFlags() + ", action=" + this.getAction() + ")";
  }
}
