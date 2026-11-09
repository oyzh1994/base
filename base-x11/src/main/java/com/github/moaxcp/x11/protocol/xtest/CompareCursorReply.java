package com.github.moaxcp.x11.protocol.xtest;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class CompareCursorReply implements XReply {
  public static final String PLUGIN_NAME = "xtest";
  private final boolean same;
  private final short sequenceNumber;

  public static CompareCursorReply readCompareCursorReply(byte same, short sequenceNumber, X11Input in) throws IOException {
    CompareCursorReply.CompareCursorReplyBuilder javaBuilder = CompareCursorReply.builder();
    int length = in.readCard32();
    in.readPad(24);
    javaBuilder.same(same > 0);
    javaBuilder.sequenceNumber(sequenceNumber);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeBool(same);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CompareCursorReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private boolean same;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    CompareCursorReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CompareCursorReply.CompareCursorReplyBuilder same(final boolean same) {
      this.same = same;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CompareCursorReply.CompareCursorReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CompareCursorReply build() {
      return new CompareCursorReply(this.same, this.sequenceNumber);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CompareCursorReply.CompareCursorReplyBuilder(same=" + this.same + ", sequenceNumber=" + this.sequenceNumber + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CompareCursorReply(final boolean same, final short sequenceNumber) {
    this.same = same;
    this.sequenceNumber = sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public static CompareCursorReply.CompareCursorReplyBuilder builder() {
    return new CompareCursorReply.CompareCursorReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public boolean isSame() {
    return this.same;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CompareCursorReply)) return false;
    final CompareCursorReply other = (CompareCursorReply) o;
    if (this.isSame() != other.isSame()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + (this.isSame() ? 79 : 97);
    result = result * PRIME + this.getSequenceNumber();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CompareCursorReply(same=" + this.isSame() + ", sequenceNumber=" + this.getSequenceNumber() + ")";
  }
}
