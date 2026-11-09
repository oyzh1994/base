package com.github.moaxcp.x11.protocol.shape;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class QueryExtentsReply implements XReply {
  public static final String PLUGIN_NAME = "shape";
  private final short sequenceNumber;
  private final boolean boundingShaped;
  private final boolean clipShaped;
  private final short boundingShapeExtentsX;
  private final short boundingShapeExtentsY;
  private final short boundingShapeExtentsWidth;
  private final short boundingShapeExtentsHeight;
  private final short clipShapeExtentsX;
  private final short clipShapeExtentsY;
  private final short clipShapeExtentsWidth;
  private final short clipShapeExtentsHeight;

  public static QueryExtentsReply readQueryExtentsReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    QueryExtentsReply.QueryExtentsReplyBuilder javaBuilder = QueryExtentsReply.builder();
    int length = in.readCard32();
    boolean boundingShaped = in.readBool();
    boolean clipShaped = in.readBool();
    byte[] pad6 = in.readPad(2);
    short boundingShapeExtentsX = in.readInt16();
    short boundingShapeExtentsY = in.readInt16();
    short boundingShapeExtentsWidth = in.readCard16();
    short boundingShapeExtentsHeight = in.readCard16();
    short clipShapeExtentsX = in.readInt16();
    short clipShapeExtentsY = in.readInt16();
    short clipShapeExtentsWidth = in.readCard16();
    short clipShapeExtentsHeight = in.readCard16();
    in.readPad(4);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.boundingShaped(boundingShaped);
    javaBuilder.clipShaped(clipShaped);
    javaBuilder.boundingShapeExtentsX(boundingShapeExtentsX);
    javaBuilder.boundingShapeExtentsY(boundingShapeExtentsY);
    javaBuilder.boundingShapeExtentsWidth(boundingShapeExtentsWidth);
    javaBuilder.boundingShapeExtentsHeight(boundingShapeExtentsHeight);
    javaBuilder.clipShapeExtentsX(clipShapeExtentsX);
    javaBuilder.clipShapeExtentsY(clipShapeExtentsY);
    javaBuilder.clipShapeExtentsWidth(clipShapeExtentsWidth);
    javaBuilder.clipShapeExtentsHeight(clipShapeExtentsHeight);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeBool(boundingShaped);
    out.writeBool(clipShaped);
    out.writePad(2);
    out.writeInt16(boundingShapeExtentsX);
    out.writeInt16(boundingShapeExtentsY);
    out.writeCard16(boundingShapeExtentsWidth);
    out.writeCard16(boundingShapeExtentsHeight);
    out.writeInt16(clipShapeExtentsX);
    out.writeInt16(clipShapeExtentsY);
    out.writeCard16(clipShapeExtentsWidth);
    out.writeCard16(clipShapeExtentsHeight);
  }

  @Override
  public int getSize() {
    return 28;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryExtentsReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private boolean boundingShaped;
    @java.lang.SuppressWarnings("all")
    private boolean clipShaped;
    @java.lang.SuppressWarnings("all")
    private short boundingShapeExtentsX;
    @java.lang.SuppressWarnings("all")
    private short boundingShapeExtentsY;
    @java.lang.SuppressWarnings("all")
    private short boundingShapeExtentsWidth;
    @java.lang.SuppressWarnings("all")
    private short boundingShapeExtentsHeight;
    @java.lang.SuppressWarnings("all")
    private short clipShapeExtentsX;
    @java.lang.SuppressWarnings("all")
    private short clipShapeExtentsY;
    @java.lang.SuppressWarnings("all")
    private short clipShapeExtentsWidth;
    @java.lang.SuppressWarnings("all")
    private short clipShapeExtentsHeight;

    public int getSize() {
      return 28;
    }

    @java.lang.SuppressWarnings("all")
    QueryExtentsReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryExtentsReply.QueryExtentsReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryExtentsReply.QueryExtentsReplyBuilder boundingShaped(final boolean boundingShaped) {
      this.boundingShaped = boundingShaped;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryExtentsReply.QueryExtentsReplyBuilder clipShaped(final boolean clipShaped) {
      this.clipShaped = clipShaped;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryExtentsReply.QueryExtentsReplyBuilder boundingShapeExtentsX(final short boundingShapeExtentsX) {
      this.boundingShapeExtentsX = boundingShapeExtentsX;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryExtentsReply.QueryExtentsReplyBuilder boundingShapeExtentsY(final short boundingShapeExtentsY) {
      this.boundingShapeExtentsY = boundingShapeExtentsY;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryExtentsReply.QueryExtentsReplyBuilder boundingShapeExtentsWidth(final short boundingShapeExtentsWidth) {
      this.boundingShapeExtentsWidth = boundingShapeExtentsWidth;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryExtentsReply.QueryExtentsReplyBuilder boundingShapeExtentsHeight(final short boundingShapeExtentsHeight) {
      this.boundingShapeExtentsHeight = boundingShapeExtentsHeight;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryExtentsReply.QueryExtentsReplyBuilder clipShapeExtentsX(final short clipShapeExtentsX) {
      this.clipShapeExtentsX = clipShapeExtentsX;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryExtentsReply.QueryExtentsReplyBuilder clipShapeExtentsY(final short clipShapeExtentsY) {
      this.clipShapeExtentsY = clipShapeExtentsY;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryExtentsReply.QueryExtentsReplyBuilder clipShapeExtentsWidth(final short clipShapeExtentsWidth) {
      this.clipShapeExtentsWidth = clipShapeExtentsWidth;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryExtentsReply.QueryExtentsReplyBuilder clipShapeExtentsHeight(final short clipShapeExtentsHeight) {
      this.clipShapeExtentsHeight = clipShapeExtentsHeight;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryExtentsReply build() {
      return new QueryExtentsReply(this.sequenceNumber, this.boundingShaped, this.clipShaped, this.boundingShapeExtentsX, this.boundingShapeExtentsY, this.boundingShapeExtentsWidth, this.boundingShapeExtentsHeight, this.clipShapeExtentsX, this.clipShapeExtentsY, this.clipShapeExtentsWidth, this.clipShapeExtentsHeight);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryExtentsReply.QueryExtentsReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", boundingShaped=" + this.boundingShaped + ", clipShaped=" + this.clipShaped + ", boundingShapeExtentsX=" + this.boundingShapeExtentsX + ", boundingShapeExtentsY=" + this.boundingShapeExtentsY + ", boundingShapeExtentsWidth=" + this.boundingShapeExtentsWidth + ", boundingShapeExtentsHeight=" + this.boundingShapeExtentsHeight + ", clipShapeExtentsX=" + this.clipShapeExtentsX + ", clipShapeExtentsY=" + this.clipShapeExtentsY + ", clipShapeExtentsWidth=" + this.clipShapeExtentsWidth + ", clipShapeExtentsHeight=" + this.clipShapeExtentsHeight + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryExtentsReply(final short sequenceNumber, final boolean boundingShaped, final boolean clipShaped, final short boundingShapeExtentsX, final short boundingShapeExtentsY, final short boundingShapeExtentsWidth, final short boundingShapeExtentsHeight, final short clipShapeExtentsX, final short clipShapeExtentsY, final short clipShapeExtentsWidth, final short clipShapeExtentsHeight) {
    this.sequenceNumber = sequenceNumber;
    this.boundingShaped = boundingShaped;
    this.clipShaped = clipShaped;
    this.boundingShapeExtentsX = boundingShapeExtentsX;
    this.boundingShapeExtentsY = boundingShapeExtentsY;
    this.boundingShapeExtentsWidth = boundingShapeExtentsWidth;
    this.boundingShapeExtentsHeight = boundingShapeExtentsHeight;
    this.clipShapeExtentsX = clipShapeExtentsX;
    this.clipShapeExtentsY = clipShapeExtentsY;
    this.clipShapeExtentsWidth = clipShapeExtentsWidth;
    this.clipShapeExtentsHeight = clipShapeExtentsHeight;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryExtentsReply.QueryExtentsReplyBuilder builder() {
    return new QueryExtentsReply.QueryExtentsReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isBoundingShaped() {
    return this.boundingShaped;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isClipShaped() {
    return this.clipShaped;
  }

  @java.lang.SuppressWarnings("all")
  public short getBoundingShapeExtentsX() {
    return this.boundingShapeExtentsX;
  }

  @java.lang.SuppressWarnings("all")
  public short getBoundingShapeExtentsY() {
    return this.boundingShapeExtentsY;
  }

  @java.lang.SuppressWarnings("all")
  public short getBoundingShapeExtentsWidth() {
    return this.boundingShapeExtentsWidth;
  }

  @java.lang.SuppressWarnings("all")
  public short getBoundingShapeExtentsHeight() {
    return this.boundingShapeExtentsHeight;
  }

  @java.lang.SuppressWarnings("all")
  public short getClipShapeExtentsX() {
    return this.clipShapeExtentsX;
  }

  @java.lang.SuppressWarnings("all")
  public short getClipShapeExtentsY() {
    return this.clipShapeExtentsY;
  }

  @java.lang.SuppressWarnings("all")
  public short getClipShapeExtentsWidth() {
    return this.clipShapeExtentsWidth;
  }

  @java.lang.SuppressWarnings("all")
  public short getClipShapeExtentsHeight() {
    return this.clipShapeExtentsHeight;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryExtentsReply)) return false;
    final QueryExtentsReply other = (QueryExtentsReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.isBoundingShaped() != other.isBoundingShaped()) return false;
    if (this.isClipShaped() != other.isClipShaped()) return false;
    if (this.getBoundingShapeExtentsX() != other.getBoundingShapeExtentsX()) return false;
    if (this.getBoundingShapeExtentsY() != other.getBoundingShapeExtentsY()) return false;
    if (this.getBoundingShapeExtentsWidth() != other.getBoundingShapeExtentsWidth()) return false;
    if (this.getBoundingShapeExtentsHeight() != other.getBoundingShapeExtentsHeight()) return false;
    if (this.getClipShapeExtentsX() != other.getClipShapeExtentsX()) return false;
    if (this.getClipShapeExtentsY() != other.getClipShapeExtentsY()) return false;
    if (this.getClipShapeExtentsWidth() != other.getClipShapeExtentsWidth()) return false;
    if (this.getClipShapeExtentsHeight() != other.getClipShapeExtentsHeight()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + (this.isBoundingShaped() ? 79 : 97);
    result = result * PRIME + (this.isClipShaped() ? 79 : 97);
    result = result * PRIME + this.getBoundingShapeExtentsX();
    result = result * PRIME + this.getBoundingShapeExtentsY();
    result = result * PRIME + this.getBoundingShapeExtentsWidth();
    result = result * PRIME + this.getBoundingShapeExtentsHeight();
    result = result * PRIME + this.getClipShapeExtentsX();
    result = result * PRIME + this.getClipShapeExtentsY();
    result = result * PRIME + this.getClipShapeExtentsWidth();
    result = result * PRIME + this.getClipShapeExtentsHeight();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryExtentsReply(sequenceNumber=" + this.getSequenceNumber() + ", boundingShaped=" + this.isBoundingShaped() + ", clipShaped=" + this.isClipShaped() + ", boundingShapeExtentsX=" + this.getBoundingShapeExtentsX() + ", boundingShapeExtentsY=" + this.getBoundingShapeExtentsY() + ", boundingShapeExtentsWidth=" + this.getBoundingShapeExtentsWidth() + ", boundingShapeExtentsHeight=" + this.getBoundingShapeExtentsHeight() + ", clipShapeExtentsX=" + this.getClipShapeExtentsX() + ", clipShapeExtentsY=" + this.getClipShapeExtentsY() + ", clipShapeExtentsWidth=" + this.getClipShapeExtentsWidth() + ", clipShapeExtentsHeight=" + this.getClipShapeExtentsHeight() + ")";
  }
}
