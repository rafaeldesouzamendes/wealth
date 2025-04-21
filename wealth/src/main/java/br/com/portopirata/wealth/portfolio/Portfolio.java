package br.com.portopirata.wealth.portfolio;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;

import br.com.portopirata.wealth.math.Matrix;
import br.com.portopirata.wealth.math.Vector;
import lombok.Getter;

@Getter
public class Portfolio {
	private final int numberOfAssets;
	private final Vector<BigDecimal> amounts;
	private Vector<BigDecimal> valueOfPortfolioInAsset;
	private Vector<BigDecimal> weightsOfAssetsInPortfolio;
	private BigDecimal geometricAverageOfValues;

	
	private final Map<Asset,Integer> assetIndex = new HashMap<>();
	
	public Portfolio( SortedMap<Asset, BigDecimal> portfolio ) 
	{
		this.numberOfAssets = portfolio.size();
		this.amounts = new Vector<>( portfolio.values().toArray( new BigDecimal[numberOfAssets]) );
		this.valueOfPortfolioInAsset = new Vector<>(BigDecimal.class, numberOfAssets);
		this.weightsOfAssetsInPortfolio = new Vector<>(BigDecimal.class, numberOfAssets);
		processPortfolio( portfolio );
	}
	
	private void processPortfolio(SortedMap<Asset, BigDecimal> portfolio) 
	{
		int i = 0;
		for( Asset asset : portfolio.keySet() )
		{
			indexAsset(i, asset);
			++i;
		}
	}
	private void indexAsset(int i, Asset asset) 
	{
		this.assetIndex.put( asset, i);
	}

	public void updateExchanges( Matrix<BigDecimal> exchanges ) 
	{
		this.valueOfPortfolioInAsset = exchanges.reduceRows( BigDecimal.ZERO, (rowAcc, i, v) -> rowAcc.add( amounts.get(i).multiply( v ) ) );
		this.weightsOfAssetsInPortfolio.forEachSet( i -> amounts.get( i ).divide( this.valueOfPortfolioInAsset.get( i ) ) );
		this.geometricAverageOfValues = valueOfPortfolioInAsset.multiplyAll();
	}

	public BigDecimal getWeight(Integer i) 
	{
		return this.weightsOfAssetsInPortfolio.get(i);
	}
}
