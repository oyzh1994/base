package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class Key implements XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final byte[] name;
  private final short gap;
  private final byte shapeNdx;
  private final byte colorNdx;

  public static Key readKey(X11Input in) throws IOException {
    Key.KeyBuilder javaBuilder = Key.builder();
    byte[] name = in.readChar(4);
    short gap = in.readInt16();
    byte shapeNdx = in.readCard8();
    byte colorNdx = in.readCard8();
    javaBuilder.name(name);
    javaBuilder.gap(gap);
    javaBuilder.shapeNdx(shapeNdx);
    javaBuilder.colorNdx(colorNdx);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeChar(name);
    out.writeInt16(gap);
    out.writeCard8(shapeNdx);
    out.writeCard8(colorNdx);
  }

  @Override
  public int getSize() {
    return 4 + 1 * name.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class KeyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte[] name;
    @java.lang.SuppressWarnings("all")
    private short gap;
    @java.lang.SuppressWarnings("all")
    private byte shapeNdx;
    @java.lang.SuppressWarnings("all")
    private byte colorNdx;

    public int getSize() {
      return 4 + 1 * name.length;
    }

    @java.lang.SuppressWarnings("all")
    KeyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Key.KeyBuilder name(final byte[] name) {
      if (name == null) {
        throw new java.lang.NullPointerException("name is marked non-null but is null");
      }
      this.name = name;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Key.KeyBuilder gap(final short gap) {
      this.gap = gap;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Key.KeyBuilder shapeNdx(final byte shapeNdx) {
      this.shapeNdx = shapeNdx;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Key.KeyBuilder colorNdx(final byte colorNdx) {
      this.colorNdx = colorNdx;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Key build() {
      return new Key(this.name, this.gap, this.shapeNdx, this.colorNdx);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Key.KeyBuilder(name=" + java.util.Arrays.toString(this.name) + ", gap=" + this.gap + ", shapeNdx=" + this.shapeNdx + ", colorNdx=" + this.colorNdx + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Key(final byte[] name, final short gap, final byte shapeNdx, final byte colorNdx) {
    if (name == null) {
      throw new java.lang.NullPointerException("name is marked non-null but is null");
    }
    this.name = name;
    this.gap = gap;
    this.shapeNdx = shapeNdx;
    this.colorNdx = colorNdx;
  }

  @java.lang.SuppressWarnings("all")
  public static Key.KeyBuilder builder() {
    return new Key.KeyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getName() {
    return this.name;
  }

  @java.lang.SuppressWarnings("all")
  public short getGap() {
    return this.gap;
  }

  @java.lang.SuppressWarnings("all")
  public byte getShapeNdx() {
    return this.shapeNdx;
  }

  @java.lang.SuppressWarnings("all")
  public byte getColorNdx() {
    return this.colorNdx;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Key)) return false;
    final Key other = (Key) o;
    if (this.getGap() != other.getGap()) return false;
    if (this.getShapeNdx() != other.getShapeNdx()) return false;
    if (this.getColorNdx() != other.getColorNdx()) return false;
    if (!java.util.Arrays.equals(this.getName(), other.getName())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getGap();
    result = result * PRIME + this.getShapeNdx();
    result = result * PRIME + this.getColorNdx();
    result = result * PRIME + java.util.Arrays.hashCode(this.getName());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Key(name=" + java.util.Arrays.toString(this.getName()) + ", gap=" + this.getGap() + ", shapeNdx=" + this.getShapeNdx() + ", colorNdx=" + this.getColorNdx() + ")";
  }
}
