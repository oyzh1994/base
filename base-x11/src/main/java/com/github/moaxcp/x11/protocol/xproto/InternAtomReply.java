package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class InternAtomReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";
  private final short sequenceNumber;
  private final int atom;

  public static InternAtomReply readInternAtomReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    InternAtomReply.InternAtomReplyBuilder javaBuilder = InternAtomReply.builder();
    int length = in.readCard32();
    int atom = in.readCard32();
    in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.atom(atom);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(atom);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class InternAtomReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int atom;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    InternAtomReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public InternAtomReply.InternAtomReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public InternAtomReply.InternAtomReplyBuilder atom(final int atom) {
      this.atom = atom;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public InternAtomReply build() {
      return new InternAtomReply(this.sequenceNumber, this.atom);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "InternAtomReply.InternAtomReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", atom=" + this.atom + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  InternAtomReply(final short sequenceNumber, final int atom) {
    this.sequenceNumber = sequenceNumber;
    this.atom = atom;
  }

  @java.lang.SuppressWarnings("all")
  public static InternAtomReply.InternAtomReplyBuilder builder() {
    return new InternAtomReply.InternAtomReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getAtom() {
    return this.atom;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof InternAtomReply)) return false;
    final InternAtomReply other = (InternAtomReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getAtom() != other.getAtom()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getAtom();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "InternAtomReply(sequenceNumber=" + this.getSequenceNumber() + ", atom=" + this.getAtom() + ")";
  }
}
