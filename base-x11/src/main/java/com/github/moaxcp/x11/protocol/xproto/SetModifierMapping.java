package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.ByteList;

public final class SetModifierMapping implements TwoWayRequest<SetModifierMappingReply> {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 118;
  private final ByteList keycodes;

  public XReplyFunction<SetModifierMappingReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> SetModifierMappingReply.readSetModifierMappingReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetModifierMapping readSetModifierMapping(X11Input in) throws IOException {
    SetModifierMapping.SetModifierMappingBuilder javaBuilder = SetModifierMapping.builder();
    byte keycodesPerModifier = in.readCard8();
    short length = in.readCard16();
    ByteList keycodes = in.readCard8(Byte.toUnsignedInt(keycodesPerModifier) * 8);
    javaBuilder.keycodes(keycodes.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    byte keycodesPerModifier = (byte) keycodes.size();
    out.writeCard8(keycodesPerModifier);
    out.writeCard16((short) getLength());
    out.writeCard8(keycodes);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 4 + 1 * keycodes.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetModifierMappingBuilder {
    @java.lang.SuppressWarnings("all")
    private ByteList keycodes;

    public int getSize() {
      return 4 + 1 * keycodes.size();
    }

    @java.lang.SuppressWarnings("all")
    SetModifierMappingBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetModifierMapping.SetModifierMappingBuilder keycodes(final ByteList keycodes) {
      if (keycodes == null) {
        throw new java.lang.NullPointerException("keycodes is marked non-null but is null");
      }
      this.keycodes = keycodes;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetModifierMapping build() {
      return new SetModifierMapping(this.keycodes);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetModifierMapping.SetModifierMappingBuilder(keycodes=" + this.keycodes + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetModifierMapping(final ByteList keycodes) {
    if (keycodes == null) {
      throw new java.lang.NullPointerException("keycodes is marked non-null but is null");
    }
    this.keycodes = keycodes;
  }

  @java.lang.SuppressWarnings("all")
  public static SetModifierMapping.SetModifierMappingBuilder builder() {
    return new SetModifierMapping.SetModifierMappingBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public ByteList getKeycodes() {
    return this.keycodes;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetModifierMapping)) return false;
    final SetModifierMapping other = (SetModifierMapping) o;
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
    final java.lang.Object $keycodes = this.getKeycodes();
    result = result * PRIME + ($keycodes == null ? 43 : $keycodes.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetModifierMapping(keycodes=" + this.getKeycodes() + ")";
  }
}
