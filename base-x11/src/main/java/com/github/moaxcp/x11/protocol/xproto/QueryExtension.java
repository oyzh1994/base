package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.ByteList;

public final class QueryExtension implements TwoWayRequest<QueryExtensionReply> {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 98;
  private final ByteList name;

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
    ByteList name = in.readChar(Short.toUnsignedInt(nameLen));
    javaBuilder.name(name.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    short nameLen = (short) name.size();
    out.writeCard16(nameLen);
    out.writePad(2);
    out.writeChar(name);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 8 + 1 * name.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryExtensionBuilder {
    @java.lang.SuppressWarnings("all")
    private ByteList name;

    public int getSize() {
      return 8 + 1 * name.size();
    }

    @java.lang.SuppressWarnings("all")
    QueryExtensionBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryExtension.QueryExtensionBuilder name(final ByteList name) {
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
      return "QueryExtension.QueryExtensionBuilder(name=" + this.name + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryExtension(final ByteList name) {
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
  public ByteList getName() {
    return this.name;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryExtension)) return false;
    final QueryExtension other = (QueryExtension) o;
    final java.lang.Object this$name = this.getName();
    final java.lang.Object other$name = other.getName();
    if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final java.lang.Object $name = this.getName();
    result = result * PRIME + ($name == null ? 43 : $name.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryExtension(name=" + this.getName() + ")";
  }
}
