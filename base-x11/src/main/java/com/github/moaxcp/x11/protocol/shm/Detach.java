package com.github.moaxcp.x11.protocol.shm;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class Detach implements OneWayRequest {
  public static final String PLUGIN_NAME = "shm";
  public static final byte OPCODE = 2;
  private final int shmseg;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Detach readDetach(X11Input in) throws IOException {
    Detach.DetachBuilder javaBuilder = Detach.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int shmseg = in.readCard32();
    javaBuilder.shmseg(shmseg);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(shmseg);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class DetachBuilder {
    @java.lang.SuppressWarnings("all")
    private int shmseg;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    DetachBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Detach.DetachBuilder shmseg(final int shmseg) {
      this.shmseg = shmseg;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Detach build() {
      return new Detach(this.shmseg);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Detach.DetachBuilder(shmseg=" + this.shmseg + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Detach(final int shmseg) {
    this.shmseg = shmseg;
  }

  @java.lang.SuppressWarnings("all")
  public static Detach.DetachBuilder builder() {
    return new Detach.DetachBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getShmseg() {
    return this.shmseg;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Detach)) return false;
    final Detach other = (Detach) o;
    if (this.getShmseg() != other.getShmseg()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getShmseg();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Detach(shmseg=" + this.getShmseg() + ")";
  }
}
