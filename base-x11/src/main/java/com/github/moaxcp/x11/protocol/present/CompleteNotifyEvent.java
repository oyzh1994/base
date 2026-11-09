package com.github.moaxcp.x11.protocol.present;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XGenericEvent;
import java.io.IOException;

public final class CompleteNotifyEvent implements XGenericEvent {
  public static final String PLUGIN_NAME = "present";
  public static final byte NUMBER = 35;
  private final byte firstEventOffset;
  private final boolean sentEvent;
  private final byte extension;
  private final short sequenceNumber;
  private final short eventType;
  private final byte kind;
  private final byte mode;
  private final int event;
  private final int window;
  private final int serial;
  private final long ust;
  private final long msc;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static CompleteNotifyEvent readCompleteNotifyEvent(byte firstEventOffset, boolean sentEvent, byte extension, short sequenceNumber, int length, short eventType, X11Input in) throws IOException {
    CompleteNotifyEvent.CompleteNotifyEventBuilder javaBuilder = CompleteNotifyEvent.builder();
    byte kind = in.readCard8();
    byte mode = in.readCard8();
    int event = in.readCard32();
    int window = in.readCard32();
    int serial = in.readCard32();
    long ust = in.readCard64();
    long msc = in.readCard64();
    javaBuilder.extension(extension);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.eventType(eventType);
    javaBuilder.kind(kind);
    javaBuilder.mode(mode);
    javaBuilder.event(event);
    javaBuilder.window(window);
    javaBuilder.serial(serial);
    javaBuilder.ust(ust);
    javaBuilder.msc(msc);
    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (128 | getResponseCode()) : getResponseCode());
    out.writeCard8(extension);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength() - 32);
    out.writeCard16(eventType);
    out.writeCard8(kind);
    out.writeCard8(mode);
    out.writeCard32(event);
    out.writeCard32(window);
    out.writeCard32(serial);
    out.writeCard64(ust);
    out.writeCard64(msc);
  }

  @Override
  public int getSize() {
    return 40;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CompleteNotifyEventBuilder {
    @java.lang.SuppressWarnings("all")
    private byte firstEventOffset;
    @java.lang.SuppressWarnings("all")
    private boolean sentEvent;
    @java.lang.SuppressWarnings("all")
    private byte extension;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private short eventType;
    @java.lang.SuppressWarnings("all")
    private byte kind;
    @java.lang.SuppressWarnings("all")
    private byte mode;
    @java.lang.SuppressWarnings("all")
    private int event;
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private int serial;
    @java.lang.SuppressWarnings("all")
    private long ust;
    @java.lang.SuppressWarnings("all")
    private long msc;

    public CompleteNotifyEvent.CompleteNotifyEventBuilder kind(CompleteKind kind) {
      this.kind = (byte) kind.getValue();
      return this;
    }

    public CompleteNotifyEvent.CompleteNotifyEventBuilder kind(byte kind) {
      this.kind = kind;
      return this;
    }

    public CompleteNotifyEvent.CompleteNotifyEventBuilder mode(CompleteMode mode) {
      this.mode = (byte) mode.getValue();
      return this;
    }

    public CompleteNotifyEvent.CompleteNotifyEventBuilder mode(byte mode) {
      this.mode = mode;
      return this;
    }

    public int getSize() {
      return 40;
    }

    @java.lang.SuppressWarnings("all")
    CompleteNotifyEventBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CompleteNotifyEvent.CompleteNotifyEventBuilder firstEventOffset(final byte firstEventOffset) {
      this.firstEventOffset = firstEventOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CompleteNotifyEvent.CompleteNotifyEventBuilder sentEvent(final boolean sentEvent) {
      this.sentEvent = sentEvent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CompleteNotifyEvent.CompleteNotifyEventBuilder extension(final byte extension) {
      this.extension = extension;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CompleteNotifyEvent.CompleteNotifyEventBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CompleteNotifyEvent.CompleteNotifyEventBuilder eventType(final short eventType) {
      this.eventType = eventType;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CompleteNotifyEvent.CompleteNotifyEventBuilder event(final int event) {
      this.event = event;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CompleteNotifyEvent.CompleteNotifyEventBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CompleteNotifyEvent.CompleteNotifyEventBuilder serial(final int serial) {
      this.serial = serial;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CompleteNotifyEvent.CompleteNotifyEventBuilder ust(final long ust) {
      this.ust = ust;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CompleteNotifyEvent.CompleteNotifyEventBuilder msc(final long msc) {
      this.msc = msc;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CompleteNotifyEvent build() {
      return new CompleteNotifyEvent(this.firstEventOffset, this.sentEvent, this.extension, this.sequenceNumber, this.eventType, this.kind, this.mode, this.event, this.window, this.serial, this.ust, this.msc);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CompleteNotifyEvent.CompleteNotifyEventBuilder(firstEventOffset=" + this.firstEventOffset + ", sentEvent=" + this.sentEvent + ", extension=" + this.extension + ", sequenceNumber=" + this.sequenceNumber + ", eventType=" + this.eventType + ", kind=" + this.kind + ", mode=" + this.mode + ", event=" + this.event + ", window=" + this.window + ", serial=" + this.serial + ", ust=" + this.ust + ", msc=" + this.msc + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CompleteNotifyEvent(final byte firstEventOffset, final boolean sentEvent, final byte extension, final short sequenceNumber, final short eventType, final byte kind, final byte mode, final int event, final int window, final int serial, final long ust, final long msc) {
    this.firstEventOffset = firstEventOffset;
    this.sentEvent = sentEvent;
    this.extension = extension;
    this.sequenceNumber = sequenceNumber;
    this.eventType = eventType;
    this.kind = kind;
    this.mode = mode;
    this.event = event;
    this.window = window;
    this.serial = serial;
    this.ust = ust;
    this.msc = msc;
  }

  @java.lang.SuppressWarnings("all")
  public static CompleteNotifyEvent.CompleteNotifyEventBuilder builder() {
    return new CompleteNotifyEvent.CompleteNotifyEventBuilder();
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
  public byte getExtension() {
    return this.extension;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public short getEventType() {
    return this.eventType;
  }

  @java.lang.SuppressWarnings("all")
  public byte getKind() {
    return this.kind;
  }

  @java.lang.SuppressWarnings("all")
  public byte getMode() {
    return this.mode;
  }

  @java.lang.SuppressWarnings("all")
  public int getEvent() {
    return this.event;
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.SuppressWarnings("all")
  public int getSerial() {
    return this.serial;
  }

  @java.lang.SuppressWarnings("all")
  public long getUst() {
    return this.ust;
  }

  @java.lang.SuppressWarnings("all")
  public long getMsc() {
    return this.msc;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CompleteNotifyEvent)) return false;
    final CompleteNotifyEvent other = (CompleteNotifyEvent) o;
    if (this.getFirstEventOffset() != other.getFirstEventOffset()) return false;
    if (this.isSentEvent() != other.isSentEvent()) return false;
    if (this.getExtension() != other.getExtension()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getEventType() != other.getEventType()) return false;
    if (this.getKind() != other.getKind()) return false;
    if (this.getMode() != other.getMode()) return false;
    if (this.getEvent() != other.getEvent()) return false;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getSerial() != other.getSerial()) return false;
    if (this.getUst() != other.getUst()) return false;
    if (this.getMsc() != other.getMsc()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFirstEventOffset();
    result = result * PRIME + (this.isSentEvent() ? 79 : 97);
    result = result * PRIME + this.getExtension();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getEventType();
    result = result * PRIME + this.getKind();
    result = result * PRIME + this.getMode();
    result = result * PRIME + this.getEvent();
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getSerial();
    final long $ust = this.getUst();
    result = result * PRIME + (int) ($ust >>> 32 ^ $ust);
    final long $msc = this.getMsc();
    result = result * PRIME + (int) ($msc >>> 32 ^ $msc);
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CompleteNotifyEvent(firstEventOffset=" + this.getFirstEventOffset() + ", sentEvent=" + this.isSentEvent() + ", extension=" + this.getExtension() + ", sequenceNumber=" + this.getSequenceNumber() + ", eventType=" + this.getEventType() + ", kind=" + this.getKind() + ", mode=" + this.getMode() + ", event=" + this.getEvent() + ", window=" + this.getWindow() + ", serial=" + this.getSerial() + ", ust=" + this.getUst() + ", msc=" + this.getMsc() + ")";
  }
}
