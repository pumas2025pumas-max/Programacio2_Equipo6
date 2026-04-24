package queueModule;

class QueueNode<E> {
	E element;
	QueueNode<E> next;
	QueueNode<E> prev;

	public QueueNode(E element) {
		this.element = element;
		this.next = null;
		this.prev = null;
	}
}

public class SimpleLinkedQueue<E> implements SimpleQueue<E> {

	private QueueNode<E> head;
	private QueueNode<E> tail;
	private int size;

	public SimpleLinkedQueue() {
		this.head = null;
		this.tail = null;
		this.size = 0;
	}

	// inserta un elemento al final de la cola
	@Override
	public void enqueue(E element) {
		QueueNode<E> newNode = new QueueNode<>(element);
		if (isEmpty()) {
			head = newNode;
			tail = newNode;
		} else {
			newNode.prev = tail;
			tail.next = newNode;
			tail = newNode;
		}
		size++;
	}

	// borra el primer elemento de la cola
	@Override
	public E dequeue() {
		if (isEmpty())
			return null;
		E element = head.element;
		head = head.next;
		if (head != null) {
			head.prev = null;
		} else {
			tail = null;
		}
		size--;
		return element;
	}

	// muestra el primer elemento de la cola sin borrarlo
	@Override
	public E peek() {
		if (isEmpty())
			return null;
		return head.element;
	}

	// borra todos los elementos de la cola
	@Override
	public void clear() {
		head = null;
		tail = null;
		size = 0;
	}

	// muestra el tamaño de la cola
	@Override
	public int size() {
		return this.size;
	}

	// devuelve true si la cola esta vacia
	@Override
	public boolean isEmpty() {
		return this.head == null;
	}
}
