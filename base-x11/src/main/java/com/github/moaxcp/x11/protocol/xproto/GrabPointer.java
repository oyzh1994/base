package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GrabPointer implements TwoWayRequest<GrabPointerReply> {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 26;
  private final boolean ownerEvents;
  private final int grabWindow;
  private final short eventMask;
  private final byte pointerMode;
  private final byte keyboardMode;
  private final int confineTo;
  private final int cursor;
  private final int time;

  public XReplyFunction<GrabPointerReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GrabPointerReply.readGrabPointerReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GrabPointer readGrabPointer(X11Input in) throws IOException {
    GrabPointer.GrabPointerBuilder javaBuilder = GrabPointer.builder();
    boolean ownerEvents = in.readBool();
    short length = in.readCard16();
    int grabWindow = in.readCard32();
    short eventMask = in.readCard16();
    byte pointerMode = in.readByte();
    byte keyboardMode = in.readByte();
    int confineTo = in.readCard32();
    int cursor = in.readCard32();
    int time = in.readCard32();
    javaBuilder.ownerEvents(ownerEvents);
    javaBuilder.grabWindow(grabWindow);
    javaBuilder.eventMask(eventMask);
    javaBuilder.pointerMode(pointerMode);
    javaBuilder.keyboardMode(keyboardMode);
    javaBuilder.confineTo(confineTo);
    javaBuilder.cursor(cursor);
    javaBuilder.time(time);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeBool(ownerEvents);
    out.writeCard16((short) getLength());
    out.writeCard32(grabWindow);
    out.writeCard16(eventMask);
    out.writeByte(pointerMode);
    out.writeByte(keyboardMode);
    out.writeCard32(confineTo);
    out.writeCard32(cursor);
    out.writeCard32(time);
  }

  public boolean isEventMaskEnabled(EventMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (EventMask m : maskEnums) {
      if (!m.isEnabled(eventMask)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 24;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GrabPointerBuilder {
    @java.lang.SuppressWarnings("all")
    private boolean ownerEvents;
    @java.lang.SuppressWarnings("all")
    private int grabWindow;
    @java.lang.SuppressWarnings("all")
    private short eventMask;
    @java.lang.SuppressWarnings("all")
    private byte pointerMode;
    @java.lang.SuppressWarnings("all")
    private byte keyboardMode;
    @java.lang.SuppressWarnings("all")
    private int confineTo;
    @java.lang.SuppressWarnings("all")
    private int cursor;
    @java.lang.SuppressWarnings("all")
    private int time;

    public boolean isEventMaskEnabled(EventMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (EventMask m : maskEnums) {
        if (!m.isEnabled(eventMask)) {
          return false;
        }
      }
      return true;
    }

    public GrabPointer.GrabPointerBuilder eventMaskEnable(EventMask... maskEnums) {
      for (EventMask m : maskEnums) {
        eventMask((short) m.enableFor(eventMask));
      }
      return this;
    }

    public GrabPointer.GrabPointerBuilder eventMaskDisable(EventMask... maskEnums) {
      for (EventMask m : maskEnums) {
        eventMask((short) m.disableFor(eventMask));
      }
      return this;
    }

    public GrabPointer.GrabPointerBuilder pointerMode(GrabMode pointerMode) {
      this.pointerMode = (byte) pointerMode.getValue();
      return this;
    }

    public GrabPointer.GrabPointerBuilder pointerMode(byte pointerMode) {
      this.pointerMode = pointerMode;
      return this;
    }

    public GrabPointer.GrabPointerBuilder keyboardMode(GrabMode keyboardMode) {
      this.keyboardMode = (byte) keyboardMode.getValue();
      return this;
    }

    public GrabPointer.GrabPointerBuilder keyboardMode(byte keyboardMode) {
      this.keyboardMode = keyboardMode;
      return this;
    }

    public int getSize() {
      return 24;
    }

    @java.lang.SuppressWarnings("all")
    GrabPointerBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GrabPointer.GrabPointerBuilder ownerEvents(final boolean ownerEvents) {
      this.ownerEvents = ownerEvents;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GrabPointer.GrabPointerBuilder grabWindow(final int grabWindow) {
      this.grabWindow = grabWindow;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GrabPointer.GrabPointerBuilder eventMask(final short eventMask) {
      this.eventMask = eventMask;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GrabPointer.GrabPointerBuilder confineTo(final int confineTo) {
      this.confineTo = confineTo;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GrabPointer.GrabPointerBuilder cursor(final int cursor) {
      this.cursor = cursor;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GrabPointer.GrabPointerBuilder time(final int time) {
      this.time = time;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GrabPointer build() {
      return new GrabPointer(this.ownerEvents, this.grabWindow, this.eventMask, this.pointerMode, this.keyboardMode, this.confineTo, this.cursor, this.time);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GrabPointer.GrabPointerBuilder(ownerEvents=" + this.ownerEvents + ", grabWindow=" + this.grabWindow + ", eventMask=" + this.eventMask + ", pointerMode=" + this.pointerMode + ", keyboardMode=" + this.keyboardMode + ", confineTo=" + this.confineTo + ", cursor=" + this.cursor + ", time=" + this.time + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GrabPointer(final boolean ownerEvents, final int grabWindow, final short eventMask, final byte pointerMode, final byte keyboardMode, final int confineTo, final int cursor, final int time) {
    this.ownerEvents = ownerEvents;
    this.grabWindow = grabWindow;
    this.eventMask = eventMask;
    this.pointerMode = pointerMode;
    this.keyboardMode = keyboardMode;
    this.confineTo = confineTo;
    this.cursor = cursor;
    this.time = time;
  }

  @java.lang.SuppressWarnings("all")
  public static GrabPointer.GrabPointerBuilder builder() {
    return new GrabPointer.GrabPointerBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public boolean isOwnerEvents() {
    return this.ownerEvents;
  }

  @java.lang.SuppressWarnings("all")
  public int getGrabWindow() {
    return this.grabWindow;
  }

  @java.lang.SuppressWarnings("all")
  public short getEventMask() {
    return this.eventMask;
  }

  @java.lang.SuppressWarnings("all")
  public byte getPointerMode() {
    return this.pointerMode;
  }

  @java.lang.SuppressWarnings("all")
  public byte getKeyboardMode() {
    return this.keyboardMode;
  }

  @java.lang.SuppressWarnings("all")
  public int getConfineTo() {
    return this.confineTo;
  }

  @java.lang.SuppressWarnings("all")
  public int getCursor() {
    return this.cursor;
  }

  @java.lang.SuppressWarnings("all")
  public int getTime() {
    return this.time;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GrabPointer)) return false;
    final GrabPointer other = (GrabPointer) o;
    if (this.isOwnerEvents() != other.isOwnerEvents()) return false;
    if (this.getGrabWindow() != other.getGrabWindow()) return false;
    if (this.getEventMask() != other.getEventMask()) return false;
    if (this.getPointerMode() != other.getPointerMode()) return false;
    if (this.getKeyboardMode() != other.getKeyboardMode()) return false;
    if (this.getConfineTo() != other.getConfineTo()) return false;
    if (this.getCursor() != other.getCursor()) return false;
    if (this.getTime() != other.getTime()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + (this.isOwnerEvents() ? 79 : 97);
    result = result * PRIME + this.getGrabWindow();
    result = result * PRIME + this.getEventMask();
    result = result * PRIME + this.getPointerMode();
    result = result * PRIME + this.getKeyboardMode();
    result = result * PRIME + this.getConfineTo();
    result = result * PRIME + this.getCursor();
    result = result * PRIME + this.getTime();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GrabPointer(ownerEvents=" + this.isOwnerEvents() + ", grabWindow=" + this.getGrabWindow() + ", eventMask=" + this.getEventMask() + ", pointerMode=" + this.getPointerMode() + ", keyboardMode=" + this.getKeyboardMode() + ", confineTo=" + this.getConfineTo() + ", cursor=" + this.getCursor() + ", time=" + this.getTime() + ")";
  }
}
