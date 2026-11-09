package com.github.moaxcp.x11.protocol.dpms;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class Info implements TwoWayRequest<InfoReply> {
  public static final String PLUGIN_NAME = "dpms";
  public static final byte OPCODE = 7;

  public XReplyFunction<InfoReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> InfoReply.readInfoReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static Info readInfo(X11Input in) throws IOException {
    Info.InfoBuilder javaBuilder = Info.builder();
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


  public static class InfoBuilder {
    public int getSize() {
      return 4;
    }

    @java.lang.SuppressWarnings("all")
    InfoBuilder() {
    }

    @java.lang.SuppressWarnings("all")
    public Info build() {
      return new Info();
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Info.InfoBuilder()";
    }
  }

  @java.lang.SuppressWarnings("all")
  Info() {
  }

  @java.lang.SuppressWarnings("all")
  public static Info.InfoBuilder builder() {
    return new Info.InfoBuilder();
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Info)) return false;
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
    return "Info()";
  }
}
