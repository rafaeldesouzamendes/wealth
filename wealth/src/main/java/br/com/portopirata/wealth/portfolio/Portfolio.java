package br.com.portopirata.wealth.portfolio;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static java.util.Objects.isNull;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.function.BiConsumer;

import lombok.Getter;

@Getter
public class Portfolio {
	private final List<Asset> assets;

	private final int numberOfAssets;
	private final BigDecimal[][] exchanges;
	private final BigDecimal[] amounts;
	private final BigDecimal[] valueOfPortfolioInAsset;
	private final BigDecimal[] weightsOfAssetsInPortfolio;

	
	private final Map<Asset,Integer> assetIndex = new HashMap<>();
	
	public Portfolio( SortedMap<Asset, BigDecimal> portfolio, BigDecimal[][] initialExchanges ) 
	{
		this.assets = portfolio.keySet().stream().toList();
		this.numberOfAssets = assets.size();
		this.exchanges = new BigDecimal[ numberOfAssets ][ numberOfAssets ];
		this.amounts = new BigDecimal[ numberOfAssets ];
		this.valueOfPortfolioInAsset = new BigDecimal[ numberOfAssets ];
		this.weightsOfAssetsInPortfolio = new BigDecimal[ numberOfAssets ];
		indexAssets();
		addAmounts( portfolio );
		updateInitialExchanges( initialExchanges );
	}
	

	private void indexAssets() 
	{
		for( int i = 0; i < this.numberOfAssets; ++i )
			assetIndex.put( this.assets.get(i), i );
	}
	
	private void addAmounts( Map<Asset, BigDecimal> portfolio ) 
	{
		for( Asset a : portfolio.keySet() )
		{
			int i = assetIndex.get( a );
			this.amounts[ i ] = portfolio.get( a );
		}
	}

	private void updateInitialExchanges(BigDecimal[][] initialExchanges) {
		computeMatrix( (i,j) -> this.exchanges[i][j] = initialExchanges[i][j] );
	}

	private void computeMatrix( BiConsumer<Integer, Integer> consumer )
	{
		for( int i = 0; i < this.numberOfAssets; ++i )
			for( int j = 0; j < this.numberOfAssets; ++ j )
				consumer.accept(i, j);
	}
	
	
	public void updateExchangeRate(Asset left, Asset right, BigDecimal rate)
	{
		checkRate( rate );
		checkAssets( left, right);
		
		int leftI = assetIndex.get(left);
		int rightI = assetIndex.get(right);

		if( left.equals(right) )
		{
			this.exchanges[ leftI ][ rightI ] = this.exchanges[ rightI ][ leftI ] = ONE;
			return;
		}
		
		this.exchanges[ leftI ][ rightI ] = rate;
		this.exchanges[ rightI ][ leftI ] = ONE.divide(rate);
		
		computeValueOfPortfolioConverted( left );
		computeValueOfPortfolioConverted( right );
		computeWeightOfAsset( left );
		computeWeightOfAsset( right );
	}

	private void checkAssets(Asset left, Asset right) {
		if( isNull( left ) )
			throw new IllegalArgumentException( "The left asset can't be null!" );
		if( isNull( right ) )
			throw new IllegalArgumentException( "The right asset can't be null!" );
	}

	private void checkRate(BigDecimal rate) {
		if(ZERO.equals(rate));
			throw new IllegalArgumentException("The rate can't be zero!");
	}
	
	private void computeValueOfPortfolioConverted( Asset asset ) 
	{
		BigDecimal totalValueInI = ZERO;
		int i = assetIndex.get( asset );
		BigDecimal[] exchengesOfAssetI = this.exchanges[i];
		
		for( int j = 0; j < this.numberOfAssets; ++j ) 
		{
			BigDecimal amountOfAssetJInAssetI = exchengesOfAssetI[j].multiply( this.amounts[j] );
			totalValueInI = totalValueInI.add( amountOfAssetJInAssetI );
		}
		
		this.valueOfPortfolioInAsset[i] = totalValueInI;
	}
	
	private void computeWeightOfAsset( Asset asset ) 
	{
		int i = assetIndex.get( asset );
		this.weightsOfAssetsInPortfolio[i] = this.amounts[i].divide( this.valueOfPortfolioInAsset[i] );
	}
}
