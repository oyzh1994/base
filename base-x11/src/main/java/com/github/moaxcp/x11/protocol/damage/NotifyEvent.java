package com.github.moaxcp.x11.protocol.damage;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import com.github.moaxcp.x11.protocol.xproto.Rectangle;
import java.io.IOException;

public final class NotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "damage";
  public static final byte NUMBER = 0;
  private final byte firstEventOffset;
  private final boolean sentEvent;
  private final byte level;
  private final short sequenceNumber;
  private final int drawable;
  private final int damage;
  private final int timestamp;
  private final Rectangle area;
  private final Rectangle geometry;

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
    byte level = in.readCard8();
    short sequenceNumber = in.readCard16();
    int drawable = in.readCard32();
    int damage = in.readCard32();
    int timestamp = in.readCard32();
    Rectangle area = Rectangle.readRectangle(in);
    Rectangle geometry = Rectangle.readRectangle(in);
    javaBuilder.level(level);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.drawable(drawable);
    javaBuilder.damage(damage);
    javaBuilder.timestamp(timestamp);
    javaBuilder.area(area);
    javaBuilder.geometry(geometry);
    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (128 | getResponseCode()) : getResponseCode());
    out.writeCard8(level);
    out.writeCard16(sequenceNumber);
    out.writeCard32(drawable);
    out.writeCard32(damage);
    out.writeCard32(timestamp);
    area.write(out);
    geometry.write(out);
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
    private byte level;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int drawable;
    @java.lang.SuppressWarnings("all")
    private int damage;
    @java.lang.SuppressWarnings("all")
    private int timestamp;
    @java.lang.SuppressWarnings("all")
    private Rectangle area;
    @java.lang.SuppressWarnings("all")
    private Rectangle geometry;

    public NotifyEvent.NotifyEventBuilder level(ReportLevel level) {
      this.level = (byte) level.getValue();
      return this;
    }

    public NotifyEvent.NotifyEventBuilder level(byte level) {
      this.level = level;
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
    public NotifyEvent.NotifyEventBuilder drawable(final int drawable) {
      this.drawable = drawable;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NotifyEvent.NotifyEventBuilder damage(final int damage) {
      this.damage = damage;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NotifyEvent.NotifyEventBuilder timestamp(final int timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NotifyEvent.NotifyEventBuilder area(final Rectangle area) {
      if (area == null) {
        throw new java.lang.NullPointerException("area is marked non-null but is null");
      }
      this.area = area;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NotifyEvent.NotifyEventBuilder geometry(final Rectangle geometry) {
      if (geometry == null) {
        throw new java.lang.NullPointerException("geometry is marked non-null but is null");
      }
      this.geometry = geometry;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public NotifyEvent build() {
      return new NotifyEvent(this.firstEventOffset, this.sentEvent, this.level, this.sequenceNumber, this.drawable, this.damage, this.timestamp, this.area, this.geometry);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "NotifyEvent.NotifyEventBuilder(firstEventOffset=" + this.firstEventOffset + ", sentEvent=" + this.sentEvent + ", level=" + this.level + ", sequenceNumber=" + this.sequenceNumber + ", drawable=" + this.drawable + ", damage=" + this.damage + ", timestamp=" + this.timestamp + ", area=" + this.area + ", geometry=" + this.geometry + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  NotifyEvent(final byte firstEventOffset, final boolean sentEvent, final byte level, final short sequenceNumber, final int drawable, final int damage, final int timestamp, final Rectangle area, final Rectangle geometry) {
    if (area == null) {
      throw new java.lang.NullPointerException("area is marked non-null but is null");
    }
    if (geometry == null) {
      throw new java.lang.NullPointerException("geometry is marked non-null but is null");
    }
    this.firstEventOffset = firstEventOffset;
    this.sentEvent = sentEvent;
    this.level = level;
    this.sequenceNumber = sequenceNumber;
    this.drawable = drawable;
    this.damage = damage;
    this.timestamp = timestamp;
    this.area = area;
    this.geometry = geometry;
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
  public byte getLevel() {
    return this.level;
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
  public int getDamage() {
    return this.damage;
  }

  @java.lang.SuppressWarnings("all")
  public int getTimestamp() {
    return this.timestamp;
  }

  @java.lang.SuppressWarnings("all")
  public Rectangle getArea() {
    return this.area;
  }

  @java.lang.SuppressWarnings("all")
  public Rectangle getGeometry() {
    return this.geometry;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof NotifyEvent)) return false;
    final NotifyEvent other = (NotifyEvent) o;
    if (this.getFirstEventOffset() != other.getFirstEventOffset()) return false;
    if (this.isSentEvent() != other.isSentEvent()) return false;
    if (this.getLevel() != other.getLevel()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getDrawable() != other.getDrawable()) return false;
    if (this.getDamage() != other.getDamage()) return false;
    if (this.getTimestamp() != other.getTimestamp()) return false;
    final java.lang.Object this$area = this.getArea();
    final java.lang.Object other$area = other.getArea();
    if (this$area == null ? other$area != null : !this$area.equals(other$area)) return false;
    final java.lang.Object this$geometry = this.getGeometry();
    final java.lang.Object other$geometry = other.getGeometry();
    if (this$geometry == null ? other$geometry != null : !this$geometry.equals(other$geometry)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFirstEventOffset();
    result = result * PRIME + (this.isSentEvent() ? 79 : 97);
    result = result * PRIME + this.getLevel();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getDrawable();
    result = result * PRIME + this.getDamage();
    result = result * PRIME + this.getTimestamp();
    final java.lang.Object $area = this.getArea();
    result = result * PRIME + ($area == null ? 43 : $area.hashCode());
    final java.lang.Object $geometry = this.getGeometry();
    result = result * PRIME + ($geometry == null ? 43 : $geometry.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "NotifyEvent(firstEventOffset=" + this.getFirstEventOffset() + ", sentEvent=" + this.isSentEvent() + ", level=" + this.getLevel() + ", sequenceNumber=" + this.getSequenceNumber() + ", drawable=" + this.getDrawable() + ", damage=" + this.getDamage() + ", timestamp=" + this.getTimestamp() + ", area=" + this.getArea() + ", geometry=" + this.getGeometry() + ")";
  }
}
