package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class SetGCClipRegion implements OneWayRequest {
  public static final String PLUGIN_NAME = "xfixes";
  public static final byte OPCODE = 20;
  private final int gc;
  private final int region;
  private final short xOrigin;
  private final short yOrigin;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetGCClipRegion readSetGCClipRegion(X11Input in) throws IOException {
    SetGCClipRegion.SetGCClipRegionBuilder javaBuilder = SetGCClipRegion.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int gc = in.readCard32();
    int region = in.readCard32();
    short xOrigin = in.readInt16();
    short yOrigin = in.readInt16();
    javaBuilder.gc(gc);
    javaBuilder.region(region);
    javaBuilder.xOrigin(xOrigin);
    javaBuilder.yOrigin(yOrigin);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(gc);
    out.writeCard32(region);
    out.writeInt16(xOrigin);
    out.writeInt16(yOrigin);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetGCClipRegionBuilder {
    @java.lang.SuppressWarnings("all")
    private int gc;
    @java.lang.SuppressWarnings("all")
    private int region;
    @java.lang.SuppressWarnings("all")
    private short xOrigin;
    @java.lang.SuppressWarnings("all")
    private short yOrigin;

    public int getSize() {
      return 16;
    }

    @java.lang.SuppressWarnings("all")
    SetGCClipRegionBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetGCClipRegion.SetGCClipRegionBuilder gc(final int gc) {
      this.gc = gc;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetGCClipRegion.SetGCClipRegionBuilder region(final int region) {
      this.region = region;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetGCClipRegion.SetGCClipRegionBuilder xOrigin(final short xOrigin) {
      this.xOrigin = xOrigin;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetGCClipRegion.SetGCClipRegionBuilder yOrigin(final short yOrigin) {
      this.yOrigin = yOrigin;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetGCClipRegion build() {
      return new SetGCClipRegion(this.gc, this.region, this.xOrigin, this.yOrigin);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetGCClipRegion.SetGCClipRegionBuilder(gc=" + this.gc + ", region=" + this.region + ", xOrigin=" + this.xOrigin + ", yOrigin=" + this.yOrigin + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetGCClipRegion(final int gc, final int region, final short xOrigin, final short yOrigin) {
    this.gc = gc;
    this.region = region;
    this.xOrigin = xOrigin;
    this.yOrigin = yOrigin;
  }

  @java.lang.SuppressWarnings("all")
  public static SetGCClipRegion.SetGCClipRegionBuilder builder() {
    return new SetGCClipRegion.SetGCClipRegionBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getGc() {
    return this.gc;
  }

  @java.lang.SuppressWarnings("all")
  public int getRegion() {
    return this.region;
  }

  @java.lang.SuppressWarnings("all")
  public short getXOrigin() {
    return this.xOrigin;
  }

  @java.lang.SuppressWarnings("all")
  public short getYOrigin() {
    return this.yOrigin;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetGCClipRegion)) return false;
    final SetGCClipRegion other = (SetGCClipRegion) o;
    if (this.getGc() != other.getGc()) return false;
    if (this.getRegion() != other.getRegion()) return false;
    if (this.getXOrigin() != other.getXOrigin()) return false;
    if (this.getYOrigin() != other.getYOrigin()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getGc();
    result = result * PRIME + this.getRegion();
    result = result * PRIME + this.getXOrigin();
    result = result * PRIME + this.getYOrigin();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetGCClipRegion(gc=" + this.getGc() + ", region=" + this.getRegion() + ", xOrigin=" + this.getXOrigin() + ", yOrigin=" + this.getYOrigin() + ")";
  }
}
