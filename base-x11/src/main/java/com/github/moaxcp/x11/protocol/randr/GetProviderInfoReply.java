package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.ByteList;
import org.eclipse.collections.api.list.primitive.IntList;

public final class GetProviderInfoReply implements XReply {
  public static final String PLUGIN_NAME = "randr";
  private final byte status;
  private final short sequenceNumber;
  private final int timestamp;
  private final int capabilities;
  private final IntList crtcs;
  private final IntList outputs;
  private final IntList associatedProviders;
  private final IntList associatedCapability;
  private final ByteList name;

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
    IntList crtcs = in.readCard32(Short.toUnsignedInt(numCrtcs));
    IntList outputs = in.readCard32(Short.toUnsignedInt(numOutputs));
    IntList associatedProviders = in.readCard32(Short.toUnsignedInt(numAssociatedProviders));
    IntList associatedCapability = in.readCard32(Short.toUnsignedInt(numAssociatedProviders));
    ByteList name = in.readChar(Short.toUnsignedInt(nameLen));
    javaBuilder.status(status);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.timestamp(timestamp);
    javaBuilder.capabilities(capabilities);
    javaBuilder.crtcs(crtcs.toImmutable());
    javaBuilder.outputs(outputs.toImmutable());
    javaBuilder.associatedProviders(associatedProviders.toImmutable());
    javaBuilder.associatedCapability(associatedCapability.toImmutable());
    javaBuilder.name(name.toImmutable());
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
    short numCrtcs = (short) crtcs.size();
    out.writeCard16(numCrtcs);
    short numOutputs = (short) outputs.size();
    out.writeCard16(numOutputs);
    short numAssociatedProviders = (short) associatedCapability.size();
    out.writeCard16(numAssociatedProviders);
    short nameLen = (short) name.size();
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
    return 32 + 4 * crtcs.size() + 4 * outputs.size() + 4 * associatedProviders.size() + 4 * associatedCapability.size() + 1 * name.size();
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
    private IntList crtcs;
    @java.lang.SuppressWarnings("all")
    private IntList outputs;
    @java.lang.SuppressWarnings("all")
    private IntList associatedProviders;
    @java.lang.SuppressWarnings("all")
    private IntList associatedCapability;
    @java.lang.SuppressWarnings("all")
    private ByteList name;

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
      return 32 + 4 * crtcs.size() + 4 * outputs.size() + 4 * associatedProviders.size() + 4 * associatedCapability.size() + 1 * name.size();
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
    public GetProviderInfoReply.GetProviderInfoReplyBuilder crtcs(final IntList crtcs) {
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
    public GetProviderInfoReply.GetProviderInfoReplyBuilder outputs(final IntList outputs) {
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
    public GetProviderInfoReply.GetProviderInfoReplyBuilder associatedProviders(final IntList associatedProviders) {
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
    public GetProviderInfoReply.GetProviderInfoReplyBuilder associatedCapability(final IntList associatedCapability) {
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
    public GetProviderInfoReply.GetProviderInfoReplyBuilder name(final ByteList name) {
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
      return "GetProviderInfoReply.GetProviderInfoReplyBuilder(status=" + this.status + ", sequenceNumber=" + this.sequenceNumber + ", timestamp=" + this.timestamp + ", capabilities=" + this.capabilities + ", crtcs=" + this.crtcs + ", outputs=" + this.outputs + ", associatedProviders=" + this.associatedProviders + ", associatedCapability=" + this.associatedCapability + ", name=" + this.name + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetProviderInfoReply(final byte status, final short sequenceNumber, final int timestamp, final int capabilities, final IntList crtcs, final IntList outputs, final IntList associatedProviders, final IntList associatedCapability, final ByteList name) {
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
  public IntList getCrtcs() {
    return this.crtcs;
  }

  @java.lang.SuppressWarnings("all")
  public IntList getOutputs() {
    return this.outputs;
  }

  @java.lang.SuppressWarnings("all")
  public IntList getAssociatedProviders() {
    return this.associatedProviders;
  }

  @java.lang.SuppressWarnings("all")
  public IntList getAssociatedCapability() {
    return this.associatedCapability;
  }

  @java.lang.SuppressWarnings("all")
  public ByteList getName() {
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
    final java.lang.Object this$crtcs = this.getCrtcs();
    final java.lang.Object other$crtcs = other.getCrtcs();
    if (this$crtcs == null ? other$crtcs != null : !this$crtcs.equals(other$crtcs)) return false;
    final java.lang.Object this$outputs = this.getOutputs();
    final java.lang.Object other$outputs = other.getOutputs();
    if (this$outputs == null ? other$outputs != null : !this$outputs.equals(other$outputs)) return false;
    final java.lang.Object this$associatedProviders = this.getAssociatedProviders();
    final java.lang.Object other$associatedProviders = other.getAssociatedProviders();
    if (this$associatedProviders == null ? other$associatedProviders != null : !this$associatedProviders.equals(other$associatedProviders)) return false;
    final java.lang.Object this$associatedCapability = this.getAssociatedCapability();
    final java.lang.Object other$associatedCapability = other.getAssociatedCapability();
    if (this$associatedCapability == null ? other$associatedCapability != null : !this$associatedCapability.equals(other$associatedCapability)) return false;
    final java.lang.Object this$name = this.getName();
    final java.lang.Object other$name = other.getName();
    if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
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
    final java.lang.Object $crtcs = this.getCrtcs();
    result = result * PRIME + ($crtcs == null ? 43 : $crtcs.hashCode());
    final java.lang.Object $outputs = this.getOutputs();
    result = result * PRIME + ($outputs == null ? 43 : $outputs.hashCode());
    final java.lang.Object $associatedProviders = this.getAssociatedProviders();
    result = result * PRIME + ($associatedProviders == null ? 43 : $associatedProviders.hashCode());
    final java.lang.Object $associatedCapability = this.getAssociatedCapability();
    result = result * PRIME + ($associatedCapability == null ? 43 : $associatedCapability.hashCode());
    final java.lang.Object $name = this.getName();
    result = result * PRIME + ($name == null ? 43 : $name.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetProviderInfoReply(status=" + this.getStatus() + ", sequenceNumber=" + this.getSequenceNumber() + ", timestamp=" + this.getTimestamp() + ", capabilities=" + this.getCapabilities() + ", crtcs=" + this.getCrtcs() + ", outputs=" + this.getOutputs() + ", associatedProviders=" + this.getAssociatedProviders() + ", associatedCapability=" + this.getAssociatedCapability() + ", name=" + this.getName() + ")";
  }
}
