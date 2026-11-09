package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public final class Overlay implements XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final int name;
  private final ImmutableList<OverlayRow> rows;

  public static Overlay readOverlay(X11Input in) throws IOException {
    Overlay.OverlayBuilder javaBuilder = Overlay.builder();
    int name = in.readCard32();
    byte nRows = in.readCard8();
    byte[] pad2 = in.readPad(3);
    MutableList<OverlayRow> rows = Lists.mutable.withInitialCapacity(Byte.toUnsignedInt(nRows));
    for (int i = 0; i < Byte.toUnsignedInt(nRows); i++) {
      rows.add(OverlayRow.readOverlayRow(in));
    }
    javaBuilder.name(name);
    javaBuilder.rows(rows.toImmutable());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(name);
    byte nRows = (byte) rows.size();
    out.writeCard8(nRows);
    out.writePad(3);
    for (OverlayRow t : rows) {
      t.write(out);
    }
  }

  @Override
  public int getSize() {
    return 8 + XObject.sizeOf(rows);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class OverlayBuilder {
    @java.lang.SuppressWarnings("all")
    private int name;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<OverlayRow> rows;

    public int getSize() {
      return 8 + XObject.sizeOf(rows);
    }

    @java.lang.SuppressWarnings("all")
    OverlayBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Overlay.OverlayBuilder name(final int name) {
      this.name = name;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Overlay.OverlayBuilder rows(final ImmutableList<OverlayRow> rows) {
      if (rows == null) {
        throw new java.lang.NullPointerException("rows is marked non-null but is null");
      }
      this.rows = rows;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Overlay build() {
      return new Overlay(this.name, this.rows);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Overlay.OverlayBuilder(name=" + this.name + ", rows=" + this.rows + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Overlay(final int name, final ImmutableList<OverlayRow> rows) {
    if (rows == null) {
      throw new java.lang.NullPointerException("rows is marked non-null but is null");
    }
    this.name = name;
    this.rows = rows;
  }

  @java.lang.SuppressWarnings("all")
  public static Overlay.OverlayBuilder builder() {
    return new Overlay.OverlayBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getName() {
    return this.name;
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<OverlayRow> getRows() {
    return this.rows;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Overlay)) return false;
    final Overlay other = (Overlay) o;
    if (this.getName() != other.getName()) return false;
    final java.lang.Object this$rows = this.getRows();
    final java.lang.Object other$rows = other.getRows();
    if (this$rows == null ? other$rows != null : !this$rows.equals(other$rows)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getName();
    final java.lang.Object $rows = this.getRows();
    result = result * PRIME + ($rows == null ? 43 : $rows.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Overlay(name=" + this.getName() + ", rows=" + this.getRows() + ")";
  }
}
