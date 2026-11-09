package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class LeaseNotify implements NotifyDataUnion, XStruct {
  public static final String PLUGIN_NAME = "randr";
  private final int timestamp;
  private final int window;
  private final int lease;
  private final byte created;

  public static LeaseNotify readLeaseNotify(X11Input in) throws IOException {
    LeaseNotify.LeaseNotifyBuilder javaBuilder = LeaseNotify.builder();
    int timestamp = in.readCard32();
    int window = in.readCard32();
    int lease = in.readCard32();
    byte created = in.readCard8();
    byte[] pad4 = in.readPad(15);
    javaBuilder.timestamp(timestamp);
    javaBuilder.window(window);
    javaBuilder.lease(lease);
    javaBuilder.created(created);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(timestamp);
    out.writeCard32(window);
    out.writeCard32(lease);
    out.writeCard8(created);
    out.writePad(15);
  }

  @Override
  public int getSize() {
    return 28;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class LeaseNotifyBuilder {
    @java.lang.SuppressWarnings("all")
    private int timestamp;
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private int lease;
    @java.lang.SuppressWarnings("all")
    private byte created;

    public int getSize() {
      return 28;
    }

    @java.lang.SuppressWarnings("all")
    LeaseNotifyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public LeaseNotify.LeaseNotifyBuilder timestamp(final int timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public LeaseNotify.LeaseNotifyBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public LeaseNotify.LeaseNotifyBuilder lease(final int lease) {
      this.lease = lease;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public LeaseNotify.LeaseNotifyBuilder created(final byte created) {
      this.created = created;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public LeaseNotify build() {
      return new LeaseNotify(this.timestamp, this.window, this.lease, this.created);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "LeaseNotify.LeaseNotifyBuilder(timestamp=" + this.timestamp + ", window=" + this.window + ", lease=" + this.lease + ", created=" + this.created + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  LeaseNotify(final int timestamp, final int window, final int lease, final byte created) {
    this.timestamp = timestamp;
    this.window = window;
    this.lease = lease;
    this.created = created;
  }

  @java.lang.SuppressWarnings("all")
  public static LeaseNotify.LeaseNotifyBuilder builder() {
    return new LeaseNotify.LeaseNotifyBuilder();
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
  public int getLease() {
    return this.lease;
  }

  @java.lang.SuppressWarnings("all")
  public byte getCreated() {
    return this.created;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof LeaseNotify)) return false;
    final LeaseNotify other = (LeaseNotify) o;
    if (this.getTimestamp() != other.getTimestamp()) return false;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getLease() != other.getLease()) return false;
    if (this.getCreated() != other.getCreated()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getTimestamp();
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getLease();
    result = result * PRIME + this.getCreated();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "LeaseNotify(timestamp=" + this.getTimestamp() + ", window=" + this.getWindow() + ", lease=" + this.getLease() + ", created=" + this.getCreated() + ")";
  }
}
