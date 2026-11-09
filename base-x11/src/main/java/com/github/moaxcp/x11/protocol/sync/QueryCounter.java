package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class QueryCounter implements TwoWayRequest<QueryCounterReply> {
  public static final String PLUGIN_NAME = "sync";
  public static final byte OPCODE = 5;
  private final int counter;

  public XReplyFunction<QueryCounterReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryCounterReply.readQueryCounterReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryCounter readQueryCounter(X11Input in) throws IOException {
    QueryCounter.QueryCounterBuilder javaBuilder = QueryCounter.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int counter = in.readCard32();
    javaBuilder.counter(counter);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(counter);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryCounterBuilder {
    @java.lang.SuppressWarnings("all")
    private int counter;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    QueryCounterBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryCounter.QueryCounterBuilder counter(final int counter) {
      this.counter = counter;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryCounter build() {
      return new QueryCounter(this.counter);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryCounter.QueryCounterBuilder(counter=" + this.counter + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryCounter(final int counter) {
    this.counter = counter;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryCounter.QueryCounterBuilder builder() {
    return new QueryCounter.QueryCounterBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getCounter() {
    return this.counter;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryCounter)) return false;
    final QueryCounter other = (QueryCounter) o;
    if (this.getCounter() != other.getCounter()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getCounter();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryCounter(counter=" + this.getCounter() + ")";
  }
}
