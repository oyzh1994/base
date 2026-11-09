package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import com.github.moaxcp.x11.protocol.xproto.ModMask;
import java.io.IOException;

public final class KeyModMap implements XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final byte keycode;
  private final byte mods;

  public static KeyModMap readKeyModMap(X11Input in) throws IOException {
    KeyModMap.KeyModMapBuilder javaBuilder = KeyModMap.builder();
    byte keycode = in.readCard8();
    byte mods = in.readCard8();
    javaBuilder.keycode(keycode);
    javaBuilder.mods(mods);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(keycode);
    out.writeCard8(mods);
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

  @Override
  public int getSize() {
    return 2;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class KeyModMapBuilder {
    @java.lang.SuppressWarnings("all")
    private byte keycode;
    @java.lang.SuppressWarnings("all")
    private byte mods;

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

    public KeyModMap.KeyModMapBuilder modsEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        mods((byte) m.enableFor(mods));
      }
      return this;
    }

    public KeyModMap.KeyModMapBuilder modsDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        mods((byte) m.disableFor(mods));
      }
      return this;
    }

    public int getSize() {
      return 2;
    }

    @java.lang.SuppressWarnings("all")
    KeyModMapBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public KeyModMap.KeyModMapBuilder keycode(final byte keycode) {
      this.keycode = keycode;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public KeyModMap.KeyModMapBuilder mods(final byte mods) {
      this.mods = mods;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public KeyModMap build() {
      return new KeyModMap(this.keycode, this.mods);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "KeyModMap.KeyModMapBuilder(keycode=" + this.keycode + ", mods=" + this.mods + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  KeyModMap(final byte keycode, final byte mods) {
    this.keycode = keycode;
    this.mods = mods;
  }

  @java.lang.SuppressWarnings("all")
  public static KeyModMap.KeyModMapBuilder builder() {
    return new KeyModMap.KeyModMapBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getKeycode() {
    return this.keycode;
  }

  @java.lang.SuppressWarnings("all")
  public byte getMods() {
    return this.mods;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof KeyModMap)) return false;
    final KeyModMap other = (KeyModMap) o;
    if (this.getKeycode() != other.getKeycode()) return false;
    if (this.getMods() != other.getMods()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getKeycode();
    result = result * PRIME + this.getMods();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "KeyModMap(keycode=" + this.getKeycode() + ", mods=" + this.getMods() + ")";
  }
}
