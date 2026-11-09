package com.github.moaxcp.x11.protocol.xkb;

import java.util.ArrayList;
import java.util.List;
import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;

public final class SetDeviceInfo implements OneWayRequest {
  public static final String PLUGIN_NAME = "xkb";
  public static final byte OPCODE = 25;
  private final short deviceSpec;
  private final byte firstBtn;
  private final short change;
  private final List<ActionUnion> btnActions;
  private final List<DeviceLedInfo> leds;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetDeviceInfo readSetDeviceInfo(X11Input in) throws IOException {
    SetDeviceInfo.SetDeviceInfoBuilder javaBuilder = SetDeviceInfo.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short deviceSpec = in.readCard16();
    byte firstBtn = in.readCard8();
    byte nBtns = in.readCard8();
    short change = in.readCard16();
    short nDeviceLedFBs = in.readCard16();
    List<ActionUnion> btnActions = new ArrayList<>(Byte.toUnsignedInt(nBtns));
    for (int i = 0; i < Byte.toUnsignedInt(nBtns); i++) {
      btnActions.add(ActionUnion.readActionUnion(in));
    }
    List<DeviceLedInfo> leds = new ArrayList<>(Short.toUnsignedInt(nDeviceLedFBs));
    for (int i = 0; i < Short.toUnsignedInt(nDeviceLedFBs); i++) {
      leds.add(DeviceLedInfo.readDeviceLedInfo(in));
    }
    javaBuilder.deviceSpec(deviceSpec);
    javaBuilder.firstBtn(firstBtn);
    javaBuilder.change(change);
    javaBuilder.btnActions(List.copyOf(btnActions));
    javaBuilder.leds(List.copyOf(leds));
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(deviceSpec);
    out.writeCard8(firstBtn);
    byte nBtns = (byte) btnActions.size();
    out.writeCard8(nBtns);
    out.writeCard16(change);
    short nDeviceLedFBs = (short) leds.size();
    out.writeCard16(nDeviceLedFBs);
    for (ActionUnion t : btnActions) {
      t.write(out);
    }
    for (DeviceLedInfo t : leds) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  public boolean isChangeEnabled(XIFeature... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (XIFeature m : maskEnums) {
      if (!m.isEnabled(change)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 12 + XObject.sizeOf(btnActions) + XObject.sizeOf(leds);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetDeviceInfoBuilder {
    @java.lang.SuppressWarnings("all")
    private short deviceSpec;
    @java.lang.SuppressWarnings("all")
    private byte firstBtn;
    @java.lang.SuppressWarnings("all")
    private short change;
    @java.lang.SuppressWarnings("all")
    private List<ActionUnion> btnActions;
    @java.lang.SuppressWarnings("all")
    private List<DeviceLedInfo> leds;

    public boolean isChangeEnabled(XIFeature... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (XIFeature m : maskEnums) {
        if (!m.isEnabled(change)) {
          return false;
        }
      }
      return true;
    }

    public SetDeviceInfo.SetDeviceInfoBuilder changeEnable(XIFeature... maskEnums) {
      for (XIFeature m : maskEnums) {
        change((short) m.enableFor(change));
      }
      return this;
    }

    public SetDeviceInfo.SetDeviceInfoBuilder changeDisable(XIFeature... maskEnums) {
      for (XIFeature m : maskEnums) {
        change((short) m.disableFor(change));
      }
      return this;
    }

    public int getSize() {
      return 12 + XObject.sizeOf(btnActions) + XObject.sizeOf(leds);
    }

    @java.lang.SuppressWarnings("all")
    SetDeviceInfoBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetDeviceInfo.SetDeviceInfoBuilder deviceSpec(final short deviceSpec) {
      this.deviceSpec = deviceSpec;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetDeviceInfo.SetDeviceInfoBuilder firstBtn(final byte firstBtn) {
      this.firstBtn = firstBtn;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetDeviceInfo.SetDeviceInfoBuilder change(final short change) {
      this.change = change;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetDeviceInfo.SetDeviceInfoBuilder btnActions(final List<ActionUnion> btnActions) {
      if (btnActions == null) {
        throw new java.lang.NullPointerException("btnActions is marked non-null but is null");
      }
      this.btnActions = btnActions;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetDeviceInfo.SetDeviceInfoBuilder leds(final List<DeviceLedInfo> leds) {
      if (leds == null) {
        throw new java.lang.NullPointerException("leds is marked non-null but is null");
      }
      this.leds = leds;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetDeviceInfo build() {
      return new SetDeviceInfo(this.deviceSpec, this.firstBtn, this.change, this.btnActions, this.leds);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetDeviceInfo.SetDeviceInfoBuilder(deviceSpec=" + this.deviceSpec + ", firstBtn=" + this.firstBtn + ", change=" + this.change + ", btnActions=" + this.btnActions + ", leds=" + this.leds + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetDeviceInfo(final short deviceSpec, final byte firstBtn, final short change, final List<ActionUnion> btnActions, final List<DeviceLedInfo> leds) {
    if (btnActions == null) {
      throw new java.lang.NullPointerException("btnActions is marked non-null but is null");
    }
    if (leds == null) {
      throw new java.lang.NullPointerException("leds is marked non-null but is null");
    }
    this.deviceSpec = deviceSpec;
    this.firstBtn = firstBtn;
    this.change = change;
    this.btnActions = btnActions;
    this.leds = leds;
  }

  @java.lang.SuppressWarnings("all")
  public static SetDeviceInfo.SetDeviceInfoBuilder builder() {
    return new SetDeviceInfo.SetDeviceInfoBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getDeviceSpec() {
    return this.deviceSpec;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFirstBtn() {
    return this.firstBtn;
  }

  @java.lang.SuppressWarnings("all")
  public short getChange() {
    return this.change;
  }

  @java.lang.SuppressWarnings("all")
  public List<ActionUnion> getBtnActions() {
    return this.btnActions;
  }

  @java.lang.SuppressWarnings("all")
  public List<DeviceLedInfo> getLeds() {
    return this.leds;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetDeviceInfo)) return false;
    final SetDeviceInfo other = (SetDeviceInfo) o;
    if (this.getDeviceSpec() != other.getDeviceSpec()) return false;
    if (this.getFirstBtn() != other.getFirstBtn()) return false;
    if (this.getChange() != other.getChange()) return false;
    final java.lang.Object this$btnActions = this.getBtnActions();
    final java.lang.Object other$btnActions = other.getBtnActions();
    if (this$btnActions == null ? other$btnActions != null : !this$btnActions.equals(other$btnActions)) return false;
    final java.lang.Object this$leds = this.getLeds();
    final java.lang.Object other$leds = other.getLeds();
    if (this$leds == null ? other$leds != null : !this$leds.equals(other$leds)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDeviceSpec();
    result = result * PRIME + this.getFirstBtn();
    result = result * PRIME + this.getChange();
    final java.lang.Object $btnActions = this.getBtnActions();
    result = result * PRIME + ($btnActions == null ? 43 : $btnActions.hashCode());
    final java.lang.Object $leds = this.getLeds();
    result = result * PRIME + ($leds == null ? 43 : $leds.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetDeviceInfo(deviceSpec=" + this.getDeviceSpec() + ", firstBtn=" + this.getFirstBtn() + ", change=" + this.getChange() + ", btnActions=" + this.getBtnActions() + ", leds=" + this.getLeds() + ")";
  }
}
