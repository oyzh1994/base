package com.github.moaxcp.x11.protocol.composite;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class RedirectWindow implements OneWayRequest {
  public static final String PLUGIN_NAME = "composite";
  public static final byte OPCODE = 1;
  private final int window;
  private final byte update;

  public byte getOpCode() {
    return OPCODE;
  }

  public static RedirectWindow readRedirectWindow(X11Input in) throws IOException {
    RedirectWindow.RedirectWindowBuilder javaBuilder = RedirectWindow.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    byte update = in.readCard8();
    byte[] pad5 = in.readPad(3);
    javaBuilder.window(window);
    javaBuilder.update(update);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard8(update);
    out.writePad(3);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class RedirectWindowBuilder {
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private byte update;

    public RedirectWindow.RedirectWindowBuilder update(Redirect update) {
      this.update = (byte) update.getValue();
      return this;
    }

    public RedirectWindow.RedirectWindowBuilder update(byte update) {
      this.update = update;
      return this;
    }

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    RedirectWindowBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RedirectWindow.RedirectWindowBuilder window(final int window) {
      this.window = window;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public RedirectWindow build() {
      return new RedirectWindow(this.window, this.update);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "RedirectWindow.RedirectWindowBuilder(window=" + this.window + ", update=" + this.update + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  RedirectWindow(final int window, final byte update) {
    this.window = window;
    this.update = update;
  }

  @java.lang.SuppressWarnings("all")
  public static RedirectWindow.RedirectWindowBuilder builder() {
    return new RedirectWindow.RedirectWindowBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.SuppressWarnings("all")
  public byte getUpdate() {
    return this.update;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof RedirectWindow)) return false;
    final RedirectWindow other = (RedirectWindow) o;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getUpdate() != other.getUpdate()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getUpdate();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "RedirectWindow(window=" + this.getWindow() + ", update=" + this.getUpdate() + ")";
  }
}
