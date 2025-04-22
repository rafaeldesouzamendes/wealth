package br.com.portopirata.wealth.math;

import static java.lang.reflect.Array.newInstance;
import static java.util.Arrays.fill;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
/**
 * i = row index
 * j = column index
 * @param <T extends BigDecimal>
 */
public class Matrix<T extends BigDecimal> 
{
	private Class<T> clazz;
	private T[][] matrix;
	
	@SuppressWarnings("unchecked")
	public Matrix(Class<T> clazz, int length, BigDecimal identity)
	{
		this.clazz = clazz;
		this.matrix = (T[][]) newInstance(clazz, length, length);
		for( T[] row : matrix ) 
		{
			fill(row, identity);
		}
	}
	
	/**
	 * 
	 * @param identity - The initial value for the accumulator.
	 * @param reducer - lambda function to reduce each element in the matrix to a single value.
	 * BigDecimal accumulator: the accumulator value for the reduction.
	 * Integer i: row index.
	 * Integer j: column index.
	 * T v: the cell's value to be reduced.
	 * T result: the reduced value.
	 * @return
	 */
	public BigDecimal reduceAll( T identity, QuadriFunction<BigDecimal,Integer, Integer, T,T> reducer ) {
		BigDecimal[] result = { identity };
		
		matrixIteration( (i,j,v) -> result[0] = reducer.apply(result[0], i, j, v) );
		
		return result[0];
	}
	
	private void matrixIteration( TriConsumer<Integer, Integer,T> consumer ) {
		for( int i = 0; i < matrix.length; ++i ) {
			for( int j = 0; j < matrix.length; ++j ) {
				consumer.accept(i, j, matrix[i][j]);
			}
		}
	}

	/**
	 * @param identity - The initial value for the accumulator.
	 * @param reducer lambda function to reduce each element in the matrix to a single value.
	 * BigDecimal accumulator: the accumulator value for the reduction.
	 * Integer i: row index.
	 * T v: the cell's value to be reduced.
	 * T result: the reduced value.
	 */
	public Vector<T> reduceRows( T identity, TriFunction<T,Integer,T,T> reducer )  
	{
		Vector<T> result = new Vector<>( clazz, matrix.length, identity );
		
		TriConsumer<Integer, Integer, T> consumer = (i,j,v) -> result.set(i, reducer.apply( result.get(i), i, v ) );
		matrixIteration( consumer );
		
		return result;
	}
	
	
	public void forEachSet( TriFunction<Integer, Integer, T, T> function )
	{
		for( int i = 0; i < matrix.length; ++i ) {
			for( int j = 0; j < matrix.length; ++j ) {
				this.matrix[i][j] = function.apply( i, j, this.matrix[i][j] );
			}
		}
	}

	public T get(int i, int j) {
		return this.matrix[i][j];
	}

	public interface TriConsumer<T1, T2, T3>
	{
		void accept(T1 t1, T2 t2, T3 t3);
	}
	
	public interface TriFunction<T1, T2, T3, R>
	{
		R apply( T1 t1, T2 t2, T3 t3 );
	}

	public interface QuadriFunction<T1, T2, T3, T4, R>
	{
		R apply( T1 t1, T2 t2, T3 t3, T4 t4 );
	}

}
