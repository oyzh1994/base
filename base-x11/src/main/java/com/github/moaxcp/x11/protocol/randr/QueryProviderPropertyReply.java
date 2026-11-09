package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class QueryProviderPropertyReply implements XReply {
  public static final String PLUGIN_NAME = "randr";
  private final short sequenceNumber;
  private final boolean pending;
  private final boolean range;
  private final boolean immutable;
  private final int[] validValues;

  public static QueryProviderPropertyReply readQueryProviderPropertyReply(byte pad1, short sequenceNumber, X11Input in) throws IOException {
    QueryProviderPropertyReply.QueryProviderPropertyReplyBuilder javaBuilder = QueryProviderPropertyReply.builder();
    int length = in.readCard32();
    boolean pending = in.readBool();
    boolean range = in.readBool();
    boolean immutable = in.readBool();
    byte[] pad7 = in.readPad(21);
    int[] validValues = in.readInt32((int) (Integer.toUnsignedLong(length)));
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.pending(pending);
    javaBuilder.range(range);
    javaBuilder.immutable(immutable);
    javaBuilder.validValues(validValues);
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
    int length = validValues.length;
    out.writeCard32(getLength());
    out.writeBool(pending);
    out.writeBool(range);
    out.writeBool(immutable);
    out.writePad(21);
    out.writeInt32(validValues);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + 4 * validValues.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class QueryProviderPropertyReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private boolean pending;
    @java.lang.SuppressWarnings("all")
    private boolean range;
    @java.lang.SuppressWarnings("all")
    private boolean immutable;
    @java.lang.SuppressWarnings("all")
    private int[] validValues;

    public int getSize() {
      return 32 + 4 * validValues.length;
    }

    @java.lang.SuppressWarnings("all")
    QueryProviderPropertyReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryProviderPropertyReply.QueryProviderPropertyReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryProviderPropertyReply.QueryProviderPropertyReplyBuilder pending(final boolean pending) {
      this.pending = pending;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryProviderPropertyReply.QueryProviderPropertyReplyBuilder range(final boolean range) {
      this.range = range;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryProviderPropertyReply.QueryProviderPropertyReplyBuilder immutable(final boolean immutable) {
      this.immutable = immutable;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public QueryProviderPropertyReply.QueryProviderPropertyReplyBuilder validValues(final int[] validValues) {
      if (validValues == null) {
        throw new java.lang.NullPointerException("validValues is marked non-null but is null");
      }
      this.validValues = validValues;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public QueryProviderPropertyReply build() {
      return new QueryProviderPropertyReply(this.sequenceNumber, this.pending, this.range, this.immutable, this.validValues);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "QueryProviderPropertyReply.QueryProviderPropertyReplyBuilder(sequenceNumber=" + this.sequenceNumber + ", pending=" + this.pending + ", range=" + this.range + ", immutable=" + this.immutable + ", validValues=" + java.util.Arrays.toString(this.validValues) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  QueryProviderPropertyReply(final short sequenceNumber, final boolean pending, final boolean range, final boolean immutable, final int[] validValues) {
    if (validValues == null) {
      throw new java.lang.NullPointerException("validValues is marked non-null but is null");
    }
    this.sequenceNumber = sequenceNumber;
    this.pending = pending;
    this.range = range;
    this.immutable = immutable;
    this.validValues = validValues;
  }

  @java.lang.SuppressWarnings("all")
  public static QueryProviderPropertyReply.QueryProviderPropertyReplyBuilder builder() {
    return new QueryProviderPropertyReply.QueryProviderPropertyReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isPending() {
    return this.pending;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isRange() {
    return this.range;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isImmutable() {
    return this.immutable;
  }

  @java.lang.SuppressWarnings("all")
  public int[] getValidValues() {
    return this.validValues;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof QueryProviderPropertyReply)) return false;
    final QueryProviderPropertyReply other = (QueryProviderPropertyReply) o;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.isPending() != other.isPending()) return false;
    if (this.isRange() != other.isRange()) return false;
    if (this.isImmutable() != other.isImmutable()) return false;
    if (!java.util.Arrays.equals(this.getValidValues(), other.getValidValues())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + (this.isPending() ? 79 : 97);
    result = result * PRIME + (this.isRange() ? 79 : 97);
    result = result * PRIME + (this.isImmutable() ? 79 : 97);
    result = result * PRIME + java.util.Arrays.hashCode(this.getValidValues());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "QueryProviderPropertyReply(sequenceNumber=" + this.getSequenceNumber() + ", pending=" + this.isPending() + ", range=" + this.isRange() + ", immutable=" + this.isImmutable() + ", validValues=" + java.util.Arrays.toString(this.getValidValues()) + ")";
  }
}
