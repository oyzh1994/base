package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class ChangeCounter implements OneWayRequest {
  public static final String PLUGIN_NAME = "sync";
  public static final byte OPCODE = 4;
  private final int counter;
  private final Int64 amount;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ChangeCounter readChangeCounter(X11Input in) throws IOException {
    ChangeCounter.ChangeCounterBuilder javaBuilder = ChangeCounter.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int counter = in.readCard32();
    Int64 amount = Int64.readInt64(in);
    javaBuilder.counter(counter);
    javaBuilder.amount(amount);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(counter);
    amount.write(out);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ChangeCounterBuilder {
    @java.lang.SuppressWarnings("all")
    private int counter;
    @java.lang.SuppressWarnings("all")
    private Int64 amount;

    public int getSize() {
      return 16;
    }

    @java.lang.SuppressWarnings("all")
    ChangeCounterBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangeCounter.ChangeCounterBuilder counter(final int counter) {
      this.counter = counter;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangeCounter.ChangeCounterBuilder amount(final Int64 amount) {
      if (amount == null) {
        throw new java.lang.NullPointerException("amount is marked non-null but is null");
      }
      this.amount = amount;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ChangeCounter build() {
      return new ChangeCounter(this.counter, this.amount);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ChangeCounter.ChangeCounterBuilder(counter=" + this.counter + ", amount=" + this.amount + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ChangeCounter(final int counter, final Int64 amount) {
    if (amount == null) {
      throw new java.lang.NullPointerException("amount is marked non-null but is null");
    }
    this.counter = counter;
    this.amount = amount;
  }

  @java.lang.SuppressWarnings("all")
  public static ChangeCounter.ChangeCounterBuilder builder() {
    return new ChangeCounter.ChangeCounterBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getCounter() {
    return this.counter;
  }

  @java.lang.SuppressWarnings("all")
  public Int64 getAmount() {
    return this.amount;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ChangeCounter)) return false;
    final ChangeCounter other = (ChangeCounter) o;
    if (this.getCounter() != other.getCounter()) return false;
    final java.lang.Object this$amount = this.getAmount();
    final java.lang.Object other$amount = other.getAmount();
    if (this$amount == null ? other$amount != null : !this$amount.equals(other$amount)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getCounter();
    final java.lang.Object $amount = this.getAmount();
    result = result * PRIME + ($amount == null ? 43 : $amount.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ChangeCounter(counter=" + this.getCounter() + ", amount=" + this.getAmount() + ")";
  }
}
