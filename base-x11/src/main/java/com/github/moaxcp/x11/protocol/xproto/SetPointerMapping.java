package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.ByteList;

public final class SetPointerMapping implements TwoWayRequest<SetPointerMappingReply> {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 116;
  private final ByteList map;

  public XReplyFunction<SetPointerMappingReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> SetPointerMappingReply.readSetPointerMappingReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetPointerMapping readSetPointerMapping(X11Input in) throws IOException {
    SetPointerMapping.SetPointerMappingBuilder javaBuilder = SetPointerMapping.builder();
    byte mapLen = in.readCard8();
    short length = in.readCard16();
    ByteList map = in.readCard8(Byte.toUnsignedInt(mapLen));
    javaBuilder.map(map.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    byte mapLen = (byte) map.size();
    out.writeCard8(mapLen);
    out.writeCard16((short) getLength());
    out.writeCard8(map);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 4 + 1 * map.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetPointerMappingBuilder {
    @java.lang.SuppressWarnings("all")
    private ByteList map;

    public int getSize() {
      return 4 + 1 * map.size();
    }

    @java.lang.SuppressWarnings("all")
    SetPointerMappingBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetPointerMapping.SetPointerMappingBuilder map(final ByteList map) {
      if (map == null) {
        throw new java.lang.NullPointerException("map is marked non-null but is null");
      }
      this.map = map;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetPointerMapping build() {
      return new SetPointerMapping(this.map);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetPointerMapping.SetPointerMappingBuilder(map=" + this.map + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetPointerMapping(final ByteList map) {
    if (map == null) {
      throw new java.lang.NullPointerException("map is marked non-null but is null");
    }
    this.map = map;
  }

  @java.lang.SuppressWarnings("all")
  public static SetPointerMapping.SetPointerMappingBuilder builder() {
    return new SetPointerMapping.SetPointerMappingBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public ByteList getMap() {
    return this.map;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetPointerMapping)) return false;
    final SetPointerMapping other = (SetPointerMapping) o;
    final java.lang.Object this$map = this.getMap();
    final java.lang.Object other$map = other.getMap();
    if (this$map == null ? other$map != null : !this$map.equals(other$map)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final java.lang.Object $map = this.getMap();
    result = result * PRIME + ($map == null ? 43 : $map.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetPointerMapping(map=" + this.getMap() + ")";
  }
}
