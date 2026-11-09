package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class SetAccessControl implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 111;
  private final byte mode;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetAccessControl readSetAccessControl(X11Input in) throws IOException {
    SetAccessControl.SetAccessControlBuilder javaBuilder = SetAccessControl.builder();
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


  public static class SetAccessControlBuilder {
    @java.lang.SuppressWarnings("all")
    private byte mode;

    public SetAccessControl.SetAccessControlBuilder mode(AccessControl mode) {
      this.mode = (byte) mode.getValue();
      return this;
    }

    public SetAccessControl.SetAccessControlBuilder mode(byte mode) {
      this.mode = mode;
      return this;
    }

    public int getSize() {
      return 4;
    }

    @java.lang.SuppressWarnings("all")
    SetAccessControlBuilder() {
    }

    @java.lang.SuppressWarnings("all")
    public SetAccessControl build() {
      return new SetAccessControl(this.mode);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetAccessControl.SetAccessControlBuilder(mode=" + this.mode + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetAccessControl(final byte mode) {
    this.mode = mode;
  }

  @java.lang.SuppressWarnings("all")
  public static SetAccessControl.SetAccessControlBuilder builder() {
    return new SetAccessControl.SetAccessControlBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getMode() {
    return this.mode;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetAccessControl)) return false;
    final SetAccessControl other = (SetAccessControl) o;
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
    return "SetAccessControl(mode=" + this.getMode() + ")";
  }
}
