package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.ByteList;

public final class PolyText8 implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 74;
  private final int drawable;
  private final int gc;
  private final short x;
  private final short y;
  private final ByteList items;

  public byte getOpCode() {
    return OPCODE;
  }

  public static PolyText8 readPolyText8(X11Input in) throws IOException {
    PolyText8.PolyText8Builder javaBuilder = PolyText8.builder();
    int javaStart = 1;
    byte[] pad1 = in.readPad(1);
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int drawable = in.readCard32();
    javaStart += 4;
    int gc = in.readCard32();
    javaStart += 4;
    short x = in.readInt16();
    javaStart += 2;
    short y = in.readInt16();
    javaStart += 2;
    ByteList items = in.readByte(Short.toUnsignedInt(length) - javaStart);
    javaBuilder.drawable(drawable);
    javaBuilder.gc(gc);
    javaBuilder.x(x);
    javaBuilder.y(y);
    javaBuilder.items(items.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeCard32(gc);
    out.writeInt16(x);
    out.writeInt16(y);
    out.writeByte(items);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 16 + 1 * items.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class PolyText8Builder {
    @java.lang.SuppressWarnings("all")
    private int drawable;
    @java.lang.SuppressWarnings("all")
    private int gc;
    @java.lang.SuppressWarnings("all")
    private short x;
    @java.lang.SuppressWarnings("all")
    private short y;
    @java.lang.SuppressWarnings("all")
    private ByteList items;

    public int getSize() {
      return 16 + 1 * items.size();
    }

    @java.lang.SuppressWarnings("all")
    PolyText8Builder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PolyText8.PolyText8Builder drawable(final int drawable) {
      this.drawable = drawable;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PolyText8.PolyText8Builder gc(final int gc) {
      this.gc = gc;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PolyText8.PolyText8Builder x(final short x) {
      this.x = x;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PolyText8.PolyText8Builder y(final short y) {
      this.y = y;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PolyText8.PolyText8Builder items(final ByteList items) {
      if (items == null) {
        throw new java.lang.NullPointerException("items is marked non-null but is null");
      }
      this.items = items;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public PolyText8 build() {
      return new PolyText8(this.drawable, this.gc, this.x, this.y, this.items);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "PolyText8.PolyText8Builder(drawable=" + this.drawable + ", gc=" + this.gc + ", x=" + this.x + ", y=" + this.y + ", items=" + this.items + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  PolyText8(final int drawable, final int gc, final short x, final short y, final ByteList items) {
    if (items == null) {
      throw new java.lang.NullPointerException("items is marked non-null but is null");
    }
    this.drawable = drawable;
    this.gc = gc;
    this.x = x;
    this.y = y;
    this.items = items;
  }

  @java.lang.SuppressWarnings("all")
  public static PolyText8.PolyText8Builder builder() {
    return new PolyText8.PolyText8Builder();
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
  public ByteList getItems() {
    return this.items;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof PolyText8)) return false;
    final PolyText8 other = (PolyText8) o;
    if (this.getDrawable() != other.getDrawable()) return false;
    if (this.getGc() != other.getGc()) return false;
    if (this.getX() != other.getX()) return false;
    if (this.getY() != other.getY()) return false;
    final java.lang.Object this$items = this.getItems();
    final java.lang.Object other$items = other.getItems();
    if (this$items == null ? other$items != null : !this$items.equals(other$items)) return false;
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
    final java.lang.Object $items = this.getItems();
    result = result * PRIME + ($items == null ? 43 : $items.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "PolyText8(drawable=" + this.getDrawable() + ", gc=" + this.getGc() + ", x=" + this.getX() + ", y=" + this.getY() + ", items=" + this.getItems() + ")";
  }
}
