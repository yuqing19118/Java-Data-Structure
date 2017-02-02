import java.util.*;

public class IntervalBSTIterator<K extends Interval> implements Iterator<K> {

	private Stack<IntervalBSTnode<K>> stack; //for keeping track of nodes
	private IntervalBSTnode<K> root;
	/**
	 * Constructs an IntervalBSTIterator using the root of the IntervalBST tree.
	 * @param root the root of the IntervalBST tree
	 */
	public IntervalBSTIterator(IntervalBSTnode<K> root) {
		stack = new Stack<IntervalBSTnode<K>>();
        this.root=root;
        sort(this.root);
	} 
	/**
     * Returns true if there are more elements to 
     * iterate over, otherwise returns false.
     * @return true if there are more elements to 
     * iterate over, otherwise returns false.
     */
    public boolean hasNext() {
		return !stack.isEmpty(); 
    }
    
    /**
	 * Returns the next element, while iterating over the 
	 * nodes of the IntervalBST tree.
	 * @return the next element, while iterating over the 
	 * nodes of the IntervalBST tree.
	 */
    public K next() {
		return stack.pop().getData();
    }

    public void remove() {
        // DO NOT CHANGE: you do not need to implement this method
        throw new UnsupportedOperationException();
    }
    private void sort(IntervalBSTnode<K> n){
    	if(n == null) {
    		return;
    	}
    	sort(n.getRight()); 
		stack.push(n);
		sort(n.getLeft());  	
 
    }
}