package cs2321;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import cs2321.TaskScheduling;

public class TaskSchedulingTest {

	int[][] tasks = new int[5][2];

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testNumOfMachinesOverlappingTasks() {
		tasks[0][0] = 0;
		tasks[1][0] = 1;
		tasks[2][0] = 2;
		tasks[3][0] = 3;
		tasks[4][0] = 4;

		tasks[0][1] = 3;
		tasks[1][1] = 4;
		tasks[2][1] = 5;
		tasks[3][1] = 5;
		tasks[4][1] = 7;

		int expectedReturn = 3;
		int result = TaskScheduling.NumOfMachines(tasks);
		assertEquals(expectedReturn, result);
	}

	@Test
	public void testNumOfMachinesOverlappingTasksLong() {
		tasks[0][0] = 0;
		tasks[1][0] = 2;
		tasks[2][0] = 3;
		tasks[3][0] = 4;
		tasks[4][0] = 5;

		tasks[0][1] = 3;
		tasks[1][1] = 10;
		tasks[2][1] = 5;
		tasks[3][1] = 7;
		tasks[4][1] = 12;

		int expectedReturn = 3;
		int result = TaskScheduling.NumOfMachines(tasks);
		assertEquals(expectedReturn, result);
	}

	@Test
	public void testNumOfMachinesAllNewMachines() {
		tasks[0][0] = 0;
		tasks[1][0] = 0;
		tasks[2][0] = 0;
		tasks[3][0] = 0;
		tasks[4][0] = 0;

		tasks[0][1] = 5;
		tasks[1][1] = 5;
		tasks[2][1] = 5;
		tasks[3][1] = 5;
		tasks[4][1] = 5;

		int expectedReturn = 5;
		int result = TaskScheduling.NumOfMachines(tasks);
		assertEquals(expectedReturn, result);
	}
}