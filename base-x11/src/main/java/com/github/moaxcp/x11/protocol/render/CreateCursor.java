package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class CreateCursor implements OneWayRequest {
  public static final String PLUGIN_NAME = "render";
  public static final byte OPCODE = 27;
  private final int cid;
  private final int source;
  private final short x;
  private final short y;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateCursor readCreateCursor(X11Input in) throws IOException {
    CreateCursor.CreateCursorBuilder javaBuilder = CreateCursor.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int cid = in.readCard32();
    int source = in.readCard32();
    short x = in.readCard16();
    short y = in.readCard16();
    javaBuilder.cid(cid);
    javaBuilder.source(source);
    javaBuilder.x(x);
    javaBuilder.y(y);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(cid);
    out.writeCard32(source);
    out.writeCard16(x);
    out.writeCard16(y);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CreateCursorBuilder {
    @java.lang.SuppressWarnings("all")
    private int cid;
    @java.lang.SuppressWarnings("all")
    private int source;
    @java.lang.SuppressWarnings("all")
    private short x;
    @java.lang.SuppressWarnings("all")
    private short y;

    public int getSize() {
      return 16;
    }

    @java.lang.SuppressWarnings("all")
    CreateCursorBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateCursor.CreateCursorBuilder cid(final int cid) {
      this.cid = cid;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateCursor.CreateCursorBuilder source(final int source) {
      this.source = source;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateCursor.CreateCursorBuilder x(final short x) {
      this.x = x;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateCursor.CreateCursorBuilder y(final short y) {
      this.y = y;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CreateCursor build() {
      return new CreateCursor(this.cid, this.source, this.x, this.y);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CreateCursor.CreateCursorBuilder(cid=" + this.cid + ", source=" + this.source + ", x=" + this.x + ", y=" + this.y + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CreateCursor(final int cid, final int source, final short x, final short y) {
    this.cid = cid;
    this.source = source;
    this.x = x;
    this.y = y;
  }

  @java.lang.SuppressWarnings("all")
  public static CreateCursor.CreateCursorBuilder builder() {
    return new CreateCursor.CreateCursorBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getCid() {
    return this.cid;
  }

  @java.lang.SuppressWarnings("all")
  public int getSource() {
    return this.source;
  }

  @java.lang.SuppressWarnings("all")
  public short getX() {
    return this.x;
  }

  @java.lang.SuppressWarnings("all")
  public short getY() {
    return this.y;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CreateCursor)) return false;
    final CreateCursor other = (CreateCursor) o;
    if (this.getCid() != other.getCid()) return false;
    if (this.getSource() != other.getSource()) return false;
    if (this.getX() != other.getX()) return false;
    if (this.getY() != other.getY()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getCid();
    result = result * PRIME + this.getSource();
    result = result * PRIME + this.getX();
    result = result * PRIME + this.getY();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CreateCursor(cid=" + this.getCid() + ", source=" + this.getSource() + ", x=" + this.getX() + ", y=" + this.getY() + ")";
  }
}
