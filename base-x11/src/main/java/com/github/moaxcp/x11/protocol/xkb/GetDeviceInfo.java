package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GetDeviceInfo implements TwoWayRequest<GetDeviceInfoReply> {
  public static final String PLUGIN_NAME = "xkb";
  public static final byte OPCODE = 24;
  private final short deviceSpec;
  private final short wanted;
  private final boolean allButtons;
  private final byte firstButton;
  private final byte nButtons;
  private final short ledClass;
  private final short ledID;

  public XReplyFunction<GetDeviceInfoReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetDeviceInfoReply.readGetDeviceInfoReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetDeviceInfo readGetDeviceInfo(X11Input in) throws IOException {
    GetDeviceInfo.GetDeviceInfoBuilder javaBuilder = GetDeviceInfo.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    short deviceSpec = in.readCard16();
    short wanted = in.readCard16();
    boolean allButtons = in.readBool();
    byte firstButton = in.readCard8();
    byte nButtons = in.readCard8();
    byte[] pad8 = in.readPad(1);
    short ledClass = in.readCard16();
    short ledID = in.readCard16();
    javaBuilder.deviceSpec(deviceSpec);
    javaBuilder.wanted(wanted);
    javaBuilder.allButtons(allButtons);
    javaBuilder.firstButton(firstButton);
    javaBuilder.nButtons(nButtons);
    javaBuilder.ledClass(ledClass);
    javaBuilder.ledID(ledID);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard16(deviceSpec);
    out.writeCard16(wanted);
    out.writeBool(allButtons);
    out.writeCard8(firstButton);
    out.writeCard8(nButtons);
    out.writePad(1);
    out.writeCard16(ledClass);
    out.writeCard16(ledID);
  }

  public boolean isWantedEnabled(XIFeature... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (XIFeature m : maskEnums) {
      if (!m.isEnabled(wanted)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 16;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetDeviceInfoBuilder {
    @java.lang.SuppressWarnings("all")
    private short deviceSpec;
    @java.lang.SuppressWarnings("all")
    private short wanted;
    @java.lang.SuppressWarnings("all")
    private boolean allButtons;
    @java.lang.SuppressWarnings("all")
    private byte firstButton;
    @java.lang.SuppressWarnings("all")
    private byte nButtons;
    @java.lang.SuppressWarnings("all")
    private short ledClass;
    @java.lang.SuppressWarnings("all")
    private short ledID;

    public boolean isWantedEnabled(XIFeature... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (XIFeature m : maskEnums) {
        if (!m.isEnabled(wanted)) {
          return false;
        }
      }
      return true;
    }

    public GetDeviceInfo.GetDeviceInfoBuilder wantedEnable(XIFeature... maskEnums) {
      for (XIFeature m : maskEnums) {
        wanted((short) m.enableFor(wanted));
      }
      return this;
    }

    public GetDeviceInfo.GetDeviceInfoBuilder wantedDisable(XIFeature... maskEnums) {
      for (XIFeature m : maskEnums) {
        wanted((short) m.disableFor(wanted));
      }
      return this;
    }

    public GetDeviceInfo.GetDeviceInfoBuilder ledClass(LedClass ledClass) {
      this.ledClass = (short) ledClass.getValue();
      return this;
    }

    public GetDeviceInfo.GetDeviceInfoBuilder ledClass(short ledClass) {
      this.ledClass = ledClass;
      return this;
    }

    public int getSize() {
      return 16;
    }

    @java.lang.SuppressWarnings("all")
    GetDeviceInfoBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetDeviceInfo.GetDeviceInfoBuilder deviceSpec(final short deviceSpec) {
      this.deviceSpec = deviceSpec;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetDeviceInfo.GetDeviceInfoBuilder wanted(final short wanted) {
      this.wanted = wanted;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetDeviceInfo.GetDeviceInfoBuilder allButtons(final boolean allButtons) {
      this.allButtons = allButtons;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetDeviceInfo.GetDeviceInfoBuilder firstButton(final byte firstButton) {
      this.firstButton = firstButton;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetDeviceInfo.GetDeviceInfoBuilder nButtons(final byte nButtons) {
      this.nButtons = nButtons;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetDeviceInfo.GetDeviceInfoBuilder ledID(final short ledID) {
      this.ledID = ledID;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetDeviceInfo build() {
      return new GetDeviceInfo(this.deviceSpec, this.wanted, this.allButtons, this.firstButton, this.nButtons, this.ledClass, this.ledID);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetDeviceInfo.GetDeviceInfoBuilder(deviceSpec=" + this.deviceSpec + ", wanted=" + this.wanted + ", allButtons=" + this.allButtons + ", firstButton=" + this.firstButton + ", nButtons=" + this.nButtons + ", ledClass=" + this.ledClass + ", ledID=" + this.ledID + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetDeviceInfo(final short deviceSpec, final short wanted, final boolean allButtons, final byte firstButton, final byte nButtons, final short ledClass, final short ledID) {
    this.deviceSpec = deviceSpec;
    this.wanted = wanted;
    this.allButtons = allButtons;
    this.firstButton = firstButton;
    this.nButtons = nButtons;
    this.ledClass = ledClass;
    this.ledID = ledID;
  }

  @java.lang.SuppressWarnings("all")
  public static GetDeviceInfo.GetDeviceInfoBuilder builder() {
    return new GetDeviceInfo.GetDeviceInfoBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getDeviceSpec() {
    return this.deviceSpec;
  }

  @java.lang.SuppressWarnings("all")
  public short getWanted() {
    return this.wanted;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isAllButtons() {
    return this.allButtons;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFirstButton() {
    return this.firstButton;
  }

  @java.lang.SuppressWarnings("all")
  public byte getNButtons() {
    return this.nButtons;
  }

  @java.lang.SuppressWarnings("all")
  public short getLedClass() {
    return this.ledClass;
  }

  @java.lang.SuppressWarnings("all")
  public short getLedID() {
    return this.ledID;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetDeviceInfo)) return false;
    final GetDeviceInfo other = (GetDeviceInfo) o;
    if (this.getDeviceSpec() != other.getDeviceSpec()) return false;
    if (this.getWanted() != other.getWanted()) return false;
    if (this.isAllButtons() != other.isAllButtons()) return false;
    if (this.getFirstButton() != other.getFirstButton()) return false;
    if (this.getNButtons() != other.getNButtons()) return false;
    if (this.getLedClass() != other.getLedClass()) return false;
    if (this.getLedID() != other.getLedID()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDeviceSpec();
    result = result * PRIME + this.getWanted();
    result = result * PRIME + (this.isAllButtons() ? 79 : 97);
    result = result * PRIME + this.getFirstButton();
    result = result * PRIME + this.getNButtons();
    result = result * PRIME + this.getLedClass();
    result = result * PRIME + this.getLedID();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetDeviceInfo(deviceSpec=" + this.getDeviceSpec() + ", wanted=" + this.getWanted() + ", allButtons=" + this.isAllButtons() + ", firstButton=" + this.getFirstButton() + ", nButtons=" + this.getNButtons() + ", ledClass=" + this.getLedClass() + ", ledID=" + this.getLedID() + ")";
  }
}
