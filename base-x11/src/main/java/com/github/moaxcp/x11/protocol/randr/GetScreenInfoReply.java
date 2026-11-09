package com.github.moaxcp.x11.protocol.randr;

import java.util.ArrayList;
import java.util.List;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class GetScreenInfoReply implements XReply {
  public static final String PLUGIN_NAME = "randr";
  private final byte rotations;
  private final short sequenceNumber;
  private final int root;
  private final int timestamp;
  private final int configTimestamp;
  private final short sizeID;
  private final short rotation;
  private final short rate;
  private final short nInfo;
  private final List<ScreenSize> sizes;
  private final List<RefreshRates> rates;

  public static GetScreenInfoReply readGetScreenInfoReply(byte rotations, short sequenceNumber, X11Input in) throws IOException {
    GetScreenInfoReply.GetScreenInfoReplyBuilder javaBuilder = GetScreenInfoReply.builder();
    int length = in.readCard32();
    int root = in.readCard32();
    int timestamp = in.readCard32();
    int configTimestamp = in.readCard32();
    short nSizes = in.readCard16();
    short sizeID = in.readCard16();
    short rotation = in.readCard16();
    short rate = in.readCard16();
    short nInfo = in.readCard16();
    byte[] pad12 = in.readPad(2);
    List<ScreenSize> sizes = new ArrayList<>(Short.toUnsignedInt(nSizes));
    for (int i = 0; i < Short.toUnsignedInt(nSizes); i++) {
      sizes.add(ScreenSize.readScreenSize(in));
    }
    List<RefreshRates> rates = new ArrayList<>(Short.toUnsignedInt(nInfo) - Short.toUnsignedInt(nSizes));
    for (int i = 0; i < Short.toUnsignedInt(nInfo) - Short.toUnsignedInt(nSizes); i++) {
      rates.add(RefreshRates.readRefreshRates(in));
    }
    javaBuilder.rotations(rotations);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.root(root);
    javaBuilder.timestamp(timestamp);
    javaBuilder.configTimestamp(configTimestamp);
    javaBuilder.sizeID(sizeID);
    javaBuilder.rotation(rotation);
    javaBuilder.rate(rate);
    javaBuilder.nInfo(nInfo);
    javaBuilder.sizes(List.copyOf(sizes));
    javaBuilder.rates(List.copyOf(rates));
    if (javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(rotations);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(root);
    out.writeCard32(timestamp);
    out.writeCard32(configTimestamp);
    short nSizes = (short) sizes.size();
    out.writeCard16(nSizes);
    out.writeCard16(sizeID);
    out.writeCard16(rotation);
    out.writeCard16(rate);
    out.writeCard16(nInfo);
    out.writePad(2);
    for (ScreenSize t : sizes) {
      t.write(out);
    }
    for (RefreshRates t : rates) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  public boolean isRotationsEnabled(Rotation... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (Rotation m : maskEnums) {
      if (!m.isEnabled(rotations)) {
        return false;
      }
    }
    return true;
  }

  public boolean isRotationEnabled(Rotation... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (Rotation m : maskEnums) {
      if (!m.isEnabled(rotation)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(sizes) + XObject.sizeOf(rates);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetScreenInfoReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte rotations;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int root;
    @java.lang.SuppressWarnings("all")
    private int timestamp;
    @java.lang.SuppressWarnings("all")
    private int configTimestamp;
    @java.lang.SuppressWarnings("all")
    private short sizeID;
    @java.lang.SuppressWarnings("all")
    private short rotation;
    @java.lang.SuppressWarnings("all")
    private short rate;
    @java.lang.SuppressWarnings("all")
    private short nInfo;
    @java.lang.SuppressWarnings("all")
    private List<ScreenSize> sizes;
    @java.lang.SuppressWarnings("all")
    private List<RefreshRates> rates;

    public boolean isRotationsEnabled(Rotation... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (Rotation m : maskEnums) {
        if (!m.isEnabled(rotations)) {
          return false;
        }
      }
      return true;
    }

    public GetScreenInfoReply.GetScreenInfoReplyBuilder rotationsEnable(Rotation... maskEnums) {
      for (Rotation m : maskEnums) {
        rotations((byte) m.enableFor(rotations));
      }
      return this;
    }

    public GetScreenInfoReply.GetScreenInfoReplyBuilder rotationsDisable(Rotation... maskEnums) {
      for (Rotation m : maskEnums) {
        rotations((byte) m.disableFor(rotations));
      }
      return this;
    }

    public boolean isRotationEnabled(Rotation... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (Rotation m : maskEnums) {
        if (!m.isEnabled(rotation)) {
          return false;
        }
      }
      return true;
    }

    public GetScreenInfoReply.GetScreenInfoReplyBuilder rotationEnable(Rotation... maskEnums) {
      for (Rotation m : maskEnums) {
        rotation((short) m.enableFor(rotation));
      }
      return this;
    }

    public GetScreenInfoReply.GetScreenInfoReplyBuilder rotationDisable(Rotation... maskEnums) {
      for (Rotation m : maskEnums) {
        rotation((short) m.disableFor(rotation));
      }
      return this;
    }

    public int getSize() {
      return 32 + XObject.sizeOf(sizes) + XObject.sizeOf(rates);
    }

    @java.lang.SuppressWarnings("all")
    GetScreenInfoReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenInfoReply.GetScreenInfoReplyBuilder rotations(final byte rotations) {
      this.rotations = rotations;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenInfoReply.GetScreenInfoReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenInfoReply.GetScreenInfoReplyBuilder root(final int root) {
      this.root = root;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenInfoReply.GetScreenInfoReplyBuilder timestamp(final int timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenInfoReply.GetScreenInfoReplyBuilder configTimestamp(final int configTimestamp) {
      this.configTimestamp = configTimestamp;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenInfoReply.GetScreenInfoReplyBuilder sizeID(final short sizeID) {
      this.sizeID = sizeID;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenInfoReply.GetScreenInfoReplyBuilder rotation(final short rotation) {
      this.rotation = rotation;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenInfoReply.GetScreenInfoReplyBuilder rate(final short rate) {
      this.rate = rate;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenInfoReply.GetScreenInfoReplyBuilder nInfo(final short nInfo) {
      this.nInfo = nInfo;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenInfoReply.GetScreenInfoReplyBuilder sizes(final List<ScreenSize> sizes) {
      if (sizes == null) {
        throw new java.lang.NullPointerException("sizes is marked non-null but is null");
      }
      this.sizes = sizes;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenInfoReply.GetScreenInfoReplyBuilder rates(final List<RefreshRates> rates) {
      if (rates == null) {
        throw new java.lang.NullPointerException("rates is marked non-null but is null");
      }
      this.rates = rates;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetScreenInfoReply build() {
      return new GetScreenInfoReply(this.rotations, this.sequenceNumber, this.root, this.timestamp, this.configTimestamp, this.sizeID, this.rotation, this.rate, this.nInfo, this.sizes, this.rates);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetScreenInfoReply.GetScreenInfoReplyBuilder(rotations=" + this.rotations + ", sequenceNumber=" + this.sequenceNumber + ", root=" + this.root + ", timestamp=" + this.timestamp + ", configTimestamp=" + this.configTimestamp + ", sizeID=" + this.sizeID + ", rotation=" + this.rotation + ", rate=" + this.rate + ", nInfo=" + this.nInfo + ", sizes=" + this.sizes + ", rates=" + this.rates + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetScreenInfoReply(final byte rotations, final short sequenceNumber, final int root, final int timestamp, final int configTimestamp, final short sizeID, final short rotation, final short rate, final short nInfo, final List<ScreenSize> sizes, final List<RefreshRates> rates) {
    if (sizes == null) {
      throw new java.lang.NullPointerException("sizes is marked non-null but is null");
    }
    if (rates == null) {
      throw new java.lang.NullPointerException("rates is marked non-null but is null");
    }
    this.rotations = rotations;
    this.sequenceNumber = sequenceNumber;
    this.root = root;
    this.timestamp = timestamp;
    this.configTimestamp = configTimestamp;
    this.sizeID = sizeID;
    this.rotation = rotation;
    this.rate = rate;
    this.nInfo = nInfo;
    this.sizes = sizes;
    this.rates = rates;
  }

  @java.lang.SuppressWarnings("all")
  public static GetScreenInfoReply.GetScreenInfoReplyBuilder builder() {
    return new GetScreenInfoReply.GetScreenInfoReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getRotations() {
    return this.rotations;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getRoot() {
    return this.root;
  }

  @java.lang.SuppressWarnings("all")
  public int getTimestamp() {
    return this.timestamp;
  }

  @java.lang.SuppressWarnings("all")
  public int getConfigTimestamp() {
    return this.configTimestamp;
  }

  @java.lang.SuppressWarnings("all")
  public short getSizeID() {
    return this.sizeID;
  }

  @java.lang.SuppressWarnings("all")
  public short getRotation() {
    return this.rotation;
  }

  @java.lang.SuppressWarnings("all")
  public short getRate() {
    return this.rate;
  }

  @java.lang.SuppressWarnings("all")
  public short getNInfo() {
    return this.nInfo;
  }

  @java.lang.SuppressWarnings("all")
  public List<ScreenSize> getSizes() {
    return this.sizes;
  }

  @java.lang.SuppressWarnings("all")
  public List<RefreshRates> getRates() {
    return this.rates;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetScreenInfoReply)) return false;
    final GetScreenInfoReply other = (GetScreenInfoReply) o;
    if (this.getRotations() != other.getRotations()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getRoot() != other.getRoot()) return false;
    if (this.getTimestamp() != other.getTimestamp()) return false;
    if (this.getConfigTimestamp() != other.getConfigTimestamp()) return false;
    if (this.getSizeID() != other.getSizeID()) return false;
    if (this.getRotation() != other.getRotation()) return false;
    if (this.getRate() != other.getRate()) return false;
    if (this.getNInfo() != other.getNInfo()) return false;
    final java.lang.Object this$sizes = this.getSizes();
    final java.lang.Object other$sizes = other.getSizes();
    if (this$sizes == null ? other$sizes != null : !this$sizes.equals(other$sizes)) return false;
    final java.lang.Object this$rates = this.getRates();
    final java.lang.Object other$rates = other.getRates();
    if (this$rates == null ? other$rates != null : !this$rates.equals(other$rates)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getRotations();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getRoot();
    result = result * PRIME + this.getTimestamp();
    result = result * PRIME + this.getConfigTimestamp();
    result = result * PRIME + this.getSizeID();
    result = result * PRIME + this.getRotation();
    result = result * PRIME + this.getRate();
    result = result * PRIME + this.getNInfo();
    final java.lang.Object $sizes = this.getSizes();
    result = result * PRIME + ($sizes == null ? 43 : $sizes.hashCode());
    final java.lang.Object $rates = this.getRates();
    result = result * PRIME + ($rates == null ? 43 : $rates.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetScreenInfoReply(rotations=" + this.getRotations() + ", sequenceNumber=" + this.getSequenceNumber() + ", root=" + this.getRoot() + ", timestamp=" + this.getTimestamp() + ", configTimestamp=" + this.getConfigTimestamp() + ", sizeID=" + this.getSizeID() + ", rotation=" + this.getRotation() + ", rate=" + this.getRate() + ", nInfo=" + this.getNInfo() + ", sizes=" + this.getSizes() + ", rates=" + this.getRates() + ")";
  }
}
