package com.github.moaxcp.x11.protocol.shape;

import java.util.ArrayList;
import java.util.List;
import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.xproto.ClipOrdering;
import com.github.moaxcp.x11.protocol.xproto.Rectangle;
import java.io.IOException;

public final class Rectangles implements OneWayRequest {
  public static final String PLUGIN_NAME = "shape";
  public static final byte OPCODE = 1;
  private final byte operation;
  private final byte destinationKind;
  private final byte ordering;
  private final int destinationWindow;
  private final short xOffset;
  private final short yOffset;
  private final List<Rectangle> rectangles;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Rectangles readRectangles(X11Input in) throws IOException {
    Rectangles.RectanglesBuilder javaBuilder = Rectangles.builder();
    int javaStart = 1;
    byte majorOpcode = in.readCard8();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    byte operation = in.readCard8();
    javaStart += 1;
    byte destinationKind = in.readCard8();
    javaStart += 1;
    byte ordering = in.readByte();
    javaStart += 1;
    byte[] pad6 = in.readPad(1);
    javaStart += 1;
    int destinationWindow = in.readCard32();
    javaStart += 4;
    short xOffset = in.readInt16();
    javaStart += 2;
    short yOffset = in.readInt16();
    javaStart += 2;
    List<Rectangle> rectangles = new ArrayList<>(Short.toUnsignedInt(length) - javaStart);
    while (javaStart < Short.toUnsignedInt(length) * 4) {
      Rectangle baseObject = Rectangle.readRectangle(in);
      rectangles.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.operation(operation);
    javaBuilder.destinationKind(destinationKind);
    javaBuilder.ordering(ordering);
    javaBuilder.destinationWindow(destinationWindow);
    javaBuilder.xOffset(xOffset);
    javaBuilder.yOffset(yOffset);
    javaBuilder.rectangles(List.copyOf(rectangles));
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard8(operation);
    out.writeCard8(destinationKind);
    out.writeByte(ordering);
    out.writePad(1);
    out.writeCard32(destinationWindow);
    out.writeInt16(xOffset);
    out.writeInt16(yOffset);
    for (Rectangle t : rectangles) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 16 + XObject.sizeOf(rectangles);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class RectanglesBuilder {
    @java.lang.SuppressWarnings("all")
    private byte operation;
    @java.lang.SuppressWarnings("all")
    private byte destinationKind;
    @java.lang.SuppressWarnings("all")
    private byte ordering;
    @java.lang.SuppressWarnings("all")
    private int destinationWindow;
    @java.lang.SuppressWarnings("all")
    private short xOffset;
    @java.lang.SuppressWarnings("all")
    private short yOffset;
    @java.lang.SuppressWarnings("all")
    private List<Rectangle> rectangles;

    public Rectangles.RectanglesBuilder operation(So operation) {
      this.operation = (byte) operation.getValue();
      return this;
    }

    public Rectangles.RectanglesBuilder operation(byte operation) {
      this.operation = operation;
      return this;
    }

    public Rectangles.RectanglesBuilder destinationKind(Sk destinationKind) {
      this.destinationKind = (byte) destinationKind.getValue();
      return this;
    }

    public Rectangles.RectanglesBuilder destinationKind(byte destinationKind) {
      this.destinationKind = destinationKind;
      return this;
    }

    public Rectangles.RectanglesBuilder ordering(ClipOrdering ordering) {
      this.ordering = (byte) ordering.getValue();
      return this;
    }

    public Rectangles.RectanglesBuilder ordering(byte ordering) {
      this.ordering = ordering;
      return this;
    }

    public int getSize() {
      return 16 + XObject.sizeOf(rectangles);
    }

    @java.lang.SuppressWarnings("all")
    RectanglesBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Rectangles.RectanglesBuilder destinationWindow(final int destinationWindow) {
      this.destinationWindow = destinationWindow;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Rectangles.RectanglesBuilder xOffset(final short xOffset) {
      this.xOffset = xOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Rectangles.RectanglesBuilder yOffset(final short yOffset) {
      this.yOffset = yOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Rectangles.RectanglesBuilder rectangles(final List<Rectangle> rectangles) {
      if (rectangles == null) {
        throw new java.lang.NullPointerException("rectangles is marked non-null but is null");
      }
      this.rectangles = rectangles;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Rectangles build() {
      return new Rectangles(this.operation, this.destinationKind, this.ordering, this.destinationWindow, this.xOffset, this.yOffset, this.rectangles);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Rectangles.RectanglesBuilder(operation=" + this.operation + ", destinationKind=" + this.destinationKind + ", ordering=" + this.ordering + ", destinationWindow=" + this.destinationWindow + ", xOffset=" + this.xOffset + ", yOffset=" + this.yOffset + ", rectangles=" + this.rectangles + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Rectangles(final byte operation, final byte destinationKind, final byte ordering, final int destinationWindow, final short xOffset, final short yOffset, final List<Rectangle> rectangles) {
    if (rectangles == null) {
      throw new java.lang.NullPointerException("rectangles is marked non-null but is null");
    }
    this.operation = operation;
    this.destinationKind = destinationKind;
    this.ordering = ordering;
    this.destinationWindow = destinationWindow;
    this.xOffset = xOffset;
    this.yOffset = yOffset;
    this.rectangles = rectangles;
  }

  @java.lang.SuppressWarnings("all")
  public static Rectangles.RectanglesBuilder builder() {
    return new Rectangles.RectanglesBuilder();
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
  public byte getOrdering() {
    return this.ordering;
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
  public List<Rectangle> getRectangles() {
    return this.rectangles;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Rectangles)) return false;
    final Rectangles other = (Rectangles) o;
    if (this.getOperation() != other.getOperation()) return false;
    if (this.getDestinationKind() != other.getDestinationKind()) return false;
    if (this.getOrdering() != other.getOrdering()) return false;
    if (this.getDestinationWindow() != other.getDestinationWindow()) return false;
    if (this.getXOffset() != other.getXOffset()) return false;
    if (this.getYOffset() != other.getYOffset()) return false;
    final java.lang.Object this$rectangles = this.getRectangles();
    final java.lang.Object other$rectangles = other.getRectangles();
    if (this$rectangles == null ? other$rectangles != null : !this$rectangles.equals(other$rectangles)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getOperation();
    result = result * PRIME + this.getDestinationKind();
    result = result * PRIME + this.getOrdering();
    result = result * PRIME + this.getDestinationWindow();
    result = result * PRIME + this.getXOffset();
    result = result * PRIME + this.getYOffset();
    final java.lang.Object $rectangles = this.getRectangles();
    result = result * PRIME + ($rectangles == null ? 43 : $rectangles.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Rectangles(operation=" + this.getOperation() + ", destinationKind=" + this.getDestinationKind() + ", ordering=" + this.getOrdering() + ", destinationWindow=" + this.getDestinationWindow() + ", xOffset=" + this.getXOffset() + ", yOffset=" + this.getYOffset() + ", rectangles=" + this.getRectangles() + ")";
  }
}
