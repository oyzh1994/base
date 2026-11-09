package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;

public final class NotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "randr";
  public static final byte NUMBER = 1;
  private final byte firstEventOffset;
  private final boolean sentEvent;
  private final byte subCode;
  private final short sequenceNumber;
  private final NotifyDataUnion u;

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
    byte subCode = in.readCard8();
    short sequenceNumber = in.readCard16();
    NotifyDataUnion u = NotifyDataUnion.readNotifyDataUnion(in, subCode);
    javaBuilder.subCode(subCode);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.u(u);
    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (128 | getResponseCode()) : getResponseCode());
    out.writeCard8(subCode);
    out.writeCard16(sequenceNumber);
    u.write(out);
  }

  @Override
  public int getSize() {
    return 200;
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
    private byte subCode;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private NotifyDataUnion u;

    public NotifyEvent.NotifyEventBuilder subCode(Notify subCode) {
      this.subCode = (byte) subCode.getValue();
      return this;
    }

    public NotifyEvent.NotifyEventBuilder subCode(byte subCode) {
      this.subCode = subCode;
      return this;
    }

    public int getSize() {
      return 200;
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
    public NotifyEvent.NotifyEventBuilder u(final NotifyDataUnion u) {
      if (u == null) {
        throw new java.lang.NullPointerException("u is marked non-null but is null");
      }
      this.u = u;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public NotifyEvent build() {
      return new NotifyEvent(this.firstEventOffset, this.sentEvent, this.subCode, this.sequenceNumber, this.u);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "NotifyEvent.NotifyEventBuilder(firstEventOffset=" + this.firstEventOffset + ", sentEvent=" + this.sentEvent + ", subCode=" + this.subCode + ", sequenceNumber=" + this.sequenceNumber + ", u=" + this.u + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  NotifyEvent(final byte firstEventOffset, final boolean sentEvent, final byte subCode, final short sequenceNumber, final NotifyDataUnion u) {
    if (u == null) {
      throw new java.lang.NullPointerException("u is marked non-null but is null");
    }
    this.firstEventOffset = firstEventOffset;
    this.sentEvent = sentEvent;
    this.subCode = subCode;
    this.sequenceNumber = sequenceNumber;
    this.u = u;
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
  public byte getSubCode() {
    return this.subCode;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public NotifyDataUnion getU() {
    return this.u;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof NotifyEvent)) return false;
    final NotifyEvent other = (NotifyEvent) o;
    if (this.getFirstEventOffset() != other.getFirstEventOffset()) return false;
    if (this.isSentEvent() != other.isSentEvent()) return false;
    if (this.getSubCode() != other.getSubCode()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    final java.lang.Object this$u = this.getU();
    final java.lang.Object other$u = other.getU();
    if (this$u == null ? other$u != null : !this$u.equals(other$u)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFirstEventOffset();
    result = result * PRIME + (this.isSentEvent() ? 79 : 97);
    result = result * PRIME + this.getSubCode();
    result = result * PRIME + this.getSequenceNumber();
    final java.lang.Object $u = this.getU();
    result = result * PRIME + ($u == null ? 43 : $u.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "NotifyEvent(firstEventOffset=" + this.getFirstEventOffset() + ", sentEvent=" + this.isSentEvent() + ", subCode=" + this.getSubCode() + ", sequenceNumber=" + this.getSequenceNumber() + ", u=" + this.getU() + ")";
  }
}
