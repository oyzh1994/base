package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class OverlayBehavior implements BehaviorUnion, XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final byte type;
  private final byte key;

  public static OverlayBehavior readOverlayBehavior(X11Input in) throws IOException {
    OverlayBehavior.OverlayBehaviorBuilder javaBuilder = OverlayBehavior.builder();
    byte type = in.readCard8();
    byte key = in.readCard8();
    javaBuilder.type(type);
    javaBuilder.key(key);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(type);
    out.writeCard8(key);
  }

  @Override
  public int getSize() {
    return 2;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class OverlayBehaviorBuilder {
    @java.lang.SuppressWarnings("all")
    private byte type;
    @java.lang.SuppressWarnings("all")
    private byte key;

    public int getSize() {
      return 2;
    }

    @java.lang.SuppressWarnings("all")
    OverlayBehaviorBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public OverlayBehavior.OverlayBehaviorBuilder type(final byte type) {
      this.type = type;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public OverlayBehavior.OverlayBehaviorBuilder key(final byte key) {
      this.key = key;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public OverlayBehavior build() {
      return new OverlayBehavior(this.type, this.key);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "OverlayBehavior.OverlayBehaviorBuilder(type=" + this.type + ", key=" + this.key + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  OverlayBehavior(final byte type, final byte key) {
    this.type = type;
    this.key = key;
  }

  @java.lang.SuppressWarnings("all")
  public static OverlayBehavior.OverlayBehaviorBuilder builder() {
    return new OverlayBehavior.OverlayBehaviorBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getType() {
    return this.type;
  }

  @java.lang.SuppressWarnings("all")
  public byte getKey() {
    return this.key;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof OverlayBehavior)) return false;
    final OverlayBehavior other = (OverlayBehavior) o;
    if (this.getType() != other.getType()) return false;
    if (this.getKey() != other.getKey()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getType();
    result = result * PRIME + this.getKey();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "OverlayBehavior(type=" + this.getType() + ", key=" + this.getKey() + ")";
  }
}
