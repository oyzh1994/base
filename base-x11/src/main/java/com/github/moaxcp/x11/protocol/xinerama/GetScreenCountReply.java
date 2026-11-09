package com.github.moaxcp.x11.protocol.xinerama;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class GetScreenCountReply implements XReply {
  public static final String PLUGIN_NAME = "xinerama";
  private final byte screenCount;
  private final short sequenceNumber;
  private final int window;

  public static GetScreenCountReply readGetScreenCountReply(byte screenCount, short sequenceNumber, X11Input in) throws IOException {
    GetScreenCountReply.GetScreenCountReplyBuilder javaBuilder = GetScreenCountReply.builder();
    int length = in.readCard32();
    int window = in.readCard32();
    in.readPad(20);
    javaBuilder.screenCount(screenCount);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.window(window);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeByte(screenCount);
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


  public static class GetScreenCountReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte screenCount;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int window;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    GetScreenCountReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenCountReply.GetScreenCountReplyBuilder screenCount(final byte screenCount) {
      this.screenCount = screenCount;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenCountReply.GetScreenCountReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenCountReply.GetScreenCountReplyBuilder window(final int window) {
      this.window = window;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetScreenCountReply build() {
      return new GetScreenCountReply(this.screenCount, this.sequenceNumber, this.window);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetScreenCountReply.GetScreenCountReplyBuilder(screenCount=" + this.screenCount + ", sequenceNumber=" + this.sequenceNumber + ", window=" + this.window + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetScreenCountReply(final byte screenCount, final short sequenceNumber, final int window) {
    this.screenCount = screenCount;
    this.sequenceNumber = sequenceNumber;
    this.window = window;
  }

  @java.lang.SuppressWarnings("all")
  public static GetScreenCountReply.GetScreenCountReplyBuilder builder() {
    return new GetScreenCountReply.GetScreenCountReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getScreenCount() {
    return this.screenCount;
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
    if (!(o instanceof GetScreenCountReply)) return false;
    final GetScreenCountReply other = (GetScreenCountReply) o;
    if (this.getScreenCount() != other.getScreenCount()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getWindow() != other.getWindow()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getScreenCount();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getWindow();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetScreenCountReply(screenCount=" + this.getScreenCount() + ", sequenceNumber=" + this.getSequenceNumber() + ", window=" + this.getWindow() + ")";
  }
}
