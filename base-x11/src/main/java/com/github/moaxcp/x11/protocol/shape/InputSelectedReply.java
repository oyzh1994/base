package com.github.moaxcp.x11.protocol.shape;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class InputSelectedReply implements XReply {
  public static final String PLUGIN_NAME = "shape";
  private final boolean enabled;
  private final short sequenceNumber;

  public static InputSelectedReply readInputSelectedReply(byte enabled, short sequenceNumber, X11Input in) throws IOException {
    InputSelectedReply.InputSelectedReplyBuilder javaBuilder = InputSelectedReply.builder();
    int length = in.readCard32();
    in.readPad(24);
    javaBuilder.enabled(enabled > 0);
    javaBuilder.sequenceNumber(sequenceNumber);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeBool(enabled);
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


  public static class InputSelectedReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private boolean enabled;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    InputSelectedReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public InputSelectedReply.InputSelectedReplyBuilder enabled(final boolean enabled) {
      this.enabled = enabled;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public InputSelectedReply.InputSelectedReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public InputSelectedReply build() {
      return new InputSelectedReply(this.enabled, this.sequenceNumber);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "InputSelectedReply.InputSelectedReplyBuilder(enabled=" + this.enabled + ", sequenceNumber=" + this.sequenceNumber + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  InputSelectedReply(final boolean enabled, final short sequenceNumber) {
    this.enabled = enabled;
    this.sequenceNumber = sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public static InputSelectedReply.InputSelectedReplyBuilder builder() {
    return new InputSelectedReply.InputSelectedReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public boolean isEnabled() {
    return this.enabled;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof InputSelectedReply)) return false;
    final InputSelectedReply other = (InputSelectedReply) o;
    if (this.isEnabled() != other.isEnabled()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + (this.isEnabled() ? 79 : 97);
    result = result * PRIME + this.getSequenceNumber();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "InputSelectedReply(enabled=" + this.isEnabled() + ", sequenceNumber=" + this.getSequenceNumber() + ")";
  }
}
