package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class RadioGroupBehavior implements BehaviorUnion, XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final byte type;
  private final byte group;

  public static RadioGroupBehavior readRadioGroupBehavior(X11Input in) throws IOException {
    RadioGroupBehavior.RadioGroupBehaviorBuilder javaBuilder = RadioGroupBehavior.builder();
    byte type = in.readCard8();
    byte group = in.readCard8();
    javaBuilder.type(type);
    javaBuilder.group(group);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(type);
    out.writeCard8(group);
  }

  @Override
  public int getSize() {
    return 2;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class RadioGroupBehaviorBuilder {
    @java.lang.SuppressWarnings("all")
    private byte type;
    @java.lang.SuppressWarnings("all")
    private byte group;

    public int getSize() {
      return 2;
    }

    @java.lang.SuppressWarnings("all")
    RadioGroupBehaviorBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RadioGroupBehavior.RadioGroupBehaviorBuilder type(final byte type) {
      this.type = type;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RadioGroupBehavior.RadioGroupBehaviorBuilder group(final byte group) {
      this.group = group;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public RadioGroupBehavior build() {
      return new RadioGroupBehavior(this.type, this.group);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "RadioGroupBehavior.RadioGroupBehaviorBuilder(type=" + this.type + ", group=" + this.group + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  RadioGroupBehavior(final byte type, final byte group) {
    this.type = type;
    this.group = group;
  }

  @java.lang.SuppressWarnings("all")
  public static RadioGroupBehavior.RadioGroupBehaviorBuilder builder() {
    return new RadioGroupBehavior.RadioGroupBehaviorBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getType() {
    return this.type;
  }

  @java.lang.SuppressWarnings("all")
  public byte getGroup() {
    return this.group;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof RadioGroupBehavior)) return false;
    final RadioGroupBehavior other = (RadioGroupBehavior) o;
    if (this.getType() != other.getType()) return false;
    if (this.getGroup() != other.getGroup()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getType();
    result = result * PRIME + this.getGroup();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "RadioGroupBehavior(type=" + this.getType() + ", group=" + this.getGroup() + ")";
  }
}
