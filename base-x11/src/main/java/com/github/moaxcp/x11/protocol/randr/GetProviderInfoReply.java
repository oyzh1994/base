package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class GetProviderInfoReply implements XReply {
  public static final String PLUGIN_NAME = "randr";
  private final byte status;
  private final short sequenceNumber;
  private final int timestamp;
  private final int capabilities;
  private final int[] crtcs;
  private final int[] outputs;
  private final int[] associatedProviders;
  private final int[] associatedCapability;
  private final byte[] name;

  public static GetProviderInfoReply readGetProviderInfoReply(byte status, short sequenceNumber, X11Input in) throws IOException {
    GetProviderInfoReply.GetProviderInfoReplyBuilder javaBuilder = GetProviderInfoReply.builder();
    int length = in.readCard32();
    int timestamp = in.readCard32();
    int capabilities = in.readCard32();
    short numCrtcs = in.readCard16();
    short numOutputs = in.readCard16();
    short numAssociatedProviders = in.readCard16();
    short nameLen = in.readCard16();
    byte[] pad10 = in.readPad(8);
    int[] crtcs = in.readCard32(Short.toUnsignedInt(numCrtcs));
    int[] outputs = in.readCard32(Short.toUnsignedInt(numOutputs));
    int[] associatedProviders = in.readCard32(Short.toUnsignedInt(numAssociatedProviders));
    int[] associatedCapability = in.readCard32(Short.toUnsignedInt(numAssociatedProviders));
    byte[] name = in.readChar(Short.toUnsignedInt(nameLen));
    javaBuilder.status(status);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.timestamp(timestamp);
    javaBuilder.capabilities(capabilities);
    javaBuilder.crtcs(crtcs);
    javaBuilder.outputs(outputs);
    javaBuilder.associatedProviders(associatedProviders);
    javaBuilder.associatedCapability(associatedCapability);
    javaBuilder.name(name);
    if (javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(status);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(timestamp);
    out.writeCard32(capabilities);
    short numCrtcs = (short) crtcs.length;
    out.writeCard16(numCrtcs);
    short numOutputs = (short) outputs.length;
    out.writeCard16(numOutputs);
    short numAssociatedProviders = (short) associatedCapability.length;
    out.writeCard16(numAssociatedProviders);
    short nameLen = (short) name.length;
    out.writeCard16(nameLen);
    out.writePad(8);
    out.writeCard32(crtcs);
    out.writeCard32(outputs);
    out.writeCard32(associatedProviders);
    out.writeCard32(associatedCapability);
    out.writeChar(name);
    out.writePadAlign(getSize());
  }

  public boolean isCapabilitiesEnabled(ProviderCapability... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (ProviderCapability m : maskEnums) {
      if (!m.isEnabled(capabilities)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 32 + 4 * crtcs.length + 4 * outputs.length + 4 * associatedProviders.length + 4 * associatedCapability.length + 1 * name.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetProviderInfoReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte status;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int timestamp;
    @java.lang.SuppressWarnings("all")
    private int capabilities;
    @java.lang.SuppressWarnings("all")
    private int[] crtcs;
    @java.lang.SuppressWarnings("all")
    private int[] outputs;
    @java.lang.SuppressWarnings("all")
    private int[] associatedProviders;
    @java.lang.SuppressWarnings("all")
    private int[] associatedCapability;
    @java.lang.SuppressWarnings("all")
    private byte[] name;

    public boolean isCapabilitiesEnabled(ProviderCapability... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (ProviderCapability m : maskEnums) {
        if (!m.isEnabled(capabilities)) {
          return false;
        }
      }
      return true;
    }

    public GetProviderInfoReply.GetProviderInfoReplyBuilder capabilitiesEnable(ProviderCapability... maskEnums) {
      for (ProviderCapability m : maskEnums) {
        capabilities((int) m.enableFor(capabilities));
      }
      return this;
    }

    public GetProviderInfoReply.GetProviderInfoReplyBuilder capabilitiesDisable(ProviderCapability... maskEnums) {
      for (ProviderCapability m : maskEnums) {
        capabilities((int) m.disableFor(capabilities));
      }
      return this;
    }

    public int getSize() {
      return 32 + 4 * crtcs.length + 4 * outputs.length + 4 * associatedProviders.length + 4 * associatedCapability.length + 1 * name.length;
    }

    @java.lang.SuppressWarnings("all")
    GetProviderInfoReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetProviderInfoReply.GetProviderInfoReplyBuilder status(final byte status) {
      this.status = status;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetProviderInfoReply.GetProviderInfoReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetProviderInfoReply.GetProviderInfoReplyBuilder timestamp(final int timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetProviderInfoReply.GetProviderInfoReplyBuilder capabilities(final int capabilities) {
      this.capabilities = capabilities;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetProviderInfoReply.GetProviderInfoReplyBuilder crtcs(final int[] crtcs) {
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
    public GetProviderInfoReply.GetProviderInfoReplyBuilder outputs(final int[] outputs) {
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
    public GetProviderInfoReply.GetProviderInfoReplyBuilder associatedProviders(final int[] associatedProviders) {
      if (associatedProviders == null) {
        throw new java.lang.NullPointerException("associatedProviders is marked non-null but is null");
      }
      this.associatedProviders = associatedProviders;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetProviderInfoReply.GetProviderInfoReplyBuilder associatedCapability(final int[] associatedCapability) {
      if (associatedCapability == null) {
        throw new java.lang.NullPointerException("associatedCapability is marked non-null but is null");
      }
      this.associatedCapability = associatedCapability;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetProviderInfoReply.GetProviderInfoReplyBuilder name(final byte[] name) {
      if (name == null) {
        throw new java.lang.NullPointerException("name is marked non-null but is null");
      }
      this.name = name;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetProviderInfoReply build() {
      return new GetProviderInfoReply(this.status, this.sequenceNumber, this.timestamp, this.capabilities, this.crtcs, this.outputs, this.associatedProviders, this.associatedCapability, this.name);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetProviderInfoReply.GetProviderInfoReplyBuilder(status=" + this.status + ", sequenceNumber=" + this.sequenceNumber + ", timestamp=" + this.timestamp + ", capabilities=" + this.capabilities + ", crtcs=" + java.util.Arrays.toString(this.crtcs) + ", outputs=" + java.util.Arrays.toString(this.outputs) + ", associatedProviders=" + java.util.Arrays.toString(this.associatedProviders) + ", associatedCapability=" + java.util.Arrays.toString(this.associatedCapability) + ", name=" + java.util.Arrays.toString(this.name) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetProviderInfoReply(final byte status, final short sequenceNumber, final int timestamp, final int capabilities, final int[] crtcs, final int[] outputs, final int[] associatedProviders, final int[] associatedCapability, final byte[] name) {
    if (crtcs == null) {
      throw new java.lang.NullPointerException("crtcs is marked non-null but is null");
    }
    if (outputs == null) {
      throw new java.lang.NullPointerException("outputs is marked non-null but is null");
    }
    if (associatedProviders == null) {
      throw new java.lang.NullPointerException("associatedProviders is marked non-null but is null");
    }
    if (associatedCapability == null) {
      throw new java.lang.NullPointerException("associatedCapability is marked non-null but is null");
    }
    if (name == null) {
      throw new java.lang.NullPointerException("name is marked non-null but is null");
    }
    this.status = status;
    this.sequenceNumber = sequenceNumber;
    this.timestamp = timestamp;
    this.capabilities = capabilities;
    this.crtcs = crtcs;
    this.outputs = outputs;
    this.associatedProviders = associatedProviders;
    this.associatedCapability = associatedCapability;
    this.name = name;
  }

  @java.lang.SuppressWarnings("all")
  public static GetProviderInfoReply.GetProviderInfoReplyBuilder builder() {
    return new GetProviderInfoReply.GetProviderInfoReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getStatus() {
    return this.status;
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
  public int getCapabilities() {
    return this.capabilities;
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
  public int[] getAssociatedProviders() {
    return this.associatedProviders;
  }

  @java.lang.SuppressWarnings("all")
  public int[] getAssociatedCapability() {
    return this.associatedCapability;
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getName() {
    return this.name;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetProviderInfoReply)) return false;
    final GetProviderInfoReply other = (GetProviderInfoReply) o;
    if (this.getStatus() != other.getStatus()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getTimestamp() != other.getTimestamp()) return false;
    if (this.getCapabilities() != other.getCapabilities()) return false;
    if (!java.util.Arrays.equals(this.getCrtcs(), other.getCrtcs())) return false;
    if (!java.util.Arrays.equals(this.getOutputs(), other.getOutputs())) return false;
    if (!java.util.Arrays.equals(this.getAssociatedProviders(), other.getAssociatedProviders())) return false;
    if (!java.util.Arrays.equals(this.getAssociatedCapability(), other.getAssociatedCapability())) return false;
    if (!java.util.Arrays.equals(this.getName(), other.getName())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getStatus();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getTimestamp();
    result = result * PRIME + this.getCapabilities();
    result = result * PRIME + java.util.Arrays.hashCode(this.getCrtcs());
    result = result * PRIME + java.util.Arrays.hashCode(this.getOutputs());
    result = result * PRIME + java.util.Arrays.hashCode(this.getAssociatedProviders());
    result = result * PRIME + java.util.Arrays.hashCode(this.getAssociatedCapability());
    result = result * PRIME + java.util.Arrays.hashCode(this.getName());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetProviderInfoReply(status=" + this.getStatus() + ", sequenceNumber=" + this.getSequenceNumber() + ", timestamp=" + this.getTimestamp() + ", capabilities=" + this.getCapabilities() + ", crtcs=" + java.util.Arrays.toString(this.getCrtcs()) + ", outputs=" + java.util.Arrays.toString(this.getOutputs()) + ", associatedProviders=" + java.util.Arrays.toString(this.getAssociatedProviders()) + ", associatedCapability=" + java.util.Arrays.toString(this.getAssociatedCapability()) + ", name=" + java.util.Arrays.toString(this.getName()) + ")";
  }
}
