package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class QueryExtensionReply implements XReply {
  public static final String PLUGIN_NAME = "xproto";
  private final short sequenceNumber;
  private final boolean present;
  private final byte majorOpcode;
  private final byte firstEvent;
  private final byte firstError;

  public static QueryExtensionReply readQueryExtensionReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    QueryExtensionReply.QueryExtensionReplyBuilder javaBuilder = QueryExtensionReply.builder();
    int length = in.readCard32();
    boolean present = in.readBool();
    byte majorOpcode = in.readCard8();
    byte firstEvent = in.readCard8();
    byte firstError = in.readCard8();
    in.readPad(20);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.present(present);
    javaBuilder.majorOpcode(majorOpcode);
    javaBuilder.firstEvent(firstEvent);
    javaBuilder.firstError(firstError);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writePad(1);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    out.writeBool(present);
    out.writeCard8(majorOpcode);
    out.writeCard8(firstEvent);
    out.writeCard8(firstError);
  }

  @Override
  public int getSize() {
    return 12;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryExtensionReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private boolean present;
    @java.lang.SuppressWarnings("all")
    private byte majorOpcode;
    @java.lang.SuppressWarnings("all")
    private byte firstEvent;
    @java.lang.SuppressWarnings("all")
    private byte firstError;

    public int getSize() {
      return 12;
    }

    @java.lang.SuppressWarnings("all")
    QueryExtensionReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryExtensionReply.QueryExtensionReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryExtensionReply.QueryExtensionReplyBuilder present(final boolean present) {
      this.present = present;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryExtensionReply.QueryExtensionReplyBuilder majorOpcode(final byte majorOpcode) {
      this.majorOpcode = majorOpcode;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryExtensionReply.QueryExtensionReplyBuilder firstEvent(final byte firstEvent) {
      this.firstEvent = firstEvent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryExtensionReply.QueryExtensionReplyBuilder firstError(final byte firstError) {
      this.firstError = firstError;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryExtensionReply build() {
      return new QueryExtensionReply(this.sequenceNumber, this.present, this.majorOpcode, this.firstEvent, this.firstError);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryExtensionReply.QueryExtensionReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", present=" + this.present + ", majorOpcode=" + this.majorOpcode + ", firstEvent=" + this.firstEvent + ", firstError=" + this.firstError + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryExtensionReply(final short sequenceNumber, final boolean present, final byte majorOpcode, final byte firstEvent, final byte firstError) {
    this.sequenceNumber = sequenceNumber;
    this.present = present;
    this.majorOpcode = majorOpcode;
    this.firstEvent = firstEvent;
    this.firstError = firstError;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryExtensionReply.QueryExtensionReplyBuilder builder() {
    return new QueryExtensionReply.QueryExtensionReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isPresent() {
    return this.present;
  }

  @java.lang.SuppressWarnings("all")
  public byte getMajorOpcode() {
    return this.majorOpcode;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFirstEvent() {
    return this.firstEvent;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFirstError() {
    return this.firstError;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryExtensionReply)) return false;
    final QueryExtensionReply other = (QueryExtensionReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.isPresent() != other.isPresent()) return false;
    if (this.getMajorOpcode() != other.getMajorOpcode()) return false;
    if (this.getFirstEvent() != other.getFirstEvent()) return false;
    if (this.getFirstError() != other.getFirstError()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + (this.isPresent() ? 79 : 97);
    result = result * PRIME + this.getMajorOpcode();
    result = result * PRIME + this.getFirstEvent();
    result = result * PRIME + this.getFirstError();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryExtensionReply(sequenceNumber=" + this.getSequenceNumber() + ", present=" + this.isPresent() + ", majorOpcode=" + this.getMajorOpcode() + ", firstEvent=" + this.getFirstEvent() + ", firstError=" + this.getFirstError() + ")";
  }
}
