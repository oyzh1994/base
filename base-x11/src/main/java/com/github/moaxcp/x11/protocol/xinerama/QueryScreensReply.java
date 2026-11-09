package com.github.moaxcp.x11.protocol.xinerama;

import java.util.ArrayList;
import java.util.List;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class QueryScreensReply implements XReply {
  public static final String PLUGIN_NAME = "xinerama";
  private final short sequenceNumber;
  private final List<ScreenInfo> screenInfo;

  public static QueryScreensReply readQueryScreensReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    QueryScreensReply.QueryScreensReplyBuilder javaBuilder = QueryScreensReply.builder();
    int length = in.readCard32();
    int number = in.readCard32();
    byte[] pad5 = in.readPad(20);
    List<ScreenInfo> screenInfo = new ArrayList<>((int) (Integer.toUnsignedLong(number)));
    for (int i = 0; i < Integer.toUnsignedLong(number); i++) {
      screenInfo.add(ScreenInfo.readScreenInfo(in));
    }
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.screenInfo(List.copyOf(screenInfo));
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
    int number = screenInfo.size();
    out.writeCard32(number);
    out.writePad(20);
    for (ScreenInfo t : screenInfo) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(screenInfo);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryScreensReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private List<ScreenInfo> screenInfo;

    public int getSize() {
      return 32 + XObject.sizeOf(screenInfo);
    }

    @java.lang.SuppressWarnings("all")
    QueryScreensReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryScreensReply.QueryScreensReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryScreensReply.QueryScreensReplyBuilder screenInfo(final List<ScreenInfo> screenInfo) {
      if (screenInfo == null) {
        throw new java.lang.NullPointerException("screenInfo is marked non-null but is null");
      }
      this.screenInfo = screenInfo;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryScreensReply build() {
      return new QueryScreensReply(this.sequenceNumber, this.screenInfo);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryScreensReply.QueryScreensReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", screenInfo=" + this.screenInfo + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryScreensReply(final short sequenceNumber, final List<ScreenInfo> screenInfo) {
    if (screenInfo == null) {
      throw new java.lang.NullPointerException("screenInfo is marked non-null but is null");
    }
    this.sequenceNumber = sequenceNumber;
    this.screenInfo = screenInfo;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryScreensReply.QueryScreensReplyBuilder builder() {
    return new QueryScreensReply.QueryScreensReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public List<ScreenInfo> getScreenInfo() {
    return this.screenInfo;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryScreensReply)) return false;
    final QueryScreensReply other = (QueryScreensReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    final java.lang.Object this$screenInfo = this.getScreenInfo();
    final java.lang.Object other$screenInfo = other.getScreenInfo();
    if (this$screenInfo == null ? other$screenInfo != null : !this$screenInfo.equals(other$screenInfo)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    final java.lang.Object $screenInfo = this.getScreenInfo();
    result = result * PRIME + ($screenInfo == null ? 43 : $screenInfo.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryScreensReply(sequenceNumber=" + this.getSequenceNumber() + ", screenInfo=" + this.getScreenInfo() + ")";
  }
}
