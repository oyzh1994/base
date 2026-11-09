package com.github.moaxcp.x11.protocol.present;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;

public final class GenericEvent implements XEvent {
  public static final String PLUGIN_NAME = "present";
  public static final byte NUMBER = 0;
  private final byte firstEventOffset;
  private final boolean sentEvent;
  private final byte extension;
  private final short sequenceNumber;
  private final int length;
  private final short evtype;
  private final int event;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static GenericEvent readGenericEvent(byte firstEventOffset, boolean sentEvent, X11Input in) throws IOException {
    GenericEvent.GenericEventBuilder javaBuilder = GenericEvent.builder();
    byte extension = in.readCard8();
    short sequenceNumber = in.readCard16();
    int length = in.readCard32();
    short evtype = in.readCard16();
    byte[] pad5 = in.readPad(2);
    int event = in.readCard32();
    byte[] pad7 = in.readPad(16);
    javaBuilder.extension(extension);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.length(length);
    javaBuilder.evtype(evtype);
    javaBuilder.event(event);
    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (128 | getResponseCode()) : getResponseCode());
    out.writeCard8(extension);
    out.writeCard16(sequenceNumber);
    out.writeCard32(length);
    out.writeCard16(evtype);
    out.writePad(2);
    out.writeCard32(event);
    out.writePad(16);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GenericEventBuilder {
    @java.lang.SuppressWarnings("all")
    private byte firstEventOffset;
    @java.lang.SuppressWarnings("all")
    private boolean sentEvent;
    @java.lang.SuppressWarnings("all")
    private byte extension;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int length;
    @java.lang.SuppressWarnings("all")
    private short evtype;
    @java.lang.SuppressWarnings("all")
    private int event;

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    GenericEventBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GenericEvent.GenericEventBuilder firstEventOffset(final byte firstEventOffset) {
      this.firstEventOffset = firstEventOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GenericEvent.GenericEventBuilder sentEvent(final boolean sentEvent) {
      this.sentEvent = sentEvent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GenericEvent.GenericEventBuilder extension(final byte extension) {
      this.extension = extension;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GenericEvent.GenericEventBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GenericEvent.GenericEventBuilder length(final int length) {
      this.length = length;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GenericEvent.GenericEventBuilder evtype(final short evtype) {
      this.evtype = evtype;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GenericEvent.GenericEventBuilder event(final int event) {
      this.event = event;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GenericEvent build() {
      return new GenericEvent(this.firstEventOffset, this.sentEvent, this.extension, this.sequenceNumber, this.length, this.evtype, this.event);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GenericEvent.GenericEventBuilder(firstEventOffset=" + this.firstEventOffset + ", sentEvent=" + this.sentEvent + ", extension=" + this.extension + ", sequenceNumber=" + this.sequenceNumber + ", length=" + this.length + ", evtype=" + this.evtype + ", event=" + this.event + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GenericEvent(final byte firstEventOffset, final boolean sentEvent, final byte extension, final short sequenceNumber, final int length, final short evtype, final int event) {
    this.firstEventOffset = firstEventOffset;
    this.sentEvent = sentEvent;
    this.extension = extension;
    this.sequenceNumber = sequenceNumber;
    this.length = length;
    this.evtype = evtype;
    this.event = event;
  }

  @java.lang.SuppressWarnings("all")
  public static GenericEvent.GenericEventBuilder builder() {
    return new GenericEvent.GenericEventBuilder();
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
  public int getLength() {
    return this.length;
  }

  @java.lang.SuppressWarnings("all")
  public short getEvtype() {
    return this.evtype;
  }

  @java.lang.SuppressWarnings("all")
  public int getEvent() {
    return this.event;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GenericEvent)) return false;
    final GenericEvent other = (GenericEvent) o;
    if (this.getFirstEventOffset() != other.getFirstEventOffset()) return false;
    if (this.isSentEvent() != other.isSentEvent()) return false;
    if (this.getExtension() != other.getExtension()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getLength() != other.getLength()) return false;
    if (this.getEvtype() != other.getEvtype()) return false;
    if (this.getEvent() != other.getEvent()) return false;
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
    result = result * PRIME + this.getLength();
    result = result * PRIME + this.getEvtype();
    result = result * PRIME + this.getEvent();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GenericEvent(firstEventOffset=" + this.getFirstEventOffset() + ", sentEvent=" + this.isSentEvent() + ", extension=" + this.getExtension() + ", sequenceNumber=" + this.getSequenceNumber() + ", length=" + this.getLength() + ", evtype=" + this.getEvtype() + ", event=" + this.getEvent() + ")";
  }
}
