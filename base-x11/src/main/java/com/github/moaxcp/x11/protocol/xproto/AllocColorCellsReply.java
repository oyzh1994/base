package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class AllocColorCellsReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";
  private final short sequenceNumber;
  private final int[] pixels;
  private final int[] masks;

  public static AllocColorCellsReply readAllocColorCellsReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    AllocColorCellsReply.AllocColorCellsReplyBuilder javaBuilder = AllocColorCellsReply.builder();
    int length = in.readCard32();
    short pixelsLen = in.readCard16();
    short masksLen = in.readCard16();
    byte[] pad6 = in.readPad(20);
    int[] pixels = in.readCard32(Short.toUnsignedInt(pixelsLen));
    int[] masks = in.readCard32(Short.toUnsignedInt(masksLen));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.pixels(pixels);
    javaBuilder.masks(masks);
    if (javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    short pixelsLen = (short) pixels.length;
    out.writeCard16(pixelsLen);
    short masksLen = (short) masks.length;
    out.writeCard16(masksLen);
    out.writePad(20);
    out.writeCard32(pixels);
    out.writeCard32(masks);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 4 * pixels.length + 4 * masks.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class AllocColorCellsReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int[] pixels;
    @java.lang.SuppressWarnings("all")
    private int[] masks;

    public int getSize() {
      return 32 + 4 * pixels.length + 4 * masks.length;
    }

    @java.lang.SuppressWarnings("all")
    AllocColorCellsReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AllocColorCellsReply.AllocColorCellsReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AllocColorCellsReply.AllocColorCellsReplyBuilder pixels(final int[] pixels) {
      if (pixels == null) {
        throw new java.lang.NullPointerException("pixels is marked non-null but is null");
      }
      this.pixels = pixels;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AllocColorCellsReply.AllocColorCellsReplyBuilder masks(final int[] masks) {
      if (masks == null) {
        throw new java.lang.NullPointerException("masks is marked non-null but is null");
      }
      this.masks = masks;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public AllocColorCellsReply build() {
      return new AllocColorCellsReply(this.sequenceNumber, this.pixels, this.masks);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "AllocColorCellsReply.AllocColorCellsReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", pixels=" + java.util.Arrays.toString(this.pixels) + ", masks=" + java.util.Arrays.toString(this.masks) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  AllocColorCellsReply(final short sequenceNumber, final int[] pixels, final int[] masks) {
    if (pixels == null) {
      throw new java.lang.NullPointerException("pixels is marked non-null but is null");
    }
    if (masks == null) {
      throw new java.lang.NullPointerException("masks is marked non-null but is null");
    }
    this.sequenceNumber = sequenceNumber;
    this.pixels = pixels;
    this.masks = masks;
  }

  @java.lang.SuppressWarnings("all")
  public static AllocColorCellsReply.AllocColorCellsReplyBuilder builder() {
    return new AllocColorCellsReply.AllocColorCellsReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int[] getPixels() {
    return this.pixels;
  }

  @java.lang.SuppressWarnings("all")
  public int[] getMasks() {
    return this.masks;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof AllocColorCellsReply)) return false;
    final AllocColorCellsReply other = (AllocColorCellsReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (!java.util.Arrays.equals(this.getPixels(), other.getPixels())) return false;
    if (!java.util.Arrays.equals(this.getMasks(), other.getMasks())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + java.util.Arrays.hashCode(this.getPixels());
    result = result * PRIME + java.util.Arrays.hashCode(this.getMasks());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "AllocColorCellsReply(sequenceNumber=" + this.getSequenceNumber() + ", pixels=" + java.util.Arrays.toString(this.getPixels()) + ", masks=" + java.util.Arrays.toString(this.getMasks()) + ")";
  }
}
