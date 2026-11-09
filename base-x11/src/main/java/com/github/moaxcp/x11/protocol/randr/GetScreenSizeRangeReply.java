package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class GetScreenSizeRangeReply implements XReply {
  public static final String PLUGIN_NAME = "randr";
  private final short sequenceNumber;
  private final short minWidth;
  private final short minHeight;
  private final short maxWidth;
  private final short maxHeight;

  public static GetScreenSizeRangeReply readGetScreenSizeRangeReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    GetScreenSizeRangeReply.GetScreenSizeRangeReplyBuilder javaBuilder = GetScreenSizeRangeReply.builder();
    int length = in.readCard32();
    short minWidth = in.readCard16();
    short minHeight = in.readCard16();
    short maxWidth = in.readCard16();
    short maxHeight = in.readCard16();
    byte[] pad8 = in.readPad(16);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.minWidth(minWidth);
    javaBuilder.minHeight(minHeight);
    javaBuilder.maxWidth(maxWidth);
    javaBuilder.maxHeight(maxHeight);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard16(minWidth);
    out.writeCard16(minHeight);
    out.writeCard16(maxWidth);
    out.writeCard16(maxHeight);
    out.writePad(16);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetScreenSizeRangeReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private short minWidth;
    @java.lang.SuppressWarnings("all")
    private short minHeight;
    @java.lang.SuppressWarnings("all")
    private short maxWidth;
    @java.lang.SuppressWarnings("all")
    private short maxHeight;

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    GetScreenSizeRangeReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenSizeRangeReply.GetScreenSizeRangeReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenSizeRangeReply.GetScreenSizeRangeReplyBuilder minWidth(final short minWidth) {
      this.minWidth = minWidth;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenSizeRangeReply.GetScreenSizeRangeReplyBuilder minHeight(final short minHeight) {
      this.minHeight = minHeight;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenSizeRangeReply.GetScreenSizeRangeReplyBuilder maxWidth(final short maxWidth) {
      this.maxWidth = maxWidth;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenSizeRangeReply.GetScreenSizeRangeReplyBuilder maxHeight(final short maxHeight) {
      this.maxHeight = maxHeight;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetScreenSizeRangeReply build() {
      return new GetScreenSizeRangeReply(this.sequenceNumber, this.minWidth, this.minHeight, this.maxWidth, this.maxHeight);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetScreenSizeRangeReply.GetScreenSizeRangeReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", minWidth=" + this.minWidth + ", minHeight=" + this.minHeight + ", maxWidth=" + this.maxWidth + ", maxHeight=" + this.maxHeight + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetScreenSizeRangeReply(final short sequenceNumber, final short minWidth, final short minHeight, final short maxWidth, final short maxHeight) {
    this.sequenceNumber = sequenceNumber;
    this.minWidth = minWidth;
    this.minHeight = minHeight;
    this.maxWidth = maxWidth;
    this.maxHeight = maxHeight;
  }

  @java.lang.SuppressWarnings("all")
  public static GetScreenSizeRangeReply.GetScreenSizeRangeReplyBuilder builder() {
    return new GetScreenSizeRangeReply.GetScreenSizeRangeReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public short getMinWidth() {
    return this.minWidth;
  }

  @java.lang.SuppressWarnings("all")
  public short getMinHeight() {
    return this.minHeight;
  }

  @java.lang.SuppressWarnings("all")
  public short getMaxWidth() {
    return this.maxWidth;
  }

  @java.lang.SuppressWarnings("all")
  public short getMaxHeight() {
    return this.maxHeight;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetScreenSizeRangeReply)) return false;
    final GetScreenSizeRangeReply other = (GetScreenSizeRangeReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getMinWidth() != other.getMinWidth()) return false;
    if (this.getMinHeight() != other.getMinHeight()) return false;
    if (this.getMaxWidth() != other.getMaxWidth()) return false;
    if (this.getMaxHeight() != other.getMaxHeight()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getMinWidth();
    result = result * PRIME + this.getMinHeight();
    result = result * PRIME + this.getMaxWidth();
    result = result * PRIME + this.getMaxHeight();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetScreenSizeRangeReply(sequenceNumber=" + this.getSequenceNumber() + ", minWidth=" + this.getMinWidth() + ", minHeight=" + this.getMinHeight() + ", maxWidth=" + this.getMaxWidth() + ", maxHeight=" + this.getMaxHeight() + ")";
  }
}
