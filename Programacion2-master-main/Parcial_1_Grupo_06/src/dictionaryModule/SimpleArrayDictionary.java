package dictionaryModule;

import listModule.SimpleList;
import listModule.SimpleLinkedList;

public class SimpleArrayDictionary<K, V> implements SimpleDictionary<K, V> {

	private Object[] keys;
	private Object[] values;
	private int size;
	private static final int INITIAL_CAPACITY = 10;

	public SimpleArrayDictionary() {
		this.keys = new Object[INITIAL_CAPACITY];
		this.values = new Object[INITIAL_CAPACITY];
		this.size = 0;
	}

	// Duplica la capacidad de los arrays cuando se llenan
	private void resize() {
		int nuevaCapacidad = keys.length * 2;
		Object[] nuevoKeys = new Object[nuevaCapacidad];
		Object[] nuevoValues = new Object[nuevaCapacidad];
		for (int i = 0; i < size; i++) {
			nuevoKeys[i] = keys[i];
			nuevoValues[i] = values[i];
		}
		this.keys = nuevoKeys;
		this.values = nuevoValues;
	}

	// Busca el indice de una clave, devuelve -1 si no existe
	private int findIndex(K key) {
		for (int i = 0; i < size; i++) {
			if (keys[i].equals(key)) {
				return i;
			}
		}
		return -1;
	}

	// Agrega o actualiza un par clave-valor
	@Override
	public void put(K key, V value) {
		if (key == null || value == null) {
			throw new NullPointerException("La clave y el valor no pueden ser null.");
		}
		int index = findIndex(key);
		if (index != -1) {
			values[index] = value; // Actualizar valor existente
		} else {
			if (size == keys.length) {
				resize();
			}
			keys[size] = key;
			values[size] = value;
			size++;
		}
	}

	// Devuelve el valor asociado a la clave
	@SuppressWarnings("unchecked")
	@Override
	public V get(K key) {
		if (key == null) {
			throw new NullPointerException("La clave no puede ser null.");
		}
		int index = findIndex(key);
		if (index == -1) return null;
		return (V) values[index];
	}

	// Remueve y devuelve el valor asociado a la clave
	@SuppressWarnings("unchecked")
	@Override
	public V remove(K key) {
		if (key == null) {
			throw new NullPointerException("La clave no puede ser null.");
		}
		int index = findIndex(key);
		if (index == -1) return null;

		V value = (V) values[index];

		// Desplazar elementos hacia la izquierda
		for (int i = index; i < size - 1; i++) {
			keys[i] = keys[i + 1];
			values[i] = values[i + 1];
		}
		keys[size - 1] = null;
		values[size - 1] = null;
		size--;
		return value;
	}

	// Devuelve true si la clave existe
	@Override
	public boolean containsKey(K key) {
		if (key == null) {
			throw new NullPointerException("La clave no puede ser null.");
		}
		return findIndex(key) != -1;
	}

	// Borra todos los pares
	@Override
	public void clear() {
		this.keys = new Object[INITIAL_CAPACITY];
		this.values = new Object[INITIAL_CAPACITY];
		this.size = 0;
	}

	// Devuelve la cantidad de pares
	@Override
	public int size() {
		return this.size;
	}

	// Devuelve true si esta vacio
	@Override
	public boolean isEmpty() {
		return this.size == 0;
	}

	// Devuelve una lista con todas las claves
	@SuppressWarnings("unchecked")
	@Override
	public SimpleList<K> keys() {
		SimpleList<K> result = new SimpleLinkedList<>();
		for (int i = 0; i < size; i++) {
			result.add((K) keys[i]);
		}
		return result;
	}

	// Devuelve una lista con todos los valores
	@SuppressWarnings("unchecked")
	@Override
	public SimpleList<V> values() {
		SimpleList<V> result = new SimpleLinkedList<>();
		for (int i = 0; i < size; i++) {
			result.add((V) values[i]);
		}
		return result;
	}
}
