package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;

public final class ClientMessageEvent implements XEvent {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte NUMBER = 33;
  private final byte firstEventOffset;
  private final boolean sentEvent;
  private final byte format;
  private final short sequenceNumber;
  private final int window;
  private final int type;
  private final ClientMessageDataUnion data;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static ClientMessageEvent readClientMessageEvent(byte firstEventOffset, boolean sentEvent, X11Input in) throws IOException {
    ClientMessageEvent.ClientMessageEventBuilder javaBuilder = ClientMessageEvent.builder();
    byte format = in.readCard8();
    short sequenceNumber = in.readCard16();
    int window = in.readCard32();
    int type = in.readCard32();
    ClientMessageDataUnion data = ClientMessageDataUnion.readClientMessageDataUnion(in, format);
    javaBuilder.format(format);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.window(window);
    javaBuilder.type(type);
    javaBuilder.data(data);
    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (128 | getResponseCode()) : getResponseCode());
    out.writeCard8(format);
    out.writeCard16(sequenceNumber);
    out.writeCard32(window);
    out.writeCard32(type);
    data.write(out);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ClientMessageEventBuilder {
    @java.lang.SuppressWarnings("all")
    private byte firstEventOffset;
    @java.lang.SuppressWarnings("all")
    private boolean sentEvent;
    @java.lang.SuppressWarnings("all")
    private byte format;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private int type;
    @java.lang.SuppressWarnings("all")
    private ClientMessageDataUnion data;

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    ClientMessageEventBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ClientMessageEvent.ClientMessageEventBuilder firstEventOffset(final byte firstEventOffset) {
      this.firstEventOffset = firstEventOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ClientMessageEvent.ClientMessageEventBuilder sentEvent(final boolean sentEvent) {
      this.sentEvent = sentEvent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ClientMessageEvent.ClientMessageEventBuilder format(final byte format) {
      this.format = format;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ClientMessageEvent.ClientMessageEventBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ClientMessageEvent.ClientMessageEventBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ClientMessageEvent.ClientMessageEventBuilder type(final int type) {
      this.type = type;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ClientMessageEvent.ClientMessageEventBuilder data(final ClientMessageDataUnion data) {
      if (data == null) {
        throw new java.lang.NullPointerException("data is marked non-null but is null");
      }
      this.data = data;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ClientMessageEvent build() {
      return new ClientMessageEvent(this.firstEventOffset, this.sentEvent, this.format, this.sequenceNumber, this.window, this.type, this.data);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ClientMessageEvent.ClientMessageEventBuilder(firstEventOffset=" + this.firstEventOffset + ", sentEvent=" + this.sentEvent + ", format=" + this.format + ", sequenceNumber=" + this.sequenceNumber + ", window=" + this.window + ", type=" + this.type + ", data=" + this.data + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ClientMessageEvent(final byte firstEventOffset, final boolean sentEvent, final byte format, final short sequenceNumber, final int window, final int type, final ClientMessageDataUnion data) {
    if (data == null) {
      throw new java.lang.NullPointerException("data is marked non-null but is null");
    }
    this.firstEventOffset = firstEventOffset;
    this.sentEvent = sentEvent;
    this.format = format;
    this.sequenceNumber = sequenceNumber;
    this.window = window;
    this.type = type;
    this.data = data;
  }

  @java.lang.SuppressWarnings("all")
  public static ClientMessageEvent.ClientMessageEventBuilder builder() {
    return new ClientMessageEvent.ClientMessageEventBuilder();
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
  public byte getFormat() {
    return this.format;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.SuppressWarnings("all")
  public int getType() {
    return this.type;
  }

  @java.lang.SuppressWarnings("all")
  public ClientMessageDataUnion getData() {
    return this.data;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ClientMessageEvent)) return false;
    final ClientMessageEvent other = (ClientMessageEvent) o;
    if (this.getFirstEventOffset() != other.getFirstEventOffset()) return false;
    if (this.isSentEvent() != other.isSentEvent()) return false;
    if (this.getFormat() != other.getFormat()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getType() != other.getType()) return false;
    final java.lang.Object this$data = this.getData();
    final java.lang.Object other$data = other.getData();
    if (this$data == null ? other$data != null : !this$data.equals(other$data)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFirstEventOffset();
    result = result * PRIME + (this.isSentEvent() ? 79 : 97);
    result = result * PRIME + this.getFormat();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getType();
    final java.lang.Object $data = this.getData();
    result = result * PRIME + ($data == null ? 43 : $data.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ClientMessageEvent(firstEventOffset=" + this.getFirstEventOffset() + ", sentEvent=" + this.isSentEvent() + ", format=" + this.getFormat() + ", sequenceNumber=" + this.getSequenceNumber() + ", window=" + this.getWindow() + ", type=" + this.getType() + ", data=" + this.getData() + ")";
  }
}
