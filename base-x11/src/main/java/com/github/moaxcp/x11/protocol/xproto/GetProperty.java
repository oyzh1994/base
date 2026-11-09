package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GetProperty implements TwoWayRequest<GetPropertyReply> {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 20;
  private final boolean delete;
  private final int window;
  private final int property;
  private final int type;
  private final int longOffset;
  private final int longLength;

  public XReplyFunction<GetPropertyReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetPropertyReply.readGetPropertyReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetProperty readGetProperty(X11Input in) throws IOException {
    GetProperty.GetPropertyBuilder javaBuilder = GetProperty.builder();
    boolean delete = in.readBool();
    short length = in.readCard16();
    int window = in.readCard32();
    int property = in.readCard32();
    int type = in.readCard32();
    int longOffset = in.readCard32();
    int longLength = in.readCard32();
    javaBuilder.delete(delete);
    javaBuilder.window(window);
    javaBuilder.property(property);
    javaBuilder.type(type);
    javaBuilder.longOffset(longOffset);
    javaBuilder.longLength(longLength);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeBool(delete);
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(property);
    out.writeCard32(type);
    out.writeCard32(longOffset);
    out.writeCard32(longLength);
  }

  @Override
  public int getSize() {
    return 24;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetPropertyBuilder {
    @java.lang.SuppressWarnings("all")
    private boolean delete;
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private int property;
    @java.lang.SuppressWarnings("all")
    private int type;
    @java.lang.SuppressWarnings("all")
    private int longOffset;
    @java.lang.SuppressWarnings("all")
    private int longLength;

    public int getSize() {
      return 24;
    }

    @java.lang.SuppressWarnings("all")
    GetPropertyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetProperty.GetPropertyBuilder delete(final boolean delete) {
      this.delete = delete;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetProperty.GetPropertyBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetProperty.GetPropertyBuilder property(final int property) {
      this.property = property;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetProperty.GetPropertyBuilder type(final int type) {
      this.type = type;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetProperty.GetPropertyBuilder longOffset(final int longOffset) {
      this.longOffset = longOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetProperty.GetPropertyBuilder longLength(final int longLength) {
      this.longLength = longLength;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetProperty build() {
      return new GetProperty(this.delete, this.window, this.property, this.type, this.longOffset, this.longLength);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetProperty.GetPropertyBuilder(delete=" + this.delete + ", window=" + this.window + ", property=" + this.property + ", type=" + this.type + ", longOffset=" + this.longOffset + ", longLength=" + this.longLength + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetProperty(final boolean delete, final int window, final int property, final int type, final int longOffset, final int longLength) {
    this.delete = delete;
    this.window = window;
    this.property = property;
    this.type = type;
    this.longOffset = longOffset;
    this.longLength = longLength;
  }

  @java.lang.SuppressWarnings("all")
  public static GetProperty.GetPropertyBuilder builder() {
    return new GetProperty.GetPropertyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public boolean isDelete() {
    return this.delete;
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
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

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetProperty)) return false;
    final GetProperty other = (GetProperty) o;
    if (this.isDelete() != other.isDelete()) return false;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getProperty() != other.getProperty()) return false;
    if (this.getType() != other.getType()) return false;
    if (this.getLongOffset() != other.getLongOffset()) return false;
    if (this.getLongLength() != other.getLongLength()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + (this.isDelete() ? 79 : 97);
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getProperty();
    result = result * PRIME + this.getType();
    result = result * PRIME + this.getLongOffset();
    result = result * PRIME + this.getLongLength();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetProperty(delete=" + this.isDelete() + ", window=" + this.getWindow() + ", property=" + this.getProperty() + ", type=" + this.getType() + ", longOffset=" + this.getLongOffset() + ", longLength=" + this.getLongLength() + ")";
  }
}
