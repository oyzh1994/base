package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.ByteList;

public final class AllocNamedColor implements TwoWayRequest<AllocNamedColorReply> {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 85;
  private final int cmap;
  private final ByteList name;

  public XReplyFunction<AllocNamedColorReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> AllocNamedColorReply.readAllocNamedColorReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static AllocNamedColor readAllocNamedColor(X11Input in) throws IOException {
    AllocNamedColor.AllocNamedColorBuilder javaBuilder = AllocNamedColor.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int cmap = in.readCard32();
    short nameLen = in.readCard16();
    byte[] pad5 = in.readPad(2);
    ByteList name = in.readChar(Short.toUnsignedInt(nameLen));
    javaBuilder.cmap(cmap);
    javaBuilder.name(name.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(cmap);
    short nameLen = (short) name.size();
    out.writeCard16(nameLen);
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


  public static class AllocNamedColorBuilder {
    @java.lang.SuppressWarnings("all")
    private int cmap;
    @java.lang.SuppressWarnings("all")
    private ByteList name;

    public int getSize() {
      return 12 + 1 * name.size();
    }

    @java.lang.SuppressWarnings("all")
    AllocNamedColorBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AllocNamedColor.AllocNamedColorBuilder cmap(final int cmap) {
      this.cmap = cmap;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AllocNamedColor.AllocNamedColorBuilder name(final ByteList name) {
      if (name == null) {
        throw new java.lang.NullPointerException("name is marked non-null but is null");
      }
      this.name = name;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public AllocNamedColor build() {
      return new AllocNamedColor(this.cmap, this.name);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "AllocNamedColor.AllocNamedColorBuilder(cmap=" + this.cmap + ", name=" + this.name + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  AllocNamedColor(final int cmap, final ByteList name) {
    if (name == null) {
      throw new java.lang.NullPointerException("name is marked non-null but is null");
    }
    this.cmap = cmap;
    this.name = name;
  }

  @java.lang.SuppressWarnings("all")
  public static AllocNamedColor.AllocNamedColorBuilder builder() {
    return new AllocNamedColor.AllocNamedColorBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getCmap() {
    return this.cmap;
  }

  @java.lang.SuppressWarnings("all")
  public ByteList getName() {
    return this.name;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof AllocNamedColor)) return false;
    final AllocNamedColor other = (AllocNamedColor) o;
    if (this.getCmap() != other.getCmap()) return false;
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
    result = result * PRIME + this.getCmap();
    final java.lang.Object $name = this.getName();
    result = result * PRIME + ($name == null ? 43 : $name.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "AllocNamedColor(cmap=" + this.getCmap() + ", name=" + this.getName() + ")";
  }
}
