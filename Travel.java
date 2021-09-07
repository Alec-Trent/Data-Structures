package cs2321;
import net.datastructures.*;
/**
 * @author Alec Trent
 */
public class Travel {
	AdjListGraph<String, Integer> adjListGraph = new AdjListGraph<>();
	DoublyLinkedList<Edge<Integer>> doublyLinkedList = new DoublyLinkedList<>();
	HashMap<String, Vertex<String>> hashMap = new HashMap<>();

	/**
	 * @param routes: Array of routes between cities. 
	 *                routes[i][0] and routes[i][1] represent the city names on both ends of the route. 
	 *                routes[i][2] represents the cost in string type. 
	 *                Hint: In Java, use Integer.valueOf to convert string to integer. 
	 */
	public Travel(String [][] routes) {
		Vertex<String> source;
		Vertex<String> dest;
		int distance;

		int departure = 0; 
		int destination = 1; 
		int weight = 2;
		for (int i = 0; i<routes.length; i++) {
			String[] flight = routes[i];
			if (hashMap.get(flight[departure]) != null) {
				source = hashMap.get(flight[departure]);
			} else {
				source = adjListGraph.insertVertex(flight[departure]);
				hashMap.put(flight[departure], source);
			}
			if (hashMap.get(flight[destination]) != null) {
				dest = hashMap.get(flight[destination]);
			} else {
				dest = adjListGraph.insertVertex(flight[destination]);
				hashMap.put(flight[destination], dest);
			}
			distance = Integer.parseInt(flight[weight]);
			adjListGraph.insertEdge(source, dest, distance);
		}
	}

	/**
	 * @param departure: the departure city name 
	 * @param destination: the destination city name
	 * @return Return the path from departure city to destination using Depth First Search algorithm. 
	 *         The path should be represented as ArrayList or DoublylinkedList of city names. 
	 *         The order of city names in the list should match order of the city names in the path.  
	 *         
	 * @IMPORTANT_NOTE: The outgoing edges should be traversed by the order of the city names stored in
	 *                 the opposite vertices. For example, if V has 3 outgoing edges as in the picture below,
	 *                           V
	 *                        /  |  \
	 *                       /   |    \
	 *                      B    A     F  
	 *              your algorithm below should visit the outgoing edges of V in the order of A,B,F.
	 *              This means you will need to create a helper function to sort the outgoing edges by 
	 *              the opposite city names.
	 *              	              
	 *              See the method sortedOutgoingEdges below. 
	 */
	public Iterable<String> DFSRoute(String departure, String destination ) {
		HashMap<Vertex<String>, Edge<Integer>> known = new HashMap<>();
		Map<Vertex<String>, Edge<Integer>> forest = new HashMap<>();
		DFS(adjListGraph, hashMap.get(departure), known, forest);
		DoublyLinkedList<String> dll = new DoublyLinkedList<>();
		dll.addLast(departure);
		for (Edge<Integer> e :constructPath(adjListGraph, hashMap.get(departure), hashMap.get(destination), forest)) {
			dll.addLast(adjListGraph.opposite(hashMap.get(dll.last().getElement()), e).getElement());
		}

		return dll;
	}

	/** Performs depth-first search of Graph g starting at Vertex u. **/
	public static <V,E> void DFS(AdjListGraph<V,E> g, Vertex<V> u, HashMap<Vertex<V>, Edge<E>> known, Map<Vertex<V>,Edge<E>> forest) {
		known.put(u, null); // u has been discovered
		for (Edge<E> e : sortedOutgoingEdges(g, u)) { // for every outgoing edge from u
			Vertex<V> v = g.opposite(u, e);
			if (!contains(v, known)) {
				forest.put(v, e); // e is the tree edge that discovered v
				DFS(g, v, known, forest); // recursively explore from v
			}
		}
	}

	public static <V,E> boolean contains(Vertex<V> vertex, Map<Vertex<V>, Edge<E>> known ) {
		for (Vertex<V> v : known.keySet()) {
			if (v.equals(vertex))
				return true;
		}	
		return false;
	}

