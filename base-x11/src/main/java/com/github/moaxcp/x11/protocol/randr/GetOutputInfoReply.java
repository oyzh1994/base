package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import com.github.moaxcp.x11.protocol.render.SubPixel;
import java.io.IOException;

public final class GetOutputInfoReply implements XReply {
  public static final String PLUGIN_NAME = "randr";
  private final byte status;
  private final short sequenceNumber;
  private final int timestamp;
  private final int crtc;
  private final int mmWidth;
  private final int mmHeight;
  private final byte connection;
  private final byte subpixelOrder;
  private final short numPreferred;
  private final int[] crtcs;
  private final int[] modes;
  private final int[] clones;
  private final byte[] name;

  public static GetOutputInfoReply readGetOutputInfoReply(byte status, short sequenceNumber, X11Input in) throws IOException {
    GetOutputInfoReply.GetOutputInfoReplyBuilder javaBuilder = GetOutputInfoReply.builder();
    int length = in.readCard32();
    int timestamp = in.readCard32();
    int crtc = in.readCard32();
    int mmWidth = in.readCard32();
    int mmHeight = in.readCard32();
    byte connection = in.readCard8();
    byte subpixelOrder = in.readCard8();
    short numCrtcs = in.readCard16();
    short numModes = in.readCard16();
    short numPreferred = in.readCard16();
    short numClones = in.readCard16();
    short nameLen = in.readCard16();
    int[] crtcs = in.readCard32(Short.toUnsignedInt(numCrtcs));
    int[] modes = in.readCard32(Short.toUnsignedInt(numModes));
    int[] clones = in.readCard32(Short.toUnsignedInt(numClones));
    byte[] name = in.readByte(Short.toUnsignedInt(nameLen));
    javaBuilder.status(status);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.timestamp(timestamp);
    javaBuilder.crtc(crtc);
    javaBuilder.mmWidth(mmWidth);
    javaBuilder.mmHeight(mmHeight);
    javaBuilder.connection(connection);
    javaBuilder.subpixelOrder(subpixelOrder);
    javaBuilder.numPreferred(numPreferred);
    javaBuilder.crtcs(crtcs);
    javaBuilder.modes(modes);
    javaBuilder.clones(clones);
    javaBuilder.name(name);
    if (javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(status);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(timestamp);
    out.writeCard32(crtc);
    out.writeCard32(mmWidth);
    out.writeCard32(mmHeight);
    out.writeCard8(connection);
    out.writeCard8(subpixelOrder);
    short numCrtcs = (short) crtcs.length;
    out.writeCard16(numCrtcs);
    short numModes = (short) modes.length;
    out.writeCard16(numModes);
    out.writeCard16(numPreferred);
    short numClones = (short) clones.length;
    out.writeCard16(numClones);
    short nameLen = (short) name.length;
    out.writeCard16(nameLen);
    out.writeCard32(crtcs);
    out.writeCard32(modes);
    out.writeCard32(clones);
    out.writeByte(name);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 36 + 4 * crtcs.length + 4 * modes.length + 4 * clones.length + 1 * name.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetOutputInfoReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte status;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int timestamp;
    @java.lang.SuppressWarnings("all")
    private int crtc;
    @java.lang.SuppressWarnings("all")
    private int mmWidth;
    @java.lang.SuppressWarnings("all")
    private int mmHeight;
    @java.lang.SuppressWarnings("all")
    private byte connection;
    @java.lang.SuppressWarnings("all")
    private byte subpixelOrder;
    @java.lang.SuppressWarnings("all")
    private short numPreferred;
    @java.lang.SuppressWarnings("all")
    private int[] crtcs;
    @java.lang.SuppressWarnings("all")
    private int[] modes;
    @java.lang.SuppressWarnings("all")
    private int[] clones;
    @java.lang.SuppressWarnings("all")
    private byte[] name;

    public GetOutputInfoReply.GetOutputInfoReplyBuilder status(SetConfig status) {
      this.status = (byte) status.getValue();
      return this;
    }

    public GetOutputInfoReply.GetOutputInfoReplyBuilder status(byte status) {
      this.status = status;
      return this;
    }

    public GetOutputInfoReply.GetOutputInfoReplyBuilder connection(Connection connection) {
      this.connection = (byte) connection.getValue();
      return this;
    }

    public GetOutputInfoReply.GetOutputInfoReplyBuilder connection(byte connection) {
      this.connection = connection;
      return this;
    }

    public GetOutputInfoReply.GetOutputInfoReplyBuilder subpixelOrder(SubPixel subpixelOrder) {
      this.subpixelOrder = (byte) subpixelOrder.getValue();
      return this;
    }

    public GetOutputInfoReply.GetOutputInfoReplyBuilder subpixelOrder(byte subpixelOrder) {
      this.subpixelOrder = subpixelOrder;
      return this;
    }

    public int getSize() {
      return 36 + 4 * crtcs.length + 4 * modes.length + 4 * clones.length + 1 * name.length;
    }

    @java.lang.SuppressWarnings("all")
    GetOutputInfoReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetOutputInfoReply.GetOutputInfoReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetOutputInfoReply.GetOutputInfoReplyBuilder timestamp(final int timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetOutputInfoReply.GetOutputInfoReplyBuilder crtc(final int crtc) {
      this.crtc = crtc;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetOutputInfoReply.GetOutputInfoReplyBuilder mmWidth(final int mmWidth) {
      this.mmWidth = mmWidth;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetOutputInfoReply.GetOutputInfoReplyBuilder mmHeight(final int mmHeight) {
      this.mmHeight = mmHeight;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetOutputInfoReply.GetOutputInfoReplyBuilder numPreferred(final short numPreferred) {
      this.numPreferred = numPreferred;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetOutputInfoReply.GetOutputInfoReplyBuilder crtcs(final int[] crtcs) {
      if (crtcs == null) {
        throw new java.lang.NullPointerException("crtcs is marked non-null but is null");
      }
      this.crtcs = crtcs;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetOutputInfoReply.GetOutputInfoReplyBuilder modes(final int[] modes) {
      if (modes == null) {
        throw new java.lang.NullPointerException("modes is marked non-null but is null");
      }
      this.modes = modes;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetOutputInfoReply.GetOutputInfoReplyBuilder clones(final int[] clones) {
      if (clones == null) {
        throw new java.lang.NullPointerException("clones is marked non-null but is null");
      }
      this.clones = clones;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetOutputInfoReply.GetOutputInfoReplyBuilder name(final byte[] name) {
      if (name == null) {
        throw new java.lang.NullPointerException("name is marked non-null but is null");
      }
      this.name = name;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetOutputInfoReply build() {
      return new GetOutputInfoReply(this.status, this.sequenceNumber, this.timestamp, this.crtc, this.mmWidth, this.mmHeight, this.connection, this.subpixelOrder, this.numPreferred, this.crtcs, this.modes, this.clones, this.name);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetOutputInfoReply.GetOutputInfoReplyBuilder(status=" + this.status + ", sequenceNumber=" + this.sequenceNumber + ", timestamp=" + this.timestamp + ", crtc=" + this.crtc + ", mmWidth=" + this.mmWidth + ", mmHeight=" + this.mmHeight + ", connection=" + this.connection + ", subpixelOrder=" + this.subpixelOrder + ", numPreferred=" + this.numPreferred + ", crtcs=" + java.util.Arrays.toString(this.crtcs) + ", modes=" + java.util.Arrays.toString(this.modes) + ", clones=" + java.util.Arrays.toString(this.clones) + ", name=" + java.util.Arrays.toString(this.name) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetOutputInfoReply(final byte status, final short sequenceNumber, final int timestamp, final int crtc, final int mmWidth, final int mmHeight, final byte connection, final byte subpixelOrder, final short numPreferred, final int[] crtcs, final int[] modes, final int[] clones, final byte[] name) {
    if (crtcs == null) {
      throw new java.lang.NullPointerException("crtcs is marked non-null but is null");
    }
    if (modes == null) {
      throw new java.lang.NullPointerException("modes is marked non-null but is null");
    }
    if (clones == null) {
      throw new java.lang.NullPointerException("clones is marked non-null but is null");
    }
    if (name == null) {
      throw new java.lang.NullPointerException("name is marked non-null but is null");
    }
    this.status = status;
    this.sequenceNumber = sequenceNumber;
    this.timestamp = timestamp;
    this.crtc = crtc;
    this.mmWidth = mmWidth;
    this.mmHeight = mmHeight;
    this.connection = connection;
    this.subpixelOrder = subpixelOrder;
    this.numPreferred = numPreferred;
    this.crtcs = crtcs;
    this.modes = modes;
    this.clones = clones;
    this.name = name;
  }

  @java.lang.SuppressWarnings("all")
  public static GetOutputInfoReply.GetOutputInfoReplyBuilder builder() {
    return new GetOutputInfoReply.GetOutputInfoReplyBuilder();
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
  public int getTimestamp() {
    return this.timestamp;
  }

  @java.lang.SuppressWarnings("all")
  public int getCrtc() {
    return this.crtc;
  }

  @java.lang.SuppressWarnings("all")
  public int getMmWidth() {
    return this.mmWidth;
  }

  @java.lang.SuppressWarnings("all")
  public int getMmHeight() {
    return this.mmHeight;
  }

  @java.lang.SuppressWarnings("all")
  public byte getConnection() {
    return this.connection;
  }

  @java.lang.SuppressWarnings("all")
  public byte getSubpixelOrder() {
    return this.subpixelOrder;
  }

  @java.lang.SuppressWarnings("all")
  public short getNumPreferred() {
    return this.numPreferred;
  }

  @java.lang.SuppressWarnings("all")
  public int[] getCrtcs() {
    return this.crtcs;
  }

  @java.lang.SuppressWarnings("all")
  public int[] getModes() {
    return this.modes;
  }

  @java.lang.SuppressWarnings("all")
  public int[] getClones() {
    return this.clones;
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getName() {
    return this.name;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetOutputInfoReply)) return false;
    final GetOutputInfoReply other = (GetOutputInfoReply) o;
    if (this.getStatus() != other.getStatus()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getTimestamp() != other.getTimestamp()) return false;
    if (this.getCrtc() != other.getCrtc()) return false;
    if (this.getMmWidth() != other.getMmWidth()) return false;
    if (this.getMmHeight() != other.getMmHeight()) return false;
    if (this.getConnection() != other.getConnection()) return false;
    if (this.getSubpixelOrder() != other.getSubpixelOrder()) return false;
    if (this.getNumPreferred() != other.getNumPreferred()) return false;
    if (!java.util.Arrays.equals(this.getCrtcs(), other.getCrtcs())) return false;
    if (!java.util.Arrays.equals(this.getModes(), other.getModes())) return false;
    if (!java.util.Arrays.equals(this.getClones(), other.getClones())) return false;
    if (!java.util.Arrays.equals(this.getName(), other.getName())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getStatus();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getTimestamp();
    result = result * PRIME + this.getCrtc();
    result = result * PRIME + this.getMmWidth();
    result = result * PRIME + this.getMmHeight();
    result = result * PRIME + this.getConnection();
    result = result * PRIME + this.getSubpixelOrder();
    result = result * PRIME + this.getNumPreferred();
    result = result * PRIME + java.util.Arrays.hashCode(this.getCrtcs());
    result = result * PRIME + java.util.Arrays.hashCode(this.getModes());
    result = result * PRIME + java.util.Arrays.hashCode(this.getClones());
    result = result * PRIME + java.util.Arrays.hashCode(this.getName());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetOutputInfoReply(status=" + this.getStatus() + ", sequenceNumber=" + this.getSequenceNumber() + ", timestamp=" + this.getTimestamp() + ", crtc=" + this.getCrtc() + ", mmWidth=" + this.getMmWidth() + ", mmHeight=" + this.getMmHeight() + ", connection=" + this.getConnection() + ", subpixelOrder=" + this.getSubpixelOrder() + ", numPreferred=" + this.getNumPreferred() + ", crtcs=" + java.util.Arrays.toString(this.getCrtcs()) + ", modes=" + java.util.Arrays.toString(this.getModes()) + ", clones=" + java.util.Arrays.toString(this.getClones()) + ", name=" + java.util.Arrays.toString(this.getName()) + ")";
  }
}
