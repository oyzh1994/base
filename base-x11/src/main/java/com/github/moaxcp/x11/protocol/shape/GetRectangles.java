package com.github.moaxcp.x11.protocol.shape;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GetRectangles implements TwoWayRequest<GetRectanglesReply> {
  public static final String PLUGIN_NAME = "shape";
  public static final byte OPCODE = 8;
  private final int window;
  private final byte sourceKind;

  public XReplyFunction<GetRectanglesReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetRectanglesReply.readGetRectanglesReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetRectangles readGetRectangles(X11Input in) throws IOException {
    GetRectangles.GetRectanglesBuilder javaBuilder = GetRectangles.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    byte sourceKind = in.readCard8();
    byte[] pad5 = in.readPad(3);
    javaBuilder.window(window);
    javaBuilder.sourceKind(sourceKind);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard8(sourceKind);
    out.writePad(3);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetRectanglesBuilder {
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private byte sourceKind;

    public GetRectangles.GetRectanglesBuilder sourceKind(Sk sourceKind) {
      this.sourceKind = (byte) sourceKind.getValue();
      return this;
    }

    public GetRectangles.GetRectanglesBuilder sourceKind(byte sourceKind) {
      this.sourceKind = sourceKind;
      return this;
    }

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    GetRectanglesBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetRectangles.GetRectanglesBuilder window(final int window) {
      this.window = window;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetRectangles build() {
      return new GetRectangles(this.window, this.sourceKind);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetRectangles.GetRectanglesBuilder(window=" + this.window + ", sourceKind=" + this.sourceKind + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetRectangles(final int window, final byte sourceKind) {
    this.window = window;
    this.sourceKind = sourceKind;
  }

  @java.lang.SuppressWarnings("all")
  public static GetRectangles.GetRectanglesBuilder builder() {
    return new GetRectangles.GetRectanglesBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.SuppressWarnings("all")
  public byte getSourceKind() {
    return this.sourceKind;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetRectangles)) return false;
    final GetRectangles other = (GetRectangles) o;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getSourceKind() != other.getSourceKind()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getSourceKind();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetRectangles(window=" + this.getWindow() + ", sourceKind=" + this.getSourceKind() + ")";
  }
}
