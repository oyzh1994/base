package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class ListHosts implements TwoWayRequest<ListHostsReply> {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 110;

  public XReplyFunction<ListHostsReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> ListHostsReply.readListHostsReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static ListHosts readListHosts(X11Input in) throws IOException {
    ListHosts.ListHostsBuilder javaBuilder = ListHosts.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
  }

  @Override
  public int getSize() {
    return 4;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ListHostsBuilder {
    public int getSize() {
      return 4;
    }

    @java.lang.SuppressWarnings("all")
    ListHostsBuilder() {
    }

    @java.lang.SuppressWarnings("all")
    public ListHosts build() {
      return new ListHosts();
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ListHosts.ListHostsBuilder()";
    }
  }

  @java.lang.SuppressWarnings("all")
  ListHosts() {
  }

  @java.lang.SuppressWarnings("all")
  public static ListHosts.ListHostsBuilder builder() {
    return new ListHosts.ListHostsBuilder();
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ListHosts)) return false;
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
    return "ListHosts()";
  }
}
