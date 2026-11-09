package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class CreateLeaseReply implements XReply {
  public static final String PLUGIN_NAME = "randr";
  private final byte nfd;
  private final short sequenceNumber;
  private final int masterFd;

  public static CreateLeaseReply readCreateLeaseReply(byte nfd, short sequenceNumber, X11Input in) throws IOException {
    CreateLeaseReply.CreateLeaseReplyBuilder javaBuilder = CreateLeaseReply.builder();
    int length = in.readCard32();
    int masterFd = in.readInt32();
    byte[] pad5 = in.readPad(24);
    javaBuilder.nfd(nfd);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.masterFd(masterFd);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(nfd);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeInt32(masterFd);
    out.writePad(24);
  }

  @Override
  public int getSize() {
    return 36;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CreateLeaseReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte nfd;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int masterFd;

    public int getSize() {
      return 36;
    }

    @java.lang.SuppressWarnings("all")
    CreateLeaseReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateLeaseReply.CreateLeaseReplyBuilder nfd(final byte nfd) {
      this.nfd = nfd;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateLeaseReply.CreateLeaseReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateLeaseReply.CreateLeaseReplyBuilder masterFd(final int masterFd) {
      this.masterFd = masterFd;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CreateLeaseReply build() {
      return new CreateLeaseReply(this.nfd, this.sequenceNumber, this.masterFd);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CreateLeaseReply.CreateLeaseReplyBuilder(nfd=" + this.nfd + ", sequenceNumber=" + this.sequenceNumber + ", masterFd=" + this.masterFd + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CreateLeaseReply(final byte nfd, final short sequenceNumber, final int masterFd) {
    this.nfd = nfd;
    this.sequenceNumber = sequenceNumber;
    this.masterFd = masterFd;
  }

  @java.lang.SuppressWarnings("all")
  public static CreateLeaseReply.CreateLeaseReplyBuilder builder() {
    return new CreateLeaseReply.CreateLeaseReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getNfd() {
    return this.nfd;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getMasterFd() {
    return this.masterFd;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CreateLeaseReply)) return false;
    final CreateLeaseReply other = (CreateLeaseReply) o;
    if (this.getNfd() != other.getNfd()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getMasterFd() != other.getMasterFd()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getNfd();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getMasterFd();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CreateLeaseReply(nfd=" + this.getNfd() + ", sequenceNumber=" + this.getSequenceNumber() + ", masterFd=" + this.getMasterFd() + ")";
  }
}
