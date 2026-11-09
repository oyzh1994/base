package com.github.moaxcp.x11.protocol.damage;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class Subtract implements OneWayRequest {
  public static final String PLUGIN_NAME = "damage";
  public static final byte OPCODE = 3;
  private final int damage;
  private final int repair;
  private final int parts;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Subtract readSubtract(X11Input in) throws IOException {
    Subtract.SubtractBuilder javaBuilder = Subtract.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int damage = in.readCard32();
    int repair = in.readCard32();
    int parts = in.readCard32();
    javaBuilder.damage(damage);
    javaBuilder.repair(repair);
    javaBuilder.parts(parts);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(damage);
    out.writeCard32(repair);
    out.writeCard32(parts);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SubtractBuilder {
    @java.lang.SuppressWarnings("all")
    private int damage;
    @java.lang.SuppressWarnings("all")
    private int repair;
    @java.lang.SuppressWarnings("all")
    private int parts;

    public int getSize() {
      return 16;
    }

    @java.lang.SuppressWarnings("all")
    SubtractBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Subtract.SubtractBuilder damage(final int damage) {
      this.damage = damage;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Subtract.SubtractBuilder repair(final int repair) {
      this.repair = repair;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Subtract.SubtractBuilder parts(final int parts) {
      this.parts = parts;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Subtract build() {
      return new Subtract(this.damage, this.repair, this.parts);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Subtract.SubtractBuilder(damage=" + this.damage + ", repair=" + this.repair + ", parts=" + this.parts + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Subtract(final int damage, final int repair, final int parts) {
    this.damage = damage;
    this.repair = repair;
    this.parts = parts;
  }

  @java.lang.SuppressWarnings("all")
  public static Subtract.SubtractBuilder builder() {
    return new Subtract.SubtractBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getDamage() {
    return this.damage;
  }

  @java.lang.SuppressWarnings("all")
  public int getRepair() {
    return this.repair;
  }

  @java.lang.SuppressWarnings("all")
  public int getParts() {
    return this.parts;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Subtract)) return false;
    final Subtract other = (Subtract) o;
    if (this.getDamage() != other.getDamage()) return false;
    if (this.getRepair() != other.getRepair()) return false;
    if (this.getParts() != other.getParts()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDamage();
    result = result * PRIME + this.getRepair();
    result = result * PRIME + this.getParts();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Subtract(damage=" + this.getDamage() + ", repair=" + this.getRepair() + ", parts=" + this.getParts() + ")";
  }
}
