package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GetAtomName implements TwoWayRequest<GetAtomNameReply> {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 17;
  private final int atom;

  public XReplyFunction<GetAtomNameReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetAtomNameReply.readGetAtomNameReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetAtomName readGetAtomName(X11Input in) throws IOException {
    GetAtomName.GetAtomNameBuilder javaBuilder = GetAtomName.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int atom = in.readCard32();
    javaBuilder.atom(atom);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(atom);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetAtomNameBuilder {
    @java.lang.SuppressWarnings("all")
    private int atom;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    GetAtomNameBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetAtomName.GetAtomNameBuilder atom(final int atom) {
      this.atom = atom;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetAtomName build() {
      return new GetAtomName(this.atom);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetAtomName.GetAtomNameBuilder(atom=" + this.atom + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetAtomName(final int atom) {
    this.atom = atom;
  }

  @java.lang.SuppressWarnings("all")
  public static GetAtomName.GetAtomNameBuilder builder() {
    return new GetAtomName.GetAtomNameBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getAtom() {
    return this.atom;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetAtomName)) return false;
    final GetAtomName other = (GetAtomName) o;
    if (this.getAtom() != other.getAtom()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getAtom();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetAtomName(atom=" + this.getAtom() + ")";
  }
}
