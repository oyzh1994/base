package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.shape.Sk;
import java.io.IOException;

public final class SetWindowShapeRegion implements OneWayRequest {
  public static final String PLUGIN_NAME = "xfixes";
  public static final byte OPCODE = 21;
  private final int dest;
  private final byte destKind;
  private final short xOffset;
  private final short yOffset;
  private final int region;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetWindowShapeRegion readSetWindowShapeRegion(X11Input in) throws IOException {
    SetWindowShapeRegion.SetWindowShapeRegionBuilder javaBuilder = SetWindowShapeRegion.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int dest = in.readCard32();
    byte destKind = in.readCard8();
    byte[] pad5 = in.readPad(3);
    short xOffset = in.readInt16();
    short yOffset = in.readInt16();
    int region = in.readCard32();
    javaBuilder.dest(dest);
    javaBuilder.destKind(destKind);
    javaBuilder.xOffset(xOffset);
    javaBuilder.yOffset(yOffset);
    javaBuilder.region(region);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(dest);
    out.writeCard8(destKind);
    out.writePad(3);
    out.writeInt16(xOffset);
    out.writeInt16(yOffset);
    out.writeCard32(region);
  }

  @Override
  public int getSize() {
    return 20;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetWindowShapeRegionBuilder {
    @java.lang.SuppressWarnings("all")
    private int dest;
    @java.lang.SuppressWarnings("all")
    private byte destKind;
    @java.lang.SuppressWarnings("all")
    private short xOffset;
    @java.lang.SuppressWarnings("all")
    private short yOffset;
    @java.lang.SuppressWarnings("all")
    private int region;

    public SetWindowShapeRegion.SetWindowShapeRegionBuilder destKind(Sk destKind) {
      this.destKind = (byte) destKind.getValue();
      return this;
    }

    public SetWindowShapeRegion.SetWindowShapeRegionBuilder destKind(byte destKind) {
      this.destKind = destKind;
      return this;
    }

    public int getSize() {
      return 20;
    }

    @java.lang.SuppressWarnings("all")
    SetWindowShapeRegionBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetWindowShapeRegion.SetWindowShapeRegionBuilder dest(final int dest) {
      this.dest = dest;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetWindowShapeRegion.SetWindowShapeRegionBuilder xOffset(final short xOffset) {
      this.xOffset = xOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetWindowShapeRegion.SetWindowShapeRegionBuilder yOffset(final short yOffset) {
      this.yOffset = yOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetWindowShapeRegion.SetWindowShapeRegionBuilder region(final int region) {
      this.region = region;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetWindowShapeRegion build() {
      return new SetWindowShapeRegion(this.dest, this.destKind, this.xOffset, this.yOffset, this.region);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetWindowShapeRegion.SetWindowShapeRegionBuilder(dest=" + this.dest + ", destKind=" + this.destKind + ", xOffset=" + this.xOffset + ", yOffset=" + this.yOffset + ", region=" + this.region + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetWindowShapeRegion(final int dest, final byte destKind, final short xOffset, final short yOffset, final int region) {
    this.dest = dest;
    this.destKind = destKind;
    this.xOffset = xOffset;
    this.yOffset = yOffset;
    this.region = region;
  }

  @java.lang.SuppressWarnings("all")
  public static SetWindowShapeRegion.SetWindowShapeRegionBuilder builder() {
    return new SetWindowShapeRegion.SetWindowShapeRegionBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getDest() {
    return this.dest;
  }

  @java.lang.SuppressWarnings("all")
  public byte getDestKind() {
    return this.destKind;
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
  public int getRegion() {
    return this.region;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetWindowShapeRegion)) return false;
    final SetWindowShapeRegion other = (SetWindowShapeRegion) o;
    if (this.getDest() != other.getDest()) return false;
    if (this.getDestKind() != other.getDestKind()) return false;
    if (this.getXOffset() != other.getXOffset()) return false;
    if (this.getYOffset() != other.getYOffset()) return false;
    if (this.getRegion() != other.getRegion()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDest();
    result = result * PRIME + this.getDestKind();
    result = result * PRIME + this.getXOffset();
    result = result * PRIME + this.getYOffset();
    result = result * PRIME + this.getRegion();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetWindowShapeRegion(dest=" + this.getDest() + ", destKind=" + this.getDestKind() + ", xOffset=" + this.getXOffset() + ", yOffset=" + this.getYOffset() + ", region=" + this.getRegion() + ")";
  }
}