	/** Returns an ordered list of edges comprising the directed path from u to v. **/
	public static <V,E> DoublyLinkedList<Edge<E>>
	constructPath(AdjListGraph<V,E> g, Vertex<V> u, Vertex<V> v,
			Map<Vertex<V>,Edge<E>> forest) {
		DoublyLinkedList<Edge<E>> path = new DoublyLinkedList<>( );
		if (forest.get(v) != null) { // v was discovered during the search
			Vertex<V> walk = v; // we construct the path from back to front
			while (walk != u) {
				Edge<E> edge = forest.get(walk);
				path.addFirst(edge); // add edge to *front* of path
				walk = g.opposite(walk, edge); // repeat with opposite endpoint
			}
		}
		return path;
	}

	/**
	 * @param departure: the departure city name 
	 * @param destination: the destination city name
	 * @return Return the path from departure city to destination using Breadth First Search algorithm. 
	 *         The path should be represented as ArrayList or DoublylinkedList of city names. 
	 *         The order of city names in the list should match order of the city names in the path.  
	 *         
	 * @IMPORTANT_NOTE: The outgoing edges should be traversed by the order of the city names stored in
	 *                 the opposite vertices. For example, if V has 3 outgoing edges as in the picture below,
	 *                           V
	 *                        /  |  \
	 *                       /   |    \
	 *                      B    A     F  
	 *              your algorithm below should visit the outgoing edges of V in the order of A,B,F.
	 *              This means you will need to create a helper function to sort the outgoing edges by 
	 *              the opposite city names.
	 *              	             
	 *              See the method sortedOutgoingEdges below. 
	 */

	public Iterable<String> BFSRoute(String departure, String destination ) {
		HashMap<Vertex<String>, Edge<Integer>> known = new HashMap<>();
		Map<Vertex<String>, Edge<Integer>> forest = new HashMap<>();
		BFS(adjListGraph, hashMap.get(departure), known, forest);
		DoublyLinkedList<String> dll = new DoublyLinkedList<>();
		dll.addLast(departure);
		for (Edge<Integer> e :constructPath(adjListGraph, hashMap.get(departure), hashMap.get(destination), forest)) {
			dll.addLast(adjListGraph.opposite(hashMap.get(dll.last().getElement()), e).getElement());
		}

		return dll;
	}

	/** Performs breadth-first search of Graph g starting at Vertex u. **/
	public static <V,E> void BFS(AdjListGraph<V,E> g, Vertex<V> s, HashMap<Vertex<V>, Edge<E>> known, Map<Vertex<V>,Edge<E>> forest) {
		DoublyLinkedList<Vertex<V>> level = new DoublyLinkedList<>( );
		contains(s, known);
		level.addLast(s); // first level includes only s
		while (!level.isEmpty( )) {
			DoublyLinkedList<Vertex<V>> nextLevel = new DoublyLinkedList<>( );
			for (Vertex<V> u : level)
				for (Edge<E> e : sortedOutgoingEdges(g, u)) {
					Vertex<V> v = g.opposite(u, e);
					if (!contains(v, known)) {
						known.put(v, e);
						forest.put(v, e); // e is the tree edge that discovered v
						nextLevel.addLast(v); // v will be further considered in next pass
					}
				}
			level = nextLevel; // relabel ’next’ level to become the current
		}
	}

	/**
	 * @param departure: the departure city name 
	 * @param destination: the destination city name
	 * @param itinerary: an empty DoublylinkedList object will be passed in to the method. 
	 * 	       When a shorted path is found, the city names in the path should be added to the list in the order. 
	 * @return return the cost of the shortest path from departure to destination. 
	 *         
	 * @IMPORTANT_NOTE: The outgoing edges should be traversed by the order of the city names stored in
	 *                 the opposite vertices. For example, if V has 3 outgoing edges as in the picture below,
	 *                           V
	 *                        /  |  \
	 *                       /   |    \
	 *                      B    A     F  
	 *              your algorithm below should visit the outgoing edges of V in the order of A,B,F.
	 *              This means you will need to create a helper function to sort the outgoing edges by 
	 *              the opposite city names.
	 *              
	 *              See the method sortedOutgoingEdges below. 
	 */

