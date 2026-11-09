package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class UngrabPointer implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 27;
  private final int time;

  public byte getOpCode() {
    return OPCODE;
  }

  public static UngrabPointer readUngrabPointer(X11Input in) throws IOException {
    UngrabPointer.UngrabPointerBuilder javaBuilder = UngrabPointer.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int time = in.readCard32();
    javaBuilder.time(time);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(time);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class UngrabPointerBuilder {
    @java.lang.SuppressWarnings("all")
    private int time;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    UngrabPointerBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public UngrabPointer.UngrabPointerBuilder time(final int time) {
      this.time = time;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public UngrabPointer build() {
      return new UngrabPointer(this.time);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "UngrabPointer.UngrabPointerBuilder(time=" + this.time + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  UngrabPointer(final int time) {
    this.time = time;
  }

  @java.lang.SuppressWarnings("all")
  public static UngrabPointer.UngrabPointerBuilder builder() {
    return new UngrabPointer.UngrabPointerBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getTime() {
    return this.time;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof UngrabPointer)) return false;
    final UngrabPointer other = (UngrabPointer) o;
    if (this.getTime() != other.getTime()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getTime();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "UngrabPointer(time=" + this.getTime() + ")";
  }
}
