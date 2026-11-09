package com.github.moaxcp.x11.protocol.xkb;

import java.util.ArrayList;
import java.util.List;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XReply;
import java.io.IOException;

public final class ListComponentsReply implements XReply {
  public static final String PLUGIN_NAME = "xkb";
  private final byte deviceID;
  private final short sequenceNumber;
  private final short extra;
  private final List<Listing> keymaps;
  private final List<Listing> keycodes;
  private final List<Listing> types;
  private final List<Listing> compatMaps;
  private final List<Listing> symbols;
  private final List<Listing> geometries;

  public static ListComponentsReply readListComponentsReply(byte deviceID, short sequenceNumber, X11Input in) throws IOException {
    ListComponentsReply.ListComponentsReplyBuilder javaBuilder = ListComponentsReply.builder();
    int length = in.readCard32();
    short nKeymaps = in.readCard16();
    short nKeycodes = in.readCard16();
    short nTypes = in.readCard16();
    short nCompatMaps = in.readCard16();
    short nSymbols = in.readCard16();
    short nGeometries = in.readCard16();
    short extra = in.readCard16();
    byte[] pad11 = in.readPad(10);
    List<Listing> keymaps = new ArrayList<>(Short.toUnsignedInt(nKeymaps));
    for (int i = 0; i < Short.toUnsignedInt(nKeymaps); i++) {
      keymaps.add(Listing.readListing(in));
    }
    List<Listing> keycodes = new ArrayList<>(Short.toUnsignedInt(nKeycodes));
    for (int i = 0; i < Short.toUnsignedInt(nKeycodes); i++) {
      keycodes.add(Listing.readListing(in));
    }
    List<Listing> types = new ArrayList<>(Short.toUnsignedInt(nTypes));
    for (int i = 0; i < Short.toUnsignedInt(nTypes); i++) {
      types.add(Listing.readListing(in));
    }
    List<Listing> compatMaps = new ArrayList<>(Short.toUnsignedInt(nCompatMaps));
    for (int i = 0; i < Short.toUnsignedInt(nCompatMaps); i++) {
      compatMaps.add(Listing.readListing(in));
    }
    List<Listing> symbols = new ArrayList<>(Short.toUnsignedInt(nSymbols));
    for (int i = 0; i < Short.toUnsignedInt(nSymbols); i++) {
      symbols.add(Listing.readListing(in));
    }
    List<Listing> geometries = new ArrayList<>(Short.toUnsignedInt(nGeometries));
    for (int i = 0; i < Short.toUnsignedInt(nGeometries); i++) {
      geometries.add(Listing.readListing(in));
    }
    javaBuilder.deviceID(deviceID);
    javaBuilder.sequenceNumber(sequenceNumber);
    javaBuilder.extra(extra);
    javaBuilder.keymaps(List.copyOf(keymaps));
    javaBuilder.keycodes(List.copyOf(keycodes));
    javaBuilder.types(List.copyOf(types));
    javaBuilder.compatMaps(List.copyOf(compatMaps));
    javaBuilder.symbols(List.copyOf(symbols));
    javaBuilder.geometries(List.copyOf(geometries));
    if (javaBuilder.getSize() < 32) {
      in.readPad(32 - javaBuilder.getSize());
    }
    in.readPadAlign(javaBuilder.getSize());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard8(getResponseCode());
    out.writeCard8(deviceID);
    out.writeCard16(sequenceNumber);
    out.writeCard32(getLength());
    short nKeymaps = (short) keymaps.size();
    out.writeCard16(nKeymaps);
    short nKeycodes = (short) keycodes.size();
    out.writeCard16(nKeycodes);
    short nTypes = (short) types.size();
    out.writeCard16(nTypes);
    short nCompatMaps = (short) compatMaps.size();
    out.writeCard16(nCompatMaps);
    short nSymbols = (short) symbols.size();
    out.writeCard16(nSymbols);
    short nGeometries = (short) geometries.size();
    out.writeCard16(nGeometries);
    out.writeCard16(extra);
    out.writePad(10);
    for (Listing t : keymaps) {
      t.write(out);
    }
    for (Listing t : keycodes) {
      t.write(out);
    }
    for (Listing t : types) {
      t.write(out);
    }
    for (Listing t : compatMaps) {
      t.write(out);
    }
    for (Listing t : symbols) {
      t.write(out);
    }
    for (Listing t : geometries) {
      t.write(out);
    }
    out.writePadAlign(getSize());
  }

