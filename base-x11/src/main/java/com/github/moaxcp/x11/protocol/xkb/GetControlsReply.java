package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import com.github.moaxcp.x11.protocol.xproto.ModMask;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.ByteList;

public final class GetControlsReply implements XReply {
  public static final String PLUGIN_NAME = "xkb";
  private final byte deviceID;
  private final short sequenceNumber;
  private final byte mouseKeysDfltBtn;
  private final byte numGroups;
  private final byte groupsWrap;
  private final byte internalModsMask;
  private final byte ignoreLockModsMask;
  private final byte internalModsRealMods;
  private final byte ignoreLockModsRealMods;
  private final short internalModsVmods;
  private final short ignoreLockModsVmods;
  private final short repeatDelay;
  private final short repeatInterval;
  private final short slowKeysDelay;
  private final short debounceDelay;
  private final short mouseKeysDelay;
  private final short mouseKeysInterval;
  private final short mouseKeysTimeToMax;
  private final short mouseKeysMaxSpeed;
  private final short mouseKeysCurve;
  private final short accessXOption;
  private final short accessXTimeout;
  private final short accessXTimeoutOptionsMask;
  private final short accessXTimeoutOptionsValues;
  private final int accessXTimeoutMask;
  private final int accessXTimeoutValues;
  private final int enabledControls;
  private final ByteList perKeyRepeat;

