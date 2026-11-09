package com.github.moaxcp.x11.protocol.xc_misc;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GetXIDRange implements TwoWayRequest<GetXIDRangeReply> {
  public static final String PLUGIN_NAME = "xc_misc";
  public static final byte OPCODE = 1;

  public XReplyFunction<GetXIDRangeReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetXIDRangeReply.readGetXIDRangeReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetXIDRange readGetXIDRange(X11Input in) throws IOException {
    GetXIDRange.GetXIDRangeBuilder javaBuilder = GetXIDRange.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
  }

  @Override
  public int getSize() {
    return 4;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetXIDRangeBuilder {
    public int getSize() {
      return 4;
    }

    @java.lang.SuppressWarnings("all")
    GetXIDRangeBuilder() {
    }

    @java.lang.SuppressWarnings("all")
    public GetXIDRange build() {
      return new GetXIDRange();
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetXIDRange.GetXIDRangeBuilder()";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetXIDRange() {
  }

  @java.lang.SuppressWarnings("all")
  public static GetXIDRange.GetXIDRangeBuilder builder() {
    return new GetXIDRange.GetXIDRangeBuilder();
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetXIDRange)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int result = 1;
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetXIDRange()";
  }
}
