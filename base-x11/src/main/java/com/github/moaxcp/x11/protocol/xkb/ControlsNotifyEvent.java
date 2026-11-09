package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;

public final class ControlsNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "xkb";
  public static final byte NUMBER = 3;
  private final byte firstEventOffset;
  private final boolean sentEvent;
  private final byte xkbType;
  private final short sequenceNumber;
  private final int time;
  private final byte deviceID;
  private final byte numGroups;
  private final int changedControls;
  private final int enabledControls;
  private final int enabledControlChanges;
  private final byte keycode;
  private final byte eventType;
  private final byte requestMajor;
  private final byte requestMinor;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static ControlsNotifyEvent readControlsNotifyEvent(byte firstEventOffset, boolean sentEvent, X11Input in) throws IOException {
    ControlsNotifyEvent.ControlsNotifyEventBuilder javaBuilder = ControlsNotifyEvent.builder();
    byte xkbType = in.readCard8();
    short sequenceNumber = in.readCard16();
    int time = in.readCard32();
    byte deviceID = in.readCard8();
    byte numGroups = in.readCard8();
    byte[] pad6 = in.readPad(2);
    int changedControls = in.readCard32();
    int enabledControls = in.readCard32();
    int enabledControlChanges = in.readCard32();
    byte keycode = in.readCard8();
    byte eventType = in.readCard8();
    byte requestMajor = in.readCard8();
    byte requestMinor = in.readCard8();
    byte[] pad14 = in.readPad(4);
    javaBuilder.xkbType(xkbType);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.time(time);
    javaBuilder.deviceID(deviceID);
    javaBuilder.numGroups(numGroups);
    javaBuilder.changedControls(changedControls);
    javaBuilder.enabledControls(enabledControls);
    javaBuilder.enabledControlChanges(enabledControlChanges);
    javaBuilder.keycode(keycode);
    javaBuilder.eventType(eventType);
    javaBuilder.requestMajor(requestMajor);
    javaBuilder.requestMinor(requestMinor);
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
    out.writeCard8(numGroups);
    out.writePad(2);
    out.writeCard32(changedControls);
    out.writeCard32(enabledControls);
    out.writeCard32(enabledControlChanges);
    out.writeCard8(keycode);
    out.writeCard8(eventType);
    out.writeCard8(requestMajor);
    out.writeCard8(requestMinor);
    out.writePad(4);
  }

  public boolean isChangedControlsEnabled(Control... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (Control m : maskEnums) {
      if (!m.isEnabled(changedControls)) {
        return false;
      }
    }
    return true;
  }

  public boolean isEnabledControlsEnabled(BoolCtrl... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (BoolCtrl m : maskEnums) {
      if (!m.isEnabled(enabledControls)) {
        return false;
      }
    }
    return true;
  }

  public boolean isEnabledControlChangesEnabled(BoolCtrl... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (BoolCtrl m : maskEnums) {
      if (!m.isEnabled(enabledControlChanges)) {
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


  public static class ControlsNotifyEventBuilder {
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
    private byte numGroups;
    @java.lang.SuppressWarnings("all")
    private int changedControls;
    @java.lang.SuppressWarnings("all")
    private int enabledControls;
    @java.lang.SuppressWarnings("all")
    private int enabledControlChanges;
    @java.lang.SuppressWarnings("all")
    private byte keycode;
    @java.lang.SuppressWarnings("all")
    private byte eventType;
    @java.lang.SuppressWarnings("all")
    private byte requestMajor;
    @java.lang.SuppressWarnings("all")
    private byte requestMinor;

    public boolean isChangedControlsEnabled(Control... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (Control m : maskEnums) {
        if (!m.isEnabled(changedControls)) {
          return false;
        }
      }
      return true;
    }

    public ControlsNotifyEvent.ControlsNotifyEventBuilder changedControlsEnable(Control... maskEnums) {
      for (Control m : maskEnums) {
        changedControls((int) m.enableFor(changedControls));
      }
      return this;
    }

    public ControlsNotifyEvent.ControlsNotifyEventBuilder changedControlsDisable(Control... maskEnums) {
      for (Control m : maskEnums) {
        changedControls((int) m.disableFor(changedControls));
      }
      return this;
    }

    public boolean isEnabledControlsEnabled(BoolCtrl... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (BoolCtrl m : maskEnums) {
        if (!m.isEnabled(enabledControls)) {
          return false;
        }
      }
      return true;
    }

    public ControlsNotifyEvent.ControlsNotifyEventBuilder enabledControlsEnable(BoolCtrl... maskEnums) {
      for (BoolCtrl m : maskEnums) {
        enabledControls((int) m.enableFor(enabledControls));
      }
      return this;
    }

    public ControlsNotifyEvent.ControlsNotifyEventBuilder enabledControlsDisable(BoolCtrl... maskEnums) {
      for (BoolCtrl m : maskEnums) {
        enabledControls((int) m.disableFor(enabledControls));
      }
      return this;
    }

    public boolean isEnabledControlChangesEnabled(BoolCtrl... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (BoolCtrl m : maskEnums) {
        if (!m.isEnabled(enabledControlChanges)) {
          return false;
        }
      }
      return true;
    }

    public ControlsNotifyEvent.ControlsNotifyEventBuilder enabledControlChangesEnable(BoolCtrl... maskEnums) {
      for (BoolCtrl m : maskEnums) {
        enabledControlChanges((int) m.enableFor(enabledControlChanges));
      }
      return this;
    }

    public ControlsNotifyEvent.ControlsNotifyEventBuilder enabledControlChangesDisable(BoolCtrl... maskEnums) {
      for (BoolCtrl m : maskEnums) {
        enabledControlChanges((int) m.disableFor(enabledControlChanges));
      }
      return this;
    }

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    ControlsNotifyEventBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ControlsNotifyEvent.ControlsNotifyEventBuilder firstEventOffset(final byte firstEventOffset) {
      this.firstEventOffset = firstEventOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ControlsNotifyEvent.ControlsNotifyEventBuilder sentEvent(final boolean sentEvent) {
      this.sentEvent = sentEvent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ControlsNotifyEvent.ControlsNotifyEventBuilder xkbType(final byte xkbType) {
      this.xkbType = xkbType;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ControlsNotifyEvent.ControlsNotifyEventBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ControlsNotifyEvent.ControlsNotifyEventBuilder time(final int time) {
      this.time = time;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ControlsNotifyEvent.ControlsNotifyEventBuilder deviceID(final byte deviceID) {
      this.deviceID = deviceID;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ControlsNotifyEvent.ControlsNotifyEventBuilder numGroups(final byte numGroups) {
      this.numGroups = numGroups;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ControlsNotifyEvent.ControlsNotifyEventBuilder changedControls(final int changedControls) {
      this.changedControls = changedControls;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ControlsNotifyEvent.ControlsNotifyEventBuilder enabledControls(final int enabledControls) {
      this.enabledControls = enabledControls;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ControlsNotifyEvent.ControlsNotifyEventBuilder enabledControlChanges(final int enabledControlChanges) {
      this.enabledControlChanges = enabledControlChanges;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ControlsNotifyEvent.ControlsNotifyEventBuilder keycode(final byte keycode) {
      this.keycode = keycode;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ControlsNotifyEvent.ControlsNotifyEventBuilder eventType(final byte eventType) {
      this.eventType = eventType;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ControlsNotifyEvent.ControlsNotifyEventBuilder requestMajor(final byte requestMajor) {
      this.requestMajor = requestMajor;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ControlsNotifyEvent.ControlsNotifyEventBuilder requestMinor(final byte requestMinor) {
      this.requestMinor = requestMinor;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ControlsNotifyEvent build() {
      return new ControlsNotifyEvent(this.firstEventOffset, this.sentEvent, this.xkbType, this.sequenceNumber, this.time, this.deviceID, this.numGroups, this.changedControls, this.enabledControls, this.enabledControlChanges, this.keycode, this.eventType, this.requestMajor, this.requestMinor);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ControlsNotifyEvent.ControlsNotifyEventBuilder(firstEventOffset=" + this.firstEventOffset + ", sentEvent=" + this.sentEvent + ", xkbType=" + this.xkbType + ", sequenceNumber=" + this.sequenceNumber + ", time=" + this.time + ", deviceID=" + this.deviceID + ", numGroups=" + this.numGroups + ", changedControls=" + this.changedControls + ", enabledControls=" + this.enabledControls + ", enabledControlChanges=" + this.enabledControlChanges + ", keycode=" + this.keycode + ", eventType=" + this.eventType + ", requestMajor=" + this.requestMajor + ", requestMinor=" + this.requestMinor + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ControlsNotifyEvent(final byte firstEventOffset, final boolean sentEvent, final byte xkbType, final short sequenceNumber, final int time, final byte deviceID, final byte numGroups, final int changedControls, final int enabledControls, final int enabledControlChanges, final byte keycode, final byte eventType, final byte requestMajor, final byte requestMinor) {
    this.firstEventOffset = firstEventOffset;
    this.sentEvent = sentEvent;
    this.xkbType = xkbType;
    this.sequenceNumber = sequenceNumber;
    this.time = time;
    this.deviceID = deviceID;
    this.numGroups = numGroups;
    this.changedControls = changedControls;
    this.enabledControls = enabledControls;
    this.enabledControlChanges = enabledControlChanges;
    this.keycode = keycode;
    this.eventType = eventType;
    this.requestMajor = requestMajor;
    this.requestMinor = requestMinor;
  }

  @java.lang.SuppressWarnings("all")
  public static ControlsNotifyEvent.ControlsNotifyEventBuilder builder() {
    return new ControlsNotifyEvent.ControlsNotifyEventBuilder();
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
  public byte getNumGroups() {
    return this.numGroups;
  }

  @java.lang.SuppressWarnings("all")
  public int getChangedControls() {
    return this.changedControls;
  }

  @java.lang.SuppressWarnings("all")
  public int getEnabledControls() {
    return this.enabledControls;
  }

  @java.lang.SuppressWarnings("all")
  public int getEnabledControlChanges() {
    return this.enabledControlChanges;
  }

  @java.lang.SuppressWarnings("all")
  public byte getKeycode() {
    return this.keycode;
  }

  @java.lang.SuppressWarnings("all")
  public byte getEventType() {
    return this.eventType;
  }

  @java.lang.SuppressWarnings("all")
  public byte getRequestMajor() {
    return this.requestMajor;
  }

  @java.lang.SuppressWarnings("all")
  public byte getRequestMinor() {
    return this.requestMinor;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ControlsNotifyEvent)) return false;
    final ControlsNotifyEvent other = (ControlsNotifyEvent) o;
    if (this.getFirstEventOffset() != other.getFirstEventOffset()) return false;
    if (this.isSentEvent() != other.isSentEvent()) return false;
    if (this.getXkbType() != other.getXkbType()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getTime() != other.getTime()) return false;
    if (this.getDeviceID() != other.getDeviceID()) return false;
    if (this.getNumGroups() != other.getNumGroups()) return false;
    if (this.getChangedControls() != other.getChangedControls()) return false;
    if (this.getEnabledControls() != other.getEnabledControls()) return false;
    if (this.getEnabledControlChanges() != other.getEnabledControlChanges()) return false;
    if (this.getKeycode() != other.getKeycode()) return false;
    if (this.getEventType() != other.getEventType()) return false;
    if (this.getRequestMajor() != other.getRequestMajor()) return false;
    if (this.getRequestMinor() != other.getRequestMinor()) return false;
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
    result = result * PRIME + this.getNumGroups();
    result = result * PRIME + this.getChangedControls();
    result = result * PRIME + this.getEnabledControls();
    result = result * PRIME + this.getEnabledControlChanges();
    result = result * PRIME + this.getKeycode();
    result = result * PRIME + this.getEventType();
    result = result * PRIME + this.getRequestMajor();
    result = result * PRIME + this.getRequestMinor();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ControlsNotifyEvent(firstEventOffset=" + this.getFirstEventOffset() + ", sentEvent=" + this.isSentEvent() + ", xkbType=" + this.getXkbType() + ", sequenceNumber=" + this.getSequenceNumber() + ", time=" + this.getTime() + ", deviceID=" + this.getDeviceID() + ", numGroups=" + this.getNumGroups() + ", changedControls=" + this.getChangedControls() + ", enabledControls=" + this.getEnabledControls() + ", enabledControlChanges=" + this.getEnabledControlChanges() + ", keycode=" + this.getKeycode() + ", eventType=" + this.getEventType() + ", requestMajor=" + this.getRequestMajor() + ", requestMinor=" + this.getRequestMinor() + ")";
  }
}
