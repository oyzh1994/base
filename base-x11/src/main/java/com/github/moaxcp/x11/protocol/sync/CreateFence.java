package com.github.moaxcp.x11.protocol.sync;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class CreateFence implements OneWayRequest {
  public static final String PLUGIN_NAME = "sync";
  public static final byte OPCODE = 14;
  private final int drawable;
  private final int fence;
  private final boolean initiallyTriggered;

  public byte getOpCode() {
    return OPCODE;
  }

  public static CreateFence readCreateFence(X11Input in) throws IOException {
    CreateFence.CreateFenceBuilder javaBuilder = CreateFence.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int drawable = in.readCard32();
    int fence = in.readCard32();
    boolean initiallyTriggered = in.readBool();
    javaBuilder.drawable(drawable);
    javaBuilder.fence(fence);
    javaBuilder.initiallyTriggered(initiallyTriggered);
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(drawable);
    out.writeCard32(fence);
    out.writeBool(initiallyTriggered);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 13;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CreateFenceBuilder {
    @java.lang.SuppressWarnings("all")
    private int drawable;
    @java.lang.SuppressWarnings("all")
    private int fence;
    @java.lang.SuppressWarnings("all")
    private boolean initiallyTriggered;

    public int getSize() {
      return 13;
    }

    @java.lang.SuppressWarnings("all")
    CreateFenceBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateFence.CreateFenceBuilder drawable(final int drawable) {
      this.drawable = drawable;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateFence.CreateFenceBuilder fence(final int fence) {
      this.fence = fence;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CreateFence.CreateFenceBuilder initiallyTriggered(final boolean initiallyTriggered) {
      this.initiallyTriggered = initiallyTriggered;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CreateFence build() {
      return new CreateFence(this.drawable, this.fence, this.initiallyTriggered);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CreateFence.CreateFenceBuilder(drawable=" + this.drawable + ", fence=" + this.fence + ", initiallyTriggered=" + this.initiallyTriggered + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CreateFence(final int drawable, final int fence, final boolean initiallyTriggered) {
    this.drawable = drawable;
    this.fence = fence;
    this.initiallyTriggered = initiallyTriggered;
  }

  @java.lang.SuppressWarnings("all")
  public static CreateFence.CreateFenceBuilder builder() {
    return new CreateFence.CreateFenceBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getDrawable() {
    return this.drawable;
  }

  @java.lang.SuppressWarnings("all")
  public int getFence() {
    return this.fence;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isInitiallyTriggered() {
    return this.initiallyTriggered;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CreateFence)) return false;
    final CreateFence other = (CreateFence) o;
    if (this.getDrawable() != other.getDrawable()) return false;
    if (this.getFence() != other.getFence()) return false;
    if (this.isInitiallyTriggered() != other.isInitiallyTriggered()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDrawable();
    result = result * PRIME + this.getFence();
    result = result * PRIME + (this.isInitiallyTriggered() ? 79 : 97);
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CreateFence(drawable=" + this.getDrawable() + ", fence=" + this.getFence() + ", initiallyTriggered=" + this.isInitiallyTriggered() + ")";
  }
}
