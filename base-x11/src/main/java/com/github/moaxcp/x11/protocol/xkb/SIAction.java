package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.ByteList;

public final class SIAction implements XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final byte type;
  private final ByteList data;

  public static SIAction readSIAction(X11Input in) throws IOException {
    SIAction.SIActionBuilder javaBuilder = SIAction.builder();
    byte type = in.readCard8();
    ByteList data = in.readCard8(7);
    javaBuilder.type(type);
    javaBuilder.data(data.toImmutable());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(type);
    out.writeCard8(data);
  }

  @Override
  public int getSize() {
    return 1 + 1 * data.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SIActionBuilder {
    @java.lang.SuppressWarnings("all")
    private byte type;
    @java.lang.SuppressWarnings("all")
    private ByteList data;

    public SIAction.SIActionBuilder type(SAType type) {
      this.type = (byte) type.getValue();
      return this;
    }

    public SIAction.SIActionBuilder type(byte type) {
      this.type = type;
      return this;
    }

    public int getSize() {
      return 1 + 1 * data.size();
    }

    @java.lang.SuppressWarnings("all")
    SIActionBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SIAction.SIActionBuilder data(final ByteList data) {
      if (data == null) {
        throw new java.lang.NullPointerException("data is marked non-null but is null");
      }
      this.data = data;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SIAction build() {
      return new SIAction(this.type, this.data);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SIAction.SIActionBuilder(type=" + this.type + ", data=" + this.data + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SIAction(final byte type, final ByteList data) {
    if (data == null) {
      throw new java.lang.NullPointerException("data is marked non-null but is null");
    }
    this.type = type;
    this.data = data;
  }

  @java.lang.SuppressWarnings("all")
  public static SIAction.SIActionBuilder builder() {
    return new SIAction.SIActionBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getType() {
    return this.type;
  }

  @java.lang.SuppressWarnings("all")
  public ByteList getData() {
    return this.data;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SIAction)) return false;
    final SIAction other = (SIAction) o;
    if (this.getType() != other.getType()) return false;
    final java.lang.Object this$data = this.getData();
    final java.lang.Object other$data = other.getData();
    if (this$data == null ? other$data != null : !this$data.equals(other$data)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getType();
    final java.lang.Object $data = this.getData();
    result = result * PRIME + ($data == null ? 43 : $data.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SIAction(type=" + this.getType() + ", data=" + this.getData() + ")";
  }
}
