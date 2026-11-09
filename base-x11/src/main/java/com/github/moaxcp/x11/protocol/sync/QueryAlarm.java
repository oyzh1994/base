package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class QueryAlarm implements TwoWayRequest<QueryAlarmReply> {
  public static final String PLUGIN_NAME = "sync";
  public static final byte OPCODE = 10;
  private final int alarm;

  public XReplyFunction<QueryAlarmReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> QueryAlarmReply.readQueryAlarmReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static QueryAlarm readQueryAlarm(X11Input in) throws IOException {
    QueryAlarm.QueryAlarmBuilder javaBuilder = QueryAlarm.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int alarm = in.readCard32();
    javaBuilder.alarm(alarm);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(alarm);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryAlarmBuilder {
    @java.lang.SuppressWarnings("all")
    private int alarm;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    QueryAlarmBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryAlarm.QueryAlarmBuilder alarm(final int alarm) {
      this.alarm = alarm;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryAlarm build() {
      return new QueryAlarm(this.alarm);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryAlarm.QueryAlarmBuilder(alarm=" + this.alarm + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryAlarm(final int alarm) {
    this.alarm = alarm;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryAlarm.QueryAlarmBuilder builder() {
    return new QueryAlarm.QueryAlarmBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getAlarm() {
    return this.alarm;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryAlarm)) return false;
    final QueryAlarm other = (QueryAlarm) o;
    if (this.getAlarm() != other.getAlarm()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getAlarm();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryAlarm(alarm=" + this.getAlarm() + ")";
  }
}
