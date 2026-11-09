package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class DeleteProviderProperty implements OneWayRequest {
  public static final String PLUGIN_NAME = "randr";
  public static final byte OPCODE = 40;
  private final int provider;
  private final int property;

  public byte getOpCode() {
    return OPCODE;
  }

  public static DeleteProviderProperty readDeleteProviderProperty(X11Input in) throws IOException {
    DeleteProviderProperty.DeleteProviderPropertyBuilder javaBuilder = DeleteProviderProperty.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int provider = in.readCard32();
    int property = in.readCard32();
    javaBuilder.provider(provider);
    javaBuilder.property(property);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(provider);
    out.writeCard32(property);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class DeleteProviderPropertyBuilder {
    @java.lang.SuppressWarnings("all")
    private int provider;
    @java.lang.SuppressWarnings("all")
    private int property;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    DeleteProviderPropertyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public DeleteProviderProperty.DeleteProviderPropertyBuilder provider(final int provider) {
      this.provider = provider;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public DeleteProviderProperty.DeleteProviderPropertyBuilder property(final int property) {
      this.property = property;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public DeleteProviderProperty build() {
      return new DeleteProviderProperty(this.provider, this.property);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "DeleteProviderProperty.DeleteProviderPropertyBuilder(provider=" + this.provider + ", property=" + this.property + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  DeleteProviderProperty(final int provider, final int property) {
    this.provider = provider;
    this.property = property;
  }

  @java.lang.SuppressWarnings("all")
  public static DeleteProviderProperty.DeleteProviderPropertyBuilder builder() {
    return new DeleteProviderProperty.DeleteProviderPropertyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getProvider() {
    return this.provider;
  }

  @java.lang.SuppressWarnings("all")
  public int getProperty() {
    return this.property;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof DeleteProviderProperty)) return false;
    final DeleteProviderProperty other = (DeleteProviderProperty) o;
    if (this.getProvider() != other.getProvider()) return false;
    if (this.getProperty() != other.getProperty()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getProvider();
    result = result * PRIME + this.getProperty();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "DeleteProviderProperty(provider=" + this.getProvider() + ", property=" + this.getProperty() + ")";
  }
}
