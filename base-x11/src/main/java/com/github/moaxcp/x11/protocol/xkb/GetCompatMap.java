package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GetCompatMap implements TwoWayRequest<GetCompatMapReply> {
  public static final String PLUGIN_NAME = "xkb";
  public static final byte OPCODE = 10;
  private final short deviceSpec;
  private final byte groups;
  private final boolean getAllSI;
  private final short firstSI;
  private final short nSI;

  public XReplyFunction<GetCompatMapReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetCompatMapReply.readGetCompatMapReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetCompatMap readGetCompatMap(X11Input in) throws IOException {
    GetCompatMap.GetCompatMapBuilder javaBuilder = GetCompatMap.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short deviceSpec = in.readCard16();
    byte groups = in.readCard8();
    boolean getAllSI = in.readBool();
    short firstSI = in.readCard16();
    short nSI = in.readCard16();
    javaBuilder.deviceSpec(deviceSpec);
    javaBuilder.groups(groups);
    javaBuilder.getAllSI(getAllSI);
    javaBuilder.firstSI(firstSI);
    javaBuilder.nSI(nSI);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(deviceSpec);
    out.writeCard8(groups);
    out.writeBool(getAllSI);
    out.writeCard16(firstSI);
    out.writeCard16(nSI);
  }

  public boolean isGroupsEnabled(SetOfGroup... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (SetOfGroup m : maskEnums) {
      if (!m.isEnabled(groups)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetCompatMapBuilder {
    @java.lang.SuppressWarnings("all")
    private short deviceSpec;
    @java.lang.SuppressWarnings("all")
    private byte groups;
    @java.lang.SuppressWarnings("all")
    private boolean getAllSI;
    @java.lang.SuppressWarnings("all")
    private short firstSI;
    @java.lang.SuppressWarnings("all")
    private short nSI;

    public boolean isGroupsEnabled(SetOfGroup... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (SetOfGroup m : maskEnums) {
        if (!m.isEnabled(groups)) {
          return false;
        }
      }
      return true;
    }

    public GetCompatMap.GetCompatMapBuilder groupsEnable(SetOfGroup... maskEnums) {
      for (SetOfGroup m : maskEnums) {
        groups((byte) m.enableFor(groups));
      }
      return this;
    }

    public GetCompatMap.GetCompatMapBuilder groupsDisable(SetOfGroup... maskEnums) {
      for (SetOfGroup m : maskEnums) {
        groups((byte) m.disableFor(groups));
      }
      return this;
    }

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    GetCompatMapBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCompatMap.GetCompatMapBuilder deviceSpec(final short deviceSpec) {
      this.deviceSpec = deviceSpec;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCompatMap.GetCompatMapBuilder groups(final byte groups) {
      this.groups = groups;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCompatMap.GetCompatMapBuilder getAllSI(final boolean getAllSI) {
      this.getAllSI = getAllSI;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCompatMap.GetCompatMapBuilder firstSI(final short firstSI) {
      this.firstSI = firstSI;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCompatMap.GetCompatMapBuilder nSI(final short nSI) {
      this.nSI = nSI;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetCompatMap build() {
      return new GetCompatMap(this.deviceSpec, this.groups, this.getAllSI, this.firstSI, this.nSI);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetCompatMap.GetCompatMapBuilder(deviceSpec=" + this.deviceSpec + ", groups=" + this.groups + ", getAllSI=" + this.getAllSI + ", firstSI=" + this.firstSI + ", nSI=" + this.nSI + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetCompatMap(final short deviceSpec, final byte groups, final boolean getAllSI, final short firstSI, final short nSI) {
    this.deviceSpec = deviceSpec;
    this.groups = groups;
    this.getAllSI = getAllSI;
    this.firstSI = firstSI;
    this.nSI = nSI;
  }

  @java.lang.SuppressWarnings("all")
  public static GetCompatMap.GetCompatMapBuilder builder() {
    return new GetCompatMap.GetCompatMapBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getDeviceSpec() {
    return this.deviceSpec;
  }

  @java.lang.SuppressWarnings("all")
  public byte getGroups() {
    return this.groups;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isGetAllSI() {
    return this.getAllSI;
  }

  @java.lang.SuppressWarnings("all")
  public short getFirstSI() {
    return this.firstSI;
  }

  @java.lang.SuppressWarnings("all")
  public short getNSI() {
    return this.nSI;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetCompatMap)) return false;
    final GetCompatMap other = (GetCompatMap) o;
    if (this.getDeviceSpec() != other.getDeviceSpec()) return false;
    if (this.getGroups() != other.getGroups()) return false;
    if (this.isGetAllSI() != other.isGetAllSI()) return false;
    if (this.getFirstSI() != other.getFirstSI()) return false;
    if (this.getNSI() != other.getNSI()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDeviceSpec();
    result = result * PRIME + this.getGroups();
    result = result * PRIME + (this.isGetAllSI() ? 79 : 97);
    result = result * PRIME + this.getFirstSI();
    result = result * PRIME + this.getNSI();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetCompatMap(deviceSpec=" + this.getDeviceSpec() + ", groups=" + this.getGroups() + ", getAllSI=" + this.isGetAllSI() + ", firstSI=" + this.getFirstSI() + ", nSI=" + this.getNSI() + ")";
  }
}
