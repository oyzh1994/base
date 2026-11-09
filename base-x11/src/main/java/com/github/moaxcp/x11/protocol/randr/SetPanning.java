package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class SetPanning implements TwoWayRequest<SetPanningReply> {
  public static final String PLUGIN_NAME = "randr";
  public static final byte OPCODE = 29;
  private final int crtc;
  private final int timestamp;
  private final short left;
  private final short top;
  private final short width;
  private final short height;
  private final short trackLeft;
  private final short trackTop;
  private final short trackWidth;
  private final short trackHeight;
  private final short borderLeft;
  private final short borderTop;
  private final short borderRight;
  private final short borderBottom;

  public XReplyFunction<SetPanningReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> SetPanningReply.readSetPanningReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetPanning readSetPanning(X11Input in) throws IOException {
    SetPanning.SetPanningBuilder javaBuilder = SetPanning.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int crtc = in.readCard32();
    int timestamp = in.readCard32();
    short left = in.readCard16();
    short top = in.readCard16();
    short width = in.readCard16();
    short height = in.readCard16();
    short trackLeft = in.readCard16();
    short trackTop = in.readCard16();
    short trackWidth = in.readCard16();
    short trackHeight = in.readCard16();
    short borderLeft = in.readInt16();
    short borderTop = in.readInt16();
    short borderRight = in.readInt16();
    short borderBottom = in.readInt16();
    javaBuilder.crtc(crtc);
    javaBuilder.timestamp(timestamp);
    javaBuilder.left(left);
    javaBuilder.top(top);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.trackLeft(trackLeft);
    javaBuilder.trackTop(trackTop);
    javaBuilder.trackWidth(trackWidth);
    javaBuilder.trackHeight(trackHeight);
    javaBuilder.borderLeft(borderLeft);
    javaBuilder.borderTop(borderTop);
    javaBuilder.borderRight(borderRight);
    javaBuilder.borderBottom(borderBottom);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(crtc);
    out.writeCard32(timestamp);
    out.writeCard16(left);
    out.writeCard16(top);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeCard16(trackLeft);
    out.writeCard16(trackTop);
    out.writeCard16(trackWidth);
    out.writeCard16(trackHeight);
    out.writeInt16(borderLeft);
    out.writeInt16(borderTop);
    out.writeInt16(borderRight);
    out.writeInt16(borderBottom);
  }

  @Override
  public int getSize() {
    return 36;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetPanningBuilder {
    @java.lang.SuppressWarnings("all")
    private int crtc;
    @java.lang.SuppressWarnings("all")
    private int timestamp;
    @java.lang.SuppressWarnings("all")
    private short left;
    @java.lang.SuppressWarnings("all")
    private short top;
    @java.lang.SuppressWarnings("all")
    private short width;
    @java.lang.SuppressWarnings("all")
    private short height;
    @java.lang.SuppressWarnings("all")
    private short trackLeft;
    @java.lang.SuppressWarnings("all")
    private short trackTop;
    @java.lang.SuppressWarnings("all")
    private short trackWidth;
    @java.lang.SuppressWarnings("all")
    private short trackHeight;
    @java.lang.SuppressWarnings("all")
    private short borderLeft;
    @java.lang.SuppressWarnings("all")
    private short borderTop;
    @java.lang.SuppressWarnings("all")
    private short borderRight;
    @java.lang.SuppressWarnings("all")
    private short borderBottom;

    public int getSize() {
      return 36;
    }

    @java.lang.SuppressWarnings("all")
    SetPanningBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetPanning.SetPanningBuilder crtc(final int crtc) {
      this.crtc = crtc;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetPanning.SetPanningBuilder timestamp(final int timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetPanning.SetPanningBuilder left(final short left) {
      this.left = left;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetPanning.SetPanningBuilder top(final short top) {
      this.top = top;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetPanning.SetPanningBuilder width(final short width) {
      this.width = width;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetPanning.SetPanningBuilder height(final short height) {
      this.height = height;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetPanning.SetPanningBuilder trackLeft(final short trackLeft) {
      this.trackLeft = trackLeft;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetPanning.SetPanningBuilder trackTop(final short trackTop) {
      this.trackTop = trackTop;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetPanning.SetPanningBuilder trackWidth(final short trackWidth) {
      this.trackWidth = trackWidth;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetPanning.SetPanningBuilder trackHeight(final short trackHeight) {
      this.trackHeight = trackHeight;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetPanning.SetPanningBuilder borderLeft(final short borderLeft) {
      this.borderLeft = borderLeft;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetPanning.SetPanningBuilder borderTop(final short borderTop) {
      this.borderTop = borderTop;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetPanning.SetPanningBuilder borderRight(final short borderRight) {
      this.borderRight = borderRight;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetPanning.SetPanningBuilder borderBottom(final short borderBottom) {
      this.borderBottom = borderBottom;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetPanning build() {
      return new SetPanning(this.crtc, this.timestamp, this.left, this.top, this.width, this.height, this.trackLeft, this.trackTop, this.trackWidth, this.trackHeight, this.borderLeft, this.borderTop, this.borderRight, this.borderBottom);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetPanning.SetPanningBuilder(crtc=" + this.crtc + ", timestamp=" + this.timestamp + ", left=" + this.left + ", top=" + this.top + ", width=" + this.width + ", height=" + this.height + ", trackLeft=" + this.trackLeft + ", trackTop=" + this.trackTop + ", trackWidth=" + this.trackWidth + ", trackHeight=" + this.trackHeight + ", borderLeft=" + this.borderLeft + ", borderTop=" + this.borderTop + ", borderRight=" + this.borderRight + ", borderBottom=" + this.borderBottom + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetPanning(final int crtc, final int timestamp, final short left, final short top, final short width, final short height, final short trackLeft, final short trackTop, final short trackWidth, final short trackHeight, final short borderLeft, final short borderTop, final short borderRight, final short borderBottom) {
    this.crtc = crtc;
    this.timestamp = timestamp;
    this.left = left;
    this.top = top;
    this.width = width;
    this.height = height;
    this.trackLeft = trackLeft;
    this.trackTop = trackTop;
    this.trackWidth = trackWidth;
    this.trackHeight = trackHeight;
    this.borderLeft = borderLeft;
    this.borderTop = borderTop;
    this.borderRight = borderRight;
    this.borderBottom = borderBottom;
  }

  @java.lang.SuppressWarnings("all")
  public static SetPanning.SetPanningBuilder builder() {
    return new SetPanning.SetPanningBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getCrtc() {
    return this.crtc;
  }

  @java.lang.SuppressWarnings("all")
  public int getTimestamp() {
    return this.timestamp;
  }

  @java.lang.SuppressWarnings("all")
  public short getLeft() {
    return this.left;
  }

  @java.lang.SuppressWarnings("all")
  public short getTop() {
    return this.top;
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
  public short getTrackLeft() {
    return this.trackLeft;
  }

  @java.lang.SuppressWarnings("all")
  public short getTrackTop() {
    return this.trackTop;
  }

  @java.lang.SuppressWarnings("all")
  public short getTrackWidth() {
    return this.trackWidth;
  }

  @java.lang.SuppressWarnings("all")
  public short getTrackHeight() {
    return this.trackHeight;
  }

  @java.lang.SuppressWarnings("all")
  public short getBorderLeft() {
    return this.borderLeft;
  }

  @java.lang.SuppressWarnings("all")
  public short getBorderTop() {
    return this.borderTop;
  }

  @java.lang.SuppressWarnings("all")
  public short getBorderRight() {
    return this.borderRight;
  }

  @java.lang.SuppressWarnings("all")
  public short getBorderBottom() {
    return this.borderBottom;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetPanning)) return false;
    final SetPanning other = (SetPanning) o;
    if (this.getCrtc() != other.getCrtc()) return false;
    if (this.getTimestamp() != other.getTimestamp()) return false;
    if (this.getLeft() != other.getLeft()) return false;
    if (this.getTop() != other.getTop()) return false;
    if (this.getWidth() != other.getWidth()) return false;
    if (this.getHeight() != other.getHeight()) return false;
    if (this.getTrackLeft() != other.getTrackLeft()) return false;
    if (this.getTrackTop() != other.getTrackTop()) return false;
    if (this.getTrackWidth() != other.getTrackWidth()) return false;
    if (this.getTrackHeight() != other.getTrackHeight()) return false;
    if (this.getBorderLeft() != other.getBorderLeft()) return false;
    if (this.getBorderTop() != other.getBorderTop()) return false;
    if (this.getBorderRight() != other.getBorderRight()) return false;
    if (this.getBorderBottom() != other.getBorderBottom()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getCrtc();
    result = result * PRIME + this.getTimestamp();
    result = result * PRIME + this.getLeft();
    result = result * PRIME + this.getTop();
    result = result * PRIME + this.getWidth();
    result = result * PRIME + this.getHeight();
    result = result * PRIME + this.getTrackLeft();
    result = result * PRIME + this.getTrackTop();
    result = result * PRIME + this.getTrackWidth();
    result = result * PRIME + this.getTrackHeight();
    result = result * PRIME + this.getBorderLeft();
    result = result * PRIME + this.getBorderTop();
    result = result * PRIME + this.getBorderRight();
    result = result * PRIME + this.getBorderBottom();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetPanning(crtc=" + this.getCrtc() + ", timestamp=" + this.getTimestamp() + ", left=" + this.getLeft() + ", top=" + this.getTop() + ", width=" + this.getWidth() + ", height=" + this.getHeight() + ", trackLeft=" + this.getTrackLeft() + ", trackTop=" + this.getTrackTop() + ", trackWidth=" + this.getTrackWidth() + ", trackHeight=" + this.getTrackHeight() + ", borderLeft=" + this.getBorderLeft() + ", borderTop=" + this.getBorderTop() + ", borderRight=" + this.getBorderRight() + ", borderBottom=" + this.getBorderBottom() + ")";
  }
}
