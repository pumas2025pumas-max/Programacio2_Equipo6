package setModule;

public class SimpleArraySet<E> implements SimpleSet<E> {

	private Object[] array;
	private int size;
	private static final int INITIAL_CAPACITY = 10;

	public SimpleArraySet() {
		this.array = new Object[INITIAL_CAPACITY];
		this.size = 0;
	}

	// Duplica la capacidad del array cuando se llena
	private void resize() {
		int nuevaCapacidad = array.length * 2;
		Object[] nuevoArray = new Object[nuevaCapacidad];
		for (int i = 0; i < size; i++) {
			nuevoArray[i] = array[i];
		}
		this.array = nuevoArray;
	}

	// Inserta element si no existe ya en el Set
	@Override
	public boolean add(E element) {
		if (element == null) {
			throw new NullPointerException("No se permiten elementos null en el Set");
		}
		if (contains(element)) {
			return false;
		}
		if (size == array.length) {
			resize();
		}
		array[size] = element;
		size++;
		return true;
	}

	// Remueve element si existe en el Set
	@Override
	public boolean remove(E element) {
		if (element == null) {
			throw new NullPointerException("No se permiten elementos null en el Set");
		}
		for (int i = 0; i < size; i++) {
			if (array[i].equals(element)) {
				for (int j = i; j < size - 1; j++) {
					array[j] = array[j + 1];
				}
				array[size - 1] = null;
				size--;
				return true;
			}
		}
		return false;
	}

	// Devuelve si element existe en el Set
	@Override
	public boolean contains(E element) {
		if (element == null) {
			throw new NullPointerException("No se permiten elementos null en el Set");
		}
		for (int i = 0; i < size; i++) {
			if (array[i].equals(element)) {
				return true;
			}
		}
		return false;
	}

	// Borra todos los elementos del Set
	@Override
	public void clear() {
		this.array = new Object[INITIAL_CAPACITY];
		this.size = 0;
	}

	// Devuelve si el set esta vacio
	@Override
	public boolean isEmpty() {
		return this.size == 0;
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
		for (int i = 0; i < size; i++) {
			resultado[i] = array[i];
		}
		return resultado;
	}

	// Devuelve un Set con todos los contenidos de este Set y other
	@Override
	public SimpleSet<E> unionWith(SimpleSet<E> other) {
		SimpleSet<E> resultado = new SimpleArraySet<>();
		// Agrega todos los de este set
		for (int i = 0; i < size; i++) {
			resultado.add((E) array[i]);
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
		SimpleSet<E> resultado = new SimpleArraySet<>();
		for (int i = 0; i < size; i++) {
			if (other.contains((E) array[i])) {
				resultado.add((E) array[i]);
			}
		}
		return resultado;
	}

	// Devuelve un Set con los elementos de este set que no estan en other
	@Override
	public SimpleSet<E> differenceWith(SimpleSet<E> other) {
		SimpleSet<E> resultado = new SimpleArraySet<>();
		for (int i = 0; i < size; i++) {
			if (!other.contains((E) array[i])) {
				resultado.add((E) array[i]);
			}
		}
		return resultado;
	}
}
