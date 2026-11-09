package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public final class GetMotionEventsReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";
  private final short sequenceNumber;
  private final ImmutableList<Timecoord> events;

  public static GetMotionEventsReply readGetMotionEventsReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    GetMotionEventsReply.GetMotionEventsReplyBuilder javaBuilder = GetMotionEventsReply.builder();
    int length = in.readCard32();
    int eventsLen = in.readCard32();
    byte[] pad5 = in.readPad(20);
    MutableList<Timecoord> events = Lists.mutable.withInitialCapacity((int) (Integer.toUnsignedLong(eventsLen)));
    for (int i = 0; i < Integer.toUnsignedLong(eventsLen); i++) {
      events.add(Timecoord.readTimecoord(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.events(events.toImmutable());
    if (javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    int eventsLen = events.size();
    out.writeCard32(eventsLen);
    out.writePad(20);
    for (Timecoord t : events) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(events);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetMotionEventsReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<Timecoord> events;

    public int getSize() {
      return 32 + XObject.sizeOf(events);
    }

    @java.lang.SuppressWarnings("all")
    GetMotionEventsReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMotionEventsReply.GetMotionEventsReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMotionEventsReply.GetMotionEventsReplyBuilder events(final ImmutableList<Timecoord> events) {
      if (events == null) {
        throw new java.lang.NullPointerException("events is marked non-null but is null");
      }
      this.events = events;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetMotionEventsReply build() {
      return new GetMotionEventsReply(this.sequenceNumber, this.events);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetMotionEventsReply.GetMotionEventsReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", events=" + this.events + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetMotionEventsReply(final short sequenceNumber, final ImmutableList<Timecoord> events) {
    if (events == null) {
      throw new java.lang.NullPointerException("events is marked non-null but is null");
    }
    this.sequenceNumber = sequenceNumber;
    this.events = events;
  }

  @java.lang.SuppressWarnings("all")
  public static GetMotionEventsReply.GetMotionEventsReplyBuilder builder() {
    return new GetMotionEventsReply.GetMotionEventsReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<Timecoord> getEvents() {
    return this.events;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetMotionEventsReply)) return false;
    final GetMotionEventsReply other = (GetMotionEventsReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    final java.lang.Object this$events = this.getEvents();
    final java.lang.Object other$events = other.getEvents();
    if (this$events == null ? other$events != null : !this$events.equals(other$events)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    final java.lang.Object $events = this.getEvents();
    result = result * PRIME + ($events == null ? 43 : $events.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetMotionEventsReply(sequenceNumber=" + this.getSequenceNumber() + ", events=" + this.getEvents() + ")";
  }
}
