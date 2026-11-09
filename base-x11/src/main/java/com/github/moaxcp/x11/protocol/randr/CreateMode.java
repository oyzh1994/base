package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class CreateMode implements TwoWayRequest<CreateModeReply> {
  public static final String PLUGIN_NAME = "randr";
  public static final byte OPCODE = 16;
  private final int window;
  private final ModeInfo modeInfo;
  private final byte[] name;

  public XReplyFunction<CreateModeReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> CreateModeReply.readCreateModeReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateMode readCreateMode(X11Input in) throws IOException {
    CreateMode.CreateModeBuilder javaBuilder = CreateMode.builder();
    int javaStart = 1;
    byte majorOpcode = in.readCard8();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int window = in.readCard32();
    javaStart += 4;
    ModeInfo modeInfo = ModeInfo.readModeInfo(in);
    javaStart += modeInfo.getSize();
    byte[] name = in.readChar(Short.toUnsignedInt(length) - javaStart);
    javaBuilder.window(window);
    javaBuilder.modeInfo(modeInfo);
    javaBuilder.name(name);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    modeInfo.write(out);
    out.writeChar(name);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 40 + 1 * name.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CreateModeBuilder {
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private ModeInfo modeInfo;
    @java.lang.SuppressWarnings("all")
    private byte[] name;

    public int getSize() {
      return 40 + 1 * name.length;
    }

    @java.lang.SuppressWarnings("all")
    CreateModeBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateMode.CreateModeBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateMode.CreateModeBuilder modeInfo(final ModeInfo modeInfo) {
      if (modeInfo == null) {
        throw new java.lang.NullPointerException("modeInfo is marked non-null but is null");
      }
      this.modeInfo = modeInfo;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateMode.CreateModeBuilder name(final byte[] name) {
      if (name == null) {
        throw new java.lang.NullPointerException("name is marked non-null but is null");
      }
      this.name = name;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CreateMode build() {
      return new CreateMode(this.window, this.modeInfo, this.name);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CreateMode.CreateModeBuilder(window=" + this.window + ", modeInfo=" + this.modeInfo + ", name=" + java.util.Arrays.toString(this.name) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CreateMode(final int window, final ModeInfo modeInfo, final byte[] name) {
    if (modeInfo == null) {
      throw new java.lang.NullPointerException("modeInfo is marked non-null but is null");
    }
    if (name == null) {
      throw new java.lang.NullPointerException("name is marked non-null but is null");
    }
    this.window = window;
    this.modeInfo = modeInfo;
    this.name = name;
  }

  @java.lang.SuppressWarnings("all")
  public static CreateMode.CreateModeBuilder builder() {
    return new CreateMode.CreateModeBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.SuppressWarnings("all")
  public ModeInfo getModeInfo() {
    return this.modeInfo;
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getName() {
    return this.name;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CreateMode)) return false;
    final CreateMode other = (CreateMode) o;
    if (this.getWindow() != other.getWindow()) return false;
    final java.lang.Object this$modeInfo = this.getModeInfo();
    final java.lang.Object other$modeInfo = other.getModeInfo();
    if (this$modeInfo == null ? other$modeInfo != null : !this$modeInfo.equals(other$modeInfo)) return false;
    if (!java.util.Arrays.equals(this.getName(), other.getName())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getWindow();
    final java.lang.Object $modeInfo = this.getModeInfo();
    result = result * PRIME + ($modeInfo == null ? 43 : $modeInfo.hashCode());
    result = result * PRIME + java.util.Arrays.hashCode(this.getName());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CreateMode(window=" + this.getWindow() + ", modeInfo=" + this.getModeInfo() + ", name=" + java.util.Arrays.toString(this.getName()) + ")";
  }
}
