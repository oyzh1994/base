package com.github.moaxcp.x11.protocol.xc_misc;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class GetXIDListReply implements XReply {
  public static final String PLUGIN_NAME = "xc_misc";
  private final short sequenceNumber;
  private final int[] ids;

  public static GetXIDListReply readGetXIDListReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    GetXIDListReply.GetXIDListReplyBuilder javaBuilder = GetXIDListReply.builder();
    int length = in.readCard32();
    int idsLen = in.readCard32();
    byte[] pad5 = in.readPad(20);
    int[] ids = in.readCard32((int) (Integer.toUnsignedLong(idsLen)));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.ids(ids);
    if (javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    int idsLen = ids.length;
    out.writeCard32(idsLen);
    out.writePad(20);
    out.writeCard32(ids);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 4 * ids.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetXIDListReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int[] ids;

    public int getSize() {
      return 32 + 4 * ids.length;
    }

    @java.lang.SuppressWarnings("all")
    GetXIDListReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetXIDListReply.GetXIDListReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetXIDListReply.GetXIDListReplyBuilder ids(final int[] ids) {
      if (ids == null) {
        throw new java.lang.NullPointerException("ids is marked non-null but is null");
      }
      this.ids = ids;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetXIDListReply build() {
      return new GetXIDListReply(this.sequenceNumber, this.ids);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetXIDListReply.GetXIDListReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", ids=" + java.util.Arrays.toString(this.ids) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetXIDListReply(final short sequenceNumber, final int[] ids) {
    if (ids == null) {
      throw new java.lang.NullPointerException("ids is marked non-null but is null");
    }
    this.sequenceNumber = sequenceNumber;
    this.ids = ids;
  }

  @java.lang.SuppressWarnings("all")
  public static GetXIDListReply.GetXIDListReplyBuilder builder() {
    return new GetXIDListReply.GetXIDListReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int[] getIds() {
    return this.ids;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetXIDListReply)) return false;
    final GetXIDListReply other = (GetXIDListReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (!java.util.Arrays.equals(this.getIds(), other.getIds())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + java.util.Arrays.hashCode(this.getIds());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetXIDListReply(sequenceNumber=" + this.getSequenceNumber() + ", ids=" + java.util.Arrays.toString(this.getIds()) + ")";
  }
}
