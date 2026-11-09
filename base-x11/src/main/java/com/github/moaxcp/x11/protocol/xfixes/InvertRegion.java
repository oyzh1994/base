package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.xproto.Rectangle;
import java.io.IOException;

public final class InvertRegion implements OneWayRequest {
  public static final String PLUGIN_NAME = "xfixes";
  public static final byte OPCODE = 16;
  private final int source;
  private final Rectangle bounds;
  private final int destination;

  public byte getOpCode() {
    return OPCODE;
  }

  public static InvertRegion readInvertRegion(X11Input in) throws IOException {
    InvertRegion.InvertRegionBuilder javaBuilder = InvertRegion.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int source = in.readCard32();
    Rectangle bounds = Rectangle.readRectangle(in);
    int destination = in.readCard32();
    javaBuilder.source(source);
    javaBuilder.bounds(bounds);
    javaBuilder.destination(destination);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(source);
    bounds.write(out);
    out.writeCard32(destination);
  }

  @Override
  public int getSize() {
    return 20;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class InvertRegionBuilder {
    @java.lang.SuppressWarnings("all")
    private int source;
    @java.lang.SuppressWarnings("all")
    private Rectangle bounds;
    @java.lang.SuppressWarnings("all")
    private int destination;

    public int getSize() {
      return 20;
    }

    @java.lang.SuppressWarnings("all")
    InvertRegionBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public InvertRegion.InvertRegionBuilder source(final int source) {
      this.source = source;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public InvertRegion.InvertRegionBuilder bounds(final Rectangle bounds) {
      if (bounds == null) {
        throw new java.lang.NullPointerException("bounds is marked non-null but is null");
      }
      this.bounds = bounds;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public InvertRegion.InvertRegionBuilder destination(final int destination) {
      this.destination = destination;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public InvertRegion build() {
      return new InvertRegion(this.source, this.bounds, this.destination);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "InvertRegion.InvertRegionBuilder(source=" + this.source + ", bounds=" + this.bounds + ", destination=" + this.destination + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  InvertRegion(final int source, final Rectangle bounds, final int destination) {
    if (bounds == null) {
      throw new java.lang.NullPointerException("bounds is marked non-null but is null");
    }
    this.source = source;
    this.bounds = bounds;
    this.destination = destination;
  }

  @java.lang.SuppressWarnings("all")
  public static InvertRegion.InvertRegionBuilder builder() {
    return new InvertRegion.InvertRegionBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getSource() {
    return this.source;
  }

  @java.lang.SuppressWarnings("all")
  public Rectangle getBounds() {
    return this.bounds;
  }

  @java.lang.SuppressWarnings("all")
  public int getDestination() {
    return this.destination;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof InvertRegion)) return false;
    final InvertRegion other = (InvertRegion) o;
    if (this.getSource() != other.getSource()) return false;
    if (this.getDestination() != other.getDestination()) return false;
    final java.lang.Object this$bounds = this.getBounds();
    final java.lang.Object other$bounds = other.getBounds();
    if (this$bounds == null ? other$bounds != null : !this$bounds.equals(other$bounds)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSource();
    result = result * PRIME + this.getDestination();
    final java.lang.Object $bounds = this.getBounds();
    result = result * PRIME + ($bounds == null ? 43 : $bounds.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "InvertRegion(source=" + this.getSource() + ", bounds=" + this.getBounds() + ", destination=" + this.getDestination() + ")";
  }
}
