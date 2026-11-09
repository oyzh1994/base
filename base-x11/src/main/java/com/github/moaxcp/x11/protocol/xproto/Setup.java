package com.github.moaxcp.x11.protocol.xproto;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import org.eclipse.collections.api.factory.Lists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.list.primitive.ByteList;

public final class Setup implements XStruct {
  public static final String PLUGIN_NAME = "xproto";
  private final byte status;
  private final short protocolMajorVersion;
  private final short protocolMinorVersion;
  private final short length;
  private final int releaseNumber;
  private final int resourceIdBase;
  private final int resourceIdMask;
  private final int motionBufferSize;
  private final short maximumRequestLength;
  private final byte imageByteOrder;
  private final byte bitmapFormatBitOrder;
  private final byte bitmapFormatScanlineUnit;
  private final byte bitmapFormatScanlinePad;
  private final byte minKeycode;
  private final byte maxKeycode;
  private final ByteList vendor;
  private final ImmutableList<Format> pixmapFormats;
  private final ImmutableList<Screen> roots;

  public static Setup readSetup(X11Input in) throws IOException {
    Setup.SetupBuilder javaBuilder = Setup.builder();
    byte status = in.readCard8();
    byte[] pad1 = in.readPad(1);
    short protocolMajorVersion = in.readCard16();
    short protocolMinorVersion = in.readCard16();
    short length = in.readCard16();
    int releaseNumber = in.readCard32();
    int resourceIdBase = in.readCard32();
    int resourceIdMask = in.readCard32();
    int motionBufferSize = in.readCard32();
    short vendorLen = in.readCard16();
    short maximumRequestLength = in.readCard16();
    byte rootsLen = in.readCard8();
    byte pixmapFormatsLen = in.readCard8();
    byte imageByteOrder = in.readCard8();
    byte bitmapFormatBitOrder = in.readCard8();
    byte bitmapFormatScanlineUnit = in.readCard8();
    byte bitmapFormatScanlinePad = in.readCard8();
    byte minKeycode = in.readCard8();
    byte maxKeycode = in.readCard8();
    byte[] pad19 = in.readPad(4);
    ByteList vendor = in.readChar(Short.toUnsignedInt(vendorLen));
    in.readPadAlign(Short.toUnsignedInt(vendorLen));
    MutableList<Format> pixmapFormats = Lists.mutable.withInitialCapacity(Byte.toUnsignedInt(pixmapFormatsLen));
    for (int i = 0; i < Byte.toUnsignedInt(pixmapFormatsLen); i++) {
      pixmapFormats.add(Format.readFormat(in));
    }
    MutableList<Screen> roots = Lists.mutable.withInitialCapacity(Byte.toUnsignedInt(rootsLen));
    for (int i = 0; i < Byte.toUnsignedInt(rootsLen); i++) {
      roots.add(Screen.readScreen(in));
    }
    javaBuilder.status(status);
    javaBuilder.protocolMajorVersion(protocolMajorVersion);
    javaBuilder.protocolMinorVersion(protocolMinorVersion);
    javaBuilder.length(length);
    javaBuilder.releaseNumber(releaseNumber);
    javaBuilder.resourceIdBase(resourceIdBase);
    javaBuilder.resourceIdMask(resourceIdMask);
    javaBuilder.motionBufferSize(motionBufferSize);
    javaBuilder.maximumRequestLength(maximumRequestLength);
    javaBuilder.imageByteOrder(imageByteOrder);
    javaBuilder.bitmapFormatBitOrder(bitmapFormatBitOrder);
    javaBuilder.bitmapFormatScanlineUnit(bitmapFormatScanlineUnit);
    javaBuilder.bitmapFormatScanlinePad(bitmapFormatScanlinePad);
    javaBuilder.minKeycode(minKeycode);
    javaBuilder.maxKeycode(maxKeycode);
    javaBuilder.vendor(vendor.toImmutable());
    javaBuilder.pixmapFormats(pixmapFormats.toImmutable());
    javaBuilder.roots(roots.toImmutable());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(status);
    out.writePad(1);
    out.writeCard16(protocolMajorVersion);
    out.writeCard16(protocolMinorVersion);
    out.writeCard16(length);
    out.writeCard32(releaseNumber);
    out.writeCard32(resourceIdBase);
    out.writeCard32(resourceIdMask);
    out.writeCard32(motionBufferSize);
    short vendorLen = (short) vendor.size();
    out.writeCard16(vendorLen);
    out.writeCard16(maximumRequestLength);
    byte rootsLen = (byte) roots.size();
    out.writeCard8(rootsLen);
    byte pixmapFormatsLen = (byte) pixmapFormats.size();
    out.writeCard8(pixmapFormatsLen);
    out.writeCard8(imageByteOrder);
    out.writeCard8(bitmapFormatBitOrder);
    out.writeCard8(bitmapFormatScanlineUnit);
    out.writeCard8(bitmapFormatScanlinePad);
    out.writeCard8(minKeycode);
    out.writeCard8(maxKeycode);
    out.writePad(4);
    out.writeChar(vendor);
    out.writePadAlign(Short.toUnsignedInt(vendorLen));
    for (Format t : pixmapFormats) {
      t.write(out);
    }
    for (Screen t : roots) {
      t.write(out);
    }
  }

