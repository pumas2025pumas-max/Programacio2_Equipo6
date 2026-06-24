package graphModule;

import listModule.SimpleList;
import listModule.SimpleLinkedList;

public class SimpleAdjacencyMatrixGraph<T> implements SimpleGraph<T> {

	private Object[] vertexList;
	private int[][] matrix;
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

	// Agrega un vertice al grafo
	@Override
	public boolean addVertex(T vertex) {
		if (containsVertex(vertex)) return false;

		if (size == capacity) {
			resize();
		}

		vertexList[size] = vertex;
		size++;
		return true;
	}

	// Remueve un vertice y todas sus aristas
	@Override
	public boolean removeVertex(T vertex) {
		int index = findVertexIndex(vertex);
		if (index == -1) return false;

		// Desplazar filas y columnas para tapar el hueco
		for (int i = index; i < size - 1; i++) {
			vertexList[i] = vertexList[i + 1];
			for (int j = 0; j < size; j++) {
				matrix[i][j] = matrix[i + 1][j];
			}
		}
		for (int j = index; j < size - 1; j++) {
			for (int i = 0; i < size - 1; i++) {
				matrix[i][j] = matrix[i][j + 1];
			}
		}

		vertexList[size - 1] = null;
		for (int i = 0; i < size; i++) {
			matrix[size - 1][i] = NO_EDGE;
			matrix[i][size - 1] = NO_EDGE;
		}

		size--;
		return true;
	}

	// Agrega una arista dirigida con peso
	@Override
	public boolean addEdge(T from, T to, int weight) {
		if (!containsVertex(from)) {
			addVertex(from);
		}
		if (!containsVertex(to)) {
			addVertex(to);
		}

		int fromIndex = findVertexIndex(from);
		int toIndex = findVertexIndex(to);

		if (matrix[fromIndex][toIndex] != NO_EDGE) {
			return false; // Ya existe
		}

		matrix[fromIndex][toIndex] = weight;
		return true;
	}

	// Remueve una arista dirigida
	@Override
	public boolean removeEdge(T from, T to) {
		int fromIndex = findVertexIndex(from);
		int toIndex = findVertexIndex(to);

		if (fromIndex == -1 || toIndex == -1) return false;
		if (matrix[fromIndex][toIndex] == NO_EDGE) return false;

		matrix[fromIndex][toIndex] = NO_EDGE;
		return true;
	}

	// Devuelve true si el vertice existe
	@Override
	public boolean containsVertex(T vertex) {
		return findVertexIndex(vertex) != -1;
	}

	// Devuelve true si la arista existe
	@Override
	public boolean containsEdge(T from, T to) {
		int fromIndex = findVertexIndex(from);
		int toIndex = findVertexIndex(to);
		if (fromIndex == -1 || toIndex == -1) return false;
		return matrix[fromIndex][toIndex] != NO_EDGE;
	}

	// Devuelve el peso de la arista, o -1 si no existe
	@Override
	public int getWeight(T from, T to) {
		int fromIndex = findVertexIndex(from);
		int toIndex = findVertexIndex(to);
		if (fromIndex == -1 || toIndex == -1) return -1;
		if (matrix[fromIndex][toIndex] == NO_EDGE) return -1;
		return matrix[fromIndex][toIndex];
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

	// Devuelve la cantidad de vertices
	@Override
	public int size() {
		return this.size;
	}

	// Imprime el grafo completo, arista por arista
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
