package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GetIndicatorMap implements TwoWayRequest<GetIndicatorMapReply> {
  public static final String PLUGIN_NAME = "xkb";
  public static final byte OPCODE = 13;
  private final short deviceSpec;
  private final int which;

  public XReplyFunction<GetIndicatorMapReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetIndicatorMapReply.readGetIndicatorMapReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetIndicatorMap readGetIndicatorMap(X11Input in) throws IOException {
    GetIndicatorMap.GetIndicatorMapBuilder javaBuilder = GetIndicatorMap.builder();
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

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetIndicatorMapBuilder {
    @java.lang.SuppressWarnings("all")
    private short deviceSpec;
    @java.lang.SuppressWarnings("all")
    private int which;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    GetIndicatorMapBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetIndicatorMap.GetIndicatorMapBuilder deviceSpec(final short deviceSpec) {
      this.deviceSpec = deviceSpec;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetIndicatorMap.GetIndicatorMapBuilder which(final int which) {
      this.which = which;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetIndicatorMap build() {
      return new GetIndicatorMap(this.deviceSpec, this.which);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetIndicatorMap.GetIndicatorMapBuilder(deviceSpec=" + this.deviceSpec + ", which=" + this.which + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetIndicatorMap(final short deviceSpec, final int which) {
    this.deviceSpec = deviceSpec;
    this.which = which;
  }

  @java.lang.SuppressWarnings("all")
  public static GetIndicatorMap.GetIndicatorMapBuilder builder() {
    return new GetIndicatorMap.GetIndicatorMapBuilder();
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
    if (!(o instanceof GetIndicatorMap)) return false;
    final GetIndicatorMap other = (GetIndicatorMap) o;
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
    return "GetIndicatorMap(deviceSpec=" + this.getDeviceSpec() + ", which=" + this.getWhich() + ")";
  }
}
