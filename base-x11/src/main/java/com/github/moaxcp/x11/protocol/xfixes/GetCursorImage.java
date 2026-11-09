package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GetCursorImage implements TwoWayRequest<GetCursorImageReply> {
  public static final String PLUGIN_NAME = "xfixes";
  public static final byte OPCODE = 4;

  public XReplyFunction<GetCursorImageReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetCursorImageReply.readGetCursorImageReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetCursorImage readGetCursorImage(X11Input in) throws IOException {
    GetCursorImage.GetCursorImageBuilder javaBuilder = GetCursorImage.builder();
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


  public static class GetCursorImageBuilder {
    public int getSize() {
      return 4;
    }

    @java.lang.SuppressWarnings("all")
    GetCursorImageBuilder() {
    }

    @java.lang.SuppressWarnings("all")
    public GetCursorImage build() {
      return new GetCursorImage();
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetCursorImage.GetCursorImageBuilder()";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetCursorImage() {
  }

  @java.lang.SuppressWarnings("all")
  public static GetCursorImage.GetCursorImageBuilder builder() {
    return new GetCursorImage.GetCursorImageBuilder();
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetCursorImage)) return false;
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
    return "GetCursorImage()";
  }
}
