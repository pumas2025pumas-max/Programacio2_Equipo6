package setModule;

public interface SimpleSet<E> {
	// Inserta element si no existe ya en el Set. Devuelve si la operacion fue exitosa.
	public boolean add(E element);

	// Remueve element si existe en el Set. Devuelve si la operacion fue exitosa.
	public boolean remove(E element);

	// Devuelve si element existe en el Set.
	public boolean contains(E element);

	// Borra todos los elementos del Set, dejandolo vacio.
	public void clear();

	// Devuelve si el set esta vacio.
	public boolean isEmpty();

	// Devuelve la cantidad de elementos en el Set.
	public int size();

	// Devuelve un array con todos los elementos del Set.
	public Object[] toArray();

	// Devuelve un Set con todos los contenidos de este Set y other.
	public SimpleSet<E> unionWith(SimpleSet<E> other);

	// Devuelve un Set con los elementos que este Set y other tienen en comun.
	public SimpleSet<E> intersectWith(SimpleSet<E> other);

	// Devuelve un Set con los elementos de este set que no estan en other.
	public SimpleSet<E> differenceWith(SimpleSet<E> other);
}
