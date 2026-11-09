package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.Popcount;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public final class SetIndicatorMap implements OneWayRequest {
  public static final String PLUGIN_NAME = "xkb";
  public static final byte OPCODE = 14;
  private final short deviceSpec;
  private final ImmutableList<IndicatorMap> maps;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetIndicatorMap readSetIndicatorMap(X11Input in) throws IOException {
    SetIndicatorMap.SetIndicatorMapBuilder javaBuilder = SetIndicatorMap.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short deviceSpec = in.readCard16();
    byte[] pad4 = in.readPad(2);
    int which = in.readCard32();
    MutableList<IndicatorMap> maps = Lists.mutable.withInitialCapacity(Popcount.popcount(Integer.toUnsignedLong(which)));
    for (int i = 0; i < Popcount.popcount(Integer.toUnsignedLong(which)); i++) {
      maps.add(IndicatorMap.readIndicatorMap(in));
    }
    javaBuilder.deviceSpec(deviceSpec);
    javaBuilder.maps(maps.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(deviceSpec);
    out.writePad(2);
    int which = maps.size();
    out.writeCard32(which);
    for (IndicatorMap t : maps) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + XObject.sizeOf(maps);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetIndicatorMapBuilder {
    @java.lang.SuppressWarnings("all")
    private short deviceSpec;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<IndicatorMap> maps;

    public int getSize() {
      return 12 + XObject.sizeOf(maps);
    }

    @java.lang.SuppressWarnings("all")
    SetIndicatorMapBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetIndicatorMap.SetIndicatorMapBuilder deviceSpec(final short deviceSpec) {
      this.deviceSpec = deviceSpec;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetIndicatorMap.SetIndicatorMapBuilder maps(final ImmutableList<IndicatorMap> maps) {
      if (maps == null) {
        throw new java.lang.NullPointerException("maps is marked non-null but is null");
      }
      this.maps = maps;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetIndicatorMap build() {
      return new SetIndicatorMap(this.deviceSpec, this.maps);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetIndicatorMap.SetIndicatorMapBuilder(deviceSpec=" + this.deviceSpec + ", maps=" + this.maps + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetIndicatorMap(final short deviceSpec, final ImmutableList<IndicatorMap> maps) {
    if (maps == null) {
      throw new java.lang.NullPointerException("maps is marked non-null but is null");
    }
    this.deviceSpec = deviceSpec;
    this.maps = maps;
  }

  @java.lang.SuppressWarnings("all")
  public static SetIndicatorMap.SetIndicatorMapBuilder builder() {
    return new SetIndicatorMap.SetIndicatorMapBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getDeviceSpec() {
    return this.deviceSpec;
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<IndicatorMap> getMaps() {
    return this.maps;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetIndicatorMap)) return false;
    final SetIndicatorMap other = (SetIndicatorMap) o;
    if (this.getDeviceSpec() != other.getDeviceSpec()) return false;
    final java.lang.Object this$maps = this.getMaps();
    final java.lang.Object other$maps = other.getMaps();
    if (this$maps == null ? other$maps != null : !this$maps.equals(other$maps)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDeviceSpec();
    final java.lang.Object $maps = this.getMaps();
    result = result * PRIME + ($maps == null ? 43 : $maps.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetIndicatorMap(deviceSpec=" + this.getDeviceSpec() + ", maps=" + this.getMaps() + ")";
  }
}
