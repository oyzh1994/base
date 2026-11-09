package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class FreePixmap implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 54;
  private final int pixmap;

  public byte getOpCode() {
    return OPCODE;
  }

  public static FreePixmap readFreePixmap(X11Input in) throws IOException {
    FreePixmap.FreePixmapBuilder javaBuilder = FreePixmap.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int pixmap = in.readCard32();
    javaBuilder.pixmap(pixmap);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(pixmap);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class FreePixmapBuilder {
    @java.lang.SuppressWarnings("all")
    private int pixmap;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    FreePixmapBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public FreePixmap.FreePixmapBuilder pixmap(final int pixmap) {
      this.pixmap = pixmap;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public FreePixmap build() {
      return new FreePixmap(this.pixmap);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "FreePixmap.FreePixmapBuilder(pixmap=" + this.pixmap + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  FreePixmap(final int pixmap) {
    this.pixmap = pixmap;
  }

  @java.lang.SuppressWarnings("all")
  public static FreePixmap.FreePixmapBuilder builder() {
    return new FreePixmap.FreePixmapBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getPixmap() {
    return this.pixmap;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof FreePixmap)) return false;
    final FreePixmap other = (FreePixmap) o;
    if (this.getPixmap() != other.getPixmap()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getPixmap();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "FreePixmap(pixmap=" + this.getPixmap() + ")";
  }
}
