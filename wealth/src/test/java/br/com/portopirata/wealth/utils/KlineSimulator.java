package br.com.portopirata.wealth.utils;
import static java.math.BigDecimal.valueOf;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.portopirata.wealth.market.Kline;

public class KlineSimulator {

    private final Random random = new Random();
    private final double stdDev;
    private final double drift;
    private final BigDecimal initialPrice;
    private final long candleDurationMillis;

    public KlineSimulator(BigDecimal initialPrice, double stdDev, double drift, long candleDurationMillis) {
        this.initialPrice = initialPrice;
        this.stdDev = stdDev;
        this.drift = drift;
        this.candleDurationMillis = candleDurationMillis;
    }

    public List<Kline> generateSeries(int count) {
        List<Kline> candles = new ArrayList<>();
        BigDecimal lastClose = initialPrice;
        Instant openTime = Instant.now();

        for (int i = 0; i < count; i++) {
            BigDecimal open = lastClose;

            double logReturn = (drift - 0.5 * Math.pow(stdDev, 2)) + stdDev * random.nextGaussian();
            BigDecimal close = open.multiply(valueOf(Math.exp(logReturn)));

            // Corpo do candle
            BigDecimal high = open.max(close).add(randomRange(close.multiply(valueOf(0.002)), close.multiply(valueOf(0.01))));
            BigDecimal low = open.min(close).subtract(randomRange(close.multiply(valueOf(0.002)), close.multiply(valueOf(0.01))));

            BigDecimal volume = valueOf(100 + random.nextDouble() * 1000); // volume aleatório

            Instant closeTime = openTime.plusMillis(candleDurationMillis);

            Kline kline = new Kline(
                openTime,
                round(open),
                round(high),
                round(low),
                round(close),
                round(volume),
                closeTime,
                BigDecimal.ZERO,
                random.nextInt(1000),
                BigDecimal.ZERO,
                BigDecimal.ZERO
            );

            candles.add(kline);
            lastClose = close;
            openTime = closeTime;
        }

        return candles;
    }

    private BigDecimal randomRange(BigDecimal min, BigDecimal max) {
        return min.add(valueOf(random.nextDouble()).multiply(max.subtract(min)));
    }

    private BigDecimal round(BigDecimal value) {
        return value.setScale(2, RoundingMode.HALF_UP);
    }
}
