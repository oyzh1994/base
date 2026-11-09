package com.github.moaxcp.x11.protocol.xinerama;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class GetStateReply implements XReply {
  public static final String PLUGIN_NAME = "xinerama";
  private final byte state;
  private final short sequenceNumber;
  private final int window;

  public static GetStateReply readGetStateReply(byte state, short sequenceNumber, X11Input in) throws IOException {
    GetStateReply.GetStateReplyBuilder javaBuilder = GetStateReply.builder();
    int length = in.readCard32();
    int window = in.readCard32();
    in.readPad(20);
    javaBuilder.state(state);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.window(window);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeByte(state);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(window);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetStateReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte state;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int window;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    GetStateReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetStateReply.GetStateReplyBuilder state(final byte state) {
      this.state = state;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetStateReply.GetStateReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetStateReply.GetStateReplyBuilder window(final int window) {
      this.window = window;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetStateReply build() {
      return new GetStateReply(this.state, this.sequenceNumber, this.window);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetStateReply.GetStateReplyBuilder(state=" + this.state + ", sequenceNumber=" + this.sequenceNumber + ", window=" + this.window + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetStateReply(final byte state, final short sequenceNumber, final int window) {
    this.state = state;
    this.sequenceNumber = sequenceNumber;
    this.window = window;
  }

  @java.lang.SuppressWarnings("all")
  public static GetStateReply.GetStateReplyBuilder builder() {
    return new GetStateReply.GetStateReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getState() {
    return this.state;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetStateReply)) return false;
    final GetStateReply other = (GetStateReply) o;
    if (this.getState() != other.getState()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getWindow() != other.getWindow()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getState();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getWindow();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetStateReply(state=" + this.getState() + ", sequenceNumber=" + this.getSequenceNumber() + ", window=" + this.getWindow() + ")";
  }
}
