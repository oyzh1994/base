package com.github.moaxcp.x11.protocol.present;

import java.util.ArrayList;
import java.util.List;
import com.github.moaxcp.x11.protocol.OneWayRequest;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import java.io.IOException;

public final class Pixmap implements OneWayRequest {
  public static final String PLUGIN_NAME = "present";
  public static final byte OPCODE = 1;
  private final int window;
  private final int pixmap;
  private final int serial;
  private final int valid;
  private final int update;
  private final short xOff;
  private final short yOff;
  private final int targetCrtc;
  private final int waitFence;
  private final int idleFence;
  private final int options;
  private final long targetMsc;
  private final long divisor;
  private final long remainder;
  private final List<Notify> notifies;

  public byte getOpCode() {
    return OPCODE;
  }

  public static Pixmap readPixmap(X11Input in) throws IOException {
    Pixmap.PixmapBuilder javaBuilder = Pixmap.builder();
    int javaStart = 1;
    byte majorOpcode = in.readCard8();
    javaStart += 1;
    short length = in.readCard16();
    javaStart += 2;
    int window = in.readCard32();
    javaStart += 4;
    int pixmap = in.readCard32();
    javaStart += 4;
    int serial = in.readCard32();
    javaStart += 4;
    int valid = in.readCard32();
    javaStart += 4;
    int update = in.readCard32();
    javaStart += 4;
    short xOff = in.readInt16();
    javaStart += 2;
    short yOff = in.readInt16();
    javaStart += 2;
    int targetCrtc = in.readCard32();
    javaStart += 4;
    int waitFence = in.readCard32();
    javaStart += 4;
    int idleFence = in.readCard32();
    javaStart += 4;
    int options = in.readCard32();
    javaStart += 4;
    byte[] pad14 = in.readPad(4);
    javaStart += 4;
    long targetMsc = in.readCard64();
    javaStart += 8;
    long divisor = in.readCard64();
    javaStart += 8;
    long remainder = in.readCard64();
    javaStart += 8;
    List<Notify> notifies = new ArrayList<>(Short.toUnsignedInt(length) - javaStart);
    while (javaStart < Short.toUnsignedInt(length) * 4) {
      Notify baseObject = Notify.readNotify(in);
      notifies.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.window(window);
    javaBuilder.pixmap(pixmap);
    javaBuilder.serial(serial);
    javaBuilder.valid(valid);
    javaBuilder.update(update);
    javaBuilder.xOff(xOff);
    javaBuilder.yOff(yOff);
    javaBuilder.targetCrtc(targetCrtc);
    javaBuilder.waitFence(waitFence);
    javaBuilder.idleFence(idleFence);
    javaBuilder.options(options);
    javaBuilder.targetMsc(targetMsc);
    javaBuilder.divisor(divisor);
    javaBuilder.remainder(remainder);
    javaBuilder.notifies(List.copyOf(notifies));
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(byte majorOpcode, X11Output out) throws IOException {
    out.writeCard8(majorOpcode);
    out.writeCard8((byte) (Byte.toUnsignedInt(OPCODE)));
    out.writeCard16((short) getLength());
    out.writeCard32(window);
    out.writeCard32(pixmap);
    out.writeCard32(serial);
    out.writeCard32(valid);
    out.writeCard32(update);
    out.writeInt16(xOff);
    out.writeInt16(yOff);
    out.writeCard32(targetCrtc);
    out.writeCard32(waitFence);
    out.writeCard32(idleFence);
    out.writeCard32(options);
    out.writePad(4);
    out.writeCard64(targetMsc);
    out.writeCard64(divisor);
    out.writeCard64(remainder);
    for (Notify t : notifies) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 72 + XObject.sizeOf(notifies);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class PixmapBuilder {
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private int pixmap;
    @java.lang.SuppressWarnings("all")
    private int serial;
    @java.lang.SuppressWarnings("all")
    private int valid;
    @java.lang.SuppressWarnings("all")
    private int update;
    @java.lang.SuppressWarnings("all")
    private short xOff;
    @java.lang.SuppressWarnings("all")
    private short yOff;
    @java.lang.SuppressWarnings("all")
    private int targetCrtc;
    @java.lang.SuppressWarnings("all")
    private int waitFence;
    @java.lang.SuppressWarnings("all")
    private int idleFence;
    @java.lang.SuppressWarnings("all")
    private int options;
    @java.lang.SuppressWarnings("all")
    private long targetMsc;
    @java.lang.SuppressWarnings("all")
    private long divisor;
    @java.lang.SuppressWarnings("all")
    private long remainder;
    @java.lang.SuppressWarnings("all")
    private List<Notify> notifies;

    public int getSize() {
      return 72 + XObject.sizeOf(notifies);
    }

    @java.lang.SuppressWarnings("all")
    PixmapBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Pixmap.PixmapBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Pixmap.PixmapBuilder pixmap(final int pixmap) {
      this.pixmap = pixmap;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Pixmap.PixmapBuilder serial(final int serial) {
      this.serial = serial;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Pixmap.PixmapBuilder valid(final int valid) {
      this.valid = valid;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Pixmap.PixmapBuilder update(final int update) {
      this.update = update;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Pixmap.PixmapBuilder xOff(final short xOff) {
      this.xOff = xOff;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Pixmap.PixmapBuilder yOff(final short yOff) {
      this.yOff = yOff;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Pixmap.PixmapBuilder targetCrtc(final int targetCrtc) {
      this.targetCrtc = targetCrtc;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Pixmap.PixmapBuilder waitFence(final int waitFence) {
      this.waitFence = waitFence;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Pixmap.PixmapBuilder idleFence(final int idleFence) {
      this.idleFence = idleFence;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Pixmap.PixmapBuilder options(final int options) {
      this.options = options;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Pixmap.PixmapBuilder targetMsc(final long targetMsc) {
      this.targetMsc = targetMsc;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Pixmap.PixmapBuilder divisor(final long divisor) {
      this.divisor = divisor;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Pixmap.PixmapBuilder remainder(final long remainder) {
      this.remainder = remainder;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Pixmap.PixmapBuilder notifies(final List<Notify> notifies) {
      if (notifies == null) {
        throw new java.lang.NullPointerException("notifies is marked non-null but is null");
      }
      this.notifies = notifies;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Pixmap build() {
      return new Pixmap(this.window, this.pixmap, this.serial, this.valid, this.update, this.xOff, this.yOff, this.targetCrtc, this.waitFence, this.idleFence, this.options, this.targetMsc, this.divisor, this.remainder, this.notifies);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Pixmap.PixmapBuilder(window=" + this.window + ", pixmap=" + this.pixmap + ", serial=" + this.serial + ", valid=" + this.valid + ", update=" + this.update + ", xOff=" + this.xOff + ", yOff=" + this.yOff + ", targetCrtc=" + this.targetCrtc + ", waitFence=" + this.waitFence + ", idleFence=" + this.idleFence + ", options=" + this.options + ", targetMsc=" + this.targetMsc + ", divisor=" + this.divisor + ", remainder=" + this.remainder + ", notifies=" + this.notifies + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Pixmap(final int window, final int pixmap, final int serial, final int valid, final int update, final short xOff, final short yOff, final int targetCrtc, final int waitFence, final int idleFence, final int options, final long targetMsc, final long divisor, final long remainder, final List<Notify> notifies) {
    if (notifies == null) {
      throw new java.lang.NullPointerException("notifies is marked non-null but is null");
    }
    this.window = window;
    this.pixmap = pixmap;
    this.serial = serial;
    this.valid = valid;
    this.update = update;
    this.xOff = xOff;
    this.yOff = yOff;
    this.targetCrtc = targetCrtc;
    this.waitFence = waitFence;
    this.idleFence = idleFence;
    this.options = options;
    this.targetMsc = targetMsc;
    this.divisor = divisor;
    this.remainder = remainder;
    this.notifies = notifies;
  }

  @java.lang.SuppressWarnings("all")
  public static Pixmap.PixmapBuilder builder() {
    return new Pixmap.PixmapBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getWindow() {
    return this.window;
  }

  @java.lang.SuppressWarnings("all")
  public int getPixmap() {
    return this.pixmap;
  }

  @java.lang.SuppressWarnings("all")
  public int getSerial() {
    return this.serial;
  }

  @java.lang.SuppressWarnings("all")
  public int getValid() {
    return this.valid;
  }

  @java.lang.SuppressWarnings("all")
  public int getUpdate() {
    return this.update;
  }

  @java.lang.SuppressWarnings("all")
  public short getXOff() {
    return this.xOff;
  }

  @java.lang.SuppressWarnings("all")
  public short getYOff() {
    return this.yOff;
  }

  @java.lang.SuppressWarnings("all")
  public int getTargetCrtc() {
    return this.targetCrtc;
  }

  @java.lang.SuppressWarnings("all")
  public int getWaitFence() {
    return this.waitFence;
  }

  @java.lang.SuppressWarnings("all")
  public int getIdleFence() {
    return this.idleFence;
  }

  @java.lang.SuppressWarnings("all")
  public int getOptions() {
    return this.options;
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

  @java.lang.SuppressWarnings("all")
  public List<Notify> getNotifies() {
    return this.notifies;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Pixmap)) return false;
    final Pixmap other = (Pixmap) o;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getPixmap() != other.getPixmap()) return false;
    if (this.getSerial() != other.getSerial()) return false;
    if (this.getValid() != other.getValid()) return false;
    if (this.getUpdate() != other.getUpdate()) return false;
    if (this.getXOff() != other.getXOff()) return false;
    if (this.getYOff() != other.getYOff()) return false;
    if (this.getTargetCrtc() != other.getTargetCrtc()) return false;
    if (this.getWaitFence() != other.getWaitFence()) return false;
    if (this.getIdleFence() != other.getIdleFence()) return false;
    if (this.getOptions() != other.getOptions()) return false;
    if (this.getTargetMsc() != other.getTargetMsc()) return false;
    if (this.getDivisor() != other.getDivisor()) return false;
    if (this.getRemainder() != other.getRemainder()) return false;
    final java.lang.Object this$notifies = this.getNotifies();
    final java.lang.Object other$notifies = other.getNotifies();
    if (this$notifies == null ? other$notifies != null : !this$notifies.equals(other$notifies)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getPixmap();
    result = result * PRIME + this.getSerial();
    result = result * PRIME + this.getValid();
    result = result * PRIME + this.getUpdate();
    result = result * PRIME + this.getXOff();
    result = result * PRIME + this.getYOff();
    result = result * PRIME + this.getTargetCrtc();
    result = result * PRIME + this.getWaitFence();
    result = result * PRIME + this.getIdleFence();
    result = result * PRIME + this.getOptions();
    final long $targetMsc = this.getTargetMsc();
    result = result * PRIME + (int) ($targetMsc >>> 32 ^ $targetMsc);
    final long $divisor = this.getDivisor();
    result = result * PRIME + (int) ($divisor >>> 32 ^ $divisor);
    final long $remainder = this.getRemainder();
    result = result * PRIME + (int) ($remainder >>> 32 ^ $remainder);
    final java.lang.Object $notifies = this.getNotifies();
    result = result * PRIME + ($notifies == null ? 43 : $notifies.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Pixmap(window=" + this.getWindow() + ", pixmap=" + this.getPixmap() + ", serial=" + this.getSerial() + ", valid=" + this.getValid() + ", update=" + this.getUpdate() + ", xOff=" + this.getXOff() + ", yOff=" + this.getYOff() + ", targetCrtc=" + this.getTargetCrtc() + ", waitFence=" + this.getWaitFence() + ", idleFence=" + this.getIdleFence() + ", options=" + this.getOptions() + ", targetMsc=" + this.getTargetMsc() + ", divisor=" + this.getDivisor() + ", remainder=" + this.getRemainder() + ", notifies=" + this.getNotifies() + ")";
  }
}
