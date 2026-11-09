package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class UseExtension implements TwoWayRequest<UseExtensionReply> {
  public static final String PLUGIN_NAME = "xkb";
  public static final byte OPCODE = 0;
  private final short wantedMajor;
  private final short wantedMinor;

  public XReplyFunction<UseExtensionReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> UseExtensionReply.readUseExtensionReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static UseExtension readUseExtension(X11Input in) throws IOException {
    UseExtension.UseExtensionBuilder javaBuilder = UseExtension.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short wantedMajor = in.readCard16();
    short wantedMinor = in.readCard16();
    javaBuilder.wantedMajor(wantedMajor);
    javaBuilder.wantedMinor(wantedMinor);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(wantedMajor);
    out.writeCard16(wantedMinor);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class UseExtensionBuilder {
    @java.lang.SuppressWarnings("all")
    private short wantedMajor;
    @java.lang.SuppressWarnings("all")
    private short wantedMinor;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    UseExtensionBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public UseExtension.UseExtensionBuilder wantedMajor(final short wantedMajor) {
      this.wantedMajor = wantedMajor;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public UseExtension.UseExtensionBuilder wantedMinor(final short wantedMinor) {
      this.wantedMinor = wantedMinor;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public UseExtension build() {
      return new UseExtension(this.wantedMajor, this.wantedMinor);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "UseExtension.UseExtensionBuilder(wantedMajor=" + this.wantedMajor + ", wantedMinor=" + this.wantedMinor + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  UseExtension(final short wantedMajor, final short wantedMinor) {
    this.wantedMajor = wantedMajor;
    this.wantedMinor = wantedMinor;
  }

  @java.lang.SuppressWarnings("all")
  public static UseExtension.UseExtensionBuilder builder() {
    return new UseExtension.UseExtensionBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getWantedMajor() {
    return this.wantedMajor;
  }

  @java.lang.SuppressWarnings("all")
  public short getWantedMinor() {
    return this.wantedMinor;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof UseExtension)) return false;
    final UseExtension other = (UseExtension) o;
    if (this.getWantedMajor() != other.getWantedMajor()) return false;
    if (this.getWantedMinor() != other.getWantedMinor()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getWantedMajor();
    result = result * PRIME + this.getWantedMinor();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "UseExtension(wantedMajor=" + this.getWantedMajor() + ", wantedMinor=" + this.getWantedMinor() + ")";
  }
}
