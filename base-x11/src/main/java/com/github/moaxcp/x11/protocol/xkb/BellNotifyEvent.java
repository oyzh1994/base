package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;

public final class BellNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "xkb";
  public static final byte NUMBER = 8;
  private final byte firstEventOffset;
  private final boolean sentEvent;
  private final byte xkbType;
  private final short sequenceNumber;
  private final int time;
  private final byte deviceID;
  private final byte bellClass;
  private final byte bellID;
  private final byte percent;
  private final short pitch;
  private final short duration;
  private final int name;
  private final int window;
  private final boolean eventOnly;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static BellNotifyEvent readBellNotifyEvent(byte firstEventOffset, boolean sentEvent, X11Input in) throws IOException {
    BellNotifyEvent.BellNotifyEventBuilder javaBuilder = BellNotifyEvent.builder();
    byte xkbType = in.readCard8();
    short sequenceNumber = in.readCard16();
    int time = in.readCard32();
    byte deviceID = in.readCard8();
    byte bellClass = in.readCard8();
    byte bellID = in.readCard8();
    byte percent = in.readCard8();
    short pitch = in.readCard16();
    short duration = in.readCard16();
    int name = in.readCard32();
    int window = in.readCard32();
    boolean eventOnly = in.readBool();
    byte[] pad13 = in.readPad(7);
    javaBuilder.xkbType(xkbType);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.time(time);
    javaBuilder.deviceID(deviceID);
    javaBuilder.bellClass(bellClass);
    javaBuilder.bellID(bellID);
    javaBuilder.percent(percent);
    javaBuilder.pitch(pitch);
    javaBuilder.duration(duration);
    javaBuilder.name(name);
    javaBuilder.window(window);
    javaBuilder.eventOnly(eventOnly);
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
    out.writeCard8(bellClass);
    out.writeCard8(bellID);
    out.writeCard8(percent);
    out.writeCard16(pitch);
    out.writeCard16(duration);
    out.writeCard32(name);
    out.writeCard32(window);
    out.writeBool(eventOnly);
    out.writePad(7);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class BellNotifyEventBuilder {
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
    private byte bellClass;
    @java.lang.SuppressWarnings("all")
    private byte bellID;
    @java.lang.SuppressWarnings("all")
    private byte percent;
    @java.lang.SuppressWarnings("all")
    private short pitch;
    @java.lang.SuppressWarnings("all")
    private short duration;
    @java.lang.SuppressWarnings("all")
    private int name;
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private boolean eventOnly;

    public BellNotifyEvent.BellNotifyEventBuilder bellClass(BellClassResult bellClass) {
      this.bellClass = (byte) bellClass.getValue();
      return this;
    }

    public BellNotifyEvent.BellNotifyEventBuilder bellClass(byte bellClass) {
      this.bellClass = bellClass;
      return this;
    }

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    BellNotifyEventBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public BellNotifyEvent.BellNotifyEventBuilder firstEventOffset(final byte firstEventOffset) {
      this.firstEventOffset = firstEventOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public BellNotifyEvent.BellNotifyEventBuilder sentEvent(final boolean sentEvent) {
      this.sentEvent = sentEvent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public BellNotifyEvent.BellNotifyEventBuilder xkbType(final byte xkbType) {
      this.xkbType = xkbType;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public BellNotifyEvent.BellNotifyEventBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public BellNotifyEvent.BellNotifyEventBuilder time(final int time) {
      this.time = time;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public BellNotifyEvent.BellNotifyEventBuilder deviceID(final byte deviceID) {
      this.deviceID = deviceID;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public BellNotifyEvent.BellNotifyEventBuilder bellID(final byte bellID) {
      this.bellID = bellID;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public BellNotifyEvent.BellNotifyEventBuilder percent(final byte percent) {
      this.percent = percent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public BellNotifyEvent.BellNotifyEventBuilder pitch(final short pitch) {
      this.pitch = pitch;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public BellNotifyEvent.BellNotifyEventBuilder duration(final short duration) {
      this.duration = duration;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public BellNotifyEvent.BellNotifyEventBuilder name(final int name) {
      this.name = name;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public BellNotifyEvent.BellNotifyEventBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public BellNotifyEvent.BellNotifyEventBuilder eventOnly(final boolean eventOnly) {
      this.eventOnly = eventOnly;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public BellNotifyEvent build() {
      return new BellNotifyEvent(this.firstEventOffset, this.sentEvent, this.xkbType, this.sequenceNumber, this.time, this.deviceID, this.bellClass, this.bellID, this.percent, this.pitch, this.duration, this.name, this.window, this.eventOnly);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "BellNotifyEvent.BellNotifyEventBuilder(firstEventOffset=" + this.firstEventOffset + ", sentEvent=" + this.sentEvent + ", xkbType=" + this.xkbType + ", sequenceNumber=" + this.sequenceNumber + ", time=" + this.time + ", deviceID=" + this.deviceID + ", bellClass=" + this.bellClass + ", bellID=" + this.bellID + ", percent=" + this.percent + ", pitch=" + this.pitch + ", duration=" + this.duration + ", name=" + this.name + ", window=" + this.window + ", eventOnly=" + this.eventOnly + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  BellNotifyEvent(final byte firstEventOffset, final boolean sentEvent, final byte xkbType, final short sequenceNumber, final int time, final byte deviceID, final byte bellClass, final byte bellID, final byte percent, final short pitch, final short duration, final int name, final int window, final boolean eventOnly) {
    this.firstEventOffset = firstEventOffset;
    this.sentEvent = sentEvent;
    this.xkbType = xkbType;
    this.sequenceNumber = sequenceNumber;
    this.time = time;
    this.deviceID = deviceID;
    this.bellClass = bellClass;
    this.bellID = bellID;
    this.percent = percent;
    this.pitch = pitch;
    this.duration = duration;
    this.name = name;
    this.window = window;
    this.eventOnly = eventOnly;
  }

  @java.lang.SuppressWarnings("all")
  public static BellNotifyEvent.BellNotifyEventBuilder builder() {
    return new BellNotifyEvent.BellNotifyEventBuilder();
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
  public byte getBellClass() {
    return this.bellClass;
  }

  @java.lang.SuppressWarnings("all")
  public byte getBellID() {
    return this.bellID;
  }

  @java.lang.SuppressWarnings("all")
  public byte getPercent() {
    return this.percent;
  }

  @java.lang.SuppressWarnings("all")
  public short getPitch() {
    return this.pitch;
  }

  @java.lang.SuppressWarnings("all")
  public short getDuration() {
    return this.duration;
  }

  @java.lang.SuppressWarnings("all")
  public int getName() {
    return this.name;
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isEventOnly() {
    return this.eventOnly;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof BellNotifyEvent)) return false;
    final BellNotifyEvent other = (BellNotifyEvent) o;
    if (this.getFirstEventOffset() != other.getFirstEventOffset()) return false;
    if (this.isSentEvent() != other.isSentEvent()) return false;
    if (this.getXkbType() != other.getXkbType()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getTime() != other.getTime()) return false;
    if (this.getDeviceID() != other.getDeviceID()) return false;
    if (this.getBellClass() != other.getBellClass()) return false;
    if (this.getBellID() != other.getBellID()) return false;
    if (this.getPercent() != other.getPercent()) return false;
    if (this.getPitch() != other.getPitch()) return false;
    if (this.getDuration() != other.getDuration()) return false;
    if (this.getName() != other.getName()) return false;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.isEventOnly() != other.isEventOnly()) return false;
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
    result = result * PRIME + this.getBellClass();
    result = result * PRIME + this.getBellID();
    result = result * PRIME + this.getPercent();
    result = result * PRIME + this.getPitch();
    result = result * PRIME + this.getDuration();
    result = result * PRIME + this.getName();
    result = result * PRIME + this.getWindow();
    result = result * PRIME + (this.isEventOnly() ? 79 : 97);
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "BellNotifyEvent(firstEventOffset=" + this.getFirstEventOffset() + ", sentEvent=" + this.isSentEvent() + ", xkbType=" + this.getXkbType() + ", sequenceNumber=" + this.getSequenceNumber() + ", time=" + this.getTime() + ", deviceID=" + this.getDeviceID() + ", bellClass=" + this.getBellClass() + ", bellID=" + this.getBellID() + ", percent=" + this.getPercent() + ", pitch=" + this.getPitch() + ", duration=" + this.getDuration() + ", name=" + this.getName() + ", window=" + this.getWindow() + ", eventOnly=" + this.isEventOnly() + ")";
  }
}
