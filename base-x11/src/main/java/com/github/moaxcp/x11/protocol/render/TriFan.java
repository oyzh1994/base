package com.github.moaxcp.x11.protocol.render;

import java.util.ArrayList;
import java.util.List;
import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;

public final class TriFan implements OneWayRequest {
  public static final String PLUGIN_NAME = "render";
  public static final byte OPCODE = 13;
  private final byte op;
  private final int src;
  private final int dst;
  private final int maskFormat;
  private final short srcX;
  private final short srcY;
  private final List<Pointfix> points;

  public byte getOpCode() {
    return OPCODE;
  }

  public static TriFan readTriFan(X11Input in) throws IOException {
    TriFan.TriFanBuilder javaBuilder = TriFan.builder();
    int javaStart = 1;
    byte majorOpcode = in.readCard8();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    byte op = in.readCard8();
    javaStart += 1;
    byte[] pad4 = in.readPad(3);
    javaStart += 3;
    int src = in.readCard32();
    javaStart += 4;
    int dst = in.readCard32();
    javaStart += 4;
    int maskFormat = in.readCard32();
    javaStart += 4;
    short srcX = in.readInt16();
    javaStart += 2;
    short srcY = in.readInt16();
    javaStart += 2;
    List<Pointfix> points = new ArrayList<>(Short.toUnsignedInt(length) - javaStart);
    while (javaStart < Short.toUnsignedInt(length) * 4) {
      Pointfix baseObject = Pointfix.readPointfix(in);
      points.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.op(op);
    javaBuilder.src(src);
    javaBuilder.dst(dst);
    javaBuilder.maskFormat(maskFormat);
    javaBuilder.srcX(srcX);
    javaBuilder.srcY(srcY);
    javaBuilder.points(List.copyOf(points));
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard8(op);
    out.writePad(3);
    out.writeCard32(src);
    out.writeCard32(dst);
    out.writeCard32(maskFormat);
    out.writeInt16(srcX);
    out.writeInt16(srcY);
    for (Pointfix t : points) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 24 + XObject.sizeOf(points);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class TriFanBuilder {
    @java.lang.SuppressWarnings("all")
    private byte op;
    @java.lang.SuppressWarnings("all")
    private int src;
    @java.lang.SuppressWarnings("all")
    private int dst;
    @java.lang.SuppressWarnings("all")
    private int maskFormat;
    @java.lang.SuppressWarnings("all")
    private short srcX;
    @java.lang.SuppressWarnings("all")
    private short srcY;
    @java.lang.SuppressWarnings("all")
    private List<Pointfix> points;

    public TriFan.TriFanBuilder op(PictOp op) {
      this.op = (byte) op.getValue();
      return this;
    }

    public TriFan.TriFanBuilder op(byte op) {
      this.op = op;
      return this;
    }

    public int getSize() {
      return 24 + XObject.sizeOf(points);
    }

    @java.lang.SuppressWarnings("all")
    TriFanBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public TriFan.TriFanBuilder src(final int src) {
      this.src = src;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public TriFan.TriFanBuilder dst(final int dst) {
      this.dst = dst;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public TriFan.TriFanBuilder maskFormat(final int maskFormat) {
      this.maskFormat = maskFormat;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public TriFan.TriFanBuilder srcX(final short srcX) {
      this.srcX = srcX;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public TriFan.TriFanBuilder srcY(final short srcY) {
      this.srcY = srcY;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public TriFan.TriFanBuilder points(final List<Pointfix> points) {
      if (points == null) {
        throw new java.lang.NullPointerException("points is marked non-null but is null");
      }
      this.points = points;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public TriFan build() {
      return new TriFan(this.op, this.src, this.dst, this.maskFormat, this.srcX, this.srcY, this.points);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "TriFan.TriFanBuilder(op=" + this.op + ", src=" + this.src + ", dst=" + this.dst + ", maskFormat=" + this.maskFormat + ", srcX=" + this.srcX + ", srcY=" + this.srcY + ", points=" + this.points + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  TriFan(final byte op, final int src, final int dst, final int maskFormat, final short srcX, final short srcY, final List<Pointfix> points) {
    if (points == null) {
      throw new java.lang.NullPointerException("points is marked non-null but is null");
    }
    this.op = op;
    this.src = src;
    this.dst = dst;
    this.maskFormat = maskFormat;
    this.srcX = srcX;
    this.srcY = srcY;
    this.points = points;
  }

  @java.lang.SuppressWarnings("all")
  public static TriFan.TriFanBuilder builder() {
    return new TriFan.TriFanBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getOp() {
    return this.op;
  }

  @java.lang.SuppressWarnings("all")
  public int getSrc() {
    return this.src;
  }

  @java.lang.SuppressWarnings("all")
  public int getDst() {
    return this.dst;
  }

  @java.lang.SuppressWarnings("all")
  public int getMaskFormat() {
    return this.maskFormat;
  }

  @java.lang.SuppressWarnings("all")
  public short getSrcX() {
    return this.srcX;
  }

  @java.lang.SuppressWarnings("all")
  public short getSrcY() {
    return this.srcY;
  }

  @java.lang.SuppressWarnings("all")
  public List<Pointfix> getPoints() {
    return this.points;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof TriFan)) return false;
    final TriFan other = (TriFan) o;
    if (this.getOp() != other.getOp()) return false;
    if (this.getSrc() != other.getSrc()) return false;
    if (this.getDst() != other.getDst()) return false;
    if (this.getMaskFormat() != other.getMaskFormat()) return false;
    if (this.getSrcX() != other.getSrcX()) return false;
    if (this.getSrcY() != other.getSrcY()) return false;
    final java.lang.Object this$points = this.getPoints();
    final java.lang.Object other$points = other.getPoints();
    if (this$points == null ? other$points != null : !this$points.equals(other$points)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getOp();
    result = result * PRIME + this.getSrc();
    result = result * PRIME + this.getDst();
    result = result * PRIME + this.getMaskFormat();
    result = result * PRIME + this.getSrcX();
    result = result * PRIME + this.getSrcY();
    final java.lang.Object $points = this.getPoints();
    result = result * PRIME + ($points == null ? 43 : $points.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "TriFan(op=" + this.getOp() + ", src=" + this.getSrc() + ", dst=" + this.getDst() + ", maskFormat=" + this.getMaskFormat() + ", srcX=" + this.getSrcX() + ", srcY=" + this.getSrcY() + ", points=" + this.getPoints() + ")";
  }
}
