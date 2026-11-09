package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GetPriority implements TwoWayRequest<GetPriorityReply> {
  public static final String PLUGIN_NAME = "sync";
  public static final byte OPCODE = 13;
  private final int id;

  public XReplyFunction<GetPriorityReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetPriorityReply.readGetPriorityReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetPriority readGetPriority(X11Input in) throws IOException {
    GetPriority.GetPriorityBuilder javaBuilder = GetPriority.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int id = in.readCard32();
    javaBuilder.id(id);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(id);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetPriorityBuilder {
    @java.lang.SuppressWarnings("all")
    private int id;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    GetPriorityBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetPriority.GetPriorityBuilder id(final int id) {
      this.id = id;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetPriority build() {
      return new GetPriority(this.id);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetPriority.GetPriorityBuilder(id=" + this.id + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetPriority(final int id) {
    this.id = id;
  }

  @java.lang.SuppressWarnings("all")
  public static GetPriority.GetPriorityBuilder builder() {
    return new GetPriority.GetPriorityBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getId() {
    return this.id;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetPriority)) return false;
    final GetPriority other = (GetPriority) o;
    if (this.getId() != other.getId()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getId();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetPriority(id=" + this.getId() + ")";
  }
}
