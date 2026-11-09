package com.github.moaxcp.x11.protocol.xinerama;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GetScreenSize implements TwoWayRequest<GetScreenSizeReply> {
  public static final String PLUGIN_NAME = "xinerama";
  public static final byte OPCODE = 3;
  private final int window;
  private final int screen;

  public XReplyFunction<GetScreenSizeReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetScreenSizeReply.readGetScreenSizeReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetScreenSize readGetScreenSize(X11Input in) throws IOException {
    GetScreenSize.GetScreenSizeBuilder javaBuilder = GetScreenSize.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    int screen = in.readCard32();
    javaBuilder.window(window);
    javaBuilder.screen(screen);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(screen);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetScreenSizeBuilder {
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private int screen;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    GetScreenSizeBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenSize.GetScreenSizeBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenSize.GetScreenSizeBuilder screen(final int screen) {
      this.screen = screen;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetScreenSize build() {
      return new GetScreenSize(this.window, this.screen);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetScreenSize.GetScreenSizeBuilder(window=" + this.window + ", screen=" + this.screen + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetScreenSize(final int window, final int screen) {
    this.window = window;
    this.screen = screen;
  }

  @java.lang.SuppressWarnings("all")
  public static GetScreenSize.GetScreenSizeBuilder builder() {
    return new GetScreenSize.GetScreenSizeBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.SuppressWarnings("all")
  public int getScreen() {
    return this.screen;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetScreenSize)) return false;
    final GetScreenSize other = (GetScreenSize) o;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getScreen() != other.getScreen()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getScreen();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetScreenSize(window=" + this.getWindow() + ", screen=" + this.getScreen() + ")";
  }
}
