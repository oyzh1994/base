package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class SetSelectionOwner implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 22;
  private final int owner;
  private final int selection;
  private final int time;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetSelectionOwner readSetSelectionOwner(X11Input in) throws IOException {
    SetSelectionOwner.SetSelectionOwnerBuilder javaBuilder = SetSelectionOwner.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int owner = in.readCard32();
    int selection = in.readCard32();
    int time = in.readCard32();
    javaBuilder.owner(owner);
    javaBuilder.selection(selection);
    javaBuilder.time(time);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(owner);
    out.writeCard32(selection);
    out.writeCard32(time);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetSelectionOwnerBuilder {
    @java.lang.SuppressWarnings("all")
    private int owner;
    @java.lang.SuppressWarnings("all")
    private int selection;
    @java.lang.SuppressWarnings("all")
    private int time;

    public int getSize() {
      return 16;
    }

    @java.lang.SuppressWarnings("all")
    SetSelectionOwnerBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetSelectionOwner.SetSelectionOwnerBuilder owner(final int owner) {
      this.owner = owner;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetSelectionOwner.SetSelectionOwnerBuilder selection(final int selection) {
      this.selection = selection;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetSelectionOwner.SetSelectionOwnerBuilder time(final int time) {
      this.time = time;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetSelectionOwner build() {
      return new SetSelectionOwner(this.owner, this.selection, this.time);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetSelectionOwner.SetSelectionOwnerBuilder(owner=" + this.owner + ", selection=" + this.selection + ", time=" + this.time + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetSelectionOwner(final int owner, final int selection, final int time) {
    this.owner = owner;
    this.selection = selection;
    this.time = time;
  }

  @java.lang.SuppressWarnings("all")
  public static SetSelectionOwner.SetSelectionOwnerBuilder builder() {
    return new SetSelectionOwner.SetSelectionOwnerBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getOwner() {
    return this.owner;
  }

  @java.lang.SuppressWarnings("all")
  public int getSelection() {
    return this.selection;
  }

  @java.lang.SuppressWarnings("all")
  public int getTime() {
    return this.time;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetSelectionOwner)) return false;
    final SetSelectionOwner other = (SetSelectionOwner) o;
    if (this.getOwner() != other.getOwner()) return false;
    if (this.getSelection() != other.getSelection()) return false;
    if (this.getTime() != other.getTime()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getOwner();
    result = result * PRIME + this.getSelection();
    result = result * PRIME + this.getTime();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetSelectionOwner(owner=" + this.getOwner() + ", selection=" + this.getSelection() + ", time=" + this.getTime() + ")";
  }
}
