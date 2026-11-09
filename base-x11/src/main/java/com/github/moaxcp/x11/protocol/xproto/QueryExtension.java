package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class QueryExtension implements TwoWayRequest<QueryExtensionReply> {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 98;
  private final byte[] name;

  public XReplyFunction<QueryExtensionReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryExtensionReply.readQueryExtensionReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryExtension readQueryExtension(X11Input in) throws IOException {
    QueryExtension.QueryExtensionBuilder javaBuilder = QueryExtension.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    short nameLen = in.readCard16();
    byte[] pad4 = in.readPad(2);
    byte[] name = in.readChar(Short.toUnsignedInt(nameLen));
    javaBuilder.name(name);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    short nameLen = (short) name.length;
    out.writeCard16(nameLen);
    out.writePad(2);
    out.writeChar(name);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 8 + 1 * name.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryExtensionBuilder {
    @java.lang.SuppressWarnings("all")
    private byte[] name;

    public int getSize() {
      return 8 + 1 * name.length;
    }

    @java.lang.SuppressWarnings("all")
    QueryExtensionBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryExtension.QueryExtensionBuilder name(final byte[] name) {
      if (name == null) {
        throw new java.lang.NullPointerException("name is marked non-null but is null");
      }
      this.name = name;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryExtension build() {
      return new QueryExtension(this.name);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryExtension.QueryExtensionBuilder(name=" + java.util.Arrays.toString(this.name) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryExtension(final byte[] name) {
    if (name == null) {
      throw new java.lang.NullPointerException("name is marked non-null but is null");
    }
    this.name = name;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryExtension.QueryExtensionBuilder builder() {
    return new QueryExtension.QueryExtensionBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getName() {
    return this.name;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryExtension)) return false;
    final QueryExtension other = (QueryExtension) o;
    if (!java.util.Arrays.equals(this.getName(), other.getName())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + java.util.Arrays.hashCode(this.getName());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryExtension(name=" + java.util.Arrays.toString(this.getName()) + ")";
  }
}
