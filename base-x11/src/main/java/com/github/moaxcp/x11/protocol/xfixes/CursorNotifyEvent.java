package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;

public final class CursorNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "xfixes";
  public static final byte NUMBER = 1;
  private final byte firstEventOffset;
  private final boolean sentEvent;
  private final byte subtype;
  private final short sequenceNumber;
  private final int window;
  private final int cursorSerial;
  private final int timestamp;
  private final int name;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static CursorNotifyEvent readCursorNotifyEvent(byte firstEventOffset, boolean sentEvent, X11Input in) throws IOException {
    CursorNotifyEvent.CursorNotifyEventBuilder javaBuilder = CursorNotifyEvent.builder();
    byte subtype = in.readCard8();
    short sequenceNumber = in.readCard16();
    int window = in.readCard32();
    int cursorSerial = in.readCard32();
    int timestamp = in.readCard32();
    int name = in.readCard32();
    byte[] pad7 = in.readPad(12);
    javaBuilder.subtype(subtype);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.window(window);
    javaBuilder.cursorSerial(cursorSerial);
    javaBuilder.timestamp(timestamp);
    javaBuilder.name(name);
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
    out.writeCard32(cursorSerial);
    out.writeCard32(timestamp);
    out.writeCard32(name);
    out.writePad(12);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CursorNotifyEventBuilder {
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
    private int cursorSerial;
    @java.lang.SuppressWarnings("all")
    private int timestamp;
    @java.lang.SuppressWarnings("all")
    private int name;

    public CursorNotifyEvent.CursorNotifyEventBuilder subtype(CursorNotify subtype) {
      this.subtype = (byte) subtype.getValue();
      return this;
    }

    public CursorNotifyEvent.CursorNotifyEventBuilder subtype(byte subtype) {
      this.subtype = subtype;
      return this;
    }

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    CursorNotifyEventBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CursorNotifyEvent.CursorNotifyEventBuilder firstEventOffset(final byte firstEventOffset) {
      this.firstEventOffset = firstEventOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CursorNotifyEvent.CursorNotifyEventBuilder sentEvent(final boolean sentEvent) {
      this.sentEvent = sentEvent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CursorNotifyEvent.CursorNotifyEventBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CursorNotifyEvent.CursorNotifyEventBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CursorNotifyEvent.CursorNotifyEventBuilder cursorSerial(final int cursorSerial) {
      this.cursorSerial = cursorSerial;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CursorNotifyEvent.CursorNotifyEventBuilder timestamp(final int timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CursorNotifyEvent.CursorNotifyEventBuilder name(final int name) {
      this.name = name;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CursorNotifyEvent build() {
      return new CursorNotifyEvent(this.firstEventOffset, this.sentEvent, this.subtype, this.sequenceNumber, this.window, this.cursorSerial, this.timestamp, this.name);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CursorNotifyEvent.CursorNotifyEventBuilder(firstEventOffset=" + this.firstEventOffset + ", sentEvent=" + this.sentEvent + ", subtype=" + this.subtype + ", sequenceNumber=" + this.sequenceNumber + ", window=" + this.window + ", cursorSerial=" + this.cursorSerial + ", timestamp=" + this.timestamp + ", name=" + this.name + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CursorNotifyEvent(final byte firstEventOffset, final boolean sentEvent, final byte subtype, final short sequenceNumber, final int window, final int cursorSerial, final int timestamp, final int name) {
    this.firstEventOffset = firstEventOffset;
    this.sentEvent = sentEvent;
    this.subtype = subtype;
    this.sequenceNumber = sequenceNumber;
    this.window = window;
    this.cursorSerial = cursorSerial;
    this.timestamp = timestamp;
    this.name = name;
  }

  @java.lang.SuppressWarnings("all")
  public static CursorNotifyEvent.CursorNotifyEventBuilder builder() {
    return new CursorNotifyEvent.CursorNotifyEventBuilder();
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
  public int getCursorSerial() {
    return this.cursorSerial;
  }

  @java.lang.SuppressWarnings("all")
  public int getTimestamp() {
    return this.timestamp;
  }

  @java.lang.SuppressWarnings("all")
  public int getName() {
    return this.name;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CursorNotifyEvent)) return false;
    final CursorNotifyEvent other = (CursorNotifyEvent) o;
    if (this.getFirstEventOffset() != other.getFirstEventOffset()) return false;
    if (this.isSentEvent() != other.isSentEvent()) return false;
    if (this.getSubtype() != other.getSubtype()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getCursorSerial() != other.getCursorSerial()) return false;
    if (this.getTimestamp() != other.getTimestamp()) return false;
    if (this.getName() != other.getName()) return false;
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
    result = result * PRIME + this.getCursorSerial();
    result = result * PRIME + this.getTimestamp();
    result = result * PRIME + this.getName();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CursorNotifyEvent(firstEventOffset=" + this.getFirstEventOffset() + ", sentEvent=" + this.isSentEvent() + ", subtype=" + this.getSubtype() + ", sequenceNumber=" + this.getSequenceNumber() + ", window=" + this.getWindow() + ", cursorSerial=" + this.getCursorSerial() + ", timestamp=" + this.getTimestamp() + ", name=" + this.getName() + ")";
  }
}
