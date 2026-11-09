package com.github.moaxcp.x11.protocol.xproto;

import java.util.ArrayList;
import java.util.List;
import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;

public final class StoreColors implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 89;
  private final int cmap;
  private final List<Coloritem> items;

  public byte getOpCode() {
    return OPCODE;
  }

  public static StoreColors readStoreColors(X11Input in) throws IOException {
    StoreColors.StoreColorsBuilder javaBuilder = StoreColors.builder();
    int javaStart = 1;
    byte[] pad1 = in.readPad(1);
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int cmap = in.readCard32();
    javaStart += 4;
    List<Coloritem> items = new ArrayList<>(Short.toUnsignedInt(length) - javaStart);
    while (javaStart < Short.toUnsignedInt(length) * 4) {
      Coloritem baseObject = Coloritem.readColoritem(in);
      items.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.cmap(cmap);
    javaBuilder.items(List.copyOf(items));
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(cmap);
    for (Coloritem t : items) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 8 + XObject.sizeOf(items);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class StoreColorsBuilder {
    @java.lang.SuppressWarnings("all")
    private int cmap;
    @java.lang.SuppressWarnings("all")
    private List<Coloritem> items;

    public int getSize() {
      return 8 + XObject.sizeOf(items);
    }

    @java.lang.SuppressWarnings("all")
    StoreColorsBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public StoreColors.StoreColorsBuilder cmap(final int cmap) {
      this.cmap = cmap;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public StoreColors.StoreColorsBuilder items(final List<Coloritem> items) {
      if (items == null) {
        throw new java.lang.NullPointerException("items is marked non-null but is null");
      }
      this.items = items;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public StoreColors build() {
      return new StoreColors(this.cmap, this.items);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "StoreColors.StoreColorsBuilder(cmap=" + this.cmap + ", items=" + this.items + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  StoreColors(final int cmap, final List<Coloritem> items) {
    if (items == null) {
      throw new java.lang.NullPointerException("items is marked non-null but is null");
    }
    this.cmap = cmap;
    this.items = items;
  }

  @java.lang.SuppressWarnings("all")
  public static StoreColors.StoreColorsBuilder builder() {
    return new StoreColors.StoreColorsBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getCmap() {
    return this.cmap;
  }

  @java.lang.SuppressWarnings("all")
  public List<Coloritem> getItems() {
    return this.items;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof StoreColors)) return false;
    final StoreColors other = (StoreColors) o;
    if (this.getCmap() != other.getCmap()) return false;
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
    result = result * PRIME + this.getCmap();
    final java.lang.Object $items = this.getItems();
    result = result * PRIME + ($items == null ? 43 : $items.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "StoreColors(cmap=" + this.getCmap() + ", items=" + this.getItems() + ")";
  }
}
