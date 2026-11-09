package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class AllocColorCells implements TwoWayRequest<AllocColorCellsReply> {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 86;
  private final boolean contiguous;
  private final int cmap;
  private final short colors;
  private final short planes;

  public XReplyFunction<AllocColorCellsReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> AllocColorCellsReply.readAllocColorCellsReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static AllocColorCells readAllocColorCells(X11Input in) throws IOException {
    AllocColorCells.AllocColorCellsBuilder javaBuilder = AllocColorCells.builder();
    boolean contiguous = in.readBool();
    short length = in.readCard16();
    int cmap = in.readCard32();
    short colors = in.readCard16();
    short planes = in.readCard16();
    javaBuilder.contiguous(contiguous);
    javaBuilder.cmap(cmap);
    javaBuilder.colors(colors);
    javaBuilder.planes(planes);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeBool(contiguous);
    out.writeCard16((short) getLength());
    out.writeCard32(cmap);
    out.writeCard16(colors);
    out.writeCard16(planes);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class AllocColorCellsBuilder {
    @java.lang.SuppressWarnings("all")
    private boolean contiguous;
    @java.lang.SuppressWarnings("all")
    private int cmap;
    @java.lang.SuppressWarnings("all")
    private short colors;
    @java.lang.SuppressWarnings("all")
    private short planes;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    AllocColorCellsBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AllocColorCells.AllocColorCellsBuilder contiguous(final boolean contiguous) {
      this.contiguous = contiguous;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AllocColorCells.AllocColorCellsBuilder cmap(final int cmap) {
      this.cmap = cmap;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AllocColorCells.AllocColorCellsBuilder colors(final short colors) {
      this.colors = colors;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AllocColorCells.AllocColorCellsBuilder planes(final short planes) {
      this.planes = planes;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public AllocColorCells build() {
      return new AllocColorCells(this.contiguous, this.cmap, this.colors, this.planes);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "AllocColorCells.AllocColorCellsBuilder(contiguous=" + this.contiguous + ", cmap=" + this.cmap + ", colors=" + this.colors + ", planes=" + this.planes + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  AllocColorCells(final boolean contiguous, final int cmap, final short colors, final short planes) {
    this.contiguous = contiguous;
    this.cmap = cmap;
    this.colors = colors;
    this.planes = planes;
  }

  @java.lang.SuppressWarnings("all")
  public static AllocColorCells.AllocColorCellsBuilder builder() {
    return new AllocColorCells.AllocColorCellsBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public boolean isContiguous() {
    return this.contiguous;
  }

  @java.lang.SuppressWarnings("all")
  public int getCmap() {
    return this.cmap;
  }

  @java.lang.SuppressWarnings("all")
  public short getColors() {
    return this.colors;
  }

  @java.lang.SuppressWarnings("all")
  public short getPlanes() {
    return this.planes;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof AllocColorCells)) return false;
    final AllocColorCells other = (AllocColorCells) o;
    if (this.isContiguous() != other.isContiguous()) return false;
    if (this.getCmap() != other.getCmap()) return false;
    if (this.getColors() != other.getColors()) return false;
    if (this.getPlanes() != other.getPlanes()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + (this.isContiguous() ? 79 : 97);
    result = result * PRIME + this.getCmap();
    result = result * PRIME + this.getColors();
    result = result * PRIME + this.getPlanes();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "AllocColorCells(contiguous=" + this.isContiguous() + ", cmap=" + this.getCmap() + ", colors=" + this.getColors() + ", planes=" + this.getPlanes() + ")";
  }
}
