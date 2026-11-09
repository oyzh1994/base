package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import com.github.moaxcp.x11.protocol.render.SubPixel;
import java.io.IOException;

public final class ScreenChangeNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "randr";
  public static final byte NUMBER = 0;
  private final byte firstEventOffset;
  private final boolean sentEvent;
  private final byte rotation;
  private final short sequenceNumber;
  private final int timestamp;
  private final int configTimestamp;
  private final int root;
  private final int requestWindow;
  private final short sizeID;
  private final short subpixelOrder;
  private final short width;
  private final short height;
  private final short mwidth;
  private final short mheight;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static ScreenChangeNotifyEvent readScreenChangeNotifyEvent(byte firstEventOffset, boolean sentEvent, X11Input in) throws IOException {
    ScreenChangeNotifyEvent.ScreenChangeNotifyEventBuilder javaBuilder = ScreenChangeNotifyEvent.builder();
    byte rotation = in.readCard8();
    short sequenceNumber = in.readCard16();
    int timestamp = in.readCard32();
    int configTimestamp = in.readCard32();
    int root = in.readCard32();
    int requestWindow = in.readCard32();
    short sizeID = in.readCard16();
    short subpixelOrder = in.readCard16();
    short width = in.readCard16();
    short height = in.readCard16();
    short mwidth = in.readCard16();
    short mheight = in.readCard16();
    javaBuilder.rotation(rotation);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.timestamp(timestamp);
    javaBuilder.configTimestamp(configTimestamp);
    javaBuilder.root(root);
    javaBuilder.requestWindow(requestWindow);
    javaBuilder.sizeID(sizeID);
    javaBuilder.subpixelOrder(subpixelOrder);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.mwidth(mwidth);
    javaBuilder.mheight(mheight);
    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (128 | getResponseCode()) : getResponseCode());
    out.writeCard8(rotation);
    out.writeCard16(sequenceNumber);
    out.writeCard32(timestamp);
    out.writeCard32(configTimestamp);
    out.writeCard32(root);
    out.writeCard32(requestWindow);
    out.writeCard16(sizeID);
    out.writeCard16(subpixelOrder);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeCard16(mwidth);
    out.writeCard16(mheight);
  }

  public boolean isRotationEnabled(Rotation... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (Rotation m : maskEnums) {
      if (!m.isEnabled(rotation)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ScreenChangeNotifyEventBuilder {
    @java.lang.SuppressWarnings("all")
    private byte firstEventOffset;
    @java.lang.SuppressWarnings("all")
    private boolean sentEvent;
    @java.lang.SuppressWarnings("all")
    private byte rotation;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int timestamp;
    @java.lang.SuppressWarnings("all")
    private int configTimestamp;
    @java.lang.SuppressWarnings("all")
    private int root;
    @java.lang.SuppressWarnings("all")
    private int requestWindow;
    @java.lang.SuppressWarnings("all")
    private short sizeID;
    @java.lang.SuppressWarnings("all")
    private short subpixelOrder;
    @java.lang.SuppressWarnings("all")
    private short width;
    @java.lang.SuppressWarnings("all")
    private short height;
    @java.lang.SuppressWarnings("all")
    private short mwidth;
    @java.lang.SuppressWarnings("all")
    private short mheight;

    public boolean isRotationEnabled(Rotation... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (Rotation m : maskEnums) {
        if (!m.isEnabled(rotation)) {
          return false;
        }
      }
      return true;
    }

    public ScreenChangeNotifyEvent.ScreenChangeNotifyEventBuilder rotationEnable(Rotation... maskEnums) {
      for (Rotation m : maskEnums) {
        rotation((byte) m.enableFor(rotation));
      }
      return this;
    }

    public ScreenChangeNotifyEvent.ScreenChangeNotifyEventBuilder rotationDisable(Rotation... maskEnums) {
      for (Rotation m : maskEnums) {
        rotation((byte) m.disableFor(rotation));
      }
      return this;
    }

    public ScreenChangeNotifyEvent.ScreenChangeNotifyEventBuilder subpixelOrder(SubPixel subpixelOrder) {
      this.subpixelOrder = (short) subpixelOrder.getValue();
      return this;
    }

    public ScreenChangeNotifyEvent.ScreenChangeNotifyEventBuilder subpixelOrder(short subpixelOrder) {
      this.subpixelOrder = subpixelOrder;
      return this;
    }

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    ScreenChangeNotifyEventBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ScreenChangeNotifyEvent.ScreenChangeNotifyEventBuilder firstEventOffset(final byte firstEventOffset) {
      this.firstEventOffset = firstEventOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ScreenChangeNotifyEvent.ScreenChangeNotifyEventBuilder sentEvent(final boolean sentEvent) {
      this.sentEvent = sentEvent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ScreenChangeNotifyEvent.ScreenChangeNotifyEventBuilder rotation(final byte rotation) {
      this.rotation = rotation;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ScreenChangeNotifyEvent.ScreenChangeNotifyEventBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ScreenChangeNotifyEvent.ScreenChangeNotifyEventBuilder timestamp(final int timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ScreenChangeNotifyEvent.ScreenChangeNotifyEventBuilder configTimestamp(final int configTimestamp) {
      this.configTimestamp = configTimestamp;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ScreenChangeNotifyEvent.ScreenChangeNotifyEventBuilder root(final int root) {
      this.root = root;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ScreenChangeNotifyEvent.ScreenChangeNotifyEventBuilder requestWindow(final int requestWindow) {
      this.requestWindow = requestWindow;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ScreenChangeNotifyEvent.ScreenChangeNotifyEventBuilder sizeID(final short sizeID) {
      this.sizeID = sizeID;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ScreenChangeNotifyEvent.ScreenChangeNotifyEventBuilder width(final short width) {
      this.width = width;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ScreenChangeNotifyEvent.ScreenChangeNotifyEventBuilder height(final short height) {
      this.height = height;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ScreenChangeNotifyEvent.ScreenChangeNotifyEventBuilder mwidth(final short mwidth) {
      this.mwidth = mwidth;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ScreenChangeNotifyEvent.ScreenChangeNotifyEventBuilder mheight(final short mheight) {
      this.mheight = mheight;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ScreenChangeNotifyEvent build() {
      return new ScreenChangeNotifyEvent(this.firstEventOffset, this.sentEvent, this.rotation, this.sequenceNumber, this.timestamp, this.configTimestamp, this.root, this.requestWindow, this.sizeID, this.subpixelOrder, this.width, this.height, this.mwidth, this.mheight);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ScreenChangeNotifyEvent.ScreenChangeNotifyEventBuilder(firstEventOffset=" + this.firstEventOffset + ", sentEvent=" + this.sentEvent + ", rotation=" + this.rotation + ", sequenceNumber=" + this.sequenceNumber + ", timestamp=" + this.timestamp + ", configTimestamp=" + this.configTimestamp + ", root=" + this.root + ", requestWindow=" + this.requestWindow + ", sizeID=" + this.sizeID + ", subpixelOrder=" + this.subpixelOrder + ", width=" + this.width + ", height=" + this.height + ", mwidth=" + this.mwidth + ", mheight=" + this.mheight + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ScreenChangeNotifyEvent(final byte firstEventOffset, final boolean sentEvent, final byte rotation, final short sequenceNumber, final int timestamp, final int configTimestamp, final int root, final int requestWindow, final short sizeID, final short subpixelOrder, final short width, final short height, final short mwidth, final short mheight) {
    this.firstEventOffset = firstEventOffset;
    this.sentEvent = sentEvent;
    this.rotation = rotation;
    this.sequenceNumber = sequenceNumber;
    this.timestamp = timestamp;
    this.configTimestamp = configTimestamp;
    this.root = root;
    this.requestWindow = requestWindow;
    this.sizeID = sizeID;
    this.subpixelOrder = subpixelOrder;
    this.width = width;
    this.height = height;
    this.mwidth = mwidth;
    this.mheight = mheight;
  }

  @java.lang.SuppressWarnings("all")
  public static ScreenChangeNotifyEvent.ScreenChangeNotifyEventBuilder builder() {
    return new ScreenChangeNotifyEvent.ScreenChangeNotifyEventBuilder();
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
  public byte getRotation() {
    return this.rotation;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getTimestamp() {
    return this.timestamp;
  }

  @java.lang.SuppressWarnings("all")
  public int getConfigTimestamp() {
    return this.configTimestamp;
  }

  @java.lang.SuppressWarnings("all")
  public int getRoot() {
    return this.root;
  }

  @java.lang.SuppressWarnings("all")
  public int getRequestWindow() {
    return this.requestWindow;
  }

  @java.lang.SuppressWarnings("all")
  public short getSizeID() {
    return this.sizeID;
  }

  @java.lang.SuppressWarnings("all")
  public short getSubpixelOrder() {
    return this.subpixelOrder;
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
  public short getMwidth() {
    return this.mwidth;
  }

  @java.lang.SuppressWarnings("all")
  public short getMheight() {
    return this.mheight;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ScreenChangeNotifyEvent)) return false;
    final ScreenChangeNotifyEvent other = (ScreenChangeNotifyEvent) o;
    if (this.getFirstEventOffset() != other.getFirstEventOffset()) return false;
    if (this.isSentEvent() != other.isSentEvent()) return false;
    if (this.getRotation() != other.getRotation()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getTimestamp() != other.getTimestamp()) return false;
    if (this.getConfigTimestamp() != other.getConfigTimestamp()) return false;
    if (this.getRoot() != other.getRoot()) return false;
    if (this.getRequestWindow() != other.getRequestWindow()) return false;
    if (this.getSizeID() != other.getSizeID()) return false;
    if (this.getSubpixelOrder() != other.getSubpixelOrder()) return false;
    if (this.getWidth() != other.getWidth()) return false;
    if (this.getHeight() != other.getHeight()) return false;
    if (this.getMwidth() != other.getMwidth()) return false;
    if (this.getMheight() != other.getMheight()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFirstEventOffset();
    result = result * PRIME + (this.isSentEvent() ? 79 : 97);
    result = result * PRIME + this.getRotation();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getTimestamp();
    result = result * PRIME + this.getConfigTimestamp();
    result = result * PRIME + this.getRoot();
    result = result * PRIME + this.getRequestWindow();
    result = result * PRIME + this.getSizeID();
    result = result * PRIME + this.getSubpixelOrder();
    result = result * PRIME + this.getWidth();
    result = result * PRIME + this.getHeight();
    result = result * PRIME + this.getMwidth();
    result = result * PRIME + this.getMheight();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ScreenChangeNotifyEvent(firstEventOffset=" + this.getFirstEventOffset() + ", sentEvent=" + this.isSentEvent() + ", rotation=" + this.getRotation() + ", sequenceNumber=" + this.getSequenceNumber() + ", timestamp=" + this.getTimestamp() + ", configTimestamp=" + this.getConfigTimestamp() + ", root=" + this.getRoot() + ", requestWindow=" + this.getRequestWindow() + ", sizeID=" + this.getSizeID() + ", subpixelOrder=" + this.getSubpixelOrder() + ", width=" + this.getWidth() + ", height=" + this.getHeight() + ", mwidth=" + this.getMwidth() + ", mheight=" + this.getMheight() + ")";
  }
}
