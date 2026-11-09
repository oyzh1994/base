package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class ChangeActivePointerGrab implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 30;
  private final int cursor;
  private final int time;
  private final short eventMask;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ChangeActivePointerGrab readChangeActivePointerGrab(X11Input in) throws IOException {
    ChangeActivePointerGrab.ChangeActivePointerGrabBuilder javaBuilder = ChangeActivePointerGrab.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int cursor = in.readCard32();
    int time = in.readCard32();
    short eventMask = in.readCard16();
    byte[] pad6 = in.readPad(2);
    javaBuilder.cursor(cursor);
    javaBuilder.time(time);
    javaBuilder.eventMask(eventMask);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(cursor);
    out.writeCard32(time);
    out.writeCard16(eventMask);
    out.writePad(2);
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
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ChangeActivePointerGrabBuilder {
    @java.lang.SuppressWarnings("all")
    private int cursor;
    @java.lang.SuppressWarnings("all")
    private int time;
    @java.lang.SuppressWarnings("all")
    private short eventMask;

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

    public ChangeActivePointerGrab.ChangeActivePointerGrabBuilder eventMaskEnable(EventMask... maskEnums) {
      for (EventMask m : maskEnums) {
        eventMask((short) m.enableFor(eventMask));
      }
      return this;
    }

    public ChangeActivePointerGrab.ChangeActivePointerGrabBuilder eventMaskDisable(EventMask... maskEnums) {
      for (EventMask m : maskEnums) {
        eventMask((short) m.disableFor(eventMask));
      }
      return this;
    }

    public int getSize() {
      return 16;
    }

    @java.lang.SuppressWarnings("all")
    ChangeActivePointerGrabBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangeActivePointerGrab.ChangeActivePointerGrabBuilder cursor(final int cursor) {
      this.cursor = cursor;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangeActivePointerGrab.ChangeActivePointerGrabBuilder time(final int time) {
      this.time = time;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangeActivePointerGrab.ChangeActivePointerGrabBuilder eventMask(final short eventMask) {
      this.eventMask = eventMask;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ChangeActivePointerGrab build() {
      return new ChangeActivePointerGrab(this.cursor, this.time, this.eventMask);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ChangeActivePointerGrab.ChangeActivePointerGrabBuilder(cursor=" + this.cursor + ", time=" + this.time + ", eventMask=" + this.eventMask + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ChangeActivePointerGrab(final int cursor, final int time, final short eventMask) {
    this.cursor = cursor;
    this.time = time;
    this.eventMask = eventMask;
  }

  @java.lang.SuppressWarnings("all")
  public static ChangeActivePointerGrab.ChangeActivePointerGrabBuilder builder() {
    return new ChangeActivePointerGrab.ChangeActivePointerGrabBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getCursor() {
    return this.cursor;
  }

  @java.lang.SuppressWarnings("all")
  public int getTime() {
    return this.time;
  }

  @java.lang.SuppressWarnings("all")
  public short getEventMask() {
    return this.eventMask;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ChangeActivePointerGrab)) return false;
    final ChangeActivePointerGrab other = (ChangeActivePointerGrab) o;
    if (this.getCursor() != other.getCursor()) return false;
    if (this.getTime() != other.getTime()) return false;
    if (this.getEventMask() != other.getEventMask()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getCursor();
    result = result * PRIME + this.getTime();
    result = result * PRIME + this.getEventMask();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ChangeActivePointerGrab(cursor=" + this.getCursor() + ", time=" + this.getTime() + ", eventMask=" + this.getEventMask() + ")";
  }
}
