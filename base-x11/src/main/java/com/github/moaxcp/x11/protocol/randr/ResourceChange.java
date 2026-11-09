package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class ResourceChange implements NotifyDataUnion, XStruct {
  public static final String PLUGIN_NAME = "randr";
  private final int timestamp;
  private final int window;

  public static ResourceChange readResourceChange(X11Input in) throws IOException {
    ResourceChange.ResourceChangeBuilder javaBuilder = ResourceChange.builder();
    int timestamp = in.readCard32();
    int window = in.readCard32();
    byte[] pad2 = in.readPad(20);
    javaBuilder.timestamp(timestamp);
    javaBuilder.window(window);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(timestamp);
    out.writeCard32(window);
    out.writePad(20);
  }

  @Override
  public int getSize() {
    return 28;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ResourceChangeBuilder {
    @java.lang.SuppressWarnings("all")
    private int timestamp;
    @java.lang.SuppressWarnings("all")
    private int window;

    public int getSize() {
      return 28;
    }

    @java.lang.SuppressWarnings("all")
    ResourceChangeBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ResourceChange.ResourceChangeBuilder timestamp(final int timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ResourceChange.ResourceChangeBuilder window(final int window) {
      this.window = window;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ResourceChange build() {
      return new ResourceChange(this.timestamp, this.window);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ResourceChange.ResourceChangeBuilder(timestamp=" + this.timestamp + ", window=" + this.window + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ResourceChange(final int timestamp, final int window) {
    this.timestamp = timestamp;
    this.window = window;
  }

  @java.lang.SuppressWarnings("all")
  public static ResourceChange.ResourceChangeBuilder builder() {
    return new ResourceChange.ResourceChangeBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getTimestamp() {
    return this.timestamp;
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ResourceChange)) return false;
    final ResourceChange other = (ResourceChange) o;
    if (this.getTimestamp() != other.getTimestamp()) return false;
    if (this.getWindow() != other.getWindow()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getTimestamp();
    result = result * PRIME + this.getWindow();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ResourceChange(timestamp=" + this.getTimestamp() + ", window=" + this.getWindow() + ")";
  }
}
