package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;

public final class MapNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte NUMBER = 19;
  private final byte firstEventOffset;
  private final boolean sentEvent;
  private final short sequenceNumber;
  private final int event;
  private final int window;
  private final boolean overrideRedirect;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static MapNotifyEvent readMapNotifyEvent(byte firstEventOffset, boolean sentEvent, X11Input in) throws IOException {
    MapNotifyEvent.MapNotifyEventBuilder javaBuilder = MapNotifyEvent.builder();
    byte[] pad1 = in.readPad(1);
    short sequenceNumber = in.readCard16();
    int event = in.readCard32();
    int window = in.readCard32();
    boolean overrideRedirect = in.readBool();
    byte[] pad6 = in.readPad(3);
    byte[] pad7 = in.readPad(16);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.event(event);
    javaBuilder.window(window);
    javaBuilder.overrideRedirect(overrideRedirect);
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
    out.writeBool(overrideRedirect);
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


  public static class MapNotifyEventBuilder {
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
    private boolean overrideRedirect;

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    MapNotifyEventBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MapNotifyEvent.MapNotifyEventBuilder firstEventOffset(final byte firstEventOffset) {
      this.firstEventOffset = firstEventOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MapNotifyEvent.MapNotifyEventBuilder sentEvent(final boolean sentEvent) {
      this.sentEvent = sentEvent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MapNotifyEvent.MapNotifyEventBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MapNotifyEvent.MapNotifyEventBuilder event(final int event) {
      this.event = event;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MapNotifyEvent.MapNotifyEventBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MapNotifyEvent.MapNotifyEventBuilder overrideRedirect(final boolean overrideRedirect) {
      this.overrideRedirect = overrideRedirect;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public MapNotifyEvent build() {
      return new MapNotifyEvent(this.firstEventOffset, this.sentEvent, this.sequenceNumber, this.event, this.window, this.overrideRedirect);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "MapNotifyEvent.MapNotifyEventBuilder(firstEventOffset=" + this.firstEventOffset + ", sentEvent=" + this.sentEvent + ", sequenceNumber=" + this.sequenceNumber + ", event=" + this.event + ", window=" + this.window + ", overrideRedirect=" + this.overrideRedirect + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  MapNotifyEvent(final byte firstEventOffset, final boolean sentEvent, final short sequenceNumber, final int event, final int window, final boolean overrideRedirect) {
    this.firstEventOffset = firstEventOffset;
    this.sentEvent = sentEvent;
    this.sequenceNumber = sequenceNumber;
    this.event = event;
    this.window = window;
    this.overrideRedirect = overrideRedirect;
  }

  @java.lang.SuppressWarnings("all")
  public static MapNotifyEvent.MapNotifyEventBuilder builder() {
    return new MapNotifyEvent.MapNotifyEventBuilder();
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
  public boolean isOverrideRedirect() {
    return this.overrideRedirect;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof MapNotifyEvent)) return false;
    final MapNotifyEvent other = (MapNotifyEvent) o;
    if (this.getFirstEventOffset() != other.getFirstEventOffset()) return false;
    if (this.isSentEvent() != other.isSentEvent()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getEvent() != other.getEvent()) return false;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.isOverrideRedirect() != other.isOverrideRedirect()) return false;
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
    result = result * PRIME + (this.isOverrideRedirect() ? 79 : 97);
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "MapNotifyEvent(firstEventOffset=" + this.getFirstEventOffset() + ", sentEvent=" + this.isSentEvent() + ", sequenceNumber=" + this.getSequenceNumber() + ", event=" + this.getEvent() + ", window=" + this.getWindow() + ", overrideRedirect=" + this.isOverrideRedirect() + ")";
  }
}
