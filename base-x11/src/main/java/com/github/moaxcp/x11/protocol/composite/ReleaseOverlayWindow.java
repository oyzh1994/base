package com.github.moaxcp.x11.protocol.composite;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class ReleaseOverlayWindow implements OneWayRequest {
  public static final String PLUGIN_NAME = "composite";
  public static final byte OPCODE = 8;
  private final int window;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ReleaseOverlayWindow readReleaseOverlayWindow(X11Input in) throws IOException {
    ReleaseOverlayWindow.ReleaseOverlayWindowBuilder javaBuilder = ReleaseOverlayWindow.builder();
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


  public static class ReleaseOverlayWindowBuilder {
    @java.lang.SuppressWarnings("all")
    private int window;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    ReleaseOverlayWindowBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ReleaseOverlayWindow.ReleaseOverlayWindowBuilder window(final int window) {
      this.window = window;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ReleaseOverlayWindow build() {
      return new ReleaseOverlayWindow(this.window);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ReleaseOverlayWindow.ReleaseOverlayWindowBuilder(window=" + this.window + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ReleaseOverlayWindow(final int window) {
    this.window = window;
  }

  @java.lang.SuppressWarnings("all")
  public static ReleaseOverlayWindow.ReleaseOverlayWindowBuilder builder() {
    return new ReleaseOverlayWindow.ReleaseOverlayWindowBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ReleaseOverlayWindow)) return false;
    final ReleaseOverlayWindow other = (ReleaseOverlayWindow) o;
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
    return "ReleaseOverlayWindow(window=" + this.getWindow() + ")";
  }
}
