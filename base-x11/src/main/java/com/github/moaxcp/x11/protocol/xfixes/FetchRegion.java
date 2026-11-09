package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class FetchRegion implements TwoWayRequest<FetchRegionReply> {
  public static final String PLUGIN_NAME = "xfixes";
  public static final byte OPCODE = 19;
  private final int region;

  public XReplyFunction<FetchRegionReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> FetchRegionReply.readFetchRegionReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static FetchRegion readFetchRegion(X11Input in) throws IOException {
    FetchRegion.FetchRegionBuilder javaBuilder = FetchRegion.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int region = in.readCard32();
    javaBuilder.region(region);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(region);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class FetchRegionBuilder {
    @java.lang.SuppressWarnings("all")
    private int region;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    FetchRegionBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public FetchRegion.FetchRegionBuilder region(final int region) {
      this.region = region;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public FetchRegion build() {
      return new FetchRegion(this.region);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "FetchRegion.FetchRegionBuilder(region=" + this.region + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  FetchRegion(final int region) {
    this.region = region;
  }

  @java.lang.SuppressWarnings("all")
  public static FetchRegion.FetchRegionBuilder builder() {
    return new FetchRegion.FetchRegionBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getRegion() {
    return this.region;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof FetchRegion)) return false;
    final FetchRegion other = (FetchRegion) o;
    if (this.getRegion() != other.getRegion()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getRegion();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "FetchRegion(region=" + this.getRegion() + ")";
  }
}
