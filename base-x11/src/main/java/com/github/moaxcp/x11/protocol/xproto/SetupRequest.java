package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.ByteList;

public final class SetupRequest implements XStruct {
  public static final String PLUGIN_NAME = "xproto";
  private final byte byteOrder;
  private final short protocolMajorVersion;
  private final short protocolMinorVersion;
  private final ByteList authorizationProtocolName;
  private final ByteList authorizationProtocolData;

  public static SetupRequest readSetupRequest(X11Input in) throws IOException {
    SetupRequest.SetupRequestBuilder javaBuilder = SetupRequest.builder();
    byte byteOrder = in.readCard8();
    byte[] pad1 = in.readPad(1);
    short protocolMajorVersion = in.readCard16();
    short protocolMinorVersion = in.readCard16();
    short authorizationProtocolNameLen = in.readCard16();
    short authorizationProtocolDataLen = in.readCard16();
    byte[] pad6 = in.readPad(2);
    ByteList authorizationProtocolName = in.readChar(Short.toUnsignedInt(authorizationProtocolNameLen));
    in.readPadAlign(Short.toUnsignedInt(authorizationProtocolNameLen));
    ByteList authorizationProtocolData = in.readChar(Short.toUnsignedInt(authorizationProtocolDataLen));
    in.readPadAlign(Short.toUnsignedInt(authorizationProtocolDataLen));
    javaBuilder.byteOrder(byteOrder);
    javaBuilder.protocolMajorVersion(protocolMajorVersion);
    javaBuilder.protocolMinorVersion(protocolMinorVersion);
    javaBuilder.authorizationProtocolName(authorizationProtocolName.toImmutable());
    javaBuilder.authorizationProtocolData(authorizationProtocolData.toImmutable());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(byteOrder);
    out.writePad(1);
    out.writeCard16(protocolMajorVersion);
    out.writeCard16(protocolMinorVersion);
    short authorizationProtocolNameLen = (short) authorizationProtocolName.size();
    out.writeCard16(authorizationProtocolNameLen);
    short authorizationProtocolDataLen = (short) authorizationProtocolData.size();
    out.writeCard16(authorizationProtocolDataLen);
    out.writePad(2);
    out.writeChar(authorizationProtocolName);
    out.writePadAlign(Short.toUnsignedInt(authorizationProtocolNameLen));
    out.writeChar(authorizationProtocolData);
    out.writePadAlign(Short.toUnsignedInt(authorizationProtocolDataLen));
  }

