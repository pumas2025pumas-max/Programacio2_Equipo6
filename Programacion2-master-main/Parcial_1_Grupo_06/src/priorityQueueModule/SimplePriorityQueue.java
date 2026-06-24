package priorityQueueModule;

public interface SimplePriorityQueue<E> {
	// Agrega un elemento con una prioridad determinada
	public void enqueue(E element, int priority);

	// Remueve y devuelve el elemento de mayor prioridad
	public E dequeue();

	// Devuelve el elemento de mayor prioridad sin removerlo
	public E peek();

	// Devuelve la prioridad del primer elemento
	public int getHighestPriority();

	// Borra todos los elementos de la cola
	public void clear();

	// Devuelve la cantidad de elementos
	public int size();

	// Devuelve true si la cola esta vacia
	public boolean isEmpty();
}
