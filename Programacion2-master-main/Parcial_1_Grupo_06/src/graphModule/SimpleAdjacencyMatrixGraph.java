package graphModule;

import listModule.SimpleList;
import listModule.SimpleLinkedList;

public class SimpleAdjacencyMatrixGraph<T> implements SimpleGraph<T> {

	private Object[] vertexList;   // Lista de vertices
	private int[][] matrix;        // Matriz de adyacencia (pesos)
	private int size;
	private int capacity;
	private static final int INITIAL_CAPACITY = 10;
	private static final int NO_EDGE = 0; // 0 significa que no hay arista

	public SimpleAdjacencyMatrixGraph() {
		this.capacity = INITIAL_CAPACITY;
		this.vertexList = new Object[capacity];
		this.matrix = new int[capacity][capacity];
		this.size = 0;
	}

	// Duplica la capacidad cuando se llena
	private void resize() {
		int nuevaCapacidad = capacity * 2;
		Object[] nuevoVertexList = new Object[nuevaCapacidad];
		int[][] nuevaMatrix = new int[nuevaCapacidad][nuevaCapacidad];

		for (int i = 0; i < size; i++) {
			nuevoVertexList[i] = vertexList[i];
			for (int j = 0; j < size; j++) {
				nuevaMatrix[i][j] = matrix[i][j];
			}
		}

		this.vertexList = nuevoVertexList;
		this.matrix = nuevaMatrix;
		this.capacity = nuevaCapacidad;
	}

	// Busca el indice de un vertice, devuelve -1 si no existe
	private int findVertexIndex(T vertex) {
		for (int i = 0; i < size; i++) {
			if (vertexList[i].equals(vertex)) {
				return i;
			}
		}
		return -1;
	}

	// Agrega un vertice al grafo
	@Override
	public void addVertex(T vertex) {
		if (containsVertex(vertex)) return;

		if (size == capacity) {
			resize();
		}

		vertexList[size] = vertex;
		size++;
	}

	// Agrega una arista dirigida con peso desde from hacia to
	@Override
	public void addEdge(T from, T to, int weight) {
		if (!containsVertex(from)) {
			addVertex(from);
		}
		if (!containsVertex(to)) {
			addVertex(to);
		}

		int fromIndex = findVertexIndex(from);
		int toIndex = findVertexIndex(to);
		matrix[fromIndex][toIndex] = weight;
	}

	// Devuelve la lista de aristas (vecinos) de un vertice
	@Override
	@SuppressWarnings("unchecked")
	public SimpleList<Edge<T>> getNeighbors(T vertex) {
		int index = findVertexIndex(vertex);
		if (index == -1) return null;

		SimpleList<Edge<T>> neighbors = new SimpleLinkedList<>();
		for (int j = 0; j < size; j++) {
			if (matrix[index][j] != NO_EDGE) {
				neighbors.add(new Edge<>((T) vertexList[j], matrix[index][j]));
			}
		}
		return neighbors;
	}

	// Devuelve una lista con todos los vertices del grafo
	@Override
	@SuppressWarnings("unchecked")
	public SimpleList<T> vertices() {
		SimpleList<T> result = new SimpleLinkedList<>();
		for (int i = 0; i < size; i++) {
			result.add((T) vertexList[i]);
		}
		return result;
	}

	// Devuelve true si el vertice existe en el grafo
	@Override
	public boolean containsVertex(T vertex) {
		return findVertexIndex(vertex) != -1;
	}

	// Devuelve la cantidad de vertices
	@Override
	public int size() {
		return this.size;
	}

	// Imprime el grafo completo, arista por arista
	@SuppressWarnings("unchecked")
	public void printGraph() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (matrix[i][j] != NO_EDGE) {
					System.out.println(vertexList[i] + " → " + vertexList[j] + ": " + matrix[i][j]);
				}
			}
		}
	}
}