  @Override
  public int getSize() {
    return 12 + 1 * authorizationProtocolName.size() + XObject.getSizeForPadAlign(4, 1 * authorizationProtocolName.size()) + 1 * authorizationProtocolData.size() + XObject.getSizeForPadAlign(4, 1 * authorizationProtocolData.size());
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetupRequestBuilder {
    @java.lang.SuppressWarnings("all")
    private byte byteOrder;
    @java.lang.SuppressWarnings("all")
    private short protocolMajorVersion;
    @java.lang.SuppressWarnings("all")
    private short protocolMinorVersion;
    @java.lang.SuppressWarnings("all")
    private ByteList authorizationProtocolName;
    @java.lang.SuppressWarnings("all")
    private ByteList authorizationProtocolData;

    public int getSize() {
      return 12 + 1 * authorizationProtocolName.size() + XObject.getSizeForPadAlign(4, 1 * authorizationProtocolName.size()) + 1 * authorizationProtocolData.size() + XObject.getSizeForPadAlign(4, 1 * authorizationProtocolData.size());
    }

    @java.lang.SuppressWarnings("all")
    SetupRequestBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetupRequest.SetupRequestBuilder byteOrder(final byte byteOrder) {
      this.byteOrder = byteOrder;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetupRequest.SetupRequestBuilder protocolMajorVersion(final short protocolMajorVersion) {
      this.protocolMajorVersion = protocolMajorVersion;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetupRequest.SetupRequestBuilder protocolMinorVersion(final short protocolMinorVersion) {
      this.protocolMinorVersion = protocolMinorVersion;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetupRequest.SetupRequestBuilder authorizationProtocolName(final ByteList authorizationProtocolName) {
      if (authorizationProtocolName == null) {
        throw new java.lang.NullPointerException("authorizationProtocolName is marked non-null but is null");
      }
      this.authorizationProtocolName = authorizationProtocolName;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SetupRequest.SetupRequestBuilder authorizationProtocolData(final ByteList authorizationProtocolData) {
      if (authorizationProtocolData == null) {
        throw new java.lang.NullPointerException("authorizationProtocolData is marked non-null but is null");
      }
      this.authorizationProtocolData = authorizationProtocolData;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SetupRequest build() {
      return new SetupRequest(this.byteOrder, this.protocolMajorVersion, this.protocolMinorVersion, this.authorizationProtocolName, this.authorizationProtocolData);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SetupRequest.SetupRequestBuilder(byteOrder=" + this.byteOrder + ", protocolMajorVersion=" + this.protocolMajorVersion + ", protocolMinorVersion=" + this.protocolMinorVersion + ", authorizationProtocolName=" + this.authorizationProtocolName + ", authorizationProtocolData=" + this.authorizationProtocolData + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SetupRequest(final byte byteOrder, final short protocolMajorVersion, final short protocolMinorVersion, final ByteList authorizationProtocolName, final ByteList authorizationProtocolData) {
    if (authorizationProtocolName == null) {
      throw new java.lang.NullPointerException("authorizationProtocolName is marked non-null but is null");
    }
    if (authorizationProtocolData == null) {
      throw new java.lang.NullPointerException("authorizationProtocolData is marked non-null but is null");
    }
    this.byteOrder = byteOrder;
    this.protocolMajorVersion = protocolMajorVersion;
    this.protocolMinorVersion = protocolMinorVersion;
    this.authorizationProtocolName = authorizationProtocolName;
    this.authorizationProtocolData = authorizationProtocolData;
  }

  @java.lang.SuppressWarnings("all")
  public static SetupRequest.SetupRequestBuilder builder() {
    return new SetupRequest.SetupRequestBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getByteOrder() {
    return this.byteOrder;
  }

  @java.lang.SuppressWarnings("all")
  public short getProtocolMajorVersion() {
    return this.protocolMajorVersion;
  }

  @java.lang.SuppressWarnings("all")
  public short getProtocolMinorVersion() {
    return this.protocolMinorVersion;
  }

  @java.lang.SuppressWarnings("all")
  public ByteList getAuthorizationProtocolName() {
    return this.authorizationProtocolName;
  }

  @java.lang.SuppressWarnings("all")
  public ByteList getAuthorizationProtocolData() {
    return this.authorizationProtocolData;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SetupRequest)) return false;
    final SetupRequest other = (SetupRequest) o;
    if (this.getByteOrder() != other.getByteOrder()) return false;
    if (this.getProtocolMajorVersion() != other.getProtocolMajorVersion()) return false;
    if (this.getProtocolMinorVersion() != other.getProtocolMinorVersion()) return false;
    final java.lang.Object this$authorizationProtocolName = this.getAuthorizationProtocolName();
    final java.lang.Object other$authorizationProtocolName = other.getAuthorizationProtocolName();
    if (this$authorizationProtocolName == null ? other$authorizationProtocolName != null : !this$authorizationProtocolName.equals(other$authorizationProtocolName)) return false;
    final java.lang.Object this$authorizationProtocolData = this.getAuthorizationProtocolData();
    final java.lang.Object other$authorizationProtocolData = other.getAuthorizationProtocolData();
    if (this$authorizationProtocolData == null ? other$authorizationProtocolData != null : !this$authorizationProtocolData.equals(other$authorizationProtocolData)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getByteOrder();
    result = result * PRIME + this.getProtocolMajorVersion();
    result = result * PRIME + this.getProtocolMinorVersion();
    final java.lang.Object $authorizationProtocolName = this.getAuthorizationProtocolName();
    result = result * PRIME + ($authorizationProtocolName == null ? 43 : $authorizationProtocolName.hashCode());
    final java.lang.Object $authorizationProtocolData = this.getAuthorizationProtocolData();
    result = result * PRIME + ($authorizationProtocolData == null ? 43 : $authorizationProtocolData.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SetupRequest(byteOrder=" + this.getByteOrder() + ", protocolMajorVersion=" + this.getProtocolMajorVersion() + ", protocolMinorVersion=" + this.getProtocolMinorVersion() + ", authorizationProtocolName=" + this.getAuthorizationProtocolName() + ", authorizationProtocolData=" + this.getAuthorizationProtocolData() + ")";
  }
}
