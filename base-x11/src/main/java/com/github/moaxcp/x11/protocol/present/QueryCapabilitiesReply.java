package com.github.moaxcp.x11.protocol.present;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class QueryCapabilitiesReply implements XReply {
  public static final String PLUGIN_NAME = "present";
  private final short sequenceNumber;
  private final int capabilities;

  public static QueryCapabilitiesReply readQueryCapabilitiesReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    QueryCapabilitiesReply.QueryCapabilitiesReplyBuilder javaBuilder = QueryCapabilitiesReply.builder();
    int length = in.readCard32();
    int capabilities = in.readCard32();
    in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.capabilities(capabilities);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(capabilities);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryCapabilitiesReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int capabilities;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    QueryCapabilitiesReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryCapabilitiesReply.QueryCapabilitiesReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryCapabilitiesReply.QueryCapabilitiesReplyBuilder capabilities(final int capabilities) {
      this.capabilities = capabilities;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryCapabilitiesReply build() {
      return new QueryCapabilitiesReply(this.sequenceNumber, this.capabilities);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryCapabilitiesReply.QueryCapabilitiesReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", capabilities=" + this.capabilities + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryCapabilitiesReply(final short sequenceNumber, final int capabilities) {
    this.sequenceNumber = sequenceNumber;
    this.capabilities = capabilities;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryCapabilitiesReply.QueryCapabilitiesReplyBuilder builder() {
    return new QueryCapabilitiesReply.QueryCapabilitiesReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getCapabilities() {
    return this.capabilities;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryCapabilitiesReply)) return false;
    final QueryCapabilitiesReply other = (QueryCapabilitiesReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getCapabilities() != other.getCapabilities()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getCapabilities();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryCapabilitiesReply(sequenceNumber=" + this.getSequenceNumber() + ", capabilities=" + this.getCapabilities() + ")";
  }
}
