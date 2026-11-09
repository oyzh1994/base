package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import java.util.Arrays;

public final class ClientMessageData32 implements ClientMessageDataUnion {
  public static final String PLUGIN_NAME = "xproto";
  private final int[] data32;
  private final byte format = 32;

  public ClientMessageData32(int... data32) {
    if (data32.length > 5) {
      throw new IllegalArgumentException("data32 must have length < 20 bytes. Got: \"" + data32.length + "\"");
    }
    int[] values = new int[5];
    System.arraycopy(data32, 0, values, 0, data32.length);
    this.data32 = values;
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
    out.writeCard32(data32);
  }

  @java.lang.SuppressWarnings("all")
  public int[] getData32() {
    return this.data32;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFormat() {
    return this.format;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ClientMessageData32)) return false;
    final ClientMessageData32 other = (ClientMessageData32) o;
    if (this.getFormat() != other.getFormat()) return false;
    if (!Arrays.equals(this.getData32(), other.getData32())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFormat();
    result = result * PRIME + Arrays.hashCode(this.getData32());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ClientMessageData32(data32=" + Arrays.toString(this.getData32()) + ", format=" + this.getFormat() + ")";
  }
}
