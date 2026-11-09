package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class GetModifierMappingReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";
  private final short sequenceNumber;
  private final byte[] keycodes;

  public static GetModifierMappingReply readGetModifierMappingReply(byte keycodesPerModifier, short sequenceNumber, X11Input in) throws IOException {
    GetModifierMappingReply.GetModifierMappingReplyBuilder javaBuilder = GetModifierMappingReply.builder();
    int length = in.readCard32();
    byte[] pad4 = in.readPad(24);
    byte[] keycodes = in.readCard8(Byte.toUnsignedInt(keycodesPerModifier) * 8);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.keycodes(keycodes);
    if (javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    byte keycodesPerModifier = (byte) keycodes.length;
    out.writeCard8(keycodesPerModifier);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writePad(24);
    out.writeCard8(keycodes);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 1 * keycodes.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetModifierMappingReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private byte[] keycodes;

    public int getSize() {
      return 32 + 1 * keycodes.length;
    }

    @java.lang.SuppressWarnings("all")
    GetModifierMappingReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetModifierMappingReply.GetModifierMappingReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetModifierMappingReply.GetModifierMappingReplyBuilder keycodes(final byte[] keycodes) {
      if (keycodes == null) {
        throw new java.lang.NullPointerException("keycodes is marked non-null but is null");
      }
      this.keycodes = keycodes;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetModifierMappingReply build() {
      return new GetModifierMappingReply(this.sequenceNumber, this.keycodes);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetModifierMappingReply.GetModifierMappingReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", keycodes=" + java.util.Arrays.toString(this.keycodes) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetModifierMappingReply(final short sequenceNumber, final byte[] keycodes) {
    if (keycodes == null) {
      throw new java.lang.NullPointerException("keycodes is marked non-null but is null");
    }
    this.sequenceNumber = sequenceNumber;
    this.keycodes = keycodes;
  }

  @java.lang.SuppressWarnings("all")
  public static GetModifierMappingReply.GetModifierMappingReplyBuilder builder() {
    return new GetModifierMappingReply.GetModifierMappingReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getKeycodes() {
    return this.keycodes;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetModifierMappingReply)) return false;
    final GetModifierMappingReply other = (GetModifierMappingReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (!java.util.Arrays.equals(this.getKeycodes(), other.getKeycodes())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + java.util.Arrays.hashCode(this.getKeycodes());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetModifierMappingReply(sequenceNumber=" + this.getSequenceNumber() + ", keycodes=" + java.util.Arrays.toString(this.getKeycodes()) + ")";
  }
}
