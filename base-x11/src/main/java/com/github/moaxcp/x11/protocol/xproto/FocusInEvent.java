package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;

public final class FocusInEvent implements XEvent {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte NUMBER = 9;
  private final byte firstEventOffset;
  private final boolean sentEvent;
  private final byte detail;
  private final short sequenceNumber;
  private final int event;
  private final byte mode;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static FocusInEvent readFocusInEvent(byte firstEventOffset, boolean sentEvent, X11Input in) throws IOException {
    FocusInEvent.FocusInEventBuilder javaBuilder = FocusInEvent.builder();
    byte detail = in.readByte();
    short sequenceNumber = in.readCard16();
    int event = in.readCard32();
    byte mode = in.readByte();
    byte[] pad5 = in.readPad(3);
    byte[] pad6 = in.readPad(20);
    javaBuilder.detail(detail);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.event(event);
    javaBuilder.mode(mode);
    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (128 | getResponseCode()) : getResponseCode());
    out.writeByte(detail);
    out.writeCard16(sequenceNumber);
    out.writeCard32(event);
    out.writeByte(mode);
    out.writePad(3);
    out.writePad(20);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class FocusInEventBuilder {
    @java.lang.SuppressWarnings("all")
    private byte firstEventOffset;
    @java.lang.SuppressWarnings("all")
    private boolean sentEvent;
    @java.lang.SuppressWarnings("all")
    private byte detail;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int event;
    @java.lang.SuppressWarnings("all")
    private byte mode;

    public FocusInEvent.FocusInEventBuilder detail(NotifyDetail detail) {
      this.detail = (byte) detail.getValue();
      return this;
    }

    public FocusInEvent.FocusInEventBuilder detail(byte detail) {
      this.detail = detail;
      return this;
    }

    public FocusInEvent.FocusInEventBuilder mode(NotifyMode mode) {
      this.mode = (byte) mode.getValue();
      return this;
    }

    public FocusInEvent.FocusInEventBuilder mode(byte mode) {
      this.mode = mode;
      return this;
    }

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    FocusInEventBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public FocusInEvent.FocusInEventBuilder firstEventOffset(final byte firstEventOffset) {
      this.firstEventOffset = firstEventOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public FocusInEvent.FocusInEventBuilder sentEvent(final boolean sentEvent) {
      this.sentEvent = sentEvent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public FocusInEvent.FocusInEventBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public FocusInEvent.FocusInEventBuilder event(final int event) {
      this.event = event;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public FocusInEvent build() {
      return new FocusInEvent(this.firstEventOffset, this.sentEvent, this.detail, this.sequenceNumber, this.event, this.mode);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "FocusInEvent.FocusInEventBuilder(firstEventOffset=" + this.firstEventOffset + ", sentEvent=" + this.sentEvent + ", detail=" + this.detail + ", sequenceNumber=" + this.sequenceNumber + ", event=" + this.event + ", mode=" + this.mode + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  FocusInEvent(final byte firstEventOffset, final boolean sentEvent, final byte detail, final short sequenceNumber, final int event, final byte mode) {
    this.firstEventOffset = firstEventOffset;
    this.sentEvent = sentEvent;
    this.detail = detail;
    this.sequenceNumber = sequenceNumber;
    this.event = event;
    this.mode = mode;
  }

  @java.lang.SuppressWarnings("all")
  public static FocusInEvent.FocusInEventBuilder builder() {
    return new FocusInEvent.FocusInEventBuilder();
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
  public byte getDetail() {
    return this.detail;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getEvent() {
    return this.event;
  }

  @java.lang.SuppressWarnings("all")
  public byte getMode() {
    return this.mode;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof FocusInEvent)) return false;
    final FocusInEvent other = (FocusInEvent) o;
    if (this.getFirstEventOffset() != other.getFirstEventOffset()) return false;
    if (this.isSentEvent() != other.isSentEvent()) return false;
    if (this.getDetail() != other.getDetail()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getEvent() != other.getEvent()) return false;
    if (this.getMode() != other.getMode()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFirstEventOffset();
    result = result * PRIME + (this.isSentEvent() ? 79 : 97);
    result = result * PRIME + this.getDetail();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getEvent();
    result = result * PRIME + this.getMode();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "FocusInEvent(firstEventOffset=" + this.getFirstEventOffset() + ", sentEvent=" + this.isSentEvent() + ", detail=" + this.getDetail() + ", sequenceNumber=" + this.getSequenceNumber() + ", event=" + this.getEvent() + ", mode=" + this.getMode() + ")";
  }
}
