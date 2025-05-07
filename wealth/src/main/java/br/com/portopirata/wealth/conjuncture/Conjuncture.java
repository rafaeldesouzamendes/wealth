package br.com.portopirata.wealth.conjuncture;

import static br.com.portopirata.wealth.config.Constants.AVOID_ZERO;
import static br.com.portopirata.wealth.config.Constants.DMC;
import static ch.obermuhlner.math.big.BigDecimalMath.log;

import java.math.BigDecimal;
import java.util.function.Function;

import br.com.portopirata.wealth.market.MarketScenario;
import br.com.portopirata.wealth.math.Matrix;
import br.com.portopirata.wealth.math.Vector;
import br.com.portopirata.wealth.portfolio.Portfolio;
import ch.obermuhlner.math.big.BigDecimalMath;
import lombok.Getter;

@Getter
public class Conjuncture {
	private Portfolio portfolio;
	private MarketScenario marketScenario;
	private ConjunctureAnalysis analysis;
	
	public Conjuncture(Portfolio portfolio, MarketScenario marketScenario) 
	{
		this.portfolio = portfolio;
		this.marketScenario = marketScenario;
		this.analysis = new ConjunctureAnalysis();
	}
	
	@Getter
	public class ConjunctureAnalysis
	{
		private BigDecimal cambialDirectWealthIndex = BigDecimal.ZERO;
		private BigDecimal cambialRelactiveWealthIndex = BigDecimal.ZERO;
		private BigDecimal diversificationIndex = BigDecimal.ZERO;
		private Vector<BigDecimal> redundanciesIndexes = new Vector<>(BigDecimal.class, portfolio.getNumberOfAssets(), BigDecimal.ZERO);
		private BigDecimal liquidityIndex = BigDecimal.ZERO;
		
		public ConjunctureAnalysis() 
		{
			Matrix<BigDecimal> exchanges = marketScenario.getExchanges();
			portfolio.updateExchanges( exchanges );
			analyse();
		}

		public void analyse() 
		{
			computeWealthIndexes();
			computeDiversificationAndRedundancies();
			computeLiquidityIndex();
		}

		void computeWealthIndexes() 
		{
			this.cambialDirectWealthIndex = portfolio.getValueOfPortfolioInAsset().sumAll( vi -> vi.pow( 2 ) );
			
			Function<BigDecimal,BigDecimal> squareNeperialLogByGeometricAverage = vi -> 
			{ 
				var valueByAverage = vi.divide( portfolio.getGeometricAverageOfValues() );
				return log( valueByAverage, DMC ).pow(2);
			};
			
			this.cambialRelactiveWealthIndex = portfolio.getValueOfPortfolioInAsset().sumAll( squareNeperialLogByGeometricAverage );
		}
		
		void computeDiversificationAndRedundancies() 
		{
			Matrix<BigDecimal> correlations = marketScenario.getCorrelations();
			computeDiversificationIndex(correlations);
			computeRedundanciesIndexes(correlations);
		}
		
		void computeLiquidityIndex() 
		{
			int lenght = portfolio.getNumberOfAssets();
			Matrix<BigDecimal> liquidities = new Matrix<BigDecimal>(BigDecimal.class, lenght, BigDecimal.ZERO);
			liquidities.forEachSet( (i, j, v) -> liquidityIndexer(i, j) );
			BigDecimal portfolioLiquidity = liquidities.reduceAll( BigDecimal.ZERO, (cellAcc,i,j,v) -> cellAcc.add( liquidityReductor( i, j, v ) ) );
			this.liquidityIndex = BigDecimalMath.exp(portfolioLiquidity, DMC); 
		}

		private BigDecimal liquidityReductor(Integer i, Integer j, BigDecimal v) {
			BigDecimal log = log( v.add( AVOID_ZERO ), DMC );
			return weight(i)
					.multiply( weight(j) )
					.multiply( log );
		}

		private void computeDiversificationIndex( Matrix<BigDecimal> correlations ) 
		{
			BigDecimal diversification = correlations.reduceAll( BigDecimal.ZERO, (cellAcc,i,j,v) -> cellAcc.add( diversification( i, j, v ) ) );
			this.diversificationIndex = BigDecimal.ONE
										.subtract( diversification )
										.divide( BigDecimal.TWO );
		}
		
		/**
		 * Formula:
		 * 
		 * $$ C_i = \sum_{j \neq i} w_j \rho_{ij} $$
		 * 
		 * @param correlations
		 */
		private void computeRedundanciesIndexes( Matrix<BigDecimal> correlations ) 
		{
			this.redundanciesIndexes = correlations.reduceRows( BigDecimal.ZERO, (rowAcc,i, v) -> rowAcc.add( weight(i).multiply( v ) ) );
		}
		
		private BigDecimal liquidityIndexer(Integer i, Integer j) {
			BigDecimal depth = depth( i, j ).divide( weight(i) );
			BigDecimal slippage = BigDecimal.ONE.subtract( slippage( i, j ) );
			BigDecimal entropy = entropy( i, j );
			return depth
					.multiply( slippage )
					.multiply( entropy );
		}
		
		private BigDecimal diversification( int i, int j, BigDecimal correlation ) 
		{
			return weight(i).multiply( weight(j) ).multiply( correlation );
		}
		
		private BigDecimal weight( int i ) {
			return portfolio.getWeight( i );
		}
		
		private BigDecimal depth( int i, int j ) {
			return marketScenario.getDepths( i , j );
		}
		
        
        private BigDecimal slippage( int i, int j ) {
            return marketScenario.getSlippages( i, j );
        }
        
        private BigDecimal entropy( int i, int j ) {
            return marketScenario.getEntropy( i, j );
        }
	}
}
