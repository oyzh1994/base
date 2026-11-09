package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class QueryTextExtentsReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";
  private final byte drawDirection;
  private final short sequenceNumber;
  private final short fontAscent;
  private final short fontDescent;
  private final short overallAscent;
  private final short overallDescent;
  private final int overallWidth;
  private final int overallLeft;
  private final int overallRight;

  public static QueryTextExtentsReply readQueryTextExtentsReply(byte drawDirection, short sequenceNumber, X11Input in) throws IOException {
    QueryTextExtentsReply.QueryTextExtentsReplyBuilder javaBuilder = QueryTextExtentsReply.builder();
    int length = in.readCard32();
    short fontAscent = in.readInt16();
    short fontDescent = in.readInt16();
    short overallAscent = in.readInt16();
    short overallDescent = in.readInt16();
    int overallWidth = in.readInt32();
    int overallLeft = in.readInt32();
    int overallRight = in.readInt32();
    in.readPad(4);
    javaBuilder.drawDirection(drawDirection);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.fontAscent(fontAscent);
    javaBuilder.fontDescent(fontDescent);
    javaBuilder.overallAscent(overallAscent);
    javaBuilder.overallDescent(overallDescent);
    javaBuilder.overallWidth(overallWidth);
    javaBuilder.overallLeft(overallLeft);
    javaBuilder.overallRight(overallRight);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeByte(drawDirection);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeInt16(fontAscent);
    out.writeInt16(fontDescent);
    out.writeInt16(overallAscent);
    out.writeInt16(overallDescent);
    out.writeInt32(overallWidth);
    out.writeInt32(overallLeft);
    out.writeInt32(overallRight);
  }

  @Override
  public int getSize() {
    return 28;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryTextExtentsReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte drawDirection;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private short fontAscent;
    @java.lang.SuppressWarnings("all")
    private short fontDescent;
    @java.lang.SuppressWarnings("all")
    private short overallAscent;
    @java.lang.SuppressWarnings("all")
    private short overallDescent;
    @java.lang.SuppressWarnings("all")
    private int overallWidth;
    @java.lang.SuppressWarnings("all")
    private int overallLeft;
    @java.lang.SuppressWarnings("all")
    private int overallRight;

    public QueryTextExtentsReply.QueryTextExtentsReplyBuilder drawDirection(FontDraw drawDirection) {
      this.drawDirection = (byte) drawDirection.getValue();
      return this;
    }

    public QueryTextExtentsReply.QueryTextExtentsReplyBuilder drawDirection(byte drawDirection) {
      this.drawDirection = drawDirection;
      return this;
    }

    public int getSize() {
      return 28;
    }

    @java.lang.SuppressWarnings("all")
    QueryTextExtentsReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryTextExtentsReply.QueryTextExtentsReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryTextExtentsReply.QueryTextExtentsReplyBuilder fontAscent(final short fontAscent) {
      this.fontAscent = fontAscent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryTextExtentsReply.QueryTextExtentsReplyBuilder fontDescent(final short fontDescent) {
      this.fontDescent = fontDescent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryTextExtentsReply.QueryTextExtentsReplyBuilder overallAscent(final short overallAscent) {
      this.overallAscent = overallAscent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryTextExtentsReply.QueryTextExtentsReplyBuilder overallDescent(final short overallDescent) {
      this.overallDescent = overallDescent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryTextExtentsReply.QueryTextExtentsReplyBuilder overallWidth(final int overallWidth) {
      this.overallWidth = overallWidth;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryTextExtentsReply.QueryTextExtentsReplyBuilder overallLeft(final int overallLeft) {
      this.overallLeft = overallLeft;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryTextExtentsReply.QueryTextExtentsReplyBuilder overallRight(final int overallRight) {
      this.overallRight = overallRight;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryTextExtentsReply build() {
      return new QueryTextExtentsReply(this.drawDirection, this.sequenceNumber, this.fontAscent, this.fontDescent, this.overallAscent, this.overallDescent, this.overallWidth, this.overallLeft, this.overallRight);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryTextExtentsReply.QueryTextExtentsReplyBuilder(drawDirection=" + this.drawDirection + ", sequenceNumber=" + this.sequenceNumber + ", fontAscent=" + this.fontAscent + ", fontDescent=" + this.fontDescent + ", overallAscent=" + this.overallAscent + ", overallDescent=" + this.overallDescent + ", overallWidth=" + this.overallWidth + ", overallLeft=" + this.overallLeft + ", overallRight=" + this.overallRight + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryTextExtentsReply(final byte drawDirection, final short sequenceNumber, final short fontAscent, final short fontDescent, final short overallAscent, final short overallDescent, final int overallWidth, final int overallLeft, final int overallRight) {
    this.drawDirection = drawDirection;
    this.sequenceNumber = sequenceNumber;
    this.fontAscent = fontAscent;
    this.fontDescent = fontDescent;
    this.overallAscent = overallAscent;
    this.overallDescent = overallDescent;
    this.overallWidth = overallWidth;
    this.overallLeft = overallLeft;
    this.overallRight = overallRight;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryTextExtentsReply.QueryTextExtentsReplyBuilder builder() {
    return new QueryTextExtentsReply.QueryTextExtentsReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getDrawDirection() {
    return this.drawDirection;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public short getFontAscent() {
    return this.fontAscent;
  }

  @java.lang.SuppressWarnings("all")
  public short getFontDescent() {
    return this.fontDescent;
  }

  @java.lang.SuppressWarnings("all")
  public short getOverallAscent() {
    return this.overallAscent;
  }

  @java.lang.SuppressWarnings("all")
  public short getOverallDescent() {
    return this.overallDescent;
  }

  @java.lang.SuppressWarnings("all")
  public int getOverallWidth() {
    return this.overallWidth;
  }

  @java.lang.SuppressWarnings("all")
  public int getOverallLeft() {
    return this.overallLeft;
  }

  @java.lang.SuppressWarnings("all")
  public int getOverallRight() {
    return this.overallRight;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryTextExtentsReply)) return false;
    final QueryTextExtentsReply other = (QueryTextExtentsReply) o;
    if (this.getDrawDirection() != other.getDrawDirection()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getFontAscent() != other.getFontAscent()) return false;
    if (this.getFontDescent() != other.getFontDescent()) return false;
    if (this.getOverallAscent() != other.getOverallAscent()) return false;
    if (this.getOverallDescent() != other.getOverallDescent()) return false;
    if (this.getOverallWidth() != other.getOverallWidth()) return false;
    if (this.getOverallLeft() != other.getOverallLeft()) return false;
    if (this.getOverallRight() != other.getOverallRight()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDrawDirection();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getFontAscent();
    result = result * PRIME + this.getFontDescent();
    result = result * PRIME + this.getOverallAscent();
    result = result * PRIME + this.getOverallDescent();
    result = result * PRIME + this.getOverallWidth();
    result = result * PRIME + this.getOverallLeft();
    result = result * PRIME + this.getOverallRight();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryTextExtentsReply(drawDirection=" + this.getDrawDirection() + ", sequenceNumber=" + this.getSequenceNumber() + ", fontAscent=" + this.getFontAscent() + ", fontDescent=" + this.getFontDescent() + ", overallAscent=" + this.getOverallAscent() + ", overallDescent=" + this.getOverallDescent() + ", overallWidth=" + this.getOverallWidth() + ", overallLeft=" + this.getOverallLeft() + ", overallRight=" + this.getOverallRight() + ")";
  }
}
