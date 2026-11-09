package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class AllocColorReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";
  private final short sequenceNumber;
  private final short red;
  private final short green;
  private final short blue;
  private final int pixel;

  public static AllocColorReply readAllocColorReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    AllocColorReply.AllocColorReplyBuilder javaBuilder = AllocColorReply.builder();
    int length = in.readCard32();
    short red = in.readCard16();
    short green = in.readCard16();
    short blue = in.readCard16();
    byte[] pad7 = in.readPad(2);
    int pixel = in.readCard32();
    in.readPad(12);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.red(red);
    javaBuilder.green(green);
    javaBuilder.blue(blue);
    javaBuilder.pixel(pixel);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard16(red);
    out.writeCard16(green);
    out.writeCard16(blue);
    out.writePad(2);
    out.writeCard32(pixel);
  }

  @Override
  public int getSize() {
    return 20;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class AllocColorReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private short red;
    @java.lang.SuppressWarnings("all")
    private short green;
    @java.lang.SuppressWarnings("all")
    private short blue;
    @java.lang.SuppressWarnings("all")
    private int pixel;

    public int getSize() {
      return 20;
    }

    @java.lang.SuppressWarnings("all")
    AllocColorReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AllocColorReply.AllocColorReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AllocColorReply.AllocColorReplyBuilder red(final short red) {
      this.red = red;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AllocColorReply.AllocColorReplyBuilder green(final short green) {
      this.green = green;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AllocColorReply.AllocColorReplyBuilder blue(final short blue) {
      this.blue = blue;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AllocColorReply.AllocColorReplyBuilder pixel(final int pixel) {
      this.pixel = pixel;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public AllocColorReply build() {
      return new AllocColorReply(this.sequenceNumber, this.red, this.green, this.blue, this.pixel);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "AllocColorReply.AllocColorReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", red=" + this.red + ", green=" + this.green + ", blue=" + this.blue + ", pixel=" + this.pixel + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  AllocColorReply(final short sequenceNumber, final short red, final short green, final short blue, final int pixel) {
    this.sequenceNumber = sequenceNumber;
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.pixel = pixel;
  }

  @java.lang.SuppressWarnings("all")
  public static AllocColorReply.AllocColorReplyBuilder builder() {
    return new AllocColorReply.AllocColorReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public short getRed() {
    return this.red;
  }

  @java.lang.SuppressWarnings("all")
  public short getGreen() {
    return this.green;
  }

  @java.lang.SuppressWarnings("all")
  public short getBlue() {
    return this.blue;
  }

  @java.lang.SuppressWarnings("all")
  public int getPixel() {
    return this.pixel;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof AllocColorReply)) return false;
    final AllocColorReply other = (AllocColorReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getRed() != other.getRed()) return false;
    if (this.getGreen() != other.getGreen()) return false;
    if (this.getBlue() != other.getBlue()) return false;
    if (this.getPixel() != other.getPixel()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getRed();
    result = result * PRIME + this.getGreen();
    result = result * PRIME + this.getBlue();
    result = result * PRIME + this.getPixel();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "AllocColorReply(sequenceNumber=" + this.getSequenceNumber() + ", red=" + this.getRed() + ", green=" + this.getGreen() + ", blue=" + this.getBlue() + ", pixel=" + this.getPixel() + ")";
  }
}
