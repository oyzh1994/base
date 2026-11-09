package com.github.moaxcp.x11.protocol.res;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class Client implements XStruct {
  public static final String PLUGIN_NAME = "res";
  private final int resourceBase;
  private final int resourceMask;

  public static Client readClient(X11Input in) throws IOException {
    Client.ClientBuilder javaBuilder = Client.builder();
    int resourceBase = in.readCard32();
    int resourceMask = in.readCard32();
    javaBuilder.resourceBase(resourceBase);
    javaBuilder.resourceMask(resourceMask);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(resourceBase);
    out.writeCard32(resourceMask);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ClientBuilder {
    @java.lang.SuppressWarnings("all")
    private int resourceBase;
    @java.lang.SuppressWarnings("all")
    private int resourceMask;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    ClientBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Client.ClientBuilder resourceBase(final int resourceBase) {
      this.resourceBase = resourceBase;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Client.ClientBuilder resourceMask(final int resourceMask) {
      this.resourceMask = resourceMask;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Client build() {
      return new Client(this.resourceBase, this.resourceMask);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Client.ClientBuilder(resourceBase=" + this.resourceBase + ", resourceMask=" + this.resourceMask + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Client(final int resourceBase, final int resourceMask) {
    this.resourceBase = resourceBase;
    this.resourceMask = resourceMask;
  }

  @java.lang.SuppressWarnings("all")
  public static Client.ClientBuilder builder() {
    return new Client.ClientBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getResourceBase() {
    return this.resourceBase;
  }

  @java.lang.SuppressWarnings("all")
  public int getResourceMask() {
    return this.resourceMask;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Client)) return false;
    final Client other = (Client) o;
    if (this.getResourceBase() != other.getResourceBase()) return false;
    if (this.getResourceMask() != other.getResourceMask()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getResourceBase();
    result = result * PRIME + this.getResourceMask();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Client(resourceBase=" + this.getResourceBase() + ", resourceMask=" + this.getResourceMask() + ")";
  }
}
