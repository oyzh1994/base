package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class SADeviceValuator implements ActionUnion, XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final byte type;
  private final byte device;
  private final byte val1what;
  private final byte val1index;
  private final byte val1value;
  private final byte val2what;
  private final byte val2index;
  private final byte val2value;

  public static SADeviceValuator readSADeviceValuator(X11Input in) throws IOException {
    SADeviceValuator.SADeviceValuatorBuilder javaBuilder = SADeviceValuator.builder();
    byte type = in.readCard8();
    byte device = in.readCard8();
    byte val1what = in.readCard8();
    byte val1index = in.readCard8();
    byte val1value = in.readCard8();
    byte val2what = in.readCard8();
    byte val2index = in.readCard8();
    byte val2value = in.readCard8();
    javaBuilder.type(type);
    javaBuilder.device(device);
    javaBuilder.val1what(val1what);
    javaBuilder.val1index(val1index);
    javaBuilder.val1value(val1value);
    javaBuilder.val2what(val2what);
    javaBuilder.val2index(val2index);
    javaBuilder.val2value(val2value);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(type);
    out.writeCard8(device);
    out.writeCard8(val1what);
    out.writeCard8(val1index);
    out.writeCard8(val1value);
    out.writeCard8(val2what);
    out.writeCard8(val2index);
    out.writeCard8(val2value);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SADeviceValuatorBuilder {
    @java.lang.SuppressWarnings("all")
    private byte type;
    @java.lang.SuppressWarnings("all")
    private byte device;
    @java.lang.SuppressWarnings("all")
    private byte val1what;
    @java.lang.SuppressWarnings("all")
    private byte val1index;
    @java.lang.SuppressWarnings("all")
    private byte val1value;
    @java.lang.SuppressWarnings("all")
    private byte val2what;
    @java.lang.SuppressWarnings("all")
    private byte val2index;
    @java.lang.SuppressWarnings("all")
    private byte val2value;

    public SADeviceValuator.SADeviceValuatorBuilder type(SAType type) {
      this.type = (byte) type.getValue();
      return this;
    }

    public SADeviceValuator.SADeviceValuatorBuilder type(byte type) {
      this.type = type;
      return this;
    }

    public SADeviceValuator.SADeviceValuatorBuilder val1what(SAValWhat val1what) {
      this.val1what = (byte) val1what.getValue();
      return this;
    }

    public SADeviceValuator.SADeviceValuatorBuilder val1what(byte val1what) {
      this.val1what = val1what;
      return this;
    }

    public SADeviceValuator.SADeviceValuatorBuilder val2what(SAValWhat val2what) {
      this.val2what = (byte) val2what.getValue();
      return this;
    }

    public SADeviceValuator.SADeviceValuatorBuilder val2what(byte val2what) {
      this.val2what = val2what;
      return this;
    }

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    SADeviceValuatorBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SADeviceValuator.SADeviceValuatorBuilder device(final byte device) {
      this.device = device;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SADeviceValuator.SADeviceValuatorBuilder val1index(final byte val1index) {
      this.val1index = val1index;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SADeviceValuator.SADeviceValuatorBuilder val1value(final byte val1value) {
      this.val1value = val1value;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SADeviceValuator.SADeviceValuatorBuilder val2index(final byte val2index) {
      this.val2index = val2index;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SADeviceValuator.SADeviceValuatorBuilder val2value(final byte val2value) {
      this.val2value = val2value;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SADeviceValuator build() {
      return new SADeviceValuator(this.type, this.device, this.val1what, this.val1index, this.val1value, this.val2what, this.val2index, this.val2value);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SADeviceValuator.SADeviceValuatorBuilder(type=" + this.type + ", device=" + this.device + ", val1what=" + this.val1what + ", val1index=" + this.val1index + ", val1value=" + this.val1value + ", val2what=" + this.val2what + ", val2index=" + this.val2index + ", val2value=" + this.val2value + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SADeviceValuator(final byte type, final byte device, final byte val1what, final byte val1index, final byte val1value, final byte val2what, final byte val2index, final byte val2value) {
    this.type = type;
    this.device = device;
    this.val1what = val1what;
    this.val1index = val1index;
    this.val1value = val1value;
    this.val2what = val2what;
    this.val2index = val2index;
    this.val2value = val2value;
  }

  @java.lang.SuppressWarnings("all")
  public static SADeviceValuator.SADeviceValuatorBuilder builder() {
    return new SADeviceValuator.SADeviceValuatorBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getType() {
    return this.type;
  }

  @java.lang.SuppressWarnings("all")
  public byte getDevice() {
    return this.device;
  }

  @java.lang.SuppressWarnings("all")
  public byte getVal1what() {
    return this.val1what;
  }

  @java.lang.SuppressWarnings("all")
  public byte getVal1index() {
    return this.val1index;
  }

  @java.lang.SuppressWarnings("all")
  public byte getVal1value() {
    return this.val1value;
  }

  @java.lang.SuppressWarnings("all")
  public byte getVal2what() {
    return this.val2what;
  }

  @java.lang.SuppressWarnings("all")
  public byte getVal2index() {
    return this.val2index;
  }

  @java.lang.SuppressWarnings("all")
  public byte getVal2value() {
    return this.val2value;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SADeviceValuator)) return false;
    final SADeviceValuator other = (SADeviceValuator) o;
    if (this.getType() != other.getType()) return false;
    if (this.getDevice() != other.getDevice()) return false;
    if (this.getVal1what() != other.getVal1what()) return false;
    if (this.getVal1index() != other.getVal1index()) return false;
    if (this.getVal1value() != other.getVal1value()) return false;
    if (this.getVal2what() != other.getVal2what()) return false;
    if (this.getVal2index() != other.getVal2index()) return false;
    if (this.getVal2value() != other.getVal2value()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getType();
    result = result * PRIME + this.getDevice();
    result = result * PRIME + this.getVal1what();
    result = result * PRIME + this.getVal1index();
    result = result * PRIME + this.getVal1value();
    result = result * PRIME + this.getVal2what();
    result = result * PRIME + this.getVal2index();
    result = result * PRIME + this.getVal2value();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SADeviceValuator(type=" + this.getType() + ", device=" + this.getDevice() + ", val1what=" + this.getVal1what() + ", val1index=" + this.getVal1index() + ", val1value=" + this.getVal1value() + ", val2what=" + this.getVal2what() + ", val2index=" + this.getVal2index() + ", val2value=" + this.getVal2value() + ")";
  }
}
