package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.TwoWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XReplyFunction;
import java.io.IOException;

public final class CreateLease implements TwoWayRequest<CreateLeaseReply> {
  public static final String PLUGIN_NAME = "randr";
  public static final byte OPCODE = 45;
  private final int window;
  private final int lid;
  private final int[] crtcs;
  private final int[] outputs;

  public XReplyFunction<CreateLeaseReply> getReplyFunction() {
    return (field, sequenceNumber, in) -> CreateLeaseReply.readCreateLeaseReply(field, sequenceNumber, in);
  }

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateLease readCreateLease(X11Input in) throws IOException {
    CreateLease.CreateLeaseBuilder javaBuilder = CreateLease.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    int lid = in.readCard32();
    short numCrtcs = in.readCard16();
    short numOutputs = in.readCard16();
    int[] crtcs = in.readCard32(Short.toUnsignedInt(numCrtcs));
    int[] outputs = in.readCard32(Short.toUnsignedInt(numOutputs));
    javaBuilder.window(window);
    javaBuilder.lid(lid);
    javaBuilder.crtcs(crtcs);
    javaBuilder.outputs(outputs);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(lid);
    short numCrtcs = (short) crtcs.length;
    out.writeCard16(numCrtcs);
    short numOutputs = (short) outputs.length;
    out.writeCard16(numOutputs);
    out.writeCard32(crtcs);
    out.writeCard32(outputs);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 16 + 4 * crtcs.length + 4 * outputs.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CreateLeaseBuilder {
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private int lid;
    @java.lang.SuppressWarnings("all")
    private int[] crtcs;
    @java.lang.SuppressWarnings("all")
    private int[] outputs;

    public int getSize() {
      return 16 + 4 * crtcs.length + 4 * outputs.length;
    }

    @java.lang.SuppressWarnings("all")
    CreateLeaseBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateLease.CreateLeaseBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateLease.CreateLeaseBuilder lid(final int lid) {
      this.lid = lid;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateLease.CreateLeaseBuilder crtcs(final int[] crtcs) {
      if (crtcs == null) {
        throw new java.lang.NullPointerException("crtcs is marked non-null but is null");
      }
      this.crtcs = crtcs;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateLease.CreateLeaseBuilder outputs(final int[] outputs) {
      if (outputs == null) {
        throw new java.lang.NullPointerException("outputs is marked non-null but is null");
      }
      this.outputs = outputs;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CreateLease build() {
      return new CreateLease(this.window, this.lid, this.crtcs, this.outputs);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CreateLease.CreateLeaseBuilder(window=" + this.window + ", lid=" + this.lid + ", crtcs=" + java.util.Arrays.toString(this.crtcs) + ", outputs=" + java.util.Arrays.toString(this.outputs) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CreateLease(final int window, final int lid, final int[] crtcs, final int[] outputs) {
    if (crtcs == null) {
      throw new java.lang.NullPointerException("crtcs is marked non-null but is null");
    }
    if (outputs == null) {
      throw new java.lang.NullPointerException("outputs is marked non-null but is null");
    }
    this.window = window;
    this.lid = lid;
    this.crtcs = crtcs;
    this.outputs = outputs;
  }

  @java.lang.SuppressWarnings("all")
  public static CreateLease.CreateLeaseBuilder builder() {
    return new CreateLease.CreateLeaseBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.SuppressWarnings("all")
  public int getLid() {
    return this.lid;
  }

  @java.lang.SuppressWarnings("all")
  public int[] getCrtcs() {
    return this.crtcs;
  }

  @java.lang.SuppressWarnings("all")
  public int[] getOutputs() {
    return this.outputs;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CreateLease)) return false;
    final CreateLease other = (CreateLease) o;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getLid() != other.getLid()) return false;
    if (!java.util.Arrays.equals(this.getCrtcs(), other.getCrtcs())) return false;
    if (!java.util.Arrays.equals(this.getOutputs(), other.getOutputs())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getLid();
    result = result * PRIME + java.util.Arrays.hashCode(this.getCrtcs());
    result = result * PRIME + java.util.Arrays.hashCode(this.getOutputs());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CreateLease(window=" + this.getWindow() + ", lid=" + this.getLid() + ", crtcs=" + java.util.Arrays.toString(this.getCrtcs()) + ", outputs=" + java.util.Arrays.toString(this.getOutputs()) + ")";
  }
}
