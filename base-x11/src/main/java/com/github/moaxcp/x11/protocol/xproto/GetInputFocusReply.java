package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class GetInputFocusReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";
  private final byte revertTo;
  private final short sequenceNumber;
  private final int focus;

  public static GetInputFocusReply readGetInputFocusReply(byte revertTo, short sequenceNumber, X11Input in) throws IOException {
    GetInputFocusReply.GetInputFocusReplyBuilder javaBuilder = GetInputFocusReply.builder();
    int length = in.readCard32();
    int focus = in.readCard32();
    in.readPad(20);
    javaBuilder.revertTo(revertTo);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.focus(focus);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(revertTo);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(focus);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetInputFocusReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte revertTo;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int focus;

    public GetInputFocusReply.GetInputFocusReplyBuilder revertTo(InputFocus revertTo) {
      this.revertTo = (byte) revertTo.getValue();
      return this;
    }

    public GetInputFocusReply.GetInputFocusReplyBuilder revertTo(byte revertTo) {
      this.revertTo = revertTo;
      return this;
    }

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    GetInputFocusReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetInputFocusReply.GetInputFocusReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetInputFocusReply.GetInputFocusReplyBuilder focus(final int focus) {
      this.focus = focus;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetInputFocusReply build() {
      return new GetInputFocusReply(this.revertTo, this.sequenceNumber, this.focus);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetInputFocusReply.GetInputFocusReplyBuilder(revertTo=" + this.revertTo + ", sequenceNumber=" + this.sequenceNumber + ", focus=" + this.focus + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetInputFocusReply(final byte revertTo, final short sequenceNumber, final int focus) {
    this.revertTo = revertTo;
    this.sequenceNumber = sequenceNumber;
    this.focus = focus;
  }

  @java.lang.SuppressWarnings("all")
  public static GetInputFocusReply.GetInputFocusReplyBuilder builder() {
    return new GetInputFocusReply.GetInputFocusReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getRevertTo() {
    return this.revertTo;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getFocus() {
    return this.focus;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetInputFocusReply)) return false;
    final GetInputFocusReply other = (GetInputFocusReply) o;
    if (this.getRevertTo() != other.getRevertTo()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getFocus() != other.getFocus()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getRevertTo();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getFocus();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetInputFocusReply(revertTo=" + this.getRevertTo() + ", sequenceNumber=" + this.getSequenceNumber() + ", focus=" + this.getFocus() + ")";
  }
}
