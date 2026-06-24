package graphModule;

import listModule.SimpleList;
import listModule.SimpleLinkedList;
import dictionaryModule.SimpleDictionary;
import dictionaryModule.SimpleLinkedDictionary;
import priorityQueueModule.SimplePriorityQueue;
import priorityQueueModule.SimpleLinkedPriorityQueue;

public class Dijkstra {

	// Calcula el camino mas corto entre origin y destination usando Dijkstra
	public static <T> DijkstraResult<T> shortestPath(SimpleGraph<T> graph, T origin, T destination) {
		// Diccionario de distancias minimas desde origin a cada vertice
		SimpleDictionary<T, Integer> distances = new SimpleLinkedDictionary<>();
		// Diccionario de vertices previos (para reconstruir el camino)
		SimpleDictionary<T, T> previous = new SimpleLinkedDictionary<>();
		// Diccionario para marcar vertices ya visitados
		SimpleDictionary<T, Boolean> visited = new SimpleLinkedDictionary<>();

		// Cola de prioridad: usamos prioridad negada porque nuestra cola
		// devuelve el de MAYOR prioridad primero, y necesitamos el de MENOR distancia
		SimplePriorityQueue<T> queue = new SimpleLinkedPriorityQueue<>();

		// Inicializar distancias en infinito para todos los vertices
		SimpleList<T> vertices = graph.vertices();
		for (int i = 0; i < vertices.size(); i++) {
			T vertex = vertices.get(i);
			distances.put(vertex, Integer.MAX_VALUE);
			visited.put(vertex, false);
		}

		// La distancia al origen es 0
		distances.put(origin, 0);
		queue.enqueue(origin, 0); // prioridad 0 (negada: -0 = 0)

		while (!queue.isEmpty()) {
			T current = queue.dequeue();

			// Si ya visitamos este vertice, saltear
			if (visited.get(current) != null && visited.get(current)) {
				continue;
			}
			visited.put(current, true);

			// Si llegamos al destino, terminamos
			if (current.equals(destination)) {
				break;
			}

			// Recorrer vecinos del vertice actual
			SimpleList<Edge<T>> neighbors = graph.getNeighbors(current);
			if (neighbors != null) {
				for (int i = 0; i < neighbors.size(); i++) {
					Edge<T> edge = neighbors.get(i);
					T neighbor = edge.getDestination();

					// Si ya fue visitado, saltear
					if (visited.get(neighbor) != null && visited.get(neighbor)) {
						continue;
					}

					int currentDist = distances.get(current);
					int newDist = currentDist + edge.getWeight();
					int oldDist = distances.get(neighbor);

					// Si encontramos un camino mas corto, actualizar
					if (newDist < oldDist) {
						distances.put(neighbor, newDist);
						previous.put(neighbor, current);
						// Prioridad negada: menor distancia = mayor prioridad
						queue.enqueue(neighbor, -newDist);
					}
				}
			}
		}

		// Reconstruir el camino desde destination hacia origin
		Integer distToDestination = distances.get(destination);
		if (distToDestination == null || distToDestination == Integer.MAX_VALUE) {
			// No hay camino
			return new DijkstraResult<>(new SimpleLinkedList<>(), -1);
		}

		// Reconstruir el camino al reves (desde destino a origen)
		SimpleLinkedList<T> reversePath = new SimpleLinkedList<>();
		T current = destination;
		while (current != null) {
			reversePath.add(0, current); // insertar al inicio para invertir
			current = previous.get(current);
		}

		return new DijkstraResult<>(reversePath, distToDestination);
	}
}
