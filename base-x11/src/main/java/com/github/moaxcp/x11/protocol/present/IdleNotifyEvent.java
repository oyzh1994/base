package com.github.moaxcp.x11.protocol.present;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XGenericEvent;
import java.io.IOException;

public final class IdleNotifyEvent implements XGenericEvent {
  public static final String PLUGIN_NAME = "present";
  public static final byte NUMBER = 35;
  private final byte firstEventOffset;
  private final boolean sentEvent;
  private final byte extension;
  private final short sequenceNumber;
  private final short eventType;
  private final int event;
  private final int window;
  private final int serial;
  private final int pixmap;
  private final int idleFence;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static IdleNotifyEvent readIdleNotifyEvent(byte firstEventOffset, boolean sentEvent, byte extension, short sequenceNumber, int length, short eventType, X11Input in) throws IOException {
    IdleNotifyEvent.IdleNotifyEventBuilder javaBuilder = IdleNotifyEvent.builder();
    byte[] pad5 = in.readPad(2);
    int event = in.readCard32();
    int window = in.readCard32();
    int serial = in.readCard32();
    int pixmap = in.readCard32();
    int idleFence = in.readCard32();
    javaBuilder.extension(extension);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.eventType(eventType);
    javaBuilder.event(event);
    javaBuilder.window(window);
    javaBuilder.serial(serial);
    javaBuilder.pixmap(pixmap);
    javaBuilder.idleFence(idleFence);
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
    out.writePad(2);
    out.writeCard32(event);
    out.writeCard32(window);
    out.writeCard32(serial);
    out.writeCard32(pixmap);
    out.writeCard32(idleFence);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class IdleNotifyEventBuilder {
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
    private int event;
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private int serial;
    @java.lang.SuppressWarnings("all")
    private int pixmap;
    @java.lang.SuppressWarnings("all")
    private int idleFence;

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    IdleNotifyEventBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public IdleNotifyEvent.IdleNotifyEventBuilder firstEventOffset(final byte firstEventOffset) {
      this.firstEventOffset = firstEventOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public IdleNotifyEvent.IdleNotifyEventBuilder sentEvent(final boolean sentEvent) {
      this.sentEvent = sentEvent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public IdleNotifyEvent.IdleNotifyEventBuilder extension(final byte extension) {
      this.extension = extension;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public IdleNotifyEvent.IdleNotifyEventBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public IdleNotifyEvent.IdleNotifyEventBuilder eventType(final short eventType) {
      this.eventType = eventType;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public IdleNotifyEvent.IdleNotifyEventBuilder event(final int event) {
      this.event = event;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public IdleNotifyEvent.IdleNotifyEventBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public IdleNotifyEvent.IdleNotifyEventBuilder serial(final int serial) {
      this.serial = serial;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public IdleNotifyEvent.IdleNotifyEventBuilder pixmap(final int pixmap) {
      this.pixmap = pixmap;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public IdleNotifyEvent.IdleNotifyEventBuilder idleFence(final int idleFence) {
      this.idleFence = idleFence;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public IdleNotifyEvent build() {
      return new IdleNotifyEvent(this.firstEventOffset, this.sentEvent, this.extension, this.sequenceNumber, this.eventType, this.event, this.window, this.serial, this.pixmap, this.idleFence);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "IdleNotifyEvent.IdleNotifyEventBuilder(firstEventOffset=" + this.firstEventOffset + ", sentEvent=" + this.sentEvent + ", extension=" + this.extension + ", sequenceNumber=" + this.sequenceNumber + ", eventType=" + this.eventType + ", event=" + this.event + ", window=" + this.window + ", serial=" + this.serial + ", pixmap=" + this.pixmap + ", idleFence=" + this.idleFence + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  IdleNotifyEvent(final byte firstEventOffset, final boolean sentEvent, final byte extension, final short sequenceNumber, final short eventType, final int event, final int window, final int serial, final int pixmap, final int idleFence) {
    this.firstEventOffset = firstEventOffset;
    this.sentEvent = sentEvent;
    this.extension = extension;
    this.sequenceNumber = sequenceNumber;
    this.eventType = eventType;
    this.event = event;
    this.window = window;
    this.serial = serial;
    this.pixmap = pixmap;
    this.idleFence = idleFence;
  }

  @java.lang.SuppressWarnings("all")
  public static IdleNotifyEvent.IdleNotifyEventBuilder builder() {
    return new IdleNotifyEvent.IdleNotifyEventBuilder();
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
  public int getPixmap() {
    return this.pixmap;
  }

  @java.lang.SuppressWarnings("all")
  public int getIdleFence() {
    return this.idleFence;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof IdleNotifyEvent)) return false;
    final IdleNotifyEvent other = (IdleNotifyEvent) o;
    if (this.getFirstEventOffset() != other.getFirstEventOffset()) return false;
    if (this.isSentEvent() != other.isSentEvent()) return false;
    if (this.getExtension() != other.getExtension()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getEventType() != other.getEventType()) return false;
    if (this.getEvent() != other.getEvent()) return false;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getSerial() != other.getSerial()) return false;
    if (this.getPixmap() != other.getPixmap()) return false;
    if (this.getIdleFence() != other.getIdleFence()) return false;
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
    result = result * PRIME + this.getEvent();
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getSerial();
    result = result * PRIME + this.getPixmap();
    result = result * PRIME + this.getIdleFence();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "IdleNotifyEvent(firstEventOffset=" + this.getFirstEventOffset() + ", sentEvent=" + this.isSentEvent() + ", extension=" + this.getExtension() + ", sequenceNumber=" + this.getSequenceNumber() + ", eventType=" + this.getEventType() + ", event=" + this.getEvent() + ", window=" + this.getWindow() + ", serial=" + this.getSerial() + ", pixmap=" + this.getPixmap() + ", idleFence=" + this.getIdleFence() + ")";
  }
}
