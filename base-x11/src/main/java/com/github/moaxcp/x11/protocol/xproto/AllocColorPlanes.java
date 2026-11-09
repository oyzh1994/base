package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class AllocColorPlanes implements TwoWayRequest<AllocColorPlanesReply> {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 87;
  private final boolean contiguous;
  private final int cmap;
  private final short colors;
  private final short reds;
  private final short greens;
  private final short blues;

  public XReplyFunction<AllocColorPlanesReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> AllocColorPlanesReply.readAllocColorPlanesReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static AllocColorPlanes readAllocColorPlanes(X11Input in) throws IOException {
    AllocColorPlanes.AllocColorPlanesBuilder javaBuilder = AllocColorPlanes.builder();
    boolean contiguous = in.readBool();
    short length = in.readCard16();
    int cmap = in.readCard32();
    short colors = in.readCard16();
    short reds = in.readCard16();
    short greens = in.readCard16();
    short blues = in.readCard16();
    javaBuilder.contiguous(contiguous);
    javaBuilder.cmap(cmap);
    javaBuilder.colors(colors);
    javaBuilder.reds(reds);
    javaBuilder.greens(greens);
    javaBuilder.blues(blues);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeBool(contiguous);
    out.writeCard16((short) getLength());
    out.writeCard32(cmap);
    out.writeCard16(colors);
    out.writeCard16(reds);
    out.writeCard16(greens);
    out.writeCard16(blues);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class AllocColorPlanesBuilder {
    @java.lang.SuppressWarnings("all")
    private boolean contiguous;
    @java.lang.SuppressWarnings("all")
    private int cmap;
    @java.lang.SuppressWarnings("all")
    private short colors;
    @java.lang.SuppressWarnings("all")
    private short reds;
    @java.lang.SuppressWarnings("all")
    private short greens;
    @java.lang.SuppressWarnings("all")
    private short blues;

    public int getSize() {
      return 16;
    }

    @java.lang.SuppressWarnings("all")
    AllocColorPlanesBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AllocColorPlanes.AllocColorPlanesBuilder contiguous(final boolean contiguous) {
      this.contiguous = contiguous;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AllocColorPlanes.AllocColorPlanesBuilder cmap(final int cmap) {
      this.cmap = cmap;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AllocColorPlanes.AllocColorPlanesBuilder colors(final short colors) {
      this.colors = colors;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AllocColorPlanes.AllocColorPlanesBuilder reds(final short reds) {
      this.reds = reds;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AllocColorPlanes.AllocColorPlanesBuilder greens(final short greens) {
      this.greens = greens;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AllocColorPlanes.AllocColorPlanesBuilder blues(final short blues) {
      this.blues = blues;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public AllocColorPlanes build() {
      return new AllocColorPlanes(this.contiguous, this.cmap, this.colors, this.reds, this.greens, this.blues);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "AllocColorPlanes.AllocColorPlanesBuilder(contiguous=" + this.contiguous + ", cmap=" + this.cmap + ", colors=" + this.colors + ", reds=" + this.reds + ", greens=" + this.greens + ", blues=" + this.blues + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  AllocColorPlanes(final boolean contiguous, final int cmap, final short colors, final short reds, final short greens, final short blues) {
    this.contiguous = contiguous;
    this.cmap = cmap;
    this.colors = colors;
    this.reds = reds;
    this.greens = greens;
    this.blues = blues;
  }

  @java.lang.SuppressWarnings("all")
  public static AllocColorPlanes.AllocColorPlanesBuilder builder() {
    return new AllocColorPlanes.AllocColorPlanesBuilder();
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
  public short getReds() {
    return this.reds;
  }

  @java.lang.SuppressWarnings("all")
  public short getGreens() {
    return this.greens;
  }

  @java.lang.SuppressWarnings("all")
  public short getBlues() {
    return this.blues;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof AllocColorPlanes)) return false;
    final AllocColorPlanes other = (AllocColorPlanes) o;
    if (this.isContiguous() != other.isContiguous()) return false;
    if (this.getCmap() != other.getCmap()) return false;
    if (this.getColors() != other.getColors()) return false;
    if (this.getReds() != other.getReds()) return false;
    if (this.getGreens() != other.getGreens()) return false;
    if (this.getBlues() != other.getBlues()) return false;
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
    result = result * PRIME + this.getReds();
    result = result * PRIME + this.getGreens();
    result = result * PRIME + this.getBlues();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "AllocColorPlanes(contiguous=" + this.isContiguous() + ", cmap=" + this.getCmap() + ", colors=" + this.getColors() + ", reds=" + this.getReds() + ", greens=" + this.getGreens() + ", blues=" + this.getBlues() + ")";
  }
}
