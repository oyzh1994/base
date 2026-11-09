package com.github.moaxcp.x11.protocol.present;

import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import java.io.IOException;

public final class NotifyMSC implements OneWayRequest {
  public static final String PLUGIN_NAME = "present";
  public static final byte OPCODE = 2;
  private final int window;
  private final int serial;
  private final long targetMsc;
  private final long divisor;
  private final long remainder;

  public byte getOpCode() {
    return OPCODE;
  }

  public static NotifyMSC readNotifyMSC(X11Input in) throws IOException {
    NotifyMSC.NotifyMSCBuilder javaBuilder = NotifyMSC.builder();
    byte majorOpcode = in.readCard8();
    short length = in.readCard16();
    int window = in.readCard32();
    int serial = in.readCard32();
    byte[] pad5 = in.readPad(4);
    long targetMsc = in.readCard64();
    long divisor = in.readCard64();
    long remainder = in.readCard64();
    javaBuilder.window(window);
    javaBuilder.serial(serial);
    javaBuilder.targetMsc(targetMsc);
    javaBuilder.divisor(divisor);
    javaBuilder.remainder(remainder);
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(serial);
    out.writePad(4);
    out.writeCard64(targetMsc);
    out.writeCard64(divisor);
    out.writeCard64(remainder);
  }

  @Override
  public int getSize() {
    return 40;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class NotifyMSCBuilder {
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private int serial;
    @java.lang.SuppressWarnings("all")
    private long targetMsc;
    @java.lang.SuppressWarnings("all")
    private long divisor;
    @java.lang.SuppressWarnings("all")
    private long remainder;

    public int getSize() {
      return 40;
    }

    @java.lang.SuppressWarnings("all")
    NotifyMSCBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NotifyMSC.NotifyMSCBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NotifyMSC.NotifyMSCBuilder serial(final int serial) {
      this.serial = serial;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NotifyMSC.NotifyMSCBuilder targetMsc(final long targetMsc) {
      this.targetMsc = targetMsc;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NotifyMSC.NotifyMSCBuilder divisor(final long divisor) {
      this.divisor = divisor;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public NotifyMSC.NotifyMSCBuilder remainder(final long remainder) {
      this.remainder = remainder;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public NotifyMSC build() {
      return new NotifyMSC(this.window, this.serial, this.targetMsc, this.divisor, this.remainder);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "NotifyMSC.NotifyMSCBuilder(window=" + this.window + ", serial=" + this.serial + ", targetMsc=" + this.targetMsc + ", divisor=" + this.divisor + ", remainder=" + this.remainder + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  NotifyMSC(final int window, final int serial, final long targetMsc, final long divisor, final long remainder) {
    this.window = window;
    this.serial = serial;
    this.targetMsc = targetMsc;
    this.divisor = divisor;
    this.remainder = remainder;
  }

  @java.lang.SuppressWarnings("all")
  public static NotifyMSC.NotifyMSCBuilder builder() {
    return new NotifyMSC.NotifyMSCBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.SuppressWarnings("all")
  public int getSerial() {
    return this.serial;
  }

  @java.lang.SuppressWarnings("all")
  public long getTargetMsc() {
    return this.targetMsc;
  }

  @java.lang.SuppressWarnings("all")
  public long getDivisor() {
    return this.divisor;
  }

  @java.lang.SuppressWarnings("all")
  public long getRemainder() {
    return this.remainder;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof NotifyMSC)) return false;
    final NotifyMSC other = (NotifyMSC) o;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getSerial() != other.getSerial()) return false;
    if (this.getTargetMsc() != other.getTargetMsc()) return false;
    if (this.getDivisor() != other.getDivisor()) return false;
    if (this.getRemainder() != other.getRemainder()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getSerial();
    final long $targetMsc = this.getTargetMsc();
    result = result * PRIME + (int) ($targetMsc >>> 32 ^ $targetMsc);
    final long $divisor = this.getDivisor();
    result = result * PRIME + (int) ($divisor >>> 32 ^ $divisor);
    final long $remainder = this.getRemainder();
    result = result * PRIME + (int) ($remainder >>> 32 ^ $remainder);
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "NotifyMSC(window=" + this.getWindow() + ", serial=" + this.getSerial() + ", targetMsc=" + this.getTargetMsc() + ", divisor=" + this.getDivisor() + ", remainder=" + this.getRemainder() + ")";
  }
}
