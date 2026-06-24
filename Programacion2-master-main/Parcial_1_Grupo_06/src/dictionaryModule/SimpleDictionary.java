package dictionaryModule;

import listModule.SimpleList;

public interface SimpleDictionary<K, V> {
	// Agrega o actualiza un par clave-valor
	public void put(K key, V value);

	// Devuelve el valor asociado a la clave, o null si no existe
	public V get(K key);

	// Remueve y devuelve el valor asociado a la clave, o null si no existe
	public V remove(K key);

	// Devuelve true si la clave existe en el diccionario
	public boolean containsKey(K key);

	// Borra todos los pares del diccionario
	public void clear();

	// Devuelve la cantidad de pares clave-valor
	public int size();

	// Devuelve true si el diccionario esta vacio
	public boolean isEmpty();

	// Devuelve una lista con todas las claves
	public SimpleList<K> keys();

	// Devuelve una lista con todos los valores
	public SimpleList<V> values();
}
