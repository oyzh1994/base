package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class ConvertSelection implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 24;
  private final int requestor;
  private final int selection;
  private final int target;
  private final int property;
  private final int time;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ConvertSelection readConvertSelection(X11Input in) throws IOException {
    ConvertSelection.ConvertSelectionBuilder javaBuilder = ConvertSelection.builder();
    byte[] pad1 = in.readPad(1);
    short length = in.readCard16();
    int requestor = in.readCard32();
    int selection = in.readCard32();
    int target = in.readCard32();
    int property = in.readCard32();
    int time = in.readCard32();
    javaBuilder.requestor(requestor);
    javaBuilder.selection(selection);
    javaBuilder.target(target);
    javaBuilder.property(property);
    javaBuilder.time(time);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writePad(1);
    out.writeCard16((short) getLength());
    out.writeCard32(requestor);
    out.writeCard32(selection);
    out.writeCard32(target);
    out.writeCard32(property);
    out.writeCard32(time);
  }

  @Override
  public int getSize() {
    return 24;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ConvertSelectionBuilder {
    @java.lang.SuppressWarnings("all")
    private int requestor;
    @java.lang.SuppressWarnings("all")
    private int selection;
    @java.lang.SuppressWarnings("all")
    private int target;
    @java.lang.SuppressWarnings("all")
    private int property;
    @java.lang.SuppressWarnings("all")
    private int time;

    public int getSize() {
      return 24;
    }

    @java.lang.SuppressWarnings("all")
    ConvertSelectionBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConvertSelection.ConvertSelectionBuilder requestor(final int requestor) {
      this.requestor = requestor;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConvertSelection.ConvertSelectionBuilder selection(final int selection) {
      this.selection = selection;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConvertSelection.ConvertSelectionBuilder target(final int target) {
      this.target = target;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConvertSelection.ConvertSelectionBuilder property(final int property) {
      this.property = property;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ConvertSelection.ConvertSelectionBuilder time(final int time) {
      this.time = time;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ConvertSelection build() {
      return new ConvertSelection(this.requestor, this.selection, this.target, this.property, this.time);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ConvertSelection.ConvertSelectionBuilder(requestor=" + this.requestor + ", selection=" + this.selection + ", target=" + this.target + ", property=" + this.property + ", time=" + this.time + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ConvertSelection(final int requestor, final int selection, final int target, final int property, final int time) {
    this.requestor = requestor;
    this.selection = selection;
    this.target = target;
    this.property = property;
    this.time = time;
  }

  @java.lang.SuppressWarnings("all")
  public static ConvertSelection.ConvertSelectionBuilder builder() {
    return new ConvertSelection.ConvertSelectionBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getRequestor() {
    return this.requestor;
  }

  @java.lang.SuppressWarnings("all")
  public int getSelection() {
    return this.selection;
  }

  @java.lang.SuppressWarnings("all")
  public int getTarget() {
    return this.target;
  }

  @java.lang.SuppressWarnings("all")
  public int getProperty() {
    return this.property;
  }

  @java.lang.SuppressWarnings("all")
  public int getTime() {
    return this.time;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ConvertSelection)) return false;
    final ConvertSelection other = (ConvertSelection) o;
    if (this.getRequestor() != other.getRequestor()) return false;
    if (this.getSelection() != other.getSelection()) return false;
    if (this.getTarget() != other.getTarget()) return false;
    if (this.getProperty() != other.getProperty()) return false;
    if (this.getTime() != other.getTime()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getRequestor();
    result = result * PRIME + this.getSelection();
    result = result * PRIME + this.getTarget();
    result = result * PRIME + this.getProperty();
    result = result * PRIME + this.getTime();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ConvertSelection(requestor=" + this.getRequestor() + ", selection=" + this.getSelection() + ", target=" + this.getTarget() + ", property=" + this.getProperty() + ", time=" + this.getTime() + ")";
  }
}
