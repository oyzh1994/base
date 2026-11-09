package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GetCursorImageAndName implements TwoWayRequest<GetCursorImageAndNameReply> {
  public static final String PLUGIN_NAME = "xfixes";
  public static final byte OPCODE = 25;

  public XReplyFunction<GetCursorImageAndNameReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetCursorImageAndNameReply.readGetCursorImageAndNameReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetCursorImageAndName readGetCursorImageAndName(X11Input in) throws IOException {
    GetCursorImageAndName.GetCursorImageAndNameBuilder javaBuilder = GetCursorImageAndName.builder();
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


  public static class GetCursorImageAndNameBuilder {
    public int getSize() {
      return 4;
    }

    @java.lang.SuppressWarnings("all")
    GetCursorImageAndNameBuilder() {
    }

    @java.lang.SuppressWarnings("all")
    public GetCursorImageAndName build() {
      return new GetCursorImageAndName();
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetCursorImageAndName.GetCursorImageAndNameBuilder()";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetCursorImageAndName() {
  }

  @java.lang.SuppressWarnings("all")
  public static GetCursorImageAndName.GetCursorImageAndNameBuilder builder() {
    return new GetCursorImageAndName.GetCursorImageAndNameBuilder();
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetCursorImageAndName)) return false;
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
    return "GetCursorImageAndName()";
  }
}
