package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class CirculateWindow implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 13;
  private final byte direction;
  private final int window;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CirculateWindow readCirculateWindow(X11Input in) throws IOException {
    CirculateWindow.CirculateWindowBuilder javaBuilder = CirculateWindow.builder();
    byte direction = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    javaBuilder.direction(direction);
    javaBuilder.window(window);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard8(direction);
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


  public static class CirculateWindowBuilder {
    @java.lang.SuppressWarnings("all")
    private byte direction;
    @java.lang.SuppressWarnings("all")
    private int window;

    public CirculateWindow.CirculateWindowBuilder direction(Circulate direction) {
      this.direction = (byte) direction.getValue();
      return this;
    }

    public CirculateWindow.CirculateWindowBuilder direction(byte direction) {
      this.direction = direction;
      return this;
    }

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    CirculateWindowBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CirculateWindow.CirculateWindowBuilder window(final int window) {
      this.window = window;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CirculateWindow build() {
      return new CirculateWindow(this.direction, this.window);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CirculateWindow.CirculateWindowBuilder(direction=" + this.direction + ", window=" + this.window + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CirculateWindow(final byte direction, final int window) {
    this.direction = direction;
    this.window = window;
  }

  @java.lang.SuppressWarnings("all")
  public static CirculateWindow.CirculateWindowBuilder builder() {
    return new CirculateWindow.CirculateWindowBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getDirection() {
    return this.direction;
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CirculateWindow)) return false;
    final CirculateWindow other = (CirculateWindow) o;
    if (this.getDirection() != other.getDirection()) return false;
    if (this.getWindow() != other.getWindow()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDirection();
    result = result * PRIME + this.getWindow();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CirculateWindow(direction=" + this.getDirection() + ", window=" + this.getWindow() + ")";
  }
}
