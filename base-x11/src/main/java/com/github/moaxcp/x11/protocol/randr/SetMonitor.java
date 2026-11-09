package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class SetMonitor implements OneWayRequest {
  public static final String PLUGIN_NAME = "randr";
  public static final byte OPCODE = 43;
  private final int window;
  private final MonitorInfo monitorinfo;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetMonitor readSetMonitor(X11Input in) throws IOException {
    SetMonitor.SetMonitorBuilder javaBuilder = SetMonitor.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    MonitorInfo monitorinfo = MonitorInfo.readMonitorInfo(in);
    javaBuilder.window(window);
    javaBuilder.monitorinfo(monitorinfo);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    monitorinfo.write(out);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 8 + monitorinfo.getSize();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetMonitorBuilder {
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private MonitorInfo monitorinfo;

    public int getSize() {
      return 8 + monitorinfo.getSize();
    }

    @java.lang.SuppressWarnings("all")
    SetMonitorBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetMonitor.SetMonitorBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetMonitor.SetMonitorBuilder monitorinfo(final MonitorInfo monitorinfo) {
      if (monitorinfo == null) {
        throw new java.lang.NullPointerException("monitorinfo is marked non-null but is null");
      }
      this.monitorinfo = monitorinfo;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetMonitor build() {
      return new SetMonitor(this.window, this.monitorinfo);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetMonitor.SetMonitorBuilder(window=" + this.window + ", monitorinfo=" + this.monitorinfo + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetMonitor(final int window, final MonitorInfo monitorinfo) {
    if (monitorinfo == null) {
      throw new java.lang.NullPointerException("monitorinfo is marked non-null but is null");
    }
    this.window = window;
    this.monitorinfo = monitorinfo;
  }

  @java.lang.SuppressWarnings("all")
  public static SetMonitor.SetMonitorBuilder builder() {
    return new SetMonitor.SetMonitorBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.SuppressWarnings("all")
  public MonitorInfo getMonitorinfo() {
    return this.monitorinfo;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetMonitor)) return false;
    final SetMonitor other = (SetMonitor) o;
    if (this.getWindow() != other.getWindow()) return false;
    final java.lang.Object this$monitorinfo = this.getMonitorinfo();
    final java.lang.Object other$monitorinfo = other.getMonitorinfo();
    if (this$monitorinfo == null ? other$monitorinfo != null : !this$monitorinfo.equals(other$monitorinfo)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getWindow();
    final java.lang.Object $monitorinfo = this.getMonitorinfo();
    result = result * PRIME + ($monitorinfo == null ? 43 : $monitorinfo.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetMonitor(window=" + this.getWindow() + ", monitorinfo=" + this.getMonitorinfo() + ")";
  }
}
