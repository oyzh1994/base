package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class ListOutputPropertiesReply implements XReply {
  public static final String PLUGIN_NAME = "randr";
  private final short sequenceNumber;
  private final int[] atoms;

  public static ListOutputPropertiesReply readListOutputPropertiesReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    ListOutputPropertiesReply.ListOutputPropertiesReplyBuilder javaBuilder = ListOutputPropertiesReply.builder();
    int length = in.readCard32();
    short numAtoms = in.readCard16();
    byte[] pad5 = in.readPad(22);
    int[] atoms = in.readCard32(Short.toUnsignedInt(numAtoms));
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
    short numAtoms = (short) atoms.length;
    out.writeCard16(numAtoms);
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


  public static class ListOutputPropertiesReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int[] atoms;

    public int getSize() {
      return 32 + 4 * atoms.length;
    }

    @java.lang.SuppressWarnings("all")
    ListOutputPropertiesReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListOutputPropertiesReply.ListOutputPropertiesReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListOutputPropertiesReply.ListOutputPropertiesReplyBuilder atoms(final int[] atoms) {
      if (atoms == null) {
        throw new java.lang.NullPointerException("atoms is marked non-null but is null");
      }
      this.atoms = atoms;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ListOutputPropertiesReply build() {
      return new ListOutputPropertiesReply(this.sequenceNumber, this.atoms);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ListOutputPropertiesReply.ListOutputPropertiesReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", atoms=" + java.util.Arrays.toString(this.atoms) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ListOutputPropertiesReply(final short sequenceNumber, final int[] atoms) {
    if (atoms == null) {
      throw new java.lang.NullPointerException("atoms is marked non-null but is null");
    }
    this.sequenceNumber = sequenceNumber;
    this.atoms = atoms;
  }

  @java.lang.SuppressWarnings("all")
  public static ListOutputPropertiesReply.ListOutputPropertiesReplyBuilder builder() {
    return new ListOutputPropertiesReply.ListOutputPropertiesReplyBuilder();
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
    if (!(o instanceof ListOutputPropertiesReply)) return false;
    final ListOutputPropertiesReply other = (ListOutputPropertiesReply) o;
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
    return "ListOutputPropertiesReply(sequenceNumber=" + this.getSequenceNumber() + ", atoms=" + java.util.Arrays.toString(this.getAtoms()) + ")";
  }
}
