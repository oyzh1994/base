package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class QueryOutputProperty implements TwoWayRequest<QueryOutputPropertyReply> {
  public static final String PLUGIN_NAME = "randr";
  public static final byte OPCODE = 11;
  private final int output;
  private final int property;

  public XReplyFunction<QueryOutputPropertyReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryOutputPropertyReply.readQueryOutputPropertyReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryOutputProperty readQueryOutputProperty(X11Input in) throws IOException {
    QueryOutputProperty.QueryOutputPropertyBuilder javaBuilder = QueryOutputProperty.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int output = in.readCard32();
    int property = in.readCard32();
    javaBuilder.output(output);
    javaBuilder.property(property);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(output);
    out.writeCard32(property);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryOutputPropertyBuilder {
    @java.lang.SuppressWarnings("all")
    private int output;
    @java.lang.SuppressWarnings("all")
    private int property;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    QueryOutputPropertyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryOutputProperty.QueryOutputPropertyBuilder output(final int output) {
      this.output = output;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryOutputProperty.QueryOutputPropertyBuilder property(final int property) {
      this.property = property;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryOutputProperty build() {
      return new QueryOutputProperty(this.output, this.property);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryOutputProperty.QueryOutputPropertyBuilder(output=" + this.output + ", property=" + this.property + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryOutputProperty(final int output, final int property) {
    this.output = output;
    this.property = property;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryOutputProperty.QueryOutputPropertyBuilder builder() {
    return new QueryOutputProperty.QueryOutputPropertyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getOutput() {
    return this.output;
  }

  @java.lang.SuppressWarnings("all")
  public int getProperty() {
    return this.property;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryOutputProperty)) return false;
    final QueryOutputProperty other = (QueryOutputProperty) o;
    if (this.getOutput() != other.getOutput()) return false;
    if (this.getProperty() != other.getProperty()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getOutput();
    result = result * PRIME + this.getProperty();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryOutputProperty(output=" + this.getOutput() + ", property=" + this.getProperty() + ")";
  }
}
