package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GetCursorName implements TwoWayRequest<GetCursorNameReply> {
  public static final String PLUGIN_NAME = "xfixes";
  public static final byte OPCODE = 24;
  private final int cursor;

  public XReplyFunction<GetCursorNameReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetCursorNameReply.readGetCursorNameReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetCursorName readGetCursorName(X11Input in) throws IOException {
    GetCursorName.GetCursorNameBuilder javaBuilder = GetCursorName.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int cursor = in.readCard32();
    javaBuilder.cursor(cursor);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(cursor);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetCursorNameBuilder {
    @java.lang.SuppressWarnings("all")
    private int cursor;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    GetCursorNameBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCursorName.GetCursorNameBuilder cursor(final int cursor) {
      this.cursor = cursor;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetCursorName build() {
      return new GetCursorName(this.cursor);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetCursorName.GetCursorNameBuilder(cursor=" + this.cursor + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetCursorName(final int cursor) {
    this.cursor = cursor;
  }

  @java.lang.SuppressWarnings("all")
  public static GetCursorName.GetCursorNameBuilder builder() {
    return new GetCursorName.GetCursorNameBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getCursor() {
    return this.cursor;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetCursorName)) return false;
    final GetCursorName other = (GetCursorName) o;
    if (this.getCursor() != other.getCursor()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getCursor();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetCursorName(cursor=" + this.getCursor() + ")";
  }
}
