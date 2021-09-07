package cs2321;

import cs2321.HeapPQ.PQEntry;
import net.datastructures.Entry;
import net.datastructures.PriorityQueue;
import cs2321.ArrayList;

/**
 * @author: Alec Trent
 *
 */
public class TaskScheduling {
	/**
	 * Goal: Perform all the tasks using a minimum number of machines. 
	 * 
	 *       
	 * @param tasks tasks[i][0] is start time for task i
	 *              tasks[i][1] is end time for task i
	 * @return The minimum number or machines
	 */
	@TimeComplexity("O(n^2)")
	public static int NumOfMachines(int[][] tasks) {
		/*
		 * TCJ
		 * for loop iterates n times, and insertion is log n
		 * while loop iterates n times
		 * for loop iterates n times
		 */
		PriorityQueue<Integer,Integer> heap = new HeapPQ<>();
		ArrayList<Integer> machines = new ArrayList<>();
		for(int i=0; i < tasks.length; i++) 	// loops n times
			heap.insert(tasks[i][0], tasks[i][1]); 	// key = start time and value = end time
		while(!heap.isEmpty()) {
			Entry minValue = heap.removeMin();	//remove task with smallest start time
			boolean found = false; 	//if not found create a machine
			for(int j=0; j < machines.size(); j++) {
				int lastTaskEndTime = machines.get(j);
				int newTaskStartTime = (int) minValue.getKey();
				if(lastTaskEndTime <= newTaskStartTime) {
					found = true;	// changes boolean statement
					int newTaskEndTime = (int) minValue.getValue();
					machines.set(j,newTaskEndTime); //reversed these
					break;
				}
			}
			if(found == false) {
				int newTaskEndTime = (int) minValue.getValue();
				machines.addLast(newTaskEndTime);
			}
		}
		return machines.size();
	}
}