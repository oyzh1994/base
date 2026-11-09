package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class DestroyCounter implements OneWayRequest {
  public static final String PLUGIN_NAME = "sync";
  public static final byte OPCODE = 6;
  private final int counter;

  public byte getOpCode() {
    return OPCODE;
  }

  public static DestroyCounter readDestroyCounter(X11Input in) throws IOException {
    DestroyCounter.DestroyCounterBuilder javaBuilder = DestroyCounter.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int counter = in.readCard32();
    javaBuilder.counter(counter);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(counter);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class DestroyCounterBuilder {
    @java.lang.SuppressWarnings("all")
    private int counter;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    DestroyCounterBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public DestroyCounter.DestroyCounterBuilder counter(final int counter) {
      this.counter = counter;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public DestroyCounter build() {
      return new DestroyCounter(this.counter);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "DestroyCounter.DestroyCounterBuilder(counter=" + this.counter + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  DestroyCounter(final int counter) {
    this.counter = counter;
  }

  @java.lang.SuppressWarnings("all")
  public static DestroyCounter.DestroyCounterBuilder builder() {
    return new DestroyCounter.DestroyCounterBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getCounter() {
    return this.counter;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof DestroyCounter)) return false;
    final DestroyCounter other = (DestroyCounter) o;
    if (this.getCounter() != other.getCounter()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getCounter();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "DestroyCounter(counter=" + this.getCounter() + ")";
  }
}
