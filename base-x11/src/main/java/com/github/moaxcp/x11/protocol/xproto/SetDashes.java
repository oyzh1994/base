package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class SetDashes implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 58;
  private final int gc;
  private final short dashOffset;
  private final byte[] dashes;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetDashes readSetDashes(X11Input in) throws IOException {
    SetDashes.SetDashesBuilder javaBuilder = SetDashes.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int gc = in.readCard32();
    short dashOffset = in.readCard16();
    short dashesLen = in.readCard16();
    byte[] dashes = in.readCard8(Short.toUnsignedInt(dashesLen));
    javaBuilder.gc(gc);
    javaBuilder.dashOffset(dashOffset);
    javaBuilder.dashes(dashes);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(gc);
    out.writeCard16(dashOffset);
    short dashesLen = (short) dashes.length;
    out.writeCard16(dashesLen);
    out.writeCard8(dashes);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + 1 * dashes.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetDashesBuilder {
    @java.lang.SuppressWarnings("all")
    private int gc;
    @java.lang.SuppressWarnings("all")
    private short dashOffset;
    @java.lang.SuppressWarnings("all")
    private byte[] dashes;

    public int getSize() {
      return 12 + 1 * dashes.length;
    }

    @java.lang.SuppressWarnings("all")
    SetDashesBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetDashes.SetDashesBuilder gc(final int gc) {
      this.gc = gc;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetDashes.SetDashesBuilder dashOffset(final short dashOffset) {
      this.dashOffset = dashOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetDashes.SetDashesBuilder dashes(final byte[] dashes) {
      if (dashes == null) {
        throw new java.lang.NullPointerException("dashes is marked non-null but is null");
      }
      this.dashes = dashes;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetDashes build() {
      return new SetDashes(this.gc, this.dashOffset, this.dashes);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetDashes.SetDashesBuilder(gc=" + this.gc + ", dashOffset=" + this.dashOffset + ", dashes=" + java.util.Arrays.toString(this.dashes) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetDashes(final int gc, final short dashOffset, final byte[] dashes) {
    if (dashes == null) {
      throw new java.lang.NullPointerException("dashes is marked non-null but is null");
    }
    this.gc = gc;
    this.dashOffset = dashOffset;
    this.dashes = dashes;
  }

  @java.lang.SuppressWarnings("all")
  public static SetDashes.SetDashesBuilder builder() {
    return new SetDashes.SetDashesBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getGc() {
    return this.gc;
  }

  @java.lang.SuppressWarnings("all")
  public short getDashOffset() {
    return this.dashOffset;
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getDashes() {
    return this.dashes;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetDashes)) return false;
    final SetDashes other = (SetDashes) o;
    if (this.getGc() != other.getGc()) return false;
    if (this.getDashOffset() != other.getDashOffset()) return false;
    if (!java.util.Arrays.equals(this.getDashes(), other.getDashes())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getGc();
    result = result * PRIME + this.getDashOffset();
    result = result * PRIME + java.util.Arrays.hashCode(this.getDashes());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetDashes(gc=" + this.getGc() + ", dashOffset=" + this.getDashOffset() + ", dashes=" + java.util.Arrays.toString(this.getDashes()) + ")";
  }
}
