package com.github.moaxcp.x11.protocol.shm;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class CreateSegmentReply implements XReply {
  public static final String PLUGIN_NAME = "shm";
  private final byte nfd;
  private final short sequenceNumber;
  private final int shmFd;

  public static CreateSegmentReply readCreateSegmentReply(byte nfd, short sequenceNumber, X11Input in) throws IOException {
    CreateSegmentReply.CreateSegmentReplyBuilder javaBuilder = CreateSegmentReply.builder();
    int length = in.readCard32();
    int shmFd = in.readInt32();
    byte[] pad5 = in.readPad(24);
    javaBuilder.nfd(nfd);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.shmFd(shmFd);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(nfd);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeInt32(shmFd);
    out.writePad(24);
  }

  @Override
  public int getSize() {
    return 36;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CreateSegmentReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte nfd;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int shmFd;

    public int getSize() {
      return 36;
    }

    @java.lang.SuppressWarnings("all")
    CreateSegmentReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateSegmentReply.CreateSegmentReplyBuilder nfd(final byte nfd) {
      this.nfd = nfd;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateSegmentReply.CreateSegmentReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateSegmentReply.CreateSegmentReplyBuilder shmFd(final int shmFd) {
      this.shmFd = shmFd;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CreateSegmentReply build() {
      return new CreateSegmentReply(this.nfd, this.sequenceNumber, this.shmFd);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CreateSegmentReply.CreateSegmentReplyBuilder(nfd=" + this.nfd + ", sequenceNumber=" + this.sequenceNumber + ", shmFd=" + this.shmFd + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CreateSegmentReply(final byte nfd, final short sequenceNumber, final int shmFd) {
    this.nfd = nfd;
    this.sequenceNumber = sequenceNumber;
    this.shmFd = shmFd;
  }

  @java.lang.SuppressWarnings("all")
  public static CreateSegmentReply.CreateSegmentReplyBuilder builder() {
    return new CreateSegmentReply.CreateSegmentReplyBuilder();
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
  public int getShmFd() {
    return this.shmFd;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CreateSegmentReply)) return false;
    final CreateSegmentReply other = (CreateSegmentReply) o;
    if (this.getNfd() != other.getNfd()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getShmFd() != other.getShmFd()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getNfd();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getShmFd();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CreateSegmentReply(nfd=" + this.getNfd() + ", sequenceNumber=" + this.getSequenceNumber() + ", shmFd=" + this.getShmFd() + ")";
  }
}
