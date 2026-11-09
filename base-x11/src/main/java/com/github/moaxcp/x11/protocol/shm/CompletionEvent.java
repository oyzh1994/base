package com.github.moaxcp.x11.protocol.shm;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;

public final class CompletionEvent implements XEvent {
  public static final String PLUGIN_NAME = "shm";
  public static final byte NUMBER = 0;
  private final byte firstEventOffset;
  private final boolean sentEvent;
  private final short sequenceNumber;
  private final int drawable;
  private final short minorEvent;
  private final byte majorEvent;
  private final int shmseg;
  private final int offset;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static CompletionEvent readCompletionEvent(byte firstEventOffset, boolean sentEvent, X11Input in) throws IOException {
    CompletionEvent.CompletionEventBuilder javaBuilder = CompletionEvent.builder();
    byte[] pad1 = in.readPad(1);
    short sequenceNumber = in.readCard16();
    int drawable = in.readCard32();
    short minorEvent = in.readCard16();
    byte majorEvent = in.readByte();
    byte[] pad6 = in.readPad(1);
    int shmseg = in.readCard32();
    int offset = in.readCard32();
    byte[] pad9 = in.readPad(12);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.drawable(drawable);
    javaBuilder.minorEvent(minorEvent);
    javaBuilder.majorEvent(majorEvent);
    javaBuilder.shmseg(shmseg);
    javaBuilder.offset(offset);
    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (128 | getResponseCode()) : getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(drawable);
    out.writeCard16(minorEvent);
    out.writeByte(majorEvent);
    out.writePad(1);
    out.writeCard32(shmseg);
    out.writeCard32(offset);
    out.writePad(12);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CompletionEventBuilder {
    @java.lang.SuppressWarnings("all")
    private byte firstEventOffset;
    @java.lang.SuppressWarnings("all")
    private boolean sentEvent;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int drawable;
    @java.lang.SuppressWarnings("all")
    private short minorEvent;
    @java.lang.SuppressWarnings("all")
    private byte majorEvent;
    @java.lang.SuppressWarnings("all")
    private int shmseg;
    @java.lang.SuppressWarnings("all")
    private int offset;

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    CompletionEventBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CompletionEvent.CompletionEventBuilder firstEventOffset(final byte firstEventOffset) {
      this.firstEventOffset = firstEventOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CompletionEvent.CompletionEventBuilder sentEvent(final boolean sentEvent) {
      this.sentEvent = sentEvent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CompletionEvent.CompletionEventBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CompletionEvent.CompletionEventBuilder drawable(final int drawable) {
      this.drawable = drawable;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CompletionEvent.CompletionEventBuilder minorEvent(final short minorEvent) {
      this.minorEvent = minorEvent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CompletionEvent.CompletionEventBuilder majorEvent(final byte majorEvent) {
      this.majorEvent = majorEvent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CompletionEvent.CompletionEventBuilder shmseg(final int shmseg) {
      this.shmseg = shmseg;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CompletionEvent.CompletionEventBuilder offset(final int offset) {
      this.offset = offset;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CompletionEvent build() {
      return new CompletionEvent(this.firstEventOffset, this.sentEvent, this.sequenceNumber, this.drawable, this.minorEvent, this.majorEvent, this.shmseg, this.offset);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CompletionEvent.CompletionEventBuilder(firstEventOffset=" + this.firstEventOffset + ", sentEvent=" + this.sentEvent + ", sequenceNumber=" + this.sequenceNumber + ", drawable=" + this.drawable + ", minorEvent=" + this.minorEvent + ", majorEvent=" + this.majorEvent + ", shmseg=" + this.shmseg + ", offset=" + this.offset + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CompletionEvent(final byte firstEventOffset, final boolean sentEvent, final short sequenceNumber, final int drawable, final short minorEvent, final byte majorEvent, final int shmseg, final int offset) {
    this.firstEventOffset = firstEventOffset;
    this.sentEvent = sentEvent;
    this.sequenceNumber = sequenceNumber;
    this.drawable = drawable;
    this.minorEvent = minorEvent;
    this.majorEvent = majorEvent;
    this.shmseg = shmseg;
    this.offset = offset;
  }

  @java.lang.SuppressWarnings("all")
  public static CompletionEvent.CompletionEventBuilder builder() {
    return new CompletionEvent.CompletionEventBuilder();
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
  public int getDrawable() {
    return this.drawable;
  }

  @java.lang.SuppressWarnings("all")
  public short getMinorEvent() {
    return this.minorEvent;
  }

  @java.lang.SuppressWarnings("all")
  public byte getMajorEvent() {
    return this.majorEvent;
  }

  @java.lang.SuppressWarnings("all")
  public int getShmseg() {
    return this.shmseg;
  }

  @java.lang.SuppressWarnings("all")
  public int getOffset() {
    return this.offset;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CompletionEvent)) return false;
    final CompletionEvent other = (CompletionEvent) o;
    if (this.getFirstEventOffset() != other.getFirstEventOffset()) return false;
    if (this.isSentEvent() != other.isSentEvent()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getDrawable() != other.getDrawable()) return false;
    if (this.getMinorEvent() != other.getMinorEvent()) return false;
    if (this.getMajorEvent() != other.getMajorEvent()) return false;
    if (this.getShmseg() != other.getShmseg()) return false;
    if (this.getOffset() != other.getOffset()) return false;
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
    result = result * PRIME + this.getDrawable();
    result = result * PRIME + this.getMinorEvent();
    result = result * PRIME + this.getMajorEvent();
    result = result * PRIME + this.getShmseg();
    result = result * PRIME + this.getOffset();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CompletionEvent(firstEventOffset=" + this.getFirstEventOffset() + ", sentEvent=" + this.isSentEvent() + ", sequenceNumber=" + this.getSequenceNumber() + ", drawable=" + this.getDrawable() + ", minorEvent=" + this.getMinorEvent() + ", majorEvent=" + this.getMajorEvent() + ", shmseg=" + this.getShmseg() + ", offset=" + this.getOffset() + ")";
  }
}
