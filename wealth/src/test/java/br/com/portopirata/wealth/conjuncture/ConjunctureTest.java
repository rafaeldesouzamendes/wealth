package br.com.portopirata.wealth.conjuncture;

import static br.com.portopirata.wealth.config.Constants.DEFAULT_ASSETS;
import static br.com.portopirata.wealth.utils.AssetValuesGenerator.amountInUSD;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.SortedMap;
import java.util.TreeMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.portopirata.wealth.market.MarketScenario;
import br.com.portopirata.wealth.math.Matrix;
import br.com.portopirata.wealth.math.Vector;
import br.com.portopirata.wealth.portfolio.Asset;
import br.com.portopirata.wealth.portfolio.Portfolio;

class ConjunctureTest {

    private Portfolio portfolio;
    private MarketScenario marketScenario;
    private Conjuncture conjuncture;

    @BeforeEach
    void setUp() {
        portfolio = createMockPortfolio();
        marketScenario = createMarketScenario();
        conjuncture = new Conjuncture(portfolio, marketScenario);
    }
    
    private Portfolio createMockPortfolio() {
    	SortedMap<Asset, BigDecimal> portfolioMap = new TreeMap<>();
    	for ( Asset asset : DEFAULT_ASSETS.subList( 0, 10 ) )
    	{
			portfolioMap.put( asset, amountInUSD( asset, 1000d ) );
    	}
    	
		return new Portfolio( portfolioMap );
    }

    private MarketScenario createMarketScenario() {
		// TODO Auto-generated method stub
		return null;
	}


	@Test
    void testConjuncture() {
        assertNotNull(conjuncture.getPortfolio());
        assertNotNull(conjuncture.getMarketScenario());
        assertNotNull(conjuncture.getAnalysis());
    }

    @Test
    void testAnalyse() {
        conjuncture.getAnalysis().analyse();
        // Add assertions to verify the correctness of the analysis results
    }

    @Test
    void testComputeWealthIndexes() {
        // Set up mock data for portfolio and market scenario
        when(portfolio.getValueOfPortfolioInAsset()).thenReturn(new Vector<>(BigDecimal.class, 3, BigDecimal.ONE));
        when(portfolio.getGeometricAverageOfValues()).thenReturn(BigDecimal.valueOf(2));

        conjuncture.getAnalysis().computeWealthIndexes();
        // Add assertions to verify the correctness of the computed wealth indexes
    }

    @Test
    void testComputeDiversificationAndRedundancies() {
        // Set up mock data for market scenario
        Matrix<BigDecimal> correlations = mock(Matrix.class);
        when(marketScenario.getCorrelations()).thenReturn(correlations);

        conjuncture.getAnalysis().computeDiversificationAndRedundancies();
        // Add assertions to verify the correctness of the computed diversification and redundancies indexes
    }

    @Test
    void testComputeLiquidityIndex() {
        // Set up mock data for market scenario
        Matrix<BigDecimal> liquidities = mock(Matrix.class);
        when(marketScenario.getDepths(anyInt(), anyInt())).thenReturn(BigDecimal.ONE);
        when(marketScenario.getSlippages(anyInt(), anyInt())).thenReturn(BigDecimal.valueOf(0.9));
        when(marketScenario.getEntropy(anyInt(), anyInt())).thenReturn(BigDecimal.valueOf(0.5));
        when(portfolio.getNumberOfAssets()).thenReturn(3);
        when(portfolio.getWeight(anyInt())).thenReturn(BigDecimal.valueOf(0.33));

        conjuncture.getAnalysis().computeLiquidityIndex();
        // Add assertions to verify the correctness of the computed liquidity index
    }

    // Add more test methods to cover all paths and edge cases in the Conjuncture class
}