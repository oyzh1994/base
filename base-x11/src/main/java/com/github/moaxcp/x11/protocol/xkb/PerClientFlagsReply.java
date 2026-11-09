package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class PerClientFlagsReply implements XReply {
  public static final String PLUGIN_NAME = "xkb";
  private final byte deviceID;
  private final short sequenceNumber;
  private final int supported;
  private final int value;
  private final int autoCtrls;
  private final int autoCtrlsValues;

  public static PerClientFlagsReply readPerClientFlagsReply(byte deviceID, short sequenceNumber, X11Input in) throws IOException {
    PerClientFlagsReply.PerClientFlagsReplyBuilder javaBuilder = PerClientFlagsReply.builder();
    int length = in.readCard32();
    int supported = in.readCard32();
    int value = in.readCard32();
    int autoCtrls = in.readCard32();
    int autoCtrlsValues = in.readCard32();
    byte[] pad8 = in.readPad(8);
    javaBuilder.deviceID(deviceID);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.supported(supported);
    javaBuilder.value(value);
    javaBuilder.autoCtrls(autoCtrls);
    javaBuilder.autoCtrlsValues(autoCtrlsValues);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(deviceID);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(supported);
    out.writeCard32(value);
    out.writeCard32(autoCtrls);
    out.writeCard32(autoCtrlsValues);
    out.writePad(8);
  }

  public boolean isSupportedEnabled(PerClientFlag... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (PerClientFlag m : maskEnums) {
      if (!m.isEnabled(supported)) {
        return false;
      }
    }
    return true;
  }

  public boolean isValueEnabled(PerClientFlag... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (PerClientFlag m : maskEnums) {
      if (!m.isEnabled(value)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAutoCtrlsEnabled(BoolCtrl... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (BoolCtrl m : maskEnums) {
      if (!m.isEnabled(autoCtrls)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAutoCtrlsValuesEnabled(BoolCtrl... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (BoolCtrl m : maskEnums) {
      if (!m.isEnabled(autoCtrlsValues)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class PerClientFlagsReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte deviceID;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int supported;
    @java.lang.SuppressWarnings("all")
    private int value;
    @java.lang.SuppressWarnings("all")
    private int autoCtrls;
    @java.lang.SuppressWarnings("all")
    private int autoCtrlsValues;

    public boolean isSupportedEnabled(PerClientFlag... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (PerClientFlag m : maskEnums) {
        if (!m.isEnabled(supported)) {
          return false;
        }
      }
      return true;
    }

    public PerClientFlagsReply.PerClientFlagsReplyBuilder supportedEnable(PerClientFlag... maskEnums) {
      for (PerClientFlag m : maskEnums) {
        supported((int) m.enableFor(supported));
      }
      return this;
    }

    public PerClientFlagsReply.PerClientFlagsReplyBuilder supportedDisable(PerClientFlag... maskEnums) {
      for (PerClientFlag m : maskEnums) {
        supported((int) m.disableFor(supported));
      }
      return this;
    }

    public boolean isValueEnabled(PerClientFlag... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (PerClientFlag m : maskEnums) {
        if (!m.isEnabled(value)) {
          return false;
        }
      }
      return true;
    }

    public PerClientFlagsReply.PerClientFlagsReplyBuilder valueEnable(PerClientFlag... maskEnums) {
      for (PerClientFlag m : maskEnums) {
        value((int) m.enableFor(value));
      }
      return this;
    }

    public PerClientFlagsReply.PerClientFlagsReplyBuilder valueDisable(PerClientFlag... maskEnums) {
      for (PerClientFlag m : maskEnums) {
        value((int) m.disableFor(value));
      }
      return this;
    }

    public boolean isAutoCtrlsEnabled(BoolCtrl... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (BoolCtrl m : maskEnums) {
        if (!m.isEnabled(autoCtrls)) {
          return false;
        }
      }
      return true;
    }

    public PerClientFlagsReply.PerClientFlagsReplyBuilder autoCtrlsEnable(BoolCtrl... maskEnums) {
      for (BoolCtrl m : maskEnums) {
        autoCtrls((int) m.enableFor(autoCtrls));
      }
      return this;
    }

    public PerClientFlagsReply.PerClientFlagsReplyBuilder autoCtrlsDisable(BoolCtrl... maskEnums) {
      for (BoolCtrl m : maskEnums) {
        autoCtrls((int) m.disableFor(autoCtrls));
      }
      return this;
    }

    public boolean isAutoCtrlsValuesEnabled(BoolCtrl... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (BoolCtrl m : maskEnums) {
        if (!m.isEnabled(autoCtrlsValues)) {
          return false;
        }
      }
      return true;
    }

    public PerClientFlagsReply.PerClientFlagsReplyBuilder autoCtrlsValuesEnable(BoolCtrl... maskEnums) {
      for (BoolCtrl m : maskEnums) {
        autoCtrlsValues((int) m.enableFor(autoCtrlsValues));
      }
      return this;
    }

    public PerClientFlagsReply.PerClientFlagsReplyBuilder autoCtrlsValuesDisable(BoolCtrl... maskEnums) {
      for (BoolCtrl m : maskEnums) {
        autoCtrlsValues((int) m.disableFor(autoCtrlsValues));
      }
      return this;
    }

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    PerClientFlagsReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PerClientFlagsReply.PerClientFlagsReplyBuilder deviceID(final byte deviceID) {
      this.deviceID = deviceID;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PerClientFlagsReply.PerClientFlagsReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PerClientFlagsReply.PerClientFlagsReplyBuilder supported(final int supported) {
      this.supported = supported;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PerClientFlagsReply.PerClientFlagsReplyBuilder value(final int value) {
      this.value = value;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PerClientFlagsReply.PerClientFlagsReplyBuilder autoCtrls(final int autoCtrls) {
      this.autoCtrls = autoCtrls;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PerClientFlagsReply.PerClientFlagsReplyBuilder autoCtrlsValues(final int autoCtrlsValues) {
      this.autoCtrlsValues = autoCtrlsValues;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public PerClientFlagsReply build() {
      return new PerClientFlagsReply(this.deviceID, this.sequenceNumber, this.supported, this.value, this.autoCtrls, this.autoCtrlsValues);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "PerClientFlagsReply.PerClientFlagsReplyBuilder(deviceID=" + this.deviceID + ", sequenceNumber=" + this.sequenceNumber + ", supported=" + this.supported + ", value=" + this.value + ", autoCtrls=" + this.autoCtrls + ", autoCtrlsValues=" + this.autoCtrlsValues + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  PerClientFlagsReply(final byte deviceID, final short sequenceNumber, final int supported, final int value, final int autoCtrls, final int autoCtrlsValues) {
    this.deviceID = deviceID;
    this.sequenceNumber = sequenceNumber;
    this.supported = supported;
    this.value = value;
    this.autoCtrls = autoCtrls;
    this.autoCtrlsValues = autoCtrlsValues;
  }

  @java.lang.SuppressWarnings("all")
  public static PerClientFlagsReply.PerClientFlagsReplyBuilder builder() {
    return new PerClientFlagsReply.PerClientFlagsReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getDeviceID() {
    return this.deviceID;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getSupported() {
    return this.supported;
  }

  @java.lang.SuppressWarnings("all")
  public int getValue() {
    return this.value;
  }

  @java.lang.SuppressWarnings("all")
  public int getAutoCtrls() {
    return this.autoCtrls;
  }

  @java.lang.SuppressWarnings("all")
  public int getAutoCtrlsValues() {
    return this.autoCtrlsValues;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof PerClientFlagsReply)) return false;
    final PerClientFlagsReply other = (PerClientFlagsReply) o;
    if (this.getDeviceID() != other.getDeviceID()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getSupported() != other.getSupported()) return false;
    if (this.getValue() != other.getValue()) return false;
    if (this.getAutoCtrls() != other.getAutoCtrls()) return false;
    if (this.getAutoCtrlsValues() != other.getAutoCtrlsValues()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDeviceID();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getSupported();
    result = result * PRIME + this.getValue();
    result = result * PRIME + this.getAutoCtrls();
    result = result * PRIME + this.getAutoCtrlsValues();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "PerClientFlagsReply(deviceID=" + this.getDeviceID() + ", sequenceNumber=" + this.getSequenceNumber() + ", supported=" + this.getSupported() + ", value=" + this.getValue() + ", autoCtrls=" + this.getAutoCtrls() + ", autoCtrlsValues=" + this.getAutoCtrlsValues() + ")";
  }
}
