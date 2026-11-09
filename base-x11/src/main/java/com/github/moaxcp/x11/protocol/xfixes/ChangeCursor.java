package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class ChangeCursor implements OneWayRequest {
  public static final String PLUGIN_NAME = "xfixes";
  public static final byte OPCODE = 26;
  private final int source;
  private final int destination;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ChangeCursor readChangeCursor(X11Input in) throws IOException {
    ChangeCursor.ChangeCursorBuilder javaBuilder = ChangeCursor.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int source = in.readCard32();
    int destination = in.readCard32();
    javaBuilder.source(source);
    javaBuilder.destination(destination);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(source);
    out.writeCard32(destination);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ChangeCursorBuilder {
    @java.lang.SuppressWarnings("all")
    private int source;
    @java.lang.SuppressWarnings("all")
    private int destination;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    ChangeCursorBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangeCursor.ChangeCursorBuilder source(final int source) {
      this.source = source;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangeCursor.ChangeCursorBuilder destination(final int destination) {
      this.destination = destination;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ChangeCursor build() {
      return new ChangeCursor(this.source, this.destination);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ChangeCursor.ChangeCursorBuilder(source=" + this.source + ", destination=" + this.destination + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ChangeCursor(final int source, final int destination) {
    this.source = source;
    this.destination = destination;
  }

  @java.lang.SuppressWarnings("all")
  public static ChangeCursor.ChangeCursorBuilder builder() {
    return new ChangeCursor.ChangeCursorBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getSource() {
    return this.source;
  }

  @java.lang.SuppressWarnings("all")
  public int getDestination() {
    return this.destination;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ChangeCursor)) return false;
    final ChangeCursor other = (ChangeCursor) o;
    if (this.getSource() != other.getSource()) return false;
    if (this.getDestination() != other.getDestination()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSource();
    result = result * PRIME + this.getDestination();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ChangeCursor(source=" + this.getSource() + ", destination=" + this.getDestination() + ")";
  }
}
