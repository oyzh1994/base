package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GetCrtcInfo implements TwoWayRequest<GetCrtcInfoReply> {
  public static final String PLUGIN_NAME = "randr";
  public static final byte OPCODE = 20;
  private final int crtc;
  private final int configTimestamp;

  public XReplyFunction<GetCrtcInfoReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetCrtcInfoReply.readGetCrtcInfoReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetCrtcInfo readGetCrtcInfo(X11Input in) throws IOException {
    GetCrtcInfo.GetCrtcInfoBuilder javaBuilder = GetCrtcInfo.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int crtc = in.readCard32();
    int configTimestamp = in.readCard32();
    javaBuilder.crtc(crtc);
    javaBuilder.configTimestamp(configTimestamp);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(crtc);
    out.writeCard32(configTimestamp);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetCrtcInfoBuilder {
    @java.lang.SuppressWarnings("all")
    private int crtc;
    @java.lang.SuppressWarnings("all")
    private int configTimestamp;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    GetCrtcInfoBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCrtcInfo.GetCrtcInfoBuilder crtc(final int crtc) {
      this.crtc = crtc;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCrtcInfo.GetCrtcInfoBuilder configTimestamp(final int configTimestamp) {
      this.configTimestamp = configTimestamp;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetCrtcInfo build() {
      return new GetCrtcInfo(this.crtc, this.configTimestamp);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetCrtcInfo.GetCrtcInfoBuilder(crtc=" + this.crtc + ", configTimestamp=" + this.configTimestamp + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetCrtcInfo(final int crtc, final int configTimestamp) {
    this.crtc = crtc;
    this.configTimestamp = configTimestamp;
  }

  @java.lang.SuppressWarnings("all")
  public static GetCrtcInfo.GetCrtcInfoBuilder builder() {
    return new GetCrtcInfo.GetCrtcInfoBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getCrtc() {
    return this.crtc;
  }

  @java.lang.SuppressWarnings("all")
  public int getConfigTimestamp() {
    return this.configTimestamp;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetCrtcInfo)) return false;
    final GetCrtcInfo other = (GetCrtcInfo) o;
    if (this.getCrtc() != other.getCrtc()) return false;
    if (this.getConfigTimestamp() != other.getConfigTimestamp()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getCrtc();
    result = result * PRIME + this.getConfigTimestamp();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetCrtcInfo(crtc=" + this.getCrtc() + ", configTimestamp=" + this.getConfigTimestamp() + ")";
  }
}
