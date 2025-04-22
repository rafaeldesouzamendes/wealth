package br.com.portopirata.math;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.portopirata.wealth.math.Matrix;
import br.com.portopirata.wealth.math.Vector;

class MatrixTest {

    private Matrix<BigDecimal> matrix;
    private int length = 5;

    @BeforeEach
    void setUp() {
        matrix = new Matrix<>( BigDecimal.class, length, ONE );
    }

    @Test
    void testGet() 
    {
    	for( int i = 0; i < length; i++ ) {
			for( int j = 0; j < length; j++ ) {
				assertEquals( ONE, matrix.get(i, j) );
			}
		}
    }
    
    @Test
    void testReduceRows() {
        Vector<BigDecimal> result = matrix.reduceRows( ZERO, (acc, i, v) -> acc.add(v) );
        
        for( int i = 0; i < length; i++ ) {
        	assertEquals( valueOf( 5 ), result.get(i) );
        }
        
    }
    
    @Test
    void testReduceAll() {
        BigDecimal sum = matrix.reduceAll( ZERO, (acc, i, j, v) -> acc.add(v) );
        assertEquals( valueOf( 25 ), sum );
    }
    
    @Test
    void testForEachSet() {
        matrix.forEachSet( (i, j, v) -> valueOf( i ).add( valueOf(j) ).add( v ) );
        
        for( int i = 0; i < length; ++i ) {
			for( int j = 0; j < length; ++j ) {
				assertEquals( valueOf( i + j + 1 ), matrix.get(i, j) );
			}
		}
    }
}