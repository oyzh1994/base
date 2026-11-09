package com.github.moaxcp.x11.protocol.xkb;

import java.util.List;
import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.Popcount;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;

public final class SetMap implements OneWayRequest {
  public static final String PLUGIN_NAME = "xkb";
  public static final byte OPCODE = 9;
  private final short deviceSpec;
  private final short present;
  private final short flags;
  private final byte minKeyCode;
  private final byte maxKeyCode;
  private final byte firstType;
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
  private final List<SetKeyType> types;
  private final List<KeySymMap> syms;
  private final byte[] actionsCount;
  private final List<ActionUnion> actions;
  private final List<SetBehavior> behaviors;
  private final byte[] vmods;
  private final List<SetExplicit> explicit;
  private final List<KeyModMap> modmap;
  private final List<KeyVModMap> vmodmap;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetMap readSetMap(X11Input in) throws IOException {
    SetMap.SetMapBuilder javaBuilder = SetMap.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short deviceSpec = in.readCard16();
    short present = in.readCard16();
    short flags = in.readCard16();
    byte minKeyCode = in.readCard8();
    byte maxKeyCode = in.readCard8();
    byte firstType = in.readCard8();
    byte nTypes = in.readCard8();
    byte firstKeySym = in.readCard8();
    byte nKeySyms = in.readCard8();
    short totalSyms = in.readCard16();
    byte firstKeyAction = in.readCard8();
    byte nKeyActions = in.readCard8();
    short totalActions = in.readCard16();
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
    short virtualMods = in.readCard16();
    List<SetKeyType> types = null;
    List<KeySymMap> syms = null;
    byte[] actionsCount = null;
    in.readPadAlign(Byte.toUnsignedInt(nKeyActions));
    List<ActionUnion> actions = null;
    List<SetBehavior> behaviors = null;
    byte[] vmods = null;
    in.readPadAlign(com.github.moaxcp.x11.protocol.Popcount.popcount(Short.toUnsignedInt(virtualMods)));
    List<SetExplicit> explicit = null;
    List<KeyModMap> modmap = null;
    List<KeyVModMap> vmodmap = null;
    javaBuilder.deviceSpec(deviceSpec);
    javaBuilder.present(present);
    javaBuilder.flags(flags);
    javaBuilder.minKeyCode(minKeyCode);
    javaBuilder.maxKeyCode(maxKeyCode);
    javaBuilder.firstType(firstType);
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
      types = in.readObject(SetKeyType::readSetKeyType, Byte.toUnsignedInt(nTypes));
      javaBuilder.types(types);
    }
    if (MapPart.KEY_SYMS.isEnabled(Short.toUnsignedInt(present))) {
      syms = in.readObject(KeySymMap::readKeySymMap, Byte.toUnsignedInt(nKeySyms));
      javaBuilder.syms(syms);
    }
    if (MapPart.KEY_ACTIONS.isEnabled(Short.toUnsignedInt(present))) {
      actionsCount = in.readCard8(Byte.toUnsignedInt(nKeyActions));
      javaBuilder.actionsCount(actionsCount);
    }
    if (MapPart.KEY_ACTIONS.isEnabled(Short.toUnsignedInt(present))) {
      actions = in.readObject(ActionUnion::readActionUnion, Short.toUnsignedInt(totalActions));
      javaBuilder.actions(actions);
    }
    if (MapPart.KEY_BEHAVIORS.isEnabled(Short.toUnsignedInt(present))) {
      behaviors = in.readObject(SetBehavior::readSetBehavior, Byte.toUnsignedInt(totalKeyBehaviors));
      javaBuilder.behaviors(behaviors);
    }
    if (MapPart.VIRTUAL_MODS.isEnabled(Short.toUnsignedInt(present))) {
      vmods = in.readCard8(Popcount.popcount(Short.toUnsignedInt(virtualMods)));
      javaBuilder.vmods(vmods);
    }
    if (MapPart.EXPLICIT_COMPONENTS.isEnabled(Short.toUnsignedInt(present))) {
      explicit = in.readObject(SetExplicit::readSetExplicit, Byte.toUnsignedInt(totalKeyExplicit));
      javaBuilder.explicit(explicit);
    }
    if (MapPart.MODIFIER_MAP.isEnabled(Short.toUnsignedInt(present))) {
      modmap = in.readObject(KeyModMap::readKeyModMap, Byte.toUnsignedInt(totalModMapKeys));
      javaBuilder.modmap(modmap);
    }
    if (MapPart.VIRTUAL_MOD_MAP.isEnabled(Short.toUnsignedInt(present))) {
      vmodmap = in.readObject(KeyVModMap::readKeyVModMap, Byte.toUnsignedInt(totalVModMapKeys));
      javaBuilder.vmodmap(vmodmap);
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
    out.writeCard16(present);
    out.writeCard16(flags);
    out.writeCard8(minKeyCode);
    out.writeCard8(maxKeyCode);
    out.writeCard8(firstType);
    byte nTypes = (byte) types.size();
    out.writeCard8(nTypes);
    out.writeCard8(firstKeySym);
    byte nKeySyms = (byte) syms.size();
    out.writeCard8(nKeySyms);
    out.writeCard16(totalSyms);
    out.writeCard8(firstKeyAction);
    byte nKeyActions = (byte) actionsCount.length;
    out.writeCard8(nKeyActions);
    short totalActions = (short) actions.size();
    out.writeCard16(totalActions);
    out.writeCard8(firstKeyBehavior);
    out.writeCard8(nKeyBehaviors);
    byte totalKeyBehaviors = (byte) behaviors.size();
    out.writeCard8(totalKeyBehaviors);
    out.writeCard8(firstKeyExplicit);
    out.writeCard8(nKeyExplicit);
    byte totalKeyExplicit = (byte) explicit.size();
    out.writeCard8(totalKeyExplicit);
    out.writeCard8(firstModMapKey);
    out.writeCard8(nModMapKeys);
    byte totalModMapKeys = (byte) modmap.size();
    out.writeCard8(totalModMapKeys);
    out.writeCard8(firstVModMapKey);
    out.writeCard8(nVModMapKeys);
    byte totalVModMapKeys = (byte) vmodmap.size();
    out.writeCard8(totalVModMapKeys);
    short virtualMods = (short) vmods.length;
    out.writeCard16(virtualMods);
    if (MapPart.KEY_TYPES.isEnabled(Short.toUnsignedInt(present))) {
      for (SetKeyType t : types) {
        t.write(out);
      }
    }
    if (MapPart.KEY_SYMS.isEnabled(Short.toUnsignedInt(present))) {
      for (KeySymMap t : syms) {
        t.write(out);
      }
    }
    if (MapPart.KEY_ACTIONS.isEnabled(Short.toUnsignedInt(present))) {
      out.writeCard8(actionsCount);
    }
    out.writePadAlign(Byte.toUnsignedInt(nKeyActions));
    if (MapPart.KEY_ACTIONS.isEnabled(Short.toUnsignedInt(present))) {
      for (ActionUnion t : actions) {
        t.write(out);
      }
    }
    if (MapPart.KEY_BEHAVIORS.isEnabled(Short.toUnsignedInt(present))) {
      for (SetBehavior t : behaviors) {
        t.write(out);
      }
    }
    if (MapPart.VIRTUAL_MODS.isEnabled(Short.toUnsignedInt(present))) {
      out.writeCard8(vmods);
    }
    out.writePadAlign(com.github.moaxcp.x11.protocol.Popcount.popcount(Short.toUnsignedInt(virtualMods)));
    if (MapPart.EXPLICIT_COMPONENTS.isEnabled(Short.toUnsignedInt(present))) {
      for (SetExplicit t : explicit) {
        t.write(out);
      }
    }
    if (MapPart.MODIFIER_MAP.isEnabled(Short.toUnsignedInt(present))) {
      for (KeyModMap t : modmap) {
        t.write(out);
      }
    }
    if (MapPart.VIRTUAL_MOD_MAP.isEnabled(Short.toUnsignedInt(present))) {
      for (KeyVModMap t : vmodmap) {
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

  public boolean isFlagsEnabled(SetMapFlags... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (SetMapFlags m : maskEnums) {
      if (!m.isEnabled(flags)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 36 + (MapPart.KEY_TYPES.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(types) : 0) + (MapPart.KEY_SYMS.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(syms) : 0) + (MapPart.KEY_ACTIONS.isEnabled(Short.toUnsignedInt(present)) ? 1 * actionsCount.length : 0) + XObject.getSizeForPadAlign(4, (MapPart.KEY_ACTIONS.isEnabled(Short.toUnsignedInt(present)) ? 1 * actionsCount.length : 0)) + (MapPart.KEY_ACTIONS.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(actions) : 0) + (MapPart.KEY_BEHAVIORS.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(behaviors) : 0) + (MapPart.VIRTUAL_MODS.isEnabled(Short.toUnsignedInt(present)) ? 1 * vmods.length : 0) + XObject.getSizeForPadAlign(4, (MapPart.VIRTUAL_MODS.isEnabled(Short.toUnsignedInt(present)) ? 1 * vmods.length : 0)) + (MapPart.EXPLICIT_COMPONENTS.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(explicit) : 0) + (MapPart.MODIFIER_MAP.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(modmap) : 0) + (MapPart.VIRTUAL_MOD_MAP.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(vmodmap) : 0);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetMapBuilder {
    @java.lang.SuppressWarnings("all")
    private short deviceSpec;
    @java.lang.SuppressWarnings("all")
    private short present;
    @java.lang.SuppressWarnings("all")
    private short flags;
    @java.lang.SuppressWarnings("all")
    private byte minKeyCode;
    @java.lang.SuppressWarnings("all")
    private byte maxKeyCode;
    @java.lang.SuppressWarnings("all")
    private byte firstType;
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
    private List<SetKeyType> types;
    @java.lang.SuppressWarnings("all")
    private List<KeySymMap> syms;
    @java.lang.SuppressWarnings("all")
    private byte[] actionsCount;
    @java.lang.SuppressWarnings("all")
    private List<ActionUnion> actions;
    @java.lang.SuppressWarnings("all")
    private List<SetBehavior> behaviors;
    @java.lang.SuppressWarnings("all")
    private byte[] vmods;
    @java.lang.SuppressWarnings("all")
    private List<SetExplicit> explicit;
    @java.lang.SuppressWarnings("all")
    private List<KeyModMap> modmap;
    @java.lang.SuppressWarnings("all")
    private List<KeyVModMap> vmodmap;

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

    public SetMap.SetMapBuilder presentEnable(MapPart... maskEnums) {
      for (MapPart m : maskEnums) {
        present((short) m.enableFor(present));
      }
      return this;
    }

    public SetMap.SetMapBuilder presentDisable(MapPart... maskEnums) {
      for (MapPart m : maskEnums) {
        present((short) m.disableFor(present));
      }
      return this;
    }

    public boolean isFlagsEnabled(SetMapFlags... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (SetMapFlags m : maskEnums) {
        if (!m.isEnabled(flags)) {
          return false;
        }
      }
      return true;
    }

    public SetMap.SetMapBuilder flagsEnable(SetMapFlags... maskEnums) {
      for (SetMapFlags m : maskEnums) {
        flags((short) m.enableFor(flags));
      }
      return this;
    }

    public SetMap.SetMapBuilder flagsDisable(SetMapFlags... maskEnums) {
      for (SetMapFlags m : maskEnums) {
        flags((short) m.disableFor(flags));
      }
      return this;
    }

    public SetMap.SetMapBuilder types(List<SetKeyType> types) {
      this.types = types;
      presentEnable(MapPart.KEY_TYPES);
      return this;
    }

    public SetMap.SetMapBuilder syms(List<KeySymMap> syms) {
      this.syms = syms;
      presentEnable(MapPart.KEY_SYMS);
      return this;
    }

    public SetMap.SetMapBuilder actionsCount(byte[] actionsCount) {
      this.actionsCount = actionsCount;
      presentEnable(MapPart.KEY_ACTIONS);
      return this;
    }

    public SetMap.SetMapBuilder actions(List<ActionUnion> actions) {
      this.actions = actions;
      presentEnable(MapPart.KEY_ACTIONS);
      return this;
    }

    public SetMap.SetMapBuilder behaviors(List<SetBehavior> behaviors) {
      this.behaviors = behaviors;
      presentEnable(MapPart.KEY_BEHAVIORS);
      return this;
    }

    public SetMap.SetMapBuilder vmods(byte[] vmods) {
      this.vmods = vmods;
      presentEnable(MapPart.VIRTUAL_MODS);
      return this;
    }

    public SetMap.SetMapBuilder explicit(List<SetExplicit> explicit) {
      this.explicit = explicit;
      presentEnable(MapPart.EXPLICIT_COMPONENTS);
      return this;
    }

    public SetMap.SetMapBuilder modmap(List<KeyModMap> modmap) {
      this.modmap = modmap;
      presentEnable(MapPart.MODIFIER_MAP);
      return this;
    }

    public SetMap.SetMapBuilder vmodmap(List<KeyVModMap> vmodmap) {
      this.vmodmap = vmodmap;
      presentEnable(MapPart.VIRTUAL_MOD_MAP);
      return this;
    }

    public int getSize() {
      return 36 + (MapPart.KEY_TYPES.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(types) : 0) + (MapPart.KEY_SYMS.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(syms) : 0) + (MapPart.KEY_ACTIONS.isEnabled(Short.toUnsignedInt(present)) ? 1 * actionsCount.length : 0) + XObject.getSizeForPadAlign(4, (MapPart.KEY_ACTIONS.isEnabled(Short.toUnsignedInt(present)) ? 1 * actionsCount.length : 0)) + (MapPart.KEY_ACTIONS.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(actions) : 0) + (MapPart.KEY_BEHAVIORS.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(behaviors) : 0) + (MapPart.VIRTUAL_MODS.isEnabled(Short.toUnsignedInt(present)) ? 1 * vmods.length : 0) + XObject.getSizeForPadAlign(4, (MapPart.VIRTUAL_MODS.isEnabled(Short.toUnsignedInt(present)) ? 1 * vmods.length : 0)) + (MapPart.EXPLICIT_COMPONENTS.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(explicit) : 0) + (MapPart.MODIFIER_MAP.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(modmap) : 0) + (MapPart.VIRTUAL_MOD_MAP.isEnabled(Short.toUnsignedInt(present)) ? XObject.sizeOf(vmodmap) : 0);
    }

    @java.lang.SuppressWarnings("all")
    SetMapBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetMap.SetMapBuilder deviceSpec(final short deviceSpec) {
      this.deviceSpec = deviceSpec;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetMap.SetMapBuilder present(final short present) {
      this.present = present;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetMap.SetMapBuilder flags(final short flags) {
      this.flags = flags;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetMap.SetMapBuilder minKeyCode(final byte minKeyCode) {
      this.minKeyCode = minKeyCode;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetMap.SetMapBuilder maxKeyCode(final byte maxKeyCode) {
      this.maxKeyCode = maxKeyCode;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetMap.SetMapBuilder firstType(final byte firstType) {
      this.firstType = firstType;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetMap.SetMapBuilder firstKeySym(final byte firstKeySym) {
      this.firstKeySym = firstKeySym;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetMap.SetMapBuilder totalSyms(final short totalSyms) {
      this.totalSyms = totalSyms;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetMap.SetMapBuilder firstKeyAction(final byte firstKeyAction) {
      this.firstKeyAction = firstKeyAction;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetMap.SetMapBuilder firstKeyBehavior(final byte firstKeyBehavior) {
      this.firstKeyBehavior = firstKeyBehavior;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetMap.SetMapBuilder nKeyBehaviors(final byte nKeyBehaviors) {
      this.nKeyBehaviors = nKeyBehaviors;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetMap.SetMapBuilder firstKeyExplicit(final byte firstKeyExplicit) {
      this.firstKeyExplicit = firstKeyExplicit;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetMap.SetMapBuilder nKeyExplicit(final byte nKeyExplicit) {
      this.nKeyExplicit = nKeyExplicit;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetMap.SetMapBuilder firstModMapKey(final byte firstModMapKey) {
      this.firstModMapKey = firstModMapKey;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetMap.SetMapBuilder nModMapKeys(final byte nModMapKeys) {
      this.nModMapKeys = nModMapKeys;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetMap.SetMapBuilder firstVModMapKey(final byte firstVModMapKey) {
      this.firstVModMapKey = firstVModMapKey;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetMap.SetMapBuilder nVModMapKeys(final byte nVModMapKeys) {
      this.nVModMapKeys = nVModMapKeys;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetMap build() {
      return new SetMap(this.deviceSpec, this.present, this.flags, this.minKeyCode, this.maxKeyCode, this.firstType, this.firstKeySym, this.totalSyms, this.firstKeyAction, this.firstKeyBehavior, this.nKeyBehaviors, this.firstKeyExplicit, this.nKeyExplicit, this.firstModMapKey, this.nModMapKeys, this.firstVModMapKey, this.nVModMapKeys, this.types, this.syms, this.actionsCount, this.actions, this.behaviors, this.vmods, this.explicit, this.modmap, this.vmodmap);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetMap.SetMapBuilder(deviceSpec=" + this.deviceSpec + ", present=" + this.present + ", flags=" + this.flags + ", minKeyCode=" + this.minKeyCode + ", maxKeyCode=" + this.maxKeyCode + ", firstType=" + this.firstType + ", firstKeySym=" + this.firstKeySym + ", totalSyms=" + this.totalSyms + ", firstKeyAction=" + this.firstKeyAction + ", firstKeyBehavior=" + this.firstKeyBehavior + ", nKeyBehaviors=" + this.nKeyBehaviors + ", firstKeyExplicit=" + this.firstKeyExplicit + ", nKeyExplicit=" + this.nKeyExplicit + ", firstModMapKey=" + this.firstModMapKey + ", nModMapKeys=" + this.nModMapKeys + ", firstVModMapKey=" + this.firstVModMapKey + ", nVModMapKeys=" + this.nVModMapKeys + ", types=" + this.types + ", syms=" + this.syms + ", actionsCount=" + java.util.Arrays.toString(this.actionsCount) + ", actions=" + this.actions + ", behaviors=" + this.behaviors + ", vmods=" + java.util.Arrays.toString(this.vmods) + ", explicit=" + this.explicit + ", modmap=" + this.modmap + ", vmodmap=" + this.vmodmap + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetMap(final short deviceSpec, final short present, final short flags, final byte minKeyCode, final byte maxKeyCode, final byte firstType, final byte firstKeySym, final short totalSyms, final byte firstKeyAction, final byte firstKeyBehavior, final byte nKeyBehaviors, final byte firstKeyExplicit, final byte nKeyExplicit, final byte firstModMapKey, final byte nModMapKeys, final byte firstVModMapKey, final byte nVModMapKeys, final List<SetKeyType> types, final List<KeySymMap> syms, final byte[] actionsCount, final List<ActionUnion> actions, final List<SetBehavior> behaviors, final byte[] vmods, final List<SetExplicit> explicit, final List<KeyModMap> modmap, final List<KeyVModMap> vmodmap) {
    if (types == null) {
      throw new java.lang.NullPointerException("types is marked non-null but is null");
    }
    if (syms == null) {
      throw new java.lang.NullPointerException("syms is marked non-null but is null");
    }
    if (actionsCount == null) {
      throw new java.lang.NullPointerException("actionsCount is marked non-null but is null");
    }
    if (actions == null) {
      throw new java.lang.NullPointerException("actions is marked non-null but is null");
    }
    if (behaviors == null) {
      throw new java.lang.NullPointerException("behaviors is marked non-null but is null");
    }
    if (vmods == null) {
      throw new java.lang.NullPointerException("vmods is marked non-null but is null");
    }
    if (explicit == null) {
      throw new java.lang.NullPointerException("explicit is marked non-null but is null");
    }
    if (modmap == null) {
      throw new java.lang.NullPointerException("modmap is marked non-null but is null");
    }
    if (vmodmap == null) {
      throw new java.lang.NullPointerException("vmodmap is marked non-null but is null");
    }
    this.deviceSpec = deviceSpec;
    this.present = present;
    this.flags = flags;
    this.minKeyCode = minKeyCode;
    this.maxKeyCode = maxKeyCode;
    this.firstType = firstType;
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
    this.types = types;
    this.syms = syms;
    this.actionsCount = actionsCount;
    this.actions = actions;
    this.behaviors = behaviors;
    this.vmods = vmods;
    this.explicit = explicit;
    this.modmap = modmap;
    this.vmodmap = vmodmap;
  }

  @java.lang.SuppressWarnings("all")
  public static SetMap.SetMapBuilder builder() {
    return new SetMap.SetMapBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getDeviceSpec() {
    return this.deviceSpec;
  }

  @java.lang.SuppressWarnings("all")
  public short getPresent() {
    return this.present;
  }

  @java.lang.SuppressWarnings("all")
  public short getFlags() {
    return this.flags;
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
  public byte getFirstType() {
    return this.firstType;
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
  public List<SetKeyType> getTypes() {
    return this.types;
  }

  @java.lang.SuppressWarnings("all")
  public List<KeySymMap> getSyms() {
    return this.syms;
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getActionsCount() {
    return this.actionsCount;
  }

  @java.lang.SuppressWarnings("all")
  public List<ActionUnion> getActions() {
    return this.actions;
  }

  @java.lang.SuppressWarnings("all")
  public List<SetBehavior> getBehaviors() {
    return this.behaviors;
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getVmods() {
    return this.vmods;
  }

  @java.lang.SuppressWarnings("all")
  public List<SetExplicit> getExplicit() {
    return this.explicit;
  }

  @java.lang.SuppressWarnings("all")
  public List<KeyModMap> getModmap() {
    return this.modmap;
  }

  @java.lang.SuppressWarnings("all")
  public List<KeyVModMap> getVmodmap() {
    return this.vmodmap;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetMap)) return false;
    final SetMap other = (SetMap) o;
    if (this.getDeviceSpec() != other.getDeviceSpec()) return false;
    if (this.getPresent() != other.getPresent()) return false;
    if (this.getFlags() != other.getFlags()) return false;
    if (this.getMinKeyCode() != other.getMinKeyCode()) return false;
    if (this.getMaxKeyCode() != other.getMaxKeyCode()) return false;
    if (this.getFirstType() != other.getFirstType()) return false;
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
    final java.lang.Object this$types = this.getTypes();
    final java.lang.Object other$types = other.getTypes();
    if (this$types == null ? other$types != null : !this$types.equals(other$types)) return false;
    final java.lang.Object this$syms = this.getSyms();
    final java.lang.Object other$syms = other.getSyms();
    if (this$syms == null ? other$syms != null : !this$syms.equals(other$syms)) return false;
    if (!java.util.Arrays.equals(this.getActionsCount(), other.getActionsCount())) return false;
    final java.lang.Object this$actions = this.getActions();
    final java.lang.Object other$actions = other.getActions();
    if (this$actions == null ? other$actions != null : !this$actions.equals(other$actions)) return false;
    final java.lang.Object this$behaviors = this.getBehaviors();
    final java.lang.Object other$behaviors = other.getBehaviors();
    if (this$behaviors == null ? other$behaviors != null : !this$behaviors.equals(other$behaviors)) return false;
    if (!java.util.Arrays.equals(this.getVmods(), other.getVmods())) return false;
    final java.lang.Object this$explicit = this.getExplicit();
    final java.lang.Object other$explicit = other.getExplicit();
    if (this$explicit == null ? other$explicit != null : !this$explicit.equals(other$explicit)) return false;
    final java.lang.Object this$modmap = this.getModmap();
    final java.lang.Object other$modmap = other.getModmap();
    if (this$modmap == null ? other$modmap != null : !this$modmap.equals(other$modmap)) return false;
    final java.lang.Object this$vmodmap = this.getVmodmap();
    final java.lang.Object other$vmodmap = other.getVmodmap();
    if (this$vmodmap == null ? other$vmodmap != null : !this$vmodmap.equals(other$vmodmap)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDeviceSpec();
    result = result * PRIME + this.getPresent();
    result = result * PRIME + this.getFlags();
    result = result * PRIME + this.getMinKeyCode();
    result = result * PRIME + this.getMaxKeyCode();
    result = result * PRIME + this.getFirstType();
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
    final java.lang.Object $types = this.getTypes();
    result = result * PRIME + ($types == null ? 43 : $types.hashCode());
    final java.lang.Object $syms = this.getSyms();
    result = result * PRIME + ($syms == null ? 43 : $syms.hashCode());
    result = result * PRIME + java.util.Arrays.hashCode(this.getActionsCount());
    final java.lang.Object $actions = this.getActions();
    result = result * PRIME + ($actions == null ? 43 : $actions.hashCode());
    final java.lang.Object $behaviors = this.getBehaviors();
    result = result * PRIME + ($behaviors == null ? 43 : $behaviors.hashCode());
    result = result * PRIME + java.util.Arrays.hashCode(this.getVmods());
    final java.lang.Object $explicit = this.getExplicit();
    result = result * PRIME + ($explicit == null ? 43 : $explicit.hashCode());
    final java.lang.Object $modmap = this.getModmap();
    result = result * PRIME + ($modmap == null ? 43 : $modmap.hashCode());
    final java.lang.Object $vmodmap = this.getVmodmap();
    result = result * PRIME + ($vmodmap == null ? 43 : $vmodmap.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetMap(deviceSpec=" + this.getDeviceSpec() + ", present=" + this.getPresent() + ", flags=" + this.getFlags() + ", minKeyCode=" + this.getMinKeyCode() + ", maxKeyCode=" + this.getMaxKeyCode() + ", firstType=" + this.getFirstType() + ", firstKeySym=" + this.getFirstKeySym() + ", totalSyms=" + this.getTotalSyms() + ", firstKeyAction=" + this.getFirstKeyAction() + ", firstKeyBehavior=" + this.getFirstKeyBehavior() + ", nKeyBehaviors=" + this.getNKeyBehaviors() + ", firstKeyExplicit=" + this.getFirstKeyExplicit() + ", nKeyExplicit=" + this.getNKeyExplicit() + ", firstModMapKey=" + this.getFirstModMapKey() + ", nModMapKeys=" + this.getNModMapKeys() + ", firstVModMapKey=" + this.getFirstVModMapKey() + ", nVModMapKeys=" + this.getNVModMapKeys() + ", types=" + this.getTypes() + ", syms=" + this.getSyms() + ", actionsCount=" + java.util.Arrays.toString(this.getActionsCount()) + ", actions=" + this.getActions() + ", behaviors=" + this.getBehaviors() + ", vmods=" + java.util.Arrays.toString(this.getVmods()) + ", explicit=" + this.getExplicit() + ", modmap=" + this.getModmap() + ", vmodmap=" + this.getVmodmap() + ")";
  }
}
