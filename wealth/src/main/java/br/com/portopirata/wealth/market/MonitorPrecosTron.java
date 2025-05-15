package br.com.portopirata.wealth.market;

import java.math.BigDecimal;
import java.net.http.HttpClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.portopirata.wealth.market.exchange.adapter.BinanceExchangeAdapter;
import br.com.portopirata.wealth.market.exchange.adapter.KrakenExchangeAdapter;
import br.com.portopirata.wealth.market.exchange.adapter.SunSwapExchangeAdapter;

public class MonitorPrecosTron {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final HttpClient httpClient = HttpClient.newHttpClient();


    public static void main(String[] args) throws Exception 
    {
    	BinanceExchangeAdapter binanceAdapter = new BinanceExchangeAdapter(objectMapper, httpClient);
    	KrakenExchangeAdapter krakenAdapter = new KrakenExchangeAdapter(objectMapper, httpClient);
    	SunSwapExchangeAdapter sunSwapAdapter = new SunSwapExchangeAdapter(objectMapper, httpClient);

        
    	// Exemplo de pares
        String[] binancePairs = {"BTCUSDT", "TRXUSDT"};
        String[] krakenPairs = {"XBTUSDT", "TRXUSD"};
        String[] sunSwapPairs = {"BTC/USDT", "TRX/USDT"};

        for (String pair : binancePairs) {
            BigDecimal binancePrice = binanceAdapter.getPrice(pair);
            System.out.println("[Binance] " + pair + ": US$ " + binancePrice);
        }
        
        for (String pair : krakenPairs) {
        	BigDecimal krakenSwapPrice = krakenAdapter.getPrice(pair);
        	System.out.println("[Kraken] " + pair + ": US$ " + krakenSwapPrice);
        }
        
        for (String pair : sunSwapPairs) {
        	BigDecimal sunSwapPrice = sunSwapAdapter.getPrice(pair);
        	System.out.println("[SunSwap] " + pair + ": US$ " + sunSwapPrice);
        }

    }
}