package com.github.moaxcp.x11.protocol.bigreq;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class EnableReply implements XReply {
  public static final String PLUGIN_NAME = "bigreq";
  private final short sequenceNumber;
  private final int maximumRequestLength;

  public static EnableReply readEnableReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    EnableReply.EnableReplyBuilder javaBuilder = EnableReply.builder();
    int length = in.readCard32();
    int maximumRequestLength = in.readCard32();
    in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.maximumRequestLength(maximumRequestLength);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(maximumRequestLength);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class EnableReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int maximumRequestLength;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    EnableReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public EnableReply.EnableReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public EnableReply.EnableReplyBuilder maximumRequestLength(final int maximumRequestLength) {
      this.maximumRequestLength = maximumRequestLength;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public EnableReply build() {
      return new EnableReply(this.sequenceNumber, this.maximumRequestLength);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "EnableReply.EnableReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", maximumRequestLength=" + this.maximumRequestLength + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  EnableReply(final short sequenceNumber, final int maximumRequestLength) {
    this.sequenceNumber = sequenceNumber;
    this.maximumRequestLength = maximumRequestLength;
  }

  @java.lang.SuppressWarnings("all")
  public static EnableReply.EnableReplyBuilder builder() {
    return new EnableReply.EnableReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getMaximumRequestLength() {
    return this.maximumRequestLength;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof EnableReply)) return false;
    final EnableReply other = (EnableReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getMaximumRequestLength() != other.getMaximumRequestLength()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getMaximumRequestLength();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "EnableReply(sequenceNumber=" + this.getSequenceNumber() + ", maximumRequestLength=" + this.getMaximumRequestLength() + ")";
  }
}
