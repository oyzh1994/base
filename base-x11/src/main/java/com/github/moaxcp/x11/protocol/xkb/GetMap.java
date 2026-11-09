package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GetMap implements TwoWayRequest<GetMapReply> {
  public static final String PLUGIN_NAME = "xkb";
  public static final byte OPCODE = 8;
  private final short deviceSpec;
  private final short full;
  private final short partial;
  private final byte firstType;
  private final byte nTypes;
  private final byte firstKeySym;
  private final byte nKeySyms;
  private final byte firstKeyAction;
  private final byte nKeyActions;
  private final byte firstKeyBehavior;
  private final byte nKeyBehaviors;
  private final short virtualMods;
  private final byte firstKeyExplicit;
  private final byte nKeyExplicit;
  private final byte firstModMapKey;
  private final byte nModMapKeys;
  private final byte firstVModMapKey;
  private final byte nVModMapKeys;

  public XReplyFunction<GetMapReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetMapReply.readGetMapReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetMap readGetMap(X11Input in) throws IOException {
    GetMap.GetMapBuilder javaBuilder = GetMap.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short deviceSpec = in.readCard16();
    short full = in.readCard16();
    short partial = in.readCard16();
    byte firstType = in.readCard8();
    byte nTypes = in.readCard8();
    byte firstKeySym = in.readCard8();
    byte nKeySyms = in.readCard8();
    byte firstKeyAction = in.readCard8();
    byte nKeyActions = in.readCard8();
    byte firstKeyBehavior = in.readCard8();
    byte nKeyBehaviors = in.readCard8();
    short virtualMods = in.readCard16();
    byte firstKeyExplicit = in.readCard8();
    byte nKeyExplicit = in.readCard8();
    byte firstModMapKey = in.readCard8();
    byte nModMapKeys = in.readCard8();
    byte firstVModMapKey = in.readCard8();
    byte nVModMapKeys = in.readCard8();
    byte[] pad21 = in.readPad(2);
    javaBuilder.deviceSpec(deviceSpec);
    javaBuilder.full(full);
    javaBuilder.partial(partial);
    javaBuilder.firstType(firstType);
    javaBuilder.nTypes(nTypes);
    javaBuilder.firstKeySym(firstKeySym);
    javaBuilder.nKeySyms(nKeySyms);
    javaBuilder.firstKeyAction(firstKeyAction);
    javaBuilder.nKeyActions(nKeyActions);
    javaBuilder.firstKeyBehavior(firstKeyBehavior);
    javaBuilder.nKeyBehaviors(nKeyBehaviors);
    javaBuilder.virtualMods(virtualMods);
    javaBuilder.firstKeyExplicit(firstKeyExplicit);
    javaBuilder.nKeyExplicit(nKeyExplicit);
    javaBuilder.firstModMapKey(firstModMapKey);
    javaBuilder.nModMapKeys(nModMapKeys);
    javaBuilder.firstVModMapKey(firstVModMapKey);
    javaBuilder.nVModMapKeys(nVModMapKeys);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(deviceSpec);
    out.writeCard16(full);
    out.writeCard16(partial);
    out.writeCard8(firstType);
    out.writeCard8(nTypes);
    out.writeCard8(firstKeySym);
    out.writeCard8(nKeySyms);
    out.writeCard8(firstKeyAction);
    out.writeCard8(nKeyActions);
    out.writeCard8(firstKeyBehavior);
    out.writeCard8(nKeyBehaviors);
    out.writeCard16(virtualMods);
    out.writeCard8(firstKeyExplicit);
    out.writeCard8(nKeyExplicit);
    out.writeCard8(firstModMapKey);
    out.writeCard8(nModMapKeys);
    out.writeCard8(firstVModMapKey);
    out.writeCard8(nVModMapKeys);
    out.writePad(2);
  }

  public boolean isFullEnabled(MapPart... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (MapPart m : maskEnums) {
      if (!m.isEnabled(full)) {
        return false;
      }
    }
    return true;
  }

  public boolean isPartialEnabled(MapPart... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (MapPart m : maskEnums) {
      if (!m.isEnabled(partial)) {
        return false;
      }
    }
    return true;
  }

  public boolean isVirtualModsEnabled(VMod... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (VMod m : maskEnums) {
      if (!m.isEnabled(virtualMods)) {
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


  public static class GetMapBuilder {
    @java.lang.SuppressWarnings("all")
    private short deviceSpec;
    @java.lang.SuppressWarnings("all")
    private short full;
    @java.lang.SuppressWarnings("all")
    private short partial;
    @java.lang.SuppressWarnings("all")
    private byte firstType;
    @java.lang.SuppressWarnings("all")
    private byte nTypes;
    @java.lang.SuppressWarnings("all")
    private byte firstKeySym;
    @java.lang.SuppressWarnings("all")
    private byte nKeySyms;
    @java.lang.SuppressWarnings("all")
    private byte firstKeyAction;
    @java.lang.SuppressWarnings("all")
    private byte nKeyActions;
    @java.lang.SuppressWarnings("all")
    private byte firstKeyBehavior;
    @java.lang.SuppressWarnings("all")
    private byte nKeyBehaviors;
    @java.lang.SuppressWarnings("all")
    private short virtualMods;
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

    public boolean isFullEnabled(MapPart... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (MapPart m : maskEnums) {
        if (!m.isEnabled(full)) {
          return false;
        }
      }
      return true;
    }

    public GetMap.GetMapBuilder fullEnable(MapPart... maskEnums) {
      for (MapPart m : maskEnums) {
        full((short) m.enableFor(full));
      }
      return this;
    }

    public GetMap.GetMapBuilder fullDisable(MapPart... maskEnums) {
      for (MapPart m : maskEnums) {
        full((short) m.disableFor(full));
      }
      return this;
    }

    public boolean isPartialEnabled(MapPart... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (MapPart m : maskEnums) {
        if (!m.isEnabled(partial)) {
          return false;
        }
      }
      return true;
    }

    public GetMap.GetMapBuilder partialEnable(MapPart... maskEnums) {
      for (MapPart m : maskEnums) {
        partial((short) m.enableFor(partial));
      }
      return this;
    }

    public GetMap.GetMapBuilder partialDisable(MapPart... maskEnums) {
      for (MapPart m : maskEnums) {
        partial((short) m.disableFor(partial));
      }
      return this;
    }

    public boolean isVirtualModsEnabled(VMod... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (VMod m : maskEnums) {
        if (!m.isEnabled(virtualMods)) {
          return false;
        }
      }
      return true;
    }

    public GetMap.GetMapBuilder virtualModsEnable(VMod... maskEnums) {
      for (VMod m : maskEnums) {
        virtualMods((short) m.enableFor(virtualMods));
      }
      return this;
    }

    public GetMap.GetMapBuilder virtualModsDisable(VMod... maskEnums) {
      for (VMod m : maskEnums) {
        virtualMods((short) m.disableFor(virtualMods));
      }
      return this;
    }

    public int getSize() {
      return 28;
    }

    @java.lang.SuppressWarnings("all")
    GetMapBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMap.GetMapBuilder deviceSpec(final short deviceSpec) {
      this.deviceSpec = deviceSpec;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMap.GetMapBuilder full(final short full) {
      this.full = full;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMap.GetMapBuilder partial(final short partial) {
      this.partial = partial;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMap.GetMapBuilder firstType(final byte firstType) {
      this.firstType = firstType;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMap.GetMapBuilder nTypes(final byte nTypes) {
      this.nTypes = nTypes;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMap.GetMapBuilder firstKeySym(final byte firstKeySym) {
      this.firstKeySym = firstKeySym;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMap.GetMapBuilder nKeySyms(final byte nKeySyms) {
      this.nKeySyms = nKeySyms;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMap.GetMapBuilder firstKeyAction(final byte firstKeyAction) {
      this.firstKeyAction = firstKeyAction;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMap.GetMapBuilder nKeyActions(final byte nKeyActions) {
      this.nKeyActions = nKeyActions;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMap.GetMapBuilder firstKeyBehavior(final byte firstKeyBehavior) {
      this.firstKeyBehavior = firstKeyBehavior;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMap.GetMapBuilder nKeyBehaviors(final byte nKeyBehaviors) {
      this.nKeyBehaviors = nKeyBehaviors;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMap.GetMapBuilder virtualMods(final short virtualMods) {
      this.virtualMods = virtualMods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMap.GetMapBuilder firstKeyExplicit(final byte firstKeyExplicit) {
      this.firstKeyExplicit = firstKeyExplicit;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMap.GetMapBuilder nKeyExplicit(final byte nKeyExplicit) {
      this.nKeyExplicit = nKeyExplicit;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMap.GetMapBuilder firstModMapKey(final byte firstModMapKey) {
      this.firstModMapKey = firstModMapKey;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMap.GetMapBuilder nModMapKeys(final byte nModMapKeys) {
      this.nModMapKeys = nModMapKeys;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMap.GetMapBuilder firstVModMapKey(final byte firstVModMapKey) {
      this.firstVModMapKey = firstVModMapKey;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMap.GetMapBuilder nVModMapKeys(final byte nVModMapKeys) {
      this.nVModMapKeys = nVModMapKeys;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetMap build() {
      return new GetMap(this.deviceSpec, this.full, this.partial, this.firstType, this.nTypes, this.firstKeySym, this.nKeySyms, this.firstKeyAction, this.nKeyActions, this.firstKeyBehavior, this.nKeyBehaviors, this.virtualMods, this.firstKeyExplicit, this.nKeyExplicit, this.firstModMapKey, this.nModMapKeys, this.firstVModMapKey, this.nVModMapKeys);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetMap.GetMapBuilder(deviceSpec=" + this.deviceSpec + ", full=" + this.full + ", partial=" + this.partial + ", firstType=" + this.firstType + ", nTypes=" + this.nTypes + ", firstKeySym=" + this.firstKeySym + ", nKeySyms=" + this.nKeySyms + ", firstKeyAction=" + this.firstKeyAction + ", nKeyActions=" + this.nKeyActions + ", firstKeyBehavior=" + this.firstKeyBehavior + ", nKeyBehaviors=" + this.nKeyBehaviors + ", virtualMods=" + this.virtualMods + ", firstKeyExplicit=" + this.firstKeyExplicit + ", nKeyExplicit=" + this.nKeyExplicit + ", firstModMapKey=" + this.firstModMapKey + ", nModMapKeys=" + this.nModMapKeys + ", firstVModMapKey=" + this.firstVModMapKey + ", nVModMapKeys=" + this.nVModMapKeys + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetMap(final short deviceSpec, final short full, final short partial, final byte firstType, final byte nTypes, final byte firstKeySym, final byte nKeySyms, final byte firstKeyAction, final byte nKeyActions, final byte firstKeyBehavior, final byte nKeyBehaviors, final short virtualMods, final byte firstKeyExplicit, final byte nKeyExplicit, final byte firstModMapKey, final byte nModMapKeys, final byte firstVModMapKey, final byte nVModMapKeys) {
    this.deviceSpec = deviceSpec;
    this.full = full;
    this.partial = partial;
    this.firstType = firstType;
    this.nTypes = nTypes;
    this.firstKeySym = firstKeySym;
    this.nKeySyms = nKeySyms;
    this.firstKeyAction = firstKeyAction;
    this.nKeyActions = nKeyActions;
    this.firstKeyBehavior = firstKeyBehavior;
    this.nKeyBehaviors = nKeyBehaviors;
    this.virtualMods = virtualMods;
    this.firstKeyExplicit = firstKeyExplicit;
    this.nKeyExplicit = nKeyExplicit;
    this.firstModMapKey = firstModMapKey;
    this.nModMapKeys = nModMapKeys;
    this.firstVModMapKey = firstVModMapKey;
    this.nVModMapKeys = nVModMapKeys;
  }

  @java.lang.SuppressWarnings("all")
  public static GetMap.GetMapBuilder builder() {
    return new GetMap.GetMapBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getDeviceSpec() {
    return this.deviceSpec;
  }

  @java.lang.SuppressWarnings("all")
  public short getFull() {
    return this.full;
  }

  @java.lang.SuppressWarnings("all")
  public short getPartial() {
    return this.partial;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFirstType() {
    return this.firstType;
  }

  @java.lang.SuppressWarnings("all")
  public byte getNTypes() {
    return this.nTypes;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFirstKeySym() {
    return this.firstKeySym;
  }

  @java.lang.SuppressWarnings("all")
  public byte getNKeySyms() {
    return this.nKeySyms;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFirstKeyAction() {
    return this.firstKeyAction;
  }

  @java.lang.SuppressWarnings("all")
  public byte getNKeyActions() {
    return this.nKeyActions;
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
  public short getVirtualMods() {
    return this.virtualMods;
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

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetMap)) return false;
    final GetMap other = (GetMap) o;
    if (this.getDeviceSpec() != other.getDeviceSpec()) return false;
    if (this.getFull() != other.getFull()) return false;
    if (this.getPartial() != other.getPartial()) return false;
    if (this.getFirstType() != other.getFirstType()) return false;
    if (this.getNTypes() != other.getNTypes()) return false;
    if (this.getFirstKeySym() != other.getFirstKeySym()) return false;
    if (this.getNKeySyms() != other.getNKeySyms()) return false;
    if (this.getFirstKeyAction() != other.getFirstKeyAction()) return false;
    if (this.getNKeyActions() != other.getNKeyActions()) return false;
    if (this.getFirstKeyBehavior() != other.getFirstKeyBehavior()) return false;
    if (this.getNKeyBehaviors() != other.getNKeyBehaviors()) return false;
    if (this.getVirtualMods() != other.getVirtualMods()) return false;
    if (this.getFirstKeyExplicit() != other.getFirstKeyExplicit()) return false;
    if (this.getNKeyExplicit() != other.getNKeyExplicit()) return false;
    if (this.getFirstModMapKey() != other.getFirstModMapKey()) return false;
    if (this.getNModMapKeys() != other.getNModMapKeys()) return false;
    if (this.getFirstVModMapKey() != other.getFirstVModMapKey()) return false;
    if (this.getNVModMapKeys() != other.getNVModMapKeys()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDeviceSpec();
    result = result * PRIME + this.getFull();
    result = result * PRIME + this.getPartial();
    result = result * PRIME + this.getFirstType();
    result = result * PRIME + this.getNTypes();
    result = result * PRIME + this.getFirstKeySym();
    result = result * PRIME + this.getNKeySyms();
    result = result * PRIME + this.getFirstKeyAction();
    result = result * PRIME + this.getNKeyActions();
    result = result * PRIME + this.getFirstKeyBehavior();
    result = result * PRIME + this.getNKeyBehaviors();
    result = result * PRIME + this.getVirtualMods();
    result = result * PRIME + this.getFirstKeyExplicit();
    result = result * PRIME + this.getNKeyExplicit();
    result = result * PRIME + this.getFirstModMapKey();
    result = result * PRIME + this.getNModMapKeys();
    result = result * PRIME + this.getFirstVModMapKey();
    result = result * PRIME + this.getNVModMapKeys();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetMap(deviceSpec=" + this.getDeviceSpec() + ", full=" + this.getFull() + ", partial=" + this.getPartial() + ", firstType=" + this.getFirstType() + ", nTypes=" + this.getNTypes() + ", firstKeySym=" + this.getFirstKeySym() + ", nKeySyms=" + this.getNKeySyms() + ", firstKeyAction=" + this.getFirstKeyAction() + ", nKeyActions=" + this.getNKeyActions() + ", firstKeyBehavior=" + this.getFirstKeyBehavior() + ", nKeyBehaviors=" + this.getNKeyBehaviors() + ", virtualMods=" + this.getVirtualMods() + ", firstKeyExplicit=" + this.getFirstKeyExplicit() + ", nKeyExplicit=" + this.getNKeyExplicit() + ", firstModMapKey=" + this.getFirstModMapKey() + ", nModMapKeys=" + this.getNModMapKeys() + ", firstVModMapKey=" + this.getFirstVModMapKey() + ", nVModMapKeys=" + this.getNVModMapKeys() + ")";
  }
}
