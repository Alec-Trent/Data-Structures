package cs2321;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import cs2321.FractionalKnapsack;
public class FractionalKnapsackTest {

	int[][] items = new int[5][2];
	
	@Before
	public void setUp() throws Exception {
		items[0][0] = 4;
		items[1][0] = 8;
		items[2][0] = 2;
		items[3][0] = 6;
		items[4][0] = 1;

		items[0][1] = 12;
		items[1][1] = 32;
		items[2][1] = 40;
		items[3][1] = 30;
		items[4][1] = 50;
	}

	@Test
	public void testMaxValueNoWeight() {
		int knapsackWeight = 0;
		double expectedReturn = 0;
		double result = FractionalKnapsack.MaximumValue(items, knapsackWeight);
		assertEquals(expectedReturn, result, 0);
	}

	@Test
	public void testMaxValueFullAmounts() {
		int knapsackWeight = 3;
		double expectedReturn = 90;
		double result = FractionalKnapsack.MaximumValue(items, knapsackWeight);
		assertEquals(expectedReturn, result, 0);
	}

	@Test
	public void testMaxValueFractionalAmounts() {
		int knapsackWeight = 6;
		double expectedReturn = 105;
		double result = FractionalKnapsack.MaximumValue(items, knapsackWeight);
		assertEquals(expectedReturn, result, 0);
	}
	
	@Test
	public void testMaxValueLargeKnapsack() {
		int knapsackWeight = 25;
		double expectedReturn = 164;
		double result = FractionalKnapsack.MaximumValue(items, knapsackWeight);
		assertEquals(expectedReturn, result, 0);
	}

	@Test
	public void testMaxValueLargeKnapsackSmallInput() {
		int[][] testitems = new int[1][2];
		testitems[0][0] = 4;
		testitems[0][1] = 10;
		
		int knapsackWeight = 3;
		double expectedReturn = 7.5;
		double result = FractionalKnapsack.MaximumValue(testitems, knapsackWeight);
		assertEquals(expectedReturn, result, 0);
	}
}