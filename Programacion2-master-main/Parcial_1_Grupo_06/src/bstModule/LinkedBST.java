package bstModule;

class BSTNode<K extends Comparable<K>, V> {
	K key;
	V value;
	BSTNode<K, V> left;
	BSTNode<K, V> right;

	public BSTNode(K key, V value) {
		this.key = key;
		this.value = value;
		this.left = null;
		this.right = null;
	}
}

public class LinkedBST<K extends Comparable<K>, V> implements SimpleBST<K, V> {

	private BSTNode<K, V> root;
	private int size;

	public LinkedBST() {
		this.root = null;
		this.size = 0;
	}

	// Inserta o actualiza un par clave-valor
	@Override
	public void put(K key, V value) {
		root = putRecursive(root, key, value);
	}

	private BSTNode<K, V> putRecursive(BSTNode<K, V> node, K key, V value) {
		if (node == null) {
			size++;
			return new BSTNode<>(key, value);
		}

		int cmp = key.compareTo(node.key);
		if (cmp < 0) {
			node.left = putRecursive(node.left, key, value);
		} else if (cmp > 0) {
			node.right = putRecursive(node.right, key, value);
		} else {
			node.value = value; // Clave ya existe, actualizar valor
		}
		return node;
	}

	// Busca el valor asociado a la clave
	@Override
	public V get(K key) {
		return getRecursive(root, key);
	}

	private V getRecursive(BSTNode<K, V> node, K key) {
		if (node == null) {
			return null;
		}

		int cmp = key.compareTo(node.key);
		if (cmp < 0) {
			return getRecursive(node.left, key);
		} else if (cmp > 0) {
			return getRecursive(node.right, key);
		} else {
			return node.value;
		}
	}

	// Remueve y devuelve el valor asociado a la clave
	@Override
	public V remove(K key) {
		V value = get(key);
		if (value != null) {
			root = removeRecursive(root, key);
			size--;
		}
		return value;
	}

	private BSTNode<K, V> removeRecursive(BSTNode<K, V> node, K key) {
		if (node == null) {
			return null;
		}

		int cmp = key.compareTo(node.key);
		if (cmp < 0) {
			node.left = removeRecursive(node.left, key);
		} else if (cmp > 0) {
			node.right = removeRecursive(node.right, key);
		} else {
			// Encontramos el nodo a eliminar

			// Caso 1: nodo hoja (sin hijos)
			if (node.left == null && node.right == null) {
				return null;
			}
			// Caso 2: un solo hijo
			if (node.left == null) {
				return node.right;
			}
			if (node.right == null) {
				return node.left;
			}
			// Caso 3: dos hijos - reemplazar por el sucesor inorder
			BSTNode<K, V> successor = findMin(node.right);
			node.key = successor.key;
			node.value = successor.value;
			node.right = removeRecursive(node.right, successor.key);
		}
		return node;
	}

	// Encuentra el nodo con la clave minima (el mas a la izquierda)
	private BSTNode<K, V> findMin(BSTNode<K, V> node) {
		while (node.left != null) {
			node = node.left;
		}
		return node;
	}

	// Devuelve true si la clave existe
	@Override
	public boolean containsKey(K key) {
		return get(key) != null;
	}

	// Borra todos los nodos
	@Override
	public void clear() {
		root = null;
		size = 0;
	}

	// Devuelve la cantidad de nodos
	@Override
	public int size() {
		return this.size;
	}

	// Devuelve true si esta vacio
	@Override
	public boolean isEmpty() {
		return root == null;
	}

	// Devuelve un array con todas las claves en orden (inorder traversal)
	@Override
	public Object[] keys() {
		Object[] result = new Object[size];
		int[] index = {0}; // Usamos array para poder modificar en recursion
		inorderKeys(root, result, index);
		return result;
	}

	private void inorderKeys(BSTNode<K, V> node, Object[] result, int[] index) {
		if (node == null) return;
		inorderKeys(node.left, result, index);
		result[index[0]] = node.key;
		index[0]++;
		inorderKeys(node.right, result, index);
	}

	// Devuelve un array con todos los valores en orden (inorder traversal)
	@Override
	public Object[] values() {
		Object[] result = new Object[size];
		int[] index = {0};
		inorderValues(root, result, index);
		return result;
	}

	private void inorderValues(BSTNode<K, V> node, Object[] result, int[] index) {
		if (node == null) return;
		inorderValues(node.left, result, index);
		result[index[0]] = node.value;
		index[0]++;
		inorderValues(node.right, result, index);
	}
}
