package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.IntList;

public final class ChangeKeyboardMapping implements OneWayRequest {
  public static final String PLUGIN_NAME = "xproto";
  public static final byte OPCODE = 100;
  private final byte keycodeCount;
  private final byte firstKeycode;
  private final byte keysymsPerKeycode;
  private final IntList keysyms;

  public byte getOpCode() {
    return OPCODE;
  }

  public static ChangeKeyboardMapping readChangeKeyboardMapping(X11Input in) throws IOException {
    ChangeKeyboardMapping.ChangeKeyboardMappingBuilder javaBuilder = ChangeKeyboardMapping.builder();
    byte keycodeCount = in.readCard8();
    short length = in.readCard16();
    byte firstKeycode = in.readCard8();
    byte keysymsPerKeycode = in.readCard8();
    byte[] pad5 = in.readPad(2);
    IntList keysyms = in.readCard32(Byte.toUnsignedInt(keycodeCount) * Byte.toUnsignedInt(keysymsPerKeycode));
    javaBuilder.keycodeCount(keycodeCount);
    javaBuilder.firstKeycode(firstKeycode);
    javaBuilder.keysymsPerKeycode(keysymsPerKeycode);
    javaBuilder.keysyms(keysyms.toImmutable());
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard8(keycodeCount);
    out.writeCard16((short) getLength());
    out.writeCard8(firstKeycode);
    out.writeCard8(keysymsPerKeycode);
    out.writePad(2);
    out.writeCard32(keysyms);
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 8 + 4 * keysyms.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ChangeKeyboardMappingBuilder {
    @java.lang.SuppressWarnings("all")
    private byte keycodeCount;
    @java.lang.SuppressWarnings("all")
    private byte firstKeycode;
    @java.lang.SuppressWarnings("all")
    private byte keysymsPerKeycode;
    @java.lang.SuppressWarnings("all")
    private IntList keysyms;

    public int getSize() {
      return 8 + 4 * keysyms.size();
    }

    @java.lang.SuppressWarnings("all")
    ChangeKeyboardMappingBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangeKeyboardMapping.ChangeKeyboardMappingBuilder keycodeCount(final byte keycodeCount) {
      this.keycodeCount = keycodeCount;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangeKeyboardMapping.ChangeKeyboardMappingBuilder firstKeycode(final byte firstKeycode) {
      this.firstKeycode = firstKeycode;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangeKeyboardMapping.ChangeKeyboardMappingBuilder keysymsPerKeycode(final byte keysymsPerKeycode) {
      this.keysymsPerKeycode = keysymsPerKeycode;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ChangeKeyboardMapping.ChangeKeyboardMappingBuilder keysyms(final IntList keysyms) {
      if (keysyms == null) {
        throw new java.lang.NullPointerException("keysyms is marked non-null but is null");
      }
      this.keysyms = keysyms;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ChangeKeyboardMapping build() {
      return new ChangeKeyboardMapping(this.keycodeCount, this.firstKeycode, this.keysymsPerKeycode, this.keysyms);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ChangeKeyboardMapping.ChangeKeyboardMappingBuilder(keycodeCount=" + this.keycodeCount + ", firstKeycode=" + this.firstKeycode + ", keysymsPerKeycode=" + this.keysymsPerKeycode + ", keysyms=" + this.keysyms + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ChangeKeyboardMapping(final byte keycodeCount, final byte firstKeycode, final byte keysymsPerKeycode, final IntList keysyms) {
    if (keysyms == null) {
      throw new java.lang.NullPointerException("keysyms is marked non-null but is null");
    }
    this.keycodeCount = keycodeCount;
    this.firstKeycode = firstKeycode;
    this.keysymsPerKeycode = keysymsPerKeycode;
    this.keysyms = keysyms;
  }

  @java.lang.SuppressWarnings("all")
  public static ChangeKeyboardMapping.ChangeKeyboardMappingBuilder builder() {
    return new ChangeKeyboardMapping.ChangeKeyboardMappingBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getKeycodeCount() {
    return this.keycodeCount;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFirstKeycode() {
    return this.firstKeycode;
  }

  @java.lang.SuppressWarnings("all")
  public byte getKeysymsPerKeycode() {
    return this.keysymsPerKeycode;
  }

  @java.lang.SuppressWarnings("all")
  public IntList getKeysyms() {
    return this.keysyms;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ChangeKeyboardMapping)) return false;
    final ChangeKeyboardMapping other = (ChangeKeyboardMapping) o;
    if (this.getKeycodeCount() != other.getKeycodeCount()) return false;
    if (this.getFirstKeycode() != other.getFirstKeycode()) return false;
    if (this.getKeysymsPerKeycode() != other.getKeysymsPerKeycode()) return false;
    final java.lang.Object this$keysyms = this.getKeysyms();
    final java.lang.Object other$keysyms = other.getKeysyms();
    if (this$keysyms == null ? other$keysyms != null : !this$keysyms.equals(other$keysyms)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getKeycodeCount();
    result = result * PRIME + this.getFirstKeycode();
    result = result * PRIME + this.getKeysymsPerKeycode();
    final java.lang.Object $keysyms = this.getKeysyms();
    result = result * PRIME + ($keysyms == null ? 43 : $keysyms.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ChangeKeyboardMapping(keycodeCount=" + this.getKeycodeCount() + ", firstKeycode=" + this.getFirstKeycode() + ", keysymsPerKeycode=" + this.getKeysymsPerKeycode() + ", keysyms=" + this.getKeysyms() + ")";
  }
}
