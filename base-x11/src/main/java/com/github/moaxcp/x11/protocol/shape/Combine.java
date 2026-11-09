package com.github.moaxcp.x11.protocol.shape;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class Combine implements OneWayRequest {
  public static final String PLUGIN_NAME = "shape";
  public static final byte OPCODE = 3;
  private final byte operation;
  private final byte destinationKind;
  private final byte sourceKind;
  private final int destinationWindow;
  private final short xOffset;
  private final short yOffset;
  private final int sourceWindow;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Combine readCombine(X11Input in) throws IOException {
    Combine.CombineBuilder javaBuilder = Combine.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    byte operation = in.readCard8();
    byte destinationKind = in.readCard8();
    byte sourceKind = in.readCard8();
    byte[] pad6 = in.readPad(1);
    int destinationWindow = in.readCard32();
    short xOffset = in.readInt16();
    short yOffset = in.readInt16();
    int sourceWindow = in.readCard32();
    javaBuilder.operation(operation);
    javaBuilder.destinationKind(destinationKind);
    javaBuilder.sourceKind(sourceKind);
    javaBuilder.destinationWindow(destinationWindow);
    javaBuilder.xOffset(xOffset);
    javaBuilder.yOffset(yOffset);
    javaBuilder.sourceWindow(sourceWindow);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard8(operation);
    out.writeCard8(destinationKind);
    out.writeCard8(sourceKind);
    out.writePad(1);
    out.writeCard32(destinationWindow);
    out.writeInt16(xOffset);
    out.writeInt16(yOffset);
    out.writeCard32(sourceWindow);
  }

  @Override
  public int getSize() {
    return 20;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CombineBuilder {
    @java.lang.SuppressWarnings("all")
    private byte operation;
    @java.lang.SuppressWarnings("all")
    private byte destinationKind;
    @java.lang.SuppressWarnings("all")
    private byte sourceKind;
    @java.lang.SuppressWarnings("all")
    private int destinationWindow;
    @java.lang.SuppressWarnings("all")
    private short xOffset;
    @java.lang.SuppressWarnings("all")
    private short yOffset;
    @java.lang.SuppressWarnings("all")
    private int sourceWindow;

    public Combine.CombineBuilder operation(So operation) {
      this.operation = (byte) operation.getValue();
      return this;
    }

    public Combine.CombineBuilder operation(byte operation) {
      this.operation = operation;
      return this;
    }

    public Combine.CombineBuilder destinationKind(Sk destinationKind) {
      this.destinationKind = (byte) destinationKind.getValue();
      return this;
    }

    public Combine.CombineBuilder destinationKind(byte destinationKind) {
      this.destinationKind = destinationKind;
      return this;
    }

    public Combine.CombineBuilder sourceKind(Sk sourceKind) {
      this.sourceKind = (byte) sourceKind.getValue();
      return this;
    }

    public Combine.CombineBuilder sourceKind(byte sourceKind) {
      this.sourceKind = sourceKind;
      return this;
    }

    public int getSize() {
      return 20;
    }

    @java.lang.SuppressWarnings("all")
    CombineBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Combine.CombineBuilder destinationWindow(final int destinationWindow) {
      this.destinationWindow = destinationWindow;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Combine.CombineBuilder xOffset(final short xOffset) {
      this.xOffset = xOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Combine.CombineBuilder yOffset(final short yOffset) {
      this.yOffset = yOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Combine.CombineBuilder sourceWindow(final int sourceWindow) {
      this.sourceWindow = sourceWindow;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Combine build() {
      return new Combine(this.operation, this.destinationKind, this.sourceKind, this.destinationWindow, this.xOffset, this.yOffset, this.sourceWindow);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Combine.CombineBuilder(operation=" + this.operation + ", destinationKind=" + this.destinationKind + ", sourceKind=" + this.sourceKind + ", destinationWindow=" + this.destinationWindow + ", xOffset=" + this.xOffset + ", yOffset=" + this.yOffset + ", sourceWindow=" + this.sourceWindow + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Combine(final byte operation, final byte destinationKind, final byte sourceKind, final int destinationWindow, final short xOffset, final short yOffset, final int sourceWindow) {
    this.operation = operation;
    this.destinationKind = destinationKind;
    this.sourceKind = sourceKind;
    this.destinationWindow = destinationWindow;
    this.xOffset = xOffset;
    this.yOffset = yOffset;
    this.sourceWindow = sourceWindow;
  }

  @java.lang.SuppressWarnings("all")
  public static Combine.CombineBuilder builder() {
    return new Combine.CombineBuilder();
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
  public byte getSourceKind() {
    return this.sourceKind;
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
  public int getSourceWindow() {
    return this.sourceWindow;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Combine)) return false;
    final Combine other = (Combine) o;
    if (this.getOperation() != other.getOperation()) return false;
    if (this.getDestinationKind() != other.getDestinationKind()) return false;
    if (this.getSourceKind() != other.getSourceKind()) return false;
    if (this.getDestinationWindow() != other.getDestinationWindow()) return false;
    if (this.getXOffset() != other.getXOffset()) return false;
    if (this.getYOffset() != other.getYOffset()) return false;
    if (this.getSourceWindow() != other.getSourceWindow()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getOperation();
    result = result * PRIME + this.getDestinationKind();
    result = result * PRIME + this.getSourceKind();
    result = result * PRIME + this.getDestinationWindow();
    result = result * PRIME + this.getXOffset();
    result = result * PRIME + this.getYOffset();
    result = result * PRIME + this.getSourceWindow();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Combine(operation=" + this.getOperation() + ", destinationKind=" + this.getDestinationKind() + ", sourceKind=" + this.getSourceKind() + ", destinationWindow=" + this.getDestinationWindow() + ", xOffset=" + this.getXOffset() + ", yOffset=" + this.getYOffset() + ", sourceWindow=" + this.getSourceWindow() + ")";
  }
}
