package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class SetDebuggingFlagsReply implements XReply {
  public static final String PLUGIN_NAME = "xkb";
  private final short sequenceNumber;
  private final int currentFlags;
  private final int currentCtrls;
  private final int supportedFlags;
  private final int supportedCtrls;

  public static SetDebuggingFlagsReply readSetDebuggingFlagsReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    SetDebuggingFlagsReply.SetDebuggingFlagsReplyBuilder javaBuilder = SetDebuggingFlagsReply.builder();
    int length = in.readCard32();
    int currentFlags = in.readCard32();
    int currentCtrls = in.readCard32();
    int supportedFlags = in.readCard32();
    int supportedCtrls = in.readCard32();
    byte[] pad8 = in.readPad(8);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.currentFlags(currentFlags);
    javaBuilder.currentCtrls(currentCtrls);
    javaBuilder.supportedFlags(supportedFlags);
    javaBuilder.supportedCtrls(supportedCtrls);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(currentFlags);
    out.writeCard32(currentCtrls);
    out.writeCard32(supportedFlags);
    out.writeCard32(supportedCtrls);
    out.writePad(8);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetDebuggingFlagsReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int currentFlags;
    @java.lang.SuppressWarnings("all")
    private int currentCtrls;
    @java.lang.SuppressWarnings("all")
    private int supportedFlags;
    @java.lang.SuppressWarnings("all")
    private int supportedCtrls;

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    SetDebuggingFlagsReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetDebuggingFlagsReply.SetDebuggingFlagsReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetDebuggingFlagsReply.SetDebuggingFlagsReplyBuilder currentFlags(final int currentFlags) {
      this.currentFlags = currentFlags;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetDebuggingFlagsReply.SetDebuggingFlagsReplyBuilder currentCtrls(final int currentCtrls) {
      this.currentCtrls = currentCtrls;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetDebuggingFlagsReply.SetDebuggingFlagsReplyBuilder supportedFlags(final int supportedFlags) {
      this.supportedFlags = supportedFlags;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetDebuggingFlagsReply.SetDebuggingFlagsReplyBuilder supportedCtrls(final int supportedCtrls) {
      this.supportedCtrls = supportedCtrls;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetDebuggingFlagsReply build() {
      return new SetDebuggingFlagsReply(this.sequenceNumber, this.currentFlags, this.currentCtrls, this.supportedFlags, this.supportedCtrls);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetDebuggingFlagsReply.SetDebuggingFlagsReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", currentFlags=" + this.currentFlags + ", currentCtrls=" + this.currentCtrls + ", supportedFlags=" + this.supportedFlags + ", supportedCtrls=" + this.supportedCtrls + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetDebuggingFlagsReply(final short sequenceNumber, final int currentFlags, final int currentCtrls, final int supportedFlags, final int supportedCtrls) {
    this.sequenceNumber = sequenceNumber;
    this.currentFlags = currentFlags;
    this.currentCtrls = currentCtrls;
    this.supportedFlags = supportedFlags;
    this.supportedCtrls = supportedCtrls;
  }

  @java.lang.SuppressWarnings("all")
  public static SetDebuggingFlagsReply.SetDebuggingFlagsReplyBuilder builder() {
    return new SetDebuggingFlagsReply.SetDebuggingFlagsReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getCurrentFlags() {
    return this.currentFlags;
  }

  @java.lang.SuppressWarnings("all")
  public int getCurrentCtrls() {
    return this.currentCtrls;
  }

  @java.lang.SuppressWarnings("all")
  public int getSupportedFlags() {
    return this.supportedFlags;
  }

  @java.lang.SuppressWarnings("all")
  public int getSupportedCtrls() {
    return this.supportedCtrls;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetDebuggingFlagsReply)) return false;
    final SetDebuggingFlagsReply other = (SetDebuggingFlagsReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getCurrentFlags() != other.getCurrentFlags()) return false;
    if (this.getCurrentCtrls() != other.getCurrentCtrls()) return false;
    if (this.getSupportedFlags() != other.getSupportedFlags()) return false;
    if (this.getSupportedCtrls() != other.getSupportedCtrls()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getCurrentFlags();
    result = result * PRIME + this.getCurrentCtrls();
    result = result * PRIME + this.getSupportedFlags();
    result = result * PRIME + this.getSupportedCtrls();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetDebuggingFlagsReply(sequenceNumber=" + this.getSequenceNumber() + ", currentFlags=" + this.getCurrentFlags() + ", currentCtrls=" + this.getCurrentCtrls() + ", supportedFlags=" + this.getSupportedFlags() + ", supportedCtrls=" + this.getSupportedCtrls() + ")";
  }
}
