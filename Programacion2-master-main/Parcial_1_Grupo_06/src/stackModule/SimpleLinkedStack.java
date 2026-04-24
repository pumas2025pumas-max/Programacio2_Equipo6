package stackModule;

class StackNode<E> {
	E element;
	StackNode<E> next;

	public StackNode(E element) {
		this.element = element;
		this.next = null;
	}
}

public class SimpleLinkedStack<E> implements SimpleStack<E> {

	private StackNode<E> top;
	private int size;

	public SimpleLinkedStack() {
		this.top = null;
		this.size = 0;
	}

	// inserta un elemento al final de la pila
	@Override
	public void push(E element) {
		StackNode<E> newNode = new StackNode<>(element);
		newNode.next = top;
		top = newNode;
		size++;
	}

	// borra el ultimo elemento de la pila
	@Override
	public E pop() {
		if (isEmpty())
			return null;
		E element = top.element;
		top = top.next;
		size--;
		return element;
	}

	// muestra el ultimo elemento de la pila sin borrarlo
	@Override
	public E peek() {
		if (isEmpty())
			return null;
		return top.element;
	}

	// borra todos los elementos de la pila
	@Override
	public void clear() {
		top = null;
		size = 0;
	}

	// muestra el tamaño de la pila
	@Override
	public int size() {
		return this.size;
	}

	// devuelve true si la pila esta vacia
	@Override
	public boolean isEmpty() {
		return this.top == null;
	}
}
