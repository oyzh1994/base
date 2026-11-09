package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class GetOutputPrimaryReply implements XReply {
  public static final String PLUGIN_NAME = "randr";
  private final short sequenceNumber;
  private final int output;

  public static GetOutputPrimaryReply readGetOutputPrimaryReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    GetOutputPrimaryReply.GetOutputPrimaryReplyBuilder javaBuilder = GetOutputPrimaryReply.builder();
    int length = in.readCard32();
    int output = in.readCard32();
    in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.output(output);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(output);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetOutputPrimaryReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int output;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    GetOutputPrimaryReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetOutputPrimaryReply.GetOutputPrimaryReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetOutputPrimaryReply.GetOutputPrimaryReplyBuilder output(final int output) {
      this.output = output;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetOutputPrimaryReply build() {
      return new GetOutputPrimaryReply(this.sequenceNumber, this.output);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetOutputPrimaryReply.GetOutputPrimaryReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", output=" + this.output + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetOutputPrimaryReply(final short sequenceNumber, final int output) {
    this.sequenceNumber = sequenceNumber;
    this.output = output;
  }

  @java.lang.SuppressWarnings("all")
  public static GetOutputPrimaryReply.GetOutputPrimaryReplyBuilder builder() {
    return new GetOutputPrimaryReply.GetOutputPrimaryReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getOutput() {
    return this.output;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetOutputPrimaryReply)) return false;
    final GetOutputPrimaryReply other = (GetOutputPrimaryReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getOutput() != other.getOutput()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getOutput();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetOutputPrimaryReply(sequenceNumber=" + this.getSequenceNumber() + ", output=" + this.getOutput() + ")";
  }
}
