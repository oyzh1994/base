package com.github.moaxcp.x11.protocol.present;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XGenericEvent;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.xproto.Rectangle;
import java.io.IOException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

public final class RedirectNotifyEvent implements XGenericEvent {
  public static final String PLUGIN_NAME = "present";
  public static final byte NUMBER = 35;
  private final byte firstEventOffset;
  private final boolean sentEvent;
  private final byte extension;
  private final short sequenceNumber;
  private final short eventType;
  private final boolean updateWindow;
  private final int event;
  private final int eventWindow;
  private final int window;
  private final int pixmap;
  private final int serial;
  private final int validRegion;
  private final int updateRegion;
  private final Rectangle validRect;
  private final Rectangle updateRect;
  private final short xOff;
  private final short yOff;
  private final int targetCrtc;
  private final int waitFence;
  private final int idleFence;
  private final int options;
  private final long targetMsc;
  private final long divisor;
  private final long remainder;
  private final ImmutableList<Notify> notifies;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static RedirectNotifyEvent readRedirectNotifyEvent(byte firstEventOffset, boolean sentEvent, byte extension, short sequenceNumber, int length, short eventType, X11Input in) throws IOException {
    RedirectNotifyEvent.RedirectNotifyEventBuilder javaBuilder = RedirectNotifyEvent.builder();
    int javaStart = 10;
    boolean updateWindow = in.readBool();
    javaStart += 1;
    byte[] pad6 = in.readPad(1);
    javaStart += 1;
    int event = in.readCard32();
    javaStart += 4;
    int eventWindow = in.readCard32();
    javaStart += 4;
    int window = in.readCard32();
    javaStart += 4;
    int pixmap = in.readCard32();
    javaStart += 4;
    int serial = in.readCard32();
    javaStart += 4;
    int validRegion = in.readCard32();
    javaStart += 4;
    int updateRegion = in.readCard32();
    javaStart += 4;
    Rectangle validRect = Rectangle.readRectangle(in);
    javaStart += validRect.getSize();
    Rectangle updateRect = Rectangle.readRectangle(in);
    javaStart += updateRect.getSize();
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
    byte[] pad22 = in.readPad(4);
    javaStart += 4;
    long targetMsc = in.readCard64();
    javaStart += 8;
    long divisor = in.readCard64();
    javaStart += 8;
    long remainder = in.readCard64();
    javaStart += 8;
    MutableList<Notify> notifies = Lists.mutable.withInitialCapacity(length - javaStart);
    while (javaStart < length * 4) {
      Notify baseObject = Notify.readNotify(in);
      notifies.add(baseObject);
      javaStart += baseObject.getSize();
    }
    javaBuilder.extension(extension);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.eventType(eventType);
    javaBuilder.updateWindow(updateWindow);
    javaBuilder.event(event);
    javaBuilder.eventWindow(eventWindow);
    javaBuilder.window(window);
    javaBuilder.pixmap(pixmap);
    javaBuilder.serial(serial);
    javaBuilder.validRegion(validRegion);
    javaBuilder.updateRegion(updateRegion);
    javaBuilder.validRect(validRect);
    javaBuilder.updateRect(updateRect);
    javaBuilder.xOff(xOff);
    javaBuilder.yOff(yOff);
    javaBuilder.targetCrtc(targetCrtc);
    javaBuilder.waitFence(waitFence);
    javaBuilder.idleFence(idleFence);
    javaBuilder.options(options);
    javaBuilder.targetMsc(targetMsc);
    javaBuilder.divisor(divisor);
    javaBuilder.remainder(remainder);
    javaBuilder.notifies(notifies.toImmutable());
    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    if (javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (128 | getResponseCode()) : getResponseCode());
    out.writeCard8(extension);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength() - 32);
    out.writeCard16(eventType);
    out.writeBool(updateWindow);
    out.writePad(1);
    out.writeCard32(event);
    out.writeCard32(eventWindow);
    out.writeCard32(window);
    out.writeCard32(pixmap);
    out.writeCard32(serial);
    out.writeCard32(validRegion);
    out.writeCard32(updateRegion);
    validRect.write(out);
    updateRect.write(out);
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
    return 104 + XObject.sizeOf(notifies);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class RedirectNotifyEventBuilder {
    @java.lang.SuppressWarnings("all")
    private byte firstEventOffset;
    @java.lang.SuppressWarnings("all")
    private boolean sentEvent;
    @java.lang.SuppressWarnings("all")
    private byte extension;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private short eventType;
    @java.lang.SuppressWarnings("all")
    private boolean updateWindow;
    @java.lang.SuppressWarnings("all")
    private int event;
    @java.lang.SuppressWarnings("all")
    private int eventWindow;
    @java.lang.SuppressWarnings("all")
    private int window;
    @java.lang.SuppressWarnings("all")
    private int pixmap;
    @java.lang.SuppressWarnings("all")
    private int serial;
    @java.lang.SuppressWarnings("all")
    private int validRegion;
    @java.lang.SuppressWarnings("all")
    private int updateRegion;
    @java.lang.SuppressWarnings("all")
    private Rectangle validRect;
    @java.lang.SuppressWarnings("all")
    private Rectangle updateRect;
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
    private ImmutableList<Notify> notifies;

    public int getSize() {
      return 104 + XObject.sizeOf(notifies);
    }

    @java.lang.SuppressWarnings("all")
    RedirectNotifyEventBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RedirectNotifyEvent.RedirectNotifyEventBuilder firstEventOffset(final byte firstEventOffset) {
      this.firstEventOffset = firstEventOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RedirectNotifyEvent.RedirectNotifyEventBuilder sentEvent(final boolean sentEvent) {
      this.sentEvent = sentEvent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RedirectNotifyEvent.RedirectNotifyEventBuilder extension(final byte extension) {
      this.extension = extension;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RedirectNotifyEvent.RedirectNotifyEventBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RedirectNotifyEvent.RedirectNotifyEventBuilder eventType(final short eventType) {
      this.eventType = eventType;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RedirectNotifyEvent.RedirectNotifyEventBuilder updateWindow(final boolean updateWindow) {
      this.updateWindow = updateWindow;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RedirectNotifyEvent.RedirectNotifyEventBuilder event(final int event) {
      this.event = event;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RedirectNotifyEvent.RedirectNotifyEventBuilder eventWindow(final int eventWindow) {
      this.eventWindow = eventWindow;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RedirectNotifyEvent.RedirectNotifyEventBuilder window(final int window) {
      this.window = window;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RedirectNotifyEvent.RedirectNotifyEventBuilder pixmap(final int pixmap) {
      this.pixmap = pixmap;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RedirectNotifyEvent.RedirectNotifyEventBuilder serial(final int serial) {
      this.serial = serial;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RedirectNotifyEvent.RedirectNotifyEventBuilder validRegion(final int validRegion) {
      this.validRegion = validRegion;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RedirectNotifyEvent.RedirectNotifyEventBuilder updateRegion(final int updateRegion) {
      this.updateRegion = updateRegion;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RedirectNotifyEvent.RedirectNotifyEventBuilder validRect(final Rectangle validRect) {
      if (validRect == null) {
        throw new java.lang.NullPointerException("validRect is marked non-null but is null");
      }
      this.validRect = validRect;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RedirectNotifyEvent.RedirectNotifyEventBuilder updateRect(final Rectangle updateRect) {
      if (updateRect == null) {
        throw new java.lang.NullPointerException("updateRect is marked non-null but is null");
      }
      this.updateRect = updateRect;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RedirectNotifyEvent.RedirectNotifyEventBuilder xOff(final short xOff) {
      this.xOff = xOff;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RedirectNotifyEvent.RedirectNotifyEventBuilder yOff(final short yOff) {
      this.yOff = yOff;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RedirectNotifyEvent.RedirectNotifyEventBuilder targetCrtc(final int targetCrtc) {
      this.targetCrtc = targetCrtc;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RedirectNotifyEvent.RedirectNotifyEventBuilder waitFence(final int waitFence) {
      this.waitFence = waitFence;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RedirectNotifyEvent.RedirectNotifyEventBuilder idleFence(final int idleFence) {
      this.idleFence = idleFence;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RedirectNotifyEvent.RedirectNotifyEventBuilder options(final int options) {
      this.options = options;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RedirectNotifyEvent.RedirectNotifyEventBuilder targetMsc(final long targetMsc) {
      this.targetMsc = targetMsc;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RedirectNotifyEvent.RedirectNotifyEventBuilder divisor(final long divisor) {
      this.divisor = divisor;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RedirectNotifyEvent.RedirectNotifyEventBuilder remainder(final long remainder) {
      this.remainder = remainder;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RedirectNotifyEvent.RedirectNotifyEventBuilder notifies(final ImmutableList<Notify> notifies) {
      if (notifies == null) {
        throw new java.lang.NullPointerException("notifies is marked non-null but is null");
      }
      this.notifies = notifies;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public RedirectNotifyEvent build() {
      return new RedirectNotifyEvent(this.firstEventOffset, this.sentEvent, this.extension, this.sequenceNumber, this.eventType, this.updateWindow, this.event, this.eventWindow, this.window, this.pixmap, this.serial, this.validRegion, this.updateRegion, this.validRect, this.updateRect, this.xOff, this.yOff, this.targetCrtc, this.waitFence, this.idleFence, this.options, this.targetMsc, this.divisor, this.remainder, this.notifies);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "RedirectNotifyEvent.RedirectNotifyEventBuilder(firstEventOffset=" + this.firstEventOffset + ", sentEvent=" + this.sentEvent + ", extension=" + this.extension + ", sequenceNumber=" + this.sequenceNumber + ", eventType=" + this.eventType + ", updateWindow=" + this.updateWindow + ", event=" + this.event + ", eventWindow=" + this.eventWindow + ", window=" + this.window + ", pixmap=" + this.pixmap + ", serial=" + this.serial + ", validRegion=" + this.validRegion + ", updateRegion=" + this.updateRegion + ", validRect=" + this.validRect + ", updateRect=" + this.updateRect + ", xOff=" + this.xOff + ", yOff=" + this.yOff + ", targetCrtc=" + this.targetCrtc + ", waitFence=" + this.waitFence + ", idleFence=" + this.idleFence + ", options=" + this.options + ", targetMsc=" + this.targetMsc + ", divisor=" + this.divisor + ", remainder=" + this.remainder + ", notifies=" + this.notifies + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  RedirectNotifyEvent(final byte firstEventOffset, final boolean sentEvent, final byte extension, final short sequenceNumber, final short eventType, final boolean updateWindow, final int event, final int eventWindow, final int window, final int pixmap, final int serial, final int validRegion, final int updateRegion, final Rectangle validRect, final Rectangle updateRect, final short xOff, final short yOff, final int targetCrtc, final int waitFence, final int idleFence, final int options, final long targetMsc, final long divisor, final long remainder, final ImmutableList<Notify> notifies) {
    if (validRect == null) {
      throw new java.lang.NullPointerException("validRect is marked non-null but is null");
    }
    if (updateRect == null) {
      throw new java.lang.NullPointerException("updateRect is marked non-null but is null");
    }
    if (notifies == null) {
      throw new java.lang.NullPointerException("notifies is marked non-null but is null");
    }
    this.firstEventOffset = firstEventOffset;
    this.sentEvent = sentEvent;
    this.extension = extension;
    this.sequenceNumber = sequenceNumber;
    this.eventType = eventType;
    this.updateWindow = updateWindow;
    this.event = event;
    this.eventWindow = eventWindow;
    this.window = window;
    this.pixmap = pixmap;
    this.serial = serial;
    this.validRegion = validRegion;
    this.updateRegion = updateRegion;
    this.validRect = validRect;
    this.updateRect = updateRect;
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
  public static RedirectNotifyEvent.RedirectNotifyEventBuilder builder() {
    return new RedirectNotifyEvent.RedirectNotifyEventBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getFirstEventOffset() {
    return this.firstEventOffset;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isSentEvent() {
    return this.sentEvent;
  }

  @java.lang.SuppressWarnings("all")
  public byte getExtension() {
    return this.extension;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public short getEventType() {
    return this.eventType;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isUpdateWindow() {
    return this.updateWindow;
  }

  @java.lang.SuppressWarnings("all")
  public int getEvent() {
    return this.event;
  }

  @java.lang.SuppressWarnings("all")
  public int getEventWindow() {
    return this.eventWindow;
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
  public int getValidRegion() {
    return this.validRegion;
  }

  @java.lang.SuppressWarnings("all")
  public int getUpdateRegion() {
    return this.updateRegion;
  }

  @java.lang.SuppressWarnings("all")
  public Rectangle getValidRect() {
    return this.validRect;
  }

  @java.lang.SuppressWarnings("all")
  public Rectangle getUpdateRect() {
    return this.updateRect;
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
  public ImmutableList<Notify> getNotifies() {
    return this.notifies;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof RedirectNotifyEvent)) return false;
    final RedirectNotifyEvent other = (RedirectNotifyEvent) o;
    if (this.getFirstEventOffset() != other.getFirstEventOffset()) return false;
    if (this.isSentEvent() != other.isSentEvent()) return false;
    if (this.getExtension() != other.getExtension()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getEventType() != other.getEventType()) return false;
    if (this.isUpdateWindow() != other.isUpdateWindow()) return false;
    if (this.getEvent() != other.getEvent()) return false;
    if (this.getEventWindow() != other.getEventWindow()) return false;
    if (this.getWindow() != other.getWindow()) return false;
    if (this.getPixmap() != other.getPixmap()) return false;
    if (this.getSerial() != other.getSerial()) return false;
    if (this.getValidRegion() != other.getValidRegion()) return false;
    if (this.getUpdateRegion() != other.getUpdateRegion()) return false;
    if (this.getXOff() != other.getXOff()) return false;
    if (this.getYOff() != other.getYOff()) return false;
    if (this.getTargetCrtc() != other.getTargetCrtc()) return false;
    if (this.getWaitFence() != other.getWaitFence()) return false;
    if (this.getIdleFence() != other.getIdleFence()) return false;
    if (this.getOptions() != other.getOptions()) return false;
    if (this.getTargetMsc() != other.getTargetMsc()) return false;
    if (this.getDivisor() != other.getDivisor()) return false;
    if (this.getRemainder() != other.getRemainder()) return false;
    final java.lang.Object this$validRect = this.getValidRect();
    final java.lang.Object other$validRect = other.getValidRect();
    if (this$validRect == null ? other$validRect != null : !this$validRect.equals(other$validRect)) return false;
    final java.lang.Object this$updateRect = this.getUpdateRect();
    final java.lang.Object other$updateRect = other.getUpdateRect();
    if (this$updateRect == null ? other$updateRect != null : !this$updateRect.equals(other$updateRect)) return false;
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
    result = result * PRIME + this.getFirstEventOffset();
    result = result * PRIME + (this.isSentEvent() ? 79 : 97);
    result = result * PRIME + this.getExtension();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getEventType();
    result = result * PRIME + (this.isUpdateWindow() ? 79 : 97);
    result = result * PRIME + this.getEvent();
    result = result * PRIME + this.getEventWindow();
    result = result * PRIME + this.getWindow();
    result = result * PRIME + this.getPixmap();
    result = result * PRIME + this.getSerial();
    result = result * PRIME + this.getValidRegion();
    result = result * PRIME + this.getUpdateRegion();
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
    final java.lang.Object $validRect = this.getValidRect();
    result = result * PRIME + ($validRect == null ? 43 : $validRect.hashCode());
    final java.lang.Object $updateRect = this.getUpdateRect();
    result = result * PRIME + ($updateRect == null ? 43 : $updateRect.hashCode());
    final java.lang.Object $notifies = this.getNotifies();
    result = result * PRIME + ($notifies == null ? 43 : $notifies.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "RedirectNotifyEvent(firstEventOffset=" + this.getFirstEventOffset() + ", sentEvent=" + this.isSentEvent() + ", extension=" + this.getExtension() + ", sequenceNumber=" + this.getSequenceNumber() + ", eventType=" + this.getEventType() + ", updateWindow=" + this.isUpdateWindow() + ", event=" + this.getEvent() + ", eventWindow=" + this.getEventWindow() + ", window=" + this.getWindow() + ", pixmap=" + this.getPixmap() + ", serial=" + this.getSerial() + ", validRegion=" + this.getValidRegion() + ", updateRegion=" + this.getUpdateRegion() + ", validRect=" + this.getValidRect() + ", updateRect=" + this.getUpdateRect() + ", xOff=" + this.getXOff() + ", yOff=" + this.getYOff() + ", targetCrtc=" + this.getTargetCrtc() + ", waitFence=" + this.getWaitFence() + ", idleFence=" + this.getIdleFence() + ", options=" + this.getOptions() + ", targetMsc=" + this.getTargetMsc() + ", divisor=" + this.getDivisor() + ", remainder=" + this.getRemainder() + ", notifies=" + this.getNotifies() + ")";
  }
}
