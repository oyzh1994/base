package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.ByteList;

public final class GetDeviceInfoReply implements XReply {
  public static final String PLUGIN_NAME = "xkb";
  private final byte deviceID;
  private final short sequenceNumber;
  private final short present;
  private final short supported;
  private final short unsupported;
  private final byte firstBtnWanted;
  private final byte nBtnsWanted;
  private final byte firstBtnRtrn;
  private final byte totalBtns;
  private final boolean hasOwnState;
  private final short dfltKbdFB;
  private final short dfltLedFB;
  private final int devType;
  private final ByteList name;
  private final ImmutableList<ActionUnion> btnActions;
  private final ImmutableList<DeviceLedInfo> leds;

  public static GetDeviceInfoReply readGetDeviceInfoReply(byte deviceID, short sequenceNumber, X11Input in) throws IOException {
    GetDeviceInfoReply.GetDeviceInfoReplyBuilder javaBuilder = GetDeviceInfoReply.builder();
    int length = in.readCard32();
    short present = in.readCard16();
    short supported = in.readCard16();
    short unsupported = in.readCard16();
    short nDeviceLedFBs = in.readCard16();
    byte firstBtnWanted = in.readCard8();
    byte nBtnsWanted = in.readCard8();
    byte firstBtnRtrn = in.readCard8();
    byte nBtnsRtrn = in.readCard8();
    byte totalBtns = in.readCard8();
    boolean hasOwnState = in.readBool();
    short dfltKbdFB = in.readCard16();
    short dfltLedFB = in.readCard16();
    byte[] pad16 = in.readPad(2);
    int devType = in.readCard32();
    short nameLen = in.readCard16();
    ByteList name = in.readChar(Short.toUnsignedInt(nameLen));
    in.readPadAlign(Short.toUnsignedInt(nameLen));
    MutableList<ActionUnion> btnActions = Lists.mutable.withInitialCapacity(Byte.toUnsignedInt(nBtnsRtrn));
    for (int i = 0; i < Byte.toUnsignedInt(nBtnsRtrn); i++) {
      btnActions.add(ActionUnion.readActionUnion(in));
    }
    MutableList<DeviceLedInfo> leds = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(nDeviceLedFBs));
    for (int i = 0; i < Short.toUnsignedInt(nDeviceLedFBs); i++) {
      leds.add(DeviceLedInfo.readDeviceLedInfo(in));
    }
    javaBuilder.deviceID(deviceID);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.present(present);
    javaBuilder.supported(supported);
    javaBuilder.unsupported(unsupported);
    javaBuilder.firstBtnWanted(firstBtnWanted);
    javaBuilder.nBtnsWanted(nBtnsWanted);
    javaBuilder.firstBtnRtrn(firstBtnRtrn);
    javaBuilder.totalBtns(totalBtns);
    javaBuilder.hasOwnState(hasOwnState);
    javaBuilder.dfltKbdFB(dfltKbdFB);
    javaBuilder.dfltLedFB(dfltLedFB);
    javaBuilder.devType(devType);
    javaBuilder.name(name.toImmutable());
    javaBuilder.btnActions(btnActions.toImmutable());
    javaBuilder.leds(leds.toImmutable());
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
    out.writeCard16(present);
    out.writeCard16(supported);
    out.writeCard16(unsupported);
    short nDeviceLedFBs = (short) leds.size();
    out.writeCard16(nDeviceLedFBs);
    out.writeCard8(firstBtnWanted);
    out.writeCard8(nBtnsWanted);
    out.writeCard8(firstBtnRtrn);
    byte nBtnsRtrn = (byte) btnActions.size();
    out.writeCard8(nBtnsRtrn);
    out.writeCard8(totalBtns);
    out.writeBool(hasOwnState);
    out.writeCard16(dfltKbdFB);
    out.writeCard16(dfltLedFB);
    out.writePad(2);
    out.writeCard32(devType);
    short nameLen = (short) name.size();
    out.writeCard16(nameLen);
    out.writeChar(name);
    out.writePadAlign(Short.toUnsignedInt(nameLen));
    for (ActionUnion t : btnActions) {
      t.write(out);
    }
    for (DeviceLedInfo t : leds) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  public boolean isPresentEnabled(XIFeature... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (XIFeature m : maskEnums) {
      if (!m.isEnabled(present)) {
        return false;
      }
    }
    return true;
  }

  public boolean isSupportedEnabled(XIFeature... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (XIFeature m : maskEnums) {
      if (!m.isEnabled(supported)) {
        return false;
      }
    }
    return true;
  }

