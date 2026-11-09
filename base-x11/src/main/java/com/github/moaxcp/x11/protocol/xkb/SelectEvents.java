package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class SelectEvents implements OneWayRequest {
  public static final String PLUGIN_NAME = "xkb";
  public static final byte OPCODE = 1;
  private final short deviceSpec;
  private final short affectWhich;
  private final short clear;
  private final short selectAll;
  private final short affectMap;
  private final short map;
  private final short affectNewKeyboard;
  private final short newKeyboardDetails;
  private final short affectState;
  private final short stateDetails;
  private final int affectCtrls;
  private final int ctrlDetails;
  private final int affectIndicatorState;
  private final int indicatorStateDetails;
  private final int affectIndicatorMap;
  private final int indicatorMapDetails;
  private final short affectNames;
  private final short namesDetails;
  private final byte affectCompat;
  private final byte compatDetails;
  private final byte affectBell;
  private final byte bellDetails;
  private final byte affectMsgDetails;
  private final byte msgDetails;
  private final short affectAccessX;
  private final short accessXDetails;
  private final short affectExtDev;
  private final short extdevDetails;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SelectEvents readSelectEvents(X11Input in) throws IOException {
    SelectEvents.SelectEventsBuilder javaBuilder = SelectEvents.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short deviceSpec = in.readCard16();
    short affectWhich = in.readCard16();
    short clear = in.readCard16();
    short selectAll = in.readCard16();
    short affectMap = in.readCard16();
    short map = in.readCard16();
    short affectNewKeyboard = 0;
    short newKeyboardDetails = 0;
    short affectState = 0;
    short stateDetails = 0;
    int affectCtrls = 0;
    int ctrlDetails = 0;
    int affectIndicatorState = 0;
    int indicatorStateDetails = 0;
    int affectIndicatorMap = 0;
    int indicatorMapDetails = 0;
    short affectNames = 0;
    short namesDetails = 0;
    byte affectCompat = 0;
    byte compatDetails = 0;
    byte affectBell = 0;
    byte bellDetails = 0;
    byte affectMsgDetails = 0;
    byte msgDetails = 0;
    short affectAccessX = 0;
    short accessXDetails = 0;
    short affectExtDev = 0;
    short extdevDetails = 0;
    javaBuilder.deviceSpec(deviceSpec);
    javaBuilder.affectWhich(affectWhich);
    javaBuilder.clear(clear);
    javaBuilder.selectAll(selectAll);
    javaBuilder.affectMap(affectMap);
    javaBuilder.map(map);
    if (EventType.NEW_KEYBOARD_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      affectNewKeyboard = in.readCard16();
      javaBuilder.affectNewKeyboard(affectNewKeyboard);
    }
    if (EventType.NEW_KEYBOARD_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      newKeyboardDetails = in.readCard16();
      javaBuilder.newKeyboardDetails(newKeyboardDetails);
    }
    if (EventType.STATE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      affectState = in.readCard16();
      javaBuilder.affectState(affectState);
    }
    if (EventType.STATE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      stateDetails = in.readCard16();
      javaBuilder.stateDetails(stateDetails);
    }
    if (EventType.CONTROLS_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      affectCtrls = in.readCard32();
      javaBuilder.affectCtrls(affectCtrls);
    }
    if (EventType.CONTROLS_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      ctrlDetails = in.readCard32();
      javaBuilder.ctrlDetails(ctrlDetails);
    }
    if (EventType.INDICATOR_STATE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      affectIndicatorState = in.readCard32();
      javaBuilder.affectIndicatorState(affectIndicatorState);
    }
    if (EventType.INDICATOR_STATE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      indicatorStateDetails = in.readCard32();
      javaBuilder.indicatorStateDetails(indicatorStateDetails);
    }
    if (EventType.INDICATOR_MAP_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      affectIndicatorMap = in.readCard32();
      javaBuilder.affectIndicatorMap(affectIndicatorMap);
    }
    if (EventType.INDICATOR_MAP_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      indicatorMapDetails = in.readCard32();
      javaBuilder.indicatorMapDetails(indicatorMapDetails);
    }
    if (EventType.NAMES_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      affectNames = in.readCard16();
      javaBuilder.affectNames(affectNames);
    }
    if (EventType.NAMES_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      namesDetails = in.readCard16();
      javaBuilder.namesDetails(namesDetails);
    }
    if (EventType.COMPAT_MAP_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      affectCompat = in.readCard8();
      javaBuilder.affectCompat(affectCompat);
    }
    if (EventType.COMPAT_MAP_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      compatDetails = in.readCard8();
      javaBuilder.compatDetails(compatDetails);
    }
    if (EventType.BELL_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      affectBell = in.readCard8();
      javaBuilder.affectBell(affectBell);
    }
    if (EventType.BELL_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      bellDetails = in.readCard8();
      javaBuilder.bellDetails(bellDetails);
    }
    if (EventType.ACTION_MESSAGE.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      affectMsgDetails = in.readCard8();
      javaBuilder.affectMsgDetails(affectMsgDetails);
    }
    if (EventType.ACTION_MESSAGE.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      msgDetails = in.readCard8();
      javaBuilder.msgDetails(msgDetails);
    }
    if (EventType.ACCESS_X_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      affectAccessX = in.readCard16();
      javaBuilder.affectAccessX(affectAccessX);
    }
    if (EventType.ACCESS_X_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      accessXDetails = in.readCard16();
      javaBuilder.accessXDetails(accessXDetails);
    }
    if (EventType.EXTENSION_DEVICE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      affectExtDev = in.readCard16();
      javaBuilder.affectExtDev(affectExtDev);
    }
    if (EventType.EXTENSION_DEVICE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      extdevDetails = in.readCard16();
      javaBuilder.extdevDetails(extdevDetails);
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(deviceSpec);
    out.writeCard16(affectWhich);
    out.writeCard16(clear);
    out.writeCard16(selectAll);
    out.writeCard16(affectMap);
    out.writeCard16(map);
    if (EventType.NEW_KEYBOARD_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      out.writeCard16(affectNewKeyboard);
    }
    if (EventType.NEW_KEYBOARD_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      out.writeCard16(newKeyboardDetails);
    }
    if (EventType.STATE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      out.writeCard16(affectState);
    }
    if (EventType.STATE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      out.writeCard16(stateDetails);
    }
    if (EventType.CONTROLS_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      out.writeCard32(affectCtrls);
    }
    if (EventType.CONTROLS_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      out.writeCard32(ctrlDetails);
    }
    if (EventType.INDICATOR_STATE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      out.writeCard32(affectIndicatorState);
    }
    if (EventType.INDICATOR_STATE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      out.writeCard32(indicatorStateDetails);
    }
    if (EventType.INDICATOR_MAP_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      out.writeCard32(affectIndicatorMap);
    }
    if (EventType.INDICATOR_MAP_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      out.writeCard32(indicatorMapDetails);
    }
    if (EventType.NAMES_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      out.writeCard16(affectNames);
    }
    if (EventType.NAMES_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      out.writeCard16(namesDetails);
    }
    if (EventType.COMPAT_MAP_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      out.writeCard8(affectCompat);
    }
    if (EventType.COMPAT_MAP_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      out.writeCard8(compatDetails);
    }
    if (EventType.BELL_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      out.writeCard8(affectBell);
    }
    if (EventType.BELL_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      out.writeCard8(bellDetails);
    }
    if (EventType.ACTION_MESSAGE.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      out.writeCard8(affectMsgDetails);
    }
    if (EventType.ACTION_MESSAGE.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      out.writeCard8(msgDetails);
    }
    if (EventType.ACCESS_X_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      out.writeCard16(affectAccessX);
    }
    if (EventType.ACCESS_X_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      out.writeCard16(accessXDetails);
    }
    if (EventType.EXTENSION_DEVICE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      out.writeCard16(affectExtDev);
    }
    if (EventType.EXTENSION_DEVICE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll)))))) {
      out.writeCard16(extdevDetails);
    }
    out.writePadAlign(getSize());
  }

  public boolean isAffectWhichEnabled(EventType... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (EventType m : maskEnums) {
      if (!m.isEnabled(affectWhich)) {
        return false;
      }
    }
    return true;
  }

  public boolean isClearEnabled(EventType... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (EventType m : maskEnums) {
      if (!m.isEnabled(clear)) {
        return false;
      }
    }
    return true;
  }

  public boolean isSelectAllEnabled(EventType... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (EventType m : maskEnums) {
      if (!m.isEnabled(selectAll)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAffectMapEnabled(MapPart... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (MapPart m : maskEnums) {
      if (!m.isEnabled(affectMap)) {
        return false;
      }
    }
    return true;
  }

  public boolean isMapEnabled(MapPart... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (MapPart m : maskEnums) {
      if (!m.isEnabled(map)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAffectNewKeyboardEnabled(NKNDetail... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (NKNDetail m : maskEnums) {
      if (!m.isEnabled(affectNewKeyboard)) {
        return false;
      }
    }
    return true;
  }

  public boolean isNewKeyboardDetailsEnabled(NKNDetail... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (NKNDetail m : maskEnums) {
      if (!m.isEnabled(newKeyboardDetails)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAffectStateEnabled(StatePart... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (StatePart m : maskEnums) {
      if (!m.isEnabled(affectState)) {
        return false;
      }
    }
    return true;
  }

  public boolean isStateDetailsEnabled(StatePart... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (StatePart m : maskEnums) {
      if (!m.isEnabled(stateDetails)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAffectCtrlsEnabled(Control... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (Control m : maskEnums) {
      if (!m.isEnabled(affectCtrls)) {
        return false;
      }
    }
    return true;
  }

  public boolean isCtrlDetailsEnabled(Control... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (Control m : maskEnums) {
      if (!m.isEnabled(ctrlDetails)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAffectNamesEnabled(NameDetail... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (NameDetail m : maskEnums) {
      if (!m.isEnabled(affectNames)) {
        return false;
      }
    }
    return true;
  }

  public boolean isNamesDetailsEnabled(NameDetail... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (NameDetail m : maskEnums) {
      if (!m.isEnabled(namesDetails)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAffectCompatEnabled(CMDetail... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (CMDetail m : maskEnums) {
      if (!m.isEnabled(affectCompat)) {
        return false;
      }
    }
    return true;
  }

  public boolean isCompatDetailsEnabled(CMDetail... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (CMDetail m : maskEnums) {
      if (!m.isEnabled(compatDetails)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAffectAccessXEnabled(AXNDetail... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (AXNDetail m : maskEnums) {
      if (!m.isEnabled(affectAccessX)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAccessXDetailsEnabled(AXNDetail... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (AXNDetail m : maskEnums) {
      if (!m.isEnabled(accessXDetails)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAffectExtDevEnabled(XIFeature... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (XIFeature m : maskEnums) {
      if (!m.isEnabled(affectExtDev)) {
        return false;
      }
    }
    return true;
  }

  public boolean isExtdevDetailsEnabled(XIFeature... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (XIFeature m : maskEnums) {
      if (!m.isEnabled(extdevDetails)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 16 + (EventType.NEW_KEYBOARD_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 2 : 0) + (EventType.NEW_KEYBOARD_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 2 : 0) + (EventType.STATE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 2 : 0) + (EventType.STATE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 2 : 0) + (EventType.CONTROLS_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 4 : 0) + (EventType.CONTROLS_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 4 : 0) + (EventType.INDICATOR_STATE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 4 : 0) + (EventType.INDICATOR_STATE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 4 : 0) + (EventType.INDICATOR_MAP_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 4 : 0) + (EventType.INDICATOR_MAP_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 4 : 0) + (EventType.NAMES_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 2 : 0) + (EventType.NAMES_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 2 : 0) + (EventType.COMPAT_MAP_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 1 : 0) + (EventType.COMPAT_MAP_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 1 : 0) + (EventType.BELL_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 1 : 0) + (EventType.BELL_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 1 : 0) + (EventType.ACTION_MESSAGE.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 1 : 0) + (EventType.ACTION_MESSAGE.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 1 : 0) + (EventType.ACCESS_X_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 2 : 0) + (EventType.ACCESS_X_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 2 : 0) + (EventType.EXTENSION_DEVICE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 2 : 0) + (EventType.EXTENSION_DEVICE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 2 : 0);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SelectEventsBuilder {
    @java.lang.SuppressWarnings("all")
    private short deviceSpec;
    @java.lang.SuppressWarnings("all")
    private short affectWhich;
    @java.lang.SuppressWarnings("all")
    private short clear;
    @java.lang.SuppressWarnings("all")
    private short selectAll;
    @java.lang.SuppressWarnings("all")
    private short affectMap;
    @java.lang.SuppressWarnings("all")
    private short map;
    @java.lang.SuppressWarnings("all")
    private short affectNewKeyboard;
    @java.lang.SuppressWarnings("all")
    private short newKeyboardDetails;
    @java.lang.SuppressWarnings("all")
    private short affectState;
    @java.lang.SuppressWarnings("all")
    private short stateDetails;
    @java.lang.SuppressWarnings("all")
    private int affectCtrls;
    @java.lang.SuppressWarnings("all")
    private int ctrlDetails;
    @java.lang.SuppressWarnings("all")
    private int affectIndicatorState;
    @java.lang.SuppressWarnings("all")
    private int indicatorStateDetails;
    @java.lang.SuppressWarnings("all")
    private int affectIndicatorMap;
    @java.lang.SuppressWarnings("all")
    private int indicatorMapDetails;
    @java.lang.SuppressWarnings("all")
    private short affectNames;
    @java.lang.SuppressWarnings("all")
    private short namesDetails;
    @java.lang.SuppressWarnings("all")
    private byte affectCompat;
    @java.lang.SuppressWarnings("all")
    private byte compatDetails;
    @java.lang.SuppressWarnings("all")
    private byte affectBell;
    @java.lang.SuppressWarnings("all")
    private byte bellDetails;
    @java.lang.SuppressWarnings("all")
    private byte affectMsgDetails;
    @java.lang.SuppressWarnings("all")
    private byte msgDetails;
    @java.lang.SuppressWarnings("all")
    private short affectAccessX;
    @java.lang.SuppressWarnings("all")
    private short accessXDetails;
    @java.lang.SuppressWarnings("all")
    private short affectExtDev;
    @java.lang.SuppressWarnings("all")
    private short extdevDetails;

    public boolean isAffectWhichEnabled(EventType... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (EventType m : maskEnums) {
        if (!m.isEnabled(affectWhich)) {
          return false;
        }
      }
      return true;
    }

    public SelectEvents.SelectEventsBuilder affectWhichEnable(EventType... maskEnums) {
      for (EventType m : maskEnums) {
        affectWhich((short) m.enableFor(affectWhich));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder affectWhichDisable(EventType... maskEnums) {
      for (EventType m : maskEnums) {
        affectWhich((short) m.disableFor(affectWhich));
      }
      return this;
    }

    public boolean isClearEnabled(EventType... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (EventType m : maskEnums) {
        if (!m.isEnabled(clear)) {
          return false;
        }
      }
      return true;
    }

    public SelectEvents.SelectEventsBuilder clearEnable(EventType... maskEnums) {
      for (EventType m : maskEnums) {
        clear((short) m.enableFor(clear));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder clearDisable(EventType... maskEnums) {
      for (EventType m : maskEnums) {
        clear((short) m.disableFor(clear));
      }
      return this;
    }

    public boolean isSelectAllEnabled(EventType... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (EventType m : maskEnums) {
        if (!m.isEnabled(selectAll)) {
          return false;
        }
      }
      return true;
    }

    public SelectEvents.SelectEventsBuilder selectAllEnable(EventType... maskEnums) {
      for (EventType m : maskEnums) {
        selectAll((short) m.enableFor(selectAll));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder selectAllDisable(EventType... maskEnums) {
      for (EventType m : maskEnums) {
        selectAll((short) m.disableFor(selectAll));
      }
      return this;
    }

    public boolean isAffectMapEnabled(MapPart... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (MapPart m : maskEnums) {
        if (!m.isEnabled(affectMap)) {
          return false;
        }
      }
      return true;
    }

    public SelectEvents.SelectEventsBuilder affectMapEnable(MapPart... maskEnums) {
      for (MapPart m : maskEnums) {
        affectMap((short) m.enableFor(affectMap));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder affectMapDisable(MapPart... maskEnums) {
      for (MapPart m : maskEnums) {
        affectMap((short) m.disableFor(affectMap));
      }
      return this;
    }

    public boolean isMapEnabled(MapPart... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (MapPart m : maskEnums) {
        if (!m.isEnabled(map)) {
          return false;
        }
      }
      return true;
    }

    public SelectEvents.SelectEventsBuilder mapEnable(MapPart... maskEnums) {
      for (MapPart m : maskEnums) {
        map((short) m.enableFor(map));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder mapDisable(MapPart... maskEnums) {
      for (MapPart m : maskEnums) {
        map((short) m.disableFor(map));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder affectNewKeyboard(short affectNewKeyboard) {
      this.affectNewKeyboard = affectNewKeyboard;
      affectWhichEnable(EventType.NEW_KEYBOARD_NOTIFY);
      return this;
    }

    public boolean isAffectNewKeyboardEnabled(NKNDetail... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (NKNDetail m : maskEnums) {
        if (!m.isEnabled(affectNewKeyboard)) {
          return false;
        }
      }
      return true;
    }

    public SelectEvents.SelectEventsBuilder affectNewKeyboardEnable(NKNDetail... maskEnums) {
      for (NKNDetail m : maskEnums) {
        affectNewKeyboard((short) m.enableFor(affectNewKeyboard));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder affectNewKeyboardDisable(NKNDetail... maskEnums) {
      for (NKNDetail m : maskEnums) {
        affectNewKeyboard((short) m.disableFor(affectNewKeyboard));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder newKeyboardDetails(short newKeyboardDetails) {
      this.newKeyboardDetails = newKeyboardDetails;
      affectWhichEnable(EventType.NEW_KEYBOARD_NOTIFY);
      return this;
    }

    public boolean isNewKeyboardDetailsEnabled(NKNDetail... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (NKNDetail m : maskEnums) {
        if (!m.isEnabled(newKeyboardDetails)) {
          return false;
        }
      }
      return true;
    }

    public SelectEvents.SelectEventsBuilder newKeyboardDetailsEnable(NKNDetail... maskEnums) {
      for (NKNDetail m : maskEnums) {
        newKeyboardDetails((short) m.enableFor(newKeyboardDetails));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder newKeyboardDetailsDisable(NKNDetail... maskEnums) {
      for (NKNDetail m : maskEnums) {
        newKeyboardDetails((short) m.disableFor(newKeyboardDetails));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder affectState(short affectState) {
      this.affectState = affectState;
      affectWhichEnable(EventType.STATE_NOTIFY);
      return this;
    }

    public boolean isAffectStateEnabled(StatePart... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (StatePart m : maskEnums) {
        if (!m.isEnabled(affectState)) {
          return false;
        }
      }
      return true;
    }

    public SelectEvents.SelectEventsBuilder affectStateEnable(StatePart... maskEnums) {
      for (StatePart m : maskEnums) {
        affectState((short) m.enableFor(affectState));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder affectStateDisable(StatePart... maskEnums) {
      for (StatePart m : maskEnums) {
        affectState((short) m.disableFor(affectState));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder stateDetails(short stateDetails) {
      this.stateDetails = stateDetails;
      affectWhichEnable(EventType.STATE_NOTIFY);
      return this;
    }

    public boolean isStateDetailsEnabled(StatePart... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (StatePart m : maskEnums) {
        if (!m.isEnabled(stateDetails)) {
          return false;
        }
      }
      return true;
    }

    public SelectEvents.SelectEventsBuilder stateDetailsEnable(StatePart... maskEnums) {
      for (StatePart m : maskEnums) {
        stateDetails((short) m.enableFor(stateDetails));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder stateDetailsDisable(StatePart... maskEnums) {
      for (StatePart m : maskEnums) {
        stateDetails((short) m.disableFor(stateDetails));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder affectCtrls(int affectCtrls) {
      this.affectCtrls = affectCtrls;
      affectWhichEnable(EventType.CONTROLS_NOTIFY);
      return this;
    }

    public boolean isAffectCtrlsEnabled(Control... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (Control m : maskEnums) {
        if (!m.isEnabled(affectCtrls)) {
          return false;
        }
      }
      return true;
    }

    public SelectEvents.SelectEventsBuilder affectCtrlsEnable(Control... maskEnums) {
      for (Control m : maskEnums) {
        affectCtrls((int) m.enableFor(affectCtrls));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder affectCtrlsDisable(Control... maskEnums) {
      for (Control m : maskEnums) {
        affectCtrls((int) m.disableFor(affectCtrls));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder ctrlDetails(int ctrlDetails) {
      this.ctrlDetails = ctrlDetails;
      affectWhichEnable(EventType.CONTROLS_NOTIFY);
      return this;
    }

    public boolean isCtrlDetailsEnabled(Control... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (Control m : maskEnums) {
        if (!m.isEnabled(ctrlDetails)) {
          return false;
        }
      }
      return true;
    }

    public SelectEvents.SelectEventsBuilder ctrlDetailsEnable(Control... maskEnums) {
      for (Control m : maskEnums) {
        ctrlDetails((int) m.enableFor(ctrlDetails));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder ctrlDetailsDisable(Control... maskEnums) {
      for (Control m : maskEnums) {
        ctrlDetails((int) m.disableFor(ctrlDetails));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder affectIndicatorState(int affectIndicatorState) {
      this.affectIndicatorState = affectIndicatorState;
      affectWhichEnable(EventType.INDICATOR_STATE_NOTIFY);
      return this;
    }

    public SelectEvents.SelectEventsBuilder indicatorStateDetails(int indicatorStateDetails) {
      this.indicatorStateDetails = indicatorStateDetails;
      affectWhichEnable(EventType.INDICATOR_STATE_NOTIFY);
      return this;
    }

    public SelectEvents.SelectEventsBuilder affectIndicatorMap(int affectIndicatorMap) {
      this.affectIndicatorMap = affectIndicatorMap;
      affectWhichEnable(EventType.INDICATOR_MAP_NOTIFY);
      return this;
    }

    public SelectEvents.SelectEventsBuilder indicatorMapDetails(int indicatorMapDetails) {
      this.indicatorMapDetails = indicatorMapDetails;
      affectWhichEnable(EventType.INDICATOR_MAP_NOTIFY);
      return this;
    }

    public SelectEvents.SelectEventsBuilder affectNames(short affectNames) {
      this.affectNames = affectNames;
      affectWhichEnable(EventType.NAMES_NOTIFY);
      return this;
    }

    public boolean isAffectNamesEnabled(NameDetail... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (NameDetail m : maskEnums) {
        if (!m.isEnabled(affectNames)) {
          return false;
        }
      }
      return true;
    }

    public SelectEvents.SelectEventsBuilder affectNamesEnable(NameDetail... maskEnums) {
      for (NameDetail m : maskEnums) {
        affectNames((short) m.enableFor(affectNames));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder affectNamesDisable(NameDetail... maskEnums) {
      for (NameDetail m : maskEnums) {
        affectNames((short) m.disableFor(affectNames));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder namesDetails(short namesDetails) {
      this.namesDetails = namesDetails;
      affectWhichEnable(EventType.NAMES_NOTIFY);
      return this;
    }

    public boolean isNamesDetailsEnabled(NameDetail... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (NameDetail m : maskEnums) {
        if (!m.isEnabled(namesDetails)) {
          return false;
        }
      }
      return true;
    }

    public SelectEvents.SelectEventsBuilder namesDetailsEnable(NameDetail... maskEnums) {
      for (NameDetail m : maskEnums) {
        namesDetails((short) m.enableFor(namesDetails));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder namesDetailsDisable(NameDetail... maskEnums) {
      for (NameDetail m : maskEnums) {
        namesDetails((short) m.disableFor(namesDetails));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder affectCompat(byte affectCompat) {
      this.affectCompat = affectCompat;
      affectWhichEnable(EventType.COMPAT_MAP_NOTIFY);
      return this;
    }

    public boolean isAffectCompatEnabled(CMDetail... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (CMDetail m : maskEnums) {
        if (!m.isEnabled(affectCompat)) {
          return false;
        }
      }
      return true;
    }

    public SelectEvents.SelectEventsBuilder affectCompatEnable(CMDetail... maskEnums) {
      for (CMDetail m : maskEnums) {
        affectCompat((byte) m.enableFor(affectCompat));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder affectCompatDisable(CMDetail... maskEnums) {
      for (CMDetail m : maskEnums) {
        affectCompat((byte) m.disableFor(affectCompat));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder compatDetails(byte compatDetails) {
      this.compatDetails = compatDetails;
      affectWhichEnable(EventType.COMPAT_MAP_NOTIFY);
      return this;
    }

    public boolean isCompatDetailsEnabled(CMDetail... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (CMDetail m : maskEnums) {
        if (!m.isEnabled(compatDetails)) {
          return false;
        }
      }
      return true;
    }

    public SelectEvents.SelectEventsBuilder compatDetailsEnable(CMDetail... maskEnums) {
      for (CMDetail m : maskEnums) {
        compatDetails((byte) m.enableFor(compatDetails));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder compatDetailsDisable(CMDetail... maskEnums) {
      for (CMDetail m : maskEnums) {
        compatDetails((byte) m.disableFor(compatDetails));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder affectBell(byte affectBell) {
      this.affectBell = affectBell;
      affectWhichEnable(EventType.BELL_NOTIFY);
      return this;
    }

    public SelectEvents.SelectEventsBuilder bellDetails(byte bellDetails) {
      this.bellDetails = bellDetails;
      affectWhichEnable(EventType.BELL_NOTIFY);
      return this;
    }

    public SelectEvents.SelectEventsBuilder affectMsgDetails(byte affectMsgDetails) {
      this.affectMsgDetails = affectMsgDetails;
      affectWhichEnable(EventType.ACTION_MESSAGE);
      return this;
    }

    public SelectEvents.SelectEventsBuilder msgDetails(byte msgDetails) {
      this.msgDetails = msgDetails;
      affectWhichEnable(EventType.ACTION_MESSAGE);
      return this;
    }

    public SelectEvents.SelectEventsBuilder affectAccessX(short affectAccessX) {
      this.affectAccessX = affectAccessX;
      affectWhichEnable(EventType.ACCESS_X_NOTIFY);
      return this;
    }

    public boolean isAffectAccessXEnabled(AXNDetail... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (AXNDetail m : maskEnums) {
        if (!m.isEnabled(affectAccessX)) {
          return false;
        }
      }
      return true;
    }

    public SelectEvents.SelectEventsBuilder affectAccessXEnable(AXNDetail... maskEnums) {
      for (AXNDetail m : maskEnums) {
        affectAccessX((short) m.enableFor(affectAccessX));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder affectAccessXDisable(AXNDetail... maskEnums) {
      for (AXNDetail m : maskEnums) {
        affectAccessX((short) m.disableFor(affectAccessX));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder accessXDetails(short accessXDetails) {
      this.accessXDetails = accessXDetails;
      affectWhichEnable(EventType.ACCESS_X_NOTIFY);
      return this;
    }

    public boolean isAccessXDetailsEnabled(AXNDetail... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (AXNDetail m : maskEnums) {
        if (!m.isEnabled(accessXDetails)) {
          return false;
        }
      }
      return true;
    }

    public SelectEvents.SelectEventsBuilder accessXDetailsEnable(AXNDetail... maskEnums) {
      for (AXNDetail m : maskEnums) {
        accessXDetails((short) m.enableFor(accessXDetails));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder accessXDetailsDisable(AXNDetail... maskEnums) {
      for (AXNDetail m : maskEnums) {
        accessXDetails((short) m.disableFor(accessXDetails));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder affectExtDev(short affectExtDev) {
      this.affectExtDev = affectExtDev;
      affectWhichEnable(EventType.EXTENSION_DEVICE_NOTIFY);
      return this;
    }

    public boolean isAffectExtDevEnabled(XIFeature... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (XIFeature m : maskEnums) {
        if (!m.isEnabled(affectExtDev)) {
          return false;
        }
      }
      return true;
    }

    public SelectEvents.SelectEventsBuilder affectExtDevEnable(XIFeature... maskEnums) {
      for (XIFeature m : maskEnums) {
        affectExtDev((short) m.enableFor(affectExtDev));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder affectExtDevDisable(XIFeature... maskEnums) {
      for (XIFeature m : maskEnums) {
        affectExtDev((short) m.disableFor(affectExtDev));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder extdevDetails(short extdevDetails) {
      this.extdevDetails = extdevDetails;
      affectWhichEnable(EventType.EXTENSION_DEVICE_NOTIFY);
      return this;
    }

    public boolean isExtdevDetailsEnabled(XIFeature... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (XIFeature m : maskEnums) {
        if (!m.isEnabled(extdevDetails)) {
          return false;
        }
      }
      return true;
    }

    public SelectEvents.SelectEventsBuilder extdevDetailsEnable(XIFeature... maskEnums) {
      for (XIFeature m : maskEnums) {
        extdevDetails((short) m.enableFor(extdevDetails));
      }
      return this;
    }

    public SelectEvents.SelectEventsBuilder extdevDetailsDisable(XIFeature... maskEnums) {
      for (XIFeature m : maskEnums) {
        extdevDetails((short) m.disableFor(extdevDetails));
      }
      return this;
    }

    public int getSize() {
      return 16 + (EventType.NEW_KEYBOARD_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 2 : 0) + (EventType.NEW_KEYBOARD_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 2 : 0) + (EventType.STATE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 2 : 0) + (EventType.STATE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 2 : 0) + (EventType.CONTROLS_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 4 : 0) + (EventType.CONTROLS_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 4 : 0) + (EventType.INDICATOR_STATE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 4 : 0) + (EventType.INDICATOR_STATE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 4 : 0) + (EventType.INDICATOR_MAP_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 4 : 0) + (EventType.INDICATOR_MAP_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 4 : 0) + (EventType.NAMES_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 2 : 0) + (EventType.NAMES_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 2 : 0) + (EventType.COMPAT_MAP_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 1 : 0) + (EventType.COMPAT_MAP_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 1 : 0) + (EventType.BELL_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 1 : 0) + (EventType.BELL_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 1 : 0) + (EventType.ACTION_MESSAGE.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 1 : 0) + (EventType.ACTION_MESSAGE.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 1 : 0) + (EventType.ACCESS_X_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 2 : 0) + (EventType.ACCESS_X_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 2 : 0) + (EventType.EXTENSION_DEVICE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 2 : 0) + (EventType.EXTENSION_DEVICE_NOTIFY.isEnabled((Short.toUnsignedInt(affectWhich)) & ((~(Short.toUnsignedInt(clear))) & (~(Short.toUnsignedInt(selectAll))))) ? 2 : 0);
    }

    @java.lang.SuppressWarnings("all")
    SelectEventsBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SelectEvents.SelectEventsBuilder deviceSpec(final short deviceSpec) {
      this.deviceSpec = deviceSpec;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SelectEvents.SelectEventsBuilder affectWhich(final short affectWhich) {
      this.affectWhich = affectWhich;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SelectEvents.SelectEventsBuilder clear(final short clear) {
      this.clear = clear;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SelectEvents.SelectEventsBuilder selectAll(final short selectAll) {
      this.selectAll = selectAll;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SelectEvents.SelectEventsBuilder affectMap(final short affectMap) {
      this.affectMap = affectMap;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SelectEvents.SelectEventsBuilder map(final short map) {
      this.map = map;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SelectEvents build() {
      return new SelectEvents(this.deviceSpec, this.affectWhich, this.clear, this.selectAll, this.affectMap, this.map, this.affectNewKeyboard, this.newKeyboardDetails, this.affectState, this.stateDetails, this.affectCtrls, this.ctrlDetails, this.affectIndicatorState, this.indicatorStateDetails, this.affectIndicatorMap, this.indicatorMapDetails, this.affectNames, this.namesDetails, this.affectCompat, this.compatDetails, this.affectBell, this.bellDetails, this.affectMsgDetails, this.msgDetails, this.affectAccessX, this.accessXDetails, this.affectExtDev, this.extdevDetails);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SelectEvents.SelectEventsBuilder(deviceSpec=" + this.deviceSpec + ", affectWhich=" + this.affectWhich + ", clear=" + this.clear + ", selectAll=" + this.selectAll + ", affectMap=" + this.affectMap + ", map=" + this.map + ", affectNewKeyboard=" + this.affectNewKeyboard + ", newKeyboardDetails=" + this.newKeyboardDetails + ", affectState=" + this.affectState + ", stateDetails=" + this.stateDetails + ", affectCtrls=" + this.affectCtrls + ", ctrlDetails=" + this.ctrlDetails + ", affectIndicatorState=" + this.affectIndicatorState + ", indicatorStateDetails=" + this.indicatorStateDetails + ", affectIndicatorMap=" + this.affectIndicatorMap + ", indicatorMapDetails=" + this.indicatorMapDetails + ", affectNames=" + this.affectNames + ", namesDetails=" + this.namesDetails + ", affectCompat=" + this.affectCompat + ", compatDetails=" + this.compatDetails + ", affectBell=" + this.affectBell + ", bellDetails=" + this.bellDetails + ", affectMsgDetails=" + this.affectMsgDetails + ", msgDetails=" + this.msgDetails + ", affectAccessX=" + this.affectAccessX + ", accessXDetails=" + this.accessXDetails + ", affectExtDev=" + this.affectExtDev + ", extdevDetails=" + this.extdevDetails + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SelectEvents(final short deviceSpec, final short affectWhich, final short clear, final short selectAll, final short affectMap, final short map, final short affectNewKeyboard, final short newKeyboardDetails, final short affectState, final short stateDetails, final int affectCtrls, final int ctrlDetails, final int affectIndicatorState, final int indicatorStateDetails, final int affectIndicatorMap, final int indicatorMapDetails, final short affectNames, final short namesDetails, final byte affectCompat, final byte compatDetails, final byte affectBell, final byte bellDetails, final byte affectMsgDetails, final byte msgDetails, final short affectAccessX, final short accessXDetails, final short affectExtDev, final short extdevDetails) {
    this.deviceSpec = deviceSpec;
    this.affectWhich = affectWhich;
    this.clear = clear;
    this.selectAll = selectAll;
    this.affectMap = affectMap;
    this.map = map;
    this.affectNewKeyboard = affectNewKeyboard;
    this.newKeyboardDetails = newKeyboardDetails;
    this.affectState = affectState;
    this.stateDetails = stateDetails;
    this.affectCtrls = affectCtrls;
    this.ctrlDetails = ctrlDetails;
    this.affectIndicatorState = affectIndicatorState;
    this.indicatorStateDetails = indicatorStateDetails;
    this.affectIndicatorMap = affectIndicatorMap;
    this.indicatorMapDetails = indicatorMapDetails;
    this.affectNames = affectNames;
    this.namesDetails = namesDetails;
    this.affectCompat = affectCompat;
    this.compatDetails = compatDetails;
    this.affectBell = affectBell;
    this.bellDetails = bellDetails;
    this.affectMsgDetails = affectMsgDetails;
    this.msgDetails = msgDetails;
    this.affectAccessX = affectAccessX;
    this.accessXDetails = accessXDetails;
    this.affectExtDev = affectExtDev;
    this.extdevDetails = extdevDetails;
  }

  @java.lang.SuppressWarnings("all")
  public static SelectEvents.SelectEventsBuilder builder() {
    return new SelectEvents.SelectEventsBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getDeviceSpec() {
    return this.deviceSpec;
  }

  @java.lang.SuppressWarnings("all")
  public short getAffectWhich() {
    return this.affectWhich;
  }

  @java.lang.SuppressWarnings("all")
  public short getClear() {
    return this.clear;
  }

  @java.lang.SuppressWarnings("all")
  public short getSelectAll() {
    return this.selectAll;
  }

  @java.lang.SuppressWarnings("all")
  public short getAffectMap() {
    return this.affectMap;
  }

  @java.lang.SuppressWarnings("all")
  public short getMap() {
    return this.map;
  }

  @java.lang.SuppressWarnings("all")
  public short getAffectNewKeyboard() {
    return this.affectNewKeyboard;
  }

  @java.lang.SuppressWarnings("all")
  public short getNewKeyboardDetails() {
    return this.newKeyboardDetails;
  }

  @java.lang.SuppressWarnings("all")
  public short getAffectState() {
    return this.affectState;
  }

  @java.lang.SuppressWarnings("all")
  public short getStateDetails() {
    return this.stateDetails;
  }

  @java.lang.SuppressWarnings("all")
  public int getAffectCtrls() {
    return this.affectCtrls;
  }

  @java.lang.SuppressWarnings("all")
  public int getCtrlDetails() {
    return this.ctrlDetails;
  }

  @java.lang.SuppressWarnings("all")
  public int getAffectIndicatorState() {
    return this.affectIndicatorState;
  }

  @java.lang.SuppressWarnings("all")
  public int getIndicatorStateDetails() {
    return this.indicatorStateDetails;
  }

  @java.lang.SuppressWarnings("all")
  public int getAffectIndicatorMap() {
    return this.affectIndicatorMap;
  }

  @java.lang.SuppressWarnings("all")
  public int getIndicatorMapDetails() {
    return this.indicatorMapDetails;
  }

  @java.lang.SuppressWarnings("all")
  public short getAffectNames() {
    return this.affectNames;
  }

  @java.lang.SuppressWarnings("all")
  public short getNamesDetails() {
    return this.namesDetails;
  }

  @java.lang.SuppressWarnings("all")
  public byte getAffectCompat() {
    return this.affectCompat;
  }

  @java.lang.SuppressWarnings("all")
  public byte getCompatDetails() {
    return this.compatDetails;
  }

  @java.lang.SuppressWarnings("all")
  public byte getAffectBell() {
    return this.affectBell;
  }

  @java.lang.SuppressWarnings("all")
  public byte getBellDetails() {
    return this.bellDetails;
  }

  @java.lang.SuppressWarnings("all")
  public byte getAffectMsgDetails() {
    return this.affectMsgDetails;
  }

  @java.lang.SuppressWarnings("all")
  public byte getMsgDetails() {
    return this.msgDetails;
  }

  @java.lang.SuppressWarnings("all")
  public short getAffectAccessX() {
    return this.affectAccessX;
  }

  @java.lang.SuppressWarnings("all")
  public short getAccessXDetails() {
    return this.accessXDetails;
  }

  @java.lang.SuppressWarnings("all")
  public short getAffectExtDev() {
    return this.affectExtDev;
  }

  @java.lang.SuppressWarnings("all")
  public short getExtdevDetails() {
    return this.extdevDetails;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SelectEvents)) return false;
    final SelectEvents other = (SelectEvents) o;
    if (this.getDeviceSpec() != other.getDeviceSpec()) return false;
    if (this.getAffectWhich() != other.getAffectWhich()) return false;
    if (this.getClear() != other.getClear()) return false;
    if (this.getSelectAll() != other.getSelectAll()) return false;
    if (this.getAffectMap() != other.getAffectMap()) return false;
    if (this.getMap() != other.getMap()) return false;
    if (this.getAffectNewKeyboard() != other.getAffectNewKeyboard()) return false;
    if (this.getNewKeyboardDetails() != other.getNewKeyboardDetails()) return false;
    if (this.getAffectState() != other.getAffectState()) return false;
    if (this.getStateDetails() != other.getStateDetails()) return false;
    if (this.getAffectCtrls() != other.getAffectCtrls()) return false;
    if (this.getCtrlDetails() != other.getCtrlDetails()) return false;
    if (this.getAffectIndicatorState() != other.getAffectIndicatorState()) return false;
    if (this.getIndicatorStateDetails() != other.getIndicatorStateDetails()) return false;
    if (this.getAffectIndicatorMap() != other.getAffectIndicatorMap()) return false;
    if (this.getIndicatorMapDetails() != other.getIndicatorMapDetails()) return false;
    if (this.getAffectNames() != other.getAffectNames()) return false;
    if (this.getNamesDetails() != other.getNamesDetails()) return false;
    if (this.getAffectCompat() != other.getAffectCompat()) return false;
    if (this.getCompatDetails() != other.getCompatDetails()) return false;
    if (this.getAffectBell() != other.getAffectBell()) return false;
    if (this.getBellDetails() != other.getBellDetails()) return false;
    if (this.getAffectMsgDetails() != other.getAffectMsgDetails()) return false;
    if (this.getMsgDetails() != other.getMsgDetails()) return false;
    if (this.getAffectAccessX() != other.getAffectAccessX()) return false;
    if (this.getAccessXDetails() != other.getAccessXDetails()) return false;
    if (this.getAffectExtDev() != other.getAffectExtDev()) return false;
    if (this.getExtdevDetails() != other.getExtdevDetails()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDeviceSpec();
    result = result * PRIME + this.getAffectWhich();
    result = result * PRIME + this.getClear();
    result = result * PRIME + this.getSelectAll();
    result = result * PRIME + this.getAffectMap();
    result = result * PRIME + this.getMap();
    result = result * PRIME + this.getAffectNewKeyboard();
    result = result * PRIME + this.getNewKeyboardDetails();
    result = result * PRIME + this.getAffectState();
    result = result * PRIME + this.getStateDetails();
    result = result * PRIME + this.getAffectCtrls();
    result = result * PRIME + this.getCtrlDetails();
    result = result * PRIME + this.getAffectIndicatorState();
    result = result * PRIME + this.getIndicatorStateDetails();
    result = result * PRIME + this.getAffectIndicatorMap();
    result = result * PRIME + this.getIndicatorMapDetails();
    result = result * PRIME + this.getAffectNames();
    result = result * PRIME + this.getNamesDetails();
    result = result * PRIME + this.getAffectCompat();
    result = result * PRIME + this.getCompatDetails();
    result = result * PRIME + this.getAffectBell();
    result = result * PRIME + this.getBellDetails();
    result = result * PRIME + this.getAffectMsgDetails();
    result = result * PRIME + this.getMsgDetails();
    result = result * PRIME + this.getAffectAccessX();
    result = result * PRIME + this.getAccessXDetails();
    result = result * PRIME + this.getAffectExtDev();
    result = result * PRIME + this.getExtdevDetails();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SelectEvents(deviceSpec=" + this.getDeviceSpec() + ", affectWhich=" + this.getAffectWhich() + ", clear=" + this.getClear() + ", selectAll=" + this.getSelectAll() + ", affectMap=" + this.getAffectMap() + ", map=" + this.getMap() + ", affectNewKeyboard=" + this.getAffectNewKeyboard() + ", newKeyboardDetails=" + this.getNewKeyboardDetails() + ", affectState=" + this.getAffectState() + ", stateDetails=" + this.getStateDetails() + ", affectCtrls=" + this.getAffectCtrls() + ", ctrlDetails=" + this.getCtrlDetails() + ", affectIndicatorState=" + this.getAffectIndicatorState() + ", indicatorStateDetails=" + this.getIndicatorStateDetails() + ", affectIndicatorMap=" + this.getAffectIndicatorMap() + ", indicatorMapDetails=" + this.getIndicatorMapDetails() + ", affectNames=" + this.getAffectNames() + ", namesDetails=" + this.getNamesDetails() + ", affectCompat=" + this.getAffectCompat() + ", compatDetails=" + this.getCompatDetails() + ", affectBell=" + this.getAffectBell() + ", bellDetails=" + this.getBellDetails() + ", affectMsgDetails=" + this.getAffectMsgDetails() + ", msgDetails=" + this.getMsgDetails() + ", affectAccessX=" + this.getAffectAccessX() + ", accessXDetails=" + this.getAccessXDetails() + ", affectExtDev=" + this.getAffectExtDev() + ", extdevDetails=" + this.getExtdevDetails() + ")";
  }
}
