package com.github.moaxcp.x11.protocol.dpms;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class InfoReply implements XReply {
  public static final String PLUGIN_NAME = "dpms";
  private final short sequenceNumber;
  private final short powerLevel;
  private final boolean state;

  public static InfoReply readInfoReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    InfoReply.InfoReplyBuilder javaBuilder = InfoReply.builder();
    int length = in.readCard32();
    short powerLevel = in.readCard16();
    boolean state = in.readBool();
    byte[] pad6 = in.readPad(21);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.powerLevel(powerLevel);
    javaBuilder.state(state);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard16(powerLevel);
    out.writeBool(state);
    out.writePad(21);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class InfoReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private short powerLevel;
    @java.lang.SuppressWarnings("all")
    private boolean state;

    public InfoReply.InfoReplyBuilder powerLevel(DPMSMode powerLevel) {
      this.powerLevel = (short) powerLevel.getValue();
      return this;
    }

    public InfoReply.InfoReplyBuilder powerLevel(short powerLevel) {
      this.powerLevel = powerLevel;
      return this;
    }

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    InfoReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public InfoReply.InfoReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public InfoReply.InfoReplyBuilder state(final boolean state) {
      this.state = state;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public InfoReply build() {
      return new InfoReply(this.sequenceNumber, this.powerLevel, this.state);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "InfoReply.InfoReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", powerLevel=" + this.powerLevel + ", state=" + this.state + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  InfoReply(final short sequenceNumber, final short powerLevel, final boolean state) {
    this.sequenceNumber = sequenceNumber;
    this.powerLevel = powerLevel;
    this.state = state;
  }

  @java.lang.SuppressWarnings("all")
  public static InfoReply.InfoReplyBuilder builder() {
    return new InfoReply.InfoReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public short getPowerLevel() {
    return this.powerLevel;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isState() {
    return this.state;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof InfoReply)) return false;
    final InfoReply other = (InfoReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getPowerLevel() != other.getPowerLevel()) return false;
    if (this.isState() != other.isState()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getPowerLevel();
    result = result * PRIME + (this.isState() ? 79 : 97);
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "InfoReply(sequenceNumber=" + this.getSequenceNumber() + ", powerLevel=" + this.getPowerLevel() + ", state=" + this.isState() + ")";
  }
}
