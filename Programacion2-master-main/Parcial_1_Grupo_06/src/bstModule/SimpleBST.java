package bstModule;

public interface SimpleBST<K extends Comparable<K>, V> {
	// Inserta o actualiza un par clave-valor
	public void put(K key, V value);

	// Devuelve el valor asociado a la clave, o null si no existe
	public V get(K key);

	// Remueve y devuelve el valor asociado a la clave, o null si no existe
	public V remove(K key);

	// Devuelve true si la clave existe en el arbol
	public boolean containsKey(K key);

	// Borra todos los nodos del arbol
	public void clear();

	// Devuelve la cantidad de nodos
	public int size();

	// Devuelve true si el arbol esta vacio
	public boolean isEmpty();

	// Devuelve un array con todas las claves en orden (inorder)
	public Object[] keys();

	// Devuelve un array con todos los valores en orden (inorder)
	public Object[] values();
}
