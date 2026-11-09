package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.Popcount;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public final class SetCompatMap implements OneWayRequest {
  public static final String PLUGIN_NAME = "xkb";
  public static final byte OPCODE = 11;
  private final short deviceSpec;
  private final boolean recomputeActions;
  private final boolean truncateSI;
  private final short firstSI;
  private final ImmutableList<SymInterpret> si;
  private final ImmutableList<ModDef> groupMaps;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetCompatMap readSetCompatMap(X11Input in) throws IOException {
    SetCompatMap.SetCompatMapBuilder javaBuilder = SetCompatMap.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short deviceSpec = in.readCard16();
    byte[] pad4 = in.readPad(1);
    boolean recomputeActions = in.readBool();
    boolean truncateSI = in.readBool();
    byte groups = in.readCard8();
    short firstSI = in.readCard16();
    short nSI = in.readCard16();
    byte[] pad10 = in.readPad(2);
    MutableList<SymInterpret> si = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(nSI));
    for (int i = 0; i < Short.toUnsignedInt(nSI); i++) {
      si.add(SymInterpret.readSymInterpret(in));
    }
    MutableList<ModDef> groupMaps = Lists.mutable.withInitialCapacity(Popcount.popcount(Byte.toUnsignedInt(groups)));
    for (int i = 0; i < Popcount.popcount(Byte.toUnsignedInt(groups)); i++) {
      groupMaps.add(ModDef.readModDef(in));
    }
    javaBuilder.deviceSpec(deviceSpec);
    javaBuilder.recomputeActions(recomputeActions);
    javaBuilder.truncateSI(truncateSI);
    javaBuilder.firstSI(firstSI);
    javaBuilder.si(si.toImmutable());
    javaBuilder.groupMaps(groupMaps.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(deviceSpec);
    out.writePad(1);
    out.writeBool(recomputeActions);
    out.writeBool(truncateSI);
    byte groups = (byte) groupMaps.size();
    out.writeCard8(groups);
    out.writeCard16(firstSI);
    short nSI = (short) si.size();
    out.writeCard16(nSI);
    out.writePad(2);
    for (SymInterpret t : si) {
      t.write(out);
    }
    for (ModDef t : groupMaps) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 16 + XObject.sizeOf(si) + XObject.sizeOf(groupMaps);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetCompatMapBuilder {
    @java.lang.SuppressWarnings("all")
    private short deviceSpec;
    @java.lang.SuppressWarnings("all")
    private boolean recomputeActions;
    @java.lang.SuppressWarnings("all")
    private boolean truncateSI;
    @java.lang.SuppressWarnings("all")
    private short firstSI;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<SymInterpret> si;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<ModDef> groupMaps;

    public int getSize() {
      return 16 + XObject.sizeOf(si) + XObject.sizeOf(groupMaps);
    }

    @java.lang.SuppressWarnings("all")
    SetCompatMapBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetCompatMap.SetCompatMapBuilder deviceSpec(final short deviceSpec) {
      this.deviceSpec = deviceSpec;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetCompatMap.SetCompatMapBuilder recomputeActions(final boolean recomputeActions) {
      this.recomputeActions = recomputeActions;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetCompatMap.SetCompatMapBuilder truncateSI(final boolean truncateSI) {
      this.truncateSI = truncateSI;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetCompatMap.SetCompatMapBuilder firstSI(final short firstSI) {
      this.firstSI = firstSI;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetCompatMap.SetCompatMapBuilder si(final ImmutableList<SymInterpret> si) {
      if (si == null) {
        throw new java.lang.NullPointerException("si is marked non-null but is null");
      }
      this.si = si;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetCompatMap.SetCompatMapBuilder groupMaps(final ImmutableList<ModDef> groupMaps) {
      if (groupMaps == null) {
        throw new java.lang.NullPointerException("groupMaps is marked non-null but is null");
      }
      this.groupMaps = groupMaps;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetCompatMap build() {
      return new SetCompatMap(this.deviceSpec, this.recomputeActions, this.truncateSI, this.firstSI, this.si, this.groupMaps);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetCompatMap.SetCompatMapBuilder(deviceSpec=" + this.deviceSpec + ", recomputeActions=" + this.recomputeActions + ", truncateSI=" + this.truncateSI + ", firstSI=" + this.firstSI + ", si=" + this.si + ", groupMaps=" + this.groupMaps + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetCompatMap(final short deviceSpec, final boolean recomputeActions, final boolean truncateSI, final short firstSI, final ImmutableList<SymInterpret> si, final ImmutableList<ModDef> groupMaps) {
    if (si == null) {
      throw new java.lang.NullPointerException("si is marked non-null but is null");
    }
    if (groupMaps == null) {
      throw new java.lang.NullPointerException("groupMaps is marked non-null but is null");
    }
    this.deviceSpec = deviceSpec;
    this.recomputeActions = recomputeActions;
    this.truncateSI = truncateSI;
    this.firstSI = firstSI;
    this.si = si;
    this.groupMaps = groupMaps;
  }

  @java.lang.SuppressWarnings("all")
  public static SetCompatMap.SetCompatMapBuilder builder() {
    return new SetCompatMap.SetCompatMapBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getDeviceSpec() {
    return this.deviceSpec;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isRecomputeActions() {
    return this.recomputeActions;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isTruncateSI() {
    return this.truncateSI;
  }

  @java.lang.SuppressWarnings("all")
  public short getFirstSI() {
    return this.firstSI;
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<SymInterpret> getSi() {
    return this.si;
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<ModDef> getGroupMaps() {
    return this.groupMaps;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetCompatMap)) return false;
    final SetCompatMap other = (SetCompatMap) o;
    if (this.getDeviceSpec() != other.getDeviceSpec()) return false;
    if (this.isRecomputeActions() != other.isRecomputeActions()) return false;
    if (this.isTruncateSI() != other.isTruncateSI()) return false;
    if (this.getFirstSI() != other.getFirstSI()) return false;
    final java.lang.Object this$si = this.getSi();
    final java.lang.Object other$si = other.getSi();
    if (this$si == null ? other$si != null : !this$si.equals(other$si)) return false;
    final java.lang.Object this$groupMaps = this.getGroupMaps();
    final java.lang.Object other$groupMaps = other.getGroupMaps();
    if (this$groupMaps == null ? other$groupMaps != null : !this$groupMaps.equals(other$groupMaps)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDeviceSpec();
    result = result * PRIME + (this.isRecomputeActions() ? 79 : 97);
    result = result * PRIME + (this.isTruncateSI() ? 79 : 97);
    result = result * PRIME + this.getFirstSI();
    final java.lang.Object $si = this.getSi();
    result = result * PRIME + ($si == null ? 43 : $si.hashCode());
    final java.lang.Object $groupMaps = this.getGroupMaps();
    result = result * PRIME + ($groupMaps == null ? 43 : $groupMaps.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetCompatMap(deviceSpec=" + this.getDeviceSpec() + ", recomputeActions=" + this.isRecomputeActions() + ", truncateSI=" + this.isTruncateSI() + ", firstSI=" + this.getFirstSI() + ", si=" + this.getSi() + ", groupMaps=" + this.getGroupMaps() + ")";
  }
}
