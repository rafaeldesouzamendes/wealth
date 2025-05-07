package br.com.portopirata.mcts;

import static br.com.portopirata.wealth.config.Constants.DMC;
import static java.util.Objects.isNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class MCTSNode<S,E>
{
	private ActionGenerator actionGenerator;
	private ConsequenceGenerator<S> consequenceGenerator;
	private EnvironmentGenarator<E> environmentGenarator;

	
	private Environment<E> environment;
	private State<S> state;
	private Action action;
	
	private List<State<S>> consequences;
	private List<Environment<E>> possibleEnvironments;

	
	private ScoreParameters nodeDeltas;
	private ScoreParameters nodeChainDeltas;

	private BigDecimal numberOfVisits = BigDecimal.ONE;
	private List<MCTSNode<S,E>> predecessors = new ArrayList<>();
	private List<MCTSNode<S,E>> successors = new ArrayList<>();
	
	private boolean leaf = false;
	
	MCTSNode(
			ActionGenerator actionGenerator, 
			ConsequenceGenerator<S> consequenceGenerator, 
			EnvironmentGenarator<E> environmentGenarator) 
	{
		this.actionGenerator = actionGenerator;
		this.consequenceGenerator = consequenceGenerator;
		this.environmentGenarator = environmentGenarator;
	}

	public void expand() {
		if( isLeaf() ) {
			return;
		}
		
	}
	
	private boolean isLeaf() {
		return leaf ;
	}

	public void rollout() {
		if(isNull(this.action)) {
			this.action = actionGenerator.generate( state );
			checkIsLeaf();
		}
		
		this.consequences.add( consequenceGenerator.generate( action, state, consequences ) );
		this.possibleEnvironments.add( environmentGenarator.generate(  ) );
		
		backpropagate();
	}

	private void checkIsLeaf() {
		if( isHopLimit() 
			|| isTimeslotLimit()
			|| isConjunctureLimit()
			|| isConstraintsLimit() ) {
			this.leaf = true;
        }
	}


	private void updateScores() {
		
	}

	private void backpropagate() {
		if( this.isLeaf() ) {
			updateScores();
		}

		updateChainScores();
		for( var predecessor : predecessors ) {
			predecessor.backpropagate();;
		}
	}
	
	private boolean isHopLimit() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private boolean isTimeslotLimit() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private boolean isConjunctureLimit() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private boolean isConstraintsLimit() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private void updateChainScores() {
		// TODO Auto-generated method stub
	}
	
	public BigDecimal qualityScore() {
		BigDecimal squareOfNodeDeltas = nodeDeltas.getScore().pow( 2 );
		BigDecimal squareOfChainDeltas = nodeChainDeltas.getScore().pow( 2 );
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
}