package bstModule;

public class LinkedAVL<K extends Comparable<K>, V> extends LinkedBST<K, V> {

	public LinkedAVL() {
		super();
	}

	// Devuelve la altura de un nodo (0 si es null)
	private int height(BSTNode<K, V> node) {
		if (node == null) return 0;
		return node.height;
	}

	// Actualiza la altura de un nodo basandose en sus hijos
	private void updateHeight(BSTNode<K, V> node) {
		int leftHeight = height(node.left);
		int rightHeight = height(node.right);
		node.height = 1 + Math.max(leftHeight, rightHeight);
	}

	// Calcula el factor de balanceo (altura izquierda - altura derecha)
	private int getBalance(BSTNode<K, V> node) {
		if (node == null) return 0;
		return height(node.left) - height(node.right);
	}

	// Rotacion simple a la derecha
	//       y                x
	//      / \              / \
	//     x   T3    ->     T1  y
	//    / \                  / \
	//   T1  T2              T2  T3
	private BSTNode<K, V> rotateRight(BSTNode<K, V> y) {
		BSTNode<K, V> x = y.left;
		BSTNode<K, V> T2 = x.right;

		// Realizar rotacion
		x.right = y;
		y.left = T2;

		// Actualizar alturas (primero y, luego x porque x ahora es padre)
		updateHeight(y);
		updateHeight(x);

		return x; // x es la nueva raiz del subarbol
	}

	// Rotacion simple a la izquierda
	//     x                  y
	//    / \                / \
	//   T1  y      ->      x   T3
	//      / \            / \
	//     T2  T3         T1  T2
	private BSTNode<K, V> rotateLeft(BSTNode<K, V> x) {
		BSTNode<K, V> y = x.right;
		BSTNode<K, V> T2 = y.left;

		// Realizar rotacion
		y.left = x;
		x.right = T2;

		// Actualizar alturas (primero x, luego y)
		updateHeight(x);
		updateHeight(y);

		return y; // y es la nueva raiz del subarbol
	}

	// Rebalancea un nodo si su factor de balanceo es > 1 o < -1
	private BSTNode<K, V> balance(BSTNode<K, V> node) {
		if (node == null) return null;

		updateHeight(node);
		int balanceFactor = getBalance(node);

		// Caso izquierda-izquierda (Left-Left)
		if (balanceFactor > 1 && getBalance(node.left) >= 0) {
			return rotateRight(node);
		}

		// Caso izquierda-derecha (Left-Right)
		if (balanceFactor > 1 && getBalance(node.left) < 0) {
			node.left = rotateLeft(node.left);
			return rotateRight(node);
		}

		// Caso derecha-derecha (Right-Right)
		if (balanceFactor < -1 && getBalance(node.right) <= 0) {
			return rotateLeft(node);
		}

		// Caso derecha-izquierda (Right-Left)
		if (balanceFactor < -1 && getBalance(node.right) > 0) {
			node.right = rotateRight(node.right);
			return rotateLeft(node);
		}

		return node; // Ya esta balanceado
	}

	// Override de putRecursive: inserta y luego rebalancea
	@Override
	protected BSTNode<K, V> putRecursive(BSTNode<K, V> node, K key, V value) {
		// Insercion normal del BST
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
			return node; // No hace falta rebalancear
		}

		// Rebalancear el nodo despues de insertar
		return balance(node);
	}

	// Override de removeRecursive: remueve y luego rebalancea
	@Override
	protected BSTNode<K, V> removeRecursive(BSTNode<K, V> node, K key) {
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

			// Caso 1: nodo hoja
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
			// Caso 3: dos hijos - reemplazar por sucesor inorder
			BSTNode<K, V> successor = findMin(node.right);
			node.key = successor.key;
			node.value = successor.value;
			node.right = removeRecursive(node.right, successor.key);
		}

		// Rebalancear el nodo despues de remover
		return balance(node);
	}
}
