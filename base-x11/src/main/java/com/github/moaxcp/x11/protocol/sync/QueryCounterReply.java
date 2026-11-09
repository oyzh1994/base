package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class QueryCounterReply implements XReply {
  public static final String PLUGIN_NAME = "sync";
  private final short sequenceNumber;
  private final Int64 counterValue;

  public static QueryCounterReply readQueryCounterReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    QueryCounterReply.QueryCounterReplyBuilder javaBuilder = QueryCounterReply.builder();
    int length = in.readCard32();
    Int64 counterValue = Int64.readInt64(in);
    in.readPad(16);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.counterValue(counterValue);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    counterValue.write(out);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryCounterReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private Int64 counterValue;

    public int getSize() {
      return 16;
    }

    @java.lang.SuppressWarnings("all")
    QueryCounterReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryCounterReply.QueryCounterReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryCounterReply.QueryCounterReplyBuilder counterValue(final Int64 counterValue) {
      if (counterValue == null) {
        throw new java.lang.NullPointerException("counterValue is marked non-null but is null");
      }
      this.counterValue = counterValue;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryCounterReply build() {
      return new QueryCounterReply(this.sequenceNumber, this.counterValue);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryCounterReply.QueryCounterReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", counterValue=" + this.counterValue + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryCounterReply(final short sequenceNumber, final Int64 counterValue) {
    if (counterValue == null) {
      throw new java.lang.NullPointerException("counterValue is marked non-null but is null");
    }
    this.sequenceNumber = sequenceNumber;
    this.counterValue = counterValue;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryCounterReply.QueryCounterReplyBuilder builder() {
    return new QueryCounterReply.QueryCounterReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public Int64 getCounterValue() {
    return this.counterValue;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryCounterReply)) return false;
    final QueryCounterReply other = (QueryCounterReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    final java.lang.Object this$counterValue = this.getCounterValue();
    final java.lang.Object other$counterValue = other.getCounterValue();
    if (this$counterValue == null ? other$counterValue != null : !this$counterValue.equals(other$counterValue)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    final java.lang.Object $counterValue = this.getCounterValue();
    result = result * PRIME + ($counterValue == null ? 43 : $counterValue.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryCounterReply(sequenceNumber=" + this.getSequenceNumber() + ", counterValue=" + this.getCounterValue() + ")";
  }
}
