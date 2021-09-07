package cs2321;

import net.datastructures.Edge;
import net.datastructures.Graph;
import net.datastructures.Vertex;

/**
 * @author Alec Trent
 */
public class Course {
	AdjListGraph<String, Integer> adjListGraph = new AdjListGraph<>(true);
	/**
	 * @param courses: An array of course information. Each element in the array is also array:
	 * 				starts with the course name, followed by a list (0 or more) of prerequisite course names.
	 * 
	 */
	public Course(String courses[][]) {
		int idxNewClass = 0;
		int idxFirstPre = 1;

		Vertex<String> newClass;
		Vertex<String> newPre;


		HashMap<String, Vertex<String>> hashMap = new HashMap<>();
		for (int i = 0; i<courses.length; i++) {
			String[] courseList = courses[i];
			if (hashMap.get(courseList[idxNewClass]) != null) {
				newClass = hashMap.get(courseList[idxNewClass]);
			} else {
				newClass = adjListGraph.insertVertex(courseList[idxNewClass]);
				hashMap.put(courseList[idxNewClass], newClass);
			}
			for (int j = idxFirstPre; j<courseList.length; j++) {
				if (hashMap.get(courseList[j]) !=null) {
					newPre = hashMap.get(courseList[j]);
				} else {
					newPre = adjListGraph.insertVertex(courseList[j]);
					hashMap.put(courseList[j], newPre);
				}
				adjListGraph.insertEdge(newPre, newClass, 0);
			}
		}
	}

	/**
	 * @param course
	 * @return find the earliest semester that the given course could be taken by a students after taking all the prerequisites. 
	 */
	public int whichSemester(String course) {
		DoublyLinkedList<Vertex<String>> topographical = new DoublyLinkedList<>();
		CircularArrayQueue<Vertex<String>> checked = new CircularArrayQueue<Vertex<String>>(adjListGraph.numVertices());

		HashMap<Vertex<String>, Integer> inGoingEdgesCount = new HashMap<>();
		HashMap<String, Integer> semesterCount = new HashMap<>();

		for(Vertex<String> u : adjListGraph.vertices()) {
			inGoingEdgesCount.put(u, adjListGraph.inDegree(u));
			semesterCount.put(u.getElement(), 1);

			if(inGoingEdgesCount.get(u) == 0) {
				checked.enqueue(u);

				if(u.getElement().equals(course))
					return 1;
			}
		}
		while(!checked.isEmpty()) {
			Vertex<String> u = checked.dequeue();
			topographical.addLast(u);

			for(Edge<Integer> edge : adjListGraph.outgoingEdges(u)) {
				Vertex<String> v = adjListGraph.opposite(u, edge);
				inGoingEdgesCount.put(v, inGoingEdgesCount.get(v) - 1); //-1 to minus edge coming into prereq
				semesterCount.put(v.getElement(), semesterCount.get(u.getElement()) + 1);

				if(inGoingEdgesCount.get(v) == 0) {
					checked.enqueue(v);
				}
			}
		}
		return semesterCount.get(course);
	}
}