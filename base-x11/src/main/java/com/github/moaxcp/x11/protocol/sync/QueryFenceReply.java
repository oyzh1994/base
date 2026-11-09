package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class QueryFenceReply implements XReply {
  public static final String PLUGIN_NAME = "sync";
  private final short sequenceNumber;
  private final boolean triggered;

  public static QueryFenceReply readQueryFenceReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    QueryFenceReply.QueryFenceReplyBuilder javaBuilder = QueryFenceReply.builder();
    int length = in.readCard32();
    boolean triggered = in.readBool();
    byte[] pad5 = in.readPad(23);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.triggered(triggered);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeBool(triggered);
    out.writePad(23);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryFenceReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private boolean triggered;

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    QueryFenceReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryFenceReply.QueryFenceReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryFenceReply.QueryFenceReplyBuilder triggered(final boolean triggered) {
      this.triggered = triggered;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryFenceReply build() {
      return new QueryFenceReply(this.sequenceNumber, this.triggered);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryFenceReply.QueryFenceReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", triggered=" + this.triggered + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryFenceReply(final short sequenceNumber, final boolean triggered) {
    this.sequenceNumber = sequenceNumber;
    this.triggered = triggered;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryFenceReply.QueryFenceReplyBuilder builder() {
    return new QueryFenceReply.QueryFenceReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isTriggered() {
    return this.triggered;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryFenceReply)) return false;
    final QueryFenceReply other = (QueryFenceReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.isTriggered() != other.isTriggered()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + (this.isTriggered() ? 79 : 97);
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryFenceReply(sequenceNumber=" + this.getSequenceNumber() + ", triggered=" + this.isTriggered() + ")";
  }
}
