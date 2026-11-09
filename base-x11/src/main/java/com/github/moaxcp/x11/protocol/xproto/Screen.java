package com.github.moaxcp.x11.protocol.xproto;

import java.util.ArrayList;
import java.util.List;
import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class Screen implements XStruct {
  public static final String PLUGIN_NAME = "xproto";
  private final int root;
  private final int defaultColormap;
  private final int whitePixel;
  private final int blackPixel;
  private final int currentInputMasks;
  private final short widthInPixels;
  private final short heightInPixels;
  private final short widthInMillimeters;
  private final short heightInMillimeters;
  private final short minInstalledMaps;
  private final short maxInstalledMaps;
  private final int rootVisual;
  private final byte backingStores;
  private final boolean saveUnders;
  private final byte rootDepth;
  private final List<Depth> allowedDepths;

  public static Screen readScreen(X11Input in) throws IOException {
    Screen.ScreenBuilder javaBuilder = Screen.builder();
    int root = in.readCard32();
    int defaultColormap = in.readCard32();
    int whitePixel = in.readCard32();
    int blackPixel = in.readCard32();
    int currentInputMasks = in.readCard32();
    short widthInPixels = in.readCard16();
    short heightInPixels = in.readCard16();
    short widthInMillimeters = in.readCard16();
    short heightInMillimeters = in.readCard16();
    short minInstalledMaps = in.readCard16();
    short maxInstalledMaps = in.readCard16();
    int rootVisual = in.readCard32();
    byte backingStores = in.readByte();
    boolean saveUnders = in.readBool();
    byte rootDepth = in.readCard8();
    byte allowedDepthsLen = in.readCard8();
    List<Depth> allowedDepths = new ArrayList<>(Byte.toUnsignedInt(allowedDepthsLen));
    for (int i = 0; i < Byte.toUnsignedInt(allowedDepthsLen); i++) {
      allowedDepths.add(Depth.readDepth(in));
    }
    javaBuilder.root(root);
    javaBuilder.defaultColormap(defaultColormap);
    javaBuilder.whitePixel(whitePixel);
    javaBuilder.blackPixel(blackPixel);
    javaBuilder.currentInputMasks(currentInputMasks);
    javaBuilder.widthInPixels(widthInPixels);
    javaBuilder.heightInPixels(heightInPixels);
    javaBuilder.widthInMillimeters(widthInMillimeters);
    javaBuilder.heightInMillimeters(heightInMillimeters);
    javaBuilder.minInstalledMaps(minInstalledMaps);
    javaBuilder.maxInstalledMaps(maxInstalledMaps);
    javaBuilder.rootVisual(rootVisual);
    javaBuilder.backingStores(backingStores);
    javaBuilder.saveUnders(saveUnders);
    javaBuilder.rootDepth(rootDepth);
    javaBuilder.allowedDepths(List.copyOf(allowedDepths));
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard32(root);
    out.writeCard32(defaultColormap);
    out.writeCard32(whitePixel);
    out.writeCard32(blackPixel);
    out.writeCard32(currentInputMasks);
    out.writeCard16(widthInPixels);
    out.writeCard16(heightInPixels);
    out.writeCard16(widthInMillimeters);
    out.writeCard16(heightInMillimeters);
    out.writeCard16(minInstalledMaps);
    out.writeCard16(maxInstalledMaps);
    out.writeCard32(rootVisual);
    out.writeByte(backingStores);
    out.writeBool(saveUnders);
    out.writeCard8(rootDepth);
    byte allowedDepthsLen = (byte) allowedDepths.size();
    out.writeCard8(allowedDepthsLen);
    for (Depth t : allowedDepths) {
      t.write(out);
    }
  }

  public boolean isCurrentInputMasksEnabled(EventMask... maskEnums) {
    if (maskEnums == null) {
      throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
    }
    for (EventMask m : maskEnums) {
      if (!m.isEnabled(currentInputMasks)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public int getSize() {
    return 40 + XObject.sizeOf(allowedDepths);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ScreenBuilder {
    @java.lang.SuppressWarnings("all")
    private int root;
    @java.lang.SuppressWarnings("all")
    private int defaultColormap;
    @java.lang.SuppressWarnings("all")
    private int whitePixel;
    @java.lang.SuppressWarnings("all")
    private int blackPixel;
    @java.lang.SuppressWarnings("all")
    private int currentInputMasks;
    @java.lang.SuppressWarnings("all")
    private short widthInPixels;
    @java.lang.SuppressWarnings("all")
    private short heightInPixels;
    @java.lang.SuppressWarnings("all")
    private short widthInMillimeters;
    @java.lang.SuppressWarnings("all")
    private short heightInMillimeters;
    @java.lang.SuppressWarnings("all")
    private short minInstalledMaps;
    @java.lang.SuppressWarnings("all")
    private short maxInstalledMaps;
    @java.lang.SuppressWarnings("all")
    private int rootVisual;
    @java.lang.SuppressWarnings("all")
    private byte backingStores;
    @java.lang.SuppressWarnings("all")
    private boolean saveUnders;
    @java.lang.SuppressWarnings("all")
    private byte rootDepth;
    @java.lang.SuppressWarnings("all")
    private List<Depth> allowedDepths;

    public boolean isCurrentInputMasksEnabled(EventMask... maskEnums) {
      if (maskEnums == null) {
        throw new java.lang.NullPointerException("maskEnums is marked non-null but is null");
      }
      for (EventMask m : maskEnums) {
        if (!m.isEnabled(currentInputMasks)) {
          return false;
        }
      }
      return true;
    }

    public Screen.ScreenBuilder currentInputMasksEnable(EventMask... maskEnums) {
      for (EventMask m : maskEnums) {
        currentInputMasks((int) m.enableFor(currentInputMasks));
      }
      return this;
    }

    public Screen.ScreenBuilder currentInputMasksDisable(EventMask... maskEnums) {
      for (EventMask m : maskEnums) {
        currentInputMasks((int) m.disableFor(currentInputMasks));
      }
      return this;
    }

    public Screen.ScreenBuilder backingStores(BackingStore backingStores) {
      this.backingStores = (byte) backingStores.getValue();
      return this;
    }

    public Screen.ScreenBuilder backingStores(byte backingStores) {
      this.backingStores = backingStores;
      return this;
    }

    public int getSize() {
      return 40 + XObject.sizeOf(allowedDepths);
    }

    @java.lang.SuppressWarnings("all")
    ScreenBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Screen.ScreenBuilder root(final int root) {
      this.root = root;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Screen.ScreenBuilder defaultColormap(final int defaultColormap) {
      this.defaultColormap = defaultColormap;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Screen.ScreenBuilder whitePixel(final int whitePixel) {
      this.whitePixel = whitePixel;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Screen.ScreenBuilder blackPixel(final int blackPixel) {
      this.blackPixel = blackPixel;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Screen.ScreenBuilder currentInputMasks(final int currentInputMasks) {
      this.currentInputMasks = currentInputMasks;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Screen.ScreenBuilder widthInPixels(final short widthInPixels) {
      this.widthInPixels = widthInPixels;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Screen.ScreenBuilder heightInPixels(final short heightInPixels) {
      this.heightInPixels = heightInPixels;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Screen.ScreenBuilder widthInMillimeters(final short widthInMillimeters) {
      this.widthInMillimeters = widthInMillimeters;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Screen.ScreenBuilder heightInMillimeters(final short heightInMillimeters) {
      this.heightInMillimeters = heightInMillimeters;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Screen.ScreenBuilder minInstalledMaps(final short minInstalledMaps) {
      this.minInstalledMaps = minInstalledMaps;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Screen.ScreenBuilder maxInstalledMaps(final short maxInstalledMaps) {
      this.maxInstalledMaps = maxInstalledMaps;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Screen.ScreenBuilder rootVisual(final int rootVisual) {
      this.rootVisual = rootVisual;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Screen.ScreenBuilder saveUnders(final boolean saveUnders) {
      this.saveUnders = saveUnders;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Screen.ScreenBuilder rootDepth(final byte rootDepth) {
      this.rootDepth = rootDepth;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Screen.ScreenBuilder allowedDepths(final List<Depth> allowedDepths) {
      if (allowedDepths == null) {
        throw new java.lang.NullPointerException("allowedDepths is marked non-null but is null");
      }
      this.allowedDepths = allowedDepths;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Screen build() {
      return new Screen(this.root, this.defaultColormap, this.whitePixel, this.blackPixel, this.currentInputMasks, this.widthInPixels, this.heightInPixels, this.widthInMillimeters, this.heightInMillimeters, this.minInstalledMaps, this.maxInstalledMaps, this.rootVisual, this.backingStores, this.saveUnders, this.rootDepth, this.allowedDepths);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Screen.ScreenBuilder(root=" + this.root + ", defaultColormap=" + this.defaultColormap + ", whitePixel=" + this.whitePixel + ", blackPixel=" + this.blackPixel + ", currentInputMasks=" + this.currentInputMasks + ", widthInPixels=" + this.widthInPixels + ", heightInPixels=" + this.heightInPixels + ", widthInMillimeters=" + this.widthInMillimeters + ", heightInMillimeters=" + this.heightInMillimeters + ", minInstalledMaps=" + this.minInstalledMaps + ", maxInstalledMaps=" + this.maxInstalledMaps + ", rootVisual=" + this.rootVisual + ", backingStores=" + this.backingStores + ", saveUnders=" + this.saveUnders + ", rootDepth=" + this.rootDepth + ", allowedDepths=" + this.allowedDepths + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Screen(final int root, final int defaultColormap, final int whitePixel, final int blackPixel, final int currentInputMasks, final short widthInPixels, final short heightInPixels, final short widthInMillimeters, final short heightInMillimeters, final short minInstalledMaps, final short maxInstalledMaps, final int rootVisual, final byte backingStores, final boolean saveUnders, final byte rootDepth, final List<Depth> allowedDepths) {
    if (allowedDepths == null) {
      throw new java.lang.NullPointerException("allowedDepths is marked non-null but is null");
    }
    this.root = root;
    this.defaultColormap = defaultColormap;
    this.whitePixel = whitePixel;
    this.blackPixel = blackPixel;
    this.currentInputMasks = currentInputMasks;
    this.widthInPixels = widthInPixels;
    this.heightInPixels = heightInPixels;
    this.widthInMillimeters = widthInMillimeters;
    this.heightInMillimeters = heightInMillimeters;
    this.minInstalledMaps = minInstalledMaps;
    this.maxInstalledMaps = maxInstalledMaps;
    this.rootVisual = rootVisual;
    this.backingStores = backingStores;
    this.saveUnders = saveUnders;
    this.rootDepth = rootDepth;
    this.allowedDepths = allowedDepths;
  }

  @java.lang.SuppressWarnings("all")
  public static Screen.ScreenBuilder builder() {
    return new Screen.ScreenBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public int getRoot() {
    return this.root;
  }

  @java.lang.SuppressWarnings("all")
  public int getDefaultColormap() {
    return this.defaultColormap;
  }

  @java.lang.SuppressWarnings("all")
  public int getWhitePixel() {
    return this.whitePixel;
  }

  @java.lang.SuppressWarnings("all")
  public int getBlackPixel() {
    return this.blackPixel;
  }

  @java.lang.SuppressWarnings("all")
  public int getCurrentInputMasks() {
    return this.currentInputMasks;
  }

  @java.lang.SuppressWarnings("all")
  public short getWidthInPixels() {
    return this.widthInPixels;
  }

  @java.lang.SuppressWarnings("all")
  public short getHeightInPixels() {
    return this.heightInPixels;
  }

  @java.lang.SuppressWarnings("all")
  public short getWidthInMillimeters() {
    return this.widthInMillimeters;
  }

  @java.lang.SuppressWarnings("all")
  public short getHeightInMillimeters() {
    return this.heightInMillimeters;
  }

  @java.lang.SuppressWarnings("all")
  public short getMinInstalledMaps() {
    return this.minInstalledMaps;
  }

  @java.lang.SuppressWarnings("all")
  public short getMaxInstalledMaps() {
    return this.maxInstalledMaps;
  }

  @java.lang.SuppressWarnings("all")
  public int getRootVisual() {
    return this.rootVisual;
  }

  @java.lang.SuppressWarnings("all")
  public byte getBackingStores() {
    return this.backingStores;
  }

  @java.lang.SuppressWarnings("all")
  public boolean isSaveUnders() {
    return this.saveUnders;
  }

  @java.lang.SuppressWarnings("all")
  public byte getRootDepth() {
    return this.rootDepth;
  }

  @java.lang.SuppressWarnings("all")
  public List<Depth> getAllowedDepths() {
    return this.allowedDepths;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Screen)) return false;
    final Screen other = (Screen) o;
    if (this.getRoot() != other.getRoot()) return false;
    if (this.getDefaultColormap() != other.getDefaultColormap()) return false;
    if (this.getWhitePixel() != other.getWhitePixel()) return false;
    if (this.getBlackPixel() != other.getBlackPixel()) return false;
    if (this.getCurrentInputMasks() != other.getCurrentInputMasks()) return false;
    if (this.getWidthInPixels() != other.getWidthInPixels()) return false;
    if (this.getHeightInPixels() != other.getHeightInPixels()) return false;
    if (this.getWidthInMillimeters() != other.getWidthInMillimeters()) return false;
    if (this.getHeightInMillimeters() != other.getHeightInMillimeters()) return false;
    if (this.getMinInstalledMaps() != other.getMinInstalledMaps()) return false;
    if (this.getMaxInstalledMaps() != other.getMaxInstalledMaps()) return false;
    if (this.getRootVisual() != other.getRootVisual()) return false;
    if (this.getBackingStores() != other.getBackingStores()) return false;
    if (this.isSaveUnders() != other.isSaveUnders()) return false;
    if (this.getRootDepth() != other.getRootDepth()) return false;
    final java.lang.Object this$allowedDepths = this.getAllowedDepths();
    final java.lang.Object other$allowedDepths = other.getAllowedDepths();
    if (this$allowedDepths == null ? other$allowedDepths != null : !this$allowedDepths.equals(other$allowedDepths)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getRoot();
    result = result * PRIME + this.getDefaultColormap();
    result = result * PRIME + this.getWhitePixel();
    result = result * PRIME + this.getBlackPixel();
    result = result * PRIME + this.getCurrentInputMasks();
    result = result * PRIME + this.getWidthInPixels();
    result = result * PRIME + this.getHeightInPixels();
    result = result * PRIME + this.getWidthInMillimeters();
    result = result * PRIME + this.getHeightInMillimeters();
    result = result * PRIME + this.getMinInstalledMaps();
    result = result * PRIME + this.getMaxInstalledMaps();
    result = result * PRIME + this.getRootVisual();
    result = result * PRIME + this.getBackingStores();
    result = result * PRIME + (this.isSaveUnders() ? 79 : 97);
    result = result * PRIME + this.getRootDepth();
    final java.lang.Object $allowedDepths = this.getAllowedDepths();
    result = result * PRIME + ($allowedDepths == null ? 43 : $allowedDepths.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Screen(root=" + this.getRoot() + ", defaultColormap=" + this.getDefaultColormap() + ", whitePixel=" + this.getWhitePixel() + ", blackPixel=" + this.getBlackPixel() + ", currentInputMasks=" + this.getCurrentInputMasks() + ", widthInPixels=" + this.getWidthInPixels() + ", heightInPixels=" + this.getHeightInPixels() + ", widthInMillimeters=" + this.getWidthInMillimeters() + ", heightInMillimeters=" + this.getHeightInMillimeters() + ", minInstalledMaps=" + this.getMinInstalledMaps() + ", maxInstalledMaps=" + this.getMaxInstalledMaps() + ", rootVisual=" + this.getRootVisual() + ", backingStores=" + this.getBackingStores() + ", saveUnders=" + this.isSaveUnders() + ", rootDepth=" + this.getRootDepth() + ", allowedDepths=" + this.getAllowedDepths() + ")";
  }
}
