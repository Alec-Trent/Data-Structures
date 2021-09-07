package cs2321;

import net.datastructures.*;

import java.lang.reflect.InvocationTargetException;

/*
 * Implement an Adjacency List Graph using the graph interface.
 *
 * @author Alec Trent
 */
@TimeComplexity("O(n)")
public class AdjListGraph<V, E> implements Graph<V, E> {
	private boolean isDirected;
	private PositionalList<Vertex<V>> vertices = new DoublyLinkedList<>();
	private PositionalList<Edge<E>> edges = new DoublyLinkedList<>();

	/**
	 * Constructs an empty graph. The parameter determines whether this is an
	 * undirected or directed graph.
	 */
	public AdjListGraph(boolean directed) {
		isDirected = directed;
	}

	public AdjListGraph() {
		isDirected = false;
	}

	/** Returns the edges of the graph as an iterable collection */
	public Iterable<Edge<E>> edges() {
		return edges;
	}

	/**
	 * Returns the vertices of edge e as an array of length two. If the graph is
	 * directed, the first vertex is the origin, and the second is the destination.
	 * If the graph is undirected, the order is arbitrary.
	 */
	@TimeComplexity("O(1)")
	public Vertex[] endVertices(Edge<E> e) throws IllegalArgumentException {
		InnerEdge<E> edge = validate(e);
		return edge.getEndpoints();
	}

