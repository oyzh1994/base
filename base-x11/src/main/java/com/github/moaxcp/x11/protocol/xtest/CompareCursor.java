package com.github.moaxcp.x11.protocol.xtest;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class CompareCursor implements TwoWayRequest<CompareCursorReply> {
  public static final String PLUGIN_NAME = "xtest";
  public static final byte OPCODE = 1;
  private final int window;
  private final int cursor;

  public XReplyFunction<CompareCursorReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> CompareCursorReply.readCompareCursorReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static CompareCursor readCompareCursor(X11Input in) throws IOException {
    CompareCursor.CompareCursorBuilder javaBuilder = CompareCursor.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    int cursor = in.readCard32();
    javaBuilder.window(window);
    javaBuilder.cursor(cursor);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(cursor);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CompareCursorBuilder {
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private int cursor;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    CompareCursorBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CompareCursor.CompareCursorBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CompareCursor.CompareCursorBuilder cursor(final int cursor) {
      this.cursor = cursor;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CompareCursor build() {
      return new CompareCursor(this.window, this.cursor);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CompareCursor.CompareCursorBuilder(window=" + this.window + ", cursor=" + this.cursor + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CompareCursor(final int window, final int cursor) {
    this.window = window;
    this.cursor = cursor;
  }

  @java.lang.SuppressWarnings("all")
  public static CompareCursor.CompareCursorBuilder builder() {
    return new CompareCursor.CompareCursorBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.SuppressWarnings("all")
  public int getCursor() {
    return this.cursor;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CompareCursor)) return false;
    final CompareCursor other = (CompareCursor) o;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getCursor() != other.getCursor()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getCursor();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CompareCursor(window=" + this.getWindow() + ", cursor=" + this.getCursor() + ")";
  }
}
