package priorityQueueModule;

class PriorityNode<E> {
	E element;
	int priority;
	PriorityNode<E> next;

	public PriorityNode(E element, int priority) {
		this.element = element;
		this.priority = priority;
		this.next = null;
	}
}

public class SimpleLinkedPriorityQueue<E> implements SimplePriorityQueue<E> {

	private PriorityNode<E> head;
	private int size;

	public SimpleLinkedPriorityQueue() {
		this.head = null;
		this.size = 0;
	}

	// Agrega un elemento en orden segun su prioridad (mayor prioridad al frente)
	@Override
	public void enqueue(E element, int priority) {
		PriorityNode<E> newNode = new PriorityNode<>(element, priority);

		// Si la cola esta vacia o el nuevo nodo tiene mayor prioridad que el head
		if (isEmpty() || priority > head.priority) {
			newNode.next = head;
			head = newNode;
		} else {
			// Buscar la posicion correcta para insertar
			PriorityNode<E> current = head;
			while (current.next != null && current.next.priority >= priority) {
				current = current.next;
			}
			newNode.next = current.next;
			current.next = newNode;
		}
		size++;
	}

	// Remueve y devuelve el elemento de mayor prioridad (el primero de la lista)
	@Override
	public E dequeue() {
		if (isEmpty())
			return null;
		E element = head.element;
		head = head.next;
		size--;
		return element;
	}

	// Devuelve el elemento de mayor prioridad sin removerlo
	@Override
	public E peek() {
		if (isEmpty())
			return null;
		return head.element;
	}

	// Borra todos los elementos de la cola
	@Override
	public void clear() {
		head = null;
		size = 0;
	}

	// Devuelve la cantidad de elementos
	@Override
	public int size() {
		return this.size;
	}

	// Devuelve true si la cola esta vacia
	@Override
	public boolean isEmpty() {
		return head == null;
	}
}
