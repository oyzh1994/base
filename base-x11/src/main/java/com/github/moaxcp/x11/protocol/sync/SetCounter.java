package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class SetCounter implements OneWayRequest {
  public static final String PLUGIN_NAME = "sync";
  public static final byte OPCODE = 3;
  private final int counter;
  private final Int64 value;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetCounter readSetCounter(X11Input in) throws IOException {
    SetCounter.SetCounterBuilder javaBuilder = SetCounter.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int counter = in.readCard32();
    Int64 value = Int64.readInt64(in);
    javaBuilder.counter(counter);
    javaBuilder.value(value);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(counter);
    value.write(out);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetCounterBuilder {
    @java.lang.SuppressWarnings("all")
    private int counter;
    @java.lang.SuppressWarnings("all")
    private Int64 value;

    public int getSize() {
      return 16;
    }

    @java.lang.SuppressWarnings("all")
    SetCounterBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetCounter.SetCounterBuilder counter(final int counter) {
      this.counter = counter;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetCounter.SetCounterBuilder value(final Int64 value) {
      if (value == null) {
        throw new java.lang.NullPointerException("value is marked non-null but is null");
      }
      this.value = value;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetCounter build() {
      return new SetCounter(this.counter, this.value);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetCounter.SetCounterBuilder(counter=" + this.counter + ", value=" + this.value + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetCounter(final int counter, final Int64 value) {
    if (value == null) {
      throw new java.lang.NullPointerException("value is marked non-null but is null");
    }
    this.counter = counter;
    this.value = value;
  }

  @java.lang.SuppressWarnings("all")
  public static SetCounter.SetCounterBuilder builder() {
    return new SetCounter.SetCounterBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getCounter() {
    return this.counter;
  }

  @java.lang.SuppressWarnings("all")
  public Int64 getValue() {
    return this.value;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetCounter)) return false;
    final SetCounter other = (SetCounter) o;
    if (this.getCounter() != other.getCounter()) return false;
    final java.lang.Object this$value = this.getValue();
    final java.lang.Object other$value = other.getValue();
    if (this$value == null ? other$value != null : !this$value.equals(other$value)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getCounter();
    final java.lang.Object $value = this.getValue();
    result = result * PRIME + ($value == null ? 43 : $value.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetCounter(counter=" + this.getCounter() + ", value=" + this.getValue() + ")";
  }
}
