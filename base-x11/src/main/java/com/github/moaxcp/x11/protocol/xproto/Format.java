package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class Format implements XStruct {
  public static final String PLUGIN_NAME = "xproto";
  private final byte depth;
  private final byte bitsPerPixel;
  private final byte scanlinePad;

  public static Format readFormat(X11Input in) throws IOException {
    Format.FormatBuilder javaBuilder = Format.builder();
    byte depth = in.readCard8();
    byte bitsPerPixel = in.readCard8();
    byte scanlinePad = in.readCard8();
    byte[] pad3 = in.readPad(5);
    javaBuilder.depth(depth);
    javaBuilder.bitsPerPixel(bitsPerPixel);
    javaBuilder.scanlinePad(scanlinePad);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(depth);
    out.writeCard8(bitsPerPixel);
    out.writeCard8(scanlinePad);
    out.writePad(5);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class FormatBuilder {
    @java.lang.SuppressWarnings("all")
    private byte depth;
    @java.lang.SuppressWarnings("all")
    private byte bitsPerPixel;
    @java.lang.SuppressWarnings("all")
    private byte scanlinePad;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    FormatBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Format.FormatBuilder depth(final byte depth) {
      this.depth = depth;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Format.FormatBuilder bitsPerPixel(final byte bitsPerPixel) {
      this.bitsPerPixel = bitsPerPixel;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Format.FormatBuilder scanlinePad(final byte scanlinePad) {
      this.scanlinePad = scanlinePad;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Format build() {
      return new Format(this.depth, this.bitsPerPixel, this.scanlinePad);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Format.FormatBuilder(depth=" + this.depth + ", bitsPerPixel=" + this.bitsPerPixel + ", scanlinePad=" + this.scanlinePad + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Format(final byte depth, final byte bitsPerPixel, final byte scanlinePad) {
    this.depth = depth;
    this.bitsPerPixel = bitsPerPixel;
    this.scanlinePad = scanlinePad;
  }

  @java.lang.SuppressWarnings("all")
  public static Format.FormatBuilder builder() {
    return new Format.FormatBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getDepth() {
    return this.depth;
  }

  @java.lang.SuppressWarnings("all")
  public byte getBitsPerPixel() {
    return this.bitsPerPixel;
  }

  @java.lang.SuppressWarnings("all")
  public byte getScanlinePad() {
    return this.scanlinePad;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Format)) return false;
    final Format other = (Format) o;
    if (this.getDepth() != other.getDepth()) return false;
    if (this.getBitsPerPixel() != other.getBitsPerPixel()) return false;
    if (this.getScanlinePad() != other.getScanlinePad()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDepth();
    result = result * PRIME + this.getBitsPerPixel();
    result = result * PRIME + this.getScanlinePad();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Format(depth=" + this.getDepth() + ", bitsPerPixel=" + this.getBitsPerPixel() + ", scanlinePad=" + this.getScanlinePad() + ")";
  }
}
