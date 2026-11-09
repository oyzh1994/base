package com.github.moaxcp.x11.protocol.screensaver;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;

public final class NotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "screensaver";
  public static final byte NUMBER = 0;
  private final byte firstEventOffset;
  private final boolean sentEvent;
  private final byte state;
  private final short sequenceNumber;
  private final int time;
  private final int root;
  private final int window;
  private final byte kind;
  private final boolean forced;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static NotifyEvent readNotifyEvent(byte firstEventOffset, boolean sentEvent, X11Input in) throws IOException {
    NotifyEvent.NotifyEventBuilder javaBuilder = NotifyEvent.builder();
    byte state = in.readByte();
    short sequenceNumber = in.readCard16();
    int time = in.readCard32();
    int root = in.readCard32();
    int window = in.readCard32();
    byte kind = in.readByte();
    boolean forced = in.readBool();
    byte[] pad8 = in.readPad(14);
    javaBuilder.state(state);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.time(time);
    javaBuilder.root(root);
    javaBuilder.window(window);
    javaBuilder.kind(kind);
    javaBuilder.forced(forced);
    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (128 | getResponseCode()) : getResponseCode());
    out.writeByte(state);
    out.writeCard16(sequenceNumber);
    out.writeCard32(time);
    out.writeCard32(root);
    out.writeCard32(window);
    out.writeByte(kind);
    out.writeBool(forced);
    out.writePad(14);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class NotifyEventBuilder {
    @java.lang.SuppressWarnings("all")
    private byte firstEventOffset;
    @java.lang.SuppressWarnings("all")
    private boolean sentEvent;
    @java.lang.SuppressWarnings("all")
    private byte state;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int time;
    @java.lang.SuppressWarnings("all")
    private int root;
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private byte kind;
    @java.lang.SuppressWarnings("all")
    private boolean forced;

    public NotifyEvent.NotifyEventBuilder state(State state) {
      this.state = (byte) state.getValue();
      return this;
    }

    public NotifyEvent.NotifyEventBuilder state(byte state) {
      this.state = state;
      return this;
    }

    public NotifyEvent.NotifyEventBuilder kind(Kind kind) {
      this.kind = (byte) kind.getValue();
      return this;
    }

    public NotifyEvent.NotifyEventBuilder kind(byte kind) {
      this.kind = kind;
      return this;
    }

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    NotifyEventBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NotifyEvent.NotifyEventBuilder firstEventOffset(final byte firstEventOffset) {
      this.firstEventOffset = firstEventOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NotifyEvent.NotifyEventBuilder sentEvent(final boolean sentEvent) {
      this.sentEvent = sentEvent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NotifyEvent.NotifyEventBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NotifyEvent.NotifyEventBuilder time(final int time) {
      this.time = time;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NotifyEvent.NotifyEventBuilder root(final int root) {
      this.root = root;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NotifyEvent.NotifyEventBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NotifyEvent.NotifyEventBuilder forced(final boolean forced) {
      this.forced = forced;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public NotifyEvent build() {
      return new NotifyEvent(this.firstEventOffset, this.sentEvent, this.state, this.sequenceNumber, this.time, this.root, this.window, this.kind, this.forced);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "NotifyEvent.NotifyEventBuilder(firstEventOffset=" + this.firstEventOffset + ", sentEvent=" + this.sentEvent + ", state=" + this.state + ", sequenceNumber=" + this.sequenceNumber + ", time=" + this.time + ", root=" + this.root + ", window=" + this.window + ", kind=" + this.kind + ", forced=" + this.forced + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  NotifyEvent(final byte firstEventOffset, final boolean sentEvent, final byte state, final short sequenceNumber, final int time, final int root, final int window, final byte kind, final boolean forced) {
    this.firstEventOffset = firstEventOffset;
    this.sentEvent = sentEvent;
    this.state = state;
    this.sequenceNumber = sequenceNumber;
    this.time = time;
    this.root = root;
    this.window = window;
    this.kind = kind;
    this.forced = forced;
  }

  @java.lang.SuppressWarnings("all")
  public static NotifyEvent.NotifyEventBuilder builder() {
    return new NotifyEvent.NotifyEventBuilder();
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
  public byte getState() {
    return this.state;
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
  public int getRoot() {
    return this.root;
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.SuppressWarnings("all")
  public byte getKind() {
    return this.kind;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isForced() {
    return this.forced;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof NotifyEvent)) return false;
    final NotifyEvent other = (NotifyEvent) o;
    if (this.getFirstEventOffset() != other.getFirstEventOffset()) return false;
    if (this.isSentEvent() != other.isSentEvent()) return false;
    if (this.getState() != other.getState()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getTime() != other.getTime()) return false;
    if (this.getRoot() != other.getRoot()) return false;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getKind() != other.getKind()) return false;
    if (this.isForced() != other.isForced()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFirstEventOffset();
    result = result * PRIME + (this.isSentEvent() ? 79 : 97);
    result = result * PRIME + this.getState();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getTime();
    result = result * PRIME + this.getRoot();
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getKind();
    result = result * PRIME + (this.isForced() ? 79 : 97);
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "NotifyEvent(firstEventOffset=" + this.getFirstEventOffset() + ", sentEvent=" + this.isSentEvent() + ", state=" + this.getState() + ", sequenceNumber=" + this.getSequenceNumber() + ", time=" + this.getTime() + ", root=" + this.getRoot() + ", window=" + this.getWindow() + ", kind=" + this.getKind() + ", forced=" + this.isForced() + ")";
  }
}
