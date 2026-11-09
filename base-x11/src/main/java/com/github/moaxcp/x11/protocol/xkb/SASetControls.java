package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class SASetControls implements ActionUnion, XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final byte type;
  private final byte boolCtrlsHigh;
  private final byte boolCtrlsLow;

  public static SASetControls readSASetControls(X11Input in) throws IOException {
    SASetControls.SASetControlsBuilder javaBuilder = SASetControls.builder();
    byte type = in.readCard8();
    byte[] pad1 = in.readPad(3);
    byte boolCtrlsHigh = in.readCard8();
    byte boolCtrlsLow = in.readCard8();
    byte[] pad4 = in.readPad(2);
    javaBuilder.type(type);
    javaBuilder.boolCtrlsHigh(boolCtrlsHigh);
    javaBuilder.boolCtrlsLow(boolCtrlsLow);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(type);
    out.writePad(3);
    out.writeCard8(boolCtrlsHigh);
    out.writeCard8(boolCtrlsLow);
    out.writePad(2);
  }

  public boolean isBoolCtrlsHighEnabled(BoolCtrlsHigh... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (BoolCtrlsHigh m : maskEnums) {
      if (!m.isEnabled(boolCtrlsHigh)) {
        return false;
      }
    }
    return true;
  }

  public boolean isBoolCtrlsLowEnabled(BoolCtrlsLow... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (BoolCtrlsLow m : maskEnums) {
      if (!m.isEnabled(boolCtrlsLow)) {
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


  public static class SASetControlsBuilder {
    @java.lang.SuppressWarnings("all")
    private byte type;
    @java.lang.SuppressWarnings("all")
    private byte boolCtrlsHigh;
    @java.lang.SuppressWarnings("all")
    private byte boolCtrlsLow;

    public SASetControls.SASetControlsBuilder type(SAType type) {
      this.type = (byte) type.getValue();
      return this;
    }

    public SASetControls.SASetControlsBuilder type(byte type) {
      this.type = type;
      return this;
    }

    public boolean isBoolCtrlsHighEnabled(BoolCtrlsHigh... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (BoolCtrlsHigh m : maskEnums) {
        if (!m.isEnabled(boolCtrlsHigh)) {
          return false;
        }
      }
      return true;
    }

    public SASetControls.SASetControlsBuilder boolCtrlsHighEnable(BoolCtrlsHigh... maskEnums) {
      for (BoolCtrlsHigh m : maskEnums) {
        boolCtrlsHigh((byte) m.enableFor(boolCtrlsHigh));
      }
      return this;
    }

    public SASetControls.SASetControlsBuilder boolCtrlsHighDisable(BoolCtrlsHigh... maskEnums) {
      for (BoolCtrlsHigh m : maskEnums) {
        boolCtrlsHigh((byte) m.disableFor(boolCtrlsHigh));
      }
      return this;
    }

    public boolean isBoolCtrlsLowEnabled(BoolCtrlsLow... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (BoolCtrlsLow m : maskEnums) {
        if (!m.isEnabled(boolCtrlsLow)) {
          return false;
        }
      }
      return true;
    }

    public SASetControls.SASetControlsBuilder boolCtrlsLowEnable(BoolCtrlsLow... maskEnums) {
      for (BoolCtrlsLow m : maskEnums) {
        boolCtrlsLow((byte) m.enableFor(boolCtrlsLow));
      }
      return this;
    }

    public SASetControls.SASetControlsBuilder boolCtrlsLowDisable(BoolCtrlsLow... maskEnums) {
      for (BoolCtrlsLow m : maskEnums) {
        boolCtrlsLow((byte) m.disableFor(boolCtrlsLow));
      }
      return this;
    }

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    SASetControlsBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SASetControls.SASetControlsBuilder boolCtrlsHigh(final byte boolCtrlsHigh) {
      this.boolCtrlsHigh = boolCtrlsHigh;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SASetControls.SASetControlsBuilder boolCtrlsLow(final byte boolCtrlsLow) {
      this.boolCtrlsLow = boolCtrlsLow;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SASetControls build() {
      return new SASetControls(this.type, this.boolCtrlsHigh, this.boolCtrlsLow);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SASetControls.SASetControlsBuilder(type=" + this.type + ", boolCtrlsHigh=" + this.boolCtrlsHigh + ", boolCtrlsLow=" + this.boolCtrlsLow + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SASetControls(final byte type, final byte boolCtrlsHigh, final byte boolCtrlsLow) {
    this.type = type;
    this.boolCtrlsHigh = boolCtrlsHigh;
    this.boolCtrlsLow = boolCtrlsLow;
  }

  @java.lang.SuppressWarnings("all")
  public static SASetControls.SASetControlsBuilder builder() {
    return new SASetControls.SASetControlsBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getType() {
    return this.type;
  }

  @java.lang.SuppressWarnings("all")
  public byte getBoolCtrlsHigh() {
    return this.boolCtrlsHigh;
  }

  @java.lang.SuppressWarnings("all")
  public byte getBoolCtrlsLow() {
    return this.boolCtrlsLow;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SASetControls)) return false;
    final SASetControls other = (SASetControls) o;
    if (this.getType() != other.getType()) return false;
    if (this.getBoolCtrlsHigh() != other.getBoolCtrlsHigh()) return false;
    if (this.getBoolCtrlsLow() != other.getBoolCtrlsLow()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getType();
    result = result * PRIME + this.getBoolCtrlsHigh();
    result = result * PRIME + this.getBoolCtrlsLow();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SASetControls(type=" + this.getType() + ", boolCtrlsHigh=" + this.getBoolCtrlsHigh() + ", boolCtrlsLow=" + this.getBoolCtrlsLow() + ")";
  }
}
