package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XEvent;
import java.io.IOException;

public final class CompatMapNotifyEvent implements XEvent {
  public static final String PLUGIN_NAME = "xkb";
  public static final byte NUMBER = 7;
  private final byte firstEventOffset;
  private final boolean sentEvent;
  private final byte xkbType;
  private final short sequenceNumber;
  private final int time;
  private final byte deviceID;
  private final byte changedGroups;
  private final short firstSI;
  private final short nSI;
  private final short nTotalSI;

  @Override
  public byte getResponseCode() {
    return (byte) (firstEventOffset + NUMBER);
  }

  @Override
  public byte getNumber() {
    return NUMBER;
  }

  public static CompatMapNotifyEvent readCompatMapNotifyEvent(byte firstEventOffset, boolean sentEvent, X11Input in) throws IOException {
    CompatMapNotifyEvent.CompatMapNotifyEventBuilder javaBuilder = CompatMapNotifyEvent.builder();
    byte xkbType = in.readCard8();
    short sequenceNumber = in.readCard16();
    int time = in.readCard32();
    byte deviceID = in.readCard8();
    byte changedGroups = in.readCard8();
    short firstSI = in.readCard16();
    short nSI = in.readCard16();
    short nTotalSI = in.readCard16();
    byte[] pad9 = in.readPad(16);
    javaBuilder.xkbType(xkbType);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.time(time);
    javaBuilder.deviceID(deviceID);
    javaBuilder.changedGroups(changedGroups);
    javaBuilder.firstSI(firstSI);
    javaBuilder.nSI(nSI);
    javaBuilder.nTotalSI(nTotalSI);
    javaBuilder.sentEvent(sentEvent);
    javaBuilder.firstEventOffset(firstEventOffset);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(sentEvent ? (byte) (128 | getResponseCode()) : getResponseCode());
    out.writeCard8(xkbType);
    out.writeCard16(sequenceNumber);
    out.writeCard32(time);
    out.writeCard8(deviceID);
    out.writeCard8(changedGroups);
    out.writeCard16(firstSI);
    out.writeCard16(nSI);
    out.writeCard16(nTotalSI);
    out.writePad(16);
  }

