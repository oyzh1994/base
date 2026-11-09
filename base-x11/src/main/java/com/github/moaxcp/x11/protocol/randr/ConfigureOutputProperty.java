package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.IntList;

public final class ConfigureOutputProperty implements OneWayRequest {
  public static final String PLUGIN_NAME = "randr";
  public static final byte OPCODE = 12;
  private final int output;
  private final int property;
  private final boolean pending;
  private final boolean range;
  private final IntList values;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ConfigureOutputProperty readConfigureOutputProperty(X11Input in) throws IOException {
    ConfigureOutputProperty.ConfigureOutputPropertyBuilder javaBuilder = ConfigureOutputProperty.builder();
    int javaStart = 1;
    byte majorOpcode = in.readCard8();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int output = in.readCard32();
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
    javaBuilder.output(output);
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
    out.writeCard32(output);
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


  public static class ConfigureOutputPropertyBuilder {
    @java.lang.SuppressWarnings("all")
    private int output;
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
    ConfigureOutputPropertyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConfigureOutputProperty.ConfigureOutputPropertyBuilder output(final int output) {
      this.output = output;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConfigureOutputProperty.ConfigureOutputPropertyBuilder property(final int property) {
      this.property = property;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConfigureOutputProperty.ConfigureOutputPropertyBuilder pending(final boolean pending) {
      this.pending = pending;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConfigureOutputProperty.ConfigureOutputPropertyBuilder range(final boolean range) {
      this.range = range;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConfigureOutputProperty.ConfigureOutputPropertyBuilder values(final IntList values) {
      if (values == null) {
        throw new java.lang.NullPointerException("values is marked non-null but is null");
      }
      this.values = values;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ConfigureOutputProperty build() {
      return new ConfigureOutputProperty(this.output, this.property, this.pending, this.range, this.values);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ConfigureOutputProperty.ConfigureOutputPropertyBuilder(output=" + this.output + ", property=" + this.property + ", pending=" + this.pending + ", range=" + this.range + ", values=" + this.values + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ConfigureOutputProperty(final int output, final int property, final boolean pending, final boolean range, final IntList values) {
    if (values == null) {
      throw new java.lang.NullPointerException("values is marked non-null but is null");
    }
    this.output = output;
    this.property = property;
    this.pending = pending;
    this.range = range;
    this.values = values;
  }

  @java.lang.SuppressWarnings("all")
  public static ConfigureOutputProperty.ConfigureOutputPropertyBuilder builder() {
    return new ConfigureOutputProperty.ConfigureOutputPropertyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getOutput() {
    return this.output;
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
    if (!(o instanceof ConfigureOutputProperty)) return false;
    final ConfigureOutputProperty other = (ConfigureOutputProperty) o;
    if (this.getOutput() != other.getOutput()) return false;
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
    result = result * PRIME + this.getOutput();
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
    return "ConfigureOutputProperty(output=" + this.getOutput() + ", property=" + this.getProperty() + ", pending=" + this.isPending() + ", range=" + this.isRange() + ", values=" + this.getValues() + ")";
  }
}
