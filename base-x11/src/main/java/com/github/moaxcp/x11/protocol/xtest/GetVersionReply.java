package com.github.moaxcp.x11.protocol.xtest;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class GetVersionReply implements XReply {
  public static final String PLUGIN_NAME = "xtest";
  private final byte majorVersion;
  private final short sequenceNumber;
  private final short minorVersion;

  public static GetVersionReply readGetVersionReply(byte majorVersion, short sequenceNumber, X11Input in) throws IOException {
    GetVersionReply.GetVersionReplyBuilder javaBuilder = GetVersionReply.builder();
    int length = in.readCard32();
    short minorVersion = in.readCard16();
    in.readPad(22);
    javaBuilder.majorVersion(majorVersion);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.minorVersion(minorVersion);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(majorVersion);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard16(minorVersion);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 10;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetVersionReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte majorVersion;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private short minorVersion;

    public int getSize() {
      return 10;
    }

    @java.lang.SuppressWarnings("all")
    GetVersionReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetVersionReply.GetVersionReplyBuilder majorVersion(final byte majorVersion) {
      this.majorVersion = majorVersion;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetVersionReply.GetVersionReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetVersionReply.GetVersionReplyBuilder minorVersion(final short minorVersion) {
      this.minorVersion = minorVersion;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetVersionReply build() {
      return new GetVersionReply(this.majorVersion, this.sequenceNumber, this.minorVersion);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetVersionReply.GetVersionReplyBuilder(majorVersion=" + this.majorVersion + ", sequenceNumber=" + this.sequenceNumber + ", minorVersion=" + this.minorVersion + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetVersionReply(final byte majorVersion, final short sequenceNumber, final short minorVersion) {
    this.majorVersion = majorVersion;
    this.sequenceNumber = sequenceNumber;
    this.minorVersion = minorVersion;
  }

  @java.lang.SuppressWarnings("all")
  public static GetVersionReply.GetVersionReplyBuilder builder() {
    return new GetVersionReply.GetVersionReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getMajorVersion() {
    return this.majorVersion;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public short getMinorVersion() {
    return this.minorVersion;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetVersionReply)) return false;
    final GetVersionReply other = (GetVersionReply) o;
    if (this.getMajorVersion() != other.getMajorVersion()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getMinorVersion() != other.getMinorVersion()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getMajorVersion();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getMinorVersion();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetVersionReply(majorVersion=" + this.getMajorVersion() + ", sequenceNumber=" + this.getSequenceNumber() + ", minorVersion=" + this.getMinorVersion() + ")";
  }
}
