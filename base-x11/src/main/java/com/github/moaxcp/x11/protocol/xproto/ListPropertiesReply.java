package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class ListPropertiesReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";
  private final short sequenceNumber;
  private final int[] atoms;

  public static ListPropertiesReply readListPropertiesReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    ListPropertiesReply.ListPropertiesReplyBuilder javaBuilder = ListPropertiesReply.builder();
    int length = in.readCard32();
    short atomsLen = in.readCard16();
    byte[] pad5 = in.readPad(22);
    int[] atoms = in.readCard32(Short.toUnsignedInt(atomsLen));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.atoms(atoms);
    if (javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    short atomsLen = (short) atoms.length;
    out.writeCard16(atomsLen);
    out.writePad(22);
    out.writeCard32(atoms);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 4 * atoms.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ListPropertiesReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int[] atoms;

    public int getSize() {
      return 32 + 4 * atoms.length;
    }

    @java.lang.SuppressWarnings("all")
    ListPropertiesReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListPropertiesReply.ListPropertiesReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListPropertiesReply.ListPropertiesReplyBuilder atoms(final int[] atoms) {
      if (atoms == null) {
        throw new java.lang.NullPointerException("atoms is marked non-null but is null");
      }
      this.atoms = atoms;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ListPropertiesReply build() {
      return new ListPropertiesReply(this.sequenceNumber, this.atoms);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ListPropertiesReply.ListPropertiesReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", atoms=" + java.util.Arrays.toString(this.atoms) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ListPropertiesReply(final short sequenceNumber, final int[] atoms) {
    if (atoms == null) {
      throw new java.lang.NullPointerException("atoms is marked non-null but is null");
    }
    this.sequenceNumber = sequenceNumber;
    this.atoms = atoms;
  }

  @java.lang.SuppressWarnings("all")
  public static ListPropertiesReply.ListPropertiesReplyBuilder builder() {
    return new ListPropertiesReply.ListPropertiesReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int[] getAtoms() {
    return this.atoms;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ListPropertiesReply)) return false;
    final ListPropertiesReply other = (ListPropertiesReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (!java.util.Arrays.equals(this.getAtoms(), other.getAtoms())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + java.util.Arrays.hashCode(this.getAtoms());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ListPropertiesReply(sequenceNumber=" + this.getSequenceNumber() + ", atoms=" + java.util.Arrays.toString(this.getAtoms()) + ")";
  }
}
