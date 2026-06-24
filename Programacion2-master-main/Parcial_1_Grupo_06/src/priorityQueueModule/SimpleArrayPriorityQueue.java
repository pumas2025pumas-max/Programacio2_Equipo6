package priorityQueueModule;

public class SimpleArrayPriorityQueue<E> implements SimplePriorityQueue<E> {

	private Object[] elements;
	private int[] priorities;
	private int size;
	private static final int INITIAL_CAPACITY = 10;

	public SimpleArrayPriorityQueue() {
		this.elements = new Object[INITIAL_CAPACITY];
		this.priorities = new int[INITIAL_CAPACITY];
		this.size = 0;
	}

	// Duplica la capacidad de los arrays cuando se llenan
	private void resize() {
		int nuevaCapacidad = elements.length * 2;
		Object[] nuevoElements = new Object[nuevaCapacidad];
		int[] nuevoPriorities = new int[nuevaCapacidad];
		for (int i = 0; i < size; i++) {
			nuevoElements[i] = elements[i];
			nuevoPriorities[i] = priorities[i];
		}
		this.elements = nuevoElements;
		this.priorities = nuevoPriorities;
	}

	// Agrega un elemento en orden segun su prioridad (mayor prioridad al inicio)
	@Override
	public void enqueue(E element, int priority) {
		if (size == elements.length) {
			resize();
		}

		int insertIndex = size;
		for (int i = size - 1; i >= 0; i--) {
			if (priority >= priorities[i]) {
				break;
			}
			elements[i + 1] = elements[i];
			priorities[i + 1] = priorities[i];
			insertIndex = i;
		}

		elements[insertIndex] = element;
		priorities[insertIndex] = priority;
		size++;
	}

	// Remueve y devuelve el elemento de mayor prioridad (el primero del array)
	@SuppressWarnings("unchecked")
	@Override
	public E dequeue() {
		if (isEmpty())
			return null;
		E element = (E) elements[0];

		// Desplazar todo hacia la izquierda
		for (int i = 0; i < size - 1; i++) {
			elements[i] = elements[i + 1];
			priorities[i] = priorities[i + 1];
		}
		elements[size - 1] = null;
		size--;
		return element;
	}

	// Devuelve el elemento de mayor prioridad sin removerlo
	@SuppressWarnings("unchecked")
	@Override
	public E peek() {
		if (isEmpty())
			return null;
		return (E) elements[0];
	}

	// Devuelve la prioridad del primer elemento
	@Override
	public int getHighestPriority() {
		if (isEmpty())
			return -1; // o tirar excepcion, usamos -1 como default
		return priorities[0];
	}

	// Borra todos los elementos de la cola
	@Override
	public void clear() {
		this.elements = new Object[INITIAL_CAPACITY];
		this.priorities = new int[INITIAL_CAPACITY];
		this.size = 0;
	}

	// Devuelve la cantidad de elementos
	@Override
	public int size() {
		return this.size;
	}

	// Devuelve true si la cola esta vacia
	@Override
	public boolean isEmpty() {
		return this.size == 0;
	}
}
