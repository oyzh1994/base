package com.github.moaxcp.x11.protocol.damage;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class Create implements OneWayRequest {
  public static final String PLUGIN_NAME = "damage";
  public static final byte OPCODE = 1;
  private final int damage;
  private final int drawable;
  private final byte level;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Create readCreate(X11Input in) throws IOException {
    Create.CreateBuilder javaBuilder = Create.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int damage = in.readCard32();
    int drawable = in.readCard32();
    byte level = in.readCard8();
    byte[] pad6 = in.readPad(3);
    javaBuilder.damage(damage);
    javaBuilder.drawable(drawable);
    javaBuilder.level(level);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(damage);
    out.writeCard32(drawable);
    out.writeCard8(level);
    out.writePad(3);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CreateBuilder {
    @java.lang.SuppressWarnings("all")
    private int damage;
    @java.lang.SuppressWarnings("all")
    private int drawable;
    @java.lang.SuppressWarnings("all")
    private byte level;

    public Create.CreateBuilder level(ReportLevel level) {
      this.level = (byte) level.getValue();
      return this;
    }

    public Create.CreateBuilder level(byte level) {
      this.level = level;
      return this;
    }

    public int getSize() {
      return 16;
    }

    @java.lang.SuppressWarnings("all")
    CreateBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Create.CreateBuilder damage(final int damage) {
      this.damage = damage;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Create.CreateBuilder drawable(final int drawable) {
      this.drawable = drawable;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Create build() {
      return new Create(this.damage, this.drawable, this.level);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Create.CreateBuilder(damage=" + this.damage + ", drawable=" + this.drawable + ", level=" + this.level + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Create(final int damage, final int drawable, final byte level) {
    this.damage = damage;
    this.drawable = drawable;
    this.level = level;
  }

  @java.lang.SuppressWarnings("all")
  public static Create.CreateBuilder builder() {
    return new Create.CreateBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getDamage() {
    return this.damage;
  }

  @java.lang.SuppressWarnings("all")
  public int getDrawable() {
    return this.drawable;
  }

  @java.lang.SuppressWarnings("all")
  public byte getLevel() {
    return this.level;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Create)) return false;
    final Create other = (Create) o;
    if (this.getDamage() != other.getDamage()) return false;
    if (this.getDrawable() != other.getDrawable()) return false;
    if (this.getLevel() != other.getLevel()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDamage();
    result = result * PRIME + this.getDrawable();
    result = result * PRIME + this.getLevel();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Create(damage=" + this.getDamage() + ", drawable=" + this.getDrawable() + ", level=" + this.getLevel() + ")";
  }
}
