package br.com.portopirata;

import org.springframework.boot.SpringApplication;

import br.com.portopirata.wealth.WealthApplication;

public class TestWealthApplication {

	public static void main(String[] args) {
		SpringApplication.from(WealthApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
