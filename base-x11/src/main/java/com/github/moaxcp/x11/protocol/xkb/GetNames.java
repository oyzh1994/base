package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GetNames implements TwoWayRequest<GetNamesReply> {
  public static final String PLUGIN_NAME = "xkb";
  public static final byte OPCODE = 17;
  private final short deviceSpec;
  private final int which;

  public XReplyFunction<GetNamesReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetNamesReply.readGetNamesReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetNames readGetNames(X11Input in) throws IOException {
    GetNames.GetNamesBuilder javaBuilder = GetNames.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short deviceSpec = in.readCard16();
    byte[] pad4 = in.readPad(2);
    int which = in.readCard32();
    javaBuilder.deviceSpec(deviceSpec);
    javaBuilder.which(which);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(deviceSpec);
    out.writePad(2);
    out.writeCard32(which);
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
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetNamesBuilder {
    @java.lang.SuppressWarnings("all")
    private short deviceSpec;
    @java.lang.SuppressWarnings("all")
    private int which;

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

    public GetNames.GetNamesBuilder whichEnable(NameDetail... maskEnums) {
      for (NameDetail m : maskEnums) {
        which((int) m.enableFor(which));
      }
      return this;
    }

    public GetNames.GetNamesBuilder whichDisable(NameDetail... maskEnums) {
      for (NameDetail m : maskEnums) {
        which((int) m.disableFor(which));
      }
      return this;
    }

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    GetNamesBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetNames.GetNamesBuilder deviceSpec(final short deviceSpec) {
      this.deviceSpec = deviceSpec;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetNames.GetNamesBuilder which(final int which) {
      this.which = which;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetNames build() {
      return new GetNames(this.deviceSpec, this.which);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetNames.GetNamesBuilder(deviceSpec=" + this.deviceSpec + ", which=" + this.which + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetNames(final short deviceSpec, final int which) {
    this.deviceSpec = deviceSpec;
    this.which = which;
  }

  @java.lang.SuppressWarnings("all")
  public static GetNames.GetNamesBuilder builder() {
    return new GetNames.GetNamesBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getDeviceSpec() {
    return this.deviceSpec;
  }

  @java.lang.SuppressWarnings("all")
  public int getWhich() {
    return this.which;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetNames)) return false;
    final GetNames other = (GetNames) o;
    if (this.getDeviceSpec() != other.getDeviceSpec()) return false;
    if (this.getWhich() != other.getWhich()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDeviceSpec();
    result = result * PRIME + this.getWhich();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetNames(deviceSpec=" + this.getDeviceSpec() + ", which=" + this.getWhich() + ")";
  }
}
