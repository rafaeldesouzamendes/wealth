package br.com.portopirata.wealth.market.exchange.adapter;

import java.math.BigDecimal;

public interface IExchangeAdapter 
{
	BigDecimal getPrice(String symbol);
}
