package graphModule;

public class Edge<T> {
	private T destination;
	private int weight;

	public Edge(T destination, int weight) {
		this.destination = destination;
		this.weight = weight;
	}

	public T getDestination() {
		return destination;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	// Override equals para comparar por destination y weight
	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Edge<T> other = (Edge<T>) obj;
		return this.weight == other.weight &&
			this.destination.equals(other.destination);
	}

	@Override
	public String toString() {
		return destination + ": " + weight;
	}
}
