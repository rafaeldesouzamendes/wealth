package br.com.portopirata.mcts;

import static br.com.portopirata.wealth.config.Constants.DMC;
import static br.com.portopirata.wealth.config.Constants.EXPLORATION_CONSTANT;
import static br.com.portopirata.wealth.config.Constants.TIMES_OF_BASE_VALUE;
import static br.com.portopirata.wealth.utils.Utils.isGreateThan;
import static br.com.portopirata.wealth.utils.Utils.random;
import static ch.obermuhlner.math.big.BigDecimalMath.log;
import static ch.obermuhlner.math.big.BigDecimalMath.sqrt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import br.com.portopirata.wealth.utils.Pair;

public class NodeRanking<S, E> extends ArrayList<Pair<MCTSNode<S,E>, BigDecimal>> {
	private static final long serialVersionUID = 2792958752108449691L;

	public NodeRanking(List<MCTSNode<S,E>> nodes) {
		rank(nodes);
	}

	private void rank(List<MCTSNode<S,E>> nodes) {
		for( MCTSNode<S,E> node : nodes )
		{
			BigDecimal newExplorationIncentive = sqrt( log( node.averagePredecessorsVisits(), DMC).divide( node.numberOfVisits() ) , DMC );
			BigDecimal UBCScore = node.qualityScore().add( EXPLORATION_CONSTANT.multiply( newExplorationIncentive ) );
			this.add( new Pair<>( node, UBCScore ) );
		}
		
		this.sort( (o1, o2) -> o2.getSecond().compareTo(o1.getSecond() ) );
	}

	public MCTSNode<S,E> raffle() {
		var winner = this.get( 0 );
		var winnerScore = winner.getSecond();
		var randomNumber = random( winnerScore, TIMES_OF_BASE_VALUE );
		
		MCTSNode<S,E> result = null;
		if( isGreateThan( winnerScore, randomNumber ) ) 
		{
			result = winner.getFirst();
		}
		return result;
	}
}