package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.ByteList;

public final class Host implements XStruct {
  public static final String PLUGIN_NAME = "xproto";
  private final byte family;
  private final ByteList address;

  public static Host readHost(X11Input in) throws IOException {
    Host.HostBuilder javaBuilder = Host.builder();
    byte family = in.readCard8();
    byte[] pad1 = in.readPad(1);
    short addressLen = in.readCard16();
    ByteList address = in.readByte(Short.toUnsignedInt(addressLen));
    in.readPadAlign(Short.toUnsignedInt(addressLen));
    javaBuilder.family(family);
    javaBuilder.address(address.toImmutable());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(family);
    out.writePad(1);
    short addressLen = (short) address.size();
    out.writeCard16(addressLen);
    out.writeByte(address);
    out.writePadAlign(Short.toUnsignedInt(addressLen));
  }

  @Override
  public int getSize() {
    return 4 + 1 * address.size() + XObject.getSizeForPadAlign(4, 1 * address.size());
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class HostBuilder {
    @java.lang.SuppressWarnings("all")
    private byte family;
    @java.lang.SuppressWarnings("all")
    private ByteList address;

    public Host.HostBuilder family(Family family) {
      this.family = (byte) family.getValue();
      return this;
    }

    public Host.HostBuilder family(byte family) {
      this.family = family;
      return this;
    }

    public int getSize() {
      return 4 + 1 * address.size() + XObject.getSizeForPadAlign(4, 1 * address.size());
    }

    @java.lang.SuppressWarnings("all")
    HostBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Host.HostBuilder address(final ByteList address) {
      if (address == null) {
        throw new java.lang.NullPointerException("address is marked non-null but is null");
      }
      this.address = address;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Host build() {
      return new Host(this.family, this.address);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Host.HostBuilder(family=" + this.family + ", address=" + this.address + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Host(final byte family, final ByteList address) {
    if (address == null) {
      throw new java.lang.NullPointerException("address is marked non-null but is null");
    }
    this.family = family;
    this.address = address;
  }

  @java.lang.SuppressWarnings("all")
  public static Host.HostBuilder builder() {
    return new Host.HostBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getFamily() {
    return this.family;
  }

  @java.lang.SuppressWarnings("all")
  public ByteList getAddress() {
    return this.address;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Host)) return false;
    final Host other = (Host) o;
    if (this.getFamily() != other.getFamily()) return false;
    final java.lang.Object this$address = this.getAddress();
    final java.lang.Object other$address = other.getAddress();
    if (this$address == null ? other$address != null : !this$address.equals(other$address)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFamily();
    final java.lang.Object $address = this.getAddress();
    result = result * PRIME + ($address == null ? 43 : $address.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Host(family=" + this.getFamily() + ", address=" + this.getAddress() + ")";
  }
}
