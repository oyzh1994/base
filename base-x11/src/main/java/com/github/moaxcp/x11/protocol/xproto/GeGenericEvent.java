package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XGenericEvent;
import java.io.IOException;

public final class GeGenericEvent implements XGenericEvent {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte NUMBER = 35;
  private final byte firstEventOffset;
  private final boolean sentEvent;
  private final byte extension;
  private final short sequenceNumber;
  private final short eventType;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static GeGenericEvent readGeGenericEvent(byte firstEventOffset, boolean sentEvent, byte extension, short sequenceNumber, int length, short eventType, X11Input in) throws IOException {
    GeGenericEvent.GeGenericEventBuilder javaBuilder = GeGenericEvent.builder();
    byte[] pad5 = in.readPad(22);
    javaBuilder.extension(extension);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.eventType(eventType);
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
    out.writePad(22);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GeGenericEventBuilder {
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

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    GeGenericEventBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GeGenericEvent.GeGenericEventBuilder firstEventOffset(final byte firstEventOffset) {
      this.firstEventOffset = firstEventOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GeGenericEvent.GeGenericEventBuilder sentEvent(final boolean sentEvent) {
      this.sentEvent = sentEvent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GeGenericEvent.GeGenericEventBuilder extension(final byte extension) {
      this.extension = extension;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GeGenericEvent.GeGenericEventBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GeGenericEvent.GeGenericEventBuilder eventType(final short eventType) {
      this.eventType = eventType;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GeGenericEvent build() {
      return new GeGenericEvent(this.firstEventOffset, this.sentEvent, this.extension, this.sequenceNumber, this.eventType);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GeGenericEvent.GeGenericEventBuilder(firstEventOffset=" + this.firstEventOffset + ", sentEvent=" + this.sentEvent + ", extension=" + this.extension + ", sequenceNumber=" + this.sequenceNumber + ", eventType=" + this.eventType + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GeGenericEvent(final byte firstEventOffset, final boolean sentEvent, final byte extension, final short sequenceNumber, final short eventType) {
    this.firstEventOffset = firstEventOffset;
    this.sentEvent = sentEvent;
    this.extension = extension;
    this.sequenceNumber = sequenceNumber;
    this.eventType = eventType;
  }

  @java.lang.SuppressWarnings("all")
  public static GeGenericEvent.GeGenericEventBuilder builder() {
    return new GeGenericEvent.GeGenericEventBuilder();
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

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GeGenericEvent)) return false;
    final GeGenericEvent other = (GeGenericEvent) o;
    if (this.getFirstEventOffset() != other.getFirstEventOffset()) return false;
    if (this.isSentEvent() != other.isSentEvent()) return false;
    if (this.getExtension() != other.getExtension()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getEventType() != other.getEventType()) return false;
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
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GeGenericEvent(firstEventOffset=" + this.getFirstEventOffset() + ", sentEvent=" + this.isSentEvent() + ", extension=" + this.getExtension() + ", sequenceNumber=" + this.getSequenceNumber() + ", eventType=" + this.getEventType() + ")";
  }
}
