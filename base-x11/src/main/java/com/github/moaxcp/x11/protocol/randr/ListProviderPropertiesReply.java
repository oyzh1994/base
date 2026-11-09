package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.IntList;

public final class ListProviderPropertiesReply implements XReply {
  public static final String PLUGIN_NAME = "randr";
  private final short sequenceNumber;
  private final IntList atoms;

  public static ListProviderPropertiesReply readListProviderPropertiesReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    ListProviderPropertiesReply.ListProviderPropertiesReplyBuilder javaBuilder = ListProviderPropertiesReply.builder();
    int length = in.readCard32();
    short numAtoms = in.readCard16();
    byte[] pad5 = in.readPad(22);
    IntList atoms = in.readCard32(Short.toUnsignedInt(numAtoms));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.atoms(atoms.toImmutable());
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
    short numAtoms = (short) atoms.size();
    out.writeCard16(numAtoms);
    out.writePad(22);
    out.writeCard32(atoms);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 4 * atoms.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ListProviderPropertiesReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private IntList atoms;

    public int getSize() {
      return 32 + 4 * atoms.size();
    }

    @java.lang.SuppressWarnings("all")
    ListProviderPropertiesReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListProviderPropertiesReply.ListProviderPropertiesReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListProviderPropertiesReply.ListProviderPropertiesReplyBuilder atoms(final IntList atoms) {
      if (atoms == null) {
        throw new java.lang.NullPointerException("atoms is marked non-null but is null");
      }
      this.atoms = atoms;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ListProviderPropertiesReply build() {
      return new ListProviderPropertiesReply(this.sequenceNumber, this.atoms);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ListProviderPropertiesReply.ListProviderPropertiesReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", atoms=" + this.atoms + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ListProviderPropertiesReply(final short sequenceNumber, final IntList atoms) {
    if (atoms == null) {
      throw new java.lang.NullPointerException("atoms is marked non-null but is null");
    }
    this.sequenceNumber = sequenceNumber;
    this.atoms = atoms;
  }

  @java.lang.SuppressWarnings("all")
  public static ListProviderPropertiesReply.ListProviderPropertiesReplyBuilder builder() {
    return new ListProviderPropertiesReply.ListProviderPropertiesReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public IntList getAtoms() {
    return this.atoms;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ListProviderPropertiesReply)) return false;
    final ListProviderPropertiesReply other = (ListProviderPropertiesReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    final java.lang.Object this$atoms = this.getAtoms();
    final java.lang.Object other$atoms = other.getAtoms();
    if (this$atoms == null ? other$atoms != null : !this$atoms.equals(other$atoms)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    final java.lang.Object $atoms = this.getAtoms();
    result = result * PRIME + ($atoms == null ? 43 : $atoms.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ListProviderPropertiesReply(sequenceNumber=" + this.getSequenceNumber() + ", atoms=" + this.getAtoms() + ")";
  }
}
