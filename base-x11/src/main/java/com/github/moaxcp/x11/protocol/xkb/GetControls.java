package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GetControls implements TwoWayRequest<GetControlsReply> {
  public static final String PLUGIN_NAME = "xkb";
  public static final byte OPCODE = 6;
  private final short deviceSpec;

  public XReplyFunction<GetControlsReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetControlsReply.readGetControlsReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetControls readGetControls(X11Input in) throws IOException {
    GetControls.GetControlsBuilder javaBuilder = GetControls.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short deviceSpec = in.readCard16();
    byte[] pad4 = in.readPad(2);
    javaBuilder.deviceSpec(deviceSpec);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(deviceSpec);
    out.writePad(2);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetControlsBuilder {
    @java.lang.SuppressWarnings("all")
    private short deviceSpec;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    GetControlsBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetControls.GetControlsBuilder deviceSpec(final short deviceSpec) {
      this.deviceSpec = deviceSpec;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetControls build() {
      return new GetControls(this.deviceSpec);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetControls.GetControlsBuilder(deviceSpec=" + this.deviceSpec + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetControls(final short deviceSpec) {
    this.deviceSpec = deviceSpec;
  }

  @java.lang.SuppressWarnings("all")
  public static GetControls.GetControlsBuilder builder() {
    return new GetControls.GetControlsBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getDeviceSpec() {
    return this.deviceSpec;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetControls)) return false;
    final GetControls other = (GetControls) o;
    if (this.getDeviceSpec() != other.getDeviceSpec()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDeviceSpec();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetControls(deviceSpec=" + this.getDeviceSpec() + ")";
  }
}
