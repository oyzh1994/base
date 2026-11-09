package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;

public final class AlarmNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "sync";
  public static final byte NUMBER = 1;
  private final byte firstEventOffset;
  private final boolean sentEvent;
  private final byte kind;
  private final short sequenceNumber;
  private final int alarm;
  private final Int64 counterValue;
  private final Int64 alarmValue;
  private final int timestamp;
  private final byte state;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static AlarmNotifyEvent readAlarmNotifyEvent(byte firstEventOffset, boolean sentEvent, X11Input in) throws IOException {
    AlarmNotifyEvent.AlarmNotifyEventBuilder javaBuilder = AlarmNotifyEvent.builder();
    byte kind = in.readCard8();
    short sequenceNumber = in.readCard16();
    int alarm = in.readCard32();
    Int64 counterValue = Int64.readInt64(in);
    Int64 alarmValue = Int64.readInt64(in);
    int timestamp = in.readCard32();
    byte state = in.readCard8();
    byte[] pad8 = in.readPad(3);
    javaBuilder.kind(kind);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.alarm(alarm);
    javaBuilder.counterValue(counterValue);
    javaBuilder.alarmValue(alarmValue);
    javaBuilder.timestamp(timestamp);
    javaBuilder.state(state);
    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (128 | getResponseCode()) : getResponseCode());
    out.writeCard8(kind);
    out.writeCard16(sequenceNumber);
    out.writeCard32(alarm);
    counterValue.write(out);
    alarmValue.write(out);
    out.writeCard32(timestamp);
    out.writeCard8(state);
    out.writePad(3);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class AlarmNotifyEventBuilder {
    @java.lang.SuppressWarnings("all")
    private byte firstEventOffset;
    @java.lang.SuppressWarnings("all")
    private boolean sentEvent;
    @java.lang.SuppressWarnings("all")
    private byte kind;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int alarm;
    @java.lang.SuppressWarnings("all")
    private Int64 counterValue;
    @java.lang.SuppressWarnings("all")
    private Int64 alarmValue;
    @java.lang.SuppressWarnings("all")
    private int timestamp;
    @java.lang.SuppressWarnings("all")
    private byte state;

    public AlarmNotifyEvent.AlarmNotifyEventBuilder state(Alarmstate state) {
      this.state = (byte) state.getValue();
      return this;
    }

    public AlarmNotifyEvent.AlarmNotifyEventBuilder state(byte state) {
      this.state = state;
      return this;
    }

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    AlarmNotifyEventBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AlarmNotifyEvent.AlarmNotifyEventBuilder firstEventOffset(final byte firstEventOffset) {
      this.firstEventOffset = firstEventOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AlarmNotifyEvent.AlarmNotifyEventBuilder sentEvent(final boolean sentEvent) {
      this.sentEvent = sentEvent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AlarmNotifyEvent.AlarmNotifyEventBuilder kind(final byte kind) {
      this.kind = kind;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AlarmNotifyEvent.AlarmNotifyEventBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AlarmNotifyEvent.AlarmNotifyEventBuilder alarm(final int alarm) {
      this.alarm = alarm;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AlarmNotifyEvent.AlarmNotifyEventBuilder counterValue(final Int64 counterValue) {
      if (counterValue == null) {
        throw new java.lang.NullPointerException("counterValue is marked non-null but is null");
      }
      this.counterValue = counterValue;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AlarmNotifyEvent.AlarmNotifyEventBuilder alarmValue(final Int64 alarmValue) {
      if (alarmValue == null) {
        throw new java.lang.NullPointerException("alarmValue is marked non-null but is null");
      }
      this.alarmValue = alarmValue;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AlarmNotifyEvent.AlarmNotifyEventBuilder timestamp(final int timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public AlarmNotifyEvent build() {
      return new AlarmNotifyEvent(this.firstEventOffset, this.sentEvent, this.kind, this.sequenceNumber, this.alarm, this.counterValue, this.alarmValue, this.timestamp, this.state);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "AlarmNotifyEvent.AlarmNotifyEventBuilder(firstEventOffset=" + this.firstEventOffset + ", sentEvent=" + this.sentEvent + ", kind=" + this.kind + ", sequenceNumber=" + this.sequenceNumber + ", alarm=" + this.alarm + ", counterValue=" + this.counterValue + ", alarmValue=" + this.alarmValue + ", timestamp=" + this.timestamp + ", state=" + this.state + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  AlarmNotifyEvent(final byte firstEventOffset, final boolean sentEvent, final byte kind, final short sequenceNumber, final int alarm, final Int64 counterValue, final Int64 alarmValue, final int timestamp, final byte state) {
    if (counterValue == null) {
      throw new java.lang.NullPointerException("counterValue is marked non-null but is null");
    }
    if (alarmValue == null) {
      throw new java.lang.NullPointerException("alarmValue is marked non-null but is null");
    }
    this.firstEventOffset = firstEventOffset;
    this.sentEvent = sentEvent;
    this.kind = kind;
    this.sequenceNumber = sequenceNumber;
    this.alarm = alarm;
    this.counterValue = counterValue;
    this.alarmValue = alarmValue;
    this.timestamp = timestamp;
    this.state = state;
  }

  @java.lang.SuppressWarnings("all")
  public static AlarmNotifyEvent.AlarmNotifyEventBuilder builder() {
    return new AlarmNotifyEvent.AlarmNotifyEventBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getFirstEventOffset() {
    return this.firstEventOffset;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isSentEvent() {
    return this.sentEvent;
  }

  @java.lang.SuppressWarnings("all")
  public byte getKind() {
    return this.kind;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getAlarm() {
    return this.alarm;
  }

  @java.lang.SuppressWarnings("all")
  public Int64 getCounterValue() {
    return this.counterValue;
  }

  @java.lang.SuppressWarnings("all")
  public Int64 getAlarmValue() {
    return this.alarmValue;
  }

  @java.lang.SuppressWarnings("all")
  public int getTimestamp() {
    return this.timestamp;
  }

  @java.lang.SuppressWarnings("all")
  public byte getState() {
    return this.state;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof AlarmNotifyEvent)) return false;
    final AlarmNotifyEvent other = (AlarmNotifyEvent) o;
    if (this.getFirstEventOffset() != other.getFirstEventOffset()) return false;
    if (this.isSentEvent() != other.isSentEvent()) return false;
    if (this.getKind() != other.getKind()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getAlarm() != other.getAlarm()) return false;
    if (this.getTimestamp() != other.getTimestamp()) return false;
    if (this.getState() != other.getState()) return false;
    final java.lang.Object this$counterValue = this.getCounterValue();
    final java.lang.Object other$counterValue = other.getCounterValue();
    if (this$counterValue == null ? other$counterValue != null : !this$counterValue.equals(other$counterValue)) return false;
    final java.lang.Object this$alarmValue = this.getAlarmValue();
    final java.lang.Object other$alarmValue = other.getAlarmValue();
    if (this$alarmValue == null ? other$alarmValue != null : !this$alarmValue.equals(other$alarmValue)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFirstEventOffset();
    result = result * PRIME + (this.isSentEvent() ? 79 : 97);
    result = result * PRIME + this.getKind();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getAlarm();
    result = result * PRIME + this.getTimestamp();
    result = result * PRIME + this.getState();
    final java.lang.Object $counterValue = this.getCounterValue();
    result = result * PRIME + ($counterValue == null ? 43 : $counterValue.hashCode());
    final java.lang.Object $alarmValue = this.getAlarmValue();
    result = result * PRIME + ($alarmValue == null ? 43 : $alarmValue.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "AlarmNotifyEvent(firstEventOffset=" + this.getFirstEventOffset() + ", sentEvent=" + this.isSentEvent() + ", kind=" + this.getKind() + ", sequenceNumber=" + this.getSequenceNumber() + ", alarm=" + this.getAlarm() + ", counterValue=" + this.getCounterValue() + ", alarmValue=" + this.getAlarmValue() + ", timestamp=" + this.getTimestamp() + ", state=" + this.getState() + ")";
  }
}
