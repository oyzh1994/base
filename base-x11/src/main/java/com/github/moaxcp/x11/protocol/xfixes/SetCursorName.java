package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.ByteList;

public final class SetCursorName implements OneWayRequest {
  public static final String PLUGIN_NAME = "xfixes";
  public static final byte OPCODE = 23;
  private final int cursor;
  private final ByteList name;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetCursorName readSetCursorName(X11Input in) throws IOException {
    SetCursorName.SetCursorNameBuilder javaBuilder = SetCursorName.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int cursor = in.readCard32();
    short nbytes = in.readCard16();
    byte[] pad5 = in.readPad(2);
    ByteList name = in.readChar(Short.toUnsignedInt(nbytes));
    javaBuilder.cursor(cursor);
    javaBuilder.name(name.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(cursor);
    short nbytes = (short) name.size();
    out.writeCard16(nbytes);
    out.writePad(2);
    out.writeChar(name);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + 1 * name.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetCursorNameBuilder {
    @java.lang.SuppressWarnings("all")
    private int cursor;
    @java.lang.SuppressWarnings("all")
    private ByteList name;

    public int getSize() {
      return 12 + 1 * name.size();
    }

    @java.lang.SuppressWarnings("all")
    SetCursorNameBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetCursorName.SetCursorNameBuilder cursor(final int cursor) {
      this.cursor = cursor;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetCursorName.SetCursorNameBuilder name(final ByteList name) {
      if (name == null) {
        throw new java.lang.NullPointerException("name is marked non-null but is null");
      }
      this.name = name;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetCursorName build() {
      return new SetCursorName(this.cursor, this.name);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetCursorName.SetCursorNameBuilder(cursor=" + this.cursor + ", name=" + this.name + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetCursorName(final int cursor, final ByteList name) {
    if (name == null) {
      throw new java.lang.NullPointerException("name is marked non-null but is null");
    }
    this.cursor = cursor;
    this.name = name;
  }

  @java.lang.SuppressWarnings("all")
  public static SetCursorName.SetCursorNameBuilder builder() {
    return new SetCursorName.SetCursorNameBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getCursor() {
    return this.cursor;
  }

  @java.lang.SuppressWarnings("all")
  public ByteList getName() {
    return this.name;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetCursorName)) return false;
    final SetCursorName other = (SetCursorName) o;
    if (this.getCursor() != other.getCursor()) return false;
    final java.lang.Object this$name = this.getName();
    final java.lang.Object other$name = other.getName();
    if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getCursor();
    final java.lang.Object $name = this.getName();
    result = result * PRIME + ($name == null ? 43 : $name.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetCursorName(cursor=" + this.getCursor() + ", name=" + this.getName() + ")";
  }
}
