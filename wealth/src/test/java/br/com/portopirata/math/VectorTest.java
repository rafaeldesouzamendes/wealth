package br.com.portopirata.math;

import static java.math.BigDecimal.valueOf;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.portopirata.wealth.math.Vector;

public class VectorTest {
	private Vector<BigDecimal> vector;

	@BeforeEach
	void setup() {
		BigDecimal[] values = { BigDecimal.ONE, valueOf(2), valueOf(3), valueOf(4), valueOf(5) };
        vector = new Vector<>(values);
	}
	
	@Test
    void testGet() {
        assertEquals(BigDecimal.ONE, vector.get(0));
        assertEquals(valueOf(2), vector.get(1));
        assertEquals(valueOf(3), vector.get(2));
        assertEquals(valueOf(4), vector.get(3));
        assertEquals(valueOf(5), vector.get(4));
    }

    @Test
    void testSet() {
        vector.set(0, valueOf(10));
        assertEquals(valueOf(10), vector.get(0));
    }

    @Test
    void testAddTo() {
        vector.addTo(0, valueOf(5));
        assertEquals(valueOf(6), vector.get(0));
    }

    @Test
    void testForEachSet() {
    	
        final Function<Integer,BigDecimal> foreach = i -> valueOf(i + 10);
		vector.forEachSet(foreach);
        assertEquals(valueOf(10), vector.get(0));
        assertEquals(valueOf(11), vector.get(1));
        assertEquals(valueOf(12), vector.get(2));
        assertEquals(valueOf(13), vector.get(3));
        assertEquals(valueOf(14), vector.get(4));
    }

    @Test
    void testSumAll() {
        assertEquals(valueOf(15), vector.sumAll());
    }

    @Test
    void testSumAllWithMapper() {
        Function<BigDecimal, BigDecimal> mapper = v -> v.multiply(valueOf(2));
        assertEquals(valueOf(30), vector.sumAll(mapper));
    }

    @Test
    void testMultiplyAll() {
        assertEquals(valueOf(120), vector.multiplyAll());
    }
}
