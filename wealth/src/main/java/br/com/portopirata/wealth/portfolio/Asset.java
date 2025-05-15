package br.com.portopirata.wealth.portfolio;

import java.util.Objects;

import br.com.portopirata.wealth.config.AssetStandard;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Asset implements Comparable<Asset> {
	private final String name;
	private final String symbol;
	private final AssetStandard standard;
	
	public Asset( Asset asset)  
	{
		this.name = asset.name;
		this.symbol = asset.symbol;
		this.standard = asset.standard;
	}
	
	public Asset( String name, String symbol) 
	{
		this.name = name;
		this.symbol = symbol;
		this.standard = AssetStandard.DEFAULT;
	}

	@Override
	public int compareTo(Asset asset) {
		return this.symbol.compareTo(asset.symbol);
	}

	@Override
	public int hashCode() {
		return Objects.hash(symbol);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Asset other = (Asset) obj;
		return Objects.equals(name, other.name) && Objects.equals(symbol, other.symbol);
	}
}
