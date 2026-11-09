package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public final class Row implements XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final short top;
  private final short left;
  private final boolean vertical;
  private final ImmutableList<Key> keys;

  public static Row readRow(X11Input in) throws IOException {
    Row.RowBuilder javaBuilder = Row.builder();
    short top = in.readInt16();
    short left = in.readInt16();
    byte nKeys = in.readCard8();
    boolean vertical = in.readBool();
    byte[] pad4 = in.readPad(2);
    MutableList<Key> keys = Lists.mutable.withInitialCapacity(Byte.toUnsignedInt(nKeys));
    for (int i = 0; i < Byte.toUnsignedInt(nKeys); i++) {
      keys.add(Key.readKey(in));
    }
    javaBuilder.top(top);
    javaBuilder.left(left);
    javaBuilder.vertical(vertical);
    javaBuilder.keys(keys.toImmutable());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeInt16(top);
    out.writeInt16(left);
    byte nKeys = (byte) keys.size();
    out.writeCard8(nKeys);
    out.writeBool(vertical);
    out.writePad(2);
    for (Key t : keys) {
      t.write(out);
    }
  }

  @Override
  public int getSize() {
    return 8 + XObject.sizeOf(keys);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class RowBuilder {
    @java.lang.SuppressWarnings("all")
    private short top;
    @java.lang.SuppressWarnings("all")
    private short left;
    @java.lang.SuppressWarnings("all")
    private boolean vertical;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<Key> keys;

    public int getSize() {
      return 8 + XObject.sizeOf(keys);
    }

    @java.lang.SuppressWarnings("all")
    RowBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Row.RowBuilder top(final short top) {
      this.top = top;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Row.RowBuilder left(final short left) {
      this.left = left;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Row.RowBuilder vertical(final boolean vertical) {
      this.vertical = vertical;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Row.RowBuilder keys(final ImmutableList<Key> keys) {
      if (keys == null) {
        throw new java.lang.NullPointerException("keys is marked non-null but is null");
      }
      this.keys = keys;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Row build() {
      return new Row(this.top, this.left, this.vertical, this.keys);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Row.RowBuilder(top=" + this.top + ", left=" + this.left + ", vertical=" + this.vertical + ", keys=" + this.keys + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Row(final short top, final short left, final boolean vertical, final ImmutableList<Key> keys) {
    if (keys == null) {
      throw new java.lang.NullPointerException("keys is marked non-null but is null");
    }
    this.top = top;
    this.left = left;
    this.vertical = vertical;
    this.keys = keys;
  }

  @java.lang.SuppressWarnings("all")
  public static Row.RowBuilder builder() {
    return new Row.RowBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getTop() {
    return this.top;
  }

  @java.lang.SuppressWarnings("all")
  public short getLeft() {
    return this.left;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isVertical() {
    return this.vertical;
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<Key> getKeys() {
    return this.keys;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Row)) return false;
    final Row other = (Row) o;
    if (this.getTop() != other.getTop()) return false;
    if (this.getLeft() != other.getLeft()) return false;
    if (this.isVertical() != other.isVertical()) return false;
    final java.lang.Object this$keys = this.getKeys();
    final java.lang.Object other$keys = other.getKeys();
    if (this$keys == null ? other$keys != null : !this$keys.equals(other$keys)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getTop();
    result = result * PRIME + this.getLeft();
    result = result * PRIME + (this.isVertical() ? 79 : 97);
    final java.lang.Object $keys = this.getKeys();
    result = result * PRIME + ($keys == null ? 43 : $keys.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Row(top=" + this.getTop() + ", left=" + this.getLeft() + ", vertical=" + this.isVertical() + ", keys=" + this.getKeys() + ")";
  }
}
