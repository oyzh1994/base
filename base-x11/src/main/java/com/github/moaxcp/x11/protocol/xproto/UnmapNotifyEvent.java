package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;

public final class UnmapNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte NUMBER = 18;
  private final byte firstEventOffset;
  private final boolean sentEvent;
  private final short sequenceNumber;
  private final int event;
  private final int window;
  private final boolean fromConfigure;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static UnmapNotifyEvent readUnmapNotifyEvent(byte firstEventOffset, boolean sentEvent, X11Input in) throws IOException {
    UnmapNotifyEvent.UnmapNotifyEventBuilder javaBuilder = UnmapNotifyEvent.builder();
    byte[] pad1 = in.readPad(1);
    short sequenceNumber = in.readCard16();
    int event = in.readCard32();
    int window = in.readCard32();
    boolean fromConfigure = in.readBool();
    byte[] pad6 = in.readPad(3);
    byte[] pad7 = in.readPad(16);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.event(event);
    javaBuilder.window(window);
    javaBuilder.fromConfigure(fromConfigure);
    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (128 | getResponseCode()) : getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(event);
    out.writeCard32(window);
    out.writeBool(fromConfigure);
    out.writePad(3);
    out.writePad(16);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class UnmapNotifyEventBuilder {
    @java.lang.SuppressWarnings("all")
    private byte firstEventOffset;
    @java.lang.SuppressWarnings("all")
    private boolean sentEvent;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int event;
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private boolean fromConfigure;

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    UnmapNotifyEventBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public UnmapNotifyEvent.UnmapNotifyEventBuilder firstEventOffset(final byte firstEventOffset) {
      this.firstEventOffset = firstEventOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public UnmapNotifyEvent.UnmapNotifyEventBuilder sentEvent(final boolean sentEvent) {
      this.sentEvent = sentEvent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public UnmapNotifyEvent.UnmapNotifyEventBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public UnmapNotifyEvent.UnmapNotifyEventBuilder event(final int event) {
      this.event = event;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public UnmapNotifyEvent.UnmapNotifyEventBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public UnmapNotifyEvent.UnmapNotifyEventBuilder fromConfigure(final boolean fromConfigure) {
      this.fromConfigure = fromConfigure;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public UnmapNotifyEvent build() {
      return new UnmapNotifyEvent(this.firstEventOffset, this.sentEvent, this.sequenceNumber, this.event, this.window, this.fromConfigure);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "UnmapNotifyEvent.UnmapNotifyEventBuilder(firstEventOffset=" + this.firstEventOffset + ", sentEvent=" + this.sentEvent + ", sequenceNumber=" + this.sequenceNumber + ", event=" + this.event + ", window=" + this.window + ", fromConfigure=" + this.fromConfigure + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  UnmapNotifyEvent(final byte firstEventOffset, final boolean sentEvent, final short sequenceNumber, final int event, final int window, final boolean fromConfigure) {
    this.firstEventOffset = firstEventOffset;
    this.sentEvent = sentEvent;
    this.sequenceNumber = sequenceNumber;
    this.event = event;
    this.window = window;
    this.fromConfigure = fromConfigure;
  }

  @java.lang.SuppressWarnings("all")
  public static UnmapNotifyEvent.UnmapNotifyEventBuilder builder() {
    return new UnmapNotifyEvent.UnmapNotifyEventBuilder();
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
  public int getEvent() {
    return this.event;
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isFromConfigure() {
    return this.fromConfigure;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof UnmapNotifyEvent)) return false;
    final UnmapNotifyEvent other = (UnmapNotifyEvent) o;
    if (this.getFirstEventOffset() != other.getFirstEventOffset()) return false;
    if (this.isSentEvent() != other.isSentEvent()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getEvent() != other.getEvent()) return false;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.isFromConfigure() != other.isFromConfigure()) return false;
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
    result = result * PRIME + this.getEvent();
    result = result * PRIME + this.getWindow();
    result = result * PRIME + (this.isFromConfigure() ? 79 : 97);
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "UnmapNotifyEvent(firstEventOffset=" + this.getFirstEventOffset() + ", sentEvent=" + this.isSentEvent() + ", sequenceNumber=" + this.getSequenceNumber() + ", event=" + this.getEvent() + ", window=" + this.getWindow() + ", fromConfigure=" + this.isFromConfigure() + ")";
  }
}
