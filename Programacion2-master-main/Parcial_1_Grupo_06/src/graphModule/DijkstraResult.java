package graphModule;

import listModule.SimpleList;

public class DijkstraResult<T> {
	private SimpleList<T> path;
	private int totalDistance;

	public DijkstraResult(SimpleList<T> path, int totalDistance) {
		this.path = path;
		this.totalDistance = totalDistance;
	}

	public SimpleList<T> getPath() {
		return path;
	}

	public int getTotalDistance() {
		return totalDistance;
	}

	// Devuelve true si se encontro un camino valido
	public boolean hasPath() {
		return path != null && !path.isEmpty();
	}
}
