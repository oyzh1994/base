package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public final class Await implements OneWayRequest {
  public static final String PLUGIN_NAME = "sync";
  public static final byte OPCODE = 7;
  private final ImmutableList<Waitcondition> waitList;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Await readAwait(X11Input in) throws IOException {
    Await.AwaitBuilder javaBuilder = Await.builder();
    int javaStart = 1;
    byte majorOpcode = in.readCard8();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    MutableList<Waitcondition> waitList = Lists.mutable.withInitialCapacity(Short.toUnsignedInt(length) - javaStart);
    while (javaStart < Short.toUnsignedInt(length) * 4) {
      Waitcondition baseObject = Waitcondition.readWaitcondition(in);
      waitList.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.waitList(waitList.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    for (Waitcondition t : waitList) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 4 + XObject.sizeOf(waitList);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class AwaitBuilder {
    @java.lang.SuppressWarnings("all")
    private ImmutableList<Waitcondition> waitList;

    public int getSize() {
      return 4 + XObject.sizeOf(waitList);
    }

    @java.lang.SuppressWarnings("all")
    AwaitBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Await.AwaitBuilder waitList(final ImmutableList<Waitcondition> waitList) {
      if (waitList == null) {
        throw new java.lang.NullPointerException("waitList is marked non-null but is null");
      }
      this.waitList = waitList;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Await build() {
      return new Await(this.waitList);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Await.AwaitBuilder(waitList=" + this.waitList + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Await(final ImmutableList<Waitcondition> waitList) {
    if (waitList == null) {
      throw new java.lang.NullPointerException("waitList is marked non-null but is null");
    }
    this.waitList = waitList;
  }

  @java.lang.SuppressWarnings("all")
  public static Await.AwaitBuilder builder() {
    return new Await.AwaitBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<Waitcondition> getWaitList() {
    return this.waitList;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Await)) return false;
    final Await other = (Await) o;
    final java.lang.Object this$waitList = this.getWaitList();
    final java.lang.Object other$waitList = other.getWaitList();
    if (this$waitList == null ? other$waitList != null : !this$waitList.equals(other$waitList)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final java.lang.Object $waitList = this.getWaitList();
    result = result * PRIME + ($waitList == null ? 43 : $waitList.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Await(waitList=" + this.getWaitList() + ")";
  }
}
