package com.github.moaxcp.x11.protocol.shm;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GetImage implements TwoWayRequest<GetImageReply> {
  public static final String PLUGIN_NAME = "shm";
  public static final byte OPCODE = 4;
  private final int drawable;
  private final short x;
  private final short y;
  private final short width;
  private final short height;
  private final int planeMask;
  private final byte format;
  private final int shmseg;
  private final int offset;

  public XReplyFunction<GetImageReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetImageReply.readGetImageReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetImage readGetImage(X11Input in) throws IOException {
    GetImage.GetImageBuilder javaBuilder = GetImage.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int drawable = in.readCard32();
    short x = in.readInt16();
    short y = in.readInt16();
    short width = in.readCard16();
    short height = in.readCard16();
    int planeMask = in.readCard32();
    byte format = in.readCard8();
    byte[] pad10 = in.readPad(3);
    int shmseg = in.readCard32();
    int offset = in.readCard32();
    javaBuilder.drawable(drawable);
    javaBuilder.x(x);
    javaBuilder.y(y);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.planeMask(planeMask);
    javaBuilder.format(format);
    javaBuilder.shmseg(shmseg);
    javaBuilder.offset(offset);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeInt16(x);
    out.writeInt16(y);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeCard32(planeMask);
    out.writeCard8(format);
    out.writePad(3);
    out.writeCard32(shmseg);
    out.writeCard32(offset);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetImageBuilder {
    @java.lang.SuppressWarnings("all")
    private int drawable;
    @java.lang.SuppressWarnings("all")
    private short x;
    @java.lang.SuppressWarnings("all")
    private short y;
    @java.lang.SuppressWarnings("all")
    private short width;
    @java.lang.SuppressWarnings("all")
    private short height;
    @java.lang.SuppressWarnings("all")
    private int planeMask;
    @java.lang.SuppressWarnings("all")
    private byte format;
    @java.lang.SuppressWarnings("all")
    private int shmseg;
    @java.lang.SuppressWarnings("all")
    private int offset;

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    GetImageBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetImage.GetImageBuilder drawable(final int drawable) {
      this.drawable = drawable;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetImage.GetImageBuilder x(final short x) {
      this.x = x;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetImage.GetImageBuilder y(final short y) {
      this.y = y;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetImage.GetImageBuilder width(final short width) {
      this.width = width;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetImage.GetImageBuilder height(final short height) {
      this.height = height;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetImage.GetImageBuilder planeMask(final int planeMask) {
      this.planeMask = planeMask;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetImage.GetImageBuilder format(final byte format) {
      this.format = format;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetImage.GetImageBuilder shmseg(final int shmseg) {
      this.shmseg = shmseg;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetImage.GetImageBuilder offset(final int offset) {
      this.offset = offset;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetImage build() {
      return new GetImage(this.drawable, this.x, this.y, this.width, this.height, this.planeMask, this.format, this.shmseg, this.offset);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetImage.GetImageBuilder(drawable=" + this.drawable + ", x=" + this.x + ", y=" + this.y + ", width=" + this.width + ", height=" + this.height + ", planeMask=" + this.planeMask + ", format=" + this.format + ", shmseg=" + this.shmseg + ", offset=" + this.offset + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetImage(final int drawable, final short x, final short y, final short width, final short height, final int planeMask, final byte format, final int shmseg, final int offset) {
    this.drawable = drawable;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.planeMask = planeMask;
    this.format = format;
    this.shmseg = shmseg;
    this.offset = offset;
  }

  @java.lang.SuppressWarnings("all")
  public static GetImage.GetImageBuilder builder() {
    return new GetImage.GetImageBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getDrawable() {
    return this.drawable;
  }

  @java.lang.SuppressWarnings("all")
  public short getX() {
    return this.x;
  }

  @java.lang.SuppressWarnings("all")
  public short getY() {
    return this.y;
  }

  @java.lang.SuppressWarnings("all")
  public short getWidth() {
    return this.width;
  }

  @java.lang.SuppressWarnings("all")
  public short getHeight() {
    return this.height;
  }

  @java.lang.SuppressWarnings("all")
  public int getPlaneMask() {
    return this.planeMask;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFormat() {
    return this.format;
  }

  @java.lang.SuppressWarnings("all")
  public int getShmseg() {
    return this.shmseg;
  }

  @java.lang.SuppressWarnings("all")
  public int getOffset() {
    return this.offset;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetImage)) return false;
    final GetImage other = (GetImage) o;
    if (this.getDrawable() != other.getDrawable()) return false;
    if (this.getX() != other.getX()) return false;
    if (this.getY() != other.getY()) return false;
    if (this.getWidth() != other.getWidth()) return false;
    if (this.getHeight() != other.getHeight()) return false;
    if (this.getPlaneMask() != other.getPlaneMask()) return false;
    if (this.getFormat() != other.getFormat()) return false;
    if (this.getShmseg() != other.getShmseg()) return false;
    if (this.getOffset() != other.getOffset()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDrawable();
    result = result * PRIME + this.getX();
    result = result * PRIME + this.getY();
    result = result * PRIME + this.getWidth();
    result = result * PRIME + this.getHeight();
    result = result * PRIME + this.getPlaneMask();
    result = result * PRIME + this.getFormat();
    result = result * PRIME + this.getShmseg();
    result = result * PRIME + this.getOffset();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetImage(drawable=" + this.getDrawable() + ", x=" + this.getX() + ", y=" + this.getY() + ", width=" + this.getWidth() + ", height=" + this.getHeight() + ", planeMask=" + this.getPlaneMask() + ", format=" + this.getFormat() + ", shmseg=" + this.getShmseg() + ", offset=" + this.getOffset() + ")";
  }
}
