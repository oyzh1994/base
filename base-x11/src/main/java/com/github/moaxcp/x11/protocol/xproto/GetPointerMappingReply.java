package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class GetPointerMappingReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";
  private final short sequenceNumber;
  private final byte[] map;

  public static GetPointerMappingReply readGetPointerMappingReply(byte mapLen, short sequenceNumber, X11Input in) throws IOException {
    GetPointerMappingReply.GetPointerMappingReplyBuilder javaBuilder = GetPointerMappingReply.builder();
    int length = in.readCard32();
    byte[] pad4 = in.readPad(24);
    byte[] map = in.readCard8(Byte.toUnsignedInt(mapLen));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.map(map);
    if (javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    byte mapLen = (byte) map.length;
    out.writeCard8(mapLen);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writePad(24);
    out.writeCard8(map);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 1 * map.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetPointerMappingReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private byte[] map;

    public int getSize() {
      return 32 + 1 * map.length;
    }

    @java.lang.SuppressWarnings("all")
    GetPointerMappingReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetPointerMappingReply.GetPointerMappingReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetPointerMappingReply.GetPointerMappingReplyBuilder map(final byte[] map) {
      if (map == null) {
        throw new java.lang.NullPointerException("map is marked non-null but is null");
      }
      this.map = map;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetPointerMappingReply build() {
      return new GetPointerMappingReply(this.sequenceNumber, this.map);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetPointerMappingReply.GetPointerMappingReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", map=" + java.util.Arrays.toString(this.map) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetPointerMappingReply(final short sequenceNumber, final byte[] map) {
    if (map == null) {
      throw new java.lang.NullPointerException("map is marked non-null but is null");
    }
    this.sequenceNumber = sequenceNumber;
    this.map = map;
  }

  @java.lang.SuppressWarnings("all")
  public static GetPointerMappingReply.GetPointerMappingReplyBuilder builder() {
    return new GetPointerMappingReply.GetPointerMappingReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getMap() {
    return this.map;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetPointerMappingReply)) return false;
    final GetPointerMappingReply other = (GetPointerMappingReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (!java.util.Arrays.equals(this.getMap(), other.getMap())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + java.util.Arrays.hashCode(this.getMap());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetPointerMappingReply(sequenceNumber=" + this.getSequenceNumber() + ", map=" + java.util.Arrays.toString(this.getMap()) + ")";
  }
}
