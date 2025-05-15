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
					new Asset("TRX","Tron"),
					new Asset("ETH","Ethereum"),
					
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

	public static final List<Asset> ALTERNATIVE_ASSETS = Arrays.asList
			(
					new Asset("SUNDOG", "Sundog", AssetStandard.TRC20),
					new Asset("SUNCAT","Suncat", AssetStandard.TRC20),
					new Asset("WIN", "WINkLink", AssetStandard.TRC20),
					new Asset("NFT ", "APENFT", AssetStandard.TRC20),
					new Asset("BTT", "BitTorrent Token", AssetStandard.TRC20),
					new Asset("USDD", "UsdD", AssetStandard.TRC20),
					new Asset("SUN", "Sun Token", AssetStandard.TRC20),
					new Asset("USDt", "UsdT", AssetStandard.TRC20),
					new Asset("KLV", "Klever", AssetStandard.TRC20),
					
					new Asset("VRA", "Verasity", AssetStandard.ERC20),
					new Asset("ICE","Ice", AssetStandard.ERC20),
					new Asset("LCX", "Liechtenstein Cryptoassets Exchange", AssetStandard.ERC20),
					new Asset("DOGE ", "Department Of Government Efficiency", AssetStandard.ERC20),
					new Asset("NAKA", "Nakamoto.Games", AssetStandard.ERC20),
					new Asset("RAI", "Reploy", AssetStandard.ERC20),
					new Asset("OM", "Mantra DAO", AssetStandard.ERC20),
					new Asset("MAX", "Matr1x", AssetStandard.ERC20),
					new Asset("PEPE", "Pepe", AssetStandard.ERC20),
					new Asset("MOG", "Mog Coin", AssetStandard.ERC20)
			);
}


/*
	10 DEXs para Tokens TRC-20 (TRON)
	=================================
	
DEX	Site 				API							Observações técnicas
------------------------------------------------------------------------
	
SunSwap					https://sunswap.com			Principal DEX da TRON. Possui APIs abertas e boa liquidez.
JustMoney Exchange		https://tron.just.money		Vários pares exóticos, útil para tokens de baixa liquidez.
PoloniDEX				https://polonidex.org		Versão descentralizada do Poloniex, compatível com TRC-20.
Zethyr Exchange			https://zethyr.finance		Menor, mas útil para pares raros.
Tronswap				https://tronswap.org		Alternativa descentralizada com volume médio.
Uswap					https://uswap.me			Interface amigável, suporta staking e farming também.
SocialSwap				https://socialswap.io		DEX menor, mas com foco em comunidade e TRX.
Lemonade				https://lemonade.finance	Especializada em tokens novos. Muitos TRC-20 experimentais.
TPswap					https://tpswap.io			Plataforma recente, boas taxas.
VersaDEX				https://versadex.io			Novata, mas com APIs modernas e rápida indexação de pools.
*/