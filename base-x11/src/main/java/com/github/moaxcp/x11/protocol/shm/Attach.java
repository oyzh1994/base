package com.github.moaxcp.x11.protocol.shm;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class Attach implements OneWayRequest {
  public static final String PLUGIN_NAME = "shm";
  public static final byte OPCODE = 1;
  private final int shmseg;
  private final int shmid;
  private final boolean readOnly;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Attach readAttach(X11Input in) throws IOException {
    Attach.AttachBuilder javaBuilder = Attach.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int shmseg = in.readCard32();
    int shmid = in.readCard32();
    boolean readOnly = in.readBool();
    byte[] pad6 = in.readPad(3);
    javaBuilder.shmseg(shmseg);
    javaBuilder.shmid(shmid);
    javaBuilder.readOnly(readOnly);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(shmseg);
    out.writeCard32(shmid);
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


  public static class AttachBuilder {
    @java.lang.SuppressWarnings("all")
    private int shmseg;
    @java.lang.SuppressWarnings("all")
    private int shmid;
    @java.lang.SuppressWarnings("all")
    private boolean readOnly;

    public int getSize() {
      return 16;
    }

    @java.lang.SuppressWarnings("all")
    AttachBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Attach.AttachBuilder shmseg(final int shmseg) {
      this.shmseg = shmseg;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Attach.AttachBuilder shmid(final int shmid) {
      this.shmid = shmid;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Attach.AttachBuilder readOnly(final boolean readOnly) {
      this.readOnly = readOnly;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Attach build() {
      return new Attach(this.shmseg, this.shmid, this.readOnly);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Attach.AttachBuilder(shmseg=" + this.shmseg + ", shmid=" + this.shmid + ", readOnly=" + this.readOnly + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Attach(final int shmseg, final int shmid, final boolean readOnly) {
    this.shmseg = shmseg;
    this.shmid = shmid;
    this.readOnly = readOnly;
  }

  @java.lang.SuppressWarnings("all")
  public static Attach.AttachBuilder builder() {
    return new Attach.AttachBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getShmseg() {
    return this.shmseg;
  }

  @java.lang.SuppressWarnings("all")
  public int getShmid() {
    return this.shmid;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isReadOnly() {
    return this.readOnly;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Attach)) return false;
    final Attach other = (Attach) o;
    if (this.getShmseg() != other.getShmseg()) return false;
    if (this.getShmid() != other.getShmid()) return false;
    if (this.isReadOnly() != other.isReadOnly()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getShmseg();
    result = result * PRIME + this.getShmid();
    result = result * PRIME + (this.isReadOnly() ? 79 : 97);
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Attach(shmseg=" + this.getShmseg() + ", shmid=" + this.getShmid() + ", readOnly=" + this.isReadOnly() + ")";
  }
}
