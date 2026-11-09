package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.ByteList;

public final class GetModifierMappingReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";
  private final short sequenceNumber;
  private final ByteList keycodes;

  public static GetModifierMappingReply readGetModifierMappingReply(byte keycodesPerModifier, short sequenceNumber, X11Input in) throws IOException {
    GetModifierMappingReply.GetModifierMappingReplyBuilder javaBuilder = GetModifierMappingReply.builder();
    int length = in.readCard32();
    byte[] pad4 = in.readPad(24);
    ByteList keycodes = in.readCard8(Byte.toUnsignedInt(keycodesPerModifier) * 8);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.keycodes(keycodes.toImmutable());
    if (javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    byte keycodesPerModifier = (byte) keycodes.size();
    out.writeCard8(keycodesPerModifier);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writePad(24);
    out.writeCard8(keycodes);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 1 * keycodes.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetModifierMappingReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private ByteList keycodes;

    public int getSize() {
      return 32 + 1 * keycodes.size();
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
    public GetModifierMappingReply.GetModifierMappingReplyBuilder keycodes(final ByteList keycodes) {
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
      return "GetModifierMappingReply.GetModifierMappingReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", keycodes=" + this.keycodes + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetModifierMappingReply(final short sequenceNumber, final ByteList keycodes) {
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
  public ByteList getKeycodes() {
    return this.keycodes;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetModifierMappingReply)) return false;
    final GetModifierMappingReply other = (GetModifierMappingReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    final java.lang.Object this$keycodes = this.getKeycodes();
    final java.lang.Object other$keycodes = other.getKeycodes();
    if (this$keycodes == null ? other$keycodes != null : !this$keycodes.equals(other$keycodes)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    final java.lang.Object $keycodes = this.getKeycodes();
    result = result * PRIME + ($keycodes == null ? 43 : $keycodes.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetModifierMappingReply(sequenceNumber=" + this.getSequenceNumber() + ", keycodes=" + this.getKeycodes() + ")";
  }
}
