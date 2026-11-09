package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;

public final class MappingNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte NUMBER = 34;
  private final byte firstEventOffset;
  private final boolean sentEvent;
  private final short sequenceNumber;
  private final byte request;
  private final byte firstKeycode;
  private final byte count;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static MappingNotifyEvent readMappingNotifyEvent(byte firstEventOffset, boolean sentEvent, X11Input in) throws IOException {
    MappingNotifyEvent.MappingNotifyEventBuilder javaBuilder = MappingNotifyEvent.builder();
    byte[] pad1 = in.readPad(1);
    short sequenceNumber = in.readCard16();
    byte request = in.readByte();
    byte firstKeycode = in.readCard8();
    byte count = in.readCard8();
    byte[] pad6 = in.readPad(1);
    byte[] pad7 = in.readPad(24);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.request(request);
    javaBuilder.firstKeycode(firstKeycode);
    javaBuilder.count(count);
    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (128 | getResponseCode()) : getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeByte(request);
    out.writeCard8(firstKeycode);
    out.writeCard8(count);
    out.writePad(1);
    out.writePad(24);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class MappingNotifyEventBuilder {
    @java.lang.SuppressWarnings("all")
    private byte firstEventOffset;
    @java.lang.SuppressWarnings("all")
    private boolean sentEvent;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private byte request;
    @java.lang.SuppressWarnings("all")
    private byte firstKeycode;
    @java.lang.SuppressWarnings("all")
    private byte count;

    public MappingNotifyEvent.MappingNotifyEventBuilder request(Mapping request) {
      this.request = (byte) request.getValue();
      return this;
    }

    public MappingNotifyEvent.MappingNotifyEventBuilder request(byte request) {
      this.request = request;
      return this;
    }

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    MappingNotifyEventBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MappingNotifyEvent.MappingNotifyEventBuilder firstEventOffset(final byte firstEventOffset) {
      this.firstEventOffset = firstEventOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MappingNotifyEvent.MappingNotifyEventBuilder sentEvent(final boolean sentEvent) {
      this.sentEvent = sentEvent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MappingNotifyEvent.MappingNotifyEventBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MappingNotifyEvent.MappingNotifyEventBuilder firstKeycode(final byte firstKeycode) {
      this.firstKeycode = firstKeycode;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public MappingNotifyEvent.MappingNotifyEventBuilder count(final byte count) {
      this.count = count;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public MappingNotifyEvent build() {
      return new MappingNotifyEvent(this.firstEventOffset, this.sentEvent, this.sequenceNumber, this.request, this.firstKeycode, this.count);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "MappingNotifyEvent.MappingNotifyEventBuilder(firstEventOffset=" + this.firstEventOffset + ", sentEvent=" + this.sentEvent + ", sequenceNumber=" + this.sequenceNumber + ", request=" + this.request + ", firstKeycode=" + this.firstKeycode + ", count=" + this.count + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  MappingNotifyEvent(final byte firstEventOffset, final boolean sentEvent, final short sequenceNumber, final byte request, final byte firstKeycode, final byte count) {
    this.firstEventOffset = firstEventOffset;
    this.sentEvent = sentEvent;
    this.sequenceNumber = sequenceNumber;
    this.request = request;
    this.firstKeycode = firstKeycode;
    this.count = count;
  }

  @java.lang.SuppressWarnings("all")
  public static MappingNotifyEvent.MappingNotifyEventBuilder builder() {
    return new MappingNotifyEvent.MappingNotifyEventBuilder();
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
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public byte getRequest() {
    return this.request;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFirstKeycode() {
    return this.firstKeycode;
  }

  @java.lang.SuppressWarnings("all")
  public byte getCount() {
    return this.count;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof MappingNotifyEvent)) return false;
    final MappingNotifyEvent other = (MappingNotifyEvent) o;
    if (this.getFirstEventOffset() != other.getFirstEventOffset()) return false;
    if (this.isSentEvent() != other.isSentEvent()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getRequest() != other.getRequest()) return false;
    if (this.getFirstKeycode() != other.getFirstKeycode()) return false;
    if (this.getCount() != other.getCount()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFirstEventOffset();
    result = result * PRIME + (this.isSentEvent() ? 79 : 97);
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getRequest();
    result = result * PRIME + this.getFirstKeycode();
    result = result * PRIME + this.getCount();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "MappingNotifyEvent(firstEventOffset=" + this.getFirstEventOffset() + ", sentEvent=" + this.isSentEvent() + ", sequenceNumber=" + this.getSequenceNumber() + ", request=" + this.getRequest() + ", firstKeycode=" + this.getFirstKeycode() + ", count=" + this.getCount() + ")";
  }
}
