package com.github.moaxcp.x11.protocol.xproto;

import java.util.ArrayList;
import java.util.List;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class ListExtensionsReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";
  private final short sequenceNumber;
  private final List<Str> names;

  public static ListExtensionsReply readListExtensionsReply(byte namesLen, short sequenceNumber, X11Input in) throws IOException {
    ListExtensionsReply.ListExtensionsReplyBuilder javaBuilder = ListExtensionsReply.builder();
    int length = in.readCard32();
    byte[] pad4 = in.readPad(24);
    List<Str> names = new ArrayList<>(Byte.toUnsignedInt(namesLen));
    for (int i = 0; i < Byte.toUnsignedInt(namesLen); i++) {
      names.add(Str.readStr(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.names(List.copyOf(names));
    if (javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    byte namesLen = (byte) names.size();
    out.writeCard8(namesLen);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writePad(24);
    for (Str t : names) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(names);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ListExtensionsReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private List<Str> names;

    public int getSize() {
      return 32 + XObject.sizeOf(names);
    }

    @java.lang.SuppressWarnings("all")
    ListExtensionsReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListExtensionsReply.ListExtensionsReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListExtensionsReply.ListExtensionsReplyBuilder names(final List<Str> names) {
      if (names == null) {
        throw new java.lang.NullPointerException("names is marked non-null but is null");
      }
      this.names = names;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ListExtensionsReply build() {
      return new ListExtensionsReply(this.sequenceNumber, this.names);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ListExtensionsReply.ListExtensionsReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", names=" + this.names + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ListExtensionsReply(final short sequenceNumber, final List<Str> names) {
    if (names == null) {
      throw new java.lang.NullPointerException("names is marked non-null but is null");
    }
    this.sequenceNumber = sequenceNumber;
    this.names = names;
  }

  @java.lang.SuppressWarnings("all")
  public static ListExtensionsReply.ListExtensionsReplyBuilder builder() {
    return new ListExtensionsReply.ListExtensionsReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public List<Str> getNames() {
    return this.names;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ListExtensionsReply)) return false;
    final ListExtensionsReply other = (ListExtensionsReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    final java.lang.Object this$names = this.getNames();
    final java.lang.Object other$names = other.getNames();
    if (this$names == null ? other$names != null : !this$names.equals(other$names)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    final java.lang.Object $names = this.getNames();
    result = result * PRIME + ($names == null ? 43 : $names.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ListExtensionsReply(sequenceNumber=" + this.getSequenceNumber() + ", names=" + this.getNames() + ")";
  }
}
