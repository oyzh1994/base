package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;

public final class IndicatorStateNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "xkb";
  public static final byte NUMBER = 4;
  private final byte firstEventOffset;
  private final boolean sentEvent;
  private final byte xkbType;
  private final short sequenceNumber;
  private final int time;
  private final byte deviceID;
  private final int state;
  private final int stateChanged;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static IndicatorStateNotifyEvent readIndicatorStateNotifyEvent(byte firstEventOffset, boolean sentEvent, X11Input in) throws IOException {
    IndicatorStateNotifyEvent.IndicatorStateNotifyEventBuilder javaBuilder = IndicatorStateNotifyEvent.builder();
    byte xkbType = in.readCard8();
    short sequenceNumber = in.readCard16();
    int time = in.readCard32();
    byte deviceID = in.readCard8();
    byte[] pad5 = in.readPad(3);
    int state = in.readCard32();
    int stateChanged = in.readCard32();
    byte[] pad8 = in.readPad(12);
    javaBuilder.xkbType(xkbType);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.time(time);
    javaBuilder.deviceID(deviceID);
    javaBuilder.state(state);
    javaBuilder.stateChanged(stateChanged);
    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (128 | getResponseCode()) : getResponseCode());
    out.writeCard8(xkbType);
    out.writeCard16(sequenceNumber);
    out.writeCard32(time);
    out.writeCard8(deviceID);
    out.writePad(3);
    out.writeCard32(state);
    out.writeCard32(stateChanged);
    out.writePad(12);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class IndicatorStateNotifyEventBuilder {
    @java.lang.SuppressWarnings("all")
    private byte firstEventOffset;
    @java.lang.SuppressWarnings("all")
    private boolean sentEvent;
    @java.lang.SuppressWarnings("all")
    private byte xkbType;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int time;
    @java.lang.SuppressWarnings("all")
    private byte deviceID;
    @java.lang.SuppressWarnings("all")
    private int state;
    @java.lang.SuppressWarnings("all")
    private int stateChanged;

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    IndicatorStateNotifyEventBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public IndicatorStateNotifyEvent.IndicatorStateNotifyEventBuilder firstEventOffset(final byte firstEventOffset) {
      this.firstEventOffset = firstEventOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public IndicatorStateNotifyEvent.IndicatorStateNotifyEventBuilder sentEvent(final boolean sentEvent) {
      this.sentEvent = sentEvent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public IndicatorStateNotifyEvent.IndicatorStateNotifyEventBuilder xkbType(final byte xkbType) {
      this.xkbType = xkbType;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public IndicatorStateNotifyEvent.IndicatorStateNotifyEventBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public IndicatorStateNotifyEvent.IndicatorStateNotifyEventBuilder time(final int time) {
      this.time = time;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public IndicatorStateNotifyEvent.IndicatorStateNotifyEventBuilder deviceID(final byte deviceID) {
      this.deviceID = deviceID;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public IndicatorStateNotifyEvent.IndicatorStateNotifyEventBuilder state(final int state) {
      this.state = state;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public IndicatorStateNotifyEvent.IndicatorStateNotifyEventBuilder stateChanged(final int stateChanged) {
      this.stateChanged = stateChanged;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public IndicatorStateNotifyEvent build() {
      return new IndicatorStateNotifyEvent(this.firstEventOffset, this.sentEvent, this.xkbType, this.sequenceNumber, this.time, this.deviceID, this.state, this.stateChanged);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "IndicatorStateNotifyEvent.IndicatorStateNotifyEventBuilder(firstEventOffset=" + this.firstEventOffset + ", sentEvent=" + this.sentEvent + ", xkbType=" + this.xkbType + ", sequenceNumber=" + this.sequenceNumber + ", time=" + this.time + ", deviceID=" + this.deviceID + ", state=" + this.state + ", stateChanged=" + this.stateChanged + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  IndicatorStateNotifyEvent(final byte firstEventOffset, final boolean sentEvent, final byte xkbType, final short sequenceNumber, final int time, final byte deviceID, final int state, final int stateChanged) {
    this.firstEventOffset = firstEventOffset;
    this.sentEvent = sentEvent;
    this.xkbType = xkbType;
    this.sequenceNumber = sequenceNumber;
    this.time = time;
    this.deviceID = deviceID;
    this.state = state;
    this.stateChanged = stateChanged;
  }

  @java.lang.SuppressWarnings("all")
  public static IndicatorStateNotifyEvent.IndicatorStateNotifyEventBuilder builder() {
    return new IndicatorStateNotifyEvent.IndicatorStateNotifyEventBuilder();
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
  public byte getXkbType() {
    return this.xkbType;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getTime() {
    return this.time;
  }

  @java.lang.SuppressWarnings("all")
  public byte getDeviceID() {
    return this.deviceID;
  }

  @java.lang.SuppressWarnings("all")
  public int getState() {
    return this.state;
  }

  @java.lang.SuppressWarnings("all")
  public int getStateChanged() {
    return this.stateChanged;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof IndicatorStateNotifyEvent)) return false;
    final IndicatorStateNotifyEvent other = (IndicatorStateNotifyEvent) o;
    if (this.getFirstEventOffset() != other.getFirstEventOffset()) return false;
    if (this.isSentEvent() != other.isSentEvent()) return false;
    if (this.getXkbType() != other.getXkbType()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getTime() != other.getTime()) return false;
    if (this.getDeviceID() != other.getDeviceID()) return false;
    if (this.getState() != other.getState()) return false;
    if (this.getStateChanged() != other.getStateChanged()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFirstEventOffset();
    result = result * PRIME + (this.isSentEvent() ? 79 : 97);
    result = result * PRIME + this.getXkbType();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getTime();
    result = result * PRIME + this.getDeviceID();
    result = result * PRIME + this.getState();
    result = result * PRIME + this.getStateChanged();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "IndicatorStateNotifyEvent(firstEventOffset=" + this.getFirstEventOffset() + ", sentEvent=" + this.isSentEvent() + ", xkbType=" + this.getXkbType() + ", sequenceNumber=" + this.getSequenceNumber() + ", time=" + this.getTime() + ", deviceID=" + this.getDeviceID() + ", state=" + this.getState() + ", stateChanged=" + this.getStateChanged() + ")";
  }
}
