package br.com.portopirata.wealth.market.exchange.client;

import java.math.BigDecimal;

public interface IExchangeClient 
{
	BigDecimal getPrice(String symbol);
}
