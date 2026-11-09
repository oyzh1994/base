package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class LookupColor implements TwoWayRequest<LookupColorReply> {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 92;
  private final int cmap;
  private final byte[] name;

  public XReplyFunction<LookupColorReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> LookupColorReply.readLookupColorReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static LookupColor readLookupColor(X11Input in) throws IOException {
    LookupColor.LookupColorBuilder javaBuilder = LookupColor.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int cmap = in.readCard32();
    short nameLen = in.readCard16();
    byte[] pad5 = in.readPad(2);
    byte[] name = in.readChar(Short.toUnsignedInt(nameLen));
    javaBuilder.cmap(cmap);
    javaBuilder.name(name);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(cmap);
    short nameLen = (short) name.length;
    out.writeCard16(nameLen);
    out.writePad(2);
    out.writeChar(name);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + 1 * name.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class LookupColorBuilder {
    @java.lang.SuppressWarnings("all")
    private int cmap;
    @java.lang.SuppressWarnings("all")
    private byte[] name;

    public int getSize() {
      return 12 + 1 * name.length;
    }

    @java.lang.SuppressWarnings("all")
    LookupColorBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public LookupColor.LookupColorBuilder cmap(final int cmap) {
      this.cmap = cmap;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public LookupColor.LookupColorBuilder name(final byte[] name) {
      if (name == null) {
        throw new java.lang.NullPointerException("name is marked non-null but is null");
      }
      this.name = name;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public LookupColor build() {
      return new LookupColor(this.cmap, this.name);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "LookupColor.LookupColorBuilder(cmap=" + this.cmap + ", name=" + java.util.Arrays.toString(this.name) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  LookupColor(final int cmap, final byte[] name) {
    if (name == null) {
      throw new java.lang.NullPointerException("name is marked non-null but is null");
    }
    this.cmap = cmap;
    this.name = name;
  }

  @java.lang.SuppressWarnings("all")
  public static LookupColor.LookupColorBuilder builder() {
    return new LookupColor.LookupColorBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getCmap() {
    return this.cmap;
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getName() {
    return this.name;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof LookupColor)) return false;
    final LookupColor other = (LookupColor) o;
    if (this.getCmap() != other.getCmap()) return false;
    if (!java.util.Arrays.equals(this.getName(), other.getName())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getCmap();
    result = result * PRIME + java.util.Arrays.hashCode(this.getName());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "LookupColor(cmap=" + this.getCmap() + ", name=" + java.util.Arrays.toString(this.getName()) + ")";
  }
}
