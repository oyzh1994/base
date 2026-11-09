package com.github.moaxcp.x11.protocol.shape;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class Offset implements OneWayRequest {
  public static final String PLUGIN_NAME = "shape";
  public static final byte OPCODE = 4;
  private final byte destinationKind;
  private final int destinationWindow;
  private final short xOffset;
  private final short yOffset;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Offset readOffset(X11Input in) throws IOException {
    Offset.OffsetBuilder javaBuilder = Offset.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    byte destinationKind = in.readCard8();
    byte[] pad4 = in.readPad(3);
    int destinationWindow = in.readCard32();
    short xOffset = in.readInt16();
    short yOffset = in.readInt16();
    javaBuilder.destinationKind(destinationKind);
    javaBuilder.destinationWindow(destinationWindow);
    javaBuilder.xOffset(xOffset);
    javaBuilder.yOffset(yOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard8(destinationKind);
    out.writePad(3);
    out.writeCard32(destinationWindow);
    out.writeInt16(xOffset);
    out.writeInt16(yOffset);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class OffsetBuilder {
    @java.lang.SuppressWarnings("all")
    private byte destinationKind;
    @java.lang.SuppressWarnings("all")
    private int destinationWindow;
    @java.lang.SuppressWarnings("all")
    private short xOffset;
    @java.lang.SuppressWarnings("all")
    private short yOffset;

    public Offset.OffsetBuilder destinationKind(Sk destinationKind) {
      this.destinationKind = (byte) destinationKind.getValue();
      return this;
    }

    public Offset.OffsetBuilder destinationKind(byte destinationKind) {
      this.destinationKind = destinationKind;
      return this;
    }

    public int getSize() {
      return 16;
    }

    @java.lang.SuppressWarnings("all")
    OffsetBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Offset.OffsetBuilder destinationWindow(final int destinationWindow) {
      this.destinationWindow = destinationWindow;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Offset.OffsetBuilder xOffset(final short xOffset) {
      this.xOffset = xOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Offset.OffsetBuilder yOffset(final short yOffset) {
      this.yOffset = yOffset;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Offset build() {
      return new Offset(this.destinationKind, this.destinationWindow, this.xOffset, this.yOffset);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Offset.OffsetBuilder(destinationKind=" + this.destinationKind + ", destinationWindow=" + this.destinationWindow + ", xOffset=" + this.xOffset + ", yOffset=" + this.yOffset + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Offset(final byte destinationKind, final int destinationWindow, final short xOffset, final short yOffset) {
    this.destinationKind = destinationKind;
    this.destinationWindow = destinationWindow;
    this.xOffset = xOffset;
    this.yOffset = yOffset;
  }

  @java.lang.SuppressWarnings("all")
  public static Offset.OffsetBuilder builder() {
    return new Offset.OffsetBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getDestinationKind() {
    return this.destinationKind;
  }

  @java.lang.SuppressWarnings("all")
  public int getDestinationWindow() {
    return this.destinationWindow;
  }

  @java.lang.SuppressWarnings("all")
  public short getXOffset() {
    return this.xOffset;
  }

  @java.lang.SuppressWarnings("all")
  public short getYOffset() {
    return this.yOffset;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Offset)) return false;
    final Offset other = (Offset) o;
    if (this.getDestinationKind() != other.getDestinationKind()) return false;
    if (this.getDestinationWindow() != other.getDestinationWindow()) return false;
    if (this.getXOffset() != other.getXOffset()) return false;
    if (this.getYOffset() != other.getYOffset()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDestinationKind();
    result = result * PRIME + this.getDestinationWindow();
    result = result * PRIME + this.getXOffset();
    result = result * PRIME + this.getYOffset();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Offset(destinationKind=" + this.getDestinationKind() + ", destinationWindow=" + this.getDestinationWindow() + ", xOffset=" + this.getXOffset() + ", yOffset=" + this.getYOffset() + ")";
  }
}
