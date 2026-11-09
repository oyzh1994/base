package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GetOutputProperty implements TwoWayRequest<GetOutputPropertyReply> {
  public static final String PLUGIN_NAME = "randr";
  public static final byte OPCODE = 15;
  private final int output;
  private final int property;
  private final int type;
  private final int longOffset;
  private final int longLength;
  private final boolean delete;
  private final boolean pending;

  public XReplyFunction<GetOutputPropertyReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetOutputPropertyReply.readGetOutputPropertyReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetOutputProperty readGetOutputProperty(X11Input in) throws IOException {
    GetOutputProperty.GetOutputPropertyBuilder javaBuilder = GetOutputProperty.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int output = in.readCard32();
    int property = in.readCard32();
    int type = in.readCard32();
    int longOffset = in.readCard32();
    int longLength = in.readCard32();
    boolean delete = in.readBool();
    boolean pending = in.readBool();
    byte[] pad10 = in.readPad(2);
    javaBuilder.output(output);
    javaBuilder.property(property);
    javaBuilder.type(type);
    javaBuilder.longOffset(longOffset);
    javaBuilder.longLength(longLength);
    javaBuilder.delete(delete);
    javaBuilder.pending(pending);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(output);
    out.writeCard32(property);
    out.writeCard32(type);
    out.writeCard32(longOffset);
    out.writeCard32(longLength);
    out.writeBool(delete);
    out.writeBool(pending);
    out.writePad(2);
  }

  @Override
  public int getSize() {
    return 28;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetOutputPropertyBuilder {
    @java.lang.SuppressWarnings("all")
    private int output;
    @java.lang.SuppressWarnings("all")
    private int property;
    @java.lang.SuppressWarnings("all")
    private int type;
    @java.lang.SuppressWarnings("all")
    private int longOffset;
    @java.lang.SuppressWarnings("all")
    private int longLength;
    @java.lang.SuppressWarnings("all")
    private boolean delete;
    @java.lang.SuppressWarnings("all")
    private boolean pending;

    public int getSize() {
      return 28;
    }

    @java.lang.SuppressWarnings("all")
    GetOutputPropertyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetOutputProperty.GetOutputPropertyBuilder output(final int output) {
      this.output = output;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetOutputProperty.GetOutputPropertyBuilder property(final int property) {
      this.property = property;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetOutputProperty.GetOutputPropertyBuilder type(final int type) {
      this.type = type;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetOutputProperty.GetOutputPropertyBuilder longOffset(final int longOffset) {
      this.longOffset = longOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetOutputProperty.GetOutputPropertyBuilder longLength(final int longLength) {
      this.longLength = longLength;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetOutputProperty.GetOutputPropertyBuilder delete(final boolean delete) {
      this.delete = delete;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetOutputProperty.GetOutputPropertyBuilder pending(final boolean pending) {
      this.pending = pending;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetOutputProperty build() {
      return new GetOutputProperty(this.output, this.property, this.type, this.longOffset, this.longLength, this.delete, this.pending);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetOutputProperty.GetOutputPropertyBuilder(output=" + this.output + ", property=" + this.property + ", type=" + this.type + ", longOffset=" + this.longOffset + ", longLength=" + this.longLength + ", delete=" + this.delete + ", pending=" + this.pending + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetOutputProperty(final int output, final int property, final int type, final int longOffset, final int longLength, final boolean delete, final boolean pending) {
    this.output = output;
    this.property = property;
    this.type = type;
    this.longOffset = longOffset;
    this.longLength = longLength;
    this.delete = delete;
    this.pending = pending;
  }

  @java.lang.SuppressWarnings("all")
  public static GetOutputProperty.GetOutputPropertyBuilder builder() {
    return new GetOutputProperty.GetOutputPropertyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getOutput() {
    return this.output;
  }

  @java.lang.SuppressWarnings("all")
  public int getProperty() {
    return this.property;
  }

  @java.lang.SuppressWarnings("all")
  public int getType() {
    return this.type;
  }

  @java.lang.SuppressWarnings("all")
  public int getLongOffset() {
    return this.longOffset;
  }

  @java.lang.SuppressWarnings("all")
  public int getLongLength() {
    return this.longLength;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isDelete() {
    return this.delete;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isPending() {
    return this.pending;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetOutputProperty)) return false;
    final GetOutputProperty other = (GetOutputProperty) o;
    if (this.getOutput() != other.getOutput()) return false;
    if (this.getProperty() != other.getProperty()) return false;
    if (this.getType() != other.getType()) return false;
    if (this.getLongOffset() != other.getLongOffset()) return false;
    if (this.getLongLength() != other.getLongLength()) return false;
    if (this.isDelete() != other.isDelete()) return false;
    if (this.isPending() != other.isPending()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getOutput();
    result = result * PRIME + this.getProperty();
    result = result * PRIME + this.getType();
    result = result * PRIME + this.getLongOffset();
    result = result * PRIME + this.getLongLength();
    result = result * PRIME + (this.isDelete() ? 79 : 97);
    result = result * PRIME + (this.isPending() ? 79 : 97);
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetOutputProperty(output=" + this.getOutput() + ", property=" + this.getProperty() + ", type=" + this.getType() + ", longOffset=" + this.getLongOffset() + ", longLength=" + this.getLongLength() + ", delete=" + this.isDelete() + ", pending=" + this.isPending() + ")";
  }
}
