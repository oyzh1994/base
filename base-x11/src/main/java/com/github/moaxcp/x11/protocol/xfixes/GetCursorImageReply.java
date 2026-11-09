package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class GetCursorImageReply implements XReply {
  public static final String PLUGIN_NAME = "xfixes";
  private final short sequenceNumber;
  private final short x;
  private final short y;
  private final short width;
  private final short height;
  private final short xhot;
  private final short yhot;
  private final int cursorSerial;
  private final int[] cursorImage;

  public static GetCursorImageReply readGetCursorImageReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    GetCursorImageReply.GetCursorImageReplyBuilder javaBuilder = GetCursorImageReply.builder();
    int length = in.readCard32();
    short x = in.readInt16();
    short y = in.readInt16();
    short width = in.readCard16();
    short height = in.readCard16();
    short xhot = in.readCard16();
    short yhot = in.readCard16();
    int cursorSerial = in.readCard32();
    byte[] pad11 = in.readPad(8);
    int[] cursorImage = in.readCard32(Short.toUnsignedInt(width) * Short.toUnsignedInt(height));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.x(x);
    javaBuilder.y(y);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.xhot(xhot);
    javaBuilder.yhot(yhot);
    javaBuilder.cursorSerial(cursorSerial);
    javaBuilder.cursorImage(cursorImage);
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
    out.writeInt16(x);
    out.writeInt16(y);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeCard16(xhot);
    out.writeCard16(yhot);
    out.writeCard32(cursorSerial);
    out.writePad(8);
    out.writeCard32(cursorImage);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 4 * cursorImage.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetCursorImageReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private short x;
    @java.lang.SuppressWarnings("all")
    private short y;
    @java.lang.SuppressWarnings("all")
    private short width;
    @java.lang.SuppressWarnings("all")
    private short height;
    @java.lang.SuppressWarnings("all")
    private short xhot;
    @java.lang.SuppressWarnings("all")
    private short yhot;
    @java.lang.SuppressWarnings("all")
    private int cursorSerial;
    @java.lang.SuppressWarnings("all")
    private int[] cursorImage;

    public int getSize() {
      return 32 + 4 * cursorImage.length;
    }

    @java.lang.SuppressWarnings("all")
    GetCursorImageReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCursorImageReply.GetCursorImageReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCursorImageReply.GetCursorImageReplyBuilder x(final short x) {
      this.x = x;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCursorImageReply.GetCursorImageReplyBuilder y(final short y) {
      this.y = y;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCursorImageReply.GetCursorImageReplyBuilder width(final short width) {
      this.width = width;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCursorImageReply.GetCursorImageReplyBuilder height(final short height) {
      this.height = height;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCursorImageReply.GetCursorImageReplyBuilder xhot(final short xhot) {
      this.xhot = xhot;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCursorImageReply.GetCursorImageReplyBuilder yhot(final short yhot) {
      this.yhot = yhot;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCursorImageReply.GetCursorImageReplyBuilder cursorSerial(final int cursorSerial) {
      this.cursorSerial = cursorSerial;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCursorImageReply.GetCursorImageReplyBuilder cursorImage(final int[] cursorImage) {
      if (cursorImage == null) {
        throw new java.lang.NullPointerException("cursorImage is marked non-null but is null");
      }
      this.cursorImage = cursorImage;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetCursorImageReply build() {
      return new GetCursorImageReply(this.sequenceNumber, this.x, this.y, this.width, this.height, this.xhot, this.yhot, this.cursorSerial, this.cursorImage);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetCursorImageReply.GetCursorImageReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", x=" + this.x + ", y=" + this.y + ", width=" + this.width + ", height=" + this.height + ", xhot=" + this.xhot + ", yhot=" + this.yhot + ", cursorSerial=" + this.cursorSerial + ", cursorImage=" + java.util.Arrays.toString(this.cursorImage) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetCursorImageReply(final short sequenceNumber, final short x, final short y, final short width, final short height, final short xhot, final short yhot, final int cursorSerial, final int[] cursorImage) {
    if (cursorImage == null) {
      throw new java.lang.NullPointerException("cursorImage is marked non-null but is null");
    }
    this.sequenceNumber = sequenceNumber;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.xhot = xhot;
    this.yhot = yhot;
    this.cursorSerial = cursorSerial;
    this.cursorImage = cursorImage;
  }

  @java.lang.SuppressWarnings("all")
  public static GetCursorImageReply.GetCursorImageReplyBuilder builder() {
    return new GetCursorImageReply.GetCursorImageReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
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
  public short getXhot() {
    return this.xhot;
  }

  @java.lang.SuppressWarnings("all")
  public short getYhot() {
    return this.yhot;
  }

  @java.lang.SuppressWarnings("all")
  public int getCursorSerial() {
    return this.cursorSerial;
  }

  @java.lang.SuppressWarnings("all")
  public int[] getCursorImage() {
    return this.cursorImage;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetCursorImageReply)) return false;
    final GetCursorImageReply other = (GetCursorImageReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getX() != other.getX()) return false;
    if (this.getY() != other.getY()) return false;
    if (this.getWidth() != other.getWidth()) return false;
    if (this.getHeight() != other.getHeight()) return false;
    if (this.getXhot() != other.getXhot()) return false;
    if (this.getYhot() != other.getYhot()) return false;
    if (this.getCursorSerial() != other.getCursorSerial()) return false;
    if (!java.util.Arrays.equals(this.getCursorImage(), other.getCursorImage())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getX();
    result = result * PRIME + this.getY();
    result = result * PRIME + this.getWidth();
    result = result * PRIME + this.getHeight();
    result = result * PRIME + this.getXhot();
    result = result * PRIME + this.getYhot();
    result = result * PRIME + this.getCursorSerial();
    result = result * PRIME + java.util.Arrays.hashCode(this.getCursorImage());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetCursorImageReply(sequenceNumber=" + this.getSequenceNumber() + ", x=" + this.getX() + ", y=" + this.getY() + ", width=" + this.getWidth() + ", height=" + this.getHeight() + ", xhot=" + this.getXhot() + ", yhot=" + this.getYhot() + ", cursorSerial=" + this.getCursorSerial() + ", cursorImage=" + java.util.Arrays.toString(this.getCursorImage()) + ")";
  }
}
