package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class SetModifierMapping implements TwoWayRequest<SetModifierMappingReply> {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 118;
  private final byte[] keycodes;

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
    byte[] keycodes = in.readCard8(Byte.toUnsignedInt(keycodesPerModifier) * 8);
    javaBuilder.keycodes(keycodes);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    byte keycodesPerModifier = (byte) keycodes.length;
    out.writeCard8(keycodesPerModifier);
    out.writeCard16((short) getLength());
    out.writeCard8(keycodes);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 4 + 1 * keycodes.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetModifierMappingBuilder {
    @java.lang.SuppressWarnings("all")
    private byte[] keycodes;

    public int getSize() {
      return 4 + 1 * keycodes.length;
    }

    @java.lang.SuppressWarnings("all")
    SetModifierMappingBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetModifierMapping.SetModifierMappingBuilder keycodes(final byte[] keycodes) {
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
      return "SetModifierMapping.SetModifierMappingBuilder(keycodes=" + java.util.Arrays.toString(this.keycodes) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetModifierMapping(final byte[] keycodes) {
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
  public byte[] getKeycodes() {
    return this.keycodes;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetModifierMapping)) return false;
    final SetModifierMapping other = (SetModifierMapping) o;
    if (!java.util.Arrays.equals(this.getKeycodes(), other.getKeycodes())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + java.util.Arrays.hashCode(this.getKeycodes());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetModifierMapping(keycodes=" + java.util.Arrays.toString(this.getKeycodes()) + ")";
  }
}
