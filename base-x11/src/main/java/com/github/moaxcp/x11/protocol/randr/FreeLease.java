package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class FreeLease implements OneWayRequest {
  public static final String PLUGIN_NAME = "randr";
  public static final byte OPCODE = 46;
  private final int lid;
  private final byte terminate;

  public byte getOpCode() {
    return OPCODE;
  }

  public static FreeLease readFreeLease(X11Input in) throws IOException {
    FreeLease.FreeLeaseBuilder javaBuilder = FreeLease.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int lid = in.readCard32();
    byte terminate = in.readByte();
    javaBuilder.lid(lid);
    javaBuilder.terminate(terminate);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(lid);
    out.writeByte(terminate);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 9;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class FreeLeaseBuilder {
    @java.lang.SuppressWarnings("all")
    private int lid;
    @java.lang.SuppressWarnings("all")
    private byte terminate;

    public int getSize() {
      return 9;
    }

    @java.lang.SuppressWarnings("all")
    FreeLeaseBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public FreeLease.FreeLeaseBuilder lid(final int lid) {
      this.lid = lid;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public FreeLease.FreeLeaseBuilder terminate(final byte terminate) {
      this.terminate = terminate;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public FreeLease build() {
      return new FreeLease(this.lid, this.terminate);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "FreeLease.FreeLeaseBuilder(lid=" + this.lid + ", terminate=" + this.terminate + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  FreeLease(final int lid, final byte terminate) {
    this.lid = lid;
    this.terminate = terminate;
  }

  @java.lang.SuppressWarnings("all")
  public static FreeLease.FreeLeaseBuilder builder() {
    return new FreeLease.FreeLeaseBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getLid() {
    return this.lid;
  }

  @java.lang.SuppressWarnings("all")
  public byte getTerminate() {
    return this.terminate;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof FreeLease)) return false;
    final FreeLease other = (FreeLease) o;
    if (this.getLid() != other.getLid()) return false;
    if (this.getTerminate() != other.getTerminate()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getLid();
    result = result * PRIME + this.getTerminate();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "FreeLease(lid=" + this.getLid() + ", terminate=" + this.getTerminate() + ")";
  }
}
