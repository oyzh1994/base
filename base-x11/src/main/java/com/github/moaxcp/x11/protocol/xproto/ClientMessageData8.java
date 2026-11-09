package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Output;
import org.eclipse.collections.api.list.primitive.ByteList;
import java.io.IOException;

public final class ClientMessageData8 implements ClientMessageDataUnion {
  public static final String PLUGIN_NAME = "xproto";
  private final ByteList data8;
  private final byte format = 8;

  public ClientMessageData8(ByteList data8) {
    if (data8 == null) {
      throw new java.lang.NullPointerException("data8 is marked non-null but is null");
    }
    if (data8.size() != 20) {
      throw new IllegalArgumentException("data8 must have length of 20. Got: \"" + data8.size() + "\"");
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
  public ByteList getData8() {
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
    final java.lang.Object this$data8 = this.getData8();
    final java.lang.Object other$data8 = other.getData8();
    if (this$data8 == null ? other$data8 != null : !this$data8.equals(other$data8)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFormat();
    final java.lang.Object $data8 = this.getData8();
    result = result * PRIME + ($data8 == null ? 43 : $data8.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ClientMessageData8(data8=" + this.getData8() + ", format=" + this.getFormat() + ")";
  }
}
