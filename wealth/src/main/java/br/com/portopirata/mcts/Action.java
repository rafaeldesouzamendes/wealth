package br.com.portopirata.mcts;

import java.util.HashMap;

import br.com.portopirata.wealth.portfolio.Asset;

public class Action //TODO refactory to remove wealth specific classes
{
	private long startTime;
	private long maxDuration;
	private ActionType type;
	private ActionData data;
	private Cost<Asset> cost;
	
	public enum ActionType {
		EXCHANGE("sourceAsset","quantity","targetAsset"),  
		SWAP("sourceAsset","quantity","targetAsset"), 
		TRANSFER("sourceAsset","sourceExchange","quantity","targetAsset","targetExchange"), 
		OPTION, 
		ARBITRATE("arbitragePlan");
		
		private String[] parameters;
		
		private ActionType( String ... parameters )  
		{
			this.parameters = parameters;
		}
		
		public String[] parameters() 
		{
			return this.parameters;
		}
		
	}
	
	public class ActionData extends HashMap<String,String>{
		private static final long serialVersionUID = -6633030763972392775L;
	}
	
	
}
