package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class ShowCursor implements OneWayRequest {
  public static final String PLUGIN_NAME = "xfixes";
  public static final byte OPCODE = 30;
  private final int window;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ShowCursor readShowCursor(X11Input in) throws IOException {
    ShowCursor.ShowCursorBuilder javaBuilder = ShowCursor.builder();
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


  public static class ShowCursorBuilder {
    @java.lang.SuppressWarnings("all")
    private int window;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    ShowCursorBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ShowCursor.ShowCursorBuilder window(final int window) {
      this.window = window;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ShowCursor build() {
      return new ShowCursor(this.window);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ShowCursor.ShowCursorBuilder(window=" + this.window + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ShowCursor(final int window) {
    this.window = window;
  }

  @java.lang.SuppressWarnings("all")
  public static ShowCursor.ShowCursorBuilder builder() {
    return new ShowCursor.ShowCursorBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ShowCursor)) return false;
    final ShowCursor other = (ShowCursor) o;
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
    return "ShowCursor(window=" + this.getWindow() + ")";
  }
}
