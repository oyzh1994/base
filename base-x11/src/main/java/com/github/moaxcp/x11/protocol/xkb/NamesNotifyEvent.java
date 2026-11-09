package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;

public final class NamesNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "xkb";
  public static final byte NUMBER = 6;
  private final byte firstEventOffset;
  private final boolean sentEvent;
  private final byte xkbType;
  private final short sequenceNumber;
  private final int time;
  private final byte deviceID;
  private final short changed;
  private final byte firstType;
  private final byte nTypes;
  private final byte firstLevelName;
  private final byte nLevelNames;
  private final byte nRadioGroups;
  private final byte nKeyAliases;
  private final byte changedGroupNames;
  private final short changedVirtualMods;
  private final byte firstKey;
  private final byte nKeys;
  private final int changedIndicators;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static NamesNotifyEvent readNamesNotifyEvent(byte firstEventOffset, boolean sentEvent, X11Input in) throws IOException {
    NamesNotifyEvent.NamesNotifyEventBuilder javaBuilder = NamesNotifyEvent.builder();
    byte xkbType = in.readCard8();
    short sequenceNumber = in.readCard16();
    int time = in.readCard32();
    byte deviceID = in.readCard8();
    byte[] pad5 = in.readPad(1);
    short changed = in.readCard16();
    byte firstType = in.readCard8();
    byte nTypes = in.readCard8();
    byte firstLevelName = in.readCard8();
    byte nLevelNames = in.readCard8();
    byte[] pad11 = in.readPad(1);
    byte nRadioGroups = in.readCard8();
    byte nKeyAliases = in.readCard8();
    byte changedGroupNames = in.readCard8();
    short changedVirtualMods = in.readCard16();
    byte firstKey = in.readCard8();
    byte nKeys = in.readCard8();
    int changedIndicators = in.readCard32();
    byte[] pad19 = in.readPad(4);
    javaBuilder.xkbType(xkbType);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.time(time);
    javaBuilder.deviceID(deviceID);
    javaBuilder.changed(changed);
    javaBuilder.firstType(firstType);
    javaBuilder.nTypes(nTypes);
    javaBuilder.firstLevelName(firstLevelName);
    javaBuilder.nLevelNames(nLevelNames);
    javaBuilder.nRadioGroups(nRadioGroups);
    javaBuilder.nKeyAliases(nKeyAliases);
    javaBuilder.changedGroupNames(changedGroupNames);
    javaBuilder.changedVirtualMods(changedVirtualMods);
    javaBuilder.firstKey(firstKey);
    javaBuilder.nKeys(nKeys);
    javaBuilder.changedIndicators(changedIndicators);
    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (128 | getResponseCode()) : getResponseCode());
    out.writeCard8(xkbType);
    out.writeCard16(sequenceNumber);
    out.writeCard32(time);
    out.writeCard8(deviceID);
    out.writePad(1);
    out.writeCard16(changed);
    out.writeCard8(firstType);
    out.writeCard8(nTypes);
    out.writeCard8(firstLevelName);
    out.writeCard8(nLevelNames);
    out.writePad(1);
    out.writeCard8(nRadioGroups);
    out.writeCard8(nKeyAliases);
    out.writeCard8(changedGroupNames);
    out.writeCard16(changedVirtualMods);
    out.writeCard8(firstKey);
    out.writeCard8(nKeys);
    out.writeCard32(changedIndicators);
    out.writePad(4);
  }

  public boolean isChangedEnabled(NameDetail... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (NameDetail m : maskEnums) {
      if (!m.isEnabled(changed)) {
        return false;
      }
    }
    return true;
  }

  public boolean isChangedGroupNamesEnabled(SetOfGroup... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (SetOfGroup m : maskEnums) {
      if (!m.isEnabled(changedGroupNames)) {
        return false;
      }
    }
    return true;
  }

  public boolean isChangedVirtualModsEnabled(VMod... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (VMod m : maskEnums) {
      if (!m.isEnabled(changedVirtualMods)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class NamesNotifyEventBuilder {
    @java.lang.SuppressWarnings("all")
    private byte firstEventOffset;
    @java.lang.SuppressWarnings("all")
    private boolean sentEvent;
    @java.lang.SuppressWarnings("all")
    private byte xkbType;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int time;
    @java.lang.SuppressWarnings("all")
    private byte deviceID;
    @java.lang.SuppressWarnings("all")
    private short changed;
    @java.lang.SuppressWarnings("all")
    private byte firstType;
    @java.lang.SuppressWarnings("all")
    private byte nTypes;
    @java.lang.SuppressWarnings("all")
    private byte firstLevelName;
    @java.lang.SuppressWarnings("all")
    private byte nLevelNames;
    @java.lang.SuppressWarnings("all")
    private byte nRadioGroups;
    @java.lang.SuppressWarnings("all")
    private byte nKeyAliases;
    @java.lang.SuppressWarnings("all")
    private byte changedGroupNames;
    @java.lang.SuppressWarnings("all")
    private short changedVirtualMods;
    @java.lang.SuppressWarnings("all")
    private byte firstKey;
    @java.lang.SuppressWarnings("all")
    private byte nKeys;
    @java.lang.SuppressWarnings("all")
    private int changedIndicators;

    public boolean isChangedEnabled(NameDetail... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (NameDetail m : maskEnums) {
        if (!m.isEnabled(changed)) {
          return false;
        }
      }
      return true;
    }

    public NamesNotifyEvent.NamesNotifyEventBuilder changedEnable(NameDetail... maskEnums) {
      for (NameDetail m : maskEnums) {
        changed((short) m.enableFor(changed));
      }
      return this;
    }

    public NamesNotifyEvent.NamesNotifyEventBuilder changedDisable(NameDetail... maskEnums) {
      for (NameDetail m : maskEnums) {
        changed((short) m.disableFor(changed));
      }
      return this;
    }

    public boolean isChangedGroupNamesEnabled(SetOfGroup... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (SetOfGroup m : maskEnums) {
        if (!m.isEnabled(changedGroupNames)) {
          return false;
        }
      }
      return true;
    }

    public NamesNotifyEvent.NamesNotifyEventBuilder changedGroupNamesEnable(SetOfGroup... maskEnums) {
      for (SetOfGroup m : maskEnums) {
        changedGroupNames((byte) m.enableFor(changedGroupNames));
      }
      return this;
    }

    public NamesNotifyEvent.NamesNotifyEventBuilder changedGroupNamesDisable(SetOfGroup... maskEnums) {
      for (SetOfGroup m : maskEnums) {
        changedGroupNames((byte) m.disableFor(changedGroupNames));
      }
      return this;
    }

    public boolean isChangedVirtualModsEnabled(VMod... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (VMod m : maskEnums) {
        if (!m.isEnabled(changedVirtualMods)) {
          return false;
        }
      }
      return true;
    }

    public NamesNotifyEvent.NamesNotifyEventBuilder changedVirtualModsEnable(VMod... maskEnums) {
      for (VMod m : maskEnums) {
        changedVirtualMods((short) m.enableFor(changedVirtualMods));
      }
      return this;
    }

    public NamesNotifyEvent.NamesNotifyEventBuilder changedVirtualModsDisable(VMod... maskEnums) {
      for (VMod m : maskEnums) {
        changedVirtualMods((short) m.disableFor(changedVirtualMods));
      }
      return this;
    }

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    NamesNotifyEventBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NamesNotifyEvent.NamesNotifyEventBuilder firstEventOffset(final byte firstEventOffset) {
      this.firstEventOffset = firstEventOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NamesNotifyEvent.NamesNotifyEventBuilder sentEvent(final boolean sentEvent) {
      this.sentEvent = sentEvent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NamesNotifyEvent.NamesNotifyEventBuilder xkbType(final byte xkbType) {
      this.xkbType = xkbType;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NamesNotifyEvent.NamesNotifyEventBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NamesNotifyEvent.NamesNotifyEventBuilder time(final int time) {
      this.time = time;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NamesNotifyEvent.NamesNotifyEventBuilder deviceID(final byte deviceID) {
      this.deviceID = deviceID;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NamesNotifyEvent.NamesNotifyEventBuilder changed(final short changed) {
      this.changed = changed;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NamesNotifyEvent.NamesNotifyEventBuilder firstType(final byte firstType) {
      this.firstType = firstType;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NamesNotifyEvent.NamesNotifyEventBuilder nTypes(final byte nTypes) {
      this.nTypes = nTypes;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NamesNotifyEvent.NamesNotifyEventBuilder firstLevelName(final byte firstLevelName) {
      this.firstLevelName = firstLevelName;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NamesNotifyEvent.NamesNotifyEventBuilder nLevelNames(final byte nLevelNames) {
      this.nLevelNames = nLevelNames;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NamesNotifyEvent.NamesNotifyEventBuilder nRadioGroups(final byte nRadioGroups) {
      this.nRadioGroups = nRadioGroups;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NamesNotifyEvent.NamesNotifyEventBuilder nKeyAliases(final byte nKeyAliases) {
      this.nKeyAliases = nKeyAliases;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NamesNotifyEvent.NamesNotifyEventBuilder changedGroupNames(final byte changedGroupNames) {
      this.changedGroupNames = changedGroupNames;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NamesNotifyEvent.NamesNotifyEventBuilder changedVirtualMods(final short changedVirtualMods) {
      this.changedVirtualMods = changedVirtualMods;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NamesNotifyEvent.NamesNotifyEventBuilder firstKey(final byte firstKey) {
      this.firstKey = firstKey;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NamesNotifyEvent.NamesNotifyEventBuilder nKeys(final byte nKeys) {
      this.nKeys = nKeys;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NamesNotifyEvent.NamesNotifyEventBuilder changedIndicators(final int changedIndicators) {
      this.changedIndicators = changedIndicators;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public NamesNotifyEvent build() {
      return new NamesNotifyEvent(this.firstEventOffset, this.sentEvent, this.xkbType, this.sequenceNumber, this.time, this.deviceID, this.changed, this.firstType, this.nTypes, this.firstLevelName, this.nLevelNames, this.nRadioGroups, this.nKeyAliases, this.changedGroupNames, this.changedVirtualMods, this.firstKey, this.nKeys, this.changedIndicators);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "NamesNotifyEvent.NamesNotifyEventBuilder(firstEventOffset=" + this.firstEventOffset + ", sentEvent=" + this.sentEvent + ", xkbType=" + this.xkbType + ", sequenceNumber=" + this.sequenceNumber + ", time=" + this.time + ", deviceID=" + this.deviceID + ", changed=" + this.changed + ", firstType=" + this.firstType + ", nTypes=" + this.nTypes + ", firstLevelName=" + this.firstLevelName + ", nLevelNames=" + this.nLevelNames + ", nRadioGroups=" + this.nRadioGroups + ", nKeyAliases=" + this.nKeyAliases + ", changedGroupNames=" + this.changedGroupNames + ", changedVirtualMods=" + this.changedVirtualMods + ", firstKey=" + this.firstKey + ", nKeys=" + this.nKeys + ", changedIndicators=" + this.changedIndicators + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  NamesNotifyEvent(final byte firstEventOffset, final boolean sentEvent, final byte xkbType, final short sequenceNumber, final int time, final byte deviceID, final short changed, final byte firstType, final byte nTypes, final byte firstLevelName, final byte nLevelNames, final byte nRadioGroups, final byte nKeyAliases, final byte changedGroupNames, final short changedVirtualMods, final byte firstKey, final byte nKeys, final int changedIndicators) {
    this.firstEventOffset = firstEventOffset;
    this.sentEvent = sentEvent;
    this.xkbType = xkbType;
    this.sequenceNumber = sequenceNumber;
    this.time = time;
    this.deviceID = deviceID;
    this.changed = changed;
    this.firstType = firstType;
    this.nTypes = nTypes;
    this.firstLevelName = firstLevelName;
    this.nLevelNames = nLevelNames;
    this.nRadioGroups = nRadioGroups;
    this.nKeyAliases = nKeyAliases;
    this.changedGroupNames = changedGroupNames;
    this.changedVirtualMods = changedVirtualMods;
    this.firstKey = firstKey;
    this.nKeys = nKeys;
    this.changedIndicators = changedIndicators;
  }

  @java.lang.SuppressWarnings("all")
  public static NamesNotifyEvent.NamesNotifyEventBuilder builder() {
    return new NamesNotifyEvent.NamesNotifyEventBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getFirstEventOffset() {
    return this.firstEventOffset;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isSentEvent() {
    return this.sentEvent;
  }

  @java.lang.SuppressWarnings("all")
  public byte getXkbType() {
    return this.xkbType;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getTime() {
    return this.time;
  }

  @java.lang.SuppressWarnings("all")
  public byte getDeviceID() {
    return this.deviceID;
  }

  @java.lang.SuppressWarnings("all")
  public short getChanged() {
    return this.changed;
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
  public byte getFirstLevelName() {
    return this.firstLevelName;
  }

  @java.lang.SuppressWarnings("all")
  public byte getNLevelNames() {
    return this.nLevelNames;
  }

  @java.lang.SuppressWarnings("all")
  public byte getNRadioGroups() {
    return this.nRadioGroups;
  }

  @java.lang.SuppressWarnings("all")
  public byte getNKeyAliases() {
    return this.nKeyAliases;
  }

  @java.lang.SuppressWarnings("all")
  public byte getChangedGroupNames() {
    return this.changedGroupNames;
  }

  @java.lang.SuppressWarnings("all")
  public short getChangedVirtualMods() {
    return this.changedVirtualMods;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFirstKey() {
    return this.firstKey;
  }

  @java.lang.SuppressWarnings("all")
  public byte getNKeys() {
    return this.nKeys;
  }

  @java.lang.SuppressWarnings("all")
  public int getChangedIndicators() {
    return this.changedIndicators;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof NamesNotifyEvent)) return false;
    final NamesNotifyEvent other = (NamesNotifyEvent) o;
    if (this.getFirstEventOffset() != other.getFirstEventOffset()) return false;
    if (this.isSentEvent() != other.isSentEvent()) return false;
    if (this.getXkbType() != other.getXkbType()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getTime() != other.getTime()) return false;
    if (this.getDeviceID() != other.getDeviceID()) return false;
    if (this.getChanged() != other.getChanged()) return false;
    if (this.getFirstType() != other.getFirstType()) return false;
    if (this.getNTypes() != other.getNTypes()) return false;
    if (this.getFirstLevelName() != other.getFirstLevelName()) return false;
    if (this.getNLevelNames() != other.getNLevelNames()) return false;
    if (this.getNRadioGroups() != other.getNRadioGroups()) return false;
    if (this.getNKeyAliases() != other.getNKeyAliases()) return false;
    if (this.getChangedGroupNames() != other.getChangedGroupNames()) return false;
    if (this.getChangedVirtualMods() != other.getChangedVirtualMods()) return false;
    if (this.getFirstKey() != other.getFirstKey()) return false;
    if (this.getNKeys() != other.getNKeys()) return false;
    if (this.getChangedIndicators() != other.getChangedIndicators()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFirstEventOffset();
    result = result * PRIME + (this.isSentEvent() ? 79 : 97);
    result = result * PRIME + this.getXkbType();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getTime();
    result = result * PRIME + this.getDeviceID();
    result = result * PRIME + this.getChanged();
    result = result * PRIME + this.getFirstType();
    result = result * PRIME + this.getNTypes();
    result = result * PRIME + this.getFirstLevelName();
    result = result * PRIME + this.getNLevelNames();
    result = result * PRIME + this.getNRadioGroups();
    result = result * PRIME + this.getNKeyAliases();
    result = result * PRIME + this.getChangedGroupNames();
    result = result * PRIME + this.getChangedVirtualMods();
    result = result * PRIME + this.getFirstKey();
    result = result * PRIME + this.getNKeys();
    result = result * PRIME + this.getChangedIndicators();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "NamesNotifyEvent(firstEventOffset=" + this.getFirstEventOffset() + ", sentEvent=" + this.isSentEvent() + ", xkbType=" + this.getXkbType() + ", sequenceNumber=" + this.getSequenceNumber() + ", time=" + this.getTime() + ", deviceID=" + this.getDeviceID() + ", changed=" + this.getChanged() + ", firstType=" + this.getFirstType() + ", nTypes=" + this.getNTypes() + ", firstLevelName=" + this.getFirstLevelName() + ", nLevelNames=" + this.getNLevelNames() + ", nRadioGroups=" + this.getNRadioGroups() + ", nKeyAliases=" + this.getNKeyAliases() + ", changedGroupNames=" + this.getChangedGroupNames() + ", changedVirtualMods=" + this.getChangedVirtualMods() + ", firstKey=" + this.getFirstKey() + ", nKeys=" + this.getNKeys() + ", changedIndicators=" + this.getChangedIndicators() + ")";
  }
}
