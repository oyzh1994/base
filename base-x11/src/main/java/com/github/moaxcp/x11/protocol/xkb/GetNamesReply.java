package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.Popcount;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.primitive.ByteList;
import org.eclipse.collections.api.list.primitive.IntList;

public final class GetNamesReply implements XReply {
  public static final String PLUGIN_NAME = "xkb";
  private final byte deviceID;
  private final short sequenceNumber;
  private final int which;
  private final byte minKeyCode;
  private final byte maxKeyCode;
  private final byte firstKey;
  private final short nKTLevels;
  private final int keycodesName;
  private final int geometryName;
  private final int symbolsName;
  private final int physSymbolsName;
  private final int typesName;
  private final int compatName;
  private final IntList typeNames;
  private final ByteList nLevelsPerType;
  private final IntList ktLevelNames;
  private final IntList indicatorNames;
  private final IntList virtualModNames;
  private final IntList groups;
  private final ImmutableList<KeyName> keyNames;
  private final ImmutableList<KeyAlias> keyAliases;
  private final IntList radioGroupNames;

  public static GetNamesReply readGetNamesReply(byte deviceID, short sequenceNumber, X11Input in) throws IOException {
    GetNamesReply.GetNamesReplyBuilder javaBuilder = GetNamesReply.builder();
    int length = in.readCard32();
    int which = in.readCard32();
    byte minKeyCode = in.readCard8();
    byte maxKeyCode = in.readCard8();
    byte nTypes = in.readCard8();
    byte groupNames = in.readCard8();
    short virtualMods = in.readCard16();
    byte firstKey = in.readCard8();
    byte nKeys = in.readCard8();
    int indicators = in.readCard32();
    byte nRadioGroups = in.readCard8();
    byte nKeyAliases = in.readCard8();
    short nKTLevels = in.readCard16();
    byte[] pad16 = in.readPad(4);
    int keycodesName = 0;
    int geometryName = 0;
    int symbolsName = 0;
    int physSymbolsName = 0;
    int typesName = 0;
    int compatName = 0;
    IntList typeNames = null;
    ByteList nLevelsPerType = null;
    in.readPadAlign(Byte.toUnsignedInt(nTypes));
    IntList ktLevelNames = null;
    IntList indicatorNames = null;
    IntList virtualModNames = null;
    IntList groups = null;
    ImmutableList<KeyName> keyNames = null;
    ImmutableList<KeyAlias> keyAliases = null;
    IntList radioGroupNames = null;
    javaBuilder.deviceID(deviceID);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.which(which);
    javaBuilder.minKeyCode(minKeyCode);
    javaBuilder.maxKeyCode(maxKeyCode);
    javaBuilder.firstKey(firstKey);
    javaBuilder.nKTLevels(nKTLevels);
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
      ktLevelNames = in.readCard32((int) nLevelsPerType.sum());
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
    out.writeCard32(which);
    out.writeCard8(minKeyCode);
    out.writeCard8(maxKeyCode);
    byte nTypes = (byte) nLevelsPerType.size();
    out.writeCard8(nTypes);
    byte groupNames = (byte) groups.size();
    out.writeCard8(groupNames);
    short virtualMods = (short) virtualModNames.size();
    out.writeCard16(virtualMods);
    out.writeCard8(firstKey);
    byte nKeys = (byte) keyNames.size();
    out.writeCard8(nKeys);
    int indicators = indicatorNames.size();
    out.writeCard32(indicators);
    byte nRadioGroups = (byte) radioGroupNames.size();
    out.writeCard8(nRadioGroups);
    byte nKeyAliases = (byte) keyAliases.size();
    out.writeCard8(nKeyAliases);
    out.writeCard16(nKTLevels);
    out.writePad(4);
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
    return 32 + (NameDetail.KEYCODES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 : 0) + (NameDetail.GEOMETRY.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 : 0) + (NameDetail.SYMBOLS.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 : 0) + (NameDetail.PHYS_SYMBOLS.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 : 0) + (NameDetail.TYPES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 : 0) + (NameDetail.COMPAT.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 : 0) + (NameDetail.KEY_TYPE_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 * typeNames.size() : 0) + (NameDetail.K_T_LEVEL_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 1 * nLevelsPerType.size() : 0) + XObject.getSizeForPadAlign(4, (NameDetail.K_T_LEVEL_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 1 * nLevelsPerType.size() : 0)) + (NameDetail.K_T_LEVEL_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 * ktLevelNames.size() : 0) + (NameDetail.INDICATOR_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 * indicatorNames.size() : 0) + (NameDetail.VIRTUAL_MOD_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 * virtualModNames.size() : 0) + (NameDetail.GROUP_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 * groups.size() : 0) + (NameDetail.KEY_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? XObject.sizeOf(keyNames) : 0) + (NameDetail.KEY_ALIASES.isEnabled((int) (Integer.toUnsignedLong(which))) ? XObject.sizeOf(keyAliases) : 0) + (NameDetail.R_G_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 * radioGroupNames.size() : 0);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetNamesReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte deviceID;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int which;
    @java.lang.SuppressWarnings("all")
    private byte minKeyCode;
    @java.lang.SuppressWarnings("all")
    private byte maxKeyCode;
    @java.lang.SuppressWarnings("all")
    private byte firstKey;
    @java.lang.SuppressWarnings("all")
    private short nKTLevels;
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
    private IntList typeNames;
    @java.lang.SuppressWarnings("all")
    private ByteList nLevelsPerType;
    @java.lang.SuppressWarnings("all")
    private IntList ktLevelNames;
    @java.lang.SuppressWarnings("all")
    private IntList indicatorNames;
    @java.lang.SuppressWarnings("all")
    private IntList virtualModNames;
    @java.lang.SuppressWarnings("all")
    private IntList groups;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<KeyName> keyNames;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<KeyAlias> keyAliases;
    @java.lang.SuppressWarnings("all")
    private IntList radioGroupNames;

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

    public GetNamesReply.GetNamesReplyBuilder whichEnable(NameDetail... maskEnums) {
      for (NameDetail m : maskEnums) {
        which((int) m.enableFor(which));
      }
      return this;
    }

    public GetNamesReply.GetNamesReplyBuilder whichDisable(NameDetail... maskEnums) {
      for (NameDetail m : maskEnums) {
        which((int) m.disableFor(which));
      }
      return this;
    }

    public GetNamesReply.GetNamesReplyBuilder keycodesName(int keycodesName) {
      this.keycodesName = keycodesName;
      whichEnable(NameDetail.KEYCODES);
      return this;
    }

    public GetNamesReply.GetNamesReplyBuilder geometryName(int geometryName) {
      this.geometryName = geometryName;
      whichEnable(NameDetail.GEOMETRY);
      return this;
    }

    public GetNamesReply.GetNamesReplyBuilder symbolsName(int symbolsName) {
      this.symbolsName = symbolsName;
      whichEnable(NameDetail.SYMBOLS);
      return this;
    }

    public GetNamesReply.GetNamesReplyBuilder physSymbolsName(int physSymbolsName) {
      this.physSymbolsName = physSymbolsName;
      whichEnable(NameDetail.PHYS_SYMBOLS);
      return this;
    }

    public GetNamesReply.GetNamesReplyBuilder typesName(int typesName) {
      this.typesName = typesName;
      whichEnable(NameDetail.TYPES);
      return this;
    }

    public GetNamesReply.GetNamesReplyBuilder compatName(int compatName) {
      this.compatName = compatName;
      whichEnable(NameDetail.COMPAT);
      return this;
    }

    public GetNamesReply.GetNamesReplyBuilder typeNames(IntList typeNames) {
      this.typeNames = typeNames;
      whichEnable(NameDetail.KEY_TYPE_NAMES);
      return this;
    }

    public GetNamesReply.GetNamesReplyBuilder nLevelsPerType(ByteList nLevelsPerType) {
      this.nLevelsPerType = nLevelsPerType;
      whichEnable(NameDetail.K_T_LEVEL_NAMES);
      return this;
    }

    public GetNamesReply.GetNamesReplyBuilder ktLevelNames(IntList ktLevelNames) {
      this.ktLevelNames = ktLevelNames;
      whichEnable(NameDetail.K_T_LEVEL_NAMES);
      return this;
    }

    public GetNamesReply.GetNamesReplyBuilder indicatorNames(IntList indicatorNames) {
      this.indicatorNames = indicatorNames;
      whichEnable(NameDetail.INDICATOR_NAMES);
      return this;
    }

    public GetNamesReply.GetNamesReplyBuilder virtualModNames(IntList virtualModNames) {
      this.virtualModNames = virtualModNames;
      whichEnable(NameDetail.VIRTUAL_MOD_NAMES);
      return this;
    }

    public GetNamesReply.GetNamesReplyBuilder groups(IntList groups) {
      this.groups = groups;
      whichEnable(NameDetail.GROUP_NAMES);
      return this;
    }

    public GetNamesReply.GetNamesReplyBuilder keyNames(ImmutableList<KeyName> keyNames) {
      this.keyNames = keyNames;
      whichEnable(NameDetail.KEY_NAMES);
      return this;
    }

    public GetNamesReply.GetNamesReplyBuilder keyAliases(ImmutableList<KeyAlias> keyAliases) {
      this.keyAliases = keyAliases;
      whichEnable(NameDetail.KEY_ALIASES);
      return this;
    }

    public GetNamesReply.GetNamesReplyBuilder radioGroupNames(IntList radioGroupNames) {
      this.radioGroupNames = radioGroupNames;
      whichEnable(NameDetail.R_G_NAMES);
      return this;
    }

    public int getSize() {
      return 32 + (NameDetail.KEYCODES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 : 0) + (NameDetail.GEOMETRY.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 : 0) + (NameDetail.SYMBOLS.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 : 0) + (NameDetail.PHYS_SYMBOLS.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 : 0) + (NameDetail.TYPES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 : 0) + (NameDetail.COMPAT.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 : 0) + (NameDetail.KEY_TYPE_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 * typeNames.size() : 0) + (NameDetail.K_T_LEVEL_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 1 * nLevelsPerType.size() : 0) + XObject.getSizeForPadAlign(4, (NameDetail.K_T_LEVEL_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 1 * nLevelsPerType.size() : 0)) + (NameDetail.K_T_LEVEL_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 * ktLevelNames.size() : 0) + (NameDetail.INDICATOR_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 * indicatorNames.size() : 0) + (NameDetail.VIRTUAL_MOD_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 * virtualModNames.size() : 0) + (NameDetail.GROUP_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 * groups.size() : 0) + (NameDetail.KEY_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? XObject.sizeOf(keyNames) : 0) + (NameDetail.KEY_ALIASES.isEnabled((int) (Integer.toUnsignedLong(which))) ? XObject.sizeOf(keyAliases) : 0) + (NameDetail.R_G_NAMES.isEnabled((int) (Integer.toUnsignedLong(which))) ? 4 * radioGroupNames.size() : 0);
    }

    @java.lang.SuppressWarnings("all")
    GetNamesReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetNamesReply.GetNamesReplyBuilder deviceID(final byte deviceID) {
      this.deviceID = deviceID;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetNamesReply.GetNamesReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetNamesReply.GetNamesReplyBuilder which(final int which) {
      this.which = which;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetNamesReply.GetNamesReplyBuilder minKeyCode(final byte minKeyCode) {
      this.minKeyCode = minKeyCode;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetNamesReply.GetNamesReplyBuilder maxKeyCode(final byte maxKeyCode) {
      this.maxKeyCode = maxKeyCode;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetNamesReply.GetNamesReplyBuilder firstKey(final byte firstKey) {
      this.firstKey = firstKey;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetNamesReply.GetNamesReplyBuilder nKTLevels(final short nKTLevels) {
      this.nKTLevels = nKTLevels;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetNamesReply build() {
      return new GetNamesReply(this.deviceID, this.sequenceNumber, this.which, this.minKeyCode, this.maxKeyCode, this.firstKey, this.nKTLevels, this.keycodesName, this.geometryName, this.symbolsName, this.physSymbolsName, this.typesName, this.compatName, this.typeNames, this.nLevelsPerType, this.ktLevelNames, this.indicatorNames, this.virtualModNames, this.groups, this.keyNames, this.keyAliases, this.radioGroupNames);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetNamesReply.GetNamesReplyBuilder(deviceID=" + this.deviceID + ", sequenceNumber=" + this.sequenceNumber + ", which=" + this.which + ", minKeyCode=" + this.minKeyCode + ", maxKeyCode=" + this.maxKeyCode + ", firstKey=" + this.firstKey + ", nKTLevels=" + this.nKTLevels + ", keycodesName=" + this.keycodesName + ", geometryName=" + this.geometryName + ", symbolsName=" + this.symbolsName + ", physSymbolsName=" + this.physSymbolsName + ", typesName=" + this.typesName + ", compatName=" + this.compatName + ", typeNames=" + this.typeNames + ", nLevelsPerType=" + this.nLevelsPerType + ", ktLevelNames=" + this.ktLevelNames + ", indicatorNames=" + this.indicatorNames + ", virtualModNames=" + this.virtualModNames + ", groups=" + this.groups + ", keyNames=" + this.keyNames + ", keyAliases=" + this.keyAliases + ", radioGroupNames=" + this.radioGroupNames + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetNamesReply(final byte deviceID, final short sequenceNumber, final int which, final byte minKeyCode, final byte maxKeyCode, final byte firstKey, final short nKTLevels, final int keycodesName, final int geometryName, final int symbolsName, final int physSymbolsName, final int typesName, final int compatName, final IntList typeNames, final ByteList nLevelsPerType, final IntList ktLevelNames, final IntList indicatorNames, final IntList virtualModNames, final IntList groups, final ImmutableList<KeyName> keyNames, final ImmutableList<KeyAlias> keyAliases, final IntList radioGroupNames) {
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
    this.deviceID = deviceID;
    this.sequenceNumber = sequenceNumber;
    this.which = which;
    this.minKeyCode = minKeyCode;
    this.maxKeyCode = maxKeyCode;
    this.firstKey = firstKey;
    this.nKTLevels = nKTLevels;
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
  public static GetNamesReply.GetNamesReplyBuilder builder() {
    return new GetNamesReply.GetNamesReplyBuilder();
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
  public int getWhich() {
    return this.which;
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
  public byte getFirstKey() {
    return this.firstKey;
  }

  @java.lang.SuppressWarnings("all")
  public short getNKTLevels() {
    return this.nKTLevels;
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
  public IntList getTypeNames() {
    return this.typeNames;
  }

  @java.lang.SuppressWarnings("all")
  public ByteList getNLevelsPerType() {
    return this.nLevelsPerType;
  }

  @java.lang.SuppressWarnings("all")
  public IntList getKtLevelNames() {
    return this.ktLevelNames;
  }

  @java.lang.SuppressWarnings("all")
  public IntList getIndicatorNames() {
    return this.indicatorNames;
  }

  @java.lang.SuppressWarnings("all")
  public IntList getVirtualModNames() {
    return this.virtualModNames;
  }

  @java.lang.SuppressWarnings("all")
  public IntList getGroups() {
    return this.groups;
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<KeyName> getKeyNames() {
    return this.keyNames;
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<KeyAlias> getKeyAliases() {
    return this.keyAliases;
  }

  @java.lang.SuppressWarnings("all")
  public IntList getRadioGroupNames() {
    return this.radioGroupNames;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetNamesReply)) return false;
    final GetNamesReply other = (GetNamesReply) o;
    if (this.getDeviceID() != other.getDeviceID()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getWhich() != other.getWhich()) return false;
    if (this.getMinKeyCode() != other.getMinKeyCode()) return false;
    if (this.getMaxKeyCode() != other.getMaxKeyCode()) return false;
    if (this.getFirstKey() != other.getFirstKey()) return false;
    if (this.getNKTLevels() != other.getNKTLevels()) return false;
    if (this.getKeycodesName() != other.getKeycodesName()) return false;
    if (this.getGeometryName() != other.getGeometryName()) return false;
    if (this.getSymbolsName() != other.getSymbolsName()) return false;
    if (this.getPhysSymbolsName() != other.getPhysSymbolsName()) return false;
    if (this.getTypesName() != other.getTypesName()) return false;
    if (this.getCompatName() != other.getCompatName()) return false;
    final java.lang.Object this$typeNames = this.getTypeNames();
    final java.lang.Object other$typeNames = other.getTypeNames();
    if (this$typeNames == null ? other$typeNames != null : !this$typeNames.equals(other$typeNames)) return false;
    final java.lang.Object this$nLevelsPerType = this.getNLevelsPerType();
    final java.lang.Object other$nLevelsPerType = other.getNLevelsPerType();
    if (this$nLevelsPerType == null ? other$nLevelsPerType != null : !this$nLevelsPerType.equals(other$nLevelsPerType)) return false;
    final java.lang.Object this$ktLevelNames = this.getKtLevelNames();
    final java.lang.Object other$ktLevelNames = other.getKtLevelNames();
    if (this$ktLevelNames == null ? other$ktLevelNames != null : !this$ktLevelNames.equals(other$ktLevelNames)) return false;
    final java.lang.Object this$indicatorNames = this.getIndicatorNames();
    final java.lang.Object other$indicatorNames = other.getIndicatorNames();
    if (this$indicatorNames == null ? other$indicatorNames != null : !this$indicatorNames.equals(other$indicatorNames)) return false;
    final java.lang.Object this$virtualModNames = this.getVirtualModNames();
    final java.lang.Object other$virtualModNames = other.getVirtualModNames();
    if (this$virtualModNames == null ? other$virtualModNames != null : !this$virtualModNames.equals(other$virtualModNames)) return false;
    final java.lang.Object this$groups = this.getGroups();
    final java.lang.Object other$groups = other.getGroups();
    if (this$groups == null ? other$groups != null : !this$groups.equals(other$groups)) return false;
    final java.lang.Object this$keyNames = this.getKeyNames();
    final java.lang.Object other$keyNames = other.getKeyNames();
    if (this$keyNames == null ? other$keyNames != null : !this$keyNames.equals(other$keyNames)) return false;
    final java.lang.Object this$keyAliases = this.getKeyAliases();
    final java.lang.Object other$keyAliases = other.getKeyAliases();
    if (this$keyAliases == null ? other$keyAliases != null : !this$keyAliases.equals(other$keyAliases)) return false;
    final java.lang.Object this$radioGroupNames = this.getRadioGroupNames();
    final java.lang.Object other$radioGroupNames = other.getRadioGroupNames();
    if (this$radioGroupNames == null ? other$radioGroupNames != null : !this$radioGroupNames.equals(other$radioGroupNames)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDeviceID();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getWhich();
    result = result * PRIME + this.getMinKeyCode();
    result = result * PRIME + this.getMaxKeyCode();
    result = result * PRIME + this.getFirstKey();
    result = result * PRIME + this.getNKTLevels();
    result = result * PRIME + this.getKeycodesName();
    result = result * PRIME + this.getGeometryName();
    result = result * PRIME + this.getSymbolsName();
    result = result * PRIME + this.getPhysSymbolsName();
    result = result * PRIME + this.getTypesName();
    result = result * PRIME + this.getCompatName();
    final java.lang.Object $typeNames = this.getTypeNames();
    result = result * PRIME + ($typeNames == null ? 43 : $typeNames.hashCode());
    final java.lang.Object $nLevelsPerType = this.getNLevelsPerType();
    result = result * PRIME + ($nLevelsPerType == null ? 43 : $nLevelsPerType.hashCode());
    final java.lang.Object $ktLevelNames = this.getKtLevelNames();
    result = result * PRIME + ($ktLevelNames == null ? 43 : $ktLevelNames.hashCode());
    final java.lang.Object $indicatorNames = this.getIndicatorNames();
    result = result * PRIME + ($indicatorNames == null ? 43 : $indicatorNames.hashCode());
    final java.lang.Object $virtualModNames = this.getVirtualModNames();
    result = result * PRIME + ($virtualModNames == null ? 43 : $virtualModNames.hashCode());
    final java.lang.Object $groups = this.getGroups();
    result = result * PRIME + ($groups == null ? 43 : $groups.hashCode());
    final java.lang.Object $keyNames = this.getKeyNames();
    result = result * PRIME + ($keyNames == null ? 43 : $keyNames.hashCode());
    final java.lang.Object $keyAliases = this.getKeyAliases();
    result = result * PRIME + ($keyAliases == null ? 43 : $keyAliases.hashCode());
    final java.lang.Object $radioGroupNames = this.getRadioGroupNames();
    result = result * PRIME + ($radioGroupNames == null ? 43 : $radioGroupNames.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetNamesReply(deviceID=" + this.getDeviceID() + ", sequenceNumber=" + this.getSequenceNumber() + ", which=" + this.getWhich() + ", minKeyCode=" + this.getMinKeyCode() + ", maxKeyCode=" + this.getMaxKeyCode() + ", firstKey=" + this.getFirstKey() + ", nKTLevels=" + this.getNKTLevels() + ", keycodesName=" + this.getKeycodesName() + ", geometryName=" + this.getGeometryName() + ", symbolsName=" + this.getSymbolsName() + ", physSymbolsName=" + this.getPhysSymbolsName() + ", typesName=" + this.getTypesName() + ", compatName=" + this.getCompatName() + ", typeNames=" + this.getTypeNames() + ", nLevelsPerType=" + this.getNLevelsPerType() + ", ktLevelNames=" + this.getKtLevelNames() + ", indicatorNames=" + this.getIndicatorNames() + ", virtualModNames=" + this.getVirtualModNames() + ", groups=" + this.getGroups() + ", keyNames=" + this.getKeyNames() + ", keyAliases=" + this.getKeyAliases() + ", radioGroupNames=" + this.getRadioGroupNames() + ")";
  }
}
