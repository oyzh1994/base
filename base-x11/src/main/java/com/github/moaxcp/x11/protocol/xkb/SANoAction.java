package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class SANoAction implements ActionUnion, XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final byte type;

  public static SANoAction readSANoAction(X11Input in) throws IOException {
    SANoAction.SANoActionBuilder javaBuilder = SANoAction.builder();
    byte type = in.readCard8();
    byte[] pad1 = in.readPad(7);
    javaBuilder.type(type);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(type);
    out.writePad(7);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SANoActionBuilder {
    @java.lang.SuppressWarnings("all")
    private byte type;

    public SANoAction.SANoActionBuilder type(SAType type) {
      this.type = (byte) type.getValue();
      return this;
    }

    public SANoAction.SANoActionBuilder type(byte type) {
      this.type = type;
      return this;
    }

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    SANoActionBuilder() {
    }

    @java.lang.SuppressWarnings("all")
    public SANoAction build() {
      return new SANoAction(this.type);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SANoAction.SANoActionBuilder(type=" + this.type + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SANoAction(final byte type) {
    this.type = type;
  }

  @java.lang.SuppressWarnings("all")
  public static SANoAction.SANoActionBuilder builder() {
    return new SANoAction.SANoActionBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getType() {
    return this.type;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SANoAction)) return false;
    final SANoAction other = (SANoAction) o;
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
    return "SANoAction(type=" + this.getType() + ")";
  }
}
