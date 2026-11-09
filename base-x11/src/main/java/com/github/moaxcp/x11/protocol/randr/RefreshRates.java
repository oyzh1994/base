package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;

public final class RefreshRates implements XStruct {
  public static final String PLUGIN_NAME = "randr";
  private final short[] rates;

  public static RefreshRates readRefreshRates(X11Input in) throws IOException {
    RefreshRates.RefreshRatesBuilder javaBuilder = RefreshRates.builder();
    short nRates = in.readCard16();
    short[] rates = in.readCard16(Short.toUnsignedInt(nRates));
    javaBuilder.rates(rates);
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    short nRates = (short) rates.length;
    out.writeCard16(nRates);
    out.writeCard16(rates);
  }

  @Override
  public int getSize() {
    return 2 + 2 * rates.length;
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class RefreshRatesBuilder {
    @java.lang.SuppressWarnings("all")
    private short[] rates;

    public int getSize() {
      return 2 + 2 * rates.length;
    }

    @java.lang.SuppressWarnings("all")
    RefreshRatesBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RefreshRates.RefreshRatesBuilder rates(final short[] rates) {
      if (rates == null) {
        throw new java.lang.NullPointerException("rates is marked non-null but is null");
      }
      this.rates = rates;
      return this;
    }

    @java.lang.SuppressWarnings("all")
    public RefreshRates build() {
      return new RefreshRates(this.rates);
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("all")
    public java.lang.String toString() {
      return "RefreshRates.RefreshRatesBuilder(rates=" + java.util.Arrays.toString(this.rates) + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  RefreshRates(final short[] rates) {
    if (rates == null) {
      throw new java.lang.NullPointerException("rates is marked non-null but is null");
    }
    this.rates = rates;
  }

  @java.lang.SuppressWarnings("all")
  public static RefreshRates.RefreshRatesBuilder builder() {
    return new RefreshRates.RefreshRatesBuilder();
  }

  @java.lang.SuppressWarnings("all")
  public short[] getRates() {
    return this.rates;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof RefreshRates)) return false;
    final RefreshRates other = (RefreshRates) o;
    if (!java.util.Arrays.equals(this.getRates(), other.getRates())) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    result = result * PRIME + java.util.Arrays.hashCode(this.getRates());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "RefreshRates(rates=" + java.util.Arrays.toString(this.getRates()) + ")";
  }
}
