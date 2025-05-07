package br.com.portopirata.mcts;

import java.math.BigDecimal;

public record Expectation (BigDecimal value, BigDecimal probability) implements Comparable<Expectation> {
	public BigDecimal e() { return value.multiply(probability); }
	
	@Override
	public int compareTo(Expectation o) {
		return this.e().compareTo(o.e());
	}
}
