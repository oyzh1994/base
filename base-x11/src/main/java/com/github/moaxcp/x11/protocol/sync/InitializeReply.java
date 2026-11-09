package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class InitializeReply implements XReply {
  public static final String PLUGIN_NAME = "sync";
  private final short sequenceNumber;
  private final byte majorVersion;
  private final byte minorVersion;

  public static InitializeReply readInitializeReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    InitializeReply.InitializeReplyBuilder javaBuilder = InitializeReply.builder();
    int length = in.readCard32();
    byte majorVersion = in.readCard8();
    byte minorVersion = in.readCard8();
    byte[] pad6 = in.readPad(22);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.majorVersion(majorVersion);
    javaBuilder.minorVersion(minorVersion);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard8(majorVersion);
    out.writeCard8(minorVersion);
    out.writePad(22);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class InitializeReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private byte majorVersion;
    @java.lang.SuppressWarnings("all")
    private byte minorVersion;

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    InitializeReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public InitializeReply.InitializeReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public InitializeReply.InitializeReplyBuilder majorVersion(final byte majorVersion) {
      this.majorVersion = majorVersion;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public InitializeReply.InitializeReplyBuilder minorVersion(final byte minorVersion) {
      this.minorVersion = minorVersion;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public InitializeReply build() {
      return new InitializeReply(this.sequenceNumber, this.majorVersion, this.minorVersion);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "InitializeReply.InitializeReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", majorVersion=" + this.majorVersion + ", minorVersion=" + this.minorVersion + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  InitializeReply(final short sequenceNumber, final byte majorVersion, final byte minorVersion) {
    this.sequenceNumber = sequenceNumber;
    this.majorVersion = majorVersion;
    this.minorVersion = minorVersion;
  }

  @java.lang.SuppressWarnings("all")
  public static InitializeReply.InitializeReplyBuilder builder() {
    return new InitializeReply.InitializeReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public byte getMajorVersion() {
    return this.majorVersion;
  }

  @java.lang.SuppressWarnings("all")
  public byte getMinorVersion() {
    return this.minorVersion;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof InitializeReply)) return false;
    final InitializeReply other = (InitializeReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getMajorVersion() != other.getMajorVersion()) return false;
    if (this.getMinorVersion() != other.getMinorVersion()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getMajorVersion();
    result = result * PRIME + this.getMinorVersion();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "InitializeReply(sequenceNumber=" + this.getSequenceNumber() + ", majorVersion=" + this.getMajorVersion() + ", minorVersion=" + this.getMinorVersion() + ")";
  }
}
