package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class InstallColormap implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 81;
  private final int cmap;

  public byte getOpCode() {
    return OPCODE;
  }

  public static InstallColormap readInstallColormap(X11Input in) throws IOException {
    InstallColormap.InstallColormapBuilder javaBuilder = InstallColormap.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int cmap = in.readCard32();
    javaBuilder.cmap(cmap);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(cmap);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class InstallColormapBuilder {
    @java.lang.SuppressWarnings("all")
    private int cmap;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    InstallColormapBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public InstallColormap.InstallColormapBuilder cmap(final int cmap) {
      this.cmap = cmap;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public InstallColormap build() {
      return new InstallColormap(this.cmap);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "InstallColormap.InstallColormapBuilder(cmap=" + this.cmap + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  InstallColormap(final int cmap) {
    this.cmap = cmap;
  }

  @java.lang.SuppressWarnings("all")
  public static InstallColormap.InstallColormapBuilder builder() {
    return new InstallColormap.InstallColormapBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getCmap() {
    return this.cmap;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof InstallColormap)) return false;
    final InstallColormap other = (InstallColormap) o;
    if (this.getCmap() != other.getCmap()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getCmap();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "InstallColormap(cmap=" + this.getCmap() + ")";
  }
}
