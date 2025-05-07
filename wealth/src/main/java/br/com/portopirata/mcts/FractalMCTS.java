package br.com.portopirata.mcts;

import static java.util.Objects.isNull;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

public class FractalMCTS<S,E>
{
	private TimeScale granularity;
	private long startTime;
	private long endTime;
	
	private TimeScale subMCTSsGranularity;
	
	private List<MCTSNode<S,E>> roots = new ArrayList<>();
	private MCTSNode<S,E> selected = null;
	
	private ActionGenerator actionGenerator = new ActionGenerator();
	private ConsequenceGenerator<S> consequenceGenerator = new ConsequenceGenerator<>();
	private EnvironmentGenarator<E> environmentGenerator = new EnvironmentGenarator<>();

	public void run()
	{
		select();
		expand();
		rollout();
	}
	
	private void select() 
	{
		if( roots.isEmpty() ) 
		{
			return;
		}

		var ranking = new NodeRanking( roots );
		this.selected = ranking.raffle();
	}


	

	private void expand() 
	{
		if( isNull(selected) ) {
			roots.add( selected = new MCTSNode<S, E>(actionGenerator, consequenceGenerator, environmentGenerator) );
			return;
		}
		
		selected.expand();
	}

	private void rollout() {
		selected.rollout();
	}

	@Getter
	public enum TimeScale {
		MINUTE_15(true), 
		HOUR_1(false),
		HOUR_4(false),
		HOUR_12(false),
		DAY_1(false),
		DAY_3(false),
		WEEK_1(false),
		WEEK_2(false),
		MONTH_1(false),
		MONTH_3(false),
		YEAR_1(false);
		
		private boolean leaf;
		
		private TimeScale( boolean leaf ) 
		{
			this.leaf = leaf;
		}
	}
}

