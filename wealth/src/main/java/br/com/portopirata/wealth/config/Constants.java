package br.com.portopirata.wealth.config;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Arrays;
import java.util.List;

import br.com.portopirata.wealth.portfolio.Asset;

public class Constants 
{
	public static final MathContext DMC = new MathContext( 20 );
	public static final BigDecimal AVOID_ZERO = BigDecimal.valueOf( 0.00000000000000000000000000000001 );
	public static final BigDecimal EXPLORATION_CONSTANT = BigDecimal.valueOf( 1.41 );
	public static final double TIMES_OF_BASE_VALUE = 1.5d;
	public static final List<Asset> DEFAULT_ASSETS = Arrays.asList
			(
					new Asset("USD","US Dollars"),
					new Asset("EUR","Euros"),
					new Asset("CGF","Swiss Franc"),
					new Asset("JPY","Japanese Yen"),
					new Asset("CNY","Chinese Yuan"),
					new Asset("RUB","Russian Rubles"),
					new Asset("BRL","Brazilian Reals"),
					new Asset("COP","Colombian pesos"),
					new Asset("BTC","Bitcoin"),
					
					new Asset("USDT","Dollar Theter"),
					new Asset("USDC","Dollar Coin"),
					new Asset("EURI","Euro Coin"),
					new Asset("PAXG","PAX Gold"),
					new Asset("XAUT","Theter Gold"),
					new Asset("XMR","Monero"), 
					new Asset("ZEC","ZCash"),
					new Asset("DASH","Dash"),
					
					new Asset("S&P500","S&P 500 ETF"),
					new Asset("DFNS","VanEck Defense UCITS ETF"),
					new Asset("REMX","VanEck Rare Earth & Strategic Metals ETF"),
					new Asset("RARE","WisdomTree Strategic Metals and Rare ETF"),
					new Asset("UCITS","WisdomTree Energy Transition Metals and Rare Earths Miners UCITS ETF"),
					new Asset("KROP","Global X AgTech & Food Innovation ETF"),
					new Asset("MOO","VanEck Agribusiness ETF"),
					new Asset("VEGI","iShares Global Agriculture ETF"),
					new Asset("PDBA","Invesco Agriculture Commodity Strategy No K-1 ETF")
			);
	
	public static final List<String> DEFAULT_EXCHANGES = Arrays.asList
			(
					"Binance",
					"Kraken",
					"Bitfinex",
                    "Bitstamp",
                    "Deribit",
                    "OKX",
                    "Interactive Brokers"
			);


}
