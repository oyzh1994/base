package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class InternAtom implements TwoWayRequest<InternAtomReply> {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 16;
  private final boolean onlyIfExists;
  private final byte[] name;

  public XReplyFunction<InternAtomReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> InternAtomReply.readInternAtomReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static InternAtom readInternAtom(X11Input in) throws IOException {
    InternAtom.InternAtomBuilder javaBuilder = InternAtom.builder();
    boolean onlyIfExists = in.readBool();
    short length = in.readCard16();
    short nameLen = in.readCard16();
    byte[] pad4 = in.readPad(2);
    byte[] name = in.readChar(Short.toUnsignedInt(nameLen));
    javaBuilder.onlyIfExists(onlyIfExists);
    javaBuilder.name(name);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeBool(onlyIfExists);
    out.writeCard16((short) getLength());
    short nameLen = (short) name.length;
    out.writeCard16(nameLen);
    out.writePad(2);
    out.writeChar(name);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 8 + 1 * name.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class InternAtomBuilder {
    @java.lang.SuppressWarnings("all")
    private boolean onlyIfExists;
    @java.lang.SuppressWarnings("all")
    private byte[] name;

    public int getSize() {
      return 8 + 1 * name.length;
    }

    @java.lang.SuppressWarnings("all")
    InternAtomBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public InternAtom.InternAtomBuilder onlyIfExists(final boolean onlyIfExists) {
      this.onlyIfExists = onlyIfExists;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public InternAtom.InternAtomBuilder name(final byte[] name) {
      if (name == null) {
        throw new java.lang.NullPointerException("name is marked non-null but is null");
      }
      this.name = name;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public InternAtom build() {
      return new InternAtom(this.onlyIfExists, this.name);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "InternAtom.InternAtomBuilder(onlyIfExists=" + this.onlyIfExists + ", name=" + java.util.Arrays.toString(this.name) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  InternAtom(final boolean onlyIfExists, final byte[] name) {
    if (name == null) {
      throw new java.lang.NullPointerException("name is marked non-null but is null");
    }
    this.onlyIfExists = onlyIfExists;
    this.name = name;
  }

  @java.lang.SuppressWarnings("all")
  public static InternAtom.InternAtomBuilder builder() {
    return new InternAtom.InternAtomBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public boolean isOnlyIfExists() {
    return this.onlyIfExists;
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getName() {
    return this.name;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof InternAtom)) return false;
    final InternAtom other = (InternAtom) o;
    if (this.isOnlyIfExists() != other.isOnlyIfExists()) return false;
    if (!java.util.Arrays.equals(this.getName(), other.getName())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + (this.isOnlyIfExists() ? 79 : 97);
    result = result * PRIME + java.util.Arrays.hashCode(this.getName());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "InternAtom(onlyIfExists=" + this.isOnlyIfExists() + ", name=" + java.util.Arrays.toString(this.getName()) + ")";
  }
}
