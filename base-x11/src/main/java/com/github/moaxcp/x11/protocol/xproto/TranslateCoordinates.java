package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class TranslateCoordinates implements TwoWayRequest<TranslateCoordinatesReply> {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 40;
  private final int srcWindow;
  private final int dstWindow;
  private final short srcX;
  private final short srcY;

  public XReplyFunction<TranslateCoordinatesReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> TranslateCoordinatesReply.readTranslateCoordinatesReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static TranslateCoordinates readTranslateCoordinates(X11Input in) throws IOException {
    TranslateCoordinates.TranslateCoordinatesBuilder javaBuilder = TranslateCoordinates.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int srcWindow = in.readCard32();
    int dstWindow = in.readCard32();
    short srcX = in.readInt16();
    short srcY = in.readInt16();
    javaBuilder.srcWindow(srcWindow);
    javaBuilder.dstWindow(dstWindow);
    javaBuilder.srcX(srcX);
    javaBuilder.srcY(srcY);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(srcWindow);
    out.writeCard32(dstWindow);
    out.writeInt16(srcX);
    out.writeInt16(srcY);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class TranslateCoordinatesBuilder {
    @java.lang.SuppressWarnings("all")
    private int srcWindow;
    @java.lang.SuppressWarnings("all")
    private int dstWindow;
    @java.lang.SuppressWarnings("all")
    private short srcX;
    @java.lang.SuppressWarnings("all")
    private short srcY;

    public int getSize() {
      return 16;
    }

    @java.lang.SuppressWarnings("all")
    TranslateCoordinatesBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public TranslateCoordinates.TranslateCoordinatesBuilder srcWindow(final int srcWindow) {
      this.srcWindow = srcWindow;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public TranslateCoordinates.TranslateCoordinatesBuilder dstWindow(final int dstWindow) {
      this.dstWindow = dstWindow;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public TranslateCoordinates.TranslateCoordinatesBuilder srcX(final short srcX) {
      this.srcX = srcX;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public TranslateCoordinates.TranslateCoordinatesBuilder srcY(final short srcY) {
      this.srcY = srcY;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public TranslateCoordinates build() {
      return new TranslateCoordinates(this.srcWindow, this.dstWindow, this.srcX, this.srcY);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "TranslateCoordinates.TranslateCoordinatesBuilder(srcWindow=" + this.srcWindow + ", dstWindow=" + this.dstWindow + ", srcX=" + this.srcX + ", srcY=" + this.srcY + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  TranslateCoordinates(final int srcWindow, final int dstWindow, final short srcX, final short srcY) {
    this.srcWindow = srcWindow;
    this.dstWindow = dstWindow;
    this.srcX = srcX;
    this.srcY = srcY;
  }

  @java.lang.SuppressWarnings("all")
  public static TranslateCoordinates.TranslateCoordinatesBuilder builder() {
    return new TranslateCoordinates.TranslateCoordinatesBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getSrcWindow() {
    return this.srcWindow;
  }

  @java.lang.SuppressWarnings("all")
  public int getDstWindow() {
    return this.dstWindow;
  }

  @java.lang.SuppressWarnings("all")
  public short getSrcX() {
    return this.srcX;
  }

  @java.lang.SuppressWarnings("all")
  public short getSrcY() {
    return this.srcY;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof TranslateCoordinates)) return false;
    final TranslateCoordinates other = (TranslateCoordinates) o;
    if (this.getSrcWindow() != other.getSrcWindow()) return false;
    if (this.getDstWindow() != other.getDstWindow()) return false;
    if (this.getSrcX() != other.getSrcX()) return false;
    if (this.getSrcY() != other.getSrcY()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSrcWindow();
    result = result * PRIME + this.getDstWindow();
    result = result * PRIME + this.getSrcX();
    result = result * PRIME + this.getSrcY();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "TranslateCoordinates(srcWindow=" + this.getSrcWindow() + ", dstWindow=" + this.getDstWindow() + ", srcX=" + this.getSrcX() + ", srcY=" + this.getSrcY() + ")";
  }
}
