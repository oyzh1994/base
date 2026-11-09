package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class ChangeSaveSet implements OneWayRequest {
  public static final String PLUGIN_NAME = "xfixes";
  public static final byte OPCODE = 1;
  private final byte mode;
  private final byte target;
  private final byte map;
  private final int window;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ChangeSaveSet readChangeSaveSet(X11Input in) throws IOException {
    ChangeSaveSet.ChangeSaveSetBuilder javaBuilder = ChangeSaveSet.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    byte mode = in.readByte();
    byte target = in.readByte();
    byte map = in.readByte();
    byte[] pad6 = in.readPad(1);
    int window = in.readCard32();
    javaBuilder.mode(mode);
    javaBuilder.target(target);
    javaBuilder.map(map);
    javaBuilder.window(window);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeByte(mode);
    out.writeByte(target);
    out.writeByte(map);
    out.writePad(1);
    out.writeCard32(window);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ChangeSaveSetBuilder {
    @java.lang.SuppressWarnings("all")
    private byte mode;
    @java.lang.SuppressWarnings("all")
    private byte target;
    @java.lang.SuppressWarnings("all")
    private byte map;
    @java.lang.SuppressWarnings("all")
    private int window;

    public ChangeSaveSet.ChangeSaveSetBuilder mode(SaveSetMode mode) {
      this.mode = (byte) mode.getValue();
      return this;
    }

    public ChangeSaveSet.ChangeSaveSetBuilder mode(byte mode) {
      this.mode = mode;
      return this;
    }

    public ChangeSaveSet.ChangeSaveSetBuilder target(SaveSetTarget target) {
      this.target = (byte) target.getValue();
      return this;
    }

    public ChangeSaveSet.ChangeSaveSetBuilder target(byte target) {
      this.target = target;
      return this;
    }

    public ChangeSaveSet.ChangeSaveSetBuilder map(SaveSetMapping map) {
      this.map = (byte) map.getValue();
      return this;
    }

    public ChangeSaveSet.ChangeSaveSetBuilder map(byte map) {
      this.map = map;
      return this;
    }

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    ChangeSaveSetBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangeSaveSet.ChangeSaveSetBuilder window(final int window) {
      this.window = window;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ChangeSaveSet build() {
      return new ChangeSaveSet(this.mode, this.target, this.map, this.window);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ChangeSaveSet.ChangeSaveSetBuilder(mode=" + this.mode + ", target=" + this.target + ", map=" + this.map + ", window=" + this.window + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ChangeSaveSet(final byte mode, final byte target, final byte map, final int window) {
    this.mode = mode;
    this.target = target;
    this.map = map;
    this.window = window;
  }

  @java.lang.SuppressWarnings("all")
  public static ChangeSaveSet.ChangeSaveSetBuilder builder() {
    return new ChangeSaveSet.ChangeSaveSetBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getMode() {
    return this.mode;
  }

  @java.lang.SuppressWarnings("all")
  public byte getTarget() {
    return this.target;
  }

  @java.lang.SuppressWarnings("all")
  public byte getMap() {
    return this.map;
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ChangeSaveSet)) return false;
    final ChangeSaveSet other = (ChangeSaveSet) o;
    if (this.getMode() != other.getMode()) return false;
    if (this.getTarget() != other.getTarget()) return false;
    if (this.getMap() != other.getMap()) return false;
    if (this.getWindow() != other.getWindow()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getMode();
    result = result * PRIME + this.getTarget();
    result = result * PRIME + this.getMap();
    result = result * PRIME + this.getWindow();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ChangeSaveSet(mode=" + this.getMode() + ", target=" + this.getTarget() + ", map=" + this.getMap() + ", window=" + this.getWindow() + ")";
  }
}
