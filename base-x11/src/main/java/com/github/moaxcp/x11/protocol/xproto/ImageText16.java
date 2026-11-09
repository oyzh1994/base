package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public final class ImageText16 implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 77;
  private final int drawable;
  private final int gc;
  private final short x;
  private final short y;
  private final ImmutableList<Char2b> string;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ImageText16 readImageText16(X11Input in) throws IOException {
    ImageText16.ImageText16Builder javaBuilder = ImageText16.builder();
    byte stringLen = in.readByte();
    short length = in.readCard16();
    int drawable = in.readCard32();
    int gc = in.readCard32();
    short x = in.readInt16();
    short y = in.readInt16();
    MutableList<Char2b> string = Lists.mutable.withInitialCapacity(stringLen);
    for (int i = 0; i < stringLen; i++) {
      string.add(Char2b.readChar2b(in));
    }
    javaBuilder.drawable(drawable);
    javaBuilder.gc(gc);
    javaBuilder.x(x);
    javaBuilder.y(y);
    javaBuilder.string(string.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    byte stringLen = (byte) string.size();
    out.writeByte(stringLen);
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeCard32(gc);
    out.writeInt16(x);
    out.writeInt16(y);
    for (Char2b t : string) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 16 + XObject.sizeOf(string);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ImageText16Builder {
    @java.lang.SuppressWarnings("all")
    private int drawable;
    @java.lang.SuppressWarnings("all")
    private int gc;
    @java.lang.SuppressWarnings("all")
    private short x;
    @java.lang.SuppressWarnings("all")
    private short y;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<Char2b> string;

    public int getSize() {
      return 16 + XObject.sizeOf(string);
    }

    @java.lang.SuppressWarnings("all")
    ImageText16Builder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ImageText16.ImageText16Builder drawable(final int drawable) {
      this.drawable = drawable;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ImageText16.ImageText16Builder gc(final int gc) {
      this.gc = gc;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ImageText16.ImageText16Builder x(final short x) {
      this.x = x;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ImageText16.ImageText16Builder y(final short y) {
      this.y = y;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ImageText16.ImageText16Builder string(final ImmutableList<Char2b> string) {
      if (string == null) {
        throw new java.lang.NullPointerException("string is marked non-null but is null");
      }
      this.string = string;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ImageText16 build() {
      return new ImageText16(this.drawable, this.gc, this.x, this.y, this.string);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ImageText16.ImageText16Builder(drawable=" + this.drawable + ", gc=" + this.gc + ", x=" + this.x + ", y=" + this.y + ", string=" + this.string + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ImageText16(final int drawable, final int gc, final short x, final short y, final ImmutableList<Char2b> string) {
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
  public static ImageText16.ImageText16Builder builder() {
    return new ImageText16.ImageText16Builder();
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
  public ImmutableList<Char2b> getString() {
    return this.string;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ImageText16)) return false;
    final ImageText16 other = (ImageText16) o;
    if (this.getDrawable() != other.getDrawable()) return false;
    if (this.getGc() != other.getGc()) return false;
    if (this.getX() != other.getX()) return false;
    if (this.getY() != other.getY()) return false;
    final java.lang.Object this$string = this.getString();
    final java.lang.Object other$string = other.getString();
    if (this$string == null ? other$string != null : !this$string.equals(other$string)) return false;
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
    final java.lang.Object $string = this.getString();
    result = result * PRIME + ($string == null ? 43 : $string.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ImageText16(drawable=" + this.getDrawable() + ", gc=" + this.getGc() + ", x=" + this.getX() + ", y=" + this.getY() + ", string=" + this.getString() + ")";
  }
}
