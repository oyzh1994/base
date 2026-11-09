package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class GetPriorityReply implements XReply {
  public static final String PLUGIN_NAME = "sync";
  private final short sequenceNumber;
  private final int priority;

  public static GetPriorityReply readGetPriorityReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    GetPriorityReply.GetPriorityReplyBuilder javaBuilder = GetPriorityReply.builder();
    int length = in.readCard32();
    int priority = in.readInt32();
    in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.priority(priority);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeInt32(priority);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetPriorityReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int priority;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    GetPriorityReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetPriorityReply.GetPriorityReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetPriorityReply.GetPriorityReplyBuilder priority(final int priority) {
      this.priority = priority;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetPriorityReply build() {
      return new GetPriorityReply(this.sequenceNumber, this.priority);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetPriorityReply.GetPriorityReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", priority=" + this.priority + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetPriorityReply(final short sequenceNumber, final int priority) {
    this.sequenceNumber = sequenceNumber;
    this.priority = priority;
  }

  @java.lang.SuppressWarnings("all")
  public static GetPriorityReply.GetPriorityReplyBuilder builder() {
    return new GetPriorityReply.GetPriorityReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getPriority() {
    return this.priority;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetPriorityReply)) return false;
    final GetPriorityReply other = (GetPriorityReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getPriority() != other.getPriority()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getPriority();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetPriorityReply(sequenceNumber=" + this.getSequenceNumber() + ", priority=" + this.getPriority() + ")";
  }
}
