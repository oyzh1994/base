package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class QueryPointerReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";
  private final boolean sameScreen;
  private final short sequenceNumber;
  private final int root;
  private final int child;
  private final short rootX;
  private final short rootY;
  private final short winX;
  private final short winY;
  private final short mask;

  public static QueryPointerReply readQueryPointerReply(byte sameScreen, short sequenceNumber, X11Input in) throws IOException {
    QueryPointerReply.QueryPointerReplyBuilder javaBuilder = QueryPointerReply.builder();
    int length = in.readCard32();
    int root = in.readCard32();
    int child = in.readCard32();
    short rootX = in.readInt16();
    short rootY = in.readInt16();
    short winX = in.readInt16();
    short winY = in.readInt16();
    short mask = in.readCard16();
    byte[] pad11 = in.readPad(2);
    in.readPad(4);
    javaBuilder.sameScreen(sameScreen > 0);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.root(root);
    javaBuilder.child(child);
    javaBuilder.rootX(rootX);
    javaBuilder.rootY(rootY);
    javaBuilder.winX(winX);
    javaBuilder.winY(winY);
    javaBuilder.mask(mask);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeBool(sameScreen);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(root);
    out.writeCard32(child);
    out.writeInt16(rootX);
    out.writeInt16(rootY);
    out.writeInt16(winX);
    out.writeInt16(winY);
    out.writeCard16(mask);
    out.writePad(2);
  }

  public boolean isMaskEnabled(KeyButMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (KeyButMask m : maskEnums) {
      if (!m.isEnabled(mask)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 28;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryPointerReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private boolean sameScreen;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int root;
    @java.lang.SuppressWarnings("all")
    private int child;
    @java.lang.SuppressWarnings("all")
    private short rootX;
    @java.lang.SuppressWarnings("all")
    private short rootY;
    @java.lang.SuppressWarnings("all")
    private short winX;
    @java.lang.SuppressWarnings("all")
    private short winY;
    @java.lang.SuppressWarnings("all")
    private short mask;

    public boolean isMaskEnabled(KeyButMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (KeyButMask m : maskEnums) {
        if (!m.isEnabled(mask)) {
          return false;
        }
      }
      return true;
    }

    public QueryPointerReply.QueryPointerReplyBuilder maskEnable(KeyButMask... maskEnums) {
      for (KeyButMask m : maskEnums) {
        mask((short) m.enableFor(mask));
      }
      return this;
    }

    public QueryPointerReply.QueryPointerReplyBuilder maskDisable(KeyButMask... maskEnums) {
      for (KeyButMask m : maskEnums) {
        mask((short) m.disableFor(mask));
      }
      return this;
    }

    public int getSize() {
      return 28;
    }

    @java.lang.SuppressWarnings("all")
    QueryPointerReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryPointerReply.QueryPointerReplyBuilder sameScreen(final boolean sameScreen) {
      this.sameScreen = sameScreen;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryPointerReply.QueryPointerReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryPointerReply.QueryPointerReplyBuilder root(final int root) {
      this.root = root;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryPointerReply.QueryPointerReplyBuilder child(final int child) {
      this.child = child;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryPointerReply.QueryPointerReplyBuilder rootX(final short rootX) {
      this.rootX = rootX;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryPointerReply.QueryPointerReplyBuilder rootY(final short rootY) {
      this.rootY = rootY;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryPointerReply.QueryPointerReplyBuilder winX(final short winX) {
      this.winX = winX;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryPointerReply.QueryPointerReplyBuilder winY(final short winY) {
      this.winY = winY;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryPointerReply.QueryPointerReplyBuilder mask(final short mask) {
      this.mask = mask;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryPointerReply build() {
      return new QueryPointerReply(this.sameScreen, this.sequenceNumber, this.root, this.child, this.rootX, this.rootY, this.winX, this.winY, this.mask);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryPointerReply.QueryPointerReplyBuilder(sameScreen=" + this.sameScreen + ", sequenceNumber=" + this.sequenceNumber + ", root=" + this.root + ", child=" + this.child + ", rootX=" + this.rootX + ", rootY=" + this.rootY + ", winX=" + this.winX + ", winY=" + this.winY + ", mask=" + this.mask + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryPointerReply(final boolean sameScreen, final short sequenceNumber, final int root, final int child, final short rootX, final short rootY, final short winX, final short winY, final short mask) {
    this.sameScreen = sameScreen;
    this.sequenceNumber = sequenceNumber;
    this.root = root;
    this.child = child;
    this.rootX = rootX;
    this.rootY = rootY;
    this.winX = winX;
    this.winY = winY;
    this.mask = mask;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryPointerReply.QueryPointerReplyBuilder builder() {
    return new QueryPointerReply.QueryPointerReplyBuilder();
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
  public int getRoot() {
    return this.root;
  }

  @java.lang.SuppressWarnings("all")
  public int getChild() {
    return this.child;
  }

  @java.lang.SuppressWarnings("all")
  public short getRootX() {
    return this.rootX;
  }

  @java.lang.SuppressWarnings("all")
  public short getRootY() {
    return this.rootY;
  }

  @java.lang.SuppressWarnings("all")
  public short getWinX() {
    return this.winX;
  }

  @java.lang.SuppressWarnings("all")
  public short getWinY() {
    return this.winY;
  }

  @java.lang.SuppressWarnings("all")
  public short getMask() {
    return this.mask;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryPointerReply)) return false;
    final QueryPointerReply other = (QueryPointerReply) o;
    if (this.isSameScreen() != other.isSameScreen()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getRoot() != other.getRoot()) return false;
    if (this.getChild() != other.getChild()) return false;
    if (this.getRootX() != other.getRootX()) return false;
    if (this.getRootY() != other.getRootY()) return false;
    if (this.getWinX() != other.getWinX()) return false;
    if (this.getWinY() != other.getWinY()) return false;
    if (this.getMask() != other.getMask()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + (this.isSameScreen() ? 79 : 97);
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getRoot();
    result = result * PRIME + this.getChild();
    result = result * PRIME + this.getRootX();
    result = result * PRIME + this.getRootY();
    result = result * PRIME + this.getWinX();
    result = result * PRIME + this.getWinY();
    result = result * PRIME + this.getMask();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryPointerReply(sameScreen=" + this.isSameScreen() + ", sequenceNumber=" + this.getSequenceNumber() + ", root=" + this.getRoot() + ", child=" + this.getChild() + ", rootX=" + this.getRootX() + ", rootY=" + this.getRootY() + ", winX=" + this.getWinX() + ", winY=" + this.getWinY() + ", mask=" + this.getMask() + ")";
  }
}
