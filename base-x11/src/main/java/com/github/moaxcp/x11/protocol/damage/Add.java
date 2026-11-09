package com.github.moaxcp.x11.protocol.damage;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class Add implements OneWayRequest {
  public static final String PLUGIN_NAME = "damage";
  public static final byte OPCODE = 4;
  private final int drawable;
  private final int region;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Add readAdd(X11Input in) throws IOException {
    Add.AddBuilder javaBuilder = Add.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int drawable = in.readCard32();
    int region = in.readCard32();
    javaBuilder.drawable(drawable);
    javaBuilder.region(region);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeCard32(region);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class AddBuilder {
    @java.lang.SuppressWarnings("all")
    private int drawable;
    @java.lang.SuppressWarnings("all")
    private int region;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    AddBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Add.AddBuilder drawable(final int drawable) {
      this.drawable = drawable;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Add.AddBuilder region(final int region) {
      this.region = region;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Add build() {
      return new Add(this.drawable, this.region);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Add.AddBuilder(drawable=" + this.drawable + ", region=" + this.region + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Add(final int drawable, final int region) {
    this.drawable = drawable;
    this.region = region;
  }

  @java.lang.SuppressWarnings("all")
  public static Add.AddBuilder builder() {
    return new Add.AddBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getDrawable() {
    return this.drawable;
  }

  @java.lang.SuppressWarnings("all")
  public int getRegion() {
    return this.region;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Add)) return false;
    final Add other = (Add) o;
    if (this.getDrawable() != other.getDrawable()) return false;
    if (this.getRegion() != other.getRegion()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDrawable();
    result = result * PRIME + this.getRegion();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Add(drawable=" + this.getDrawable() + ", region=" + this.getRegion() + ")";
  }
}
