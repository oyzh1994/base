package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GetProviderInfo implements TwoWayRequest<GetProviderInfoReply> {
  public static final String PLUGIN_NAME = "randr";
  public static final byte OPCODE = 33;
  private final int provider;
  private final int configTimestamp;

  public XReplyFunction<GetProviderInfoReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetProviderInfoReply.readGetProviderInfoReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetProviderInfo readGetProviderInfo(X11Input in) throws IOException {
    GetProviderInfo.GetProviderInfoBuilder javaBuilder = GetProviderInfo.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int provider = in.readCard32();
    int configTimestamp = in.readCard32();
    javaBuilder.provider(provider);
    javaBuilder.configTimestamp(configTimestamp);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(provider);
    out.writeCard32(configTimestamp);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetProviderInfoBuilder {
    @java.lang.SuppressWarnings("all")
    private int provider;
    @java.lang.SuppressWarnings("all")
    private int configTimestamp;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    GetProviderInfoBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetProviderInfo.GetProviderInfoBuilder provider(final int provider) {
      this.provider = provider;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetProviderInfo.GetProviderInfoBuilder configTimestamp(final int configTimestamp) {
      this.configTimestamp = configTimestamp;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetProviderInfo build() {
      return new GetProviderInfo(this.provider, this.configTimestamp);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetProviderInfo.GetProviderInfoBuilder(provider=" + this.provider + ", configTimestamp=" + this.configTimestamp + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetProviderInfo(final int provider, final int configTimestamp) {
    this.provider = provider;
    this.configTimestamp = configTimestamp;
  }

  @java.lang.SuppressWarnings("all")
  public static GetProviderInfo.GetProviderInfoBuilder builder() {
    return new GetProviderInfo.GetProviderInfoBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getProvider() {
    return this.provider;
  }

  @java.lang.SuppressWarnings("all")
  public int getConfigTimestamp() {
    return this.configTimestamp;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetProviderInfo)) return false;
    final GetProviderInfo other = (GetProviderInfo) o;
    if (this.getProvider() != other.getProvider()) return false;
    if (this.getConfigTimestamp() != other.getConfigTimestamp()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getProvider();
    result = result * PRIME + this.getConfigTimestamp();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetProviderInfo(provider=" + this.getProvider() + ", configTimestamp=" + this.getConfigTimestamp() + ")";
  }
}
