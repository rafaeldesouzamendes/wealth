package br.com.portopirata.wealth.portfolio;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AssetPair {
	private final Asset left;
	private final Asset right;
	
	public boolean isLeft( Asset asset )
	{
		return left.equals( asset );
	}
	
	public boolean isRight( Asset asset )
	{
		return right.equals( asset );
	}

	@Override
	public int hashCode() {
		return Objects.hash(left, right);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AssetPair other = (AssetPair) obj;
		return Objects.equals(left, other.left) && Objects.equals(right, other.right);
	}

	@Override
	public String toString() {
		return "(left=" + left.getSymbol() + ", right=" + right.getSymbol() + ")";
	}
}
