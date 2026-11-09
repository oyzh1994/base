package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class DestroyFence implements OneWayRequest {
  public static final String PLUGIN_NAME = "sync";
  public static final byte OPCODE = 17;
  private final int fence;

  public byte getOpCode() {
    return OPCODE;
  }

  public static DestroyFence readDestroyFence(X11Input in) throws IOException {
    DestroyFence.DestroyFenceBuilder javaBuilder = DestroyFence.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int fence = in.readCard32();
    javaBuilder.fence(fence);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(fence);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class DestroyFenceBuilder {
    @java.lang.SuppressWarnings("all")
    private int fence;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    DestroyFenceBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public DestroyFence.DestroyFenceBuilder fence(final int fence) {
      this.fence = fence;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public DestroyFence build() {
      return new DestroyFence(this.fence);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "DestroyFence.DestroyFenceBuilder(fence=" + this.fence + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  DestroyFence(final int fence) {
    this.fence = fence;
  }

  @java.lang.SuppressWarnings("all")
  public static DestroyFence.DestroyFenceBuilder builder() {
    return new DestroyFence.DestroyFenceBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getFence() {
    return this.fence;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof DestroyFence)) return false;
    final DestroyFence other = (DestroyFence) o;
    if (this.getFence() != other.getFence()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFence();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "DestroyFence(fence=" + this.getFence() + ")";
  }
}
