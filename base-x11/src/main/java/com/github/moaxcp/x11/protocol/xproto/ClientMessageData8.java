package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class ClientMessageData8 implements ClientMessageDataUnion {
  public static final String PLUGIN_NAME = "xproto";
  private final byte[] data8;
  private final byte format = 8;

  public ClientMessageData8(byte[] data8) {
    if (data8 == null) {
      throw new java.lang.NullPointerException("data8 is marked non-null but is null");
    }
    if (data8.length != 20) {
      throw new IllegalArgumentException("data8 must have length of 20. Got: \"" + data8.length + "\"");
    }
    this.data8 = data8;
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
    out.writeCard8(data8);
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getData8() {
    return this.data8;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFormat() {
    return this.format;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ClientMessageData8)) return false;
    final ClientMessageData8 other = (ClientMessageData8) o;
    if (this.getFormat() != other.getFormat()) return false;
    if (!java.util.Arrays.equals(this.getData8(), other.getData8())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFormat();
    result = result * PRIME + java.util.Arrays.hashCode(this.getData8());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ClientMessageData8(data8=" + java.util.Arrays.toString(this.getData8()) + ", format=" + this.getFormat() + ")";
  }
}
