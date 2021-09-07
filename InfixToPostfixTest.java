package cs2321;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class InfixToPostfixTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testConvert1() {
		String infixExp = "2 - 3 * 4";
		String expected = "2 3 4 * -";
		assertEquals(expected, InfixToPostfix.convert(infixExp));
	}

	@Test
	public void testConvert2() {
		String infixExp = "( 1 + 2 ) * 7";
		String expected = "1 2 + 7 *";
		assertEquals(expected, InfixToPostfix.convert(infixExp));
	}

	@Test
	public void testConvert3() {
		String infixExp = "2 + 2";
		String expected = "2 2 +";
		assertEquals(expected, InfixToPostfix.convert(infixExp));
	}

	@Test
	public void testConvert4() {
		String infixExp = "2 * 2";
		String expected = "2 2 *";
		assertEquals(expected, InfixToPostfix.convert(infixExp));
	}

	@Test
	public void testConvert5() {
		String infixExp = "2 / 2";
		String expected = "2 2 /";
		assertEquals(expected, InfixToPostfix.convert(infixExp));
	}

	@Test
	public void testConvert6() {
		String infixExp = "( 2 * 2 ) / 0";
		String expected = "2 2 * 0 /";
		assertEquals(expected, InfixToPostfix.convert(infixExp));
	}

	@Test
	public void testConvert7() {
		String infixExp = "( 2 * 0 ) - ( 2 * 2 )";
		String expected = "2 0 * 2 2 * -";
		assertEquals(expected, InfixToPostfix.convert(infixExp));
	}

	@Test
	public void testConvert8() {
		String infixExp = "( 2 * 0 ) * ( 2 * 2 )";
		String expected = "2 0 * 2 2 * *";
		assertEquals(expected, InfixToPostfix.convert(infixExp));
	}
}