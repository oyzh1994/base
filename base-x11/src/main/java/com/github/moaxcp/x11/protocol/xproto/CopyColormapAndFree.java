package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class CopyColormapAndFree implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 80;
  private final int mid;
  private final int srcCmap;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CopyColormapAndFree readCopyColormapAndFree(X11Input in) throws IOException {
    CopyColormapAndFree.CopyColormapAndFreeBuilder javaBuilder = CopyColormapAndFree.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int mid = in.readCard32();
    int srcCmap = in.readCard32();
    javaBuilder.mid(mid);
    javaBuilder.srcCmap(srcCmap);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(mid);
    out.writeCard32(srcCmap);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CopyColormapAndFreeBuilder {
    @java.lang.SuppressWarnings("all")
    private int mid;
    @java.lang.SuppressWarnings("all")
    private int srcCmap;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    CopyColormapAndFreeBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CopyColormapAndFree.CopyColormapAndFreeBuilder mid(final int mid) {
      this.mid = mid;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CopyColormapAndFree.CopyColormapAndFreeBuilder srcCmap(final int srcCmap) {
      this.srcCmap = srcCmap;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CopyColormapAndFree build() {
      return new CopyColormapAndFree(this.mid, this.srcCmap);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CopyColormapAndFree.CopyColormapAndFreeBuilder(mid=" + this.mid + ", srcCmap=" + this.srcCmap + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CopyColormapAndFree(final int mid, final int srcCmap) {
    this.mid = mid;
    this.srcCmap = srcCmap;
  }

  @java.lang.SuppressWarnings("all")
  public static CopyColormapAndFree.CopyColormapAndFreeBuilder builder() {
    return new CopyColormapAndFree.CopyColormapAndFreeBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getMid() {
    return this.mid;
  }

  @java.lang.SuppressWarnings("all")
  public int getSrcCmap() {
    return this.srcCmap;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CopyColormapAndFree)) return false;
    final CopyColormapAndFree other = (CopyColormapAndFree) o;
    if (this.getMid() != other.getMid()) return false;
    if (this.getSrcCmap() != other.getSrcCmap()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getMid();
    result = result * PRIME + this.getSrcCmap();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CopyColormapAndFree(mid=" + this.getMid() + ", srcCmap=" + this.getSrcCmap() + ")";
  }
}
