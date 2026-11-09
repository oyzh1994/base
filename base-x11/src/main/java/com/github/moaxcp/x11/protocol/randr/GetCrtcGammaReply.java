package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class GetCrtcGammaReply implements XReply {
  public static final String PLUGIN_NAME = "randr";
  private final short sequenceNumber;
  private final short[] red;
  private final short[] green;
  private final short[] blue;

  public static GetCrtcGammaReply readGetCrtcGammaReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    GetCrtcGammaReply.GetCrtcGammaReplyBuilder javaBuilder = GetCrtcGammaReply.builder();
    int length = in.readCard32();
    short size = in.readCard16();
    byte[] pad5 = in.readPad(22);
    short[] red = in.readCard16(Short.toUnsignedInt(size));
    short[] green = in.readCard16(Short.toUnsignedInt(size));
    short[] blue = in.readCard16(Short.toUnsignedInt(size));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.red(red);
    javaBuilder.green(green);
    javaBuilder.blue(blue);
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
    short size = (short) blue.length;
    out.writeCard16(size);
    out.writePad(22);
    out.writeCard16(red);
    out.writeCard16(green);
    out.writeCard16(blue);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 2 * red.length + 2 * green.length + 2 * blue.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetCrtcGammaReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private short[] red;
    @java.lang.SuppressWarnings("all")
    private short[] green;
    @java.lang.SuppressWarnings("all")
    private short[] blue;

    public int getSize() {
      return 32 + 2 * red.length + 2 * green.length + 2 * blue.length;
    }

    @java.lang.SuppressWarnings("all")
    GetCrtcGammaReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCrtcGammaReply.GetCrtcGammaReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCrtcGammaReply.GetCrtcGammaReplyBuilder red(final short[] red) {
      if (red == null) {
        throw new java.lang.NullPointerException("red is marked non-null but is null");
      }
      this.red = red;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCrtcGammaReply.GetCrtcGammaReplyBuilder green(final short[] green) {
      if (green == null) {
        throw new java.lang.NullPointerException("green is marked non-null but is null");
      }
      this.green = green;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCrtcGammaReply.GetCrtcGammaReplyBuilder blue(final short[] blue) {
      if (blue == null) {
        throw new java.lang.NullPointerException("blue is marked non-null but is null");
      }
      this.blue = blue;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetCrtcGammaReply build() {
      return new GetCrtcGammaReply(this.sequenceNumber, this.red, this.green, this.blue);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetCrtcGammaReply.GetCrtcGammaReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", red=" + java.util.Arrays.toString(this.red) + ", green=" + java.util.Arrays.toString(this.green) + ", blue=" + java.util.Arrays.toString(this.blue) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetCrtcGammaReply(final short sequenceNumber, final short[] red, final short[] green, final short[] blue) {
    if (red == null) {
      throw new java.lang.NullPointerException("red is marked non-null but is null");
    }
    if (green == null) {
      throw new java.lang.NullPointerException("green is marked non-null but is null");
    }
    if (blue == null) {
      throw new java.lang.NullPointerException("blue is marked non-null but is null");
    }
    this.sequenceNumber = sequenceNumber;
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  @java.lang.SuppressWarnings("all")
  public static GetCrtcGammaReply.GetCrtcGammaReplyBuilder builder() {
    return new GetCrtcGammaReply.GetCrtcGammaReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public short[] getRed() {
    return this.red;
  }

  @java.lang.SuppressWarnings("all")
  public short[] getGreen() {
    return this.green;
  }

  @java.lang.SuppressWarnings("all")
  public short[] getBlue() {
    return this.blue;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetCrtcGammaReply)) return false;
    final GetCrtcGammaReply other = (GetCrtcGammaReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (!java.util.Arrays.equals(this.getRed(), other.getRed())) return false;
    if (!java.util.Arrays.equals(this.getGreen(), other.getGreen())) return false;
    if (!java.util.Arrays.equals(this.getBlue(), other.getBlue())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + java.util.Arrays.hashCode(this.getRed());
    result = result * PRIME + java.util.Arrays.hashCode(this.getGreen());
    result = result * PRIME + java.util.Arrays.hashCode(this.getBlue());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetCrtcGammaReply(sequenceNumber=" + this.getSequenceNumber() + ", red=" + java.util.Arrays.toString(this.getRed()) + ", green=" + java.util.Arrays.toString(this.getGreen()) + ", blue=" + java.util.Arrays.toString(this.getBlue()) + ")";
  }
}
