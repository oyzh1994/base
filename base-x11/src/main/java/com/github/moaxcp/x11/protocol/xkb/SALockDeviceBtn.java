package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class SALockDeviceBtn implements ActionUnion, XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final byte type;
  private final byte flags;
  private final byte button;
  private final byte device;

  public static SALockDeviceBtn readSALockDeviceBtn(X11Input in) throws IOException {
    SALockDeviceBtn.SALockDeviceBtnBuilder javaBuilder = SALockDeviceBtn.builder();
    byte type = in.readCard8();
    byte flags = in.readCard8();
    byte[] pad2 = in.readPad(1);
    byte button = in.readCard8();
    byte device = in.readCard8();
    byte[] pad5 = in.readPad(3);
    javaBuilder.type(type);
    javaBuilder.flags(flags);
    javaBuilder.button(button);
    javaBuilder.device(device);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(type);
    out.writeCard8(flags);
    out.writePad(1);
    out.writeCard8(button);
    out.writeCard8(device);
    out.writePad(3);
  }

  public boolean isFlagsEnabled(LockDeviceFlags... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (LockDeviceFlags m : maskEnums) {
      if (!m.isEnabled(flags)) {
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


  public static class SALockDeviceBtnBuilder {
    @java.lang.SuppressWarnings("all")
    private byte type;
    @java.lang.SuppressWarnings("all")
    private byte flags;
    @java.lang.SuppressWarnings("all")
    private byte button;
    @java.lang.SuppressWarnings("all")
    private byte device;

    public SALockDeviceBtn.SALockDeviceBtnBuilder type(SAType type) {
      this.type = (byte) type.getValue();
      return this;
    }

    public SALockDeviceBtn.SALockDeviceBtnBuilder type(byte type) {
      this.type = type;
      return this;
    }

    public boolean isFlagsEnabled(LockDeviceFlags... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (LockDeviceFlags m : maskEnums) {
        if (!m.isEnabled(flags)) {
          return false;
        }
      }
      return true;
    }

    public SALockDeviceBtn.SALockDeviceBtnBuilder flagsEnable(LockDeviceFlags... maskEnums) {
      for (LockDeviceFlags m : maskEnums) {
        flags((byte) m.enableFor(flags));
      }
      return this;
    }

    public SALockDeviceBtn.SALockDeviceBtnBuilder flagsDisable(LockDeviceFlags... maskEnums) {
      for (LockDeviceFlags m : maskEnums) {
        flags((byte) m.disableFor(flags));
      }
      return this;
    }

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    SALockDeviceBtnBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SALockDeviceBtn.SALockDeviceBtnBuilder flags(final byte flags) {
      this.flags = flags;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SALockDeviceBtn.SALockDeviceBtnBuilder button(final byte button) {
      this.button = button;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SALockDeviceBtn.SALockDeviceBtnBuilder device(final byte device) {
      this.device = device;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SALockDeviceBtn build() {
      return new SALockDeviceBtn(this.type, this.flags, this.button, this.device);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SALockDeviceBtn.SALockDeviceBtnBuilder(type=" + this.type + ", flags=" + this.flags + ", button=" + this.button + ", device=" + this.device + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SALockDeviceBtn(final byte type, final byte flags, final byte button, final byte device) {
    this.type = type;
    this.flags = flags;
    this.button = button;
    this.device = device;
  }

  @java.lang.SuppressWarnings("all")
  public static SALockDeviceBtn.SALockDeviceBtnBuilder builder() {
    return new SALockDeviceBtn.SALockDeviceBtnBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getType() {
    return this.type;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFlags() {
    return this.flags;
  }

  @java.lang.SuppressWarnings("all")
  public byte getButton() {
    return this.button;
  }

  @java.lang.SuppressWarnings("all")
  public byte getDevice() {
    return this.device;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SALockDeviceBtn)) return false;
    final SALockDeviceBtn other = (SALockDeviceBtn) o;
    if (this.getType() != other.getType()) return false;
    if (this.getFlags() != other.getFlags()) return false;
    if (this.getButton() != other.getButton()) return false;
    if (this.getDevice() != other.getDevice()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getType();
    result = result * PRIME + this.getFlags();
    result = result * PRIME + this.getButton();
    result = result * PRIME + this.getDevice();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SALockDeviceBtn(type=" + this.getType() + ", flags=" + this.getFlags() + ", button=" + this.getButton() + ", device=" + this.getDevice() + ")";
  }
}
