package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class GetOutputInfo implements TwoWayRequest<GetOutputInfoReply> {
  public static final String PLUGIN_NAME = "randr";
  public static final byte OPCODE = 9;
  private final int output;
  private final int configTimestamp;

  public XReplyFunction<GetOutputInfoReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> GetOutputInfoReply.readGetOutputInfoReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static GetOutputInfo readGetOutputInfo(X11Input in) throws IOException {
    GetOutputInfo.GetOutputInfoBuilder javaBuilder = GetOutputInfo.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int output = in.readCard32();
    int configTimestamp = in.readCard32();
    javaBuilder.output(output);
    javaBuilder.configTimestamp(configTimestamp);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(output);
    out.writeCard32(configTimestamp);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetOutputInfoBuilder {
    @java.lang.SuppressWarnings("all")
    private int output;
    @java.lang.SuppressWarnings("all")
    private int configTimestamp;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    GetOutputInfoBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetOutputInfo.GetOutputInfoBuilder output(final int output) {
      this.output = output;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetOutputInfo.GetOutputInfoBuilder configTimestamp(final int configTimestamp) {
      this.configTimestamp = configTimestamp;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetOutputInfo build() {
      return new GetOutputInfo(this.output, this.configTimestamp);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetOutputInfo.GetOutputInfoBuilder(output=" + this.output + ", configTimestamp=" + this.configTimestamp + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetOutputInfo(final int output, final int configTimestamp) {
    this.output = output;
    this.configTimestamp = configTimestamp;
  }

  @java.lang.SuppressWarnings("all")
  public static GetOutputInfo.GetOutputInfoBuilder builder() {
    return new GetOutputInfo.GetOutputInfoBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getOutput() {
    return this.output;
  }

  @java.lang.SuppressWarnings("all")
  public int getConfigTimestamp() {
    return this.configTimestamp;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetOutputInfo)) return false;
    final GetOutputInfo other = (GetOutputInfo) o;
    if (this.getOutput() != other.getOutput()) return false;
    if (this.getConfigTimestamp() != other.getConfigTimestamp()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getOutput();
    result = result * PRIME + this.getConfigTimestamp();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetOutputInfo(output=" + this.getOutput() + ", configTimestamp=" + this.getConfigTimestamp() + ")";
  }
}
