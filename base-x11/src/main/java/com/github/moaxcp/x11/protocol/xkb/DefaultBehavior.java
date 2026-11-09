package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class DefaultBehavior implements BehaviorUnion, XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final byte type;

  public static DefaultBehavior readDefaultBehavior(X11Input in) throws IOException {
    DefaultBehavior.DefaultBehaviorBuilder javaBuilder = DefaultBehavior.builder();
    byte type = in.readCard8();
    byte[] pad1 = in.readPad(1);
    javaBuilder.type(type);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(type);
    out.writePad(1);
  }

  @Override
  public int getSize() {
    return 2;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class DefaultBehaviorBuilder {
    @java.lang.SuppressWarnings("all")
    private byte type;

    public int getSize() {
      return 2;
    }

    @java.lang.SuppressWarnings("all")
    DefaultBehaviorBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public DefaultBehavior.DefaultBehaviorBuilder type(final byte type) {
      this.type = type;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public DefaultBehavior build() {
      return new DefaultBehavior(this.type);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "DefaultBehavior.DefaultBehaviorBuilder(type=" + this.type + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  DefaultBehavior(final byte type) {
    this.type = type;
  }

  @java.lang.SuppressWarnings("all")
  public static DefaultBehavior.DefaultBehaviorBuilder builder() {
    return new DefaultBehavior.DefaultBehaviorBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getType() {
    return this.type;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof DefaultBehavior)) return false;
    final DefaultBehavior other = (DefaultBehavior) o;
    if (this.getType() != other.getType()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getType();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "DefaultBehavior(type=" + this.getType() + ")";
  }
}
