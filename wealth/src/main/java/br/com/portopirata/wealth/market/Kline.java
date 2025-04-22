package br.com.portopirata.wealth.market;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Kline {

    private Instant openTime;
    private BigDecimal open;
    private BigDecimal high;
    private BigDecimal low;
    private BigDecimal close;
    private BigDecimal volume;
    private Instant closeTime;
    private BigDecimal quoteAssetVolume;
    private int numberOfTrades;
    private BigDecimal takerBuyBaseVolume;
    private BigDecimal takerBuyQuoteVolume;

    @Override
    public String toString() {
        return String.format(
            "[%s] O: %s H: %s L: %s C: %s V: %s Trades: %d",
            openTime, open, high, low, close, volume, numberOfTrades
        );
    }
}