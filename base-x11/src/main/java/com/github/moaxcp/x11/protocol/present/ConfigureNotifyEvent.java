package com.github.moaxcp.x11.protocol.present;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XGenericEvent;
import java.io.IOException;

public final class ConfigureNotifyEvent implements XGenericEvent {
  public static final String PLUGIN_NAME = "present";
  public static final byte NUMBER = 35;
  private final byte firstEventOffset;
  private final boolean sentEvent;
  private final byte extension;
  private final short sequenceNumber;
  private final short eventType;
  private final int event;
  private final int window;
  private final short x;
  private final short y;
  private final short width;
  private final short height;
  private final short offX;
  private final short offY;
  private final short pixmapWidth;
  private final short pixmapHeight;
  private final int pixmapFlags;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static ConfigureNotifyEvent readConfigureNotifyEvent(byte firstEventOffset, boolean sentEvent, byte extension, short sequenceNumber, int length, short eventType, X11Input in) throws IOException {
    ConfigureNotifyEvent.ConfigureNotifyEventBuilder javaBuilder = ConfigureNotifyEvent.builder();
    byte[] pad5 = in.readPad(2);
    int event = in.readCard32();
    int window = in.readCard32();
    short x = in.readInt16();
    short y = in.readInt16();
    short width = in.readCard16();
    short height = in.readCard16();
    short offX = in.readInt16();
    short offY = in.readInt16();
    short pixmapWidth = in.readCard16();
    short pixmapHeight = in.readCard16();
    int pixmapFlags = in.readCard32();
    javaBuilder.extension(extension);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.eventType(eventType);
    javaBuilder.event(event);
    javaBuilder.window(window);
    javaBuilder.x(x);
    javaBuilder.y(y);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.offX(offX);
    javaBuilder.offY(offY);
    javaBuilder.pixmapWidth(pixmapWidth);
    javaBuilder.pixmapHeight(pixmapHeight);
    javaBuilder.pixmapFlags(pixmapFlags);
    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (128 | getResponseCode()) : getResponseCode());
    out.writeCard8(extension);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength() - 32);
    out.writeCard16(eventType);
    out.writePad(2);
    out.writeCard32(event);
    out.writeCard32(window);
    out.writeInt16(x);
    out.writeInt16(y);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeInt16(offX);
    out.writeInt16(offY);
    out.writeCard16(pixmapWidth);
    out.writeCard16(pixmapHeight);
    out.writeCard32(pixmapFlags);
  }

  @Override
  public int getSize() {
    return 40;
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
    private byte extension;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private short eventType;
    @java.lang.SuppressWarnings("all")
    private int event;
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private short x;
    @java.lang.SuppressWarnings("all")
    private short y;
    @java.lang.SuppressWarnings("all")
    private short width;
    @java.lang.SuppressWarnings("all")
    private short height;
    @java.lang.SuppressWarnings("all")
    private short offX;
    @java.lang.SuppressWarnings("all")
    private short offY;
    @java.lang.SuppressWarnings("all")
    private short pixmapWidth;
    @java.lang.SuppressWarnings("all")
    private short pixmapHeight;
    @java.lang.SuppressWarnings("all")
    private int pixmapFlags;

    public int getSize() {
      return 40;
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
    public ConfigureNotifyEvent.ConfigureNotifyEventBuilder extension(final byte extension) {
      this.extension = extension;
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
    public ConfigureNotifyEvent.ConfigureNotifyEventBuilder eventType(final short eventType) {
      this.eventType = eventType;
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
    public ConfigureNotifyEvent.ConfigureNotifyEventBuilder offX(final short offX) {
      this.offX = offX;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConfigureNotifyEvent.ConfigureNotifyEventBuilder offY(final short offY) {
      this.offY = offY;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConfigureNotifyEvent.ConfigureNotifyEventBuilder pixmapWidth(final short pixmapWidth) {
      this.pixmapWidth = pixmapWidth;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConfigureNotifyEvent.ConfigureNotifyEventBuilder pixmapHeight(final short pixmapHeight) {
      this.pixmapHeight = pixmapHeight;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConfigureNotifyEvent.ConfigureNotifyEventBuilder pixmapFlags(final int pixmapFlags) {
      this.pixmapFlags = pixmapFlags;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ConfigureNotifyEvent build() {
      return new ConfigureNotifyEvent(this.firstEventOffset, this.sentEvent, this.extension, this.sequenceNumber, this.eventType, this.event, this.window, this.x, this.y, this.width, this.height, this.offX, this.offY, this.pixmapWidth, this.pixmapHeight, this.pixmapFlags);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ConfigureNotifyEvent.ConfigureNotifyEventBuilder(firstEventOffset=" + this.firstEventOffset + ", sentEvent=" + this.sentEvent + ", extension=" + this.extension + ", sequenceNumber=" + this.sequenceNumber + ", eventType=" + this.eventType + ", event=" + this.event + ", window=" + this.window + ", x=" + this.x + ", y=" + this.y + ", width=" + this.width + ", height=" + this.height + ", offX=" + this.offX + ", offY=" + this.offY + ", pixmapWidth=" + this.pixmapWidth + ", pixmapHeight=" + this.pixmapHeight + ", pixmapFlags=" + this.pixmapFlags + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ConfigureNotifyEvent(final byte firstEventOffset, final boolean sentEvent, final byte extension, final short sequenceNumber, final short eventType, final int event, final int window, final short x, final short y, final short width, final short height, final short offX, final short offY, final short pixmapWidth, final short pixmapHeight, final int pixmapFlags) {
    this.firstEventOffset = firstEventOffset;
    this.sentEvent = sentEvent;
    this.extension = extension;
    this.sequenceNumber = sequenceNumber;
    this.eventType = eventType;
    this.event = event;
    this.window = window;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.offX = offX;
    this.offY = offY;
    this.pixmapWidth = pixmapWidth;
    this.pixmapHeight = pixmapHeight;
    this.pixmapFlags = pixmapFlags;
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
  public byte getExtension() {
    return this.extension;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public short getEventType() {
    return this.eventType;
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
  public short getOffX() {
    return this.offX;
  }

  @java.lang.SuppressWarnings("all")
  public short getOffY() {
    return this.offY;
  }

  @java.lang.SuppressWarnings("all")
  public short getPixmapWidth() {
    return this.pixmapWidth;
  }

  @java.lang.SuppressWarnings("all")
  public short getPixmapHeight() {
    return this.pixmapHeight;
  }

  @java.lang.SuppressWarnings("all")
  public int getPixmapFlags() {
    return this.pixmapFlags;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ConfigureNotifyEvent)) return false;
    final ConfigureNotifyEvent other = (ConfigureNotifyEvent) o;
    if (this.getFirstEventOffset() != other.getFirstEventOffset()) return false;
    if (this.isSentEvent() != other.isSentEvent()) return false;
    if (this.getExtension() != other.getExtension()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getEventType() != other.getEventType()) return false;
    if (this.getEvent() != other.getEvent()) return false;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getX() != other.getX()) return false;
    if (this.getY() != other.getY()) return false;
    if (this.getWidth() != other.getWidth()) return false;
    if (this.getHeight() != other.getHeight()) return false;
    if (this.getOffX() != other.getOffX()) return false;
    if (this.getOffY() != other.getOffY()) return false;
    if (this.getPixmapWidth() != other.getPixmapWidth()) return false;
    if (this.getPixmapHeight() != other.getPixmapHeight()) return false;
    if (this.getPixmapFlags() != other.getPixmapFlags()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFirstEventOffset();
    result = result * PRIME + (this.isSentEvent() ? 79 : 97);
    result = result * PRIME + this.getExtension();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getEventType();
    result = result * PRIME + this.getEvent();
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getX();
    result = result * PRIME + this.getY();
    result = result * PRIME + this.getWidth();
    result = result * PRIME + this.getHeight();
    result = result * PRIME + this.getOffX();
    result = result * PRIME + this.getOffY();
    result = result * PRIME + this.getPixmapWidth();
    result = result * PRIME + this.getPixmapHeight();
    result = result * PRIME + this.getPixmapFlags();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ConfigureNotifyEvent(firstEventOffset=" + this.getFirstEventOffset() + ", sentEvent=" + this.isSentEvent() + ", extension=" + this.getExtension() + ", sequenceNumber=" + this.getSequenceNumber() + ", eventType=" + this.getEventType() + ", event=" + this.getEvent() + ", window=" + this.getWindow() + ", x=" + this.getX() + ", y=" + this.getY() + ", width=" + this.getWidth() + ", height=" + this.getHeight() + ", offX=" + this.getOffX() + ", offY=" + this.getOffY() + ", pixmapWidth=" + this.getPixmapWidth() + ", pixmapHeight=" + this.getPixmapHeight() + ", pixmapFlags=" + this.getPixmapFlags() + ")";
  }
}
