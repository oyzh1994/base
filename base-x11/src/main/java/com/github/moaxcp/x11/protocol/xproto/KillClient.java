package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class KillClient implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 113;
  private final int resource;

  public byte getOpCode() {
    return OPCODE;
  }

  public static KillClient readKillClient(X11Input in) throws IOException {
    KillClient.KillClientBuilder javaBuilder = KillClient.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int resource = in.readCard32();
    javaBuilder.resource(resource);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(resource);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class KillClientBuilder {
    @java.lang.SuppressWarnings("all")
    private int resource;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    KillClientBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public KillClient.KillClientBuilder resource(final int resource) {
      this.resource = resource;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public KillClient build() {
      return new KillClient(this.resource);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "KillClient.KillClientBuilder(resource=" + this.resource + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  KillClient(final int resource) {
    this.resource = resource;
  }

  @java.lang.SuppressWarnings("all")
  public static KillClient.KillClientBuilder builder() {
    return new KillClient.KillClientBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getResource() {
    return this.resource;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof KillClient)) return false;
    final KillClient other = (KillClient) o;
    if (this.getResource() != other.getResource()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getResource();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "KillClient(resource=" + this.getResource() + ")";
  }
}
