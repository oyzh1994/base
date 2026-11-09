package com.github.moaxcp.x11.protocol.shape;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class Mask implements OneWayRequest {
  public static final String PLUGIN_NAME = "shape";
  public static final byte OPCODE = 2;
  private final byte operation;
  private final byte destinationKind;
  private final int destinationWindow;
  private final short xOffset;
  private final short yOffset;
  private final int sourceBitmap;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Mask readMask(X11Input in) throws IOException {
    Mask.MaskBuilder javaBuilder = Mask.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    byte operation = in.readCard8();
    byte destinationKind = in.readCard8();
    byte[] pad5 = in.readPad(2);
    int destinationWindow = in.readCard32();
    short xOffset = in.readInt16();
    short yOffset = in.readInt16();
    int sourceBitmap = in.readCard32();
    javaBuilder.operation(operation);
    javaBuilder.destinationKind(destinationKind);
    javaBuilder.destinationWindow(destinationWindow);
    javaBuilder.xOffset(xOffset);
    javaBuilder.yOffset(yOffset);
    javaBuilder.sourceBitmap(sourceBitmap);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard8(operation);
    out.writeCard8(destinationKind);
    out.writePad(2);
    out.writeCard32(destinationWindow);
    out.writeInt16(xOffset);
    out.writeInt16(yOffset);
    out.writeCard32(sourceBitmap);
  }

  @Override
  public int getSize() {
    return 20;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class MaskBuilder {
    @java.lang.SuppressWarnings("all")
    private byte operation;
    @java.lang.SuppressWarnings("all")
    private byte destinationKind;
    @java.lang.SuppressWarnings("all")
    private int destinationWindow;
    @java.lang.SuppressWarnings("all")
    private short xOffset;
    @java.lang.SuppressWarnings("all")
    private short yOffset;
    @java.lang.SuppressWarnings("all")
    private int sourceBitmap;

    public Mask.MaskBuilder operation(So operation) {
      this.operation = (byte) operation.getValue();
      return this;
    }

    public Mask.MaskBuilder operation(byte operation) {
      this.operation = operation;
      return this;
    }

    public Mask.MaskBuilder destinationKind(Sk destinationKind) {
      this.destinationKind = (byte) destinationKind.getValue();
      return this;
    }

    public Mask.MaskBuilder destinationKind(byte destinationKind) {
      this.destinationKind = destinationKind;
      return this;
    }

    public int getSize() {
      return 20;
    }

    @java.lang.SuppressWarnings("all")
    MaskBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Mask.MaskBuilder destinationWindow(final int destinationWindow) {
      this.destinationWindow = destinationWindow;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Mask.MaskBuilder xOffset(final short xOffset) {
      this.xOffset = xOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Mask.MaskBuilder yOffset(final short yOffset) {
      this.yOffset = yOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Mask.MaskBuilder sourceBitmap(final int sourceBitmap) {
      this.sourceBitmap = sourceBitmap;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Mask build() {
      return new Mask(this.operation, this.destinationKind, this.destinationWindow, this.xOffset, this.yOffset, this.sourceBitmap);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Mask.MaskBuilder(operation=" + this.operation + ", destinationKind=" + this.destinationKind + ", destinationWindow=" + this.destinationWindow + ", xOffset=" + this.xOffset + ", yOffset=" + this.yOffset + ", sourceBitmap=" + this.sourceBitmap + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Mask(final byte operation, final byte destinationKind, final int destinationWindow, final short xOffset, final short yOffset, final int sourceBitmap) {
    this.operation = operation;
    this.destinationKind = destinationKind;
    this.destinationWindow = destinationWindow;
    this.xOffset = xOffset;
    this.yOffset = yOffset;
    this.sourceBitmap = sourceBitmap;
  }

  @java.lang.SuppressWarnings("all")
  public static Mask.MaskBuilder builder() {
    return new Mask.MaskBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getOperation() {
    return this.operation;
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

  @java.lang.SuppressWarnings("all")
  public int getSourceBitmap() {
    return this.sourceBitmap;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Mask)) return false;
    final Mask other = (Mask) o;
    if (this.getOperation() != other.getOperation()) return false;
    if (this.getDestinationKind() != other.getDestinationKind()) return false;
    if (this.getDestinationWindow() != other.getDestinationWindow()) return false;
    if (this.getXOffset() != other.getXOffset()) return false;
    if (this.getYOffset() != other.getYOffset()) return false;
    if (this.getSourceBitmap() != other.getSourceBitmap()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getOperation();
    result = result * PRIME + this.getDestinationKind();
    result = result * PRIME + this.getDestinationWindow();
    result = result * PRIME + this.getXOffset();
    result = result * PRIME + this.getYOffset();
    result = result * PRIME + this.getSourceBitmap();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Mask(operation=" + this.getOperation() + ", destinationKind=" + this.getDestinationKind() + ", destinationWindow=" + this.getDestinationWindow() + ", xOffset=" + this.getXOffset() + ", yOffset=" + this.getYOffset() + ", sourceBitmap=" + this.getSourceBitmap() + ")";
  }
}
