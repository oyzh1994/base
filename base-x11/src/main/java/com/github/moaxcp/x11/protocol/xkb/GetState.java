package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GetState implements TwoWayRequest<GetStateReply> {
  public static final String PLUGIN_NAME = "xkb";
  public static final byte OPCODE = 4;
  private final short deviceSpec;

  public XReplyFunction<GetStateReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetStateReply.readGetStateReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetState readGetState(X11Input in) throws IOException {
    GetState.GetStateBuilder javaBuilder = GetState.builder();
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


  public static class GetStateBuilder {
    @java.lang.SuppressWarnings("all")
    private short deviceSpec;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    GetStateBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetState.GetStateBuilder deviceSpec(final short deviceSpec) {
      this.deviceSpec = deviceSpec;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetState build() {
      return new GetState(this.deviceSpec);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetState.GetStateBuilder(deviceSpec=" + this.deviceSpec + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetState(final short deviceSpec) {
    this.deviceSpec = deviceSpec;
  }

  @java.lang.SuppressWarnings("all")
  public static GetState.GetStateBuilder builder() {
    return new GetState.GetStateBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getDeviceSpec() {
    return this.deviceSpec;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetState)) return false;
    final GetState other = (GetState) o;
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
    return "GetState(deviceSpec=" + this.getDeviceSpec() + ")";
  }
}
