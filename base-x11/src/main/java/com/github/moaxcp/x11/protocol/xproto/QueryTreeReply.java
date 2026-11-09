package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.IntList;

public final class QueryTreeReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";
  private final short sequenceNumber;
  private final int root;
  private final int parent;
  private final IntList children;

  public static QueryTreeReply readQueryTreeReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    QueryTreeReply.QueryTreeReplyBuilder javaBuilder = QueryTreeReply.builder();
    int length = in.readCard32();
    int root = in.readCard32();
    int parent = in.readCard32();
    short childrenLen = in.readCard16();
    byte[] pad7 = in.readPad(14);
    IntList children = in.readCard32(Short.toUnsignedInt(childrenLen));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.root(root);
    javaBuilder.parent(parent);
    javaBuilder.children(children.toImmutable());
    if (javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(root);
    out.writeCard32(parent);
    short childrenLen = (short) children.size();
    out.writeCard16(childrenLen);
    out.writePad(14);
    out.writeCard32(children);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 4 * children.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryTreeReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int root;
    @java.lang.SuppressWarnings("all")
    private int parent;
    @java.lang.SuppressWarnings("all")
    private IntList children;

    public int getSize() {
      return 32 + 4 * children.size();
    }

    @java.lang.SuppressWarnings("all")
    QueryTreeReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryTreeReply.QueryTreeReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryTreeReply.QueryTreeReplyBuilder root(final int root) {
      this.root = root;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryTreeReply.QueryTreeReplyBuilder parent(final int parent) {
      this.parent = parent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryTreeReply.QueryTreeReplyBuilder children(final IntList children) {
      if (children == null) {
        throw new java.lang.NullPointerException("children is marked non-null but is null");
      }
      this.children = children;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryTreeReply build() {
      return new QueryTreeReply(this.sequenceNumber, this.root, this.parent, this.children);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryTreeReply.QueryTreeReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", root=" + this.root + ", parent=" + this.parent + ", children=" + this.children + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryTreeReply(final short sequenceNumber, final int root, final int parent, final IntList children) {
    if (children == null) {
      throw new java.lang.NullPointerException("children is marked non-null but is null");
    }
    this.sequenceNumber = sequenceNumber;
    this.root = root;
    this.parent = parent;
    this.children = children;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryTreeReply.QueryTreeReplyBuilder builder() {
    return new QueryTreeReply.QueryTreeReplyBuilder();
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
  public int getParent() {
    return this.parent;
  }

  @java.lang.SuppressWarnings("all")
  public IntList getChildren() {
    return this.children;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryTreeReply)) return false;
    final QueryTreeReply other = (QueryTreeReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getRoot() != other.getRoot()) return false;
    if (this.getParent() != other.getParent()) return false;
    final java.lang.Object this$children = this.getChildren();
    final java.lang.Object other$children = other.getChildren();
    if (this$children == null ? other$children != null : !this$children.equals(other$children)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getRoot();
    result = result * PRIME + this.getParent();
    final java.lang.Object $children = this.getChildren();
    result = result * PRIME + ($children == null ? 43 : $children.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryTreeReply(sequenceNumber=" + this.getSequenceNumber() + ", root=" + this.getRoot() + ", parent=" + this.getParent() + ", children=" + this.getChildren() + ")";
  }
}
