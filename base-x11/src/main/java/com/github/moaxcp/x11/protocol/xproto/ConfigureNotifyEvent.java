package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;

public final class ConfigureNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte NUMBER = 22;
  private final byte firstEventOffset;
  private final boolean sentEvent;
  private final short sequenceNumber;
  private final int event;
  private final int window;
  private final int aboveSibling;
  private final short x;
  private final short y;
  private final short width;
  private final short height;
  private final short borderWidth;
  private final boolean overrideRedirect;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static ConfigureNotifyEvent readConfigureNotifyEvent(byte firstEventOffset, boolean sentEvent, X11Input in) throws IOException {
    ConfigureNotifyEvent.ConfigureNotifyEventBuilder javaBuilder = ConfigureNotifyEvent.builder();
    byte[] pad1 = in.readPad(1);
    short sequenceNumber = in.readCard16();
    int event = in.readCard32();
    int window = in.readCard32();
    int aboveSibling = in.readCard32();
    short x = in.readInt16();
    short y = in.readInt16();
    short width = in.readCard16();
    short height = in.readCard16();
    short borderWidth = in.readCard16();
    boolean overrideRedirect = in.readBool();
    byte[] pad12 = in.readPad(1);
    byte[] pad13 = in.readPad(4);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.event(event);
    javaBuilder.window(window);
    javaBuilder.aboveSibling(aboveSibling);
    javaBuilder.x(x);
    javaBuilder.y(y);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.borderWidth(borderWidth);
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
    out.writeCard32(aboveSibling);
    out.writeInt16(x);
    out.writeInt16(y);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeCard16(borderWidth);
    out.writeBool(overrideRedirect);
    out.writePad(1);
    out.writePad(4);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ConfigureNotifyEventBuilder {
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
    private int aboveSibling;
    @java.lang.SuppressWarnings("all")
    private short x;
    @java.lang.SuppressWarnings("all")
    private short y;
    @java.lang.SuppressWarnings("all")
    private short width;
    @java.lang.SuppressWarnings("all")
    private short height;
    @java.lang.SuppressWarnings("all")
    private short borderWidth;
    @java.lang.SuppressWarnings("all")
    private boolean overrideRedirect;

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    ConfigureNotifyEventBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConfigureNotifyEvent.ConfigureNotifyEventBuilder firstEventOffset(final byte firstEventOffset) {
      this.firstEventOffset = firstEventOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConfigureNotifyEvent.ConfigureNotifyEventBuilder sentEvent(final boolean sentEvent) {
      this.sentEvent = sentEvent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConfigureNotifyEvent.ConfigureNotifyEventBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConfigureNotifyEvent.ConfigureNotifyEventBuilder event(final int event) {
      this.event = event;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConfigureNotifyEvent.ConfigureNotifyEventBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConfigureNotifyEvent.ConfigureNotifyEventBuilder aboveSibling(final int aboveSibling) {
      this.aboveSibling = aboveSibling;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConfigureNotifyEvent.ConfigureNotifyEventBuilder x(final short x) {
      this.x = x;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConfigureNotifyEvent.ConfigureNotifyEventBuilder y(final short y) {
      this.y = y;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConfigureNotifyEvent.ConfigureNotifyEventBuilder width(final short width) {
      this.width = width;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConfigureNotifyEvent.ConfigureNotifyEventBuilder height(final short height) {
      this.height = height;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConfigureNotifyEvent.ConfigureNotifyEventBuilder borderWidth(final short borderWidth) {
      this.borderWidth = borderWidth;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConfigureNotifyEvent.ConfigureNotifyEventBuilder overrideRedirect(final boolean overrideRedirect) {
      this.overrideRedirect = overrideRedirect;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ConfigureNotifyEvent build() {
      return new ConfigureNotifyEvent(this.firstEventOffset, this.sentEvent, this.sequenceNumber, this.event, this.window, this.aboveSibling, this.x, this.y, this.width, this.height, this.borderWidth, this.overrideRedirect);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ConfigureNotifyEvent.ConfigureNotifyEventBuilder(firstEventOffset=" + this.firstEventOffset + ", sentEvent=" + this.sentEvent + ", sequenceNumber=" + this.sequenceNumber + ", event=" + this.event + ", window=" + this.window + ", aboveSibling=" + this.aboveSibling + ", x=" + this.x + ", y=" + this.y + ", width=" + this.width + ", height=" + this.height + ", borderWidth=" + this.borderWidth + ", overrideRedirect=" + this.overrideRedirect + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ConfigureNotifyEvent(final byte firstEventOffset, final boolean sentEvent, final short sequenceNumber, final int event, final int window, final int aboveSibling, final short x, final short y, final short width, final short height, final short borderWidth, final boolean overrideRedirect) {
    this.firstEventOffset = firstEventOffset;
    this.sentEvent = sentEvent;
    this.sequenceNumber = sequenceNumber;
    this.event = event;
    this.window = window;
    this.aboveSibling = aboveSibling;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.borderWidth = borderWidth;
    this.overrideRedirect = overrideRedirect;
  }

  @java.lang.SuppressWarnings("all")
  public static ConfigureNotifyEvent.ConfigureNotifyEventBuilder builder() {
    return new ConfigureNotifyEvent.ConfigureNotifyEventBuilder();
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
  public int getAboveSibling() {
    return this.aboveSibling;
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
  public short getWidth() {
    return this.width;
  }

  @java.lang.SuppressWarnings("all")
  public short getHeight() {
    return this.height;
  }

  @java.lang.SuppressWarnings("all")
  public short getBorderWidth() {
    return this.borderWidth;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isOverrideRedirect() {
    return this.overrideRedirect;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ConfigureNotifyEvent)) return false;
    final ConfigureNotifyEvent other = (ConfigureNotifyEvent) o;
    if (this.getFirstEventOffset() != other.getFirstEventOffset()) return false;
    if (this.isSentEvent() != other.isSentEvent()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getEvent() != other.getEvent()) return false;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getAboveSibling() != other.getAboveSibling()) return false;
    if (this.getX() != other.getX()) return false;
    if (this.getY() != other.getY()) return false;
    if (this.getWidth() != other.getWidth()) return false;
    if (this.getHeight() != other.getHeight()) return false;
    if (this.getBorderWidth() != other.getBorderWidth()) return false;
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
    result = result * PRIME + this.getAboveSibling();
    result = result * PRIME + this.getX();
    result = result * PRIME + this.getY();
    result = result * PRIME + this.getWidth();
    result = result * PRIME + this.getHeight();
    result = result * PRIME + this.getBorderWidth();
    result = result * PRIME + (this.isOverrideRedirect() ? 79 : 97);
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ConfigureNotifyEvent(firstEventOffset=" + this.getFirstEventOffset() + ", sentEvent=" + this.isSentEvent() + ", sequenceNumber=" + this.getSequenceNumber() + ", event=" + this.getEvent() + ", window=" + this.getWindow() + ", aboveSibling=" + this.getAboveSibling() + ", x=" + this.getX() + ", y=" + this.getY() + ", width=" + this.getWidth() + ", height=" + this.getHeight() + ", borderWidth=" + this.getBorderWidth() + ", overrideRedirect=" + this.isOverrideRedirect() + ")";
  }
}
