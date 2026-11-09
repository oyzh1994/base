package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;

public final class ReparentNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte NUMBER = 21;
  private final byte firstEventOffset;
  private final boolean sentEvent;
  private final short sequenceNumber;
  private final int event;
  private final int window;
  private final int parent;
  private final short x;
  private final short y;
  private final boolean overrideRedirect;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static ReparentNotifyEvent readReparentNotifyEvent(byte firstEventOffset, boolean sentEvent, X11Input in) throws IOException {
    ReparentNotifyEvent.ReparentNotifyEventBuilder javaBuilder = ReparentNotifyEvent.builder();
    byte[] pad1 = in.readPad(1);
    short sequenceNumber = in.readCard16();
    int event = in.readCard32();
    int window = in.readCard32();
    int parent = in.readCard32();
    short x = in.readInt16();
    short y = in.readInt16();
    boolean overrideRedirect = in.readBool();
    byte[] pad9 = in.readPad(3);
    byte[] pad10 = in.readPad(8);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.event(event);
    javaBuilder.window(window);
    javaBuilder.parent(parent);
    javaBuilder.x(x);
    javaBuilder.y(y);
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
    out.writeCard32(parent);
    out.writeInt16(x);
    out.writeInt16(y);
    out.writeBool(overrideRedirect);
    out.writePad(3);
    out.writePad(8);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ReparentNotifyEventBuilder {
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
    private int parent;
    @java.lang.SuppressWarnings("all")
    private short x;
    @java.lang.SuppressWarnings("all")
    private short y;
    @java.lang.SuppressWarnings("all")
    private boolean overrideRedirect;

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    ReparentNotifyEventBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ReparentNotifyEvent.ReparentNotifyEventBuilder firstEventOffset(final byte firstEventOffset) {
      this.firstEventOffset = firstEventOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ReparentNotifyEvent.ReparentNotifyEventBuilder sentEvent(final boolean sentEvent) {
      this.sentEvent = sentEvent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ReparentNotifyEvent.ReparentNotifyEventBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ReparentNotifyEvent.ReparentNotifyEventBuilder event(final int event) {
      this.event = event;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ReparentNotifyEvent.ReparentNotifyEventBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ReparentNotifyEvent.ReparentNotifyEventBuilder parent(final int parent) {
      this.parent = parent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ReparentNotifyEvent.ReparentNotifyEventBuilder x(final short x) {
      this.x = x;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ReparentNotifyEvent.ReparentNotifyEventBuilder y(final short y) {
      this.y = y;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ReparentNotifyEvent.ReparentNotifyEventBuilder overrideRedirect(final boolean overrideRedirect) {
      this.overrideRedirect = overrideRedirect;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ReparentNotifyEvent build() {
      return new ReparentNotifyEvent(this.firstEventOffset, this.sentEvent, this.sequenceNumber, this.event, this.window, this.parent, this.x, this.y, this.overrideRedirect);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ReparentNotifyEvent.ReparentNotifyEventBuilder(firstEventOffset=" + this.firstEventOffset + ", sentEvent=" + this.sentEvent + ", sequenceNumber=" + this.sequenceNumber + ", event=" + this.event + ", window=" + this.window + ", parent=" + this.parent + ", x=" + this.x + ", y=" + this.y + ", overrideRedirect=" + this.overrideRedirect + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ReparentNotifyEvent(final byte firstEventOffset, final boolean sentEvent, final short sequenceNumber, final int event, final int window, final int parent, final short x, final short y, final boolean overrideRedirect) {
    this.firstEventOffset = firstEventOffset;
    this.sentEvent = sentEvent;
    this.sequenceNumber = sequenceNumber;
    this.event = event;
    this.window = window;
    this.parent = parent;
    this.x = x;
    this.y = y;
    this.overrideRedirect = overrideRedirect;
  }

  @java.lang.SuppressWarnings("all")
  public static ReparentNotifyEvent.ReparentNotifyEventBuilder builder() {
    return new ReparentNotifyEvent.ReparentNotifyEventBuilder();
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
  public int getParent() {
    return this.parent;
  }

  @java.lang.SuppressWarnings("all")
  public short getX() {
    return this.x;
  }

  @java.lang.SuppressWarnings("all")
  public short getY() {
    return this.y;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isOverrideRedirect() {
    return this.overrideRedirect;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ReparentNotifyEvent)) return false;
    final ReparentNotifyEvent other = (ReparentNotifyEvent) o;
    if (this.getFirstEventOffset() != other.getFirstEventOffset()) return false;
    if (this.isSentEvent() != other.isSentEvent()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getEvent() != other.getEvent()) return false;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getParent() != other.getParent()) return false;
    if (this.getX() != other.getX()) return false;
    if (this.getY() != other.getY()) return false;
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
    result = result * PRIME + this.getParent();
    result = result * PRIME + this.getX();
    result = result * PRIME + this.getY();
    result = result * PRIME + (this.isOverrideRedirect() ? 79 : 97);
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ReparentNotifyEvent(firstEventOffset=" + this.getFirstEventOffset() + ", sentEvent=" + this.isSentEvent() + ", sequenceNumber=" + this.getSequenceNumber() + ", event=" + this.getEvent() + ", window=" + this.getWindow() + ", parent=" + this.getParent() + ", x=" + this.getX() + ", y=" + this.getY() + ", overrideRedirect=" + this.isOverrideRedirect() + ")";
  }
}
