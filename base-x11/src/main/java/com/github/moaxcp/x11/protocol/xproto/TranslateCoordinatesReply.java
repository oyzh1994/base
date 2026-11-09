package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class TranslateCoordinatesReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";
  private final boolean sameScreen;
  private final short sequenceNumber;
  private final int child;
  private final short dstX;
  private final short dstY;

  public static TranslateCoordinatesReply readTranslateCoordinatesReply(byte sameScreen, short sequenceNumber, X11Input in) throws IOException {
    TranslateCoordinatesReply.TranslateCoordinatesReplyBuilder javaBuilder = TranslateCoordinatesReply.builder();
    int length = in.readCard32();
    int child = in.readCard32();
    short dstX = in.readInt16();
    short dstY = in.readInt16();
    in.readPad(16);
    javaBuilder.sameScreen(sameScreen > 0);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.child(child);
    javaBuilder.dstX(dstX);
    javaBuilder.dstY(dstY);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeBool(sameScreen);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(child);
    out.writeInt16(dstX);
    out.writeInt16(dstY);
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class TranslateCoordinatesReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private boolean sameScreen;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int child;
    @java.lang.SuppressWarnings("all")
    private short dstX;
    @java.lang.SuppressWarnings("all")
    private short dstY;

    public int getSize() {
      return 16;
    }

    @java.lang.SuppressWarnings("all")
    TranslateCoordinatesReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public TranslateCoordinatesReply.TranslateCoordinatesReplyBuilder sameScreen(final boolean sameScreen) {
      this.sameScreen = sameScreen;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public TranslateCoordinatesReply.TranslateCoordinatesReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public TranslateCoordinatesReply.TranslateCoordinatesReplyBuilder child(final int child) {
      this.child = child;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public TranslateCoordinatesReply.TranslateCoordinatesReplyBuilder dstX(final short dstX) {
      this.dstX = dstX;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public TranslateCoordinatesReply.TranslateCoordinatesReplyBuilder dstY(final short dstY) {
      this.dstY = dstY;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public TranslateCoordinatesReply build() {
      return new TranslateCoordinatesReply(this.sameScreen, this.sequenceNumber, this.child, this.dstX, this.dstY);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "TranslateCoordinatesReply.TranslateCoordinatesReplyBuilder(sameScreen=" + this.sameScreen + ", sequenceNumber=" + this.sequenceNumber + ", child=" + this.child + ", dstX=" + this.dstX + ", dstY=" + this.dstY + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  TranslateCoordinatesReply(final boolean sameScreen, final short sequenceNumber, final int child, final short dstX, final short dstY) {
    this.sameScreen = sameScreen;
    this.sequenceNumber = sequenceNumber;
    this.child = child;
    this.dstX = dstX;
    this.dstY = dstY;
  }

  @java.lang.SuppressWarnings("all")
  public static TranslateCoordinatesReply.TranslateCoordinatesReplyBuilder builder() {
    return new TranslateCoordinatesReply.TranslateCoordinatesReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public boolean isSameScreen() {
    return this.sameScreen;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getChild() {
    return this.child;
  }

  @java.lang.SuppressWarnings("all")
  public short getDstX() {
    return this.dstX;
  }

  @java.lang.SuppressWarnings("all")
  public short getDstY() {
    return this.dstY;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof TranslateCoordinatesReply)) return false;
    final TranslateCoordinatesReply other = (TranslateCoordinatesReply) o;
    if (this.isSameScreen() != other.isSameScreen()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getChild() != other.getChild()) return false;
    if (this.getDstX() != other.getDstX()) return false;
    if (this.getDstY() != other.getDstY()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + (this.isSameScreen() ? 79 : 97);
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getChild();
    result = result * PRIME + this.getDstX();
    result = result * PRIME + this.getDstY();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "TranslateCoordinatesReply(sameScreen=" + this.isSameScreen() + ", sequenceNumber=" + this.getSequenceNumber() + ", child=" + this.getChild() + ", dstX=" + this.getDstX() + ", dstY=" + this.getDstY() + ")";
  }
}