  @Override
  public int getSize() {
    return 40 + 1 * vendor.size() + XObject.getSizeForPadAlign(4, 1 * vendor.size()) + XObject.sizeOf(pixmapFormats) + XObject.sizeOf(roots);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class SetupBuilder {
    @java.lang.SuppressWarnings("all")
    private byte status;
    @java.lang.SuppressWarnings("all")
    private short protocolMajorVersion;
    @java.lang.SuppressWarnings("all")
    private short protocolMinorVersion;
    @java.lang.SuppressWarnings("all")
    private short length;
    @java.lang.SuppressWarnings("all")
    private int releaseNumber;
    @java.lang.SuppressWarnings("all")
    private int resourceIdBase;
    @java.lang.SuppressWarnings("all")
    private int resourceIdMask;
    @java.lang.SuppressWarnings("all")
    private int motionBufferSize;
    @java.lang.SuppressWarnings("all")
    private short maximumRequestLength;
    @java.lang.SuppressWarnings("all")
    private byte imageByteOrder;
    @java.lang.SuppressWarnings("all")
    private byte bitmapFormatBitOrder;
    @java.lang.SuppressWarnings("all")
    private byte bitmapFormatScanlineUnit;
    @java.lang.SuppressWarnings("all")
    private byte bitmapFormatScanlinePad;
    @java.lang.SuppressWarnings("all")
    private byte minKeycode;
    @java.lang.SuppressWarnings("all")
    private byte maxKeycode;
    @java.lang.SuppressWarnings("all")
    private ByteList vendor;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<Format> pixmapFormats;
    @java.lang.SuppressWarnings("all")
    private ImmutableList<Screen> roots;

    public Setup.SetupBuilder imageByteOrder(ImageOrder imageByteOrder) {
      this.imageByteOrder = (byte) imageByteOrder.getValue();
      return this;
    }

    public Setup.SetupBuilder imageByteOrder(byte imageByteOrder) {
      this.imageByteOrder = imageByteOrder;
      return this;
    }

    public Setup.SetupBuilder bitmapFormatBitOrder(ImageOrder bitmapFormatBitOrder) {
      this.bitmapFormatBitOrder = (byte) bitmapFormatBitOrder.getValue();
      return this;
    }

    public Setup.SetupBuilder bitmapFormatBitOrder(byte bitmapFormatBitOrder) {
      this.bitmapFormatBitOrder = bitmapFormatBitOrder;
      return this;
    }

    public int getSize() {
      return 40 + 1 * vendor.size() + XObject.getSizeForPadAlign(4, 1 * vendor.size()) + XObject.sizeOf(pixmapFormats) + XObject.sizeOf(roots);
    }

    @java.lang.SuppressWarnings("all")
    SetupBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Setup.SetupBuilder status(final byte status) {
      this.status = status;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Setup.SetupBuilder protocolMajorVersion(final short protocolMajorVersion) {
      this.protocolMajorVersion = protocolMajorVersion;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Setup.SetupBuilder protocolMinorVersion(final short protocolMinorVersion) {
      this.protocolMinorVersion = protocolMinorVersion;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Setup.SetupBuilder length(final short length) {
      this.length = length;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Setup.SetupBuilder releaseNumber(final int releaseNumber) {
      this.releaseNumber = releaseNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Setup.SetupBuilder resourceIdBase(final int resourceIdBase) {
      this.resourceIdBase = resourceIdBase;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Setup.SetupBuilder resourceIdMask(final int resourceIdMask) {
      this.resourceIdMask = resourceIdMask;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Setup.SetupBuilder motionBufferSize(final int motionBufferSize) {
      this.motionBufferSize = motionBufferSize;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Setup.SetupBuilder maximumRequestLength(final short maximumRequestLength) {
      this.maximumRequestLength = maximumRequestLength;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Setup.SetupBuilder bitmapFormatScanlineUnit(final byte bitmapFormatScanlineUnit) {
      this.bitmapFormatScanlineUnit = bitmapFormatScanlineUnit;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Setup.SetupBuilder bitmapFormatScanlinePad(final byte bitmapFormatScanlinePad) {
      this.bitmapFormatScanlinePad = bitmapFormatScanlinePad;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Setup.SetupBuilder minKeycode(final byte minKeycode) {
      this.minKeycode = minKeycode;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Setup.SetupBuilder maxKeycode(final byte maxKeycode) {
      this.maxKeycode = maxKeycode;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Setup.SetupBuilder vendor(final ByteList vendor) {
      if (vendor == null) {
        throw new java.lang.NullPointerException("vendor is marked non-null but is null");
      }
      this.vendor = vendor;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Setup.SetupBuilder pixmapFormats(final ImmutableList<Format> pixmapFormats) {
      if (pixmapFormats == null) {
        throw new java.lang.NullPointerException("pixmapFormats is marked non-null but is null");
      }
      this.pixmapFormats = pixmapFormats;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Setup.SetupBuilder roots(final ImmutableList<Screen> roots) {
      if (roots == null) {
        throw new java.lang.NullPointerException("roots is marked non-null but is null");
      }
      this.roots = roots;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Setup build() {
      return new Setup(this.status, this.protocolMajorVersion, this.protocolMinorVersion, this.length, this.releaseNumber, this.resourceIdBase, this.resourceIdMask, this.motionBufferSize, this.maximumRequestLength, this.imageByteOrder, this.bitmapFormatBitOrder, this.bitmapFormatScanlineUnit, this.bitmapFormatScanlinePad, this.minKeycode, this.maxKeycode, this.vendor, this.pixmapFormats, this.roots);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Setup.SetupBuilder(status=" + this.status + ", protocolMajorVersion=" + this.protocolMajorVersion + ", protocolMinorVersion=" + this.protocolMinorVersion + ", length=" + this.length + ", releaseNumber=" + this.releaseNumber + ", resourceIdBase=" + this.resourceIdBase + ", resourceIdMask=" + this.resourceIdMask + ", motionBufferSize=" + this.motionBufferSize + ", maximumRequestLength=" + this.maximumRequestLength + ", imageByteOrder=" + this.imageByteOrder + ", bitmapFormatBitOrder=" + this.bitmapFormatBitOrder + ", bitmapFormatScanlineUnit=" + this.bitmapFormatScanlineUnit + ", bitmapFormatScanlinePad=" + this.bitmapFormatScanlinePad + ", minKeycode=" + this.minKeycode + ", maxKeycode=" + this.maxKeycode + ", vendor=" + this.vendor + ", pixmapFormats=" + this.pixmapFormats + ", roots=" + this.roots + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Setup(final byte status, final short protocolMajorVersion, final short protocolMinorVersion, final short length, final int releaseNumber, final int resourceIdBase, final int resourceIdMask, final int motionBufferSize, final short maximumRequestLength, final byte imageByteOrder, final byte bitmapFormatBitOrder, final byte bitmapFormatScanlineUnit, final byte bitmapFormatScanlinePad, final byte minKeycode, final byte maxKeycode, final ByteList vendor, final ImmutableList<Format> pixmapFormats, final ImmutableList<Screen> roots) {
    if (vendor == null) {
      throw new java.lang.NullPointerException("vendor is marked non-null but is null");
    }
    if (pixmapFormats == null) {
      throw new java.lang.NullPointerException("pixmapFormats is marked non-null but is null");
    }
    if (roots == null) {
      throw new java.lang.NullPointerException("roots is marked non-null but is null");
    }
    this.status = status;
    this.protocolMajorVersion = protocolMajorVersion;
    this.protocolMinorVersion = protocolMinorVersion;
    this.length = length;
    this.releaseNumber = releaseNumber;
    this.resourceIdBase = resourceIdBase;
    this.resourceIdMask = resourceIdMask;
    this.motionBufferSize = motionBufferSize;
    this.maximumRequestLength = maximumRequestLength;
    this.imageByteOrder = imageByteOrder;
    this.bitmapFormatBitOrder = bitmapFormatBitOrder;
    this.bitmapFormatScanlineUnit = bitmapFormatScanlineUnit;
    this.bitmapFormatScanlinePad = bitmapFormatScanlinePad;
    this.minKeycode = minKeycode;
    this.maxKeycode = maxKeycode;
    this.vendor = vendor;
    this.pixmapFormats = pixmapFormats;
    this.roots = roots;
  }

  @java.lang.SuppressWarnings("all")
  public static Setup.SetupBuilder builder() {
    return new Setup.SetupBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getStatus() {
    return this.status;
  }

  @java.lang.SuppressWarnings("all")
  public short getProtocolMajorVersion() {
    return this.protocolMajorVersion;
  }

  @java.lang.SuppressWarnings("all")
  public short getProtocolMinorVersion() {
    return this.protocolMinorVersion;
  }

  @java.lang.SuppressWarnings("all")
  public short getLength() {
    return this.length;
  }

  @java.lang.SuppressWarnings("all")
  public int getReleaseNumber() {
    return this.releaseNumber;
  }

  @java.lang.SuppressWarnings("all")
  public int getResourceIdBase() {
    return this.resourceIdBase;
  }

  @java.lang.SuppressWarnings("all")
  public int getResourceIdMask() {
    return this.resourceIdMask;
  }

  @java.lang.SuppressWarnings("all")
  public int getMotionBufferSize() {
    return this.motionBufferSize;
  }

  @java.lang.SuppressWarnings("all")
  public short getMaximumRequestLength() {
    return this.maximumRequestLength;
  }

  @java.lang.SuppressWarnings("all")
  public byte getImageByteOrder() {
    return this.imageByteOrder;
  }

  @java.lang.SuppressWarnings("all")
  public byte getBitmapFormatBitOrder() {
    return this.bitmapFormatBitOrder;
  }

  @java.lang.SuppressWarnings("all")
  public byte getBitmapFormatScanlineUnit() {
    return this.bitmapFormatScanlineUnit;
  }

  @java.lang.SuppressWarnings("all")
  public byte getBitmapFormatScanlinePad() {
    return this.bitmapFormatScanlinePad;
  }

  @java.lang.SuppressWarnings("all")
  public byte getMinKeycode() {
    return this.minKeycode;
  }

  @java.lang.SuppressWarnings("all")
  public byte getMaxKeycode() {
    return this.maxKeycode;
  }

  @java.lang.SuppressWarnings("all")
  public ByteList getVendor() {
    return this.vendor;
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<Format> getPixmapFormats() {
    return this.pixmapFormats;
  }

  @java.lang.SuppressWarnings("all")
  public ImmutableList<Screen> getRoots() {
    return this.roots;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Setup)) return false;
    final Setup other = (Setup) o;
    if (this.getStatus() != other.getStatus()) return false;
    if (this.getProtocolMajorVersion() != other.getProtocolMajorVersion()) return false;
    if (this.getProtocolMinorVersion() != other.getProtocolMinorVersion()) return false;
    if (this.getLength() != other.getLength()) return false;
    if (this.getReleaseNumber() != other.getReleaseNumber()) return false;
    if (this.getResourceIdBase() != other.getResourceIdBase()) return false;
    if (this.getResourceIdMask() != other.getResourceIdMask()) return false;
    if (this.getMotionBufferSize() != other.getMotionBufferSize()) return false;
    if (this.getMaximumRequestLength() != other.getMaximumRequestLength()) return false;
    if (this.getImageByteOrder() != other.getImageByteOrder()) return false;
    if (this.getBitmapFormatBitOrder() != other.getBitmapFormatBitOrder()) return false;
    if (this.getBitmapFormatScanlineUnit() != other.getBitmapFormatScanlineUnit()) return false;
    if (this.getBitmapFormatScanlinePad() != other.getBitmapFormatScanlinePad()) return false;
    if (this.getMinKeycode() != other.getMinKeycode()) return false;
    if (this.getMaxKeycode() != other.getMaxKeycode()) return false;
    final java.lang.Object this$vendor = this.getVendor();
    final java.lang.Object other$vendor = other.getVendor();
    if (this$vendor == null ? other$vendor != null : !this$vendor.equals(other$vendor)) return false;
    final java.lang.Object this$pixmapFormats = this.getPixmapFormats();
    final java.lang.Object other$pixmapFormats = other.getPixmapFormats();
    if (this$pixmapFormats == null ? other$pixmapFormats != null : !this$pixmapFormats.equals(other$pixmapFormats)) return false;
    final java.lang.Object this$roots = this.getRoots();
    final java.lang.Object other$roots = other.getRoots();
    if (this$roots == null ? other$roots != null : !this$roots.equals(other$roots)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getStatus();
    result = result * PRIME + this.getProtocolMajorVersion();
    result = result * PRIME + this.getProtocolMinorVersion();
    result = result * PRIME + this.getLength();
    result = result * PRIME + this.getReleaseNumber();
    result = result * PRIME + this.getResourceIdBase();
    result = result * PRIME + this.getResourceIdMask();
    result = result * PRIME + this.getMotionBufferSize();
    result = result * PRIME + this.getMaximumRequestLength();
    result = result * PRIME + this.getImageByteOrder();
    result = result * PRIME + this.getBitmapFormatBitOrder();
    result = result * PRIME + this.getBitmapFormatScanlineUnit();
    result = result * PRIME + this.getBitmapFormatScanlinePad();
    result = result * PRIME + this.getMinKeycode();
    result = result * PRIME + this.getMaxKeycode();
    final java.lang.Object $vendor = this.getVendor();
    result = result * PRIME + ($vendor == null ? 43 : $vendor.hashCode());
    final java.lang.Object $pixmapFormats = this.getPixmapFormats();
    result = result * PRIME + ($pixmapFormats == null ? 43 : $pixmapFormats.hashCode());
    final java.lang.Object $roots = this.getRoots();
    result = result * PRIME + ($roots == null ? 43 : $roots.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Setup(status=" + this.getStatus() + ", protocolMajorVersion=" + this.getProtocolMajorVersion() + ", protocolMinorVersion=" + this.getProtocolMinorVersion() + ", length=" + this.getLength() + ", releaseNumber=" + this.getReleaseNumber() + ", resourceIdBase=" + this.getResourceIdBase() + ", resourceIdMask=" + this.getResourceIdMask() + ", motionBufferSize=" + this.getMotionBufferSize() + ", maximumRequestLength=" + this.getMaximumRequestLength() + ", imageByteOrder=" + this.getImageByteOrder() + ", bitmapFormatBitOrder=" + this.getBitmapFormatBitOrder() + ", bitmapFormatScanlineUnit=" + this.getBitmapFormatScanlineUnit() + ", bitmapFormatScanlinePad=" + this.getBitmapFormatScanlinePad() + ", minKeycode=" + this.getMinKeycode() + ", maxKeycode=" + this.getMaxKeycode() + ", vendor=" + this.getVendor() + ", pixmapFormats=" + this.getPixmapFormats() + ", roots=" + this.getRoots() + ")";
  }
}
