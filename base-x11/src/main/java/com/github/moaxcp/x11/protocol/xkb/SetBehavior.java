package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class SetBehavior implements XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final byte keycode;
  private final BehaviorUnion behavior;

  public static SetBehavior readSetBehavior(X11Input in) throws IOException {
    SetBehavior.SetBehaviorBuilder javaBuilder = SetBehavior.builder();
    byte keycode = in.readCard8();
    BehaviorUnion behavior = BehaviorUnion.readBehaviorUnion(in);
    byte[] pad2 = in.readPad(1);
    javaBuilder.keycode(keycode);
    javaBuilder.behavior(behavior);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(keycode);
    behavior.write(out);
    out.writePad(1);
  }

  @Override
  public int getSize() {
    return 23;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetBehaviorBuilder {
    @java.lang.SuppressWarnings("all")
    private byte keycode;
    @java.lang.SuppressWarnings("all")
    private BehaviorUnion behavior;

    public int getSize() {
      return 23;
    }

    @java.lang.SuppressWarnings("all")
    SetBehaviorBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetBehavior.SetBehaviorBuilder keycode(final byte keycode) {
      this.keycode = keycode;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetBehavior.SetBehaviorBuilder behavior(final BehaviorUnion behavior) {
      if (behavior == null) {
        throw new java.lang.NullPointerException("behavior is marked non-null but is null");
      }
      this.behavior = behavior;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetBehavior build() {
      return new SetBehavior(this.keycode, this.behavior);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetBehavior.SetBehaviorBuilder(keycode=" + this.keycode + ", behavior=" + this.behavior + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetBehavior(final byte keycode, final BehaviorUnion behavior) {
    if (behavior == null) {
      throw new java.lang.NullPointerException("behavior is marked non-null but is null");
    }
    this.keycode = keycode;
    this.behavior = behavior;
  }

  @java.lang.SuppressWarnings("all")
  public static SetBehavior.SetBehaviorBuilder builder() {
    return new SetBehavior.SetBehaviorBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getKeycode() {
    return this.keycode;
  }

  @java.lang.SuppressWarnings("all")
  public BehaviorUnion getBehavior() {
    return this.behavior;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetBehavior)) return false;
    final SetBehavior other = (SetBehavior) o;
    if (this.getKeycode() != other.getKeycode()) return false;
    final java.lang.Object this$behavior = this.getBehavior();
    final java.lang.Object other$behavior = other.getBehavior();
    if (this$behavior == null ? other$behavior != null : !this$behavior.equals(other$behavior)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getKeycode();
    final java.lang.Object $behavior = this.getBehavior();
    result = result * PRIME + ($behavior == null ? 43 : $behavior.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetBehavior(keycode=" + this.getKeycode() + ", behavior=" + this.getBehavior() + ")";
  }
}
