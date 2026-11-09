package com.github.moaxcp.x11.protocol.xfixes;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.ByteList;

public final class ChangeCursorByName implements OneWayRequest {
  public static final String PLUGIN_NAME = "xfixes";
  public static final byte OPCODE = 27;
  private final int src;
  private final ByteList name;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ChangeCursorByName readChangeCursorByName(X11Input in) throws IOException {
    ChangeCursorByName.ChangeCursorByNameBuilder javaBuilder = ChangeCursorByName.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int src = in.readCard32();
    short nbytes = in.readCard16();
    byte[] pad5 = in.readPad(2);
    ByteList name = in.readChar(Short.toUnsignedInt(nbytes));
    javaBuilder.src(src);
    javaBuilder.name(name.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(src);
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


  public static class ChangeCursorByNameBuilder {
    @java.lang.SuppressWarnings("all")
    private int src;
    @java.lang.SuppressWarnings("all")
    private ByteList name;

    public int getSize() {
      return 12 + 1 * name.size();
    }

    @java.lang.SuppressWarnings("all")
    ChangeCursorByNameBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangeCursorByName.ChangeCursorByNameBuilder src(final int src) {
      this.src = src;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangeCursorByName.ChangeCursorByNameBuilder name(final ByteList name) {
      if (name == null) {
        throw new java.lang.NullPointerException("name is marked non-null but is null");
      }
      this.name = name;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ChangeCursorByName build() {
      return new ChangeCursorByName(this.src, this.name);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ChangeCursorByName.ChangeCursorByNameBuilder(src=" + this.src + ", name=" + this.name + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ChangeCursorByName(final int src, final ByteList name) {
    if (name == null) {
      throw new java.lang.NullPointerException("name is marked non-null but is null");
    }
    this.src = src;
    this.name = name;
  }

  @java.lang.SuppressWarnings("all")
  public static ChangeCursorByName.ChangeCursorByNameBuilder builder() {
    return new ChangeCursorByName.ChangeCursorByNameBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getSrc() {
    return this.src;
  }

  @java.lang.SuppressWarnings("all")
  public ByteList getName() {
    return this.name;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ChangeCursorByName)) return false;
    final ChangeCursorByName other = (ChangeCursorByName) o;
    if (this.getSrc() != other.getSrc()) return false;
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
    result = result * PRIME + this.getSrc();
    final java.lang.Object $name = this.getName();
    result = result * PRIME + ($name == null ? 43 : $name.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ChangeCursorByName(src=" + this.getSrc() + ", name=" + this.getName() + ")";
  }
}
