package br.com.portopirata.wealth.utils;

import static br.com.portopirata.wealth.config.Constants.DEFAULT_ASSETS;
import static java.math.BigDecimal.valueOf;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import br.com.portopirata.wealth.portfolio.Asset;

public class AssetValuesGenerator 
{
	private static Map<Asset,BigDecimal> USDPriceMap = new HashMap<>() {
		private static final long serialVersionUID = 8962035295878519689L;
		{
			put(DEFAULT_ASSETS.get(0), valueOf(1.00d));          // USD – US Dollars
			put(DEFAULT_ASSETS.get(1), valueOf(1.15d));          // EUR – Euros
			put(DEFAULT_ASSETS.get(2), valueOf(1.24d));          // CHF – Swiss Franc
			put(DEFAULT_ASSETS.get(3), valueOf(0.0071d));        // JPY – Japanese Yen
			put(DEFAULT_ASSETS.get(4), valueOf(0.137d));         // CNY – Chinese Yuan
			put(DEFAULT_ASSETS.get(5), valueOf(0.0124d));        // RUB – Russian Ruble
			put(DEFAULT_ASSETS.get(6), valueOf(0.172d));         // BRL – Brazilian Real
			put(DEFAULT_ASSETS.get(7), valueOf(0.000232d));      // COP – Colombian Peso
	
			put(DEFAULT_ASSETS.get(8), valueOf(87473.39d));      // BTC – Bitcoin
			put(DEFAULT_ASSETS.get(9), valueOf(1.00d));          // USDT – Tether
			put(DEFAULT_ASSETS.get(10), valueOf(1.00d));         // USDC – USD Coin
			put(DEFAULT_ASSETS.get(11), valueOf(1.15d));         // EURI – Euro Coin
			put(DEFAULT_ASSETS.get(12), valueOf(3462.13d));      // PAXG – PAX Gold
			put(DEFAULT_ASSETS.get(13), valueOf(3458.40d));      // XAUT – Tether Gold
			put(DEFAULT_ASSETS.get(14), valueOf(215.27d));       // XMR – Monero
			put(DEFAULT_ASSETS.get(15), valueOf(31.14d));        // ZEC – ZCash
			put(DEFAULT_ASSETS.get(16), valueOf(31.14d));        // DASH – Dash
	
			put(DEFAULT_ASSETS.get(17), valueOf(437.00d));       // S&P500 – S&P 500 ETF
			put(DEFAULT_ASSETS.get(18), valueOf(20.00d));        // DFNS – VanEck Defense UCITS ETF
			put(DEFAULT_ASSETS.get(19), valueOf(80.00d));        // REMX – Rare Earths & Strategic Metals
			put(DEFAULT_ASSETS.get(20), valueOf(25.00d));        // RARE – Strategic Metals and Rare ETF
			put(DEFAULT_ASSETS.get(21), valueOf(30.00d));        // UCITS – Energy Transition Metals
			put(DEFAULT_ASSETS.get(22), valueOf(18.00d));        // KROP – AgTech & Food Innovation
			put(DEFAULT_ASSETS.get(23), valueOf(90.00d));        // MOO – Agribusiness ETF
			put(DEFAULT_ASSETS.get(24), valueOf(40.00d));        // VEGI – Global Agriculture ETF
			put(DEFAULT_ASSETS.get(25), valueOf(25.00d));        // PDBA – Commodity Strategy ETF
	}};
	
	private static Map<Asset,BigDecimal> StandardDeviationMap = new HashMap<>() {
		private static final long serialVersionUID = -2814628214315030218L;
		{
			put(DEFAULT_ASSETS.get(0), valueOf(0.01d));       // USD – Estável (~1%)
			put(DEFAULT_ASSETS.get(1), valueOf(0.07d));       // EUR – Volátil moderado (~7%)
			put(DEFAULT_ASSETS.get(2), valueOf(0.06d));       // CHF – Estável (~6%)
			put(DEFAULT_ASSETS.get(3), valueOf(0.12d));       // JPY – Moderado (~12%)
			put(DEFAULT_ASSETS.get(4), valueOf(0.08d));       // CNY – Controlado (~8%)
			put(DEFAULT_ASSETS.get(5), valueOf(0.25d));       // RUB – Alta volatilidade (~25%)
			put(DEFAULT_ASSETS.get(6), valueOf(0.18d));       // BRL – Alta volatilidade (~18%)
			put(DEFAULT_ASSETS.get(7), valueOf(0.22d));       // COP – Muito volátil (~22%)

			put(DEFAULT_ASSETS.get(8), valueOf(0.60d));       // BTC – Extremamente volátil (~60%)
			put(DEFAULT_ASSETS.get(9), valueOf(0.01d));       // USDT – Estável
			put(DEFAULT_ASSETS.get(10), valueOf(0.01d));      // USDC – Estável
			put(DEFAULT_ASSETS.get(11), valueOf(0.02d));      // EURI – Estável (~2%)
			put(DEFAULT_ASSETS.get(12), valueOf(0.18d));      // PAXG – Ouro tokenizado (~18%)
			put(DEFAULT_ASSETS.get(13), valueOf(0.18d));      // XAUT – Ouro tokenizado (~18%)
			put(DEFAULT_ASSETS.get(14), valueOf(0.55d));      // XMR – Alta volatilidade (~55%)
			put(DEFAULT_ASSETS.get(15), valueOf(0.50d));      // ZEC – Alta (~50%)
			put(DEFAULT_ASSETS.get(16), valueOf(0.45d));      // DASH – Alta (~45%)

			put(DEFAULT_ASSETS.get(17), valueOf(0.15d));      // S&P500 – Moderado (~15%)
			put(DEFAULT_ASSETS.get(18), valueOf(0.25d));      // DFNS – Setor defesa (~25%)
			put(DEFAULT_ASSETS.get(19), valueOf(0.30d));      // REMX – Mineração estratégica (~30%)
			put(DEFAULT_ASSETS.get(20), valueOf(0.28d));      // RARE – Metais estratégicos (~28%)
			put(DEFAULT_ASSETS.get(21), valueOf(0.32d));      // UCITS – Metais + energia (~32%)
			put(DEFAULT_ASSETS.get(22), valueOf(0.22d));      // KROP – Agro + tech (~22%)
			put(DEFAULT_ASSETS.get(23), valueOf(0.18d));      // MOO – Agronegócio (~18%)
			put(DEFAULT_ASSETS.get(24), valueOf(0.17d));      // VEGI – Agricultura global (~17%)
			put(DEFAULT_ASSETS.get(25), valueOf(0.20d));      // PDBA – Commodities agrícolas (~20%)
			put(DEFAULT_ASSETS.get(26), valueOf(0.17d));      // VEGI (duplicado)

		}
	};
	
	public static BigDecimal amountInUSD(Asset asset, double amount) {
		return USDPriceMap.get( asset ).multiply( valueOf( amount ) );
	}
	
}
