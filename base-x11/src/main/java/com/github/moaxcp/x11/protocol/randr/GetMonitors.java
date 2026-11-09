package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GetMonitors implements TwoWayRequest<GetMonitorsReply> {
  public static final String PLUGIN_NAME = "randr";
  public static final byte OPCODE = 42;
  private final int window;
  private final boolean getActive;

  public XReplyFunction<GetMonitorsReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetMonitorsReply.readGetMonitorsReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetMonitors readGetMonitors(X11Input in) throws IOException {
    GetMonitors.GetMonitorsBuilder javaBuilder = GetMonitors.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    boolean getActive = in.readBool();
    javaBuilder.window(window);
    javaBuilder.getActive(getActive);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeBool(getActive);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 9;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetMonitorsBuilder {
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private boolean getActive;

    public int getSize() {
      return 9;
    }

    @java.lang.SuppressWarnings("all")
    GetMonitorsBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMonitors.GetMonitorsBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMonitors.GetMonitorsBuilder getActive(final boolean getActive) {
      this.getActive = getActive;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetMonitors build() {
      return new GetMonitors(this.window, this.getActive);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetMonitors.GetMonitorsBuilder(window=" + this.window + ", getActive=" + this.getActive + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetMonitors(final int window, final boolean getActive) {
    this.window = window;
    this.getActive = getActive;
  }

  @java.lang.SuppressWarnings("all")
  public static GetMonitors.GetMonitorsBuilder builder() {
    return new GetMonitors.GetMonitorsBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isGetActive() {
    return this.getActive;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetMonitors)) return false;
    final GetMonitors other = (GetMonitors) o;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.isGetActive() != other.isGetActive()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getWindow();
    result = result * PRIME + (this.isGetActive() ? 79 : 97);
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetMonitors(window=" + this.getWindow() + ", getActive=" + this.isGetActive() + ")";
  }
}
