package com.github.moaxcp.x11.protocol.shm;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class CreatePixmap implements OneWayRequest {
  public static final String PLUGIN_NAME = "shm";
  public static final byte OPCODE = 5;
  private final int pid;
  private final int drawable;
  private final short width;
  private final short height;
  private final byte depth;
  private final int shmseg;
  private final int offset;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreatePixmap readCreatePixmap(X11Input in) throws IOException {
    CreatePixmap.CreatePixmapBuilder javaBuilder = CreatePixmap.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int pid = in.readCard32();
    int drawable = in.readCard32();
    short width = in.readCard16();
    short height = in.readCard16();
    byte depth = in.readCard8();
    byte[] pad8 = in.readPad(3);
    int shmseg = in.readCard32();
    int offset = in.readCard32();
    javaBuilder.pid(pid);
    javaBuilder.drawable(drawable);
    javaBuilder.width(width);
    javaBuilder.height(height);
    javaBuilder.depth(depth);
    javaBuilder.shmseg(shmseg);
    javaBuilder.offset(offset);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(pid);
    out.writeCard32(drawable);
    out.writeCard16(width);
    out.writeCard16(height);
    out.writeCard8(depth);
    out.writePad(3);
    out.writeCard32(shmseg);
    out.writeCard32(offset);
  }

  @Override
  public int getSize() {
    return 28;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CreatePixmapBuilder {
    @java.lang.SuppressWarnings("all")
    private int pid;
    @java.lang.SuppressWarnings("all")
    private int drawable;
    @java.lang.SuppressWarnings("all")
    private short width;
    @java.lang.SuppressWarnings("all")
    private short height;
    @java.lang.SuppressWarnings("all")
    private byte depth;
    @java.lang.SuppressWarnings("all")
    private int shmseg;
    @java.lang.SuppressWarnings("all")
    private int offset;

    public int getSize() {
      return 28;
    }

    @java.lang.SuppressWarnings("all")
    CreatePixmapBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreatePixmap.CreatePixmapBuilder pid(final int pid) {
      this.pid = pid;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreatePixmap.CreatePixmapBuilder drawable(final int drawable) {
      this.drawable = drawable;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreatePixmap.CreatePixmapBuilder width(final short width) {
      this.width = width;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreatePixmap.CreatePixmapBuilder height(final short height) {
      this.height = height;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreatePixmap.CreatePixmapBuilder depth(final byte depth) {
      this.depth = depth;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreatePixmap.CreatePixmapBuilder shmseg(final int shmseg) {
      this.shmseg = shmseg;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreatePixmap.CreatePixmapBuilder offset(final int offset) {
      this.offset = offset;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CreatePixmap build() {
      return new CreatePixmap(this.pid, this.drawable, this.width, this.height, this.depth, this.shmseg, this.offset);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CreatePixmap.CreatePixmapBuilder(pid=" + this.pid + ", drawable=" + this.drawable + ", width=" + this.width + ", height=" + this.height + ", depth=" + this.depth + ", shmseg=" + this.shmseg + ", offset=" + this.offset + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CreatePixmap(final int pid, final int drawable, final short width, final short height, final byte depth, final int shmseg, final int offset) {
    this.pid = pid;
    this.drawable = drawable;
    this.width = width;
    this.height = height;
    this.depth = depth;
    this.shmseg = shmseg;
    this.offset = offset;
  }

  @java.lang.SuppressWarnings("all")
  public static CreatePixmap.CreatePixmapBuilder builder() {
    return new CreatePixmap.CreatePixmapBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getPid() {
    return this.pid;
  }

  @java.lang.SuppressWarnings("all")
  public int getDrawable() {
    return this.drawable;
  }

  @java.lang.SuppressWarnings("all")
  public short getWidth() {
    return this.width;
  }

  @java.lang.SuppressWarnings("all")
  public short getHeight() {
    return this.height;
  }

  @java.lang.SuppressWarnings("all")
  public byte getDepth() {
    return this.depth;
  }

  @java.lang.SuppressWarnings("all")
  public int getShmseg() {
    return this.shmseg;
  }

  @java.lang.SuppressWarnings("all")
  public int getOffset() {
    return this.offset;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CreatePixmap)) return false;
    final CreatePixmap other = (CreatePixmap) o;
    if (this.getPid() != other.getPid()) return false;
    if (this.getDrawable() != other.getDrawable()) return false;
    if (this.getWidth() != other.getWidth()) return false;
    if (this.getHeight() != other.getHeight()) return false;
    if (this.getDepth() != other.getDepth()) return false;
    if (this.getShmseg() != other.getShmseg()) return false;
    if (this.getOffset() != other.getOffset()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getPid();
    result = result * PRIME + this.getDrawable();
    result = result * PRIME + this.getWidth();
    result = result * PRIME + this.getHeight();
    result = result * PRIME + this.getDepth();
    result = result * PRIME + this.getShmseg();
    result = result * PRIME + this.getOffset();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CreatePixmap(pid=" + this.getPid() + ", drawable=" + this.getDrawable() + ", width=" + this.getWidth() + ", height=" + this.getHeight() + ", depth=" + this.getDepth() + ", shmseg=" + this.getShmseg() + ", offset=" + this.getOffset() + ")";
  }
}
