package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;

public final class NoExposureEvent implements XEvent {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte NUMBER = 14;
  private final byte firstEventOffset;
  private final boolean sentEvent;
  private final short sequenceNumber;
  private final int drawable;
  private final short minorOpcode;
  private final byte majorOpcode;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static NoExposureEvent readNoExposureEvent(byte firstEventOffset, boolean sentEvent, X11Input in) throws IOException {
    NoExposureEvent.NoExposureEventBuilder javaBuilder = NoExposureEvent.builder();
    byte[] pad1 = in.readPad(1);
    short sequenceNumber = in.readCard16();
    int drawable = in.readCard32();
    short minorOpcode = in.readCard16();
    byte majorOpcode = in.readCard8();
    byte[] pad6 = in.readPad(1);
    byte[] pad7 = in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.drawable(drawable);
    javaBuilder.minorOpcode(minorOpcode);
    javaBuilder.majorOpcode(majorOpcode);
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
    out.writeCard16(minorOpcode);
    out.writeCard8(majorOpcode);
    out.writePad(1);
    out.writePad(20);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class NoExposureEventBuilder {
    @java.lang.SuppressWarnings("all")
    private byte firstEventOffset;
    @java.lang.SuppressWarnings("all")
    private boolean sentEvent;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int drawable;
    @java.lang.SuppressWarnings("all")
    private short minorOpcode;
    @java.lang.SuppressWarnings("all")
    private byte majorOpcode;

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    NoExposureEventBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NoExposureEvent.NoExposureEventBuilder firstEventOffset(final byte firstEventOffset) {
      this.firstEventOffset = firstEventOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NoExposureEvent.NoExposureEventBuilder sentEvent(final boolean sentEvent) {
      this.sentEvent = sentEvent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NoExposureEvent.NoExposureEventBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NoExposureEvent.NoExposureEventBuilder drawable(final int drawable) {
      this.drawable = drawable;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NoExposureEvent.NoExposureEventBuilder minorOpcode(final short minorOpcode) {
      this.minorOpcode = minorOpcode;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NoExposureEvent.NoExposureEventBuilder majorOpcode(final byte majorOpcode) {
      this.majorOpcode = majorOpcode;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public NoExposureEvent build() {
      return new NoExposureEvent(this.firstEventOffset, this.sentEvent, this.sequenceNumber, this.drawable, this.minorOpcode, this.majorOpcode);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "NoExposureEvent.NoExposureEventBuilder(firstEventOffset=" + this.firstEventOffset + ", sentEvent=" + this.sentEvent + ", sequenceNumber=" + this.sequenceNumber + ", drawable=" + this.drawable + ", minorOpcode=" + this.minorOpcode + ", majorOpcode=" + this.majorOpcode + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  NoExposureEvent(final byte firstEventOffset, final boolean sentEvent, final short sequenceNumber, final int drawable, final short minorOpcode, final byte majorOpcode) {
    this.firstEventOffset = firstEventOffset;
    this.sentEvent = sentEvent;
    this.sequenceNumber = sequenceNumber;
    this.drawable = drawable;
    this.minorOpcode = minorOpcode;
    this.majorOpcode = majorOpcode;
  }

  @java.lang.SuppressWarnings("all")
  public static NoExposureEvent.NoExposureEventBuilder builder() {
    return new NoExposureEvent.NoExposureEventBuilder();
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
  public short getMinorOpcode() {
    return this.minorOpcode;
  }

  @java.lang.SuppressWarnings("all")
  public byte getMajorOpcode() {
    return this.majorOpcode;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof NoExposureEvent)) return false;
    final NoExposureEvent other = (NoExposureEvent) o;
    if (this.getFirstEventOffset() != other.getFirstEventOffset()) return false;
    if (this.isSentEvent() != other.isSentEvent()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getDrawable() != other.getDrawable()) return false;
    if (this.getMinorOpcode() != other.getMinorOpcode()) return false;
    if (this.getMajorOpcode() != other.getMajorOpcode()) return false;
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
    result = result * PRIME + this.getMinorOpcode();
    result = result * PRIME + this.getMajorOpcode();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "NoExposureEvent(firstEventOffset=" + this.getFirstEventOffset() + ", sentEvent=" + this.isSentEvent() + ", sequenceNumber=" + this.getSequenceNumber() + ", drawable=" + this.getDrawable() + ", minorOpcode=" + this.getMinorOpcode() + ", majorOpcode=" + this.getMajorOpcode() + ")";
  }
}
