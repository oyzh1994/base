package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;

public final class SelectionNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte NUMBER = 31;
  private final byte firstEventOffset;
  private final boolean sentEvent;
  private final short sequenceNumber;
  private final int time;
  private final int requestor;
  private final int selection;
  private final int target;
  private final int property;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static SelectionNotifyEvent readSelectionNotifyEvent(byte firstEventOffset, boolean sentEvent, X11Input in) throws IOException {
    SelectionNotifyEvent.SelectionNotifyEventBuilder javaBuilder = SelectionNotifyEvent.builder();
    byte[] pad1 = in.readPad(1);
    short sequenceNumber = in.readCard16();
    int time = in.readCard32();
    int requestor = in.readCard32();
    int selection = in.readCard32();
    int target = in.readCard32();
    int property = in.readCard32();
    byte[] pad8 = in.readPad(8);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.time(time);
    javaBuilder.requestor(requestor);
    javaBuilder.selection(selection);
    javaBuilder.target(target);
    javaBuilder.property(property);
    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (128 | getResponseCode()) : getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(time);
    out.writeCard32(requestor);
    out.writeCard32(selection);
    out.writeCard32(target);
    out.writeCard32(property);
    out.writePad(8);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SelectionNotifyEventBuilder {
    @java.lang.SuppressWarnings("all")
    private byte firstEventOffset;
    @java.lang.SuppressWarnings("all")
    private boolean sentEvent;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int time;
    @java.lang.SuppressWarnings("all")
    private int requestor;
    @java.lang.SuppressWarnings("all")
    private int selection;
    @java.lang.SuppressWarnings("all")
    private int target;
    @java.lang.SuppressWarnings("all")
    private int property;

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    SelectionNotifyEventBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SelectionNotifyEvent.SelectionNotifyEventBuilder firstEventOffset(final byte firstEventOffset) {
      this.firstEventOffset = firstEventOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SelectionNotifyEvent.SelectionNotifyEventBuilder sentEvent(final boolean sentEvent) {
      this.sentEvent = sentEvent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SelectionNotifyEvent.SelectionNotifyEventBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SelectionNotifyEvent.SelectionNotifyEventBuilder time(final int time) {
      this.time = time;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SelectionNotifyEvent.SelectionNotifyEventBuilder requestor(final int requestor) {
      this.requestor = requestor;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SelectionNotifyEvent.SelectionNotifyEventBuilder selection(final int selection) {
      this.selection = selection;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SelectionNotifyEvent.SelectionNotifyEventBuilder target(final int target) {
      this.target = target;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SelectionNotifyEvent.SelectionNotifyEventBuilder property(final int property) {
      this.property = property;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SelectionNotifyEvent build() {
      return new SelectionNotifyEvent(this.firstEventOffset, this.sentEvent, this.sequenceNumber, this.time, this.requestor, this.selection, this.target, this.property);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SelectionNotifyEvent.SelectionNotifyEventBuilder(firstEventOffset=" + this.firstEventOffset + ", sentEvent=" + this.sentEvent + ", sequenceNumber=" + this.sequenceNumber + ", time=" + this.time + ", requestor=" + this.requestor + ", selection=" + this.selection + ", target=" + this.target + ", property=" + this.property + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SelectionNotifyEvent(final byte firstEventOffset, final boolean sentEvent, final short sequenceNumber, final int time, final int requestor, final int selection, final int target, final int property) {
    this.firstEventOffset = firstEventOffset;
    this.sentEvent = sentEvent;
    this.sequenceNumber = sequenceNumber;
    this.time = time;
    this.requestor = requestor;
    this.selection = selection;
    this.target = target;
    this.property = property;
  }

  @java.lang.SuppressWarnings("all")
  public static SelectionNotifyEvent.SelectionNotifyEventBuilder builder() {
    return new SelectionNotifyEvent.SelectionNotifyEventBuilder();
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
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getTime() {
    return this.time;
  }

  @java.lang.SuppressWarnings("all")
  public int getRequestor() {
    return this.requestor;
  }

  @java.lang.SuppressWarnings("all")
  public int getSelection() {
    return this.selection;
  }

  @java.lang.SuppressWarnings("all")
  public int getTarget() {
    return this.target;
  }

  @java.lang.SuppressWarnings("all")
  public int getProperty() {
    return this.property;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SelectionNotifyEvent)) return false;
    final SelectionNotifyEvent other = (SelectionNotifyEvent) o;
    if (this.getFirstEventOffset() != other.getFirstEventOffset()) return false;
    if (this.isSentEvent() != other.isSentEvent()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getTime() != other.getTime()) return false;
    if (this.getRequestor() != other.getRequestor()) return false;
    if (this.getSelection() != other.getSelection()) return false;
    if (this.getTarget() != other.getTarget()) return false;
    if (this.getProperty() != other.getProperty()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFirstEventOffset();
    result = result * PRIME + (this.isSentEvent() ? 79 : 97);
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getTime();
    result = result * PRIME + this.getRequestor();
    result = result * PRIME + this.getSelection();
    result = result * PRIME + this.getTarget();
    result = result * PRIME + this.getProperty();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SelectionNotifyEvent(firstEventOffset=" + this.getFirstEventOffset() + ", sentEvent=" + this.isSentEvent() + ", sequenceNumber=" + this.getSequenceNumber() + ", time=" + this.getTime() + ", requestor=" + this.getRequestor() + ", selection=" + this.getSelection() + ", target=" + this.getTarget() + ", property=" + this.getProperty() + ")";
  }
}
