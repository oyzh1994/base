package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GetModifierMapping implements TwoWayRequest<GetModifierMappingReply> {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 119;

  public XReplyFunction<GetModifierMappingReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetModifierMappingReply.readGetModifierMappingReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetModifierMapping readGetModifierMapping(X11Input in) throws IOException {
    GetModifierMapping.GetModifierMappingBuilder javaBuilder = GetModifierMapping.builder();
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


  public static class GetModifierMappingBuilder {
    public int getSize() {
      return 4;
    }

    @java.lang.SuppressWarnings("all")
    GetModifierMappingBuilder() {
    }

    @java.lang.SuppressWarnings("all")
    public GetModifierMapping build() {
      return new GetModifierMapping();
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetModifierMapping.GetModifierMappingBuilder()";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetModifierMapping() {
  }

  @java.lang.SuppressWarnings("all")
  public static GetModifierMapping.GetModifierMappingBuilder builder() {
    return new GetModifierMapping.GetModifierMappingBuilder();
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetModifierMapping)) return false;
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
    return "GetModifierMapping()";
  }
}
