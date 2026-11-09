package com.github.moaxcp.x11.protocol.damage;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class Destroy implements OneWayRequest {
  public static final String PLUGIN_NAME = "damage";
  public static final byte OPCODE = 2;
  private final int damage;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Destroy readDestroy(X11Input in) throws IOException {
    Destroy.DestroyBuilder javaBuilder = Destroy.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int damage = in.readCard32();
    javaBuilder.damage(damage);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(damage);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class DestroyBuilder {
    @java.lang.SuppressWarnings("all")
    private int damage;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    DestroyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Destroy.DestroyBuilder damage(final int damage) {
      this.damage = damage;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Destroy build() {
      return new Destroy(this.damage);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Destroy.DestroyBuilder(damage=" + this.damage + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Destroy(final int damage) {
    this.damage = damage;
  }

  @java.lang.SuppressWarnings("all")
  public static Destroy.DestroyBuilder builder() {
    return new Destroy.DestroyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getDamage() {
    return this.damage;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Destroy)) return false;
    final Destroy other = (Destroy) o;
    if (this.getDamage() != other.getDamage()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDamage();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Destroy(damage=" + this.getDamage() + ")";
  }
}
