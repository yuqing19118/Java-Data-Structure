import java.util.Iterator;



public class IntervalBST<K extends Interval> implements SortedListADT<K> {
	// data field
	private IntervalBSTnode <K> root;
	
    public IntervalBST() {
		root = null;
	}
    /**
     * Inserts the given key into the Sorted List.
     * @param key
     */
	public void insert(K key){
		root = insert(root,key);
	}
	private IntervalBSTnode<K> insert(IntervalBSTnode<K> n, K key) throws IntervalConflictException {
	    if (n == null) {
	        return new IntervalBSTnode<K>(key);
	    }
	     
	    if (n.getKey().equals(key)) {
	        throw new IntervalConflictException ();
	    }
	    
	    if (key.compareTo(n.getKey()) < 0) {
	        // add key to the left subtree
	        n.setLeft( insert(n.getLeft(), key) );
	        return n;
	    }
	    
	    else {
	        // add key to the right subtree
	        n.setRight( insert(n.getRight(), key) );
	        return n;
	    }
	}
	/**
	 * Deletes the given key from the Sorted List. If the key is in 
	 * the Sorted List, the key is deleted and true is returned. If 
	 * the key is not in the Sorted List, the Sorted List is unchanged 
	 * and false is returned.
	 * @param key
	 */
	public boolean delete(K key) {
		int size = size();
		root = delete(root,key);
		if(size == size()) return false;
		else return true;
	}
	
	
	private IntervalBSTnode<K> delete(IntervalBSTnode<K> n, K key) {
	    if (n == null) {
	        return null;
	    }
	    
	    if (key.equals(n.getKey())) {
	        // n is the node to be removed
	        if (n.getLeft() == null && n.getRight() == null) {
	            return null;
	        }
	        if (n.getLeft() == null) {
	            return n.getRight();
	        }
	        if (n.getRight() == null) {
	            return n.getLeft();
	        }
	        
	        K smallVal = smallest(n.getRight());
	        n.setKey(smallVal);
	        n.setRight( delete(n.getRight(), smallVal) );
	        return n; 
	    }
	    
	    else if (key.compareTo(n.getKey()) < 0) {
	        n.setLeft( delete(n.getLeft(), key) );
	        return n;
	    }
	    
	    else {
	        n.setRight( delete(n.getRight(), key) );
	        return n;
	    }
	}
	/**
	 * Find the smallest key from the subtree of IntervalBSTnode n 
	 * @param n a IntervalBSTnode
	 * @return the smallest key
	 */
	private K smallest(IntervalBSTnode<K> n)
	// precondition: n is not null
	// postcondition: return the smallest value in the subtree rooted at n

	{
	    if (n.getLeft() == null) {
	        return n.getKey();
	    } else {
	        return smallest(n.getLeft());
	    }
	}
	/**
	 * Searches for the given key in the Sorted List and returns it. 
	 * If the key is not in the Sorted List, null is returned.
	 * @param key
	 */
	public K lookup(K key) {
		if(lookup(root,key)==true) return key;
		else return null; 
	}
	
	private boolean lookup(IntervalBSTnode<K> n, K key) {
	    if (n == null) {
	        return false;
	    }
	    
	    if (n.getKey().equals(key)) {
	        return true;
	    }
	    
	    if (key.compareTo(n.getKey()) < 0) {
	        // key < this node's key; look in left subtree
	        return lookup(n.getLeft(), key);
	    }
	    
	    else {
	        // key > this node's key; look in right subtree
	        return lookup(n.getRight(), key);
	    }
	}
	/**
	 * Returns the number of items in the Sorted List.
	 * @return the number of items in the Sorted List.
	 */
	public int size() {
		return size(root);
	}
	private int size(IntervalBSTnode<K> n){
		if(n==null)return 0;
		if(n.getLeft()==null && n.getRight()==null)return 1;
		return 1+size(n.getLeft())+size(n.getRight());
	}
	/**
	 * Returns true if and only if the Sorted List is empty.
	 * @return true if and only if the Sorted List is empty.
	 */
	public boolean isEmpty() {
		if(size()==0) return true;
		else return false;
	}
	/**
	 * Returns an iterator over the Sorted List that iterates 
	 * over the items in the Sorted List from smallest to largest.
	 * @return an iterator 
	 */
	public Iterator<K> iterator() {
		return  new IntervalBSTIterator<K>(root);
	}

}