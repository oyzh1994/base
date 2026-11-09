package com.github.moaxcp.x11.protocol.dpms;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class Capable implements TwoWayRequest<CapableReply> {
  public static final String PLUGIN_NAME = "dpms";
  public static final byte OPCODE = 1;

  public XReplyFunction<CapableReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> CapableReply.readCapableReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static Capable readCapable(X11Input in) throws IOException {
    Capable.CapableBuilder javaBuilder = Capable.builder();
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


  public static class CapableBuilder {
    public int getSize() {
      return 4;
    }

    @java.lang.SuppressWarnings("all")
    CapableBuilder() {
    }

    @java.lang.SuppressWarnings("all")
    public Capable build() {
      return new Capable();
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Capable.CapableBuilder()";
    }
  }

  @java.lang.SuppressWarnings("all")
  Capable() {
  }

  @java.lang.SuppressWarnings("all")
  public static Capable.CapableBuilder builder() {
    return new Capable.CapableBuilder();
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Capable)) return false;
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
    return "Capable()";
  }
}
