package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GetCrtcGammaSize implements TwoWayRequest<GetCrtcGammaSizeReply> {
  public static final String PLUGIN_NAME = "randr";
  public static final byte OPCODE = 22;
  private final int crtc;

  public XReplyFunction<GetCrtcGammaSizeReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetCrtcGammaSizeReply.readGetCrtcGammaSizeReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetCrtcGammaSize readGetCrtcGammaSize(X11Input in) throws IOException {
    GetCrtcGammaSize.GetCrtcGammaSizeBuilder javaBuilder = GetCrtcGammaSize.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int crtc = in.readCard32();
    javaBuilder.crtc(crtc);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(crtc);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetCrtcGammaSizeBuilder {
    @java.lang.SuppressWarnings("all")
    private int crtc;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    GetCrtcGammaSizeBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCrtcGammaSize.GetCrtcGammaSizeBuilder crtc(final int crtc) {
      this.crtc = crtc;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetCrtcGammaSize build() {
      return new GetCrtcGammaSize(this.crtc);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetCrtcGammaSize.GetCrtcGammaSizeBuilder(crtc=" + this.crtc + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetCrtcGammaSize(final int crtc) {
    this.crtc = crtc;
  }

  @java.lang.SuppressWarnings("all")
  public static GetCrtcGammaSize.GetCrtcGammaSizeBuilder builder() {
    return new GetCrtcGammaSize.GetCrtcGammaSizeBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getCrtc() {
    return this.crtc;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetCrtcGammaSize)) return false;
    final GetCrtcGammaSize other = (GetCrtcGammaSize) o;
    if (this.getCrtc() != other.getCrtc()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getCrtc();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetCrtcGammaSize(crtc=" + this.getCrtc() + ")";
  }
}
