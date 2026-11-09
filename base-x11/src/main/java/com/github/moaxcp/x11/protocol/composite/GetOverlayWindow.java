package com.github.moaxcp.x11.protocol.composite;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GetOverlayWindow implements TwoWayRequest<GetOverlayWindowReply> {
  public static final String PLUGIN_NAME = "composite";
  public static final byte OPCODE = 7;
  private final int window;

  public XReplyFunction<GetOverlayWindowReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetOverlayWindowReply.readGetOverlayWindowReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetOverlayWindow readGetOverlayWindow(X11Input in) throws IOException {
    GetOverlayWindow.GetOverlayWindowBuilder javaBuilder = GetOverlayWindow.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    javaBuilder.window(window);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
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


  public static class GetOverlayWindowBuilder {
    @java.lang.SuppressWarnings("all")
    private int window;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    GetOverlayWindowBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetOverlayWindow.GetOverlayWindowBuilder window(final int window) {
      this.window = window;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetOverlayWindow build() {
      return new GetOverlayWindow(this.window);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetOverlayWindow.GetOverlayWindowBuilder(window=" + this.window + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetOverlayWindow(final int window) {
    this.window = window;
  }

  @java.lang.SuppressWarnings("all")
  public static GetOverlayWindow.GetOverlayWindowBuilder builder() {
    return new GetOverlayWindow.GetOverlayWindowBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetOverlayWindow)) return false;
    final GetOverlayWindow other = (GetOverlayWindow) o;
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
    return "GetOverlayWindow(window=" + this.getWindow() + ")";
  }
}
