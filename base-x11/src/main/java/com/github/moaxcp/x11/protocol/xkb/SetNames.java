package com.github.moaxcp.x11.protocol.xkb;

import java.util.List;
import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.Popcount;
import com.github.moaxcp.x11.protocol.Utilities;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;

public final class SetNames implements OneWayRequest {
  public static final String PLUGIN_NAME = "xkb";
  public static final byte OPCODE = 18;
  private final short deviceSpec;
  private final int which;
  private final byte firstType;
  private final byte firstKTLevelt;
  private final byte nKTLevels;
  private final byte firstKey;
  private final short totalKTLevelNames;
  private final int keycodesName;
  private final int geometryName;
  private final int symbolsName;
  private final int physSymbolsName;
  private final int typesName;
  private final int compatName;
  private final int[] typeNames;
  private final byte[] nLevelsPerType;
  private final int[] ktLevelNames;
  private final int[] indicatorNames;
  private final int[] virtualModNames;
  private final int[] groups;
  private final List<KeyName> keyNames;
  private final List<KeyAlias> keyAliases;
  private final int[] radioGroupNames;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetNames readSetNames(X11Input in) throws IOException {
    SetNames.SetNamesBuilder javaBuilder = SetNames.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short deviceSpec = in.readCard16();
    short virtualMods = in.readCard16();
    int which = in.readCard32();
    byte firstType = in.readCard8();
    byte nTypes = in.readCard8();
    byte firstKTLevelt = in.readCard8();
    byte nKTLevels = in.readCard8();
    int indicators = in.readCard32();
    byte groupNames = in.readCard8();
    byte nRadioGroups = in.readCard8();
    byte firstKey = in.readCard8();
    byte nKeys = in.readCard8();
    byte nKeyAliases = in.readCard8();
    byte[] pad16 = in.readPad(1);
    short totalKTLevelNames = in.readCard16();
    int keycodesName = 0;
    int geometryName = 0;
    int symbolsName = 0;
    int physSymbolsName = 0;
    int typesName = 0;
    int compatName = 0;
    int[] typeNames = null;
    byte[] nLevelsPerType = null;
    in.readPadAlign(Byte.toUnsignedInt(nTypes));
    int[] ktLevelNames = null;
    int[] indicatorNames = null;
    int[] virtualModNames = null;
    int[] groups = null;
    List<KeyName> keyNames = null;
    List<KeyAlias> keyAliases = null;
    int[] radioGroupNames = null;
    javaBuilder.deviceSpec(deviceSpec);
    javaBuilder.which(which);
    javaBuilder.firstType(firstType);
    javaBuilder.firstKTLevelt(firstKTLevelt);
    javaBuilder.nKTLevels(nKTLevels);
    javaBuilder.firstKey(firstKey);
    javaBuilder.totalKTLevelNames(totalKTLevelNames);
    if (NameDetail.KEYCODES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      keycodesName = in.readCard32();
      javaBuilder.keycodesName(keycodesName);
    }
    if (NameDetail.GEOMETRY.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      geometryName = in.readCard32();
      javaBuilder.geometryName(geometryName);
    }
    if (NameDetail.SYMBOLS.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      symbolsName = in.readCard32();
      javaBuilder.symbolsName(symbolsName);
    }
    if (NameDetail.PHYS_SYMBOLS.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      physSymbolsName = in.readCard32();
      javaBuilder.physSymbolsName(physSymbolsName);
    }
    if (NameDetail.TYPES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      typesName = in.readCard32();
      javaBuilder.typesName(typesName);
    }
    if (NameDetail.COMPAT.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      compatName = in.readCard32();
      javaBuilder.compatName(compatName);
    }
    if (NameDetail.KEY_TYPE_NAMES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      typeNames = in.readCard32(Byte.toUnsignedInt(nTypes));
      javaBuilder.typeNames(typeNames);
    }
    if (NameDetail.K_T_LEVEL_NAMES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      nLevelsPerType = in.readCard8(Byte.toUnsignedInt(nTypes));
      javaBuilder.nLevelsPerType(nLevelsPerType);
    }
    if (NameDetail.K_T_LEVEL_NAMES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      ktLevelNames = in.readCard32((int) Utilities.sum(nLevelsPerType));
      javaBuilder.ktLevelNames(ktLevelNames);
    }
    if (NameDetail.INDICATOR_NAMES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      indicatorNames = in.readCard32(Popcount.popcount(Integer.toUnsignedLong(indicators)));
      javaBuilder.indicatorNames(indicatorNames);
    }
    if (NameDetail.VIRTUAL_MOD_NAMES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      virtualModNames = in.readCard32(Popcount.popcount(Short.toUnsignedInt(virtualMods)));
      javaBuilder.virtualModNames(virtualModNames);
    }
    if (NameDetail.GROUP_NAMES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      groups = in.readCard32(Popcount.popcount(Byte.toUnsignedInt(groupNames)));
      javaBuilder.groups(groups);
    }
    if (NameDetail.KEY_NAMES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      keyNames = in.readObject(KeyName::readKeyName, Byte.toUnsignedInt(nKeys));
      javaBuilder.keyNames(keyNames);
    }
    if (NameDetail.KEY_ALIASES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      keyAliases = in.readObject(KeyAlias::readKeyAlias, Byte.toUnsignedInt(nKeyAliases));
      javaBuilder.keyAliases(keyAliases);
    }
    if (NameDetail.R_G_NAMES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      radioGroupNames = in.readCard32(Byte.toUnsignedInt(nRadioGroups));
      javaBuilder.radioGroupNames(radioGroupNames);
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
    short virtualMods = (short) virtualModNames.length;
    out.writeCard16(virtualMods);
    out.writeCard32(which);
    out.writeCard8(firstType);
    byte nTypes = (byte) nLevelsPerType.length;
    out.writeCard8(nTypes);
    out.writeCard8(firstKTLevelt);
    out.writeCard8(nKTLevels);
    int indicators = indicatorNames.length;
    out.writeCard32(indicators);
    byte groupNames = (byte) groups.length;
    out.writeCard8(groupNames);
    byte nRadioGroups = (byte) radioGroupNames.length;
    out.writeCard8(nRadioGroups);
    out.writeCard8(firstKey);
    byte nKeys = (byte) keyNames.size();
    out.writeCard8(nKeys);
    byte nKeyAliases = (byte) keyAliases.size();
    out.writeCard8(nKeyAliases);
    out.writePad(1);
    out.writeCard16(totalKTLevelNames);
    if (NameDetail.KEYCODES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      out.writeCard32(keycodesName);
    }
    if (NameDetail.GEOMETRY.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      out.writeCard32(geometryName);
    }
    if (NameDetail.SYMBOLS.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      out.writeCard32(symbolsName);
    }
    if (NameDetail.PHYS_SYMBOLS.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      out.writeCard32(physSymbolsName);
    }
    if (NameDetail.TYPES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      out.writeCard32(typesName);
    }
    if (NameDetail.COMPAT.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      out.writeCard32(compatName);
    }
    if (NameDetail.KEY_TYPE_NAMES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      out.writeCard32(typeNames);
    }
    if (NameDetail.K_T_LEVEL_NAMES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      out.writeCard8(nLevelsPerType);
    }
    out.writePadAlign(Byte.toUnsignedInt(nTypes));
    if (NameDetail.K_T_LEVEL_NAMES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      out.writeCard32(ktLevelNames);
    }
    if (NameDetail.INDICATOR_NAMES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      out.writeCard32(indicatorNames);
    }
    if (NameDetail.VIRTUAL_MOD_NAMES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      out.writeCard32(virtualModNames);
    }
    if (NameDetail.GROUP_NAMES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      out.writeCard32(groups);
    }
    if (NameDetail.KEY_NAMES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      for (KeyName t : keyNames) {
        t.write(out);
      }
    }
    if (NameDetail.KEY_ALIASES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      for (KeyAlias t : keyAliases) {
        t.write(out);
      }
    }
    if (NameDetail.R_G_NAMES.isEnabled((int) (Integer.toUnsignedLong(which)))) {
      out.writeCard32(radioGroupNames);
    }
    out.writePadAlign(getSize());
  }

  public boolean isWhichEnabled(NameDetail... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (NameDetail m : maskEnums) {
      if (!m.isEnabled(which)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 28 + (NameDetail.KEYCODES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 : 0) + (NameDetail.GEOMETRY.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 : 0) + (NameDetail.SYMBOLS.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 : 0) + (NameDetail.PHYS_SYMBOLS.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 : 0) + (NameDetail.TYPES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 : 0) + (NameDetail.COMPAT.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 : 0) + (NameDetail.KEY_TYPE_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 * typeNames.length : 0) + (NameDetail.K_T_LEVEL_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 1 * nLevelsPerType.length : 0) + XObject.getSizeForPadAlign(4, (NameDetail.K_T_LEVEL_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 1 * nLevelsPerType.length : 0)) + (NameDetail.K_T_LEVEL_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 * ktLevelNames.length : 0) + (NameDetail.INDICATOR_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 * indicatorNames.length : 0) + (NameDetail.VIRTUAL_MOD_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 * virtualModNames.length : 0) + (NameDetail.GROUP_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 * groups.length : 0) + (NameDetail.KEY_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? XObject.sizeOf(keyNames) : 0) + (NameDetail.KEY_ALIASES.isEnabled((int) (Integer.toUnsignedLong(which))) ? XObject.sizeOf(keyAliases) : 0) + (NameDetail.R_G_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 * radioGroupNames.length : 0);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetNamesBuilder {
    @java.lang.SuppressWarnings("all")
    private short deviceSpec;
    @java.lang.SuppressWarnings("all")
    private int which;
    @java.lang.SuppressWarnings("all")
    private byte firstType;
    @java.lang.SuppressWarnings("all")
    private byte firstKTLevelt;
    @java.lang.SuppressWarnings("all")
    private byte nKTLevels;
    @java.lang.SuppressWarnings("all")
    private byte firstKey;
    @java.lang.SuppressWarnings("all")
    private short totalKTLevelNames;
    @java.lang.SuppressWarnings("all")
    private int keycodesName;
    @java.lang.SuppressWarnings("all")
    private int geometryName;
    @java.lang.SuppressWarnings("all")
    private int symbolsName;
    @java.lang.SuppressWarnings("all")
    private int physSymbolsName;
    @java.lang.SuppressWarnings("all")
    private int typesName;
    @java.lang.SuppressWarnings("all")
    private int compatName;
    @java.lang.SuppressWarnings("all")
    private int[] typeNames;
    @java.lang.SuppressWarnings("all")
    private byte[] nLevelsPerType;
    @java.lang.SuppressWarnings("all")
    private int[] ktLevelNames;
    @java.lang.SuppressWarnings("all")
    private int[] indicatorNames;
    @java.lang.SuppressWarnings("all")
    private int[] virtualModNames;
    @java.lang.SuppressWarnings("all")
    private int[] groups;
    @java.lang.SuppressWarnings("all")
    private List<KeyName> keyNames;
    @java.lang.SuppressWarnings("all")
    private List<KeyAlias> keyAliases;
    @java.lang.SuppressWarnings("all")
    private int[] radioGroupNames;

    public boolean isWhichEnabled(NameDetail... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (NameDetail m : maskEnums) {
        if (!m.isEnabled(which)) {
          return false;
        }
      }
      return true;
    }

    public SetNames.SetNamesBuilder whichEnable(NameDetail... maskEnums) {
      for (NameDetail m : maskEnums) {
        which((int) m.enableFor(which));
      }
      return this;
    }

    public SetNames.SetNamesBuilder whichDisable(NameDetail... maskEnums) {
      for (NameDetail m : maskEnums) {
        which((int) m.disableFor(which));
      }
      return this;
    }

    public SetNames.SetNamesBuilder keycodesName(int keycodesName) {
      this.keycodesName = keycodesName;
      whichEnable(NameDetail.KEYCODES);
      return this;
    }

    public SetNames.SetNamesBuilder geometryName(int geometryName) {
      this.geometryName = geometryName;
      whichEnable(NameDetail.GEOMETRY);
      return this;
    }

    public SetNames.SetNamesBuilder symbolsName(int symbolsName) {
      this.symbolsName = symbolsName;
      whichEnable(NameDetail.SYMBOLS);
      return this;
    }

    public SetNames.SetNamesBuilder physSymbolsName(int physSymbolsName) {
      this.physSymbolsName = physSymbolsName;
      whichEnable(NameDetail.PHYS_SYMBOLS);
      return this;
    }

    public SetNames.SetNamesBuilder typesName(int typesName) {
      this.typesName = typesName;
      whichEnable(NameDetail.TYPES);
      return this;
    }

    public SetNames.SetNamesBuilder compatName(int compatName) {
      this.compatName = compatName;
      whichEnable(NameDetail.COMPAT);
      return this;
    }

    public SetNames.SetNamesBuilder typeNames(int[] typeNames) {
      this.typeNames = typeNames;
      whichEnable(NameDetail.KEY_TYPE_NAMES);
      return this;
    }

    public SetNames.SetNamesBuilder nLevelsPerType(byte[] nLevelsPerType) {
      this.nLevelsPerType = nLevelsPerType;
      whichEnable(NameDetail.K_T_LEVEL_NAMES);
      return this;
    }

    public SetNames.SetNamesBuilder ktLevelNames(int[] ktLevelNames) {
      this.ktLevelNames = ktLevelNames;
      whichEnable(NameDetail.K_T_LEVEL_NAMES);
      return this;
    }

    public SetNames.SetNamesBuilder indicatorNames(int[] indicatorNames) {
      this.indicatorNames = indicatorNames;
      whichEnable(NameDetail.INDICATOR_NAMES);
      return this;
    }

    public SetNames.SetNamesBuilder virtualModNames(int[] virtualModNames) {
      this.virtualModNames = virtualModNames;
      whichEnable(NameDetail.VIRTUAL_MOD_NAMES);
      return this;
    }

    public SetNames.SetNamesBuilder groups(int[] groups) {
      this.groups = groups;
      whichEnable(NameDetail.GROUP_NAMES);
      return this;
    }

    public SetNames.SetNamesBuilder keyNames(List<KeyName> keyNames) {
      this.keyNames = keyNames;
      whichEnable(NameDetail.KEY_NAMES);
      return this;
    }

    public SetNames.SetNamesBuilder keyAliases(List<KeyAlias> keyAliases) {
      this.keyAliases = keyAliases;
      whichEnable(NameDetail.KEY_ALIASES);
      return this;
    }

    public SetNames.SetNamesBuilder radioGroupNames(int[] radioGroupNames) {
      this.radioGroupNames = radioGroupNames;
      whichEnable(NameDetail.R_G_NAMES);
      return this;
    }

    public int getSize() {
      return 28 + (NameDetail.KEYCODES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 : 0) + (NameDetail.GEOMETRY.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 : 0) + (NameDetail.SYMBOLS.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 : 0) + (NameDetail.PHYS_SYMBOLS.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 : 0) + (NameDetail.TYPES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 : 0) + (NameDetail.COMPAT.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 : 0) + (NameDetail.KEY_TYPE_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 * typeNames.length : 0) + (NameDetail.K_T_LEVEL_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 1 * nLevelsPerType.length : 0) + XObject.getSizeForPadAlign(4, (NameDetail.K_T_LEVEL_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 1 * nLevelsPerType.length : 0)) + (NameDetail.K_T_LEVEL_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 * ktLevelNames.length : 0) + (NameDetail.INDICATOR_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 * indicatorNames.length : 0) + (NameDetail.VIRTUAL_MOD_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 * virtualModNames.length : 0) + (NameDetail.GROUP_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 * groups.length : 0) + (NameDetail.KEY_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? XObject.sizeOf(keyNames) : 0) + (NameDetail.KEY_ALIASES.isEnabled((int) (Integer.toUnsignedLong(which))) ? XObject.sizeOf(keyAliases) : 0) + (NameDetail.R_G_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 * radioGroupNames.length : 0);
    }

    @java.lang.SuppressWarnings("all")
    SetNamesBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetNames.SetNamesBuilder deviceSpec(final short deviceSpec) {
      this.deviceSpec = deviceSpec;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetNames.SetNamesBuilder which(final int which) {
      this.which = which;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetNames.SetNamesBuilder firstType(final byte firstType) {
      this.firstType = firstType;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetNames.SetNamesBuilder firstKTLevelt(final byte firstKTLevelt) {
      this.firstKTLevelt = firstKTLevelt;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetNames.SetNamesBuilder nKTLevels(final byte nKTLevels) {
      this.nKTLevels = nKTLevels;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetNames.SetNamesBuilder firstKey(final byte firstKey) {
      this.firstKey = firstKey;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetNames.SetNamesBuilder totalKTLevelNames(final short totalKTLevelNames) {
      this.totalKTLevelNames = totalKTLevelNames;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetNames build() {
      return new SetNames(this.deviceSpec, this.which, this.firstType, this.firstKTLevelt, this.nKTLevels, this.firstKey, this.totalKTLevelNames, this.keycodesName, this.geometryName, this.symbolsName, this.physSymbolsName, this.typesName, this.compatName, this.typeNames, this.nLevelsPerType, this.ktLevelNames, this.indicatorNames, this.virtualModNames, this.groups, this.keyNames, this.keyAliases, this.radioGroupNames);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetNames.SetNamesBuilder(deviceSpec=" + this.deviceSpec + ", which=" + this.which + ", firstType=" + this.firstType + ", firstKTLevelt=" + this.firstKTLevelt + ", nKTLevels=" + this.nKTLevels + ", firstKey=" + this.firstKey + ", totalKTLevelNames=" + this.totalKTLevelNames + ", keycodesName=" + this.keycodesName + ", geometryName=" + this.geometryName + ", symbolsName=" + this.symbolsName + ", physSymbolsName=" + this.physSymbolsName + ", typesName=" + this.typesName + ", compatName=" + this.compatName + ", typeNames=" + java.util.Arrays.toString(this.typeNames) + ", nLevelsPerType=" + java.util.Arrays.toString(this.nLevelsPerType) + ", ktLevelNames=" + java.util.Arrays.toString(this.ktLevelNames) + ", indicatorNames=" + java.util.Arrays.toString(this.indicatorNames) + ", virtualModNames=" + java.util.Arrays.toString(this.virtualModNames) + ", groups=" + java.util.Arrays.toString(this.groups) + ", keyNames=" + this.keyNames + ", keyAliases=" + this.keyAliases + ", radioGroupNames=" + java.util.Arrays.toString(this.radioGroupNames) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetNames(final short deviceSpec, final int which, final byte firstType, final byte firstKTLevelt, final byte nKTLevels, final byte firstKey, final short totalKTLevelNames, final int keycodesName, final int geometryName, final int symbolsName, final int physSymbolsName, final int typesName, final int compatName, final int[] typeNames, final byte[] nLevelsPerType, final int[] ktLevelNames, final int[] indicatorNames, final int[] virtualModNames, final int[] groups, final List<KeyName> keyNames, final List<KeyAlias> keyAliases, final int[] radioGroupNames) {
    if (typeNames == null) {
      throw new java.lang.NullPointerException("typeNames is marked non-null but is null");
    }
    if (nLevelsPerType == null) {
      throw new java.lang.NullPointerException("nLevelsPerType is marked non-null but is null");
    }
    if (ktLevelNames == null) {
      throw new java.lang.NullPointerException("ktLevelNames is marked non-null but is null");
    }
    if (indicatorNames == null) {
      throw new java.lang.NullPointerException("indicatorNames is marked non-null but is null");
    }
    if (virtualModNames == null) {
      throw new java.lang.NullPointerException("virtualModNames is marked non-null but is null");
    }
    if (groups == null) {
      throw new java.lang.NullPointerException("groups is marked non-null but is null");
    }
    if (keyNames == null) {
      throw new java.lang.NullPointerException("keyNames is marked non-null but is null");
    }
    if (keyAliases == null) {
      throw new java.lang.NullPointerException("keyAliases is marked non-null but is null");
    }
    if (radioGroupNames == null) {
      throw new java.lang.NullPointerException("radioGroupNames is marked non-null but is null");
    }
    this.deviceSpec = deviceSpec;
    this.which = which;
    this.firstType = firstType;
    this.firstKTLevelt = firstKTLevelt;
    this.nKTLevels = nKTLevels;
    this.firstKey = firstKey;
    this.totalKTLevelNames = totalKTLevelNames;
    this.keycodesName = keycodesName;
    this.geometryName = geometryName;
    this.symbolsName = symbolsName;
    this.physSymbolsName = physSymbolsName;
    this.typesName = typesName;
    this.compatName = compatName;
    this.typeNames = typeNames;
    this.nLevelsPerType = nLevelsPerType;
    this.ktLevelNames = ktLevelNames;
    this.indicatorNames = indicatorNames;
    this.virtualModNames = virtualModNames;
    this.groups = groups;
    this.keyNames = keyNames;
    this.keyAliases = keyAliases;
    this.radioGroupNames = radioGroupNames;
  }

  @java.lang.SuppressWarnings("all")
  public static SetNames.SetNamesBuilder builder() {
    return new SetNames.SetNamesBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getDeviceSpec() {
    return this.deviceSpec;
  }

  @java.lang.SuppressWarnings("all")
  public int getWhich() {
    return this.which;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFirstType() {
    return this.firstType;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFirstKTLevelt() {
    return this.firstKTLevelt;
  }

  @java.lang.SuppressWarnings("all")
  public byte getNKTLevels() {
    return this.nKTLevels;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFirstKey() {
    return this.firstKey;
  }

  @java.lang.SuppressWarnings("all")
  public short getTotalKTLevelNames() {
    return this.totalKTLevelNames;
  }

  @java.lang.SuppressWarnings("all")
  public int getKeycodesName() {
    return this.keycodesName;
  }

  @java.lang.SuppressWarnings("all")
  public int getGeometryName() {
    return this.geometryName;
  }

  @java.lang.SuppressWarnings("all")
  public int getSymbolsName() {
    return this.symbolsName;
  }

  @java.lang.SuppressWarnings("all")
  public int getPhysSymbolsName() {
    return this.physSymbolsName;
  }

  @java.lang.SuppressWarnings("all")
  public int getTypesName() {
    return this.typesName;
  }

  @java.lang.SuppressWarnings("all")
  public int getCompatName() {
    return this.compatName;
  }

  @java.lang.SuppressWarnings("all")
  public int[] getTypeNames() {
    return this.typeNames;
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getNLevelsPerType() {
    return this.nLevelsPerType;
  }

  @java.lang.SuppressWarnings("all")
  public int[] getKtLevelNames() {
    return this.ktLevelNames;
  }

  @java.lang.SuppressWarnings("all")
  public int[] getIndicatorNames() {
    return this.indicatorNames;
  }

  @java.lang.SuppressWarnings("all")
  public int[] getVirtualModNames() {
    return this.virtualModNames;
  }

  @java.lang.SuppressWarnings("all")
  public int[] getGroups() {
    return this.groups;
  }

  @java.lang.SuppressWarnings("all")
  public List<KeyName> getKeyNames() {
    return this.keyNames;
  }

  @java.lang.SuppressWarnings("all")
  public List<KeyAlias> getKeyAliases() {
    return this.keyAliases;
  }

  @java.lang.SuppressWarnings("all")
  public int[] getRadioGroupNames() {
    return this.radioGroupNames;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetNames)) return false;
    final SetNames other = (SetNames) o;
    if (this.getDeviceSpec() != other.getDeviceSpec()) return false;
    if (this.getWhich() != other.getWhich()) return false;
    if (this.getFirstType() != other.getFirstType()) return false;
    if (this.getFirstKTLevelt() != other.getFirstKTLevelt()) return false;
    if (this.getNKTLevels() != other.getNKTLevels()) return false;
    if (this.getFirstKey() != other.getFirstKey()) return false;
    if (this.getTotalKTLevelNames() != other.getTotalKTLevelNames()) return false;
    if (this.getKeycodesName() != other.getKeycodesName()) return false;
    if (this.getGeometryName() != other.getGeometryName()) return false;
    if (this.getSymbolsName() != other.getSymbolsName()) return false;
    if (this.getPhysSymbolsName() != other.getPhysSymbolsName()) return false;
    if (this.getTypesName() != other.getTypesName()) return false;
    if (this.getCompatName() != other.getCompatName()) return false;
    if (!java.util.Arrays.equals(this.getTypeNames(), other.getTypeNames())) return false;
    if (!java.util.Arrays.equals(this.getNLevelsPerType(), other.getNLevelsPerType())) return false;
    if (!java.util.Arrays.equals(this.getKtLevelNames(), other.getKtLevelNames())) return false;
    if (!java.util.Arrays.equals(this.getIndicatorNames(), other.getIndicatorNames())) return false;
    if (!java.util.Arrays.equals(this.getVirtualModNames(), other.getVirtualModNames())) return false;
    if (!java.util.Arrays.equals(this.getGroups(), other.getGroups())) return false;
    final java.lang.Object this$keyNames = this.getKeyNames();
    final java.lang.Object other$keyNames = other.getKeyNames();
    if (this$keyNames == null ? other$keyNames != null : !this$keyNames.equals(other$keyNames)) return false;
    final java.lang.Object this$keyAliases = this.getKeyAliases();
    final java.lang.Object other$keyAliases = other.getKeyAliases();
    if (this$keyAliases == null ? other$keyAliases != null : !this$keyAliases.equals(other$keyAliases)) return false;
    if (!java.util.Arrays.equals(this.getRadioGroupNames(), other.getRadioGroupNames())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDeviceSpec();
    result = result * PRIME + this.getWhich();
    result = result * PRIME + this.getFirstType();
    result = result * PRIME + this.getFirstKTLevelt();
    result = result * PRIME + this.getNKTLevels();
    result = result * PRIME + this.getFirstKey();
    result = result * PRIME + this.getTotalKTLevelNames();
    result = result * PRIME + this.getKeycodesName();
    result = result * PRIME + this.getGeometryName();
    result = result * PRIME + this.getSymbolsName();
    result = result * PRIME + this.getPhysSymbolsName();
    result = result * PRIME + this.getTypesName();
    result = result * PRIME + this.getCompatName();
    result = result * PRIME + java.util.Arrays.hashCode(this.getTypeNames());
    result = result * PRIME + java.util.Arrays.hashCode(this.getNLevelsPerType());
    result = result * PRIME + java.util.Arrays.hashCode(this.getKtLevelNames());
    result = result * PRIME + java.util.Arrays.hashCode(this.getIndicatorNames());
    result = result * PRIME + java.util.Arrays.hashCode(this.getVirtualModNames());
    result = result * PRIME + java.util.Arrays.hashCode(this.getGroups());
    final java.lang.Object $keyNames = this.getKeyNames();
    result = result * PRIME + ($keyNames == null ? 43 : $keyNames.hashCode());
    final java.lang.Object $keyAliases = this.getKeyAliases();
    result = result * PRIME + ($keyAliases == null ? 43 : $keyAliases.hashCode());
    result = result * PRIME + java.util.Arrays.hashCode(this.getRadioGroupNames());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetNames(deviceSpec=" + this.getDeviceSpec() + ", which=" + this.getWhich() + ", firstType=" + this.getFirstType() + ", firstKTLevelt=" + this.getFirstKTLevelt() + ", nKTLevels=" + this.getNKTLevels() + ", firstKey=" + this.getFirstKey() + ", totalKTLevelNames=" + this.getTotalKTLevelNames() + ", keycodesName=" + this.getKeycodesName() + ", geometryName=" + this.getGeometryName() + ", symbolsName=" + this.getSymbolsName() + ", physSymbolsName=" + this.getPhysSymbolsName() + ", typesName=" + this.getTypesName() + ", compatName=" + this.getCompatName() + ", typeNames=" + java.util.Arrays.toString(this.getTypeNames()) + ", nLevelsPerType=" + java.util.Arrays.toString(this.getNLevelsPerType()) + ", ktLevelNames=" + java.util.Arrays.toString(this.getKtLevelNames()) + ", indicatorNames=" + java.util.Arrays.toString(this.getIndicatorNames()) + ", virtualModNames=" + java.util.Arrays.toString(this.getVirtualModNames()) + ", groups=" + java.util.Arrays.toString(this.getGroups()) + ", keyNames=" + this.getKeyNames() + ", keyAliases=" + this.getKeyAliases() + ", radioGroupNames=" + java.util.Arrays.toString(this.getRadioGroupNames()) + ")";
  }
}
