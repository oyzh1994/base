package com.github.moaxcp.x11.protocol.screensaver;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class QueryInfoReply implements XReply {
  public static final String PLUGIN_NAME = "screensaver";
  private final byte state;
  private final short sequenceNumber;
  private final int saverWindow;
  private final int msUntilServer;
  private final int msSinceUserInput;
  private final int eventMask;
  private final byte kind;

  public static QueryInfoReply readQueryInfoReply(byte state, short sequenceNumber, X11Input in) throws IOException {
    QueryInfoReply.QueryInfoReplyBuilder javaBuilder = QueryInfoReply.builder();
    int length = in.readCard32();
    int saverWindow = in.readCard32();
    int msUntilServer = in.readCard32();
    int msSinceUserInput = in.readCard32();
    int eventMask = in.readCard32();
    byte kind = in.readByte();
    byte[] pad9 = in.readPad(7);
    javaBuilder.state(state);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.saverWindow(saverWindow);
    javaBuilder.msUntilServer(msUntilServer);
    javaBuilder.msSinceUserInput(msSinceUserInput);
    javaBuilder.eventMask(eventMask);
    javaBuilder.kind(kind);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(state);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeCard32(saverWindow);
    out.writeCard32(msUntilServer);
    out.writeCard32(msSinceUserInput);
    out.writeCard32(eventMask);
    out.writeByte(kind);
    out.writePad(7);
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryInfoReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte state;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int saverWindow;
    @java.lang.SuppressWarnings("all")
    private int msUntilServer;
    @java.lang.SuppressWarnings("all")
    private int msSinceUserInput;
    @java.lang.SuppressWarnings("all")
    private int eventMask;
    @java.lang.SuppressWarnings("all")
    private byte kind;

    public QueryInfoReply.QueryInfoReplyBuilder kind(Kind kind) {
      this.kind = (byte) kind.getValue();
      return this;
    }

    public QueryInfoReply.QueryInfoReplyBuilder kind(byte kind) {
      this.kind = kind;
      return this;
    }

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    QueryInfoReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryInfoReply.QueryInfoReplyBuilder state(final byte state) {
      this.state = state;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryInfoReply.QueryInfoReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryInfoReply.QueryInfoReplyBuilder saverWindow(final int saverWindow) {
      this.saverWindow = saverWindow;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryInfoReply.QueryInfoReplyBuilder msUntilServer(final int msUntilServer) {
      this.msUntilServer = msUntilServer;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryInfoReply.QueryInfoReplyBuilder msSinceUserInput(final int msSinceUserInput) {
      this.msSinceUserInput = msSinceUserInput;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryInfoReply.QueryInfoReplyBuilder eventMask(final int eventMask) {
      this.eventMask = eventMask;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryInfoReply build() {
      return new QueryInfoReply(this.state, this.sequenceNumber, this.saverWindow, this.msUntilServer, this.msSinceUserInput, this.eventMask, this.kind);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryInfoReply.QueryInfoReplyBuilder(state=" + this.state + ", sequenceNumber=" + this.sequenceNumber + ", saverWindow=" + this.saverWindow + ", msUntilServer=" + this.msUntilServer + ", msSinceUserInput=" + this.msSinceUserInput + ", eventMask=" + this.eventMask + ", kind=" + this.kind + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryInfoReply(final byte state, final short sequenceNumber, final int saverWindow, final int msUntilServer, final int msSinceUserInput, final int eventMask, final byte kind) {
    this.state = state;
    this.sequenceNumber = sequenceNumber;
    this.saverWindow = saverWindow;
    this.msUntilServer = msUntilServer;
    this.msSinceUserInput = msSinceUserInput;
    this.eventMask = eventMask;
    this.kind = kind;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryInfoReply.QueryInfoReplyBuilder builder() {
    return new QueryInfoReply.QueryInfoReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getState() {
    return this.state;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getSaverWindow() {
    return this.saverWindow;
  }

  @java.lang.SuppressWarnings("all")
  public int getMsUntilServer() {
    return this.msUntilServer;
  }

  @java.lang.SuppressWarnings("all")
  public int getMsSinceUserInput() {
    return this.msSinceUserInput;
  }

  @java.lang.SuppressWarnings("all")
  public int getEventMask() {
    return this.eventMask;
  }

  @java.lang.SuppressWarnings("all")
  public byte getKind() {
    return this.kind;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryInfoReply)) return false;
    final QueryInfoReply other = (QueryInfoReply) o;
    if (this.getState() != other.getState()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getSaverWindow() != other.getSaverWindow()) return false;
    if (this.getMsUntilServer() != other.getMsUntilServer()) return false;
    if (this.getMsSinceUserInput() != other.getMsSinceUserInput()) return false;
    if (this.getEventMask() != other.getEventMask()) return false;
    if (this.getKind() != other.getKind()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getState();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getSaverWindow();
    result = result * PRIME + this.getMsUntilServer();
    result = result * PRIME + this.getMsSinceUserInput();
    result = result * PRIME + this.getEventMask();
    result = result * PRIME + this.getKind();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryInfoReply(state=" + this.getState() + ", sequenceNumber=" + this.getSequenceNumber() + ", saverWindow=" + this.getSaverWindow() + ", msUntilServer=" + this.getMsUntilServer() + ", msSinceUserInput=" + this.getMsSinceUserInput() + ", eventMask=" + this.getEventMask() + ", kind=" + this.getKind() + ")";
  }
}
