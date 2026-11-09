package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class ListFontsWithInfo implements TwoWayRequest<ListFontsWithInfoReply> {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 50;
  private final short maxNames;
  private final byte[] pattern;

  public XReplyFunction<ListFontsWithInfoReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> ListFontsWithInfoReply.readListFontsWithInfoReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static ListFontsWithInfo readListFontsWithInfo(X11Input in) throws IOException {
    ListFontsWithInfo.ListFontsWithInfoBuilder javaBuilder = ListFontsWithInfo.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    short maxNames = in.readCard16();
    short patternLen = in.readCard16();
    byte[] pattern = in.readChar(Short.toUnsignedInt(patternLen));
    javaBuilder.maxNames(maxNames);
    javaBuilder.pattern(pattern);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard16(maxNames);
    short patternLen = (short) pattern.length;
    out.writeCard16(patternLen);
    out.writeChar(pattern);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 8 + 1 * pattern.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ListFontsWithInfoBuilder {
    @java.lang.SuppressWarnings("all")
    private short maxNames;
    @java.lang.SuppressWarnings("all")
    private byte[] pattern;

    public int getSize() {
      return 8 + 1 * pattern.length;
    }

    @java.lang.SuppressWarnings("all")
    ListFontsWithInfoBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListFontsWithInfo.ListFontsWithInfoBuilder maxNames(final short maxNames) {
      this.maxNames = maxNames;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListFontsWithInfo.ListFontsWithInfoBuilder pattern(final byte[] pattern) {
      if (pattern == null) {
        throw new java.lang.NullPointerException("pattern is marked non-null but is null");
      }
      this.pattern = pattern;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ListFontsWithInfo build() {
      return new ListFontsWithInfo(this.maxNames, this.pattern);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ListFontsWithInfo.ListFontsWithInfoBuilder(maxNames=" + this.maxNames + ", pattern=" + java.util.Arrays.toString(this.pattern) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ListFontsWithInfo(final short maxNames, final byte[] pattern) {
    if (pattern == null) {
      throw new java.lang.NullPointerException("pattern is marked non-null but is null");
    }
    this.maxNames = maxNames;
    this.pattern = pattern;
  }

  @java.lang.SuppressWarnings("all")
  public static ListFontsWithInfo.ListFontsWithInfoBuilder builder() {
    return new ListFontsWithInfo.ListFontsWithInfoBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getMaxNames() {
    return this.maxNames;
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getPattern() {
    return this.pattern;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ListFontsWithInfo)) return false;
    final ListFontsWithInfo other = (ListFontsWithInfo) o;
    if (this.getMaxNames() != other.getMaxNames()) return false;
    if (!java.util.Arrays.equals(this.getPattern(), other.getPattern())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getMaxNames();
    result = result * PRIME + java.util.Arrays.hashCode(this.getPattern());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ListFontsWithInfo(maxNames=" + this.getMaxNames() + ", pattern=" + java.util.Arrays.toString(this.getPattern()) + ")";
  }
}
