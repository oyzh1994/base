package com.github.moaxcp.x11.protocol.sync;

import java.util.ArrayList;
import java.util.List;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class ListSystemCountersReply implements XReply {
  public static final String PLUGIN_NAME = "sync";
  private final short sequenceNumber;
  private final List<Systemcounter> counters;

  public static ListSystemCountersReply readListSystemCountersReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    ListSystemCountersReply.ListSystemCountersReplyBuilder javaBuilder = ListSystemCountersReply.builder();
    int length = in.readCard32();
    int countersLen = in.readCard32();
    byte[] pad5 = in.readPad(20);
    List<Systemcounter> counters = new ArrayList<>((int) (Integer.toUnsignedLong(countersLen)));
    for (int i = 0; i < Integer.toUnsignedLong(countersLen); i++) {
      counters.add(Systemcounter.readSystemcounter(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.counters(List.copyOf(counters));
    if (javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    int countersLen = counters.size();
    out.writeCard32(countersLen);
    out.writePad(20);
    for (Systemcounter t : counters) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(counters);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ListSystemCountersReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private List<Systemcounter> counters;

    public int getSize() {
      return 32 + XObject.sizeOf(counters);
    }

    @java.lang.SuppressWarnings("all")
    ListSystemCountersReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListSystemCountersReply.ListSystemCountersReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListSystemCountersReply.ListSystemCountersReplyBuilder counters(final List<Systemcounter> counters) {
      if (counters == null) {
        throw new java.lang.NullPointerException("counters is marked non-null but is null");
      }
      this.counters = counters;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ListSystemCountersReply build() {
      return new ListSystemCountersReply(this.sequenceNumber, this.counters);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ListSystemCountersReply.ListSystemCountersReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", counters=" + this.counters + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ListSystemCountersReply(final short sequenceNumber, final List<Systemcounter> counters) {
    if (counters == null) {
      throw new java.lang.NullPointerException("counters is marked non-null but is null");
    }
    this.sequenceNumber = sequenceNumber;
    this.counters = counters;
  }

  @java.lang.SuppressWarnings("all")
  public static ListSystemCountersReply.ListSystemCountersReplyBuilder builder() {
    return new ListSystemCountersReply.ListSystemCountersReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public List<Systemcounter> getCounters() {
    return this.counters;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ListSystemCountersReply)) return false;
    final ListSystemCountersReply other = (ListSystemCountersReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    final java.lang.Object this$counters = this.getCounters();
    final java.lang.Object other$counters = other.getCounters();
    if (this$counters == null ? other$counters != null : !this$counters.equals(other$counters)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    final java.lang.Object $counters = this.getCounters();
    result = result * PRIME + ($counters == null ? 43 : $counters.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ListSystemCountersReply(sequenceNumber=" + this.getSequenceNumber() + ", counters=" + this.getCounters() + ")";
  }
}
