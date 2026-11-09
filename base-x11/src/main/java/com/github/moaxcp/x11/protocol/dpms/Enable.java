package com.github.moaxcp.x11.protocol.dpms;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class Enable implements OneWayRequest {
  public static final String PLUGIN_NAME = "dpms";
  public static final byte OPCODE = 4;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Enable readEnable(X11Input in) throws IOException {
    Enable.EnableBuilder javaBuilder = Enable.builder();
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


  public static class EnableBuilder {
    public int getSize() {
      return 4;
    }

    @java.lang.SuppressWarnings("all")
    EnableBuilder() {
    }

    @java.lang.SuppressWarnings("all")
    public Enable build() {
      return new Enable();
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Enable.EnableBuilder()";
    }
  }

  @java.lang.SuppressWarnings("all")
  Enable() {
  }

  @java.lang.SuppressWarnings("all")
  public static Enable.EnableBuilder builder() {
    return new Enable.EnableBuilder();
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Enable)) return false;
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
    return "Enable()";
  }
}
