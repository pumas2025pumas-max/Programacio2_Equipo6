package setModule;

class SetNode<E> {
	E element;
	SetNode<E> next;

	public SetNode(E element) {
		this.element = element;
		this.next = null;
	}
}

public class SimpleLinkedSet<E> implements SimpleSet<E> {

	private SetNode<E> head;
	private int size;

	public SimpleLinkedSet() {
		this.head = null;
		this.size = 0;
	}

	// Inserta element si no existe ya en el Set
	@Override
	public boolean add(E element) {
		if (contains(element)) {
			return false;
		}
		SetNode<E> newNode = new SetNode<>(element);
		newNode.next = head;
		head = newNode;
		size++;
		return true;
	}

	// Remueve element si existe en el Set
	@Override
	public boolean remove(E element) {
		if (isEmpty()) {
			return false;
		}
		// Si es el primer nodo
		if (head.element.equals(element)) {
			head = head.next;
			size--;
			return true;
		}
		// Buscar en el resto de la lista
		SetNode<E> current = head;
		while (current.next != null) {
			if (current.next.element.equals(element)) {
				current.next = current.next.next;
				size--;
				return true;
			}
			current = current.next;
		}
		return false;
	}

	// Devuelve si element existe en el Set
	@Override
	public boolean contains(E element) {
		SetNode<E> current = head;
		while (current != null) {
			if (current.element.equals(element)) {
				return true;
			}
			current = current.next;
		}
		return false;
	}

	// Borra todos los elementos del Set
	@Override
	public void clear() {
		head = null;
		size = 0;
	}

	// Devuelve si el set esta vacio
	@Override
	public boolean isEmpty() {
		return head == null;
	}

	// Devuelve la cantidad de elementos
	@Override
	public int size() {
		return this.size;
	}

	// Devuelve un array con todos los elementos del Set
	@Override
	public Object[] toArray() {
		Object[] resultado = new Object[size];
		SetNode<E> current = head;
		int i = 0;
		while (current != null) {
			resultado[i] = current.element;
			current = current.next;
			i++;
		}
		return resultado;
	}

	// Devuelve un Set con todos los contenidos de este Set y other
	@Override
	public SimpleSet<E> unionWith(SimpleSet<E> other) {
		SimpleSet<E> resultado = new SimpleLinkedSet<>();
		// Agrega todos los de este set
		SetNode<E> current = head;
		while (current != null) {
			resultado.add(current.element);
			current = current.next;
		}
		// Agrega todos los de other (los duplicados se ignoran por add)
		Object[] otherArray = other.toArray();
		for (int i = 0; i < otherArray.length; i++) {
			resultado.add((E) otherArray[i]);
		}
		return resultado;
	}

	// Devuelve un Set con los elementos que este Set y other tienen en comun
	@Override
	public SimpleSet<E> intersectWith(SimpleSet<E> other) {
		SimpleSet<E> resultado = new SimpleLinkedSet<>();
		SetNode<E> current = head;
		while (current != null) {
			if (other.contains(current.element)) {
				resultado.add(current.element);
			}
			current = current.next;
		}
		return resultado;
	}

	// Devuelve un Set con los elementos de este set que no estan en other
	@Override
	public SimpleSet<E> differenceWith(SimpleSet<E> other) {
		SimpleSet<E> resultado = new SimpleLinkedSet<>();
		SetNode<E> current = head;
		while (current != null) {
			if (!other.contains(current.element)) {
				resultado.add(current.element);
			}
			current = current.next;
		}
		return resultado;
	}
}
