package bstModule;

public class AVL<E> extends BSTNode<E> {
	
	public int height () {return getHeightRecursive(root); }
	
	protected int getHeightRecursive (TreeNode<E> current)
	{
		if (current == null) return -1;
		return 1 + Math.max(getHeightRecursive(current.left), getH)
	}
	
	private int balanceFactor()
	{
		return getHeightRecursive(root.left) - getHeightRecursive ()
	}
	

}
