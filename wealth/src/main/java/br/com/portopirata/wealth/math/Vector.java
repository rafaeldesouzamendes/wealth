package br.com.portopirata.wealth.math;

import static java.lang.reflect.Array.newInstance;
import static java.util.Arrays.fill;

import java.math.BigDecimal;
import java.util.function.Function;

public class Vector<T extends BigDecimal>
{
	private T[] vector;
	
	public Vector(T[] values) 
	{
		this.vector = values;
	}
	
	@SuppressWarnings("unchecked")
	public Vector( Class<T> clazz, int size )
	{
		this.vector = (T[]) newInstance(clazz, size);
	}

	@SuppressWarnings("unchecked")
	public Vector( Class<T> clazz, int size, T identity )
	{
		this.vector = (T[]) newInstance(clazz, size);
		fill( vector, identity );
	}
	
	public T get(int i) 
	{
		return vector[i];
	}
	
	public T set(Integer i, T value) {
		return vector[i] = value;
	}

	@SuppressWarnings("unchecked")
	public void addTo(int i, T value) 
	{
		this.vector[i] = (T) this.vector[i].add( value );
	}

	public void forEachSet( Function<Integer, T> foreach ) 
	{
		for( int i = 0; i < vector.length; ++i ) {
			vector[i] = foreach.apply(i);
		}
	}
	
	public BigDecimal sumAll() {
		return sumAll( v -> v );
	}

	public BigDecimal sumAll( Function<T, T> mapper ) 
	{
		var result = BigDecimal.ZERO;
		
		for( int i = 0; i < vector.length; ++i )
		{
			result = result.add( mapper.apply( vector[i] ) );
		}
		
		return result;
	}

	public BigDecimal multiplyAll() 
	{
		var result = BigDecimal.ONE;
		
		for( int i = 0; i < vector.length; ++i )
		{
			result = result.multiply( vector[ i ] );
		}
		
		return result;
	}
}
