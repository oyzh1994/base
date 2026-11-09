package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GetPointerMapping implements TwoWayRequest<GetPointerMappingReply> {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 117;

  public XReplyFunction<GetPointerMappingReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetPointerMappingReply.readGetPointerMappingReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetPointerMapping readGetPointerMapping(X11Input in) throws IOException {
    GetPointerMapping.GetPointerMappingBuilder javaBuilder = GetPointerMapping.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
  }

  @Override
  public int getSize() {
    return 4;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetPointerMappingBuilder {
    public int getSize() {
      return 4;
    }

    @java.lang.SuppressWarnings("all")
    GetPointerMappingBuilder() {
    }

    @java.lang.SuppressWarnings("all")
    public GetPointerMapping build() {
      return new GetPointerMapping();
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetPointerMapping.GetPointerMappingBuilder()";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetPointerMapping() {
  }

  @java.lang.SuppressWarnings("all")
  public static GetPointerMapping.GetPointerMappingBuilder builder() {
    return new GetPointerMapping.GetPointerMappingBuilder();
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetPointerMapping)) return false;
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
    return "GetPointerMapping()";
  }
}
