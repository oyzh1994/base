package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.xproto.ModMask;
import java.io.IOException;

public final class LatchLockState implements OneWayRequest {
  public static final String PLUGIN_NAME = "xkb";
  public static final byte OPCODE = 5;
  private final short deviceSpec;
  private final byte affectModLocks;
  private final byte modLocks;
  private final boolean lockGroup;
  private final byte groupLock;
  private final byte affectModLatches;
  private final boolean latchGroup;
  private final short groupLatch;

  public byte getOpCode() {
    return OPCODE;
  }

  public static LatchLockState readLatchLockState(X11Input in) throws IOException {
    LatchLockState.LatchLockStateBuilder javaBuilder = LatchLockState.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short deviceSpec = in.readCard16();
    byte affectModLocks = in.readCard8();
    byte modLocks = in.readCard8();
    boolean lockGroup = in.readBool();
    byte groupLock = in.readCard8();
    byte affectModLatches = in.readCard8();
    byte[] pad9 = in.readPad(1);
    byte[] pad10 = in.readPad(1);
    boolean latchGroup = in.readBool();
    short groupLatch = in.readCard16();
    javaBuilder.deviceSpec(deviceSpec);
    javaBuilder.affectModLocks(affectModLocks);
    javaBuilder.modLocks(modLocks);
    javaBuilder.lockGroup(lockGroup);
    javaBuilder.groupLock(groupLock);
    javaBuilder.affectModLatches(affectModLatches);
    javaBuilder.latchGroup(latchGroup);
    javaBuilder.groupLatch(groupLatch);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(deviceSpec);
    out.writeCard8(affectModLocks);
    out.writeCard8(modLocks);
    out.writeBool(lockGroup);
    out.writeCard8(groupLock);
    out.writeCard8(affectModLatches);
    out.writePad(1);
    out.writePad(1);
    out.writeBool(latchGroup);
    out.writeCard16(groupLatch);
  }

  public boolean isAffectModLocksEnabled(ModMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (ModMask m : maskEnums) {
      if (!m.isEnabled(affectModLocks)) {
        return false;
      }
    }
    return true;
  }

  public boolean isModLocksEnabled(ModMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (ModMask m : maskEnums) {
      if (!m.isEnabled(modLocks)) {
        return false;
      }
    }
    return true;
  }

  public boolean isAffectModLatchesEnabled(ModMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (ModMask m : maskEnums) {
      if (!m.isEnabled(affectModLatches)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class LatchLockStateBuilder {
    @java.lang.SuppressWarnings("all")
    private short deviceSpec;
    @java.lang.SuppressWarnings("all")
    private byte affectModLocks;
    @java.lang.SuppressWarnings("all")
    private byte modLocks;
    @java.lang.SuppressWarnings("all")
    private boolean lockGroup;
    @java.lang.SuppressWarnings("all")
    private byte groupLock;
    @java.lang.SuppressWarnings("all")
    private byte affectModLatches;
    @java.lang.SuppressWarnings("all")
    private boolean latchGroup;
    @java.lang.SuppressWarnings("all")
    private short groupLatch;

    public boolean isAffectModLocksEnabled(ModMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (ModMask m : maskEnums) {
        if (!m.isEnabled(affectModLocks)) {
          return false;
        }
      }
      return true;
    }

    public LatchLockState.LatchLockStateBuilder affectModLocksEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        affectModLocks((byte) m.enableFor(affectModLocks));
      }
      return this;
    }

    public LatchLockState.LatchLockStateBuilder affectModLocksDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        affectModLocks((byte) m.disableFor(affectModLocks));
      }
      return this;
    }

    public boolean isModLocksEnabled(ModMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (ModMask m : maskEnums) {
        if (!m.isEnabled(modLocks)) {
          return false;
        }
      }
      return true;
    }

