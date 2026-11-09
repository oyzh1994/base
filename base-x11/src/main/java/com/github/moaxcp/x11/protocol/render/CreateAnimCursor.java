package com.github.moaxcp.x11.protocol.render;

import java.util.ArrayList;
import java.util.List;
import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;

public final class CreateAnimCursor implements OneWayRequest {
  public static final String PLUGIN_NAME = "render";
  public static final byte OPCODE = 31;
  private final int cid;
  private final List<Animcursorelt> cursors;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateAnimCursor readCreateAnimCursor(X11Input in) throws IOException {
    CreateAnimCursor.CreateAnimCursorBuilder javaBuilder = CreateAnimCursor.builder();
    int javaStart = 1;
    byte majorOpcode = in.readCard8();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int cid = in.readCard32();
    javaStart += 4;
    List<Animcursorelt> cursors = new ArrayList<>(Short.toUnsignedInt(length) - javaStart);
    while (javaStart < Short.toUnsignedInt(length) * 4) {
      Animcursorelt baseObject = Animcursorelt.readAnimcursorelt(in);
      cursors.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.cid(cid);
    javaBuilder.cursors(List.copyOf(cursors));
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(cid);
    for (Animcursorelt t : cursors) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 8 + XObject.sizeOf(cursors);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CreateAnimCursorBuilder {
    @java.lang.SuppressWarnings("all")
    private int cid;
    @java.lang.SuppressWarnings("all")
    private List<Animcursorelt> cursors;

    public int getSize() {
      return 8 + XObject.sizeOf(cursors);
    }

    @java.lang.SuppressWarnings("all")
    CreateAnimCursorBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateAnimCursor.CreateAnimCursorBuilder cid(final int cid) {
      this.cid = cid;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateAnimCursor.CreateAnimCursorBuilder cursors(final List<Animcursorelt> cursors) {
      if (cursors == null) {
        throw new java.lang.NullPointerException("cursors is marked non-null but is null");
      }
      this.cursors = cursors;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CreateAnimCursor build() {
      return new CreateAnimCursor(this.cid, this.cursors);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CreateAnimCursor.CreateAnimCursorBuilder(cid=" + this.cid + ", cursors=" + this.cursors + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CreateAnimCursor(final int cid, final List<Animcursorelt> cursors) {
    if (cursors == null) {
      throw new java.lang.NullPointerException("cursors is marked non-null but is null");
    }
    this.cid = cid;
    this.cursors = cursors;
  }

  @java.lang.SuppressWarnings("all")
  public static CreateAnimCursor.CreateAnimCursorBuilder builder() {
    return new CreateAnimCursor.CreateAnimCursorBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getCid() {
    return this.cid;
  }

  @java.lang.SuppressWarnings("all")
  public List<Animcursorelt> getCursors() {
    return this.cursors;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CreateAnimCursor)) return false;
    final CreateAnimCursor other = (CreateAnimCursor) o;
    if (this.getCid() != other.getCid()) return false;
    final java.lang.Object this$cursors = this.getCursors();
    final java.lang.Object other$cursors = other.getCursors();
    if (this$cursors == null ? other$cursors != null : !this$cursors.equals(other$cursors)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getCid();
    final java.lang.Object $cursors = this.getCursors();
    result = result * PRIME + ($cursors == null ? 43 : $cursors.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CreateAnimCursor(cid=" + this.getCid() + ", cursors=" + this.getCursors() + ")";
  }
}
