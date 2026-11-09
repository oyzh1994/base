package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.Popcount;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public final class GetCompatMapReply implements XReply {
  public static final String PLUGIN_NAME = "xkb";
  private final byte deviceID;
  private final short sequenceNumber;
  private final short firstSIRtrn;
  private final short nTotalSI;
  private final ImmutableList<SymInterpret> siRtrn;
  private final ImmutableList<ModDef> groupRtrn;

  public static GetCompatMapReply readGetCompatMapReply(byte deviceID, short sequenceNumber, X11Input in) throws IOException {
    GetCompatMapReply.GetCompatMapReplyBuilder javaBuilder = GetCompatMapReply.builder();
    int length = in.readCard32();
    byte groupsRtrn = in.readCard8();
    byte[] pad5 = in.readPad(1);
    short firstSIRtrn = in.readCard16();
    short nSIRtrn = in.readCard16();
    short nTotalSI = in.readCard16();
    byte[] pad9 = in.readPad(16);
    MutableList<SymInterpret> siRtrn = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(nSIRtrn));
    for (int i = 0; i < Short.toUnsignedInt(nSIRtrn); i++) {
      siRtrn.add(SymInterpret.readSymInterpret(in));
    }
    MutableList<ModDef> groupRtrn = Lists.mutable.withInitialCapacity(Popcount.popcount(Byte.toUnsignedInt(groupsRtrn)));
    for (int i = 0; i < Popcount.popcount(Byte.toUnsignedInt(groupsRtrn)); i++) {
      groupRtrn.add(ModDef.readModDef(in));
    }
    javaBuilder.deviceID(deviceID);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.firstSIRtrn(firstSIRtrn);
    javaBuilder.nTotalSI(nTotalSI);
    javaBuilder.siRtrn(siRtrn.toImmutable());
    javaBuilder.groupRtrn(groupRtrn.toImmutable());
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
    byte groupsRtrn = (byte) groupRtrn.size();
    out.writeCard8(groupsRtrn);
    out.writePad(1);
    out.writeCard16(firstSIRtrn);
    short nSIRtrn = (short) siRtrn.size();
    out.writeCard16(nSIRtrn);
    out.writeCard16(nTotalSI);
    out.writePad(16);
    for (SymInterpret t : siRtrn) {
      t.write(out);
    }
    for (ModDef t : groupRtrn) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(siRtrn) + XObject.sizeOf(groupRtrn);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetCompatMapReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte deviceID;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private short firstSIRtrn;
    @java.lang.SuppressWarnings("all")
    private short nTotalSI;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<SymInterpret> siRtrn;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<ModDef> groupRtrn;

    public int getSize() {
      return 32 + XObject.sizeOf(siRtrn) + XObject.sizeOf(groupRtrn);
    }

    @java.lang.SuppressWarnings("all")
    GetCompatMapReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCompatMapReply.GetCompatMapReplyBuilder deviceID(final byte deviceID) {
      this.deviceID = deviceID;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCompatMapReply.GetCompatMapReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCompatMapReply.GetCompatMapReplyBuilder firstSIRtrn(final short firstSIRtrn) {
      this.firstSIRtrn = firstSIRtrn;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCompatMapReply.GetCompatMapReplyBuilder nTotalSI(final short nTotalSI) {
      this.nTotalSI = nTotalSI;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCompatMapReply.GetCompatMapReplyBuilder siRtrn(final ImmutableList<SymInterpret> siRtrn) {
      if (siRtrn == null) {
        throw new java.lang.NullPointerException("siRtrn is marked non-null but is null");
      }
      this.siRtrn = siRtrn;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCompatMapReply.GetCompatMapReplyBuilder groupRtrn(final ImmutableList<ModDef> groupRtrn) {
      if (groupRtrn == null) {
        throw new java.lang.NullPointerException("groupRtrn is marked non-null but is null");
      }
      this.groupRtrn = groupRtrn;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetCompatMapReply build() {
      return new GetCompatMapReply(this.deviceID, this.sequenceNumber, this.firstSIRtrn, this.nTotalSI, this.siRtrn, this.groupRtrn);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetCompatMapReply.GetCompatMapReplyBuilder(deviceID=" + this.deviceID + ", sequenceNumber=" + this.sequenceNumber + ", firstSIRtrn=" + this.firstSIRtrn + ", nTotalSI=" + this.nTotalSI + ", siRtrn=" + this.siRtrn + ", groupRtrn=" + this.groupRtrn + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetCompatMapReply(final byte deviceID, final short sequenceNumber, final short firstSIRtrn, final short nTotalSI, final ImmutableList<SymInterpret> siRtrn, final ImmutableList<ModDef> groupRtrn) {
    if (siRtrn == null) {
      throw new java.lang.NullPointerException("siRtrn is marked non-null but is null");
    }
    if (groupRtrn == null) {
      throw new java.lang.NullPointerException("groupRtrn is marked non-null but is null");
    }
    this.deviceID = deviceID;
    this.sequenceNumber = sequenceNumber;
    this.firstSIRtrn = firstSIRtrn;
    this.nTotalSI = nTotalSI;
    this.siRtrn = siRtrn;
    this.groupRtrn = groupRtrn;
  }

  @java.lang.SuppressWarnings("all")
  public static GetCompatMapReply.GetCompatMapReplyBuilder builder() {
    return new GetCompatMapReply.GetCompatMapReplyBuilder();
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
  public short getFirstSIRtrn() {
    return this.firstSIRtrn;
  }

  @java.lang.SuppressWarnings("all")
  public short getNTotalSI() {
    return this.nTotalSI;
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<SymInterpret> getSiRtrn() {
    return this.siRtrn;
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<ModDef> getGroupRtrn() {
    return this.groupRtrn;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetCompatMapReply)) return false;
    final GetCompatMapReply other = (GetCompatMapReply) o;
    if (this.getDeviceID() != other.getDeviceID()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getFirstSIRtrn() != other.getFirstSIRtrn()) return false;
    if (this.getNTotalSI() != other.getNTotalSI()) return false;
    final java.lang.Object this$siRtrn = this.getSiRtrn();
    final java.lang.Object other$siRtrn = other.getSiRtrn();
    if (this$siRtrn == null ? other$siRtrn != null : !this$siRtrn.equals(other$siRtrn)) return false;
    final java.lang.Object this$groupRtrn = this.getGroupRtrn();
    final java.lang.Object other$groupRtrn = other.getGroupRtrn();
    if (this$groupRtrn == null ? other$groupRtrn != null : !this$groupRtrn.equals(other$groupRtrn)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDeviceID();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getFirstSIRtrn();
    result = result * PRIME + this.getNTotalSI();
    final java.lang.Object $siRtrn = this.getSiRtrn();
    result = result * PRIME + ($siRtrn == null ? 43 : $siRtrn.hashCode());
    final java.lang.Object $groupRtrn = this.getGroupRtrn();
    result = result * PRIME + ($groupRtrn == null ? 43 : $groupRtrn.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetCompatMapReply(deviceID=" + this.getDeviceID() + ", sequenceNumber=" + this.getSequenceNumber() + ", firstSIRtrn=" + this.getFirstSIRtrn() + ", nTotalSI=" + this.getNTotalSI() + ", siRtrn=" + this.getSiRtrn() + ", groupRtrn=" + this.getGroupRtrn() + ")";
  }
}
