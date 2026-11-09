package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class NoOperation implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 127;

  public byte getOpCode() {
    return OPCODE;
  }

  public static NoOperation readNoOperation(X11Input in) throws IOException {
    NoOperation.NoOperationBuilder javaBuilder = NoOperation.builder();
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


  public static class NoOperationBuilder {
    public int getSize() {
      return 4;
    }

    @java.lang.SuppressWarnings("all")
    NoOperationBuilder() {
    }

    @java.lang.SuppressWarnings("all")
    public NoOperation build() {
      return new NoOperation();
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "NoOperation.NoOperationBuilder()";
    }
  }

  @java.lang.SuppressWarnings("all")
  NoOperation() {
  }

  @java.lang.SuppressWarnings("all")
  public static NoOperation.NoOperationBuilder builder() {
    return new NoOperation.NoOperationBuilder();
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof NoOperation)) return false;
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
    return "NoOperation()";
  }
}
