package com.github.moaxcp.x11.protocol.xinerama;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GetScreenCount implements TwoWayRequest<GetScreenCountReply> {
  public static final String PLUGIN_NAME = "xinerama";
  public static final byte OPCODE = 2;
  private final int window;

  public XReplyFunction<GetScreenCountReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetScreenCountReply.readGetScreenCountReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetScreenCount readGetScreenCount(X11Input in) throws IOException {
    GetScreenCount.GetScreenCountBuilder javaBuilder = GetScreenCount.builder();
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


  public static class GetScreenCountBuilder {
    @java.lang.SuppressWarnings("all")
    private int window;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    GetScreenCountBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenCount.GetScreenCountBuilder window(final int window) {
      this.window = window;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetScreenCount build() {
      return new GetScreenCount(this.window);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetScreenCount.GetScreenCountBuilder(window=" + this.window + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetScreenCount(final int window) {
    this.window = window;
  }

  @java.lang.SuppressWarnings("all")
  public static GetScreenCount.GetScreenCountBuilder builder() {
    return new GetScreenCount.GetScreenCountBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetScreenCount)) return false;
    final GetScreenCount other = (GetScreenCount) o;
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
    return "GetScreenCount(window=" + this.getWindow() + ")";
  }
}