    public LatchLockState.LatchLockStateBuilder modLocksEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        modLocks((byte) m.enableFor(modLocks));
      }
      return this;
    }

    public LatchLockState.LatchLockStateBuilder modLocksDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        modLocks((byte) m.disableFor(modLocks));
      }
      return this;
    }

    public LatchLockState.LatchLockStateBuilder groupLock(Group groupLock) {
      this.groupLock = (byte) groupLock.getValue();
      return this;
    }

    public LatchLockState.LatchLockStateBuilder groupLock(byte groupLock) {
      this.groupLock = groupLock;
      return this;
    }

    public boolean isAffectModLatchesEnabled(ModMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (ModMask m : maskEnums) {
        if (!m.isEnabled(affectModLatches)) {
          return false;
        }
      }
      return true;
    }

    public LatchLockState.LatchLockStateBuilder affectModLatchesEnable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        affectModLatches((byte) m.enableFor(affectModLatches));
      }
      return this;
    }

    public LatchLockState.LatchLockStateBuilder affectModLatchesDisable(ModMask... maskEnums) {
      for (ModMask m : maskEnums) {
        affectModLatches((byte) m.disableFor(affectModLatches));
      }
      return this;
    }

    public int getSize() {
      return 16;
    }

    @java.lang.SuppressWarnings("all")
    LatchLockStateBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public LatchLockState.LatchLockStateBuilder deviceSpec(final short deviceSpec) {
      this.deviceSpec = deviceSpec;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public LatchLockState.LatchLockStateBuilder affectModLocks(final byte affectModLocks) {
      this.affectModLocks = affectModLocks;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public LatchLockState.LatchLockStateBuilder modLocks(final byte modLocks) {
      this.modLocks = modLocks;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public LatchLockState.LatchLockStateBuilder lockGroup(final boolean lockGroup) {
      this.lockGroup = lockGroup;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public LatchLockState.LatchLockStateBuilder affectModLatches(final byte affectModLatches) {
      this.affectModLatches = affectModLatches;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public LatchLockState.LatchLockStateBuilder latchGroup(final boolean latchGroup) {
      this.latchGroup = latchGroup;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public LatchLockState.LatchLockStateBuilder groupLatch(final short groupLatch) {
      this.groupLatch = groupLatch;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public LatchLockState build() {
      return new LatchLockState(this.deviceSpec, this.affectModLocks, this.modLocks, this.lockGroup, this.groupLock, this.affectModLatches, this.latchGroup, this.groupLatch);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "LatchLockState.LatchLockStateBuilder(deviceSpec=" + this.deviceSpec + ", affectModLocks=" + this.affectModLocks + ", modLocks=" + this.modLocks + ", lockGroup=" + this.lockGroup + ", groupLock=" + this.groupLock + ", affectModLatches=" + this.affectModLatches + ", latchGroup=" + this.latchGroup + ", groupLatch=" + this.groupLatch + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  LatchLockState(final short deviceSpec, final byte affectModLocks, final byte modLocks, final boolean lockGroup, final byte groupLock, final byte affectModLatches, final boolean latchGroup, final short groupLatch) {
    this.deviceSpec = deviceSpec;
    this.affectModLocks = affectModLocks;
    this.modLocks = modLocks;
    this.lockGroup = lockGroup;
    this.groupLock = groupLock;
    this.affectModLatches = affectModLatches;
    this.latchGroup = latchGroup;
    this.groupLatch = groupLatch;
  }

  @java.lang.SuppressWarnings("all")
  public static LatchLockState.LatchLockStateBuilder builder() {
    return new LatchLockState.LatchLockStateBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getDeviceSpec() {
    return this.deviceSpec;
  }

  @java.lang.SuppressWarnings("all")
  public byte getAffectModLocks() {
    return this.affectModLocks;
  }

  @java.lang.SuppressWarnings("all")
  public byte getModLocks() {
    return this.modLocks;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isLockGroup() {
    return this.lockGroup;
  }

  @java.lang.SuppressWarnings("all")
  public byte getGroupLock() {
    return this.groupLock;
  }

  @java.lang.SuppressWarnings("all")
  public byte getAffectModLatches() {
    return this.affectModLatches;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isLatchGroup() {
    return this.latchGroup;
  }

  @java.lang.SuppressWarnings("all")
  public short getGroupLatch() {
    return this.groupLatch;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof LatchLockState)) return false;
    final LatchLockState other = (LatchLockState) o;
    if (this.getDeviceSpec() != other.getDeviceSpec()) return false;
    if (this.getAffectModLocks() != other.getAffectModLocks()) return false;
    if (this.getModLocks() != other.getModLocks()) return false;
    if (this.isLockGroup() != other.isLockGroup()) return false;
    if (this.getGroupLock() != other.getGroupLock()) return false;
    if (this.getAffectModLatches() != other.getAffectModLatches()) return false;
    if (this.isLatchGroup() != other.isLatchGroup()) return false;
    if (this.getGroupLatch() != other.getGroupLatch()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDeviceSpec();
    result = result * PRIME + this.getAffectModLocks();
    result = result * PRIME + this.getModLocks();
    result = result * PRIME + (this.isLockGroup() ? 79 : 97);
    result = result * PRIME + this.getGroupLock();
    result = result * PRIME + this.getAffectModLatches();
    result = result * PRIME + (this.isLatchGroup() ? 79 : 97);
    result = result * PRIME + this.getGroupLatch();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "LatchLockState(deviceSpec=" + this.getDeviceSpec() + ", affectModLocks=" + this.getAffectModLocks() + ", modLocks=" + this.getModLocks() + ", lockGroup=" + this.isLockGroup() + ", groupLock=" + this.getGroupLock() + ", affectModLatches=" + this.getAffectModLatches() + ", latchGroup=" + this.isLatchGroup() + ", groupLatch=" + this.getGroupLatch() + ")";
  }
}
