package graphModule;

import listModule.SimpleList;

public interface SimpleGraph<T> {
	// Devuelve una lista con todos los vertices del grafo
	public SimpleList<T> vertices();

	// Agrega un vertice al grafo. Devuelve true si se agrego, false si ya existia
	public boolean addVertex(T vertex);

	// Remueve un vertice y todas sus aristas. Devuelve true si existia
	public boolean removeVertex(T vertex);

	// Agrega una arista dirigida con peso. Devuelve true si se agrego, false si ya existia
	public boolean addEdge(T from, T to, int weight);

	// Remueve una arista dirigida. Devuelve true si existia
	public boolean removeEdge(T from, T to);

	// Devuelve true si el vertice existe en el grafo
	public boolean containsVertex(T vertex);

	// Devuelve true si la arista existe en el grafo
	public boolean containsEdge(T from, T to);

	// Devuelve el peso de la arista entre from y to, o -1 si no existe
	public int getWeight(T from, T to);

	// Devuelve la lista de aristas (vecinos) de un vertice
	public SimpleList<Edge<T>> getNeighbors(T vertex);

	// Devuelve la cantidad de vertices
	public int size();
}
