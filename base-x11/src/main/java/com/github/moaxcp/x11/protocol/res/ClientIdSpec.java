package com.github.moaxcp.x11.protocol.res;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class ClientIdSpec implements XStruct {
  public static final String PLUGIN_NAME = "res";
  private final int client;
  private final int mask;

  public static ClientIdSpec readClientIdSpec(X11Input in) throws IOException {
    ClientIdSpec.ClientIdSpecBuilder javaBuilder = ClientIdSpec.builder();
    int client = in.readCard32();
    int mask = in.readCard32();
    javaBuilder.client(client);
    javaBuilder.mask(mask);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(client);
    out.writeCard32(mask);
  }

  public boolean isMaskEnabled(ClientIdMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (ClientIdMask m : maskEnums) {
      if (!m.isEnabled(mask)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ClientIdSpecBuilder {
    @java.lang.SuppressWarnings("all")
    private int client;
    @java.lang.SuppressWarnings("all")
    private int mask;

    public boolean isMaskEnabled(ClientIdMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (ClientIdMask m : maskEnums) {
        if (!m.isEnabled(mask)) {
          return false;
        }
      }
      return true;
    }

    public ClientIdSpec.ClientIdSpecBuilder maskEnable(ClientIdMask... maskEnums) {
      for (ClientIdMask m : maskEnums) {
        mask((int) m.enableFor(mask));
      }
      return this;
    }

    public ClientIdSpec.ClientIdSpecBuilder maskDisable(ClientIdMask... maskEnums) {
      for (ClientIdMask m : maskEnums) {
        mask((int) m.disableFor(mask));
      }
      return this;
    }

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    ClientIdSpecBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ClientIdSpec.ClientIdSpecBuilder client(final int client) {
      this.client = client;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ClientIdSpec.ClientIdSpecBuilder mask(final int mask) {
      this.mask = mask;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ClientIdSpec build() {
      return new ClientIdSpec(this.client, this.mask);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ClientIdSpec.ClientIdSpecBuilder(client=" + this.client + ", mask=" + this.mask + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ClientIdSpec(final int client, final int mask) {
    this.client = client;
    this.mask = mask;
  }

  @java.lang.SuppressWarnings("all")
  public static ClientIdSpec.ClientIdSpecBuilder builder() {
    return new ClientIdSpec.ClientIdSpecBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getClient() {
    return this.client;
  }

  @java.lang.SuppressWarnings("all")
  public int getMask() {
    return this.mask;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ClientIdSpec)) return false;
    final ClientIdSpec other = (ClientIdSpec) o;
    if (this.getClient() != other.getClient()) return false;
    if (this.getMask() != other.getMask()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getClient();
    result = result * PRIME + this.getMask();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ClientIdSpec(client=" + this.getClient() + ", mask=" + this.getMask() + ")";
  }
}
