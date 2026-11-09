package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class CommonBehavior implements BehaviorUnion, XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final byte type;
  private final byte data;

  public static CommonBehavior readCommonBehavior(X11Input in) throws IOException {
    CommonBehavior.CommonBehaviorBuilder javaBuilder = CommonBehavior.builder();
    byte type = in.readCard8();
    byte data = in.readCard8();
    javaBuilder.type(type);
    javaBuilder.data(data);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(type);
    out.writeCard8(data);
  }

  @Override
  public int getSize() {
    return 2;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CommonBehaviorBuilder {
    @java.lang.SuppressWarnings("all")
    private byte type;
    @java.lang.SuppressWarnings("all")
    private byte data;

    public int getSize() {
      return 2;
    }

    @java.lang.SuppressWarnings("all")
    CommonBehaviorBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CommonBehavior.CommonBehaviorBuilder type(final byte type) {
      this.type = type;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CommonBehavior.CommonBehaviorBuilder data(final byte data) {
      this.data = data;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CommonBehavior build() {
      return new CommonBehavior(this.type, this.data);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CommonBehavior.CommonBehaviorBuilder(type=" + this.type + ", data=" + this.data + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CommonBehavior(final byte type, final byte data) {
    this.type = type;
    this.data = data;
  }

  @java.lang.SuppressWarnings("all")
  public static CommonBehavior.CommonBehaviorBuilder builder() {
    return new CommonBehavior.CommonBehaviorBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getType() {
    return this.type;
  }

  @java.lang.SuppressWarnings("all")
  public byte getData() {
    return this.data;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CommonBehavior)) return false;
    final CommonBehavior other = (CommonBehavior) o;
    if (this.getType() != other.getType()) return false;
    if (this.getData() != other.getData()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getType();
    result = result * PRIME + this.getData();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CommonBehavior(type=" + this.getType() + ", data=" + this.getData() + ")";
  }
}
