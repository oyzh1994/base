package com.github.moaxcp.x11.protocol.xc_misc;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class GetXIDRangeReply implements XReply {
  public static final String PLUGIN_NAME = "xc_misc";
  private final short sequenceNumber;
  private final int startId;
  private final int count;

  public static GetXIDRangeReply readGetXIDRangeReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    GetXIDRangeReply.GetXIDRangeReplyBuilder javaBuilder = GetXIDRangeReply.builder();
    int length = in.readCard32();
    int startId = in.readCard32();
    int count = in.readCard32();
    in.readPad(16);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.startId(startId);
    javaBuilder.count(count);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(startId);
    out.writeCard32(count);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetXIDRangeReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int startId;
    @java.lang.SuppressWarnings("all")
    private int count;

    public int getSize() {
      return 16;
    }

    @java.lang.SuppressWarnings("all")
    GetXIDRangeReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetXIDRangeReply.GetXIDRangeReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetXIDRangeReply.GetXIDRangeReplyBuilder startId(final int startId) {
      this.startId = startId;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetXIDRangeReply.GetXIDRangeReplyBuilder count(final int count) {
      this.count = count;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetXIDRangeReply build() {
      return new GetXIDRangeReply(this.sequenceNumber, this.startId, this.count);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetXIDRangeReply.GetXIDRangeReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", startId=" + this.startId + ", count=" + this.count + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetXIDRangeReply(final short sequenceNumber, final int startId, final int count) {
    this.sequenceNumber = sequenceNumber;
    this.startId = startId;
    this.count = count;
  }

  @java.lang.SuppressWarnings("all")
  public static GetXIDRangeReply.GetXIDRangeReplyBuilder builder() {
    return new GetXIDRangeReply.GetXIDRangeReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getStartId() {
    return this.startId;
  }

  @java.lang.SuppressWarnings("all")
  public int getCount() {
    return this.count;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetXIDRangeReply)) return false;
    final GetXIDRangeReply other = (GetXIDRangeReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getStartId() != other.getStartId()) return false;
    if (this.getCount() != other.getCount()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getStartId();
    result = result * PRIME + this.getCount();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetXIDRangeReply(sequenceNumber=" + this.getSequenceNumber() + ", startId=" + this.getStartId() + ", count=" + this.getCount() + ")";
  }
}
