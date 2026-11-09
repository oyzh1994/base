package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class ChangeHosts implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 109;
  private final byte mode;
  private final byte family;
  private final byte[] address;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ChangeHosts readChangeHosts(X11Input in) throws IOException {
    ChangeHosts.ChangeHostsBuilder javaBuilder = ChangeHosts.builder();
    byte mode = in.readCard8();
    short length = in.readCard16();
    byte family = in.readCard8();
    byte[] pad4 = in.readPad(1);
    short addressLen = in.readCard16();
    byte[] address = in.readByte(Short.toUnsignedInt(addressLen));
    javaBuilder.mode(mode);
    javaBuilder.family(family);
    javaBuilder.address(address);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard8(mode);
    out.writeCard16((short) getLength());
    out.writeCard8(family);
    out.writePad(1);
    short addressLen = (short) address.length;
    out.writeCard16(addressLen);
    out.writeByte(address);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 8 + 1 * address.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ChangeHostsBuilder {
    @java.lang.SuppressWarnings("all")
    private byte mode;
    @java.lang.SuppressWarnings("all")
    private byte family;
    @java.lang.SuppressWarnings("all")
    private byte[] address;

    public ChangeHosts.ChangeHostsBuilder mode(HostMode mode) {
      this.mode = (byte) mode.getValue();
      return this;
    }

    public ChangeHosts.ChangeHostsBuilder mode(byte mode) {
      this.mode = mode;
      return this;
    }

    public ChangeHosts.ChangeHostsBuilder family(Family family) {
      this.family = (byte) family.getValue();
      return this;
    }

    public ChangeHosts.ChangeHostsBuilder family(byte family) {
      this.family = family;
      return this;
    }

    public int getSize() {
      return 8 + 1 * address.length;
    }

    @java.lang.SuppressWarnings("all")
    ChangeHostsBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangeHosts.ChangeHostsBuilder address(final byte[] address) {
      if (address == null) {
        throw new java.lang.NullPointerException("address is marked non-null but is null");
      }
      this.address = address;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ChangeHosts build() {
      return new ChangeHosts(this.mode, this.family, this.address);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ChangeHosts.ChangeHostsBuilder(mode=" + this.mode + ", family=" + this.family + ", address=" + java.util.Arrays.toString(this.address) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ChangeHosts(final byte mode, final byte family, final byte[] address) {
    if (address == null) {
      throw new java.lang.NullPointerException("address is marked non-null but is null");
    }
    this.mode = mode;
    this.family = family;
    this.address = address;
  }

  @java.lang.SuppressWarnings("all")
  public static ChangeHosts.ChangeHostsBuilder builder() {
    return new ChangeHosts.ChangeHostsBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getMode() {
    return this.mode;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFamily() {
    return this.family;
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getAddress() {
    return this.address;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ChangeHosts)) return false;
    final ChangeHosts other = (ChangeHosts) o;
    if (this.getMode() != other.getMode()) return false;
    if (this.getFamily() != other.getFamily()) return false;
    if (!java.util.Arrays.equals(this.getAddress(), other.getAddress())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getMode();
    result = result * PRIME + this.getFamily();
    result = result * PRIME + java.util.Arrays.hashCode(this.getAddress());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ChangeHosts(mode=" + this.getMode() + ", family=" + this.getFamily() + ", address=" + java.util.Arrays.toString(this.getAddress()) + ")";
  }
}
