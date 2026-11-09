package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import com.github.moaxcp.x11.protocol.render.SubPixel;
import java.io.IOException;

public final class SetScreenConfigReply implements XReply {
  public static final String PLUGIN_NAME = "randr";
  private final byte status;
  private final short sequenceNumber;
  private final int newTimestamp;
  private final int configTimestamp;
  private final int root;
  private final short subpixelOrder;

  public static SetScreenConfigReply readSetScreenConfigReply(byte status, short sequenceNumber, X11Input in) throws IOException {
    SetScreenConfigReply.SetScreenConfigReplyBuilder javaBuilder = SetScreenConfigReply.builder();
    int length = in.readCard32();
    int newTimestamp = in.readCard32();
    int configTimestamp = in.readCard32();
    int root = in.readCard32();
    short subpixelOrder = in.readCard16();
    byte[] pad8 = in.readPad(10);
    javaBuilder.status(status);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.newTimestamp(newTimestamp);
    javaBuilder.configTimestamp(configTimestamp);
    javaBuilder.root(root);
    javaBuilder.subpixelOrder(subpixelOrder);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(status);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(newTimestamp);
    out.writeCard32(configTimestamp);
    out.writeCard32(root);
    out.writeCard16(subpixelOrder);
    out.writePad(10);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetScreenConfigReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte status;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int newTimestamp;
    @java.lang.SuppressWarnings("all")
    private int configTimestamp;
    @java.lang.SuppressWarnings("all")
    private int root;
    @java.lang.SuppressWarnings("all")
    private short subpixelOrder;

    public SetScreenConfigReply.SetScreenConfigReplyBuilder status(SetConfig status) {
      this.status = (byte) status.getValue();
      return this;
    }

    public SetScreenConfigReply.SetScreenConfigReplyBuilder status(byte status) {
      this.status = status;
      return this;
    }

    public SetScreenConfigReply.SetScreenConfigReplyBuilder subpixelOrder(SubPixel subpixelOrder) {
      this.subpixelOrder = (short) subpixelOrder.getValue();
      return this;
    }

    public SetScreenConfigReply.SetScreenConfigReplyBuilder subpixelOrder(short subpixelOrder) {
      this.subpixelOrder = subpixelOrder;
      return this;
    }

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    SetScreenConfigReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetScreenConfigReply.SetScreenConfigReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetScreenConfigReply.SetScreenConfigReplyBuilder newTimestamp(final int newTimestamp) {
      this.newTimestamp = newTimestamp;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetScreenConfigReply.SetScreenConfigReplyBuilder configTimestamp(final int configTimestamp) {
      this.configTimestamp = configTimestamp;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetScreenConfigReply.SetScreenConfigReplyBuilder root(final int root) {
      this.root = root;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetScreenConfigReply build() {
      return new SetScreenConfigReply(this.status, this.sequenceNumber, this.newTimestamp, this.configTimestamp, this.root, this.subpixelOrder);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetScreenConfigReply.SetScreenConfigReplyBuilder(status=" + this.status + ", sequenceNumber=" + this.sequenceNumber + ", newTimestamp=" + this.newTimestamp + ", configTimestamp=" + this.configTimestamp + ", root=" + this.root + ", subpixelOrder=" + this.subpixelOrder + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetScreenConfigReply(final byte status, final short sequenceNumber, final int newTimestamp, final int configTimestamp, final int root, final short subpixelOrder) {
    this.status = status;
    this.sequenceNumber = sequenceNumber;
    this.newTimestamp = newTimestamp;
    this.configTimestamp = configTimestamp;
    this.root = root;
    this.subpixelOrder = subpixelOrder;
  }

  @java.lang.SuppressWarnings("all")
  public static SetScreenConfigReply.SetScreenConfigReplyBuilder builder() {
    return new SetScreenConfigReply.SetScreenConfigReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getStatus() {
    return this.status;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getNewTimestamp() {
    return this.newTimestamp;
  }

  @java.lang.SuppressWarnings("all")
  public int getConfigTimestamp() {
    return this.configTimestamp;
  }

  @java.lang.SuppressWarnings("all")
  public int getRoot() {
    return this.root;
  }

  @java.lang.SuppressWarnings("all")
  public short getSubpixelOrder() {
    return this.subpixelOrder;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetScreenConfigReply)) return false;
    final SetScreenConfigReply other = (SetScreenConfigReply) o;
    if (this.getStatus() != other.getStatus()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getNewTimestamp() != other.getNewTimestamp()) return false;
    if (this.getConfigTimestamp() != other.getConfigTimestamp()) return false;
    if (this.getRoot() != other.getRoot()) return false;
    if (this.getSubpixelOrder() != other.getSubpixelOrder()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getStatus();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getNewTimestamp();
    result = result * PRIME + this.getConfigTimestamp();
    result = result * PRIME + this.getRoot();
    result = result * PRIME + this.getSubpixelOrder();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetScreenConfigReply(status=" + this.getStatus() + ", sequenceNumber=" + this.getSequenceNumber() + ", newTimestamp=" + this.getNewTimestamp() + ", configTimestamp=" + this.getConfigTimestamp() + ", root=" + this.getRoot() + ", subpixelOrder=" + this.getSubpixelOrder() + ")";
  }
}
