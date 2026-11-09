package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.Popcount;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.IntList;

public final class DeviceLedInfo implements XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final short ledClass;
  private final short ledID;
  private final int physIndicators;
  private final int state;
  private final IntList names;
  private final ImmutableList<IndicatorMap> maps;

  public static DeviceLedInfo readDeviceLedInfo(X11Input in) throws IOException {
    DeviceLedInfo.DeviceLedInfoBuilder javaBuilder = DeviceLedInfo.builder();
    short ledClass = in.readCard16();
    short ledID = in.readCard16();
    int namesPresent = in.readCard32();
    int mapsPresent = in.readCard32();
    int physIndicators = in.readCard32();
    int state = in.readCard32();
    IntList names = in.readCard32(Popcount.popcount(Integer.toUnsignedLong(namesPresent)));
    MutableList<IndicatorMap> maps = Lists.mutable.withInitialCapacity(Popcount.popcount(Integer.toUnsignedLong(mapsPresent)));
    for (int i = 0; i < Popcount.popcount(Integer.toUnsignedLong(mapsPresent)); i++) {
      maps.add(IndicatorMap.readIndicatorMap(in));
    }
    javaBuilder.ledClass(ledClass);
    javaBuilder.ledID(ledID);
    javaBuilder.physIndicators(physIndicators);
    javaBuilder.state(state);
    javaBuilder.names(names.toImmutable());
    javaBuilder.maps(maps.toImmutable());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard16(ledClass);
    out.writeCard16(ledID);
    int namesPresent = names.size();
    out.writeCard32(namesPresent);
    int mapsPresent = maps.size();
    out.writeCard32(mapsPresent);
    out.writeCard32(physIndicators);
    out.writeCard32(state);
    out.writeCard32(names);
    for (IndicatorMap t : maps) {
      t.write(out);
    }
  }

  @Override
  public int getSize() {
    return 20 + 4 * names.size() + XObject.sizeOf(maps);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class DeviceLedInfoBuilder {
    @java.lang.SuppressWarnings("all")
    private short ledClass;
    @java.lang.SuppressWarnings("all")
    private short ledID;
    @java.lang.SuppressWarnings("all")
    private int physIndicators;
    @java.lang.SuppressWarnings("all")
    private int state;
    @java.lang.SuppressWarnings("all")
    private IntList names;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<IndicatorMap> maps;

    public DeviceLedInfo.DeviceLedInfoBuilder ledClass(LedClass ledClass) {
      this.ledClass = (short) ledClass.getValue();
      return this;
    }

    public DeviceLedInfo.DeviceLedInfoBuilder ledClass(short ledClass) {
      this.ledClass = ledClass;
      return this;
    }

    public int getSize() {
      return 20 + 4 * names.size() + XObject.sizeOf(maps);
    }

    @java.lang.SuppressWarnings("all")
    DeviceLedInfoBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public DeviceLedInfo.DeviceLedInfoBuilder ledID(final short ledID) {
      this.ledID = ledID;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public DeviceLedInfo.DeviceLedInfoBuilder physIndicators(final int physIndicators) {
      this.physIndicators = physIndicators;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public DeviceLedInfo.DeviceLedInfoBuilder state(final int state) {
      this.state = state;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public DeviceLedInfo.DeviceLedInfoBuilder names(final IntList names) {
      if (names == null) {
        throw new java.lang.NullPointerException("names is marked non-null but is null");
      }
      this.names = names;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public DeviceLedInfo.DeviceLedInfoBuilder maps(final ImmutableList<IndicatorMap> maps) {
      if (maps == null) {
        throw new java.lang.NullPointerException("maps is marked non-null but is null");
      }
      this.maps = maps;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public DeviceLedInfo build() {
      return new DeviceLedInfo(this.ledClass, this.ledID, this.physIndicators, this.state, this.names, this.maps);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "DeviceLedInfo.DeviceLedInfoBuilder(ledClass=" + this.ledClass + ", ledID=" + this.ledID + ", physIndicators=" + this.physIndicators + ", state=" + this.state + ", names=" + this.names + ", maps=" + this.maps + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  DeviceLedInfo(final short ledClass, final short ledID, final int physIndicators, final int state, final IntList names, final ImmutableList<IndicatorMap> maps) {
    if (names == null) {
      throw new java.lang.NullPointerException("names is marked non-null but is null");
    }
    if (maps == null) {
      throw new java.lang.NullPointerException("maps is marked non-null but is null");
    }
    this.ledClass = ledClass;
    this.ledID = ledID;
    this.physIndicators = physIndicators;
    this.state = state;
    this.names = names;
    this.maps = maps;
  }

  @java.lang.SuppressWarnings("all")
  public static DeviceLedInfo.DeviceLedInfoBuilder builder() {
    return new DeviceLedInfo.DeviceLedInfoBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getLedClass() {
    return this.ledClass;
  }

  @java.lang.SuppressWarnings("all")
  public short getLedID() {
    return this.ledID;
  }

  @java.lang.SuppressWarnings("all")
  public int getPhysIndicators() {
    return this.physIndicators;
  }

  @java.lang.SuppressWarnings("all")
  public int getState() {
    return this.state;
  }

  @java.lang.SuppressWarnings("all")
  public IntList getNames() {
    return this.names;
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<IndicatorMap> getMaps() {
    return this.maps;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof DeviceLedInfo)) return false;
    final DeviceLedInfo other = (DeviceLedInfo) o;
    if (this.getLedClass() != other.getLedClass()) return false;
    if (this.getLedID() != other.getLedID()) return false;
    if (this.getPhysIndicators() != other.getPhysIndicators()) return false;
    if (this.getState() != other.getState()) return false;
    final java.lang.Object this$names = this.getNames();
    final java.lang.Object other$names = other.getNames();
    if (this$names == null ? other$names != null : !this$names.equals(other$names)) return false;
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
    result = result * PRIME + this.getLedClass();
    result = result * PRIME + this.getLedID();
    result = result * PRIME + this.getPhysIndicators();
    result = result * PRIME + this.getState();
    final java.lang.Object $names = this.getNames();
    result = result * PRIME + ($names == null ? 43 : $names.hashCode());
    final java.lang.Object $maps = this.getMaps();
    result = result * PRIME + ($maps == null ? 43 : $maps.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "DeviceLedInfo(ledClass=" + this.getLedClass() + ", ledID=" + this.getLedID() + ", physIndicators=" + this.getPhysIndicators() + ", state=" + this.getState() + ", names=" + this.getNames() + ", maps=" + this.getMaps() + ")";
  }
}
