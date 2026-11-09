package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class QueryAlarmReply implements XReply {
  public static final String PLUGIN_NAME = "sync";
  private final short sequenceNumber;
  private final Trigger trigger;
  private final Int64 delta;
  private final boolean events;
  private final byte state;

  public static QueryAlarmReply readQueryAlarmReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    QueryAlarmReply.QueryAlarmReplyBuilder javaBuilder = QueryAlarmReply.builder();
    int length = in.readCard32();
    Trigger trigger = Trigger.readTrigger(in);
    Int64 delta = Int64.readInt64(in);
    boolean events = in.readBool();
    byte state = in.readCard8();
    byte[] pad8 = in.readPad(2);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.trigger(trigger);
    javaBuilder.delta(delta);
    javaBuilder.events(events);
    javaBuilder.state(state);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    trigger.write(out);
    delta.write(out);
    out.writeBool(events);
    out.writeCard8(state);
    out.writePad(2);
  }

  @Override
  public int getSize() {
    return 40;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryAlarmReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private Trigger trigger;
    @java.lang.SuppressWarnings("all")
    private Int64 delta;
    @java.lang.SuppressWarnings("all")
    private boolean events;
    @java.lang.SuppressWarnings("all")
    private byte state;

    public QueryAlarmReply.QueryAlarmReplyBuilder state(Alarmstate state) {
      this.state = (byte) state.getValue();
      return this;
    }

    public QueryAlarmReply.QueryAlarmReplyBuilder state(byte state) {
      this.state = state;
      return this;
    }

    public int getSize() {
      return 40;
    }

    @java.lang.SuppressWarnings("all")
    QueryAlarmReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryAlarmReply.QueryAlarmReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryAlarmReply.QueryAlarmReplyBuilder trigger(final Trigger trigger) {
      if (trigger == null) {
        throw new java.lang.NullPointerException("trigger is marked non-null but is null");
      }
      this.trigger = trigger;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryAlarmReply.QueryAlarmReplyBuilder delta(final Int64 delta) {
      if (delta == null) {
        throw new java.lang.NullPointerException("delta is marked non-null but is null");
      }
      this.delta = delta;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryAlarmReply.QueryAlarmReplyBuilder events(final boolean events) {
      this.events = events;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryAlarmReply build() {
      return new QueryAlarmReply(this.sequenceNumber, this.trigger, this.delta, this.events, this.state);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryAlarmReply.QueryAlarmReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", trigger=" + this.trigger + ", delta=" + this.delta + ", events=" + this.events + ", state=" + this.state + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryAlarmReply(final short sequenceNumber, final Trigger trigger, final Int64 delta, final boolean events, final byte state) {
    if (trigger == null) {
      throw new java.lang.NullPointerException("trigger is marked non-null but is null");
    }
    if (delta == null) {
      throw new java.lang.NullPointerException("delta is marked non-null but is null");
    }
    this.sequenceNumber = sequenceNumber;
    this.trigger = trigger;
    this.delta = delta;
    this.events = events;
    this.state = state;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryAlarmReply.QueryAlarmReplyBuilder builder() {
    return new QueryAlarmReply.QueryAlarmReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public Trigger getTrigger() {
    return this.trigger;
  }

  @java.lang.SuppressWarnings("all")
  public Int64 getDelta() {
    return this.delta;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isEvents() {
    return this.events;
  }

  @java.lang.SuppressWarnings("all")
  public byte getState() {
    return this.state;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryAlarmReply)) return false;
    final QueryAlarmReply other = (QueryAlarmReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.isEvents() != other.isEvents()) return false;
    if (this.getState() != other.getState()) return false;
    final java.lang.Object this$trigger = this.getTrigger();
    final java.lang.Object other$trigger = other.getTrigger();
    if (this$trigger == null ? other$trigger != null : !this$trigger.equals(other$trigger)) return false;
    final java.lang.Object this$delta = this.getDelta();
    final java.lang.Object other$delta = other.getDelta();
    if (this$delta == null ? other$delta != null : !this$delta.equals(other$delta)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + (this.isEvents() ? 79 : 97);
    result = result * PRIME + this.getState();
    final java.lang.Object $trigger = this.getTrigger();
    result = result * PRIME + ($trigger == null ? 43 : $trigger.hashCode());
    final java.lang.Object $delta = this.getDelta();
    result = result * PRIME + ($delta == null ? 43 : $delta.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryAlarmReply(sequenceNumber=" + this.getSequenceNumber() + ", trigger=" + this.getTrigger() + ", delta=" + this.getDelta() + ", events=" + this.isEvents() + ", state=" + this.getState() + ")";
  }
}
