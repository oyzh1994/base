package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class SADeviceBtn implements ActionUnion, XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final byte type;
  private final byte flags;
  private final byte count;
  private final byte button;
  private final byte device;

  public static SADeviceBtn readSADeviceBtn(X11Input in) throws IOException {
    SADeviceBtn.SADeviceBtnBuilder javaBuilder = SADeviceBtn.builder();
    byte type = in.readCard8();
    byte flags = in.readCard8();
    byte count = in.readCard8();
    byte button = in.readCard8();
    byte device = in.readCard8();
    byte[] pad5 = in.readPad(3);
    javaBuilder.type(type);
    javaBuilder.flags(flags);
    javaBuilder.count(count);
    javaBuilder.button(button);
    javaBuilder.device(device);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(type);
    out.writeCard8(flags);
    out.writeCard8(count);
    out.writeCard8(button);
    out.writeCard8(device);
    out.writePad(3);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SADeviceBtnBuilder {
    @java.lang.SuppressWarnings("all")
    private byte type;
    @java.lang.SuppressWarnings("all")
    private byte flags;
    @java.lang.SuppressWarnings("all")
    private byte count;
    @java.lang.SuppressWarnings("all")
    private byte button;
    @java.lang.SuppressWarnings("all")
    private byte device;

    public SADeviceBtn.SADeviceBtnBuilder type(SAType type) {
      this.type = (byte) type.getValue();
      return this;
    }

    public SADeviceBtn.SADeviceBtnBuilder type(byte type) {
      this.type = type;
      return this;
    }

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    SADeviceBtnBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SADeviceBtn.SADeviceBtnBuilder flags(final byte flags) {
      this.flags = flags;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SADeviceBtn.SADeviceBtnBuilder count(final byte count) {
      this.count = count;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SADeviceBtn.SADeviceBtnBuilder button(final byte button) {
      this.button = button;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SADeviceBtn.SADeviceBtnBuilder device(final byte device) {
      this.device = device;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SADeviceBtn build() {
      return new SADeviceBtn(this.type, this.flags, this.count, this.button, this.device);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SADeviceBtn.SADeviceBtnBuilder(type=" + this.type + ", flags=" + this.flags + ", count=" + this.count + ", button=" + this.button + ", device=" + this.device + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SADeviceBtn(final byte type, final byte flags, final byte count, final byte button, final byte device) {
    this.type = type;
    this.flags = flags;
    this.count = count;
    this.button = button;
    this.device = device;
  }

  @java.lang.SuppressWarnings("all")
  public static SADeviceBtn.SADeviceBtnBuilder builder() {
    return new SADeviceBtn.SADeviceBtnBuilder();
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
  public byte getCount() {
    return this.count;
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
    if (!(o instanceof SADeviceBtn)) return false;
    final SADeviceBtn other = (SADeviceBtn) o;
    if (this.getType() != other.getType()) return false;
    if (this.getFlags() != other.getFlags()) return false;
    if (this.getCount() != other.getCount()) return false;
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
    result = result * PRIME + this.getCount();
    result = result * PRIME + this.getButton();
    result = result * PRIME + this.getDevice();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SADeviceBtn(type=" + this.getType() + ", flags=" + this.getFlags() + ", count=" + this.getCount() + ", button=" + this.getButton() + ", device=" + this.getDevice() + ")";
  }
}
