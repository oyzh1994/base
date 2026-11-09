package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;

public final class NewKeyboardNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "xkb";
  public static final byte NUMBER = 0;
  private final byte firstEventOffset;
  private final boolean sentEvent;
  private final byte xkbType;
  private final short sequenceNumber;
  private final int time;
  private final byte deviceID;
  private final byte oldDeviceID;
  private final byte minKeyCode;
  private final byte maxKeyCode;
  private final byte oldMinKeyCode;
  private final byte oldMaxKeyCode;
  private final byte requestMajor;
  private final byte requestMinor;
  private final short changed;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static NewKeyboardNotifyEvent readNewKeyboardNotifyEvent(byte firstEventOffset, boolean sentEvent, X11Input in) throws IOException {
    NewKeyboardNotifyEvent.NewKeyboardNotifyEventBuilder javaBuilder = NewKeyboardNotifyEvent.builder();
    byte xkbType = in.readCard8();
    short sequenceNumber = in.readCard16();
    int time = in.readCard32();
    byte deviceID = in.readCard8();
    byte oldDeviceID = in.readCard8();
    byte minKeyCode = in.readCard8();
    byte maxKeyCode = in.readCard8();
    byte oldMinKeyCode = in.readCard8();
    byte oldMaxKeyCode = in.readCard8();
    byte requestMajor = in.readCard8();
    byte requestMinor = in.readCard8();
    short changed = in.readCard16();
    byte[] pad13 = in.readPad(14);
    javaBuilder.xkbType(xkbType);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.time(time);
    javaBuilder.deviceID(deviceID);
    javaBuilder.oldDeviceID(oldDeviceID);
    javaBuilder.minKeyCode(minKeyCode);
    javaBuilder.maxKeyCode(maxKeyCode);
    javaBuilder.oldMinKeyCode(oldMinKeyCode);
    javaBuilder.oldMaxKeyCode(oldMaxKeyCode);
    javaBuilder.requestMajor(requestMajor);
    javaBuilder.requestMinor(requestMinor);
    javaBuilder.changed(changed);
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
    out.writeCard8(oldDeviceID);
    out.writeCard8(minKeyCode);
    out.writeCard8(maxKeyCode);
    out.writeCard8(oldMinKeyCode);
    out.writeCard8(oldMaxKeyCode);
    out.writeCard8(requestMajor);
    out.writeCard8(requestMinor);
    out.writeCard16(changed);
    out.writePad(14);
  }

  public boolean isChangedEnabled(NKNDetail... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (NKNDetail m : maskEnums) {
      if (!m.isEnabled(changed)) {
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


  public static class NewKeyboardNotifyEventBuilder {
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
    private byte oldDeviceID;
    @java.lang.SuppressWarnings("all")
    private byte minKeyCode;
    @java.lang.SuppressWarnings("all")
    private byte maxKeyCode;
    @java.lang.SuppressWarnings("all")
    private byte oldMinKeyCode;
    @java.lang.SuppressWarnings("all")
    private byte oldMaxKeyCode;
    @java.lang.SuppressWarnings("all")
    private byte requestMajor;
    @java.lang.SuppressWarnings("all")
    private byte requestMinor;
    @java.lang.SuppressWarnings("all")
    private short changed;

    public boolean isChangedEnabled(NKNDetail... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (NKNDetail m : maskEnums) {
        if (!m.isEnabled(changed)) {
          return false;
        }
      }
      return true;
    }

    public NewKeyboardNotifyEvent.NewKeyboardNotifyEventBuilder changedEnable(NKNDetail... maskEnums) {
      for (NKNDetail m : maskEnums) {
        changed((short) m.enableFor(changed));
      }
      return this;
    }

    public NewKeyboardNotifyEvent.NewKeyboardNotifyEventBuilder changedDisable(NKNDetail... maskEnums) {
      for (NKNDetail m : maskEnums) {
        changed((short) m.disableFor(changed));
      }
      return this;
    }

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    NewKeyboardNotifyEventBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NewKeyboardNotifyEvent.NewKeyboardNotifyEventBuilder firstEventOffset(final byte firstEventOffset) {
      this.firstEventOffset = firstEventOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NewKeyboardNotifyEvent.NewKeyboardNotifyEventBuilder sentEvent(final boolean sentEvent) {
      this.sentEvent = sentEvent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NewKeyboardNotifyEvent.NewKeyboardNotifyEventBuilder xkbType(final byte xkbType) {
      this.xkbType = xkbType;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NewKeyboardNotifyEvent.NewKeyboardNotifyEventBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NewKeyboardNotifyEvent.NewKeyboardNotifyEventBuilder time(final int time) {
      this.time = time;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NewKeyboardNotifyEvent.NewKeyboardNotifyEventBuilder deviceID(final byte deviceID) {
      this.deviceID = deviceID;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NewKeyboardNotifyEvent.NewKeyboardNotifyEventBuilder oldDeviceID(final byte oldDeviceID) {
      this.oldDeviceID = oldDeviceID;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NewKeyboardNotifyEvent.NewKeyboardNotifyEventBuilder minKeyCode(final byte minKeyCode) {
      this.minKeyCode = minKeyCode;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NewKeyboardNotifyEvent.NewKeyboardNotifyEventBuilder maxKeyCode(final byte maxKeyCode) {
      this.maxKeyCode = maxKeyCode;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NewKeyboardNotifyEvent.NewKeyboardNotifyEventBuilder oldMinKeyCode(final byte oldMinKeyCode) {
      this.oldMinKeyCode = oldMinKeyCode;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NewKeyboardNotifyEvent.NewKeyboardNotifyEventBuilder oldMaxKeyCode(final byte oldMaxKeyCode) {
      this.oldMaxKeyCode = oldMaxKeyCode;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NewKeyboardNotifyEvent.NewKeyboardNotifyEventBuilder requestMajor(final byte requestMajor) {
      this.requestMajor = requestMajor;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NewKeyboardNotifyEvent.NewKeyboardNotifyEventBuilder requestMinor(final byte requestMinor) {
      this.requestMinor = requestMinor;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NewKeyboardNotifyEvent.NewKeyboardNotifyEventBuilder changed(final short changed) {
      this.changed = changed;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public NewKeyboardNotifyEvent build() {
      return new NewKeyboardNotifyEvent(this.firstEventOffset, this.sentEvent, this.xkbType, this.sequenceNumber, this.time, this.deviceID, this.oldDeviceID, this.minKeyCode, this.maxKeyCode, this.oldMinKeyCode, this.oldMaxKeyCode, this.requestMajor, this.requestMinor, this.changed);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "NewKeyboardNotifyEvent.NewKeyboardNotifyEventBuilder(firstEventOffset=" + this.firstEventOffset + ", sentEvent=" + this.sentEvent + ", xkbType=" + this.xkbType + ", sequenceNumber=" + this.sequenceNumber + ", time=" + this.time + ", deviceID=" + this.deviceID + ", oldDeviceID=" + this.oldDeviceID + ", minKeyCode=" + this.minKeyCode + ", maxKeyCode=" + this.maxKeyCode + ", oldMinKeyCode=" + this.oldMinKeyCode + ", oldMaxKeyCode=" + this.oldMaxKeyCode + ", requestMajor=" + this.requestMajor + ", requestMinor=" + this.requestMinor + ", changed=" + this.changed + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  NewKeyboardNotifyEvent(final byte firstEventOffset, final boolean sentEvent, final byte xkbType, final short sequenceNumber, final int time, final byte deviceID, final byte oldDeviceID, final byte minKeyCode, final byte maxKeyCode, final byte oldMinKeyCode, final byte oldMaxKeyCode, final byte requestMajor, final byte requestMinor, final short changed) {
    this.firstEventOffset = firstEventOffset;
    this.sentEvent = sentEvent;
    this.xkbType = xkbType;
    this.sequenceNumber = sequenceNumber;
    this.time = time;
    this.deviceID = deviceID;
    this.oldDeviceID = oldDeviceID;
    this.minKeyCode = minKeyCode;
    this.maxKeyCode = maxKeyCode;
    this.oldMinKeyCode = oldMinKeyCode;
    this.oldMaxKeyCode = oldMaxKeyCode;
    this.requestMajor = requestMajor;
    this.requestMinor = requestMinor;
    this.changed = changed;
  }

  @java.lang.SuppressWarnings("all")
  public static NewKeyboardNotifyEvent.NewKeyboardNotifyEventBuilder builder() {
    return new NewKeyboardNotifyEvent.NewKeyboardNotifyEventBuilder();
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
  public byte getOldDeviceID() {
    return this.oldDeviceID;
  }

  @java.lang.SuppressWarnings("all")
  public byte getMinKeyCode() {
    return this.minKeyCode;
  }

  @java.lang.SuppressWarnings("all")
  public byte getMaxKeyCode() {
    return this.maxKeyCode;
  }

  @java.lang.SuppressWarnings("all")
  public byte getOldMinKeyCode() {
    return this.oldMinKeyCode;
  }

  @java.lang.SuppressWarnings("all")
  public byte getOldMaxKeyCode() {
    return this.oldMaxKeyCode;
  }

  @java.lang.SuppressWarnings("all")
  public byte getRequestMajor() {
    return this.requestMajor;
  }

  @java.lang.SuppressWarnings("all")
  public byte getRequestMinor() {
    return this.requestMinor;
  }

  @java.lang.SuppressWarnings("all")
  public short getChanged() {
    return this.changed;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof NewKeyboardNotifyEvent)) return false;
    final NewKeyboardNotifyEvent other = (NewKeyboardNotifyEvent) o;
    if (this.getFirstEventOffset() != other.getFirstEventOffset()) return false;
    if (this.isSentEvent() != other.isSentEvent()) return false;
    if (this.getXkbType() != other.getXkbType()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getTime() != other.getTime()) return false;
    if (this.getDeviceID() != other.getDeviceID()) return false;
    if (this.getOldDeviceID() != other.getOldDeviceID()) return false;
    if (this.getMinKeyCode() != other.getMinKeyCode()) return false;
    if (this.getMaxKeyCode() != other.getMaxKeyCode()) return false;
    if (this.getOldMinKeyCode() != other.getOldMinKeyCode()) return false;
    if (this.getOldMaxKeyCode() != other.getOldMaxKeyCode()) return false;
    if (this.getRequestMajor() != other.getRequestMajor()) return false;
    if (this.getRequestMinor() != other.getRequestMinor()) return false;
    if (this.getChanged() != other.getChanged()) return false;
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
    result = result * PRIME + this.getOldDeviceID();
    result = result * PRIME + this.getMinKeyCode();
    result = result * PRIME + this.getMaxKeyCode();
    result = result * PRIME + this.getOldMinKeyCode();
    result = result * PRIME + this.getOldMaxKeyCode();
    result = result * PRIME + this.getRequestMajor();
    result = result * PRIME + this.getRequestMinor();
    result = result * PRIME + this.getChanged();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "NewKeyboardNotifyEvent(firstEventOffset=" + this.getFirstEventOffset() + ", sentEvent=" + this.isSentEvent() + ", xkbType=" + this.getXkbType() + ", sequenceNumber=" + this.getSequenceNumber() + ", time=" + this.getTime() + ", deviceID=" + this.getDeviceID() + ", oldDeviceID=" + this.getOldDeviceID() + ", minKeyCode=" + this.getMinKeyCode() + ", maxKeyCode=" + this.getMaxKeyCode() + ", oldMinKeyCode=" + this.getOldMinKeyCode() + ", oldMaxKeyCode=" + this.getOldMaxKeyCode() + ", requestMajor=" + this.getRequestMajor() + ", requestMinor=" + this.getRequestMinor() + ", changed=" + this.getChanged() + ")";
  }
}
