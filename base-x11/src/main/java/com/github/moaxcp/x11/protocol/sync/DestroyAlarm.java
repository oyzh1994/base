package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class DestroyAlarm implements OneWayRequest {
  public static final String PLUGIN_NAME = "sync";
  public static final byte OPCODE = 11;
  private final int alarm;

  public byte getOpCode() {
    return OPCODE;
  }

  public static DestroyAlarm readDestroyAlarm(X11Input in) throws IOException {
    DestroyAlarm.DestroyAlarmBuilder javaBuilder = DestroyAlarm.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int alarm = in.readCard32();
    javaBuilder.alarm(alarm);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(alarm);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class DestroyAlarmBuilder {
    @java.lang.SuppressWarnings("all")
    private int alarm;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    DestroyAlarmBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public DestroyAlarm.DestroyAlarmBuilder alarm(final int alarm) {
      this.alarm = alarm;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public DestroyAlarm build() {
      return new DestroyAlarm(this.alarm);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "DestroyAlarm.DestroyAlarmBuilder(alarm=" + this.alarm + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  DestroyAlarm(final int alarm) {
    this.alarm = alarm;
  }

  @java.lang.SuppressWarnings("all")
  public static DestroyAlarm.DestroyAlarmBuilder builder() {
    return new DestroyAlarm.DestroyAlarmBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getAlarm() {
    return this.alarm;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof DestroyAlarm)) return false;
    final DestroyAlarm other = (DestroyAlarm) o;
    if (this.getAlarm() != other.getAlarm()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getAlarm();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "DestroyAlarm(alarm=" + this.getAlarm() + ")";
  }
}
