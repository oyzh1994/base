package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class ImageText8 implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 76;
  private final int drawable;
  private final int gc;
  private final short x;
  private final short y;
  private final byte[] string;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ImageText8 readImageText8(X11Input in) throws IOException {
    ImageText8.ImageText8Builder javaBuilder = ImageText8.builder();
    byte stringLen = in.readByte();
    short length = in.readCard16();
    int drawable = in.readCard32();
    int gc = in.readCard32();
    short x = in.readInt16();
    short y = in.readInt16();
    byte[] string = in.readChar(stringLen);
    javaBuilder.drawable(drawable);
    javaBuilder.gc(gc);
    javaBuilder.x(x);
    javaBuilder.y(y);
    javaBuilder.string(string);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    byte stringLen = (byte) string.length;
    out.writeByte(stringLen);
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeCard32(gc);
    out.writeInt16(x);
    out.writeInt16(y);
    out.writeChar(string);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 16 + 1 * string.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ImageText8Builder {
    @java.lang.SuppressWarnings("all")
    private int drawable;
    @java.lang.SuppressWarnings("all")
    private int gc;
    @java.lang.SuppressWarnings("all")
    private short x;
    @java.lang.SuppressWarnings("all")
    private short y;
    @java.lang.SuppressWarnings("all")
    private byte[] string;

    public int getSize() {
      return 16 + 1 * string.length;
    }

    @java.lang.SuppressWarnings("all")
    ImageText8Builder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ImageText8.ImageText8Builder drawable(final int drawable) {
      this.drawable = drawable;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ImageText8.ImageText8Builder gc(final int gc) {
      this.gc = gc;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ImageText8.ImageText8Builder x(final short x) {
      this.x = x;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ImageText8.ImageText8Builder y(final short y) {
      this.y = y;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ImageText8.ImageText8Builder string(final byte[] string) {
      if (string == null) {
        throw new java.lang.NullPointerException("string is marked non-null but is null");
      }
      this.string = string;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ImageText8 build() {
      return new ImageText8(this.drawable, this.gc, this.x, this.y, this.string);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ImageText8.ImageText8Builder(drawable=" + this.drawable + ", gc=" + this.gc + ", x=" + this.x + ", y=" + this.y + ", string=" + java.util.Arrays.toString(this.string) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ImageText8(final int drawable, final int gc, final short x, final short y, final byte[] string) {
    if (string == null) {
      throw new java.lang.NullPointerException("string is marked non-null but is null");
    }
    this.drawable = drawable;
    this.gc = gc;
    this.x = x;
    this.y = y;
    this.string = string;
  }

  @java.lang.SuppressWarnings("all")
  public static ImageText8.ImageText8Builder builder() {
    return new ImageText8.ImageText8Builder();
  }

  @java.lang.SuppressWarnings("all")
  public int getDrawable() {
    return this.drawable;
  }

  @java.lang.SuppressWarnings("all")
  public int getGc() {
    return this.gc;
  }

  @java.lang.SuppressWarnings("all")
  public short getX() {
    return this.x;
  }

  @java.lang.SuppressWarnings("all")
  public short getY() {
    return this.y;
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getString() {
    return this.string;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ImageText8)) return false;
    final ImageText8 other = (ImageText8) o;
    if (this.getDrawable() != other.getDrawable()) return false;
    if (this.getGc() != other.getGc()) return false;
    if (this.getX() != other.getX()) return false;
    if (this.getY() != other.getY()) return false;
    if (!java.util.Arrays.equals(this.getString(), other.getString())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDrawable();
    result = result * PRIME + this.getGc();
    result = result * PRIME + this.getX();
    result = result * PRIME + this.getY();
    result = result * PRIME + java.util.Arrays.hashCode(this.getString());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ImageText8(drawable=" + this.getDrawable() + ", gc=" + this.getGc() + ", x=" + this.getX() + ", y=" + this.getY() + ", string=" + java.util.Arrays.toString(this.getString()) + ")";
  }
}
