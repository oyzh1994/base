package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class PerClientFlags implements TwoWayRequest<PerClientFlagsReply> {
  public static final String PLUGIN_NAME = "xkb";
  public static final byte OPCODE = 21;
  private final short deviceSpec;
  private final int change;
  private final int value;
  private final int ctrlsToChange;
  private final int autoCtrls;
  private final int autoCtrlsValues;

  public XReplyFunction<PerClientFlagsReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> PerClientFlagsReply.readPerClientFlagsReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static PerClientFlags readPerClientFlags(X11Input in) throws IOException {
    PerClientFlags.PerClientFlagsBuilder javaBuilder = PerClientFlags.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short deviceSpec = in.readCard16();
    byte[] pad4 = in.readPad(2);
    int change = in.readCard32();
    int value = in.readCard32();
    int ctrlsToChange = in.readCard32();
    int autoCtrls = in.readCard32();
    int autoCtrlsValues = in.readCard32();
    javaBuilder.deviceSpec(deviceSpec);
    javaBuilder.change(change);
    javaBuilder.value(value);
    javaBuilder.ctrlsToChange(ctrlsToChange);
    javaBuilder.autoCtrls(autoCtrls);
    javaBuilder.autoCtrlsValues(autoCtrlsValues);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(deviceSpec);
    out.writePad(2);
    out.writeCard32(change);
    out.writeCard32(value);
    out.writeCard32(ctrlsToChange);
    out.writeCard32(autoCtrls);
    out.writeCard32(autoCtrlsValues);
  }

  public boolean isChangeEnabled(PerClientFlag... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (PerClientFlag m : maskEnums) {
      if (!m.isEnabled(change)) {
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

  public boolean isCtrlsToChangeEnabled(BoolCtrl... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (BoolCtrl m : maskEnums) {
      if (!m.isEnabled(ctrlsToChange)) {
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
    return 28;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class PerClientFlagsBuilder {
    @java.lang.SuppressWarnings("all")
    private short deviceSpec;
    @java.lang.SuppressWarnings("all")
    private int change;
    @java.lang.SuppressWarnings("all")
    private int value;
    @java.lang.SuppressWarnings("all")
    private int ctrlsToChange;
    @java.lang.SuppressWarnings("all")
    private int autoCtrls;
    @java.lang.SuppressWarnings("all")
    private int autoCtrlsValues;

    public boolean isChangeEnabled(PerClientFlag... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (PerClientFlag m : maskEnums) {
        if (!m.isEnabled(change)) {
          return false;
        }
      }
      return true;
    }

    public PerClientFlags.PerClientFlagsBuilder changeEnable(PerClientFlag... maskEnums) {
      for (PerClientFlag m : maskEnums) {
        change((int) m.enableFor(change));
      }
      return this;
    }

    public PerClientFlags.PerClientFlagsBuilder changeDisable(PerClientFlag... maskEnums) {
      for (PerClientFlag m : maskEnums) {
        change((int) m.disableFor(change));
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

    public PerClientFlags.PerClientFlagsBuilder valueEnable(PerClientFlag... maskEnums) {
      for (PerClientFlag m : maskEnums) {
        value((int) m.enableFor(value));
      }
      return this;
    }

    public PerClientFlags.PerClientFlagsBuilder valueDisable(PerClientFlag... maskEnums) {
      for (PerClientFlag m : maskEnums) {
        value((int) m.disableFor(value));
      }
      return this;
    }

    public boolean isCtrlsToChangeEnabled(BoolCtrl... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (BoolCtrl m : maskEnums) {
        if (!m.isEnabled(ctrlsToChange)) {
          return false;
        }
      }
      return true;
    }

    public PerClientFlags.PerClientFlagsBuilder ctrlsToChangeEnable(BoolCtrl... maskEnums) {
      for (BoolCtrl m : maskEnums) {
        ctrlsToChange((int) m.enableFor(ctrlsToChange));
      }
      return this;
    }

    public PerClientFlags.PerClientFlagsBuilder ctrlsToChangeDisable(BoolCtrl... maskEnums) {
      for (BoolCtrl m : maskEnums) {
        ctrlsToChange((int) m.disableFor(ctrlsToChange));
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

    public PerClientFlags.PerClientFlagsBuilder autoCtrlsEnable(BoolCtrl... maskEnums) {
      for (BoolCtrl m : maskEnums) {
        autoCtrls((int) m.enableFor(autoCtrls));
      }
      return this;
    }

    public PerClientFlags.PerClientFlagsBuilder autoCtrlsDisable(BoolCtrl... maskEnums) {
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

    public PerClientFlags.PerClientFlagsBuilder autoCtrlsValuesEnable(BoolCtrl... maskEnums) {
      for (BoolCtrl m : maskEnums) {
        autoCtrlsValues((int) m.enableFor(autoCtrlsValues));
      }
      return this;
    }

    public PerClientFlags.PerClientFlagsBuilder autoCtrlsValuesDisable(BoolCtrl... maskEnums) {
      for (BoolCtrl m : maskEnums) {
        autoCtrlsValues((int) m.disableFor(autoCtrlsValues));
      }
      return this;
    }

    public int getSize() {
      return 28;
    }

    @java.lang.SuppressWarnings("all")
    PerClientFlagsBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PerClientFlags.PerClientFlagsBuilder deviceSpec(final short deviceSpec) {
      this.deviceSpec = deviceSpec;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PerClientFlags.PerClientFlagsBuilder change(final int change) {
      this.change = change;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PerClientFlags.PerClientFlagsBuilder value(final int value) {
      this.value = value;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PerClientFlags.PerClientFlagsBuilder ctrlsToChange(final int ctrlsToChange) {
      this.ctrlsToChange = ctrlsToChange;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PerClientFlags.PerClientFlagsBuilder autoCtrls(final int autoCtrls) {
      this.autoCtrls = autoCtrls;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PerClientFlags.PerClientFlagsBuilder autoCtrlsValues(final int autoCtrlsValues) {
      this.autoCtrlsValues = autoCtrlsValues;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public PerClientFlags build() {
      return new PerClientFlags(this.deviceSpec, this.change, this.value, this.ctrlsToChange, this.autoCtrls, this.autoCtrlsValues);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "PerClientFlags.PerClientFlagsBuilder(deviceSpec=" + this.deviceSpec + ", change=" + this.change + ", value=" + this.value + ", ctrlsToChange=" + this.ctrlsToChange + ", autoCtrls=" + this.autoCtrls + ", autoCtrlsValues=" + this.autoCtrlsValues + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  PerClientFlags(final short deviceSpec, final int change, final int value, final int ctrlsToChange, final int autoCtrls, final int autoCtrlsValues) {
    this.deviceSpec = deviceSpec;
    this.change = change;
    this.value = value;
    this.ctrlsToChange = ctrlsToChange;
    this.autoCtrls = autoCtrls;
    this.autoCtrlsValues = autoCtrlsValues;
  }

  @java.lang.SuppressWarnings("all")
  public static PerClientFlags.PerClientFlagsBuilder builder() {
    return new PerClientFlags.PerClientFlagsBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getDeviceSpec() {
    return this.deviceSpec;
  }

  @java.lang.SuppressWarnings("all")
  public int getChange() {
    return this.change;
  }

  @java.lang.SuppressWarnings("all")
  public int getValue() {
    return this.value;
  }

  @java.lang.SuppressWarnings("all")
  public int getCtrlsToChange() {
    return this.ctrlsToChange;
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
    if (!(o instanceof PerClientFlags)) return false;
    final PerClientFlags other = (PerClientFlags) o;
    if (this.getDeviceSpec() != other.getDeviceSpec()) return false;
    if (this.getChange() != other.getChange()) return false;
    if (this.getValue() != other.getValue()) return false;
    if (this.getCtrlsToChange() != other.getCtrlsToChange()) return false;
    if (this.getAutoCtrls() != other.getAutoCtrls()) return false;
    if (this.getAutoCtrlsValues() != other.getAutoCtrlsValues()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDeviceSpec();
    result = result * PRIME + this.getChange();
    result = result * PRIME + this.getValue();
    result = result * PRIME + this.getCtrlsToChange();
    result = result * PRIME + this.getAutoCtrls();
    result = result * PRIME + this.getAutoCtrlsValues();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "PerClientFlags(deviceSpec=" + this.getDeviceSpec() + ", change=" + this.getChange() + ", value=" + this.getValue() + ", ctrlsToChange=" + this.getCtrlsToChange() + ", autoCtrls=" + this.getAutoCtrls() + ", autoCtrlsValues=" + this.getAutoCtrlsValues() + ")";
  }
}
