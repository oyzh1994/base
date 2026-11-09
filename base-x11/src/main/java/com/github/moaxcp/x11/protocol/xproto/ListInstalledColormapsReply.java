package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class ListInstalledColormapsReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";
  private final short sequenceNumber;
  private final int[] cmaps;

  public static ListInstalledColormapsReply readListInstalledColormapsReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    ListInstalledColormapsReply.ListInstalledColormapsReplyBuilder javaBuilder = ListInstalledColormapsReply.builder();
    int length = in.readCard32();
    short cmapsLen = in.readCard16();
    byte[] pad5 = in.readPad(22);
    int[] cmaps = in.readCard32(Short.toUnsignedInt(cmapsLen));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.cmaps(cmaps);
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
    short cmapsLen = (short) cmaps.length;
    out.writeCard16(cmapsLen);
    out.writePad(22);
    out.writeCard32(cmaps);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 4 * cmaps.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ListInstalledColormapsReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int[] cmaps;

    public int getSize() {
      return 32 + 4 * cmaps.length;
    }

    @java.lang.SuppressWarnings("all")
    ListInstalledColormapsReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListInstalledColormapsReply.ListInstalledColormapsReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListInstalledColormapsReply.ListInstalledColormapsReplyBuilder cmaps(final int[] cmaps) {
      if (cmaps == null) {
        throw new java.lang.NullPointerException("cmaps is marked non-null but is null");
      }
      this.cmaps = cmaps;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ListInstalledColormapsReply build() {
      return new ListInstalledColormapsReply(this.sequenceNumber, this.cmaps);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ListInstalledColormapsReply.ListInstalledColormapsReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", cmaps=" + java.util.Arrays.toString(this.cmaps) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ListInstalledColormapsReply(final short sequenceNumber, final int[] cmaps) {
    if (cmaps == null) {
      throw new java.lang.NullPointerException("cmaps is marked non-null but is null");
    }
    this.sequenceNumber = sequenceNumber;
    this.cmaps = cmaps;
  }

  @java.lang.SuppressWarnings("all")
  public static ListInstalledColormapsReply.ListInstalledColormapsReplyBuilder builder() {
    return new ListInstalledColormapsReply.ListInstalledColormapsReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int[] getCmaps() {
    return this.cmaps;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ListInstalledColormapsReply)) return false;
    final ListInstalledColormapsReply other = (ListInstalledColormapsReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (!java.util.Arrays.equals(this.getCmaps(), other.getCmaps())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + java.util.Arrays.hashCode(this.getCmaps());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ListInstalledColormapsReply(sequenceNumber=" + this.getSequenceNumber() + ", cmaps=" + java.util.Arrays.toString(this.getCmaps()) + ")";
  }
}
