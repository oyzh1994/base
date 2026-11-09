package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;

public final class AccessXNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "xkb";
  public static final byte NUMBER = 10;
  private final byte firstEventOffset;
  private final boolean sentEvent;
  private final byte xkbType;
  private final short sequenceNumber;
  private final int time;
  private final byte deviceID;
  private final byte keycode;
  private final short detailt;
  private final short slowKeysDelay;
  private final short debounceDelay;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static AccessXNotifyEvent readAccessXNotifyEvent(byte firstEventOffset, boolean sentEvent, X11Input in) throws IOException {
    AccessXNotifyEvent.AccessXNotifyEventBuilder javaBuilder = AccessXNotifyEvent.builder();
    byte xkbType = in.readCard8();
    short sequenceNumber = in.readCard16();
    int time = in.readCard32();
    byte deviceID = in.readCard8();
    byte keycode = in.readCard8();
    short detailt = in.readCard16();
    short slowKeysDelay = in.readCard16();
    short debounceDelay = in.readCard16();
    byte[] pad9 = in.readPad(16);
    javaBuilder.xkbType(xkbType);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.time(time);
    javaBuilder.deviceID(deviceID);
    javaBuilder.keycode(keycode);
    javaBuilder.detailt(detailt);
    javaBuilder.slowKeysDelay(slowKeysDelay);
    javaBuilder.debounceDelay(debounceDelay);
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
    out.writeCard8(keycode);
    out.writeCard16(detailt);
    out.writeCard16(slowKeysDelay);
    out.writeCard16(debounceDelay);
    out.writePad(16);
  }

  public boolean isDetailtEnabled(AXNDetail... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (AXNDetail m : maskEnums) {
      if (!m.isEnabled(detailt)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class AccessXNotifyEventBuilder {
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
    private byte keycode;
    @java.lang.SuppressWarnings("all")
    private short detailt;
    @java.lang.SuppressWarnings("all")
    private short slowKeysDelay;
    @java.lang.SuppressWarnings("all")
    private short debounceDelay;

    public boolean isDetailtEnabled(AXNDetail... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (AXNDetail m : maskEnums) {
        if (!m.isEnabled(detailt)) {
          return false;
        }
      }
      return true;
    }

    public AccessXNotifyEvent.AccessXNotifyEventBuilder detailtEnable(AXNDetail... maskEnums) {
      for (AXNDetail m : maskEnums) {
        detailt((short) m.enableFor(detailt));
      }
      return this;
    }

    public AccessXNotifyEvent.AccessXNotifyEventBuilder detailtDisable(AXNDetail... maskEnums) {
      for (AXNDetail m : maskEnums) {
        detailt((short) m.disableFor(detailt));
      }
      return this;
    }

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    AccessXNotifyEventBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AccessXNotifyEvent.AccessXNotifyEventBuilder firstEventOffset(final byte firstEventOffset) {
      this.firstEventOffset = firstEventOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AccessXNotifyEvent.AccessXNotifyEventBuilder sentEvent(final boolean sentEvent) {
      this.sentEvent = sentEvent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AccessXNotifyEvent.AccessXNotifyEventBuilder xkbType(final byte xkbType) {
      this.xkbType = xkbType;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AccessXNotifyEvent.AccessXNotifyEventBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AccessXNotifyEvent.AccessXNotifyEventBuilder time(final int time) {
      this.time = time;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AccessXNotifyEvent.AccessXNotifyEventBuilder deviceID(final byte deviceID) {
      this.deviceID = deviceID;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AccessXNotifyEvent.AccessXNotifyEventBuilder keycode(final byte keycode) {
      this.keycode = keycode;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AccessXNotifyEvent.AccessXNotifyEventBuilder detailt(final short detailt) {
      this.detailt = detailt;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AccessXNotifyEvent.AccessXNotifyEventBuilder slowKeysDelay(final short slowKeysDelay) {
      this.slowKeysDelay = slowKeysDelay;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AccessXNotifyEvent.AccessXNotifyEventBuilder debounceDelay(final short debounceDelay) {
      this.debounceDelay = debounceDelay;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public AccessXNotifyEvent build() {
      return new AccessXNotifyEvent(this.firstEventOffset, this.sentEvent, this.xkbType, this.sequenceNumber, this.time, this.deviceID, this.keycode, this.detailt, this.slowKeysDelay, this.debounceDelay);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "AccessXNotifyEvent.AccessXNotifyEventBuilder(firstEventOffset=" + this.firstEventOffset + ", sentEvent=" + this.sentEvent + ", xkbType=" + this.xkbType + ", sequenceNumber=" + this.sequenceNumber + ", time=" + this.time + ", deviceID=" + this.deviceID + ", keycode=" + this.keycode + ", detailt=" + this.detailt + ", slowKeysDelay=" + this.slowKeysDelay + ", debounceDelay=" + this.debounceDelay + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  AccessXNotifyEvent(final byte firstEventOffset, final boolean sentEvent, final byte xkbType, final short sequenceNumber, final int time, final byte deviceID, final byte keycode, final short detailt, final short slowKeysDelay, final short debounceDelay) {
    this.firstEventOffset = firstEventOffset;
    this.sentEvent = sentEvent;
    this.xkbType = xkbType;
    this.sequenceNumber = sequenceNumber;
    this.time = time;
    this.deviceID = deviceID;
    this.keycode = keycode;
    this.detailt = detailt;
    this.slowKeysDelay = slowKeysDelay;
    this.debounceDelay = debounceDelay;
  }

  @java.lang.SuppressWarnings("all")
  public static AccessXNotifyEvent.AccessXNotifyEventBuilder builder() {
    return new AccessXNotifyEvent.AccessXNotifyEventBuilder();
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
  public byte getKeycode() {
    return this.keycode;
  }

  @java.lang.SuppressWarnings("all")
  public short getDetailt() {
    return this.detailt;
  }

  @java.lang.SuppressWarnings("all")
  public short getSlowKeysDelay() {
    return this.slowKeysDelay;
  }

  @java.lang.SuppressWarnings("all")
  public short getDebounceDelay() {
    return this.debounceDelay;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof AccessXNotifyEvent)) return false;
    final AccessXNotifyEvent other = (AccessXNotifyEvent) o;
    if (this.getFirstEventOffset() != other.getFirstEventOffset()) return false;
    if (this.isSentEvent() != other.isSentEvent()) return false;
    if (this.getXkbType() != other.getXkbType()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getTime() != other.getTime()) return false;
    if (this.getDeviceID() != other.getDeviceID()) return false;
    if (this.getKeycode() != other.getKeycode()) return false;
    if (this.getDetailt() != other.getDetailt()) return false;
    if (this.getSlowKeysDelay() != other.getSlowKeysDelay()) return false;
    if (this.getDebounceDelay() != other.getDebounceDelay()) return false;
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
    result = result * PRIME + this.getKeycode();
    result = result * PRIME + this.getDetailt();
    result = result * PRIME + this.getSlowKeysDelay();
    result = result * PRIME + this.getDebounceDelay();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "AccessXNotifyEvent(firstEventOffset=" + this.getFirstEventOffset() + ", sentEvent=" + this.isSentEvent() + ", xkbType=" + this.getXkbType() + ", sequenceNumber=" + this.getSequenceNumber() + ", time=" + this.getTime() + ", deviceID=" + this.getDeviceID() + ", keycode=" + this.getKeycode() + ", detailt=" + this.getDetailt() + ", slowKeysDelay=" + this.getSlowKeysDelay() + ", debounceDelay=" + this.getDebounceDelay() + ")";
  }
}
