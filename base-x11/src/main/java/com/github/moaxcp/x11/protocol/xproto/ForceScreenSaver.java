package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class ForceScreenSaver implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 115;
  private final byte mode;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ForceScreenSaver readForceScreenSaver(X11Input in) throws IOException {
    ForceScreenSaver.ForceScreenSaverBuilder javaBuilder = ForceScreenSaver.builder();
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


  public static class ForceScreenSaverBuilder {
    @java.lang.SuppressWarnings("all")
    private byte mode;

    public ForceScreenSaver.ForceScreenSaverBuilder mode(ScreenSaver mode) {
      this.mode = (byte) mode.getValue();
      return this;
    }

    public ForceScreenSaver.ForceScreenSaverBuilder mode(byte mode) {
      this.mode = mode;
      return this;
    }

    public int getSize() {
      return 4;
    }

    @java.lang.SuppressWarnings("all")
    ForceScreenSaverBuilder() {
    }

    @java.lang.SuppressWarnings("all")
    public ForceScreenSaver build() {
      return new ForceScreenSaver(this.mode);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ForceScreenSaver.ForceScreenSaverBuilder(mode=" + this.mode + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ForceScreenSaver(final byte mode) {
    this.mode = mode;
  }

  @java.lang.SuppressWarnings("all")
  public static ForceScreenSaver.ForceScreenSaverBuilder builder() {
    return new ForceScreenSaver.ForceScreenSaverBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getMode() {
    return this.mode;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ForceScreenSaver)) return false;
    final ForceScreenSaver other = (ForceScreenSaver) o;
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
    return "ForceScreenSaver(mode=" + this.getMode() + ")";
  }
}
