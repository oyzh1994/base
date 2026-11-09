package com.github.moaxcp.x11.protocol.xkb;

import java.util.ArrayList;
import java.util.List;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class OverlayRow implements XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final byte rowUnder;
  private final List<OverlayKey> keys;

  public static OverlayRow readOverlayRow(X11Input in) throws IOException {
    OverlayRow.OverlayRowBuilder javaBuilder = OverlayRow.builder();
    byte rowUnder = in.readCard8();
    byte nKeys = in.readCard8();
    byte[] pad2 = in.readPad(2);
    List<OverlayKey> keys = new ArrayList<>(Byte.toUnsignedInt(nKeys));
    for (int i = 0; i < Byte.toUnsignedInt(nKeys); i++) {
      keys.add(OverlayKey.readOverlayKey(in));
    }
    javaBuilder.rowUnder(rowUnder);
    javaBuilder.keys(List.copyOf(keys));
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(rowUnder);
    byte nKeys = (byte) keys.size();
    out.writeCard8(nKeys);
    out.writePad(2);
    for (OverlayKey t : keys) {
      t.write(out);
    }
  }

  @Override
  public int getSize() {
    return 4 + XObject.sizeOf(keys);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class OverlayRowBuilder {
    @java.lang.SuppressWarnings("all")
    private byte rowUnder;
    @java.lang.SuppressWarnings("all")
    private List<OverlayKey> keys;

    public int getSize() {
      return 4 + XObject.sizeOf(keys);
    }

    @java.lang.SuppressWarnings("all")
    OverlayRowBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public OverlayRow.OverlayRowBuilder rowUnder(final byte rowUnder) {
      this.rowUnder = rowUnder;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public OverlayRow.OverlayRowBuilder keys(final List<OverlayKey> keys) {
      if (keys == null) {
        throw new java.lang.NullPointerException("keys is marked non-null but is null");
      }
      this.keys = keys;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public OverlayRow build() {
      return new OverlayRow(this.rowUnder, this.keys);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "OverlayRow.OverlayRowBuilder(rowUnder=" + this.rowUnder + ", keys=" + this.keys + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  OverlayRow(final byte rowUnder, final List<OverlayKey> keys) {
    if (keys == null) {
      throw new java.lang.NullPointerException("keys is marked non-null but is null");
    }
    this.rowUnder = rowUnder;
    this.keys = keys;
  }

  @java.lang.SuppressWarnings("all")
  public static OverlayRow.OverlayRowBuilder builder() {
    return new OverlayRow.OverlayRowBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getRowUnder() {
    return this.rowUnder;
  }

  @java.lang.SuppressWarnings("all")
  public List<OverlayKey> getKeys() {
    return this.keys;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof OverlayRow)) return false;
    final OverlayRow other = (OverlayRow) o;
    if (this.getRowUnder() != other.getRowUnder()) return false;
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
    result = result * PRIME + this.getRowUnder();
    final java.lang.Object $keys = this.getKeys();
    result = result * PRIME + ($keys == null ? 43 : $keys.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "OverlayRow(rowUnder=" + this.getRowUnder() + ", keys=" + this.getKeys() + ")";
  }
}
