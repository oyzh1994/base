package com.github.moaxcp.x11.protocol.shm;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class AttachFd implements OneWayRequest {
  public static final String PLUGIN_NAME = "shm";
  public static final byte OPCODE = 6;
  private final int shmseg;
  private final int shmFd;
  private final boolean readOnly;

  public byte getOpCode() {
    return OPCODE;
  }

  public static AttachFd readAttachFd(X11Input in) throws IOException {
    AttachFd.AttachFdBuilder javaBuilder = AttachFd.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int shmseg = in.readCard32();
    int shmFd = in.readInt32();
    boolean readOnly = in.readBool();
    byte[] pad6 = in.readPad(3);
    javaBuilder.shmseg(shmseg);
    javaBuilder.shmFd(shmFd);
    javaBuilder.readOnly(readOnly);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(shmseg);
    out.writeInt32(shmFd);
    out.writeBool(readOnly);
    out.writePad(3);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class AttachFdBuilder {
    @java.lang.SuppressWarnings("all")
    private int shmseg;
    @java.lang.SuppressWarnings("all")
    private int shmFd;
    @java.lang.SuppressWarnings("all")
    private boolean readOnly;

    public int getSize() {
      return 16;
    }

    @java.lang.SuppressWarnings("all")
    AttachFdBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AttachFd.AttachFdBuilder shmseg(final int shmseg) {
      this.shmseg = shmseg;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AttachFd.AttachFdBuilder shmFd(final int shmFd) {
      this.shmFd = shmFd;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AttachFd.AttachFdBuilder readOnly(final boolean readOnly) {
      this.readOnly = readOnly;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public AttachFd build() {
      return new AttachFd(this.shmseg, this.shmFd, this.readOnly);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "AttachFd.AttachFdBuilder(shmseg=" + this.shmseg + ", shmFd=" + this.shmFd + ", readOnly=" + this.readOnly + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  AttachFd(final int shmseg, final int shmFd, final boolean readOnly) {
    this.shmseg = shmseg;
    this.shmFd = shmFd;
    this.readOnly = readOnly;
  }

  @java.lang.SuppressWarnings("all")
  public static AttachFd.AttachFdBuilder builder() {
    return new AttachFd.AttachFdBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getShmseg() {
    return this.shmseg;
  }

  @java.lang.SuppressWarnings("all")
  public int getShmFd() {
    return this.shmFd;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isReadOnly() {
    return this.readOnly;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof AttachFd)) return false;
    final AttachFd other = (AttachFd) o;
    if (this.getShmseg() != other.getShmseg()) return false;
    if (this.getShmFd() != other.getShmFd()) return false;
    if (this.isReadOnly() != other.isReadOnly()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getShmseg();
    result = result * PRIME + this.getShmFd();
    result = result * PRIME + (this.isReadOnly() ? 79 : 97);
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "AttachFd(shmseg=" + this.getShmseg() + ", shmFd=" + this.getShmFd() + ", readOnly=" + this.isReadOnly() + ")";
  }
}
