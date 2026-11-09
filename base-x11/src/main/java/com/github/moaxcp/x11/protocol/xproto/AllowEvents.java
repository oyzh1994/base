package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class AllowEvents implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 35;
  private final byte mode;
  private final int time;

  public byte getOpCode() {
    return OPCODE;
  }

  public static AllowEvents readAllowEvents(X11Input in) throws IOException {
    AllowEvents.AllowEventsBuilder javaBuilder = AllowEvents.builder();
    byte mode = in.readCard8();
    short length = in.readCard16();
    int time = in.readCard32();
    javaBuilder.mode(mode);
    javaBuilder.time(time);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard8(mode);
    out.writeCard16((short) getLength());
    out.writeCard32(time);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class AllowEventsBuilder {
    @java.lang.SuppressWarnings("all")
    private byte mode;
    @java.lang.SuppressWarnings("all")
    private int time;

    public AllowEvents.AllowEventsBuilder mode(Allow mode) {
      this.mode = (byte) mode.getValue();
      return this;
    }

    public AllowEvents.AllowEventsBuilder mode(byte mode) {
      this.mode = mode;
      return this;
    }

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    AllowEventsBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AllowEvents.AllowEventsBuilder time(final int time) {
      this.time = time;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public AllowEvents build() {
      return new AllowEvents(this.mode, this.time);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "AllowEvents.AllowEventsBuilder(mode=" + this.mode + ", time=" + this.time + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  AllowEvents(final byte mode, final int time) {
    this.mode = mode;
    this.time = time;
  }

  @java.lang.SuppressWarnings("all")
  public static AllowEvents.AllowEventsBuilder builder() {
    return new AllowEvents.AllowEventsBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getMode() {
    return this.mode;
  }

  @java.lang.SuppressWarnings("all")
  public int getTime() {
    return this.time;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof AllowEvents)) return false;
    final AllowEvents other = (AllowEvents) o;
    if (this.getMode() != other.getMode()) return false;
    if (this.getTime() != other.getTime()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getMode();
    result = result * PRIME + this.getTime();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "AllowEvents(mode=" + this.getMode() + ", time=" + this.getTime() + ")";
  }
}
