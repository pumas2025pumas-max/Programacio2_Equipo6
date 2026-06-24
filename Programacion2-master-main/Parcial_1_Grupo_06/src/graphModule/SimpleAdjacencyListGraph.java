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

	// Auxiliar: busca un Edge por destino en la lista de vecinos de from
	private Edge<T> getEdge(T from, T to) {
		SimpleList<Edge<T>> edges = adjacencyList.get(from);
		if (edges == null) return null;

		for (int i = 0; i < edges.size(); i++) {
			if (edges.get(i).getDestination().equals(to)) {
				return edges.get(i);
			}
		}
		return null;
	}

	// Devuelve una lista con todos los vertices del grafo
	@Override
	public SimpleList<T> vertices() {
		return adjacencyList.keys();
	}

	// Agrega un vertice al grafo
	@Override
	public boolean addVertex(T vertex) {
		if (containsVertex(vertex)) {
			return false;
		}
		adjacencyList.put(vertex, new SimpleLinkedList<>());
		size++;
		return true;
	}

	// Remueve un vertice y todas sus aristas
	@Override
	public boolean removeVertex(T vertex) {
		if (!containsVertex(vertex)) {
			return false;
		}

		// Remover todas las aristas que apuntan a este vertice
		SimpleList<T> verts = vertices();
		for (int i = 0; i < verts.size(); i++) {
			T v = verts.get(i);
			if (!v.equals(vertex)) {
				removeEdge(v, vertex);
			}
		}

		// Remover el vertice del diccionario
		adjacencyList.remove(vertex);
		size--;
		return true;
	}

	// Agrega una arista dirigida con peso
	@Override
	public boolean addEdge(T from, T to, int weight) {
		// Crear vertices si no existen
		if (!containsVertex(from)) {
			addVertex(from);
		}
		if (!containsVertex(to)) {
			addVertex(to);
		}

		// Verificar si la arista ya existe
		Edge<T> existing = getEdge(from, to);
		if (existing != null) {
			return false; // Ya existe
		}

		adjacencyList.get(from).add(new Edge<>(to, weight));
		return true;
	}

	// Remueve una arista dirigida
	@Override
	public boolean removeEdge(T from, T to) {
		SimpleList<Edge<T>> edges = adjacencyList.get(from);
		if (edges == null) return false;

		for (int i = 0; i < edges.size(); i++) {
			if (edges.get(i).getDestination().equals(to)) {
				edges.remove(i);
				return true;
			}
		}
		return false;
	}

	// Devuelve true si el vertice existe
	@Override
	public boolean containsVertex(T vertex) {
		return adjacencyList.containsKey(vertex);
	}

	// Devuelve true si la arista existe
	@Override
	public boolean containsEdge(T from, T to) {
		return getEdge(from, to) != null;
	}

	// Devuelve el peso de la arista, o -1 si no existe
	@Override
	public int getWeight(T from, T to) {
		Edge<T> edge = getEdge(from, to);
		if (edge == null) return -1;
		return edge.getWeight();
	}

	// Devuelve la lista de aristas (vecinos) de un vertice
	@Override
	public SimpleList<Edge<T>> getNeighbors(T vertex) {
		return adjacencyList.get(vertex);
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
