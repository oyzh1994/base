package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class SetPriority implements OneWayRequest {
  public static final String PLUGIN_NAME = "sync";
  public static final byte OPCODE = 12;
  private final int id;
  private final int priority;

  public byte getOpCode() {
    return OPCODE;
  }

  public static SetPriority readSetPriority(X11Input in) throws IOException {
    SetPriority.SetPriorityBuilder javaBuilder = SetPriority.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int id = in.readCard32();
    int priority = in.readInt32();
    javaBuilder.id(id);
    javaBuilder.priority(priority);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(id);
    out.writeInt32(priority);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetPriorityBuilder {
    @java.lang.SuppressWarnings("all")
    private int id;
    @java.lang.SuppressWarnings("all")
    private int priority;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    SetPriorityBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetPriority.SetPriorityBuilder id(final int id) {
      this.id = id;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetPriority.SetPriorityBuilder priority(final int priority) {
      this.priority = priority;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetPriority build() {
      return new SetPriority(this.id, this.priority);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetPriority.SetPriorityBuilder(id=" + this.id + ", priority=" + this.priority + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetPriority(final int id, final int priority) {
    this.id = id;
    this.priority = priority;
  }

  @java.lang.SuppressWarnings("all")
  public static SetPriority.SetPriorityBuilder builder() {
    return new SetPriority.SetPriorityBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getId() {
    return this.id;
  }

  @java.lang.SuppressWarnings("all")
  public int getPriority() {
    return this.priority;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetPriority)) return false;
    final SetPriority other = (SetPriority) o;
    if (this.getId() != other.getId()) return false;
    if (this.getPriority() != other.getPriority()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getId();
    result = result * PRIME + this.getPriority();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetPriority(id=" + this.getId() + ", priority=" + this.getPriority() + ")";
  }
}
