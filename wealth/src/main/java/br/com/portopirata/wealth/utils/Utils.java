package br.com.portopirata.wealth.utils;

import java.math.BigDecimal;
import java.util.Random;

public class Utils {

	private static Random random = new Random();
	
	public static BigDecimal random( BigDecimal base, double times ) 
	{
		return BigDecimal.valueOf( random.nextDouble( base.doubleValue() * times ) );
	}

	public static boolean isGreateThan( BigDecimal left, BigDecimal right ) {
		return left.compareTo(right) > 0;
	}

}