	public int DijkstraRoute(String departure, String destination, DoublyLinkedList<String> itinerary ) {
		Map<Vertex<String>, Integer> cloud = shortestPathLengths(adjListGraph, hashMap.get(departure));
		Map<Vertex<String>,Edge<Integer>> forest = path(adjListGraph, hashMap.get(departure), cloud);
		itinerary.addLast(departure);
		int pathCost = 0;
		for (Edge<Integer> e :constructPath(adjListGraph, hashMap.get(departure), hashMap.get(destination), forest)) {
			itinerary.addLast(adjListGraph.opposite(hashMap.get(itinerary.last().getElement()), e).getElement());
			pathCost += e.getElement();
		}

		return pathCost;
	}

	public static <V> Map<Vertex<V>,Edge<Integer>> path(Graph<V, Integer> g, Vertex<V> s, Map<Vertex<V>,Integer> d){
		Map<Vertex<V>, Edge<Integer>> tree = new HashMap<>();
		for (Vertex<V> v : d.keySet())
			if(v != s)
				for (Edge<Integer> e: g.incomingEdges(v)) {
					Vertex<V> u = g.opposite(v, e);
					int wgt = e.getElement();
					if(d.get(v) == d.get(u) + wgt)
						tree.put(v, e);
				}
		return tree;
	}


	/** Computes shortest-path distances from src vertex to all reachable vertices of g. **/
	public static <V> Map<Vertex<V>, Integer> shortestPathLengths(Graph<V,Integer> g, Vertex<V> src) {
		// d.get(v) is upper bound on distance from src to v
		Map<Vertex<V>, Integer> d = new HashMap<>( );
		// map reachable v to its d value
		Map<Vertex<V>, Integer> cloud = new HashMap<>( );
		// pq will have vertices as elements, with d.get(v) as key
		AdaptablePriorityQueue<Integer, Vertex<V>> pq;
		pq = new HeapPQ<>( );
		// maps from vertex to its pq locator
		Map<Vertex<V>, Entry<Integer,Vertex<V>>> pqTokens;
		pqTokens = new HashMap<>( );

		// for each vertex v of the graph, add an entry to the priority queue, with
		// the source having distance 0 and all others having infinite distance
		for (Vertex<V> v : g.vertices( )) {
			if (v == src)
				d.put(v,0);
			else
				d.put(v, Integer.MAX_VALUE);
			pqTokens.put(v, pq.insert(d.get(v), v)); // save entry for future updates
		}
		// now begin adding reachable vertices to the cloud
		while (!pq.isEmpty( )) {
			Entry<Integer, Vertex<V>> entry = pq.removeMin( );
			int key = entry.getKey( );
			Vertex<V> u = entry.getValue( );
			cloud.put(u, key); // this is actual distance to u
			pqTokens.remove(u); // u is no longer in pq
			for (Edge<Integer> e : g.outgoingEdges(u)) {
				Vertex<V> v = g.opposite(u,e);
				if (cloud.get(v) == null) {
					// perform relaxation step on edge (u,v)
					int wgt = e.getElement( );
					if (d.get(u) + wgt < d.get(v)) { // better path to v?
						d.put(v, d.get(u) + wgt); // update the distance
						pq.replaceKey(pqTokens.get(v), d.get(v)); // update the pq entry
					}
				}
			}
		}
		return cloud; // this only includes reachable vertices
	}

	/**
	 * I strongly recommend you to implement this method to return sorted outgoing edges for vertex V
	 * You may use any sorting algorithms, such as insert sort, selection sort, etc.
	 * 
	 * @param v: vertex v
	 * @return a list of edges ordered by edge's name
	 */
	public static <V, E> Iterable<Edge<E>> sortedOutgoingEdges(AdjListGraph<V,E> graph, Vertex<V> v)  {
		HeapPQ<V, Edge<E>> pq = new HeapPQ<>();
		for (Edge<E> e: graph.outgoingEdges(v)) {
			Vertex<V> u = graph.opposite(v, e);
			pq.insert(u.getElement(), e);
		}

		DoublyLinkedList<Edge<E>> sortedList = new DoublyLinkedList<>();
		while (!pq.isEmpty()) {
			Entry<V, Edge<E>> pqEntry = pq.removeMin();
			sortedList.addLast(pqEntry.getValue());
		}
		return sortedList;
	}
}