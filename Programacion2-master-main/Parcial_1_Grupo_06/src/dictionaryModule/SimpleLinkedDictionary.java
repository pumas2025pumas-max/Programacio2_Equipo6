package dictionaryModule;

import listModule.SimpleList;
import listModule.SimpleLinkedList;

class DictNode<K, V> {
	K key;
	V value;
	DictNode<K, V> next;

	public DictNode(K key, V value) {
		this.key = key;
		this.value = value;
		this.next = null;
	}
}

public class SimpleLinkedDictionary<K, V> implements SimpleDictionary<K, V> {

	private DictNode<K, V> head;
	private int size;

	public SimpleLinkedDictionary() {
		this.head = null;
		this.size = 0;
	}

	// Agrega o actualiza un par clave-valor
	@Override
	public void put(K key, V value) {
		if (key == null || value == null) {
			throw new NullPointerException("La clave y el valor no pueden ser null.");
		}

		// Buscar si la clave ya existe
		DictNode<K, V> current = head;
		while (current != null) {
			if (current.key.equals(key)) {
				current.value = value; // Actualizar valor existente
				return;
			}
			current = current.next;
		}

		// La clave no existe, agregar al inicio
		DictNode<K, V> newNode = new DictNode<>(key, value);
		newNode.next = head;
		head = newNode;
		size++;
	}

	// Devuelve el valor asociado a la clave
	@Override
	public V get(K key) {
		if (key == null) {
			throw new NullPointerException("La clave no puede ser null.");
		}

		DictNode<K, V> current = head;
		while (current != null) {
			if (current.key.equals(key)) {
				return current.value;
			}
			current = current.next;
		}
		return null;
	}

	// Remueve y devuelve el valor asociado a la clave
	@Override
	public V remove(K key) {
		if (key == null) {
			throw new NullPointerException("La clave no puede ser null.");
		}

		if (isEmpty()) return null;

		// Si es el primer nodo
		if (head.key.equals(key)) {
			V value = head.value;
			head = head.next;
			size--;
			return value;
		}

		// Buscar en el resto de la lista
		DictNode<K, V> current = head;
		while (current.next != null) {
			if (current.next.key.equals(key)) {
				V value = current.next.value;
				current.next = current.next.next;
				size--;
				return value;
			}
			current = current.next;
		}
		return null;
	}

	// Devuelve true si la clave existe
	@Override
	public boolean containsKey(K key) {
		if (key == null) {
			throw new NullPointerException("La clave no puede ser null.");
		}
		return get(key) != null;
	}

	// Borra todos los pares
	@Override
	public void clear() {
		head = null;
		size = 0;
	}

	// Devuelve la cantidad de pares
	@Override
	public int size() {
		return this.size;
	}

	// Devuelve true si esta vacio
	@Override
	public boolean isEmpty() {
		return head == null;
	}

	// Devuelve una lista con todas las claves
	@Override
	public SimpleList<K> keys() {
		SimpleList<K> result = new SimpleLinkedList<>();
		DictNode<K, V> current = head;
		while (current != null) {
			result.add(current.key);
			current = current.next;
		}
		return result;
	}
}
