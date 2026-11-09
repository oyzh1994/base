package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import com.github.moaxcp.x11.protocol.render.SubPixel;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.ByteList;
import org.eclipse.collections.api.list.primitive.IntList;

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
  private final IntList crtcs;
  private final IntList modes;
  private final IntList clones;
  private final ByteList name;

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
    IntList crtcs = in.readCard32(Short.toUnsignedInt(numCrtcs));
    IntList modes = in.readCard32(Short.toUnsignedInt(numModes));
    IntList clones = in.readCard32(Short.toUnsignedInt(numClones));
    ByteList name = in.readByte(Short.toUnsignedInt(nameLen));
    javaBuilder.status(status);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.timestamp(timestamp);
    javaBuilder.crtc(crtc);
    javaBuilder.mmWidth(mmWidth);
    javaBuilder.mmHeight(mmHeight);
    javaBuilder.connection(connection);
    javaBuilder.subpixelOrder(subpixelOrder);
    javaBuilder.numPreferred(numPreferred);
    javaBuilder.crtcs(crtcs.toImmutable());
    javaBuilder.modes(modes.toImmutable());
    javaBuilder.clones(clones.toImmutable());
    javaBuilder.name(name.toImmutable());
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
    short numCrtcs = (short) crtcs.size();
    out.writeCard16(numCrtcs);
    short numModes = (short) modes.size();
    out.writeCard16(numModes);
    out.writeCard16(numPreferred);
    short numClones = (short) clones.size();
    out.writeCard16(numClones);
    short nameLen = (short) name.size();
    out.writeCard16(nameLen);
    out.writeCard32(crtcs);
    out.writeCard32(modes);
    out.writeCard32(clones);
    out.writeByte(name);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 36 + 4 * crtcs.size() + 4 * modes.size() + 4 * clones.size() + 1 * name.size();
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
    private IntList crtcs;
    @java.lang.SuppressWarnings("all")
    private IntList modes;
    @java.lang.SuppressWarnings("all")
    private IntList clones;
    @java.lang.SuppressWarnings("all")
    private ByteList name;

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
      return 36 + 4 * crtcs.size() + 4 * modes.size() + 4 * clones.size() + 1 * name.size();
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
    public GetOutputInfoReply.GetOutputInfoReplyBuilder crtcs(final IntList crtcs) {
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
    public GetOutputInfoReply.GetOutputInfoReplyBuilder modes(final IntList modes) {
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
    public GetOutputInfoReply.GetOutputInfoReplyBuilder clones(final IntList clones) {
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
    public GetOutputInfoReply.GetOutputInfoReplyBuilder name(final ByteList name) {
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
      return "GetOutputInfoReply.GetOutputInfoReplyBuilder(status=" + this.status + ", sequenceNumber=" + this.sequenceNumber + ", timestamp=" + this.timestamp + ", crtc=" + this.crtc + ", mmWidth=" + this.mmWidth + ", mmHeight=" + this.mmHeight + ", connection=" + this.connection + ", subpixelOrder=" + this.subpixelOrder + ", numPreferred=" + this.numPreferred + ", crtcs=" + this.crtcs + ", modes=" + this.modes + ", clones=" + this.clones + ", name=" + this.name + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetOutputInfoReply(final byte status, final short sequenceNumber, final int timestamp, final int crtc, final int mmWidth, final int mmHeight, final byte connection, final byte subpixelOrder, final short numPreferred, final IntList crtcs, final IntList modes, final IntList clones, final ByteList name) {
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
  public IntList getCrtcs() {
    return this.crtcs;
  }

  @java.lang.SuppressWarnings("all")
  public IntList getModes() {
    return this.modes;
  }

  @java.lang.SuppressWarnings("all")
  public IntList getClones() {
    return this.clones;
  }

  @java.lang.SuppressWarnings("all")
  public ByteList getName() {
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
    final java.lang.Object this$crtcs = this.getCrtcs();
    final java.lang.Object other$crtcs = other.getCrtcs();
    if (this$crtcs == null ? other$crtcs != null : !this$crtcs.equals(other$crtcs)) return false;
    final java.lang.Object this$modes = this.getModes();
    final java.lang.Object other$modes = other.getModes();
    if (this$modes == null ? other$modes != null : !this$modes.equals(other$modes)) return false;
    final java.lang.Object this$clones = this.getClones();
    final java.lang.Object other$clones = other.getClones();
    if (this$clones == null ? other$clones != null : !this$clones.equals(other$clones)) return false;
    final java.lang.Object this$name = this.getName();
    final java.lang.Object other$name = other.getName();
    if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
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
    final java.lang.Object $crtcs = this.getCrtcs();
    result = result * PRIME + ($crtcs == null ? 43 : $crtcs.hashCode());
    final java.lang.Object $modes = this.getModes();
    result = result * PRIME + ($modes == null ? 43 : $modes.hashCode());
    final java.lang.Object $clones = this.getClones();
    result = result * PRIME + ($clones == null ? 43 : $clones.hashCode());
    final java.lang.Object $name = this.getName();
    result = result * PRIME + ($name == null ? 43 : $name.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetOutputInfoReply(status=" + this.getStatus() + ", sequenceNumber=" + this.getSequenceNumber() + ", timestamp=" + this.getTimestamp() + ", crtc=" + this.getCrtc() + ", mmWidth=" + this.getMmWidth() + ", mmHeight=" + this.getMmHeight() + ", connection=" + this.getConnection() + ", subpixelOrder=" + this.getSubpixelOrder() + ", numPreferred=" + this.getNumPreferred() + ", crtcs=" + this.getCrtcs() + ", modes=" + this.getModes() + ", clones=" + this.getClones() + ", name=" + this.getName() + ")";
  }
}
