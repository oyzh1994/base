package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GetWindowAttributes implements TwoWayRequest<GetWindowAttributesReply> {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 3;
  private final int window;

  public XReplyFunction<GetWindowAttributesReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetWindowAttributesReply.readGetWindowAttributesReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetWindowAttributes readGetWindowAttributes(X11Input in) throws IOException {
    GetWindowAttributes.GetWindowAttributesBuilder javaBuilder = GetWindowAttributes.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int window = in.readCard32();
    javaBuilder.window(window);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetWindowAttributesBuilder {
    @java.lang.SuppressWarnings("all")
    private int window;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    GetWindowAttributesBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetWindowAttributes.GetWindowAttributesBuilder window(final int window) {
      this.window = window;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetWindowAttributes build() {
      return new GetWindowAttributes(this.window);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetWindowAttributes.GetWindowAttributesBuilder(window=" + this.window + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetWindowAttributes(final int window) {
    this.window = window;
  }

  @java.lang.SuppressWarnings("all")
  public static GetWindowAttributes.GetWindowAttributesBuilder builder() {
    return new GetWindowAttributes.GetWindowAttributesBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetWindowAttributes)) return false;
    final GetWindowAttributes other = (GetWindowAttributes) o;
    if (this.getWindow() != other.getWindow()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getWindow();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetWindowAttributes(window=" + this.getWindow() + ")";
  }
}
