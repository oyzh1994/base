package com.github.moaxcp.x11.protocol.shape;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;

public final class NotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "shape";
  public static final byte NUMBER = 0;
  private final byte firstEventOffset;
  private final boolean sentEvent;
  private final byte shapeKind;
  private final short sequenceNumber;
  private final int affectedWindow;
  private final short extentsX;
  private final short extentsY;
  private final short extentsWidth;
  private final short extentsHeight;
  private final int serverTime;
  private final boolean shaped;

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
    byte shapeKind = in.readCard8();
    short sequenceNumber = in.readCard16();
    int affectedWindow = in.readCard32();
    short extentsX = in.readInt16();
    short extentsY = in.readInt16();
    short extentsWidth = in.readCard16();
    short extentsHeight = in.readCard16();
    int serverTime = in.readCard32();
    boolean shaped = in.readBool();
    byte[] pad10 = in.readPad(11);
    javaBuilder.shapeKind(shapeKind);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.affectedWindow(affectedWindow);
    javaBuilder.extentsX(extentsX);
    javaBuilder.extentsY(extentsY);
    javaBuilder.extentsWidth(extentsWidth);
    javaBuilder.extentsHeight(extentsHeight);
    javaBuilder.serverTime(serverTime);
    javaBuilder.shaped(shaped);
    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (128 | getResponseCode()) : getResponseCode());
    out.writeCard8(shapeKind);
    out.writeCard16(sequenceNumber);
    out.writeCard32(affectedWindow);
    out.writeInt16(extentsX);
    out.writeInt16(extentsY);
    out.writeCard16(extentsWidth);
    out.writeCard16(extentsHeight);
    out.writeCard32(serverTime);
    out.writeBool(shaped);
    out.writePad(11);
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
    private byte shapeKind;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int affectedWindow;
    @java.lang.SuppressWarnings("all")
    private short extentsX;
    @java.lang.SuppressWarnings("all")
    private short extentsY;
    @java.lang.SuppressWarnings("all")
    private short extentsWidth;
    @java.lang.SuppressWarnings("all")
    private short extentsHeight;
    @java.lang.SuppressWarnings("all")
    private int serverTime;
    @java.lang.SuppressWarnings("all")
    private boolean shaped;

    public NotifyEvent.NotifyEventBuilder shapeKind(Sk shapeKind) {
      this.shapeKind = (byte) shapeKind.getValue();
      return this;
    }

    public NotifyEvent.NotifyEventBuilder shapeKind(byte shapeKind) {
      this.shapeKind = shapeKind;
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
    public NotifyEvent.NotifyEventBuilder affectedWindow(final int affectedWindow) {
      this.affectedWindow = affectedWindow;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NotifyEvent.NotifyEventBuilder extentsX(final short extentsX) {
      this.extentsX = extentsX;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NotifyEvent.NotifyEventBuilder extentsY(final short extentsY) {
      this.extentsY = extentsY;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NotifyEvent.NotifyEventBuilder extentsWidth(final short extentsWidth) {
      this.extentsWidth = extentsWidth;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NotifyEvent.NotifyEventBuilder extentsHeight(final short extentsHeight) {
      this.extentsHeight = extentsHeight;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NotifyEvent.NotifyEventBuilder serverTime(final int serverTime) {
      this.serverTime = serverTime;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NotifyEvent.NotifyEventBuilder shaped(final boolean shaped) {
      this.shaped = shaped;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public NotifyEvent build() {
      return new NotifyEvent(this.firstEventOffset, this.sentEvent, this.shapeKind, this.sequenceNumber, this.affectedWindow, this.extentsX, this.extentsY, this.extentsWidth, this.extentsHeight, this.serverTime, this.shaped);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "NotifyEvent.NotifyEventBuilder(firstEventOffset=" + this.firstEventOffset + ", sentEvent=" + this.sentEvent + ", shapeKind=" + this.shapeKind + ", sequenceNumber=" + this.sequenceNumber + ", affectedWindow=" + this.affectedWindow + ", extentsX=" + this.extentsX + ", extentsY=" + this.extentsY + ", extentsWidth=" + this.extentsWidth + ", extentsHeight=" + this.extentsHeight + ", serverTime=" + this.serverTime + ", shaped=" + this.shaped + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  NotifyEvent(final byte firstEventOffset, final boolean sentEvent, final byte shapeKind, final short sequenceNumber, final int affectedWindow, final short extentsX, final short extentsY, final short extentsWidth, final short extentsHeight, final int serverTime, final boolean shaped) {
    this.firstEventOffset = firstEventOffset;
    this.sentEvent = sentEvent;
    this.shapeKind = shapeKind;
    this.sequenceNumber = sequenceNumber;
    this.affectedWindow = affectedWindow;
    this.extentsX = extentsX;
    this.extentsY = extentsY;
    this.extentsWidth = extentsWidth;
    this.extentsHeight = extentsHeight;
    this.serverTime = serverTime;
    this.shaped = shaped;
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
  public byte getShapeKind() {
    return this.shapeKind;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getAffectedWindow() {
    return this.affectedWindow;
  }

  @java.lang.SuppressWarnings("all")
  public short getExtentsX() {
    return this.extentsX;
  }

  @java.lang.SuppressWarnings("all")
  public short getExtentsY() {
    return this.extentsY;
  }

  @java.lang.SuppressWarnings("all")
  public short getExtentsWidth() {
    return this.extentsWidth;
  }

  @java.lang.SuppressWarnings("all")
  public short getExtentsHeight() {
    return this.extentsHeight;
  }

  @java.lang.SuppressWarnings("all")
  public int getServerTime() {
    return this.serverTime;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isShaped() {
    return this.shaped;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof NotifyEvent)) return false;
    final NotifyEvent other = (NotifyEvent) o;
    if (this.getFirstEventOffset() != other.getFirstEventOffset()) return false;
    if (this.isSentEvent() != other.isSentEvent()) return false;
    if (this.getShapeKind() != other.getShapeKind()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getAffectedWindow() != other.getAffectedWindow()) return false;
    if (this.getExtentsX() != other.getExtentsX()) return false;
    if (this.getExtentsY() != other.getExtentsY()) return false;
    if (this.getExtentsWidth() != other.getExtentsWidth()) return false;
    if (this.getExtentsHeight() != other.getExtentsHeight()) return false;
    if (this.getServerTime() != other.getServerTime()) return false;
    if (this.isShaped() != other.isShaped()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFirstEventOffset();
    result = result * PRIME + (this.isSentEvent() ? 79 : 97);
    result = result * PRIME + this.getShapeKind();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getAffectedWindow();
    result = result * PRIME + this.getExtentsX();
    result = result * PRIME + this.getExtentsY();
    result = result * PRIME + this.getExtentsWidth();
    result = result * PRIME + this.getExtentsHeight();
    result = result * PRIME + this.getServerTime();
    result = result * PRIME + (this.isShaped() ? 79 : 97);
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "NotifyEvent(firstEventOffset=" + this.getFirstEventOffset() + ", sentEvent=" + this.isSentEvent() + ", shapeKind=" + this.getShapeKind() + ", sequenceNumber=" + this.getSequenceNumber() + ", affectedWindow=" + this.getAffectedWindow() + ", extentsX=" + this.getExtentsX() + ", extentsY=" + this.getExtentsY() + ", extentsWidth=" + this.getExtentsWidth() + ", extentsHeight=" + this.getExtentsHeight() + ", serverTime=" + this.getServerTime() + ", shaped=" + this.isShaped() + ")";
  }
}