  @Override
  public int getSize() {
    return 32 + XObject.sizeOf(keymaps) + XObject.sizeOf(keycodes) + XObject.sizeOf(types) + XObject.sizeOf(compatMaps) + XObject.sizeOf(symbols) + XObject.sizeOf(geometries);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ListComponentsReplyBuilder {
    @java.lang.SuppressWarnings("all")
    private byte deviceID;
    @java.lang.SuppressWarnings("all")
    private short sequenceNumber;
    @java.lang.SuppressWarnings("all")
    private short extra;
    @java.lang.SuppressWarnings("all")
    private List<Listing> keymaps;
    @java.lang.SuppressWarnings("all")
    private List<Listing> keycodes;
    @java.lang.SuppressWarnings("all")
    private List<Listing> types;
    @java.lang.SuppressWarnings("all")
    private List<Listing> compatMaps;
    @java.lang.SuppressWarnings("all")
    private List<Listing> symbols;
    @java.lang.SuppressWarnings("all")
    private List<Listing> geometries;

    public int getSize() {
      return 32 + XObject.sizeOf(keymaps) + XObject.sizeOf(keycodes) + XObject.sizeOf(types) + XObject.sizeOf(compatMaps) + XObject.sizeOf(symbols) + XObject.sizeOf(geometries);
    }

    @java.lang.SuppressWarnings("all")
    ListComponentsReplyBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListComponentsReply.ListComponentsReplyBuilder deviceID(final byte deviceID) {
      this.deviceID = deviceID;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListComponentsReply.ListComponentsReplyBuilder sequenceNumber(final short sequenceNumber) {
      this.sequenceNumber = sequenceNumber;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListComponentsReply.ListComponentsReplyBuilder extra(final short extra) {
      this.extra = extra;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListComponentsReply.ListComponentsReplyBuilder keymaps(final List<Listing> keymaps) {
      if (keymaps == null) {
        throw new java.lang.NullPointerException("keymaps is marked non-null but is null");
      }
      this.keymaps = keymaps;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListComponentsReply.ListComponentsReplyBuilder keycodes(final List<Listing> keycodes) {
      if (keycodes == null) {
        throw new java.lang.NullPointerException("keycodes is marked non-null but is null");
      }
      this.keycodes = keycodes;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListComponentsReply.ListComponentsReplyBuilder types(final List<Listing> types) {
      if (types == null) {
        throw new java.lang.NullPointerException("types is marked non-null but is null");
      }
      this.types = types;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListComponentsReply.ListComponentsReplyBuilder compatMaps(final List<Listing> compatMaps) {
      if (compatMaps == null) {
        throw new java.lang.NullPointerException("compatMaps is marked non-null but is null");
      }
      this.compatMaps = compatMaps;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListComponentsReply.ListComponentsReplyBuilder symbols(final List<Listing> symbols) {
      if (symbols == null) {
        throw new java.lang.NullPointerException("symbols is marked non-null but is null");
      }
      this.symbols = symbols;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public ListComponentsReply.ListComponentsReplyBuilder geometries(final List<Listing> geometries) {
      if (geometries == null) {
        throw new java.lang.NullPointerException("geometries is marked non-null but is null");
      }
      this.geometries = geometries;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public ListComponentsReply build() {
      return new ListComponentsReply(this.deviceID, this.sequenceNumber, this.extra, this.keymaps, this.keycodes, this.types, this.compatMaps, this.symbols, this.geometries);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "ListComponentsReply.ListComponentsReplyBuilder(deviceID=" + this.deviceID + ", sequenceNumber=" + this.sequenceNumber + ", extra=" + this.extra + ", keymaps=" + this.keymaps + ", keycodes=" + this.keycodes + ", types=" + this.types + ", compatMaps=" + this.compatMaps + ", symbols=" + this.symbols + ", geometries=" + this.geometries + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  ListComponentsReply(final byte deviceID, final short sequenceNumber, final short extra, final List<Listing> keymaps, final List<Listing> keycodes, final List<Listing> types, final List<Listing> compatMaps, final List<Listing> symbols, final List<Listing> geometries) {
    if (keymaps == null) {
      throw new java.lang.NullPointerException("keymaps is marked non-null but is null");
    }
    if (keycodes == null) {
      throw new java.lang.NullPointerException("keycodes is marked non-null but is null");
    }
    if (types == null) {
      throw new java.lang.NullPointerException("types is marked non-null but is null");
    }
    if (compatMaps == null) {
      throw new java.lang.NullPointerException("compatMaps is marked non-null but is null");
    }
    if (symbols == null) {
      throw new java.lang.NullPointerException("symbols is marked non-null but is null");
    }
    if (geometries == null) {
      throw new java.lang.NullPointerException("geometries is marked non-null but is null");
    }
    this.deviceID = deviceID;
    this.sequenceNumber = sequenceNumber;
    this.extra = extra;
    this.keymaps = keymaps;
    this.keycodes = keycodes;
    this.types = types;
    this.compatMaps = compatMaps;
    this.symbols = symbols;
    this.geometries = geometries;
  }

  @java.lang.SuppressWarnings("all")
  public static ListComponentsReply.ListComponentsReplyBuilder builder() {
    return new ListComponentsReply.ListComponentsReplyBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public byte getDeviceID() {
    return this.deviceID;
  }

  @java.lang.SuppressWarnings("all")
  public short getSequenceNumber() {
    return this.sequenceNumber;
  }

  @java.lang.SuppressWarnings("all")
  public short getExtra() {
    return this.extra;
  }

  @java.lang.SuppressWarnings("all")
  public List<Listing> getKeymaps() {
    return this.keymaps;
  }

  @java.lang.SuppressWarnings("all")
  public List<Listing> getKeycodes() {
    return this.keycodes;
  }

  @java.lang.SuppressWarnings("all")
  public List<Listing> getTypes() {
    return this.types;
  }

  @java.lang.SuppressWarnings("all")
  public List<Listing> getCompatMaps() {
    return this.compatMaps;
  }

  @java.lang.SuppressWarnings("all")
  public List<Listing> getSymbols() {
    return this.symbols;
  }

  @java.lang.SuppressWarnings("all")
  public List<Listing> getGeometries() {
    return this.geometries;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof ListComponentsReply)) return false;
    final ListComponentsReply other = (ListComponentsReply) o;
    if (this.getDeviceID() != other.getDeviceID()) return false;
    if (this.getSequenceNumber() != other.getSequenceNumber()) return false;
    if (this.getExtra() != other.getExtra()) return false;
    final java.lang.Object this$keymaps = this.getKeymaps();
    final java.lang.Object other$keymaps = other.getKeymaps();
    if (this$keymaps == null ? other$keymaps != null : !this$keymaps.equals(other$keymaps)) return false;
    final java.lang.Object this$keycodes = this.getKeycodes();
    final java.lang.Object other$keycodes = other.getKeycodes();
    if (this$keycodes == null ? other$keycodes != null : !this$keycodes.equals(other$keycodes)) return false;
    final java.lang.Object this$types = this.getTypes();
    final java.lang.Object other$types = other.getTypes();
    if (this$types == null ? other$types != null : !this$types.equals(other$types)) return false;
    final java.lang.Object this$compatMaps = this.getCompatMaps();
    final java.lang.Object other$compatMaps = other.getCompatMaps();
    if (this$compatMaps == null ? other$compatMaps != null : !this$compatMaps.equals(other$compatMaps)) return false;
    final java.lang.Object this$symbols = this.getSymbols();
    final java.lang.Object other$symbols = other.getSymbols();
    if (this$symbols == null ? other$symbols != null : !this$symbols.equals(other$symbols)) return false;
    final java.lang.Object this$geometries = this.getGeometries();
    final java.lang.Object other$geometries = other.getGeometries();
    if (this$geometries == null ? other$geometries != null : !this$geometries.equals(other$geometries)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getDeviceID();
    result = result * PRIME + this.getSequenceNumber();
    result = result * PRIME + this.getExtra();
    final java.lang.Object $keymaps = this.getKeymaps();
    result = result * PRIME + ($keymaps == null ? 43 : $keymaps.hashCode());
    final java.lang.Object $keycodes = this.getKeycodes();
    result = result * PRIME + ($keycodes == null ? 43 : $keycodes.hashCode());
    final java.lang.Object $types = this.getTypes();
    result = result * PRIME + ($types == null ? 43 : $types.hashCode());
    final java.lang.Object $compatMaps = this.getCompatMaps();
    result = result * PRIME + ($compatMaps == null ? 43 : $compatMaps.hashCode());
    final java.lang.Object $symbols = this.getSymbols();
    result = result * PRIME + ($symbols == null ? 43 : $symbols.hashCode());
    final java.lang.Object $geometries = this.getGeometries();
    result = result * PRIME + ($geometries == null ? 43 : $geometries.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "ListComponentsReply(deviceID=" + this.getDeviceID() + ", sequenceNumber=" + this.getSequenceNumber() + ", extra=" + this.getExtra() + ", keymaps=" + this.getKeymaps() + ", keycodes=" + this.getKeycodes() + ", types=" + this.getTypes() + ", compatMaps=" + this.getCompatMaps() + ", symbols=" + this.getSymbols() + ", geometries=" + this.getGeometries() + ")";
  }
}
