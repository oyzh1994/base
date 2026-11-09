package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class CreateCounter implements OneWayRequest {
  public static final String PLUGIN_NAME = "sync";
  public static final byte OPCODE = 2;
  private final int id;
  private final Int64 initialValue;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateCounter readCreateCounter(X11Input in) throws IOException {
    CreateCounter.CreateCounterBuilder javaBuilder = CreateCounter.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int id = in.readCard32();
    Int64 initialValue = Int64.readInt64(in);
    javaBuilder.id(id);
    javaBuilder.initialValue(initialValue);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(id);
    initialValue.write(out);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CreateCounterBuilder {
    @java.lang.SuppressWarnings("all")
    private int id;
    @java.lang.SuppressWarnings("all")
    private Int64 initialValue;

    public int getSize() {
      return 16;
    }

    @java.lang.SuppressWarnings("all")
    CreateCounterBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateCounter.CreateCounterBuilder id(final int id) {
      this.id = id;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateCounter.CreateCounterBuilder initialValue(final Int64 initialValue) {
      if (initialValue == null) {
        throw new java.lang.NullPointerException("initialValue is marked non-null but is null");
      }
      this.initialValue = initialValue;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CreateCounter build() {
      return new CreateCounter(this.id, this.initialValue);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CreateCounter.CreateCounterBuilder(id=" + this.id + ", initialValue=" + this.initialValue + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CreateCounter(final int id, final Int64 initialValue) {
    if (initialValue == null) {
      throw new java.lang.NullPointerException("initialValue is marked non-null but is null");
    }
    this.id = id;
    this.initialValue = initialValue;
  }

  @java.lang.SuppressWarnings("all")
  public static CreateCounter.CreateCounterBuilder builder() {
    return new CreateCounter.CreateCounterBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getId() {
    return this.id;
  }

  @java.lang.SuppressWarnings("all")
  public Int64 getInitialValue() {
    return this.initialValue;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CreateCounter)) return false;
    final CreateCounter other = (CreateCounter) o;
    if (this.getId() != other.getId()) return false;
    final java.lang.Object this$initialValue = this.getInitialValue();
    final java.lang.Object other$initialValue = other.getInitialValue();
    if (this$initialValue == null ? other$initialValue != null : !this$initialValue.equals(other$initialValue)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getId();
    final java.lang.Object $initialValue = this.getInitialValue();
    result = result * PRIME + ($initialValue == null ? 43 : $initialValue.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CreateCounter(id=" + this.getId() + ", initialValue=" + this.getInitialValue() + ")";
  }
}
