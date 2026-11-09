package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.IntList;

public final class AllocColorPlanesReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";
  private final short sequenceNumber;
  private final int redMask;
  private final int greenMask;
  private final int blueMask;
  private final IntList pixels;

  public static AllocColorPlanesReply readAllocColorPlanesReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    AllocColorPlanesReply.AllocColorPlanesReplyBuilder javaBuilder = AllocColorPlanesReply.builder();
    int length = in.readCard32();
    short pixelsLen = in.readCard16();
    byte[] pad5 = in.readPad(2);
    int redMask = in.readCard32();
    int greenMask = in.readCard32();
    int blueMask = in.readCard32();
    byte[] pad9 = in.readPad(8);
    IntList pixels = in.readCard32(Short.toUnsignedInt(pixelsLen));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.redMask(redMask);
    javaBuilder.greenMask(greenMask);
    javaBuilder.blueMask(blueMask);
    javaBuilder.pixels(pixels.toImmutable());
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
    out.writePad(2);
    out.writeCard32(redMask);
    out.writeCard32(greenMask);
    out.writeCard32(blueMask);
    out.writePad(8);
    out.writeCard32(pixels);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 4 * pixels.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class AllocColorPlanesReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int redMask;
    @java.lang.SuppressWarnings("all")
    private int greenMask;
    @java.lang.SuppressWarnings("all")
    private int blueMask;
    @java.lang.SuppressWarnings("all")
    private IntList pixels;

    public int getSize() {
      return 32 + 4 * pixels.size();
    }

    @java.lang.SuppressWarnings("all")
    AllocColorPlanesReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AllocColorPlanesReply.AllocColorPlanesReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AllocColorPlanesReply.AllocColorPlanesReplyBuilder redMask(final int redMask) {
      this.redMask = redMask;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AllocColorPlanesReply.AllocColorPlanesReplyBuilder greenMask(final int greenMask) {
      this.greenMask = greenMask;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AllocColorPlanesReply.AllocColorPlanesReplyBuilder blueMask(final int blueMask) {
      this.blueMask = blueMask;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AllocColorPlanesReply.AllocColorPlanesReplyBuilder pixels(final IntList pixels) {
      if (pixels == null) {
        throw new java.lang.NullPointerException("pixels is marked non-null but is null");
      }
      this.pixels = pixels;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public AllocColorPlanesReply build() {
      return new AllocColorPlanesReply(this.sequenceNumber, this.redMask, this.greenMask, this.blueMask, this.pixels);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "AllocColorPlanesReply.AllocColorPlanesReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", redMask=" + this.redMask + ", greenMask=" + this.greenMask + ", blueMask=" + this.blueMask + ", pixels=" + this.pixels + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  AllocColorPlanesReply(final short sequenceNumber, final int redMask, final int greenMask, final int blueMask, final IntList pixels) {
    if (pixels == null) {
      throw new java.lang.NullPointerException("pixels is marked non-null but is null");
    }
    this.sequenceNumber = sequenceNumber;
    this.redMask = redMask;
    this.greenMask = greenMask;
    this.blueMask = blueMask;
    this.pixels = pixels;
  }

  @java.lang.SuppressWarnings("all")
  public static AllocColorPlanesReply.AllocColorPlanesReplyBuilder builder() {
    return new AllocColorPlanesReply.AllocColorPlanesReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getRedMask() {
    return this.redMask;
  }

  @java.lang.SuppressWarnings("all")
  public int getGreenMask() {
    return this.greenMask;
  }

  @java.lang.SuppressWarnings("all")
  public int getBlueMask() {
    return this.blueMask;
  }

  @java.lang.SuppressWarnings("all")
  public IntList getPixels() {
    return this.pixels;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof AllocColorPlanesReply)) return false;
    final AllocColorPlanesReply other = (AllocColorPlanesReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getRedMask() != other.getRedMask()) return false;
    if (this.getGreenMask() != other.getGreenMask()) return false;
    if (this.getBlueMask() != other.getBlueMask()) return false;
    final java.lang.Object this$pixels = this.getPixels();
    final java.lang.Object other$pixels = other.getPixels();
    if (this$pixels == null ? other$pixels != null : !this$pixels.equals(other$pixels)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getRedMask();
    result = result * PRIME + this.getGreenMask();
    result = result * PRIME + this.getBlueMask();
    final java.lang.Object $pixels = this.getPixels();
    result = result * PRIME + ($pixels == null ? 43 : $pixels.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "AllocColorPlanesReply(sequenceNumber=" + this.getSequenceNumber() + ", redMask=" + this.getRedMask() + ", greenMask=" + this.getGreenMask() + ", blueMask=" + this.getBlueMask() + ", pixels=" + this.getPixels() + ")";
  }
}
