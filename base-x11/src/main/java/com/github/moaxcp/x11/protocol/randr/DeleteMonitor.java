package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class DeleteMonitor implements OneWayRequest {
  public static final String PLUGIN_NAME = "randr";
  public static final byte OPCODE = 44;
  private final int window;
  private final int name;

  public byte getOpCode() {
    return OPCODE;
  }

  public static DeleteMonitor readDeleteMonitor(X11Input in) throws IOException {
    DeleteMonitor.DeleteMonitorBuilder javaBuilder = DeleteMonitor.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    int name = in.readCard32();
    javaBuilder.window(window);
    javaBuilder.name(name);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(name);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class DeleteMonitorBuilder {
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private int name;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    DeleteMonitorBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public DeleteMonitor.DeleteMonitorBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public DeleteMonitor.DeleteMonitorBuilder name(final int name) {
      this.name = name;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public DeleteMonitor build() {
      return new DeleteMonitor(this.window, this.name);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "DeleteMonitor.DeleteMonitorBuilder(window=" + this.window + ", name=" + this.name + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  DeleteMonitor(final int window, final int name) {
    this.window = window;
    this.name = name;
  }

  @java.lang.SuppressWarnings("all")
  public static DeleteMonitor.DeleteMonitorBuilder builder() {
    return new DeleteMonitor.DeleteMonitorBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.SuppressWarnings("all")
  public int getName() {
    return this.name;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof DeleteMonitor)) return false;
    final DeleteMonitor other = (DeleteMonitor) o;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getName() != other.getName()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getName();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "DeleteMonitor(window=" + this.getWindow() + ", name=" + this.getName() + ")";
  }
}
