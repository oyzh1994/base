package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;

public final class ColormapNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte NUMBER = 32;
  private final byte firstEventOffset;
  private final boolean sentEvent;
  private final short sequenceNumber;
  private final int window;
  private final int colormap;
  private final boolean newValue;
  private final byte state;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static ColormapNotifyEvent readColormapNotifyEvent(byte firstEventOffset, boolean sentEvent, X11Input in) throws IOException {
    ColormapNotifyEvent.ColormapNotifyEventBuilder javaBuilder = ColormapNotifyEvent.builder();
    byte[] pad1 = in.readPad(1);
    short sequenceNumber = in.readCard16();
    int window = in.readCard32();
    int colormap = in.readCard32();
    boolean newValue = in.readBool();
    byte state = in.readByte();
    byte[] pad7 = in.readPad(2);
    byte[] pad8 = in.readPad(16);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.window(window);
    javaBuilder.colormap(colormap);
    javaBuilder.newValue(newValue);
    javaBuilder.state(state);
    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (128 | getResponseCode()) : getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(window);
    out.writeCard32(colormap);
    out.writeBool(newValue);
    out.writeByte(state);
    out.writePad(2);
    out.writePad(16);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ColormapNotifyEventBuilder {
    @java.lang.SuppressWarnings("all")
    private byte firstEventOffset;
    @java.lang.SuppressWarnings("all")
    private boolean sentEvent;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private int colormap;
    @java.lang.SuppressWarnings("all")
    private boolean newValue;
    @java.lang.SuppressWarnings("all")
    private byte state;

    public ColormapNotifyEvent.ColormapNotifyEventBuilder state(ColormapState state) {
      this.state = (byte) state.getValue();
      return this;
    }

    public ColormapNotifyEvent.ColormapNotifyEventBuilder state(byte state) {
      this.state = state;
      return this;
    }

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    ColormapNotifyEventBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ColormapNotifyEvent.ColormapNotifyEventBuilder firstEventOffset(final byte firstEventOffset) {
      this.firstEventOffset = firstEventOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ColormapNotifyEvent.ColormapNotifyEventBuilder sentEvent(final boolean sentEvent) {
      this.sentEvent = sentEvent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ColormapNotifyEvent.ColormapNotifyEventBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ColormapNotifyEvent.ColormapNotifyEventBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ColormapNotifyEvent.ColormapNotifyEventBuilder colormap(final int colormap) {
      this.colormap = colormap;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ColormapNotifyEvent.ColormapNotifyEventBuilder newValue(final boolean newValue) {
      this.newValue = newValue;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ColormapNotifyEvent build() {
      return new ColormapNotifyEvent(this.firstEventOffset, this.sentEvent, this.sequenceNumber, this.window, this.colormap, this.newValue, this.state);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ColormapNotifyEvent.ColormapNotifyEventBuilder(firstEventOffset=" + this.firstEventOffset + ", sentEvent=" + this.sentEvent + ", sequenceNumber=" + this.sequenceNumber + ", window=" + this.window + ", colormap=" + this.colormap + ", newValue=" + this.newValue + ", state=" + this.state + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ColormapNotifyEvent(final byte firstEventOffset, final boolean sentEvent, final short sequenceNumber, final int window, final int colormap, final boolean newValue, final byte state) {
    this.firstEventOffset = firstEventOffset;
    this.sentEvent = sentEvent;
    this.sequenceNumber = sequenceNumber;
    this.window = window;
    this.colormap = colormap;
    this.newValue = newValue;
    this.state = state;
  }

  @java.lang.SuppressWarnings("all")
  public static ColormapNotifyEvent.ColormapNotifyEventBuilder builder() {
    return new ColormapNotifyEvent.ColormapNotifyEventBuilder();
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
  public int getWindow() {
    return this.window;
  }

  @java.lang.SuppressWarnings("all")
  public int getColormap() {
    return this.colormap;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isNewValue() {
    return this.newValue;
  }

  @java.lang.SuppressWarnings("all")
  public byte getState() {
    return this.state;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ColormapNotifyEvent)) return false;
    final ColormapNotifyEvent other = (ColormapNotifyEvent) o;
    if (this.getFirstEventOffset() != other.getFirstEventOffset()) return false;
    if (this.isSentEvent() != other.isSentEvent()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getColormap() != other.getColormap()) return false;
    if (this.isNewValue() != other.isNewValue()) return false;
    if (this.getState() != other.getState()) return false;
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
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getColormap();
    result = result * PRIME + (this.isNewValue() ? 79 : 97);
    result = result * PRIME + this.getState();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ColormapNotifyEvent(firstEventOffset=" + this.getFirstEventOffset() + ", sentEvent=" + this.isSentEvent() + ", sequenceNumber=" + this.getSequenceNumber() + ", window=" + this.getWindow() + ", colormap=" + this.getColormap() + ", newValue=" + this.isNewValue() + ", state=" + this.getState() + ")";
  }
}
