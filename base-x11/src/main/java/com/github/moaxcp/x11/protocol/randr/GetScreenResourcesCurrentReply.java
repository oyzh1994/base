package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.ByteList;
import org.eclipse.collections.api.list.primitive.IntList;

public final class GetScreenResourcesCurrentReply implements XReply {
  public static final String PLUGIN_NAME = "randr";
  private final short sequenceNumber;
  private final int timestamp;
  private final int configTimestamp;
  private final IntList crtcs;
  private final IntList outputs;
  private final ImmutableList<ModeInfo> modes;
  private final ByteList names;

  public static GetScreenResourcesCurrentReply readGetScreenResourcesCurrentReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    GetScreenResourcesCurrentReply.GetScreenResourcesCurrentReplyBuilder javaBuilder = GetScreenResourcesCurrentReply.builder();
    int length = in.readCard32();
    int timestamp = in.readCard32();
    int configTimestamp = in.readCard32();
    short numCrtcs = in.readCard16();
    short numOutputs = in.readCard16();
    short numModes = in.readCard16();
    short namesLen = in.readCard16();
    byte[] pad10 = in.readPad(8);
    IntList crtcs = in.readCard32(Short.toUnsignedInt(numCrtcs));
    IntList outputs = in.readCard32(Short.toUnsignedInt(numOutputs));
    MutableList<ModeInfo> modes = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(numModes));
    for (int i = 0; i < Short.toUnsignedInt(numModes); i++) {
      modes.add(ModeInfo.readModeInfo(in));
    }
    ByteList names = in.readByte(Short.toUnsignedInt(namesLen));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.timestamp(timestamp);
    javaBuilder.configTimestamp(configTimestamp);
    javaBuilder.crtcs(crtcs.toImmutable());
    javaBuilder.outputs(outputs.toImmutable());
    javaBuilder.modes(modes.toImmutable());
    javaBuilder.names(names.toImmutable());
    if (javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(timestamp);
    out.writeCard32(configTimestamp);
    short numCrtcs = (short) crtcs.size();
    out.writeCard16(numCrtcs);
    short numOutputs = (short) outputs.size();
    out.writeCard16(numOutputs);
    short numModes = (short) modes.size();
    out.writeCard16(numModes);
    short namesLen = (short) names.size();
    out.writeCard16(namesLen);
    out.writePad(8);
    out.writeCard32(crtcs);
    out.writeCard32(outputs);
    for (ModeInfo t : modes) {
      t.write(out);
    }
    out.writeByte(names);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 4 * crtcs.size() + 4 * outputs.size() + XObject.sizeOf(modes) + 1 * names.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetScreenResourcesCurrentReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int timestamp;
    @java.lang.SuppressWarnings("all")
    private int configTimestamp;
    @java.lang.SuppressWarnings("all")
    private IntList crtcs;
    @java.lang.SuppressWarnings("all")
    private IntList outputs;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<ModeInfo> modes;
    @java.lang.SuppressWarnings("all")
    private ByteList names;

    public int getSize() {
      return 32 + 4 * crtcs.size() + 4 * outputs.size() + XObject.sizeOf(modes) + 1 * names.size();
    }

    @java.lang.SuppressWarnings("all")
    GetScreenResourcesCurrentReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenResourcesCurrentReply.GetScreenResourcesCurrentReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenResourcesCurrentReply.GetScreenResourcesCurrentReplyBuilder timestamp(final int timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenResourcesCurrentReply.GetScreenResourcesCurrentReplyBuilder configTimestamp(final int configTimestamp) {
      this.configTimestamp = configTimestamp;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenResourcesCurrentReply.GetScreenResourcesCurrentReplyBuilder crtcs(final IntList crtcs) {
      if (crtcs == null) {
        throw new java.lang.NullPointerException("crtcs is marked non-null but is null");
      }
      this.crtcs = crtcs;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenResourcesCurrentReply.GetScreenResourcesCurrentReplyBuilder outputs(final IntList outputs) {
      if (outputs == null) {
        throw new java.lang.NullPointerException("outputs is marked non-null but is null");
      }
      this.outputs = outputs;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenResourcesCurrentReply.GetScreenResourcesCurrentReplyBuilder modes(final ImmutableList<ModeInfo> modes) {
      if (modes == null) {
        throw new java.lang.NullPointerException("modes is marked non-null but is null");
      }
      this.modes = modes;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenResourcesCurrentReply.GetScreenResourcesCurrentReplyBuilder names(final ByteList names) {
      if (names == null) {
        throw new java.lang.NullPointerException("names is marked non-null but is null");
      }
      this.names = names;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetScreenResourcesCurrentReply build() {
      return new GetScreenResourcesCurrentReply(this.sequenceNumber, this.timestamp, this.configTimestamp, this.crtcs, this.outputs, this.modes, this.names);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetScreenResourcesCurrentReply.GetScreenResourcesCurrentReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", timestamp=" + this.timestamp + ", configTimestamp=" + this.configTimestamp + ", crtcs=" + this.crtcs + ", outputs=" + this.outputs + ", modes=" + this.modes + ", names=" + this.names + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetScreenResourcesCurrentReply(final short sequenceNumber, final int timestamp, final int configTimestamp, final IntList crtcs, final IntList outputs, final ImmutableList<ModeInfo> modes, final ByteList names) {
    if (crtcs == null) {
      throw new java.lang.NullPointerException("crtcs is marked non-null but is null");
    }
    if (outputs == null) {
      throw new java.lang.NullPointerException("outputs is marked non-null but is null");
    }
    if (modes == null) {
      throw new java.lang.NullPointerException("modes is marked non-null but is null");
    }
    if (names == null) {
      throw new java.lang.NullPointerException("names is marked non-null but is null");
    }
    this.sequenceNumber = sequenceNumber;
    this.timestamp = timestamp;
    this.configTimestamp = configTimestamp;
    this.crtcs = crtcs;
    this.outputs = outputs;
    this.modes = modes;
    this.names = names;
  }

  @java.lang.SuppressWarnings("all")
  public static GetScreenResourcesCurrentReply.GetScreenResourcesCurrentReplyBuilder builder() {
    return new GetScreenResourcesCurrentReply.GetScreenResourcesCurrentReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
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
  public IntList getCrtcs() {
    return this.crtcs;
  }

  @java.lang.SuppressWarnings("all")
  public IntList getOutputs() {
    return this.outputs;
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<ModeInfo> getModes() {
    return this.modes;
  }

  @java.lang.SuppressWarnings("all")
  public ByteList getNames() {
    return this.names;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetScreenResourcesCurrentReply)) return false;
    final GetScreenResourcesCurrentReply other = (GetScreenResourcesCurrentReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getTimestamp() != other.getTimestamp()) return false;
    if (this.getConfigTimestamp() != other.getConfigTimestamp()) return false;
    final java.lang.Object this$crtcs = this.getCrtcs();
    final java.lang.Object other$crtcs = other.getCrtcs();
    if (this$crtcs == null ? other$crtcs != null : !this$crtcs.equals(other$crtcs)) return false;
    final java.lang.Object this$outputs = this.getOutputs();
    final java.lang.Object other$outputs = other.getOutputs();
    if (this$outputs == null ? other$outputs != null : !this$outputs.equals(other$outputs)) return false;
    final java.lang.Object this$modes = this.getModes();
    final java.lang.Object other$modes = other.getModes();
    if (this$modes == null ? other$modes != null : !this$modes.equals(other$modes)) return false;
    final java.lang.Object this$names = this.getNames();
    final java.lang.Object other$names = other.getNames();
    if (this$names == null ? other$names != null : !this$names.equals(other$names)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getTimestamp();
    result = result * PRIME + this.getConfigTimestamp();
    final java.lang.Object $crtcs = this.getCrtcs();
    result = result * PRIME + ($crtcs == null ? 43 : $crtcs.hashCode());
    final java.lang.Object $outputs = this.getOutputs();
    result = result * PRIME + ($outputs == null ? 43 : $outputs.hashCode());
    final java.lang.Object $modes = this.getModes();
    result = result * PRIME + ($modes == null ? 43 : $modes.hashCode());
    final java.lang.Object $names = this.getNames();
    result = result * PRIME + ($names == null ? 43 : $names.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetScreenResourcesCurrentReply(sequenceNumber=" + this.getSequenceNumber() + ", timestamp=" + this.getTimestamp() + ", configTimestamp=" + this.getConfigTimestamp() + ", crtcs=" + this.getCrtcs() + ", outputs=" + this.getOutputs() + ", modes=" + this.getModes() + ", names=" + this.getNames() + ")";
  }
}
