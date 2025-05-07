package br.com.portopirata.wealth.mcts;

import static br.com.portopirata.wealth.config.Constants.DMC;
import static ch.obermuhlner.math.big.BigDecimalMath.log;

import java.math.BigDecimal;

import br.com.portopirata.mcts.Expectation;
import br.com.portopirata.mcts.ScoreParameters;

public class WealthScoreParameters implements ScoreParameters
{
	private Expectation deltaCDWIndex;
	private Expectation deltaCRWIndex;
	private Expectation deltaDiversificationIndex;
	private Expectation deltaLiquidityIndex;
	
	/**
	 * $$ \sqrt{ ln(\Delta CRWIndex)^2 + ln(\Delta DiversificationIndex)^2 + ln(\Delta LiquidityIndex)^2 } $$
	 * @return
	 */
	@Override
	public BigDecimal getScore() 
	{
		return log(deltaCDWIndex.e(), DMC).pow( 2 )
				.add( log( deltaCRWIndex.e(), DMC ).pow( 2 ) )
				.add( log( deltaDiversificationIndex.e() , DMC).pow( 2 ) )
				.add( log( deltaLiquidityIndex.e(), DMC ).pow( 2 ) )
				.sqrt(DMC);
	}
}