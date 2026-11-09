package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class DeletePointerBarrier implements OneWayRequest {
  public static final String PLUGIN_NAME = "xfixes";
  public static final byte OPCODE = 32;
  private final int barrier;

  public byte getOpCode() {
    return OPCODE;
  }

  public static DeletePointerBarrier readDeletePointerBarrier(X11Input in) throws IOException {
    DeletePointerBarrier.DeletePointerBarrierBuilder javaBuilder = DeletePointerBarrier.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int barrier = in.readCard32();
    javaBuilder.barrier(barrier);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(barrier);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class DeletePointerBarrierBuilder {
    @java.lang.SuppressWarnings("all")
    private int barrier;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    DeletePointerBarrierBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public DeletePointerBarrier.DeletePointerBarrierBuilder barrier(final int barrier) {
      this.barrier = barrier;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public DeletePointerBarrier build() {
      return new DeletePointerBarrier(this.barrier);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "DeletePointerBarrier.DeletePointerBarrierBuilder(barrier=" + this.barrier + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  DeletePointerBarrier(final int barrier) {
    this.barrier = barrier;
  }

  @java.lang.SuppressWarnings("all")
  public static DeletePointerBarrier.DeletePointerBarrierBuilder builder() {
    return new DeletePointerBarrier.DeletePointerBarrierBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getBarrier() {
    return this.barrier;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof DeletePointerBarrier)) return false;
    final DeletePointerBarrier other = (DeletePointerBarrier) o;
    if (this.getBarrier() != other.getBarrier()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getBarrier();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "DeletePointerBarrier(barrier=" + this.getBarrier() + ")";
  }
}
