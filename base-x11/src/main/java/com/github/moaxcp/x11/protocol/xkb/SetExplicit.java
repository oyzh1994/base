package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class SetExplicit implements XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final byte keycode;
  private final byte explicit;

  public static SetExplicit readSetExplicit(X11Input in) throws IOException {
    SetExplicit.SetExplicitBuilder javaBuilder = SetExplicit.builder();
    byte keycode = in.readCard8();
    byte explicit = in.readCard8();
    javaBuilder.keycode(keycode);
    javaBuilder.explicit(explicit);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(keycode);
    out.writeCard8(explicit);
  }

  public boolean isExplicitEnabled(Explicit... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (Explicit m : maskEnums) {
      if (!m.isEnabled(explicit)) {
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


  public static class SetExplicitBuilder {
    @java.lang.SuppressWarnings("all")
    private byte keycode;
    @java.lang.SuppressWarnings("all")
    private byte explicit;

    public boolean isExplicitEnabled(Explicit... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (Explicit m : maskEnums) {
        if (!m.isEnabled(explicit)) {
          return false;
        }
      }
      return true;
    }

    public SetExplicit.SetExplicitBuilder explicitEnable(Explicit... maskEnums) {
      for (Explicit m : maskEnums) {
        explicit((byte) m.enableFor(explicit));
      }
      return this;
    }

    public SetExplicit.SetExplicitBuilder explicitDisable(Explicit... maskEnums) {
      for (Explicit m : maskEnums) {
        explicit((byte) m.disableFor(explicit));
      }
      return this;
    }

    public int getSize() {
      return 2;
    }

    @java.lang.SuppressWarnings("all")
    SetExplicitBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetExplicit.SetExplicitBuilder keycode(final byte keycode) {
      this.keycode = keycode;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetExplicit.SetExplicitBuilder explicit(final byte explicit) {
      this.explicit = explicit;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetExplicit build() {
      return new SetExplicit(this.keycode, this.explicit);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetExplicit.SetExplicitBuilder(keycode=" + this.keycode + ", explicit=" + this.explicit + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetExplicit(final byte keycode, final byte explicit) {
    this.keycode = keycode;
    this.explicit = explicit;
  }

  @java.lang.SuppressWarnings("all")
  public static SetExplicit.SetExplicitBuilder builder() {
    return new SetExplicit.SetExplicitBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getKeycode() {
    return this.keycode;
  }

  @java.lang.SuppressWarnings("all")
  public byte getExplicit() {
    return this.explicit;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetExplicit)) return false;
    final SetExplicit other = (SetExplicit) o;
    if (this.getKeycode() != other.getKeycode()) return false;
    if (this.getExplicit() != other.getExplicit()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getKeycode();
    result = result * PRIME + this.getExplicit();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetExplicit(keycode=" + this.getKeycode() + ", explicit=" + this.getExplicit() + ")";
  }
}
