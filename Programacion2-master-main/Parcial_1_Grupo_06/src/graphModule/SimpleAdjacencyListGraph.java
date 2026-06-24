package graphModule;

import listModule.SimpleList;
import listModule.SimpleLinkedList;
import dictionaryModule.SimpleDictionary;
import dictionaryModule.SimpleLinkedDictionary;

public class SimpleAdjacencyListGraph<T> implements SimpleGraph<T> {

	private SimpleDictionary<T, SimpleList<Edge<T>>> adjacencyList;
	private int size;

	public SimpleAdjacencyListGraph() {
		this.adjacencyList = new SimpleLinkedDictionary<>();
		this.size = 0;
	}

	// Agrega un vertice al grafo
	@Override
	public void addVertex(T vertex) {
		if (!containsVertex(vertex)) {
			adjacencyList.put(vertex, new SimpleLinkedList<>());
			size++;
		}
	}

	// Agrega una arista dirigida con peso desde from hacia to
	@Override
	public void addEdge(T from, T to, int weight) {
		// Si los vertices no existen, los crea
		if (!containsVertex(from)) {
			addVertex(from);
		}
		if (!containsVertex(to)) {
			addVertex(to);
		}

		Edge<T> edge = new Edge<>(to, weight);
		adjacencyList.get(from).add(edge);
	}

	// Devuelve la lista de aristas (vecinos) de un vertice
	@Override
	public SimpleList<Edge<T>> getNeighbors(T vertex) {
		return adjacencyList.get(vertex);
	}

	// Devuelve una lista con todos los vertices del grafo
	@Override
	public SimpleList<T> vertices() {
		return adjacencyList.keys();
	}

	// Devuelve true si el vertice existe en el grafo
	@Override
	public boolean containsVertex(T vertex) {
		return adjacencyList.containsKey(vertex);
	}

	// Devuelve la cantidad de vertices
	@Override
	public int size() {
		return this.size;
	}

	// Imprime el grafo completo, arista por arista
	public void printGraph() {
		SimpleList<T> verts = vertices();
		for (int i = 0; i < verts.size(); i++) {
			T vertex = verts.get(i);
			SimpleList<Edge<T>> edges = getNeighbors(vertex);
			if (edges != null) {
				for (int j = 0; j < edges.size(); j++) {
					Edge<T> edge = edges.get(j);
					System.out.println(vertex + " → " + edge.getDestination() + ": " + edge.getWeight());
				}
			}
		}
	}
}
