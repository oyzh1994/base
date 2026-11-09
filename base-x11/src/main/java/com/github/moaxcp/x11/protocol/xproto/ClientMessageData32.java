package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Output;
import org.eclipse.collections.api.factory.primitive.IntLists;
import org.eclipse.collections.api.list.primitive.IntList;
import java.io.IOException;

public final class ClientMessageData32 implements ClientMessageDataUnion {
  public static final String PLUGIN_NAME = "xproto";
  private final IntList data32;
  private final byte format = 32;

  public ClientMessageData32(int... data32) {
    if (data32.length > 5) {
      throw new IllegalArgumentException("data32 must have length < 20 bytes. Got: \"" + data32.length + "\"");
    }
    var list = IntLists.mutable.withInitialCapacity(data32.length);
    for (int data : data32) {
      list.add(data);
    }
    for (int i = data32.length; i < 5; i++) {
      list.add(0);
    }
    this.data32 = list.toImmutable();
  }

  public ClientMessageData32(IntList data32) {
    if (data32 == null) {
      throw new java.lang.NullPointerException("data32 is marked non-null but is null");
    }
    if (data32.size() != 5) {
      throw new IllegalArgumentException("data32 must have length of 5. Got: \"" + data32.size() + "\"");
    }
    this.data32 = data32;
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
  public IntList getData32() {
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
    final java.lang.Object this$data32 = this.getData32();
    final java.lang.Object other$data32 = other.getData32();
    if (this$data32 == null ? other$data32 != null : !this$data32.equals(other$data32)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFormat();
    final java.lang.Object $data32 = this.getData32();
    result = result * PRIME + ($data32 == null ? 43 : $data32.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ClientMessageData32(data32=" + this.getData32() + ", format=" + this.getFormat() + ")";
  }
}
