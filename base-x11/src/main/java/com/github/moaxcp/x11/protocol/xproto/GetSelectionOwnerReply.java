package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class GetSelectionOwnerReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";
  private final short sequenceNumber;
  private final int owner;

  public static GetSelectionOwnerReply readGetSelectionOwnerReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    GetSelectionOwnerReply.GetSelectionOwnerReplyBuilder javaBuilder = GetSelectionOwnerReply.builder();
    int length = in.readCard32();
    int owner = in.readCard32();
    in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.owner(owner);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(owner);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetSelectionOwnerReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int owner;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    GetSelectionOwnerReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetSelectionOwnerReply.GetSelectionOwnerReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetSelectionOwnerReply.GetSelectionOwnerReplyBuilder owner(final int owner) {
      this.owner = owner;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetSelectionOwnerReply build() {
      return new GetSelectionOwnerReply(this.sequenceNumber, this.owner);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetSelectionOwnerReply.GetSelectionOwnerReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", owner=" + this.owner + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetSelectionOwnerReply(final short sequenceNumber, final int owner) {
    this.sequenceNumber = sequenceNumber;
    this.owner = owner;
  }

  @java.lang.SuppressWarnings("all")
  public static GetSelectionOwnerReply.GetSelectionOwnerReplyBuilder builder() {
    return new GetSelectionOwnerReply.GetSelectionOwnerReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getOwner() {
    return this.owner;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetSelectionOwnerReply)) return false;
    final GetSelectionOwnerReply other = (GetSelectionOwnerReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getOwner() != other.getOwner()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getOwner();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetSelectionOwnerReply(sequenceNumber=" + this.getSequenceNumber() + ", owner=" + this.getOwner() + ")";
  }
}
