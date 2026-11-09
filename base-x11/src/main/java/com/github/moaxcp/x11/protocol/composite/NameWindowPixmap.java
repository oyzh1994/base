package com.github.moaxcp.x11.protocol.composite;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class NameWindowPixmap implements OneWayRequest {
  public static final String PLUGIN_NAME = "composite";
  public static final byte OPCODE = 6;
  private final int window;
  private final int pixmap;

  public byte getOpCode() {
    return OPCODE;
  }

  public static NameWindowPixmap readNameWindowPixmap(X11Input in) throws IOException {
    NameWindowPixmap.NameWindowPixmapBuilder javaBuilder = NameWindowPixmap.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    int pixmap = in.readCard32();
    javaBuilder.window(window);
    javaBuilder.pixmap(pixmap);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(pixmap);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class NameWindowPixmapBuilder {
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private int pixmap;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    NameWindowPixmapBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NameWindowPixmap.NameWindowPixmapBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NameWindowPixmap.NameWindowPixmapBuilder pixmap(final int pixmap) {
      this.pixmap = pixmap;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public NameWindowPixmap build() {
      return new NameWindowPixmap(this.window, this.pixmap);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "NameWindowPixmap.NameWindowPixmapBuilder(window=" + this.window + ", pixmap=" + this.pixmap + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  NameWindowPixmap(final int window, final int pixmap) {
    this.window = window;
    this.pixmap = pixmap;
  }

  @java.lang.SuppressWarnings("all")
  public static NameWindowPixmap.NameWindowPixmapBuilder builder() {
    return new NameWindowPixmap.NameWindowPixmapBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.SuppressWarnings("all")
  public int getPixmap() {
    return this.pixmap;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof NameWindowPixmap)) return false;
    final NameWindowPixmap other = (NameWindowPixmap) o;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getPixmap() != other.getPixmap()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getPixmap();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "NameWindowPixmap(window=" + this.getWindow() + ", pixmap=" + this.getPixmap() + ")";
  }
}
