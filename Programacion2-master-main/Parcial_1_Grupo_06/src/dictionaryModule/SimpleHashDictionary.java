package dictionaryModule;

class DictEntry<K, V> {
	K key;
	V value;
	DictEntry<K, V> next;

	public DictEntry(K key, V value) {
		this.key = key;
		this.value = value;
		this.next = null;
	}
}

public class SimpleHashDictionary<K, V> implements SimpleDictionary<K, V> {

	private DictEntry<K, V>[] buckets;
	private int size;
	private static final int INITIAL_CAPACITY = 16;

	@SuppressWarnings("unchecked")
	public SimpleHashDictionary() {
		this.buckets = new DictEntry[INITIAL_CAPACITY];
		this.size = 0;
	}

	// Calcula el indice del bucket para una clave
	private int getBucketIndex(K key) {
		return Math.abs(key.hashCode()) % buckets.length;
	}

	// Agrega o actualiza un par clave-valor
	@Override
	public void put(K key, V value) {
		int index = getBucketIndex(key);
		DictEntry<K, V> current = buckets[index];

		// Buscar si la clave ya existe en el bucket
		while (current != null) {
			if (current.key.equals(key)) {
				current.value = value; // Actualizar valor existente
				return;
			}
			current = current.next;
		}

		// La clave no existe, agregar al inicio del bucket
		DictEntry<K, V> newEntry = new DictEntry<>(key, value);
		newEntry.next = buckets[index];
		buckets[index] = newEntry;
		size++;
	}

	// Devuelve el valor asociado a la clave
	@Override
	public V get(K key) {
		int index = getBucketIndex(key);
		DictEntry<K, V> current = buckets[index];

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
		int index = getBucketIndex(key);
		DictEntry<K, V> current = buckets[index];
		DictEntry<K, V> prev = null;

		while (current != null) {
			if (current.key.equals(key)) {
				V value = current.value;
				if (prev == null) {
					buckets[index] = current.next; // Era el primero del bucket
				} else {
					prev.next = current.next; // Desenlazar del medio o final
				}
				size--;
				return value;
			}
			prev = current;
			current = current.next;
		}
		return null;
	}

	// Devuelve true si la clave existe
	@Override
	public boolean containsKey(K key) {
		return get(key) != null;
	}

	// Borra todos los pares
	@Override
	@SuppressWarnings("unchecked")
	public void clear() {
		this.buckets = new DictEntry[INITIAL_CAPACITY];
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

	// Devuelve un array con todas las claves
	@Override
	public Object[] keys() {
		Object[] result = new Object[size];
		int pos = 0;
		for (int i = 0; i < buckets.length; i++) {
			DictEntry<K, V> current = buckets[i];
			while (current != null) {
				result[pos] = current.key;
				pos++;
				current = current.next;
			}
		}
		return result;
	}
}
