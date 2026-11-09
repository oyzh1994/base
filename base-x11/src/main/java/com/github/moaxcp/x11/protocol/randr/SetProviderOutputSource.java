package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class SetProviderOutputSource implements OneWayRequest {
  public static final String PLUGIN_NAME = "randr";
  public static final byte OPCODE = 35;
  private final int provider;
  private final int sourceProvider;
  private final int configTimestamp;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetProviderOutputSource readSetProviderOutputSource(X11Input in) throws IOException {
    SetProviderOutputSource.SetProviderOutputSourceBuilder javaBuilder = SetProviderOutputSource.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int provider = in.readCard32();
    int sourceProvider = in.readCard32();
    int configTimestamp = in.readCard32();
    javaBuilder.provider(provider);
    javaBuilder.sourceProvider(sourceProvider);
    javaBuilder.configTimestamp(configTimestamp);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(provider);
    out.writeCard32(sourceProvider);
    out.writeCard32(configTimestamp);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetProviderOutputSourceBuilder {
    @java.lang.SuppressWarnings("all")
    private int provider;
    @java.lang.SuppressWarnings("all")
    private int sourceProvider;
    @java.lang.SuppressWarnings("all")
    private int configTimestamp;

    public int getSize() {
      return 16;
    }

    @java.lang.SuppressWarnings("all")
    SetProviderOutputSourceBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetProviderOutputSource.SetProviderOutputSourceBuilder provider(final int provider) {
      this.provider = provider;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetProviderOutputSource.SetProviderOutputSourceBuilder sourceProvider(final int sourceProvider) {
      this.sourceProvider = sourceProvider;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetProviderOutputSource.SetProviderOutputSourceBuilder configTimestamp(final int configTimestamp) {
      this.configTimestamp = configTimestamp;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetProviderOutputSource build() {
      return new SetProviderOutputSource(this.provider, this.sourceProvider, this.configTimestamp);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetProviderOutputSource.SetProviderOutputSourceBuilder(provider=" + this.provider + ", sourceProvider=" + this.sourceProvider + ", configTimestamp=" + this.configTimestamp + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetProviderOutputSource(final int provider, final int sourceProvider, final int configTimestamp) {
    this.provider = provider;
    this.sourceProvider = sourceProvider;
    this.configTimestamp = configTimestamp;
  }

  @java.lang.SuppressWarnings("all")
  public static SetProviderOutputSource.SetProviderOutputSourceBuilder builder() {
    return new SetProviderOutputSource.SetProviderOutputSourceBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getProvider() {
    return this.provider;
  }

  @java.lang.SuppressWarnings("all")
  public int getSourceProvider() {
    return this.sourceProvider;
  }

  @java.lang.SuppressWarnings("all")
  public int getConfigTimestamp() {
    return this.configTimestamp;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetProviderOutputSource)) return false;
    final SetProviderOutputSource other = (SetProviderOutputSource) o;
    if (this.getProvider() != other.getProvider()) return false;
    if (this.getSourceProvider() != other.getSourceProvider()) return false;
    if (this.getConfigTimestamp() != other.getConfigTimestamp()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getProvider();
    result = result * PRIME + this.getSourceProvider();
    result = result * PRIME + this.getConfigTimestamp();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetProviderOutputSource(provider=" + this.getProvider() + ", sourceProvider=" + this.getSourceProvider() + ", configTimestamp=" + this.getConfigTimestamp() + ")";
  }
}
