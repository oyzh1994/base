package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class UnionRegion implements OneWayRequest {
  public static final String PLUGIN_NAME = "xfixes";
  public static final byte OPCODE = 13;
  private final int source1;
  private final int source2;
  private final int destination;

  public byte getOpCode() {
    return OPCODE;
  }

  public static UnionRegion readUnionRegion(X11Input in) throws IOException {
    UnionRegion.UnionRegionBuilder javaBuilder = UnionRegion.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int source1 = in.readCard32();
    int source2 = in.readCard32();
    int destination = in.readCard32();
    javaBuilder.source1(source1);
    javaBuilder.source2(source2);
    javaBuilder.destination(destination);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(source1);
    out.writeCard32(source2);
    out.writeCard32(destination);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class UnionRegionBuilder {
    @java.lang.SuppressWarnings("all")
    private int source1;
    @java.lang.SuppressWarnings("all")
    private int source2;
    @java.lang.SuppressWarnings("all")
    private int destination;

    public int getSize() {
      return 16;
    }

    @java.lang.SuppressWarnings("all")
    UnionRegionBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public UnionRegion.UnionRegionBuilder source1(final int source1) {
      this.source1 = source1;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public UnionRegion.UnionRegionBuilder source2(final int source2) {
      this.source2 = source2;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public UnionRegion.UnionRegionBuilder destination(final int destination) {
      this.destination = destination;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public UnionRegion build() {
      return new UnionRegion(this.source1, this.source2, this.destination);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "UnionRegion.UnionRegionBuilder(source1=" + this.source1 + ", source2=" + this.source2 + ", destination=" + this.destination + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  UnionRegion(final int source1, final int source2, final int destination) {
    this.source1 = source1;
    this.source2 = source2;
    this.destination = destination;
  }

  @java.lang.SuppressWarnings("all")
  public static UnionRegion.UnionRegionBuilder builder() {
    return new UnionRegion.UnionRegionBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getSource1() {
    return this.source1;
  }

  @java.lang.SuppressWarnings("all")
  public int getSource2() {
    return this.source2;
  }

  @java.lang.SuppressWarnings("all")
  public int getDestination() {
    return this.destination;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof UnionRegion)) return false;
    final UnionRegion other = (UnionRegion) o;
    if (this.getSource1() != other.getSource1()) return false;
    if (this.getSource2() != other.getSource2()) return false;
    if (this.getDestination() != other.getDestination()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSource1();
    result = result * PRIME + this.getSource2();
    result = result * PRIME + this.getDestination();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "UnionRegion(source1=" + this.getSource1() + ", source2=" + this.getSource2() + ", destination=" + this.getDestination() + ")";
  }
}
