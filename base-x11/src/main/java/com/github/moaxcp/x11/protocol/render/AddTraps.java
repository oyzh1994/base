package com.github.moaxcp.x11.protocol.render;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public final class AddTraps implements OneWayRequest {
  public static final String PLUGIN_NAME = "render";
  public static final byte OPCODE = 32;
  private final int picture;
  private final short xOff;
  private final short yOff;
  private final ImmutableList<Trap> traps;

  public byte getOpCode() {
    return OPCODE;
  }

  public static AddTraps readAddTraps(X11Input in) throws IOException {
    AddTraps.AddTrapsBuilder javaBuilder = AddTraps.builder();
    int javaStart = 1;
    byte majorOpcode = in.readCard8();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int picture = in.readCard32();
    javaStart += 4;
    short xOff = in.readInt16();
    javaStart += 2;
    short yOff = in.readInt16();
    javaStart += 2;
    MutableList<Trap> traps = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(length) - javaStart);
    while (javaStart < Short.toUnsignedInt(length) * 4) {
      Trap baseObject = Trap.readTrap(in);
      traps.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.picture(picture);
    javaBuilder.xOff(xOff);
    javaBuilder.yOff(yOff);
    javaBuilder.traps(traps.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(picture);
    out.writeInt16(xOff);
    out.writeInt16(yOff);
    for (Trap t : traps) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 12 + XObject.sizeOf(traps);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class AddTrapsBuilder {
    @java.lang.SuppressWarnings("all")
    private int picture;
    @java.lang.SuppressWarnings("all")
    private short xOff;
    @java.lang.SuppressWarnings("all")
    private short yOff;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<Trap> traps;

    public int getSize() {
      return 12 + XObject.sizeOf(traps);
    }

    @java.lang.SuppressWarnings("all")
    AddTrapsBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AddTraps.AddTrapsBuilder picture(final int picture) {
      this.picture = picture;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AddTraps.AddTrapsBuilder xOff(final short xOff) {
      this.xOff = xOff;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AddTraps.AddTrapsBuilder yOff(final short yOff) {
      this.yOff = yOff;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AddTraps.AddTrapsBuilder traps(final ImmutableList<Trap> traps) {
      if (traps == null) {
        throw new java.lang.NullPointerException("traps is marked non-null but is null");
      }
      this.traps = traps;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public AddTraps build() {
      return new AddTraps(this.picture, this.xOff, this.yOff, this.traps);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "AddTraps.AddTrapsBuilder(picture=" + this.picture + ", xOff=" + this.xOff + ", yOff=" + this.yOff + ", traps=" + this.traps + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  AddTraps(final int picture, final short xOff, final short yOff, final ImmutableList<Trap> traps) {
    if (traps == null) {
      throw new java.lang.NullPointerException("traps is marked non-null but is null");
    }
    this.picture = picture;
    this.xOff = xOff;
    this.yOff = yOff;
    this.traps = traps;
  }

  @java.lang.SuppressWarnings("all")
  public static AddTraps.AddTrapsBuilder builder() {
    return new AddTraps.AddTrapsBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getPicture() {
    return this.picture;
  }

  @java.lang.SuppressWarnings("all")
  public short getXOff() {
    return this.xOff;
  }

  @java.lang.SuppressWarnings("all")
  public short getYOff() {
    return this.yOff;
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<Trap> getTraps() {
    return this.traps;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof AddTraps)) return false;
    final AddTraps other = (AddTraps) o;
    if (this.getPicture() != other.getPicture()) return false;
    if (this.getXOff() != other.getXOff()) return false;
    if (this.getYOff() != other.getYOff()) return false;
    final java.lang.Object this$traps = this.getTraps();
    final java.lang.Object other$traps = other.getTraps();
    if (this$traps == null ? other$traps != null : !this$traps.equals(other$traps)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getPicture();
    result = result * PRIME + this.getXOff();
    result = result * PRIME + this.getYOff();
    final java.lang.Object $traps = this.getTraps();
    result = result * PRIME + ($traps == null ? 43 : $traps.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "AddTraps(picture=" + this.getPicture() + ", xOff=" + this.getXOff() + ", yOff=" + this.getYOff() + ", traps=" + this.getTraps() + ")";
  }
}
