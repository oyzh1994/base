package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;

public final class LeaveNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte NUMBER = 8;
  private final byte firstEventOffset;
  private final boolean sentEvent;
  private final byte detail;
  private final short sequenceNumber;
  private final int time;
  private final int root;
  private final int event;
  private final int child;
  private final short rootX;
  private final short rootY;
  private final short eventX;
  private final short eventY;
  private final short state;
  private final byte mode;
  private final byte sameScreenFocus;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static LeaveNotifyEvent readLeaveNotifyEvent(byte firstEventOffset, boolean sentEvent, X11Input in) throws IOException {
    LeaveNotifyEvent.LeaveNotifyEventBuilder javaBuilder = LeaveNotifyEvent.builder();
    byte detail = in.readByte();
    short sequenceNumber = in.readCard16();
    int time = in.readCard32();
    int root = in.readCard32();
    int event = in.readCard32();
    int child = in.readCard32();
    short rootX = in.readInt16();
    short rootY = in.readInt16();
    short eventX = in.readInt16();
    short eventY = in.readInt16();
    short state = in.readCard16();
    byte mode = in.readByte();
    byte sameScreenFocus = in.readByte();
    javaBuilder.detail(detail);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.time(time);
    javaBuilder.root(root);
    javaBuilder.event(event);
    javaBuilder.child(child);
    javaBuilder.rootX(rootX);
    javaBuilder.rootY(rootY);
    javaBuilder.eventX(eventX);
    javaBuilder.eventY(eventY);
    javaBuilder.state(state);
    javaBuilder.mode(mode);
    javaBuilder.sameScreenFocus(sameScreenFocus);
    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (128 | getResponseCode()) : getResponseCode());
    out.writeByte(detail);
    out.writeCard16(sequenceNumber);
    out.writeCard32(time);
    out.writeCard32(root);
    out.writeCard32(event);
    out.writeCard32(child);
    out.writeInt16(rootX);
    out.writeInt16(rootY);
    out.writeInt16(eventX);
    out.writeInt16(eventY);
    out.writeCard16(state);
    out.writeByte(mode);
    out.writeByte(sameScreenFocus);
  }

  public boolean isStateEnabled(KeyButMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (KeyButMask m : maskEnums) {
      if (!m.isEnabled(state)) {
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


  public static class LeaveNotifyEventBuilder {
    @java.lang.SuppressWarnings("all")
    private byte firstEventOffset;
    @java.lang.SuppressWarnings("all")
    private boolean sentEvent;
    @java.lang.SuppressWarnings("all")
    private byte detail;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int time;
    @java.lang.SuppressWarnings("all")
    private int root;
    @java.lang.SuppressWarnings("all")
    private int event;
    @java.lang.SuppressWarnings("all")
    private int child;
    @java.lang.SuppressWarnings("all")
    private short rootX;
    @java.lang.SuppressWarnings("all")
    private short rootY;
    @java.lang.SuppressWarnings("all")
    private short eventX;
    @java.lang.SuppressWarnings("all")
    private short eventY;
    @java.lang.SuppressWarnings("all")
    private short state;
    @java.lang.SuppressWarnings("all")
    private byte mode;
    @java.lang.SuppressWarnings("all")
    private byte sameScreenFocus;

    public LeaveNotifyEvent.LeaveNotifyEventBuilder detail(NotifyDetail detail) {
      this.detail = (byte) detail.getValue();
      return this;
    }

    public LeaveNotifyEvent.LeaveNotifyEventBuilder detail(byte detail) {
      this.detail = detail;
      return this;
    }

    public boolean isStateEnabled(KeyButMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (KeyButMask m : maskEnums) {
        if (!m.isEnabled(state)) {
          return false;
        }
      }
      return true;
    }

    public LeaveNotifyEvent.LeaveNotifyEventBuilder stateEnable(KeyButMask... maskEnums) {
      for (KeyButMask m : maskEnums) {
        state((short) m.enableFor(state));
      }
      return this;
    }

    public LeaveNotifyEvent.LeaveNotifyEventBuilder stateDisable(KeyButMask... maskEnums) {
      for (KeyButMask m : maskEnums) {
        state((short) m.disableFor(state));
      }
      return this;
    }

    public LeaveNotifyEvent.LeaveNotifyEventBuilder mode(NotifyMode mode) {
      this.mode = (byte) mode.getValue();
      return this;
    }

    public LeaveNotifyEvent.LeaveNotifyEventBuilder mode(byte mode) {
      this.mode = mode;
      return this;
    }

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    LeaveNotifyEventBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public LeaveNotifyEvent.LeaveNotifyEventBuilder firstEventOffset(final byte firstEventOffset) {
      this.firstEventOffset = firstEventOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public LeaveNotifyEvent.LeaveNotifyEventBuilder sentEvent(final boolean sentEvent) {
      this.sentEvent = sentEvent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public LeaveNotifyEvent.LeaveNotifyEventBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public LeaveNotifyEvent.LeaveNotifyEventBuilder time(final int time) {
      this.time = time;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public LeaveNotifyEvent.LeaveNotifyEventBuilder root(final int root) {
      this.root = root;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public LeaveNotifyEvent.LeaveNotifyEventBuilder event(final int event) {
      this.event = event;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public LeaveNotifyEvent.LeaveNotifyEventBuilder child(final int child) {
      this.child = child;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public LeaveNotifyEvent.LeaveNotifyEventBuilder rootX(final short rootX) {
      this.rootX = rootX;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public LeaveNotifyEvent.LeaveNotifyEventBuilder rootY(final short rootY) {
      this.rootY = rootY;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public LeaveNotifyEvent.LeaveNotifyEventBuilder eventX(final short eventX) {
      this.eventX = eventX;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public LeaveNotifyEvent.LeaveNotifyEventBuilder eventY(final short eventY) {
      this.eventY = eventY;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public LeaveNotifyEvent.LeaveNotifyEventBuilder state(final short state) {
      this.state = state;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public LeaveNotifyEvent.LeaveNotifyEventBuilder sameScreenFocus(final byte sameScreenFocus) {
      this.sameScreenFocus = sameScreenFocus;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public LeaveNotifyEvent build() {
      return new LeaveNotifyEvent(this.firstEventOffset, this.sentEvent, this.detail, this.sequenceNumber, this.time, this.root, this.event, this.child, this.rootX, this.rootY, this.eventX, this.eventY, this.state, this.mode, this.sameScreenFocus);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "LeaveNotifyEvent.LeaveNotifyEventBuilder(firstEventOffset=" + this.firstEventOffset + ", sentEvent=" + this.sentEvent + ", detail=" + this.detail + ", sequenceNumber=" + this.sequenceNumber + ", time=" + this.time + ", root=" + this.root + ", event=" + this.event + ", child=" + this.child + ", rootX=" + this.rootX + ", rootY=" + this.rootY + ", eventX=" + this.eventX + ", eventY=" + this.eventY + ", state=" + this.state + ", mode=" + this.mode + ", sameScreenFocus=" + this.sameScreenFocus + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  LeaveNotifyEvent(final byte firstEventOffset, final boolean sentEvent, final byte detail, final short sequenceNumber, final int time, final int root, final int event, final int child, final short rootX, final short rootY, final short eventX, final short eventY, final short state, final byte mode, final byte sameScreenFocus) {
    this.firstEventOffset = firstEventOffset;
    this.sentEvent = sentEvent;
    this.detail = detail;
    this.sequenceNumber = sequenceNumber;
    this.time = time;
    this.root = root;
    this.event = event;
    this.child = child;
    this.rootX = rootX;
    this.rootY = rootY;
    this.eventX = eventX;
    this.eventY = eventY;
    this.state = state;
    this.mode = mode;
    this.sameScreenFocus = sameScreenFocus;
  }

  @java.lang.SuppressWarnings("all")
  public static LeaveNotifyEvent.LeaveNotifyEventBuilder builder() {
    return new LeaveNotifyEvent.LeaveNotifyEventBuilder();
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
  public byte getDetail() {
    return this.detail;
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
  public int getEvent() {
    return this.event;
  }

  @java.lang.SuppressWarnings("all")
  public int getChild() {
    return this.child;
  }

  @java.lang.SuppressWarnings("all")
  public short getRootX() {
    return this.rootX;
  }

  @java.lang.SuppressWarnings("all")
  public short getRootY() {
    return this.rootY;
  }

  @java.lang.SuppressWarnings("all")
  public short getEventX() {
    return this.eventX;
  }

  @java.lang.SuppressWarnings("all")
  public short getEventY() {
    return this.eventY;
  }

  @java.lang.SuppressWarnings("all")
  public short getState() {
    return this.state;
  }

  @java.lang.SuppressWarnings("all")
  public byte getMode() {
    return this.mode;
  }

  @java.lang.SuppressWarnings("all")
  public byte getSameScreenFocus() {
    return this.sameScreenFocus;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof LeaveNotifyEvent)) return false;
    final LeaveNotifyEvent other = (LeaveNotifyEvent) o;
    if (this.getFirstEventOffset() != other.getFirstEventOffset()) return false;
    if (this.isSentEvent() != other.isSentEvent()) return false;
    if (this.getDetail() != other.getDetail()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getTime() != other.getTime()) return false;
    if (this.getRoot() != other.getRoot()) return false;
    if (this.getEvent() != other.getEvent()) return false;
    if (this.getChild() != other.getChild()) return false;
    if (this.getRootX() != other.getRootX()) return false;
    if (this.getRootY() != other.getRootY()) return false;
    if (this.getEventX() != other.getEventX()) return false;
    if (this.getEventY() != other.getEventY()) return false;
    if (this.getState() != other.getState()) return false;
    if (this.getMode() != other.getMode()) return false;
    if (this.getSameScreenFocus() != other.getSameScreenFocus()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFirstEventOffset();
    result = result * PRIME + (this.isSentEvent() ? 79 : 97);
    result = result * PRIME + this.getDetail();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getTime();
    result = result * PRIME + this.getRoot();
    result = result * PRIME + this.getEvent();
    result = result * PRIME + this.getChild();
    result = result * PRIME + this.getRootX();
    result = result * PRIME + this.getRootY();
    result = result * PRIME + this.getEventX();
    result = result * PRIME + this.getEventY();
    result = result * PRIME + this.getState();
    result = result * PRIME + this.getMode();
    result = result * PRIME + this.getSameScreenFocus();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "LeaveNotifyEvent(firstEventOffset=" + this.getFirstEventOffset() + ", sentEvent=" + this.isSentEvent() + ", detail=" + this.getDetail() + ", sequenceNumber=" + this.getSequenceNumber() + ", time=" + this.getTime() + ", root=" + this.getRoot() + ", event=" + this.getEvent() + ", child=" + this.getChild() + ", rootX=" + this.getRootX() + ", rootY=" + this.getRootY() + ", eventX=" + this.getEventX() + ", eventY=" + this.getEventY() + ", state=" + this.getState() + ", mode=" + this.getMode() + ", sameScreenFocus=" + this.getSameScreenFocus() + ")";
  }
}
