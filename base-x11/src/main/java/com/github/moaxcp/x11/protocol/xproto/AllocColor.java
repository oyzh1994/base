package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class AllocColor implements TwoWayRequest<AllocColorReply> {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 84;
  private final int cmap;
  private final short red;
  private final short green;
  private final short blue;

  public XReplyFunction<AllocColorReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> AllocColorReply.readAllocColorReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static AllocColor readAllocColor(X11Input in) throws IOException {
    AllocColor.AllocColorBuilder javaBuilder = AllocColor.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int cmap = in.readCard32();
    short red = in.readCard16();
    short green = in.readCard16();
    short blue = in.readCard16();
    byte[] pad7 = in.readPad(2);
    javaBuilder.cmap(cmap);
    javaBuilder.red(red);
    javaBuilder.green(green);
    javaBuilder.blue(blue);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(cmap);
    out.writeCard16(red);
    out.writeCard16(green);
    out.writeCard16(blue);
    out.writePad(2);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class AllocColorBuilder {
    @java.lang.SuppressWarnings("all")
    private int cmap;
    @java.lang.SuppressWarnings("all")
    private short red;
    @java.lang.SuppressWarnings("all")
    private short green;
    @java.lang.SuppressWarnings("all")
    private short blue;

    public int getSize() {
      return 16;
    }

    @java.lang.SuppressWarnings("all")
    AllocColorBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AllocColor.AllocColorBuilder cmap(final int cmap) {
      this.cmap = cmap;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AllocColor.AllocColorBuilder red(final short red) {
      this.red = red;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AllocColor.AllocColorBuilder green(final short green) {
      this.green = green;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AllocColor.AllocColorBuilder blue(final short blue) {
      this.blue = blue;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public AllocColor build() {
      return new AllocColor(this.cmap, this.red, this.green, this.blue);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "AllocColor.AllocColorBuilder(cmap=" + this.cmap + ", red=" + this.red + ", green=" + this.green + ", blue=" + this.blue + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  AllocColor(final int cmap, final short red, final short green, final short blue) {
    this.cmap = cmap;
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  @java.lang.SuppressWarnings("all")
  public static AllocColor.AllocColorBuilder builder() {
    return new AllocColor.AllocColorBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getCmap() {
    return this.cmap;
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

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof AllocColor)) return false;
    final AllocColor other = (AllocColor) o;
    if (this.getCmap() != other.getCmap()) return false;
    if (this.getRed() != other.getRed()) return false;
    if (this.getGreen() != other.getGreen()) return false;
    if (this.getBlue() != other.getBlue()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getCmap();
    result = result * PRIME + this.getRed();
    result = result * PRIME + this.getGreen();
    result = result * PRIME + this.getBlue();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "AllocColor(cmap=" + this.getCmap() + ", red=" + this.getRed() + ", green=" + this.getGreen() + ", blue=" + this.getBlue() + ")";
  }
}
