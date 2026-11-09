package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GetScreenSizeRange implements TwoWayRequest<GetScreenSizeRangeReply> {
  public static final String PLUGIN_NAME = "randr";
  public static final byte OPCODE = 6;
  private final int window;

  public XReplyFunction<GetScreenSizeRangeReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetScreenSizeRangeReply.readGetScreenSizeRangeReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetScreenSizeRange readGetScreenSizeRange(X11Input in) throws IOException {
    GetScreenSizeRange.GetScreenSizeRangeBuilder javaBuilder = GetScreenSizeRange.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    javaBuilder.window(window);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetScreenSizeRangeBuilder {
    @java.lang.SuppressWarnings("all")
    private int window;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    GetScreenSizeRangeBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenSizeRange.GetScreenSizeRangeBuilder window(final int window) {
      this.window = window;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetScreenSizeRange build() {
      return new GetScreenSizeRange(this.window);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetScreenSizeRange.GetScreenSizeRangeBuilder(window=" + this.window + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetScreenSizeRange(final int window) {
    this.window = window;
  }

  @java.lang.SuppressWarnings("all")
  public static GetScreenSizeRange.GetScreenSizeRangeBuilder builder() {
    return new GetScreenSizeRange.GetScreenSizeRangeBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetScreenSizeRange)) return false;
    final GetScreenSizeRange other = (GetScreenSizeRange) o;
    if (this.getWindow() != other.getWindow()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getWindow();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetScreenSizeRange(window=" + this.getWindow() + ")";
  }
}
