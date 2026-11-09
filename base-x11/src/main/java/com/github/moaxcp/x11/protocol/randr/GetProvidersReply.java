package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class GetProvidersReply implements XReply {
  public static final String PLUGIN_NAME = "randr";
  private final short sequenceNumber;
  private final int timestamp;
  private final int[] providers;

  public static GetProvidersReply readGetProvidersReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    GetProvidersReply.GetProvidersReplyBuilder javaBuilder = GetProvidersReply.builder();
    int length = in.readCard32();
    int timestamp = in.readCard32();
    short numProviders = in.readCard16();
    byte[] pad6 = in.readPad(18);
    int[] providers = in.readCard32(Short.toUnsignedInt(numProviders));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.timestamp(timestamp);
    javaBuilder.providers(providers);
    if (javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(timestamp);
    short numProviders = (short) providers.length;
    out.writeCard16(numProviders);
    out.writePad(18);
    out.writeCard32(providers);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 4 * providers.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetProvidersReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int timestamp;
    @java.lang.SuppressWarnings("all")
    private int[] providers;

    public int getSize() {
      return 32 + 4 * providers.length;
    }

    @java.lang.SuppressWarnings("all")
    GetProvidersReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetProvidersReply.GetProvidersReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetProvidersReply.GetProvidersReplyBuilder timestamp(final int timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetProvidersReply.GetProvidersReplyBuilder providers(final int[] providers) {
      if (providers == null) {
        throw new java.lang.NullPointerException("providers is marked non-null but is null");
      }
      this.providers = providers;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetProvidersReply build() {
      return new GetProvidersReply(this.sequenceNumber, this.timestamp, this.providers);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetProvidersReply.GetProvidersReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", timestamp=" + this.timestamp + ", providers=" + java.util.Arrays.toString(this.providers) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetProvidersReply(final short sequenceNumber, final int timestamp, final int[] providers) {
    if (providers == null) {
      throw new java.lang.NullPointerException("providers is marked non-null but is null");
    }
    this.sequenceNumber = sequenceNumber;
    this.timestamp = timestamp;
    this.providers = providers;
  }

  @java.lang.SuppressWarnings("all")
  public static GetProvidersReply.GetProvidersReplyBuilder builder() {
    return new GetProvidersReply.GetProvidersReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getTimestamp() {
    return this.timestamp;
  }

  @java.lang.SuppressWarnings("all")
  public int[] getProviders() {
    return this.providers;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetProvidersReply)) return false;
    final GetProvidersReply other = (GetProvidersReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getTimestamp() != other.getTimestamp()) return false;
    if (!java.util.Arrays.equals(this.getProviders(), other.getProviders())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getTimestamp();
    result = result * PRIME + java.util.Arrays.hashCode(this.getProviders());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetProvidersReply(sequenceNumber=" + this.getSequenceNumber() + ", timestamp=" + this.getTimestamp() + ", providers=" + java.util.Arrays.toString(this.getProviders()) + ")";
  }
}
