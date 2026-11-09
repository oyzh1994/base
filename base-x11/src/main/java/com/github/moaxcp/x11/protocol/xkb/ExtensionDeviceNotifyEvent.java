package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;

public final class ExtensionDeviceNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "xkb";
  public static final byte NUMBER = 11;
  private final byte firstEventOffset;
  private final boolean sentEvent;
  private final byte xkbType;
  private final short sequenceNumber;
  private final int time;
  private final byte deviceID;
  private final short reason;
  private final short ledClass;
  private final short ledID;
  private final int ledsDefined;
  private final int ledState;
  private final byte firstButton;
  private final byte nButtons;
  private final short supported;
  private final short unsupported;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static ExtensionDeviceNotifyEvent readExtensionDeviceNotifyEvent(byte firstEventOffset, boolean sentEvent, X11Input in) throws IOException {
    ExtensionDeviceNotifyEvent.ExtensionDeviceNotifyEventBuilder javaBuilder = ExtensionDeviceNotifyEvent.builder();
    byte xkbType = in.readCard8();
    short sequenceNumber = in.readCard16();
    int time = in.readCard32();
    byte deviceID = in.readCard8();
    byte[] pad5 = in.readPad(1);
    short reason = in.readCard16();
    short ledClass = in.readCard16();
    short ledID = in.readCard16();
    int ledsDefined = in.readCard32();
    int ledState = in.readCard32();
    byte firstButton = in.readCard8();
    byte nButtons = in.readCard8();
    short supported = in.readCard16();
    short unsupported = in.readCard16();
    byte[] pad15 = in.readPad(2);
    javaBuilder.xkbType(xkbType);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.time(time);
    javaBuilder.deviceID(deviceID);
    javaBuilder.reason(reason);
    javaBuilder.ledClass(ledClass);
    javaBuilder.ledID(ledID);
    javaBuilder.ledsDefined(ledsDefined);
    javaBuilder.ledState(ledState);
    javaBuilder.firstButton(firstButton);
    javaBuilder.nButtons(nButtons);
    javaBuilder.supported(supported);
    javaBuilder.unsupported(unsupported);
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
    out.writePad(1);
    out.writeCard16(reason);
    out.writeCard16(ledClass);
    out.writeCard16(ledID);
    out.writeCard32(ledsDefined);
    out.writeCard32(ledState);
    out.writeCard8(firstButton);
    out.writeCard8(nButtons);
    out.writeCard16(supported);
    out.writeCard16(unsupported);
    out.writePad(2);
  }

  public boolean isReasonEnabled(XIFeature... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (XIFeature m : maskEnums) {
      if (!m.isEnabled(reason)) {
        return false;
      }
    }
    return true;
  }

  public boolean isSupportedEnabled(XIFeature... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (XIFeature m : maskEnums) {
      if (!m.isEnabled(supported)) {
        return false;
      }
    }
    return true;
  }

  public boolean isUnsupportedEnabled(XIFeature... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (XIFeature m : maskEnums) {
      if (!m.isEnabled(unsupported)) {
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


  public static class ExtensionDeviceNotifyEventBuilder {
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
    private short reason;
    @java.lang.SuppressWarnings("all")
    private short ledClass;
    @java.lang.SuppressWarnings("all")
    private short ledID;
    @java.lang.SuppressWarnings("all")
    private int ledsDefined;
    @java.lang.SuppressWarnings("all")
    private int ledState;
    @java.lang.SuppressWarnings("all")
    private byte firstButton;
    @java.lang.SuppressWarnings("all")
    private byte nButtons;
    @java.lang.SuppressWarnings("all")
    private short supported;
    @java.lang.SuppressWarnings("all")
    private short unsupported;

    public boolean isReasonEnabled(XIFeature... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (XIFeature m : maskEnums) {
        if (!m.isEnabled(reason)) {
          return false;
        }
      }
      return true;
    }

    public ExtensionDeviceNotifyEvent.ExtensionDeviceNotifyEventBuilder reasonEnable(XIFeature... maskEnums) {
      for (XIFeature m : maskEnums) {
        reason((short) m.enableFor(reason));
      }
      return this;
    }

    public ExtensionDeviceNotifyEvent.ExtensionDeviceNotifyEventBuilder reasonDisable(XIFeature... maskEnums) {
      for (XIFeature m : maskEnums) {
        reason((short) m.disableFor(reason));
      }
      return this;
    }

    public ExtensionDeviceNotifyEvent.ExtensionDeviceNotifyEventBuilder ledClass(LedClassResult ledClass) {
      this.ledClass = (short) ledClass.getValue();
      return this;
    }

    public ExtensionDeviceNotifyEvent.ExtensionDeviceNotifyEventBuilder ledClass(short ledClass) {
      this.ledClass = ledClass;
      return this;
    }

    public boolean isSupportedEnabled(XIFeature... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (XIFeature m : maskEnums) {
        if (!m.isEnabled(supported)) {
          return false;
        }
      }
      return true;
    }

    public ExtensionDeviceNotifyEvent.ExtensionDeviceNotifyEventBuilder supportedEnable(XIFeature... maskEnums) {
      for (XIFeature m : maskEnums) {
        supported((short) m.enableFor(supported));
      }
      return this;
    }

    public ExtensionDeviceNotifyEvent.ExtensionDeviceNotifyEventBuilder supportedDisable(XIFeature... maskEnums) {
      for (XIFeature m : maskEnums) {
        supported((short) m.disableFor(supported));
      }
      return this;
    }

    public boolean isUnsupportedEnabled(XIFeature... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (XIFeature m : maskEnums) {
        if (!m.isEnabled(unsupported)) {
          return false;
        }
      }
      return true;
    }

    public ExtensionDeviceNotifyEvent.ExtensionDeviceNotifyEventBuilder unsupportedEnable(XIFeature... maskEnums) {
      for (XIFeature m : maskEnums) {
        unsupported((short) m.enableFor(unsupported));
      }
      return this;
    }

    public ExtensionDeviceNotifyEvent.ExtensionDeviceNotifyEventBuilder unsupportedDisable(XIFeature... maskEnums) {
      for (XIFeature m : maskEnums) {
        unsupported((short) m.disableFor(unsupported));
      }
      return this;
    }

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    ExtensionDeviceNotifyEventBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ExtensionDeviceNotifyEvent.ExtensionDeviceNotifyEventBuilder firstEventOffset(final byte firstEventOffset) {
      this.firstEventOffset = firstEventOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ExtensionDeviceNotifyEvent.ExtensionDeviceNotifyEventBuilder sentEvent(final boolean sentEvent) {
      this.sentEvent = sentEvent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ExtensionDeviceNotifyEvent.ExtensionDeviceNotifyEventBuilder xkbType(final byte xkbType) {
      this.xkbType = xkbType;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ExtensionDeviceNotifyEvent.ExtensionDeviceNotifyEventBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ExtensionDeviceNotifyEvent.ExtensionDeviceNotifyEventBuilder time(final int time) {
      this.time = time;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ExtensionDeviceNotifyEvent.ExtensionDeviceNotifyEventBuilder deviceID(final byte deviceID) {
      this.deviceID = deviceID;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ExtensionDeviceNotifyEvent.ExtensionDeviceNotifyEventBuilder reason(final short reason) {
      this.reason = reason;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ExtensionDeviceNotifyEvent.ExtensionDeviceNotifyEventBuilder ledID(final short ledID) {
      this.ledID = ledID;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ExtensionDeviceNotifyEvent.ExtensionDeviceNotifyEventBuilder ledsDefined(final int ledsDefined) {
      this.ledsDefined = ledsDefined;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ExtensionDeviceNotifyEvent.ExtensionDeviceNotifyEventBuilder ledState(final int ledState) {
      this.ledState = ledState;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ExtensionDeviceNotifyEvent.ExtensionDeviceNotifyEventBuilder firstButton(final byte firstButton) {
      this.firstButton = firstButton;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ExtensionDeviceNotifyEvent.ExtensionDeviceNotifyEventBuilder nButtons(final byte nButtons) {
      this.nButtons = nButtons;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ExtensionDeviceNotifyEvent.ExtensionDeviceNotifyEventBuilder supported(final short supported) {
      this.supported = supported;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ExtensionDeviceNotifyEvent.ExtensionDeviceNotifyEventBuilder unsupported(final short unsupported) {
      this.unsupported = unsupported;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ExtensionDeviceNotifyEvent build() {
      return new ExtensionDeviceNotifyEvent(this.firstEventOffset, this.sentEvent, this.xkbType, this.sequenceNumber, this.time, this.deviceID, this.reason, this.ledClass, this.ledID, this.ledsDefined, this.ledState, this.firstButton, this.nButtons, this.supported, this.unsupported);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ExtensionDeviceNotifyEvent.ExtensionDeviceNotifyEventBuilder(firstEventOffset=" + this.firstEventOffset + ", sentEvent=" + this.sentEvent + ", xkbType=" + this.xkbType + ", sequenceNumber=" + this.sequenceNumber + ", time=" + this.time + ", deviceID=" + this.deviceID + ", reason=" + this.reason + ", ledClass=" + this.ledClass + ", ledID=" + this.ledID + ", ledsDefined=" + this.ledsDefined + ", ledState=" + this.ledState + ", firstButton=" + this.firstButton + ", nButtons=" + this.nButtons + ", supported=" + this.supported + ", unsupported=" + this.unsupported + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ExtensionDeviceNotifyEvent(final byte firstEventOffset, final boolean sentEvent, final byte xkbType, final short sequenceNumber, final int time, final byte deviceID, final short reason, final short ledClass, final short ledID, final int ledsDefined, final int ledState, final byte firstButton, final byte nButtons, final short supported, final short unsupported) {
    this.firstEventOffset = firstEventOffset;
    this.sentEvent = sentEvent;
    this.xkbType = xkbType;
    this.sequenceNumber = sequenceNumber;
    this.time = time;
    this.deviceID = deviceID;
    this.reason = reason;
    this.ledClass = ledClass;
    this.ledID = ledID;
    this.ledsDefined = ledsDefined;
    this.ledState = ledState;
    this.firstButton = firstButton;
    this.nButtons = nButtons;
    this.supported = supported;
    this.unsupported = unsupported;
  }

  @java.lang.SuppressWarnings("all")
  public static ExtensionDeviceNotifyEvent.ExtensionDeviceNotifyEventBuilder builder() {
    return new ExtensionDeviceNotifyEvent.ExtensionDeviceNotifyEventBuilder();
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
  public short getReason() {
    return this.reason;
  }

  @java.lang.SuppressWarnings("all")
  public short getLedClass() {
    return this.ledClass;
  }

  @java.lang.SuppressWarnings("all")
  public short getLedID() {
    return this.ledID;
  }

  @java.lang.SuppressWarnings("all")
  public int getLedsDefined() {
    return this.ledsDefined;
  }

  @java.lang.SuppressWarnings("all")
  public int getLedState() {
    return this.ledState;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFirstButton() {
    return this.firstButton;
  }

  @java.lang.SuppressWarnings("all")
  public byte getNButtons() {
    return this.nButtons;
  }

  @java.lang.SuppressWarnings("all")
  public short getSupported() {
    return this.supported;
  }

  @java.lang.SuppressWarnings("all")
  public short getUnsupported() {
    return this.unsupported;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ExtensionDeviceNotifyEvent)) return false;
    final ExtensionDeviceNotifyEvent other = (ExtensionDeviceNotifyEvent) o;
    if (this.getFirstEventOffset() != other.getFirstEventOffset()) return false;
    if (this.isSentEvent() != other.isSentEvent()) return false;
    if (this.getXkbType() != other.getXkbType()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getTime() != other.getTime()) return false;
    if (this.getDeviceID() != other.getDeviceID()) return false;
    if (this.getReason() != other.getReason()) return false;
    if (this.getLedClass() != other.getLedClass()) return false;
    if (this.getLedID() != other.getLedID()) return false;
    if (this.getLedsDefined() != other.getLedsDefined()) return false;
    if (this.getLedState() != other.getLedState()) return false;
    if (this.getFirstButton() != other.getFirstButton()) return false;
    if (this.getNButtons() != other.getNButtons()) return false;
    if (this.getSupported() != other.getSupported()) return false;
    if (this.getUnsupported() != other.getUnsupported()) return false;
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
    result = result * PRIME + this.getReason();
    result = result * PRIME + this.getLedClass();
    result = result * PRIME + this.getLedID();
    result = result * PRIME + this.getLedsDefined();
    result = result * PRIME + this.getLedState();
    result = result * PRIME + this.getFirstButton();
    result = result * PRIME + this.getNButtons();
    result = result * PRIME + this.getSupported();
    result = result * PRIME + this.getUnsupported();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ExtensionDeviceNotifyEvent(firstEventOffset=" + this.getFirstEventOffset() + ", sentEvent=" + this.isSentEvent() + ", xkbType=" + this.getXkbType() + ", sequenceNumber=" + this.getSequenceNumber() + ", time=" + this.getTime() + ", deviceID=" + this.getDeviceID() + ", reason=" + this.getReason() + ", ledClass=" + this.getLedClass() + ", ledID=" + this.getLedID() + ", ledsDefined=" + this.getLedsDefined() + ", ledState=" + this.getLedState() + ", firstButton=" + this.getFirstButton() + ", nButtons=" + this.getNButtons() + ", supported=" + this.getSupported() + ", unsupported=" + this.getUnsupported() + ")";
  }
}
