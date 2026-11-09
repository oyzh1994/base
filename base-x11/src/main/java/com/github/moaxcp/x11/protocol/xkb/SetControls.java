package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.xproto.ModMask;
import java.io.IOException;

public final class SetControls implements OneWayRequest {
  public static final String PLUGIN_NAME = "xkb";
  public static final byte OPCODE = 7;
  private final short deviceSpec;
  private final byte affectInternalRealMods;
  private final byte internalRealMods;
  private final byte affectIgnoreLockRealMods;
  private final byte ignoreLockRealMods;
  private final short affectInternalVirtualMods;
  private final short internalVirtualMods;
  private final short affectIgnoreLockVirtualMods;
  private final short ignoreLockVirtualMods;
  private final byte mouseKeysDfltBtn;
  private final byte groupsWrap;
  private final short accessXOptions;
  private final int affectEnabledControls;
  private final int enabledControls;
  private final int changeControls;
  private final short repeatDelay;
  private final short repeatInterval;
  private final short slowKeysDelay;
  private final short debounceDelay;
  private final short mouseKeysDelay;
  private final short mouseKeysInterval;
  private final short mouseKeysTimeToMax;
  private final short mouseKeysMaxSpeed;
  private final short mouseKeysCurve;
  private final short accessXTimeout;
  private final int accessXTimeoutMask;
  private final int accessXTimeoutValues;
  private final short accessXTimeoutOptionsMask;
  private final short accessXTimeoutOptionsValues;
  private final byte[] perKeyRepeat;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetControls readSetControls(X11Input in) throws IOException {
    SetControls.SetControlsBuilder javaBuilder = SetControls.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short deviceSpec = in.readCard16();
    byte affectInternalRealMods = in.readCard8();
    byte internalRealMods = in.readCard8();
    byte affectIgnoreLockRealMods = in.readCard8();
    byte ignoreLockRealMods = in.readCard8();
    short affectInternalVirtualMods = in.readCard16();
    short internalVirtualMods = in.readCard16();
    short affectIgnoreLockVirtualMods = in.readCard16();
    short ignoreLockVirtualMods = in.readCard16();
    byte mouseKeysDfltBtn = in.readCard8();
    byte groupsWrap = in.readCard8();
    short accessXOptions = in.readCard16();
    byte[] pad15 = in.readPad(2);
    int affectEnabledControls = in.readCard32();
    int enabledControls = in.readCard32();
    int changeControls = in.readCard32();
    short repeatDelay = in.readCard16();
    short repeatInterval = in.readCard16();
    short slowKeysDelay = in.readCard16();
    short debounceDelay = in.readCard16();
    short mouseKeysDelay = in.readCard16();
    short mouseKeysInterval = in.readCard16();
    short mouseKeysTimeToMax = in.readCard16();
    short mouseKeysMaxSpeed = in.readCard16();
    short mouseKeysCurve = in.readInt16();
    short accessXTimeout = in.readCard16();
    int accessXTimeoutMask = in.readCard32();
    int accessXTimeoutValues = in.readCard32();
    short accessXTimeoutOptionsMask = in.readCard16();
    short accessXTimeoutOptionsValues = in.readCard16();
    byte[] perKeyRepeat = in.readCard8(32);
    javaBuilder.deviceSpec(deviceSpec);
    javaBuilder.affectInternalRealMods(affectInternalRealMods);
    javaBuilder.internalRealMods(internalRealMods);
    javaBuilder.affectIgnoreLockRealMods(affectIgnoreLockRealMods);
    javaBuilder.ignoreLockRealMods(ignoreLockRealMods);
    javaBuilder.affectInternalVirtualMods(affectInternalVirtualMods);
    javaBuilder.internalVirtualMods(internalVirtualMods);
    javaBuilder.affectIgnoreLockVirtualMods(affectIgnoreLockVirtualMods);
    javaBuilder.ignoreLockVirtualMods(ignoreLockVirtualMods);
    javaBuilder.mouseKeysDfltBtn(mouseKeysDfltBtn);
    javaBuilder.groupsWrap(groupsWrap);
    javaBuilder.accessXOptions(accessXOptions);
    javaBuilder.affectEnabledControls(affectEnabledControls);
    javaBuilder.enabledControls(enabledControls);
    javaBuilder.changeControls(changeControls);
    javaBuilder.repeatDelay(repeatDelay);
    javaBuilder.repeatInterval(repeatInterval);
    javaBuilder.slowKeysDelay(slowKeysDelay);
    javaBuilder.debounceDelay(debounceDelay);
    javaBuilder.mouseKeysDelay(mouseKeysDelay);
    javaBuilder.mouseKeysInterval(mouseKeysInterval);
    javaBuilder.mouseKeysTimeToMax(mouseKeysTimeToMax);
    javaBuilder.mouseKeysMaxSpeed(mouseKeysMaxSpeed);
    javaBuilder.mouseKeysCurve(mouseKeysCurve);
    javaBuilder.accessXTimeout(accessXTimeout);
    javaBuilder.accessXTimeoutMask(accessXTimeoutMask);
    javaBuilder.accessXTimeoutValues(accessXTimeoutValues);
    javaBuilder.accessXTimeoutOptionsMask(accessXTimeoutOptionsMask);
    javaBuilder.accessXTimeoutOptionsValues(accessXTimeoutOptionsValues);
    javaBuilder.perKeyRepeat(perKeyRepeat);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(deviceSpec);
    out.writeCard8(affectInternalRealMods);
    out.writeCard8(internalRealMods);
    out.writeCard8(affectIgnoreLockRealMods);
    out.writeCard8(ignoreLockRealMods);
    out.writeCard16(affectInternalVirtualMods);
    out.writeCard16(internalVirtualMods);
    out.writeCard16(affectIgnoreLockVirtualMods);
    out.writeCard16(ignoreLockVirtualMods);
    out.writeCard8(mouseKeysDfltBtn);
    out.writeCard8(groupsWrap);
    out.writeCard16(accessXOptions);
    out.writePad(2);
    out.writeCard32(affectEnabledControls);
    out.writeCard32(enabledControls);
    out.writeCard32(changeControls);
    out.writeCard16(repeatDelay);
    out.writeCard16(repeatInterval);
    out.writeCard16(slowKeysDelay);
    out.writeCard16(debounceDelay);
    out.writeCard16(mouseKeysDelay);
    out.writeCard16(mouseKeysInterval);
    out.writeCard16(mouseKeysTimeToMax);
    out.writeCard16(mouseKeysMaxSpeed);
    out.writeInt16(mouseKeysCurve);
    out.writeCard16(accessXTimeout);
    out.writeCard32(accessXTimeoutMask);
    out.writeCard32(accessXTimeoutValues);
    out.writeCard16(accessXTimeoutOptionsMask);
    out.writeCard16(accessXTimeoutOptionsValues);
    out.writeCard8(perKeyRepeat);
    out.writePadAlign(getSize());
  }

