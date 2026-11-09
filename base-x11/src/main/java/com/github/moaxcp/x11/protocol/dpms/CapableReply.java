package com.github.moaxcp.x11.protocol.dpms;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class CapableReply implements XReply {
  public static final String PLUGIN_NAME = "dpms";
  private final short sequenceNumber;
  private final boolean capable;

  public static CapableReply readCapableReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    CapableReply.CapableReplyBuilder javaBuilder = CapableReply.builder();
    int length = in.readCard32();
    boolean capable = in.readBool();
    byte[] pad5 = in.readPad(23);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.capable(capable);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeBool(capable);
    out.writePad(23);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CapableReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private boolean capable;

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    CapableReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CapableReply.CapableReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CapableReply.CapableReplyBuilder capable(final boolean capable) {
      this.capable = capable;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CapableReply build() {
      return new CapableReply(this.sequenceNumber, this.capable);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CapableReply.CapableReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", capable=" + this.capable + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CapableReply(final short sequenceNumber, final boolean capable) {
    this.sequenceNumber = sequenceNumber;
    this.capable = capable;
  }

  @java.lang.SuppressWarnings("all")
  public static CapableReply.CapableReplyBuilder builder() {
    return new CapableReply.CapableReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isCapable() {
    return this.capable;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CapableReply)) return false;
    final CapableReply other = (CapableReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.isCapable() != other.isCapable()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + (this.isCapable() ? 79 : 97);
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CapableReply(sequenceNumber=" + this.getSequenceNumber() + ", capable=" + this.isCapable() + ")";
  }
}
