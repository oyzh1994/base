package com.github.moaxcp.x11.protocol.xkb;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XObject;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class Listing implements XStruct {
  public static final String PLUGIN_NAME = "xkb";
  private final short flags;
  private final byte[] string;

  public static Listing readListing(X11Input in) throws IOException {
    Listing.ListingBuilder javaBuilder = Listing.builder();
    short flags = in.readCard16();
    short length = in.readCard16();
    byte[] string = in.readChar(Short.toUnsignedInt(length));
    in.readPadAlign(2, Short.toUnsignedInt(length));
    javaBuilder.flags(flags);
    javaBuilder.string(string);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    out.writeCard16(flags);
    short length = (short) string.length;
    out.writeCard16(length);
    out.writeChar(string);
    out.writePadAlign(2, Short.toUnsignedInt(length));
  }

  @Override
  public int getSize() {
    return 4 + 1 * string.length + XObject.getSizeForPadAlign(2, 1 * string.length);
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class ListingBuilder {
    @java.lang.SuppressWarnings("all")
    private short flags;
    @java.lang.SuppressWarnings("all")
    private byte[] string;

    public int getSize() {
      return 4 + 1 * string.length + XObject.getSizeForPadAlign(2, 1 * string.length);
    }

    @java.lang.SuppressWarnings("all")
    ListingBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Listing.ListingBuilder flags(final short flags) {
      this.flags = flags;
      return this;
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public Listing.ListingBuilder string(final byte[] string) {
      if (string == null) {
        throw new java.lang.NullPointerException("string is marked non-null but is null");
      }
      this.string = string;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public Listing build() {
      return new Listing(this.flags, this.string);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "Listing.ListingBuilder(flags=" + this.flags + ", string=" + java.util.Arrays.toString(this.string) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  Listing(final short flags, final byte[] string) {
    if (string == null) {
      throw new java.lang.NullPointerException("string is marked non-null but is null");
    }
    this.flags = flags;
    this.string = string;
  }

  @java.lang.SuppressWarnings("all")
  public static Listing.ListingBuilder builder() {
    return new Listing.ListingBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short getFlags() {
    return this.flags;
  }

  @java.lang.SuppressWarnings("all")
  public byte[] getString() {
    return this.string;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof Listing)) return false;
    final Listing other = (Listing) o;
    if (this.getFlags() != other.getFlags()) return false;
    if (!java.util.Arrays.equals(this.getString(), other.getString())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + this.getFlags();
    result = result * PRIME + java.util.Arrays.hashCode(this.getString());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "Listing(flags=" + this.getFlags() + ", string=" + java.util.Arrays.toString(this.getString()) + ")";
  }
}
