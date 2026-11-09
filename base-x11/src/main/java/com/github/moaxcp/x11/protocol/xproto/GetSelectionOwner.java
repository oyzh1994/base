package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GetSelectionOwner implements TwoWayRequest<GetSelectionOwnerReply> {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 23;
  private final int selection;

  public XReplyFunction<GetSelectionOwnerReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetSelectionOwnerReply.readGetSelectionOwnerReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetSelectionOwner readGetSelectionOwner(X11Input in) throws IOException {
    GetSelectionOwner.GetSelectionOwnerBuilder javaBuilder = GetSelectionOwner.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int selection = in.readCard32();
    javaBuilder.selection(selection);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(selection);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetSelectionOwnerBuilder {
    @java.lang.SuppressWarnings("all")
    private int selection;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    GetSelectionOwnerBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetSelectionOwner.GetSelectionOwnerBuilder selection(final int selection) {
      this.selection = selection;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetSelectionOwner build() {
      return new GetSelectionOwner(this.selection);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetSelectionOwner.GetSelectionOwnerBuilder(selection=" + this.selection + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetSelectionOwner(final int selection) {
    this.selection = selection;
  }

  @java.lang.SuppressWarnings("all")
  public static GetSelectionOwner.GetSelectionOwnerBuilder builder() {
    return new GetSelectionOwner.GetSelectionOwnerBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getSelection() {
    return this.selection;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetSelectionOwner)) return false;
    final GetSelectionOwner other = (GetSelectionOwner) o;
    if (this.getSelection() != other.getSelection()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSelection();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetSelectionOwner(selection=" + this.getSelection() + ")";
  }
}
