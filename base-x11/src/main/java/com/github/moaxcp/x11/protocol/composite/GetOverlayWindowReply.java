package com.github.moaxcp.x11.protocol.composite;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class GetOverlayWindowReply implements XReply {
  public static final String PLUGIN_NAME = "composite";
  private final short sequenceNumber;
  private final int overlayWin;

  public static GetOverlayWindowReply readGetOverlayWindowReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    GetOverlayWindowReply.GetOverlayWindowReplyBuilder javaBuilder = GetOverlayWindowReply.builder();
    int length = in.readCard32();
    int overlayWin = in.readCard32();
    byte[] pad5 = in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.overlayWin(overlayWin);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(overlayWin);
    out.writePad(20);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetOverlayWindowReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int overlayWin;

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    GetOverlayWindowReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetOverlayWindowReply.GetOverlayWindowReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetOverlayWindowReply.GetOverlayWindowReplyBuilder overlayWin(final int overlayWin) {
      this.overlayWin = overlayWin;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetOverlayWindowReply build() {
      return new GetOverlayWindowReply(this.sequenceNumber, this.overlayWin);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetOverlayWindowReply.GetOverlayWindowReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", overlayWin=" + this.overlayWin + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetOverlayWindowReply(final short sequenceNumber, final int overlayWin) {
    this.sequenceNumber = sequenceNumber;
    this.overlayWin = overlayWin;
  }

  @java.lang.SuppressWarnings("all")
  public static GetOverlayWindowReply.GetOverlayWindowReplyBuilder builder() {
    return new GetOverlayWindowReply.GetOverlayWindowReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getOverlayWin() {
    return this.overlayWin;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetOverlayWindowReply)) return false;
    final GetOverlayWindowReply other = (GetOverlayWindowReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getOverlayWin() != other.getOverlayWin()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getOverlayWin();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetOverlayWindowReply(sequenceNumber=" + this.getSequenceNumber() + ", overlayWin=" + this.getOverlayWin() + ")";
  }
}
