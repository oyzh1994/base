package com.github.moaxcp.x11.protocol.res;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.IntList;

public final class ClientIdValue implements XStruct {
  public static final String PLUGIN_NAME = "res";
  private final ClientIdSpec spec;
  private final IntList value;

  public static ClientIdValue readClientIdValue(X11Input in) throws IOException {
    ClientIdValue.ClientIdValueBuilder javaBuilder = ClientIdValue.builder();
    ClientIdSpec spec = ClientIdSpec.readClientIdSpec(in);
    int length = in.readCard32();
    IntList value = in.readCard32((int) (Integer.toUnsignedLong(length) / 4));
    javaBuilder.spec(spec);
    javaBuilder.value(value.toImmutable());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    spec.write(out);
    int length = value.size();
    out.writeCard32(length);
    out.writeCard32(value);
  }

  @Override
  public int getSize() {
    return 12 + 4 * value.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ClientIdValueBuilder {
    @java.lang.SuppressWarnings("all")
    private ClientIdSpec spec;
    @java.lang.SuppressWarnings("all")
    private IntList value;

    public int getSize() {
      return 12 + 4 * value.size();
    }

    @java.lang.SuppressWarnings("all")
    ClientIdValueBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ClientIdValue.ClientIdValueBuilder spec(final ClientIdSpec spec) {
      if (spec == null) {
        throw new java.lang.NullPointerException("spec is marked non-null but is null");
      }
      this.spec = spec;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ClientIdValue.ClientIdValueBuilder value(final IntList value) {
      if (value == null) {
        throw new java.lang.NullPointerException("value is marked non-null but is null");
      }
      this.value = value;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ClientIdValue build() {
      return new ClientIdValue(this.spec, this.value);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ClientIdValue.ClientIdValueBuilder(spec=" + this.spec + ", value=" + this.value + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ClientIdValue(final ClientIdSpec spec, final IntList value) {
    if (spec == null) {
      throw new java.lang.NullPointerException("spec is marked non-null but is null");
    }
    if (value == null) {
      throw new java.lang.NullPointerException("value is marked non-null but is null");
    }
    this.spec = spec;
    this.value = value;
  }

  @java.lang.SuppressWarnings("all")
  public static ClientIdValue.ClientIdValueBuilder builder() {
    return new ClientIdValue.ClientIdValueBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public ClientIdSpec getSpec() {
    return this.spec;
  }

  @java.lang.SuppressWarnings("all")
  public IntList getValue() {
    return this.value;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ClientIdValue)) return false;
    final ClientIdValue other = (ClientIdValue) o;
    final java.lang.Object this$spec = this.getSpec();
    final java.lang.Object other$spec = other.getSpec();
    if (this$spec == null ? other$spec != null : !this$spec.equals(other$spec)) return false;
    final java.lang.Object this$value = this.getValue();
    final java.lang.Object other$value = other.getValue();
    if (this$value == null ? other$value != null : !this$value.equals(other$value)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final java.lang.Object $spec = this.getSpec();
    result = result * PRIME + ($spec == null ? 43 : $spec.hashCode());
    final java.lang.Object $value = this.getValue();
    result = result * PRIME + ($value == null ? 43 : $value.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ClientIdValue(spec=" + this.getSpec() + ", value=" + this.getValue() + ")";
  }
}
