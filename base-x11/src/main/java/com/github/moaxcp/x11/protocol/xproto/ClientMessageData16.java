package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class ClientMessageData16 implements ClientMessageDataUnion {
  public static final String PLUGIN_NAME = "xproto";
  private final short[] data16;
  private final byte format = 16;

  public ClientMessageData16(short[] data16) {
    if (data16 == null) {
      throw new java.lang.NullPointerException("data16 is marked non-null but is null");
    }
    if (data16.length != 10) {
      throw new IllegalArgumentException("data16 must have length of 10. Got: \"" + data16.length + "\"");
    }
    this.data16 = data16;
  }

  @Override
  public int getSize() {
    return 20;
  }

  @Override
  public String getPluginName() {
    return PLUGIN_NAME;
  }

  public void write(X11Output out) throws IOException {
    out.writeCard16(data16);
  }

  @java.lang.SuppressWarnings("all")
  public short[] getData16() {
    return this.data16;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFormat() {
    return this.format;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ClientMessageData16)) return false;
    final ClientMessageData16 other = (ClientMessageData16) o;
    if (this.getFormat() != other.getFormat()) return false;
    if (!java.util.Arrays.equals(this.getData16(), other.getData16())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFormat();
    result = result * PRIME + java.util.Arrays.hashCode(this.getData16());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ClientMessageData16(data16=" + java.util.Arrays.toString(this.getData16()) + ", format=" + this.getFormat() + ")";
  }
}
