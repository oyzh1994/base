package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class UngrabServer implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 37;

  public byte getOpCode() {
    return OPCODE;
  }

  public static UngrabServer readUngrabServer(X11Input in) throws IOException {
    UngrabServer.UngrabServerBuilder javaBuilder = UngrabServer.builder();
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


  public static class UngrabServerBuilder {
    public int getSize() {
      return 4;
    }

    @java.lang.SuppressWarnings("all")
    UngrabServerBuilder() {
    }

    @java.lang.SuppressWarnings("all")
    public UngrabServer build() {
      return new UngrabServer();
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "UngrabServer.UngrabServerBuilder()";
    }
  }

  @java.lang.SuppressWarnings("all")
  UngrabServer() {
  }

  @java.lang.SuppressWarnings("all")
  public static UngrabServer.UngrabServerBuilder builder() {
    return new UngrabServer.UngrabServerBuilder();
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof UngrabServer)) return false;
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
    return "UngrabServer()";
  }
}
