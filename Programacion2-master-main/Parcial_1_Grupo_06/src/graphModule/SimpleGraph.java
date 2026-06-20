package graphModule;

import listModule.SimpleList;

public interface SimpleGraph<T> {
	// Agrega un vertice al grafo
	public void addVertex(T vertex);

	// Agrega una arista dirigida con peso desde from hacia to
	public void addEdge(T from, T to, int weight);

	// Devuelve la lista de aristas (vecinos) de un vertice
	public SimpleList<Edge<T>> getNeighbors(T vertex);

	// Devuelve una lista con todos los vertices del grafo
	public SimpleList<T> vertices();

	// Devuelve true si el vertice existe en el grafo
	public boolean containsVertex(T vertex);

	// Devuelve la cantidad de vertices
	public int size();
}
