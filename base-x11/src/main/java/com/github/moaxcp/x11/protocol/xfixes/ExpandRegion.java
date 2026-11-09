package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class ExpandRegion implements OneWayRequest {
  public static final String PLUGIN_NAME = "xfixes";
  public static final byte OPCODE = 28;
  private final int source;
  private final int destination;
  private final short left;
  private final short right;
  private final short top;
  private final short bottom;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ExpandRegion readExpandRegion(X11Input in) throws IOException {
    ExpandRegion.ExpandRegionBuilder javaBuilder = ExpandRegion.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int source = in.readCard32();
    int destination = in.readCard32();
    short left = in.readCard16();
    short right = in.readCard16();
    short top = in.readCard16();
    short bottom = in.readCard16();
    javaBuilder.source(source);
    javaBuilder.destination(destination);
    javaBuilder.left(left);
    javaBuilder.right(right);
    javaBuilder.top(top);
    javaBuilder.bottom(bottom);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(source);
    out.writeCard32(destination);
    out.writeCard16(left);
    out.writeCard16(right);
    out.writeCard16(top);
    out.writeCard16(bottom);
  }

  @Override
  public int getSize() {
    return 20;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ExpandRegionBuilder {
    @java.lang.SuppressWarnings("all")
    private int source;
    @java.lang.SuppressWarnings("all")
    private int destination;
    @java.lang.SuppressWarnings("all")
    private short left;
    @java.lang.SuppressWarnings("all")
    private short right;
    @java.lang.SuppressWarnings("all")
    private short top;
    @java.lang.SuppressWarnings("all")
    private short bottom;

    public int getSize() {
      return 20;
    }

    @java.lang.SuppressWarnings("all")
    ExpandRegionBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ExpandRegion.ExpandRegionBuilder source(final int source) {
      this.source = source;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ExpandRegion.ExpandRegionBuilder destination(final int destination) {
      this.destination = destination;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ExpandRegion.ExpandRegionBuilder left(final short left) {
      this.left = left;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ExpandRegion.ExpandRegionBuilder right(final short right) {
      this.right = right;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ExpandRegion.ExpandRegionBuilder top(final short top) {
      this.top = top;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ExpandRegion.ExpandRegionBuilder bottom(final short bottom) {
      this.bottom = bottom;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ExpandRegion build() {
      return new ExpandRegion(this.source, this.destination, this.left, this.right, this.top, this.bottom);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ExpandRegion.ExpandRegionBuilder(source=" + this.source + ", destination=" + this.destination + ", left=" + this.left + ", right=" + this.right + ", top=" + this.top + ", bottom=" + this.bottom + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ExpandRegion(final int source, final int destination, final short left, final short right, final short top, final short bottom) {
    this.source = source;
    this.destination = destination;
    this.left = left;
    this.right = right;
    this.top = top;
    this.bottom = bottom;
  }

  @java.lang.SuppressWarnings("all")
  public static ExpandRegion.ExpandRegionBuilder builder() {
    return new ExpandRegion.ExpandRegionBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getSource() {
    return this.source;
  }

  @java.lang.SuppressWarnings("all")
  public int getDestination() {
    return this.destination;
  }

  @java.lang.SuppressWarnings("all")
  public short getLeft() {
    return this.left;
  }

  @java.lang.SuppressWarnings("all")
  public short getRight() {
    return this.right;
  }

  @java.lang.SuppressWarnings("all")
  public short getTop() {
    return this.top;
  }

  @java.lang.SuppressWarnings("all")
  public short getBottom() {
    return this.bottom;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ExpandRegion)) return false;
    final ExpandRegion other = (ExpandRegion) o;
    if (this.getSource() != other.getSource()) return false;
    if (this.getDestination() != other.getDestination()) return false;
    if (this.getLeft() != other.getLeft()) return false;
    if (this.getRight() != other.getRight()) return false;
    if (this.getTop() != other.getTop()) return false;
    if (this.getBottom() != other.getBottom()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSource();
    result = result * PRIME + this.getDestination();
    result = result * PRIME + this.getLeft();
    result = result * PRIME + this.getRight();
    result = result * PRIME + this.getTop();
    result = result * PRIME + this.getBottom();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ExpandRegion(source=" + this.getSource() + ", destination=" + this.getDestination() + ", left=" + this.getLeft() + ", right=" + this.getRight() + ", top=" + this.getTop() + ", bottom=" + this.getBottom() + ")";
  }
}
