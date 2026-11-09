package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GetGeometry implements TwoWayRequest<GetGeometryReply> {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 14;
  private final int drawable;

  public XReplyFunction<GetGeometryReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetGeometryReply.readGetGeometryReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetGeometry readGetGeometry(X11Input in) throws IOException {
    GetGeometry.GetGeometryBuilder javaBuilder = GetGeometry.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int drawable = in.readCard32();
    javaBuilder.drawable(drawable);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetGeometryBuilder {
    @java.lang.SuppressWarnings("all")
    private int drawable;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    GetGeometryBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetGeometry.GetGeometryBuilder drawable(final int drawable) {
      this.drawable = drawable;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetGeometry build() {
      return new GetGeometry(this.drawable);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetGeometry.GetGeometryBuilder(drawable=" + this.drawable + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetGeometry(final int drawable) {
    this.drawable = drawable;
  }

  @java.lang.SuppressWarnings("all")
  public static GetGeometry.GetGeometryBuilder builder() {
    return new GetGeometry.GetGeometryBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getDrawable() {
    return this.drawable;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetGeometry)) return false;
    final GetGeometry other = (GetGeometry) o;
    if (this.getDrawable() != other.getDrawable()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDrawable();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetGeometry(drawable=" + this.getDrawable() + ")";
  }
}
