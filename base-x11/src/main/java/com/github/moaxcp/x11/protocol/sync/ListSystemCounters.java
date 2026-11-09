package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class ListSystemCounters implements TwoWayRequest<ListSystemCountersReply> {
  public static final String PLUGIN_NAME = "sync";
  public static final byte OPCODE = 1;

  public XReplyFunction<ListSystemCountersReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> ListSystemCountersReply.readListSystemCountersReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static ListSystemCounters readListSystemCounters(X11Input in) throws IOException {
    ListSystemCounters.ListSystemCountersBuilder javaBuilder = ListSystemCounters.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
  }

  @Override
  public int getSize() {
    return 4;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ListSystemCountersBuilder {
    public int getSize() {
      return 4;
    }

    @java.lang.SuppressWarnings("all")
    ListSystemCountersBuilder() {
    }

    @java.lang.SuppressWarnings("all")
    public ListSystemCounters build() {
      return new ListSystemCounters();
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ListSystemCounters.ListSystemCountersBuilder()";
    }
  }

  @java.lang.SuppressWarnings("all")
  ListSystemCounters() {
  }

  @java.lang.SuppressWarnings("all")
  public static ListSystemCounters.ListSystemCountersBuilder builder() {
    return new ListSystemCounters.ListSystemCountersBuilder();
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ListSystemCounters)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int result = 1;
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ListSystemCounters()";
  }
}
