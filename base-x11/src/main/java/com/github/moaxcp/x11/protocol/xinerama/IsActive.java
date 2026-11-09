package com.github.moaxcp.x11.protocol.xinerama;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class IsActive implements TwoWayRequest<IsActiveReply> {
  public static final String PLUGIN_NAME = "xinerama";
  public static final byte OPCODE = 4;

  public XReplyFunction<IsActiveReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> IsActiveReply.readIsActiveReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static IsActive readIsActive(X11Input in) throws IOException {
    IsActive.IsActiveBuilder javaBuilder = IsActive.builder();
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


  public static class IsActiveBuilder {
    public int getSize() {
      return 4;
    }

    @java.lang.SuppressWarnings("all")
    IsActiveBuilder() {
    }

    @java.lang.SuppressWarnings("all")
    public IsActive build() {
      return new IsActive();
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "IsActive.IsActiveBuilder()";
    }
  }

  @java.lang.SuppressWarnings("all")
  IsActive() {
  }

  @java.lang.SuppressWarnings("all")
  public static IsActive.IsActiveBuilder builder() {
    return new IsActive.IsActiveBuilder();
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof IsActive)) return false;
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
    return "IsActive()";
  }
}
