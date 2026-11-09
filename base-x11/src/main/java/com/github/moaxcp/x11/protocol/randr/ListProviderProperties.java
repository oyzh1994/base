package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class ListProviderProperties implements TwoWayRequest<ListProviderPropertiesReply> {
  public static final String PLUGIN_NAME = "randr";
  public static final byte OPCODE = 36;
  private final int provider;

  public XReplyFunction<ListProviderPropertiesReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> ListProviderPropertiesReply.readListProviderPropertiesReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static ListProviderProperties readListProviderProperties(X11Input in) throws IOException {
    ListProviderProperties.ListProviderPropertiesBuilder javaBuilder = ListProviderProperties.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int provider = in.readCard32();
    javaBuilder.provider(provider);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(provider);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ListProviderPropertiesBuilder {
    @java.lang.SuppressWarnings("all")
    private int provider;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    ListProviderPropertiesBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListProviderProperties.ListProviderPropertiesBuilder provider(final int provider) {
      this.provider = provider;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ListProviderProperties build() {
      return new ListProviderProperties(this.provider);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ListProviderProperties.ListProviderPropertiesBuilder(provider=" + this.provider + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ListProviderProperties(final int provider) {
    this.provider = provider;
  }

  @java.lang.SuppressWarnings("all")
  public static ListProviderProperties.ListProviderPropertiesBuilder builder() {
    return new ListProviderProperties.ListProviderPropertiesBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getProvider() {
    return this.provider;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ListProviderProperties)) return false;
    final ListProviderProperties other = (ListProviderProperties) o;
    if (this.getProvider() != other.getProvider()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getProvider();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ListProviderProperties(provider=" + this.getProvider() + ")";
  }
}
