package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class ProviderProperty implements NotifyDataUnion, XStruct {
  public static final String PLUGIN_NAME = "randr";
  private final int window;
  private final int provider;
  private final int atom;
  private final int timestamp;
  private final byte state;

  public static ProviderProperty readProviderProperty(X11Input in) throws IOException {
    ProviderProperty.ProviderPropertyBuilder javaBuilder = ProviderProperty.builder();
    int window = in.readCard32();
    int provider = in.readCard32();
    int atom = in.readCard32();
    int timestamp = in.readCard32();
    byte state = in.readCard8();
    byte[] pad5 = in.readPad(11);
    javaBuilder.window(window);
    javaBuilder.provider(provider);
    javaBuilder.atom(atom);
    javaBuilder.timestamp(timestamp);
    javaBuilder.state(state);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(window);
    out.writeCard32(provider);
    out.writeCard32(atom);
    out.writeCard32(timestamp);
    out.writeCard8(state);
    out.writePad(11);
  }

  @Override
  public int getSize() {
    return 28;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ProviderPropertyBuilder {
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private int provider;
    @java.lang.SuppressWarnings("all")
    private int atom;
    @java.lang.SuppressWarnings("all")
    private int timestamp;
    @java.lang.SuppressWarnings("all")
    private byte state;

    public int getSize() {
      return 28;
    }

    @java.lang.SuppressWarnings("all")
    ProviderPropertyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ProviderProperty.ProviderPropertyBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ProviderProperty.ProviderPropertyBuilder provider(final int provider) {
      this.provider = provider;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ProviderProperty.ProviderPropertyBuilder atom(final int atom) {
      this.atom = atom;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ProviderProperty.ProviderPropertyBuilder timestamp(final int timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ProviderProperty.ProviderPropertyBuilder state(final byte state) {
      this.state = state;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ProviderProperty build() {
      return new ProviderProperty(this.window, this.provider, this.atom, this.timestamp, this.state);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ProviderProperty.ProviderPropertyBuilder(window=" + this.window + ", provider=" + this.provider + ", atom=" + this.atom + ", timestamp=" + this.timestamp + ", state=" + this.state + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ProviderProperty(final int window, final int provider, final int atom, final int timestamp, final byte state) {
    this.window = window;
    this.provider = provider;
    this.atom = atom;
    this.timestamp = timestamp;
    this.state = state;
  }

  @java.lang.SuppressWarnings("all")
  public static ProviderProperty.ProviderPropertyBuilder builder() {
    return new ProviderProperty.ProviderPropertyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.SuppressWarnings("all")
  public int getProvider() {
    return this.provider;
  }

  @java.lang.SuppressWarnings("all")
  public int getAtom() {
    return this.atom;
  }

  @java.lang.SuppressWarnings("all")
  public int getTimestamp() {
    return this.timestamp;
  }

  @java.lang.SuppressWarnings("all")
  public byte getState() {
    return this.state;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ProviderProperty)) return false;
    final ProviderProperty other = (ProviderProperty) o;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getProvider() != other.getProvider()) return false;
    if (this.getAtom() != other.getAtom()) return false;
    if (this.getTimestamp() != other.getTimestamp()) return false;
    if (this.getState() != other.getState()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getProvider();
    result = result * PRIME + this.getAtom();
    result = result * PRIME + this.getTimestamp();
    result = result * PRIME + this.getState();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ProviderProperty(window=" + this.getWindow() + ", provider=" + this.getProvider() + ", atom=" + this.getAtom() + ", timestamp=" + this.getTimestamp() + ", state=" + this.getState() + ")";
  }
}
