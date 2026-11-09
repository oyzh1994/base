package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;

public final class KeymapNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte NUMBER = 11;
  private final byte firstEventOffset;
  private final boolean sentEvent;
  private final byte[] keys;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  @Override
  public short getSequenceNumber() {
    throw new UnsupportedOperationException("noSequenceNumber");
  }

  public static KeymapNotifyEvent readKeymapNotifyEvent(byte firstEventOffset, boolean sentEvent, X11Input in) throws IOException {
    KeymapNotifyEvent.KeymapNotifyEventBuilder javaBuilder = KeymapNotifyEvent.builder();
    byte[] keys = in.readCard8(31);
    javaBuilder.keys(keys);
    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    if (javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (128 | getResponseCode()) : getResponseCode());
    out.writeCard8(keys);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 1 + 1 * keys.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class KeymapNotifyEventBuilder {
    @java.lang.SuppressWarnings("all")
    private byte firstEventOffset;
    @java.lang.SuppressWarnings("all")
    private boolean sentEvent;
    @java.lang.SuppressWarnings("all")
    private byte[] keys;

    public int getSize() {
      return 1 + 1 * keys.length;
    }

    @java.lang.SuppressWarnings("all")
    KeymapNotifyEventBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public KeymapNotifyEvent.KeymapNotifyEventBuilder firstEventOffset(final byte firstEventOffset) {
      this.firstEventOffset = firstEventOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public KeymapNotifyEvent.KeymapNotifyEventBuilder sentEvent(final boolean sentEvent) {
      this.sentEvent = sentEvent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public KeymapNotifyEvent.KeymapNotifyEventBuilder keys(final byte[] keys) {
      if (keys == null) {
        throw new java.lang.NullPointerException("keys is marked non-null but is null");
      }
      this.keys = keys;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public KeymapNotifyEvent build() {
      return new KeymapNotifyEvent(this.firstEventOffset, this.sentEvent, this.keys);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "KeymapNotifyEvent.KeymapNotifyEventBuilder(firstEventOffset=" + this.firstEventOffset + ", sentEvent=" + this.sentEvent + ", keys=" + java.util.Arrays.toString(this.keys) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  KeymapNotifyEvent(final byte firstEventOffset, final boolean sentEvent, final byte[] keys) {
    if (keys == null) {
      throw new java.lang.NullPointerException("keys is marked non-null but is null");
    }
    this.firstEventOffset = firstEventOffset;
    this.sentEvent = sentEvent;
    this.keys = keys;
  }

  @java.lang.SuppressWarnings("all")
  public static KeymapNotifyEvent.KeymapNotifyEventBuilder builder() {
    return new KeymapNotifyEvent.KeymapNotifyEventBuilder();
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
  public byte[] getKeys() {
    return this.keys;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof KeymapNotifyEvent)) return false;
    final KeymapNotifyEvent other = (KeymapNotifyEvent) o;
    if (this.getFirstEventOffset() != other.getFirstEventOffset()) return false;
    if (this.isSentEvent() != other.isSentEvent()) return false;
    if (!java.util.Arrays.equals(this.getKeys(), other.getKeys())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFirstEventOffset();
    result = result * PRIME + (this.isSentEvent() ? 79 : 97);
    result = result * PRIME + java.util.Arrays.hashCode(this.getKeys());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "KeymapNotifyEvent(firstEventOffset=" + this.getFirstEventOffset() + ", sentEvent=" + this.isSentEvent() + ", keys=" + java.util.Arrays.toString(this.getKeys()) + ")";
  }
}
