package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.IntList;

public final class AwaitFence implements OneWayRequest {
  public static final String PLUGIN_NAME = "sync";
  public static final byte OPCODE = 19;
  private final IntList fenceList;

  public byte getOpCode() {
    return OPCODE;
  }

  public static AwaitFence readAwaitFence(X11Input in) throws IOException {
    AwaitFence.AwaitFenceBuilder javaBuilder = AwaitFence.builder();
    int javaStart = 1;
    byte majorOpcode = in.readCard8();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    IntList fenceList = in.readCard32(Short.toUnsignedInt(length) - javaStart);
    javaBuilder.fenceList(fenceList.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(fenceList);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 4 + 4 * fenceList.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class AwaitFenceBuilder {
    @java.lang.SuppressWarnings("all")
    private IntList fenceList;

    public int getSize() {
      return 4 + 4 * fenceList.size();
    }

    @java.lang.SuppressWarnings("all")
    AwaitFenceBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public AwaitFence.AwaitFenceBuilder fenceList(final IntList fenceList) {
      if (fenceList == null) {
        throw new java.lang.NullPointerException("fenceList is marked non-null but is null");
      }
      this.fenceList = fenceList;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public AwaitFence build() {
      return new AwaitFence(this.fenceList);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "AwaitFence.AwaitFenceBuilder(fenceList=" + this.fenceList + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  AwaitFence(final IntList fenceList) {
    if (fenceList == null) {
      throw new java.lang.NullPointerException("fenceList is marked non-null but is null");
    }
    this.fenceList = fenceList;
  }

  @java.lang.SuppressWarnings("all")
  public static AwaitFence.AwaitFenceBuilder builder() {
    return new AwaitFence.AwaitFenceBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public IntList getFenceList() {
    return this.fenceList;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof AwaitFence)) return false;
    final AwaitFence other = (AwaitFence) o;
    final java.lang.Object this$fenceList = this.getFenceList();
    final java.lang.Object other$fenceList = other.getFenceList();
    if (this$fenceList == null ? other$fenceList != null : !this$fenceList.equals(other$fenceList)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final java.lang.Object $fenceList = this.getFenceList();
    result = result * PRIME + ($fenceList == null ? 43 : $fenceList.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "AwaitFence(fenceList=" + this.getFenceList() + ")";
  }
}
