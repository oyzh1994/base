package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.IntList;

public final class AllocColorCellsReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";
  private final short sequenceNumber;
  private final IntList pixels;
  private final IntList masks;

  public static AllocColorCellsReply readAllocColorCellsReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    AllocColorCellsReply.AllocColorCellsReplyBuilder javaBuilder = AllocColorCellsReply.builder();
    int length = in.readCard32();
    short pixelsLen = in.readCard16();
    short masksLen = in.readCard16();
    byte[] pad6 = in.readPad(20);
    IntList pixels = in.readCard32(Short.toUnsignedInt(pixelsLen));
    IntList masks = in.readCard32(Short.toUnsignedInt(masksLen));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.pixels(pixels.toImmutable());
    javaBuilder.masks(masks.toImmutable());
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
    short pixelsLen = (short) pixels.size();
    out.writeCard16(pixelsLen);
    short masksLen = (short) masks.size();
    out.writeCard16(masksLen);
    out.writePad(20);
    out.writeCard32(pixels);
    out.writeCard32(masks);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 4 * pixels.size() + 4 * masks.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class AllocColorCellsReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private IntList pixels;
    @java.lang.SuppressWarnings("all")
    private IntList masks;

    public int getSize() {
      return 32 + 4 * pixels.size() + 4 * masks.size();
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
    public AllocColorCellsReply.AllocColorCellsReplyBuilder pixels(final IntList pixels) {
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
    public AllocColorCellsReply.AllocColorCellsReplyBuilder masks(final IntList masks) {
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
      return "AllocColorCellsReply.AllocColorCellsReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", pixels=" + this.pixels + ", masks=" + this.masks + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  AllocColorCellsReply(final short sequenceNumber, final IntList pixels, final IntList masks) {
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
  public IntList getPixels() {
    return this.pixels;
  }

  @java.lang.SuppressWarnings("all")
  public IntList getMasks() {
    return this.masks;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof AllocColorCellsReply)) return false;
    final AllocColorCellsReply other = (AllocColorCellsReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    final java.lang.Object this$pixels = this.getPixels();
    final java.lang.Object other$pixels = other.getPixels();
    if (this$pixels == null ? other$pixels != null : !this$pixels.equals(other$pixels)) return false;
    final java.lang.Object this$masks = this.getMasks();
    final java.lang.Object other$masks = other.getMasks();
    if (this$masks == null ? other$masks != null : !this$masks.equals(other$masks)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    final java.lang.Object $pixels = this.getPixels();
    result = result * PRIME + ($pixels == null ? 43 : $pixels.hashCode());
    final java.lang.Object $masks = this.getMasks();
    result = result * PRIME + ($masks == null ? 43 : $masks.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "AllocColorCellsReply(sequenceNumber=" + this.getSequenceNumber() + ", pixels=" + this.getPixels() + ", masks=" + this.getMasks() + ")";
  }
}
