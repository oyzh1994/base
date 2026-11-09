package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class FreeGC implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 60;
  private final int gc;

  public byte getOpCode() {
    return OPCODE;
  }

  public static FreeGC readFreeGC(X11Input in) throws IOException {
    FreeGC.FreeGCBuilder javaBuilder = FreeGC.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int gc = in.readCard32();
    javaBuilder.gc(gc);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(gc);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class FreeGCBuilder {
    @java.lang.SuppressWarnings("all")
    private int gc;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    FreeGCBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public FreeGC.FreeGCBuilder gc(final int gc) {
      this.gc = gc;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public FreeGC build() {
      return new FreeGC(this.gc);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "FreeGC.FreeGCBuilder(gc=" + this.gc + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  FreeGC(final int gc) {
    this.gc = gc;
  }

  @java.lang.SuppressWarnings("all")
  public static FreeGC.FreeGCBuilder builder() {
    return new FreeGC.FreeGCBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getGc() {
    return this.gc;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof FreeGC)) return false;
    final FreeGC other = (FreeGC) o;
    if (this.getGc() != other.getGc()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getGc();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "FreeGC(gc=" + this.getGc() + ")";
  }
}
