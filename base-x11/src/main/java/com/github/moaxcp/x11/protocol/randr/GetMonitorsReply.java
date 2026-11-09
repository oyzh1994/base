package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public final class GetMonitorsReply implements XReply {
  public static final String PLUGIN_NAME = "randr";
  private final short sequenceNumber;
  private final int timestamp;
  private final int nOutputs;
  private final ImmutableList<MonitorInfo> monitors;

  public static GetMonitorsReply readGetMonitorsReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    GetMonitorsReply.GetMonitorsReplyBuilder javaBuilder = GetMonitorsReply.builder();
    int length = in.readCard32();
    int timestamp = in.readCard32();
    int nMonitors = in.readCard32();
    int nOutputs = in.readCard32();
    byte[] pad7 = in.readPad(12);
    MutableList<MonitorInfo> monitors = Lists.mutable.withInitialCapacity((int) (Integer.toUnsignedLong(nMonitors)));
    for (int i = 0; i < Integer.toUnsignedLong(nMonitors); i++) {
      monitors.add(MonitorInfo.readMonitorInfo(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.timestamp(timestamp);
    javaBuilder.nOutputs(nOutputs);
    javaBuilder.monitors(monitors.toImmutable());
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
    int nMonitors = monitors.size();
    out.writeCard32(nMonitors);
    out.writeCard32(nOutputs);
    out.writePad(12);
    for (MonitorInfo t : monitors) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(monitors);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class GetMonitorsReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int timestamp;
    @java.lang.SuppressWarnings("all")
    private int nOutputs;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<MonitorInfo> monitors;

    public int getSize() {
      return 32 + XObject.sizeOf(monitors);
    }

    @java.lang.SuppressWarnings("all")
    GetMonitorsReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMonitorsReply.GetMonitorsReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMonitorsReply.GetMonitorsReplyBuilder timestamp(final int timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMonitorsReply.GetMonitorsReplyBuilder nOutputs(final int nOutputs) {
      this.nOutputs = nOutputs;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public GetMonitorsReply.GetMonitorsReplyBuilder monitors(final ImmutableList<MonitorInfo> monitors) {
      if (monitors == null) {
        throw new java.lang.NullPointerException("monitors is marked non-null but is null");
      }
      this.monitors = monitors;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public GetMonitorsReply build() {
      return new GetMonitorsReply(this.sequenceNumber, this.timestamp, this.nOutputs, this.monitors);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "GetMonitorsReply.GetMonitorsReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", timestamp=" + this.timestamp + ", nOutputs=" + this.nOutputs + ", monitors=" + this.monitors + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  GetMonitorsReply(final short sequenceNumber, final int timestamp, final int nOutputs, final ImmutableList<MonitorInfo> monitors) {
    if (monitors == null) {
      throw new java.lang.NullPointerException("monitors is marked non-null but is null");
    }
    this.sequenceNumber = sequenceNumber;
    this.timestamp = timestamp;
    this.nOutputs = nOutputs;
    this.monitors = monitors;
  }

  @java.lang.SuppressWarnings("all")
  public static GetMonitorsReply.GetMonitorsReplyBuilder builder() {
    return new GetMonitorsReply.GetMonitorsReplyBuilder();
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
  public int getNOutputs() {
    return this.nOutputs;
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<MonitorInfo> getMonitors() {
    return this.monitors;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof GetMonitorsReply)) return false;
    final GetMonitorsReply other = (GetMonitorsReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getTimestamp() != other.getTimestamp()) return false;
    if (this.getNOutputs() != other.getNOutputs()) return false;
    final java.lang.Object this$monitors = this.getMonitors();
    final java.lang.Object other$monitors = other.getMonitors();
    if (this$monitors == null ? other$monitors != null : !this$monitors.equals(other$monitors)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getTimestamp();
    result = result * PRIME + this.getNOutputs();
    final java.lang.Object $monitors = this.getMonitors();
    result = result * PRIME + ($monitors == null ? 43 : $monitors.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "GetMonitorsReply(sequenceNumber=" + this.getSequenceNumber() + ", timestamp=" + this.getTimestamp() + ", nOutputs=" + this.getNOutputs() + ", monitors=" + this.getMonitors() + ")";
  }
}
