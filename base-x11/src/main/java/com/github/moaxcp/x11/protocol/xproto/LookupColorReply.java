package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class LookupColorReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";
  private final short sequenceNumber;
  private final short exactRed;
  private final short exactGreen;
  private final short exactBlue;
  private final short visualRed;
  private final short visualGreen;
  private final short visualBlue;

  public static LookupColorReply readLookupColorReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    LookupColorReply.LookupColorReplyBuilder javaBuilder = LookupColorReply.builder();
    int length = in.readCard32();
    short exactRed = in.readCard16();
    short exactGreen = in.readCard16();
    short exactBlue = in.readCard16();
    short visualRed = in.readCard16();
    short visualGreen = in.readCard16();
    short visualBlue = in.readCard16();
    in.readPad(12);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.exactRed(exactRed);
    javaBuilder.exactGreen(exactGreen);
    javaBuilder.exactBlue(exactBlue);
    javaBuilder.visualRed(visualRed);
    javaBuilder.visualGreen(visualGreen);
    javaBuilder.visualBlue(visualBlue);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard16(exactRed);
    out.writeCard16(exactGreen);
    out.writeCard16(exactBlue);
    out.writeCard16(visualRed);
    out.writeCard16(visualGreen);
    out.writeCard16(visualBlue);
  }

  @Override
  public int getSize() {
    return 20;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class LookupColorReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private short exactRed;
    @java.lang.SuppressWarnings("all")
    private short exactGreen;
    @java.lang.SuppressWarnings("all")
    private short exactBlue;
    @java.lang.SuppressWarnings("all")
    private short visualRed;
    @java.lang.SuppressWarnings("all")
    private short visualGreen;
    @java.lang.SuppressWarnings("all")
    private short visualBlue;

    public int getSize() {
      return 20;
    }

    @java.lang.SuppressWarnings("all")
    LookupColorReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public LookupColorReply.LookupColorReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public LookupColorReply.LookupColorReplyBuilder exactRed(final short exactRed) {
      this.exactRed = exactRed;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public LookupColorReply.LookupColorReplyBuilder exactGreen(final short exactGreen) {
      this.exactGreen = exactGreen;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public LookupColorReply.LookupColorReplyBuilder exactBlue(final short exactBlue) {
      this.exactBlue = exactBlue;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public LookupColorReply.LookupColorReplyBuilder visualRed(final short visualRed) {
      this.visualRed = visualRed;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public LookupColorReply.LookupColorReplyBuilder visualGreen(final short visualGreen) {
      this.visualGreen = visualGreen;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public LookupColorReply.LookupColorReplyBuilder visualBlue(final short visualBlue) {
      this.visualBlue = visualBlue;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public LookupColorReply build() {
      return new LookupColorReply(this.sequenceNumber, this.exactRed, this.exactGreen, this.exactBlue, this.visualRed, this.visualGreen, this.visualBlue);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "LookupColorReply.LookupColorReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", exactRed=" + this.exactRed + ", exactGreen=" + this.exactGreen + ", exactBlue=" + this.exactBlue + ", visualRed=" + this.visualRed + ", visualGreen=" + this.visualGreen + ", visualBlue=" + this.visualBlue + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  LookupColorReply(final short sequenceNumber, final short exactRed, final short exactGreen, final short exactBlue, final short visualRed, final short visualGreen, final short visualBlue) {
    this.sequenceNumber = sequenceNumber;
    this.exactRed = exactRed;
    this.exactGreen = exactGreen;
    this.exactBlue = exactBlue;
    this.visualRed = visualRed;
    this.visualGreen = visualGreen;
    this.visualBlue = visualBlue;
  }

  @java.lang.SuppressWarnings("all")
  public static LookupColorReply.LookupColorReplyBuilder builder() {
    return new LookupColorReply.LookupColorReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public short getExactRed() {
    return this.exactRed;
  }

  @java.lang.SuppressWarnings("all")
  public short getExactGreen() {
    return this.exactGreen;
  }

  @java.lang.SuppressWarnings("all")
  public short getExactBlue() {
    return this.exactBlue;
  }

  @java.lang.SuppressWarnings("all")
  public short getVisualRed() {
    return this.visualRed;
  }

  @java.lang.SuppressWarnings("all")
  public short getVisualGreen() {
    return this.visualGreen;
  }

  @java.lang.SuppressWarnings("all")
  public short getVisualBlue() {
    return this.visualBlue;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof LookupColorReply)) return false;
    final LookupColorReply other = (LookupColorReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getExactRed() != other.getExactRed()) return false;
    if (this.getExactGreen() != other.getExactGreen()) return false;
    if (this.getExactBlue() != other.getExactBlue()) return false;
    if (this.getVisualRed() != other.getVisualRed()) return false;
    if (this.getVisualGreen() != other.getVisualGreen()) return false;
    if (this.getVisualBlue() != other.getVisualBlue()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getExactRed();
    result = result * PRIME + this.getExactGreen();
    result = result * PRIME + this.getExactBlue();
    result = result * PRIME + this.getVisualRed();
    result = result * PRIME + this.getVisualGreen();
    result = result * PRIME + this.getVisualBlue();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "LookupColorReply(sequenceNumber=" + this.getSequenceNumber() + ", exactRed=" + this.getExactRed() + ", exactGreen=" + this.getExactGreen() + ", exactBlue=" + this.getExactBlue() + ", visualRed=" + this.getVisualRed() + ", visualGreen=" + this.getVisualGreen() + ", visualBlue=" + this.getVisualBlue() + ")";
  }
}
