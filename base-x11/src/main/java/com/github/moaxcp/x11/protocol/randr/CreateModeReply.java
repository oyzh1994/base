package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class CreateModeReply implements XReply {
  public static final String PLUGIN_NAME = "randr";
  private final short sequenceNumber;
  private final int mode;

  public static CreateModeReply readCreateModeReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    CreateModeReply.CreateModeReplyBuilder javaBuilder = CreateModeReply.builder();
    int length = in.readCard32();
    int mode = in.readCard32();
    byte[] pad5 = in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.mode(mode);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(mode);
    out.writePad(20);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CreateModeReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int mode;

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    CreateModeReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateModeReply.CreateModeReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateModeReply.CreateModeReplyBuilder mode(final int mode) {
      this.mode = mode;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CreateModeReply build() {
      return new CreateModeReply(this.sequenceNumber, this.mode);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CreateModeReply.CreateModeReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", mode=" + this.mode + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CreateModeReply(final short sequenceNumber, final int mode) {
    this.sequenceNumber = sequenceNumber;
    this.mode = mode;
  }

  @java.lang.SuppressWarnings("all")
  public static CreateModeReply.CreateModeReplyBuilder builder() {
    return new CreateModeReply.CreateModeReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getMode() {
    return this.mode;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CreateModeReply)) return false;
    final CreateModeReply other = (CreateModeReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getMode() != other.getMode()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getMode();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CreateModeReply(sequenceNumber=" + this.getSequenceNumber() + ", mode=" + this.getMode() + ")";
  }
}
