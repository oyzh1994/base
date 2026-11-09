package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GetScreenSaver implements TwoWayRequest<GetScreenSaverReply> {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 108;

  public XReplyFunction<GetScreenSaverReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetScreenSaverReply.readGetScreenSaverReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetScreenSaver readGetScreenSaver(X11Input in) throws IOException {
    GetScreenSaver.GetScreenSaverBuilder javaBuilder = GetScreenSaver.builder();
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


  public static class GetScreenSaverBuilder {
    public int getSize() {
      return 4;
    }

    @java.lang.SuppressWarnings("all")
    GetScreenSaverBuilder() {
    }

    @java.lang.SuppressWarnings("all")
    public GetScreenSaver build() {
      return new GetScreenSaver();
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetScreenSaver.GetScreenSaverBuilder()";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetScreenSaver() {
  }

  @java.lang.SuppressWarnings("all")
  public static GetScreenSaver.GetScreenSaverBuilder builder() {
    return new GetScreenSaver.GetScreenSaverBuilder();
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetScreenSaver)) return false;
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
    return "GetScreenSaver()";
  }
}
