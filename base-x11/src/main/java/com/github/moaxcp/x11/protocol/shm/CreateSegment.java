package com.github.moaxcp.x11.protocol.shm;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class CreateSegment implements TwoWayRequest<CreateSegmentReply> {
  public static final String PLUGIN_NAME = "shm";
  public static final byte OPCODE = 7;
  private final int shmseg;
  private final int size;
  private final boolean readOnly;

  public XReplyFunction<CreateSegmentReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> CreateSegmentReply.readCreateSegmentReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateSegment readCreateSegment(X11Input in) throws IOException {
    CreateSegment.CreateSegmentBuilder javaBuilder = CreateSegment.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int shmseg = in.readCard32();
    int size = in.readCard32();
    boolean readOnly = in.readBool();
    byte[] pad6 = in.readPad(3);
    javaBuilder.shmseg(shmseg);
    javaBuilder.size(size);
    javaBuilder.readOnly(readOnly);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(shmseg);
    out.writeCard32(size);
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


  public static class CreateSegmentBuilder {
    @java.lang.SuppressWarnings("all")
    private int shmseg;
    @java.lang.SuppressWarnings("all")
    private int size;
    @java.lang.SuppressWarnings("all")
    private boolean readOnly;

    public int getSize() {
      return 16;
    }

    @java.lang.SuppressWarnings("all")
    CreateSegmentBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateSegment.CreateSegmentBuilder shmseg(final int shmseg) {
      this.shmseg = shmseg;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateSegment.CreateSegmentBuilder size(final int size) {
      this.size = size;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateSegment.CreateSegmentBuilder readOnly(final boolean readOnly) {
      this.readOnly = readOnly;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CreateSegment build() {
      return new CreateSegment(this.shmseg, this.size, this.readOnly);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CreateSegment.CreateSegmentBuilder(shmseg=" + this.shmseg + ", size=" + this.size + ", readOnly=" + this.readOnly + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CreateSegment(final int shmseg, final int size, final boolean readOnly) {
    this.shmseg = shmseg;
    this.size = size;
    this.readOnly = readOnly;
  }

  @java.lang.SuppressWarnings("all")
  public static CreateSegment.CreateSegmentBuilder builder() {
    return new CreateSegment.CreateSegmentBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getShmseg() {
    return this.shmseg;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isReadOnly() {
    return this.readOnly;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CreateSegment)) return false;
    final CreateSegment other = (CreateSegment) o;
    if (this.getShmseg() != other.getShmseg()) return false;
    if (this.getSize() != other.getSize()) return false;
    if (this.isReadOnly() != other.isReadOnly()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getShmseg();
    result = result * PRIME + this.getSize();
    result = result * PRIME + (this.isReadOnly() ? 79 : 97);
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CreateSegment(shmseg=" + this.getShmseg() + ", size=" + this.getSize() + ", readOnly=" + this.isReadOnly() + ")";
  }
}
