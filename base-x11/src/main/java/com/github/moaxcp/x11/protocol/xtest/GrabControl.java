package com.github.moaxcp.x11.protocol.xtest;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class GrabControl implements OneWayRequest {
  public static final String PLUGIN_NAME = "xtest";
  public static final byte OPCODE = 3;
  private final boolean impervious;

  public byte getOpCode() {
    return OPCODE;
  }

  public static GrabControl readGrabControl(X11Input in) throws IOException {
    GrabControl.GrabControlBuilder javaBuilder = GrabControl.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    boolean impervious = in.readBool();
    byte[] pad4 = in.readPad(3);
    javaBuilder.impervious(impervious);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeBool(impervious);
    out.writePad(3);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GrabControlBuilder {
    @java.lang.SuppressWarnings("all")
    private boolean impervious;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    GrabControlBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GrabControl.GrabControlBuilder impervious(final boolean impervious) {
      this.impervious = impervious;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GrabControl build() {
      return new GrabControl(this.impervious);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GrabControl.GrabControlBuilder(impervious=" + this.impervious + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GrabControl(final boolean impervious) {
    this.impervious = impervious;
  }

  @java.lang.SuppressWarnings("all")
  public static GrabControl.GrabControlBuilder builder() {
    return new GrabControl.GrabControlBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public boolean isImpervious() {
    return this.impervious;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GrabControl)) return false;
    final GrabControl other = (GrabControl) o;
    if (this.isImpervious() != other.isImpervious()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + (this.isImpervious() ? 79 : 97);
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GrabControl(impervious=" + this.isImpervious() + ")";
  }
}
