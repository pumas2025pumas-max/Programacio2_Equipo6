package listModule;

class Node<E> {
	E element;
	Node<E> next;
	Node<E> prev;

	public Node(E element) {
		this.element = element;
		this.next = null;
		this.prev = null;
	}

}

public class SimpleLinkedList<E> implements SimpleList<E> {

	private Node<E> head;
	private Node<E> tail;
	private int size;

	public SimpleLinkedList() {
		this.head = null;
		this.tail = null;
		this.size = 0;

	}

	// inserta un elemento al final de la lista
	@Override
	public boolean add(E element) {
		Node<E> newNode = new Node<>(element);
		if (isEmpty()) {
			head = newNode;
			tail = newNode;
		} else {
			newNode.prev = tail;
			tail.next = newNode;
			tail = newNode;

		}
		size++;
		return true;

	}

	// inserta un elemento en la posicion index
	@Override
	public void add(int index, E element) {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException();
		if (index == size) {
			add(element);
			return;
		}

		Node<E> newNode = new Node<>(element);

		if (index == 0) {
			newNode.next = head;
			head.prev = newNode;
			head = newNode;
		} else {
			Node<E> b = getNode(index);
			Node<E> a = b.prev;
			newNode.prev = a;
			newNode.next = b;
			b.prev = newNode;
			a.next = newNode;

		}

		size++;

	}

	// devuelve el nodo en la posicion index
	private Node<E> getNode(int index) {
		if (index < 0 || index >= size || head == null) {
			return null;
		}
		Node<E> current = head;
		for (int i = 0; i < index; i++) {
			if (current == null)
				return null;
			current = current.next;
		}
		return current;
	}

	// remueve el elemento en la posicion index
	@Override
	public E remove(int index) {
		if (index < 0 || index >= size)
			throw new IndexOutOfBoundsException();
		Node<E> target = getNode(index);
		E element = target.element;
		if (target == head) {
			head = target.next;
			if (head != null)
				head.prev = null;
		} else if (target == tail) {
			tail = target.prev;
			if (tail != null)
				tail.next = null;
		} else {
			target.prev.next = target.next;
			target.next.prev = target.prev;
		}
		size--;
		if (size == 0)
			tail = null;
		return element;

	}

	// remueve un elemento por su valor
	@Override
	public boolean remove(Object object) {
		Node<E> current = head;
		while (current != null) {
			if (current.element.equals(object)) {
				if (current == head)
					head = current.next;
				if (current == tail)
					tail = current.prev;
				if (current.prev != null)
					current.prev.next = current.next;
				if (current.next != null)
					current.next.prev = current.prev;
				size--;
				return true;
			}
			current = current.next;
		}
		return false;
	}

	// borra todos los elementos de la lista
	@Override
	public void clear() {
		head = null;
		tail = null;
		size = 0;

	}

	// busca si un elemento existe en la lista
	@Override
	public boolean contains(Object object) {
		Node<E> current = head;
		while (current != null) {
			if (current.element.equals(object))
				return true;
			current = current.next;
		}
		return false;
	}

	// devuelve el elemento en la posicion index
	@Override
	public E get(int index) {
		Node<E> node = getNode(index);
		if (node == null) {
			return null;
		}
		return node.element;
	}

	// reemplaza el elemento en la posicion index
	@Override
	public E set(int index, E element) {
		Node<E> target = getNode(index);
		E oldElement = target.element;
		target.element = element;
		return oldElement;
	}

	// devuelve el tamaño de la lista
	@Override
	public int size() {
		return this.size;
	}

	// devuelve true si la lista esta vacia
	@Override
	public boolean isEmpty() {
		return this.head == null;
	}

}
