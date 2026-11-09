package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class CopyRegion implements OneWayRequest {
  public static final String PLUGIN_NAME = "xfixes";
  public static final byte OPCODE = 12;
  private final int source;
  private final int destination;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CopyRegion readCopyRegion(X11Input in) throws IOException {
    CopyRegion.CopyRegionBuilder javaBuilder = CopyRegion.builder();
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


  public static class CopyRegionBuilder {
    @java.lang.SuppressWarnings("all")
    private int source;
    @java.lang.SuppressWarnings("all")
    private int destination;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    CopyRegionBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CopyRegion.CopyRegionBuilder source(final int source) {
      this.source = source;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CopyRegion.CopyRegionBuilder destination(final int destination) {
      this.destination = destination;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CopyRegion build() {
      return new CopyRegion(this.source, this.destination);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CopyRegion.CopyRegionBuilder(source=" + this.source + ", destination=" + this.destination + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CopyRegion(final int source, final int destination) {
    this.source = source;
    this.destination = destination;
  }

  @java.lang.SuppressWarnings("all")
  public static CopyRegion.CopyRegionBuilder builder() {
    return new CopyRegion.CopyRegionBuilder();
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
    if (!(o instanceof CopyRegion)) return false;
    final CopyRegion other = (CopyRegion) o;
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
    return "CopyRegion(source=" + this.getSource() + ", destination=" + this.getDestination() + ")";
  }
}
