package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class ListComponents implements TwoWayRequest<ListComponentsReply> {
  public static final String PLUGIN_NAME = "xkb";
  public static final byte OPCODE = 22;
  private final short deviceSpec;
  private final short maxNames;

  public XReplyFunction<ListComponentsReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> ListComponentsReply.readListComponentsReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static ListComponents readListComponents(X11Input in) throws IOException {
    ListComponents.ListComponentsBuilder javaBuilder = ListComponents.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short deviceSpec = in.readCard16();
    short maxNames = in.readCard16();
    javaBuilder.deviceSpec(deviceSpec);
    javaBuilder.maxNames(maxNames);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(deviceSpec);
    out.writeCard16(maxNames);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ListComponentsBuilder {
    @java.lang.SuppressWarnings("all")
    private short deviceSpec;
    @java.lang.SuppressWarnings("all")
    private short maxNames;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    ListComponentsBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListComponents.ListComponentsBuilder deviceSpec(final short deviceSpec) {
      this.deviceSpec = deviceSpec;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListComponents.ListComponentsBuilder maxNames(final short maxNames) {
      this.maxNames = maxNames;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ListComponents build() {
      return new ListComponents(this.deviceSpec, this.maxNames);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ListComponents.ListComponentsBuilder(deviceSpec=" + this.deviceSpec + ", maxNames=" + this.maxNames + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ListComponents(final short deviceSpec, final short maxNames) {
    this.deviceSpec = deviceSpec;
    this.maxNames = maxNames;
  }

  @java.lang.SuppressWarnings("all")
  public static ListComponents.ListComponentsBuilder builder() {
    return new ListComponents.ListComponentsBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getDeviceSpec() {
    return this.deviceSpec;
  }

  @java.lang.SuppressWarnings("all")
  public short getMaxNames() {
    return this.maxNames;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ListComponents)) return false;
    final ListComponents other = (ListComponents) o;
    if (this.getDeviceSpec() != other.getDeviceSpec()) return false;
    if (this.getMaxNames() != other.getMaxNames()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDeviceSpec();
    result = result * PRIME + this.getMaxNames();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ListComponents(deviceSpec=" + this.getDeviceSpec() + ", maxNames=" + this.getMaxNames() + ")";
  }
}
