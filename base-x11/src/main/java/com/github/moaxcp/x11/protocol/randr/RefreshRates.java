package com.github.moaxcp.x11.protocol.randr;

import com.github.moaxcp.x11.protocol.X11Input;
import com.github.moaxcp.x11.protocol.X11Output;
import com.github.moaxcp.x11.protocol.XStruct;
import java.io.IOException;
import org.eclipse.collections.api.list.primitive.ShortList;

public final class RefreshRates implements XStruct {
  public static final String PLUGIN_NAME = "randr";
  private final ShortList rates;

  public static RefreshRates readRefreshRates(X11Input in) throws IOException {
    RefreshRates.RefreshRatesBuilder javaBuilder = RefreshRates.builder();
    short nRates = in.readCard16();
    ShortList rates = in.readCard16(Short.toUnsignedInt(nRates));
    javaBuilder.rates(rates.toImmutable());
    return javaBuilder.build();
  }

  @Override
  public void write(X11Output out) throws IOException {
    short nRates = (short) rates.size();
    out.writeCard16(nRates);
    out.writeCard16(rates);
  }

  @Override
  public int getSize() {
    return 2 + 2 * rates.size();
  }

  public String getPluginName() {
    return PLUGIN_NAME;
  }


  public static class RefreshRatesBuilder {
    @java.lang.SuppressWarnings("all")
    private ShortList rates;

    public int getSize() {
      return 2 + 2 * rates.size();
    }

    @java.lang.SuppressWarnings("all")
    RefreshRatesBuilder() {
    }

    /**
     * @return {@code this}.
     */
    @java.lang.SuppressWarnings("all")
    public RefreshRates.RefreshRatesBuilder rates(final ShortList rates) {
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
      return "RefreshRates.RefreshRatesBuilder(rates=" + this.rates + ")";
    }
  }

  @java.lang.SuppressWarnings("all")
  RefreshRates(final ShortList rates) {
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
  public ShortList getRates() {
    return this.rates;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public boolean equals(final java.lang.Object o) {
    if (o == this) return true;
    if (!(o instanceof RefreshRates)) return false;
    final RefreshRates other = (RefreshRates) o;
    final java.lang.Object this$rates = this.getRates();
    final java.lang.Object other$rates = other.getRates();
    if (this$rates == null ? other$rates != null : !this$rates.equals(other$rates)) return false;
    return true;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final java.lang.Object $rates = this.getRates();
    result = result * PRIME + ($rates == null ? 43 : $rates.hashCode());
    return result;
  }

  @java.lang.Override
  @java.lang.SuppressWarnings("all")
  public java.lang.String toString() {
    return "RefreshRates(rates=" + this.getRates() + ")";
  }
}