  public static GetControlsReply readGetControlsReply(byte deviceID, short sequenceNumber, X11Input in) throws IOException {
    GetControlsReply.GetControlsReplyBuilder javaBuilder = GetControlsReply.builder();
    int length = in.readCard32();
    byte mouseKeysDfltBtn = in.readCard8();
    byte numGroups = in.readCard8();
    byte groupsWrap = in.readCard8();
    byte internalModsMask = in.readCard8();
    byte ignoreLockModsMask = in.readCard8();
    byte internalModsRealMods = in.readCard8();
    byte ignoreLockModsRealMods = in.readCard8();
    byte[] pad11 = in.readPad(1);
    short internalModsVmods = in.readCard16();
    short ignoreLockModsVmods = in.readCard16();
    short repeatDelay = in.readCard16();
    short repeatInterval = in.readCard16();
    short slowKeysDelay = in.readCard16();
    short debounceDelay = in.readCard16();
    short mouseKeysDelay = in.readCard16();
    short mouseKeysInterval = in.readCard16();
    short mouseKeysTimeToMax = in.readCard16();
    short mouseKeysMaxSpeed = in.readCard16();
    short mouseKeysCurve = in.readInt16();
    short accessXOption = in.readCard16();
    short accessXTimeout = in.readCard16();
    short accessXTimeoutOptionsMask = in.readCard16();
    short accessXTimeoutOptionsValues = in.readCard16();
    byte[] pad27 = in.readPad(2);
    int accessXTimeoutMask = in.readCard32();
    int accessXTimeoutValues = in.readCard32();
    int enabledControls = in.readCard32();
    ByteList perKeyRepeat = in.readCard8(32);
    javaBuilder.deviceID(deviceID);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.mouseKeysDfltBtn(mouseKeysDfltBtn);
    javaBuilder.numGroups(numGroups);
    javaBuilder.groupsWrap(groupsWrap);
    javaBuilder.internalModsMask(internalModsMask);
    javaBuilder.ignoreLockModsMask(ignoreLockModsMask);
    javaBuilder.internalModsRealMods(internalModsRealMods);
    javaBuilder.ignoreLockModsRealMods(ignoreLockModsRealMods);
    javaBuilder.internalModsVmods(internalModsVmods);
    javaBuilder.ignoreLockModsVmods(ignoreLockModsVmods);
    javaBuilder.repeatDelay(repeatDelay);
    javaBuilder.repeatInterval(repeatInterval);
    javaBuilder.slowKeysDelay(slowKeysDelay);
    javaBuilder.debounceDelay(debounceDelay);
    javaBuilder.mouseKeysDelay(mouseKeysDelay);
    javaBuilder.mouseKeysInterval(mouseKeysInterval);
    javaBuilder.mouseKeysTimeToMax(mouseKeysTimeToMax);
    javaBuilder.mouseKeysMaxSpeed(mouseKeysMaxSpeed);
    javaBuilder.mouseKeysCurve(mouseKeysCurve);
    javaBuilder.accessXOption(accessXOption);
    javaBuilder.accessXTimeout(accessXTimeout);
    javaBuilder.accessXTimeoutOptionsMask(accessXTimeoutOptionsMask);
    javaBuilder.accessXTimeoutOptionsValues(accessXTimeoutOptionsValues);
    javaBuilder.accessXTimeoutMask(accessXTimeoutMask);
    javaBuilder.accessXTimeoutValues(accessXTimeoutValues);
    javaBuilder.enabledControls(enabledControls);
    javaBuilder.perKeyRepeat(perKeyRepeat.toImmutable());
    if (javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(deviceID);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard8(mouseKeysDfltBtn);
    out.writeCard8(numGroups);
    out.writeCard8(groupsWrap);
    out.writeCard8(internalModsMask);
    out.writeCard8(ignoreLockModsMask);
    out.writeCard8(internalModsRealMods);
    out.writeCard8(ignoreLockModsRealMods);
    out.writePad(1);
    out.writeCard16(internalModsVmods);
    out.writeCard16(ignoreLockModsVmods);
    out.writeCard16(repeatDelay);
    out.writeCard16(repeatInterval);
    out.writeCard16(slowKeysDelay);
    out.writeCard16(debounceDelay);
    out.writeCard16(mouseKeysDelay);
    out.writeCard16(mouseKeysInterval);
    out.writeCard16(mouseKeysTimeToMax);
    out.writeCard16(mouseKeysMaxSpeed);
    out.writeInt16(mouseKeysCurve);
    out.writeCard16(accessXOption);
    out.writeCard16(accessXTimeout);
    out.writeCard16(accessXTimeoutOptionsMask);
    out.writeCard16(accessXTimeoutOptionsValues);
    out.writePad(2);
    out.writeCard32(accessXTimeoutMask);
    out.writeCard32(accessXTimeoutValues);
    out.writeCard32(enabledControls);
    out.writeCard8(perKeyRepeat);
    out.writePadAlign(getSize());
  }

  public boolean isInternalModsMaskEnabled(ModMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (ModMask m : maskEnums) {
      if (!m.isEnabled(internalModsMask)) {
        return false;
      }
    }
    return true;
  }

  public boolean isIgnoreLockModsMaskEnabled(ModMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (ModMask m : maskEnums) {
      if (!m.isEnabled(ignoreLockModsMask)) {
        return false;
      }
    }
    return true;
  }

  public boolean isInternalModsRealModsEnabled(ModMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (ModMask m : maskEnums) {
      if (!m.isEnabled(internalModsRealMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isIgnoreLockModsRealModsEnabled(ModMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (ModMask m : maskEnums) {
      if (!m.isEnabled(ignoreLockModsRealMods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isInternalModsVmodsEnabled(VMod... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (VMod m : maskEnums) {
      if (!m.isEnabled(internalModsVmods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isIgnoreLockModsVmodsEnabled(VMod... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (VMod m : maskEnums) {
      if (!m.isEnabled(ignoreLockModsVmods)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAccessXOptionEnabled(AXOption... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (AXOption m : maskEnums) {
      if (!m.isEnabled(accessXOption)) {
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

  @Override
  public int getSize() {
    return 60 + 1 * perKeyRepeat.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetControlsReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte deviceID;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private byte mouseKeysDfltBtn;
    @java.lang.SuppressWarnings("all")
    private byte numGroups;
    @java.lang.SuppressWarnings("all")
    private byte groupsWrap;
    @java.lang.SuppressWarnings("all")
    private byte internalModsMask;
    @java.lang.SuppressWarnings("all")
    private byte ignoreLockModsMask;
    @java.lang.SuppressWarnings("all")
    private byte internalModsRealMods;
    @java.lang.SuppressWarnings("all")
    private byte ignoreLockModsRealMods;
    @java.lang.SuppressWarnings("all")
    private short internalModsVmods;
    @java.lang.SuppressWarnings("all")
    private short ignoreLockModsVmods;
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
    private short accessXOption;
    @java.lang.SuppressWarnings("all")
    private short accessXTimeout;
    @java.lang.SuppressWarnings("all")
    private short accessXTimeoutOptionsMask;
    @java.lang.SuppressWarnings("all")
    private short accessXTimeoutOptionsValues;
    @java.lang.SuppressWarnings("all")
    private int accessXTimeoutMask;
    @java.lang.SuppressWarnings("all")
    private int accessXTimeoutValues;
    @java.lang.SuppressWarnings("all")
    private int enabledControls;
    @java.lang.SuppressWarnings("all")
    private ByteList perKeyRepeat;

    public boolean isInternalModsMaskEnabled(ModMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (ModMask m : maskEnums) {
        if (!m.isEnabled(internalModsMask)) {
          return false;
        }
      }
      return true;
    }

    public GetControlsReply.GetControlsReplyBuilder internalModsMaskEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        internalModsMask((byte) m.enableFor(internalModsMask));
      }
      return this;
    }

    public GetControlsReply.GetControlsReplyBuilder internalModsMaskDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        internalModsMask((byte) m.disableFor(internalModsMask));
      }
      return this;
    }

    public boolean isIgnoreLockModsMaskEnabled(ModMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (ModMask m : maskEnums) {
        if (!m.isEnabled(ignoreLockModsMask)) {
          return false;
        }
      }
      return true;
    }

    public GetControlsReply.GetControlsReplyBuilder ignoreLockModsMaskEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        ignoreLockModsMask((byte) m.enableFor(ignoreLockModsMask));
      }
      return this;
    }

    public GetControlsReply.GetControlsReplyBuilder ignoreLockModsMaskDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        ignoreLockModsMask((byte) m.disableFor(ignoreLockModsMask));
      }
      return this;
    }

    public boolean isInternalModsRealModsEnabled(ModMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (ModMask m : maskEnums) {
        if (!m.isEnabled(internalModsRealMods)) {
          return false;
        }
      }
      return true;
    }

    public GetControlsReply.GetControlsReplyBuilder internalModsRealModsEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        internalModsRealMods((byte) m.enableFor(internalModsRealMods));
      }
      return this;
    }

    public GetControlsReply.GetControlsReplyBuilder internalModsRealModsDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        internalModsRealMods((byte) m.disableFor(internalModsRealMods));
      }
      return this;
    }

    public boolean isIgnoreLockModsRealModsEnabled(ModMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (ModMask m : maskEnums) {
        if (!m.isEnabled(ignoreLockModsRealMods)) {
          return false;
        }
      }
      return true;
    }

    public GetControlsReply.GetControlsReplyBuilder ignoreLockModsRealModsEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        ignoreLockModsRealMods((byte) m.enableFor(ignoreLockModsRealMods));
      }
      return this;
    }

    public GetControlsReply.GetControlsReplyBuilder ignoreLockModsRealModsDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        ignoreLockModsRealMods((byte) m.disableFor(ignoreLockModsRealMods));
      }
      return this;
    }

    public boolean isInternalModsVmodsEnabled(VMod... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (VMod m : maskEnums) {
        if (!m.isEnabled(internalModsVmods)) {
          return false;
        }
      }
      return true;
    }

    public GetControlsReply.GetControlsReplyBuilder internalModsVmodsEnable(VMod... maskEnums) {
      for (VMod m : maskEnums) {
        internalModsVmods((short) m.enableFor(internalModsVmods));
      }
      return this;
    }

    public GetControlsReply.GetControlsReplyBuilder internalModsVmodsDisable(VMod... maskEnums) {
      for (VMod m : maskEnums) {
        internalModsVmods((short) m.disableFor(internalModsVmods));
      }
      return this;
    }

    public boolean isIgnoreLockModsVmodsEnabled(VMod... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (VMod m : maskEnums) {
        if (!m.isEnabled(ignoreLockModsVmods)) {
          return false;
        }
      }
      return true;
    }

    public GetControlsReply.GetControlsReplyBuilder ignoreLockModsVmodsEnable(VMod... maskEnums) {
      for (VMod m : maskEnums) {
        ignoreLockModsVmods((short) m.enableFor(ignoreLockModsVmods));
      }
      return this;
    }

    public GetControlsReply.GetControlsReplyBuilder ignoreLockModsVmodsDisable(VMod... maskEnums) {
      for (VMod m : maskEnums) {
        ignoreLockModsVmods((short) m.disableFor(ignoreLockModsVmods));
      }
      return this;
    }

    public boolean isAccessXOptionEnabled(AXOption... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (AXOption m : maskEnums) {
        if (!m.isEnabled(accessXOption)) {
          return false;
        }
      }
      return true;
    }

    public GetControlsReply.GetControlsReplyBuilder accessXOptionEnable(AXOption... maskEnums) {
      for (AXOption m : maskEnums) {
        accessXOption((short) m.enableFor(accessXOption));
      }
      return this;
    }

    public GetControlsReply.GetControlsReplyBuilder accessXOptionDisable(AXOption... maskEnums) {
      for (AXOption m : maskEnums) {
        accessXOption((short) m.disableFor(accessXOption));
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

    public GetControlsReply.GetControlsReplyBuilder accessXTimeoutOptionsMaskEnable(AXOption... maskEnums) {
      for (AXOption m : maskEnums) {
        accessXTimeoutOptionsMask((short) m.enableFor(accessXTimeoutOptionsMask));
      }
      return this;
    }

    public GetControlsReply.GetControlsReplyBuilder accessXTimeoutOptionsMaskDisable(AXOption... maskEnums) {
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

    public GetControlsReply.GetControlsReplyBuilder accessXTimeoutOptionsValuesEnable(AXOption... maskEnums) {
      for (AXOption m : maskEnums) {
        accessXTimeoutOptionsValues((short) m.enableFor(accessXTimeoutOptionsValues));
      }
      return this;
    }

    public GetControlsReply.GetControlsReplyBuilder accessXTimeoutOptionsValuesDisable(AXOption... maskEnums) {
      for (AXOption m : maskEnums) {
        accessXTimeoutOptionsValues((short) m.disableFor(accessXTimeoutOptionsValues));
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

    public GetControlsReply.GetControlsReplyBuilder accessXTimeoutMaskEnable(BoolCtrl... maskEnums) {
      for (BoolCtrl m : maskEnums) {
        accessXTimeoutMask((int) m.enableFor(accessXTimeoutMask));
      }
      return this;
    }

    public GetControlsReply.GetControlsReplyBuilder accessXTimeoutMaskDisable(BoolCtrl... maskEnums) {
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

    public GetControlsReply.GetControlsReplyBuilder accessXTimeoutValuesEnable(BoolCtrl... maskEnums) {
      for (BoolCtrl m : maskEnums) {
        accessXTimeoutValues((int) m.enableFor(accessXTimeoutValues));
      }
      return this;
    }

    public GetControlsReply.GetControlsReplyBuilder accessXTimeoutValuesDisable(BoolCtrl... maskEnums) {
      for (BoolCtrl m : maskEnums) {
        accessXTimeoutValues((int) m.disableFor(accessXTimeoutValues));
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

    public GetControlsReply.GetControlsReplyBuilder enabledControlsEnable(BoolCtrl... maskEnums) {
      for (BoolCtrl m : maskEnums) {
        enabledControls((int) m.enableFor(enabledControls));
      }
      return this;
    }

    public GetControlsReply.GetControlsReplyBuilder enabledControlsDisable(BoolCtrl... maskEnums) {
      for (BoolCtrl m : maskEnums) {
        enabledControls((int) m.disableFor(enabledControls));
      }
      return this;
    }

    public int getSize() {
      return 60 + 1 * perKeyRepeat.size();
    }

    @java.lang.SuppressWarnings("all")
    GetControlsReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetControlsReply.GetControlsReplyBuilder deviceID(final byte deviceID) {
      this.deviceID = deviceID;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetControlsReply.GetControlsReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetControlsReply.GetControlsReplyBuilder mouseKeysDfltBtn(final byte mouseKeysDfltBtn) {
      this.mouseKeysDfltBtn = mouseKeysDfltBtn;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetControlsReply.GetControlsReplyBuilder numGroups(final byte numGroups) {
      this.numGroups = numGroups;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetControlsReply.GetControlsReplyBuilder groupsWrap(final byte groupsWrap) {
      this.groupsWrap = groupsWrap;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetControlsReply.GetControlsReplyBuilder internalModsMask(final byte internalModsMask) {
      this.internalModsMask = internalModsMask;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetControlsReply.GetControlsReplyBuilder ignoreLockModsMask(final byte ignoreLockModsMask) {
      this.ignoreLockModsMask = ignoreLockModsMask;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetControlsReply.GetControlsReplyBuilder internalModsRealMods(final byte internalModsRealMods) {
      this.internalModsRealMods = internalModsRealMods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetControlsReply.GetControlsReplyBuilder ignoreLockModsRealMods(final byte ignoreLockModsRealMods) {
      this.ignoreLockModsRealMods = ignoreLockModsRealMods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetControlsReply.GetControlsReplyBuilder internalModsVmods(final short internalModsVmods) {
      this.internalModsVmods = internalModsVmods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetControlsReply.GetControlsReplyBuilder ignoreLockModsVmods(final short ignoreLockModsVmods) {
      this.ignoreLockModsVmods = ignoreLockModsVmods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetControlsReply.GetControlsReplyBuilder repeatDelay(final short repeatDelay) {
      this.repeatDelay = repeatDelay;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetControlsReply.GetControlsReplyBuilder repeatInterval(final short repeatInterval) {
      this.repeatInterval = repeatInterval;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetControlsReply.GetControlsReplyBuilder slowKeysDelay(final short slowKeysDelay) {
      this.slowKeysDelay = slowKeysDelay;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetControlsReply.GetControlsReplyBuilder debounceDelay(final short debounceDelay) {
      this.debounceDelay = debounceDelay;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetControlsReply.GetControlsReplyBuilder mouseKeysDelay(final short mouseKeysDelay) {
      this.mouseKeysDelay = mouseKeysDelay;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetControlsReply.GetControlsReplyBuilder mouseKeysInterval(final short mouseKeysInterval) {
      this.mouseKeysInterval = mouseKeysInterval;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetControlsReply.GetControlsReplyBuilder mouseKeysTimeToMax(final short mouseKeysTimeToMax) {
      this.mouseKeysTimeToMax = mouseKeysTimeToMax;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetControlsReply.GetControlsReplyBuilder mouseKeysMaxSpeed(final short mouseKeysMaxSpeed) {
      this.mouseKeysMaxSpeed = mouseKeysMaxSpeed;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetControlsReply.GetControlsReplyBuilder mouseKeysCurve(final short mouseKeysCurve) {
      this.mouseKeysCurve = mouseKeysCurve;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetControlsReply.GetControlsReplyBuilder accessXOption(final short accessXOption) {
      this.accessXOption = accessXOption;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetControlsReply.GetControlsReplyBuilder accessXTimeout(final short accessXTimeout) {
      this.accessXTimeout = accessXTimeout;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetControlsReply.GetControlsReplyBuilder accessXTimeoutOptionsMask(final short accessXTimeoutOptionsMask) {
      this.accessXTimeoutOptionsMask = accessXTimeoutOptionsMask;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetControlsReply.GetControlsReplyBuilder accessXTimeoutOptionsValues(final short accessXTimeoutOptionsValues) {
      this.accessXTimeoutOptionsValues = accessXTimeoutOptionsValues;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetControlsReply.GetControlsReplyBuilder accessXTimeoutMask(final int accessXTimeoutMask) {
      this.accessXTimeoutMask = accessXTimeoutMask;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetControlsReply.GetControlsReplyBuilder accessXTimeoutValues(final int accessXTimeoutValues) {
      this.accessXTimeoutValues = accessXTimeoutValues;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetControlsReply.GetControlsReplyBuilder enabledControls(final int enabledControls) {
      this.enabledControls = enabledControls;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetControlsReply.GetControlsReplyBuilder perKeyRepeat(final ByteList perKeyRepeat) {
      if (perKeyRepeat == null) {
        throw new java.lang.NullPointerException("perKeyRepeat is marked non-null but is null");
      }
      this.perKeyRepeat = perKeyRepeat;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetControlsReply build() {
      return new GetControlsReply(this.deviceID, this.sequenceNumber, this.mouseKeysDfltBtn, this.numGroups, this.groupsWrap, this.internalModsMask, this.ignoreLockModsMask, this.internalModsRealMods, this.ignoreLockModsRealMods, this.internalModsVmods, this.ignoreLockModsVmods, this.repeatDelay, this.repeatInterval, this.slowKeysDelay, this.debounceDelay, this.mouseKeysDelay, this.mouseKeysInterval, this.mouseKeysTimeToMax, this.mouseKeysMaxSpeed, this.mouseKeysCurve, this.accessXOption, this.accessXTimeout, this.accessXTimeoutOptionsMask, this.accessXTimeoutOptionsValues, this.accessXTimeoutMask, this.accessXTimeoutValues, this.enabledControls, this.perKeyRepeat);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetControlsReply.GetControlsReplyBuilder(deviceID=" + this.deviceID + ", sequenceNumber=" + this.sequenceNumber + ", mouseKeysDfltBtn=" + this.mouseKeysDfltBtn + ", numGroups=" + this.numGroups + ", groupsWrap=" + this.groupsWrap + ", internalModsMask=" + this.internalModsMask + ", ignoreLockModsMask=" + this.ignoreLockModsMask + ", internalModsRealMods=" + this.internalModsRealMods + ", ignoreLockModsRealMods=" + this.ignoreLockModsRealMods + ", internalModsVmods=" + this.internalModsVmods + ", ignoreLockModsVmods=" + this.ignoreLockModsVmods + ", repeatDelay=" + this.repeatDelay + ", repeatInterval=" + this.repeatInterval + ", slowKeysDelay=" + this.slowKeysDelay + ", debounceDelay=" + this.debounceDelay + ", mouseKeysDelay=" + this.mouseKeysDelay + ", mouseKeysInterval=" + this.mouseKeysInterval + ", mouseKeysTimeToMax=" + this.mouseKeysTimeToMax + ", mouseKeysMaxSpeed=" + this.mouseKeysMaxSpeed + ", mouseKeysCurve=" + this.mouseKeysCurve + ", accessXOption=" + this.accessXOption + ", accessXTimeout=" + this.accessXTimeout + ", accessXTimeoutOptionsMask=" + this.accessXTimeoutOptionsMask + ", accessXTimeoutOptionsValues=" + this.accessXTimeoutOptionsValues + ", accessXTimeoutMask=" + this.accessXTimeoutMask + ", accessXTimeoutValues=" + this.accessXTimeoutValues + ", enabledControls=" + this.enabledControls + ", perKeyRepeat=" + this.perKeyRepeat + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetControlsReply(final byte deviceID, final short sequenceNumber, final byte mouseKeysDfltBtn, final byte numGroups, final byte groupsWrap, final byte internalModsMask, final byte ignoreLockModsMask, final byte internalModsRealMods, final byte ignoreLockModsRealMods, final short internalModsVmods, final short ignoreLockModsVmods, final short repeatDelay, final short repeatInterval, final short slowKeysDelay, final short debounceDelay, final short mouseKeysDelay, final short mouseKeysInterval, final short mouseKeysTimeToMax, final short mouseKeysMaxSpeed, final short mouseKeysCurve, final short accessXOption, final short accessXTimeout, final short accessXTimeoutOptionsMask, final short accessXTimeoutOptionsValues, final int accessXTimeoutMask, final int accessXTimeoutValues, final int enabledControls, final ByteList perKeyRepeat) {
    if (perKeyRepeat == null) {
      throw new java.lang.NullPointerException("perKeyRepeat is marked non-null but is null");
    }
    this.deviceID = deviceID;
    this.sequenceNumber = sequenceNumber;
    this.mouseKeysDfltBtn = mouseKeysDfltBtn;
    this.numGroups = numGroups;
    this.groupsWrap = groupsWrap;
    this.internalModsMask = internalModsMask;
    this.ignoreLockModsMask = ignoreLockModsMask;
    this.internalModsRealMods = internalModsRealMods;
    this.ignoreLockModsRealMods = ignoreLockModsRealMods;
    this.internalModsVmods = internalModsVmods;
    this.ignoreLockModsVmods = ignoreLockModsVmods;
    this.repeatDelay = repeatDelay;
    this.repeatInterval = repeatInterval;
    this.slowKeysDelay = slowKeysDelay;
    this.debounceDelay = debounceDelay;
    this.mouseKeysDelay = mouseKeysDelay;
    this.mouseKeysInterval = mouseKeysInterval;
    this.mouseKeysTimeToMax = mouseKeysTimeToMax;
    this.mouseKeysMaxSpeed = mouseKeysMaxSpeed;
    this.mouseKeysCurve = mouseKeysCurve;
    this.accessXOption = accessXOption;
    this.accessXTimeout = accessXTimeout;
    this.accessXTimeoutOptionsMask = accessXTimeoutOptionsMask;
    this.accessXTimeoutOptionsValues = accessXTimeoutOptionsValues;
    this.accessXTimeoutMask = accessXTimeoutMask;
    this.accessXTimeoutValues = accessXTimeoutValues;
    this.enabledControls = enabledControls;
    this.perKeyRepeat = perKeyRepeat;
  }

  @java.lang.SuppressWarnings("all")
  public static GetControlsReply.GetControlsReplyBuilder builder() {
    return new GetControlsReply.GetControlsReplyBuilder();
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
  public byte getMouseKeysDfltBtn() {
    return this.mouseKeysDfltBtn;
  }

  @java.lang.SuppressWarnings("all")
  public byte getNumGroups() {
    return this.numGroups;
  }

  @java.lang.SuppressWarnings("all")
  public byte getGroupsWrap() {
    return this.groupsWrap;
  }

  @java.lang.SuppressWarnings("all")
  public byte getInternalModsMask() {
    return this.internalModsMask;
  }

  @java.lang.SuppressWarnings("all")
  public byte getIgnoreLockModsMask() {
    return this.ignoreLockModsMask;
  }

  @java.lang.SuppressWarnings("all")
  public byte getInternalModsRealMods() {
    return this.internalModsRealMods;
  }

  @java.lang.SuppressWarnings("all")
  public byte getIgnoreLockModsRealMods() {
    return this.ignoreLockModsRealMods;
  }

  @java.lang.SuppressWarnings("all")
  public short getInternalModsVmods() {
    return this.internalModsVmods;
  }

  @java.lang.SuppressWarnings("all")
  public short getIgnoreLockModsVmods() {
    return this.ignoreLockModsVmods;
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
  public short getAccessXOption() {
    return this.accessXOption;
  }

  @java.lang.SuppressWarnings("all")
  public short getAccessXTimeout() {
    return this.accessXTimeout;
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
  public int getAccessXTimeoutMask() {
    return this.accessXTimeoutMask;
  }

  @java.lang.SuppressWarnings("all")
  public int getAccessXTimeoutValues() {
    return this.accessXTimeoutValues;
  }

  @java.lang.SuppressWarnings("all")
  public int getEnabledControls() {
    return this.enabledControls;
  }

  @java.lang.SuppressWarnings("all")
  public ByteList getPerKeyRepeat() {
    return this.perKeyRepeat;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetControlsReply)) return false;
    final GetControlsReply other = (GetControlsReply) o;
    if (this.getDeviceID() != other.getDeviceID()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getMouseKeysDfltBtn() != other.getMouseKeysDfltBtn()) return false;
    if (this.getNumGroups() != other.getNumGroups()) return false;
    if (this.getGroupsWrap() != other.getGroupsWrap()) return false;
    if (this.getInternalModsMask() != other.getInternalModsMask()) return false;
    if (this.getIgnoreLockModsMask() != other.getIgnoreLockModsMask()) return false;
    if (this.getInternalModsRealMods() != other.getInternalModsRealMods()) return false;
    if (this.getIgnoreLockModsRealMods() != other.getIgnoreLockModsRealMods()) return false;
    if (this.getInternalModsVmods() != other.getInternalModsVmods()) return false;
    if (this.getIgnoreLockModsVmods() != other.getIgnoreLockModsVmods()) return false;
    if (this.getRepeatDelay() != other.getRepeatDelay()) return false;
    if (this.getRepeatInterval() != other.getRepeatInterval()) return false;
    if (this.getSlowKeysDelay() != other.getSlowKeysDelay()) return false;
    if (this.getDebounceDelay() != other.getDebounceDelay()) return false;
    if (this.getMouseKeysDelay() != other.getMouseKeysDelay()) return false;
    if (this.getMouseKeysInterval() != other.getMouseKeysInterval()) return false;
    if (this.getMouseKeysTimeToMax() != other.getMouseKeysTimeToMax()) return false;
    if (this.getMouseKeysMaxSpeed() != other.getMouseKeysMaxSpeed()) return false;
    if (this.getMouseKeysCurve() != other.getMouseKeysCurve()) return false;
    if (this.getAccessXOption() != other.getAccessXOption()) return false;
    if (this.getAccessXTimeout() != other.getAccessXTimeout()) return false;
    if (this.getAccessXTimeoutOptionsMask() != other.getAccessXTimeoutOptionsMask()) return false;
    if (this.getAccessXTimeoutOptionsValues() != other.getAccessXTimeoutOptionsValues()) return false;
    if (this.getAccessXTimeoutMask() != other.getAccessXTimeoutMask()) return false;
    if (this.getAccessXTimeoutValues() != other.getAccessXTimeoutValues()) return false;
    if (this.getEnabledControls() != other.getEnabledControls()) return false;
    final java.lang.Object this$perKeyRepeat = this.getPerKeyRepeat();
    final java.lang.Object other$perKeyRepeat = other.getPerKeyRepeat();
    if (this$perKeyRepeat == null ? other$perKeyRepeat != null : !this$perKeyRepeat.equals(other$perKeyRepeat)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDeviceID();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getMouseKeysDfltBtn();
    result = result * PRIME + this.getNumGroups();
    result = result * PRIME + this.getGroupsWrap();
    result = result * PRIME + this.getInternalModsMask();
    result = result * PRIME + this.getIgnoreLockModsMask();
    result = result * PRIME + this.getInternalModsRealMods();
    result = result * PRIME + this.getIgnoreLockModsRealMods();
    result = result * PRIME + this.getInternalModsVmods();
    result = result * PRIME + this.getIgnoreLockModsVmods();
    result = result * PRIME + this.getRepeatDelay();
    result = result * PRIME + this.getRepeatInterval();
    result = result * PRIME + this.getSlowKeysDelay();
    result = result * PRIME + this.getDebounceDelay();
    result = result * PRIME + this.getMouseKeysDelay();
    result = result * PRIME + this.getMouseKeysInterval();
    result = result * PRIME + this.getMouseKeysTimeToMax();
    result = result * PRIME + this.getMouseKeysMaxSpeed();
    result = result * PRIME + this.getMouseKeysCurve();
    result = result * PRIME + this.getAccessXOption();
    result = result * PRIME + this.getAccessXTimeout();
    result = result * PRIME + this.getAccessXTimeoutOptionsMask();
    result = result * PRIME + this.getAccessXTimeoutOptionsValues();
    result = result * PRIME + this.getAccessXTimeoutMask();
    result = result * PRIME + this.getAccessXTimeoutValues();
    result = result * PRIME + this.getEnabledControls();
    final java.lang.Object $perKeyRepeat = this.getPerKeyRepeat();
    result = result * PRIME + ($perKeyRepeat == null ? 43 : $perKeyRepeat.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetControlsReply(deviceID=" + this.getDeviceID() + ", sequenceNumber=" + this.getSequenceNumber() + ", mouseKeysDfltBtn=" + this.getMouseKeysDfltBtn() + ", numGroups=" + this.getNumGroups() + ", groupsWrap=" + this.getGroupsWrap() + ", internalModsMask=" + this.getInternalModsMask() + ", ignoreLockModsMask=" + this.getIgnoreLockModsMask() + ", internalModsRealMods=" + this.getInternalModsRealMods() + ", ignoreLockModsRealMods=" + this.getIgnoreLockModsRealMods() + ", internalModsVmods=" + this.getInternalModsVmods() + ", ignoreLockModsVmods=" + this.getIgnoreLockModsVmods() + ", repeatDelay=" + this.getRepeatDelay() + ", repeatInterval=" + this.getRepeatInterval() + ", slowKeysDelay=" + this.getSlowKeysDelay() + ", debounceDelay=" + this.getDebounceDelay() + ", mouseKeysDelay=" + this.getMouseKeysDelay() + ", mouseKeysInterval=" + this.getMouseKeysInterval() + ", mouseKeysTimeToMax=" + this.getMouseKeysTimeToMax() + ", mouseKeysMaxSpeed=" + this.getMouseKeysMaxSpeed() + ", mouseKeysCurve=" + this.getMouseKeysCurve() + ", accessXOption=" + this.getAccessXOption() + ", accessXTimeout=" + this.getAccessXTimeout() + ", accessXTimeoutOptionsMask=" + this.getAccessXTimeoutOptionsMask() + ", accessXTimeoutOptionsValues=" + this.getAccessXTimeoutOptionsValues() + ", accessXTimeoutMask=" + this.getAccessXTimeoutMask() + ", accessXTimeoutValues=" + this.getAccessXTimeoutValues() + ", enabledControls=" + this.getEnabledControls() + ", perKeyRepeat=" + this.getPerKeyRepeat() + ")";
  }
}
