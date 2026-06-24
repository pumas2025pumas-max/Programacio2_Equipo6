package bstModule;

public class ArrayBST<K extends Comparable<K>, V> implements SimpleBST<K, V> {

	private Object[] keys;
	private Object[] values;
	private boolean[] occupied; // Marca si la posicion tiene un nodo
	private int size;
	private int capacity;
	private static final int INITIAL_CAPACITY = 31; // 5 niveles completos (2^5 - 1)

	public ArrayBST() {
		this.capacity = INITIAL_CAPACITY;
		this.keys = new Object[capacity];
		this.values = new Object[capacity];
		this.occupied = new boolean[capacity];
		this.size = 0;
	}

	// Duplica la capacidad del array cuando se necesita
	private void resize() {
		int nuevaCapacidad = capacity * 2 + 1;
		Object[] nuevoKeys = new Object[nuevaCapacidad];
		Object[] nuevoValues = new Object[nuevaCapacidad];
		boolean[] nuevoOccupied = new boolean[nuevaCapacidad];
		for (int i = 0; i < capacity; i++) {
			nuevoKeys[i] = keys[i];
			nuevoValues[i] = values[i];
			nuevoOccupied[i] = occupied[i];
		}
		this.keys = nuevoKeys;
		this.values = nuevoValues;
		this.occupied = nuevoOccupied;
		this.capacity = nuevaCapacidad;
	}

	// Indice del hijo izquierdo
	private int leftChild(int index) {
		return 2 * index + 1;
	}

	// Indice del hijo derecho
	private int rightChild(int index) {
		return 2 * index + 2;
	}

	// Inserta o actualiza un par clave-valor
	@Override
	@SuppressWarnings("unchecked")
	public void put(K key, V value) {
		int index = 0;
		while (true) {
			// Asegurar que hay espacio
			while (index >= capacity) {
				resize();
			}

			if (!occupied[index]) {
				// Posicion vacia, insertar aqui
				keys[index] = key;
				values[index] = value;
				occupied[index] = true;
				size++;
				return;
			}

			int cmp = key.compareTo((K) keys[index]);
			if (cmp < 0) {
				index = leftChild(index);
			} else if (cmp > 0) {
				index = rightChild(index);
			} else {
				// Clave ya existe, actualizar valor
				values[index] = value;
				return;
			}
		}
	}

	// Busca el valor asociado a la clave
	@Override
	@SuppressWarnings("unchecked")
	public V get(K key) {
		int index = 0;
		while (index < capacity && occupied[index]) {
			int cmp = key.compareTo((K) keys[index]);
			if (cmp < 0) {
				index = leftChild(index);
			} else if (cmp > 0) {
				index = rightChild(index);
			} else {
				return (V) values[index];
			}
		}
		return null;
	}

	// Remueve y devuelve el valor asociado a la clave
	@Override
	@SuppressWarnings("unchecked")
	public V remove(K key) {
		int index = findIndex(key);
		if (index == -1) return null;

		V value = (V) values[index];
		removeAt(index);
		size--;
		return value;
	}

	// Busca el indice de una clave en el arbol
	@SuppressWarnings("unchecked")
	private int findIndex(K key) {
		int index = 0;
		while (index < capacity && occupied[index]) {
			int cmp = key.compareTo((K) keys[index]);
			if (cmp < 0) {
				index = leftChild(index);
			} else if (cmp > 0) {
				index = rightChild(index);
			} else {
				return index;
			}
		}
		return -1;
	}

	// Remueve el nodo en la posicion index y reacomoda el subarbol
	private void removeAt(int index) {
		int left = leftChild(index);
		int right = rightChild(index);

		boolean hasLeft = left < capacity && occupied[left];
		boolean hasRight = right < capacity && occupied[right];

		if (!hasLeft && !hasRight) {
			// Caso 1: nodo hoja
			keys[index] = null;
			values[index] = null;
			occupied[index] = false;
		} else if (!hasLeft) {
			// Caso 2a: solo hijo derecho - reemplazar por sucesor inorder
			int successor = findMinIndex(right);
			keys[index] = keys[successor];
			values[index] = values[successor];
			removeAt(successor);
		} else if (!hasRight) {
			// Caso 2b: solo hijo izquierdo - reemplazar por predecesor inorder
			int predecessor = findMaxIndex(left);
			keys[index] = keys[predecessor];
			values[index] = values[predecessor];
			removeAt(predecessor);
		} else {
			// Caso 3: dos hijos - reemplazar por sucesor inorder
			int successor = findMinIndex(right);
			keys[index] = keys[successor];
			values[index] = values[successor];
			removeAt(successor);
		}
	}

	// Encuentra el indice del nodo con la clave minima en el subarbol
	private int findMinIndex(int index) {
		while (true) {
			int left = leftChild(index);
			if (left < capacity && occupied[left]) {
				index = left;
			} else {
				return index;
			}
		}
	}

	// Encuentra el indice del nodo con la clave maxima en el subarbol
	private int findMaxIndex(int index) {
		while (true) {
			int right = rightChild(index);
			if (right < capacity && occupied[right]) {
				index = right;
			} else {
				return index;
			}
		}
	}

	// Devuelve true si la clave existe
	@Override
	public boolean containsKey(K key) {
		return findIndex(key) != -1;
	}

	// Borra todos los nodos
	@Override
	public void clear() {
		this.capacity = INITIAL_CAPACITY;
		this.keys = new Object[capacity];
		this.values = new Object[capacity];
		this.occupied = new boolean[capacity];
		this.size = 0;
	}

	// Devuelve la cantidad de nodos
	@Override
	public int size() {
		return this.size;
	}

	// Devuelve true si esta vacio
	@Override
	public boolean isEmpty() {
		return this.size == 0;
	}

	// Devuelve un array con todas las claves en orden (inorder traversal)
	@Override
	public Object[] keys() {
		Object[] result = new Object[size];
		int[] pos = {0};
		inorderKeys(0, result, pos);
		return result;
	}

	private void inorderKeys(int index, Object[] result, int[] pos) {
		if (index >= capacity || !occupied[index]) return;
		inorderKeys(leftChild(index), result, pos);
		result[pos[0]] = keys[index];
		pos[0]++;
		inorderKeys(rightChild(index), result, pos);
	}

	// Devuelve un array con todos los valores en orden (inorder traversal)
	@Override
	public Object[] values() {
		Object[] result = new Object[size];
		int[] pos = {0};
		inorderValues(0, result, pos);
		return result;
	}

	private void inorderValues(int index, Object[] result, int[] pos) {
		if (index >= capacity || !occupied[index]) return;
		inorderValues(leftChild(index), result, pos);
		result[pos[0]] = values[index];
		pos[0]++;
		inorderValues(rightChild(index), result, pos);
	}
}