  public boolean isUnsupportedEnabled(XIFeature... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (XIFeature m : maskEnums) {
      if (!m.isEnabled(unsupported)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 34 + 1 * name.size() + XObject.getSizeForPadAlign(4, 1 * name.size()) + XObject.sizeOf(btnActions) + XObject.sizeOf(leds);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetDeviceInfoReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte deviceID;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private short present;
    @java.lang.SuppressWarnings("all")
    private short supported;
    @java.lang.SuppressWarnings("all")
    private short unsupported;
    @java.lang.SuppressWarnings("all")
    private byte firstBtnWanted;
    @java.lang.SuppressWarnings("all")
    private byte nBtnsWanted;
    @java.lang.SuppressWarnings("all")
    private byte firstBtnRtrn;
    @java.lang.SuppressWarnings("all")
    private byte totalBtns;
    @java.lang.SuppressWarnings("all")
    private boolean hasOwnState;
    @java.lang.SuppressWarnings("all")
    private short dfltKbdFB;
    @java.lang.SuppressWarnings("all")
    private short dfltLedFB;
    @java.lang.SuppressWarnings("all")
    private int devType;
    @java.lang.SuppressWarnings("all")
    private ByteList name;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<ActionUnion> btnActions;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<DeviceLedInfo> leds;

    public boolean isPresentEnabled(XIFeature... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (XIFeature m : maskEnums) {
        if (!m.isEnabled(present)) {
          return false;
        }
      }
      return true;
    }

    public GetDeviceInfoReply.GetDeviceInfoReplyBuilder presentEnable(XIFeature... maskEnums) {
      for (XIFeature m : maskEnums) {
        present((short) m.enableFor(present));
      }
      return this;
    }

    public GetDeviceInfoReply.GetDeviceInfoReplyBuilder presentDisable(XIFeature... maskEnums) {
      for (XIFeature m : maskEnums) {
        present((short) m.disableFor(present));
      }
      return this;
    }

    public boolean isSupportedEnabled(XIFeature... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (XIFeature m : maskEnums) {
        if (!m.isEnabled(supported)) {
          return false;
        }
      }
      return true;
    }

    public GetDeviceInfoReply.GetDeviceInfoReplyBuilder supportedEnable(XIFeature... maskEnums) {
      for (XIFeature m : maskEnums) {
        supported((short) m.enableFor(supported));
      }
      return this;
    }

    public GetDeviceInfoReply.GetDeviceInfoReplyBuilder supportedDisable(XIFeature... maskEnums) {
      for (XIFeature m : maskEnums) {
        supported((short) m.disableFor(supported));
      }
      return this;
    }

    public boolean isUnsupportedEnabled(XIFeature... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (XIFeature m : maskEnums) {
        if (!m.isEnabled(unsupported)) {
          return false;
        }
      }
      return true;
    }

    public GetDeviceInfoReply.GetDeviceInfoReplyBuilder unsupportedEnable(XIFeature... maskEnums) {
      for (XIFeature m : maskEnums) {
        unsupported((short) m.enableFor(unsupported));
      }
      return this;
    }

    public GetDeviceInfoReply.GetDeviceInfoReplyBuilder unsupportedDisable(XIFeature... maskEnums) {
      for (XIFeature m : maskEnums) {
        unsupported((short) m.disableFor(unsupported));
      }
      return this;
    }

    public int getSize() {
      return 34 + 1 * name.size() + XObject.getSizeForPadAlign(4, 1 * name.size()) + XObject.sizeOf(btnActions) + XObject.sizeOf(leds);
    }

    @java.lang.SuppressWarnings("all")
    GetDeviceInfoReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetDeviceInfoReply.GetDeviceInfoReplyBuilder deviceID(final byte deviceID) {
      this.deviceID = deviceID;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetDeviceInfoReply.GetDeviceInfoReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetDeviceInfoReply.GetDeviceInfoReplyBuilder present(final short present) {
      this.present = present;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetDeviceInfoReply.GetDeviceInfoReplyBuilder supported(final short supported) {
      this.supported = supported;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetDeviceInfoReply.GetDeviceInfoReplyBuilder unsupported(final short unsupported) {
      this.unsupported = unsupported;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetDeviceInfoReply.GetDeviceInfoReplyBuilder firstBtnWanted(final byte firstBtnWanted) {
      this.firstBtnWanted = firstBtnWanted;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetDeviceInfoReply.GetDeviceInfoReplyBuilder nBtnsWanted(final byte nBtnsWanted) {
      this.nBtnsWanted = nBtnsWanted;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetDeviceInfoReply.GetDeviceInfoReplyBuilder firstBtnRtrn(final byte firstBtnRtrn) {
      this.firstBtnRtrn = firstBtnRtrn;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetDeviceInfoReply.GetDeviceInfoReplyBuilder totalBtns(final byte totalBtns) {
      this.totalBtns = totalBtns;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetDeviceInfoReply.GetDeviceInfoReplyBuilder hasOwnState(final boolean hasOwnState) {
      this.hasOwnState = hasOwnState;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetDeviceInfoReply.GetDeviceInfoReplyBuilder dfltKbdFB(final short dfltKbdFB) {
      this.dfltKbdFB = dfltKbdFB;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetDeviceInfoReply.GetDeviceInfoReplyBuilder dfltLedFB(final short dfltLedFB) {
      this.dfltLedFB = dfltLedFB;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetDeviceInfoReply.GetDeviceInfoReplyBuilder devType(final int devType) {
      this.devType = devType;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetDeviceInfoReply.GetDeviceInfoReplyBuilder name(final ByteList name) {
      if (name == null) {
        throw new java.lang.NullPointerException("name is marked non-null but is null");
      }
      this.name = name;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetDeviceInfoReply.GetDeviceInfoReplyBuilder btnActions(final ImmutableList<ActionUnion> btnActions) {
      if (btnActions == null) {
        throw new java.lang.NullPointerException("btnActions is marked non-null but is null");
      }
      this.btnActions = btnActions;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetDeviceInfoReply.GetDeviceInfoReplyBuilder leds(final ImmutableList<DeviceLedInfo> leds) {
      if (leds == null) {
        throw new java.lang.NullPointerException("leds is marked non-null but is null");
      }
      this.leds = leds;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetDeviceInfoReply build() {
      return new GetDeviceInfoReply(this.deviceID, this.sequenceNumber, this.present, this.supported, this.unsupported, this.firstBtnWanted, this.nBtnsWanted, this.firstBtnRtrn, this.totalBtns, this.hasOwnState, this.dfltKbdFB, this.dfltLedFB, this.devType, this.name, this.btnActions, this.leds);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetDeviceInfoReply.GetDeviceInfoReplyBuilder(deviceID=" + this.deviceID + ", sequenceNumber=" + this.sequenceNumber + ", present=" + this.present + ", supported=" + this.supported + ", unsupported=" + this.unsupported + ", firstBtnWanted=" + this.firstBtnWanted + ", nBtnsWanted=" + this.nBtnsWanted + ", firstBtnRtrn=" + this.firstBtnRtrn + ", totalBtns=" + this.totalBtns + ", hasOwnState=" + this.hasOwnState + ", dfltKbdFB=" + this.dfltKbdFB + ", dfltLedFB=" + this.dfltLedFB + ", devType=" + this.devType + ", name=" + this.name + ", btnActions=" + this.btnActions + ", leds=" + this.leds + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetDeviceInfoReply(final byte deviceID, final short sequenceNumber, final short present, final short supported, final short unsupported, final byte firstBtnWanted, final byte nBtnsWanted, final byte firstBtnRtrn, final byte totalBtns, final boolean hasOwnState, final short dfltKbdFB, final short dfltLedFB, final int devType, final ByteList name, final ImmutableList<ActionUnion> btnActions, final ImmutableList<DeviceLedInfo> leds) {
    if (name == null) {
      throw new java.lang.NullPointerException("name is marked non-null but is null");
    }
    if (btnActions == null) {
      throw new java.lang.NullPointerException("btnActions is marked non-null but is null");
    }
    if (leds == null) {
      throw new java.lang.NullPointerException("leds is marked non-null but is null");
    }
    this.deviceID = deviceID;
    this.sequenceNumber = sequenceNumber;
    this.present = present;
    this.supported = supported;
    this.unsupported = unsupported;
    this.firstBtnWanted = firstBtnWanted;
    this.nBtnsWanted = nBtnsWanted;
    this.firstBtnRtrn = firstBtnRtrn;
    this.totalBtns = totalBtns;
    this.hasOwnState = hasOwnState;
    this.dfltKbdFB = dfltKbdFB;
    this.dfltLedFB = dfltLedFB;
    this.devType = devType;
    this.name = name;
    this.btnActions = btnActions;
    this.leds = leds;
  }

  @java.lang.SuppressWarnings("all")
  public static GetDeviceInfoReply.GetDeviceInfoReplyBuilder builder() {
    return new GetDeviceInfoReply.GetDeviceInfoReplyBuilder();
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
  public short getPresent() {
    return this.present;
  }

  @java.lang.SuppressWarnings("all")
  public short getSupported() {
    return this.supported;
  }

  @java.lang.SuppressWarnings("all")
  public short getUnsupported() {
    return this.unsupported;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFirstBtnWanted() {
    return this.firstBtnWanted;
  }

  @java.lang.SuppressWarnings("all")
  public byte getNBtnsWanted() {
    return this.nBtnsWanted;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFirstBtnRtrn() {
    return this.firstBtnRtrn;
  }

  @java.lang.SuppressWarnings("all")
  public byte getTotalBtns() {
    return this.totalBtns;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isHasOwnState() {
    return this.hasOwnState;
  }

  @java.lang.SuppressWarnings("all")
  public short getDfltKbdFB() {
    return this.dfltKbdFB;
  }

  @java.lang.SuppressWarnings("all")
  public short getDfltLedFB() {
    return this.dfltLedFB;
  }

  @java.lang.SuppressWarnings("all")
  public int getDevType() {
    return this.devType;
  }

  @java.lang.SuppressWarnings("all")
  public ByteList getName() {
    return this.name;
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<ActionUnion> getBtnActions() {
    return this.btnActions;
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<DeviceLedInfo> getLeds() {
    return this.leds;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetDeviceInfoReply)) return false;
    final GetDeviceInfoReply other = (GetDeviceInfoReply) o;
    if (this.getDeviceID() != other.getDeviceID()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getPresent() != other.getPresent()) return false;
    if (this.getSupported() != other.getSupported()) return false;
    if (this.getUnsupported() != other.getUnsupported()) return false;
    if (this.getFirstBtnWanted() != other.getFirstBtnWanted()) return false;
    if (this.getNBtnsWanted() != other.getNBtnsWanted()) return false;
    if (this.getFirstBtnRtrn() != other.getFirstBtnRtrn()) return false;
    if (this.getTotalBtns() != other.getTotalBtns()) return false;
    if (this.isHasOwnState() != other.isHasOwnState()) return false;
    if (this.getDfltKbdFB() != other.getDfltKbdFB()) return false;
    if (this.getDfltLedFB() != other.getDfltLedFB()) return false;
    if (this.getDevType() != other.getDevType()) return false;
    final java.lang.Object this$name = this.getName();
    final java.lang.Object other$name = other.getName();
    if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
    final java.lang.Object this$btnActions = this.getBtnActions();
    final java.lang.Object other$btnActions = other.getBtnActions();
    if (this$btnActions == null ? other$btnActions != null : !this$btnActions.equals(other$btnActions)) return false;
    final java.lang.Object this$leds = this.getLeds();
    final java.lang.Object other$leds = other.getLeds();
    if (this$leds == null ? other$leds != null : !this$leds.equals(other$leds)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDeviceID();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getPresent();
    result = result * PRIME + this.getSupported();
    result = result * PRIME + this.getUnsupported();
    result = result * PRIME + this.getFirstBtnWanted();
    result = result * PRIME + this.getNBtnsWanted();
    result = result * PRIME + this.getFirstBtnRtrn();
    result = result * PRIME + this.getTotalBtns();
    result = result * PRIME + (this.isHasOwnState() ? 79 : 97);
    result = result * PRIME + this.getDfltKbdFB();
    result = result * PRIME + this.getDfltLedFB();
    result = result * PRIME + this.getDevType();
    final java.lang.Object $name = this.getName();
    result = result * PRIME + ($name == null ? 43 : $name.hashCode());
    final java.lang.Object $btnActions = this.getBtnActions();
    result = result * PRIME + ($btnActions == null ? 43 : $btnActions.hashCode());
    final java.lang.Object $leds = this.getLeds();
    result = result * PRIME + ($leds == null ? 43 : $leds.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetDeviceInfoReply(deviceID=" + this.getDeviceID() + ", sequenceNumber=" + this.getSequenceNumber() + ", present=" + this.getPresent() + ", supported=" + this.getSupported() + ", unsupported=" + this.getUnsupported() + ", firstBtnWanted=" + this.getFirstBtnWanted() + ", nBtnsWanted=" + this.getNBtnsWanted() + ", firstBtnRtrn=" + this.getFirstBtnRtrn() + ", totalBtns=" + this.getTotalBtns() + ", hasOwnState=" + this.isHasOwnState() + ", dfltKbdFB=" + this.getDfltKbdFB() + ", dfltLedFB=" + this.getDfltLedFB() + ", devType=" + this.getDevType() + ", name=" + this.getName() + ", btnActions=" + this.getBtnActions() + ", leds=" + this.getLeds() + ")";
  }
}
