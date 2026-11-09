package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class ListOutputProperties implements TwoWayRequest<ListOutputPropertiesReply> {
  public static final String PLUGIN_NAME = "randr";
  public static final byte OPCODE = 10;
  private final int output;

  public XReplyFunction<ListOutputPropertiesReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> ListOutputPropertiesReply.readListOutputPropertiesReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static ListOutputProperties readListOutputProperties(X11Input in) throws IOException {
    ListOutputProperties.ListOutputPropertiesBuilder javaBuilder = ListOutputProperties.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int output = in.readCard32();
    javaBuilder.output(output);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(output);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ListOutputPropertiesBuilder {
    @java.lang.SuppressWarnings("all")
    private int output;

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    ListOutputPropertiesBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListOutputProperties.ListOutputPropertiesBuilder output(final int output) {
      this.output = output;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ListOutputProperties build() {
      return new ListOutputProperties(this.output);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ListOutputProperties.ListOutputPropertiesBuilder(output=" + this.output + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ListOutputProperties(final int output) {
    this.output = output;
  }

  @java.lang.SuppressWarnings("all")
  public static ListOutputProperties.ListOutputPropertiesBuilder builder() {
    return new ListOutputProperties.ListOutputPropertiesBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getOutput() {
    return this.output;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ListOutputProperties)) return false;
    final ListOutputProperties other = (ListOutputProperties) o;
    if (this.getOutput() != other.getOutput()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getOutput();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ListOutputProperties(output=" + this.getOutput() + ")";
  }
}
