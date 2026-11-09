package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class KeyVModMap implements XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final byte keycode;
  private final short vmods;

  public static KeyVModMap readKeyVModMap(X11Input in) throws IOException {
    KeyVModMap.KeyVModMapBuilder javaBuilder = KeyVModMap.builder();
    byte keycode = in.readCard8();
    byte[] pad1 = in.readPad(1);
    short vmods = in.readCard16();
    javaBuilder.keycode(keycode);
    javaBuilder.vmods(vmods);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(keycode);
    out.writePad(1);
    out.writeCard16(vmods);
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


  public static class KeyVModMapBuilder {
    @java.lang.SuppressWarnings("all")
    private byte keycode;
    @java.lang.SuppressWarnings("all")
    private short vmods;

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

    public KeyVModMap.KeyVModMapBuilder vmodsEnable(VMod... maskEnums) {
      for (VMod m : maskEnums) {
        vmods((short) m.enableFor(vmods));
      }
      return this;
    }

    public KeyVModMap.KeyVModMapBuilder vmodsDisable(VMod... maskEnums) {
      for (VMod m : maskEnums) {
        vmods((short) m.disableFor(vmods));
      }
      return this;
    }

    public int getSize() {
      return 4;
    }

    @java.lang.SuppressWarnings("all")
    KeyVModMapBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public KeyVModMap.KeyVModMapBuilder keycode(final byte keycode) {
      this.keycode = keycode;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public KeyVModMap.KeyVModMapBuilder vmods(final short vmods) {
      this.vmods = vmods;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public KeyVModMap build() {
      return new KeyVModMap(this.keycode, this.vmods);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "KeyVModMap.KeyVModMapBuilder(keycode=" + this.keycode + ", vmods=" + this.vmods + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  KeyVModMap(final byte keycode, final short vmods) {
    this.keycode = keycode;
    this.vmods = vmods;
  }

  @java.lang.SuppressWarnings("all")
  public static KeyVModMap.KeyVModMapBuilder builder() {
    return new KeyVModMap.KeyVModMapBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getKeycode() {
    return this.keycode;
  }

  @java.lang.SuppressWarnings("all")
  public short getVmods() {
    return this.vmods;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof KeyVModMap)) return false;
    final KeyVModMap other = (KeyVModMap) o;
    if (this.getKeycode() != other.getKeycode()) return false;
    if (this.getVmods() != other.getVmods()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getKeycode();
    result = result * PRIME + this.getVmods();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "KeyVModMap(keycode=" + this.getKeycode() + ", vmods=" + this.getVmods() + ")";
  }
}
