package com.github.moaxcp.x11.protocol.xkb;

import java.util.ArrayList;
import java.util.List;
import com.github.moaxcp.x11.protocol.Popcount;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class GetIndicatorMapReply implements XReply {
  public static final String PLUGIN_NAME = "xkb";
  private final byte deviceID;
  private final short sequenceNumber;
  private final int realIndicators;
  private final byte nIndicators;
  private final List<IndicatorMap> maps;

  public static GetIndicatorMapReply readGetIndicatorMapReply(byte deviceID, short sequenceNumber, X11Input in) throws IOException {
    GetIndicatorMapReply.GetIndicatorMapReplyBuilder javaBuilder = GetIndicatorMapReply.builder();
    int length = in.readCard32();
    int which = in.readCard32();
    int realIndicators = in.readCard32();
    byte nIndicators = in.readCard8();
    byte[] pad7 = in.readPad(15);
    List<IndicatorMap> maps = new ArrayList<>(Popcount.popcount(Integer.toUnsignedLong(which)));
    for (int i = 0; i < Popcount.popcount(Integer.toUnsignedLong(which)); i++) {
      maps.add(IndicatorMap.readIndicatorMap(in));
    }
    javaBuilder.deviceID(deviceID);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.realIndicators(realIndicators);
    javaBuilder.nIndicators(nIndicators);
    javaBuilder.maps(List.copyOf(maps));
    if (javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(deviceID);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    int which = maps.size();
    out.writeCard32(which);
    out.writeCard32(realIndicators);
    out.writeCard8(nIndicators);
    out.writePad(15);
    for (IndicatorMap t : maps) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(maps);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetIndicatorMapReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte deviceID;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int realIndicators;
    @java.lang.SuppressWarnings("all")
    private byte nIndicators;
    @java.lang.SuppressWarnings("all")
    private List<IndicatorMap> maps;

    public int getSize() {
      return 32 + XObject.sizeOf(maps);
    }

    @java.lang.SuppressWarnings("all")
    GetIndicatorMapReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetIndicatorMapReply.GetIndicatorMapReplyBuilder deviceID(final byte deviceID) {
      this.deviceID = deviceID;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetIndicatorMapReply.GetIndicatorMapReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetIndicatorMapReply.GetIndicatorMapReplyBuilder realIndicators(final int realIndicators) {
      this.realIndicators = realIndicators;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetIndicatorMapReply.GetIndicatorMapReplyBuilder nIndicators(final byte nIndicators) {
      this.nIndicators = nIndicators;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetIndicatorMapReply.GetIndicatorMapReplyBuilder maps(final List<IndicatorMap> maps) {
      if (maps == null) {
        throw new java.lang.NullPointerException("maps is marked non-null but is null");
      }
      this.maps = maps;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetIndicatorMapReply build() {
      return new GetIndicatorMapReply(this.deviceID, this.sequenceNumber, this.realIndicators, this.nIndicators, this.maps);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetIndicatorMapReply.GetIndicatorMapReplyBuilder(deviceID=" + this.deviceID + ", sequenceNumber=" + this.sequenceNumber + ", realIndicators=" + this.realIndicators + ", nIndicators=" + this.nIndicators + ", maps=" + this.maps + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetIndicatorMapReply(final byte deviceID, final short sequenceNumber, final int realIndicators, final byte nIndicators, final List<IndicatorMap> maps) {
    if (maps == null) {
      throw new java.lang.NullPointerException("maps is marked non-null but is null");
    }
    this.deviceID = deviceID;
    this.sequenceNumber = sequenceNumber;
    this.realIndicators = realIndicators;
    this.nIndicators = nIndicators;
    this.maps = maps;
  }

  @java.lang.SuppressWarnings("all")
  public static GetIndicatorMapReply.GetIndicatorMapReplyBuilder builder() {
    return new GetIndicatorMapReply.GetIndicatorMapReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getDeviceID() {
    return this.deviceID;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getRealIndicators() {
    return this.realIndicators;
  }

  @java.lang.SuppressWarnings("all")
  public byte getNIndicators() {
    return this.nIndicators;
  }

  @java.lang.SuppressWarnings("all")
  public List<IndicatorMap> getMaps() {
    return this.maps;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetIndicatorMapReply)) return false;
    final GetIndicatorMapReply other = (GetIndicatorMapReply) o;
    if (this.getDeviceID() != other.getDeviceID()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getRealIndicators() != other.getRealIndicators()) return false;
    if (this.getNIndicators() != other.getNIndicators()) return false;
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
    result = result * PRIME + this.getDeviceID();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getRealIndicators();
    result = result * PRIME + this.getNIndicators();
    final java.lang.Object $maps = this.getMaps();
    result = result * PRIME + ($maps == null ? 43 : $maps.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetIndicatorMapReply(deviceID=" + this.getDeviceID() + ", sequenceNumber=" + this.getSequenceNumber() + ", realIndicators=" + this.getRealIndicators() + ", nIndicators=" + this.getNIndicators() + ", maps=" + this.getMaps() + ")";
  }
}
