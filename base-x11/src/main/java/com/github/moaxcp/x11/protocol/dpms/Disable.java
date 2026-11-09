package com.github.moaxcp.x11.protocol.dpms;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class Disable implements OneWayRequest {
  public static final String PLUGIN_NAME = "dpms";
  public static final byte OPCODE = 5;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Disable readDisable(X11Input in) throws IOException {
    Disable.DisableBuilder javaBuilder = Disable.builder();
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


  public static class DisableBuilder {
    public int getSize() {
      return 4;
    }

    @java.lang.SuppressWarnings("all")
    DisableBuilder() {
    }

    @java.lang.SuppressWarnings("all")
    public Disable build() {
      return new Disable();
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Disable.DisableBuilder()";
    }
  }

  @java.lang.SuppressWarnings("all")
  Disable() {
  }

  @java.lang.SuppressWarnings("all")
  public static Disable.DisableBuilder builder() {
    return new Disable.DisableBuilder();
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Disable)) return false;
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
    return "Disable()";
  }
}