  public boolean isAffectInternalRealModsEnabled(ModMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (ModMask m : maskEnums) {
      if (!m.isEnabled(affectInternalRealMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isInternalRealModsEnabled(ModMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (ModMask m : maskEnums) {
      if (!m.isEnabled(internalRealMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAffectIgnoreLockRealModsEnabled(ModMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (ModMask m : maskEnums) {
      if (!m.isEnabled(affectIgnoreLockRealMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isIgnoreLockRealModsEnabled(ModMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (ModMask m : maskEnums) {
      if (!m.isEnabled(ignoreLockRealMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAffectInternalVirtualModsEnabled(VMod... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (VMod m : maskEnums) {
      if (!m.isEnabled(affectInternalVirtualMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isInternalVirtualModsEnabled(VMod... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (VMod m : maskEnums) {
      if (!m.isEnabled(internalVirtualMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAffectIgnoreLockVirtualModsEnabled(VMod... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (VMod m : maskEnums) {
      if (!m.isEnabled(affectIgnoreLockVirtualMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isIgnoreLockVirtualModsEnabled(VMod... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (VMod m : maskEnums) {
      if (!m.isEnabled(ignoreLockVirtualMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAccessXOptionsEnabled(AXOption... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (AXOption m : maskEnums) {
      if (!m.isEnabled(accessXOptions)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAffectEnabledControlsEnabled(BoolCtrl... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (BoolCtrl m : maskEnums) {
      if (!m.isEnabled(affectEnabledControls)) {
        return false;
      }
    }
    return true;
  }

  public boolean isEnabledControlsEnabled(BoolCtrl... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (BoolCtrl m : maskEnums) {
      if (!m.isEnabled(enabledControls)) {
        return false;
      }
    }
    return true;
  }

  public boolean isChangeControlsEnabled(Control... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (Control m : maskEnums) {
      if (!m.isEnabled(changeControls)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAccessXTimeoutMaskEnabled(BoolCtrl... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (BoolCtrl m : maskEnums) {
      if (!m.isEnabled(accessXTimeoutMask)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAccessXTimeoutValuesEnabled(BoolCtrl... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (BoolCtrl m : maskEnums) {
      if (!m.isEnabled(accessXTimeoutValues)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAccessXTimeoutOptionsMaskEnabled(AXOption... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (AXOption m : maskEnums) {
      if (!m.isEnabled(accessXTimeoutOptionsMask)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAccessXTimeoutOptionsValuesEnabled(AXOption... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (AXOption m : maskEnums) {
      if (!m.isEnabled(accessXTimeoutOptionsValues)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 68 + 1 * perKeyRepeat.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetControlsBuilder {
    @java.lang.SuppressWarnings("all")
    private short deviceSpec;
    @java.lang.SuppressWarnings("all")
    private byte affectInternalRealMods;
    @java.lang.SuppressWarnings("all")
    private byte internalRealMods;
    @java.lang.SuppressWarnings("all")
    private byte affectIgnoreLockRealMods;
    @java.lang.SuppressWarnings("all")
    private byte ignoreLockRealMods;
    @java.lang.SuppressWarnings("all")
    private short affectInternalVirtualMods;
    @java.lang.SuppressWarnings("all")
    private short internalVirtualMods;
    @java.lang.SuppressWarnings("all")
    private short affectIgnoreLockVirtualMods;
    @java.lang.SuppressWarnings("all")
    private short ignoreLockVirtualMods;
    @java.lang.SuppressWarnings("all")
    private byte mouseKeysDfltBtn;
    @java.lang.SuppressWarnings("all")
    private byte groupsWrap;
    @java.lang.SuppressWarnings("all")
    private short accessXOptions;
    @java.lang.SuppressWarnings("all")
    private int affectEnabledControls;
    @java.lang.SuppressWarnings("all")
    private int enabledControls;
    @java.lang.SuppressWarnings("all")
    private int changeControls;
    @java.lang.SuppressWarnings("all")
    private short repeatDelay;
    @java.lang.SuppressWarnings("all")
    private short repeatInterval;
    @java.lang.SuppressWarnings("all")
    private short slowKeysDelay;
    @java.lang.SuppressWarnings("all")
    private short debounceDelay;
    @java.lang.SuppressWarnings("all")
    private short mouseKeysDelay;
    @java.lang.SuppressWarnings("all")
    private short mouseKeysInterval;
    @java.lang.SuppressWarnings("all")
    private short mouseKeysTimeToMax;
    @java.lang.SuppressWarnings("all")
    private short mouseKeysMaxSpeed;
    @java.lang.SuppressWarnings("all")
    private short mouseKeysCurve;
    @java.lang.SuppressWarnings("all")
    private short accessXTimeout;
    @java.lang.SuppressWarnings("all")
    private int accessXTimeoutMask;
    @java.lang.SuppressWarnings("all")
    private int accessXTimeoutValues;
    @java.lang.SuppressWarnings("all")
    private short accessXTimeoutOptionsMask;
    @java.lang.SuppressWarnings("all")
    private short accessXTimeoutOptionsValues;
    @java.lang.SuppressWarnings("all")
    private byte[] perKeyRepeat;

    public boolean isAffectInternalRealModsEnabled(ModMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (ModMask m : maskEnums) {
        if (!m.isEnabled(affectInternalRealMods)) {
          return false;
        }
      }
      return true;
    }

    public SetControls.SetControlsBuilder affectInternalRealModsEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        affectInternalRealMods((byte) m.enableFor(affectInternalRealMods));
      }
      return this;
    }

    public SetControls.SetControlsBuilder affectInternalRealModsDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        affectInternalRealMods((byte) m.disableFor(affectInternalRealMods));
      }
      return this;
    }

    public boolean isInternalRealModsEnabled(ModMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (ModMask m : maskEnums) {
        if (!m.isEnabled(internalRealMods)) {
          return false;
        }
      }
      return true;
    }

    public SetControls.SetControlsBuilder internalRealModsEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        internalRealMods((byte) m.enableFor(internalRealMods));
      }
      return this;
    }

    public SetControls.SetControlsBuilder internalRealModsDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        internalRealMods((byte) m.disableFor(internalRealMods));
      }
      return this;
    }

    public boolean isAffectIgnoreLockRealModsEnabled(ModMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (ModMask m : maskEnums) {
        if (!m.isEnabled(affectIgnoreLockRealMods)) {
          return false;
        }
      }
      return true;
    }

    public SetControls.SetControlsBuilder affectIgnoreLockRealModsEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        affectIgnoreLockRealMods((byte) m.enableFor(affectIgnoreLockRealMods));
      }
      return this;
    }

    public SetControls.SetControlsBuilder affectIgnoreLockRealModsDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        affectIgnoreLockRealMods((byte) m.disableFor(affectIgnoreLockRealMods));
      }
      return this;
    }

    public boolean isIgnoreLockRealModsEnabled(ModMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (ModMask m : maskEnums) {
        if (!m.isEnabled(ignoreLockRealMods)) {
          return false;
        }
      }
      return true;
    }

    public SetControls.SetControlsBuilder ignoreLockRealModsEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        ignoreLockRealMods((byte) m.enableFor(ignoreLockRealMods));
      }
      return this;
    }

    public SetControls.SetControlsBuilder ignoreLockRealModsDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        ignoreLockRealMods((byte) m.disableFor(ignoreLockRealMods));
      }
      return this;
    }

    public boolean isAffectInternalVirtualModsEnabled(VMod... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (VMod m : maskEnums) {
        if (!m.isEnabled(affectInternalVirtualMods)) {
          return false;
        }
      }
      return true;
    }

    public SetControls.SetControlsBuilder affectInternalVirtualModsEnable(VMod... maskEnums) {
      for (VMod m : maskEnums) {
        affectInternalVirtualMods((short) m.enableFor(affectInternalVirtualMods));
      }
      return this;
    }

    public SetControls.SetControlsBuilder affectInternalVirtualModsDisable(VMod... maskEnums) {
      for (VMod m : maskEnums) {
        affectInternalVirtualMods((short) m.disableFor(affectInternalVirtualMods));
      }
      return this;
    }

    public boolean isInternalVirtualModsEnabled(VMod... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (VMod m : maskEnums) {
        if (!m.isEnabled(internalVirtualMods)) {
          return false;
        }
      }
      return true;
    }

    public SetControls.SetControlsBuilder internalVirtualModsEnable(VMod... maskEnums) {
      for (VMod m : maskEnums) {
        internalVirtualMods((short) m.enableFor(internalVirtualMods));
      }
      return this;
    }

    public SetControls.SetControlsBuilder internalVirtualModsDisable(VMod... maskEnums) {
      for (VMod m : maskEnums) {
        internalVirtualMods((short) m.disableFor(internalVirtualMods));
      }
      return this;
    }

    public boolean isAffectIgnoreLockVirtualModsEnabled(VMod... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (VMod m : maskEnums) {
        if (!m.isEnabled(affectIgnoreLockVirtualMods)) {
          return false;
        }
      }
      return true;
    }

    public SetControls.SetControlsBuilder affectIgnoreLockVirtualModsEnable(VMod... maskEnums) {
      for (VMod m : maskEnums) {
        affectIgnoreLockVirtualMods((short) m.enableFor(affectIgnoreLockVirtualMods));
      }
      return this;
    }

    public SetControls.SetControlsBuilder affectIgnoreLockVirtualModsDisable(VMod... maskEnums) {
      for (VMod m : maskEnums) {
        affectIgnoreLockVirtualMods((short) m.disableFor(affectIgnoreLockVirtualMods));
      }
      return this;
    }

    public boolean isIgnoreLockVirtualModsEnabled(VMod... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (VMod m : maskEnums) {
        if (!m.isEnabled(ignoreLockVirtualMods)) {
          return false;
        }
      }
      return true;
    }

    public SetControls.SetControlsBuilder ignoreLockVirtualModsEnable(VMod... maskEnums) {
      for (VMod m : maskEnums) {
        ignoreLockVirtualMods((short) m.enableFor(ignoreLockVirtualMods));
      }
      return this;
    }

    public SetControls.SetControlsBuilder ignoreLockVirtualModsDisable(VMod... maskEnums) {
      for (VMod m : maskEnums) {
        ignoreLockVirtualMods((short) m.disableFor(ignoreLockVirtualMods));
      }
      return this;
    }

    public boolean isAccessXOptionsEnabled(AXOption... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (AXOption m : maskEnums) {
        if (!m.isEnabled(accessXOptions)) {
          return false;
        }
      }
      return true;
    }

    public SetControls.SetControlsBuilder accessXOptionsEnable(AXOption... maskEnums) {
      for (AXOption m : maskEnums) {
        accessXOptions((short) m.enableFor(accessXOptions));
      }
      return this;
    }

    public SetControls.SetControlsBuilder accessXOptionsDisable(AXOption... maskEnums) {
      for (AXOption m : maskEnums) {
        accessXOptions((short) m.disableFor(accessXOptions));
      }
      return this;
    }

    public boolean isAffectEnabledControlsEnabled(BoolCtrl... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (BoolCtrl m : maskEnums) {
        if (!m.isEnabled(affectEnabledControls)) {
          return false;
        }
      }
      return true;
    }

    public SetControls.SetControlsBuilder affectEnabledControlsEnable(BoolCtrl... maskEnums) {
      for (BoolCtrl m : maskEnums) {
        affectEnabledControls((int) m.enableFor(affectEnabledControls));
      }
      return this;
    }

    public SetControls.SetControlsBuilder affectEnabledControlsDisable(BoolCtrl... maskEnums) {
      for (BoolCtrl m : maskEnums) {
        affectEnabledControls((int) m.disableFor(affectEnabledControls));
      }
      return this;
    }

    public boolean isEnabledControlsEnabled(BoolCtrl... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (BoolCtrl m : maskEnums) {
        if (!m.isEnabled(enabledControls)) {
          return false;
        }
      }
      return true;
    }

    public SetControls.SetControlsBuilder enabledControlsEnable(BoolCtrl... maskEnums) {
      for (BoolCtrl m : maskEnums) {
        enabledControls((int) m.enableFor(enabledControls));
      }
      return this;
    }

    public SetControls.SetControlsBuilder enabledControlsDisable(BoolCtrl... maskEnums) {
      for (BoolCtrl m : maskEnums) {
        enabledControls((int) m.disableFor(enabledControls));
      }
      return this;
    }

    public boolean isChangeControlsEnabled(Control... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (Control m : maskEnums) {
        if (!m.isEnabled(changeControls)) {
          return false;
        }
      }
      return true;
    }

    public SetControls.SetControlsBuilder changeControlsEnable(Control... maskEnums) {
      for (Control m : maskEnums) {
        changeControls((int) m.enableFor(changeControls));
      }
      return this;
    }

    public SetControls.SetControlsBuilder changeControlsDisable(Control... maskEnums) {
      for (Control m : maskEnums) {
        changeControls((int) m.disableFor(changeControls));
      }
      return this;
    }

    public boolean isAccessXTimeoutMaskEnabled(BoolCtrl... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (BoolCtrl m : maskEnums) {
        if (!m.isEnabled(accessXTimeoutMask)) {
          return false;
        }
      }
      return true;
    }

    public SetControls.SetControlsBuilder accessXTimeoutMaskEnable(BoolCtrl... maskEnums) {
      for (BoolCtrl m : maskEnums) {
        accessXTimeoutMask((int) m.enableFor(accessXTimeoutMask));
      }
      return this;
    }

    public SetControls.SetControlsBuilder accessXTimeoutMaskDisable(BoolCtrl... maskEnums) {
      for (BoolCtrl m : maskEnums) {
        accessXTimeoutMask((int) m.disableFor(accessXTimeoutMask));
      }
      return this;
    }

    public boolean isAccessXTimeoutValuesEnabled(BoolCtrl... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (BoolCtrl m : maskEnums) {
        if (!m.isEnabled(accessXTimeoutValues)) {
          return false;
        }
      }
      return true;
    }

    public SetControls.SetControlsBuilder accessXTimeoutValuesEnable(BoolCtrl... maskEnums) {
      for (BoolCtrl m : maskEnums) {
        accessXTimeoutValues((int) m.enableFor(accessXTimeoutValues));
      }
      return this;
    }

    public SetControls.SetControlsBuilder accessXTimeoutValuesDisable(BoolCtrl... maskEnums) {
      for (BoolCtrl m : maskEnums) {
        accessXTimeoutValues((int) m.disableFor(accessXTimeoutValues));
      }
      return this;
    }

    public boolean isAccessXTimeoutOptionsMaskEnabled(AXOption... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (AXOption m : maskEnums) {
        if (!m.isEnabled(accessXTimeoutOptionsMask)) {
          return false;
        }
      }
      return true;
    }

    public SetControls.SetControlsBuilder accessXTimeoutOptionsMaskEnable(AXOption... maskEnums) {
      for (AXOption m : maskEnums) {
        accessXTimeoutOptionsMask((short) m.enableFor(accessXTimeoutOptionsMask));
      }
      return this;
    }

    public SetControls.SetControlsBuilder accessXTimeoutOptionsMaskDisable(AXOption... maskEnums) {
      for (AXOption m : maskEnums) {
        accessXTimeoutOptionsMask((short) m.disableFor(accessXTimeoutOptionsMask));
      }
      return this;
    }

    public boolean isAccessXTimeoutOptionsValuesEnabled(AXOption... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (AXOption m : maskEnums) {
        if (!m.isEnabled(accessXTimeoutOptionsValues)) {
          return false;
        }
      }
      return true;
    }

    public SetControls.SetControlsBuilder accessXTimeoutOptionsValuesEnable(AXOption... maskEnums) {
      for (AXOption m : maskEnums) {
        accessXTimeoutOptionsValues((short) m.enableFor(accessXTimeoutOptionsValues));
      }
      return this;
    }

    public SetControls.SetControlsBuilder accessXTimeoutOptionsValuesDisable(AXOption... maskEnums) {
      for (AXOption m : maskEnums) {
        accessXTimeoutOptionsValues((short) m.disableFor(accessXTimeoutOptionsValues));
      }
      return this;
    }

    public int getSize() {
      return 68 + 1 * perKeyRepeat.length;
    }

    @java.lang.SuppressWarnings("all")
    SetControlsBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetControls.SetControlsBuilder deviceSpec(final short deviceSpec) {
      this.deviceSpec = deviceSpec;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetControls.SetControlsBuilder affectInternalRealMods(final byte affectInternalRealMods) {
      this.affectInternalRealMods = affectInternalRealMods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetControls.SetControlsBuilder internalRealMods(final byte internalRealMods) {
      this.internalRealMods = internalRealMods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetControls.SetControlsBuilder affectIgnoreLockRealMods(final byte affectIgnoreLockRealMods) {
      this.affectIgnoreLockRealMods = affectIgnoreLockRealMods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetControls.SetControlsBuilder ignoreLockRealMods(final byte ignoreLockRealMods) {
      this.ignoreLockRealMods = ignoreLockRealMods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetControls.SetControlsBuilder affectInternalVirtualMods(final short affectInternalVirtualMods) {
      this.affectInternalVirtualMods = affectInternalVirtualMods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetControls.SetControlsBuilder internalVirtualMods(final short internalVirtualMods) {
      this.internalVirtualMods = internalVirtualMods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetControls.SetControlsBuilder affectIgnoreLockVirtualMods(final short affectIgnoreLockVirtualMods) {
      this.affectIgnoreLockVirtualMods = affectIgnoreLockVirtualMods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetControls.SetControlsBuilder ignoreLockVirtualMods(final short ignoreLockVirtualMods) {
      this.ignoreLockVirtualMods = ignoreLockVirtualMods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetControls.SetControlsBuilder mouseKeysDfltBtn(final byte mouseKeysDfltBtn) {
      this.mouseKeysDfltBtn = mouseKeysDfltBtn;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetControls.SetControlsBuilder groupsWrap(final byte groupsWrap) {
      this.groupsWrap = groupsWrap;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetControls.SetControlsBuilder accessXOptions(final short accessXOptions) {
      this.accessXOptions = accessXOptions;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetControls.SetControlsBuilder affectEnabledControls(final int affectEnabledControls) {
      this.affectEnabledControls = affectEnabledControls;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetControls.SetControlsBuilder enabledControls(final int enabledControls) {
      this.enabledControls = enabledControls;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetControls.SetControlsBuilder changeControls(final int changeControls) {
      this.changeControls = changeControls;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetControls.SetControlsBuilder repeatDelay(final short repeatDelay) {
      this.repeatDelay = repeatDelay;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetControls.SetControlsBuilder repeatInterval(final short repeatInterval) {
      this.repeatInterval = repeatInterval;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetControls.SetControlsBuilder slowKeysDelay(final short slowKeysDelay) {
      this.slowKeysDelay = slowKeysDelay;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetControls.SetControlsBuilder debounceDelay(final short debounceDelay) {
      this.debounceDelay = debounceDelay;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetControls.SetControlsBuilder mouseKeysDelay(final short mouseKeysDelay) {
      this.mouseKeysDelay = mouseKeysDelay;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetControls.SetControlsBuilder mouseKeysInterval(final short mouseKeysInterval) {
      this.mouseKeysInterval = mouseKeysInterval;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetControls.SetControlsBuilder mouseKeysTimeToMax(final short mouseKeysTimeToMax) {
      this.mouseKeysTimeToMax = mouseKeysTimeToMax;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetControls.SetControlsBuilder mouseKeysMaxSpeed(final short mouseKeysMaxSpeed) {
      this.mouseKeysMaxSpeed = mouseKeysMaxSpeed;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetControls.SetControlsBuilder mouseKeysCurve(final short mouseKeysCurve) {
      this.mouseKeysCurve = mouseKeysCurve;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetControls.SetControlsBuilder accessXTimeout(final short accessXTimeout) {
      this.accessXTimeout = accessXTimeout;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetControls.SetControlsBuilder accessXTimeoutMask(final int accessXTimeoutMask) {
      this.accessXTimeoutMask = accessXTimeoutMask;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetControls.SetControlsBuilder accessXTimeoutValues(final int accessXTimeoutValues) {
      this.accessXTimeoutValues = accessXTimeoutValues;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetControls.SetControlsBuilder accessXTimeoutOptionsMask(final short accessXTimeoutOptionsMask) {
      this.accessXTimeoutOptionsMask = accessXTimeoutOptionsMask;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetControls.SetControlsBuilder accessXTimeoutOptionsValues(final short accessXTimeoutOptionsValues) {
      this.accessXTimeoutOptionsValues = accessXTimeoutOptionsValues;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetControls.SetControlsBuilder perKeyRepeat(final byte[] perKeyRepeat) {
      if (perKeyRepeat == null) {
        throw new java.lang.NullPointerException("perKeyRepeat is marked non-null but is null");
      }
      this.perKeyRepeat = perKeyRepeat;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetControls build() {
      return new SetControls(this.deviceSpec, this.affectInternalRealMods, this.internalRealMods, this.affectIgnoreLockRealMods, this.ignoreLockRealMods, this.affectInternalVirtualMods, this.internalVirtualMods, this.affectIgnoreLockVirtualMods, this.ignoreLockVirtualMods, this.mouseKeysDfltBtn, this.groupsWrap, this.accessXOptions, this.affectEnabledControls, this.enabledControls, this.changeControls, this.repeatDelay, this.repeatInterval, this.slowKeysDelay, this.debounceDelay, this.mouseKeysDelay, this.mouseKeysInterval, this.mouseKeysTimeToMax, this.mouseKeysMaxSpeed, this.mouseKeysCurve, this.accessXTimeout, this.accessXTimeoutMask, this.accessXTimeoutValues, this.accessXTimeoutOptionsMask, this.accessXTimeoutOptionsValues, this.perKeyRepeat);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetControls.SetControlsBuilder(deviceSpec=" + this.deviceSpec + ", affectInternalRealMods=" + this.affectInternalRealMods + ", internalRealMods=" + this.internalRealMods + ", affectIgnoreLockRealMods=" + this.affectIgnoreLockRealMods + ", ignoreLockRealMods=" + this.ignoreLockRealMods + ", affectInternalVirtualMods=" + this.affectInternalVirtualMods + ", internalVirtualMods=" + this.internalVirtualMods + ", affectIgnoreLockVirtualMods=" + this.affectIgnoreLockVirtualMods + ", ignoreLockVirtualMods=" + this.ignoreLockVirtualMods + ", mouseKeysDfltBtn=" + this.mouseKeysDfltBtn + ", groupsWrap=" + this.groupsWrap + ", accessXOptions=" + this.accessXOptions + ", affectEnabledControls=" + this.affectEnabledControls + ", enabledControls=" + this.enabledControls + ", changeControls=" + this.changeControls + ", repeatDelay=" + this.repeatDelay + ", repeatInterval=" + this.repeatInterval + ", slowKeysDelay=" + this.slowKeysDelay + ", debounceDelay=" + this.debounceDelay + ", mouseKeysDelay=" + this.mouseKeysDelay + ", mouseKeysInterval=" + this.mouseKeysInterval + ", mouseKeysTimeToMax=" + this.mouseKeysTimeToMax + ", mouseKeysMaxSpeed=" + this.mouseKeysMaxSpeed + ", mouseKeysCurve=" + this.mouseKeysCurve + ", accessXTimeout=" + this.accessXTimeout + ", accessXTimeoutMask=" + this.accessXTimeoutMask + ", accessXTimeoutValues=" + this.accessXTimeoutValues + ", accessXTimeoutOptionsMask=" + this.accessXTimeoutOptionsMask + ", accessXTimeoutOptionsValues=" + this.accessXTimeoutOptionsValues + ", perKeyRepeat=" + java.util.Arrays.toString(this.perKeyRepeat) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetControls(final short deviceSpec, final byte affectInternalRealMods, final byte internalRealMods, final byte affectIgnoreLockRealMods, final byte ignoreLockRealMods, final short affectInternalVirtualMods, final short internalVirtualMods, final short affectIgnoreLockVirtualMods, final short ignoreLockVirtualMods, final byte mouseKeysDfltBtn, final byte groupsWrap, final short accessXOptions, final int affectEnabledControls, final int enabledControls, final int changeControls, final short repeatDelay, final short repeatInterval, final short slowKeysDelay, final short debounceDelay, final short mouseKeysDelay, final short mouseKeysInterval, final short mouseKeysTimeToMax, final short mouseKeysMaxSpeed, final short mouseKeysCurve, final short accessXTimeout, final int accessXTimeoutMask, final int accessXTimeoutValues, final short accessXTimeoutOptionsMask, final short accessXTimeoutOptionsValues, final byte[] perKeyRepeat) {
    if (perKeyRepeat == null) {
      throw new java.lang.NullPointerException("perKeyRepeat is marked non-null but is null");
    }
    this.deviceSpec = deviceSpec;
    this.affectInternalRealMods = affectInternalRealMods;
    this.internalRealMods = internalRealMods;
    this.affectIgnoreLockRealMods = affectIgnoreLockRealMods;
    this.ignoreLockRealMods = ignoreLockRealMods;
    this.affectInternalVirtualMods = affectInternalVirtualMods;
    this.internalVirtualMods = internalVirtualMods;
    this.affectIgnoreLockVirtualMods = affectIgnoreLockVirtualMods;
    this.ignoreLockVirtualMods = ignoreLockVirtualMods;
    this.mouseKeysDfltBtn = mouseKeysDfltBtn;
    this.groupsWrap = groupsWrap;
    this.accessXOptions = accessXOptions;
    this.affectEnabledControls = affectEnabledControls;
    this.enabledControls = enabledControls;
    this.changeControls = changeControls;
    this.repeatDelay = repeatDelay;
    this.repeatInterval = repeatInterval;
    this.slowKeysDelay = slowKeysDelay;
    this.debounceDelay = debounceDelay;
    this.mouseKeysDelay = mouseKeysDelay;
    this.mouseKeysInterval = mouseKeysInterval;
    this.mouseKeysTimeToMax = mouseKeysTimeToMax;
    this.mouseKeysMaxSpeed = mouseKeysMaxSpeed;
    this.mouseKeysCurve = mouseKeysCurve;
    this.accessXTimeout = accessXTimeout;
    this.accessXTimeoutMask = accessXTimeoutMask;
    this.accessXTimeoutValues = accessXTimeoutValues;
    this.accessXTimeoutOptionsMask = accessXTimeoutOptionsMask;
    this.accessXTimeoutOptionsValues = accessXTimeoutOptionsValues;
    this.perKeyRepeat = perKeyRepeat;
  }

  @java.lang.SuppressWarnings("all")
  public static SetControls.SetControlsBuilder builder() {
    return new SetControls.SetControlsBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getDeviceSpec() {
    return this.deviceSpec;
  }

  @java.lang.SuppressWarnings("all")
  public byte getAffectInternalRealMods() {
    return this.affectInternalRealMods;
  }

  @java.lang.SuppressWarnings("all")
  public byte getInternalRealMods() {
    return this.internalRealMods;
  }

  @java.lang.SuppressWarnings("all")
  public byte getAffectIgnoreLockRealMods() {
    return this.affectIgnoreLockRealMods;
  }

  @java.lang.SuppressWarnings("all")
  public byte getIgnoreLockRealMods() {
    return this.ignoreLockRealMods;
  }

  @java.lang.SuppressWarnings("all")
  public short getAffectInternalVirtualMods() {
    return this.affectInternalVirtualMods;
  }

  @java.lang.SuppressWarnings("all")
  public short getInternalVirtualMods() {
    return this.internalVirtualMods;
  }

  @java.lang.SuppressWarnings("all")
  public short getAffectIgnoreLockVirtualMods() {
    return this.affectIgnoreLockVirtualMods;
  }

  @java.lang.SuppressWarnings("all")
  public short getIgnoreLockVirtualMods() {
    return this.ignoreLockVirtualMods;
  }

  @java.lang.SuppressWarnings("all")
  public byte getMouseKeysDfltBtn() {
    return this.mouseKeysDfltBtn;
  }

  @java.lang.SuppressWarnings("all")
  public byte getGroupsWrap() {
    return this.groupsWrap;
  }

  @java.lang.SuppressWarnings("all")
  public short getAccessXOptions() {
    return this.accessXOptions;
  }

  @java.lang.SuppressWarnings("all")
  public int getAffectEnabledControls() {
    return this.affectEnabledControls;
  }

  @java.lang.SuppressWarnings("all")
  public int getEnabledControls() {
    return this.enabledControls;
  }

  @java.lang.SuppressWarnings("all")
  public int getChangeControls() {
    return this.changeControls;
  }

  @java.lang.SuppressWarnings("all")
  public short getRepeatDelay() {
    return this.repeatDelay;
  }

  @java.lang.SuppressWarnings("all")
  public short getRepeatInterval() {
    return this.repeatInterval;
  }

  @java.lang.SuppressWarnings("all")
  public short getSlowKeysDelay() {
    return this.slowKeysDelay;
  }

  @java.lang.SuppressWarnings("all")
  public short getDebounceDelay() {
    return this.debounceDelay;
  }

  @java.lang.SuppressWarnings("all")
  public short getMouseKeysDelay() {
    return this.mouseKeysDelay;
  }

  @java.lang.SuppressWarnings("all")
  public short getMouseKeysInterval() {
    return this.mouseKeysInterval;
  }

  @java.lang.SuppressWarnings("all")
  public short getMouseKeysTimeToMax() {
    return this.mouseKeysTimeToMax;
  }

  @java.lang.SuppressWarnings("all")
  public short getMouseKeysMaxSpeed() {
    return this.mouseKeysMaxSpeed;
  }

  @java.lang.SuppressWarnings("all")
  public short getMouseKeysCurve() {
    return this.mouseKeysCurve;
  }

  @java.lang.SuppressWarnings("all")
  public short getAccessXTimeout() {
    return this.accessXTimeout;
  }

  @java.lang.SuppressWarnings("all")
  public int getAccessXTimeoutMask() {
    return this.accessXTimeoutMask;
  }

  @java.lang.SuppressWarnings("all")
  public int getAccessXTimeoutValues() {
    return this.accessXTimeoutValues;
  }

  @java.lang.SuppressWarnings("all")
  public short getAccessXTimeoutOptionsMask() {
    return this.accessXTimeoutOptionsMask;
  }

  @java.lang.SuppressWarnings("all")
  public short getAccessXTimeoutOptionsValues() {
    return this.accessXTimeoutOptionsValues;
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getPerKeyRepeat() {
    return this.perKeyRepeat;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetControls)) return false;
    final SetControls other = (SetControls) o;
    if (this.getDeviceSpec() != other.getDeviceSpec()) return false;
    if (this.getAffectInternalRealMods() != other.getAffectInternalRealMods()) return false;
    if (this.getInternalRealMods() != other.getInternalRealMods()) return false;
    if (this.getAffectIgnoreLockRealMods() != other.getAffectIgnoreLockRealMods()) return false;
    if (this.getIgnoreLockRealMods() != other.getIgnoreLockRealMods()) return false;
    if (this.getAffectInternalVirtualMods() != other.getAffectInternalVirtualMods()) return false;
    if (this.getInternalVirtualMods() != other.getInternalVirtualMods()) return false;
    if (this.getAffectIgnoreLockVirtualMods() != other.getAffectIgnoreLockVirtualMods()) return false;
    if (this.getIgnoreLockVirtualMods() != other.getIgnoreLockVirtualMods()) return false;
    if (this.getMouseKeysDfltBtn() != other.getMouseKeysDfltBtn()) return false;
    if (this.getGroupsWrap() != other.getGroupsWrap()) return false;
    if (this.getAccessXOptions() != other.getAccessXOptions()) return false;
    if (this.getAffectEnabledControls() != other.getAffectEnabledControls()) return false;
    if (this.getEnabledControls() != other.getEnabledControls()) return false;
    if (this.getChangeControls() != other.getChangeControls()) return false;
    if (this.getRepeatDelay() != other.getRepeatDelay()) return false;
    if (this.getRepeatInterval() != other.getRepeatInterval()) return false;
    if (this.getSlowKeysDelay() != other.getSlowKeysDelay()) return false;
    if (this.getDebounceDelay() != other.getDebounceDelay()) return false;
    if (this.getMouseKeysDelay() != other.getMouseKeysDelay()) return false;
    if (this.getMouseKeysInterval() != other.getMouseKeysInterval()) return false;
    if (this.getMouseKeysTimeToMax() != other.getMouseKeysTimeToMax()) return false;
    if (this.getMouseKeysMaxSpeed() != other.getMouseKeysMaxSpeed()) return false;
    if (this.getMouseKeysCurve() != other.getMouseKeysCurve()) return false;
    if (this.getAccessXTimeout() != other.getAccessXTimeout()) return false;
    if (this.getAccessXTimeoutMask() != other.getAccessXTimeoutMask()) return false;
    if (this.getAccessXTimeoutValues() != other.getAccessXTimeoutValues()) return false;
    if (this.getAccessXTimeoutOptionsMask() != other.getAccessXTimeoutOptionsMask()) return false;
    if (this.getAccessXTimeoutOptionsValues() != other.getAccessXTimeoutOptionsValues()) return false;
    if (!java.util.Arrays.equals(this.getPerKeyRepeat(), other.getPerKeyRepeat())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDeviceSpec();
    result = result * PRIME + this.getAffectInternalRealMods();
    result = result * PRIME + this.getInternalRealMods();
    result = result * PRIME + this.getAffectIgnoreLockRealMods();
    result = result * PRIME + this.getIgnoreLockRealMods();
    result = result * PRIME + this.getAffectInternalVirtualMods();
    result = result * PRIME + this.getInternalVirtualMods();
    result = result * PRIME + this.getAffectIgnoreLockVirtualMods();
    result = result * PRIME + this.getIgnoreLockVirtualMods();
    result = result * PRIME + this.getMouseKeysDfltBtn();
    result = result * PRIME + this.getGroupsWrap();
    result = result * PRIME + this.getAccessXOptions();
    result = result * PRIME + this.getAffectEnabledControls();
    result = result * PRIME + this.getEnabledControls();
    result = result * PRIME + this.getChangeControls();
    result = result * PRIME + this.getRepeatDelay();
    result = result * PRIME + this.getRepeatInterval();
    result = result * PRIME + this.getSlowKeysDelay();
    result = result * PRIME + this.getDebounceDelay();
    result = result * PRIME + this.getMouseKeysDelay();
    result = result * PRIME + this.getMouseKeysInterval();
    result = result * PRIME + this.getMouseKeysTimeToMax();
    result = result * PRIME + this.getMouseKeysMaxSpeed();
    result = result * PRIME + this.getMouseKeysCurve();
    result = result * PRIME + this.getAccessXTimeout();
    result = result * PRIME + this.getAccessXTimeoutMask();
    result = result * PRIME + this.getAccessXTimeoutValues();
    result = result * PRIME + this.getAccessXTimeoutOptionsMask();
    result = result * PRIME + this.getAccessXTimeoutOptionsValues();
    result = result * PRIME + java.util.Arrays.hashCode(this.getPerKeyRepeat());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetControls(deviceSpec=" + this.getDeviceSpec() + ", affectInternalRealMods=" + this.getAffectInternalRealMods() + ", internalRealMods=" + this.getInternalRealMods() + ", affectIgnoreLockRealMods=" + this.getAffectIgnoreLockRealMods() + ", ignoreLockRealMods=" + this.getIgnoreLockRealMods() + ", affectInternalVirtualMods=" + this.getAffectInternalVirtualMods() + ", internalVirtualMods=" + this.getInternalVirtualMods() + ", affectIgnoreLockVirtualMods=" + this.getAffectIgnoreLockVirtualMods() + ", ignoreLockVirtualMods=" + this.getIgnoreLockVirtualMods() + ", mouseKeysDfltBtn=" + this.getMouseKeysDfltBtn() + ", groupsWrap=" + this.getGroupsWrap() + ", accessXOptions=" + this.getAccessXOptions() + ", affectEnabledControls=" + this.getAffectEnabledControls() + ", enabledControls=" + this.getEnabledControls() + ", changeControls=" + this.getChangeControls() + ", repeatDelay=" + this.getRepeatDelay() + ", repeatInterval=" + this.getRepeatInterval() + ", slowKeysDelay=" + this.getSlowKeysDelay() + ", debounceDelay=" + this.getDebounceDelay() + ", mouseKeysDelay=" + this.getMouseKeysDelay() + ", mouseKeysInterval=" + this.getMouseKeysInterval() + ", mouseKeysTimeToMax=" + this.getMouseKeysTimeToMax() + ", mouseKeysMaxSpeed=" + this.getMouseKeysMaxSpeed() + ", mouseKeysCurve=" + this.getMouseKeysCurve() + ", accessXTimeout=" + this.getAccessXTimeout() + ", accessXTimeoutMask=" + this.getAccessXTimeoutMask() + ", accessXTimeoutValues=" + this.getAccessXTimeoutValues() + ", accessXTimeoutOptionsMask=" + this.getAccessXTimeoutOptionsMask() + ", accessXTimeoutOptionsValues=" + this.getAccessXTimeoutOptionsValues() + ", perKeyRepeat=" + java.util.Arrays.toString(this.getPerKeyRepeat()) + ")";
  }
}
