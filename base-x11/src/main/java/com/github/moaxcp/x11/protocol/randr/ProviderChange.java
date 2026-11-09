package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class ProviderChange implements NotifyDataUnion, XStruct {
  public static final String PLUGIN_NAME = "randr";
  private final int timestamp;
  private final int window;
  private final int provider;

  public static ProviderChange readProviderChange(X11Input in) throws IOException {
    ProviderChange.ProviderChangeBuilder javaBuilder = ProviderChange.builder();
    int timestamp = in.readCard32();
    int window = in.readCard32();
    int provider = in.readCard32();
    byte[] pad3 = in.readPad(16);
    javaBuilder.timestamp(timestamp);
    javaBuilder.window(window);
    javaBuilder.provider(provider);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(timestamp);
    out.writeCard32(window);
    out.writeCard32(provider);
    out.writePad(16);
  }

  @Override
  public int getSize() {
    return 28;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ProviderChangeBuilder {
    @java.lang.SuppressWarnings("all")
    private int timestamp;
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private int provider;

    public int getSize() {
      return 28;
    }

    @java.lang.SuppressWarnings("all")
    ProviderChangeBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ProviderChange.ProviderChangeBuilder timestamp(final int timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ProviderChange.ProviderChangeBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ProviderChange.ProviderChangeBuilder provider(final int provider) {
      this.provider = provider;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ProviderChange build() {
      return new ProviderChange(this.timestamp, this.window, this.provider);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ProviderChange.ProviderChangeBuilder(timestamp=" + this.timestamp + ", window=" + this.window + ", provider=" + this.provider + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ProviderChange(final int timestamp, final int window, final int provider) {
    this.timestamp = timestamp;
    this.window = window;
    this.provider = provider;
  }

  @java.lang.SuppressWarnings("all")
  public static ProviderChange.ProviderChangeBuilder builder() {
    return new ProviderChange.ProviderChangeBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getTimestamp() {
    return this.timestamp;
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.SuppressWarnings("all")
  public int getProvider() {
    return this.provider;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ProviderChange)) return false;
    final ProviderChange other = (ProviderChange) o;
    if (this.getTimestamp() != other.getTimestamp()) return false;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getProvider() != other.getProvider()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getTimestamp();
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getProvider();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ProviderChange(timestamp=" + this.getTimestamp() + ", window=" + this.getWindow() + ", provider=" + this.getProvider() + ")";
  }
}
