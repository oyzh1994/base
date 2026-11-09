package com.github.moaxcp.x11.protocol.xinerama;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class GetScreenSizeReply implements XReply {
  public static final String PLUGIN_NAME = "xinerama";
  private final short sequenceNumber;
  private final int width;
  private final int height;
  private final int window;
  private final int screen;

  public static GetScreenSizeReply readGetScreenSizeReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    GetScreenSizeReply.GetScreenSizeReplyBuilder javaBuilder = GetScreenSizeReply.builder();
    int length = in.readCard32();
    int width = in.readCard32();
    int height = in.readCard32();
    int window = in.readCard32();
    int screen = in.readCard32();
    in.readPad(8);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.window(window);
    javaBuilder.screen(screen);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(width);
    out.writeCard32(height);
    out.writeCard32(window);
    out.writeCard32(screen);
  }

  @Override
  public int getSize() {
    return 24;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetScreenSizeReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int width;
    @java.lang.SuppressWarnings("all")
    private int height;
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private int screen;

    public int getSize() {
      return 24;
    }

    @java.lang.SuppressWarnings("all")
    GetScreenSizeReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenSizeReply.GetScreenSizeReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenSizeReply.GetScreenSizeReplyBuilder width(final int width) {
      this.width = width;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenSizeReply.GetScreenSizeReplyBuilder height(final int height) {
      this.height = height;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenSizeReply.GetScreenSizeReplyBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetScreenSizeReply.GetScreenSizeReplyBuilder screen(final int screen) {
      this.screen = screen;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetScreenSizeReply build() {
      return new GetScreenSizeReply(this.sequenceNumber, this.width, this.height, this.window, this.screen);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetScreenSizeReply.GetScreenSizeReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", width=" + this.width + ", height=" + this.height + ", window=" + this.window + ", screen=" + this.screen + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetScreenSizeReply(final short sequenceNumber, final int width, final int height, final int window, final int screen) {
    this.sequenceNumber = sequenceNumber;
    this.width = width;
    this.height = height;
    this.window = window;
    this.screen = screen;
  }

  @java.lang.SuppressWarnings("all")
  public static GetScreenSizeReply.GetScreenSizeReplyBuilder builder() {
    return new GetScreenSizeReply.GetScreenSizeReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getWidth() {
    return this.width;
  }

  @java.lang.SuppressWarnings("all")
  public int getHeight() {
    return this.height;
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.SuppressWarnings("all")
  public int getScreen() {
    return this.screen;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetScreenSizeReply)) return false;
    final GetScreenSizeReply other = (GetScreenSizeReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getWidth() != other.getWidth()) return false;
    if (this.getHeight() != other.getHeight()) return false;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getScreen() != other.getScreen()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getWidth();
    result = result * PRIME + this.getHeight();
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getScreen();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetScreenSizeReply(sequenceNumber=" + this.getSequenceNumber() + ", width=" + this.getWidth() + ", height=" + this.getHeight() + ", window=" + this.getWindow() + ", screen=" + this.getScreen() + ")";
  }
}
