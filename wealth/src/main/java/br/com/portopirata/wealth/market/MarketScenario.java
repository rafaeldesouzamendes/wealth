package br.com.portopirata.wealth.market;

import java.math.BigDecimal;

import br.com.portopirata.wealth.math.Matrix;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MarketScenario 
{
	private final Matrix<BigDecimal> exchanges;
	private final Matrix<BigDecimal> correlations;
	private final Matrix<BigDecimal> slippages;
	private final Matrix<BigDecimal> depths;
	private final Matrix<BigDecimal> distributionsEntropies;
	
	public BigDecimal getDepths(int i, int j) 
	{
		return depths.get(i, j);
	}

	public BigDecimal getSlippages(int i, int j) 
	{
		return slippages.get(i, j);
	}

	public BigDecimal getEntropy(int i, int j) {
		return distributionsEntropies.get(i, j);
	}
}
