package com.github.moaxcp.x11.protocol.randr;

import java.util.ArrayList;
import java.util.List;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class GetScreenResourcesReply implements XReply {
  public static final String PLUGIN_NAME = "randr";
  private final short sequenceNumber;
  private final int timestamp;
  private final int configTimestamp;
  private final int[] crtcs;
  private final int[] outputs;
  private final List<ModeInfo> modes;
  private final byte[] names;

  public static GetScreenResourcesReply readGetScreenResourcesReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    GetScreenResourcesReply.GetScreenResourcesReplyBuilder javaBuilder = GetScreenResourcesReply.builder();
    int length = in.readCard32();
    int timestamp = in.readCard32();
    int configTimestamp = in.readCard32();
    short numCrtcs = in.readCard16();
    short numOutputs = in.readCard16();
    short numModes = in.readCard16();
    short namesLen = in.readCard16();
    byte[] pad10 = in.readPad(8);
    int[] crtcs = in.readCard32(Short.toUnsignedInt(numCrtcs));
    int[] outputs = in.readCard32(Short.toUnsignedInt(numOutputs));
    List<ModeInfo> modes = new ArrayList<>(Short.toUnsignedInt(numModes));
    for (int i = 0; i < Short.toUnsignedInt(numModes); i++) {
      modes.add(ModeInfo.readModeInfo(in));
    }
    byte[] names = in.readByte(Short.toUnsignedInt(namesLen));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.timestamp(timestamp);
    javaBuilder.configTimestamp(configTimestamp);
    javaBuilder.crtcs(crtcs);
    javaBuilder.outputs(outputs);
    javaBuilder.modes(List.copyOf(modes));
    javaBuilder.names(names);
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
    short numCrtcs = (short) crtcs.length;
    out.writeCard16(numCrtcs);
    short numOutputs = (short) outputs.length;
    out.writeCard16(numOutputs);
    short numModes = (short) modes.size();
    out.writeCard16(numModes);
    short namesLen = (short) names.length;
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
    return 32 + 4 * crtcs.length + 4 * outputs.length + XObject.sizeOf(modes) + 1 * names.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetScreenResourcesReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int timestamp;
    @java.lang.SuppressWarnings("all")
    private int configTimestamp;
    @java.lang.SuppressWarnings("all")
    private int[] crtcs;
    @java.lang.SuppressWarnings("all")
    private int[] outputs;
    @java.lang.SuppressWarnings("all")
    private List<ModeInfo> modes;
    @java.lang.SuppressWarnings("all")
    private byte[] names;

    public int getSize() {
      return 32 + 4 * crtcs.length + 4 * outputs.length + XObject.sizeOf(modes) + 1 * names.length;
    }

    @java.lang.SuppressWarnings("all")
    GetScreenResourcesReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenResourcesReply.GetScreenResourcesReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenResourcesReply.GetScreenResourcesReplyBuilder timestamp(final int timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenResourcesReply.GetScreenResourcesReplyBuilder configTimestamp(final int configTimestamp) {
      this.configTimestamp = configTimestamp;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenResourcesReply.GetScreenResourcesReplyBuilder crtcs(final int[] crtcs) {
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
    public GetScreenResourcesReply.GetScreenResourcesReplyBuilder outputs(final int[] outputs) {
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
    public GetScreenResourcesReply.GetScreenResourcesReplyBuilder modes(final List<ModeInfo> modes) {
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
    public GetScreenResourcesReply.GetScreenResourcesReplyBuilder names(final byte[] names) {
      if (names == null) {
        throw new java.lang.NullPointerException("names is marked non-null but is null");
      }
      this.names = names;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetScreenResourcesReply build() {
      return new GetScreenResourcesReply(this.sequenceNumber, this.timestamp, this.configTimestamp, this.crtcs, this.outputs, this.modes, this.names);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetScreenResourcesReply.GetScreenResourcesReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", timestamp=" + this.timestamp + ", configTimestamp=" + this.configTimestamp + ", crtcs=" + java.util.Arrays.toString(this.crtcs) + ", outputs=" + java.util.Arrays.toString(this.outputs) + ", modes=" + this.modes + ", names=" + java.util.Arrays.toString(this.names) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetScreenResourcesReply(final short sequenceNumber, final int timestamp, final int configTimestamp, final int[] crtcs, final int[] outputs, final List<ModeInfo> modes, final byte[] names) {
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
  public static GetScreenResourcesReply.GetScreenResourcesReplyBuilder builder() {
    return new GetScreenResourcesReply.GetScreenResourcesReplyBuilder();
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
  public int[] getCrtcs() {
    return this.crtcs;
  }

  @java.lang.SuppressWarnings("all")
  public int[] getOutputs() {
    return this.outputs;
  }

  @java.lang.SuppressWarnings("all")
  public List<ModeInfo> getModes() {
    return this.modes;
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getNames() {
    return this.names;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetScreenResourcesReply)) return false;
    final GetScreenResourcesReply other = (GetScreenResourcesReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getTimestamp() != other.getTimestamp()) return false;
    if (this.getConfigTimestamp() != other.getConfigTimestamp()) return false;
    if (!java.util.Arrays.equals(this.getCrtcs(), other.getCrtcs())) return false;
    if (!java.util.Arrays.equals(this.getOutputs(), other.getOutputs())) return false;
    final java.lang.Object this$modes = this.getModes();
    final java.lang.Object other$modes = other.getModes();
    if (this$modes == null ? other$modes != null : !this$modes.equals(other$modes)) return false;
    if (!java.util.Arrays.equals(this.getNames(), other.getNames())) return false;
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
    result = result * PRIME + java.util.Arrays.hashCode(this.getCrtcs());
    result = result * PRIME + java.util.Arrays.hashCode(this.getOutputs());
    final java.lang.Object $modes = this.getModes();
    result = result * PRIME + ($modes == null ? 43 : $modes.hashCode());
    result = result * PRIME + java.util.Arrays.hashCode(this.getNames());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetScreenResourcesReply(sequenceNumber=" + this.getSequenceNumber() + ", timestamp=" + this.getTimestamp() + ", configTimestamp=" + this.getConfigTimestamp() + ", crtcs=" + java.util.Arrays.toString(this.getCrtcs()) + ", outputs=" + java.util.Arrays.toString(this.getOutputs()) + ", modes=" + this.getModes() + ", names=" + java.util.Arrays.toString(this.getNames()) + ")";
  }
}
