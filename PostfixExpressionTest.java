package cs2321;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PostfixExpressionTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testEvaluate1() {
		String exp = "2 2 +";
		int expected = 4;
		assertEquals(expected, PostfixExpression.evaluate(exp));
	}

	@Test
	public void testEvaluate2() {
		String exp = "2 2 -";
		int expected = 0;
		assertEquals(expected, PostfixExpression.evaluate(exp));
	}

	@Test
	public void testEvaluate3() {
		String exp = "2 2 *";
		int expected = 4;
		assertEquals(expected, PostfixExpression.evaluate(exp));
	}

	@Test
	public void testEvaluate4() {
		String exp = "2 2 /";
		int expected = 1;
		assertEquals(expected, PostfixExpression.evaluate(exp));
	}

	@Test
	public void testEvaluate5() {
		String exp = "2 2 2 * +";
		int expected = 6;
		assertEquals(expected, PostfixExpression.evaluate(exp));
	}
	@Test
	public void testEvaluatemmmm() {
		String exp = "2 2 * 4 /";
		int expected = 1;
		assertEquals(expected, PostfixExpression.evaluate(exp));
	}
	
	@Test
	public void testEvaluate11() {
		String exp = "3 2 * 2 /";
		int expected = 3;
		assertEquals(expected, PostfixExpression.evaluate(exp));
	}
	@Test
	public void testEvaluate6() {
		String exp = "2 3 * 2 /";
		int expected = 3;
		assertEquals(expected, PostfixExpression.evaluate(exp));
	}

	@Test
	public void testEvaluate7() {
		String exp = "2 2 / 0 *";
		int expected = 0;
		assertEquals(expected, PostfixExpression.evaluate(exp));
	}

	@Test
	public void testEvaluate8() {
		String exp = "2 10 * 5 6 3 / 2 - + +";
		int expected = 25;
		assertEquals(expected, PostfixExpression.evaluate(exp));
	}
}