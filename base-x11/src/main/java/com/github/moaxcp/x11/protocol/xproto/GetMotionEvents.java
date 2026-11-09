package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GetMotionEvents implements TwoWayRequest<GetMotionEventsReply> {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 39;
  private final int window;
  private final int start;
  private final int stop;

  public XReplyFunction<GetMotionEventsReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetMotionEventsReply.readGetMotionEventsReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetMotionEvents readGetMotionEvents(X11Input in) throws IOException {
    GetMotionEvents.GetMotionEventsBuilder javaBuilder = GetMotionEvents.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int window = in.readCard32();
    int start = in.readCard32();
    int stop = in.readCard32();
    javaBuilder.window(window);
    javaBuilder.start(start);
    javaBuilder.stop(stop);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(start);
    out.writeCard32(stop);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetMotionEventsBuilder {
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private int start;
    @java.lang.SuppressWarnings("all")
    private int stop;

    public int getSize() {
      return 16;
    }

    @java.lang.SuppressWarnings("all")
    GetMotionEventsBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMotionEvents.GetMotionEventsBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMotionEvents.GetMotionEventsBuilder start(final int start) {
      this.start = start;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMotionEvents.GetMotionEventsBuilder stop(final int stop) {
      this.stop = stop;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetMotionEvents build() {
      return new GetMotionEvents(this.window, this.start, this.stop);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetMotionEvents.GetMotionEventsBuilder(window=" + this.window + ", start=" + this.start + ", stop=" + this.stop + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetMotionEvents(final int window, final int start, final int stop) {
    this.window = window;
    this.start = start;
    this.stop = stop;
  }

  @java.lang.SuppressWarnings("all")
  public static GetMotionEvents.GetMotionEventsBuilder builder() {
    return new GetMotionEvents.GetMotionEventsBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.SuppressWarnings("all")
  public int getStart() {
    return this.start;
  }

  @java.lang.SuppressWarnings("all")
  public int getStop() {
    return this.stop;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetMotionEvents)) return false;
    final GetMotionEvents other = (GetMotionEvents) o;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getStart() != other.getStart()) return false;
    if (this.getStop() != other.getStop()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getStart();
    result = result * PRIME + this.getStop();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetMotionEvents(window=" + this.getWindow() + ", start=" + this.getStart() + ", stop=" + this.getStop() + ")";
  }
}
