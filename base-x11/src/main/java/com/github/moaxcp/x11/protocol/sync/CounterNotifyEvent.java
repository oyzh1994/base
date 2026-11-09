package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;

public final class CounterNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "sync";
  public static final byte NUMBER = 0;
  private final byte firstEventOffset;
  private final boolean sentEvent;
  private final byte kind;
  private final short sequenceNumber;
  private final int counter;
  private final Int64 waitValue;
  private final Int64 counterValue;
  private final int timestamp;
  private final short count;
  private final boolean destroyed;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static CounterNotifyEvent readCounterNotifyEvent(byte firstEventOffset, boolean sentEvent, X11Input in) throws IOException {
    CounterNotifyEvent.CounterNotifyEventBuilder javaBuilder = CounterNotifyEvent.builder();
    byte kind = in.readCard8();
    short sequenceNumber = in.readCard16();
    int counter = in.readCard32();
    Int64 waitValue = Int64.readInt64(in);
    Int64 counterValue = Int64.readInt64(in);
    int timestamp = in.readCard32();
    short count = in.readCard16();
    boolean destroyed = in.readBool();
    byte[] pad9 = in.readPad(1);
    javaBuilder.kind(kind);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.counter(counter);
    javaBuilder.waitValue(waitValue);
    javaBuilder.counterValue(counterValue);
    javaBuilder.timestamp(timestamp);
    javaBuilder.count(count);
    javaBuilder.destroyed(destroyed);
    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (128 | getResponseCode()) : getResponseCode());
    out.writeCard8(kind);
    out.writeCard16(sequenceNumber);
    out.writeCard32(counter);
    waitValue.write(out);
    counterValue.write(out);
    out.writeCard32(timestamp);
    out.writeCard16(count);
    out.writeBool(destroyed);
    out.writePad(1);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CounterNotifyEventBuilder {
    @java.lang.SuppressWarnings("all")
    private byte firstEventOffset;
    @java.lang.SuppressWarnings("all")
    private boolean sentEvent;
    @java.lang.SuppressWarnings("all")
    private byte kind;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int counter;
    @java.lang.SuppressWarnings("all")
    private Int64 waitValue;
    @java.lang.SuppressWarnings("all")
    private Int64 counterValue;
    @java.lang.SuppressWarnings("all")
    private int timestamp;
    @java.lang.SuppressWarnings("all")
    private short count;
    @java.lang.SuppressWarnings("all")
    private boolean destroyed;

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    CounterNotifyEventBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CounterNotifyEvent.CounterNotifyEventBuilder firstEventOffset(final byte firstEventOffset) {
      this.firstEventOffset = firstEventOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CounterNotifyEvent.CounterNotifyEventBuilder sentEvent(final boolean sentEvent) {
      this.sentEvent = sentEvent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CounterNotifyEvent.CounterNotifyEventBuilder kind(final byte kind) {
      this.kind = kind;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CounterNotifyEvent.CounterNotifyEventBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CounterNotifyEvent.CounterNotifyEventBuilder counter(final int counter) {
      this.counter = counter;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CounterNotifyEvent.CounterNotifyEventBuilder waitValue(final Int64 waitValue) {
      if (waitValue == null) {
        throw new java.lang.NullPointerException("waitValue is marked non-null but is null");
      }
      this.waitValue = waitValue;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CounterNotifyEvent.CounterNotifyEventBuilder counterValue(final Int64 counterValue) {
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
    public CounterNotifyEvent.CounterNotifyEventBuilder timestamp(final int timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CounterNotifyEvent.CounterNotifyEventBuilder count(final short count) {
      this.count = count;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CounterNotifyEvent.CounterNotifyEventBuilder destroyed(final boolean destroyed) {
      this.destroyed = destroyed;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CounterNotifyEvent build() {
      return new CounterNotifyEvent(this.firstEventOffset, this.sentEvent, this.kind, this.sequenceNumber, this.counter, this.waitValue, this.counterValue, this.timestamp, this.count, this.destroyed);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CounterNotifyEvent.CounterNotifyEventBuilder(firstEventOffset=" + this.firstEventOffset + ", sentEvent=" + this.sentEvent + ", kind=" + this.kind + ", sequenceNumber=" + this.sequenceNumber + ", counter=" + this.counter + ", waitValue=" + this.waitValue + ", counterValue=" + this.counterValue + ", timestamp=" + this.timestamp + ", count=" + this.count + ", destroyed=" + this.destroyed + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CounterNotifyEvent(final byte firstEventOffset, final boolean sentEvent, final byte kind, final short sequenceNumber, final int counter, final Int64 waitValue, final Int64 counterValue, final int timestamp, final short count, final boolean destroyed) {
    if (waitValue == null) {
      throw new java.lang.NullPointerException("waitValue is marked non-null but is null");
    }
    if (counterValue == null) {
      throw new java.lang.NullPointerException("counterValue is marked non-null but is null");
    }
    this.firstEventOffset = firstEventOffset;
    this.sentEvent = sentEvent;
    this.kind = kind;
    this.sequenceNumber = sequenceNumber;
    this.counter = counter;
    this.waitValue = waitValue;
    this.counterValue = counterValue;
    this.timestamp = timestamp;
    this.count = count;
    this.destroyed = destroyed;
  }

  @java.lang.SuppressWarnings("all")
  public static CounterNotifyEvent.CounterNotifyEventBuilder builder() {
    return new CounterNotifyEvent.CounterNotifyEventBuilder();
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
  public int getCounter() {
    return this.counter;
  }

  @java.lang.SuppressWarnings("all")
  public Int64 getWaitValue() {
    return this.waitValue;
  }

  @java.lang.SuppressWarnings("all")
  public Int64 getCounterValue() {
    return this.counterValue;
  }

  @java.lang.SuppressWarnings("all")
  public int getTimestamp() {
    return this.timestamp;
  }

  @java.lang.SuppressWarnings("all")
  public short getCount() {
    return this.count;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isDestroyed() {
    return this.destroyed;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CounterNotifyEvent)) return false;
    final CounterNotifyEvent other = (CounterNotifyEvent) o;
    if (this.getFirstEventOffset() != other.getFirstEventOffset()) return false;
    if (this.isSentEvent() != other.isSentEvent()) return false;
    if (this.getKind() != other.getKind()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getCounter() != other.getCounter()) return false;
    if (this.getTimestamp() != other.getTimestamp()) return false;
    if (this.getCount() != other.getCount()) return false;
    if (this.isDestroyed() != other.isDestroyed()) return false;
    final java.lang.Object this$waitValue = this.getWaitValue();
    final java.lang.Object other$waitValue = other.getWaitValue();
    if (this$waitValue == null ? other$waitValue != null : !this$waitValue.equals(other$waitValue)) return false;
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
    result = result * PRIME + this.getFirstEventOffset();
    result = result * PRIME + (this.isSentEvent() ? 79 : 97);
    result = result * PRIME + this.getKind();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getCounter();
    result = result * PRIME + this.getTimestamp();
    result = result * PRIME + this.getCount();
    result = result * PRIME + (this.isDestroyed() ? 79 : 97);
    final java.lang.Object $waitValue = this.getWaitValue();
    result = result * PRIME + ($waitValue == null ? 43 : $waitValue.hashCode());
    final java.lang.Object $counterValue = this.getCounterValue();
    result = result * PRIME + ($counterValue == null ? 43 : $counterValue.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CounterNotifyEvent(firstEventOffset=" + this.getFirstEventOffset() + ", sentEvent=" + this.isSentEvent() + ", kind=" + this.getKind() + ", sequenceNumber=" + this.getSequenceNumber() + ", counter=" + this.getCounter() + ", waitValue=" + this.getWaitValue() + ", counterValue=" + this.getCounterValue() + ", timestamp=" + this.getTimestamp() + ", count=" + this.getCount() + ", destroyed=" + this.isDestroyed() + ")";
  }
}
