package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class UseExtensionReply implements XReply {
  public static final String PLUGIN_NAME = "xkb";
  private final boolean supported;
  private final short sequenceNumber;
  private final short serverMajor;
  private final short serverMinor;

  public static UseExtensionReply readUseExtensionReply(byte supported, short sequenceNumber, X11Input in) throws IOException {
    UseExtensionReply.UseExtensionReplyBuilder javaBuilder = UseExtensionReply.builder();
    int length = in.readCard32();
    short serverMajor = in.readCard16();
    short serverMinor = in.readCard16();
    byte[] pad6 = in.readPad(20);
    javaBuilder.supported(supported > 0);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.serverMajor(serverMajor);
    javaBuilder.serverMinor(serverMinor);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeBool(supported);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard16(serverMajor);
    out.writeCard16(serverMinor);
    out.writePad(20);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class UseExtensionReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private boolean supported;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private short serverMajor;
    @java.lang.SuppressWarnings("all")
    private short serverMinor;

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    UseExtensionReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public UseExtensionReply.UseExtensionReplyBuilder supported(final boolean supported) {
      this.supported = supported;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public UseExtensionReply.UseExtensionReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public UseExtensionReply.UseExtensionReplyBuilder serverMajor(final short serverMajor) {
      this.serverMajor = serverMajor;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public UseExtensionReply.UseExtensionReplyBuilder serverMinor(final short serverMinor) {
      this.serverMinor = serverMinor;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public UseExtensionReply build() {
      return new UseExtensionReply(this.supported, this.sequenceNumber, this.serverMajor, this.serverMinor);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "UseExtensionReply.UseExtensionReplyBuilder(supported=" + this.supported + ", sequenceNumber=" + this.sequenceNumber + ", serverMajor=" + this.serverMajor + ", serverMinor=" + this.serverMinor + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  UseExtensionReply(final boolean supported, final short sequenceNumber, final short serverMajor, final short serverMinor) {
    this.supported = supported;
    this.sequenceNumber = sequenceNumber;
    this.serverMajor = serverMajor;
    this.serverMinor = serverMinor;
  }

  @java.lang.SuppressWarnings("all")
  public static UseExtensionReply.UseExtensionReplyBuilder builder() {
    return new UseExtensionReply.UseExtensionReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public boolean isSupported() {
    return this.supported;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public short getServerMajor() {
    return this.serverMajor;
  }

  @java.lang.SuppressWarnings("all")
  public short getServerMinor() {
    return this.serverMinor;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof UseExtensionReply)) return false;
    final UseExtensionReply other = (UseExtensionReply) o;
    if (this.isSupported() != other.isSupported()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getServerMajor() != other.getServerMajor()) return false;
    if (this.getServerMinor() != other.getServerMinor()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + (this.isSupported() ? 79 : 97);
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getServerMajor();
    result = result * PRIME + this.getServerMinor();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "UseExtensionReply(supported=" + this.isSupported() + ", sequenceNumber=" + this.getSequenceNumber() + ", serverMajor=" + this.getServerMajor() + ", serverMinor=" + this.getServerMinor() + ")";
  }
}
