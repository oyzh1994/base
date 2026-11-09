package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class RegionExtents implements OneWayRequest {
  public static final String PLUGIN_NAME = "xfixes";
  public static final byte OPCODE = 18;
  private final int source;
  private final int destination;

  public byte getOpCode() {
    return OPCODE;
  }

  public static RegionExtents readRegionExtents(X11Input in) throws IOException {
    RegionExtents.RegionExtentsBuilder javaBuilder = RegionExtents.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int source = in.readCard32();
    int destination = in.readCard32();
    javaBuilder.source(source);
    javaBuilder.destination(destination);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(source);
    out.writeCard32(destination);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class RegionExtentsBuilder {
    @java.lang.SuppressWarnings("all")
    private int source;
    @java.lang.SuppressWarnings("all")
    private int destination;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    RegionExtentsBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RegionExtents.RegionExtentsBuilder source(final int source) {
      this.source = source;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RegionExtents.RegionExtentsBuilder destination(final int destination) {
      this.destination = destination;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public RegionExtents build() {
      return new RegionExtents(this.source, this.destination);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "RegionExtents.RegionExtentsBuilder(source=" + this.source + ", destination=" + this.destination + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  RegionExtents(final int source, final int destination) {
    this.source = source;
    this.destination = destination;
  }

  @java.lang.SuppressWarnings("all")
  public static RegionExtents.RegionExtentsBuilder builder() {
    return new RegionExtents.RegionExtentsBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getSource() {
    return this.source;
  }

  @java.lang.SuppressWarnings("all")
  public int getDestination() {
    return this.destination;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof RegionExtents)) return false;
    final RegionExtents other = (RegionExtents) o;
    if (this.getSource() != other.getSource()) return false;
    if (this.getDestination() != other.getDestination()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSource();
    result = result * PRIME + this.getDestination();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "RegionExtents(source=" + this.getSource() + ", destination=" + this.getDestination() + ")";
  }
}
