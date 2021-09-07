package cs2321;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import java.util.Iterator;

public class JosephusTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testOrder() {
		String[] eliminatedOrder = {"A", "B", "C", "D", "E", "F"};
		Josephus josephus = new Josephus();
		DoublyLinkedList<String> newJosephus = josephus.order(eliminatedOrder, 3);
		Iterator<String> iterator = newJosephus.iterator();
		String answer = "CFDBEA";
		String values = "";
		while (iterator.hasNext()) {
			values += iterator.next();
		}
		assertEquals(values, answer);
	} 

	@Test
	public void testOrder2() {
		String[] eliminatedOrder = {"A", "B", "C", "D", "E", "F", "G", "H"};
		Josephus josephus = new Josephus();
		DoublyLinkedList<String> newJosephus = josephus.order(eliminatedOrder, 7);
		Iterator<String> iterator = newJosephus.iterator();
		String answer = "GFHBEACD";
		String values = "";
		while (iterator.hasNext()) {
			values += iterator.next();
		}
		assertEquals(values, answer);
	}

	@Test
	public void testOrder3() {
		String[] eliminatedOrder = {"A", "B", "C", "D", "E", "F", "G", "H", "I"};
		Josephus josephus = new Josephus();
		DoublyLinkedList<String> newJosephus = josephus.order(eliminatedOrder, 22);
		Iterator<String> iterator = newJosephus.iterator();
		String answer = "DABGIEFCH";
		String values = "";
		while (iterator.hasNext()) {
			values += iterator.next();
		}
		assertEquals(values, answer);
	}

	@Test
	public void testOrder4() {
		String[] eliminatedOrder = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
		Josephus josephus = new Josephus();
		DoublyLinkedList<String> newJosephus = josephus.order(eliminatedOrder, 100);
		Iterator<String> iterator = newJosephus.iterator();
		String answer = "JAEGCBIDHF";
		String values = "";
		while (iterator.hasNext()) {
			values += iterator.next();
		}
		assertEquals(values, answer);
	}
}