	/**
	 * Inserts and returns a new edge between vertices u and v, storing given
	 * element.
	 *
	 * @throws IllegalArgumentException if u or v are invalid vertices, or if an
	 *                                  edge already exists between u and v.
	 */
	@TimeComplexity("O(1)")
	public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E o) throws IllegalArgumentException {
		if (getEdge(u, v) == null) {
			InnerEdge<E> e = new InnerEdge<>(u, v, o);
			e.setPosition(edges.addLast(e));
			InnerVertex<V> origin = validate(u);
			InnerVertex<V> dest = validate(v);
			origin.getOutgoing().put(v, e);
			dest.getIncoming().put(u, e);
			return e;
		} else
			throw new IllegalArgumentException("Edge from u to v exists");
	}

	/** Inserts and returns a new vertex with the given element. */
	@TimeComplexity("O(1)")
	public Vertex<V> insertVertex(V o) {
		InnerVertex<V> v = new InnerVertex<>(o, isDirected);
		v.setPosition(vertices.addLast(v));
		return v;
	}

	/** Returns the number of edges of the graph */
	public int numEdges() {
		return edges.size();
	}

	/** Returns the number of vertices of the graph */
	public int numVertices() {
		return vertices.size();


	}/** Returns the vertex that is opposite vertex v on edge e. */
	@TimeComplexity("O(1)")
	public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws IllegalArgumentException {
		InnerEdge<E> edge = validate(e);
		Vertex<V>[] endpoints = edge.getEndpoints();
		if (endpoints[0] == v)
			return endpoints[1];
		else if (endpoints[1] == v)
			return endpoints[0];
		else
			throw new IllegalArgumentException("v is not incident to this edge");
	}

	/** Removes an edge from the graph. */
	@TimeComplexity("O(1)")
	public void removeEdge(Edge<E> e) throws IllegalArgumentException {
		InnerEdge<E> edge = validate(e);
		// remove this edge from vertices' adjacencies
		Vertex<V>[] verts = edge.getEndpoints();
		validate(verts[0]).getOutgoing().remove(verts[1]);
		validate(verts[1]).getIncoming().remove(verts[0]);
		// remove this edge from the list of edges
		edges.remove(edge.getPosition());
		edge.setPosition(null); // invalidates the edge
	}

	/** Removes a vertex and all its incident edges from the graph. */
	@TimeComplexity("O(n)")
	public void removeVertex(Vertex<V> v) throws IllegalArgumentException {
		/*
		 * TCJ
		 * Two for loops could each run n times
		 */
		InnerVertex<V> vert = validate(v);
		// remove all incident edges from the graph
		for (Edge<E> e : vert.getOutgoing().values())
			removeEdge(e);
		for (Edge<E> e : vert.getIncoming().values())
			removeEdge(e);
		// remove this vertex from the list of vertices
		vertices.remove(vert.getPosition());
		vert.setPosition(null); // invalidates the vertex
	}

	/* replace the element in edge object, return the old element */
	@TimeComplexity("O(1)")
	public E replace(Edge<E> e, E o) throws IllegalArgumentException {
		E oldE = validate(e).getElement();
		validate(e).setElement(o);
		return oldE;
	}

	/* replace the element in vertex object, return the old element */
	@TimeComplexity("O(1)")
	public V replace(Vertex<V> v, V o) throws IllegalArgumentException {
		V oldV = validate(v).getElement();
		validate(v).setElement(o);
		return oldV;
	}

	/** Returns the vertices of the graph as an iterable collection */
	public Iterable<Vertex<V>> vertices() {
		return vertices;
	}

	/**
	 * Returns the number of edges for which vertex v is the origin. Note that for
	 * an undirected graph, this is the same result returned by inDegree(v).
	 *
	 * @throws IllegalArgumentException if v is not a valid vertex
	 */
	@Override
	@TimeComplexity("O(1)")
	public int outDegree(Vertex<V> v) throws IllegalArgumentException {
		InnerVertex<V> vert = validate(v);
		return vert.getOutgoing().size();
	}

	/**
	 * Returns the number of edges for which vertex v is the destination. Note that
	 * for an undirected graph, this is the same result returned by outDegree(v).
	 *
	 * @throws IllegalArgumentException if v is not a valid vertex
	 */
	@Override
	@TimeComplexity("O(1)")
	public int inDegree(Vertex<V> v) throws IllegalArgumentException {
		InnerVertex<V> vert = validate(v);
		return vert.getIncoming().size();
	}

	/**
	 * Returns an iterable collection of edges for which vertex v is the origin.
	 * Note that for an undirected graph, this is the same result returned by
	 * incomingEdges(v).
	 *
	 * @throws IllegalArgumentException if v is not a valid vertex
	 */
	@Override
	@TimeComplexity("O(1)")
	public Iterable<Edge<E>> outgoingEdges(Vertex<V> v) throws IllegalArgumentException {
		InnerVertex<V> vert = validate(v);
		return vert.getOutgoing().values(); // edges are the values in the adjacency map
	}

	/**
	 * Returns an iterable collection of edges for which vertex v is the
	 * destination. Note that for an undirected graph, this is the same result
	 * returned by outgoingEdges(v).
	 *
	 * @throws IllegalArgumentException if v is not a valid vertex
	 */
	@Override
	@TimeComplexity("O(1)")
	public Iterable<Edge<E>> incomingEdges(Vertex<V> v) throws IllegalArgumentException {
		InnerVertex<V> vert = validate(v);
		return vert.getIncoming().values(); // edges are the values in the adjacency map
	}

	/** Returns the edge from u to v, or null if they are not adjacent. */
	@Override
	@TimeComplexity("O(1)")
	public Edge<E> getEdge(Vertex<V> u, Vertex<V> v) throws IllegalArgumentException {
		InnerVertex<V> origin = validate(u);
		return origin.getOutgoing().get(v); // will be null if no edge from u to v
	}

	@SuppressWarnings({ "unchecked" })
	@TimeComplexity("O(1)")
	private InnerVertex<V> validate(Vertex<V> v) {
		if (!(v instanceof InnerVertex))
			throw new IllegalArgumentException("Invalid vertex");
		InnerVertex<V> vert = (InnerVertex<V>) v; // safe cast
		if (!vert.validate(this))
			throw new IllegalArgumentException("Invalid vertex");
		return vert;
	}

	@SuppressWarnings({ "unchecked" })
	@TimeComplexity("O(1)")
	private InnerEdge<E> validate(Edge<E> e) {
		if (!(e instanceof InnerEdge))
			throw new IllegalArgumentException("Invalid edge");
		InnerEdge<E> edge = (InnerEdge<E>) e; // safe cast
		if (!edge.validate(this))
			throw new IllegalArgumentException("Invalid edge");
		return edge;
	}

	// ---------------- nested Vertex class ----------------
	/** A vertex of an adjacency map graph representation. */
	private class InnerVertex<V> implements Vertex<V> {
		private V element;
		private Position<Vertex<V>> pos;
		private Map<Vertex<V>, Edge<E>> outgoing, incoming;

		/** Constructs a new InnerVertex instance storing the given element. */
		public InnerVertex(V elem, boolean graphIsDirected) {
			element = elem;
			outgoing = new HashMap<>();
			if (graphIsDirected)
				incoming = new HashMap<>();
			else
				incoming = outgoing; // if undirected, alias outgoing map
		}

		/** Validates that this vertex instance belongs to the given graph. */
		public boolean validate(Graph<V, E> graph) {
			return (AdjListGraph.this == graph && pos != null);
		}

		/** Returns the element associated with the vertex. */
		public V getElement() {
			return element;
		}

		public void setElement(V element) {
			this.element = element;
		}

		/** Stores the position of this vertex within the graph's vertex list. */
		public void setPosition(Position<Vertex<V>> p) {
			pos = p;
		}

		/** Returns the position of this vertex within the graph's vertex list. */
		public Position<Vertex<V>> getPosition() {
			return pos;
		}

		/** Returns reference to the underlying map of outgoing edges. */
		public Map<Vertex<V>, Edge<E>> getOutgoing() {
			return outgoing;
		}

		/** Returns reference to the underlying map of incoming edges. */
		public Map<Vertex<V>, Edge<E>> getIncoming() {
			return incoming;
		}
	} // ------------ end of InnerVertex class ------------

	// ---------------- nested InnerEdge class ----------------
	/** An edge between two vertices. */
	private class InnerEdge<E> implements Edge<E> {
		private E element;
		private Position<Edge<E>> pos;
		private Vertex<V>[] endpoints;

		@SuppressWarnings({ "unchecked" })
		/** Constructs InnerEdge instance from u to v, storing the given element. */
		public InnerEdge(Vertex<V> u, Vertex<V> v, E elem) {
			element = elem;
			endpoints = (Vertex<V>[]) new Vertex[] { u, v }; // array of length 2
		}

		/** Returns the element associated with the edge. */
		public E getElement() {
			return element;
		}

		public void setElement(E element) {
			this.element = element;
		}

		/** Returns reference to the endpoint array. */
		public Vertex<V>[] getEndpoints() {
			return this.endpoints;
		}

		/** Validates that this edge instance belongs to the given graph. */
		public boolean validate(Graph<V, E> graph) {
			return AdjListGraph.this == graph && pos != null;
		}

		/** Stores the position of this edge within the graph's vertex list. */
		public void setPosition(Position<Edge<E>> p) {
			pos = p;
		}

		/** Returns the position of this edge within the graph's vertex list. */
		public Position<Edge<E>> getPosition() {
			return pos;
		}
	} // ------------ end of InnerEdge class ------------
}