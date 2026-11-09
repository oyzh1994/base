package com.github.moaxcp.x11.protocol.screensaver;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class UnsetAttributes implements OneWayRequest {
  public static final String PLUGIN_NAME = "screensaver";
  public static final byte OPCODE = 4;
  private final int drawable;

  public byte getOpCode() {
    return OPCODE;
  }

  public static UnsetAttributes readUnsetAttributes(X11Input in) throws IOException {
    UnsetAttributes.UnsetAttributesBuilder javaBuilder = UnsetAttributes.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int drawable = in.readCard32();
    javaBuilder.drawable(drawable);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class UnsetAttributesBuilder {
    @java.lang.SuppressWarnings("all")
    private int drawable;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    UnsetAttributesBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public UnsetAttributes.UnsetAttributesBuilder drawable(final int drawable) {
      this.drawable = drawable;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public UnsetAttributes build() {
      return new UnsetAttributes(this.drawable);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "UnsetAttributes.UnsetAttributesBuilder(drawable=" + this.drawable + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  UnsetAttributes(final int drawable) {
    this.drawable = drawable;
  }

  @java.lang.SuppressWarnings("all")
  public static UnsetAttributes.UnsetAttributesBuilder builder() {
    return new UnsetAttributes.UnsetAttributesBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getDrawable() {
    return this.drawable;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof UnsetAttributes)) return false;
    final UnsetAttributes other = (UnsetAttributes) o;
    if (this.getDrawable() != other.getDrawable()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDrawable();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "UnsetAttributes(drawable=" + this.getDrawable() + ")";
  }
}
