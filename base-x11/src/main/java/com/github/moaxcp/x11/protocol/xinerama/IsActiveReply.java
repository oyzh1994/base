package com.github.moaxcp.x11.protocol.xinerama;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class IsActiveReply implements XReply {
  public static final String PLUGIN_NAME = "xinerama";
  private final short sequenceNumber;
  private final int state;

  public static IsActiveReply readIsActiveReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    IsActiveReply.IsActiveReplyBuilder javaBuilder = IsActiveReply.builder();
    int length = in.readCard32();
    int state = in.readCard32();
    in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.state(state);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(state);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class IsActiveReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int state;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    IsActiveReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public IsActiveReply.IsActiveReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public IsActiveReply.IsActiveReplyBuilder state(final int state) {
      this.state = state;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public IsActiveReply build() {
      return new IsActiveReply(this.sequenceNumber, this.state);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "IsActiveReply.IsActiveReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", state=" + this.state + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  IsActiveReply(final short sequenceNumber, final int state) {
    this.sequenceNumber = sequenceNumber;
    this.state = state;
  }

  @java.lang.SuppressWarnings("all")
  public static IsActiveReply.IsActiveReplyBuilder builder() {
    return new IsActiveReply.IsActiveReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getState() {
    return this.state;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof IsActiveReply)) return false;
    final IsActiveReply other = (IsActiveReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getState() != other.getState()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getState();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "IsActiveReply(sequenceNumber=" + this.getSequenceNumber() + ", state=" + this.getState() + ")";
  }
}
