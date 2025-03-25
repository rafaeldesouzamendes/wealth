package br.com.portopirata;

import org.springframework.boot.SpringApplication;

public class TestWealthApplication {

	public static void main(String[] args) {
		SpringApplication.from(WealthApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