  public boolean isChangedGroupsEnabled(SetOfGroup... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (SetOfGroup m : maskEnums) {
      if (!m.isEnabled(changedGroups)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 32;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class CompatMapNotifyEventBuilder {
    @java.lang.SuppressWarnings("all")
    private byte firstEventOffset;
    @java.lang.SuppressWarnings("all")
    private boolean sentEvent;
    @java.lang.SuppressWarnings("all")
    private byte xkbType;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private int time;
    @java.lang.SuppressWarnings("all")
    private byte deviceID;
    @java.lang.SuppressWarnings("all")
    private byte changedGroups;
    @java.lang.SuppressWarnings("all")
    private short firstSI;
    @java.lang.SuppressWarnings("all")
    private short nSI;
    @java.lang.SuppressWarnings("all")
    private short nTotalSI;

    public boolean isChangedGroupsEnabled(SetOfGroup... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (SetOfGroup m : maskEnums) {
        if (!m.isEnabled(changedGroups)) {
          return false;
        }
      }
      return true;
    }

    public CompatMapNotifyEvent.CompatMapNotifyEventBuilder changedGroupsEnable(SetOfGroup... maskEnums) {
      for (SetOfGroup m : maskEnums) {
        changedGroups((byte) m.enableFor(changedGroups));
      }
      return this;
    }

    public CompatMapNotifyEvent.CompatMapNotifyEventBuilder changedGroupsDisable(SetOfGroup... maskEnums) {
      for (SetOfGroup m : maskEnums) {
        changedGroups((byte) m.disableFor(changedGroups));
      }
      return this;
    }

    public int getSize() {
      return 32;
    }

    @java.lang.SuppressWarnings("all")
    CompatMapNotifyEventBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CompatMapNotifyEvent.CompatMapNotifyEventBuilder firstEventOffset(final byte firstEventOffset) {
      this.firstEventOffset = firstEventOffset;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CompatMapNotifyEvent.CompatMapNotifyEventBuilder sentEvent(final boolean sentEvent) {
      this.sentEvent = sentEvent;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CompatMapNotifyEvent.CompatMapNotifyEventBuilder xkbType(final byte xkbType) {
      this.xkbType = xkbType;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CompatMapNotifyEvent.CompatMapNotifyEventBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CompatMapNotifyEvent.CompatMapNotifyEventBuilder time(final int time) {
      this.time = time;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CompatMapNotifyEvent.CompatMapNotifyEventBuilder deviceID(final byte deviceID) {
      this.deviceID = deviceID;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CompatMapNotifyEvent.CompatMapNotifyEventBuilder changedGroups(final byte changedGroups) {
      this.changedGroups = changedGroups;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CompatMapNotifyEvent.CompatMapNotifyEventBuilder firstSI(final short firstSI) {
      this.firstSI = firstSI;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CompatMapNotifyEvent.CompatMapNotifyEventBuilder nSI(final short nSI) {
      this.nSI = nSI;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public CompatMapNotifyEvent.CompatMapNotifyEventBuilder nTotalSI(final short nTotalSI) {
      this.nTotalSI = nTotalSI;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public CompatMapNotifyEvent build() {
      return new CompatMapNotifyEvent(this.firstEventOffset, this.sentEvent, this.xkbType, this.sequenceNumber, this.time, this.deviceID, this.changedGroups, this.firstSI, this.nSI, this.nTotalSI);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "CompatMapNotifyEvent.CompatMapNotifyEventBuilder(firstEventOffset=" + this.firstEventOffset + ", sentEvent=" + this.sentEvent + ", xkbType=" + this.xkbType + ", sequenceNumber=" + this.sequenceNumber + ", time=" + this.time + ", deviceID=" + this.deviceID + ", changedGroups=" + this.changedGroups + ", firstSI=" + this.firstSI + ", nSI=" + this.nSI + ", nTotalSI=" + this.nTotalSI + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  CompatMapNotifyEvent(final byte firstEventOffset, final boolean sentEvent, final byte xkbType, final short sequenceNumber, final int time, final byte deviceID, final byte changedGroups, final short firstSI, final short nSI, final short nTotalSI) {
    this.firstEventOffset = firstEventOffset;
    this.sentEvent = sentEvent;
    this.xkbType = xkbType;
    this.sequenceNumber = sequenceNumber;
    this.time = time;
    this.deviceID = deviceID;
    this.changedGroups = changedGroups;
    this.firstSI = firstSI;
    this.nSI = nSI;
    this.nTotalSI = nTotalSI;
  }

  @java.lang.SuppressWarnings("all")
  public static CompatMapNotifyEvent.CompatMapNotifyEventBuilder builder() {
    return new CompatMapNotifyEvent.CompatMapNotifyEventBuilder();
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
  public byte getXkbType() {
    return this.xkbType;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getTime() {
    return this.time;
  }

  @java.lang.SuppressWarnings("all")
  public byte getDeviceID() {
    return this.deviceID;
  }

  @java.lang.SuppressWarnings("all")
  public byte getChangedGroups() {
    return this.changedGroups;
  }

  @java.lang.SuppressWarnings("all")
  public short getFirstSI() {
    return this.firstSI;
  }

  @java.lang.SuppressWarnings("all")
  public short getNSI() {
    return this.nSI;
  }

  @java.lang.SuppressWarnings("all")
  public short getNTotalSI() {
    return this.nTotalSI;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof CompatMapNotifyEvent)) return false;
    final CompatMapNotifyEvent other = (CompatMapNotifyEvent) o;
    if (this.getFirstEventOffset() != other.getFirstEventOffset()) return false;
    if (this.isSentEvent() != other.isSentEvent()) return false;
    if (this.getXkbType() != other.getXkbType()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getTime() != other.getTime()) return false;
    if (this.getDeviceID() != other.getDeviceID()) return false;
    if (this.getChangedGroups() != other.getChangedGroups()) return false;
    if (this.getFirstSI() != other.getFirstSI()) return false;
    if (this.getNSI() != other.getNSI()) return false;
    if (this.getNTotalSI() != other.getNTotalSI()) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFirstEventOffset();
    result = result * PRIME + (this.isSentEvent() ? 79 : 97);
    result = result * PRIME + this.getXkbType();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getTime();
    result = result * PRIME + this.getDeviceID();
    result = result * PRIME + this.getChangedGroups();
    result = result * PRIME + this.getFirstSI();
    result = result * PRIME + this.getNSI();
    result = result * PRIME + this.getNTotalSI();
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "CompatMapNotifyEvent(firstEventOffset=" + this.getFirstEventOffset() + ", sentEvent=" + this.isSentEvent() + ", xkbType=" + this.getXkbType() + ", sequenceNumber=" + this.getSequenceNumber() + ", time=" + this.getTime() + ", deviceID=" + this.getDeviceID() + ", changedGroups=" + this.getChangedGroups() + ", firstSI=" + this.getFirstSI() + ", nSI=" + this.getNSI() + ", nTotalSI=" + this.getNTotalSI() + ")";
  }
}
