package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class DestroyMode implements OneWayRequest {
  public static final String PLUGIN_NAME = "randr";
  public static final byte OPCODE = 17;
  private final int mode;

  public byte getOpCode() {
    return OPCODE;
  }

  public static DestroyMode readDestroyMode(X11Input in) throws IOException {
    DestroyMode.DestroyModeBuilder javaBuilder = DestroyMode.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int mode = in.readCard32();
    javaBuilder.mode(mode);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(mode);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class DestroyModeBuilder {
    @java.lang.SuppressWarnings("all")
    private int mode;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    DestroyModeBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public DestroyMode.DestroyModeBuilder mode(final int mode) {
      this.mode = mode;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public DestroyMode build() {
      return new DestroyMode(this.mode);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "DestroyMode.DestroyModeBuilder(mode=" + this.mode + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  DestroyMode(final int mode) {
    this.mode = mode;
  }

  @java.lang.SuppressWarnings("all")
  public static DestroyMode.DestroyModeBuilder builder() {
    return new DestroyMode.DestroyModeBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getMode() {
    return this.mode;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof DestroyMode)) return false;
    final DestroyMode other = (DestroyMode) o;
    if (this.getMode() != other.getMode()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getMode();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "DestroyMode(mode=" + this.getMode() + ")";
  }
}
