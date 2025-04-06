package br.com.portopirata.wealth.portfolio;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Asset implements Comparable<Asset> {
	private final String name;
	private final String symbol;
	
	public Asset( Asset asset)  
	{
		this.name = asset.name;
		this.symbol = asset.symbol;
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Asset other = (Asset) obj;
		return Objects.equals(name, other.name) && Objects.equals(symbol, other.symbol);
	}
}
