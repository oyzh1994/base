package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public final class PolySegment implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 66;
  private final int drawable;
  private final int gc;
  private final ImmutableList<Segment> segments;

  public byte getOpCode() {
    return OPCODE;
  }

  public static PolySegment readPolySegment(X11Input in) throws IOException {
    PolySegment.PolySegmentBuilder javaBuilder = PolySegment.builder();
    int javaStart = 1;
    byte[] pad1 = in.readPad(1);
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int drawable = in.readCard32();
    javaStart += 4;
    int gc = in.readCard32();
    javaStart += 4;
    MutableList<Segment> segments = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(length) - javaStart);
    while (javaStart < Short.toUnsignedInt(length) * 4) {
      Segment baseObject = Segment.readSegment(in);
      segments.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.drawable(drawable);
    javaBuilder.gc(gc);
    javaBuilder.segments(segments.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeCard32(gc);
    for (Segment t : segments) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + XObject.sizeOf(segments);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class PolySegmentBuilder {
    @java.lang.SuppressWarnings("all")
    private int drawable;
    @java.lang.SuppressWarnings("all")
    private int gc;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<Segment> segments;

    public int getSize() {
      return 12 + XObject.sizeOf(segments);
    }

    @java.lang.SuppressWarnings("all")
    PolySegmentBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PolySegment.PolySegmentBuilder drawable(final int drawable) {
      this.drawable = drawable;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PolySegment.PolySegmentBuilder gc(final int gc) {
      this.gc = gc;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public PolySegment.PolySegmentBuilder segments(final ImmutableList<Segment> segments) {
      if (segments == null) {
        throw new java.lang.NullPointerException("segments is marked non-null but is null");
      }
      this.segments = segments;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public PolySegment build() {
      return new PolySegment(this.drawable, this.gc, this.segments);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "PolySegment.PolySegmentBuilder(drawable=" + this.drawable + ", gc=" + this.gc + ", segments=" + this.segments + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  PolySegment(final int drawable, final int gc, final ImmutableList<Segment> segments) {
    if (segments == null) {
      throw new java.lang.NullPointerException("segments is marked non-null but is null");
    }
    this.drawable = drawable;
    this.gc = gc;
    this.segments = segments;
  }

  @java.lang.SuppressWarnings("all")
  public static PolySegment.PolySegmentBuilder builder() {
    return new PolySegment.PolySegmentBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getDrawable() {
    return this.drawable;
  }

  @java.lang.SuppressWarnings("all")
  public int getGc() {
    return this.gc;
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<Segment> getSegments() {
    return this.segments;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof PolySegment)) return false;
    final PolySegment other = (PolySegment) o;
    if (this.getDrawable() != other.getDrawable()) return false;
    if (this.getGc() != other.getGc()) return false;
    final java.lang.Object this$segments = this.getSegments();
    final java.lang.Object other$segments = other.getSegments();
    if (this$segments == null ? other$segments != null : !this$segments.equals(other$segments)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDrawable();
    result = result * PRIME + this.getGc();
    final java.lang.Object $segments = this.getSegments();
    result = result * PRIME + ($segments == null ? 43 : $segments.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "PolySegment(drawable=" + this.getDrawable() + ", gc=" + this.getGc() + ", segments=" + this.getSegments() + ")";
  }
}
