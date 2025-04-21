package br.com.portopirata.wealth.mcts;

import static br.com.portopirata.wealth.config.Constants.DMC;
import static br.com.portopirata.wealth.config.Constants.EXPLORATION_CONSTANT;
import static br.com.portopirata.wealth.config.Constants.TIMES_OF_BASE_VALUE;
import static br.com.portopirata.wealth.utils.Utils.isGreateThan;
import static br.com.portopirata.wealth.utils.Utils.random;
import static ch.obermuhlner.math.big.BigDecimalMath.log;
import static ch.obermuhlner.math.big.BigDecimalMath.sqrt;
import static java.util.Objects.isNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.portopirata.wealth.conjuncture.Conjuncture;
import br.com.portopirata.wealth.utils.Pair;
import lombok.Getter;

public class FractalMCTS 
{
	private TimeGranularity granularity;
	private int granularityHorizon;
	
	private TimeGranularity subMCTSsGranularity;
	private int subMCTSsGranularityHorizon;
	
	private List<MCTSNode> roots = new ArrayList<>();
	private MCTSNode selected = null;

	public void run()
	{
		select();
		expand();
		rollout();
		indexing();
		backpropagation();
	}
	
	private void select() 
	{
		if( roots.isEmpty() ) 
		{
			return;
		}

		var ranking = new ArrayList<Pair<MCTSNode, BigDecimal>>();
		for( MCTSNode node : roots )
		{
			BigDecimal newExplorationIncentive = sqrt( log( node.averagePredecessorsVisits(), DMC).divide( node.numberOfVisits() ) , DMC );
			BigDecimal UBCScore = node.qualityScore().add( EXPLORATION_CONSTANT.multiply( newExplorationIncentive ) );
			ranking.add( new Pair<>( node, UBCScore ) );
		}
		
		ranking.sort( (o1, o2) -> o2.getSecond().compareTo(o1.getSecond() ) );
		
		var winner = ranking.get( 0 );
		var winnerScore = winner.getSecond();
		var randomNumber = random( winnerScore, TIMES_OF_BASE_VALUE );
		
		if( isGreateThan( randomNumber, winnerScore ) ) 
		{
			this.selected = null;
		} else {
			this.selected = winner.getFirst();
		}
	}

	private void expand() 
	{
		if( isNull(selected) ) {
			roots.add( selected = new MCTSNode() );
		}
		
		selected.expand();
	}

	private void rollout() {
		// TODO Auto-generated method stub
		
	}

	private void indexing() {
		// TODO Auto-generated method stub
		
	}

	private void backpropagation() {
		// TODO Auto-generated method stub
	}
	
	@Getter
	public enum TimeGranularity {
		MINUTE(true), HOUR(false), DAY(false), WEEK(false), MONTH(false), YEAR(false);
		
		private boolean leaf;
		
		private TimeGranularity( boolean leaf ) 
		{
			this.leaf = leaf;
		}
	}

	@Getter
	public class MCTSNode
	{
		private List<MCTSNode> predecessors = new ArrayList<>();
		private List<MCTSNode> successors = new ArrayList<>();
		
		private Conjuncture conjuncture;
		private ScoreParameters nodeDeltas;
		private ScoreParameters nodeChainDeltas;
		private BigDecimal numberOfVisits = BigDecimal.ONE;

		public void expand() {
			// TODO Auto-generated method stub
			
		}
		
		public BigDecimal qualityScore() {
			BigDecimal squareOfNodeDeltas = nodeDeltas.logDistance().pow( 2 );
			BigDecimal squareOfChainDeltas = nodeChainDeltas.logDistance().pow( 2 );
			BigDecimal sumOfSquares = squareOfNodeDeltas.add(  squareOfChainDeltas );
			return sumOfSquares.sqrt( DMC );
		}
		
		public BigDecimal averagePredecessorsVisits() {
			// TODO Auto-generated method stub
			return null;
		}

		public BigDecimal numberOfVisits() {
			return this.numberOfVisits;
		}


		public class ScoreParameters
		{
			private BigDecimal deltaCDWIndex;
			private BigDecimal deltaCRWIndex;
			private BigDecimal deltaDiversificationIndex;
			private BigDecimal deltaLiquidityIndex;
			
			/**
			 * $$  $$
			 * @return
			 */
			public BigDecimal logDistance() 
			{
				return log(deltaCDWIndex, DMC).pow( 2 )
						.add( log( deltaCRWIndex, DMC ).pow( 2 ) )
						.add( log( deltaDiversificationIndex , DMC).pow( 2 ) )
						.add( log( deltaLiquidityIndex, DMC ).pow( 2 ) )
						.sqrt(DMC);
			}
		}
	}
}

