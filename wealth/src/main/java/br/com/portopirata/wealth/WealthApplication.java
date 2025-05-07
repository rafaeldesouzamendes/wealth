package br.com.portopirata.wealth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.portopirata.mcts.FractalMCTS;
import br.com.portopirata.wealth.market.MarketScenario;
import br.com.portopirata.wealth.portfolio.Portfolio;

@SpringBootApplication
public class WealthApplication {

	public static void main(String[] args) {
		FractalMCTS<Portfolio,MarketScenario> mcts = new FractalMCTS<>();
		SpringApplication.run(WealthApplication.class, args);
	}

}
