package com.github.moaxcp.x11.protocol.xkb;

import java.util.List;
import com.github.moaxcp.x11.protocol.Popcount;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class GetMapReply implements XReply {
  public static final String PLUGIN_NAME = "xkb";
  private final byte deviceID;
  private final short sequenceNumber;
  private final byte minKeyCode;
  private final byte maxKeyCode;
  private final short present;
  private final byte firstType;
  private final byte totalTypes;
  private final byte firstKeySym;
  private final short totalSyms;
  private final byte firstKeyAction;
  private final byte firstKeyBehavior;
  private final byte nKeyBehaviors;
  private final byte firstKeyExplicit;
  private final byte nKeyExplicit;
  private final byte firstModMapKey;
  private final byte nModMapKeys;
  private final byte firstVModMapKey;
  private final byte nVModMapKeys;
  private final List<KeyType> typesRtrn;
  private final List<KeySymMap> symsRtrn;
  private final byte[] actsRtrnCount;
  private final List<ActionUnion> actsRtrnActs;
  private final List<SetBehavior> behaviorsRtrn;
  private final byte[] vmodsRtrn;
  private final List<SetExplicit> explicitRtrn;
  private final List<KeyModMap> modmapRtrn;
  private final List<KeyVModMap> vmodmapRtrn;

  public static GetMapReply readGetMapReply(byte deviceID, short sequenceNumber, X11Input in) throws IOException {
    GetMapReply.GetMapReplyBuilder javaBuilder = GetMapReply.builder();
    int length = in.readCard32();
    byte[] pad4 = in.readPad(2);
    byte minKeyCode = in.readCard8();
    byte maxKeyCode = in.readCard8();
    short present = in.readCard16();
    byte firstType = in.readCard8();
    byte nTypes = in.readCard8();
    byte totalTypes = in.readCard8();
    byte firstKeySym = in.readCard8();
    short totalSyms = in.readCard16();
    byte nKeySyms = in.readCard8();
    byte firstKeyAction = in.readCard8();
    short totalActions = in.readCard16();
    byte nKeyActions = in.readCard8();
    byte firstKeyBehavior = in.readCard8();
    byte nKeyBehaviors = in.readCard8();
    byte totalKeyBehaviors = in.readCard8();
    byte firstKeyExplicit = in.readCard8();
    byte nKeyExplicit = in.readCard8();
    byte totalKeyExplicit = in.readCard8();
    byte firstModMapKey = in.readCard8();
    byte nModMapKeys = in.readCard8();
    byte totalModMapKeys = in.readCard8();
    byte firstVModMapKey = in.readCard8();
    byte nVModMapKeys = in.readCard8();
    byte totalVModMapKeys = in.readCard8();
    byte[] pad29 = in.readPad(1);
    short virtualMods = in.readCard16();
    List<KeyType> typesRtrn = null;
    List<KeySymMap> symsRtrn = null;
    byte[] actsRtrnCount = null;
    in.readPadAlign(Byte.toUnsignedInt(nKeyActions));
    List<ActionUnion> actsRtrnActs = null;
    List<SetBehavior> behaviorsRtrn = null;
    byte[] vmodsRtrn = null;
    in.readPadAlign(com.github.moaxcp.x11.protocol.Popcount.popcount(Short.toUnsignedInt(virtualMods)));
    List<SetExplicit> explicitRtrn = null;
    in.readPadAlign(Byte.toUnsignedInt(totalKeyExplicit));
    List<KeyModMap> modmapRtrn = null;
    in.readPadAlign(Byte.toUnsignedInt(totalModMapKeys));
    List<KeyVModMap> vmodmapRtrn = null;
    javaBuilder.deviceID(deviceID);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.minKeyCode(minKeyCode);
    javaBuilder.maxKeyCode(maxKeyCode);
    javaBuilder.present(present);
    javaBuilder.firstType(firstType);
    javaBuilder.totalTypes(totalTypes);
    javaBuilder.firstKeySym(firstKeySym);
    javaBuilder.totalSyms(totalSyms);
    javaBuilder.firstKeyAction(firstKeyAction);
    javaBuilder.firstKeyBehavior(firstKeyBehavior);
    javaBuilder.nKeyBehaviors(nKeyBehaviors);
    javaBuilder.firstKeyExplicit(firstKeyExplicit);
    javaBuilder.nKeyExplicit(nKeyExplicit);
    javaBuilder.firstModMapKey(firstModMapKey);
    javaBuilder.nModMapKeys(nModMapKeys);
    javaBuilder.firstVModMapKey(firstVModMapKey);
    javaBuilder.nVModMapKeys(nVModMapKeys);
    if (MapPart.KEY_TYPES.isEnabled(Short.toUnsignedInt(present))) {
      typesRtrn = in.readObject(KeyType::readKeyType, Byte.toUnsignedInt(nTypes));
      javaBuilder.typesRtrn(typesRtrn);
    }
    if (MapPart.KEY_SYMS.isEnabled(Short.toUnsignedInt(present))) {
      symsRtrn = in.readObject(KeySymMap::readKeySymMap, Byte.toUnsignedInt(nKeySyms));
      javaBuilder.symsRtrn(symsRtrn);
    }
    if (MapPart.KEY_ACTIONS.isEnabled(Short.toUnsignedInt(present))) {
      actsRtrnCount = in.readCard8(Byte.toUnsignedInt(nKeyActions));
      javaBuilder.actsRtrnCount(actsRtrnCount);
    }
    if (MapPart.KEY_ACTIONS.isEnabled(Short.toUnsignedInt(present))) {
      actsRtrnActs = in.readObject(ActionUnion::readActionUnion, Short.toUnsignedInt(totalActions));
      javaBuilder.actsRtrnActs(actsRtrnActs);
    }
    if (MapPart.KEY_BEHAVIORS.isEnabled(Short.toUnsignedInt(present))) {
      behaviorsRtrn = in.readObject(SetBehavior::readSetBehavior, Byte.toUnsignedInt(totalKeyBehaviors));
      javaBuilder.behaviorsRtrn(behaviorsRtrn);
    }
    if (MapPart.VIRTUAL_MODS.isEnabled(Short.toUnsignedInt(present))) {
      vmodsRtrn = in.readCard8(Popcount.popcount(Short.toUnsignedInt(virtualMods)));
      javaBuilder.vmodsRtrn(vmodsRtrn);
    }
    if (MapPart.EXPLICIT_COMPONENTS.isEnabled(Short.toUnsignedInt(present))) {
      explicitRtrn = in.readObject(SetExplicit::readSetExplicit, Byte.toUnsignedInt(totalKeyExplicit));
      javaBuilder.explicitRtrn(explicitRtrn);
    }
    if (MapPart.MODIFIER_MAP.isEnabled(Short.toUnsignedInt(present))) {
      modmapRtrn = in.readObject(KeyModMap::readKeyModMap, Byte.toUnsignedInt(totalModMapKeys));
      javaBuilder.modmapRtrn(modmapRtrn);
    }
    if (MapPart.VIRTUAL_MOD_MAP.isEnabled(Short.toUnsignedInt(present))) {
      vmodmapRtrn = in.readObject(KeyVModMap::readKeyVModMap, Byte.toUnsignedInt(totalVModMapKeys));
      javaBuilder.vmodmapRtrn(vmodmapRtrn);
    }
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
    out.writePad(2);
    out.writeCard8(minKeyCode);
    out.writeCard8(maxKeyCode);
    out.writeCard16(present);
    out.writeCard8(firstType);
    byte nTypes = (byte) typesRtrn.size();
    out.writeCard8(nTypes);
    out.writeCard8(totalTypes);
    out.writeCard8(firstKeySym);
    out.writeCard16(totalSyms);
    byte nKeySyms = (byte) symsRtrn.size();
    out.writeCard8(nKeySyms);
    out.writeCard8(firstKeyAction);
    short totalActions = (short) actsRtrnActs.size();
    out.writeCard16(totalActions);
    byte nKeyActions = (byte) actsRtrnCount.length;
    out.writeCard8(nKeyActions);
    out.writeCard8(firstKeyBehavior);
    out.writeCard8(nKeyBehaviors);
    byte totalKeyBehaviors = (byte) behaviorsRtrn.size();
    out.writeCard8(totalKeyBehaviors);
    out.writeCard8(firstKeyExplicit);
    out.writeCard8(nKeyExplicit);
    byte totalKeyExplicit = (byte) explicitRtrn.size();
    out.writeCard8(totalKeyExplicit);
    out.writeCard8(firstModMapKey);
    out.writeCard8(nModMapKeys);
    byte totalModMapKeys = (byte) modmapRtrn.size();
    out.writeCard8(totalModMapKeys);
    out.writeCard8(firstVModMapKey);
    out.writeCard8(nVModMapKeys);
    byte totalVModMapKeys = (byte) vmodmapRtrn.size();
    out.writeCard8(totalVModMapKeys);
    out.writePad(1);
    short virtualMods = (short) vmodsRtrn.length;
    out.writeCard16(virtualMods);
    if (MapPart.KEY_TYPES.isEnabled(Short.toUnsignedInt(present))) {
      for (KeyType t : typesRtrn) {
        t.write(out);
      }
    }
    if (MapPart.KEY_SYMS.isEnabled(Short.toUnsignedInt(present))) {
      for (KeySymMap t : symsRtrn) {
        t.write(out);
      }
    }
    if (MapPart.KEY_ACTIONS.isEnabled(Short.toUnsignedInt(present))) {
      out.writeCard8(actsRtrnCount);
    }
    out.writePadAlign(Byte.toUnsignedInt(nKeyActions));
    if (MapPart.KEY_ACTIONS.isEnabled(Short.toUnsignedInt(present))) {
      for (ActionUnion t : actsRtrnActs) {
        t.write(out);
      }
    }
    if (MapPart.KEY_BEHAVIORS.isEnabled(Short.toUnsignedInt(present))) {
      for (SetBehavior t : behaviorsRtrn) {
        t.write(out);
      }
    }
    if (MapPart.VIRTUAL_MODS.isEnabled(Short.toUnsignedInt(present))) {
      out.writeCard8(vmodsRtrn);
    }
    out.writePadAlign(com.github.moaxcp.x11.protocol.Popcount.popcount(Short.toUnsignedInt(virtualMods)));
    if (MapPart.EXPLICIT_COMPONENTS.isEnabled(Short.toUnsignedInt(present))) {
      for (SetExplicit t : explicitRtrn) {
        t.write(out);
      }
    }
    out.writePadAlign(Byte.toUnsignedInt(totalKeyExplicit));
    if (MapPart.MODIFIER_MAP.isEnabled(Short.toUnsignedInt(present))) {
      for (KeyModMap t : modmapRtrn) {
        t.write(out);
      }
    }
    out.writePadAlign(Byte.toUnsignedInt(totalModMapKeys));
    if (MapPart.VIRTUAL_MOD_MAP.isEnabled(Short.toUnsignedInt(present))) {
      for (KeyVModMap t : vmodmapRtrn) {
        t.write(out);
      }
    }
    out.writePadAlign(getSize());
  }

  public boolean isPresentEnabled(MapPart... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (MapPart m : maskEnums) {
      if (!m.isEnabled(present)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 40 + (MapPart.KEY_TYPES.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(typesRtrn) : 0) + (MapPart.KEY_SYMS.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(symsRtrn) : 0) + (MapPart.KEY_ACTIONS.isEnabled(Short.toUnsignedInt(present)) ? 1 * actsRtrnCount.length : 0) + XObject.getSizeForPadAlign(4, (MapPart.KEY_ACTIONS.isEnabled(Short.toUnsignedInt(present)) ? 1 * actsRtrnCount.length : 0)) + (MapPart.KEY_ACTIONS.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(actsRtrnActs) : 0) + (MapPart.KEY_BEHAVIORS.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(behaviorsRtrn) : 0) + (MapPart.VIRTUAL_MODS.isEnabled(Short.toUnsignedInt(present)) ? 1 * vmodsRtrn.length : 0) + XObject.getSizeForPadAlign(4, (MapPart.VIRTUAL_MODS.isEnabled(Short.toUnsignedInt(present)) ? 1 * vmodsRtrn.length : 0)) + (MapPart.EXPLICIT_COMPONENTS.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(explicitRtrn) : 0) + XObject.getSizeForPadAlign(4, (MapPart.EXPLICIT_COMPONENTS.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(explicitRtrn) : 0)) + (MapPart.MODIFIER_MAP.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(modmapRtrn) : 0) + XObject.getSizeForPadAlign(4, (MapPart.MODIFIER_MAP.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(modmapRtrn) : 0)) + (MapPart.VIRTUAL_MOD_MAP.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(vmodmapRtrn) : 0);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetMapReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte deviceID;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private byte minKeyCode;
    @java.lang.SuppressWarnings("all")
    private byte maxKeyCode;
    @java.lang.SuppressWarnings("all")
    private short present;
    @java.lang.SuppressWarnings("all")
    private byte firstType;
    @java.lang.SuppressWarnings("all")
    private byte totalTypes;
    @java.lang.SuppressWarnings("all")
    private byte firstKeySym;
    @java.lang.SuppressWarnings("all")
    private short totalSyms;
    @java.lang.SuppressWarnings("all")
    private byte firstKeyAction;
    @java.lang.SuppressWarnings("all")
    private byte firstKeyBehavior;
    @java.lang.SuppressWarnings("all")
    private byte nKeyBehaviors;
    @java.lang.SuppressWarnings("all")
    private byte firstKeyExplicit;
    @java.lang.SuppressWarnings("all")
    private byte nKeyExplicit;
    @java.lang.SuppressWarnings("all")
    private byte firstModMapKey;
    @java.lang.SuppressWarnings("all")
    private byte nModMapKeys;
    @java.lang.SuppressWarnings("all")
    private byte firstVModMapKey;
    @java.lang.SuppressWarnings("all")
    private byte nVModMapKeys;
    @java.lang.SuppressWarnings("all")
    private List<KeyType> typesRtrn;
    @java.lang.SuppressWarnings("all")
    private List<KeySymMap> symsRtrn;
    @java.lang.SuppressWarnings("all")
    private byte[] actsRtrnCount;
    @java.lang.SuppressWarnings("all")
    private List<ActionUnion> actsRtrnActs;
    @java.lang.SuppressWarnings("all")
    private List<SetBehavior> behaviorsRtrn;
    @java.lang.SuppressWarnings("all")
    private byte[] vmodsRtrn;
    @java.lang.SuppressWarnings("all")
    private List<SetExplicit> explicitRtrn;
    @java.lang.SuppressWarnings("all")
    private List<KeyModMap> modmapRtrn;
    @java.lang.SuppressWarnings("all")
    private List<KeyVModMap> vmodmapRtrn;

    public boolean isPresentEnabled(MapPart... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (MapPart m : maskEnums) {
        if (!m.isEnabled(present)) {
          return false;
        }
      }
      return true;
    }

    public GetMapReply.GetMapReplyBuilder presentEnable(MapPart... maskEnums) {
      for (MapPart m : maskEnums) {
        present((short) m.enableFor(present));
      }
      return this;
    }

    public GetMapReply.GetMapReplyBuilder presentDisable(MapPart... maskEnums) {
      for (MapPart m : maskEnums) {
        present((short) m.disableFor(present));
      }
      return this;
    }

    public GetMapReply.GetMapReplyBuilder typesRtrn(List<KeyType> typesRtrn) {
      this.typesRtrn = typesRtrn;
      presentEnable(MapPart.KEY_TYPES);
      return this;
    }

    public GetMapReply.GetMapReplyBuilder symsRtrn(List<KeySymMap> symsRtrn) {
      this.symsRtrn = symsRtrn;
      presentEnable(MapPart.KEY_SYMS);
      return this;
    }

    public GetMapReply.GetMapReplyBuilder actsRtrnCount(byte[] actsRtrnCount) {
      this.actsRtrnCount = actsRtrnCount;
      presentEnable(MapPart.KEY_ACTIONS);
      return this;
    }

    public GetMapReply.GetMapReplyBuilder actsRtrnActs(List<ActionUnion> actsRtrnActs) {
      this.actsRtrnActs = actsRtrnActs;
      presentEnable(MapPart.KEY_ACTIONS);
      return this;
    }

    public GetMapReply.GetMapReplyBuilder behaviorsRtrn(List<SetBehavior> behaviorsRtrn) {
      this.behaviorsRtrn = behaviorsRtrn;
      presentEnable(MapPart.KEY_BEHAVIORS);
      return this;
    }

    public GetMapReply.GetMapReplyBuilder vmodsRtrn(byte[] vmodsRtrn) {
      this.vmodsRtrn = vmodsRtrn;
      presentEnable(MapPart.VIRTUAL_MODS);
      return this;
    }

    public GetMapReply.GetMapReplyBuilder explicitRtrn(List<SetExplicit> explicitRtrn) {
      this.explicitRtrn = explicitRtrn;
      presentEnable(MapPart.EXPLICIT_COMPONENTS);
      return this;
    }

    public GetMapReply.GetMapReplyBuilder modmapRtrn(List<KeyModMap> modmapRtrn) {
      this.modmapRtrn = modmapRtrn;
      presentEnable(MapPart.MODIFIER_MAP);
      return this;
    }

    public GetMapReply.GetMapReplyBuilder vmodmapRtrn(List<KeyVModMap> vmodmapRtrn) {
      this.vmodmapRtrn = vmodmapRtrn;
      presentEnable(MapPart.VIRTUAL_MOD_MAP);
      return this;
    }

    public int getSize() {
      return 40 + (MapPart.KEY_TYPES.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(typesRtrn) : 0) + (MapPart.KEY_SYMS.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(symsRtrn) : 0) + (MapPart.KEY_ACTIONS.isEnabled(Short.toUnsignedInt(present)) ? 1 * actsRtrnCount.length : 0) + XObject.getSizeForPadAlign(4, (MapPart.KEY_ACTIONS.isEnabled(Short.toUnsignedInt(present)) ? 1 * actsRtrnCount.length : 0)) + (MapPart.KEY_ACTIONS.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(actsRtrnActs) : 0) + (MapPart.KEY_BEHAVIORS.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(behaviorsRtrn) : 0) + (MapPart.VIRTUAL_MODS.isEnabled(Short.toUnsignedInt(present)) ? 1 * vmodsRtrn.length : 0) + XObject.getSizeForPadAlign(4, (MapPart.VIRTUAL_MODS.isEnabled(Short.toUnsignedInt(present)) ? 1 * vmodsRtrn.length : 0)) + (MapPart.EXPLICIT_COMPONENTS.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(explicitRtrn) : 0) + XObject.getSizeForPadAlign(4, (MapPart.EXPLICIT_COMPONENTS.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(explicitRtrn) : 0)) + (MapPart.MODIFIER_MAP.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(modmapRtrn) : 0) + XObject.getSizeForPadAlign(4, (MapPart.MODIFIER_MAP.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(modmapRtrn) : 0)) + (MapPart.VIRTUAL_MOD_MAP.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(vmodmapRtrn) : 0);
    }

    @java.lang.SuppressWarnings("all")
    GetMapReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMapReply.GetMapReplyBuilder deviceID(final byte deviceID) {
      this.deviceID = deviceID;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMapReply.GetMapReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMapReply.GetMapReplyBuilder minKeyCode(final byte minKeyCode) {
      this.minKeyCode = minKeyCode;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMapReply.GetMapReplyBuilder maxKeyCode(final byte maxKeyCode) {
      this.maxKeyCode = maxKeyCode;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMapReply.GetMapReplyBuilder present(final short present) {
      this.present = present;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMapReply.GetMapReplyBuilder firstType(final byte firstType) {
      this.firstType = firstType;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMapReply.GetMapReplyBuilder totalTypes(final byte totalTypes) {
      this.totalTypes = totalTypes;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMapReply.GetMapReplyBuilder firstKeySym(final byte firstKeySym) {
      this.firstKeySym = firstKeySym;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMapReply.GetMapReplyBuilder totalSyms(final short totalSyms) {
      this.totalSyms = totalSyms;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMapReply.GetMapReplyBuilder firstKeyAction(final byte firstKeyAction) {
      this.firstKeyAction = firstKeyAction;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMapReply.GetMapReplyBuilder firstKeyBehavior(final byte firstKeyBehavior) {
      this.firstKeyBehavior = firstKeyBehavior;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMapReply.GetMapReplyBuilder nKeyBehaviors(final byte nKeyBehaviors) {
      this.nKeyBehaviors = nKeyBehaviors;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMapReply.GetMapReplyBuilder firstKeyExplicit(final byte firstKeyExplicit) {
      this.firstKeyExplicit = firstKeyExplicit;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMapReply.GetMapReplyBuilder nKeyExplicit(final byte nKeyExplicit) {
      this.nKeyExplicit = nKeyExplicit;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMapReply.GetMapReplyBuilder firstModMapKey(final byte firstModMapKey) {
      this.firstModMapKey = firstModMapKey;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMapReply.GetMapReplyBuilder nModMapKeys(final byte nModMapKeys) {
      this.nModMapKeys = nModMapKeys;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMapReply.GetMapReplyBuilder firstVModMapKey(final byte firstVModMapKey) {
      this.firstVModMapKey = firstVModMapKey;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMapReply.GetMapReplyBuilder nVModMapKeys(final byte nVModMapKeys) {
      this.nVModMapKeys = nVModMapKeys;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetMapReply build() {
      return new GetMapReply(this.deviceID, this.sequenceNumber, this.minKeyCode, this.maxKeyCode, this.present, this.firstType, this.totalTypes, this.firstKeySym, this.totalSyms, this.firstKeyAction, this.firstKeyBehavior, this.nKeyBehaviors, this.firstKeyExplicit, this.nKeyExplicit, this.firstModMapKey, this.nModMapKeys, this.firstVModMapKey, this.nVModMapKeys, this.typesRtrn, this.symsRtrn, this.actsRtrnCount, this.actsRtrnActs, this.behaviorsRtrn, this.vmodsRtrn, this.explicitRtrn, this.modmapRtrn, this.vmodmapRtrn);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetMapReply.GetMapReplyBuilder(deviceID=" + this.deviceID + ", sequenceNumber=" + this.sequenceNumber + ", minKeyCode=" + this.minKeyCode + ", maxKeyCode=" + this.maxKeyCode + ", present=" + this.present + ", firstType=" + this.firstType + ", totalTypes=" + this.totalTypes + ", firstKeySym=" + this.firstKeySym + ", totalSyms=" + this.totalSyms + ", firstKeyAction=" + this.firstKeyAction + ", firstKeyBehavior=" + this.firstKeyBehavior + ", nKeyBehaviors=" + this.nKeyBehaviors + ", firstKeyExplicit=" + this.firstKeyExplicit + ", nKeyExplicit=" + this.nKeyExplicit + ", firstModMapKey=" + this.firstModMapKey + ", nModMapKeys=" + this.nModMapKeys + ", firstVModMapKey=" + this.firstVModMapKey + ", nVModMapKeys=" + this.nVModMapKeys + ", typesRtrn=" + this.typesRtrn + ", symsRtrn=" + this.symsRtrn + ", actsRtrnCount=" + java.util.Arrays.toString(this.actsRtrnCount) + ", actsRtrnActs=" + this.actsRtrnActs + ", behaviorsRtrn=" + this.behaviorsRtrn + ", vmodsRtrn=" + java.util.Arrays.toString(this.vmodsRtrn) + ", explicitRtrn=" + this.explicitRtrn + ", modmapRtrn=" + this.modmapRtrn + ", vmodmapRtrn=" + this.vmodmapRtrn + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetMapReply(final byte deviceID, final short sequenceNumber, final byte minKeyCode, final byte maxKeyCode, final short present, final byte firstType, final byte totalTypes, final byte firstKeySym, final short totalSyms, final byte firstKeyAction, final byte firstKeyBehavior, final byte nKeyBehaviors, final byte firstKeyExplicit, final byte nKeyExplicit, final byte firstModMapKey, final byte nModMapKeys, final byte firstVModMapKey, final byte nVModMapKeys, final List<KeyType> typesRtrn, final List<KeySymMap> symsRtrn, final byte[] actsRtrnCount, final List<ActionUnion> actsRtrnActs, final List<SetBehavior> behaviorsRtrn, final byte[] vmodsRtrn, final List<SetExplicit> explicitRtrn, final List<KeyModMap> modmapRtrn, final List<KeyVModMap> vmodmapRtrn) {
    if (typesRtrn == null) {
      throw new java.lang.NullPointerException("typesRtrn is marked non-null but is null");
    }
    if (symsRtrn == null) {
      throw new java.lang.NullPointerException("symsRtrn is marked non-null but is null");
    }
    if (actsRtrnCount == null) {
      throw new java.lang.NullPointerException("actsRtrnCount is marked non-null but is null");
    }
    if (actsRtrnActs == null) {
      throw new java.lang.NullPointerException("actsRtrnActs is marked non-null but is null");
    }
    if (behaviorsRtrn == null) {
      throw new java.lang.NullPointerException("behaviorsRtrn is marked non-null but is null");
    }
    if (vmodsRtrn == null) {
      throw new java.lang.NullPointerException("vmodsRtrn is marked non-null but is null");
    }
    if (explicitRtrn == null) {
      throw new java.lang.NullPointerException("explicitRtrn is marked non-null but is null");
    }
    if (modmapRtrn == null) {
      throw new java.lang.NullPointerException("modmapRtrn is marked non-null but is null");
    }
    if (vmodmapRtrn == null) {
      throw new java.lang.NullPointerException("vmodmapRtrn is marked non-null but is null");
    }
    this.deviceID = deviceID;
    this.sequenceNumber = sequenceNumber;
    this.minKeyCode = minKeyCode;
    this.maxKeyCode = maxKeyCode;
    this.present = present;
    this.firstType = firstType;
    this.totalTypes = totalTypes;
    this.firstKeySym = firstKeySym;
    this.totalSyms = totalSyms;
    this.firstKeyAction = firstKeyAction;
    this.firstKeyBehavior = firstKeyBehavior;
    this.nKeyBehaviors = nKeyBehaviors;
    this.firstKeyExplicit = firstKeyExplicit;
    this.nKeyExplicit = nKeyExplicit;
    this.firstModMapKey = firstModMapKey;
    this.nModMapKeys = nModMapKeys;
    this.firstVModMapKey = firstVModMapKey;
    this.nVModMapKeys = nVModMapKeys;
    this.typesRtrn = typesRtrn;
    this.symsRtrn = symsRtrn;
    this.actsRtrnCount = actsRtrnCount;
    this.actsRtrnActs = actsRtrnActs;
    this.behaviorsRtrn = behaviorsRtrn;
    this.vmodsRtrn = vmodsRtrn;
    this.explicitRtrn = explicitRtrn;
    this.modmapRtrn = modmapRtrn;
    this.vmodmapRtrn = vmodmapRtrn;
  }

  @java.lang.SuppressWarnings("all")
  public static GetMapReply.GetMapReplyBuilder builder() {
    return new GetMapReply.GetMapReplyBuilder();
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
  public byte getMinKeyCode() {
    return this.minKeyCode;
  }

  @java.lang.SuppressWarnings("all")
  public byte getMaxKeyCode() {
    return this.maxKeyCode;
  }

  @java.lang.SuppressWarnings("all")
  public short getPresent() {
    return this.present;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFirstType() {
    return this.firstType;
  }

  @java.lang.SuppressWarnings("all")
  public byte getTotalTypes() {
    return this.totalTypes;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFirstKeySym() {
    return this.firstKeySym;
  }

  @java.lang.SuppressWarnings("all")
  public short getTotalSyms() {
    return this.totalSyms;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFirstKeyAction() {
    return this.firstKeyAction;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFirstKeyBehavior() {
    return this.firstKeyBehavior;
  }

  @java.lang.SuppressWarnings("all")
  public byte getNKeyBehaviors() {
    return this.nKeyBehaviors;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFirstKeyExplicit() {
    return this.firstKeyExplicit;
  }

  @java.lang.SuppressWarnings("all")
  public byte getNKeyExplicit() {
    return this.nKeyExplicit;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFirstModMapKey() {
    return this.firstModMapKey;
  }

  @java.lang.SuppressWarnings("all")
  public byte getNModMapKeys() {
    return this.nModMapKeys;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFirstVModMapKey() {
    return this.firstVModMapKey;
  }

  @java.lang.SuppressWarnings("all")
  public byte getNVModMapKeys() {
    return this.nVModMapKeys;
  }

  @java.lang.SuppressWarnings("all")
  public List<KeyType> getTypesRtrn() {
    return this.typesRtrn;
  }

  @java.lang.SuppressWarnings("all")
  public List<KeySymMap> getSymsRtrn() {
    return this.symsRtrn;
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getActsRtrnCount() {
    return this.actsRtrnCount;
  }

  @java.lang.SuppressWarnings("all")
  public List<ActionUnion> getActsRtrnActs() {
    return this.actsRtrnActs;
  }

  @java.lang.SuppressWarnings("all")
  public List<SetBehavior> getBehaviorsRtrn() {
    return this.behaviorsRtrn;
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getVmodsRtrn() {
    return this.vmodsRtrn;
  }

  @java.lang.SuppressWarnings("all")
  public List<SetExplicit> getExplicitRtrn() {
    return this.explicitRtrn;
  }

  @java.lang.SuppressWarnings("all")
  public List<KeyModMap> getModmapRtrn() {
    return this.modmapRtrn;
  }

  @java.lang.SuppressWarnings("all")
  public List<KeyVModMap> getVmodmapRtrn() {
    return this.vmodmapRtrn;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetMapReply)) return false;
    final GetMapReply other = (GetMapReply) o;
    if (this.getDeviceID() != other.getDeviceID()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getMinKeyCode() != other.getMinKeyCode()) return false;
    if (this.getMaxKeyCode() != other.getMaxKeyCode()) return false;
    if (this.getPresent() != other.getPresent()) return false;
    if (this.getFirstType() != other.getFirstType()) return false;
    if (this.getTotalTypes() != other.getTotalTypes()) return false;
    if (this.getFirstKeySym() != other.getFirstKeySym()) return false;
    if (this.getTotalSyms() != other.getTotalSyms()) return false;
    if (this.getFirstKeyAction() != other.getFirstKeyAction()) return false;
    if (this.getFirstKeyBehavior() != other.getFirstKeyBehavior()) return false;
    if (this.getNKeyBehaviors() != other.getNKeyBehaviors()) return false;
    if (this.getFirstKeyExplicit() != other.getFirstKeyExplicit()) return false;
    if (this.getNKeyExplicit() != other.getNKeyExplicit()) return false;
    if (this.getFirstModMapKey() != other.getFirstModMapKey()) return false;
    if (this.getNModMapKeys() != other.getNModMapKeys()) return false;
    if (this.getFirstVModMapKey() != other.getFirstVModMapKey()) return false;
    if (this.getNVModMapKeys() != other.getNVModMapKeys()) return false;
    final java.lang.Object this$typesRtrn = this.getTypesRtrn();
    final java.lang.Object other$typesRtrn = other.getTypesRtrn();
    if (this$typesRtrn == null ? other$typesRtrn != null : !this$typesRtrn.equals(other$typesRtrn)) return false;
    final java.lang.Object this$symsRtrn = this.getSymsRtrn();
    final java.lang.Object other$symsRtrn = other.getSymsRtrn();
    if (this$symsRtrn == null ? other$symsRtrn != null : !this$symsRtrn.equals(other$symsRtrn)) return false;
    if (!java.util.Arrays.equals(this.getActsRtrnCount(), other.getActsRtrnCount())) return false;
    final java.lang.Object this$actsRtrnActs = this.getActsRtrnActs();
    final java.lang.Object other$actsRtrnActs = other.getActsRtrnActs();
    if (this$actsRtrnActs == null ? other$actsRtrnActs != null : !this$actsRtrnActs.equals(other$actsRtrnActs)) return false;
    final java.lang.Object this$behaviorsRtrn = this.getBehaviorsRtrn();
    final java.lang.Object other$behaviorsRtrn = other.getBehaviorsRtrn();
    if (this$behaviorsRtrn == null ? other$behaviorsRtrn != null : !this$behaviorsRtrn.equals(other$behaviorsRtrn)) return false;
    if (!java.util.Arrays.equals(this.getVmodsRtrn(), other.getVmodsRtrn())) return false;
    final java.lang.Object this$explicitRtrn = this.getExplicitRtrn();
    final java.lang.Object other$explicitRtrn = other.getExplicitRtrn();
    if (this$explicitRtrn == null ? other$explicitRtrn != null : !this$explicitRtrn.equals(other$explicitRtrn)) return false;
    final java.lang.Object this$modmapRtrn = this.getModmapRtrn();
    final java.lang.Object other$modmapRtrn = other.getModmapRtrn();
    if (this$modmapRtrn == null ? other$modmapRtrn != null : !this$modmapRtrn.equals(other$modmapRtrn)) return false;
    final java.lang.Object this$vmodmapRtrn = this.getVmodmapRtrn();
    final java.lang.Object other$vmodmapRtrn = other.getVmodmapRtrn();
    if (this$vmodmapRtrn == null ? other$vmodmapRtrn != null : !this$vmodmapRtrn.equals(other$vmodmapRtrn)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDeviceID();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getMinKeyCode();
    result = result * PRIME + this.getMaxKeyCode();
    result = result * PRIME + this.getPresent();
    result = result * PRIME + this.getFirstType();
    result = result * PRIME + this.getTotalTypes();
    result = result * PRIME + this.getFirstKeySym();
    result = result * PRIME + this.getTotalSyms();
    result = result * PRIME + this.getFirstKeyAction();
    result = result * PRIME + this.getFirstKeyBehavior();
    result = result * PRIME + this.getNKeyBehaviors();
    result = result * PRIME + this.getFirstKeyExplicit();
    result = result * PRIME + this.getNKeyExplicit();
    result = result * PRIME + this.getFirstModMapKey();
    result = result * PRIME + this.getNModMapKeys();
    result = result * PRIME + this.getFirstVModMapKey();
    result = result * PRIME + this.getNVModMapKeys();
    final java.lang.Object $typesRtrn = this.getTypesRtrn();
    result = result * PRIME + ($typesRtrn == null ? 43 : $typesRtrn.hashCode());
    final java.lang.Object $symsRtrn = this.getSymsRtrn();
    result = result * PRIME + ($symsRtrn == null ? 43 : $symsRtrn.hashCode());
    result = result * PRIME + java.util.Arrays.hashCode(this.getActsRtrnCount());
    final java.lang.Object $actsRtrnActs = this.getActsRtrnActs();
    result = result * PRIME + ($actsRtrnActs == null ? 43 : $actsRtrnActs.hashCode());
    final java.lang.Object $behaviorsRtrn = this.getBehaviorsRtrn();
    result = result * PRIME + ($behaviorsRtrn == null ? 43 : $behaviorsRtrn.hashCode());
    result = result * PRIME + java.util.Arrays.hashCode(this.getVmodsRtrn());
    final java.lang.Object $explicitRtrn = this.getExplicitRtrn();
    result = result * PRIME + ($explicitRtrn == null ? 43 : $explicitRtrn.hashCode());
    final java.lang.Object $modmapRtrn = this.getModmapRtrn();
    result = result * PRIME + ($modmapRtrn == null ? 43 : $modmapRtrn.hashCode());
    final java.lang.Object $vmodmapRtrn = this.getVmodmapRtrn();
    result = result * PRIME + ($vmodmapRtrn == null ? 43 : $vmodmapRtrn.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetMapReply(deviceID=" + this.getDeviceID() + ", sequenceNumber=" + this.getSequenceNumber() + ", minKeyCode=" + this.getMinKeyCode() + ", maxKeyCode=" + this.getMaxKeyCode() + ", present=" + this.getPresent() + ", firstType=" + this.getFirstType() + ", totalTypes=" + this.getTotalTypes() + ", firstKeySym=" + this.getFirstKeySym() + ", totalSyms=" + this.getTotalSyms() + ", firstKeyAction=" + this.getFirstKeyAction() + ", firstKeyBehavior=" + this.getFirstKeyBehavior() + ", nKeyBehaviors=" + this.getNKeyBehaviors() + ", firstKeyExplicit=" + this.getFirstKeyExplicit() + ", nKeyExplicit=" + this.getNKeyExplicit() + ", firstModMapKey=" + this.getFirstModMapKey() + ", nModMapKeys=" + this.getNModMapKeys() + ", firstVModMapKey=" + this.getFirstVModMapKey() + ", nVModMapKeys=" + this.getNVModMapKeys() + ", typesRtrn=" + this.getTypesRtrn() + ", symsRtrn=" + this.getSymsRtrn() + ", actsRtrnCount=" + java.util.Arrays.toString(this.getActsRtrnCount()) + ", actsRtrnActs=" + this.getActsRtrnActs() + ", behaviorsRtrn=" + this.getBehaviorsRtrn() + ", vmodsRtrn=" + java.util.Arrays.toString(this.getVmodsRtrn()) + ", explicitRtrn=" + this.getExplicitRtrn() + ", modmapRtrn=" + this.getModmapRtrn() + ", vmodmapRtrn=" + this.getVmodmapRtrn() + ")";
  }
}
