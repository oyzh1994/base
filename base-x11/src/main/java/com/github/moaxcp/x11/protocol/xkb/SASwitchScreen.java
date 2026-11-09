package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class SASwitchScreen implements ActionUnion, XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final byte type;
  private final byte flags;
  private final byte newScreen;

  public static SASwitchScreen readSASwitchScreen(X11Input in) throws IOException {
    SASwitchScreen.SASwitchScreenBuilder javaBuilder = SASwitchScreen.builder();
    byte type = in.readCard8();
    byte flags = in.readCard8();
    byte newScreen = in.readInt8();
    byte[] pad3 = in.readPad(5);
    javaBuilder.type(type);
    javaBuilder.flags(flags);
    javaBuilder.newScreen(newScreen);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(type);
    out.writeCard8(flags);
    out.writeInt8(newScreen);
    out.writePad(5);
  }

  @Override
  public int getSize() {
    return 8;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SASwitchScreenBuilder {
    @java.lang.SuppressWarnings("all")
    private byte type;
    @java.lang.SuppressWarnings("all")
    private byte flags;
    @java.lang.SuppressWarnings("all")
    private byte newScreen;

    public SASwitchScreen.SASwitchScreenBuilder type(SAType type) {
      this.type = (byte) type.getValue();
      return this;
    }

    public SASwitchScreen.SASwitchScreenBuilder type(byte type) {
      this.type = type;
      return this;
    }

    public int getSize() {
      return 8;
    }

    @java.lang.SuppressWarnings("all")
    SASwitchScreenBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SASwitchScreen.SASwitchScreenBuilder flags(final byte flags) {
      this.flags = flags;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public SASwitchScreen.SASwitchScreenBuilder newScreen(final byte newScreen) {
      this.newScreen = newScreen;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public SASwitchScreen build() {
      return new SASwitchScreen(this.type, this.flags, this.newScreen);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "SASwitchScreen.SASwitchScreenBuilder(type=" + this.type + ", flags=" + this.flags + ", newScreen=" + this.newScreen + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  SASwitchScreen(final byte type, final byte flags, final byte newScreen) {
    this.type = type;
    this.flags = flags;
    this.newScreen = newScreen;
  }

  @java.lang.SuppressWarnings("all")
  public static SASwitchScreen.SASwitchScreenBuilder builder() {
    return new SASwitchScreen.SASwitchScreenBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getType() {
    return this.type;
  }

  @java.lang.SuppressWarnings("all")
  public byte getFlags() {
    return this.flags;
  }

  @java.lang.SuppressWarnings("all")
  public byte getNewScreen() {
    return this.newScreen;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof SASwitchScreen)) return false;
    final SASwitchScreen other = (SASwitchScreen) o;
    if (this.getType() != other.getType()) return false;
    if (this.getFlags() != other.getFlags()) return false;
    if (this.getNewScreen() != other.getNewScreen()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getType();
    result = result * PRIME + this.getFlags();
    result = result * PRIME + this.getNewScreen();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "SASwitchScreen(type=" + this.getType() + ", flags=" + this.getFlags() + ", newScreen=" + this.getNewScreen() + ")";
  }
}
