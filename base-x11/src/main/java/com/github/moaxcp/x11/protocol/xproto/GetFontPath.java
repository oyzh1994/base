package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GetFontPath implements TwoWayRequest<GetFontPathReply> {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 52;

  public XReplyFunction<GetFontPathReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetFontPathReply.readGetFontPathReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetFontPath readGetFontPath(X11Input in) throws IOException {
    GetFontPath.GetFontPathBuilder javaBuilder = GetFontPath.builder();
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


  public static class GetFontPathBuilder {
    public int getSize() {
      return 4;
    }

    @java.lang.SuppressWarnings("all")
    GetFontPathBuilder() {
    }

    @java.lang.SuppressWarnings("all")
    public GetFontPath build() {
      return new GetFontPath();
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetFontPath.GetFontPathBuilder()";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetFontPath() {
  }

  @java.lang.SuppressWarnings("all")
  public static GetFontPath.GetFontPathBuilder builder() {
    return new GetFontPath.GetFontPathBuilder();
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetFontPath)) return false;
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
    return "GetFontPath()";
  }
}
