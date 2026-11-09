package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class GetCrtcGammaSizeReply implements XReply {
  public static final String PLUGIN_NAME = "randr";
  private final short sequenceNumber;
  private final short size;

  public static GetCrtcGammaSizeReply readGetCrtcGammaSizeReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    GetCrtcGammaSizeReply.GetCrtcGammaSizeReplyBuilder javaBuilder = GetCrtcGammaSizeReply.builder();
    int length = in.readCard32();
    short size = in.readCard16();
    byte[] pad5 = in.readPad(22);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.size(size);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard16(size);
    out.writePad(22);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetCrtcGammaSizeReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private short size;

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    GetCrtcGammaSizeReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCrtcGammaSizeReply.GetCrtcGammaSizeReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetCrtcGammaSizeReply.GetCrtcGammaSizeReplyBuilder size(final short size) {
      this.size = size;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetCrtcGammaSizeReply build() {
      return new GetCrtcGammaSizeReply(this.sequenceNumber, this.size);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetCrtcGammaSizeReply.GetCrtcGammaSizeReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", size=" + this.size + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetCrtcGammaSizeReply(final short sequenceNumber, final short size) {
    this.sequenceNumber = sequenceNumber;
    this.size = size;
  }

  @java.lang.SuppressWarnings("all")
  public static GetCrtcGammaSizeReply.GetCrtcGammaSizeReplyBuilder builder() {
    return new GetCrtcGammaSizeReply.GetCrtcGammaSizeReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetCrtcGammaSizeReply)) return false;
    final GetCrtcGammaSizeReply other = (GetCrtcGammaSizeReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getSize() != other.getSize()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getSize();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetCrtcGammaSizeReply(sequenceNumber=" + this.getSequenceNumber() + ", size=" + this.getSize() + ")";
  }
}
