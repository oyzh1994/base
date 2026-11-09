package com.github.moaxcp.x11.protocol.screensaver;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class Suspend implements OneWayRequest {
  public static final String PLUGIN_NAME = "screensaver";
  public static final byte OPCODE = 5;
  private final int suspend;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Suspend readSuspend(X11Input in) throws IOException {
    Suspend.SuspendBuilder javaBuilder = Suspend.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int suspend = in.readCard32();
    javaBuilder.suspend(suspend);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(suspend);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SuspendBuilder {
    @java.lang.SuppressWarnings("all")
    private int suspend;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    SuspendBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Suspend.SuspendBuilder suspend(final int suspend) {
      this.suspend = suspend;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Suspend build() {
      return new Suspend(this.suspend);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Suspend.SuspendBuilder(suspend=" + this.suspend + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Suspend(final int suspend) {
    this.suspend = suspend;
  }

  @java.lang.SuppressWarnings("all")
  public static Suspend.SuspendBuilder builder() {
    return new Suspend.SuspendBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getSuspend() {
    return this.suspend;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Suspend)) return false;
    final Suspend other = (Suspend) o;
    if (this.getSuspend() != other.getSuspend()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSuspend();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Suspend(suspend=" + this.getSuspend() + ")";
  }
}
