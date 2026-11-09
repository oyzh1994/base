package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class SetCloseDownMode implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 112;
  private final byte mode;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetCloseDownMode readSetCloseDownMode(X11Input in) throws IOException {
    SetCloseDownMode.SetCloseDownModeBuilder javaBuilder = SetCloseDownMode.builder();
    byte mode = in.readCard8();
    short length = in.readCard16();
    javaBuilder.mode(mode);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard8(mode);
    out.writeCard16((short) getLength());
  }

  @Override
  public int getSize() {
    return 4;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetCloseDownModeBuilder {
    @java.lang.SuppressWarnings("all")
    private byte mode;

    public SetCloseDownMode.SetCloseDownModeBuilder mode(CloseDown mode) {
      this.mode = (byte) mode.getValue();
      return this;
    }

    public SetCloseDownMode.SetCloseDownModeBuilder mode(byte mode) {
      this.mode = mode;
      return this;
    }

    public int getSize() {
      return 4;
    }

    @java.lang.SuppressWarnings("all")
    SetCloseDownModeBuilder() {
    }

    @java.lang.SuppressWarnings("all")
    public SetCloseDownMode build() {
      return new SetCloseDownMode(this.mode);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetCloseDownMode.SetCloseDownModeBuilder(mode=" + this.mode + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetCloseDownMode(final byte mode) {
    this.mode = mode;
  }

  @java.lang.SuppressWarnings("all")
  public static SetCloseDownMode.SetCloseDownModeBuilder builder() {
    return new SetCloseDownMode.SetCloseDownModeBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getMode() {
    return this.mode;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetCloseDownMode)) return false;
    final SetCloseDownMode other = (SetCloseDownMode) o;
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
    return "SetCloseDownMode(mode=" + this.getMode() + ")";
  }
}
