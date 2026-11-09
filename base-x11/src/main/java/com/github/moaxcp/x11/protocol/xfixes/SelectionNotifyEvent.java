package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;

public final class SelectionNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "xfixes";
  public static final byte NUMBER = 0;
  private final byte firstEventOffset;
  private final boolean sentEvent;
  private final byte subtype;
  private final short sequenceNumber;
  private final int window;
  private final int owner;
  private final int selection;
  private final int timestamp;
  private final int selectionTimestamp;

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
    byte subtype = in.readCard8();
    short sequenceNumber = in.readCard16();
    int window = in.readCard32();
    int owner = in.readCard32();
    int selection = in.readCard32();
    int timestamp = in.readCard32();
    int selectionTimestamp = in.readCard32();
    byte[] pad8 = in.readPad(8);
    javaBuilder.subtype(subtype);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.window(window);
    javaBuilder.owner(owner);
    javaBuilder.selection(selection);
    javaBuilder.timestamp(timestamp);
    javaBuilder.selectionTimestamp(selectionTimestamp);
    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (128 | getResponseCode()) : getResponseCode());
    out.writeCard8(subtype);
    out.writeCard16(sequenceNumber);
    out.writeCard32(window);
    out.writeCard32(owner);
    out.writeCard32(selection);
    out.writeCard32(timestamp);
    out.writeCard32(selectionTimestamp);
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
    private byte subtype;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private int owner;
    @java.lang.SuppressWarnings("all")
    private int selection;
    @java.lang.SuppressWarnings("all")
    private int timestamp;
    @java.lang.SuppressWarnings("all")
    private int selectionTimestamp;

    public SelectionNotifyEvent.SelectionNotifyEventBuilder subtype(SelectionEvent subtype) {
      this.subtype = (byte) subtype.getValue();
      return this;
    }

    public SelectionNotifyEvent.SelectionNotifyEventBuilder subtype(byte subtype) {
      this.subtype = subtype;
      return this;
    }

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
    public SelectionNotifyEvent.SelectionNotifyEventBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SelectionNotifyEvent.SelectionNotifyEventBuilder owner(final int owner) {
      this.owner = owner;
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
    public SelectionNotifyEvent.SelectionNotifyEventBuilder timestamp(final int timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SelectionNotifyEvent.SelectionNotifyEventBuilder selectionTimestamp(final int selectionTimestamp) {
      this.selectionTimestamp = selectionTimestamp;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SelectionNotifyEvent build() {
      return new SelectionNotifyEvent(this.firstEventOffset, this.sentEvent, this.subtype, this.sequenceNumber, this.window, this.owner, this.selection, this.timestamp, this.selectionTimestamp);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SelectionNotifyEvent.SelectionNotifyEventBuilder(firstEventOffset=" + this.firstEventOffset + ", sentEvent=" + this.sentEvent + ", subtype=" + this.subtype + ", sequenceNumber=" + this.sequenceNumber + ", window=" + this.window + ", owner=" + this.owner + ", selection=" + this.selection + ", timestamp=" + this.timestamp + ", selectionTimestamp=" + this.selectionTimestamp + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SelectionNotifyEvent(final byte firstEventOffset, final boolean sentEvent, final byte subtype, final short sequenceNumber, final int window, final int owner, final int selection, final int timestamp, final int selectionTimestamp) {
    this.firstEventOffset = firstEventOffset;
    this.sentEvent = sentEvent;
    this.subtype = subtype;
    this.sequenceNumber = sequenceNumber;
    this.window = window;
    this.owner = owner;
    this.selection = selection;
    this.timestamp = timestamp;
    this.selectionTimestamp = selectionTimestamp;
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
  public byte getSubtype() {
    return this.subtype;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.SuppressWarnings("all")
  public int getOwner() {
    return this.owner;
  }

  @java.lang.SuppressWarnings("all")
  public int getSelection() {
    return this.selection;
  }

  @java.lang.SuppressWarnings("all")
  public int getTimestamp() {
    return this.timestamp;
  }

  @java.lang.SuppressWarnings("all")
  public int getSelectionTimestamp() {
    return this.selectionTimestamp;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SelectionNotifyEvent)) return false;
    final SelectionNotifyEvent other = (SelectionNotifyEvent) o;
    if (this.getFirstEventOffset() != other.getFirstEventOffset()) return false;
    if (this.isSentEvent() != other.isSentEvent()) return false;
    if (this.getSubtype() != other.getSubtype()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getOwner() != other.getOwner()) return false;
    if (this.getSelection() != other.getSelection()) return false;
    if (this.getTimestamp() != other.getTimestamp()) return false;
    if (this.getSelectionTimestamp() != other.getSelectionTimestamp()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFirstEventOffset();
    result = result * PRIME + (this.isSentEvent() ? 79 : 97);
    result = result * PRIME + this.getSubtype();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getOwner();
    result = result * PRIME + this.getSelection();
    result = result * PRIME + this.getTimestamp();
    result = result * PRIME + this.getSelectionTimestamp();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SelectionNotifyEvent(firstEventOffset=" + this.getFirstEventOffset() + ", sentEvent=" + this.isSentEvent() + ", subtype=" + this.getSubtype() + ", sequenceNumber=" + this.getSequenceNumber() + ", window=" + this.getWindow() + ", owner=" + this.getOwner() + ", selection=" + this.getSelection() + ", timestamp=" + this.getTimestamp() + ", selectionTimestamp=" + this.getSelectionTimestamp() + ")";
  }
}
