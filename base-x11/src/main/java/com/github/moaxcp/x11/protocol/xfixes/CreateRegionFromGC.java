package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class CreateRegionFromGC implements OneWayRequest {
  public static final String PLUGIN_NAME = "xfixes";
  public static final byte OPCODE = 8;
  private final int region;
  private final int gc;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateRegionFromGC readCreateRegionFromGC(X11Input in) throws IOException {
    CreateRegionFromGC.CreateRegionFromGCBuilder javaBuilder = CreateRegionFromGC.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int region = in.readCard32();
    int gc = in.readCard32();
    javaBuilder.region(region);
    javaBuilder.gc(gc);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(region);
    out.writeCard32(gc);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CreateRegionFromGCBuilder {
    @java.lang.SuppressWarnings("all")
    private int region;
    @java.lang.SuppressWarnings("all")
    private int gc;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    CreateRegionFromGCBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateRegionFromGC.CreateRegionFromGCBuilder region(final int region) {
      this.region = region;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateRegionFromGC.CreateRegionFromGCBuilder gc(final int gc) {
      this.gc = gc;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CreateRegionFromGC build() {
      return new CreateRegionFromGC(this.region, this.gc);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CreateRegionFromGC.CreateRegionFromGCBuilder(region=" + this.region + ", gc=" + this.gc + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CreateRegionFromGC(final int region, final int gc) {
    this.region = region;
    this.gc = gc;
  }

  @java.lang.SuppressWarnings("all")
  public static CreateRegionFromGC.CreateRegionFromGCBuilder builder() {
    return new CreateRegionFromGC.CreateRegionFromGCBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getRegion() {
    return this.region;
  }

  @java.lang.SuppressWarnings("all")
  public int getGc() {
    return this.gc;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CreateRegionFromGC)) return false;
    final CreateRegionFromGC other = (CreateRegionFromGC) o;
    if (this.getRegion() != other.getRegion()) return false;
    if (this.getGc() != other.getGc()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getRegion();
    result = result * PRIME + this.getGc();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CreateRegionFromGC(region=" + this.getRegion() + ", gc=" + this.getGc() + ")";
  }
}
