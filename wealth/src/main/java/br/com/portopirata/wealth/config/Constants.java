package br.com.portopirata.wealth.config;

import java.math.BigDecimal;
import java.math.MathContext;

public class Constants 
{
	public static final MathContext DMC = new MathContext( 20 );
	public static final BigDecimal AVOID_ZERO = BigDecimal.valueOf( 0.00000000000000000000000000000001 );
	public static final BigDecimal EXPLORATION_CONSTANT = BigDecimal.valueOf( 1.41 );
	public static final double TIMES_OF_BASE_VALUE = 1.5d;


}
