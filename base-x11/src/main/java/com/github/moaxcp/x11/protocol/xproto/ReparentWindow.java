package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class ReparentWindow implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 7;
  private final int window;
  private final int parent;
  private final short x;
  private final short y;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ReparentWindow readReparentWindow(X11Input in) throws IOException {
    ReparentWindow.ReparentWindowBuilder javaBuilder = ReparentWindow.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int window = in.readCard32();
    int parent = in.readCard32();
    short x = in.readInt16();
    short y = in.readInt16();
    javaBuilder.window(window);
    javaBuilder.parent(parent);
    javaBuilder.x(x);
    javaBuilder.y(y);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(parent);
    out.writeInt16(x);
    out.writeInt16(y);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ReparentWindowBuilder {
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private int parent;
    @java.lang.SuppressWarnings("all")
    private short x;
    @java.lang.SuppressWarnings("all")
    private short y;

    public int getSize() {
      return 16;
    }

    @java.lang.SuppressWarnings("all")
    ReparentWindowBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ReparentWindow.ReparentWindowBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ReparentWindow.ReparentWindowBuilder parent(final int parent) {
      this.parent = parent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ReparentWindow.ReparentWindowBuilder x(final short x) {
      this.x = x;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ReparentWindow.ReparentWindowBuilder y(final short y) {
      this.y = y;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ReparentWindow build() {
      return new ReparentWindow(this.window, this.parent, this.x, this.y);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ReparentWindow.ReparentWindowBuilder(window=" + this.window + ", parent=" + this.parent + ", x=" + this.x + ", y=" + this.y + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ReparentWindow(final int window, final int parent, final short x, final short y) {
    this.window = window;
    this.parent = parent;
    this.x = x;
    this.y = y;
  }

  @java.lang.SuppressWarnings("all")
  public static ReparentWindow.ReparentWindowBuilder builder() {
    return new ReparentWindow.ReparentWindowBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.SuppressWarnings("all")
  public int getParent() {
    return this.parent;
  }

  @java.lang.SuppressWarnings("all")
  public short getX() {
    return this.x;
  }

  @java.lang.SuppressWarnings("all")
  public short getY() {
    return this.y;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ReparentWindow)) return false;
    final ReparentWindow other = (ReparentWindow) o;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getParent() != other.getParent()) return false;
    if (this.getX() != other.getX()) return false;
    if (this.getY() != other.getY()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getParent();
    result = result * PRIME + this.getX();
    result = result * PRIME + this.getY();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ReparentWindow(window=" + this.getWindow() + ", parent=" + this.getParent() + ", x=" + this.getX() + ", y=" + this.getY() + ")";
  }
}
