package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GetKeyboardMapping implements TwoWayRequest<GetKeyboardMappingReply> {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 101;
  private final byte firstKeycode;
  private final byte count;

  public XReplyFunction<GetKeyboardMappingReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetKeyboardMappingReply.readGetKeyboardMappingReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetKeyboardMapping readGetKeyboardMapping(X11Input in) throws IOException {
    GetKeyboardMapping.GetKeyboardMappingBuilder javaBuilder = GetKeyboardMapping.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    byte firstKeycode = in.readCard8();
    byte count = in.readCard8();
    javaBuilder.firstKeycode(firstKeycode);
    javaBuilder.count(count);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard8(firstKeycode);
    out.writeCard8(count);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 6;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetKeyboardMappingBuilder {
    @java.lang.SuppressWarnings("all")
    private byte firstKeycode;
    @java.lang.SuppressWarnings("all")
    private byte count;

    public int getSize() {
      return 6;
    }

    @java.lang.SuppressWarnings("all")
    GetKeyboardMappingBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetKeyboardMapping.GetKeyboardMappingBuilder firstKeycode(final byte firstKeycode) {
      this.firstKeycode = firstKeycode;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetKeyboardMapping.GetKeyboardMappingBuilder count(final byte count) {
      this.count = count;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetKeyboardMapping build() {
      return new GetKeyboardMapping(this.firstKeycode, this.count);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetKeyboardMapping.GetKeyboardMappingBuilder(firstKeycode=" + this.firstKeycode + ", count=" + this.count + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetKeyboardMapping(final byte firstKeycode, final byte count) {
    this.firstKeycode = firstKeycode;
    this.count = count;
  }

  @java.lang.SuppressWarnings("all")
  public static GetKeyboardMapping.GetKeyboardMappingBuilder builder() {
    return new GetKeyboardMapping.GetKeyboardMappingBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getFirstKeycode() {
    return this.firstKeycode;
  }

  @java.lang.SuppressWarnings("all")
  public byte getCount() {
    return this.count;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetKeyboardMapping)) return false;
    final GetKeyboardMapping other = (GetKeyboardMapping) o;
    if (this.getFirstKeycode() != other.getFirstKeycode()) return false;
    if (this.getCount() != other.getCount()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFirstKeycode();
    result = result * PRIME + this.getCount();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetKeyboardMapping(firstKeycode=" + this.getFirstKeycode() + ", count=" + this.getCount() + ")";
  }
}
