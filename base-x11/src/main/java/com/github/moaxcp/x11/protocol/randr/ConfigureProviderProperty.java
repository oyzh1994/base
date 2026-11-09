package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.IntList;

public final class ConfigureProviderProperty implements OneWayRequest {
  public static final String PLUGIN_NAME = "randr";
  public static final byte OPCODE = 38;
  private final int provider;
  private final int property;
  private final boolean pending;
  private final boolean range;
  private final IntList values;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ConfigureProviderProperty readConfigureProviderProperty(X11Input in) throws IOException {
    ConfigureProviderProperty.ConfigureProviderPropertyBuilder javaBuilder = ConfigureProviderProperty.builder();
    int javaStart = 1;
    byte majorOpcode = in.readCard8();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int provider = in.readCard32();
    javaStart += 4;
    int property = in.readCard32();
    javaStart += 4;
    boolean pending = in.readBool();
    javaStart += 1;
    boolean range = in.readBool();
    javaStart += 1;
    byte[] pad7 = in.readPad(2);
    javaStart += 2;
    IntList values = in.readInt32(Short.toUnsignedInt(length) - javaStart);
    javaBuilder.provider(provider);
    javaBuilder.property(property);
    javaBuilder.pending(pending);
    javaBuilder.range(range);
    javaBuilder.values(values.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(provider);
    out.writeCard32(property);
    out.writeBool(pending);
    out.writeBool(range);
    out.writePad(2);
    out.writeInt32(values);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 16 + 4 * values.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ConfigureProviderPropertyBuilder {
    @java.lang.SuppressWarnings("all")
    private int provider;
    @java.lang.SuppressWarnings("all")
    private int property;
    @java.lang.SuppressWarnings("all")
    private boolean pending;
    @java.lang.SuppressWarnings("all")
    private boolean range;
    @java.lang.SuppressWarnings("all")
    private IntList values;

    public int getSize() {
      return 16 + 4 * values.size();
    }

    @java.lang.SuppressWarnings("all")
    ConfigureProviderPropertyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConfigureProviderProperty.ConfigureProviderPropertyBuilder provider(final int provider) {
      this.provider = provider;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConfigureProviderProperty.ConfigureProviderPropertyBuilder property(final int property) {
      this.property = property;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConfigureProviderProperty.ConfigureProviderPropertyBuilder pending(final boolean pending) {
      this.pending = pending;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConfigureProviderProperty.ConfigureProviderPropertyBuilder range(final boolean range) {
      this.range = range;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConfigureProviderProperty.ConfigureProviderPropertyBuilder values(final IntList values) {
      if (values == null) {
        throw new java.lang.NullPointerException("values is marked non-null but is null");
      }
      this.values = values;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ConfigureProviderProperty build() {
      return new ConfigureProviderProperty(this.provider, this.property, this.pending, this.range, this.values);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ConfigureProviderProperty.ConfigureProviderPropertyBuilder(provider=" + this.provider + ", property=" + this.property + ", pending=" + this.pending + ", range=" + this.range + ", values=" + this.values + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ConfigureProviderProperty(final int provider, final int property, final boolean pending, final boolean range, final IntList values) {
    if (values == null) {
      throw new java.lang.NullPointerException("values is marked non-null but is null");
    }
    this.provider = provider;
    this.property = property;
    this.pending = pending;
    this.range = range;
    this.values = values;
  }

  @java.lang.SuppressWarnings("all")
  public static ConfigureProviderProperty.ConfigureProviderPropertyBuilder builder() {
    return new ConfigureProviderProperty.ConfigureProviderPropertyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getProvider() {
    return this.provider;
  }

  @java.lang.SuppressWarnings("all")
  public int getProperty() {
    return this.property;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isPending() {
    return this.pending;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isRange() {
    return this.range;
  }

  @java.lang.SuppressWarnings("all")
  public IntList getValues() {
    return this.values;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ConfigureProviderProperty)) return false;
    final ConfigureProviderProperty other = (ConfigureProviderProperty) o;
    if (this.getProvider() != other.getProvider()) return false;
    if (this.getProperty() != other.getProperty()) return false;
    if (this.isPending() != other.isPending()) return false;
    if (this.isRange() != other.isRange()) return false;
    final java.lang.Object this$values = this.getValues();
    final java.lang.Object other$values = other.getValues();
    if (this$values == null ? other$values != null : !this$values.equals(other$values)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getProvider();
    result = result * PRIME + this.getProperty();
    result = result * PRIME + (this.isPending() ? 79 : 97);
    result = result * PRIME + (this.isRange() ? 79 : 97);
    final java.lang.Object $values = this.getValues();
    result = result * PRIME + ($values == null ? 43 : $values.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ConfigureProviderProperty(provider=" + this.getProvider() + ", property=" + this.getProperty() + ", pending=" + this.isPending() + ", range=" + this.isRange() + ", values=" + this.getValues() + ")";
  }
}
