package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class SendEvent implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 25;
  private final boolean propagate;
  private final int destination;
  private final int eventMask;
  private final byte[] event;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SendEvent readSendEvent(X11Input in) throws IOException {
    SendEvent.SendEventBuilder javaBuilder = SendEvent.builder();
    boolean propagate = in.readBool();
    short length = in.readCard16();
    int destination = in.readCard32();
    int eventMask = in.readCard32();
    byte[] event = in.readChar(32);
    javaBuilder.propagate(propagate);
    javaBuilder.destination(destination);
    javaBuilder.eventMask(eventMask);
    javaBuilder.event(event);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeBool(propagate);
    out.writeCard16((short) getLength());
    out.writeCard32(destination);
    out.writeCard32(eventMask);
    out.writeChar(event);
    out.writePadAlign(getSize());
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
    return 12 + 1 * event.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SendEventBuilder {
    @java.lang.SuppressWarnings("all")
    private boolean propagate;
    @java.lang.SuppressWarnings("all")
    private int destination;
    @java.lang.SuppressWarnings("all")
    private int eventMask;
    @java.lang.SuppressWarnings("all")
    private byte[] event;

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

    public SendEvent.SendEventBuilder eventMaskEnable(EventMask... maskEnums) {
      for (EventMask m : maskEnums) {
        eventMask((int) m.enableFor(eventMask));
      }
      return this;
    }

    public SendEvent.SendEventBuilder eventMaskDisable(EventMask... maskEnums) {
      for (EventMask m : maskEnums) {
        eventMask((int) m.disableFor(eventMask));
      }
      return this;
    }

    public int getSize() {
      return 12 + 1 * event.length;
    }

    @java.lang.SuppressWarnings("all")
    SendEventBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SendEvent.SendEventBuilder propagate(final boolean propagate) {
      this.propagate = propagate;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SendEvent.SendEventBuilder destination(final int destination) {
      this.destination = destination;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SendEvent.SendEventBuilder eventMask(final int eventMask) {
      this.eventMask = eventMask;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SendEvent.SendEventBuilder event(final byte[] event) {
      if (event == null) {
        throw new java.lang.NullPointerException("event is marked non-null but is null");
      }
      this.event = event;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SendEvent build() {
      return new SendEvent(this.propagate, this.destination, this.eventMask, this.event);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SendEvent.SendEventBuilder(propagate=" + this.propagate + ", destination=" + this.destination + ", eventMask=" + this.eventMask + ", event=" + java.util.Arrays.toString(this.event) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SendEvent(final boolean propagate, final int destination, final int eventMask, final byte[] event) {
    if (event == null) {
      throw new java.lang.NullPointerException("event is marked non-null but is null");
    }
    this.propagate = propagate;
    this.destination = destination;
    this.eventMask = eventMask;
    this.event = event;
  }

  @java.lang.SuppressWarnings("all")
  public static SendEvent.SendEventBuilder builder() {
    return new SendEvent.SendEventBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public boolean isPropagate() {
    return this.propagate;
  }

  @java.lang.SuppressWarnings("all")
  public int getDestination() {
    return this.destination;
  }

  @java.lang.SuppressWarnings("all")
  public int getEventMask() {
    return this.eventMask;
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getEvent() {
    return this.event;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SendEvent)) return false;
    final SendEvent other = (SendEvent) o;
    if (this.isPropagate() != other.isPropagate()) return false;
    if (this.getDestination() != other.getDestination()) return false;
    if (this.getEventMask() != other.getEventMask()) return false;
    if (!java.util.Arrays.equals(this.getEvent(), other.getEvent())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + (this.isPropagate() ? 79 : 97);
    result = result * PRIME + this.getDestination();
    result = result * PRIME + this.getEventMask();
    result = result * PRIME + java.util.Arrays.hashCode(this.getEvent());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SendEvent(propagate=" + this.isPropagate() + ", destination=" + this.getDestination() + ", eventMask=" + this.getEventMask() + ", event=" + java.util.Arrays.toString(this.getEvent()) + ")";
  }
}
