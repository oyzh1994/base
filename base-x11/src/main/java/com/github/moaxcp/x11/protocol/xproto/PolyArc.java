package com.github.moaxcp.x11.protocol.xproto;

import java.util.ArrayList;
import java.util.List;
import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;

public final class PolyArc implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 68;
  private final int drawable;
  private final int gc;
  private final List<Arc> arcs;

  public byte getOpCode() {
    return OPCODE;
  }

  public static PolyArc readPolyArc(X11Input in) throws IOException {
    PolyArc.PolyArcBuilder javaBuilder = PolyArc.builder();
    int javaStart = 1;
    byte[] pad1 = in.readPad(1);
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int drawable = in.readCard32();
    javaStart += 4;
    int gc = in.readCard32();
    javaStart += 4;
    List<Arc> arcs = new ArrayList<>(Short.toUnsignedInt(length) - javaStart);
    while (javaStart < Short.toUnsignedInt(length) * 4) {
      Arc baseObject = Arc.readArc(in);
      arcs.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.drawable(drawable);
    javaBuilder.gc(gc);
    javaBuilder.arcs(List.copyOf(arcs));
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
    for (Arc t : arcs) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + XObject.sizeOf(arcs);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class PolyArcBuilder {
    @java.lang.SuppressWarnings("all")
    private int drawable;
    @java.lang.SuppressWarnings("all")
    private int gc;
    @java.lang.SuppressWarnings("all")
    private List<Arc> arcs;

    public int getSize() {
      return 12 + XObject.sizeOf(arcs);
    }

    @java.lang.SuppressWarnings("all")
    PolyArcBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PolyArc.PolyArcBuilder drawable(final int drawable) {
      this.drawable = drawable;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PolyArc.PolyArcBuilder gc(final int gc) {
      this.gc = gc;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PolyArc.PolyArcBuilder arcs(final List<Arc> arcs) {
      if (arcs == null) {
        throw new java.lang.NullPointerException("arcs is marked non-null but is null");
      }
      this.arcs = arcs;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public PolyArc build() {
      return new PolyArc(this.drawable, this.gc, this.arcs);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "PolyArc.PolyArcBuilder(drawable=" + this.drawable + ", gc=" + this.gc + ", arcs=" + this.arcs + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  PolyArc(final int drawable, final int gc, final List<Arc> arcs) {
    if (arcs == null) {
      throw new java.lang.NullPointerException("arcs is marked non-null but is null");
    }
    this.drawable = drawable;
    this.gc = gc;
    this.arcs = arcs;
  }

  @java.lang.SuppressWarnings("all")
  public static PolyArc.PolyArcBuilder builder() {
    return new PolyArc.PolyArcBuilder();
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
  public List<Arc> getArcs() {
    return this.arcs;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof PolyArc)) return false;
    final PolyArc other = (PolyArc) o;
    if (this.getDrawable() != other.getDrawable()) return false;
    if (this.getGc() != other.getGc()) return false;
    final java.lang.Object this$arcs = this.getArcs();
    final java.lang.Object other$arcs = other.getArcs();
    if (this$arcs == null ? other$arcs != null : !this$arcs.equals(other$arcs)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDrawable();
    result = result * PRIME + this.getGc();
    final java.lang.Object $arcs = this.getArcs();
    result = result * PRIME + ($arcs == null ? 43 : $arcs.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "PolyArc(drawable=" + this.getDrawable() + ", gc=" + this.getGc() + ", arcs=" + this.getArcs() + ")";
  }
}
