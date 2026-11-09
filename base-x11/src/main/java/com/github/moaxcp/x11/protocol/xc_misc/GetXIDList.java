package com.github.moaxcp.x11.protocol.xc_misc;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GetXIDList implements TwoWayRequest<GetXIDListReply> {
  public static final String PLUGIN_NAME = "xc_misc";
  public static final byte OPCODE = 2;
  private final int count;

  public XReplyFunction<GetXIDListReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetXIDListReply.readGetXIDListReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetXIDList readGetXIDList(X11Input in) throws IOException {
    GetXIDList.GetXIDListBuilder javaBuilder = GetXIDList.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int count = in.readCard32();
    javaBuilder.count(count);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(count);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetXIDListBuilder {
    @java.lang.SuppressWarnings("all")
    private int count;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    GetXIDListBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetXIDList.GetXIDListBuilder count(final int count) {
      this.count = count;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetXIDList build() {
      return new GetXIDList(this.count);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetXIDList.GetXIDListBuilder(count=" + this.count + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetXIDList(final int count) {
    this.count = count;
  }

  @java.lang.SuppressWarnings("all")
  public static GetXIDList.GetXIDListBuilder builder() {
    return new GetXIDList.GetXIDListBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getCount() {
    return this.count;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetXIDList)) return false;
    final GetXIDList other = (GetXIDList) o;
    if (this.getCount() != other.getCount()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getCount();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetXIDList(count=" + this.getCount() + ")";
  }
}
